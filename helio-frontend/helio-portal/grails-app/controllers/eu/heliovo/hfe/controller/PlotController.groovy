package eu.heliovo.hfe.controller

import eu.heliovo.hfe.model.param.ParamSet
import eu.heliovo.hfe.model.param.TimeRangeParam
import eu.heliovo.hfe.model.task.Task
import eu.heliovo.hfe.utils.TaskDescriptor;
import eu.heliovo.shared.util.DateUtil
import grails.converters.JSON
import grails.validation.ValidationException

class PlotController {    
    
    def plotService

    def index = {
    }
    
    def plot = {
        
        // do the data binding (i.e. create task)
        def jsonBindings = JSON.parse(params.bindings) // parse bindings
        def taskName = jsonBindings.taskName
        def taskDescriptor = TaskDescriptor.findTaskDescriptor(taskName)
        
        // create a new task
        def task = new Task(taskName : taskName)
        
        // create timeRanges
        if (taskDescriptor.inputParams.timeRanges) {
            def timeRanges = new TimeRangeParam();
            bindData(timeRanges, jsonBindings.inputParams.timeRanges, [exclude :['timeRanges']])
            // bind time ranges
            jsonBindings.inputParams.timeRanges.timeRanges?.each{ tr->
                println "tr : " +tr
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
            
        render (template: "/output/processingResult", model: [plotResults: model.plotResults, userLogs : model.userLogs, taskDescriptor : taskDescriptor])
    }

    
}