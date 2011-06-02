package eu.heliovo.registryclient.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import eu.heliovo.registryclient.AccessInterface;
import eu.heliovo.registryclient.AccessInterfaceType;
import eu.heliovo.registryclient.ServiceCapability;
import eu.heliovo.registryclient.ServiceDescriptor;

/**
 * Unit test for the {@link LocalHelioServiceRegistryClient}.
 */
public class LocalHelioRegistryImplTest {

    private AbstractHelioServiceRegistryClient helioRegistry;
    
    
    @Before public void setUp() {
        helioRegistry = new LocalHelioServiceRegistryClient();
        //HelioRegistryDaoFactory.getInstance().setHelioServiceRegistryDao(helioRegistry);
    }
    
	@Test public void initialize() {
		assertNotNull(helioRegistry);
		ServiceDescriptor[] serviceDescriptors = helioRegistry.getAllServiceDescriptors();
		assertNotNull(serviceDescriptors);
		for (ServiceDescriptor helioServiceDescriptor : serviceDescriptors) {
			AccessInterface[] endpoints = helioRegistry.getAllEndpoints(helioServiceDescriptor, null, null);
			assertNotNull(endpoints);
			for (AccessInterface url : endpoints) {
				assertNotNull(url);
			}
		}
	}
	
	/**
	 * Test {@link LocalHelioServiceRegistryDao#registerServiceDescriptor(GenericServiceDescriptor)}.
	 */
	@Test public void testRegisterServiceDescriptor() {
		// register new service
		ServiceDescriptor descriptor = new GenericServiceDescriptor("test", "test service", "a test service", ServiceCapability.UNDEFINED);
		ServiceDescriptor descriptor2 = helioRegistry.registerServiceDescriptor(descriptor);
		assertSame(descriptor, descriptor2);
		
		assertNotNull(helioRegistry.getServiceDescriptor("test"));
		
		// register a service with the same name and type
		descriptor2 = new GenericServiceDescriptor("test", "test service", "a test service", ServiceCapability.UNDEFINED);
		ServiceDescriptor descriptor3 = helioRegistry.registerServiceDescriptor(descriptor2);
		assertNotSame(descriptor2, descriptor3);		
	}
	
	/**
	 * Test {@link LocalHelioServiceRegistryDao#registerServiceInstance(ServiceDescriptor, URL...)}
	 * @throws Exception if anything goes wrong
	 */
	@Test public void testRegisterServiceInstanceDescriptor() throws Exception {
		
		// create service descriptor
		ServiceDescriptor descriptor = new GenericServiceDescriptor("test2", "test service", "a test service", ServiceCapability.UNDEFINED);
		
		// create service instance descriptor
		helioRegistry.registerServiceInstance(descriptor, ServiceCapability.UNDEFINED, new AccessInterfaceImpl(AccessInterfaceType.SOAP_SERVICE, new URL("http://www.example.com/test.wsdl")));
		
		assertNotNull(helioRegistry.getServiceDescriptor("test2"));
		
		// register a service with the same name and type
		assertFalse(helioRegistry.registerServiceInstance(descriptor, ServiceCapability.UNDEFINED, new AccessInterfaceImpl(AccessInterfaceType.SOAP_SERVICE, new URL("http://www.example.com/test.wsdl"))));
	}
	
	/**
	 * Test method {@link LocalHelioServiceRegistryDao#getBestEndpoint(GenericServiceDescriptor)}.
	 * @throws Exception in case of an error
	 */
	@Test public void testGetBestEndpoint() throws Exception {
		ServiceDescriptor descriptor = new GenericServiceDescriptor("test3", "test service", "a test service", ServiceCapability.UNDEFINED);
		assertNotNull(helioRegistry.registerServiceDescriptor(descriptor));
		assertTrue(helioRegistry.registerServiceInstance(descriptor, ServiceCapability.UNDEFINED, new AccessInterfaceImpl(AccessInterfaceType.SOAP_SERVICE, new URL("http://www.example.com/test2.wsdl"))));
		assertTrue(helioRegistry.registerServiceInstance(descriptor, ServiceCapability.UNDEFINED, new AccessInterfaceImpl(AccessInterfaceType.SOAP_SERVICE, new URL("http://www.example.com/test3.wsdl"))));
		assertTrue(helioRegistry.registerServiceInstance(descriptor, ServiceCapability.UNDEFINED, new AccessInterfaceImpl(AccessInterfaceType.SOAP_SERVICE, new URL("http://www.example.com/test1.wsdl"))));
		
		AccessInterface bestEndPoint = helioRegistry.getBestEndpoint(descriptor, ServiceCapability.UNDEFINED, AccessInterfaceType.SOAP_SERVICE);
		assertNotNull(bestEndPoint);
		assertEquals(new URL("http://www.example.com/test2.wsdl"), bestEndPoint.getUrl());
		
		bestEndPoint = helioRegistry.getBestEndpoint("test3", ServiceCapability.UNDEFINED, AccessInterfaceType.SOAP_SERVICE);
		assertNotNull(bestEndPoint);
		assertEquals(new URL("http://www.example.com/test2.wsdl"), bestEndPoint.getUrl());		
		
		AccessInterface[] allEndpoints = helioRegistry.getAllEndpoints(descriptor, null, null);
		URL[] bestEndPoints = new URL[allEndpoints.length];
		for (int i = 0; i < allEndpoints.length; i++) {
            bestEndPoints[i] = allEndpoints[i].getUrl();
        }
		assertNotNull(bestEndPoints);
		assertArrayEquals(new URL[] {new URL("http://www.example.com/test2.wsdl"), new URL("http://www.example.com/test3.wsdl"), new URL("http://www.example.com/test1.wsdl")}, bestEndPoints);
	}
}
