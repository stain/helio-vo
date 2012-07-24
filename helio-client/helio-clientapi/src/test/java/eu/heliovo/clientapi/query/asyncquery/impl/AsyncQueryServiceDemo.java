package eu.heliovo.clientapi.query.asyncquery.impl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogRecord;

import org.springframework.context.support.GenericXmlApplicationContext;

import eu.heliovo.clientapi.HelioClient;
import eu.heliovo.clientapi.query.HelioQueryResult;
import eu.heliovo.clientapi.query.QueryService;
import eu.heliovo.clientapi.query.QueryType;
import eu.heliovo.clientapi.utils.DebugUtils;
import eu.heliovo.clientapi.workerservice.JobExecutionException;
import eu.heliovo.registryclient.AccessInterface;
import eu.heliovo.registryclient.HelioServiceName;
import eu.heliovo.registryclient.ServiceCapability;

/**
 * Query program for the async query.
 * @author MarcoSoldati
 *
 */
public class AsyncQueryServiceDemo {
    /**
     * The main method
     * @param args will be ignored
     * @throws Exception
     */
	public static void main(String[] args) throws Exception {
	    // init the system
        GenericXmlApplicationContext context = new GenericXmlApplicationContext("classpath:spring/clientapi-main.xml");
        
	    HelioClient client = (HelioClient) context.getBean("helioClient");
	    //client.getServiceInstance(client, HelioServiceName.ICS, ServiceCapability.ASYNC_QUERY_SERVICE, null);
	    
	    //DebugUtils.enableDump();
//	    long start = System.currentTimeMillis();
//	    testLongRunningService(client, HelioServiceName.DPAS, Arrays.asList("2003-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00"), Arrays.asList("SOHO__CDS"), "", (String)null, new AccessInterfaceImpl(AccessInterfaceType.SOAP_SERVICE, ServiceCapability.ASYNC_QUERY_SERVICE, HelioFileUtil.asURL("http://localhost:8080/helio-queryservice-server/HelioLongQueryService?wsdl")));
//	    long end = System.currentTimeMillis();
//	    System.out.println("query took " + (end - start) + "ms.");
//	    start = System.currentTimeMillis();
//	    testLongRunningService(client, HelioServiceName.DPAS, Arrays.asList("1890-10-20T20:30:56"), Arrays.asList("2009-10-20T20:30:56"), Arrays.asList("HXT"), "helio", (String)null, new AccessInterfaceImpl(AccessInterfaceType.SOAP_SERVICE, ServiceCapability.ASYNC_QUERY_SERVICE, HelioFileUtil.asURL("http://localhost:8080/helio-queryservice-server/HelioLongQueryService?wsdl")));
//	    end = System.currentTimeMillis();
//	    System.out.println("query took " + (end - start) + "ms.");
	    testLongRunningService(client, HelioServiceName.DPAS, Arrays.asList("2003-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00"), Arrays.asList("SOHO__CDS"), "");
		testLongRunningService(client, HelioServiceName.ICS, Arrays.asList("2003-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00"), Arrays.asList("instrument"));
		testLongRunningService(client, HelioServiceName.HEC, Arrays.asList("2003-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00"), Arrays.asList("kso_halpha_flare"));
//		testLongRunningService(client, HelioServiceName.HEC, Arrays.asList("2003-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00"), Arrays.asList("cactus_soho_flow"), null);
//		testLongRunningService(client, HelioServiceName.HEC, Arrays.asList("2003-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00"), Arrays.asList("cactus_soho_flow"), null);
//		testLongRunningService(client, HelioServiceName.HEC, Arrays.asList("1900-01-01T00:00:00"), Arrays.asList("2020-12-31T00:00:00"), Arrays.asList("hec_catalogue"), null);
//		testLongRunningService(client, HelioServiceName.HEC, Arrays.asList("2003-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00"), Arrays.asList("noaa_active_region_summary"), null);
//		testLongRunningService(client, HelioServiceName.HEC, Arrays.asList("2007-03-01T00:00:00"), Arrays.asList("2007-03-31T00:00:00"), Arrays.asList("goes_xray_flare"), null);
//		testLongRunningService(client, HelioServiceName.HEC, Arrays.asList("2007-03-01T00:00:00"), Arrays.asList("2007-03-31T00:00:00"), Arrays.asList("goes_xray_flare"), null);
//		testLongRunningService(client, HelioServiceName.HEC, Arrays.asList("2007-03-01T00:00:00"), Arrays.asList("2007-03-31T00:00:00"), Arrays.asList("goes_xray_flare"), null);
		testLongRunningService(client, HelioServiceName.HEC, Arrays.asList("2007-03-01T00:00:00"), Arrays.asList("2007-03-31T00:00:00"), Arrays.asList("goes_xray_flare"));
		testLongRunningService(client, HelioServiceName.ILS, Arrays.asList("2003-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00"), Arrays.asList("trajectories"));
		testLongRunningService(client, HelioServiceName.ICS, Arrays.asList("2003-02-01T00:00:00", "2005-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00", "2005-02-01T00:00:00"), Arrays.asList("instrument"));
		testLongRunningService(client, HelioServiceName.UOC, Arrays.asList("2003-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00"), Arrays.asList("test"));
	    testLongRunningService(client, HelioServiceName.DES, Arrays.asList("2007-07-10T12:00:00"), Arrays.asList("2007-07-11T12:00:00"), Arrays.asList("ACE"), "VAR,ACE:N:/16.0:1200.0:60.0", (AccessInterface[])null);	//DebugUtils.disableDump();
	    DebugUtils.disableDump();
	}
	
