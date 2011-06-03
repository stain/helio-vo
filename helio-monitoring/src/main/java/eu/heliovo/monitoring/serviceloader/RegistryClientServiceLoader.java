package eu.heliovo.monitoring.serviceloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import eu.heliovo.monitoring.model.ModelFactory;
import eu.heliovo.monitoring.model.Service;
import eu.heliovo.registryclient.AccessInterface;
import eu.heliovo.registryclient.AccessInterfaceType;
import eu.heliovo.registryclient.ServiceDescriptor;
import eu.heliovo.registryclient.ServiceRegistryClient;
import eu.heliovo.registryclient.impl.ServiceRegistryClientFactory;

/**
 * Service loader that uses the helio-registryclient to load the
 * registered web services.
 * @author MarcoSoldati
 *
 */
public class RegistryClientServiceLoader implements ServiceLoader {
    /**
     * the logger
     */
    private static final Logger _LOGGER = Logger.getLogger(RegistryClientServiceLoader.class);
    
    @Override
    public Set<Service> loadServices() {
        final Set<Service> services = new HashSet<Service>();
        
        // get a pointer to the service registry client
        ServiceRegistryClient serviceRegistryClient = ServiceRegistryClientFactory.getInstance().getServiceRegistryClient();
        
        ServiceDescriptor[] serviceDescriptors = serviceRegistryClient.getAllServiceDescriptors();
        for (ServiceDescriptor serviceDescriptor : serviceDescriptors) {
            AccessInterface[] soapServices = serviceRegistryClient.getAllEndpoints(serviceDescriptor, null, null);
            if (soapServices != null && soapServices.length > 0) {
                try {
                    services.add(ModelFactory.newService(serviceDescriptor.getName(), serviceDescriptor.getLabel(), new URL(soapServices[0].getUrl().toExternalForm() + "?wsdl")));
                } catch (MalformedURLException e) {
                    _LOGGER.warn("illegal URL: " + e.getMessage(), e);
                }
            }
        }
        return services;
    }
}
