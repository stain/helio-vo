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


   helio.DropdownMenu = function(id, options) {
       var THIS = this;
       this.id = id;
       this.options = options;
       
       // init the drop down menu
       $("#" + this.id + "_menu").menu();
       
       // click handler
       $("#" + this.id).button().click(function(e) {
           $("#" + THIS.id + "_menu").toggle();
           e.stopPropagation();
       })
       .next()
       .button({
           text: false,
           icons: {
               primary: "ui-icon-triangle-1-s"
           }
       })
       .click(function(e) {
           $("#" + THIS.id + "_menu").toggle();
           e.stopPropagation();
       })
       .parent()
       .buttonset();
       
       for(var menuItem in this.options) {
           $("#" + menuItem)
           .addClass('ui-state-default')
           .hover(function() {$(this).addClass('ui-state-hover');}, function() {$(this).removeClass('ui-state-hover');});
           $("#" + menuItem)
           .click( (function(menuItem) {
               return function() {
                   $("#" + THIS.id + "_menu").hide();
                   // change button text.
                   var title = THIS.options[menuItem].title;
                   $("#" + THIS.id + " span.ui-button-text").empty().html(title);
                   
                   // register click handler
                   $("#" + THIS.id).unbind("click");
                   var click = THIS.options[menuItem].click;
                   $("#" + THIS.id).click(click);
                   click.call(this);
               };
           }) (menuItem));
       };
       
       // register a global handler to hide the menu if clicked outside.
       $(document).click(function() {        
           $("#" + THIS.id + "_menu").hide();
       });
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
   
})();      


  
/**
 * Base module for the HELIO project. This is used to initialize the HELIO
 * project and provides some generic classes.
 */
$(document).ready(function() {
    // decorate moments object with a custom to json method
    moment.fn.toJSON = function() {
        return this.format("YYYY-MM-DDTHH:mm:ss");
    };
    
    //create and init the data cart
    helio.dataCart = new helio.DataCart();

    // provde global access to the task map
    helio.taskMap = new helio.TaskMap();

    // init menu tabs
    $( "#tabs" ).tabs();//inits the main task selector

    // generic configuration of the menu
    var menuConfig = {
        "task_upload2" : {
            "url"      : "./task/uploadVoTable",
            "taskName" : "votableupload",
            "taskConstructor" : function(taskName) { return new helio.VOTableUploadTask("votableupload"); }
        },
        "task_eventlist" : {
            "url"      : "./task/load?taskName=eventlist",
            "taskName" : "eventlist",
            "taskConstructor" : function(taskName) { return new helio.EventListTask(taskName); }
        },
        "task_dataaccess" : {
            "url"      : "./task/load?taskName=dataaccess",
            "taskName" : "dataaccess",
            "taskConstructor" : function(taskName) { return new helio.DataAccessTask(taskName); }
        },
        "task_ics" : {
            "url"      : "./task/load?taskName=ics",
            "taskName" : "ics",
            "taskConstructor" : function(taskName) { return new helio.IcsTask(taskName); }
        },
        "task_ils" : {
            "url"      : "./task/load?taskName=ils",
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
            "url"      : "./task/load?taskName=des",
            "taskName" : "des",
            "taskConstructor" : function(taskName) { return new helio.DesTask(taskName); }
        },
    };
    
    // loop over config and fill menu object.
    for (var menuName in menuConfig) {
        var config = menuConfig[menuName];
        if (config.menuitems) {
            var pmMenu = new Object();
            for (var subMenuItem in config.menuitems) {
                var subMenuItemConfig = config.menuitems[subMenuItem];
                var subMenuItemName = menuName + "_" + subMenuItem; 
                pmMenu[subMenuItemName] = { 
                    "title": subMenuItemConfig.title,
                    "click": (function(taskName, subMenuItemConfig) {
                        return function() {
                            $('#content').load('./task/load?taskName=' + taskName, function() {
                                var task = helio.taskMap.findByName(taskName);
                                if (!task) {
                                    task = subMenuItemConfig.taskConstructor.call(this, taskName); 
                                    helio.taskMap.put(taskName, task);
                                }
                                task.init.call(task);
                            });
                        };
                    }) (subMenuItem, subMenuItemConfig)
                };
            }
            new helio.DropdownMenu(menuName, pmMenu);
        } else if (config.taskName) {
            $("#" + menuName).button();
            $("#" + menuName).click((function(config) {
                return function() {
                    $('#content').empty();
                    $('#content').load(config.url, function() {
                        var task = helio.taskMap.findByName(config.taskName);
                        if (!task) {
                            task = config.taskConstructor.call(this, config.taskName); 
                            helio.taskMap.put(config.taskName, task);
                        }
                        task.init.call(task);
                    });
                };                
            })(config));
        } else {
            throw "unknown menu config " + config;
        }
    }


//    $("#advanced_tab").click();
//    $("#task_plotservice_menu").click();
//    $("#task_plotservice_parker").click();
    
    //$('#task_ics').click();
    
    
});


