
package eu.heliovo.hfe.controller

import java.util.logging.Level
import java.util.logging.LogRecord

import eu.heliovo.hfe.model.param.EventListParam
import eu.heliovo.hfe.model.param.InstrumentParam
import eu.heliovo.hfe.model.param.TimeRangeParam
import eu.heliovo.hfe.model.task.Task
import eu.heliovo.shared.util.DateUtil
import grails.converters.JSON
import grails.validation.ValidationException


/**
 * Controller that handles access to data and metadata catalogues
 * TODO: Rename to data access controller
 * @author MarcoSoldati
 */
class CatalogController {
    
    def taskDescriptorService
    
    def catalogService
        
    def voTableService

    def index = {
    }
    
    def hec = {
        // do the data binding (i.e. create task)
        def jsonBindings = JSON.parse(params.bindings) // parse bindings
        def taskName = jsonBindings.taskName
        def taskDescriptor = taskDescriptorService.findTaskDescriptor(taskName)
        
        // create a new task
        def task = new Task(taskName : taskName)
        
        // create timeRanges
        if (taskDescriptor.inputParams.timeRanges) {
            def timeRanges = new TimeRangeParam();
            bindData(timeRanges, jsonBindings.inputParams.timeRanges, [exclude :['timeRanges']])
            // bind time ranges
            jsonBindings.inputParams.timeRanges.timeRanges?.each{ tr->
                timeRanges.addTimeRange(DateUtil.fromIsoDate(tr.startTime), DateUtil.fromIsoDate(tr.endTime))
            }
            if (!timeRanges.validate()) {
                throw new ValidationException ("Invalid time ranges", timeRanges.errors)
            }
            timeRanges.save()
            
            // add to current task
            task.inputParams.put("timeRanges", timeRanges)
        }
        
        // handle eventlist params, if required
        if (taskDescriptor.inputParams.eventList) {
            def eventList = new EventListParam(jsonBindings.inputParams.eventList)
            if (!eventList.validate()) {
                throw new ValidationException ("Invalid param set", eventList.errors)
            }
            eventList.save()
            task.inputParams.put("eventList", eventList)
        }
        
        if (!task.validate()) {
            throw new ValidationException ("Unable to create task", task.errors)
        }
        task.save(flush:true)

        // execute the query (this adds the tasks to the output params).
        def model = catalogService.queryCatalog(task)
         
        def votableModel
        try {
            votableModel = (model.votableResults.size() > 0) ? voTableService.createVOTableModel(model.votableResults[0].value) : null;
            
            // add custom hec action
            votableModel.tables.each { table -> 
                table.rowactions += 'examine_event'
            }
            
        } catch (Exception e) {
            model.status = "Error while processing result votable (see logs for more information)"
            model.userLogs.add(new LogRecord(Level.WARNING, e.getClass + ": " + e.getMessage()))
            votableModel = null
        } finally {
            if (model.status) {
                response.setHeader("status", model.status)
            }
        }
        render (template: "/output/processingResult", model: [votableModel : votableModel, plotResults: model.plotResults, userLogs : model.userLogs, taskDescriptor : taskDescriptor])
    }
    
    def dpas = {
        // do the data binding (i.e. create task)
        def jsonBindings = JSON.parse(params.bindings) // parse bindings
                def taskName = jsonBindings.taskName
                def taskDescriptor = taskDescriptorService.findTaskDescriptor(taskName)
                
                // create a new task
                def task = new Task(taskName : taskName)
        
        // create timeRanges
        if (taskDescriptor.inputParams.timeRanges) {
            def timeRanges = new TimeRangeParam();
            bindData(timeRanges, jsonBindings.inputParams.timeRanges, [exclude :['timeRanges']])
            // bind time ranges
            jsonBindings.inputParams.timeRanges.timeRanges?.each{ tr->
                timeRanges.addTimeRange(DateUtil.fromIsoDate(tr.startTime), DateUtil.fromIsoDate(tr.endTime))
            }
            if (!timeRanges.validate()) {
                throw new ValidationException ("Invalid time ranges", timeRanges.errors)
            }
            timeRanges.save()
            
            // add to current task
            task.inputParams.put("timeRanges", timeRanges)
        }
        
        // handle instrument params, if required
        if (taskDescriptor.inputParams.instruments) {
            def instrumentParam = new InstrumentParam(jsonBindings.inputParams.instruments)
            if (!instrumentParam.validate()) {
                throw new ValidationException ("Invalid param set", instrumentParam.errors)
            }
            instrumentParam.save()
            task.inputParams.put("instruments", instrumentParam)
        }
        
        if (!task.validate()) {
            throw new ValidationException ("Unable to create task", task.errors)
        }
        task.save(flush:true)
        
        // execute the query (this adds the tasks to the output params).
        def model = catalogService.queryCatalog(task)
        
        def votableModel
        try {
            votableModel = (model.votableResults.size() > 0) ? voTableService.createVOTableModel(model.votableResults[0].value) : null;
        } catch (Exception e) {
            model.status = "Error while processing result votable (see logs for more information)"
                    model.userLogs.add(new LogRecord(Level.WARNING, e.getClass + ": " + e.getMessage()))
                    votableModel = null
        } finally {
            if (model.status) {
                response.setHeader("status", model.status)
            }
        }
        render (template: "/output/processingResult", model: [votableModel : votableModel, plotResults: model.plotResults, userLogs : model.userLogs, taskDescriptor : taskDescriptor])
    }
    
