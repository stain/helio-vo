package org.egso.provider.communication;

import org.egso.comms.eis.adapter.AdapterException;
import org.egso.comms.eis.adapter.Session;
import org.egso.comms.eis.adapter.SessionFactory;
import org.egso.comms.eis.adapter.SessionMetadata;
import org.egso.provider.query.QueryEngine;
import org.egso.provider.utils.ProviderUtils;

/**
 * Implementation of a Session Factory for the Provider communication module.
 * 
 * @author Romain Linsolas (linsolas@gmail.com)
 * @version 1.0 - [10/02/2004]
 */
public class ProviderSessionFactoryImpl implements SessionFactory {

    /**
     * QueryEngine that handles queries for the provider.
     */
    private QueryEngine queryEngine = null;

    /**
     * Constructor for the ProviderSessionFactoryImpl object.
     * 
     * @param qei
     *            Instance of the QueryEngine that handles the queries.
     */
    public ProviderSessionFactoryImpl(QueryEngine qe) {
        queryEngine = qe;
    }

    //	/**
    //	 * Creates a new session for a given partner.
    //	 *
    //	 * @param partner The partner requesting the session.
    //	 * @return The session.
    //	 * @exception AdapterException If the creation of a session failed.
    //	 */
    //	 public Session createSession(String partner)
    //	 	throws AdapterException {
    //		System.out.println("[ECI] " + ProviderUtils.getDate() + " -> Creating a
    // new Session for the partner " + partner + ".");
    //		return (new ProviderSessionImpl(queryEngine));
    //	}

    /**
     * Creates a new session for a given partner.
     * 
     * @param partner
     *            The partner requesting the session.
     * @return The session.
     * @exception AdapterException
     *                If the creation of a session failed.
     */
    public Session createSession(SessionMetadata metadata)
            throws AdapterException {
        System.out.println("[ECI] " + ProviderUtils.getDate()
                + " -> Creating a new Session for the partner "
                + metadata.getClientId() + ".");
        return (new ProviderSessionImpl(queryEngine, metadata));
    }

}

