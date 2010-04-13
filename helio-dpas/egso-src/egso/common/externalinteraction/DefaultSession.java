package org.egso.common.externalinteraction;

import java.util.Iterator;
import java.util.List;
import org.egso.common.configuration.NamedConfigurationFactory;
import org.egso.common.logging.AbstractLogEnabled;
import org.egso.comms.eis.adapter.AdapterException;
import org.egso.comms.eis.adapter.Session;
import org.egso.comms.eis.adapter.SessionMetadata;
import org.apache.commons.configuration.Configuration;

/**
 * Configurable Implementation of the EIS session.
 * It uses a configuration file to lookup the service objects.
 * 
 * @author soldati
 * @created 10.02.2004
 * 
 */
public class DefaultSession extends AbstractLogEnabled implements Session 
{
    SessionMetadata sessionMetadata = null;
    
    
    /* (non-Javadoc)
     * @see org.egso.comms.eis.adapter.Session#getMetadata()
     */
    public SessionMetadata getMetadata() {
        return sessionMetadata;
    }
    
    /**
     * load the service configuration
     */
    private static final Configuration configuration = 
        NamedConfigurationFactory.addNamedConfiguration(
                "service", 
                "/org/egso/common/conf", 
                "common", 
                "services"); 
        
    /**
     * true if the session is setup and has not been closed.
     */
    private boolean valid = false;
    
    /**
     * Default avalonized session object. The getObject method 
     * uses the avalon serviceManager to get an instance of a specific object
     *
     */    
    public DefaultSession(SessionMetadata sessionMetadata)
    {
        this.sessionMetadata = sessionMetadata;
        valid = true;
    }
    
    /**
     * Returns an Object of the specified type. Also allows an Object to be passed
     * that can be used as a notification mechanism back to the client. cf
     * getAsynchCommand
     *
     * @param type          the type of Object to return
     * @param notifier      the notifier Object to use
     * @param notifierType  the type of the notifier Object
     * @return              the Object
     * @throws Exception
     */
    public Object getObject(Class<?> type, Object notifier, Class<?> notifierType)
    throws AdapterException
    {
        if (getLogger().isDebugEnabled())
        {
            getLogger().debug("Create new Object of type " + type);
        }
        
        Object service = findService(type.getName());
        
        // check if the service is a async service
        if (!(service instanceof AsyncService))
            throw new IllegalArgumentException(   "The implementation of the asynchronous service '" 
                    + service.getClass().getName() + "' must implement '" + AsyncService.class.getName() + "'");
        
        // set the notifier proxy on our new object
        ((AsyncService)service).setNotifier(notifier);
        
        return service;
    }
    
    /**
     * Returns an Object of the specified type.
     *
     * @param type         the type of object to return
     * @return             the Object
     * @throws Exception
     */
    public Object getObject(Class<?> type)
    throws AdapterException 
    {
        if (getLogger().isDebugEnabled())
        {
            getLogger().debug("Create new Object of type " + type);
        }
        
        Object service = findService(type.getName());
        return service;
    }
    
    /**
     * Returns the types of object supported by this session.
     * @see org.egso.comms.eis.adapter.Session#getTypes()
     * 
     * @return the object types
     * @throws AdapterException
     */
    public Class<?>[] getTypes() throws AdapterException
    {
        throw new AdapterException("Method not supported yet.");
    }

    /**
     * Returns the types of object supported by this session for the
     * specified notifier type.
     * @see org.egso.comms.eis.adapter.Session#getTypes(java.lang.Class)
     * 
     * @param notifierType the type of the notifier
     * @return the object types
     * @throws AdapterException
     */
    public Class<?>[] getTypes(Class<?> arg0) throws AdapterException
    {
        throw new AdapterException("Method not supported yet.");
    }
 
    /**
     * return true if the session has not been closed previously.   
     * @see org.egso.comms.eis.adapter.Session#isValid()
     */
    public boolean isValid()
    {
        return valid;
    }
    
    /**
     * close the session and release any resources
     * @see org.egso.comms.eis.adapter.Session#close()
     */
    public void close()
    {
        valid = false;
    }
    
    
    /**
     * Check the service configuration for a service name entry 
     * and try to load the class referenced by this name. 
     * @param serviceName name of the service to load.
     * @return the instanciated service class.
     * @throws AdapterException if the service name is unknown or if the class cannot be loaded.
     */
    @SuppressWarnings("unchecked")
    private Object findService(String serviceName) throws AdapterException
    {
        List<String> services = configuration.getList("services.service.name");
        String serviceImplName = null;
        int i = 0; // counter point to the current service tag
        
        // loop through all service entries and check the names
        for (String s:services)
        {
            if (serviceName.equals(s))
            {
                serviceImplName = configuration.getString("services.service(" + i + ").impl");
                if (serviceImplName == null)
                    throw new AdapterException("Unable to find tag 'impl' for '" + serviceName + "' in configuration.");
                break;
            }
            i++;
        }
        
        if (serviceImplName == null)
            throw new AdapterException("Could not find service " + serviceName + " in configuration.");

        try
        {
            Class<?> service = Class.forName(serviceImplName);
            return service.newInstance();
        }
        catch (ClassNotFoundException e)
        {
            throw new AdapterException("Unable to find class " + serviceImplName 
                    + " for service " + serviceName + ": " + e.getMessage(), e);
        }
        catch (IllegalAccessException e)
        {
            throw new AdapterException("Unable to instantiate class " + serviceImplName 
                    + " for service " + serviceName + ": " + e.getMessage(), e);
        }
        catch (InstantiationException e)
        {
            throw new AdapterException("Unable to instantiate class " + serviceImplName 
                    + " for service " + serviceName + ": " + e.getMessage(), e);
        }
    }
}
