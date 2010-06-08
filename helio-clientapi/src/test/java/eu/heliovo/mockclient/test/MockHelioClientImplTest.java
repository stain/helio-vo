package eu.heliovo.mockclient.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eu.heliovo.clientapi.HelioClient;
import eu.heliovo.clientapi.model.infrastructure.HelioService;
import eu.heliovo.clientapi.query.HelioParamQuery;
import eu.heliovo.mockclient.MockHelioClient;


public class MockHelioClientImplTest {

	@Test
	public void testDefaultQuery() {
	    HelioClient helio = new MockHelioClient();		
	    HelioService[] services = helio.getServices();
	    assertEquals(3, services.length);
	}
	
	@Test
	public void testHecQuery() {
		HelioClient helio = new MockHelioClient();
	    HelioService[] services = helio.getServices();
	    assertEquals(3, services.length);	    
	    
	    HelioService hecService = helio.getService("hec");
	    assertNotNull(hecService);
	    assertTrue(hecService instanceof HelioParamQuery);
	    
	    HelioParamQuery hecService2 = helio.getService("hec", HelioParamQuery.class);
	    assertNotNull(hecService2);
	    assertTrue(hecService2 instanceof HelioService);
	    assertTrue(hecService2 instanceof HelioParamQuery);
	}
}
