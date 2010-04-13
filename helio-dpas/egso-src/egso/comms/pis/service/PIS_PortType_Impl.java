/*
 * Created on Mar 4, 2004
 */
package org.egso.comms.pis.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;
import javax.xml.rpc.server.ServiceLifecycle;
import javax.xml.rpc.server.ServletEndpointContext;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.egso.comms.common.messaging.MessageTransport;
import org.egso.comms.common.messaging.MessagingException;
import org.egso.comms.eis.wsdl.Application_PortType;
import org.egso.comms.eis.wsdl.Application_Service_Impl;
import org.egso.comms.nds.service.NDS_PortType_Impl;
import org.egso.comms.nds.types.Application;
import org.egso.comms.nds.types.ApplicationList;
import org.egso.comms.nds.types.PIS;
import org.egso.comms.nds.types.PISList;
import org.egso.comms.pis.conf.Configuration;
import org.egso.comms.pis.conf.ConfigurationException;
import org.egso.comms.pis.conf.ConfigurationFactory;
import org.egso.comms.pis.types.Message;
import org.egso.comms.pis.types.MessageStatus;
import org.egso.comms.pis.wsdl.PIS_PortType;
import org.egso.comms.pis.wsdl.PIS_Service_Impl;

import EDU.oswego.cs.dl.util.concurrent.PooledExecutor;

import com.sun.xml.rpc.client.ClientTransport;
import com.sun.xml.rpc.client.ClientTransportFactory;
import com.sun.xml.rpc.client.StubBase;
import com.sun.xml.rpc.soap.message.SOAPMessageContext;

