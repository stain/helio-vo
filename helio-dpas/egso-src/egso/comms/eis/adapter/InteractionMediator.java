/*
 * Created on May 7, 2004
 */
package org.egso.comms.eis.adapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.util.*;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

import org.apache.commons.id.uuid.VersionFourGenerator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.egso.comms.eis.conf.Configuration;
import org.egso.comms.eis.conf.ConfigurationException;
import org.egso.comms.eis.conf.ConfigurationFactory;
import org.egso.comms.eis.storage.FilespaceManager;
import org.egso.comms.eis.storage.StorageException;
import org.egso.comms.eis.types.AliveRequest;
import org.egso.comms.eis.types.AliveResponse;
import org.egso.comms.eis.types.CloseSessionRequest;
import org.egso.comms.eis.types.CreateSessionRequest;
import org.egso.comms.eis.types.CreateSessionResponse;
import org.egso.comms.eis.types.GetObjectRequest;
import org.egso.comms.eis.types.GetObjectResponse;
import org.egso.comms.eis.types.GetTypesRequest;
import org.egso.comms.eis.types.GetTypesResponse;
import org.egso.comms.eis.types.InvokeObjectRequest;
import org.egso.comms.eis.types.InvokeObjectResponse;
import org.egso.comms.eis.types.Parameter;
import org.egso.comms.eis.types.ReleaseObjectRequest;
import org.egso.comms.eis.types.Request;
import org.egso.comms.eis.types.RequestSubtype;
import org.egso.comms.eis.types.RequestSubtypeSerializer;
import org.egso.comms.eis.types.Response;
import org.egso.comms.eis.types.ResponseSubtypeSerializer;
import org.egso.comms.eis.types.SerializationException;
import org.egso.comms.eis.types.ValidateSessionRequest;
import org.egso.comms.eis.types.ValidateSessionResponse;
import org.egso.comms.nds.types.Application;
import org.egso.comms.nds.types.ApplicationList;

import EDU.oswego.cs.dl.util.concurrent.FutureResult;
import EDU.oswego.cs.dl.util.concurrent.SyncMap;
import EDU.oswego.cs.dl.util.concurrent.ThreadedExecutor;
import EDU.oswego.cs.dl.util.concurrent.WriterPreferenceReadWriteLock;

