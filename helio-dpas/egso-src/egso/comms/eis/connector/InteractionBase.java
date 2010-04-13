/*
 * Created on Oct 21, 2003
 */
package org.egso.comms.eis.connector;

import java.net.URI;
import java.rmi.RemoteException;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.xml.rpc.Stub;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

import org.apache.commons.id.uuid.VersionFourGenerator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.egso.comms.common.messaging.MessagePeer;
import org.egso.comms.common.messaging.MessagingException;
import org.egso.comms.eis.adapter.AdapterException;
import org.egso.comms.eis.conf.Configuration;
import org.egso.comms.eis.conf.ConfigurationException;
import org.egso.comms.eis.conf.ConfigurationFactory;
import org.egso.comms.eis.types.MessageSerializer;
import org.egso.comms.eis.types.ReceiveMessage;
import org.egso.comms.eis.types.ReturnMessage;
import org.egso.comms.eis.types.SerializationException;
import org.egso.comms.eis.wsdl.Application_PortType;
import org.egso.comms.nds.types.ApplicationList;
import org.egso.comms.pis.types.Header;
import org.egso.comms.pis.types.Message;
import org.egso.comms.pis.types.MessageStatus;
import org.egso.comms.pis.wsdl.PIS_PortType;
import org.egso.comms.pis.wsdl.PIS_Service_Impl;

import EDU.oswego.cs.dl.util.concurrent.PooledExecutor;

