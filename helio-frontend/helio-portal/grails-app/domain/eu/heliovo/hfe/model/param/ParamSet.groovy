package eu.heliovo.hfe.model.param

import eu.heliovo.hfe.model.security.User


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
    
    transient taskDescriptorService
   
    /**
     * Hold the params
     */
    Map<String, String> params
        
    static constraints = {
    }
	
	static hasMany = [
		params : String
	]

    static transients = [
        'config'
    ]
    
    /**
     * Load the task descriptor config.
     */
    Map<String, String> getConfig() {
        findTaskDescriptor()?.inputParams?.paramSet
    }
    
    def setConfig(Map config) {
        // ignore config, this is just to make the data-binding happy.    
    }
    	
	/**
     * Load the task description from the config
     * @return
     */
    def findTaskDescriptor() {
        taskDescriptorService.taskDescriptor[this.taskName]
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