/**
 * Principle class for object-oriented interaction.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public class InteractionMediator {

    // Logging

    private static Logger logger = LogManager.getLogger(InteractionMediator.class);

    // Constants

    private static final String STATIC_HANDLER_ID = "InteractionMediator";

    private static final String CLIENT_SESSION_HANDLER_ID_PREFIX = "ClientSession-";

    private static final String SERVER_SESSION_HANDLER_ID_PREFIX = "ServerSession-";

    // Instance variables

    private RequestManager requestManager = new RequestManager();

    private FilespaceManager filespaceManager = new FilespaceManager();

    private ThreadedExecutor threadedExecutor = new ThreadedExecutor();

    private RequestSubtypeSerializer requestSubtypeSerializer = null;

    private ResponseSubtypeSerializer responseSubtypeSerializer = null;

    private VersionFourGenerator uuidGenerator = new VersionFourGenerator();

    private String nextRequestId = uuidGenerator.nextIdentifier().toString();

    private SessionFactory sessionFactory = null;

    private SOAPFactory soapFactory = null;

    private String id = null;

    private long timeoutPeriod = 0l;

    private boolean configured = false;

    private boolean init = false;

    // Configurable implementation

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
        logger.info("Configuring, id: " + configuration.getId() + ", timeout period: " + configuration.getTimeoutPeriod());
        requestManager.configure(configuration);
        filespaceManager.configure(configuration);
        id = configuration.getId();
        timeoutPeriod = configuration.getTimeoutPeriod();
        configured = true;
    }

    // Lifecycle implementation

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
                requestManager.init();
                requestManager.addHandler(STATIC_HANDLER_ID, new RequestHandler());
                filespaceManager.init();
                soapFactory = SOAPFactory.newInstance();
                requestSubtypeSerializer = new RequestSubtypeSerializer(soapFactory, filespaceManager);
                responseSubtypeSerializer = new ResponseSubtypeSerializer(soapFactory, filespaceManager);
                init = true;
                logger.info("Ready");
            } catch (ConfigurationException e) {
                throw new AdapterException("Failed to initialize", e);
            } catch (SOAPException e) {
                throw new AdapterException("Failed to initialize", e);
            } catch (StorageException e) {
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
        requestManager.removeHandler(STATIC_HANDLER_ID);
        requestManager.destroy();
        logger.info("Destroyed");
    }

    // Public interface

    /**
     * Returns the id of this application.
     * 
     * @return the id of this application
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the id of the next request to be despatched.
     * 
     * @return the id of the next request
     */
    public String getNextRequestId() {
        return nextRequestId;
    }

    /**
     * Sets the <code>SessionFactory</code> object used to provide
     * <code>Sessions</code> to clients.
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Returns the results of a <code>SELECT</code> query against
     * <code>Application.type</code>.
     * 
     * 
     * @param type the parameter used in the <code>WHERE</code> clause
     * @return the query results
     * @throws AdapterException
     * @see org.egso.comms.nds.types.Application
     */
    public ApplicationList selectApplicationByType(URI type) throws AdapterException {
        return requestManager.selectApplicationByType(type);
    }

    /**
     * Creates a <code>Session</code> with the first server to
     * respond from the specified list.
     * 
     * @param serverList the list of servers
     * @return the created <code>Session</code>
     * @throws AdapterException
     */
    public Session createSession(ApplicationList serverList) throws AdapterException {
        Application[] servers = serverList.getApplications();
        String[] serverIds = new String[servers.length];
        for (int i = 0; i < serverIds.length; i++) {
            serverIds[i] = servers[i].getId();
        }
        return createSession(serverIds);
    }
    
    /**
     * Creates a <code>Session</code> with the first server to
     * respond from the specified list.
     * 
     * @param serverIds the list of server ids
     * @return the created <code>Session</code>
     * @throws AdapterException
     */
    public Session createSession(String[] serverIds) throws AdapterException {
        FutureResult sessionHolder = new FutureResult();
        try {
            for (int i = 0; i < serverIds.length; i++) {
                threadedExecutor.execute(new CreateSessionRunnable(serverIds[i], sessionHolder));
            }
            return (Session) sessionHolder.timedGet(timeoutPeriod);
        } catch (EDU.oswego.cs.dl.util.concurrent.TimeoutException e) {
            throw new TimeoutException("Failed to create session, timeout period: " + timeoutPeriod + " ms", e);
        } catch (InterruptedException e) {
            throw new AdapterException("Failed to create session", e);
        } catch (InvocationTargetException e) {
            throw new AdapterException("Failed to create session", e);
        } finally {
            sessionHolder.set(new Object());
        }  
    }

    /**
     * Creates a <code>Session</code> with the specified server
     * 
     * @param serverId the id of the server
     * @return the created <code>Session</code>
     * @throws AdapterException
     */
    public Session createSession(String serverId) throws AdapterException {
        try {
            logger.info("Creating session, server id: " + serverId);
            CreateSessionRequest createSessionRequest = new CreateSessionRequest(id);
            Request request = requestManager.createRequest(serverId, STATIC_HANDLER_ID);
            requestSubtypeSerializer.serializeRequestSubtype(createSessionRequest, request);
            request.setId(getRequestId());
            request.setOneWay(false);
            Response response = requestManager.dispatchRequest(request);
            CreateSessionResponse createSessionResponse = (CreateSessionResponse) responseSubtypeSerializer.deserializeResponseSubtype(response);
            ClientSession sessionProxy = new ClientSession(createSessionResponse.getSessionId(), serverId);
            sessionProxy.init();
            return sessionProxy;
        } catch (SerializationException e) {
            throw new AdapterException("Failed to create session, server id: " + serverId, e);
        }
    }

    /**
     * Returns whether the specified server is responsive.
     * 
     * @param serverId the id of the server
     * @return <code>true</code> if the server is responsive
     * @throws AdapterException
     */
    public boolean isAlive(String serverId) throws AdapterException {
        try {
            logger.info("Pinging, server id: " + serverId);
            AliveRequest aliveRequest = new AliveRequest(id);
            Request request = requestManager.createRequest(serverId, STATIC_HANDLER_ID);
            requestSubtypeSerializer.serializeRequestSubtype(aliveRequest, request);
            request.setId(getRequestId());
            request.setOneWay(false);
            Response response = requestManager.dispatchRequest(request);
            responseSubtypeSerializer.deserializeResponseSubtype(response);
            return true;
        } catch (AdapterException e) {
            return false;
        } catch (SerializationException e) {
            return false;
        }
    }

    // Private interface

    private synchronized String getRequestId() {
        String requestId = nextRequestId;
        nextRequestId = uuidGenerator.nextIdentifier().toString();
        return requestId;
    }

    // Member classes

    protected class CreateSessionRunnable implements Runnable {

        // Instance variables

        private String serverId = null;

        private FutureResult sessionHolder = null;

        // Constructors

        public CreateSessionRunnable(String serverId, FutureResult sessionHolder) {
            this.serverId = serverId;
            this.sessionHolder = sessionHolder;
        }

        // Runnable implementation

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            try {
                Session session = createSession(serverId);
                if (sessionHolder.isReady()) {
                    session.close();
                } else {
                    sessionHolder.set(session);
                }
            } catch (AdapterException e) {
                logger.warn("Failed to create session, server id: " + serverId, e);
            }
        }

    }

    protected class RequestHandler extends RequestHandlerBase {

        // RequestHandler implementation

        /*
         * (non-Javadoc)
         * 
         * @see org.egso.comms.eis.adapter.RequestHandler#handleRequest(org.egso.comms.eis.types.Request,
         * org.egso.comms.eis.types.Response)
         */
        public void handleRequest(Request request, Response response) throws RequestHandlerException {
            try {
                logger.info("Handling request, request id: " + request.getId() + ", client id: " + request.getClientId() + ", handler id: " + request.getHandlerId());
                RequestSubtype requestSubtype = requestSubtypeSerializer.deserializeRequestSubtype(request);
                if (requestSubtype instanceof CreateSessionRequest) {
                    CreateSessionResponse createSessionResponse = new CreateSessionResponse();
                    handleCreateSession((CreateSessionRequest) requestSubtype, createSessionResponse);
                    responseSubtypeSerializer.serializeResponseSubtype(createSessionResponse, response);
                } else if (requestSubtype instanceof AliveRequest) {
                    AliveResponse aliveResponse = new AliveResponse();
                    handleAlive((AliveRequest) requestSubtype, aliveResponse);
                    responseSubtypeSerializer.serializeResponseSubtype(aliveResponse, response);
                }
            } catch (SerializationException e) {
                throw new RequestHandlerException("Failed to handle request, request id: " + request.getId() + ", client id: " + request.getClientId() + ", handler id: " + request.getHandlerId(), e);
            }
        }

        // Private interface

        private void handleCreateSession(CreateSessionRequest createSessionRequest, CreateSessionResponse createSessionResponse) throws RequestHandlerException {
            try {
                logger.info("Handling session creation, client id: " + createSessionRequest.getClientId());
                if (sessionFactory == null) {
                    throw new RequestHandlerException("Failed to handle session creation, no factory, client id: " + createSessionRequest.getClientId());
                }
                String sessionId = uuidGenerator.nextIdentifier().toString();
                ServerSession serverSession = new ServerSession(createSessionRequest.getClientId(), sessionId);
                serverSession.setSession(sessionFactory.createSession(serverSession.getMetadata()));
                serverSession.init();
                createSessionResponse.setSessionId(sessionId);
            } catch (AdapterException e) {
                throw new RequestHandlerException("Failed to handle session creation, client id: " + createSessionRequest.getClientId(), e);
            }
        }

        private void handleAlive(AliveRequest aliveRequest, AliveResponse aliveResponse) throws RequestHandlerException {
            logger.info("Handling ping, client id: " + aliveRequest.getClientId());
        }

    }

    protected class ServerSession {

        // Instance variables

        private SyncMap objects = new SyncMap(new HashMap<String,Object>(), new WriterPreferenceReadWriteLock());

        private String clientId = null;

        private String sessionId = null;

        private Session session = null;

        private boolean init = false;

        // Constructors

        public ServerSession(String clientId, String sessionId) {
            this.clientId = clientId;
            this.sessionId = sessionId;
        }

        // Lifecycle implementation

        public synchronized void init() {
            if (!init) {
                requestManager.addHandler(SERVER_SESSION_HANDLER_ID_PREFIX + sessionId, new RequestHandler());
                init = true;
            }
        }

        public synchronized void destroy() {
            init = false;
            requestManager.removeHandler(SERVER_SESSION_HANDLER_ID_PREFIX + sessionId);
            objects.clear();
        }

        // Public interface

        public void setSession(Session session) {
            this.session = session;
        }

        public SessionMetadata getMetadata() {
            return new SessionMetadata();
        }

        // Member classes

        protected class SessionMetadata implements org.egso.comms.eis.adapter.SessionMetadata {

            /*
             * (non-Javadoc)
             * 
             * @see org.egso.comms.eis.adapter.SessionMetadata#getSessionId()
             */
            public String getSessionId() {
                return sessionId;
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.egso.comms.eis.adapter.SessionMetadata#getClientId()
             */
            public String getClientId() {
                return clientId;
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.egso.comms.eis.adapter.SessionMetadata#getServerId()
             */
            public String getServerId() {
                return id;
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.egso.comms.eis.adapter.SessionMetadata#getObjectId(java.lang.Object)
             */
            public String getObjectId(Object object) {
                String objectId = null;
                if (object instanceof NotifierProxy) {
                    objectId = ((NotifierProxy) object).getNotifierId();
                } else {
                  for(Object key:objects.keySet())
                    if (objects.get(key) == object)
                        objectId = (String)key;
                }
                return objectId;
            }
        }

        protected class RequestHandler extends RequestHandlerBase {

            // RequestHandler implementation

            /*
             * (non-Javadoc)
             * 
             * @see org.egso.comms.eis.adapter.RequestHandler#handleRequest(org.egso.comms.eis.types.Request,
             * org.egso.comms.eis.types.Response)
             */
            public void handleRequest(Request request, Response response) throws RequestHandlerException {
                try {
                    logger.info("Handling request, request id: " + request.getId() + ", client id: " + request.getClientId() + ", handler id: " + request.getHandlerId());
                    RequestSubtype requestSubtype = requestSubtypeSerializer.deserializeRequestSubtype(request);
                    if (requestSubtype instanceof ValidateSessionRequest) {
                        ValidateSessionResponse validateSessionResponse = new ValidateSessionResponse();
                        handleValidateSession((ValidateSessionRequest) requestSubtype, validateSessionResponse);
                        responseSubtypeSerializer.serializeResponseSubtype(validateSessionResponse, response);
                    } else if (requestSubtype instanceof GetTypesRequest) {
                        GetTypesResponse getTypesResponse = new GetTypesResponse();
                        handleGetTypes((GetTypesRequest) requestSubtype, getTypesResponse);
                        responseSubtypeSerializer.serializeResponseSubtype(getTypesResponse, response);
                    } else if (requestSubtype instanceof GetObjectRequest) {
                        GetObjectResponse getObjectResponse = new GetObjectResponse();
                        handleGetObject((GetObjectRequest) requestSubtype, getObjectResponse);
                        responseSubtypeSerializer.serializeResponseSubtype(getObjectResponse, response);
                    } else if (requestSubtype instanceof InvokeObjectRequest) {
                        InvokeObjectResponse invokeObjectResponse = new InvokeObjectResponse();
                        handleInvokeObject((InvokeObjectRequest) requestSubtype, invokeObjectResponse);
                        responseSubtypeSerializer.serializeResponseSubtype(invokeObjectResponse, response);
                    }
                } catch (SerializationException e) {
                    e.printStackTrace();
                    throw new RequestHandlerException("Failed to handle request, request id: " + request.getId() + ", client id: " + request.getClientId() + ", handler id: " + request.getHandlerId(), e);

                }
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.egso.comms.eis.adapter.RequestHandler#handleOneWayRequest(org.egso.comms.eis.types.Request)
             */
            public void handleOneWayRequest(Request request) {
                try {
                    logger.info("Handling one-way request, request id: " + request.getId() + ", client id: " + request.getClientId() + ", handler id: " + request.getHandlerId());
                    RequestSubtype requestSubtype = requestSubtypeSerializer.deserializeRequestSubtype(request);
                    if (requestSubtype instanceof CloseSessionRequest) {
                        handleCloseSession((CloseSessionRequest) requestSubtype);
                    } else if (requestSubtype instanceof ReleaseObjectRequest) {
                        handleReleaseObject((ReleaseObjectRequest) requestSubtype);
                    }
                } catch (SerializationException e) {
                    logger.error("Failed to handle one-way request, request id: " + request.getId() + ", client id: " + request.getClientId() + ", handler id: " + request.getHandlerId(), e);
                }
            }

            // Private interface

            private void handleValidateSession(ValidateSessionRequest validateSessionRequest, ValidateSessionResponse validateSessionResponse) {
                logger.info("Handling session validation, client id: " + clientId + ", session id: " + sessionId);
                validateSessionResponse.setValid(session.isValid());
            }

            private void handleCloseSession(CloseSessionRequest closeSessionRequest) {
                logger.info("Handling session close, client id: " + clientId + ", session id: " + sessionId);
                session.close();
                destroy();
            }

            private void handleGetTypes(GetTypesRequest getTypesRequest, GetTypesResponse getTypesResponse) throws RequestHandlerException {
                try {
                    logger.info("Handling get types, client id: " + clientId + ", session id: " + sessionId);
                    Class<?> notifierType = getTypesRequest.getNotifierType();
                    Class<?>[] types = null;
                    if (notifierType == null) {
                        types = session.getTypes();
                    } else {
                        types = session.getTypes(notifierType);
                    }
                    List<Class<?>> typesList = new ArrayList<Class<?>>();
                    typesList.addAll(Arrays.asList(types));
                    
                    getTypesResponse.setTypes(typesList);
                } catch (AdapterException e) {
                    throw new RequestHandlerException("Failed to handle get types, client id: " + clientId + ", session id: " + sessionId);
                }
            }

            private void handleGetObject(GetObjectRequest getObjectRequest, GetObjectResponse getObjectResponse) throws RequestHandlerException {
                try {
                    logger.info("Handling get object, client id: " + clientId + ", session id: " + sessionId + ", object type " + getObjectRequest.getType().getName());
                    String notifierId = getObjectRequest.getNotifierId();
                    String objectId = uuidGenerator.nextIdentifier().toString();
                    Class<?> type = getObjectRequest.getType();
                    Object object = null;
                    if (notifierId == null) {
                        object = session.getObject(type);
                    } else {
                        Class<?> notifierType = getObjectRequest.getNotifierType();
                        Object notifier = Proxy.newProxyInstance(InteractionMediator.class.getClassLoader(), new Class[] { notifierType }, new NotifierProxy(notifierId));
                        object = session.getObject(getObjectRequest.getType(), notifier, notifierType);
                    }
                    objects.put(objectId, object);
                    getObjectResponse.setObjectId(objectId);
                } catch (AdapterException e) {
                    throw new RequestHandlerException("Failed to handle get object, client id: " + clientId + ", session id: " + sessionId + ", object type " + getObjectRequest.getType().getName(), e);
                }
            }

            private void handleInvokeObject(InvokeObjectRequest invokeObjectRequest, InvokeObjectResponse invokeObjectResponse) throws RequestHandlerException {
                try {
                    logger.info("Handling object invocation, client id: " + clientId + ", session id: " + sessionId + ", object id: " + invokeObjectRequest.getObjectId());
                    Object object = objects.get(invokeObjectRequest.getObjectId());
                    List<Parameter> inputParams = invokeObjectRequest.getInputParams();
                    Class<?>[] types = new Class[inputParams.size()];
                    Object[] params = new Object[inputParams.size()];
                    for (int i = 0; i < types.length; i++) {
                        Parameter parameter = (Parameter) inputParams.get(i);
                        types[i] = parameter.getType();
                        params[i] = parameter.getValue();
                    }
                    String methodName = invokeObjectRequest.getMethodName();
                    Method method = object.getClass().getDeclaredMethod(methodName, types);
                    Parameter parameter = new Parameter(method.getReturnType(), method.invoke(object, params));
                    invokeObjectResponse.setOutputParam(parameter);
                } catch (java.lang.SecurityException e) {
                    throw new RequestHandlerException("Failed to handle object invocation, client id: " + clientId + ", session id: " + sessionId + ", object id: " + invokeObjectRequest.getObjectId(), e);
                } catch (IllegalArgumentException e) {
                    throw new RequestHandlerException("Failed to handle object invocation, client id: " + clientId + ", session id: " + sessionId + ", object id: " + invokeObjectRequest.getObjectId(), e);
                } catch (NoSuchMethodException e) {
                    throw new RequestHandlerException("Failed to handle object invocation, client id: " + clientId + ", session id: " + sessionId + ", object id: " + invokeObjectRequest.getObjectId(), e);
                } catch (IllegalAccessException e) {
                    throw new RequestHandlerException("Failed to handle object invocation, client id: " + clientId + ", session id: " + sessionId + ", object id: " + invokeObjectRequest.getObjectId(), e);
                } catch (InvocationTargetException e) {
                    throw new RequestHandlerException("Failed to handle object invocation, client id: " + clientId + ", session id: " + sessionId + ", object id: " + invokeObjectRequest.getObjectId(), e);
                } catch (RuntimeException e) {
                    throw new RequestHandlerException("Failed to handle object invocation, client id: " + clientId + ", session id: " + sessionId + ", object id: " + invokeObjectRequest.getObjectId(), e);
                }
            }

            private void handleReleaseObject(ReleaseObjectRequest releaseObjectRequest) {
                try {
                    logger.info("Handling object release, client id: " + clientId + ", session id: " + sessionId + ", object id: " + releaseObjectRequest.getObjectId());
                    Object object = objects.remove(releaseObjectRequest.getObjectId());
                    Method release = object.getClass().getDeclaredMethod("release", new Class[] {});
                    release.invoke(object, new Object[] {});
                } catch (java.lang.SecurityException e) {
                    logger.error("Failed handle object release, client id: " + clientId + ", session id: " + sessionId + ", object id: " + releaseObjectRequest.getObjectId(), e);
                } catch (IllegalArgumentException e) {
                    logger.error("Failed handle object release, client id: " + clientId + ", session id: " + sessionId + ", object id: " + releaseObjectRequest.getObjectId(), e);
                } catch (NoSuchMethodException e) {
                    logger.error("Failed handle object release, client id: " + clientId + ", session id: " + sessionId + ", object id: " + releaseObjectRequest.getObjectId(), e);
                } catch (IllegalAccessException e) {
                    logger.error("Failed handle object release, client id: " + clientId + ", session id: " + sessionId + ", object id: " + releaseObjectRequest.getObjectId(), e);
                } catch (InvocationTargetException e) {
                    logger.error("Failed handle object release, client id: " + clientId + ", session id: " + sessionId + ", object id: " + releaseObjectRequest.getObjectId(), e);
                }
            }

        }

        protected class NotifierProxy implements InvocationHandler {

            // Instance variables

            private String notifierId = null;

            // Constructors

            public NotifierProxy(String notifierId) {
                this.notifierId = notifierId;
            }

            // InvocationHandler implementation

            /*
             * (non-Javadoc)
             * 
             * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
             * java.lang.reflect.Method, java.lang.Object[])
             */
            public Object invoke(Object object, Method method, Object[] params) throws Throwable {
                logger.info("Invoking notifier, client id: " + clientId + ", session id: " + sessionId + ", notifier id: " + notifierId + ", method name: " + method.getName());
                Request request = requestManager.createRequest(clientId, CLIENT_SESSION_HANDLER_ID_PREFIX + sessionId);
                request.setId(getNextRequestId());
                Object outputParam = null;
                if (method.getName().equals("release")) {
                    ReleaseObjectRequest releaseObjectRequest = new ReleaseObjectRequest();
                    releaseObjectRequest.setObjectId(notifierId);
                    requestSubtypeSerializer.serializeRequestSubtype(releaseObjectRequest, request);
                    request.setOneWay(true);
                    requestManager.dispatchRequest(request);
                } else {
                    InvokeObjectRequest invokeObjectRequest = new InvokeObjectRequest();
                    invokeObjectRequest.setObjectId(notifierId);
                    invokeObjectRequest.setMethodName(method.getName());
                    Class<?>[] paramTypes = method.getParameterTypes();
                    List<Parameter> inputParamsList = new ArrayList<Parameter>();
                    for(int i = 0; i < paramTypes.length; i++) {
                        Parameter parameter = new Parameter(paramTypes[i], params[i]);
                        inputParamsList.add(parameter);
                    }
                    invokeObjectRequest.setInputParams(inputParamsList);
                    requestSubtypeSerializer.serializeRequestSubtype(invokeObjectRequest, request);
                    request.setOneWay(false);
                    Response response = requestManager.dispatchRequest(request);
                    InvokeObjectResponse invokeObjectResponse = (InvokeObjectResponse) responseSubtypeSerializer.deserializeResponseSubtype(response);
                    outputParam = invokeObjectResponse.getOutputParam().getValue();
                }
                return outputParam;
            }

            // Public interface

            public String getNotifierId() {
                return notifierId;
            }

        }

    }

    @SuppressWarnings("unchecked")
    protected class ClientSession implements Session {

        // Instance variables

        private Map<String,Object> notifiers = new SyncMap(new HashMap<String,Object>(), new WriterPreferenceReadWriteLock());

        private String sessionId = null;

        private String serverId = null;

        private boolean init = false;

        // Constructors

        public ClientSession(String sessionId, String serverId) {
            this.sessionId = sessionId;
            this.serverId = serverId;
        }

        // Lifecycle implementation

        public synchronized void init() {
            if (!init) {
                requestManager.addHandler(CLIENT_SESSION_HANDLER_ID_PREFIX + sessionId, new RequestHandler());
                init = true;
            }
        }

        public synchronized void destroy() {
            requestManager.removeHandler(CLIENT_SESSION_HANDLER_ID_PREFIX + sessionId);
            notifiers.clear();
            init = false;
        }

        // Session implementation

        /*
         * (non-Javadoc)
         * 
         * @see org.egso.comms.eis.adapter.Session#getTypes()
         */
        public Class[] getTypes() throws AdapterException {
            try {
                logger.info("Getting types, server id: " + serverId + ", session id: " + sessionId);
                if (!init) {
                    throw new AdapterException("Failed to get types, session invalid, server id: " + serverId + ", session id: " + sessionId);
                }
                GetTypesRequest getTypesRequest = new GetTypesRequest();
                Request request = requestManager.createRequest(serverId, SERVER_SESSION_HANDLER_ID_PREFIX + sessionId);
                requestSubtypeSerializer.serializeRequestSubtype(getTypesRequest, request);
                request.setId(getRequestId());
                request.setOneWay(false);
                Response response = requestManager.dispatchRequest(request);
                GetTypesResponse getTypesResponse = (GetTypesResponse) responseSubtypeSerializer.deserializeResponseSubtype(response);
                List typesList = getTypesResponse.getTypes();
                Class[] types = new Class[typesList.size()];
                for (int i = 0; i < types.length; i++) {
                    types[i] = (Class) typesList.get(i);
                }
                return types;
            } catch (SerializationException e) {
                throw new AdapterException("Failed to get types, server id: " + serverId + ", session id: " + sessionId, e);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.egso.comms.eis.adapter.Session#getObject(java.lang.Class)
         */
        public Object getObject(Class type) throws AdapterException {
            try {
                logger.info("Getting object, server id: " + serverId + ", session id: " + sessionId + ", object type: " + type.getName());
                if (!init) {
                    throw new AdapterException("Failed to get object, session invalid, server id: " + serverId + ", session id: " + sessionId);
                }
                GetObjectRequest getObjectRequest = new GetObjectRequest();
                getObjectRequest.setType(type);
                Request request = requestManager.createRequest(serverId, SERVER_SESSION_HANDLER_ID_PREFIX + sessionId);
                requestSubtypeSerializer.serializeRequestSubtype(getObjectRequest, request);
                request.setId(getRequestId());
                request.setOneWay(false);
                Response response = requestManager.dispatchRequest(request);
                GetObjectResponse getObjectResponse = (GetObjectResponse) responseSubtypeSerializer.deserializeResponseSubtype(response);
                String objectId = getObjectResponse.getObjectId();
                return Proxy.newProxyInstance(InteractionMediator.class.getClassLoader(), new Class[] { type }, new ObjectProxy(objectId));
            } catch (SerializationException e) {
                throw new AdapterException("Failed to get object, server id: " + serverId + ", session id: " + sessionId + ", object type " + type.getName(), e);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.egso.comms.eis.adapter.Session#getTypes(java.lang.Class)
         */
        public Class[] getTypes(Class notifierType) throws AdapterException {
            try {
                logger.info("Getting types, server id: " + serverId + ", session id: " + sessionId + ", notifier type: " + notifierType.getName());
                if (!init) {
                    throw new AdapterException("Failed to get types, session invalid, server id: " + serverId + ", session id: " + sessionId + ", notifier type: " + notifierType.getName());
                }
                GetTypesRequest getTypesRequest = new GetTypesRequest(notifierType);
                Request request = requestManager.createRequest(serverId, SERVER_SESSION_HANDLER_ID_PREFIX + sessionId);
                requestSubtypeSerializer.serializeRequestSubtype(getTypesRequest, request);
                request.setId(getRequestId());
                request.setOneWay(false);
                requestManager.dispatchRequest(request);
                GetTypesResponse getTypesResponse = (GetTypesResponse) requestSubtypeSerializer.deserializeRequestSubtype(request);
                List typesList = getTypesResponse.getTypes();
                Class[] types = new Class[typesList.size()];
                for (int i = 0; i < types.length; i++) {
                    types[i] = (Class) typesList.get(i);
                }
                return types;
            } catch (SerializationException e) {
                throw new AdapterException("Failed to get types, server id: " + serverId + ", session id: " + sessionId + ", notifier type: " + notifierType.getName(), e);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.egso.comms.eis.adapter.Session#getObject(java.lang.Class,
         * java.lang.Object, java.lang.Class)
         */
        public Object getObject(Class type, Object notifier, Class notifierType) throws AdapterException {
            try {
                logger.info("Getting object, server id: " + serverId + ", session id: " + sessionId + ", object type " + type.getName() + ", notifier type: " + notifierType.getName());
                if (!init) {
                    throw new AdapterException("Failed to get object, session invalid, server id: " + serverId + ", session id: " + sessionId + ", object type " + type.getName() + ", notifier type: " + notifierType.getName());
                }
                GetObjectRequest getObjectRequest = new GetObjectRequest();
                getObjectRequest.setType(type);
                String notifierId = uuidGenerator.nextIdentifier().toString();
                notifiers.put(notifierId, notifier);
                getObjectRequest.setNotifierId(notifierId);
                getObjectRequest.setNotifierType(notifierType);
                Request request = requestManager.createRequest(serverId, SERVER_SESSION_HANDLER_ID_PREFIX + sessionId);
                requestSubtypeSerializer.serializeRequestSubtype(getObjectRequest, request);
                request.setId(getRequestId());
                request.setOneWay(false);
                Response response = requestManager.dispatchRequest(request);
                GetObjectResponse getObjectResponse = (GetObjectResponse) responseSubtypeSerializer.deserializeResponseSubtype(response);
                String objectId = getObjectResponse.getObjectId();
                return Proxy.newProxyInstance(InteractionMediator.class.getClassLoader(), new Class[] { type }, new ObjectProxy(objectId));
            } catch (SerializationException e) {
                throw new AdapterException("Failed to get object, server id: " + serverId + ", session id: " + sessionId + ", object type " + type.getName() + ", notifier type: " + notifierType.getName(), e);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.egso.comms.eis.adapter.Session#isValid()
         */
        public boolean isValid() {
            try {
                logger.info("Validating session, server id: " + serverId + ", session id: " + sessionId);
                if (!init) {
                    return false;
                }
                ValidateSessionRequest validateSessionRequest = new ValidateSessionRequest();
                Request request = requestManager.createRequest(serverId, SERVER_SESSION_HANDLER_ID_PREFIX + sessionId);
                requestSubtypeSerializer.serializeRequestSubtype(validateSessionRequest, request);
                request.setId(getRequestId());
                request.setOneWay(false);
                Response response = requestManager.dispatchRequest(request);
                ValidateSessionResponse validateSessionResponse = (ValidateSessionResponse) responseSubtypeSerializer.deserializeResponseSubtype(response);
                boolean valid = validateSessionResponse.isValid();
                if (!valid) {
                    destroy();
                }
                return valid;
            } catch (AdapterException e) {
                destroy();
                return false;
            } catch (SerializationException e) {
                destroy();
                return false;
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.egso.comms.eis.adapter.Session#getMetadata()
         */
        public org.egso.comms.eis.adapter.SessionMetadata getMetadata() {
            return new SessionMetadata();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.egso.comms.eis.adapter.Session#close()
         */
        public void close() {
            try {
                logger.info("Closing session, server id: " + serverId + ", session id: " + sessionId);
                if (!init) {
                    logger.warn("Failed to close session, session invalid, server id: " + serverId + ", session id: " + sessionId);
                    return;
                }
                CloseSessionRequest closeSessionRequest = new CloseSessionRequest();
                Request request = requestManager.createRequest(serverId, SERVER_SESSION_HANDLER_ID_PREFIX + sessionId);
                requestSubtypeSerializer.serializeRequestSubtype(closeSessionRequest, request);
                request.setId(getRequestId());
                request.setOneWay(true);
                requestManager.dispatchRequest(request);
                destroy();
            } catch (AdapterException e) {
                logger.warn("Failed to close session, server id: " + serverId + ", session id: " + sessionId, e);
            } catch (SerializationException e) {
                logger.warn("Failed to close session, server id: " + serverId + ", session id: " + sessionId, e);
            }
        }

        // Member classes

        protected class SessionMetadata implements org.egso.comms.eis.adapter.SessionMetadata {

            /*
             * (non-Javadoc)
             * 
             * @see org.egso.comms.eis.adapter.SessionMetadata#getSessionId()
             */
            public String getSessionId() {
                return sessionId;
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.egso.comms.eis.adapter.SessionMetadata#getClientId()
             */
            public String getClientId() {
                return id;
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.egso.comms.eis.adapter.SessionMetadata#getServerId()
             */
            public String getServerId() {
                return serverId;
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.egso.comms.eis.adapter.SessionMetadata#getObjectId(java.lang.Object)
             */
            public String getObjectId(Object object) {
                String objectId = null;
                if (object instanceof ObjectProxy) {
                    objectId = ((ObjectProxy) object).getObjectId();
                } else {
                    for (String key:notifiers.keySet()) {
                        if (notifiers.get(key) == object) {
                            objectId = key;
                        }
                    }
                }
                return objectId;
            }

        }

        protected class RequestHandler extends RequestHandlerBase {

            // RequestHandler implementation

            /*
             * (non-Javadoc)
             * 
             * @see org.egso.comms.eis.adapter.RequestHandler#handleRequest(org.egso.comms.eis.types.Request,
             * org.egso.comms.eis.types.Response)
             */
            public void handleRequest(Request request, Response response) throws RequestHandlerException {
                try {
                    logger.info("Handling request, request id: " + request.getId() + ", server id: " + serverId + ", handler id: " + request.getHandlerId());
                    RequestSubtype requestSubtype = requestSubtypeSerializer.deserializeRequestSubtype(request);
                    if (requestSubtype instanceof InvokeObjectRequest) {
                        InvokeObjectResponse invokeObjectResponse = new InvokeObjectResponse();
                        handleInvokeObject((InvokeObjectRequest) requestSubtype, invokeObjectResponse);
                        responseSubtypeSerializer.serializeResponseSubtype(invokeObjectResponse, response);
                    }
                } catch (SerializationException e) {
                    throw new RequestHandlerException("Failed to handle request, request id: " + request.getId() + ", server id: " + serverId + ", handler id: " + request.getHandlerId(), e);
                }
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.egso.comms.eis.adapter.RequestHandler#handleOneWayRequest(org.egso.comms.eis.types.Request)
             */
            public void handleOneWayRequest(Request request) {
                try {
                    logger.info("Handling one-way request, request id: " + request.getId() + ", server id: " + serverId + ", handler id: " + request.getHandlerId());
                    RequestSubtype requestSubtype = requestSubtypeSerializer.deserializeRequestSubtype(request);
                    if (requestSubtype instanceof ReleaseObjectRequest) {
                        handleReleaseObject((ReleaseObjectRequest) requestSubtype);
                    }
                } catch (SerializationException e) {
                    logger.error("Failed to handle one-way request, request id: " + request.getId() + ", server id: " + serverId + ", handler id: " + request.getHandlerId(), e);
                }
            }

            // Private interface

            private void handleInvokeObject(InvokeObjectRequest invokeObjectRequest, InvokeObjectResponse invokeObjectResponse) throws RequestHandlerException {
                try {
                    logger.info("Handling notifier invocation, server id: " + serverId + ", session id: " + sessionId + ", object id: " + invokeObjectRequest.getObjectId());
                    Object notifier = notifiers.get(invokeObjectRequest.getObjectId());
                    List<Parameter> inputParams = invokeObjectRequest.getInputParams();
                    Class<?>[] types = new Class[inputParams.size()];
                    Object[] params = new Object[inputParams.size()];
                    for (int i = 0; i < types.length; i++) {
                        Parameter parameter = (Parameter) inputParams.get(i);
                        types[i] = parameter.getType();
                        params[i] = parameter.getValue();
                    }
                    String methodName = invokeObjectRequest.getMethodName();
                    Method method = notifier.getClass().getDeclaredMethod(methodName, types);
                    Parameter parameter = new Parameter(method.getReturnType(), method.invoke(notifier, params));
                    invokeObjectResponse.setOutputParam(parameter);
                } catch (java.lang.SecurityException e) {
                    e.printStackTrace();
                    throw new RequestHandlerException("Failed to handle notifier invocation, server id: " + serverId + ", session id: " + sessionId + ", object id: " + invokeObjectRequest.getObjectId(), e);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    throw new RequestHandlerException("Failed to handle notifier invocation, server id: " + serverId + ", session id: " + sessionId + ", object id: " + invokeObjectRequest.getObjectId(), e);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    throw new RequestHandlerException("Failed to handle notifier invocation, server id: " + serverId + ", session id: " + sessionId + ", object id: " + invokeObjectRequest.getObjectId(), e);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new RequestHandlerException("Failed to handle notifier invocation, server id: " + serverId + ", session id: " + sessionId + ", object id: " + invokeObjectRequest.getObjectId(), e);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    throw new RequestHandlerException("Failed to handle notifier invocation, server id: " + serverId + ", session id: " + sessionId + ", object id: " + invokeObjectRequest.getObjectId(), e);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    throw new RequestHandlerException("Failed to handle notifier invocation, server id: " + serverId + ", session id: " + sessionId + ", object id: " + invokeObjectRequest.getObjectId(), e);
                }
            }

            private void handleReleaseObject(ReleaseObjectRequest releaseObjectRequest) {
                try {
                    logger.info("Handling notifier release, server id: " + serverId + ", session id: " + sessionId + ", object id: " + releaseObjectRequest.getObjectId());
                    Object notifier = notifiers.remove(releaseObjectRequest.getObjectId());
                    Method release = notifier.getClass().getDeclaredMethod("release", new Class[] {});
                    release.invoke(notifier, new Object[] {});
                } catch (java.lang.SecurityException e) {
                    logger.error("Failed to handle notifier release, server id: " + serverId + ", session id: " + sessionId + ", object id: " + releaseObjectRequest.getObjectId(), e);
                } catch (IllegalArgumentException e) {
                    logger.error("Failed to handle notifier release, server id: " + serverId + ", session id: " + sessionId + ", object id: " + releaseObjectRequest.getObjectId(), e);
                } catch (NoSuchMethodException e) {
                    logger.error("Failed to handle notifier release, server id: " + serverId + ", session id: " + sessionId + ", object id: " + releaseObjectRequest.getObjectId(), e);
                } catch (IllegalAccessException e) {
                    logger.error("Failed to handle notifier release, server id: " + serverId + ", session id: " + sessionId + ", object id: " + releaseObjectRequest.getObjectId(), e);
                } catch (InvocationTargetException e) {
                    logger.error("Failed to handle notifier release, server id: " + serverId + ", session id: " + sessionId + ", object id: " + releaseObjectRequest.getObjectId(), e);
                }
            }
        }

        protected class ObjectProxy implements InvocationHandler {

            // Instance variables

            private String objectId = null;

            // Constructors

            public ObjectProxy(String objectId) {
                this.objectId = objectId;
            }

            // InvocationHandler implementation

            /*
             * (non-Javadoc)
             * 
             * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
             * java.lang.reflect.Method, java.lang.Object[])
             */
            public Object invoke(Object object, Method method, Object[] params) throws Throwable {
                logger.info("Invoking object, server id: " + serverId + ", session id: " + sessionId + ", object id: " + objectId + ", method name: " + method.getName());
                Request request = requestManager.createRequest(serverId, SERVER_SESSION_HANDLER_ID_PREFIX + sessionId);
                request.setId(getRequestId());
                Object outputParam = null;
                if (method.getName().equals("release")) {
                    ReleaseObjectRequest releaseObjectRequest = new ReleaseObjectRequest();
                    releaseObjectRequest.setObjectId(objectId);
                    requestSubtypeSerializer.serializeRequestSubtype(releaseObjectRequest, request);
                    request.setOneWay(true);
                    requestManager.dispatchRequest(request);
                } else {
                    InvokeObjectRequest invokeObjectRequest = new InvokeObjectRequest();
                    invokeObjectRequest.setObjectId(objectId);
                    invokeObjectRequest.setMethodName(method.getName());
                    Class<?>[] paramTypes = method.getParameterTypes();
                    List<Parameter> inputParamsList = new ArrayList<Parameter>();
                    for (int i = 0; i < paramTypes.length; i++) {
                        Parameter parameter = new Parameter(paramTypes[i], params[i]);
                        inputParamsList.add(parameter);
                    }
                    invokeObjectRequest.setInputParams(inputParamsList);
                    requestSubtypeSerializer.serializeRequestSubtype(invokeObjectRequest, request);
                    request.setOneWay(false);
                    Response response = requestManager.dispatchRequest(request);
                    InvokeObjectResponse invokeObjectResponse = (InvokeObjectResponse) responseSubtypeSerializer.deserializeResponseSubtype(response);
                    outputParam = invokeObjectResponse.getOutputParam().getValue();
                }
                return outputParam;
            }

            // Public interface

            public String getObjectId() {
                return objectId;
            }

        }

    }

}