/*
 * Created on Jul 5, 2004
 */
package org.egso.comms.eis.adapter;

/**
 * Interface for objects that implement a stateful session.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public interface Session {
    
    /**
     * Returns the types of object supported by this session.
     * 
     * @return the object types
     * @throws AdapterException
     */
    public Class<?>[] getTypes() throws AdapterException;

    /**
     * Returns an object of the specified type. Method calls to this object may
     * be serialized, in which case only a limited set of parameter types can
     * be used in it's interface methods. These are defined by the
     * <code>ParameterSerializer</code> class.
     * 
     * 
     * @param type the type of the object
     * @return the object
     * @throws AdapterException
     * @see org.egso.comms.eis.types.ParameterSerializer
     */
    public Object getObject(Class<?> type) throws AdapterException;

    /**
     * Returns the types of object supported by this session for the
     * specified notifier type.
     * 
     * @param notifierType the type of the notifier
     * @return the object types
     * @throws AdapterException
     */
    public Class<?>[] getTypes(Class<?> notifierType) throws AdapterException;

    /**
     * Returns an object of the specified type, supplying the server
     * with a notifier for callbacks. Method calls to this object may
     * be serialized, in which case only a limited set of parameter types can
     * be used in it's interface methods. These are defined by the
     * <code>ParameterSerializer</code> class.
     * 
     * @param type the type of the object
     * @param notifier the object used as a notifier for callbacks
     * @param notifierType the type of the notifier
     * @return the object
     * @throws AdapterException
     * @see org.egso.comms.eis.types.ParameterSerializer
     */
    public Object getObject(Class<?> type, Object notifier, Class<?> notifierType) throws AdapterException;
    
    /**
     * Returns whether the session is still valid.
     * 
     * @return <code>true</code> if the session is still valid
     */
    public boolean isValid();

    /**
     * Returns metadata for this session.
     * 
     * @return the metadata
     * @throws AdapterException
     */
    public SessionMetadata getMetadata();
    
    /**
     * Closes the session.
     */
    public void close();
    
}