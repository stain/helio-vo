/**
 * The helio name space
 */
//use existing global 'helio' (here, 'this' === window/global context)
//or create new object/package
this.helio = this.helio || { toString: function() { return 'package: helio'; } };


/**
 * Init some global data structure, functions and handlers for 
 */
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

	this.helio.DataTableExt = this.helio.DataTableExt || 
	{ toString : function() {return 'package helio.DataTableExt';}};

	// a custom regex to detect xrayclasses
	this.helio.DataTableExt.xrayclass = /(^[ABCMX])(\d+(?:\.\d+)?$)/;

	jQuery.extend( jQuery.fn.dataTableExt.oSort, {
		"xrayclass-pre": function ( value ) {
			var matcher = helio.DataTableExt.xrayclass.exec(value);
			var key;
			if (matcher) {
				// insert additional 0 if X flare < 10.
				if (matcher[1] == 'X' && parseInt(matcher[2]) < 10) {
					key = matcher[1] + "0" + matcher[2];
				} else {
					key = matcher[0];
				}
			} else {
				key = '!'; // first character in ascii alphabet
			}
			return key;
		},

		"xrayclass-asc": function( a, b ) {
			return ((a < b) ? -1 : ((a > b) ? 1 : 0));
		},

		"xrayclass-desc": function(a,b) {
			return ((a < b) ? 1 : ((a > b) ? -1 : 0));
		}
	} );
})();      


/**
 * Base module for the HELIO project. This is used to initialize the HELIO
 * project and provides some generic classes.
 */
$(document).ready(function() {
	// create and load the helio config
	$.ajax({
		type: 'GET',
		url: './config',
		async: false,  // need to wait for completion before we can load the data cart.
		dataType: "json"
	}).done(function(data) {helio.config = data; });
	
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
					"pm_cme_fw" : { title: "CME Forward PM", 
						taskConstructor : function(taskName) {return new helio.PropagationModelTask(taskName);}},
					"pm_cme_back" : { title: "CME Backward PM", 
						taskConstructor : function(taskName) {return new helio.PropagationModelTask(taskName);}},
					"pm_cir_fw" : { title: "CIR Forward PM", 
						taskConstructor : function(taskName) {return new helio.PropagationModelTask(taskName);}},
					"pm_cir_back" : { title: "CIR Backward PM", 
						taskConstructor : function(taskName) {return new helio.PropagationModelTask(taskName);}},
					"pm_sep_fw" : { title: "SEP Forward PM", 
						taskConstructor : function(taskName) {return new helio.PropagationModelTask(taskName);}},
					"pm_sep_back" : { title: "SEP Backward PM",
						taskConstructor : function(taskName) {return new helio.PropagationModelTask(taskName);}},
				}
			},
			"task_plotservice" : {
				"menuitems" : {
					"goesplot" : { title: "GOES timeline plot", 
						taskConstructor : function(taskName) {return new helio.PlotTask(taskName);}},
					"flareplot" : { title: "Flare plot", taskConstructor : function(taskName) {return new helio.PlotTask(taskName);}},
					"parkerplot" : { title: "Parker spiral plot", 
						taskConstructor : function(taskName) {return new helio.PlotTask(taskName);}},
					"aceplot" : { title: "ACE timeline plot", 
						taskConstructor : function(taskName) {return new helio.PlotTask(taskName);}},
					"staplot" : { title: "STEREO-A timeline plot", 
						taskConstructor : function(taskName) {return new helio.PlotTask(taskName);}},
					"stbplot" : { title: "STEREO-B timeline plot", 
						taskConstructor : function(taskName) {return new helio.PlotTask(taskName);}},
					"ulyssesplot" : { title: "Ulysses timeline plot", 
						taskConstructor : function(taskName) {return new helio.PlotTask(taskName);}},
					"windplot" : { title: "Wind timeline plot", 
						taskConstructor : function(taskName) {return new helio.PlotTask(taskName);}},
				}
			},
			"task_taverna" : {
				"menuitems" : {
					"tav_2283" : { "title" : "Combine Event Lists", 
						taskConstructor : function(taskName) { return new helio.PropagationModelTask(taskName); }}
				}
			},
			"task_datamining" : {
				"taskName" : "des",
				"taskConstructor" : function(taskName) { return new helio.DesTask(taskName); }
			},
	};

	// a generic click handler for most buttons and menu entries
	var attachClickHandler = function(buttonSelector, taskName, config) {
		$(buttonSelector).click(function() {
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

	var initSplashButtons = function() {
		attachClickHandler('#entry_screen_eventlist', 'eventlist', menuConfig['task_eventlist']);
		attachClickHandler('#entry_screen_dataaccess', 'dataaccess', menuConfig['task_dataaccess']);
		$('#entry_screen_featurelist').click(function() { alert('Searching for features is not yet active.');});
	};

	$('#misc_splash').click(function() {
		$('#content').empty().load('misc/splash', function() {
			// init splash buttons
			initSplashButtons();
			$('ul.sf-menu').hideSuperfishUl();
		});
	});
	initSplashButtons();

	$('#misc_changelog').click(function() {
		$('#content').empty().load('misc/changelog');
		$('ul.sf-menu').hideSuperfishUl();
	});

	var helpWindow = null;
	$('#misc_help').click(function() {
		// open help in new window
		$('ul.sf-menu').hideSuperfishUl();
		if (helpWindow && !helpWindow.closed) {
			helpWindow.focus();
		} else {
			helpWindow = window.open("help/", "_blank", "width=630,height=800,left=50,top=50,toolbar=0,location=0,menubar=0");            
		}
	});

	// loop over config and fill menu object.
	for (var menuName in menuConfig) {
		var config = menuConfig[menuName];
		if (config.menuitems) {
			for (var taskName in config.menuitems) {
				var subMenuItemConfig = config.menuitems[taskName];
				attachClickHandler("#task_" + taskName, taskName, subMenuItemConfig);
			}
		} else if (config.taskName) {
			attachClickHandler("#task_" + config.taskName, config.taskName, config);
		} else {
			throw "unknown menu config " + config;
		}
	}
});