function ActionViewerExtended(imageParam,typeParam,actionNameParam,labelParam,serviceNameParam) {

    var className = "ActionViewerExtended";
    var actionName = actionNameParam;
    var serviceName = serviceNameParam;
    var type = typeParam;
    var content;
    var imagePath = imageParam;
    var label = labelParam;
    var advancedSearch;
    var prevData;
    var step =0;
    var history = new Array();

    /**
     * Initialize the tooltips and reset button of columns 
     * Called after onSucess, onError
     */
    var _onCompleteGetHecColumns = function(xmlHttpRequest,textStatus,jqXHR){
        // trace method
        //    	if (typeof console!="undefined") {
        //    		console.info("_onCompleteGetHecColumns " + textStatus );
        //    	}

        $('.column-reset').each(function() {
            $(this).button();
        });

        // setup tooltips
        $(".hecColLabelTooltipMe").each(function() {
        	var me = this;
        	$(this).tooltip({
                position: "center right",
                tipClass: 'ctooltip_' + this.id.substring(6),
                delay: 0,
                predelay:0,
                relative: true
            });
		});
        
    };
    
    /**
     * Called after successful loading of HEC columns
     * @param data HTML stub containing the loaded columns
     * @param textStatus a status message.
     */
    var __onSuccessGetHecColumns = function(data,textStatus) {
        //if (typeof console!="undefined") console.info("_onSuccessGetHECColumns");
        $('#hecExtendedQueryContent').append(data);
    };

    /**
     * Method called in case an error occurs when loading the HEC table.
     * @param XMLHttpREquest the underlying request
     * @param textStatus status message
     * @param errorThrown error object
     */
    var __onErrorGetHecColumns = function(XMLHttpREquest,textStatus,errorThrown) {
        $('#hecExtendedQueryContent').append('<div class="hec_' + catalogName + '">' +
            "<p>Error occurred while loading catalog info for " + catalogName + ".</p>" +
            "Reason: " + textStatus + " </p>" +
            "<p>" + errorThrown + "</p>" +
            "</div>");
    };

    /**
     * Load the input fields for a given catalog from remote
     */
    var _loadHecCatalog = function(catalogName) {
        if (typeof console!="undefined")console.info("ActionViewerExtended ::  _loadHecCatalog "+catalogName);

        // call getHecColumns asynchronously
        jQuery.ajax(
        {
            type : 'GET',
            data : {
                "catalog":catalogName
            },
            url : 'getHecColumns',
            success: __onSuccessGetHecColumns,
            error: __onErrorGetHecColumns,
            complete: _onCompleteGetHecColumns
        });
        return false;
    };

    var _removeHecCatalog = function(catalogName) {
        if (typeof console!="undefined")console.info("ActionViewerExtended ::  _removeHecCatalog "+catalogName);
        $(".hec_" + catalogName).remove();
    };
    
    /* * register click handler on advanced HEC query. */
    var _initGhostElements = function(){
        if (typeof console!="undefined")console.info("ActionViewerExtended :: _initGhostElements");
        
        fnInitializeDatePicker();
        $("#minDate").val($.cookie("minDate"));
        $("#maxDate").val($.cookie("maxDate"));
        $("#minTime").val($.cookie("minTime"));
        $("#maxTime").val($.cookie("maxTime"));
        

        var catalogCheckboxes = $("#hecExtendedCatalogSelector input:checkbox");

        // disable search button as long as no column is selected.
        var onChangeSearchButton = function(event){
            $("input[name='hecSearchButton']").button({
                disabled: !$("#hecExtendedCatalogSelector input:checked").val()
            });
        };
        onChangeSearchButton();  // init button state
        catalogCheckboxes.change(onChangeSearchButton); // register button handler
        $.collapsible(".queryHeader","group1");
        
        catalogCheckboxes.change(function(event){
            if (typeof console!="undefined")console.info("ActionViewerExtended ::  catalogCheckboxes.change "+$(this).val());
            var catalogName = $(this).val();
            if($(event.currentTarget).is(':checked')){
                _loadHecCatalog(catalogName);
            }else{
                _removeHecCatalog(catalogName);
            }
        });
        
       $("#currentDisplay").find("#delete").click(function(){
                  if(history.length>0){
                    history.splice(step, 1);
                    step = history.length-1;
                    window.historyBar.render();


                }else{
                    window.historyBar.removeCurrent();
                }

            });
        $( ".custom_button").button();

        // setup tooltips
        $(".hecLabelTooltipMe").each(function() {
        	$(this).tooltip({
                position: "center right",
                tipClass: 'hecLabelTooltip',
                delay: 0,
                predelay:0,
                relative: true
            });
        });
        
        // setup column tooltips
        _onCompleteGetHecColumns();
    };

    var _initSolidElements = function(){
        if (typeof console!="undefined")console.info("ActionViewerExtended :: _initSolidElements ");
        
        $("#currentDisplay").find("#counter").css("display","block");
        $("#currentDisplay").find("#counter").text((step+1)+"/"+history.length);
        $("#currentDisplay").find("#label").val(label);
        
        $(".placeholder").remove();
        $.collapsible(".advancedParameters","group2");

        $("#currentDisplay").find("#forward").css("display","block");
        $("#currentDisplay").find("#forward").click(function(){
            window.workspace.getElement().nextStep();
        });

        $("#currentDisplay").find("#backward").css("display","block");
        $("#currentDisplay").find("#backward").click(function(){
            window.workspace.getElement().prevStep();
        });

        $("#currentDisplay").find("#label").change(function() {

            window.historyBar.getCurrent().setLabel($(this).val());
            window.historyBar.render(1);
        });
        /*
           $("#resultSelectionSelectAll").click(function(){
            
            var tableId =$(this).attr('reference');

            $("#"+tableId).find("tbody").find("tr").each(function(){
                
                var cssClass =$(this).attr('class');
                if(cssClass.indexOf("odd")!= -1){
                    $(this).removeClass('odd');
                    $(this).removeClass('odd_selected');
                    $(this).addClass('odd_selected');
                }else if(cssClass.indexOf("even")!= -1){
                    $(this).removeClass('even');
                    $(this).removeClass('even_selected');
                    $(this).addClass('even_selected');
                }
            });
        });*/
    };
    
    /*
     * Takes in the serialized parameters from the previous form, parses and redraws them into the new form.
     * @formData: serialized form string, Example: "minDateList=2003-01-01T07%3A49%3A00%2C2003-01-02T04%3A41%3A00%2C2003-01-02T12%3A58%3A00"
     *
     */
    var _unserialize = function(formData,advancedSearchParam){

        $('#currentDisplay').find('#hecExtendedQueryContent').html(advancedSearchParam);

        var fields = formData.split("&");
        for(field in fields){
            var tempField= fields[field];

            if(tempField.indexOf("minDateList=")!= -1){
                tempField =tempField.replace('minDateList=',"");
                tempField =tempField.replace('%3A',":");
                tempField =tempField.replace('%2C',",");
                tempField =tempField.replace('+',"");
                $(".minDateList").val(tempField);
            }//end if
            else if(tempField.indexOf("maxDateList=")!= -1){
                tempField =tempField.replace('maxDateList=',"");
                tempField =tempField.replace('%3A',":");
                tempField =tempField.replace('%2C',",");
                tempField =tempField.replace('+',"");
                $(".maxDateList").val(tempField);
            }//end if
            else if(tempField.indexOf("minTime=")!= -1){
                tempField =tempField.replace('minTime=',"");
                tempField =tempField.replace('%3A',":");
                $("#currentDisplay").find("input[name='minTime']").val(tempField);
            }//end if
            else if(tempField.indexOf("maxTime=")!= -1){
                tempField =tempField.replace('maxTime=',"");
                tempField =tempField.replace('%3A',":");
                $("#currentDisplay").find("input[name='maxTime']").val(tempField);
            }//end if
            else if(tempField.indexOf("minDate=")!= -1){
                tempField =tempField.replace('minDate=',"");
                $("#currentDisplay").find("input[name='minDate']").val(tempField);
            }//end if
            else if(tempField.indexOf("maxDate=")!= -1){
                tempField =tempField.replace('maxDate=',"");
                $("#currentDisplay").find("input[name='maxDate']").val(tempField);
            }//end if
            else if(tempField.indexOf("extra=")!= -1){

                tempField =tempField.replace('extra=',"");
                
                $("#currentDisplay").find("input[value='"+tempField+"']").attr("checked","checked");


            }else if(tempField.indexOf("where=")!= -1){

                tempField =tempField.replace('where=',"");
                tempField =tempField.replace(/%5C/g,"\\");
                tempField =tempField.replace(/%2F/g,"/");

                tempField =tempField.split("%3B");
                for(input in tempField){
                    var innerTempField = tempField[input].split("%2C");
                    var value = innerTempField[1];
                    innerTempField = innerTempField[0].split(".");
                    var inputName = innerTempField[0];
                    var labelName = innerTempField[1];

                    $("#currentDisplay").find("input[name='"+inputName+"."+labelName+"']").val(value);
                }//end input
            }//end if
        }//end fields

    };//end unserialized

    return {
        // Public methods
        getClassName: function() {
            if (typeof console!="undefined")console.info("ActionViewerExtended :: getClassName");
            return className;
        },
        getServiceName: function() {
            if (typeof console!="undefined")console.info("ActionViewerExtended :: getServiceName");
            return serviceName;
        },
        prepareStep: function(formData,advancedSearchParams) {
            if (typeof console!="undefined")console.info("ActionViewerExtended :: prepareStep ->"+ formData);
            prevData=formData;
            advancedSearch=advancedSearchParams;
        },

        addStep: function(result) {
            if (typeof console!="undefined")console.info("ActionViewerExtended :: addStep -> notshown");

            var object = new Object();
            object['result']=result;
            object['formData']=prevData;
            object['advancedSearch']=advancedSearch;

            history.push(object);
            step = history.length -1;

        },
        nextStep: function() {
            if (typeof console!="undefined")console.info("ActionViewerExtended :: nextStep");
            if(step < history.length -1){
                step++;

                this.renderContent();
            }
        },
        prevStep: function() {
            if (typeof console!="undefined")console.info("ActionViewerExtended :: prevStep");
            if(step > 0){
                step--;

                this.renderContent();
            }

        },
        setLabel: function(labelParam) {
            if (typeof console!="undefined")console.info("ActionViewerExtended :: setLabel -> " +labelParam);
            label=labelParam;

        },
        getLabel: function() {
            if (typeof console!="undefined")console.info("ActionViewerExtended :: getLabel");
            return label;
        },
        setImagePath: function(path) {
            if (typeof console!="undefined")console.info("ActionViewerExtended :: setImagePath -> " +path);
            imagePath = path;
        },
        getImagePath: function() {
            if (typeof console!="undefined")console.info("ActionViewerExtended :: getImagePath");
            return imagePath;
        },
        setContent: function(contentParam) {
            if (typeof console!="undefined")console.info("ActionViewerExtended :: setContent -> " +contentParam);
            content = contentParam;
        },
        getContent: function() {
            if (typeof console!="undefined")console.info("ActionViewerExtended :: getContent");
            return content;
        },

        getType: function() {
            if (typeof console!="undefined")console.info("ActionViewerExtended :: getType -> " + type);
            return type;
        },
        setType: function(typeParam) {
            if (typeof console!="undefined")console.info("ActionViewerExtended :: setType -> " +typeParam);
            type =typeParam;
        },
        renderContent: function() {
            if (typeof console!="undefined")console.info("ActionViewerExtended :: renderContent");
            window.workspace.setDisplay(actionName);
            if(history.length > 0){
                
                var result = history[step].result;
                var formData = history[step].formData;
                var advancedSearch= history[step].advancedSearch;
                _unserialize(formData,advancedSearch);
                $("#responseDivision").html(result);
                $('#displayableResult').append($('#tables'));
                $('#displayableResult').css("display","block");
                fnInitSave();
                $("#responseDivision").html("");
                $(".tooltipme").tooltip({
                    position: "top center",
                    delay: 0,
                    predelay:0
                });
                $('.resultTable').each(function(){
                    fnFormatTable(this.id);
                });
                _initSolidElements();
            }
            _initGhostElements();
     
        },//end renderContent
        render: function(key,current) {
            if (typeof console!="undefined")console.info("ActionViewerExtended :: render ->"+ key +" current "+current);

            if(history.length <= 0){

                var div = $("<div class='floaters'></div>");
                var img =   $( "<img alt='" +"image missing"+"' class='ghost'  />" ).attr( "src",imagePath );
                div.append(img);
                //if(label != null)div.append("<div class='customLabel'>"+label+"</div>");
                if(key==current){
                    div.addClass('current');
                }
                $("#historyContent").append(div);
                type="ghost";
            }else{

                div = $("<div class='floaters'></div>");
                img =   $( "<img alt='" +"image missing"+"'   />" ).attr( "src",imagePath );
                //history.length
                div.append(img);


                //if(label != null)div.append("<div class='customLabel'>"+label+"</div>");
                if(key==current){
                    div.addClass('current');
                }

                for(var i=0;i < history.length;i++){
                    var pageDiv =$("<div id='"+i+"' class='ui-state-default new1'>"+"Page "+(i+1)+"</div>");
                    pageDiv.click(function(){                       
                       step = parseInt($(this).attr('id'),10);
                       window.historyBar.render();
                    });
                    
                    div.append(pageDiv);

                }
                $("#historyContent").append(div);
                type="solid";
                /*
                div.click(function() {
                    if (typeof console!="undefined")console.info("ActionViewerExtended :: item doubleclicked ->"+ key);
                    window.historyBar.cleanGhost();
                    
                    window.historyBar.setFocus(key);
                });//end dbclick*/
            }//end else
        }//end render
    };//end public methods
}//end class

