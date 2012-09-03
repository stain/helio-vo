package eu.heliovo.hfe.model.param

import eu.heliovo.clientapi.model.catalog.descriptor.EventListDescriptor;
import eu.heliovo.clientapi.model.service.HelioService;
import eu.heliovo.hfe.model.security.User
import eu.heliovo.registryclient.HelioServiceName;


/**
 * Collection of parameters of simple type.
 * This object is mainly useful for holding specific parameters of a task. 
 * @author MarcoSoldati
 */
class QueryParamSet extends ParamSet {
    /**
     * Auto-wire the config manager
     */
    transient configurationManager

    /**
     * list of fields to escape from query param set configuration.
     */
    private static final ESCAPED_FIELDS = ['hec_id', 'time_start', 'time_end', 'time_peak']
    
        
    String listName
    
    static transients = [
        'config'
    ]
    
    static mapping = {
        tablePerHierarchy false
    }
    
    static constraints = {
        listName nullable : false
    }
    
    /**
     * Load the task descriptor config.
     */
    Map<String, String> getConfig() {
        def taskDescriptor = findTaskDescriptor();
        
        // get the property descriptor for the from value of the current service
        def catalogueDescriptors = configurationManager.getCatalogueDescriptors(taskDescriptor.serviceName, null)
        if (! catalogueDescriptors) {
            return [:]
        }
        
        // now get the value domain and create a map, using the value (i.e. the list name) property as key.
        def catalogueDescriptor = catalogueDescriptors.find{ it.value == listName};
        if (!catalogueDescriptor) {
            return [:]
        }
        return catalogueDescriptor.fieldDescriptors.findAll{ fd -> !ESCAPED_FIELDS.contains(fd.id)  }.collectEntries{[it.id, it]}
    }
    
    def String toString() {
        "QueryParamSet [taskName: " + taskName + ", listName: " + listName + ", entries: " + entries +"]"
    }
}
