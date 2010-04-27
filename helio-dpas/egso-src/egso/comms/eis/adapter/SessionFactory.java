/*
 * Created on Dec 18, 2003
 */
package org.egso.comms.eis.adapter;

import org.egso.comms.eis.adapter.Session;

/**
 * Interface for objects that provide <code>Sessions</code>.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public interface SessionFactory {

    /**
     * Creates a <code>Session</code> for the specified client.
     * 
     * @param metadata the metadata associated with the <code>Session</code>
     * @return the created <code>Session</code>
     * @throws AdapterException
     */
    public Session createSession(SessionMetadata metadata) throws AdapterException;

}