/**
 * Called before submitting the HecQuery
 */
function beforeHecQuery() {
    // TODO: input validation

    _populateWhereClause();
}

/**
 * Create the were statement in PQL.
 */
function _populateWhereClause() {
    //if (typeof console!="undefined") console.info("_populateWhereClause");

    // reset where field
    $("#whereField").val("");

    // loop over all extra parameters
    $(".columnSelection").each(function(i){
        if($(this).val() == ""){
        // nothing to do
        } else {
            var value = $(this).val();
            var id = $(this).attr('name');

            if($("#whereField").val()!=""){
                var prevVal = $("#whereField").val();
                $("#whereField").val(prevVal + ";" + id + "," + value);
            }else{
                $("#whereField").val(id + "," + value);
            }
        }
        return true;
    });
}

/**
 * Called after submitting the HecQuery
 */
function afterHecQuery(event) {
    var element = window.historyBar.getCurrent();
    element.addStep($('#responseDivision').html());
    window.historyBar.render();

    var rowpos = $('#displayableResult').position();
    $('html,body').scrollTop(rowpos.top);
}

/**
 * Called by the reset form actions in the list details form.
 */
function resetHecForm(listName) {
    var fields = $('input[name^="' + listName + '"]');
    fields.each(function() {
        $(this).val("");
    });
}

