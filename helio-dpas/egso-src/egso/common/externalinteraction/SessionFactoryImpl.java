package org.egso.common.externalinteraction;

import org.egso.common.logging.AbstractLogEnabled;
import org.egso.comms.eis.adapter.AdapterException;
import org.egso.comms.eis.adapter.Session;
import org.egso.comms.eis.adapter.SessionFactory;
import org.egso.comms.eis.adapter.SessionMetadata;


/**
 * Simple default implementation of the ECI session factory interface. 
 * The session factory mainly creates a DefaultSession object.
 * 
 * @author soldati
 * @created 10.02.2004
 * 
 */
public class SessionFactoryImpl extends AbstractLogEnabled implements SessionFactory 
{
    /**
     * create a new Default Session for local loopback
     * @see org.egso.eis.adapter.SessionFactory#createSession(java.lang.String)
     */
    public Session createSession(SessionMetadata metadata) throws AdapterException
    {
        if (this.getLogger().isDebugEnabled())
            this.getLogger().debug("Create new server side session for partner " + metadata.getClientId());
        return (new DefaultSession(metadata));
    }
    
}
