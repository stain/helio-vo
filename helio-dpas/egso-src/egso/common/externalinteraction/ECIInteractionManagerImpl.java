package org.egso.common.externalinteraction;

import java.net.URI;
import java.util.List;
import java.util.Vector;
import org.egso.common.CommonConfig;
import org.egso.common.Disposable;
import org.egso.common.logging.AbstractLogEnabled;
import org.egso.comms.eis.adapter.AdapterException;
import org.egso.comms.eis.adapter.InteractionMediator;
import org.egso.comms.eis.adapter.Session;
import org.egso.comms.eis.adapter.SessionFactory;
import org.egso.comms.nds.types.ApplicationList;

/**
 *  Role independent component that handles grid services. The components uses
 *  ECI
 *
 * @author     Marco Soldati
 * @created    October 2003
 * @version    $id$
 */
public class ECIInteractionManagerImpl extends AbstractLogEnabled 
implements InteractionManager, Disposable
{
    // *************************** private attributes ****************************
    /**
     * store the singelton instance
     */
    private static ECIInteractionManagerImpl eciInteractionManager = null;
        
    /**
     *  store open sessions
     */
    private List<Session> openSessions = new Vector<Session>();

    /**
     *  holds the interactionMediator
     */
    private InteractionMediator interactionMediator = null;

    
    // ***************************** Constructor *********************************
    /**
     *  the default constructor initializes the component
     */
    private ECIInteractionManagerImpl() throws Exception
    {
        initialize();
    }
    
    /**
     * Return the singelton instance of the ECIInteractionManager.
     * @return
     */
    public synchronized static InteractionManager getInstance() throws Exception
    {
        if (eciInteractionManager == null)
            eciInteractionManager = new ECIInteractionManagerImpl();
        
        return eciInteractionManager;
    }
    
    /**
     *  Initialize the component.
     *
     * @exception  Exception  If anything goes wrong.
     */
    private synchronized void initialize() throws Exception
    {        
        //create the interaction Mediator
        interactionMediator = new InteractionMediator();
        interactionMediator.init();
        
        SessionFactory sessionFactory = null;

        // if server mode, create and register the session Factory
        if (CommonConfig.configuration.getBoolean("utils.interactionmediatoradapter.isServer"))
        {
            sessionFactory = new SessionFactoryImpl();
            interactionMediator.setSessionFactory(sessionFactory);
            this.getLogger().info("InteractionMediator is in servermode: SessionFactory was acquired.");
        }
        else
        {
            this.getLogger().info("InteractionMediator is in client mode.");
        }
    }
    
    /**
     * release the interactionMediatorAdapter and clean the sessionMap
     * @see org.egso.common.Disposable#dispose()
     */
    public synchronized void dispose()
    {
        // close all open sessions
        for (Session session:openSessions)
        {
            try
            {
                session.close();
            }
            catch (Throwable t)
            {
                // ignore
            }
        }
        
        eciInteractionManager = null;
        if(interactionMediator != null)
        {
            try
            {
                interactionMediator.destroy();
            }
            catch(Exception e)
            {
                throw new RuntimeException("Exception while cleaning up interaction Mediator", e);
            }
        }
        interactionMediator = null;
    }
    
    
    // ************************ interface InteractionManager **********************
    /**
     *  Login to EIS and return a session object to a specific remote host
     *
     * @param  name                             Name of the user
     * @param  password                         Password of the user
     * @param  recipient                        Name of the recipient
     * @return                                  A session to connect to the Grid
     * @exception  InteractionManagerException  If anything goes wrong
     */
    public Session getSession(String name, String password, String recipient)
    throws InteractionManagerException
    {
        if(this.getLogger().isDebugEnabled())
        {
            this.getLogger().debug("create new session.");
        }
        
        try
        {
            Session session = interactionMediator.createSession(recipient);
            openSessions.add(session);
            return session;
        }
        catch(Exception e)
        {
            throw new InteractionManagerException("Exception while creating session: " + e.getMessage(), e);
        }        
    }
    
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
            throws InteractionManagerException
    {
        if(this.getLogger().isDebugEnabled())
        {
            this.getLogger().debug("create new session.");
        }
        
        try
        {
            Session session = interactionMediator.createSession(recipient);
            openSessions.add(session);
            return session;
        }
        catch(Exception e)
        {
            throw new InteractionManagerException("Exception while creating session: " + e.getMessage(), e);
        }
    }

    
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
            throws InteractionManagerException
    {
        if(this.getLogger().isDebugEnabled())
        {
            this.getLogger().debug("create new session.");
        }
        
        try
        {
            Session session = interactionMediator.createSession(interactionMediator.selectApplicationByType(partner));
            openSessions.add(session);
            return session;
        }
        catch(Exception e)
        {
            throw new InteractionManagerException("Exception while creating session: " + e.getMessage(), e);
        }        
    }
    
    /**
     * release a previously acquired session.
     * @param session the session to release.
     */
    public void releaseSession(Session session)
    {
        int pos = openSessions.indexOf(session);
        
        if (pos != -1)
        {
            Session currentSession = openSessions.get(pos);
            
            openSessions.remove(pos);
            currentSession.close();
        } 
        else
        {
            final String message = "Unknown Sessions cannot be released (EISInteractionManager.releaseSession())";
            getLogger().warn(message);
            return;
        }
    }
    

    /**
     * Dynamically lookup Applications of a certain type
     * @param type urn of the application
     * @return List of Applications
     * @throws InteractionManagerException
     */
    public ApplicationList selectApplicationByType(URI type) 
        throws InteractionManagerException
    {   
        try
        {
            return interactionMediator.selectApplicationByType(type);
        }
        catch (AdapterException e) {
            throw new InteractionManagerException(e.getMessage(), e);
        }
    }
    
    // ************************* client side methods *****************************
    /**
     *  Returns a Session with the specified partner.
     *
     * @param  partner        the partner to set up a session with
     * @return                the Session
     * @exception  Exception  if anything goes wrong
     */
    public Session createSession(final String partner)
        throws InteractionManagerException
    {
        if (this.getLogger().isDebugEnabled())
        {
            this.getLogger().debug("Creating a new client side Session for partner: " + partner);
        }      
        
        try
        {       
            // original implementation
            return interactionMediator.createSession(partner);
        }
        catch (AdapterException e)
        {
            throw new InteractionManagerException("AdapterException while getting a session: " + e.getMessage(), e);
        }
        finally 
        {
            if (this.getLogger().isDebugEnabled())
            {
                this.getLogger().debug("Client side Session created.");
            }      
        }
    }

    /*
     * Get the id of this application
     * @see org.egso.common.externalinteraction.InteractionManager#getId()
     */
    public String getId()
    {
        return interactionMediator.getId();
    }
    
    
}