package eu.heliovo.mockclient.query;

import java.util.Date;
import java.util.Map;

import eu.heliovo.clientapi.query.HelioParameter;
/**
 * Mock implementation of the param query. This implementation returns just a 
 * @author marco soldati at fhnw ch 
 */
public class IlsMockParamQueryImpl extends AbstractMockParamQueryImpl {
	
	
	private final HelioParameter<?>[] ilsMockParameters = new HelioParameter[] {
		new HelioParameter<Date>("startdate", "the start date", "xsd:dateTime"),
		new HelioParameter<Date>("enddate", "the end date", "xsd:dateTime"),
		new HelioParameter<Integer>("delay", "Add an artifical delay in milliseconds to the service. For testing purposes only.", "xsd:int", new Integer(0)),
		new HelioParameter<Float>("exception-probability", "Add a probability for an exception to the service. no exception if <=0, always an exception if > 1.0. For testing purposes only.", "xsd:float", new Float(0))
	};

	
	public IlsMockParamQueryImpl() {
		super("./resource/ils.xml");
	}
	
	@Override
	public HelioParameter<?>[] getParameterDescription(Map<String, ? extends Object> context) {
		return ilsMockParameters;
	}
	
	@Override
	public String getResourceId() {
		return "ils";
	}
	
	@Override
	public String getName() {
		return "ils";
	}

	@Override
	public String getDescription() {
		return "Instrument Location Service";
	}
}
