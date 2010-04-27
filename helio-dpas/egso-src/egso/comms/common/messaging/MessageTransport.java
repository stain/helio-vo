package org.egso.comms.common.messaging;

import java.net.URI;

import javax.jms.Message;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sun.xml.rpc.client.ClientTransport;
import com.sun.xml.rpc.soap.message.SOAPMessageContext;

/**
 * Class for handling service invocation over JMS.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 * @see com.sun.xml.rpc.client.StubBase#_setTransportFactory(com.sun.xml.rpc.client.ClientTransportFactory)
 */
public class MessageTransport implements ClientTransport {

    // Logging

    private static Logger logger = LogManager.getLogger(MessageTransport.class);

    // Instance variables

    private MessageFactory messageFactory = null;

    private boolean init = false;

    //  Lifecycle implemenation

    /**
     * Initializes the object. This should be called <b>before use
     * </b> in order for the object to setup or aquire any resources.
     * It is an error to use the object whilst uninitialized.
     * 
     * @see #isInit()
     * @see #destroy()
     */
    public synchronized void init() throws MessagingException {
        if (!init) {
            try {
                logger.info("Initializing");
                messageFactory = MessageFactory.newInstance();
                init = true;
                logger.info("Ready");
            } catch (SOAPException e) {
                throw new MessagingException("Failed to initialize", e);
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
        logger.info("Destroyed");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sun.xml.rpc.client.ClientTransport#invoke(java.lang.String,
     * com.sun.xml.rpc.soap.message.SOAPMessageContext)
     */
    public void invoke(String endpoint, SOAPMessageContext context) {
        try {
            logger.info("Invoking, endpoint: " + endpoint);
            MessagePeer messagePeer = new MessagePeer();
            messagePeer.setJMSEndpoint(URI.create(endpoint));
            messagePeer.init();
            Message jmsMessage = messagePeer.createJMSMessage(context.getMessage());
            messagePeer.sendJMSMessage(jmsMessage);
            messagePeer.destroy();
            context.setFailure(false);
            context.setMessage(messageFactory.createMessage());
        } catch (MessagingException e) {
            logger.info("Failed to invoke", e);
            context.setFailure(true);
        } catch (SOAPException e) {
            logger.info("Failed to invoke", e);
            context.setFailure(true);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sun.xml.rpc.client.ClientTransport#invokeOneWay(java.lang.String,
     * com.sun.xml.rpc.soap.message.SOAPMessageContext)
     */
    public void invokeOneWay(String endpoint, SOAPMessageContext context) {
        // TODO Auto-generated method stub
    }

}