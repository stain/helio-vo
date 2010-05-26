package eu.heliovo.mockclient.query;

import java.util.HashMap;
import java.util.Map;

import eu.heliovo.clientapi.query.HelioParamQuery;
import eu.heliovo.clientapi.query.HelioParameter;
import eu.heliovo.clientapi.query.HelioParameter.AnnotatedObject;
import eu.heliovo.clientapi.result.HelioResultSetFuture;
import eu.heliovo.clientapi.service.QueryServiceDescriptor;
/**
 * Mock implementation of the param query. This implementation returns just a 
 * @author marco soldati at fhnw ch 
 */
public class MockParamQueryImpl implements HelioParamQuery {

	private static final String PARAM_SERVICE = "service";
	
	/**
	 * The Map of registered query services.
	 */
	private final Map<String, QueryServiceDescriptor> SERVICES = new HashMap<String, QueryServiceDescriptor>();
	
	public MockParamQueryImpl() {
		initialize();
	}
	
	/**
	 * Initialize the mock param query impl with some static data.
	 */
	private void initialize() {
		/**
		 * The HEC domain value.
		 */
		final AnnotatedObject<String> hecParam = new AnnotatedObject<String>("hec", "Helio Event Catalogue");		// TODO Auto-generated method stub		

		/**
		 * The ILS domain value.
		 */
		final AnnotatedObject<String> ilsParam = new AnnotatedObject<String>("ils", "Instrument Location Service");
		
		/**
		 * The DPAS domain value.
		 */
		final AnnotatedObject<String> dpasParam = new AnnotatedObject<String>("dpas", "Data Provider Access Service");

		// the default parameters
		{
			QueryServiceDescriptor defaultQueryServiceDescriptor = new QueryServiceDescriptor() {
				private final HelioParameter[] serviceMockParameters = new HelioParameter[] {
						new HelioParameter(PARAM_SERVICE, "Available query parameter services.", "xsd:string",
						new AnnotatedObject[] {hecParam, ilsParam, dpasParam})
					};
				
				@Override
				public String getServiceName() {
					return null;
				}
				
				@Override
				public HelioParameter[] getHelioParameters() {
					return serviceMockParameters;
				}
				
				@Override
				public String getDescription() {
					return serviceMockParameters[0].getDescription();
				}
			};		
			SERVICES.put(null, defaultQueryServiceDescriptor);
		}
		
		// the hec parameters
		{
			QueryServiceDescriptor hecQueryServiceDescriptor = new QueryServiceDescriptor() {
				private final HelioParameter[] hecMockParameters = new HelioParameter[] {
						new HelioParameter("startdate", "the start date", "xsd:dateTime", null),
						new HelioParameter("enddate", "the end date", "xsd:dateTime", null),
						new HelioParameter("eventlist", "select the event list", "xsd:string", new String[] {"goeseventlist", "hessiflarelist"})
					};
				
				@Override
				public String getServiceName() {
					return null;
				}
				
				@Override
				public HelioParameter[] getHelioParameters() {
					return hecMockParameters;
				}
				
				@Override
				public String getDescription() {
					return hecParam.getDescription();
				}
			};
			SERVICES.put(hecParam.getValue(), hecQueryServiceDescriptor);
		}

		// the ils parameters
		{
			QueryServiceDescriptor ilsQueryServiceDescriptor = new QueryServiceDescriptor() {
				private final HelioParameter[] ilsMockParameters = new HelioParameter[] {
						new HelioParameter("startdate", "the start date", "xsd:dateTime", null),
						new HelioParameter("enddate", "the end date", "xsd:dateTime", null),
						//new HelioParameter("eventlist", "select the event list", "xsd:string", new String[] {"goeseventlist", "hessiflarelist"})						
				};
				
				@Override
				public String getServiceName() {
					return null;
				}
				
				@Override
				public HelioParameter[] getHelioParameters() {
					return ilsMockParameters;
				}
				
				@Override
				public String getDescription() {
					return ilsParam.getDescription();
				}
			};
			SERVICES.put(ilsParam.getValue(), ilsQueryServiceDescriptor);
		}
		
		// the dpas parameters
		{
			QueryServiceDescriptor dpasQueryServiceDescriptor = new QueryServiceDescriptor() {
				private final HelioParameter[] dpasMockParameters = new HelioParameter[] {
						new HelioParameter("startdate", "the start date", "xsd:dateTime", null),
						new HelioParameter("enddate", "the end date", "xsd:dateTime", null),
						new HelioParameter("eventlist", "select the data provider", "xsd:string", new String[] {"rhessi", "phoenix"})
					};
				
				@Override
				public String getServiceName() {
					return null;
				}
				
				@Override
				public HelioParameter[] getHelioParameters() {
					return dpasMockParameters;
				}
				
				@Override
				public String getDescription() {
					return dpasParam.getDescription();
				}
			};
			SERVICES.put(dpasParam.getValue(), dpasQueryServiceDescriptor);
		}
	}
	

	
	@Override
	public HelioParameter[] getParameterDescription(Map<String, ? extends Object> context) {		
		if (context == null || !context.containsKey(PARAM_SERVICE)) {
			return SERVICES.get(null).getHelioParameters();
		} else {
			Object serviceName = context.get(PARAM_SERVICE);
			QueryServiceDescriptor serviceImpl = SERVICES.get(serviceName);
			if (serviceImpl != null) {
				return serviceImpl.getHelioParameters();
			} else {
				return SERVICES.get(null).getHelioParameters();
				// TODO: Or shall we throw an exception here???
			}
		}
	}

	@Override
	public HelioResultSetFuture queryAsync(Map<String, ? extends Object> params) {
		return null;
	}

	@Override
	public HelioResultSetFuture querySync(Map<String, ? extends Object> params) {
		return null;
	}

}
