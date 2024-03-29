package eu.heliovo.hfe.service

import org.springframework.beans.factory.InitializingBean

import eu.heliovo.clientapi.config.des.DesFunctionArgument
import eu.heliovo.clientapi.config.des.DesFunctionArgument.DesFunctionOperator
import eu.heliovo.clientapi.model.catalog.descriptor.EventListDescriptor;
import eu.heliovo.clientapi.model.catalog.descriptor.InstrumentDescriptor;
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
 * Service to load the task descriptor
 * @author MarcoSoldati
 *
 */
class TaskDescriptorService implements InitializingBean {

    static transactional = false;
    
    /**
     * Auto wire the des configuration bean
     */
    def desConfiguration;
    
    
    def taskDescriptor;

    /**
     * Use the configuration manager to get the DPAS instruments.    
     */
    def transient configurationManager
    
    
    /**
     * Create task descriptors, if required.
     * @return
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // objects that could be hit by a propagation model
        [value : "",
        label : ",",
        description : ""]
        def hitObjectDomain = [
            [label:"Mercury", value:"Mercury", description:"Mercury"],
            [label:"Venus", value: "Venus", description: "Venus"],
            [label:"Earth", value: "Earth", description: "Earth"],
            [label:"Mars", value: "Mars", description: "Mars"],
            [label:"Jupiter", value: "Jupiter", description: "Jupiter"],
            [label:"Saturn", value: "Saturn", description: "Saturn"],
            [label:"Uranus", value: "Uranus", description: "Uranus"],
            [label:"Neptune", value: "Neptune", description: "Neptune"],
            [label:"Pluto", value: "Pluto", description: "Pluto"],
            [label:"Cassini", value: "Cassini", description: "Cassini"],
            [label:"Dawn", value: "Dawn", description: "Dawn"],
            [label:"Galileo", value: "Galileo", description: "Galileo"],
            [label:"Messenger", value: "Messenger", description: "Messenger"],
            [label:"NewHorizons", value: "NewHorizons", description: "NewHorizons"],
            [label:"Rosetta", value: "Rosetta", description: "Rosetta"],
            [label:"StereoA", value: "StereoA", description: "StereoA"],
            [label:"StereoB", value: "StereoB", description: "StereoB"],
            [label:"Ulysses", value: "Ulysses", description: "Ulysses"],
            [label:"Voyager1", value: "Voyager1", description: "Voyager1"],
            [label:"Voyager2", value: "Voyager2", description: "Voyager2"]]
        
        def tav2283ValueDomain = [
            [label:"goes_sxr_flare", value: "goes_sxr_flare", description: "goes_sxr_flare"],
            [label:"ngdc_halpha_flare", value: "ngdc_halpha_flare", description: "ngdc_halpha_flare"],
            [label:"noaa_energetic_event", value: "noaa_energetic_event", description: "noaa_energetic_event"],
            [label:"yohkoh_hxr_flare", value: "yohkoh_hxr_flare", description: "yohkoh_hxr_flare"],
            [label:"kso_halpha_flare", value: "kso_halpha_flare", description: "kso_halpha_flare"],
            [label:"stereo_euvi_event", value: "stereo_euvi_event", description: "stereo_euvi_event"],
            [label:"ulysses_hxr_flare", value: "ulysses_hxr_flare", description: "ulysses_hxr_flare"],
            [label:"timed_see_flare", value: "timed_see_flare", description: "timed_see_flare"],
            [label:"goes_flare_sep_event", value: "goes_flare_sep_event", description: "goes_flare_sep_event"]]
        
        //def eventListModel = ServletContextHolder.servletContext.eventListModel
        List<EventListDescriptor> eventListDescriptors = configurationManager.getCatalogueDescriptors(HelioServiceName.HEC, null)
        List<InstrumentDescriptor> instrumentDescriptors = configurationManager.getCatalogueDescriptors(HelioServiceName.DPAS, null)
        
      /************* PROPAGATION MODEL *****************/
      this.taskDescriptor =
      [
          "votableupload" : [
            "label" : "Upload VOTable",
            "description" : "Tool to uppload any valid VOTable",
            "template" : "/task/votableupload"
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
                        valueDomain: instrumentDescriptors]
                ]
            ]
          ],
          "pm_cme_fw" : [
            "label" : "CME Forward Propagation Model (from Sun to objects)",
            "description" : "CME Propagation Model from the Sun to a collection of predefined objects.",
            "serviceName" : HelioServiceName.HPS,
            "serviceCapability" : ServiceCapability.HELIO_PROCESSING_SERVICE,
            "serviceVariant" : CmePropagationModelImpl.SERVICE_VARIANT,
			"help" : [ position: "bottom", template: "/dialog/paramSetHelpImage", 
				model : ["imageDir" : "images/helio/hps", "helpImage" : "exp_cme.png"]],
            "resultfilter" : "hpsfilter", // name and id of the gsp page to use as result filter
            "inputParams" : [
                "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "single_start_time"]],
                "paramSet" : [
                    "longitude" : [label : "Longitude", description : "Heliographic longitude in degrees (e.g., the position of a flare)", type : [javaType: Float], defaultValue : 0],
                    "width" : [label : "Width", description : "Longitudinal width of the CME in degrees", type : [javaType: Float], defaultValue : 45.0],
                    "speed" : [label : "Speed", description : "CME speed in km/s", type : [javaType: Float], defaultValue : 800],
                    "speedError" : [label : "SpeedError &plusmn;", description : "Error in the speed in km/s", type : [javaType: Float], defaultValue : 0]
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
			  "help" : [ position: "bottom", template: "/dialog/paramSetHelpImage",
				  model : ["imageDir" : "images/helio/hps", "helpImage" : "exp_cme.png"]],
              "resultfilter" : "hpsfilter", // name and id of the gsp page to use as result filter
              "inputParams" : [
                  "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "single_start_time"]],
                  "paramSet" : [
                      "hitObject" : [label : "Object", description : "Planet or Satellite hit by the CME", type : [javaType: String], defaultValue : "Earth", 
                          valueDomain : hitObjectDomain],
                      "speed" : [label : "Speed", description : "CME speed in km/s", type : [javaType: Float], defaultValue : 800],
                      "speedError" : [label : "SpeedError &plusmn;", description : "Error in the speed in km/s", type : [javaType: Float], defaultValue : 0],
                      "width" : [label : "Width", description : "Longitudinal width of the CME in degrees", type : [javaType: Float], defaultValue : 45.0],
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
              "label" : "Co-rotating Interaction Region (CIR) Propagation Model (from Sun to object)",
              "description" : "Co-rotating Interaction Region (CIR) Propagation Model from the Sun to a collection of predefined objects.",
              "serviceName" : HelioServiceName.HPS,
              "serviceCapability" : ServiceCapability.HELIO_PROCESSING_SERVICE,
              "serviceVariant" : CirPropagationModelImpl.SERVICE_VARIANT,
			  "help" : [ position: "bottom", template: "/dialog/paramSetHelpImage",
				  model : ["imageDir" : "images/helio/hps", "helpImage" : "exp_sw.png"]],
              "resultfilter" : "hpsfilter", // name and id of the gsp page to use as result filter
              "inputParams" : [
                  "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "single_start_time"]],
                  "paramSet" : [
                      "longitude" : [label : "Longitude", description : "Heliographic longitude in degrees (e.g., the most-west edge of a Coronal hole)", type : [javaType: Float], defaultValue : 0],
                      "speed" : [label : "Speed", description : "The speed of the Solar Wind in km/s", type : [javaType: Float], defaultValue : 600],
                      "speedError" : [label : "SpeedError &plusmn;", description : "Error in the speed in km/s", type : [javaType: Float], defaultValue : 0],
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
			  "help" : [ position: "bottom", template: "/dialog/paramSetHelpImage",
				  model : ["imageDir" : "images/helio/hps", "helpImage" : "exp_sw.png"]],
              "resultfilter" : "hpsfilter", // name and id of the gsp page to use as result filter
              "inputParams" : [
                  "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "single_start_time"]],
                  "paramSet" : [
                      "hitObject" : [label : "Object", description : "Planet or Satellite hit by the CME", type : [javaType: String], defaultValue : "Earth",
                          valueDomain : hitObjectDomain],
                      "speed" : [label : "Speed", description : "The speed of the Solar Wind in km/s", type : [javaType: Float], defaultValue : 600],
                      "speedError" : [label : "SpeedError &plusmn;", description : "Error in the speed in km/s", type : [javaType: Float], defaultValue : 0],
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
			  "help" : [ position: "bottom", template: "/dialog/paramSetHelpImage",
				  model : ["imageDir" : "images/helio/hps", "helpImage" : "exp_sep.png"]],
              "resultfilter" : "hpsfilter", // name and id of the gsp page to use as result filter
              "inputParams" : [
                  "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "single_start_time"]],
                  "paramSet" : [
                      "longitude" : [label : "Longitude", description : "Heliographic longitude in degrees (e.g., the position of a flare)", type : [javaType: Float], defaultValue : 0],
                      "speed" : [label : "Speed", description : "Speed of the ambient solar wind in km/s", type : [javaType: Float], defaultValue : 600],
                      "speedError" : [label : "SpeedError &plusmn;", description : "Error in the speed of the solar wind in km/s", type : [javaType: Float], defaultValue : 0],
                      "beta" : [label : "Beta", description : "Fraction of lightspeed", type : [javaType: Float], defaultValue : 0.9],
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
			  "help" : [ position: "bottom", template: "/dialog/paramSetHelpImage",
				  model : ["imageDir" : "images/helio/hps", "helpImage" : "exp_sep.png"]],
              "resultfilter" : "hpsfilter", // name and id of the gsp page to use as result filter
              "inputParams" : [
                  "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "single_start_time"]],
                  "paramSet" : [
                      "hitObject" : [label : "Object", description : "Planet or Satellite hit by the CME", type : [javaType: String], defaultValue : "Earth",
                          valueDomain : hitObjectDomain],
                      "speed" : [label : "Speed", description : "Speed of the ambient solar wind in km/s", type : [javaType: Float], defaultValue : 600],
                      "speedError" : [label : "SpeedError &plusmn;", description : "Error in the speed of the solar wind in km/s", type : [javaType: Float], defaultValue : 0],
                      "beta" : [label : "Beta", description : "Fraction of lightspeed", type : [javaType: Float], defaultValue : 0.9],
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
                "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "single_time_range"]],
                "paramSet" : [
                    "catalogue1" : [label : "Catalogue 1", description : "1st Event list (not all HEC lists are supported)", type : [javaType: String], defaultValue : "goes_sxr_flare", 
                        valueDomain: tav2283ValueDomain],
                    "catalogue2" : [label : "Catalogue 2", description : "2nd Event list", type : [javaType: String], defaultValue : "ngdc_halpha_flare",
                        valueDomain: tav2283ValueDomain],
                    "timeDelta" : [label : "Time delta", description : "Max time delta between the two lists in seconds", type : [javaType: Integer], defaultValue : 0],
                    "locationDelta" : [label : "Location delta", description : "Max delta in degrees", type : [javaType: Double], defaultValue : 1.5d],
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
             "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "single_time_range"]],
             "paramSet" : [
               "plotType" : [label: "Plot type", description : "Choose the kind of GOES data to plot", 
                            type : [javaType: eu.heliovo.clientapi.processing.context.GoesPlotterService.PlotType], 
                            defaultValue : eu.heliovo.clientapi.processing.context.GoesPlotterService.PlotType.PROTON]]
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
              "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "single_start_time"]]
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
              "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "single_start_time"]],
              "paramSet" : [
                "velocity" : [label : "Velocity", description : "Velocity in km/s (would speed be the better term?)", type : [javaType: int], defaultValue : 400],
                "plotType" : [label : "Area to plot", description : "Plot inner or outer planets", 
                              type : [javaType: PlotType], 
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
            "serviceCapability" : ServiceCapability.ASYNC_QUERY_SERVICE,
            "serviceVariant" : AcePlotterServiceImpl.SERVICE_VARIANT,
            "timeout" : 60,
            "inputParams" : [
              "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "single_time_range"]]
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
              "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "single_time_range"]]
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
                "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "single_time_range"]]
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
            "serviceVariant" : UlyssesPlotterServiceImpl.SERVICE_VARIANT,
            "timeout" : 60,
            "inputParams" : [
              "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "single_time_range"]]
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
              "serviceVariant" : WindPlotterServiceImpl.SERVICE_VARIANT,
              "timeout" : 60,
              "inputParams" : [
                "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "single_time_range"]]
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
			  "help" : [ position: "bottom", template: "/dialog/paramSetHelpHEC"],
              "inputParams" : [
                "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "multi_time_range"]],
                "eventList" :  [
                    "listNames" : [label : "Event List", description : "Name of the Event List", type : [javaType: String][], 
                        defaultValue : [], 
                        valueDomain: eventListDescriptors]
                    ],
				"paramSet" : [:]  /* dynamically populated */ 
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
                "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "multi_time_range"]],
                "instruments" :  [
                    "instruments" : [label : "Instruments", description : "Name of the Instrument", type : String[],
                        defaultValue : [],
                        valueDomain: instrumentDescriptors]
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
                "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "single_time_range"]],
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
                  "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "single_time_range"]],
              ],
              "outputParams" : [
                  "voTableUrl" : [id : "voTableUrl", label: "VOTable", type : "votable" ],
              ]
          ],
          "des" :  [
              "label" : "Insitu data mining",
              "description" : "Find event associated with insitu heliospheric data",
              "serviceName" : HelioServiceName.DES,
              "serviceCapability" : ServiceCapability.ASYNC_QUERY_SERVICE,
              "timeout" : 180,
              "inputParams" : [
                  "timeRanges" : ["timeRanges" : [type : TimeRange.class, restriction: "single_time_range"]],
                  "paramSet" : [
                      "mission" : [label : "Mission", description : "Select a mission", type : [javaType: String], defaultValue : "ACE", 
                          valueDomain: desConfiguration?.missions],
                      "function" : [label : "Function", 
                          description : "Select the function to apply to the data of the selected mission. Hover your mouse over the selection items to get more information.", 
                          type : [javaType: String], 
                          defaultValue : "DERRIV",
                          valueDomain: desConfiguration?.functions],
                      "parameter_param" : [label : "Parameter",
                          description : "Select the parameter to analyze by the function",
                          type : [javaType: String], 
                          defaultValue : "",
                          valueDomain: desConfiguration?.params,
                          /*template : "/dialog/_desParamSetParamRow"*/],
                      "parameter_operator" : [ label : "Operator",
                          description : "operator of the param",
                          type : [javaType: DesFunctionOperator],
                          defaultValue : DesFunctionOperator.GT,
                          render : false],
                      "parameter_value" : [label : "Value",
                          description : "Value for a specific parameter",
                          type : [javaType: Double],
                          defaultValue : 0,
                          render : false
                          ],
                      "parameter_avaragetime" : [label : "Average time",
                          description : "Average/Sampling time resolution - by default 600 sec",
                          type : [javaType: Double],
                          defaultValue : 0,
                          render : true
                          ],
                      "parameter_samplingwindow" : [label : "Sampling window",
                          description : "Average sampling window size",
                          type : [javaType: Double],
                          defaultValue : 60,
                          render : true
                          ],
                  ]
      
              ],
              "outputParams" : [
                  "voTableUrl" : [id : "voTableUrl", label: "VOTable", type : "votable" ],
              ]
          ],
      ]
    }
    
   /**
    * Find a task descriptor by name
    * @param taskName
    * @return
    */
   def findTaskDescriptor(taskName) {
       taskDescriptor[taskName]
   }
   
   /**
    * Find all tasks that implement the same service
    * @param serviceName the name of the service
    * @return collection of tasks with the given serviceName
    */
   def findTaskDescriptorByServiceName(serviceName) {
       taskDescriptor.find{ it.value?.serviceName == serviceName }
   }
   
   /**
    * Find a task descriptor 
    * @param taskName
    * @return
    */
   def findParamSetConfig() {
       def ret = [:]
       taskDescriptor.each{ 
           if (it.value.inputParams && it.value.inputParams.paramSet ) {
               ret[it.key] = it.value.inputParams.paramSet
           }
       }
       ret
   }
}