	/**
	 * Test the timeQuery method
	 * @param serviceName
	 * @param startTime
	 * @param endTime
	 * @param from
	 * @param accessInterfaces
	 */
	private static synchronized void testLongRunningService(HelioClient client, HelioServiceName serviceName, List<String> startTime, List<String> endTime, List<String> from, AccessInterface... accessInterfaces) {
		System.out.println("--------------------" + serviceName.getServiceName() + "--------------------");
		try {
		    QueryService queryService = (QueryService) client.getServiceInstance(serviceName, null, ServiceCapability.ASYNC_QUERY_SERVICE);
		    queryService.setQueryType(QueryType.ASYNC_QUERY);
			HelioQueryResult result = queryService.timeQuery(startTime, endTime, from, 100, 0);

			System.out.println(result);
			if (result != null) {
				System.out.println("Phase: " + result.getPhase());
				Thread.sleep(1000);
				System.out.println("Phase: " + result.getPhase());
				System.out.println("Result URL: " + result.asURL());
				System.out.println("Result VOTable: " + result.asVOTable());
				System.out.println("Result VOTable as String: " + result.asString());
				StringBuilder sb = new StringBuilder("User log: ");
				for (LogRecord logRecord : result.getUserLogs()) {
					if (sb.length() > 0) {
						sb.append(",\n");
					}
					sb.append(logRecord.getMessage());
				}
				System.out.println(sb.toString());
			}
			System.out.println();
	         
            //StarTable[] table = STILUtils.read(result.asURL());

		} catch (Exception e) {
			System.err.println("Error occured: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Test the query methods
	 * @param serviceName
	 * @param startTime
	 * @param endTime
	 * @param from
	 * @param where
	 * @param accessInterfaces
	 */
	private static synchronized void testLongRunningService(HelioClient client, HelioServiceName serviceName, List<String> startTime, List<String> endTime, List<String> from, String where, AccessInterface ... accessInterfaces) {
		System.out.println("--------------------" + serviceName + "--------------------");
		try {
	        QueryService queryService = (QueryService) client.getServiceInstance(serviceName, null, ServiceCapability.ASYNC_QUERY_SERVICE);
	        queryService.setQueryType(QueryType.ASYNC_QUERY);

			HelioQueryResult result = queryService.query(startTime, endTime, from, where, 100, 0, null);
			
			System.out.println(result);
			if (result != null) {
			    try {
    				System.out.println("Phase: " + result.getPhase());
    				System.out.println("Result URL: " + result.asURL(30, TimeUnit.SECONDS));
    				System.out.println("Result VOTable: " + result.asVOTable());
//    				System.out.println(result.asString());
			    } catch (JobExecutionException e) {
			        e.printStackTrace();
                }
			    // try to display logs
				StringBuilder sb = new StringBuilder("User log: ");
				for (LogRecord logRecord : result.getUserLogs()) {
					if (sb.length() > 0) {
						sb.append(", ");
					}
					sb.append(logRecord.getMessage());
				}
				System.out.println(sb.toString());
				
			}
			System.out.println();
		} catch (Exception e) {
			System.err.println("Error occured: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
}