/**
 * Principle class for asynchronous messaging.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public class InteractionBase {

    // Logging

    private static Logger logger = LogManager.getLogger(InteractionBase.class);

    // Constants

    private static final String DELIVERY_ID_PREFIX = "Message-";

    // Instance variables

    private MessagePeer messagePeer = new MessagePeer();

    private URI jmsEndpoint = null;
    
    private PIS_PortType parentPIS = new PIS_Service_Impl().getPISPort();

    private PooledExecutor pooledExecutor = new PooledExecutor();;

    private VersionFourGenerator uuidGenerator = new VersionFourGenerator();
    
    private MessageSerializer messageSerializer = null;

    private Application_PortType application = null;

    private SOAPFactory soapFactory = null;

    private String id = null;

    private int listeners = 0;

    private boolean configured = false;

    private boolean init = false;

    //  Configurable implementation

    /**
     * Configures the object. This may be called <b>before
     * initialization </b> in order to supply an external
     * configuration. It is an error to call this method whilst the
     * object is initialized.
     * 
     * @param configuration the configuration to use
     * @see #init()
     * @see #isInit()
     * @see #destroy()
     */
    public synchronized void configure(Configuration configuration) {
        logger.info("Configuring, id: " + configuration.getId() + "JMS endpoint: " + configuration.getJMSEndpoint() + ", parent PIS endpoint: " + configuration.getParentPISEndpoint() + ", thread pool size: " + configuration.getThreadPoolSize() + ", listeners: " + configuration.getListeners());
        id = configuration.getId();
        jmsEndpoint = configuration.getJMSEndpoint();
        ((Stub) parentPIS)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, configuration.getParentPISEndpoint().toString());
        pooledExecutor.setMaximumPoolSize(configuration.getThreadPoolSize());
        listeners = configuration.getListeners();
        configured = true;
    }

    // Lifecycle implemenation

    /**
     * Initializes the object. This should be called <b>before use
     * </b> in order for the object to setup or aquire any resources.
     * It is an error to use the object whilst uninitialized, aside
     * from configuring it. If the object has not been externally
     * configured then a call to this method will configure it
     * internally.
     * 
     * @see #configure(Configuration)
     * @see #isInit()
     * @see #destroy()
     */
    public synchronized void init() throws ConnectorException {
        if (!init) {
            try {
                logger.info("Initializing");
                if (!configured) {
                    configure(ConfigurationFactory.createConfiguration());
                }
                pooledExecutor.setKeepAliveTime(-1);
                pooledExecutor.createThreads(pooledExecutor.getMaximumPoolSize());
                soapFactory = SOAPFactory.newInstance();
                messageSerializer = new MessageSerializer(soapFactory);
                messagePeer.setJMSEndpoint(jmsEndpoint);
                messagePeer.init();
                for (int i = 0; i < listeners; i++) {
                    messagePeer.addJMSListener(new EndpointListener());
                }
                init = true;
                logger.info("Ready");
            } catch (ConfigurationException e) {
                throw new ConnectorException("Failed to initialize", e);
            } catch (MessagingException e) {
                throw new ConnectorException("Failed to initialize", e);
            } catch (SOAPException e) {
                throw new ConnectorException("Failed to initialize", e);
            }
        }
    }

    /**
     * Returns whether the object is initialized.
     * 
     * @return <code>true</code> if the object is initialized
     * @see #init()
     * @see #destroy()
     */
    public synchronized boolean isInit() {
        return init;
    }

    /**
     * Destroys the object. This should be called <b>after use </b> in
     * order for the object to correctly dispose of resources. It is
     * an error to use the object whilst destroyed although it may be
     * safely used after re-initialization.
     * 
     * @see #init()
     * @see #isInit()
     */
    public synchronized void destroy() {
        init = false;
        messagePeer.destroy();
        pooledExecutor.shutdownAfterProcessingCurrentlyQueuedTasks();
        pooledExecutor.setKeepAliveTime(0);
        logger.info("Destroyed");
    }


    // Public interface

    /**
     * Creates a <code>Message</code> for the specified recipient.
     */
    public Message createMessage(String recipientId) throws ConnectorException {
        try {
            Header header = new Header(DELIVERY_ID_PREFIX + uuidGenerator.nextIdentifier().toString(), new String[] {}, MessageStatus.UNDEFINED, id, recipientId);
            SOAPElement bodyElement = soapFactory.createElement(soapFactory.createName(MessageSerializer.BODY_LOCALNAME));
            return new Message(header, bodyElement);
        } catch (SOAPException e) {
            throw new ConnectorException("Failed to create message", e);
        }
    }

    /**
     * Delivers the specified <code>Message</code> without blocking.
     */
    public void deliverMessage(Message message) throws ConnectorException {
        try {
            logger.info("Delivering message, message delivery id: " + message.getHeader().getDeliveryId());
            parentPIS.deliverMessage(message);
        } catch (RemoteException e) {
            throw new ConnectorException("Failed to deliver message, message delivery id: " + message.getHeader().getDeliveryId(), e);
        }
    }

    /**
     * Returns the results of a <code>SELECT</code> query against
     * <code>Application.type</code>.
     * 
     * @param type the parameter used in the <code>WHERE</code>
     * clause
     * @return the query results
     * @throws AdapterException
     * @see org.egso.comms.nds.types.Application
     */
    public ApplicationList selectApplicationByType(URI type) throws ConnectorException {
        try {
            return parentPIS.selectApplicationsByType(type);
        } catch (RemoteException e) {
            throw new ConnectorException("Failed to select applications by type, type: " + type, e);
        }
    }

    /**
     * Sets the <code>Application_PortType</code> object
     * implementing this application.
     */
    public void setApplication(Application_PortType application) {
        this.application = application;
    }

    // Private interface

    private void discardMessage(Message message) {
        if (message != null) {
            logger.error("Discarded message, message delivery id: " + message.getHeader().getDeliveryId() + ", message status code: " + MessageStatus.getString(message.getHeader().getStatusCode()));
        }
    }

    // Member classes

    protected class EndpointListener implements MessageListener {

        public void onMessage(javax.jms.Message jmsMessage) {
            String jmsMessageId = null;
            Message message = null;
            try {
                jmsMessageId = jmsMessage.getJMSMessageID();
                logger.info("Processing JMS message, JMS message id: " + jmsMessageId);
                message = messageSerializer.deserializeMessage(messagePeer.recreateSOAPMessage(jmsMessage));
                pooledExecutor.execute(new MessageRunnable(message));
            } catch (JMSException e) {
                logger.error("Failed to process JMS message, JMS message id: " + jmsMessageId, e);
                discardMessage(message);
            } catch (InterruptedException e) {
                logger.error("Failed to handoff runnable to executor", e);
                discardMessage(message);
            } catch (MessagingException e) {
                logger.error("Failed to process JMS message, JMS message id: " + jmsMessageId, e);
                discardMessage(message);
            } catch (SerializationException e) {
                logger.error("Failed to process JMS message, JMS message id: " + jmsMessageId, e);
                discardMessage(message);
            } catch (RuntimeException e) {
                logger.error("Failed to process JMS message, JMS message id: " + jmsMessageId, e);
                discardMessage(message);
            }
        }

    }

    protected class MessageRunnable implements Runnable {

        private Message message = null;

        public MessageRunnable(Message message) {
            this.message = message;
        }

        public void run() {
            try {
                logger.info("Running");
                if (application == null) {
                    logger.warn("Failed to delegate received message to application, no application registered, message delivery id: " + message.getHeader().getDeliveryId());
                    discardMessage(message);
                } else if (message instanceof ReceiveMessage) {
                    application.receiveMessage(message);
                } else if (message instanceof ReturnMessage) {
                    application.returnMessage(message);
                } else {
                    logger.warn(message.getClass());
                    logger.warn("Failed to delegate received message to application, operation unknown, message delivery id: " + message.getHeader().getDeliveryId());
                    discardMessage(message);
                }
            } catch (RemoteException e) {
                logger.error("Failed to delegate received message to application, message delivery id: " + message.getHeader().getDeliveryId(), e);
                discardMessage(message);
            }
        }
    }

}