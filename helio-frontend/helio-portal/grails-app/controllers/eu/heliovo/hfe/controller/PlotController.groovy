package eu.heliovo.hfe.controller

import eu.heliovo.hfe.model.param.ParamSet
import eu.heliovo.hfe.model.param.TimeRangeParam
import eu.heliovo.hfe.model.task.Task
import eu.heliovo.shared.util.DateUtil
import grails.converters.JSON
import grails.validation.ValidationException

class PlotController {    
    
    def taskDescriptorService
    
    def plotService

    def index = {
    }
    
    def plot = {
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
        
        // handle plot params, if required
        if (taskDescriptor.inputParams.paramSet) {
            def paramSet = new ParamSet(jsonBindings.inputParams.paramSet)
            jsonBindings.inputParams.paramSet.params?.each{ entry ->
                paramSet.params.put(entry.key, entry.value)
            }
            if (!paramSet.validate()) {
                throw new ValidationException ("Invalid param set", paramSet.errors)
            }
            paramSet.save()
            task.inputParams.put("paramSet", paramSet)
        }
        
        if (!task.validate()) {
            throw new ValidationException ("Unable to create task", task.errors)
        }
        task.save(flush:true)

        // execute the query (this adds the tasks to the output params).
        def model = plotService.plot(task)
        if (model.status) {
            response.setHeader("status", model.status)
        }
        if (model.error) {
            response.setStatus(400) // bad request
        }
            
        render (template: "/output/processingResult", model: [plotResults: model.plotResults, userLogs : model.userLogs, taskDescriptor : taskDescriptor])
    }
    
    /**
     * Load a plot and return the url and the log as JSON structure
     */
    def asyncplot = {
        def taskName = params.taskName
        def taskDescriptor = taskDescriptorService.findTaskDescriptor(taskName)

        // create a new task
        def task = new Task(taskName : taskName)

        // create timeRanges
        if (taskDescriptor.inputParams.timeRanges) {
            def timeRanges = new TimeRangeParam(taskName : taskName)
            timeRanges.addTimeRange(DateUtil.fromIsoDate(params.startTime), DateUtil.fromIsoDate(params.endTime))
            if (!timeRanges.validate()) {
                throw new ValidationException ("Invalid time ranges", timeRanges.errors)
            }

            // add to current task
            task.inputParams.put("timeRanges", timeRanges)
        }

        // handle plot params, if required
        if (taskDescriptor.inputParams.paramSet) {
            def paramSet = new ParamSet(taskName: taskName)
            params.each{ entry ->
                if (entry.key.startsWith("paramSet.")) {
                    paramSet.params.put(entry.key.substring(9), entry.value)
                }
            }
            if (!paramSet.validate()) {
                throw new ValidationException ("Invalid param set", paramSet.errors)
            }
            task.inputParams.put("paramSet", paramSet)
        }

        if (!task.validate()) {
            throw new ValidationException ("Unable to create task", task.errors)
        }

        // execute the query (this adds the tasks to the output params).
        def model = plotService.plot(task)
        if (model.status) {
            response.setHeader("status", model.status)
        }
        if (model.error) {
            response.setStatus(400) // bad request
        }
        
        render model as JSON
        //render (template: "/output/plotResult", model: [plotResults: model.plotResults, taskDescriptor : taskDescriptor])
    }

    
}
