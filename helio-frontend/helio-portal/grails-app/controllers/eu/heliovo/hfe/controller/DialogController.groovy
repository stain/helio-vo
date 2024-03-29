package eu.heliovo.hfe.controller

import eu.heliovo.clientapi.model.field.descriptor.HelioFieldDescriptor
import eu.heliovo.hfe.model.param.EventListParam
import eu.heliovo.hfe.model.param.InstrumentParam
import eu.heliovo.hfe.model.param.ParamSet
import eu.heliovo.hfe.model.param.ParamSetEntry
import eu.heliovo.hfe.model.param.QueryParamSet
import eu.heliovo.hfe.model.param.TimeRange
import eu.heliovo.hfe.model.result.HelioResult
import eu.heliovo.hfe.model.task.Task
import eu.heliovo.registryclient.ServiceCapability
import eu.heliovo.shared.util.DateUtil


/**
 * Controller for the Dialogs
 * @author MarcoSoldati
 *
 */
class DialogController {
    def taskDescriptorService
    
    /**
     * Auto-wire the defaults service.
     */
    def defaultsService
    
    /**
     * Auto wire the HELIO client.
     */
    def helioClient

    /**
     * Auto wire the extractParamsService    
     */
    def extractParamsService
    
    /**
     * Factory to create an empty where clause
     */
    def whereClauseFactoryBean
    
    def index = { }
    
    /**
     * Get the time range dialog.
     */
    def timeRangeDialog = {
        def taskName = params.taskName
        def taskDescriptor = taskDescriptorService.findTaskDescriptor(taskName)
        assert taskDescriptor != null, "Unable to find descriptor for task: " + taskName
        def initMode = InitMode.valueOf(params.init)
        
        def defaultTimeRange = defaultsService.createDefaultTimeRange(taskName).timeRanges[0]
        def timeRange
        switch (initMode) {
            case InitMode.none:
                timeRange = defaultsService.createDefaultTimeRange(taskName) // use the default time range
                break
            case InitMode.last_task:
            case InitMode.default_mode:
                // get task and load params from there.
                Task task = defaultsService.loadTask(taskName)
                timeRange = task.inputParams.timeRange
                if (timeRange == null) {
                    timeRange = defaultsService.createDefaultTimeRange(taskName)  
                }
                break;
           default: throw "Unknown init mode " + initMode + " (params.init=" + params.init +")."
        }
        
        render (template: "/dialog/timeRangeDialog", model: [ timeRange : timeRange, defaultTimeRange : defaultTimeRange, taskDescriptor: taskDescriptor])
    }
    
    /**
     * Get the time range dialog.
     */
    def timeRangeDetailsDialog = {
        def startTime = params.startTime
        def endTime = params.endTime
        
        def startTimeObj = DateUtil.fromIsoDate(startTime)
        def endTimeObj = DateUtil.fromIsoDate(endTime)
        
        def plots = []
        
        def plotTasks = [
            'goesplot_proton' : [taskName:'goesplot', query:'paramSet.plotType=PROTON', label:'GOES Proton Plot'], 
            'goesplot_sxr' : [taskName:'goesplot', query:'paramSet.plotType=XRAY', label:'GOES SXR Plot'], 
            'flareplot' : [taskName:'flareplot', title: ''], 
            'parkerplot' : [taskName : 'parkerplot', title: '']]
        plotTasks.each {
            def plotTask = taskDescriptorService.findTaskDescriptor(it.value.taskName)
            def label = it.value.label ?: plotTask.label
            plots.add([plotName: it.key, label : label,  
                taskName : it.value.taskName, task : plotTask, 
                startTime : startTime, endTime : endTime, 
                query:it.value.query])
        }
        
        def links = []
        def linkProviders = helioClient.getServiceInstances(ServiceCapability.LINK_PROVIDER_SERVICE);
        linkProviders.each { 
            def link = it.getLink(startTimeObj, endTimeObj)
            def title = it.getTitle(startTimeObj, endTimeObj)
            if (link) {
                links.add([link : link, title: title])
            }
        }
        
        def timeRangeDescriptor = ["timeRanges" : [type : TimeRange.class, restriction: 'single_time_range']]
        def timeRangeParam = new TimeRange(startTime : startTimeObj, endTime : endTimeObj)
        render (template: "/dialog/timeRangeDetailsDialog", model: [ plots : plots, links : links, timeRangeDescriptor : timeRangeDescriptor, timeRangeParam : timeRangeParam])
    }
    
    def paramSetDialog = {
        def taskName = params.taskName
        def listName = params.listName
        def taskDescriptor = taskDescriptorService.findTaskDescriptor(taskName)
        def initMode = InitMode.valueOf(params.init)
        
        def paramSet;
        
        if (listName) { // handle event list dialog
            def whereClause = whereClauseFactoryBean.createWhereClause(taskDescriptor.serviceName, listName)
            List<HelioFieldDescriptor<?>> fieldDescriptors = whereClause.getFieldDescriptors()
            paramSet = new QueryParamSet(taskName : taskName, listName : listName)
            fieldDescriptors.each { 
                paramSet.addToEntries(new ParamSetEntry(paramName : it.name, operator: it.type.operatorDomain[0], paramValue : it.defaultValue))
            }
        } else {  // handle task paramset dialog
            switch (initMode) {
                case InitMode.none:
                    paramSet = new ParamSet(taskName : taskName);
                    break;
                case InitMode.last_task:
                case InitMode.default_mode: 
                    Task task = defaultsService.loadTask(taskName)
                    paramSet = task.inputParams.paramSet
                    if (paramSet == null) {
                        paramSet = new ParamSet(taskName : taskName)
                    }
                    break;
                default: throw "Unknown init mode " + initMode + " (params.init=" + params.init +")."
            }
        }
        
        render (template: "/dialog/paramSetDialog", model: [ paramSet : paramSet, taskDescriptor : taskDescriptor])
    }
    
    def eventListDialog = {
        def taskName = params.taskName
        def taskDescriptor = taskDescriptorService.findTaskDescriptor(taskName)
        def initMode = InitMode.valueOf(params.init)
        
        def eventList;
        switch (initMode) {
            case InitMode.none:
                eventList = new EventListParam(taskName : taskName);
                break;
            case InitMode.last_task:
            case InitMode.default_mode:
                Task task = defaultsService.loadTask(taskName)
                eventList = task.inputParams.eventList
                if (eventList == null)
                    eventList = new EventListParam(taskName : taskName);
                break;
            default: throw new IllegalStateException("Unknown init mode " + initMode + " (params.init=" + params.init +").")
        }
        //println taskDescriptor
        render (template: "/dialog/eventListDialog", model: [ eventList : eventList, taskDescriptor : taskDescriptor])
    }

     def instrumentDialog = {
        def taskName = params.taskName
        def taskDescriptor = taskDescriptorService.findTaskDescriptor(taskName)
        def initMode = InitMode.valueOf(params.init)
        
        def instrumentParam;
        switch (initMode) {
        case InitMode.none:
            instrumentParam = new InstrumentParam(taskName : taskName);
            break;
        case InitMode.last_task:
        case InitMode.default_mode:
            Task task = defaultsService.loadTask(taskName)
            instrumentParam = task.inputParams.instruments
            if (instrumentParam == null)
                instrumentParam = new InstrumentParam(taskName : taskName);
            break;
        default: throw "Unknown init mode " + initMode + " (params.init=" + params.init +")."
        }
        //println taskDescriptor.inputParams.instruments.instruments.valueDomain
        render (template: "/dialog/instrumentDialog", model: [ instrument : instrumentParam, taskDescriptor : taskDescriptor])
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
