/*
 * Created on Jan 26, 2004
 */
package org.egso.comms.eis.adapter;

import java.net.URI;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

import org.apache.commons.id.uuid.VersionFourGenerator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.egso.comms.eis.conf.Configuration;
import org.egso.comms.eis.conf.ConfigurationException;
import org.egso.comms.eis.conf.ConfigurationFactory;
import org.egso.comms.eis.connector.ConnectorException;
import org.egso.comms.eis.connector.InteractionBase;
import org.egso.comms.eis.types.FormalMessage;
import org.egso.comms.eis.types.FormalMessageSerializer;
import org.egso.comms.eis.types.Request;
import org.egso.comms.eis.types.RequestSerializer;
import org.egso.comms.eis.types.Response;
import org.egso.comms.eis.types.ResponseSerializer;
import org.egso.comms.eis.types.SerializationException;
import org.egso.comms.eis.wsdl.Application_PortType;
import org.egso.comms.nds.types.ApplicationList;
import org.egso.comms.pis.types.Message;

import EDU.oswego.cs.dl.util.concurrent.Slot;
import EDU.oswego.cs.dl.util.concurrent.SyncMap;
import EDU.oswego.cs.dl.util.concurrent.WriterPreferenceReadWriteLock;

/**
 * Principle class for synchronous request-response messaging.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
@SuppressWarnings("unchecked")
public class RequestManager {

    // Logging

    private static Logger logger = LogManager.getLogger(RequestManager.class);

    // Constants

    private static final String MESSAGE_ID_PREFIX = "Request-";

    // Instance variables

    private InteractionBase interactionBase = new InteractionBase();

    private Map<String,RequestHandler> requestHandlers = new SyncMap(new HashMap<String,RequestHandler>(), new WriterPreferenceReadWriteLock());

    private Map<String,Slot> responses = new SyncMap(new HashMap<String,Slot>(), new WriterPreferenceReadWriteLock());

    private FormalMessageSerializer formalMessageSerializer = null;

    private VersionFourGenerator uuidGenerator = new VersionFourGenerator();
    
    private SOAPFactory soapFactory = null;
    
    private String id = null;

    private long timeoutPeriod = 0l;

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
        logger.info("Configuring, id: " + configuration.getId() + ", timeout period: " + configuration.getTimeoutPeriod() + " ms");
        interactionBase.configure(configuration);
        id = configuration.getId();
        timeoutPeriod = configuration.getTimeoutPeriod();
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
    public synchronized void init() throws AdapterException {
        if (!init) {
            try {
                logger.info("Initializing");
                if (!configured) {
                    configure(ConfigurationFactory.createConfiguration());
                }
                requestHandlers.clear();
                responses.clear();
                soapFactory = SOAPFactory.newInstance();
                formalMessageSerializer = new FormalMessageSerializer(soapFactory);
                interactionBase.setApplication(new ApplicationImpl());
                interactionBase.init();
                init = true;
                logger.info("Ready");
            } catch (ConfigurationException e) {
                throw new AdapterException("Failed to initialize", e);
            } catch (ConnectorException e) {
                throw new AdapterException("Failed to initialize", e);
            } catch (SOAPException e) {
                throw new AdapterException("Failed to initialize", e);
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
        interactionBase.destroy();
        logger.info("Destroyed");
    }

    // Public interface

    /**
     * Adds a <code>RequestHandler</code> object with the specified
     * id.
     */
    public void addHandler(String handlerId, RequestHandler requestHandler) {
        logger.info("Registering handler, handler id: " + handlerId);
        requestHandlers.put(handlerId, requestHandler);
    }

    /**
     * Removes a <code>RequestHandler</code> object.
     * 
     * @param handlerId the handler id of the
     * <code>RequestHadler</code>
     * @return the <code>RequestHandler</code> object
     */
    public RequestHandler removeHandler(String handlerId) {
        logger.info("Deregistering handler, handler id: " + handlerId);
        return (RequestHandler) requestHandlers.remove(handlerId);
    }

    /**
     * Creates a <code>Request</code> for the specified server and
     * handler.
     * 
     * @param serverId the server id
     * @param handlerId the server side handler id
     * @return the <code>Response</code>
     * @throws AdapterException
     */
    public Request createRequest(String serverId, String handlerId) throws AdapterException {
        try {
            SOAPElement subtypeElement = soapFactory.createElement(soapFactory.createName(RequestSerializer.SUBTYPE_LOCALNAME));
            return new Request(MESSAGE_ID_PREFIX + uuidGenerator.nextIdentifier().toString(), timeoutPeriod, id, serverId, handlerId, false, subtypeElement);
        } catch (SOAPException e) {
            throw new AdapterException("Failed to create request", e);
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
    public ApplicationList selectApplicationByType(URI type) throws AdapterException {
        try {
            return interactionBase.selectApplicationByType(type);
        } catch (ConnectorException e) {
            throw new AdapterException("Failed to select applications by type, type: " + type, e);
        }
    }

    /**
     * Dispatches a <code>Request</code> and optionally blocks
     * pending a <code>Response</code>.
     * 
     * @param request the <code>Request</code>
     * @return the <code>Response</code> or <code>null</code> when
     * <code>Request.oneWay</code>
     * @throws AdapterException
     */
    public Response dispatchRequest(Request request) throws AdapterException {
        try {
            Response response = null;
            Message message = interactionBase.createMessage(null);
            formalMessageSerializer.serializeFormalMessage(request, message);
            if (!request.isOneWay()) {
                Slot responseSlot = new Slot();
                responses.put(request.getId(), responseSlot);
            }
            interactionBase.deliverMessage(message);
            if (!request.isOneWay()) {
                logger.info("Dispatched request, request id: " + request.getId() + ", server id: " + request.getServerId() + ", handler id: " + request.getHandlerId());
                response = waitforResponse(request);
                Exception exception = response.getException();
                if (exception != null) {
                    if (exception instanceof MissingHandlerException) {
                        throw (MissingHandlerException) exception;
                    } else if (exception instanceof RequestHandlerException) {
                        throw (RequestHandlerException) exception;
                    } else if (exception instanceof UndeliverableException) {
                        throw (UndeliverableException) exception;
                    }
                }
            } else {
                logger.info("Dispatched one-way request, request id: " + request.getId() + ", server id: " + request.getServerId() + ", handler id: " + request.getHandlerId());
            }
            return response;
        } catch (ConnectorException e) {
            throw new AdapterException("Failed to dispatch request, request id: " + request.getId() + ", server id: " + request.getServerId() + ", handler id: " + request.getHandlerId(), e);
        } catch (SerializationException e) {
            throw new AdapterException("Failed to dispatch request, request id: " + request.getId() + ", server id: " + request.getServerId() + ", handler id: " + request.getHandlerId(), e);
        }
    }

    // Private interface

    private Response createResponse(Request request) throws AdapterException {
        try {
            SOAPElement subtypeElement = soapFactory.createElement(soapFactory.createName(ResponseSerializer.SUBTYPE_LOCALNAME));
            return new Response(request.getId(), request.getClientId(), request.getServerId(), null, subtypeElement);
        } catch (SOAPException e) {
            throw new AdapterException("Failed to create response", e);
        }
    }

    private Response waitforResponse(Request request) throws AdapterException {
        try {
            logger.info("Waiting for response from server, request id: " + request.getId() + ", server id: " + request.getServerId() + ", handler id: " + request.getHandlerId());
            Slot responseSlot = (Slot) responses.get(request.getId());
            Response response = (Response) responseSlot.poll(request.getTimeout());
            responses.remove(request.getId());
            if (response == null) {
                throw new TimeoutException("Failed to get response from server, request id: " + request.getId() + ", server id: " + request.getServerId() + ", handler id: " + request.getHandlerId() + ", timeout period: " + request.getTimeout() + " ms");
            } else {
                return response;
            }
        } catch (InterruptedException e) {
            throw new AdapterException("Failed to get response from server, request id: " + request.getId() + ", server id: " + request.getServerId() + ", handler id: " + request.getHandlerId(), e);
        }
    }

    private void handoffResponse(Response response) {
        try {
            Slot responseSlot = (Slot) responses.get(response.getRequestId());
            if (responseSlot == null) {
                logger.error("Failed to handoff response to channel, no channel, request id: " + response.getRequestId() + ", server id: " + response.getServerId());
                discardResponse(response);
            } else {
                responseSlot.put(response);
            }
        } catch (InterruptedException e) {
            logger.error("Failed to handoff response to channel, request id: " + response.getRequestId() + ", server id: " + response.getServerId(), e);
            discardResponse(response);
        }
    }

    private void discardResponse(Response response) {
        logger.error("Discarded response, request id: " + response.getRequestId() + ", server id: " + response.getServerId());
    }

    private void delegateOneWayRequest(Request request) {
        RequestHandler handler = (RequestHandler) requestHandlers.get(request.getHandlerId());
        if (handler == null) {
            logger.warn("Failed to delegate one-way request, missing handler, request id: " + request.getId() + ", handler id: " + request.getHandlerId());
        } else {
            handler.handleOneWayRequest(request);
        }
    }

    private void delegateRequest(Request request, Response response) {
        RequestHandler handler = (RequestHandler) requestHandlers.get(request.getHandlerId());
        try {
            if (handler == null) {
                logger.warn("Failed to delegate request, missing handler, request id: " + request.getId() + ", handler id: " + request.getHandlerId() + ", one way: " + request.isOneWay());
                response.setException(new MissingHandlerException());
            } else {
                handler.handleRequest(request, response);
            }
        } catch (RequestHandlerException e) {
            response.setException(e);
        }
    }

    // Member classes

    protected class ApplicationImpl implements Application_PortType {

        // Application_PortType implementation

        public void returnMessage(Message message) throws RemoteException {
            try {
                logger.warn("Service operation called, operation: returnMessage, message delivery id: " + message.getHeader().getDeliveryId());
                FormalMessage formalMessage = formalMessageSerializer.deserializeFormalMessage(message);
                if (formalMessage instanceof Request) {
                    Request request = (Request) formalMessage;
                    Response response = createResponse(request);
                    response.setRequestId(message.getHeader().getDeliveryId());
                    response.setException(new UndeliverableException(message.getHeader()));
                    handoffResponse(response);
                } else if (formalMessage instanceof Response) {
                    Response response = (Response) formalMessage;
                    logger.error("Failed to process returned message, failed response");
                    discardResponse(response);
                }
            } catch (SerializationException e) {
                logger.error("Failed to process returned message", e);
            } catch (AdapterException e) {
                logger.error("Failed to process returned message", e);
            }
        }

        public void receiveMessage(Message message) throws RemoteException {
            try {
                logger.info("Service operation called, operation: receiveMessage, message delivery id: " + message.getHeader().getDeliveryId());
                FormalMessage formalMessage = formalMessageSerializer.deserializeFormalMessage(message);
                if (formalMessage instanceof Request) {
                    Message responseMessage = interactionBase.createMessage(null);
                    Request request = (Request) formalMessage;
                    if (request.isOneWay()) {
                        delegateOneWayRequest(request);
                    } else {
                        Response response = createResponse(request);
                        delegateRequest(request, response);
                        formalMessageSerializer.serializeFormalMessage(response, responseMessage);
                        interactionBase.deliverMessage(responseMessage);
                    }
                } else if (formalMessage instanceof Response) {
                    Response response = (Response) formalMessage;
                    handoffResponse(response);
                }
            } catch (SerializationException e) {
                logger.error("Failed to process received message", e);
            } catch (AdapterException e) {
                logger.error("Failed to process received message", e);
            } catch (ConnectorException e) {
                logger.error("Failed to process received message", e);
            }
        }

    }

}