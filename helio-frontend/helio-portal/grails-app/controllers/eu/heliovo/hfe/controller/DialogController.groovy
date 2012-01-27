package eu.heliovo.hfe.controller

import eu.heliovo.hfe.model.param.ParamSet
import eu.heliovo.hfe.model.param.TimeRangeParam
import eu.heliovo.hfe.model.result.HelioResult;
import eu.heliovo.hfe.model.result.RemotePlotResult
import eu.heliovo.hfe.model.task.Task
import eu.heliovo.hfe.utils.TaskDescriptor;

class DialogController {
    /**
     * Auto-wire the defaults service.
     */
    def defaultsService

    /**
     * Auto wire the extractParamsService    
     */
    def extractParamsService
    
    def index = { }
    
    /**
     * Get the time range dialog.
     */
    def timeRangeDialog = {
        def taskName = params.taskName
        def initMode = InitMode.valueOf(params.init)
        
        def defaultTimeRange = defaultsService.createDefaultTimeRange().timeRanges[0]
        def timeRange
        switch (initMode) {
            case InitMode.none:
                timeRange = new TimeRangeParam() // create an empty time ranges param
                break
            case InitMode.last_task:
            case InitMode.default_mode:
                // get task and load params from there.
                Task task = defaultsService.loadTask(taskName)
                timeRange = task.inputParams.timeRange
                break;
           default: throw "Unknown init mode " + initMode + " (params.init=" + params.init +")."
        }
        
        render (template: "/dialog/timeRangeDialog", model: [ timeRange : timeRange, defaultTimeRange : defaultTimeRange])
    }
    
    def paramSetDialog = {
        def taskName = params.taskName
        def taskDescriptor = TaskDescriptor.findTaskDescriptor(taskName)
        def initMode = InitMode.valueOf(params.init)
        
        def paramSet;
        switch (initMode) {
            case InitMode.none:
                paramSet = new ParamSet(taskName : taskName);
                break;
            case InitMode.last_task:
            case InitMode.default_mode: 
                Task task = defaultsService.loadTask(taskName)
                paramSet = task.inputParams.paramSet
                break;
            default: throw "Unknown init mode " + initMode + " (params.init=" + params.init +")."
        }
        
        render (template: "/dialog/paramSetDialog", model: [ paramSet : paramSet, taskDescriptor : taskDescriptor])
    }
    
    def extractParamDialog = {
        def tableId = params.tableId
        def tableIndex = params.tableIndex
        
        if (tableId == null || tableIndex == 0) {
            throw new RuntimeException("Got invalid parameters.")
        }
        
        def votable = HelioResult.get(tableId)
        
        extractParamsService.createExtractionModel(votable, tableIndex)
        
        render (template: "/dialog/extractParamsDialog", model: [])
        
        
    }
    
//    def plotResult = {
//        def plotResults = [];
//        
//        (1..6).each{
//            plotResults.add([id: "test_${it}", label: "Label ${it}", value: new RemotePlotResult(url : "http://sircamt.canceraquitaine.org/images/RCA/sarcomes/casdumois/200501/image${it}.jpg") ]);
//        }
//        println plotResults
//        render template: "/output/plotResult", model: [ plotResults : plotResults]
//    }
}

/**
 * How to initialize the default values of a dialog
 * @author MarcoSoldati
 *
 */
enum InitMode {
    /**
     * No initialization should be done, i.e. no default values should be set.
     */
    none,
    
    /**
     * Use the values from the last created tasks
     */
    last_task,
    
    /**
     * Use the default parameter.
     */
    default_mode
}
