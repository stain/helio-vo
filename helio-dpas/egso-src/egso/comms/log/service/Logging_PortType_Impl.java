/*
 * Created on Jun 11, 2004
 */
package org.egso.comms.log.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Properties;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.server.ServiceLifecycle;
import javax.xml.rpc.server.ServletEndpointContext;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.id.uuid.VersionFourGenerator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.egso.comms.log.conf.Configuration;
import org.egso.comms.log.conf.ConfigurationException;
import org.egso.comms.log.conf.ConfigurationFactory;
import org.egso.comms.log.types.Event;
import org.egso.comms.log.types.EventList;
import org.egso.comms.log.types.Log;
import org.egso.comms.log.wsdl.Logging_PortType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.sun.xml.rpc.soap.message.SOAPMessageContext;

/**
 * Service endpoint implementation class.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public class Logging_PortType_Impl implements Logging_PortType, ServiceLifecycle {

    // Logging

    private static Logger logger = LogManager.getLogger(Logging_PortType_Impl.class);

    private static Logger soapLogger = LogManager.getLogger("org.egso.comms.soap");

    // Instance variables

    private org.hibernate.cfg.Configuration hibernateConfiguration = null;

    private SessionFactory sessionFactory = null;

    private ServletEndpointContext servletEndpointContext = null;

    private VersionFourGenerator uuidGenerator = new VersionFourGenerator();

    private boolean init = false;

    private boolean configured = false;

    // Configurable implementation

    public synchronized void configure(Configuration configuration) {
        logger.info("Configuring, hibernate database URL: " + configuration.getHibernateConnectionURL() + ", hibernate configuration URL: " + configuration.getHibernateConfigurationURL());
        hibernateConfiguration = new org.hibernate.cfg.Configuration();
        Properties properties = new Properties();
        properties.put("hibernate.connection.url", configuration.getHibernateConnectionURL().toString());
        properties.put("hibernate.connection.username", configuration.getHibernateConnectionUsername());
        properties.put("hibernate.connection.password", configuration.getHibernateConnectionPassword());
        hibernateConfiguration.configure(configuration.getHibernateConfigurationURL());
        hibernateConfiguration.addProperties(properties);
        hibernateConfiguration.addClass(Log.class);
        hibernateConfiguration.addClass(Event.class);
        configured = true;
    }

    // ServiceLifecycle implementation

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
    public void destroy() {
        init = false;
        sessionFactory = null;
        logger.info("Destroyed");
    }

    // Logging_PortType implementation

    public Log createLog(Log log) throws RemoteException {
        logger.info("Service operation called, operation: createLog, log owner id: " + log.getOwnerId());
        logSoapMessage();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        log.setId(uuidGenerator.nextIdentifier().toString());
        session.save(log);
        transaction.commit();
        session.close();
        return log;
    }

    public void appendEvents(EventList eventList) throws RemoteException {
        logger.info("Service operation called, operation: appendEvents");
        logSoapMessage();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Event[] events = eventList.getEvents();
        for (int i = 0; i < events.length; i++) {
            events[i].setId(uuidGenerator.nextIdentifier().toString());
            session.save(events[i]);
            logger.info("Appended event, event id: " + events[i].getId() + ", event log id: " + events[i].getLogId());
        }
        transaction.commit();
        session.close();
    }
    
    // Private interface

    private void logSoapMessage() {
        if (soapLogger.isInfoEnabled()) {
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

}
