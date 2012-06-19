package eu.heliovo.registryclient.impl;

import java.util.Collection;

import eu.heliovo.registryclient.AccessInterface;
import eu.heliovo.registryclient.ServiceCapability;
import eu.heliovo.registryclient.ServiceDescriptor;

/**
 * Demo class to read the data from the remote service registry client.
 * @author MarcoSoldati
 *
 */
public class HelioRemoteServiceRegistryClientDemo {


    private static final String REGISTRY_TABLE_FORMAT = "| %-80s | %-50s | %-20s | %-90s |";
    
    public static void main(String[] args) {
        HelioRemoteServiceRegistryClient serviceRegistryClient = new HelioRemoteServiceRegistryClient("demo");
        serviceRegistryClient.init();
        
        System.out.println("Known capabilities");
        System.out.println("-------------------------------------------------------------------------------------------------------------------");
        Collection<ServiceCapability> vals = ServiceCapability.values();
        for (ServiceCapability serviceCapability : vals) {
            System.out.println(String.format("| %-50s | %-50s |", serviceCapability.getStandardId(), serviceCapability.getName()));
        }
        
        System.out.println("-------------------------------------------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Registry content summary");
        System.out.println("-------------------------------------------------------------------------------------------------------------------");
        System.out.println(String.format(REGISTRY_TABLE_FORMAT, "service", "capability", "xsi:Type", "url"));
        ServiceDescriptor[] serviceDescriptors = serviceRegistryClient.getAllServiceDescriptors();
        for (ServiceDescriptor serviceDescriptor : serviceDescriptors) {
            AccessInterface[] soapServices = serviceRegistryClient.getAllEndpoints(serviceDescriptor, null, null);
            for (AccessInterface accessInterface : soapServices) {
                System.out.println(String.format(REGISTRY_TABLE_FORMAT, serviceDescriptor.getName(), accessInterface.getCapability(), accessInterface.getInterfaceType(), accessInterface.getUrl()));
            }
        }

    }
}
