package eu.heliovo.hfe.utils

import org.codehaus.groovy.grails.web.context.ServletContextHolder

import eu.heliovo.clientapi.processing.context.SimpleParkerModelService.PlotType
import eu.heliovo.clientapi.processing.context.impl.FlarePlotterServiceImpl
import eu.heliovo.clientapi.processing.context.impl.GoesPlotterServiceImpl
import eu.heliovo.clientapi.processing.context.impl.SimpleParkerModelServiceImpl
import eu.heliovo.clientapi.processing.context.impl.des.AcePlotterServiceImpl
import eu.heliovo.clientapi.processing.context.impl.des.StaPlotterServiceImpl
import eu.heliovo.clientapi.processing.context.impl.des.StbPlotterServiceImpl
import eu.heliovo.clientapi.processing.context.impl.des.UlyssesPlotterServiceImpl
import eu.heliovo.clientapi.processing.context.impl.des.WindPlotterServiceImpl
import eu.heliovo.clientapi.processing.hps.impl.CirBackwardPropagationModelImpl
import eu.heliovo.clientapi.processing.hps.impl.CirPropagationModelImpl
import eu.heliovo.clientapi.processing.hps.impl.CmeBackwardPropagationModelImpl
import eu.heliovo.clientapi.processing.hps.impl.CmePropagationModelImpl
import eu.heliovo.clientapi.processing.hps.impl.SepBackwardPropagationModelImpl
import eu.heliovo.clientapi.processing.hps.impl.SepPropagationModelImpl
import eu.heliovo.clientapi.processing.taverna.impl.TavernaWorkflow2283
import eu.heliovo.hfe.model.param.TimeRange
import eu.heliovo.registryclient.HelioServiceName
import eu.heliovo.registryclient.ServiceCapability

/**
 * Utility to get the task descriptor map. 
 * TODO: needs some refactoring (i.e. move to clientapi).
 * 
 * @author MarcoSoldati
 *
 */
class TaskDescriptor {
    
    def static Map<String, Map<String, Object>> taskDescriptor;
    
    def synchronized static  Map<String, Map<String, Object>> getTaskDescriptor() {
        if (taskDescriptor == null) {
            taskDescriptor = initTaskDescriptors();
        }
        taskDescriptor;
    }
    
