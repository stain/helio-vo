package eu.heliovo.hfe.controller

import eu.heliovo.hfe.model.task.Task;

/**
 * Controller to asynchronously load the input interface for a particular task.
 * The server side part of the task interface can be initialized here.
 * @author MarcoSoldati
 *
 */
class TaskController {
    
    def taskDescriptorService
    
    /**
     * Auto-wire the springSecurityService
     */
    def springSecurityService

    /**
     * Auto-wire the defaults service
     */
    def defaultsService
    
    def index = {
    }

    /**
     * Task to upload a voTable.
     * Requests are handled by the VoTableController 
     */
    def uploadVoTable = {
        render (template: "/task/uploadVoTable", model: []);
    }

    /**
     * Load a specific task configuration.
     * Requests are handled by specific task controllers.
     */
    def load = {
        def taskName = params.taskName;
        def taskDescriptor = taskDescriptorService.findTaskDescriptor(taskName)
        if (!taskDescriptor) {
            throw new RuntimeException("Unknown task name " + taskName)
        }

        // load previous task from database
        Task task = defaultsService.loadTask(taskName)
        def defaultTimeRange = defaultsService.createDefaultTimeRange(taskName).timeRanges[0]

        render (template: "/task/task", model: [task:task, taskDescriptor: taskDescriptor, defaultTimeRange: defaultTimeRange])
    }
    
    /**
     * Task to configure a call to the propagation model.
     * Requests are handled by the ProcessingController
     * @deprecated replace by load
     */
    def propagationModel = {
        def taskName = params.taskName;
        def taskDescriptor = taskDescriptorService.findTaskDescriptor(taskName)
        if (!taskDescriptor) {
            throw new RuntimeException("Unknown task name " + taskName)
        }
        
        // load previous task from database
        def task = defaultsService.loadTask(taskName)
        def defaultTimeRange = defaultsService.createDefaultTimeRange(taskName).timeRanges[0];
    
        render (template: "/task/task", model: [task:task, taskDescriptor: taskDescriptor, defaultTimeRange: defaultTimeRange])
    }

    /**
     * Task to configure a call to a plotting service.
     * Requests are handled by the PlotController
     * @deprecated replace by load
     */
    def plot = {
        def taskName = params.taskName;
        def taskDescriptor = taskDescriptorService.findTaskDescriptor(taskName)
        if (!taskDescriptor) {
            throw new RuntimeException("Unknown task name " + taskName)
        }
        
        // load previous task from database
        Task task = defaultsService.loadTask(taskName)
        def defaultTimeRange = defaultsService.createDefaultTimeRange(taskName).timeRanges[0]
        
        render (template: "/task/task", model: [task:task, taskDescriptor: taskDescriptor, defaultTimeRange: defaultTimeRange])
    }
}
