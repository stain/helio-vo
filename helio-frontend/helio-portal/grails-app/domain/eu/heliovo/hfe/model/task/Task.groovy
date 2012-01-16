package eu.heliovo.hfe.model.task

import java.util.Date
import java.util.Map;

import eu.heliovo.clientapi.workerservice.HelioWorkerServiceHandler
import eu.heliovo.hfe.model.param.AbstractParam
import eu.heliovo.hfe.model.result.HelioResult
import eu.heliovo.hfe.model.security.User
import eu.heliovo.hfe.utils.TaskDescriptor

/**
 * Configuration of a concrete instance of a HELIO task. A task basically consists of a 
 * collection on inputParameters and once exectued of a collection of outputParameters.
 * @author MarcoSoldati
 *
 */
class Task {
    /**
     * Auto-wire the spring security service.
     */
    transient springSecurityService;

    /**
     * Creation date will be automatically set by GORM
     */
    Date dateCreated
  
    /**
     * Last update date will be automatically managed by GORM
     */
    Date lastUpdated
    
    /**
     * Name of the task.
     */
    String taskName
       
    /**
     * This is the last known status of the task. It may be that the actual task implementation changed it's status without
     * being updated here. Still this property can be used to query for tasks in a specific status.
     */
    HelioWorkerServiceHandler.Phase lastKnownStatus = HelioWorkerServiceHandler.Phase.PENDING
   
    /**
      * The user this result belongs to
      */
    User owner
      
    /**
     * Name and concrete instance of the input parameters for a task.
     */
    Map<String, AbstractParam> inputParams = new HashMap<String, AbstractParam>();
    
    /**
     * Name and concrete instance of the output parameters of a task.
     */
    Map<String, HelioResult> outputParams = new HashMap<String, HelioResult>();
     
    static constraints = {
        owner nullable : false
    }
    
//    static hasMany = [
//        inputParams : AbstractParam,
//        outputParams : HelioResult
//    ]
    
    /**
     * Assign user if required.
     * @return nothing
     */
    def beforeValidate() {
        if (!owner) 
            owner = User.get(springSecurityService.principal.id) 
    }
    
    /**
    * Load the task description from the config
    * @return
    */
   def findTaskDescriptor() {
       TaskDescriptor.taskDescriptor[taskName]
   }
   
   def String toString() {
       "Task: [taskName:" + taskName + ",\n    inputParams: " + inputParams + ",\n    outputParams: " + outputParams +"\n]"
   }
}
