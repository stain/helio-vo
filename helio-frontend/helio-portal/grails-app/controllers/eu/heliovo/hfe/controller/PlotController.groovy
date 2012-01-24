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
        
        // create input params
        def timeRanges = new TimeRangeParam();
        def dates = jsonBindings.inputParams.timeRanges.collect{ [DateUtil.fromIsoDate(it.start), DateUtil.fromIsoDate(it.end)] }
        dates.each { timeRanges.addTimeRange(it) }
        if (!timeRanges.validate()) {
            throw new ValidationException ("Invalid time ranges", timeRanges.errors)
        }
        timeRanges.save()
        
        def task;
        // handle parker spiral params
        if (taskName == 'parkerplot') {
            def inParams = [:]
            jsonBindings.inputParams.paramSet.params.each{inParams.put(it.key, it.value)}
            
            def paramSet = new ParamSet(name : jsonBindings.inputParams.paramSet.name, taskName : taskName, params : inParams)
            if (!paramSet.validate()) {
                throw new ValidationException ("Invalid param set", paramSet.errors)
            }
            paramSet.save()
            task = new Task([taskName : taskName, "inputParams" : ["timeRanges" : timeRanges, "paramSet" : paramSet]])
        } else {
            task = new Task([taskName : taskName, "inputParams" : ["timeRanges" : timeRanges]])
        }
        if (task.validate()) {
            task.save(flush:true)
            
            // execute the query (this adds the tasks to the output params).
            def model = plotService.plot(task)
            //def votableModel = (model.votableResults.size() > 0) ? voTableService.createVOTableModel(model.votableResults[0].value) : null;
            
            render (template: "/output/processingResult", model: [plotResults: model.plotResults, userLogs : model.userLogs, taskDescriptor : taskDescriptor])
        } else {
            def message = "Unable to process the request."
            def responseObject = [message : message, stackTrace : task.errors.allErrors];
            render (template:'/output/votableResultError', bean:responseObject, var:'responseObject')
        }
    }

    
}
