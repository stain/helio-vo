package eu.heliovo.registryclient.impl;


import static uk.ac.starlink.registry.RegistryRequestFactory.keywordSearch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;

import uk.ac.starlink.registry.AbstractRegistryClient;
import uk.ac.starlink.registry.BasicCapability;
import uk.ac.starlink.registry.BasicResource;
import uk.ac.starlink.registry.SoapClient;
import eu.heliovo.registryclient.AccessInterface;
import eu.heliovo.registryclient.AccessInterfaceType;
import eu.heliovo.registryclient.HelioServiceName;
import eu.heliovo.registryclient.RegistryProperties;
import eu.heliovo.registryclient.ServiceCapability;
import eu.heliovo.registryclient.ServiceDescriptor;
import eu.heliovo.registryclient.ServiceResolutionException;
import eu.heliovo.shared.util.FileUtil;

/**
 * A registry client to access the HELIO Search Registry in order to find the
 * appropriate services.
 * @author donal k fellows
 * @author marco soldati at fhnw ch
 */
public class HelioRemoteServiceRegistryClient extends AbstractHelioServiceRegistryClient {
    /**
     * The logger.
     */
	private static final Logger _LOGGER = Logger.getLogger(HelioRemoteServiceRegistryClient.class);

	/** The default address of the registry. */
	//public static final String REGISTRY_AT_MSSL = "http://msslxw.mssl.ucl.ac.uk:8080/helio_registry/services/RegistryQueryv1_0";
	public static final String REGISTRY_AT_MSSL = "http://msslkz.mssl.ucl.ac.uk/helio_registry/services/RegistryQueryv1_0";

	/**
	 * Name of the remote registry location
	 */
    private static final String REGISTRY_LOCATION = "eu.heliovo.registryclient.remote_registry_location";
	
	/**
	 * Client stub to the registry.
	 */
	private AbstractRegistryClient<BasicResource> registry;

    /**
     * Optional registry persister bean. 
     */
    private RegistryPersister registryPersister;

    /**
     * Properties of the registry.
     */
    private RegistryProperties registryProperties;
    
    /**
     * Filter the registry entries.
     */
    private RegistryEntryFilter registryFilter;

	/**
	 * Create the registry impl and initialize it accordingly.
	 * @param appId id of the application. Used to find the configuration directory
	 */
	public HelioRemoteServiceRegistryClient(String appId) {
        this.registryProperties = new DefaultRegistryProperties(appId);
        
        // create the registry filter
        registryFilter = new BlacklistRegistryFilter();
        ((BlacklistRegistryFilter)registryFilter).setRegistryProperties(registryProperties);
        ((BlacklistRegistryFilter)registryFilter).init();
	}
	
	/**
	 * Set the URL to use to access the registry.
	 * @param url the URL to use.
	 */
	public void setRegistryURL(URL url)  {
		registry = new HelioBasicRegistryClient(new SoapClient(url));
	}

	/**
	 * Delete the full content of the registry and repopulate it
	 */
	public void reinit() {
	    this.instanceDescriptors.clear();
	    this.serviceDescriptors.clear();
	    init();
    }
	
