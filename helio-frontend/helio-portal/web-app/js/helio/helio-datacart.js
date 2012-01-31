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
    $.getJSON(
        '../dataCart/load',
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
    this.data = $.getJSON(
        '../dataCart/create',
        dataItem,
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
helio.DataCart.prototype.removeItem = function(dataItem) {
    debugger;
    this.data = $.getJSON(
        '../dataCart/remove',
        dataItem,
        function(data, textStatus, jqXHR) {
            THIS.data = data;
            THIS.render.call(THIS);
        }
    );
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
            var taskName = "datacart";  // name of the task
            var task = {};              // not sure we need a task
            
            var dataObject;             // helio-model javascript object holding the data
            var dialogFactory;          // the dialog factory bound to this item. This is basically a function that opens the dialog.
            var icon;
            switch (cartItem.class) {
            case 'eu.heliovo.hfe.model.param.TimeRangeParam':
                dataObject = new helio.TimeRanges(cartItem.name);
                dataObject.timeRanges = [];
                dataObject.id = cartItem.id;
                $.each (cartItem.timeRanges, function(index, timeRange) {
                    dataObject.timeRanges.push(new helio.TimeRange(timeRange.startTime.replace('Z', ''), timeRange.endTime.replace('Z', '')));
                });
                dialogFactory = (function(task, taskName, dataObject) { 
                    return function() {
                        return new helio.TimeRangeDialog(task, taskName, dataObject);
                        dialog.show();
                    };
                })(task, taskName, dataObject);
                
                icon = 'circle_time.png';
                break;
            case 'eu.heliovo.hfe.model.param.ParamSet':
                dataObject = new helio.ParamSet(cartItem.taskName, cartItem.name);
                dataObject.params = cartItem.params;
                dataObject.id = cartItem.id;
                dialogFactory = (function(task, taskName, dataObject) { 
                    return function() {
                        return new helio.ParamSetDialog(task, taskName, dataObject);
                    };
                })(task, taskName, dataObject);
                
                icon = 'circle_block.png';
                break;
            case 'eu.heliovo.hfe.model.param.InstrumentParam':
            case 'eu.heliovo.hfe.model.param.ObservatoryParam':
            default:
                dataObject = "Unsupported " + cartItem.class;
                icon = 'circle_block.png';
                dialogFactory = null;
                break;
            }
            
            var cartItemDiv= $('<div  title="' + cartItem.name + '" class="cartitem cartitem_draggable"></div>');
            var img = $('<img class="cartitem_image" alt="' + icon + '"/>').attr('src', '../images/helio/' + icon);
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
            var cartItemLabel = $('<span class="cartitem_label">' + cartItem.name + '</span>');
            cartItemDiv.append(cartItemLabel);
            
            cartItemDiv.draggable({
                revert: "invalid",
                helper: (function(img) {
                    return function() {
                        return img.clone();
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
                            THIS.addItem(dialog.data());
                        });
                    };
                })(dialogFactory));
            } else {
                $('<div title="Information">Unsupported data type</div>')
                .button();
            }
            
            removeCartItem.click((function(cartItemDiv){   
                return function(){
                    // dialog which asks if parameter really ought to be deleted 
                    $('<div title="Confirmation">Do you want to remove this parameter from the data cart?</div>')
                    .dialog({ 
                      buttons:{
                        "Yes": function() {
                            
                            cartItemDiv.remove();
                            $(this).dialog("close");
                        },
                        "No": function() {
                            $(this).dialog("close");
                        },
                      }
                    });
                    return false;
                };
            })(cartItemDiv));
            
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
    
};

})();
