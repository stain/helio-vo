package org.egso.common.externalinteraction;
import java.net.URI;
import org.egso.comms.eis.adapter.Session;
import org.egso.comms.nds.types.ApplicationList;
/**
 * Role independent component to handle grid services.
 * 
 * @author Marco Soldati
 * @created January 2004
 * @version $id$
 */
public interface InteractionManager
{        
    /**
     * Login to ECI and return a session object to a specific remote host
     * 
     * @param name
     *            Name of the user
     * @param password
     *            Password of the user
     * @param recipient
     *            Name of the recipient
     * @return A session to connect to the Grid
     * @exception InteractionManagerException
     *                If anything goes wrong.
     */
    public Session getSession(String name, String password, String recipient)
            throws InteractionManagerException;

    /**
     * Login to ECI and return a session object to fastest of a specific group of remote host
     * 
     * @param name
     *            Name of the user
     * @param password
     *            Password of the user
     * @param recipient
     *            List of recipient names
     * @return A session to connect to the Grid
     * @exception InteractionManagerException
     *                If anything goes wrong.
     */
    public Session getSession(String name, String password, String[] recipient)
            throws InteractionManagerException;

    /**
     * Login to ECI and return a session object to a specific type of remote host
     * 
     * @param name
     *            Name of the user
     * @param password
     *            Password of the user
     * @param partner
     *            Name of the partner
     * @return A session to connect to the Grid
     * @exception InteractionManagerException
     *                If anything goes wrong.
     */
    public Session getSession(String name, String password, URI partner)
            throws InteractionManagerException;
    
    /**
     * release a previously acquired session.
     * @param session the session to release.
     */
    public void releaseSession(Session session); 
    
    /**
     * Dynamically lookup Applications of a certain type
     * 
     * @param type urn of the application
     * @return List of Applications
     * @throws InteractionManagerException
     */
    public ApplicationList selectApplicationByType(URI type)
            throws InteractionManagerException;
    
    /**
     * Returns the id of this application.
     * 
     * @return returns the id of this application
     */
    public String getId();
}