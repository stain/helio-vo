/**
 * Base module for the HELIO project. This is used to initialize the HELIO
 * project and provides some generic classes.
 */
$(document).ready(function() {
    $("#task_upload2").click(function() {
        $('#content').load('../task/uploadVoTable', function() {
            var task = new helio.VOTableUploadTask("votableupload"); 
            task.init.call(task);
        });
    });    

    var pmConfig = new Object();
    pmConfig["pm_cme"] = { title: "CME Forward PM"};
    pmConfig["pm_cme_back"] = { title: "CME Backward PM"};
    pmConfig["pm_sw"] = { title: "Solar Wind PM"};
    pmConfig["pm_sep"] = { title: "Energetic Particle PM"};
    
    var pmMenu = new Object();
    // loop over config and fill menu object.
    for (taskName in pmConfig) {
        pmMenu["task_propagationmodel_" + taskName] = { 
            "title": pmConfig[taskName].title,
            "click": (function(taskName) {
                return function() {
                    $('#content').load('../task/propagationModel?taskName=' + taskName, function() {
                        var task = helio.taskMap.findByName(taskName);
                        if (!task) {
                            task = new helio.PropagationModelTask(taskName); 
                            helio.taskMap.put(taskName, task);
                        }
                        task.init.call(task);
                    });
                };
            }) (taskName)
        };
    }
    
    // create the dropdown menu
    new helio.DropdownMenu("task_propagationmodel", pmMenu);
    
    // create the dropdown menu for the plotservcie
    new helio.DropdownMenu("task_plotservice", {
        "task_plotservice_goes" : 
        { 
          "title": "GOES timeline plot",
          "click": function() {
            $('#content').load('../task/plot?taskName=goesplot', function() {
              var task = helio.taskMap.findByName("goesplot");
              if (!task) {
                task = new helio.PlotTask("goesplot");
                helio.taskMap.put("goesplot", task);
              }
              task.init.call(task);
            });
          }
        },
        "task_plotservice_flare" : 
        { 
          "title": "Flare plot",
          "click": function() {
            $('#content').load('../task/plot?taskName=flareplot', function() {
              var task = helio.taskMap.findByName("flareplot");
              if (!task) {
                task = new helio.PlotTask("flareplot");
                helio.taskMap.put("flareplot", task);
              }
              task.init.call(task);
            });
          }
        },
        "task_plotservice_parker" : 
        { 
          "title": "Parker spiral plot",
          "click": function() {
            $('#content').load('../task/plot?taskName=parkerplot', function() {
              var task = helio.taskMap.findByName("parkerplot");
              if (!task) {
                task = new helio.PlotTask("parkerplot");
                helio.taskMap.put("parkerplot", task);
              }
              task.init.call(task);
            });
          }
        },
        "task_plotservice_ace" : 
        { 
            "title": "ACE plot",
            "click": function() {
                $('#content').load('../task/plot?taskName=aceplot', function() {
                    var task = helio.taskMap.findByName("aceplot");
                    if (!task) {
                        task = new helio.PlotTask("aceplot");
                        helio.taskMap.put("aceplot", task);
                    }
                    task.init.call(task);
                });
            }
        },
        "task_plotservice_sta" : 
        { 
            "title": "STEREO-A plot",
            "click": function() {
                $('#content').load('../task/plot?taskName=staplot', function() {
                    var task = helio.taskMap.findByName("staplot");
                    if (!task) {
                        task = new helio.PlotTask("staplot");
                        helio.taskMap.put("staplot", task);
                    }
                    task.init.call(task);
                });
            }
        },
        "task_plotservice_stb" : 
        { 
            "title": "STEREO-B plot",
            "click": function() {
                $('#content').load('../task/plot?taskName=stbplot', function() {
                    var task = helio.taskMap.findByName("stbplot");
                    if (!task) {
                        task = new helio.PlotTask("stbplot");
                        helio.taskMap.put("stbplot", task);
                    }
                    task.init.call(task);
                });
            }
        },
        "task_plotservice_ulysses" : 
        { 
            "title": "Ulysses plot",
            "click": function() {
                $('#content').load('../task/plot?taskName=ulyssesplot', function() {
                    var task = helio.taskMap.findByName("ulyssesplot");
                    if (!task) {
                        task = new helio.PlotTask("ulyssesplot");
                        helio.taskMap.put("ulyssesplot", task);
                    }
                    task.init.call(task);
                });
            }
        },
        "task_plotservice_wind" : 
        { 
            "title": "WIND plot",
            "click": function() {
                $('#content').load('../task/plot?taskName=windplot', function() {
                    var task = helio.taskMap.findByName("windplot");
                    if (!task) {
                        task = new helio.PlotTask("windplot");
                        helio.taskMap.put("windplot", task);
                    }
                    task.init.call(task);
                });
            }
        },
    });
});

/**
 * The helio name space
 */
(function() {
// use existing global 'helio' (here, 'this' === window/global context)
// or create new object/package
this.helio = this.helio ||
  { toString: function() { return 'package: helio'; } };
  
// cache for temporary data. Temporary data is stored in an associative array.
this.helio.cache = new Object();

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

/**
 * Provide global access to the task map.
 */
this.helio.taskMap = new helio.TaskMap();

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
                $("#" + THIS.id + " span.ui-button-text").html(title);
                
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

})();
