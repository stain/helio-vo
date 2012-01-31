package eu.heliovo.hfe.model.param

import eu.heliovo.hfe.model.security.User
import eu.heliovo.hfe.utils.TaskDescriptor


/**
 * Collection of parameters of simple type.
 * This object is mainly useful for holding specific parameters of a task. 
 * @author MarcoSoldati
 */
class ParamSet extends AbstractParam {
    /**
     * Wire the spring security service.
     */
    transient springSecurityService
   
    /**
     * Hold the params
     */
    Map<String, String> params
    
    /**
     * The name of the Task this paramSet belongs to.
     */
    String taskName
    
    static constraints = {
    }
	
	static hasMany = [
		params : String
	]
	
	/**
     * Load the task description from the config
     * @return
     */
    def findTaskDescriptor() {
        TaskDescriptor.taskDescriptor[taskName]
    }
    
    def String toString() {
        "ParamSet [taskName: " + taskName + ", params: " + params +"]"
    }
    
    /**
     * Assign user if required.
     */
    def beforeValidate() {
        if (!owner) {
            owner = User.get(springSecurityService.principal.id)
        }
    }
}
