package eu.heliovo.hfe.model.task

import java.util.Map;

import eu.heliovo.hfe.model.param.AbstractParam;
import eu.heliovo.hfe.model.param.ParamSet;
import eu.heliovo.hfe.model.param.TimeRangeParam;
import eu.heliovo.hfe.model.result.HelioResult;

class PropagationModelTask extends Task {
    
    TimeRangeParam inTimeRange
    
    ParamSet inParamSet;
    
    List<HelioResult> outVoTable;
    
    
    HelioResult test;
    
   /**
    * Name and concrete instance of the input parameters for a task.
    */
   Map<String, AbstractParam> inputParams = new HashMap<String, AbstractParam>();
   
   /**
    * Name and concrete instance of the output parameters of a task.
    */
   Map<String, HelioResult> outputParams = new HashMap<String, HelioResult>();

    static constraints = {
    }
}