/**
 * Service endpoint implementation class.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public class PIS_PortType_Impl implements PIS_PortType, ServiceLifecycle {

    // Logging

    private Logger logger = LogManager.getLogger(PIS_PortType_Impl.class);

    private Logger soapLogger = LogManager.getLogger("org.egso.comms.soap");

    // Instance variables

    private String id = null;

    private MessageTransport messageTransport = new MessageTransport();

    private NDS_PortType_Impl nds = new NDS_PortType_Impl();

    private PooledExecutor pooledExecutor = new PooledExecutor();

    private ServletEndpointContext servletEndpointContext = null;

    private boolean init = false;

    private boolean configured = false;

    // Configurable implementation

    public synchronized void configure(Configuration configuration) {
        logger.info("Configuring, id: " + configuration.getId() + ", thread pool size: " + configuration.getThreadPoolSize());
        id = configuration.getId();
        pooledExecutor.setMaximumPoolSize(configuration.getThreadPoolSize());
        nds.configure(configuration);
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
                pooledExecutor.setKeepAliveTime(-1);
                pooledExecutor.createThreads(pooledExecutor.getMaximumPoolSize());
                messageTransport.init();
                nds.init(null);
                init = true;
                logger.info("Ready");
            } catch (ConfigurationException e) {
                throw new ServiceException("Failed to initialize", e);
            } catch (MessagingException e) {
                throw new ServiceException("Failed to initialize", e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.xml.rpc.server.ServiceLifecycle#destroy()
     */
    public synchronized void destroy() {
        init = false;
        pooledExecutor.shutdownAfterProcessingCurrentlyQueuedTasks();
        pooledExecutor.setKeepAliveTime(0);
        if (nds instanceof org.egso.comms.nds.service.NDS_PortType_Impl) {
            ((org.egso.comms.nds.service.NDS_PortType_Impl) nds).destroy();
        }
        messageTransport.destroy();
        logger.info("Destroyed");
    }

    public synchronized boolean isInit() {
        return init;
    }

    // PIS_PortType implementation

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.pis.wsdl.PIS_PortType#deliverMessage(org.egso.comms.pis.types.Message)
     */
    public void deliverMessage(Message message) throws RemoteException {
        try {
            logger.info("Service operation called, operation: deliverMessage, message delivery id: " + message.getHeader().getDeliveryId());
            logSoapMessage();
            pooledExecutor.execute(new DeliverMessageRunnable(message));
        } catch (InterruptedException e) {
            logger.error("Failed to handoff runnable to executor", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.pis.wsdl.PIS_PortType#returnMessage(org.egso.comms.pis.types.Message)
     */
    public void returnMessage(Message message) throws RemoteException {
        try {
            logger.info("Service operation called, operation: returnMessage, message delivery id: " + message.getHeader().getDeliveryId());
            logSoapMessage();
            pooledExecutor.execute(new ReturnMessageRunnable(message));
        } catch (InterruptedException e) {
            logger.error("Failed to handoff runnable to executor", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.pis.wsdl.PIS_PortType#selectPISById(java.lang.String)
     */
    public PIS selectPISById(String id) throws RemoteException {
        return nds.selectPISById(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.pis.wsdl.PIS_PortType#selectPISByName(java.lang.String)
     */
    public PISList selectPISByName(String name) throws RemoteException {
        return nds.selectPISByName(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.pis.wsdl.PIS_PortType#selectPISByEndpoint(java.net.URI)
     */
    public PISList selectPISByEndpoint(URI endpoint) throws RemoteException {
        return nds.selectPISByEndpoint(endpoint);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.pis.wsdl.PIS_PortType#selectPISByAlive(boolean)
     */
    public PISList selectPISByAlive(boolean alive) throws RemoteException {
        return nds.selectPISByAlive(alive);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.pis.wsdl.PIS_PortType#selectPISByApplicationId(java.lang.String)
     */
    public PISList selectPISByApplicationId(String applicationId) throws RemoteException {
        return nds.selectPISByApplicationId(applicationId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.pis.wsdl.PIS_PortType#registerApplication(org.egso.comms.nds.types.Application)
     */
    public Application registerApplication(Application application) throws RemoteException {
        application.setParentPISId(id);
        return nds.registerApplication(application);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.pis.wsdl.PIS_PortType#updateApplication(org.egso.comms.nds.types.Application)
     */
    public void updateApplication(Application application) throws RemoteException {
        application.setParentPISId(id);
        nds.updateApplication(application);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.pis.wsdl.PIS_PortType#deregisterApplication(org.egso.comms.nds.types.Application)
     */
    public void deregisterApplication(Application application) throws RemoteException {
        application.setParentPISId(id);
        nds.deregisterApplication(application);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.pis.wsdl.PIS_PortType#selectApplicationsById(java.lang.String)
     */
    public ApplicationList selectApplicationsById(String id) throws RemoteException {
        return nds.selectApplicationsByParentPISId(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.pis.wsdl.PIS_PortType#selectApplicationsByParentPISId(java.lang.String)
     */
    public ApplicationList selectApplicationsByParentPISId(String parentPISId) throws RemoteException {
        return nds.selectApplicationsByParentPISId(parentPISId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.pis.wsdl.PIS_PortType#selectApplicationsByName(java.lang.String)
     */
    public ApplicationList selectApplicationsByName(String name) throws RemoteException {
        return nds.selectApplicationsByName(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.pis.wsdl.PIS_PortType#selectApplicationsByType(java.net.URI)
     */
    public ApplicationList selectApplicationsByType(URI type) throws RemoteException {
        return nds.selectApplicationsByType(type);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.pis.wsdl.PIS_PortType#selectApplicationsByEndpoint(java.net.URI)
     */
    public ApplicationList selectApplicationsByEndpoint(URI endpoint) throws RemoteException {
        return nds.selectApplicationsByEndpoint(endpoint);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.pis.wsdl.PIS_PortType#selectApplicationsByAlive(boolean)
     */
    public ApplicationList selectApplicationsByAlive(boolean alive) throws RemoteException {
        return nds.selectApplicationsByAlive(alive);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.pis.wsdl.PIS_PortType#synchronizeDirectory()
     */
    public void synchronizeDirectory() throws RemoteException {
        nds.synchronizeDirectory();
    }

    // Private interface

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

    private void stampHeader(Message message) {
        String[] deliveryNodes = message.getHeader().getDeliveryNodes();
        String[] updatedDeliveryNodes = new String[deliveryNodes.length + 1];
        System.arraycopy(deliveryNodes, 0, updatedDeliveryNodes, 0, deliveryNodes.length);
        updatedDeliveryNodes[updatedDeliveryNodes.length - 1] = id;
        message.getHeader().setDeliveryNodes(updatedDeliveryNodes);
    }

    private PIS resolveRecipientParentPIS(Message message) {
        PIS parentPIS = null;
        try {
            logger.info("Resolving parent PIS for recipient, message delivery id: " + message.getHeader().getDeliveryId());
            PIS[] selectParentPIS = nds.selectPISByApplicationId(message.getHeader().getRecipientId()).getPis();
            if (selectParentPIS == null || selectParentPIS.length == 0) {
                logger.warn("Failed to resolve parent PIS for recipient, message delivery id: " + message.getHeader().getDeliveryId());
                message.getHeader().setStatusCode(MessageStatus.RECIPIENT_PIS_NOT_FOUND);
            } else {
                parentPIS = selectParentPIS[0];
            }
        } catch (RemoteException e) {
            logger.warn("Failed to resolve parent PIS for recipient, message delivery id: " + message.getHeader().getDeliveryId(), e);
            message.getHeader().setStatusCode(MessageStatus.NDS_UNAVAILABLE);
        }
        return parentPIS;
    }

    private PIS resolveSenderParentPIS(Message message) {
        PIS parentPIS = null;
        try {
            logger.info("Resolving parent PIS for sender, message delivery id: " + message.getHeader().getDeliveryId());
            PIS[] selectParentPIS = nds.selectPISByApplicationId(message.getHeader().getSenderId()).getPis();
            if (selectParentPIS == null || selectParentPIS.length == 0) {
                logger.warn("Failed to resolve parent PIS for sender, message delivery id: " + message.getHeader().getDeliveryId());
                message.getHeader().setStatusCode(MessageStatus.SENDER_PIS_NOT_FOUND);
            } else {
                parentPIS = selectParentPIS[0];
            }
        } catch (RemoteException e) {
            logger.warn("Failed to resolve parent PIS for sender, message delivery id: " + message.getHeader().getDeliveryId(), e);
            message.getHeader().setStatusCode(MessageStatus.NDS_UNAVAILABLE);
        }
        return parentPIS;
    }

    private Application resolveRecipient(Message message) {
        Application recipient = null;
        try {
            logger.info("Resolving recipient, message delivery id: " + message.getHeader().getDeliveryId());
            recipient = nds.selectApplicationById(message.getHeader().getRecipientId());
            if (recipient == null) {
                logger.warn("Failed to resolve recipient, message delivery id:, " + message.getHeader().getDeliveryId());
                message.getHeader().setStatusCode(MessageStatus.RECIPIENT_NOT_FOUND);
            }
        } catch (RemoteException e) {
            logger.warn("Failed to resolve recipient, message delivery id: " + message.getHeader().getDeliveryId(), e);
            message.getHeader().setStatusCode(MessageStatus.NDS_UNAVAILABLE);
        }
        return recipient;
    }

    private Application resolveSender(Message message) {
        Application sender = null;
        try {
            logger.info("Resolving sender, message delivery id: " + message.getHeader().getDeliveryId());
            sender = nds.selectApplicationById(message.getHeader().getSenderId());
            if (sender == null) {
                logger.warn("Failed to resolve sender, message delivery id:, " + message.getHeader().getDeliveryId());
                message.getHeader().setStatusCode(MessageStatus.SENDER_NOT_FOUND);
            }
        } catch (RemoteException e) {
            logger.warn("Failed to resolve sender, message delivery id: " + message.getHeader().getDeliveryId(), e);
            message.getHeader().setStatusCode(MessageStatus.NDS_UNAVAILABLE);
        }
        return sender;
    }

    private boolean deliverMessageLocally(Application recipient, Message message) {
        boolean delivered = false;
        try {
            logger.info("Delivering message to local recipient, message delivery id: " + message.getHeader().getDeliveryId());
            Application_PortType application = new Application_Service_Impl().getApplicationPort();
            if (recipient.getEndpoint().toString().startsWith("jms:")) {
                ((StubBase) application)._setTransportFactory(new MessageTransportFactory());
            }
            ((Stub) application)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, recipient.getEndpoint().toString());
            application.receiveMessage(message);
            delivered = true;
        } catch (RemoteException e) {
            logger.warn("Failed to deliver message to local recipient, message delivery id: " + message.getHeader().getDeliveryId(), e);
            message.getHeader().setStatusCode(MessageStatus.RECIPIENT_UNAVAILABLE);
        }
        return delivered;
    }

    private boolean returnMessageLocally(Application sender, Message message) {
        boolean returned = false;
        try {
            logger.info("Returning message to local sender, message delivery id: " + message.getHeader().getDeliveryId());
            Application_PortType application = new Application_Service_Impl().getApplicationPort();
            if (sender.getEndpoint().toString().startsWith("jms:")) {
                ((StubBase) application)._setTransportFactory(new MessageTransportFactory());
            }
            ((Stub) application)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, sender.getEndpoint().toString());
            application.returnMessage(message);
            returned = true;
        } catch (RemoteException e) {
            logger.warn("Failed to return message to local sender, message delivery id: " + message.getHeader().getDeliveryId(), e);
            message.getHeader().setStatusCode(MessageStatus.SENDER_UNAVAILABLE);
        }
        return returned;
    }

    private boolean delegateMessageDelivery(PIS parentPIS, Message message) {
        boolean delegated = false;
        try {
            PIS_PortType pis = new PIS_Service_Impl().getPISPort();
            ((Stub) pis)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, parentPIS.getEndpoint().toString());
            logger.info("Delegating message delivery to parent PIS, message delivery id: " + message.getHeader().getDeliveryId());
            pis.deliverMessage(message);
            delegated = true;
        } catch (RemoteException e) {
            logger.info("Failed to delegate message delivery to parent PIS, message delivery id: " + message.getHeader().getDeliveryId(), e);
            message.getHeader().setStatusCode(MessageStatus.RECIPIENT_PIS_UNAVAILABLE);
        }
        return delegated;
    }

    private boolean delegateMessageReturn(PIS parentPIS, Message message) {
        boolean delegated = false;
        try {
            PIS_PortType pis = new PIS_Service_Impl().getPISPort();
            ((Stub) pis)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, parentPIS.getEndpoint().toString());
            logger.info("Delegating message return to parent PIS, message delivery id: " + message.getHeader().getDeliveryId());
            pis.returnMessage(message);
            delegated = true;
        } catch (RemoteException e) {
            logger.info("Failed to delegate message return to parent PIS, message delivery id: " + message.getHeader().getDeliveryId(), e);
            message.getHeader().setStatusCode(MessageStatus.SENDER_PIS_UNAVAILABLE);
        }
        return delegated;
    }

    private void discardMessage(Message message) {
        logger.error("Discarded message, message delivery id: " + message.getHeader().getDeliveryId() + ", message status code: " + MessageStatus.getString(message.getHeader().getStatusCode()));
    }

    // Member classes

    protected class DeliverMessageRunnable implements Runnable {

        // Instance variables

        private Message message = null;

        private PIS parentPIS = null;

        private Application recipient = null;

        // Constructors

        public DeliverMessageRunnable(Message message) {
            this.message = message;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            try {
                logger.info("Running, message delivery id: " + message.getHeader().getDeliveryId());
                stampHeader(message);
                parentPIS = resolveRecipientParentPIS(message);
                if (parentPIS == null) {
                    pooledExecutor.execute(new ReturnMessageRunnable(message));
                } else if (parentPIS.getId().equals(id)) {
                    recipient = resolveRecipient(message);
                    if (recipient == null) {
                        pooledExecutor.execute(new ReturnMessageRunnable(message));
                    } else {
                        if (!deliverMessageLocally(recipient, message)) {
                            pooledExecutor.execute(new ReturnMessageRunnable(message));
                        }
                    }
                } else if (parentPIS != null) {
                    if (!delegateMessageDelivery(parentPIS, message)) {
                        pooledExecutor.execute(new ReturnMessageRunnable(message));
                    }
                }
            } catch (InterruptedException e) {
                logger.error("Failed to handoff runnable to executor", e);
            }
        }

    }

    protected class ReturnMessageRunnable implements Runnable {

        // Instance variables

        private Message message = null;

        private PIS parentPIS = null;

        private Application sender = null;

        // Constructors

        public ReturnMessageRunnable(Message message) {
            this.message = message;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            logger.info("Running, message delivery id: " + message.getHeader().getDeliveryId());
            stampHeader(message);
            parentPIS = resolveSenderParentPIS(message);
            if (parentPIS == null) {
                discardMessage(message);
            } else if (parentPIS.getId().equals(id)) {
                sender = resolveSender(message);
                if (sender == null) {
                    discardMessage(message);
                } else {
                    if (!returnMessageLocally(sender, message)) {
                        discardMessage(message);
                    }
                }
            } else if (parentPIS != null) {
                if (!delegateMessageReturn(parentPIS, message)) {
                    discardMessage(message);
                }
            }
        }
    }

    protected class MessageTransportFactory implements ClientTransportFactory {

        public ClientTransport create() {
            return messageTransport;
        }

    }

}