package eu.heliovo.workflow.interfaces;

import java.io.Writer;
import java.util.Arrays;
import java.util.Map;
import eu.heliovo.workflow.workflows.InitialWorkflow;
import eu.heliovo.workflow.workflows.firstusecase.GetEvents;
import eu.heliovo.workflow.workflows.firstusecase.GetData;
import eu.heliovo.workflow.workflows.firstusecase.ListInstruments;

public class WorkflowDispatcher
{
  public static void runWorkflow(Writer _w,Map<String,String> _parameters) throws Exception
  {
    /*if("InitialWorkflow".equals(_parameters.get("WORKFLOW")))
      InitialWorkflow.runWorkflow(_w,
          Arrays.asList(_parameters.get("INSTRUMENT").split(",")),
          _parameters.get("STARTTIME"),
          _parameters.get("ENDTIME"),
          ifnull(_parameters.get("GOES_MIN"),""),
          ifnull(_parameters.get("GOES_MAX"),"")
        );*/
    
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
  
  public static String[] getSupportedWorkflows()
  {
    return new String[]{"InitialWorkflow"};
  }
  
  private static String ifnull(String _v,String _null_value)
  {
    if(_v==null)
      return _null_value;
    return _v;
  }
}
