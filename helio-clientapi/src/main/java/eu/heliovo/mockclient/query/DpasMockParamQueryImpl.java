package eu.heliovo.mockclient.query;

import java.util.Date;
import java.util.Map;

import eu.heliovo.clientapi.query.HelioParameter;
/**
 * Mock implementation of the param query. This implementation returns just a 
 * @author marco soldati at fhnw ch 
 */
public class DpasMockParamQueryImpl extends AbstractMockParamQueryImpl {
	private final HelioParameter<?>[] dpasMockParameters = new HelioParameter[] {
			new HelioParameter<Date>("startdate", "the start date", "xsd:dateTime"),
			new HelioParameter<Date>("enddate", "the end date", "xsd:dateTime"),
			new HelioParameter<String>("eventlist", "select the data provider", "xsd:string", new String[] {"rhessi", "phoenix"}),
			new HelioParameter<Integer>("delay", "Add an artifical delay in milliseconds to the service. For testing purposes only.", "xsd:int", new Integer(0)),
			new HelioParameter<Float>("exception-probability", "Add a probability for an exception to the service. no exception if <=0, always an exception if > 1.0. For testing purposes only.", "xsd:float", new Float(0))
		};

	public DpasMockParamQueryImpl() {
		super("./resource/dpas.xml");
	}	
	
	@Override
	public String getResourceId() {
		return "dpas";
	}
	
	@Override
	public String getName() {
		return "dpas";
	}
	
	@Override
	public String getDescription() {
		return "Data Provider Access Service";
	}
	
	@Override
	public HelioParameter<?>[] getParameterDescription(Map<String, ? extends Object> context) {
		return dpasMockParameters;
	}
}
