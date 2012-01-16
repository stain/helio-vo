package eu.heliovo.hfe.utils

import eu.heliovo.hfe.model.param.TimeRange

/**
 * Utility to get the task descriptor map. 
 * 
 * @author MarcoSoldati
 *
 */
class TaskDescriptor {
    
    def static Map<String, Map<String, Object>> taskDescriptor = initTaskDescriptors();
    
    /**
    * Create task descriptors, if required.
    * @return
    */
    private static def initTaskDescriptors() {
       ["pmFwCme" : [
                "label" : "CME Propagation Model",
                "description" : "CME Propagation Model from Earth to a collection of predefined objects.",
                "inputParams" : [
                    "timeRanges" : ["timeRanges" : [type : TimeRange.class]],
                    "paramSet" : [
                        "longitude" : [label : "Longitude", description : "Longitude in xxx", type : Float],
                        "width" : [label : "Width", description : "Width in xxx", type : Float],
                        "speed" : [label : "Speed", description : "Speed in xxx", type : Float],
                        "speedError" : [label : "SpeedError &plusmn;", description : "Speed Error in xxx", type : Float]
                    ]
                ]
            ]
        ]
    }
}
