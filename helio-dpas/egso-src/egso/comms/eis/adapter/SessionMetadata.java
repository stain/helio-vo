/*
 * Created on Feb 21, 2005
 */
package org.egso.comms.eis.adapter;

/**
 * Interface for objects that provide information about a <code>Session</code>.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public interface SessionMetadata {
        
    /**
     * Returns the id for the <code>Session</code>
     * 
     * @return the id
     */
    public String getSessionId();
    
    /**
     * Returns the server id for the <code>Session</code>
     * 
     * @return the server id
     */
    public String getServerId();
    
    /**
     * Returns the client id for the <code>Session</code>
     * 
     * @return the client id
     */
    public String getClientId();
        
    /**
     * Returns the id of the specified <code>Object</code> or
     * <code>null</code> if the object is unknown.
     * 
     * @param object the object
     * @return the object id
     */
    public String getObjectId(Object object);
    
}
