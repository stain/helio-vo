/**
 * Container to handle the contents of the data cart
 */

(function() {
    
/**
 * The constructor of the DataCart. The data cart should be instantiated only once.
 */
helio.DataCart = function() {
    this.data = []; // array of helio.AbstractData
    this._init();
};

/**
 * Load the initial content of the data cart from the remote host.
 * This is read when the data cart is created
 */
helio.DataCart.prototype._init = function() {
    var THIS = this;
    $.postJSON(
        './dataCart/load',
        function(data, textStatus, jqXHR) {
            THIS.data = data;
            THIS.render.call(THIS);
        }
    );
    
    // init the slider
    $("#datacart_slider").slider({
        animate: true,
        change: THIS._handleSliderChange,
        slide: THIS._handleSliderSlide
    });
};

/**
 * Helper function to enable scrolling slider in the datacart
 *
 * @event onChangeEvent
 * @ui helper selector
 */
helio.DataCart.prototype._handleSliderChange = function(event, ui) {
    var maxScroll = $("#datacart_scrollarea").prop("width") - $("#datacart_scrollarea").width();
    $("#datacart_scrollarea").animate({
        scrollLeft: ui.value * (maxScroll / 100)
    }, 1000);
};

/**
 * Helper function to enable scrolling slider in the datacart
 * @event sliderEvent
 * @ui helper selector
 */
helio.DataCart.prototype._handleSliderSlide = function(event, ui) {
    var maxScroll = $("#datacart_scrollarea").prop("width") - $("#datacart_scrollarea").width();
    $("#datacart_scrollarea").prop({
        scrollLeft: ui.value * (maxScroll / 100)
    });
};

/**
 * Add a data item to the data cart and re-paint the cart.
 * This method also updates the server side.
 * @param {helio.AbstractModel} dataItem, the data Item to add.
 */
helio.DataCart.prototype.addItem = function(dataItem) {
    var THIS = this;
    this.data = $.postJSON(
        './dataCart/create',
        {data : JSON.stringify(dataItem, this._jsonReplacer)},
        function(data, textStatus, jqXHR) {
            THIS.data = data;
            THIS.render.call(THIS);
        }
    );
};

/**
 * Upate the content of an existing item and re-paint the cart.
 * This method also updates the server side.
 * @param {helio.AbstractModel} dataItem, the data Item to add.
 */
helio.DataCart.prototype.update = function(dataItem) {
    var THIS = this;
    this.data = $.postJSON(
        './dataCart/update',
        {id : dataItem.id, data : JSON.stringify(dataItem, this._jsonReplacer)},
        function(data, textStatus, jqXHR) {
            THIS.data = data;
            THIS.render.call(THIS);
        }
    );
};

/**
 * Remove a data item from the data cart and re-paint the cart.
 * This method also updates the server side.
 * @param {helio.AbstractModel} dataItem, the data Item to add.
 */
helio.DataCart.prototype.deleteItem = function(dataItem) {
    var THIS = this;
    this.data = $.postJSON(
        './dataCart/delete',
        {data : JSON.stringify(dataItem, this._jsonReplacer)},
        function(data, textStatus, jqXHR) {
            THIS.data = data;
            THIS.render.call(THIS);
        }
    );
};

/**
 * Custom JSON rendering. Filter the config params.
 * @param key the key to render as JSON
 * @param value the value to render as JSON
 * @returns the filtered value. Nothing to skip a value.
 */
helio.DataCart.prototype._jsonReplacer = function(key, value) {
    if (key == 'config') {
        return undefined;
    }
    return value;
};


/**
 * Render / re-render the content of the data cart.
 */
helio.DataCart.prototype.render = function() {
    var THIS = this;
    // loop over the data items
    var dataCartDivs = [];
    if (this.data && this.data.cartItems) {        
        
        $.each(this.data.cartItems, function(index, cartItem) {
            // name of the task
            var taskName = cartItem.taskName; // != null ? cartItem.taskName : "datacart";
            
            var task = {};              // not sure we need a task
            var dataObject;             // helio-model javascript object holding the data
            var dialogFactory;          // the dialog factory bound to this item. This is basically a function that opens the dialog.
            
            if (cartItem.name == null) {
                cartItem.name = cartItem.taskName != null ? cartItem.taskName : "no name";
            }
            
            switch (cartItem.class) {
            case 'eu.heliovo.hfe.model.param.TimeRangeParam':
                dataObject = new helio.TimeRanges(cartItem.taskName, cartItem.name);
                dataObject.timeRanges = [];
                dataObject.id = cartItem.id;
                $.each (cartItem.timeRanges, function(index, timeRange) {
                    dataObject.timeRanges.push(new helio.TimeRange(timeRange.startTime.replace('Z', ''), timeRange.endTime.replace('Z', '')));
                });
                dialogFactory = (function(task, taskName, dataObject) { 
                    return function() {
                        return new helio.TimeRangeDialog(task, taskName, dataObject);
                    };
                })(task, taskName, dataObject);
                
                break;
            case 'eu.heliovo.hfe.model.param.ParamSet':
            	dataObject = THIS._newParamSet(cartItem.taskName, cartItem.name, cartItem.entries);
            	
                taskName = cartItem.taskName != null ? cartItem.taskName : taskName;
                dataObject.id = cartItem.id;
                dialogFactory = (function(task, taskName, dataObject) { 
                    return function() {
                        return new helio.ParamSetDialog(task, taskName, dataObject);
                    };
                })(task, taskName, dataObject);
                
                break;
            case 'eu.heliovo.hfe.model.param.InstrumentParam':
                dataObject = new helio.Instrument(taskName, cartItem.name);
                for (var i = 0; i < cartItem.instruments.length; i++) {
                    var instr = cartItem.instruments[i];
                    dataObject.setInstrument(instr); 
                }
                dataObject.id = cartItem.id;
                dialogFactory = (function(task, taskName, dataObject) { 
                    return function() {
                        return new helio.InstrumentDialog(task, taskName, dataObject);
                    };
                })(task, taskName, dataObject);
                break;                
            case 'eu.heliovo.hfe.model.param.EventListParam':
                dataObject = new helio.EventList(cartItem.taskName, cartItem.name);
                // loop over event lists
                for (var i = 0; i < cartItem.entries.length; i++) {
                	var eventListEntry = cartItem.entries[i];
                	
                	// find according param set
                	var paramSet = undefined;
                	if (eventListEntry.whereClause) {
                		paramSet = THIS._newParamSet.call(THIS, cartItem.taskName, "whereClause", eventListEntry.whereClause.entries);
                	}
                	dataObject.addEntry(eventListEntry.listName, paramSet);
                }
                dataObject.id = cartItem.id;
                dialogFactory = (function(task, taskName, dataObject) { 
                    return function() {
                        return new helio.EventListDialog(task, taskName, dataObject);
                    };
                })(task, taskName, dataObject);
                break;
            default:
                dataObject = new helio.AbstractModel("Unsupported " + cartItem.class, 'block');
                dialogFactory = null;
                break;
            }
            
            var draggableClass = 'cartitemDraggable' + dataObject.type + (dataObject.subtype ? '_' + dataObject.subtype : '');
            var cartItemDiv= $('<div  title="' + jQuery.escapeHTML(cartItem.name) + '" class="cartitem ' + draggableClass + '"></div>');
            var img = $('<img class="cartitem_image ' + draggableClass + '" alt="' + dataObject.type + '"/>').attr('src', './images/helio/circle_' + dataObject.type + '.png');
            cartItemDiv.append(img);
            var removeCartItem = $('<div class="cartitem_close ui-state-default ui-corner-all" title="Remove parameter">' +
            '<span class="ui-icon ui-icon-close"></span></div>');
            cartItemDiv.append(removeCartItem);
            var editCartItem;
            if (dialogFactory) {
                editCartItem = $('<div class="cartitem_edit ui-state-default ui-corner-all" title="Modify parameter content">' +
                '<span class="ui-icon ui-icon-pencil"></span></div>');
                cartItemDiv.append(editCartItem);
            } else {
                editCartItem = null;
            }

            var cartItemLabel = $('<span class="cartitem_label">' + jQuery.escapeHTML(cartItem.name) + '</span>');
            cartItemDiv.append(cartItemLabel);
            
            // attach data to cartItemDiv
            cartItemDiv.draggable({
                start : function(event, ui) {
                    cartItemDiv.data('data', dataObject);
                },
                revert: "invalid",
                helper: (function(img) {
                    return function() {
                        var ret = img.clone().attr("style", "width:50px; height:50px;");
                        return ret;
                    };
                })(img),
                zIndex: 1700
            });
            
            // move the icon around
            if (editCartItem) {
                editCartItem.click( (function(dialogFactory){
                    return function() {
                        var dialog = dialogFactory.call(THIS);
                        dialog.show(function() {
                            THIS.update.call(THIS, dialog.data);
                            cartItemLabel.children().text(dialog.data.name);
                        });
                    };
                })(dialogFactory));
            } else {
                $('<div title="Information">Unsupported data type</div>')
                .button();
            }
            removeCartItem.click((function(dataObject){   
                return function(){
                    // dialog which asks if parameter really ought to be deleted 
                    $('<div title="Confirmation">Do you want to remove this parameter from the data cart?</div>')
                    .dialog({ 
                      buttons:{
                        "Yes": function() {
                            THIS.deleteItem(dataObject);
//                            cartItemDiv.remove();
                            $(this).dialog("close");
                        },
                        "No": function() {
                            $(this).dialog("close");
                        },
                      }
                    });
                    return false;
                };
            })(dataObject));
            
            // add cart item to datacarts
            dataCartDivs.push(cartItemDiv);
        });
    } else {
        dataCartDivs.push($('<div class="cartitem">Nothing to display</div>'));
    }
    
    var dataCart = $('#datacart_content');
    dataCart.empty();
    $(dataCartDivs).each(function(index, item) {
        dataCart.append(item);
    });
   
    $('#datacart_scrollarea').droppable({
        accept: ".paramDraggable",
//        create : function(event, ui) {
//        },
        activeClass: "dataCartDroppableActive",
        hoverClass: "dataCartDroppableHover",
        drop: function( event, ui ) {
            if( ui.draggable.data('data') != null){
                THIS.addItem(ui.draggable.data('data'));
            }
        }
    });
};

/**
 * Create a new param set
 * @param {String} taskName the name of the parent task
 * @param {String} paramSetName the name of the param set
 * @param {Array} list of Objects consisting of a paramName-operator-paramValue triple.
 */
helio.DataCart.prototype._newParamSet = function(taskName, paramSetName, entries) {
	var paramSet = new helio.ParamSet(taskName, paramSetName);
	for (var i = 0; i < entries.length; i++) {
	    var entry = entries[i];
	    paramSet.setParamSetEntry(entry.paramName, entry.operator.name, entry.paramValue); 
	}
	return paramSet;
};

})();
