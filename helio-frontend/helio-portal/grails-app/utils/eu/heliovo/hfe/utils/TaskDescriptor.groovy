package eu.heliovo.hfe.utils

import eu.heliovo.clientapi.processing.context.impl.FlarePlotterServiceImpl
import eu.heliovo.clientapi.processing.context.impl.GoesPlotterServiceImpl
import eu.heliovo.clientapi.processing.context.impl.SimpleParkerModelServiceImpl
import eu.heliovo.clientapi.processing.context.impl.des.AcePlotterServiceImpl
import eu.heliovo.clientapi.processing.context.impl.des.StaPlotterServiceImpl;
import eu.heliovo.clientapi.processing.context.impl.des.StbPlotterServiceImpl;
import eu.heliovo.clientapi.processing.context.impl.des.UlyssesPlotterServiceImpl;
import eu.heliovo.clientapi.processing.context.impl.des.WindPlotterServiceImpl;
import eu.heliovo.hfe.model.param.TimeRange
import eu.heliovo.registryclient.HelioServiceName
import eu.heliovo.registryclient.ServiceCapability

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
        "serviceCapability" : ServiceCapability.COMMON_EXECUTION_ARCHITECTURE_SERVICE,
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
        "serviceCapability" : ServiceCapability.COMMON_EXECUTION_ARCHITECTURE_SERVICE,
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
        "serviceCapability" : ServiceCapability.COMMON_EXECUTION_ARCHITECTURE_SERVICE,
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
      "aceplot" :  [
        "label" : "ACE timeline plot",
        "description" : "ACE timeline plot",
        "serviceName" : HelioServiceName.DES,
        "serviceCapability" : ServiceCapability.ASYNC_QUERY_SERVICE,
        "serviceVariant" : AcePlotterServiceImpl.SERVICE_VARIANT,
        "timeout" : 60,
        "inputParams" : [
          "timeRanges" : ["timeRanges" : [type : TimeRange.class]]
        ],
        "outputParams" : [
            "url" : [id : "url", label: "ACE Plot", type : "url" ],
        ]
      ],
      "staplot" :  [
        "label" : "STEREO-A timeline plot",
        "description" : "STEREO-A timeline plot",
        "serviceName" : HelioServiceName.DES,
        "serviceCapability" : ServiceCapability.ASYNC_QUERY_SERVICE,
        "serviceVariant" : StaPlotterServiceImpl.SERVICE_VARIANT,
        "timeout" : 60,
        "inputParams" : [
          "timeRanges" : ["timeRanges" : [type : TimeRange.class]]
        ],
        "outputParams" : [
          "url" : [id : "url", label: "STEREO-A Plot", type : "url" ],
        ]
      ],
      "stbplot" :  [
          "label" : "STEREO-B timeline plot",
          "description" : "STEREO-B timeline plot",
          "serviceName" : HelioServiceName.DES,
          "serviceCapability" : ServiceCapability.ASYNC_QUERY_SERVICE,
          "serviceVariant" : StbPlotterServiceImpl.SERVICE_VARIANT,
          "timeout" : 60,
          "inputParams" : [
            "timeRanges" : ["timeRanges" : [type : TimeRange.class]]
          ],
          "outputParams" : [
            "url" : [id : "url", label: "STEREO-B Plot", type : "url" ],
          ]
      ],
      "ulyssesplot" :  [
        "label" : "Ulysses timeline plot",
        "description" : "Ulysses timeline plot",
        "serviceName" : HelioServiceName.DES,
        "serviceCapability" : ServiceCapability.ASYNC_QUERY_SERVICE,
        "serviceVariant" : StaPlotterServiceImpl.SERVICE_VARIANT,
        "timeout" : 60,
        "inputParams" : [
          "timeRanges" : ["timeRanges" : [type : TimeRange.class]]
        ],
        "outputParams" : [
          "url" : [id : "url", label: "Ulysses Plot", type : "url" ],
        ]
      ],
      "windplot" :  [
          "label" : "WIND timeline plot",
          "description" : "WIND timeline plot",
          "serviceName" : HelioServiceName.DES,
          "serviceCapability" : ServiceCapability.ASYNC_QUERY_SERVICE,
          "serviceVariant" : StaPlotterServiceImpl.SERVICE_VARIANT,
          "timeout" : 60,
          "inputParams" : [
            "timeRanges" : ["timeRanges" : [type : TimeRange.class]]
          ],
          "outputParams" : [
            "url" : [id : "url", label: "WIND Plot", type : "url" ],
          ]
      ],  
    ]}
    
    /**
     * Find a task descriptor by name
     * @param taskName
     * @return
     */
    static def findTaskDescriptor(taskName) {
        taskDescriptor[taskName]    
    }
}