    /**
     * Update the service descriptors with all known services. 
     * @throws ServiceResolutionException
     */
    public void init() throws ServiceResolutionException {
        setRegistryURL(getRegistryLocation());
        List<BasicResource> allServices;
        try {
            allServices = registry.getResourceList(keywordSearch(new String[] { "*" }, false));
            storeRegistryLocally(allServices);
        } catch (IOException e) {
            _LOGGER.warn("Unable to access remote registry: " + e.getMessage() + ". Trying locally persisted registry.", e);
            allServices = getFromLocallyStoredRegistry();
        }
        
        // loop through all services and qualify them
        for (BasicResource r : allServices) {
            if (_LOGGER.isTraceEnabled()) {
                _LOGGER.trace("found match: " + r.getIdentifier() + " (" + r.getTitle() + ")");
            }
            String description = getDescription(r);
            if (r.getIdentifier() != null && r.getTitle() != null) {
                HelioServiceName serviceName = HelioServiceName.register(r.getTitle(), r.getIdentifier());
                ServiceDescriptor serviceDescriptor = new GenericServiceDescriptor(serviceName, description, (ServiceCapability)null);
                serviceDescriptor = registerServiceDescriptor(serviceDescriptor);
                // register capabilities
                for (BasicCapability c : r.getCapabilities()) {
                    ServiceCapability cap = ServiceCapability.findServiceCapabilityByStandardId(c.getStandardId());
                    //System.out.println(c.getStandardId() + ", " + c.getXsiType() + ", " +  c.getDescription() + ", " + c.getVersion() + ", "+ c.getAccessUrl());
                    if (cap == null) {
                        if (c.getStandardId() != null) {
                            cap = ServiceCapability.register(c.getStandardId(), c.getStandardId());
                        } else {
                            cap = ServiceCapability.UNDEFINED;
                        }
                    }
                    
                    if (registryFilter == null || !registryFilter.filterAccessUrl(c.getAccessUrl())) {
                        AccessInterfaceType accessInterfaceType = AccessInterfaceType.findInterfaceTypeByXsiType(c.getXsiType());
                        
                        if (accessInterfaceType == null) {
                            accessInterfaceType = AccessInterfaceType.register(c.getXsiType(), c.getXsiType());
                        }
                        try {
                            AccessInterface accessInterface = new AccessInterfaceImpl(accessInterfaceType, cap, new URL(c.getAccessUrl()));
                            registerServiceInstance(serviceDescriptor, accessInterface);
                        } catch (MalformedURLException e) {
                            _LOGGER.warn("Unable to register URL " + c.getAccessUrl() + " for " + serviceDescriptor + "::" + cap + ": " + e.getMessage(), e);
                        }
                    }
                }
            }
        }
    }


    private void storeRegistryLocally(List<BasicResource> allServices) {
        if (this.registryPersister != null) {
            try {
                this.registryPersister.persistRegistry(allServices);
            } catch (IOException e) {
                _LOGGER.warn("Unable to persist registry: " + e.getMessage(), e);
            }
        }
    }

    private List<BasicResource> getFromLocallyStoredRegistry() {
        if (this.registryPersister != null) {
            try {
                return this.registryPersister.loadRegistry();
            } catch (IOException e) {
                _LOGGER.warn("Failed to load locally persisted registry: " + e.getMessage(), e);
            }
        } else {
            _LOGGER.warn("No RegistryPersister has been registered with this bean.");            
        }
        throw new ServiceResolutionException("Unable to load local registry. See logs for more information");
    }
    
    /**
     * Check if a service registry client class has been defined in the properties file.
     * @return the registered or the default service registry client class.
     */
    private URL getRegistryLocation() {
        if (registryProperties.containsKey(REGISTRY_LOCATION)) {
            return FileUtil.asURL(registryProperties.getProperty(REGISTRY_LOCATION));
        } else {
            return FileUtil.asURL(REGISTRY_AT_MSSL);
        }
    }
    
    /**
     * Extract a description string from a basic resource.
     * @param r the resource to read
     * @return the description as a multiline string or null if not description.
     */
    private String getDescription(BasicResource r) {
        StringBuilder desc = new StringBuilder();
        if (r.getTitle() != null) {
            desc.append(r.getTitle()).append("\n");            
        }
        if (r.getReferenceUrl() != null) {
            desc.append("reference url: ").append(r.getReferenceUrl()).append("\n");            
        }
        if (r.getPublisher() != null) {
            desc.append("publisher: ").append(r.getPublisher()).append("\n");            
        }
        if (r.getContact() != null) {
            desc.append("contact: ").append(r.getContact()).append("\n");            
        }
        if (desc.length() == 0) {
            return null;
        }
        // remove trailing new line.
        desc.delete(desc.length()-1, desc.length());
        return desc.toString();
    }

    /**
     * @return the registryPersister
     */
    public RegistryPersister getRegistryPersister() {
        return registryPersister;
    }

    /**
     * @param registryPersister the registryPersister to set
     */
    public void setRegistryPersister(RegistryPersister registryPersister) {
        this.registryPersister = registryPersister;
    }
}
