/*
 * Created on Jun 24, 2004
 */
package org.egso.comms.nds.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;
import javax.xml.rpc.server.ServiceLifecycle;
import javax.xml.rpc.server.ServletEndpointContext;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.egso.comms.nds.conf.Configuration;
import org.egso.comms.nds.conf.ConfigurationException;
import org.egso.comms.nds.conf.ConfigurationFactory;
import org.egso.comms.nds.types.Application;
import org.egso.comms.nds.types.ApplicationList;
import org.egso.comms.nds.types.PIS;
import org.egso.comms.nds.types.PISList;
import org.egso.comms.nds.wsdl.NDS_PortType;
import org.egso.comms.nds.wsdl.NDS_Service_Impl;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import EDU.oswego.cs.dl.util.concurrent.ClockDaemon;
import EDU.oswego.cs.dl.util.concurrent.Mutex;
import EDU.oswego.cs.dl.util.concurrent.ThreadFactory;

import com.sun.xml.rpc.soap.message.SOAPMessageContext;

/**
 * Service endpoint implementation class.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public class NDS_PortType_Impl implements NDS_PortType, ServiceLifecycle {

    // Logging

    private static Logger logger = LogManager.getLogger(NDS_PortType_Impl.class);

    private static Logger soapLogger = LogManager.getLogger("org.egso.comms.soap");

    // Constants

    private static final String QUERY_PIS_BY_NAME = "SELECT FROM " + PIS.class.getName() + " AS pis WHERE pis.name LIKE ?";

    private static final String QUERY_PIS_BY_ENDPOINT = "SELECT FROM " + PIS.class.getName() + " AS pis WHERE pis.endpoint LIKE ?";

    private static final String QUERY_PIS_BY_ALIVE = "SELECT FROM " + PIS.class.getName() + " AS pis WHERE pis.alive = ?";

    private static final String QUERY_PIS_BY_APPLICATION_ID = "SELECT pis FROM " + PIS.class.getName() + " AS pis " + "," + Application.class.getName() + " AS application WHERE pis.id = application.parentPISId AND application.id = ?";

    private static final String QUERY_APPLICATIONS_BY_PARENT_PIS_ID = "SELECT FROM " + Application.class.getName() + " AS application WHERE application.parentPISId = ?";

    private static final String QUERY_APPLICATIONS_BY_NAME = "SELECT FROM " + Application.class.getName() + " AS application WHERE application.name LIKE ?";

    private static final String QUERY_APPLICATIONS_BY_TYPE = "SELECT FROM " + Application.class.getName() + " AS application WHERE application.type LIKE ?";

    private static final String QUERY_APPLICATIONS_BY_ENDPOINT = "SELECT FROM " + Application.class.getName() + " AS application WHERE application.endpoint LIKE ?";

    private static final String QUERY_APPLICATIONS_BY_ALIVE = "SELECT FROM " + Application.class.getName() + " AS application WHERE application.alive = ?";

    // Instance variables

    private ClockDaemon clockDaemon = null;

    private org.hibernate.cfg.Configuration hibernateConfiguration = null;

    private SessionFactory sessionFactory = null;

    private NDS_PortType primaryNDS = null;

    private long primaryNDSUpdateInterval = 0l;

    private ServletEndpointContext servletEndpointContext = null;

    private Mutex mutex = new Mutex();

    private boolean configured = false;

    private boolean init = false;

    // Configurable implementation

    public synchronized void configure(Configuration configuration) {
        logger.info("Configuring, hibernate connection URL: " + configuration.getHibernateConnectionURL() + ", hibernate configuration URL: " + configuration.getHibernateConfigurationURL() + ", primary NDS endpoint: " + configuration.getPrimaryNDSEndpoint() + ", primary NDS update interval: " + configuration.getPrimaryNDSUpdateInterval() + " ms");
        hibernateConfiguration = new org.hibernate.cfg.Configuration();
        Properties properties = new Properties();
        properties.put("hibernate.connection.url", configuration.getHibernateConnectionURL().toString());
        properties.put("hibernate.connection.username", configuration.getHibernateConnectionUsername());
        properties.put("hibernate.connection.password", configuration.getHibernateConnectionPassword());
        hibernateConfiguration.configure(configuration.getHibernateConfigurationURL());
        hibernateConfiguration.addProperties(properties);
        hibernateConfiguration.addClass(Application.class);
        hibernateConfiguration.addClass(PIS.class);
        if (configuration.getPrimaryNDSEndpoint() != null) {
            primaryNDS = new NDS_Service_Impl().getNDSPort();
            ((Stub) primaryNDS)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, configuration.getPrimaryNDSEndpoint().toString());
            primaryNDSUpdateInterval = configuration.getPrimaryNDSUpdateInterval();
        }
        configured = true;
    }

    // ServiceLifecycle implemenation

    /*
     * (non-Javadoc)
     * 
     * @see javax.xml.rpc.server.ServiceLifecycle#destroy()
     */
    public synchronized void init(Object context) throws ServiceException {
        if (!init) {
            try {
                logger.info("Initializing");
                if (!configured) {
                    configure(ConfigurationFactory.createConfiguration());
                }
                if (context instanceof ServletEndpointContext) {
                    servletEndpointContext = (ServletEndpointContext) context;
                }
                sessionFactory = hibernateConfiguration.buildSessionFactory();
                init = true;
                if (primaryNDS != null && primaryNDSUpdateInterval > 0) {
                    clockDaemon = new ClockDaemon();
                    clockDaemon.setThreadFactory(new DaemonThreadFactory());
                    clockDaemon.executePeriodically(primaryNDSUpdateInterval, new SynchronizeDirectoryRunnable(), true);
                }
                logger.info("Ready");
            } catch (ConfigurationException e) {
                throw new ServiceException("Failed to initialize", e);
            }
        }
    }

    public synchronized boolean isInit() {
        return init;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.xml.rpc.server.ServiceLifecycle#destroy()
     */
    public synchronized void destroy() {
        init = false;
        sessionFactory = null;
        if (primaryNDS != null && primaryNDSUpdateInterval > 0) {
            clockDaemon.shutDown();
            clockDaemon = null;
        }
        logger.info("Destroyed");
    }

    // NDS_PortType implementation

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.nds.wsdl.NDS_PortType#registerPIS(org.egso.comms.nds.types.PIS)
     */
    public PIS registerPIS(PIS pis) throws RemoteException {
        logger.info("Service operation called, operation: registerPIS, PIS id: " + pis.getId());
        logSoapMessage();
        try {
            mutex.acquire();
            if (primaryNDS != null) {
                primaryNDS.registerPIS(pis);
            }
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            PIS persistentPIS = (PIS) session.get(PIS.class, pis.getId());
            if (persistentPIS != null) {
                transaction.rollback();
                session.close();
                throw new RemoteException("Failed to register PIS, already registered, PIS id: " + pis.getId());
            }
            session.save(pis);
            transaction.commit();
            session.close();
            mutex.release();
            return pis;
        } catch (InterruptedException e) {
            throw new RemoteException("Fail to register PIS", e);
        } finally {
            mutex.release();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.nds.wsdl.NDS_PortType#registerApplication(org.egso.comms.nds.types.Application)
     */
    public Application registerApplication(Application application) throws RemoteException {
        logger.info("Service operation called, operation: registerApplication, for application with id: " + application.getId());
        logSoapMessage();
        try {
            mutex.acquire();
            if (primaryNDS != null) {
                primaryNDS.registerApplication(application);
            }
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Application persistentApplication = (Application) session.get(Application.class, application.getId());
            if (persistentApplication != null) {
                transaction.rollback();
                session.close();
                throw new RemoteException("Failed to register application, already registered, application id: " + application.getId());
            }
            session.save(application);
            transaction.commit();
            session.close();
            return application;
        } catch (InterruptedException e) {
            throw new RemoteException("Fail to register application", e);
        } finally {
            mutex.release();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.nds.wsdl.NDS_PortType#updatePIS(org.egso.comms.nds.types.PIS)
     */
    public void updatePIS(PIS pis) throws RemoteException {
        logger.info("Service operation called, operation: updatePIS, PIS id: " + pis.getId());
        logSoapMessage();
        try {
            mutex.acquire();
            if (primaryNDS != null) {
                primaryNDS.updatePIS(pis);
            }
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            PIS persistentPIS = (PIS) session.get(PIS.class, pis.getId());
            if (persistentPIS == null) {
                transaction.rollback();
                session.close();
                throw new RemoteException("Failed to update PIS, not registered, PIS id: " + pis.getId());
            }
            session.merge(pis);
            transaction.commit();
            session.close();
        } catch (InterruptedException e) {
            throw new RemoteException("Fail to update PIS", e);
        } finally {
            mutex.release();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.nds.wsdl.NDS_PortType#updateApplication(org.egso.comms.nds.types.Application)
     */
    public void updateApplication(Application application) throws RemoteException {
        logger.info("Service operation called, operation: updateApplication, application id: " + application.getId());
        logSoapMessage();
        try {
            mutex.acquire();
            if (primaryNDS != null) {
                primaryNDS.updateApplication(application);
            }
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Application persistentApplication = (Application) session.get(Application.class, application.getId());
            if (persistentApplication == null) {
                transaction.rollback();
                session.close();
                throw new RemoteException("Failed to update application, not registered, application id: " + application.getId());
            }
            session.merge(application);
            transaction.commit();
            session.close();
        } catch (InterruptedException e) {
            throw new RemoteException("Fail to update application", e);
        } finally {
            mutex.release();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.nds.wsdl.NDS_PortType#deregisterPIS(org.egso.comms.nds.types.PIS)
     */
    public void deregisterPIS(PIS pis) throws RemoteException {
        logger.info("Service operation called, operation: deregisterPIS, PIS id: " + pis.getId());
        logSoapMessage();
        try {
            mutex.acquire();
            if (primaryNDS != null) {
                primaryNDS.deregisterPIS(pis);
            }
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            PIS persistentPIS = (PIS) session.get(PIS.class, pis.getId());
            if (persistentPIS == null) {
                transaction.rollback();
                session.close();
                throw new RemoteException("Failed to deregister PIS, not registered, PIS id: " + pis.getId());
            }
            session.delete(persistentPIS);
            transaction.commit();
            session.close();
        } catch (InterruptedException e) {
            throw new RemoteException("Fail to deregister PIS", e);
        } finally {
            mutex.release();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.nds.wsdl.NDS_PortType#deregisterApplication(org.egso.comms.nds.types.Application)
     */
    public void deregisterApplication(Application application) throws RemoteException {
        logger.info("Service operation called, operation: deregisterApplication, application id: " + application.getId());
        logSoapMessage();
        try {
            mutex.acquire();
            if (primaryNDS != null) {
                primaryNDS.deregisterApplication(application);
            }
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Application persistentApplication = (Application) session.get(Application.class, application.getId());
            if (persistentApplication == null) {
                transaction.rollback();
                session.close();
                throw new RemoteException("Failed to deregister application, not registered, application id: " + application.getId());
            }
            session.delete(persistentApplication);
            transaction.commit();
            session.close();
        } catch (InterruptedException e) {
            throw new RemoteException("Fail to deregister application", e);
        } finally {
            mutex.release();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.nds.wsdl.NDS_PortType#selectPISById(java.lang.String)
     */
    public PIS selectPISById(String id) throws RemoteException {
        logger.info("Service operation called, operation: selectPISById, id equals:" + id);
        logSoapMessage();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        PIS pis = (PIS) session.get(PIS.class, id);
        transaction.commit();
        session.close();
        return pis;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.nds.wsdl.NDS_PortType#selectPISByName(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public PISList selectPISByName(String name) throws RemoteException {
        logger.info("Service operation called, operation: selectPISByName, name like: " + name);
        logSoapMessage();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(QUERY_PIS_BY_NAME);
        query.setString(0, name);
        PISList pisList = new PISList((PIS[]) query.list().toArray(new PIS[] {}));
        transaction.commit();
        session.close();
        return pisList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.nds.wsdl.NDS_PortType#selectPISByEndpoint(java.net.URI)
     */
    @SuppressWarnings("unchecked")
    public PISList selectPISByEndpoint(URI endpoint) throws RemoteException {
        logger.info("Service operation called, operation: selectPISByEndpoint, endpoint like: " + endpoint.toString());
        logSoapMessage();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(QUERY_PIS_BY_ENDPOINT);
        query.setString(0, endpoint.toString());
        PISList pisList = new PISList((PIS[]) query.list().toArray(new PIS[] {}));
        transaction.commit();
        session.close();
        return pisList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.nds.wsdl.NDS_PortType#selectPISByAlive(boolean)
     */
    @SuppressWarnings("unchecked")
    public PISList selectPISByAlive(boolean alive) throws RemoteException {
        logger.info("Service operation called, operation: selectPISByAlive, alive equals: " + alive);
        logSoapMessage();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(QUERY_PIS_BY_ALIVE);
        query.setBoolean(0, alive);
        PISList pisList = new PISList((PIS[]) query.list().toArray(new PIS[] {}));
        transaction.commit();
        session.close();
        return pisList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.nds.wsdl.NDS_PortType#selectPISByApplicationId(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public PISList selectPISByApplicationId(String applicationId) throws RemoteException {
        logger.info("Service operation called, operation: selectPISByApplicationId, application id equals: " + applicationId);
        logSoapMessage();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(QUERY_PIS_BY_APPLICATION_ID);
        query.setString(0, applicationId);
        PISList pisList = new PISList((PIS[]) query.list().toArray(new PIS[] {}));
        transaction.commit();
        session.close();
        return pisList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.nds.wsdl.NDS_PortType#selectApplicationById(java.lang.String)
     */
    public Application selectApplicationById(String id) throws RemoteException {
        logger.info("Service operation called, operation: selectApplicationsById, id equals: " + id);
        logSoapMessage();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Application application = (Application) session.get(Application.class, id);
        transaction.commit();
        session.close();
        return application;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.nds.wsdl.NDS_PortType#selectApplicationsByParentPISId(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public ApplicationList selectApplicationsByParentPISId(String parentPISId) throws RemoteException {
        logger.info("Service operation called, operation: selectApplicationsByParentPISId, parent PIS id equals: " + parentPISId);
        logSoapMessage();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(QUERY_APPLICATIONS_BY_PARENT_PIS_ID);
        query.setString(0, parentPISId);
        ApplicationList applicationList = new ApplicationList((Application[]) query.list().toArray(new Application[] {}));
        transaction.commit();
        session.close();
        return applicationList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.nds.wsdl.NDS_PortType#selectApplicationsByName(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public ApplicationList selectApplicationsByName(String name) throws RemoteException {
        logger.info("Service operation called, operation: selectApplicationsByName, name like: " + name);
        logSoapMessage();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(QUERY_APPLICATIONS_BY_NAME);
        query.setString(0, name);
        ApplicationList applicationList = new ApplicationList((Application[]) query.list().toArray(new Application[] {}));
        transaction.commit();
        session.close();
        return applicationList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.nds.wsdl.NDS_PortType#selectApplicationsByType(java.net.URI)
     */
    @SuppressWarnings("unchecked")
    public ApplicationList selectApplicationsByType(URI type) throws RemoteException {
        logger.info("Service operation called, operation: selectApplicationsByType, type like: " + type.toString());
        logSoapMessage();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(QUERY_APPLICATIONS_BY_TYPE);
        query.setString(0, type.toString());
        ApplicationList applicationList = new ApplicationList((Application[]) query.list().toArray(new Application[] {}));
        transaction.commit();
        session.close();
        return applicationList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.nds.wsdl.NDS_PortType#selectApplicationsByEndpoint(java.net.URI)
     */
    @SuppressWarnings("unchecked")
    public ApplicationList selectApplicationsByEndpoint(URI endpoint) throws RemoteException {
        logger.info("Service operation called, operation: selectApplicationsByEndpoint, endpoint like: " + endpoint.toString());
        logSoapMessage();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(QUERY_APPLICATIONS_BY_ENDPOINT);
        query.setString(0, endpoint.toString());
        ApplicationList applicationList = new ApplicationList((Application[]) query.list().toArray(new Application[] {}));
        transaction.commit();
        session.close();
        return applicationList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.nds.wsdl.NDS_PortType#selectApplicationsByAlive(boolean)
     */
    @SuppressWarnings("unchecked")
    public ApplicationList selectApplicationsByAlive(boolean alive) throws RemoteException {
        logger.info("Service operation called, operation: selectApplicationsByAlive, alive equals: " + alive);
        logSoapMessage();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(QUERY_APPLICATIONS_BY_ALIVE);
        query.setBoolean(0, alive);
        ApplicationList applicationList = new ApplicationList((Application[]) query.list().toArray(new Application[] {}));
        transaction.commit();
        session.close();
        return applicationList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.nds.wsdl.NDS_PortType#synchronizeDirectory()
     */
    public synchronized void synchronizeDirectory() throws RemoteException {
        logger.info("Service operation called, operation: synchronizeDirectory");
        logSoapMessage();
        try {
            mutex.acquire();
            if (primaryNDS != null) {
                synchronizeApplications(primaryNDS.selectApplicationsByName("%"));
                synchronizePIS(primaryNDS.selectPISByName("%"));
            } else {
                throw new RemoteException("Failed to synchronize directory, service is primary");
            }
        } catch (InterruptedException e) {
            throw new RemoteException("Fail to synchronize directory", e);
        } finally {
            mutex.release();
        }
    }

    // Private interface

    @SuppressWarnings("unchecked")
    private void synchronizeApplications(ApplicationList applicationList) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(QUERY_APPLICATIONS_BY_NAME);
        query.setString(0, "%");
        Map<String,Application> nonAuthoritative = new HashMap<String,Application>();
        for (Iterator<Application> iter = query.iterate(); iter.hasNext();) {
            Application persistentApplication = iter.next();
            nonAuthoritative.put(persistentApplication.getId(), persistentApplication);
        }
        Map<String,Application> authoritative = new HashMap<String,Application>();
        Application[] applications = applicationList.getApplications();
        for (int i = 0; i < applications.length; i++) {
            authoritative.put(applications[i].getId(), applications[i]);
        }
        Set<String> createSet = new HashSet<String>(authoritative.keySet());
        createSet.removeAll(nonAuthoritative.keySet());
        for (String s:createSet) {
            session.save(authoritative.get(s));
        }
        Set<String> removeSet = new HashSet<String>(nonAuthoritative.keySet());
        removeSet.removeAll(authoritative.keySet());
        for (String s:removeSet) {
            session.delete(nonAuthoritative.get(s));
        }
        Set<String> updateSet = new HashSet<String>(nonAuthoritative.keySet());
        updateSet.retainAll(authoritative.keySet());
        for (String s:updateSet) {
            session.merge(authoritative.get(s));
        }
        transaction.commit();
        session.close();
        logger.info("Synchronized applications, created: " + createSet.size() + ", removed: " + removeSet.size() + ", updated: " + updateSet.size());
    }

    @SuppressWarnings("unchecked")
    private void synchronizePIS(PISList pisList) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(QUERY_PIS_BY_NAME);
        query.setString(0, "%");
        Map<String,PIS> nonAuthoritative = new HashMap<String,PIS>();
        for (Iterator<PIS> iter = query.iterate(); iter.hasNext();) {
            PIS persistentPIS = iter.next();
            nonAuthoritative.put(persistentPIS.getId(), persistentPIS);
        }
        Map<String,PIS> authoritative = new HashMap<String,PIS>();
        PIS[] pis = pisList.getPis();
        for (int i = 0; i < pis.length; i++) {
            authoritative.put(pis[i].getId(), pis[i]);
        }
        Set<String> createSet = new HashSet<String>(authoritative.keySet());
        createSet.removeAll(nonAuthoritative.keySet());
        for (String s:createSet) {
            session.save(authoritative.get(s));
        }
        Set<String> removeSet = new HashSet<String>(nonAuthoritative.keySet());
        removeSet.removeAll(authoritative.keySet());
        for (String s:removeSet) {
            session.delete(nonAuthoritative.get(s));
        }
        Set<String> updateSet = new HashSet<String>(nonAuthoritative.keySet());
        updateSet.retainAll(authoritative.keySet());
        for (String s:updateSet) {
            session.merge(authoritative.get(s));
        }
        transaction.commit();
        session.close();
        logger.info("Synchronized PIS, created: " + createSet.size() + ", removed: " + removeSet.size() + ", updated: " + updateSet.size());
    }

    private void logSoapMessage() {
        if (soapLogger.isInfoEnabled() && servletEndpointContext != null) {
            SOAPMessageContext soapMessageContext = (SOAPMessageContext) servletEndpointContext.getMessageContext();
            SOAPMessage soapMessage = soapMessageContext.getMessage();
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
            try {
                soapMessage.writeTo(byteOutputStream);
                soapLogger.info(byteOutputStream.toString());
            } catch (SOAPException e) {
                soapLogger.error("Failed to log SOAP message", e);
            } catch (IOException e) {
                soapLogger.error("Failed to log SOAP message", e);
            }
        }
    }

    // Member classes

    protected class SynchronizeDirectoryRunnable implements Runnable {

        public void run() {
            try {
                logger.info("Running");
                synchronizeDirectory();
            } catch (RemoteException e) {
                logger.error(e);
            }
        }

    }

    protected class DaemonThreadFactory implements ThreadFactory {

        public Thread newThread(Runnable command) {
            Thread thread = new Thread(command);
            thread.setDaemon(true);
            return thread;
        }

    }

}
