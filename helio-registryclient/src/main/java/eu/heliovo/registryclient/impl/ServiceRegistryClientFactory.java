package eu.heliovo.registryclient.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import eu.heliovo.registryclient.ServiceRegistryClient;
import eu.heliovo.shared.props.HelioFileUtil;

/**
 * Factory to get the registry DAO
 * @author MarcoSoldati
 *
 */
public class ServiceRegistryClientFactory {
    /**
     * the properties file of the registry.
     */
    private static final String REGISTRY_FILE_NAME = "registry.properties";
    
    /**
     * Key of the registry class.
     */
    private static final String REGISTRY_CLASS_KEY = "eu.heliovo.registryclient.default_class";
    
    /**
     * The singleton instance
     */
    private static final ServiceRegistryClientFactory instance = new ServiceRegistryClientFactory();
    
    /**
     * The default DAO to use if none has been set.
     */
    private final static Class<? extends ServiceRegistryClient> DEFAULT_REGISTRY_DAO = getServiceRegistryClientClass();
    //private final static Class<? extends ServiceRegistryClient> DEFAULT_REGISTRY_DAO = LocalServiceRegistryClient.class;
    
    /**
     * Get the singleton instance of this factory
     * @return the factory instance
     */
    public static synchronized ServiceRegistryClientFactory getInstance() {
        return instance;
    }
    
    /**
     * Check if a service registry client class has been defined in the properties file.
     * @return the registered or the default service registry client class.
     */
    private static Class<? extends ServiceRegistryClient> getServiceRegistryClientClass() {
        File propertiesDir = HelioFileUtil.getHelioHomeDir("registry");
        File propertiesFile = new File(propertiesDir, REGISTRY_FILE_NAME);
        Class<? extends ServiceRegistryClient> registryClientClass = HelioRemoteServiceRegistryClient.class;
        
        if (propertiesFile.exists()) {
            Properties props = new Properties();
            try {
                props.load(new FileReader(propertiesFile));
            } catch (FileNotFoundException e) {
                // ignore
            } catch (IOException e) {
                // ignore
            }
            if (props.containsKey(REGISTRY_CLASS_KEY)) {
                String className = props.getProperty(REGISTRY_CLASS_KEY);
                try {
                    @SuppressWarnings("unchecked")
                    Class<? extends ServiceRegistryClient> classInstance = (Class<? extends ServiceRegistryClient>) Class.forName(className);
                    registryClientClass = classInstance;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        
        
        return registryClientClass;
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
