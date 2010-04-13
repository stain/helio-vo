/*
 * Created on Oct 21, 2003
 */
package org.egso.comms.common.messaging;

import java.net.URI;
import java.util.Hashtable;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.messaging.JAXMException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sun.messaging.xml.MessageTransformer;

/**
 * Class for asynchronous messaging over JMS.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public class MessagePeer {

    // Logging

    private static Logger logger = LogManager.getLogger(MessagePeer.class);

    // Constants

    private static final String JMS_QUEUE_PATH = "/queue";

    private static final String JMS_TOPIC_PATH = "/topic";

    private static final String JMS_DESTINATION_CLAUSE = "destination";

    private static final String JMS_CONNECTION_FACTORY_CLAUSE = "connectionFactory";

    private static final String JMS_CONNECTION_FACTORY_IMPL_CLAUSE = "connectionFactoryImpl";

    private static final String TARGET_SERVICE_CLAUSE = "targetService";

    private static final String JNDI_INITIAL_CONTEXT_FACTORY_CLAUSE = "initialContextFactory";

    private static final String JNDI_PROVIDER_URL_CLAUSE = "jndiProviderURL";

    private static final String JMS_DELIVERY_MODE_CLAUSE = "deliveryMode";

    private static final String JMS_PRIORITY_CLAUSE = "priority";

    private static final String JMS_TIME_TO_LIVE_CLAUSE = "timeToLive";

    // Instance variables

    private String jmsEndpointPath = null;

    private String jmsDestination = null;

    private String jmsConnectionFactory = null;

    private String jmsConnectionFactoryImpl = null;

    private String jndiInitialContextFactory = null;

    private String jndiProviderURL = null;

    private int jmsSessionAcknowledgeMode = Session.AUTO_ACKNOWLEDGE;

    private int jmsDeliveryMode = DeliveryMode.NON_PERSISTENT;

    private long jmsTimeToLive = Message.DEFAULT_TIME_TO_LIVE;

    private int jmsPriority = Message.DEFAULT_PRIORITY;

    private Context context = null;

    private Connection connection = null;

    private ConnectionFactory connectionFactory = null;

    private Destination destination = null;

    private MessageFactory messageFactory = null;

    private boolean init = false;

    // Lifecycle implemenation

    /**
     * Initializes the object. This should be called <b>before use
     * </b> in order for the object to setup or aquire any resources.
     * It is an error to use the object whilst uninitialized.
     * 
     * @see #isInit()
     * @see #destroy()
     */
    public synchronized void init() throws MessagingException {
        try {
            if (!init) {
                logger.info("Initializing");
                messageFactory = MessageFactory.newInstance();
                if (jndiInitialContextFactory != null) {
                    setupContext();
                }
                setupConnectionFactory();
                setupConnection();
                setupDestination();
                init = true;
                logger.info("Ready");
            }
        } catch (SOAPException e) {
            throw new MessagingException("Failed to initialize", e);
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
        teardownConnection();
        teardownContext();
        destination = null;
        logger.info("Destroyed");
    }

    // Public interface

    /**
     * Returns the JNDI name of the JMS <code>Destination</code>.
     * 
     * @return the JMS <code>Destination</code> name
     */
    public String getJMSDestination() {
        return jmsDestination;
    }

    /**
     * Returns the JNDI name of the JMS <code>ConnectionFactory</code>
     * 
     * @return the JMS <code>ConnectionFactory</code> name
     */
    public String getJMSConnectionFactory() {
        return jmsConnectionFactory;
    }

    /**
     * Returns the name of the <code>Class</code> implementing the
     * JMS <code>ConnectionFactory</code>.
     * 
     * @return the JMS <code>ConnectionFactory</code> implementation
     */
    public String getJMSConnectionFactoryImpl() {
        return jmsConnectionFactoryImpl;
    }

    /**
     * Retuns the name of the <code>Class</code> implementing the
     * JNDI <code>InitialContextFactory</code>.
     * 
     * @return the JNDI <code>InitialContextFactory</code>
     * implementation
     */
    public String getJNDIInitialContextFactory() {
        return jndiInitialContextFactory;
    }

    /**
     * Returns the URL of the JNDI provider.
     * 
     * @return the URL of the provider
     */
    public String getJNDIProviderURL() {
        return jndiProviderURL;
    }

    /**
     * Returns the JMS <code>DeliveyMode</code> for
     * <code>Message</code> delivery.
     * 
     * @return the JMS <code>DeliveryMode</code>
     * @see DeliveryMode
     */
    public int getJMSDeliveryMode() {
        return jmsDeliveryMode;
    }

    /**
     * Returns the JMS time-to-live for <code>Message</code>
     * delivery.
     * 
     * @return the JMS time-to-live
     */
    public long getJMSTimeToLive() {
        return jmsTimeToLive;
    }

    /**
     * Returns the JMS priority for <code>Message</code> delivery.
     * 
     * @return the JMS priority
     */
    public int getJMSPriority() {
        return jmsPriority;
    }

    /**
     * Sets the JMS endpoint. The endpoint is a specific URI type
     * where the protocol is <code>jms</code> and path one of
     * <code>/queue</code> or <code>/topic</code> according to the
     * JMS destination type. Assignment of the bean properties of the
     * object may be specified in the query.
     * 
     * @param jmsEndpoint the JMS endpoint
     */
    public void setJMSEndpoint(URI jmsEndpoint) throws MessagingException {
        logger.debug("Parsing JMS endpoint: " + jmsEndpoint);
        jmsEndpointPath = jmsEndpoint.getPath();
        logger.debug("JMS endpoint path: " + jmsEndpointPath);
        String[] clauses = jmsEndpoint.getQuery().split("&");
        String targetService = null;
        for (int i = 0; i < clauses.length; i++) {
            String[] equality = clauses[i].split("=");
            if (equality[0].equals(JMS_DESTINATION_CLAUSE)) {
                jmsDestination = equality[1];
                logger.debug("Parsed JMS destination: " + jmsDestination);
            } else if (equality[0].equals(JMS_CONNECTION_FACTORY_CLAUSE)) {
                jmsConnectionFactory = equality[1];
                logger.debug("Parsed JMS connection factory: " + jmsDestination);
            } else if (equality[0].equals(JMS_CONNECTION_FACTORY_IMPL_CLAUSE)) {
                jmsConnectionFactoryImpl = equality[1];
                logger.debug("Parsed JMS connection factory implementation: " + jmsConnectionFactoryImpl);
            } else if (equality[0].equals(TARGET_SERVICE_CLAUSE)) {
                targetService = equality[1];
                logger.debug("Parsed target service: " + targetService);
            } else if (equality[0].equals(JNDI_INITIAL_CONTEXT_FACTORY_CLAUSE)) {
                jndiInitialContextFactory = equality[1];
                logger.debug("Parsed JNDI initial context factory: " + jndiInitialContextFactory);
            } else if (equality[0].equals(JNDI_PROVIDER_URL_CLAUSE)) {
                jndiProviderURL = equality[1];
                logger.debug("Parsed JNDI provider URL: " + jndiProviderURL);
            } else if (equality[0].equals(JMS_DELIVERY_MODE_CLAUSE)) {
                jmsDeliveryMode = Integer.parseInt(equality[1]);
                logger.debug("Parsed JMS delivery mode: " + jmsDeliveryMode);
            } else if (equality[0].equals(JMS_TIME_TO_LIVE_CLAUSE)) {
                jmsTimeToLive = Long.parseLong(equality[1]);
                logger.debug("Parsed JMS time to live: " + jmsTimeToLive);
            } else if (equality[0].equals(JMS_PRIORITY_CLAUSE)) {
                jmsPriority = Integer.parseInt(equality[1]);
                logger.debug("Parsed JMS priority: " + jmsPriority);
            } else {
                logger.debug("Unparsed clause: " + equality[0] + "=" + equality[1]);
            }
        }
        jmsDestination += "/" + targetService;
    }

    /**
     * Adds a JMS listener to the encapsulated JMS
     * <code>Destination</code>.
     * 
     * @param listener the listener
     * @throws MessagingException
     */
    public void addJMSListener(MessageListener listener) throws MessagingException {
        this.init();
        try {
            logger.info("Adding JMS listener to destination");
            Session session = createSession();
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(listener);
        } catch (JMSException e) {
            throw new MessagingException("Failed to add JMS listener to destination", e);
        }
    }

    /**
     * Creates a JMS <code>Message</code> of the specified type.
     * 
     * @param jmsMessageType the type of <code>Message</code> to
     * create
     * @return the created JMS <code>Message</code>
     * @throws MessagingException
     */
    public Message createJMSMessage(Class<?> jmsMessageType) throws MessagingException { try {
            Message jmsMessage = null;
            Session session = createSession();
            if (jmsMessageType == BytesMessage.class) {
                logger.info("Creating JMS bytes message");
                jmsMessage = session.createBytesMessage();
            } else if (jmsMessageType == MapMessage.class) {
                logger.info("Creating JMS map message");
                jmsMessage = session.createMapMessage();
            } else if (jmsMessageType == ObjectMessage.class) {
                logger.info("Creating JMS object message");
                jmsMessage = session.createObjectMessage();
            } else if (jmsMessageType == StreamMessage.class) {
                logger.info("Creating JMS stream message");
                jmsMessage = session.createStreamMessage();
            } else if (jmsMessageType == TextMessage.class) {
                logger.info("Creating JMS text message");
                jmsMessage = session.createTextMessage();
            } else if (jmsMessageType == Message.class) {
                logger.info("Creating JMS message");
                jmsMessage = session.createMessage();
            } else {
                throw new JMSException("Failed to create JMS message, unknown type: " + jmsMessageType.getName());
            }
            return jmsMessage;
        } catch (JMSException e) {
            throw new MessagingException("Failed to create JMS message, message type: " + jmsMessageType.getName(), e);
        }
    }

    /**
     * Creates a JMS <code>Message</code> which can be used to
     * transport the specified <code>SOAPMessage</code>.
     * 
     * @param soapMessage the <code>SOAPMessage</code>
     * @return the JMS <code>Message</code>
     * @throws MessagingException
     */
    public Message createJMSMessage(SOAPMessage soapMessage) throws MessagingException {
        try {
            Session session = createSession();
            Message jmsMessage = MessageTransformer.SOAPMessageIntoJMSMessage(soapMessage, session);
            return jmsMessage;
        } catch (JAXMException e) {
            throw new MessagingException("Failed to create JMS message from SOAP message", e);
        }
    }

    /**
     * Re-creates the <code>SOAPMessage</code> which was previouly
     * used to create the specified JMS <code>Message</code>.
     * 
     * @param jmsMessage the JMS <code>Message</code>
     * @return the <code>SOAPMessage</code>
     * @throws MessagingException
     */
    public SOAPMessage recreateSOAPMessage(Message jmsMessage) throws MessagingException {
        try {
            return MessageTransformer.SOAPMessageFromJMSMessage(jmsMessage, messageFactory);
        } catch (JAXMException e) {
            throw new MessagingException("Failed to create SOAP message", e);
        }
    }

    /**
     * Sends the specified JMS <code>Message</code> to the
     * encapsulated JMS <code>Destination</code>.
     * 
     * @param jmsMessage the JMS <code>Message</code>
     * @throws MessagingException
     */
    public void sendJMSMessage(Message jmsMessage) throws MessagingException {
        try {
            Session session = createSession();
            MessageProducer producer = session.createProducer(destination);
            producer.send(jmsMessage, jmsDeliveryMode, jmsPriority, jmsTimeToLive);
            producer.close();
            logger.info("Sent JMS message to destination, message id: " + jmsMessage.getJMSMessageID());
        } catch (JMSException e) {
            throw new MessagingException("Failed to send JMS message", e);
        }
    }

    // Private interface

    private void setupContext() throws MessagingException {
        try {
            if (jndiInitialContextFactory == null) {
                throw new MessagingException("Failed to setup JNDI context, no initial context factory");
            } else if (jndiProviderURL == null) {
                throw new MessagingException("Failed to setup JNDI context, no provider URL");
            } else {
                logger.info("Setting up JNDI context, initial context factory: " + jndiInitialContextFactory + ", provider URL: " + jndiProviderURL);
                Hashtable<String,String> environment = new Hashtable<String,String>();
                environment.put(Context.INITIAL_CONTEXT_FACTORY, jndiInitialContextFactory);
                environment.put(Context.PROVIDER_URL, jndiProviderURL);
                context = new InitialContext(environment);
            }
        } catch (NamingException e) {
            throw new MessagingException("Failed to setup JNDI context, initial context factory: " + jndiInitialContextFactory + ", provider URL: " + jndiProviderURL, e);
        }
    }

    private void teardownContext() {
        if (context != null) {
            logger.info("Tearing down JNDI context");
            try {
                context.close();
                context = null;
            } catch (NamingException e) {
                logger.warn("Failed to teardown JNDI context", e);
            }
        }
    }

    private void setupConnectionFactory() throws MessagingException {
        try {
            if (context != null && jmsConnectionFactory != null) {
                logger.info("Looking up JMS connection factory, factory JNDI name: " + jmsConnectionFactory);
                connectionFactory = (ConnectionFactory) context.lookup(jmsConnectionFactory);
            } else if (jmsConnectionFactoryImpl != null) {
                logger.info("Instantiating JMS connection factory, factory implementation: " + jmsConnectionFactoryImpl);
                connectionFactory = (ConnectionFactory) Class.forName(jmsConnectionFactoryImpl).newInstance();
            } else {
                throw new MessagingException("Failed to setup connection factory, no JNDI context or factory implementation");
            }
        } catch (ClassNotFoundException e) {
            throw new MessagingException("Failed to setup connection factory, factory implementation: " + jmsConnectionFactoryImpl, e);
        } catch (IllegalAccessException e) {
            throw new MessagingException("Failed to setup connection factory, factory implementation: " + jmsConnectionFactoryImpl, e);
        } catch (InstantiationException e) {
            throw new MessagingException("Failed to setup connection factory, factory implementation: " + jmsConnectionFactoryImpl, e);
        } catch (NamingException e) {
            throw new MessagingException("Failed to setup connection factory, JNDI name: " + jmsConnectionFactory, e);
        }
    }

    private void setupConnection() throws MessagingException {
        try {
            logger.info("Setting up JMS connection");
            connection = connectionFactory.createConnection();
            connection.setExceptionListener(new ExceptionListener());
            connection.start();
        } catch (JMSException e) {
            throw new MessagingException("Failed to setup JMS connection", e);
        }
    }

    private void setupDestination() throws MessagingException {
        try {
            if (context != null && jmsDestination != null) {
                logger.info("Looking up JMS destination, destination JNDI name: " + jmsDestination);
                destination = (Destination) context.lookup(jmsDestination);
            } else if (jmsDestination != null) {
                logger.info("Instantiating JMS destination, destination name: " + jmsDestination);
                Session session = createSession();
                if (jmsEndpointPath.equals(JMS_QUEUE_PATH)) {
                    destination = ((QueueSession) session).createQueue(jmsDestination);
                } else if (jmsEndpointPath.equals(JMS_TOPIC_PATH)) {
                    destination = ((TopicSession) session).createTopic(jmsDestination);
                } else {
                    throw new JMSException("Failed to setup JMS destination, unknown endpoint path: " + jmsEndpointPath);
                }
            } else {
                throw new JMSException("Failed to setup JMS destination, no JNDI context or factory implementation");
            }
        } catch (JMSException e) {
            throw new MessagingException("Failed to setup JMS destination, destination name: " + jmsDestination, e);
        } catch (NamingException e) {
            throw new MessagingException("Failed to setup JMS destination,  destination name: " + jmsDestination, e);
        }
    }

    private void teardownConnection() {
        if (connection != null) {
            logger.info("Tearing down JMS connection");
            try {
                connection.close();
                connection = null;
            } catch (JMSException e) {
                logger.warn("Failed to teardown JMS connection", e);
            }
        }
    }

    private Session createSession() throws MessagingException {
        try {
            return connection.createSession(false, jmsSessionAcknowledgeMode);
        } catch (JMSException e) {
            throw new MessagingException("Failed to create JMS session, JMS destination: " + jmsDestination, e);
        }
    }

    // Member classes

    protected class ExceptionListener implements javax.jms.ExceptionListener {

        public void onException(JMSException e) {
            logger.error("Caught exception thrown by JMS connection", e);
            destroy();
        }

    }

}