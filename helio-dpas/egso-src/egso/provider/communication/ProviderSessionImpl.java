package org.egso.provider.communication;

import org.egso.common.services.network.Ping;
import org.egso.common.services.provider.CoSECProvider;
import org.egso.common.services.provider.FileQueryProvider;
import org.egso.common.services.provider.QueryProvider;
import org.egso.common.services.provider.ResponseFileQueryProvider;
import org.egso.common.services.provider.ResponseQueryProvider;
import org.egso.comms.eis.adapter.AdapterException;
import org.egso.comms.eis.adapter.Session;
import org.egso.comms.eis.adapter.SessionMetadata;
import org.egso.provider.query.QueryEngine;

/**
 * JAVADOC: Description of the Class
 * 
 * @author Romain Linsolas (linsolas@gmail.com)
 * @version 1.0 - [10/02/2004]
 */
public class ProviderSessionImpl implements Session {

    /**
     * QueryEngine that handles the queries.
     */
    private QueryEngine queryEngine = null;

    private SessionMetadata metadata = null;

    /**
     * Constructor for the ProviderSessionImpl object.
     * 
     * @param qei
     *            QueryEngine that handles queries.
     */
    public ProviderSessionImpl(QueryEngine qe, SessionMetadata metadata) {
        queryEngine = qe;
        this.metadata = metadata;
    }

    /**
     * Returns an Object of the specified type for ASYNCHRONOUS communications.
     * Also allows an Object to be passed that can be used as a notification
     * mechanism back to the client.
     * 
     * @param type
     *            the type of Object to return
     * @param notifier
     *            the notifier Object to use
     * @param notifierType
     *            the type of the notifier Object
     * @return the Object
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Object getObject(Class type, Object notifier, Class notifierType)
            throws AdapterException {
        System.out.println("ProviderSessionImpl#getObject [ASYNC]: "
                + type.getName());
        System.out.println("\tNotifier: " + notifier.getClass().getName());
        System.out.println("\tNotifierType: " + notifierType.getName());
        if (type == QueryProvider.class) {
            if (notifierType == ResponseQueryProvider.class) {
                return (new QueryProviderImpl(queryEngine,
                        (ResponseQueryProvider) notifier));
            } else {
                throw (new AdapterException(
                        "The notifier must be a ResponseProvider class, and not a "
                                + notifierType.getName() + " class."));
            }
        } else {
            if (type == FileQueryProvider.class) {
                if (notifierType == ResponseFileQueryProvider.class) {
                    return (new QueryProviderImpl(queryEngine,
                            (ResponseFileQueryProvider) notifier));
                } else {
                    throw (new AdapterException(
                            "The notifier must be a ResponseFileQueryProvider class, and not a "
                                    + notifierType.getName() + " class."));
                }
            } else {
                throw (new AdapterException(
                        "The type muse be a QueryProvider or FileQueryProvider class and not a "
                                + type.getName() + " class."));
            }
        }
    }

    /**
     * Returns an Object of the specified type. Used for SYNCHRONOUS
     * communications.
     * 
     * @param type
     *            Type of the requested object.
     * @return The object itself.
     * @throws Exception
     */
    public Object getObject(Class<?> type) throws AdapterException {
        System.out.println("ProviderSessionImpl#getObject [SYNC]: "
                + type.getName());
        if (type == CoSECProvider.class) {
            System.out.println("\tCoSEC invocation.");
            return (new QueryProviderImpl(queryEngine));
        } else {
            if (type == Ping.class) {
                return (new QueryProviderImpl());
            } else {
                System.out.println("\tError, the class " + type.getName()
                        + " is not valid here.");
                throw (new AdapterException("Class '" + type.getName()
                        + "' not found for SYNCHRONOUS communication."));
            }
        }
    }

    public void close() {
    }

    public boolean isValid() {
        return (true);
    }

    public Class<?>[] getTypes() throws AdapterException {
        return (null);
    }

    public Class<?>[] getTypes(Class<?> arg) throws AdapterException {
        return (null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.egso.comms.eis.adapter.Session#getMetadata()
     */
    public SessionMetadata getMetadata() {
        return metadata;
    }

}

