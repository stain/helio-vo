
function HelioElement(imageParam,typeParam,contentParam,labelParam) {
    // Private variable
    //console.log("HelioElement created "+ imageParam);
    
    var type=typeParam;
    var content=contentParam;
    var imagePath = imageParam;
    var label = labelParam;

    // Private method
    var privateMethod = function(){
    // Access to private fields
    //name += " Changed";
    };

    return {
        // Public methods
        setLabel: function(labelParam) {
            label=labelParam;
        },
       getLabel: function() {
            return label;
        },
        setImagePath: function(path) {
            imagePath = path;
        },
        getImagePath: function() {
            return imagePath;
        },
        setContent: function(contentParam) {
            content = contentParam;
        },
        getContent: function() {
            return content;
        },
       
        getType: function() {
            return type;
        },
        setType: function(typeParam) {
            type =typeParam;
        },
        render: function(key) {


            if(type=="ghost"){
              
                var div = $("<div class='floaters'></div>");
                var img =   $( "<img alt='" +"image missing"+"' class='ghost'  />" ).attr( "src",imagePath );
                div.append(img);
                //if(label != "")div.append("<div class='customLabel'>custom1</div>");
                $("#historyContent").append(div);
            }
            else if(type == 'query'){
            
                var item = $( "<img title='"+content+"' alt='" + "image missing" + "' class='floaters'  />" ).attr( "src",imagePath );
                item.dblclick(function() {

                    
                    $(".tooltip").css("display","none");
                    
                    $("#currentDisplay").remove();
                    var queryHtml =$(window.historyBar.getItem(key)).data("query");
                    var serializedData =$(window.historyBar.getItem(key)).data("serialized");

                    

                    var temp = $('<div></div>');
                    temp.html(queryHtml);

                    //console.dir(temp);
                    $(".displayable").css("display","none");
                    $(temp).css("display","block");
                    $(temp).attr("id","currentDisplay");
                    $(temp).attr("class","displayable");
                    
                    $("#droppable-inner").append(temp);
                    fnInitDroppable();
                    
                    var imagePath =$("#currentDisplay").find("img").attr("src");
                    
                    var element = new HelioElement(imagePath,"ghost");
                    window.historyBar.addItem(element);
                    window.historyBar.render();
                    $("#currentDisplay").find("select").find("option").removeAttr("selected");
                    var fields = serializedData.split("&");
                    for(field in fields){
                        //console.log(fields[field]);
                        var tempField= fields[field];
                        //minDateList=2003-01-01T07%3A49%3A00%2C2003-01-02T04%3A41%3A00%2C2003-01-02T12%3A58%3A00
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
                            tempField =tempField.split("%3B");
                            for(input in tempField){
                                var innerTempField = tempField[input].split("%2C");
                                var value = innerTempField[1];
                                innerTempField = innerTempField[0].split(".");
                                var inputName= innerTempField[0];
                                var labelName = innerTempField[1];
                                //console.log("inputName:"+inputName + " labelName:"+labelName+" value:"+value);

                                $("#currentDisplay").find("label:contains('"+labelName+"')").parent("li").find("input").val(value);
                            }//end input
                        }//end if
                    }//end fields
               var deleteViewer = $("#currentDisplay").find(".deleteViewer");
               
               deleteViewer.css("display","block");
               deleteViewer.data("key",key);
               deleteViewer.click(function(){
                   
                   window.historyBar.removeItem($(this).data("key"));
                   
                   $("#displayableSpalsh").css("display","block");
                   $("#currentDisplay").remove();
               });
               
                });//end dbclick


                $("#historyContent").append(item);
            }
            else if(type == 'solid'){
                $( "<img title='"+content+"' alt='" + "image missing" + "' class='floaters'  />" ).attr( "src",imagePath ).appendTo("#historyContent").fadeIn();
            }
            else if(type == 'nativeResult'){
                //$( "<img title='"+content+"' alt='" + "image missing" + "' class='floaters'  />" ).attr( "src",imagePath ).appendTo("#historyContent").fadeIn();
                    div = $("<div class='floaters'></div>");
                    img =   $( "<img id='"+key+"' title='"+content+"' alt='" +"image missing"+"'/>" ).attr( "src",imagePath );
                    div.append(img);
                    if(label != null && label != "")div.append("<div class='customLabel'>"+label+"</div>");
                    $("#historyContent").append(div);

                //$( "<img  alt='" + "image missing" + "' class='floaters'  />" ).attr( "src",imagePath ).appendTo("#historyContent").fadeIn();
                var nativeResult = $("#"+key);
          
                nativeResult.dblclick(function()
                {
                    $(".tooltip").css("display","none");
                    window.historyBar.cleanGhost();
                    var item =window.historyBar.getItem(key);
                    $("#displayableResult").html("");
                    $(".displayable").css("display","none");
                    $('#displayableResult').append($(item).data("nativeResult"));
                    $("#displayableResult").css("display","block");
                    $(".resCont").remove();
                    $('.resultTable').each(function()
                    {
                        fnFormatTable(this.id);
                        $("#"+this.id).dataTable().fnDraw();
                    });

                         var deleteViewer = $("#displayableResult").find(".deleteViewer");
                         $("#displayableResult").find(".customLabelViewer").css("display","block");
               deleteViewer.css("display","block");
               deleteViewer.data("key",key);
               var customLabel = $("#labelcustom");
               customLabel.change(function() {
                   window.historyBar.getItem(key).setLabel($(this).val());
                   window.historyBar.render();
               });


               customLabel.css("display","block");
               deleteViewer.click(function(){

                   window.historyBar.removeItem($(this).data("key"));
                   $("#displayableResult").html("");
                $("#displayableSpalsh").css("display","block");
               });
                
                
                });
             
                
            }
            else if(type == 'resultSelection'){
            
                $( "<img id='"+key+"' title='"+content.count+"' alt='" + "image missing" + "' class='floaters resultDraggable'  />" ).attr( "src",imagePath ).appendTo("#historyContent").fadeIn();

                var draggable = $("#"+key);
                draggable.data("Left", 0).data("Top", 0);
                draggable.data('returnMe',false);
                draggable.dblclick(function()
                {
                    $(".displayable").css("display","none");
                    $(".tooltip").css("display","none");
                    window.historyBar.cleanGhost();
                    $("#staticFormContent").html("");
                    
                    var content = window.historyBar.getItem(key).getContent();
                    $("#staticFormContent").append("Amount of "+ content.count);
                    for(i in content){
                        if(i=="count"){
                            continue;
                        }
                        $("#staticFormContent").append("<br>");
                        $("#staticFormContent").append("<h3>_____________________________</h3>");
                        $("#staticFormContent").append("<ul>");
                        for(j in content[i]){
                            $("#staticFormContent").append("<li>"+j +"  : " +content[i][j]+"</li>");
                        }
                        $("#staticFormContent").append("</ul>");
                        $("#displayableSeletedResult").css("display","block");
                    }
                });

                $( ".resultDraggable" ).draggable({
                    revert: "invalid",
              
              

                    zIndex: 1700,
                    start: function(event,ui ) {
                  
                        var tooltip =$(this).data('tooltip');
                        tooltip.getConf().opacity = 0;
                        $(".resultDroppable2").droppable("enable");
                        $(".resultDroppable").droppable("enable");
                  
                  
                  
                    },
                    stop: function(event,ui ) {
                  
                        var tooltip =$(this).data('tooltip');
                        tooltip.getConf().opacity = 1;
                        if($(this).data('returnMe')){
                            var dropBox =$(this).data('dropBox');
                       
                            fnclearDateTexts2();
                           
                            $("#instArea").html($("#droppable-inner").data("content"));
                            $(dropBox).removeClass("ui-state-active");
                            $( dropBox).removeClass( "ui-state-highlight" );
                            $(this).animate({
                                "left": $(this).data("Left"),
                                "top": $(this).data("Top")
                            }, "slow",function(){
                                window.historyBar.render();
                            });

                        }
                        $(this).data('returnMe',false)
                           
                           

                            
                  
                  
                    }
                });//dragable
                $(".deleteViewer").css("display","block");

            }
        
        }

    };
}
