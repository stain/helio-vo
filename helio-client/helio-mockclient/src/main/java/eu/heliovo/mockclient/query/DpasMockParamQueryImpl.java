package eu.heliovo.mockclient.query;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import eu.heliovo.clientapi.query.HelioParameter;
import eu.heliovo.workflow.workflows.firstusecase.GetData;
/**
 * Mock implementation of the param query. This implementation returns just a 
 * @author marco soldati at fhnw ch 
 */
public class DpasMockParamQueryImpl extends AbstractMockParamQueryImpl {
	private final HelioParameter<?>[] dpasMockParameters = new HelioParameter[] {
			new HelioParameter<String>("INSTRUMENT", "the instruments to query", "xsd:string[]", new String[] {"RHESSI__HESSI_GMR"}, "RHESSI__HESSI_GMR"),
			new HelioParameter<Date>("STARTTIME", "the start date. Format: 2005-01-01T00:00:00", "xsd:dateTime"),
			new HelioParameter<Date>("ENDTIME", "the end date. Format: 2005-01-01T00:00:00", "xsd:dateTime"),
			new HelioParameter<Integer>("delay", "Add an artifical delay in milliseconds to the service. For testing purposes only.", "xsd:int", new Integer(0)),
			new HelioParameter<Float>("exception-probability", "Add a probability for an exception to the service. no exception if <=0, always an exception if > 1.0. For testing purposes only.", "xsd:float", new Float(0))
		};

	public DpasMockParamQueryImpl() {
		super("./resource/ils.xml");
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
	
	@Override
	protected InputStream performQuery(Map<String, ? extends Object> parameters) throws Exception {
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		String instrument = getAs(parameters, "INSTRUMENT", String.class, "2005-01-01T00:00:00");
	    String startTime = getAs(parameters, "STARTTIME", String.class, "2005-01-01T00:00:00");
	    String endTime = getAs(parameters, "ENDTIME", String.class, "2005-01-02T00:00:00");	    
	    
		GetData.runWorkflow(new PrintWriter(baos), Collections.singletonList(instrument), 
				Collections.singletonList(startTime), 
				Collections.singletonList(endTime));
		
		return new ByteArrayInputStream(baos.toByteArray());
	}
}