    def ics = {
        // do the data binding (i.e. create task)
        def jsonBindings = JSON.parse(params.bindings) // parse bindings
        def taskName = jsonBindings.taskName
        def taskDescriptor = taskDescriptorService.findTaskDescriptor(taskName)
        
        // create a new task
        def task = new Task(taskName : taskName)
        
        // create timeRanges
        if (taskDescriptor.inputParams.timeRanges) {
            def timeRanges = new TimeRangeParam();
            bindData(timeRanges, jsonBindings.inputParams.timeRanges, [exclude :['timeRanges']])
            // bind time ranges
            jsonBindings.inputParams.timeRanges.timeRanges?.each{ tr->
                timeRanges.addTimeRange(DateUtil.fromIsoDate(tr.startTime), DateUtil.fromIsoDate(tr.endTime))
            }
            if (!timeRanges.validate()) {
                throw new ValidationException ("Invalid time ranges", timeRanges.errors)
            }
            timeRanges.save()
            
            // add to current task
            task.inputParams.put("timeRanges", timeRanges)
        }
                
        if (!task.validate()) {
            throw new ValidationException ("Unable to create task", task.errors)
        }
        task.save(flush:true)

        // execute the query (this adds the tasks to the output params).
        def model = catalogService.queryCatalog(task)
         
        def votableModel
        try {
            votableModel = (model.votableResults.size() > 0) ? voTableService.createVOTableModel(model.votableResults[0].value) : null;
        } catch (Exception e) {
            model.status = "Error while processing result votable (see logs for more information)"
            model.userLogs.add(new LogRecord(Level.WARNING, e.getClass + ": " + e.getMessage()))
            votableModel = null
        } finally {
            if (model.status) {
                response.setHeader("status", model.status)
            }
        }
        render (template: "/output/processingResult", model: [votableModel : votableModel, plotResults: model.plotResults, userLogs : model.userLogs, taskDescriptor : taskDescriptor])
    }
    
    def ils = {
        // do the data binding (i.e. create task)
        def jsonBindings = JSON.parse(params.bindings) // parse bindings
        def taskName = jsonBindings.taskName
        def taskDescriptor = taskDescriptorService.findTaskDescriptor(taskName)
        
        // create a new task
        def task = new Task(taskName : taskName)
        
        // create timeRanges
        if (taskDescriptor.inputParams.timeRanges) {
            def timeRanges = new TimeRangeParam();
            bindData(timeRanges, jsonBindings.inputParams.timeRanges, [exclude :['timeRanges']])
            // bind time ranges
            jsonBindings.inputParams.timeRanges.timeRanges?.each{ tr->
                timeRanges.addTimeRange(DateUtil.fromIsoDate(tr.startTime), DateUtil.fromIsoDate(tr.endTime))
            }
            if (!timeRanges.validate()) {
                throw new ValidationException ("Invalid time ranges", timeRanges.errors)
            }
            timeRanges.save()
            
            // add to current task
            task.inputParams.put("timeRanges", timeRanges)
        }
                
        if (!task.validate()) {
            throw new ValidationException ("Unable to create task", task.errors)
        }
        task.save(flush:true)

        // execute the query (this adds the tasks to the output params).
        def model = catalogService.queryCatalog(task)
         
        def votableModel
        try {
            votableModel = (model.votableResults.size() > 0) ? voTableService.createVOTableModel(model.votableResults[0].value) : null;
                        
        } catch (Exception e) {
            model.status = "Error while processing result votable (see logs for more information)"
            model.userLogs.add(new LogRecord(Level.WARNING, e.getClass + ": " + e.getMessage()))
            votableModel = null
        } finally {
            if (model.status) {
                response.setHeader("status", model.status)
            }
        }
        render (template: "/output/processingResult", model: [votableModel : votableModel, plotResults: model.plotResults, userLogs : model.userLogs, taskDescriptor : taskDescriptor])
    }

}
