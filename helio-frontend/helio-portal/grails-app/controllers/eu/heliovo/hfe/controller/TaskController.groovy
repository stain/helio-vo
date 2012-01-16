package eu.heliovo.hfe.controller

import eu.heliovo.hfe.model.security.User

/**
 * Controller to asynchronously load the input interface for a particular task.
 * The server side part of the task interface can be initialized here.
 * @author MarcoSoldati
 *
 */
class TaskController {
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
     * Task to configure a call to the propagation model.
     * Requests are handled by the ProcessingController
     */
    def propagationModel = {
        def taskName = params.taskName;
        if (!taskName) taskName = "pmFwCme";

        // load previous task from database
        def user = User.get(springSecurityService.principal.id)
        def task = defaultsService.loadTask(taskName)
        
        def defaultTimeRange = defaultsService.createDefaultTimeRange().timeRanges[0];
    
        def title
        if (taskName == "pmFwCme") {
            title = "CME Propagation model: Sun -&gt; object"
            
        }
        render (template: "/task/propagationModel", model: [task:task, defaultTimeRange: defaultTimeRange, title:title])
    }
}
