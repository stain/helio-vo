/**
 * The helio name space
 */
// use existing global 'helio' (here, 'this' === window/global context)
// or create new object/package
this.helio = this.helio ||
  { toString: function() { return 'package: helio'; } };

(function() {
      
   // map of previously stored tasks
   this.helio.TaskMap = function() {
       this.tasks = new Object();
   };

   /**
    * Find task in the task map
    * 
    * @param {String}
    *            taskName name of the task to find.
    * @return the found task or undefined if not found.
    */
   this.helio.TaskMap.prototype.findByName = function(taskName) {
       return this.tasks[taskName];
   };

   /**
    * Put task into map.
    * 
    * @param {String}
    *            taskName name of the task to find.
    * @param {helio.AbstractTask}
    *            task the task instance to persist.
    * @return the found task or undefined if not found.
    */
   this.helio.TaskMap.prototype.put = function(taskName, task) {
       this.tasks[taskName] = task;
   };
   
   // an extension to use JSON through a post request.
   jQuery.extend({
       postJSON: function( url, data, callback) {
          return jQuery.post(url, data, callback, "json");
       }
   });
   
   jQuery.escapeHTML = function (text){
       return text.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
   };

   // decorate moments object with a custom to json method
   moment.fn.toJSON = function() {
       return this.format("YYYY-MM-DDTHH:mm:ss");
   };
})();      


  
/**
 * Base module for the HELIO project. This is used to initialize the HELIO
 * project and provides some generic classes.
 */
$(document).ready(function() {
    jQuery('ul.sf-menu').superfish({
        speed:  'fast',
        delay:  '600',
        autoArrows: false,
        animation:  {opacity:'show'}
    });
    
    //create and init the data cart
    helio.dataCart = new helio.DataCart();

    // provde global access to the task map
    helio.taskMap = new helio.TaskMap();

    // init menu tabs
    $( "#tabs" ).tabs();//inits the main task selector

    // generic configuration of the menu
    var menuConfig = {
        "task_upload2" : {
            "taskName" : "votableupload",
            "taskConstructor" : function(taskName) { return new helio.VOTableUploadTask("votableupload"); }
        },
        "task_eventlist" : {
            "taskName" : "eventlist",
            "taskConstructor" : function(taskName) { return new helio.EventListTask(taskName); }
        },
        "task_dataaccess" : {
            "taskName" : "dataaccess",
            "taskConstructor" : function(taskName) { return new helio.DataAccessTask(taskName); }
        },
        "task_ics" : {
            "taskName" : "ics",
            "taskConstructor" : function(taskName) { return new helio.IcsTask(taskName); }
        },
        "task_ils" : {
            "taskName" : "ils",
            "taskConstructor" : function(taskName) { return new helio.IlsTask(taskName); }
        },
        "task_propagationmodel" : {
            "menuitems" : {
                "pm_cme_fw" : { title: "CME Forward PM", taskConstructor : function(taskName) {return new helio.PropagationModelTask(taskName);}},
                "pm_cme_back" : { title: "CME Backward PM", taskConstructor : function(taskName) {return new helio.PropagationModelTask(taskName);}},
                "pm_cir_fw" : { title: "CIR Forward PM", taskConstructor : function(taskName) {return new helio.PropagationModelTask(taskName);}},
                "pm_cir_back" : { title: "CIR Backward PM", taskConstructor : function(taskName) {return new helio.PropagationModelTask(taskName);}},
                "pm_sep_fw" : { title: "SEP Forward PM", taskConstructor : function(taskName) {return new helio.PropagationModelTask(taskName);}},
                "pm_sep_back" : { title: "SEP Backward PM", taskConstructor : function(taskName) {return new helio.PropagationModelTask(taskName);}},
            }
        },
        "task_plotservice" : {
            "menuitems" : {
                "goesplot" : { title: "GOES timeline plot", taskConstructor : function(taskName) {return new helio.PlotTask(taskName);}},
                "flareplot" : { title: "Flare plot", taskConstructor : function(taskName) {return new helio.PlotTask(taskName);}},
                "parkerplot" : { title: "Parker spiral plot", taskConstructor : function(taskName) {return new helio.PlotTask(taskName);}},
                "aceplot" : { title: "ACE timeline plot", taskConstructor : function(taskName) {return new helio.PlotTask(taskName);}},
                "staplot" : { title: "STEREO-A timeline plot", taskConstructor : function(taskName) {return new helio.PlotTask(taskName);}},
                "stbplot" : { title: "STEREO-B timeline plot", taskConstructor : function(taskName) {return new helio.PlotTask(taskName);}},
                "ulyssesplot" : { title: "Ulysses timeline plot", taskConstructor : function(taskName) {return new helio.PlotTask(taskName);}},
                "windplot" : { title: "Wind timeline plot", taskConstructor : function(taskName) {return new helio.PlotTask(taskName);}},
            }
        },
        "task_taverna" : {
            "menuitems" : {
                "tav_2283" : { "title" : "Combine Event Lists", taskConstructor : function(taskName) { return new helio.PropagationModelTask(taskName); }}
            }
        },
        "task_datamining" : {
            "taskName" : "des",
            "taskConstructor" : function(taskName) { return new helio.DesTask(taskName); }
        },
    };

    $('#misc_splash').click(function() {
        $('#content').empty().load('misc/splash', function() {
            // init splash buttons
        });
    });

    $('#misc_changelog').click(function() {
        $('#content').empty().load('misc/changelog');
    });
    
    $('#misc_help').click(function() {
        // open help in new window
    });

    
    var attachClickHandler = function(taskName, config) {
        $("#task_"+taskName).click(function() {
            $('#content').empty().load('./task/load?taskName=' + taskName, function() {
                var task = helio.taskMap.findByName(taskName);
                if (!task) {
                    task = config.taskConstructor.call(this, taskName);
                    helio.taskMap.put(taskName, task);
                }
                task.init.call(task);
                $('ul.sf-menu').hideSuperfishUl();
            });
        });
    };

    // loop over config and fill menu object.
    for (var menuName in menuConfig) {
        var config = menuConfig[menuName];
        if (config.menuitems) {
            for (var taskName in config.menuitems) {
                var subMenuItemConfig = config.menuitems[taskName];
                attachClickHandler(taskName, subMenuItemConfig);
            }
        } else if (config.taskName) {
            attachClickHandler(config.taskName, config);
        } else {
            throw "unknown menu config " + config;
        }
    }


//    $("#advanced_tab").click();
//    $("#task_plotservice_menu").click();
//    $("#task_plotservice_parker").click();
    
    //$('#task_ics').click();
    
    
});