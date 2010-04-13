package org.egso.common.services.broker;

/**
 * Interface to access the service registry on the broker.
 * 
 *  @author Marco Soldati
 */
public interface ServiceRegistry
{
    /**
     * Get a list of providers that implement a desired service.
     * @param serviceType name of the service to be accessed. 
     * In general this is the fully qualified Java interface name of a service.
     *  
     * @return List of provider ids, as they are in the ECI.
     */
    public String[] getServiceProvider(String serviceType);
}
