package eu.heliovo.clientapi.query.syncquery.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import eu.heliovo.clientapi.query.HelioQueryResult;
import eu.heliovo.clientapi.query.MockWhereClauseFactoryBean;
import eu.heliovo.clientapi.query.WhereClauseFactoryBean;
import eu.heliovo.clientapi.query.paramquery.serialize.PQLSerializer;
import eu.heliovo.clientapi.query.syncquery.impl.MockSyncQueryService.MockQueryServicePort;
import eu.heliovo.clientapi.workerservice.HelioWorkerServiceHandler.Phase;

public class SyncQueryServiceImplTest {
    MockSyncQueryService service;
    
    @Before public void setup() {
        URL resultFile = getDefaultVoTable();
        MockQueryServicePort port = new MockQueryServicePort(resultFile, 0);
        service = new MockSyncQueryService(port);
        WhereClauseFactoryBean whereClauseFactoryBean = new MockWhereClauseFactoryBean(); 
        service.setWhereClauseFactoryBean(whereClauseFactoryBean);
        service.setQuerySerializer(new PQLSerializer());
        assertNotNull(service.getServiceName());
    }
	
	@Test public void testQuery() {
		HelioQueryResult result = service.query(Arrays.asList("2003-02-01T00:00:00", "2003-02-02T00:00:00"), Arrays.asList("2003-02-10T00:00:00", "2003-02-12T00:00:00"), Arrays.asList("instrument"), 100, 0, null);
		
		assertEquals(Phase.COMPLETED, result.getPhase());
		
		assertNotNull(result.asURL());
		
		assertNotNull(result.asVOTable());
		assertNotNull(result.toString());
		assertNotNull(result.asString());
		assertNotNull(result.getDestructionTime());
		assertTrue(1 < result.getExecutionDuration());
		
		// test invalid calls
		try {
			service.query(Arrays.asList("2003-02-01T00:00:00", "2003-02-02T00:00:00"), Arrays.asList("2003-02-10T00:00:00"), Arrays.asList("instrument"), 100, 0, null);
			fail("IllegalArgumentException expected.");
		} catch (IllegalArgumentException e) {
			// we're fine
		}
		try {
			service.query(Arrays.asList("2003-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00", "2003-02-12T00:00:00"), Arrays.asList("instrument"), 100, 0, null);
			fail("IllegalArgumentException expected.");
		} catch (IllegalArgumentException e) {
			// we're fine
		}
		try {
			service.query(Arrays.asList("2003-02-01T00:00:00", "2003-02-02T00:00:00"), Arrays.asList("2003-02-10T00:00:00", "2003-02-12T00:00:00"), Arrays.asList("instrument", "instrument2", "instrument3"), 100, 0, null);
			fail("IllegalArgumentException expected.");
		} catch (IllegalArgumentException e) {
			// we're fine
		}
		
	}
	
	@Test public void testTimeQuery() {
		HelioQueryResult result = service.timeQuery(Arrays.asList("2003-02-01T00:00:00", "2003-02-02T00:00:00"), Arrays.asList("2003-02-10T00:00:00", "2003-02-12T00:00:00"), Arrays.asList("instrument"), 100, 0);
		
		assertEquals(Phase.COMPLETED, result.getPhase());
		
		assertNotNull(result.asURL());
		
		assertNotNull(result.asVOTable());
		assertNotNull(result.toString());
		
		
		// test invalid calls
		try {
			service.timeQuery(Arrays.asList("2003-02-01T00:00:00", "2003-02-02T00:00:00"), Arrays.asList("2003-02-10T00:00:00"), Arrays.asList("instrument"), 100, 0);
			fail("IllegalArgumentException expected.");
		} catch (IllegalArgumentException e) {
			// we're fine
		}
		try {
			service.timeQuery(Arrays.asList("2003-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00", "2003-02-12T00:00:00"), Arrays.asList("instrument"), 100, 0);
			fail("IllegalArgumentException expected.");
		} catch (IllegalArgumentException e) {
			// we're fine
		}
		try {
			service.timeQuery(Arrays.asList("2003-02-01T00:00:00", "2003-02-02T00:00:00"), Arrays.asList("2003-02-10T00:00:00", "2003-02-12T00:00:00"), Arrays.asList("instrument", "instrument2", "instrument3"), 100, 0);
			fail("IllegalArgumentException expected.");
		} catch (IllegalArgumentException e) {
			// we're fine
		}
		
	}
	
	
	private URL getDefaultVoTable() {
		String resource = "/eu/heliovo/clientapi/query/resource/hec_goes_xray.xml";
		URL resultFile = getClass().getResource(resource);
		assertNotNull("resource not found: " + resource, resultFile);
		return resultFile;
	}
}
