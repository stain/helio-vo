package org.egso.common.externalinteraction;

import java.net.URI;
import org.egso.common.CommonConfig;
import org.egso.common.Disposable;
import org.egso.common.logging.AbstractLogEnabled;
import org.egso.comms.eis.adapter.AdapterException;
import org.egso.comms.eis.adapter.InteractionMediator;
import org.egso.comms.eis.adapter.Session;
import org.egso.comms.eis.adapter.SessionFactory;
import org.egso.comms.nds.types.ApplicationList;

/**
 * Adapter for the InteractionMediator.<br>
 * Use the following parameter in the config file to enable server mode.<br>
 * <pre>
 * &lt;utils&gt;
 *   &lt;interactionmediatoradapter&gt;
 *     &lt;isServer&gt;true&lt;/isServer&gt;
 *   &lt;/interactionmediatoradapter&gt;
 * &lt;/utils&gt;
 * </pre>
 * If the server mode is enabled the component creates a new {@link SessionFactoryImpl SessionFactoryImpl}
 * which will then be registered with the ECI interactionMediator.
 *
 * @author     Marco Soldati
 * @created    9. Februar 2004
 * @version    $id$
 */
public class InteractionMediatorAdapterImpl extends AbstractLogEnabled implements InteractionMediatorAdapter, Disposable
{
    /**
     *  holds the interactionMediator
     */
    private InteractionMediator interactionMediator = null;
    
    
    // *************************** Constructor ***********************************
    /**
     *  Constructor for the InteractionMediatorAdapterImpl object
     */
    public InteractionMediatorAdapterImpl() throws Exception
    {
        initialize();
    }
    
    /**
     *  Initialize the interactionMediator.
     *
     * @throws  Exception if anything goes wrong
     */
    private void initialize() throws Exception
    {
        if (this.getLogger().isDebugEnabled())
        {
            this.getLogger().debug("Initializing new InteractionMediator().");
        }
        
        //create the interaction Mediator
        interactionMediator = new InteractionMediator();
        interactionMediator.init();
        
        SessionFactory sessionFactory = null;

        // if server mode, create and register the session Factory
        if (CommonConfig.configuration.getBoolean("utils.interactionmediatoradapter.isServer"))
        {
            sessionFactory = new SessionFactoryImpl();
            interactionMediator.setSessionFactory(sessionFactory);
            if (this.getLogger().isDebugEnabled())
            {
                this.getLogger().debug("InteractionMediator is in servermode: SessionFactory was acquired.");
            }
        }
        
    }
       
    /**
     * Dispose the interactionmediator on shutdown. 
     */
    public void dispose()
    {
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
}
