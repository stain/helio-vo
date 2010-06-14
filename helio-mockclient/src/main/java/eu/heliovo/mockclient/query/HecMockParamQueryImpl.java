package eu.heliovo.mockclient.query;

import java.util.Date;
import java.util.Map;

import eu.heliovo.clientapi.query.HelioParameter;
/**
 * Mock implementation of the param query for the HEC service. 
 * @author marco soldati at fhnw ch 
 */
public class HecMockParamQueryImpl extends AbstractMockParamQueryImpl {
	/**
	 * Specify the hec parameters.
	 */
	private final HelioParameter<?>[] hecMockParameters = new HelioParameter[] {
		new HelioParameter<Date>("startdate", "the start date", "xsd:dateTime"),
		new HelioParameter<Date>("enddate", "the end date", "xsd:dateTime"),
		new HelioParameter<String>("eventlist", "select the event list", "xsd:string", new String[] {"goeseventlist", "hessiflarelist"}),
		new HelioParameter<Integer>("delay", "Add an artifical delay in milliseconds to the service. For testing purposes only.", "xsd:integer", new Integer(0)),
		new HelioParameter<Float>("exception-probability", "Add a probability that the service throws an exception. " +
				"if <=0: never throw an exception, if >= 1.0: always throw an exception. " +
				"For testing purposes only.", "xsd:float", new Float(0))
	};
	
	/**
	 * Create a mock implementation of hte DPAS.
	 */
	public HecMockParamQueryImpl() {
		super("./resource/hec_goes_xray.xml");		
	}
	
	@Override
	public String getResourceId() {
		return "hec";
	}
		
	@Override
	public String getName() {
		return "hec";
	}
	
	
	@Override
	public String getDescription() {
		return "Helio Event Catalogue";
	}
	
	@Override
	public HelioParameter<?>[] getParameterDescription(Map<String, ? extends Object> context) {	
		return hecMockParameters;
	}


}
