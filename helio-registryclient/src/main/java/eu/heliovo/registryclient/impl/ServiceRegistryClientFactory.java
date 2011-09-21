package eu.heliovo.registryclient.impl;

import eu.heliovo.registryclient.ServiceRegistryClient;

/**
 * Factory to get the registry DAO
 * @author MarcoSoldati
 *
 */
public class ServiceRegistryClientFactory {
    /**
     * The singleton instance
     */
    private static final ServiceRegistryClientFactory instance = new ServiceRegistryClientFactory();
    
    /**
     * The default DAO to use if none has been set.
     */
    private final static Class<? extends ServiceRegistryClient> DEFAULT_REGISTRY_DAO = HelioRemoteServiceRegistryClient.class;
    //private final static Class<? extends ServiceRegistryClient> DEFAULT_REGISTRY_DAO = LocalServiceRegistryClient.class;
    
    /**
     * Get the singleton instance of this factory
     * @return the factory instance
     */
    public static synchronized ServiceRegistryClientFactory getInstance() {
        return instance;
    }
    
    /**
     * The registry client to use
     */
    private ServiceRegistryClient serviceRegistryClient;

    /**
     * Hide the constructor
     */
    private ServiceRegistryClientFactory() {
    }
    
    /**
     * Get the service registry client to access the registry.
     * @return the registry client
     */
    public synchronized ServiceRegistryClient getServiceRegistryClient() {
        if (this.serviceRegistryClient == null) {
            try {
                this.serviceRegistryClient = DEFAULT_REGISTRY_DAO.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("Unable to instantiate " + DEFAULT_REGISTRY_DAO.getName() + ". Cause: " + e.getMessage(), e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Unable to instantiate " + DEFAULT_REGISTRY_DAO.getName() + ". Cause: " + e.getMessage(), e);
            }
        }
        return serviceRegistryClient;
    }
    
    /**
     * Set the ServiceRegistryClient. This is only needed for testing.
     * @param serviceRegistryClient
     */
    public synchronized void setServiceRegistryClient(ServiceRegistryClient serviceRegistryClient) {
//        if (this.helioServiceRegistryDao != null && !this.helioServiceRegistryDao.equals(helioServiceRegistryDao)) {
//            throw new IllegalArgumentException("Argument 'helioServiceRegistryDao' must not be changed once it has been set.");
//        }
        this.serviceRegistryClient = serviceRegistryClient;
    }
}
