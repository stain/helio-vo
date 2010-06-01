package eu.heliovo.workflow;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import eu.heliovo.workflow.interfaces.WorkflowDispatcher;
import junit.framework.TestCase;

/**
 * Some simple unit tests for the initial workflow
 */
public class ListInstrumentsWorkflowTest extends TestCase
{
  public void testForResults() throws Exception
  {
    /*ByteArrayOutputStream baos=new ByteArrayOutputStream();
    Map<String,String> parameters=new HashMap<String,String>();
    
    parameters.put("WORKFLOW","FirstUseCase2ListInstruments");
    parameters.put("STARTTIME","2005-01-01T00:00:00");
    parameters.put("ENDTIME","2005-01-11T00:00:00");
    
    WorkflowDispatcher.runWorkflow(new PrintWriter(baos),parameters);
    assertTrue("FirstUseCase2ListInstruments didn't return enough data",baos.toByteArray().length>2048);*/
  }
  
  public void testForNoResults() throws Exception
  {
    /*ByteArrayOutputStream baos=new ByteArrayOutputStream();
    Map<String,String> parameters=new HashMap<String,String>();
    
    parameters.put("WORKFLOW","FirstUseCase2ListInstruments");
    parameters.put("STARTTIME","1955-11-05T01:19:00");
    parameters.put("ENDTIME","1955-11-12T06:00:00");
    
    WorkflowDispatcher.runWorkflow(new PrintWriter(baos),parameters);
    assertTrue("FirstUseCase2ListInstruments shouldn't return data",baos.toByteArray().length<2048);*/
  }
}
