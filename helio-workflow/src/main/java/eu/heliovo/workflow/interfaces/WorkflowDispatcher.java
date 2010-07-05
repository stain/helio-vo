package eu.heliovo.workflow.interfaces;

import java.io.Writer;
import java.util.Arrays;
import java.util.Map;
import eu.heliovo.workflow.workflows.firstusecase.GetEvents;
import eu.heliovo.workflow.workflows.firstusecase.GetData;
import eu.heliovo.workflow.workflows.firstusecase.ListInstruments;

/**
 * This class parses the call parameters and executes the requested workflow.
 * It will be used by RestDispatcher or SoapDispatcher.
 * 
 * @author simon felix at fhnw ch
 */
public class WorkflowDispatcher
{
  /**
   * This method parses the call parameters and executes the requested workflow.
   * 
   * @param _w Where the result of the workflow will be written to
   * @param _parameters A map of all parameters given by the caller
   */
  public static void runWorkflow(Writer _w,Map<String,String> _parameters) throws Exception
  {
    if("FirstUseCase1GetEvents".equals(_parameters.get("WORKFLOW")))
      GetEvents.runWorkflow(_w,
          _parameters.get("STARTTIME"),
          _parameters.get("ENDTIME")
        );
    
    if("FirstUseCase2ListInstruments".equals(_parameters.get("WORKFLOW")))
      ListInstruments.runWorkflow(_w,
          Arrays.asList(_parameters.get("STARTTIME").split(",")),
          Arrays.asList(_parameters.get("ENDTIME").split(","))
        );
    
    if("FirstUseCase3GetData".equals(_parameters.get("WORKFLOW")))
      GetData.runWorkflow(_w,
          Arrays.asList(_parameters.get("INSTRUMENT").split(",")),
          Arrays.asList(_parameters.get("STARTTIME").split(",")),
          Arrays.asList(_parameters.get("ENDTIME").split(","))
        );
  }
  
  /*private static String ifnull(String _v,String _null_value)
  {
    if(_v==null)
      return _null_value;
    return _v;
  }*/
}
