/*
 * Created on Oct 23, 2003
 */
package org.egso.comms.eis.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import org.egso.comms.eis.conf.Configuration;
import org.egso.comms.eis.conf.ConfigurationException;
import org.egso.comms.eis.conf.ConfigurationFactory;
import org.egso.comms.eis.wsdl.Application_PortType;
import org.egso.comms.eis.wsdl.Application_Service_Impl;
import org.egso.comms.pis.types.Message;

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
public class Application_PortType_Impl implements Application_PortType, ServiceLifecycle {

    // Logging

    private static Logger logger = LogManager.getLogger(Application_PortType_Impl.class);

    private static Logger soapLogger = LogManager.getLogger("org.egso.comms.soap");

    // Instance variables

    private Application_PortType application = new Application_Service_Impl().getApplicationPort();

    private MessageTransport messageTransport = null;

    private ServletEndpointContext servletEndpointContext = null;

    private boolean configured = false;

    private boolean init = false;

    // Configurable implementation

    public synchronized void configure(Configuration configuration) {
        logger.info("Configuring, JMS endpoint: " + configuration.getJMSEndpoint());
        ((Stub) application)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, configuration.getJMSEndpoint().toString());
        ((StubBase) application)._setTransportFactory(new MessageTransportFactory());
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
                if(context instanceof ServletEndpointContext) {
                    servletEndpointContext = (ServletEndpointContext) context;
                }
                messageTransport.init();
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
        messageTransport.destroy();
        logger.info("Destroyed");
    }

    public synchronized boolean isInit() {
        return init;
    }

    // Application_PortType implemenation

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.eis.wsdl.Application_PortType#receiveMessage(org.egso.comms.pis.types.Message)
     */
    public void receiveMessage(Message message) throws RemoteException {
        logger.info("Service operation called, operation: receiveMessage, message delivery id: " + message.getHeader().getDeliveryId());
        logSoapMessage();
        application.receiveMessage(message);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.eis.wsdl.Application_PortType#returnMessage(org.egso.comms.pis.types.Message)
     */
    public void returnMessage(Message message) throws RemoteException {
        logger.info("Service operation called, operation: returnMessage, message delivery id: " + message.getHeader().getDeliveryId());
        logSoapMessage();
        application.returnMessage(message);
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

    // Member classes

    protected class MessageTransportFactory implements ClientTransportFactory {

        public ClientTransport create() {
            return messageTransport;
        }

    }

}