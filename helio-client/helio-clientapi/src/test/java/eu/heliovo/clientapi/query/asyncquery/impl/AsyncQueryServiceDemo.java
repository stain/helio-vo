package eu.heliovo.clientapi.query.asyncquery.impl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogRecord;

import eu.heliovo.clientapi.query.HelioQueryResult;
import eu.heliovo.clientapi.query.asyncquery.AsyncQueryService;
import eu.heliovo.clientapi.utils.DebugUtils;
import eu.heliovo.clientapi.workerservice.JobExecutionException;
import eu.heliovo.registryclient.AccessInterface;
import eu.heliovo.registryclient.AccessInterfaceType;
import eu.heliovo.registryclient.HelioServiceName;
import eu.heliovo.registryclient.ServiceCapability;
import eu.heliovo.registryclient.impl.AccessInterfaceImpl;
import eu.heliovo.shared.props.HelioFileUtil;

public class AsyncQueryServiceDemo {
	public static void main(String[] args) throws Exception {
//	    DebugUtils.enableDump();
//	    testLongRunningService(HelioServiceName.DPAS, Arrays.asList("2003-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00"), Arrays.asList("SOHO__CDS"), "", (String)null, new AccessInterfaceImpl(AccessInterfaceType.SOAP_SERVICE, ServiceCapability.ASYNC_QUERY_SERVICE, HelioFileUtil.asURL("http://localhost:8080/helio-dpas/HelioLongQueryService?wsdl")));
//	    testLongRunningService(HelioServiceName.DPAS, Arrays.asList("2003-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00"), Arrays.asList("SOHO__CDS"), "", (String)null);
	    DebugUtils.disableDump();
//		testLongRunningService(HelioServiceName.ICS, Arrays.asList("2003-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00"), Arrays.asList("instrument"), null);
//		testLongRunningService(HelioServiceName.HEC, Arrays.asList("2003-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00"), Arrays.asList("goes_sxr_flare"), null);
//		testLongRunningService(HelioServiceName.ILS, Arrays.asList("2003-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00"), Arrays.asList("trajectories"), null);
//		testLongRunningService(HelioServiceName.ICS, Arrays.asList("2003-02-01T00:00:00", "2005-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00", "2005-02-01T00:00:00"), Arrays.asList("instrument"), null);
//		testLongRunningService(HelioServiceName.UOC, Arrays.asList("2003-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00"), Arrays.asList("test"), null);
		testLongRunningService(HelioServiceName.MDES, Arrays.asList("2003-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00"), Arrays.asList("ACE"), "DERIV.DELTAT,100;DERIV.DELTAV,/900;DERIV.AVERAGETIME,600", null);
//	    testLongRunningService(HelioServiceName.MDES, Arrays.asList("2003-02-01T00:00:00"), Arrays.asList("2003-02-10T00:00:00"), Arrays.asList("ACE"), "SIR.DELTAT,100;SIR.DELTAV,/900;SIR.AVERAGETIME,600", null);	//DebugUtils.disableDump();
	}
	
	private static synchronized void testLongRunningService(HelioServiceName serviceName, List<String> startTime, List<String> endTime, List<String> from, String saveto, AccessInterface... accessInterfaces) {
		System.out.println("--------------------" + serviceName.getServiceName() + "--------------------");
		try {
			AsyncQueryServiceFactory queryServiceFactory = AsyncQueryServiceFactory.getInstance();
			AsyncQueryService queryService = queryServiceFactory.getAsyncQueryService(serviceName, accessInterfaces);
			HelioQueryResult result = queryService.timeQuery(startTime, endTime, from, 100, 0, saveto);

			System.out.println(result);
			if (result != null) {
				System.out.println("Phase: " + result.getPhase());
				Thread.sleep(1000);
				System.out.println("Phase: " + result.getPhase());
				System.out.println("Result URL: " + result.asURL());
				System.out.println("Result VOTable: " + result.asVOTable());
				//System.out.println("Result VOTable as String: " + result.asString());
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

	private static synchronized void testLongRunningService(HelioServiceName serviceName, List<String> startTime, List<String> endTime, List<String> from, String where, String saveto, AccessInterface... accessInterfaces) {
		System.out.println("--------------------" + serviceName + "--------------------");
		try {
			AsyncQueryServiceFactory queryServiceFactory = AsyncQueryServiceFactory.getInstance();
			AsyncQueryService queryService = queryServiceFactory.getAsyncQueryService(serviceName, accessInterfaces);
			HelioQueryResult result = queryService.query(startTime, endTime, from, where, 100, 0, null, saveto);
			
			System.out.println(result);
			if (result != null) {
			    try {
				System.out.println("Phase: " + result.getPhase());
				System.out.println("Result URL: " + result.asURL(30, TimeUnit.SECONDS));
				System.out.println("Result VOTable: " + result.asVOTable());
				//System.out.println(result.asString());
			    } catch (JobExecutionException e) {
			        e.printStackTrace();
			        // try to display logs
                }
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
