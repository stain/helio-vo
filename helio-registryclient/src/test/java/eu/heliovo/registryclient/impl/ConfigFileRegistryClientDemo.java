package eu.heliovo.registryclient.impl;

import static org.junit.Assert.assertNotNull;
import eu.heliovo.registryclient.AccessInterface;
import eu.heliovo.registryclient.ServiceDescriptor;

/**
 * Unit test for the {@link LocalHelioServiceRegistryClient}.
 */
public class ConfigFileRegistryClientDemo {

    
    public static void main(String[] args) {
        ConfigFileServiceRegistryClient helioRegistry = new ConfigFileServiceRegistryClient();
        helioRegistry.init();
        ServiceDescriptor[] serviceDescriptors = helioRegistry.getAllServiceDescriptors();
        for (ServiceDescriptor helioServiceDescriptor : serviceDescriptors) {
            AccessInterface[] endpoints = helioRegistry.getAllEndpoints(helioServiceDescriptor, null, null);
            assertNotNull(endpoints);
            for (AccessInterface url : endpoints) {
                assertNotNull(url);
                System.out.println(url);
            }
        }
    }    
}
