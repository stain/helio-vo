function ActionViewer(imageParam,typeParam,actionNameParam,labelParam,serviceNameParam) {

    var className = "ActionViewer";
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


    var _initGhostElements = function(){
        if (typeof console!="undefined")console.info("ActionViewer :: _initSolidElements ");
            
        fnInitializeDatePicker();
        
        $("#minDate").val($.cookie("minDate"));
        $("#maxDate").val($.cookie("maxDate"));
        $("#minTime").val($.cookie("minTime"));
        $("#maxTime").val($.cookie("maxTime"));

          
        $.collapsible(".queryHeader","group1");
        $.collapsible(".advancedParameters","group2");
        $("#currentDisplay").find("#delete").click(function(){
            if(history.length>0){
                history.splice(step, 1);
                step = history.length-1;
                window.historyBar.render();


            }else{
                window.historyBar.removeCurrent();
                window.workspace.setDisplay("splash");
            }

        });

        $(".catalogueSelector").change(function(){
            $('.columnInputs').html("");
            $('#whereField').val("");
        });
            
        $( ".custom_button").button();
        $(".column-reset").click(function(){

            $(".columnSelection").val("");
        });

      var options = {
        target:        '#responseDivision',   // target element(s) to be updated with server response
        beforeSerialize: fnBeforeQuery,
        beforeSubmit:  window.workspace.onLoading,  // pre-submit callback
        success:       fnOnComplete,  // post-submit callback
        
        // other available options:
        //url:       "asyncQuery",        // override for form's 'action' attribute
        //type:      'POST'        // 'get' or 'post', override for form's 'method' attribute
        //dataType:  null        // 'xml', 'script', or 'json' (expected server response type)
        //clearForm: true        // clear all form fields after successful submit
        //resetForm: true        // reset the form after successful submit

        // $.ajax options can be used here too, for example:
        timeout:   3000
    };

    // bind form using 'ajaxForm'
      
    $('#actionViewerForm').ajaxForm(options);

    }
    var _initSolidElements = function(){
        if (typeof console!="undefined")console.info("ActionViewer:: _initSolidElements ");
        $("#currentDisplay").find("#counter").css("display","block");
        $("#currentDisplay").find("#counter").text((step+1)+"/"+history.length);
        $("#currentDisplay").find("#label").val(label);

        $(".placeholder").remove();
        
        

        $("#currentDisplay").find("#forward").css("display","block");
        $("#currentDisplay").find("#forward").click(function(){
            window.workspace.getElement().nextStep()
        });

        $("#currentDisplay").find("#backward").css("display","block");
        $("#currentDisplay").find("#backward").click(function(){
            window.workspace.getElement().prevStep()
        });

        $("#currentDisplay").find("#label").change(function() {

            window.historyBar.getCurrent().setLabel($(this).val());
            window.historyBar.render(1);
        });
        /*
        $("#resultSelectionSelectAll").click(function(){
            
            var tableId =$(this).attr('reference');
            
            $("#"+tableId).find("tbody").find("tr").each(function(){
                
                
                this.className.replace( /_selected/, "" );
                this.className+'_selected';
                
            });
        });*/
    }
    /*
     * Takes in the serialized parameters from the previous form, parses and redraws them into the new form.
     * @formData: serialized form string, Example: "minDateList=2003-01-01T07%3A49%3A00%2C2003-01-02T04%3A41%3A00%2C2003-01-02T12%3A58%3A00"
     *
     */
    var _unserialize = function(formData,advancedSearchParam){

        
        $('#currentDisplay').find('.columnInputs').html(advancedSearchParam);
        $('#currentDisplay').find('.columnInputs').css("display","block");
        $("#currentDisplay").find("select").find("option").removeAttr("selected");
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
                $("#currentDisplay").find("select").find("option[value='"+tempField+"']").attr("selected","selected");
                


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
                    

                    $("#currentDisplay").find("label:contains('"+labelName+"')").each(function(){
                        

                        if($(this).text() == labelName+" "){
                            $(this).parent().find("input").val(value);

                        }//if

                    });//each
                }//end input
            }//end if
        }//end fields
        
    };//end unserialized

    return {
        // Public methods
        getClassName: function() {
            if (typeof console!="undefined")console.info("ActionViewer :: getClassName");
            return className;
        },
       
        getServiceName: function() {
            if (typeof console!="undefined")console.info("ActionViewer :: getServiceName");
            return serviceName;
        },
        prepareStep: function(formData,advancedSearchParams) {
            if (typeof console!="undefined")console.info("ActionViewer :: prepareStep ->"+ formData);
            
            prevData=formData;
            advancedSearch=advancedSearchParams;
        },

        addStep: function(result) {
            if (typeof console!="undefined")console.info("ActionViewer :: addStep -> notshown");
          
            var object = new Object();
            object['result']=result;
            object['formData']=prevData;
            object['advancedSearch']=advancedSearch;
            
            history.push(object);
            step = history.length -1;
            
            
            
        },
        nextStep: function() {
            if (typeof console!="undefined")console.info("ActionViewer :: nextStep");
            if(step < history.length -1){
                step++;
                
                this.renderContent();
            }
        },
        prevStep: function() {
            if (typeof console!="undefined")console.info("ActionViewer :: prevStep");
            if(step > 0){
                step--;
                
                this.renderContent();
            }
            
        },
        setLabel: function(labelParam) {
            if (typeof console!="undefined")console.info("ActionViewer :: setLabel -> " +labelParam);
            label=labelParam;
            
        },
        getLabel: function() {
            if (typeof console!="undefined")console.info("ActionViewer :: getLabel");
            return label;
        },
        

        setImagePath: function(path) {
            if (typeof console!="undefined")console.info("ActionViewer :: setImagePath -> " +path);
            imagePath = path;
        },
        getImagePath: function() {
            if (typeof console!="undefined")console.info("ActionViewer :: getImagePath");
            return imagePath;
        },
        setContent: function(contentParam) {
            if (typeof console!="undefined")console.info("ActionViewer :: setContent -> " +contentParam);
            content = contentParam;
        },
        getContent: function() {
            if (typeof console!="undefined")console.info("ActionViewer :: getContent");
            return content;
        },

        getType: function() {
            if (typeof console!="undefined")console.info("ActionViewer :: getType -> " + type);
            return type;
        },
        setType: function(typeParam) {
            if (typeof console!="undefined")console.info("ActionViewer :: setType -> " +typeParam);
            type =typeParam;
        },
        renderContent: function() {
            if (typeof console!="undefined")console.info("ActionViewer :: renderContent");
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
                //$('#resultTable0').dataTable().fnUpdate();
                //$('#resultTable0').dataTable().fnDraw();
                
                _initSolidElements();
            }
            _initGhostElements();           
            $(".tooltipme").tooltip({
                position: "top center",
                delay: 0,
                predelay:0
            });

            
        
        },//end renderContent
        render: function(key,current) {
            if (typeof console!="undefined")console.info("ActionViewer :: render ->"+ key +" current "+current);

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
                
                for(i in history){
                    div.append("<div class='ui-state-default new1'>"+"Page "+(parseInt(i,10)+1)+"</div>");
                }
                $("#historyContent").append(div);
                type="solid";

                div.click(function() {
                    if (typeof console!="undefined")console.info("ActionViewer :: item doubleclicked ->"+ key);
                    window.historyBar.cleanGhost();
                    //var item = window.historyBar.getItem(key);
                    window.historyBar.setFocus(key);
                });//end dbclick
            }//end else
        }//end render
    };//end public methods
}//end class
