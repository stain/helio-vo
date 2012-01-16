package eu.heliovo.hfe.model.param

import eu.heliovo.hfe.utils.TaskDescriptor;


/**
 * Collection of parameters of simple type.
 * This object is mainly useful for holding specific parameters of a task. 
 * @author MarcoSoldati
 */
class ParamSet extends AbstractParam {
    /**
     * Hold the params
     */
    Map<String, Object> params = new HashMap<String, Object>()
    
    /**
     * The name of the Task this paramSet belongs to.
     */
    String taskName
    
    /**
     * The associated TaskDescriptor
     */
    transient Map taskDescriptor

    static constraints = {
    }
    
    /**
     * Load the task description from the config
     * @return
     */
    def findTaskDescriptor() {
        TaskDescriptor.taskDescriptor[taskName]
    }
    
    def String toString() {
        "ParamSet: [" + params  + "]"
    }
}