    /**
    * Create task descriptors, if required.
    * @return
    */
    private static def initTaskDescriptors() {
        // objects that could be hit by a propagation model
        def hitObjectDomain = ["Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto", 
                          "Cassini", "Dawn", "Galileo", "Messenger", "NewHorizons", "Rosetta", "StereoA", "StereoB", 
                          "Ulysses", "Voyager1", "Voyager2"]
        
        def tav2283ValueDomain = ["goes_sxr_flare", "ngdc_halpha_flare", "noaa_energetic_event", "yohkoh_hxr_flare",
            "kso_halpha_flare", "stereo_euvi_event", "ulysses_hxr_flare",
            "timed_see_flare", "goes_flare_sep_event"]
        
        def eventListModel = ServletContextHolder.servletContext.eventListModel
        def instrumentDescriptor =  ServletContextHolder.servletContext.instrumentDescriptors
        
      /************* PROPAGATION MODEL *****************/
      [
      "votableupload" : [
        "label" : "Upload VOTable",
        "description" : "Tool to uppload any valid VOTable",
      ],
      "datacart" : [
        "label" : "Data Cart",
        "description" : "Internal task used for the data cart",
        "inputParams" : [
            "timeRanges" : ["timeRanges" : [type : TimeRange.class]],
            "paramSet" : [
            ],
            "instruments" :  [
                "instruments" : [label : "Instruments", description : "Name of the Instrument", type : String[],
                    defaultValue : [],
                    selectionDescriptor: instrumentDescriptor]
            ]
        ]
      ],
      "pm_cme_fw" : [
        "label" : "CME Forward Propagation Model (from Sun to objects)",
        "description" : "CME Propagation Model from the Sun to a collection of predefined objects.",
        "serviceName" : HelioServiceName.HPS,
        "serviceCapability" : ServiceCapability.HELIO_PROCESSING_SERVICE,
        "serviceVariant" : CmePropagationModelImpl.SERVICE_VARIANT,
        "helpImage" : "exp_cme.png",
        "inputParams" : [
            "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: 'single_start_time']],
            "paramSet" : [
                "longitude" : [label : "Longitude", description : "Heliographic longitude in degrees (e.g., the position of a flare)", type : Float, defaultValue : 0],
                "width" : [label : "Width", description : "Longitudinal width of the CME in degrees", type : Float, defaultValue : 45.0],
                "speed" : [label : "Speed", description : "CME speed in km/s", type : Float, defaultValue : 800],
                "speedError" : [label : "SpeedError &plusmn;", description : "Error in the speed in km/s", type : Float, defaultValue : 0]
            ]
        ],
        "outputParams" : [
            "voTableUrl" : [id : "voTableUrl", label: "VOTable", type : "votable" ],
            "innerPlotUrl" : [id : "innerPlotUrl", label: "Plot of inner planets", type : "url" ],
            "outerPlotUrl" : [id : "outerPlotUrl", label: "Plot of outer planets", type : "url" ],
            "voyagerPlotUrl" : [id : "voyagerPlotUrl", label: "Plot containing voyager", type : "url" ]
        ]
      ],
      "pm_cme_back" : [
          "label" : "CME Backward Propagation Model (from planet or satellite to Sun)",
          "description" : "CME Propagation Model from a collection of predefined objects back to the Sun.",
          "serviceName" : HelioServiceName.HPS,
          "serviceCapability" : ServiceCapability.HELIO_PROCESSING_SERVICE,
          "serviceVariant" : CmeBackwardPropagationModelImpl.SERVICE_VARIANT,
          "helpImage" : "exp_cme.png",
          "inputParams" : [
              "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: 'single_start_time']],
              "paramSet" : [
                  "hitObject" : [label : "Object", description : "Planet or Satellite hit by the CME", type : String, defaultValue : "Earth", 
                      valueDomain : hitObjectDomain],
                  "speed" : [label : "Speed", description : "CME speed in km/s", type : Float, defaultValue : 800],
                  "speedError" : [label : "SpeedError &plusmn;", description : "Error in the speed in km/s", type : Float, defaultValue : 0],
                  "width" : [label : "Width", description : "Longitudinal width of the CME in degrees", type : Float, defaultValue : 45.0],
              ]
          ],
          "outputParams" : [
              "voTableUrl" : [id : "voTableUrl", label: "VOTable", type : "votable" ],
              "innerPlotUrl" : [id : "innerPlotUrl", label: "Plot of inner planets", type : "url" ],
              "outerPlotUrl" : [id : "outerPlotUrl", label: "Plot of outer planets", type : "url" ],
              "voyagerPlotUrl" : [id : "voyagerPlotUrl", label: "Plot containing voyager", type : "url" ]
          ]
      ],
      "pm_cir_fw" : [
          "label" : "Co-rotate Interaction Region (CIR) Propagation Model (from Sun to object)",
          "description" : "Co-rotate Interaction Region (CIR) Propagation Model from the Sun to a collection of predefined objects.",
          "serviceName" : HelioServiceName.HPS,
          "serviceCapability" : ServiceCapability.HELIO_PROCESSING_SERVICE,
          "serviceVariant" : CirPropagationModelImpl.SERVICE_VARIANT,
          "helpImage" : "exp_sw.png",
          "inputParams" : [
              "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: 'single_start_time']],
              "paramSet" : [
                  "longitude" : [label : "Longitude", description : "Heliographic longitude in degrees (e.g., the most-west edge of a Coronal hole)", type : Float, defaultValue : 0],
                  "speed" : [label : "Speed", description : "The speed of the Solar Wind in km/s", type : Float, defaultValue : 600],
                  "speedError" : [label : "SpeedError &plusmn;", description : "Error in the speed in km/s", type : Float, defaultValue : 0],
              ]
          ],
          "outputParams" : [
              "voTableUrl" : [id : "voTableUrl", label: "VOTable", type : "votable" ],
              "innerPlotUrl" : [id : "innerPlotUrl", label: "Plot of inner planets", type : "url" ],
              "outerPlotUrl" : [id : "outerPlotUrl", label: "Plot of outer planets", type : "url" ],
          ]
      ],
      "pm_cir_back" : [
          "label" : "Co-rotate Interaction Region (CIR) Propagation Model (from object to Sun)",
          "description" : "Co-rotate Interaction Region (CIR) Propagation Model from a planet or instrument to the Sun.",
          "serviceName" : HelioServiceName.HPS,
          "serviceCapability" : ServiceCapability.HELIO_PROCESSING_SERVICE,
          "serviceVariant" : CirBackwardPropagationModelImpl.SERVICE_VARIANT,
          "helpImage" : "exp_sw.png",
          "inputParams" : [
              "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: 'single_start_time']],
              "paramSet" : [
                  "hitObject" : [label : "Object", description : "Planet or Satellite hit by the CME", type : String, defaultValue : "Earth",
                      valueDomain : hitObjectDomain],
                  "speed" : [label : "Speed", description : "The speed of the Solar Wind in km/s", type : Float, defaultValue : 600],
                  "speedError" : [label : "SpeedError &plusmn;", description : "Error in the speed in km/s", type : Float, defaultValue : 0],
              ]
          ],
          "outputParams" : [
              "voTableUrl" : [id : "voTableUrl", label: "VOTable", type : "votable" ],
              "innerPlotUrl" : [id : "innerPlotUrl", label: "Plot of inner planets", type : "url" ],
              "outerPlotUrl" : [id : "outerPlotUrl", label: "Plot of outer planets", type : "url" ],
          ]
      ],
      "pm_sep_fw" : [
          "label" : "Solar Energetic Particles Propagation Model (from Sun to objects)",
          "description" : "Solar Energetic Particles from the Sun to a collection of predefined objects.",
          "serviceName" : HelioServiceName.HPS,
          "serviceCapability" : ServiceCapability.HELIO_PROCESSING_SERVICE,
          "serviceVariant" : SepPropagationModelImpl.SERVICE_VARIANT,
          "helpImage" : "exp_sep.png",
          "inputParams" : [
              "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: 'single_start_time']],
              "paramSet" : [
                  "longitude" : [label : "Longitude", description : "Heliographic longitude in degrees (e.g., the position of a flare)", type : Float, defaultValue : 0],
                  "speed" : [label : "Speed", description : "Speed of the ambient solar wind in km/s", type : Float, defaultValue : 600],
                  "speedError" : [label : "SpeedError &plusmn;", description : "Error in the speed of the solar wind in km/s", type : Float, defaultValue : 0],
                  "beta" : [label : "Beta", description : "Fraction of lightspeed", type : Float, defaultValue : 0.9],
              ]
          ],
          "outputParams" : [
              "voTableUrl" : [id : "voTableUrl", label: "VOTable", type : "votable" ],
              "innerPlotUrl" : [id : "innerPlotUrl", label: "Plot of inner planets", type : "url" ],
              "outerPlotUrl" : [id : "outerPlotUrl", label: "Plot of outer planets", type : "url" ],
          ]
      ],
      "pm_sep_back" : [
          "label" : "Solar Energetic Particles Propagation Model (from object to Sun)",
          "description" : "Solar Energetic Particles from a planet/satellite to the Sun.",
          "serviceName" : HelioServiceName.HPS,
          "serviceCapability" : ServiceCapability.HELIO_PROCESSING_SERVICE,
          "serviceVariant" : SepBackwardPropagationModelImpl.SERVICE_VARIANT,
          "helpImage" : "exp_sep.png",
          "inputParams" : [
              "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: 'single_start_time']],
              "paramSet" : [
                  "hitObject" : [label : "Object", description : "Planet or Satellite hit by the CME", type : String, defaultValue : "Earth",
                      valueDomain : hitObjectDomain],
                  "speed" : [label : "Speed", description : "Speed of the ambient solar wind in km/s", type : Float, defaultValue : 600],
                  "speedError" : [label : "SpeedError &plusmn;", description : "Error in the speed of the solar wind in km/s", type : Float, defaultValue : 0],
                  "beta" : [label : "Beta", description : "Fraction of lightspeed", type : Float, defaultValue : 0.9],
              ]
          ],
          "outputParams" : [
              "voTableUrl" : [id : "voTableUrl", label: "VOTable", type : "votable" ],
              "innerPlotUrl" : [id : "innerPlotUrl", label: "Plot of inner planets", type : "url" ],
              "outerPlotUrl" : [id : "outerPlotUrl", label: "Plot of outer planets", type : "url" ],
          ]
      ],
/************* TAVERNA *****************/
      "tav_2283" : [
        "label" : "Crossmatching of two HELIO event lists",
        "description" : "Merge two HELIO event lists",
        "serviceName" : HelioServiceName.TAVERNA_SERVER,
        "serviceCapability" : ServiceCapability.TAVERNA_SERVER,
        "serviceVariant" : TavernaWorkflow2283.SERVICE_VARIANT,
        "inputParams" : [
            "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: 'single_time_range']],
            "paramSet" : [
                "catalogue1" : [label : "Catalogue 1", description : "1st Event list (not all HEC lists are supported)", type : String, defaultValue : "goes_sxr_flare", 
                    valueDomain: tav2283ValueDomain],
                "catalogue2" : [label : "Catalogue 2", description : "2nd Event list", type : String, defaultValue : "ngdc_halpha_flare",
                    valueDomain: tav2283ValueDomain],
                "timeDelta" : [label : "Time delta", description : "Max time delta between the two lists in seconds", type : Integer, defaultValue : 0],
                "locationDelta" : [label : "Location delta", description : "Max delta in degrees", type : Double, defaultValue : 1.5d],
            ]
        ],
        "outputParams" : [
            "voTableUrl" : [id : "voTableUrl", label: "VOTable", type : "votable" ],
        ]
      ],
/************* PLOTS *****************/
      "goesplot" : [
        "label" : "GOES timeline plot",
        "description" : "GOES timeline plot",
        "serviceName" : HelioServiceName.CXS,
        "serviceCapability" : ServiceCapability.COMMON_EXECUTION_ARCHITECTURE_SERVICE,
        "serviceVariant" : GoesPlotterServiceImpl.SERVICE_VARIANT,
        "timeout" : 60,  // timeout in seconds.
        "inputParams" : [
         "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: 'single_time_range']],
         "paramSet" : [
           "plotType" : [label: "Plot type", description : "Choose the kind of GOES data to plot", 
                        type : eu.heliovo.clientapi.processing.context.GoesPlotterService.PlotType, 
                        defaultValue : eu.heliovo.clientapi.processing.context.GoesPlotterService.PlotType.BOTH]]
        ],
        "outputParams" : [
          "url" : [id : "url", label: "GOES Plot", type : "url" ],
        ]
      ],
      "flareplot" : [
        "label" : "Flare Plot",
        "description" : "A flare plot coming from different sources depending on the time range.",
        "serviceName" : HelioServiceName.CXS,
        "serviceCapability" : ServiceCapability.COMMON_EXECUTION_ARCHITECTURE_SERVICE,
        "serviceVariant" : FlarePlotterServiceImpl.SERVICE_VARIANT,
        "timeout" : 60,
        "inputParams" : [
          "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: 'single_start_time']]
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
          "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: 'single_start_time']],
          "paramSet" : [
            "velocity" : [label : "Velocity", description : "Velocity in km/s (would speed be the better term?)", type : int, defaultValue : 400],
            "plotType" : [label : "Area to plot", description : "Plot inner or outer planets", 
                          type : PlotType, 
                          defaultValue : PlotType.INNER]
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
        "serviceCapability" : ServiceCapability.COMMON_EXECUTION_ARCHITECTURE_SERVICE,
        "serviceVariant" : AcePlotterServiceImpl.SERVICE_VARIANT,
        "timeout" : 60,
        "inputParams" : [
          "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: 'single_time_range']]
        ],
        "outputParams" : [
            "url" : [id : "url", label: "ACE Plot", type : "url" ],
        ]
      ],
      "staplot" :  [
        "label" : "STEREO-A timeline plot",
        "description" : "STEREO-A timeline plot",
        "serviceName" : HelioServiceName.DES,
        "serviceCapability" : ServiceCapability.COMMON_EXECUTION_ARCHITECTURE_SERVICE,
        "serviceVariant" : StaPlotterServiceImpl.SERVICE_VARIANT,
        "timeout" : 60,
        "inputParams" : [
          "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: 'single_time_range']]
        ],
        "outputParams" : [
          "url" : [id : "url", label: "STEREO-A Plot", type : "url" ],
        ]
      ],
      "stbplot" :  [
          "label" : "STEREO-B timeline plot",
          "description" : "STEREO-B timeline plot",
          "serviceName" : HelioServiceName.DES,
          "serviceCapability" : ServiceCapability.COMMON_EXECUTION_ARCHITECTURE_SERVICE,
          "serviceVariant" : StbPlotterServiceImpl.SERVICE_VARIANT,
          "timeout" : 60,
          "inputParams" : [
            "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: 'single_time_range']]
          ],
          "outputParams" : [
            "url" : [id : "url", label: "STEREO-B Plot", type : "url" ],
          ]
      ],
      "ulyssesplot" :  [
        "label" : "Ulysses timeline plot",
        "description" : "Ulysses timeline plot",
        "serviceName" : HelioServiceName.DES,
        "serviceCapability" : ServiceCapability.COMMON_EXECUTION_ARCHITECTURE_SERVICE,
        "serviceVariant" : UlyssesPlotterServiceImpl.SERVICE_VARIANT,
        "timeout" : 60,
        "inputParams" : [
          "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: 'single_time_range']]
        ],
        "outputParams" : [
          "url" : [id : "url", label: "Ulysses Plot", type : "url" ],
        ]
      ],
      "windplot" :  [
          "label" : "WIND timeline plot",
          "description" : "WIND timeline plot",
          "serviceName" : HelioServiceName.DES,
          "serviceCapability" : ServiceCapability.COMMON_EXECUTION_ARCHITECTURE_SERVICE,
          "serviceVariant" : WindPlotterServiceImpl.SERVICE_VARIANT,
          "timeout" : 60,
          "inputParams" : [
            "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: 'single_time_range']]
          ],
          "outputParams" : [
            "url" : [id : "url", label: "WIND Plot", type : "url" ],
          ]
      ],
      "eventlist" :  [
          "label" : "HELIO Event Catalogue",
          "description" : "Query the HELIO Event Catalogue",
          "serviceName" : HelioServiceName.HEC,
          "serviceCapability" : ServiceCapability.SYNC_QUERY_SERVICE,
          "serviceVariant" : null,
          "timeout" : 120,
          "inputParams" : [
            "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: 'multi_time_range']],
            "eventList" :  [
                "listNames" : [label : "Event List", description : "Name of the Event List", type : String[], 
                    defaultValue : [], 
                    selectionTable: eventListModel]
                ]
          ],
          "outputParams" : [
            "voTableUrl" : [id : "voTableUrl", label: "VOTable", type : "votable" ],
          ]
      ],
      "dataaccess" :  [
        "label" : "Data Access Service",
        "description" : "Query for data",
        "serviceName" : HelioServiceName.DPAS,
        "serviceCapability" : ServiceCapability.SYNC_QUERY_SERVICE,
        "serviceVariant" : null,
        "timeout" : 180,
        "inputParams" : [
            "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: 'multi_time_range']],
            "instruments" :  [
                "instruments" : [label : "Instruments", description : "Name of the Instrument", type : String[],
                    defaultValue : [],
                    selectionDescriptor: instrumentDescriptor]
            ]
        ],
        "outputParams" : [
            "voTableUrl" : [id : "voTableUrl", label: "VOTable", type : "votable" ],
        ]
      ],
      "ils" :  [
        "label" : "Locate planets/intruments by time",
        "description" : "Locate planets/intruments by time",
        "serviceName" : HelioServiceName.ILS,
        "serviceCapability" : ServiceCapability.SYNC_QUERY_SERVICE,
        "serviceVariant" : null,
        "from" : "trajectories",
        "resultfilter" : "ilsfilter", // name and id of the gsp page to use as result filter
        "timeout" : 180,
        "inputParams" : [
            "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: 'single_time_range']],
        ],
        "outputParams" : [
            "voTableUrl" : [id : "voTableUrl", label: "VOTable", type : "votable" ],
        ]
      ],
      "ics" :  [
          "label" : "Find instruments by capability",
          "description" : "Find instruments by capability",
          "serviceName" : HelioServiceName.ICS,
          "serviceCapability" : ServiceCapability.ASYNC_QUERY_SERVICE,
          "serviceVariant" : "ivo://helio-vo.eu/ics/ics_pat",
          "from" : "instrument",
          "resultfilter" : "icsfilter", // name and id of the gsp page to use as result filter
          "timeout" : 180,
          "inputParams" : [
              "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: 'single_time_range']],
          ],
          "outputParams" : [
              "voTableUrl" : [id : "voTableUrl", label: "VOTable", type : "votable" ],
          ]
      ],
    ]}
    
    /**
     * Find a task descriptor by name
     * @param taskName
     * @return
     */
    static def findTaskDescriptor(taskName) {
        getTaskDescriptor()[taskName]    
    }
}