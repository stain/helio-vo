package eu.heliovo.hfe.controller

import eu.heliovo.clientapi.workerservice.HelioWorkerServiceHandler;
import eu.heliovo.hfe.model.param.ParamSet
import eu.heliovo.hfe.model.param.TimeRangeParam
import eu.heliovo.hfe.model.result.RemotePlotResult
import eu.heliovo.hfe.model.result.RemoteVOTableResult
import eu.heliovo.hfe.model.task.Task
import eu.heliovo.hfe.service.VoTableService;
import eu.heliovo.hfe.utils.TaskDescriptor
import eu.heliovo.shared.util.DateUtil
import grails.converters.JSON
import grails.validation.ValidationException


/**
 * Controller to handle processing requests by forwarding them to the clientapi.
 * 
 * @author MarcoSoldati
 *
 */
class ProcessingController {
    
    def processingService
    
    def conversionService
	
	def voTableService

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
		if (!timeRanges.validate()) {
			throw new ValidationException ("Invalid time ranges", timeRanges.errors)
		}
        timeRanges.save()
		
        // map 
        def inParams = [:]
		jsonBindings.inputParams.paramSet.params.each{inParams.put(it.key, it.value)}
		
        def paramSet = new ParamSet(name : jsonBindings.inputParams.paramSet.name, taskName : taskName, params : inParams) 
		if (!paramSet.validate()) {
			throw new ValidationException ("Invalid param set", paramSet.errors)
		}
		paramSet.save()
        
        def task = new Task([taskName : taskName, "inputParams" : ["timeRanges" : timeRanges, "paramSet" : paramSet]])
        
        if (task.validate()) {
            task.save(flush:true)
            
            // execute the query (this adds the tasks to the output params).
            def model = processingService.propagationModel(task)
            def votableModel = (model.votableResults.size() > 0) ? voTableService.createVOTableModel(model.votableResults[0].value) : null;  
            render (template: "/output/processingResult", model: [votableModel : votableModel, plotResults: model.plotResults, userLogs : model.userLogs])            
        } else {
            def message = "Unable to process the request."
            def responseObject = [message : message, stackTrace : task.errors.allErrors];
            render (template:'/output/votableResultError', bean:responseObject, var:'responseObject')
        }
    }
}
