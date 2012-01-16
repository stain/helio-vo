package eu.heliovo.hfe.controller

import eu.heliovo.hfe.model.param.ParamSet
import eu.heliovo.hfe.model.param.TimeRangeParam
import eu.heliovo.hfe.model.result.RemotePlotResult
import eu.heliovo.hfe.model.result.RemoteVOTableResult
import eu.heliovo.hfe.model.task.Task
import eu.heliovo.hfe.utils.TaskDescriptor
import eu.heliovo.shared.util.DateUtil
import grails.converters.JSON

class ProcessingController {
    
    def processingService
    
    def conversionService

    def index = { 
    }
    
    def propagationModel = {
        
        // do the data binding (i.e. create task)
        def jsonBindings = JSON.parse(params.bindings) // parse bindings
        def taskName = jsonBindings.taskName;
        
        
        // create input params
        def timeRanges = new TimeRangeParam();
        def dates = jsonBindings.inputParams.timeRanges.collect{ [DateUtil.fromIsoDate(it.start), DateUtil.fromIsoDate(it.end)] }
        dates.each { timeRanges.addTimeRange(it) }
        
        // map 
        def inParams = jsonBindings.inputParams.paramSet.params
        def paramMap = [:];
        def paramConfig = TaskDescriptor.taskDescriptor[taskName].inputParams.paramSet;
        paramConfig.each { paramMap[it.key] = conversionService.convert(inParams[it.key] , it.value.type) }
        
        def paramSet = new ParamSet(name : jsonBindings.inputParams.paramSet.name, taskName : taskName, params : paramMap) 
        println paramSet
        
        def task = new Task([taskName : taskName, "inputParams" : ["timeRanges" : timeRanges, "paramSet" : paramSet]])
        println task
        
        if (task.validate()) {
            task.save(flush:true)
            
            // execute the query.
            println "here1"
            def resultObject = processingService.propagationModel(task)
            task.outputParams.put("votable", new RemoteVOTableResult(url: resultObject.getVOTableUrl()))
            task.outputParams.put("innerPlotUrl", new RemotePlotResult(url: resultObject.getInnerPlotUrl()))
            task.outputParams.put("outerPlotUrl", new RemotePlotResult(url: resultObject.getOuterPlotUrl()))
    
            task.save()
            println "here2"
            
            render (template: "/output/votableResult", model: [ votableResult : task.outputParams.votable])            
        } else {
            def message = "Unable to process the request."
            def responseObject = [message : message, stackTrace : task.errors.allErrors];
            render (template:'/output/votableResultError', bean:responseObject, var:'responseObject')
        }
    }
}
