package org.egso.common.network;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import org.egso.common.externalinteraction.InteractionManager;
import org.egso.common.externalinteraction.InteractionManagerException;
import org.egso.common.externalinteraction.InteractionManagerFactory;
import org.egso.comms.nds.types.*;

/**
 * Helper class for application discovery
 * @author Marco Soldati
 * @created 16.09.2004
 */
public class ApplicationDiscovery
{
    /**
     * URI for the broker
     */
    public static String APPLICATION_TYPE_BROKER = "urn:org.egso.comms/broker";
    
    /**
     * URI of the provider
     */
    public static String APPLICATION_TYPE_PROVIDER = "urn:org.egso.comms/provider";
    
    /**
     * URI of the consumer
     */
    public static String APPLICATION_TYPE_CONSUMER = "urn:org.egso.comms/consumer";

    /**
     * hold reference to the interaction manager
     */
    InteractionManager interactionManager = null;
    
    /**
     * init the component
     * @see org.apache.avalon.framework.activity.Initializable#initialize()
     */
    private void initialize()
    {
        interactionManager = InteractionManagerFactory.getInteractionManager();    
    }
    
    /**
     * 
     */
    public ApplicationDiscovery()
    {
        super();
        initialize();
    }
    
    /**
     * Gets a List of {@link org.egso.comms.nds.types.Application org.egso.comms.nds.types.Application}
     * of type consumer. 
     * @return List of Application objects. 
     * @throws InteractionManagerException
     */
    public List<Application> getConsumerApplications() throws InteractionManagerException
    {
        try
        {
            return selectApplicationByType(new URI(APPLICATION_TYPE_CONSUMER));
        } catch (URISyntaxException e)
        {
            throw new IllegalArgumentException(APPLICATION_TYPE_CONSUMER + " is not a valid URI: " + e.getMessage());
        }
    }

    
    /**
     * Gets a List of {@link org.egso.comms.nds.types.Application org.egso.comms.nds.types.Application}
     * of type broker. 
     * @return List of Application objects. 
     * @throws InteractionManagerException
     */
    public List<Application> getBrokerApplications() throws InteractionManagerException
    {
        try
        {
            return selectApplicationByType(new URI(APPLICATION_TYPE_BROKER));
        } catch (URISyntaxException e)
        {
            throw new IllegalArgumentException(APPLICATION_TYPE_BROKER + " is not a valid URI: " + e.getMessage());
        }
    }
    
    /**
     * Gets a List of {@link org.egso.comms.nds.types.Application org.egso.comms.nds.types.Application}
     * of type provider. 
     * @return List of Application objects. 
     * @throws InteractionManagerException
     */
    public List<Application> getProviderApplications() throws InteractionManagerException
    {
        try
        {
            return selectApplicationByType(new URI(APPLICATION_TYPE_PROVIDER));
        } catch (URISyntaxException e)
        {
            throw new IllegalArgumentException(APPLICATION_TYPE_PROVIDER + " is not a valid URI: " + e.getMessage());
        }
    }
    
    /**
     * Gets a list of {@link org.egso.comms.nds.types.Application org.egso.comms.nds.types.Application}
     * for all three roles.
     * @return
     * @throws InteractionManagerException
     */
    public List<Application> getAllRoles() throws InteractionManagerException
    {
        List<Application> allRoles = new Vector<Application>();
        allRoles.addAll(getConsumerApplications());
        allRoles.addAll(getBrokerApplications());
        allRoles.addAll(getProviderApplications());
        return allRoles;
    }
    
    /**
     * gets a list of Applications from the ECI
     * @param type
     * @return
     * @throws InteractionManagerException
     */
    private List<Application> selectApplicationByType(URI type) throws InteractionManagerException
    {
        if (type == null)
            throw new NullPointerException("Argument 'type' must not be null");
        List<Application> retList = new Vector<Application>();
        
        // lookup all apps
        ApplicationList apps = interactionManager.selectApplicationByType(type);
        retList = Arrays.asList(apps.getApplications());
        
        return retList;
    }
}