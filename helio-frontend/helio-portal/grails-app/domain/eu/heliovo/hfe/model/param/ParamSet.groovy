package eu.heliovo.hfe.model.param

import java.util.Map;
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
    
    static constraints = {
    }
	
	static hasMany = [
		entries : ParamSetEntry
	]

    static transients = [
        'config'
    ]
    
    static mapping = {
        tablePerHierarchy false
        entries cascade: "all-delete-orphan"
    }
    
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
    
    /**
     * Find the ParamSetEntry by its name.
     * @param entryName the name of the entry
     * @return the found entry.
     */
    def ParamSetEntry findEntryByName(String entryName) {
        for (ParamSetEntry entry : entries) {
            if (entryName == entry.paramName) {
                return entry
            }
        }
        return null;
    }
    
    def String toString() {
        "ParamSet [taskName: " + taskName + ", entries: " + entries +"]"
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
