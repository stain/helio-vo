package eu.heliovo.hfe.controller

import java.util.logging.Level
import java.util.logging.LogRecord

import eu.heliovo.clientapi.model.field.Operator
import eu.heliovo.hfe.model.param.ParamSet
import eu.heliovo.hfe.model.param.ParamSetEntry
import eu.heliovo.hfe.model.param.TimeRangeParam
import eu.heliovo.hfe.model.task.Task
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
    
    def taskDescriptorService
    
    def processingService
	
	def voTableService

    def index = { 
    }
    
    def propagationModel = {
        
        // do the data binding (i.e. create task)
        def jsonBindings = JSON.parse(params.bindings) // parse bindings
        def taskName = jsonBindings.taskName;
        def taskDescriptor = taskDescriptorService.findTaskDescriptor(taskName)
        
        
        // create input params
        def timeRanges = new TimeRangeParam(taskName : taskName);
        def dates = jsonBindings.inputParams.timeRanges.timeRanges.collect{ [DateUtil.fromIsoDate(it.startTime), DateUtil.fromIsoDate(it.endTime)] }
        dates.each { timeRanges.addTimeRange(it) }
        if (!timeRanges.validate()) {
            throw new ValidationException ("Invalid time ranges", timeRanges.errors)
        }
		timeRanges.save()
        
        // map 		
        def paramSet = new ParamSet(name : jsonBindings.inputParams.paramSet.name, taskName : taskName)
        jsonBindings.inputParams.paramSet.entries.each{ entry ->
            paramSet.addToEntries(new ParamSetEntry(paramName : entry.paramName, operator : Operator.EQUALS, paramValue : entry.paramValue))
        }

        if (!paramSet.validate()) {
            throw new ValidationException ("Invalid param set", paramSet.errors)
        }
        paramSet.save()
		
        def task = new Task(taskName : taskName)
        task.inputParams = ["timeRanges" : timeRanges, "paramSet" : paramSet]
        
        if (!task.validate()) {
            throw new ValidationException ("Unable to create task", task.errors)
        }
        task.save(flush:true)
        
        // execute the query (this adds the tasks to the output params).
        def model = processingService.propagationModel(task)
        def votableModel
        try {
            votableModel = (model.votableResults.size() > 0) ? voTableService.createVOTableModel(model.votableResults[0].value) : null;
        } catch (Exception e) {
            model.status = "Error while processing result votable (see logs for more information)"
            model.userLogs.add(new LogRecord(Level.WARNING, e.getClass() + ": " + e.getMessage()))
            votableModel = null
        } finally {
            if (model.status) {
                response.setHeader("status", model.status)
            }
        }
        render (template: "/output/processingResult", model: [votableModel : votableModel, plotResults: model.plotResults, userLogs : model.userLogs, taskDescriptor : taskDescriptor])            
    }
}
