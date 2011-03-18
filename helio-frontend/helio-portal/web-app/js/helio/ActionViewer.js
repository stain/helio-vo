function ActionViewer(imageParam,typeParam,actionNameParam,contentParam,labelParam) {

    var className = "ActionViewer";
    var actionName = actionNameParam;
    var type = typeParam;
    var content = contentParam;
    var imagePath = imageParam;
    var label = labelParam;
    
    var advancedSearch;
    var prevData;
    
    var step =0;
    var history = new Array();



    /*
     * Takes in the serialized parameters from the previous form, parses and redraws them into the new form.
     * @formData: serialized form string, Example: "minDateList=2003-01-01T07%3A49%3A00%2C2003-01-02T04%3A41%3A00%2C2003-01-02T12%3A58%3A00"
     *
     */
    var unserialize = function(formData,advancedSearchParam){
        
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
                    var inputName= innerTempField[0];
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
            //console.log("rendering content " + step );
            
            window.workspace.setDisplay(actionName);
            $("#currentDisplay").find("#counter").text((step+1)+"/"+history.length);
            $("#currentDisplay").find("#label").val(label);
            if(history.length <= 0)return;
            var result = history[step].result;
            var formData = history[step].formData;
            var advancedSearch= history[step].advancedSearch;


            unserialize(formData,advancedSearch);

            $("#responseDivision").html(result);
            $('.resultTable').each(function(){

                fnFormatTable(this.id);

            });
    
            $('#displayableResult').append($('#tables'));


            $('#displayableResult').css("display","block");
            fnInitSave();
            $("#currentDisplay").find("#forward").click(function(){
                window.workspace.getElement().nextStep()
            });
            
            
            $("#currentDisplay").find("#backward").click(function(){
                window.workspace.getElement().prevStep()
            });
            $("#currentDisplay").find("#delete").click(function(){
                window.historyBar.removeCurrent()
            });
            $("#currentDisplay").find("#label").change(function() {
                window.historyBar.getCurrent().setLabel($(this).val());
                window.historyBar.render(1);
            });
            $(".catalogueSelector").change(function(){
                $('.columnInputs').html("");
                $('#whereField').val("");
            });
            $("#responseDivision").html("");

              
    fnInitializeDatePicker();

           
        },//end renderContent
        render: function(key,current) {
            if (typeof console!="undefined")console.info("ActionViewer :: render ->"+ key +" current "+current);
            
            /**
            if(history.length <= 0){

                var div = $("<div class='newghost'>Query "+actionNameParam+" </div>");
                
                //div.append(img);
                //if(label != null)div.append("<div class='customLabel'>"+label+"</div>");
                $("#historyContent").append(div);
                type="ghost";
            }
            else{
                if(window.historyBar.getCurrentKey() == key){
                    var div = $("<div class='newcurrent'>Query "+actionNameParam+" "+history.length+" </div>");
                }else{
                    var div = $("<div class='new1'>Query "+actionNameParam+" "+history.length+" </div>");
                }

                
                
                //div.append(img);
                //if(label != null)div.append("<div class='customLabel'>"+label+"</div>");
                $("#historyContent").append(div);
                
                type="solid";

                div.dblclick(function() {
                    if (typeof console!="undefined")console.info("ActionViewer :: item doubleclicked ->"+ key);
                    window.historyBar.cleanGhost();
                    //var item = window.historyBar.getItem(key);
                    window.historyBar.setFocus(key);
                });//end dbclick
            }//end else







            return;
            */
            if(history.length <= 0){
                               
                var div = $("<div class='floaters'></div>");
                var img =   $( "<img alt='" +"image missing"+"' class='ghost'  />" ).attr( "src",imagePath );
                div.append(img);
                if(label != null)div.append("<div class='customLabel'>"+label+"</div>");
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
                if(label != null)div.append("<div class='customLabel'>"+label+"</div>");
                if(key==current){
                    div.addClass('current');
                }
                $("#historyContent").append(div);
                type="solid";

                div.dblclick(function() {
                    if (typeof console!="undefined")console.info("ActionViewer :: item doubleclicked ->"+ key);
                    window.historyBar.cleanGhost();
                    //var item = window.historyBar.getItem(key);
                    window.historyBar.setFocus(key);
                });//end dbclick
            }//end else
        }//end render
    };//end public methods
}//end class
