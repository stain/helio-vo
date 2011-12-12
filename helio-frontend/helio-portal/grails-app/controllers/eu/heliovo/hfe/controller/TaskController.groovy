package eu.heliovo.hfe.controller

/**
 * Controller to asynchronously load the input interface for a particular task.
 * The server side part of the task interface can be initialized here.
 * @author MarcoSoldati
 *
 */
class TaskController {

    def index = { 
    }
    
    
    /**
     * Task to upload a votable
     */
    def uploadVoTable = {
        render (template: "/task/uploadVoTable", model: []);
    }
    
}
