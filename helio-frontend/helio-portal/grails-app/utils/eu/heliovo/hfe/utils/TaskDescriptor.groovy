package eu.heliovo.hfe.utils

import eu.heliovo.clientapi.processing.context.impl.FlarePlotterServiceImpl
import eu.heliovo.clientapi.processing.context.impl.GoesPlotterServiceImpl
import eu.heliovo.clientapi.processing.context.impl.SimpleParkerModelServiceImpl
import eu.heliovo.hfe.model.param.TimeRange
import eu.heliovo.registryclient.HelioServiceName

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
                "longitude" : [label : "Longitude", description : "Longitude in Degrees", type : Float, defaultValue : 0],
                "width" : [label : "Width", description : "Width in Degrees", type : Float, defaultValue : 45.0],
                "speed" : [label : "Speed", description : "Speed in km/s", type : Float, defaultValue : 800],
                "speedError" : [label : "SpeedError &plusmn;", description : "Speed Error in km/s", type : Float, defaultValue : 0]
            ]
        ],
        "outputParams" : [
            "voTableUrl" : [id : "voTableUrl", label: "VOTable", type : "votable" ],
            "innerPlotUrl" : [id : "innerPlotUrl", label: "Plot of inner planets", type : "url" ],
            "outerPlotUrl" : [id : "outerPlotUrl", label: "Plot of outer planets", type : "url" ],
            "voyagerPlotUrl" : [id : "voyagerPlotUrl", label: "Plot containing voyager", type : "url" ]
        ]
      ],
      "goesplot" : [
        "label" : "GOES XRay timeline plot",
        "description" : "GOES XRay timeline plot",
        "serviceName" : HelioServiceName.CXS,
        "serviceVariant" : GoesPlotterServiceImpl.SERVICE_VARIANT,
        "timeout" : 60,  // timeout in seconds.
        "inputParams" : [
         "timeRanges" : ["timeRanges" : [type : TimeRange.class]]
        ],
        "outputParams" : [
          "url" : [id : "url", label: "GOES Plot", type : "url" ],
        ]
      ],
      "flareplot" : [
        "label" : "SOHO EIT Flare Plot",
        "description" : "SOHO EIT flare plot",
        "serviceName" : HelioServiceName.CXS,
        "serviceVariant" : FlarePlotterServiceImpl.SERVICE_VARIANT,
        "timeout" : 60,
        "inputParams" : [
          "timeRanges" : ["timeRanges" : [type : TimeRange.class]]
        ],
        "outputParams" : [
          "url" : [id : "url", label: "Flare Plot", type : "url" ],
        ]
      ],
      "parkerplot" : [
        "label" : "Simple parker spiral",
        "description" : "Simple parker spiral",
        "serviceName" : HelioServiceName.CXS,
        "serviceVariant" : SimpleParkerModelServiceImpl.SERVICE_VARIANT,
        "timeout" : 60,
        "inputParams" : [
          "timeRanges" : ["timeRanges" : [type : TimeRange.class]],
          "paramSet" : [
            "velocity" : [label : "Velocity", description : "Velocity in km/s (would speed be the better term?)", type : int, defaultValue : 400],
            "inner" : [label : "Inner ?", description : "Width in Degrees", type : int, defaultValue : 4],
            "outer" : [label : "Outer ?", description : "Speed in km/s", type : int, defaultValue : 0],
          ]
        ],
        "outputParams" : [
            "url" : [id : "url", label: "Parker Spiral Plot", type : "url" ],
        ]
      ],
      ]
    }
    
    /**
     * Find a task descriptor by name
     * @param taskName
     * @return
     */
    static def findTaskDescriptor(taskName) {
        taskDescriptor[taskName]    
    }
}