function ResultViewer(imageParam,typeParam,resultHtmlParam,contentParam,labelParam) {

    // Private variable


    var className = "ResultViewer";
    var resultHtml = resultHtmlParam;
    var type = typeParam;
    var content = contentParam;
    var imagePath = imageParam;
    var label = labelParam;
    var resulthtml;
    var prevData;
    var printKey;
    var step =0;
    var history = new Array();
    var result;


    // Private method


    return {
        // Public methods


        getClassName: function() {
            if (typeof console!="undefined")console.info("ResultViewer :: getClassName");
            return className;
        },
        addStep: function(resultParam) {
            if (typeof console!="undefined")console.info("ResultViewer :: addStep -> Html not shown");

            result =resultParam;



        },

        setLabel: function(labelParam) {
            if (typeof console!="undefined")console.info("ResultViewer :: setLabel -> " +labelParam);
            label=labelParam;

        },
        getLabel: function() {
            if (typeof console!="undefined")console.info("ResultViewer :: getLabel");
            return label;
        },


        setImagePath: function(path) {
            if (typeof console!="undefined")console.info("ResultViewer :: setImagePath -> " +path);
            imagePath = path;
        },
        getImagePath: function() {
            if (typeof console!="undefined")console.info("ResultViewer :: getImagePath");
            return imagePath;
        },
        setContent: function(contentParam) {
            if (typeof console!="undefined")console.info("ResultViewer :: setContent -> " +contentParam);
            content = contentParam;
        },
        getContent: function() {
            if (typeof console!="undefined")console.info("ResultViewer :: getContent");
            return content;
        },

        getType: function() {
            if (typeof console!="undefined")console.info("ResultViewer :: getType -> " + type);
            return type;
        },
        setType: function(typeParam) {
            if (typeof console!="undefined")console.info("ResultViewer :: setType -> " +typeParam);
            type =typeParam;
        },
        renderContent: function() {
            if (typeof console!="undefined")console.info("ResultViewer :: renderContent");
            
            window.workspace.setDisplay("selected-result");
            var content = window.historyBar.getCurrent().getContent();
            //console.debug(content);
            $("#displayableResult").html(resultHtml);
            $("#displayableResult").css('display','block');
            $("#resultSelectionSave").remove();
            $("#displayableResult").find("input[type=submit]").remove();
            for(i in content){

                var time_start;
                var time_end;
                var tar_object;
                var obsinst_key;
                for(j in content[i]){

                    if(j == "time_start "){
                        time_start = content[i][j];
                        
                    }
                    if(j == "time_end "){
                        time_end = content[i][j];
                        
                    }
                    if(j == "tar_object "){
                        tar_object = content[i][j];
                        
                    }
                    if(j == "obsinst_key "){
                        obsinst_key = content[i][j];

                    }

                //$("#time-row").append("<li> "+j +"  : " +content[i][j]+" </li>");
                //$("#instrument-row").append("<li> "+j +"  : " +content[i][j]+" </li>");
                    

                }
                if(time_start != null && time_end != null) {
                    $("#time-row").append("<div> <div class='subbing'>-</div><div class='adding'>+</div><div style='float:left;'>St: </div><ul>"+time_start+"</ul></div>");
                    $("#time-row").append("<div> <div class='subbing'>-</div><div class='adding'>+</div><div style='float:left;'>Et: </div><ul>"+ time_end+"</ul></div>");
                }
                if(tar_object != null ) $("#observatory-row").append("<ul>"+tar_object+"</ul>");
                if(obsinst_key != null ) $("#instrument-row").append("<ul>"+obsinst_key+"</ul>");
            //2003-01-01T07:49:00 / 2003-01-01T07:59:00
               
            }
            $(".adding").click(function(){
                if (typeof console!="undefined")console.info("ResultViewer :: adding click");

                var time_start = $(this).parent().children("ul").text();
                var fields = time_start.split("T");
                var first = fields[0].split("-");
                var second = fields[1].split(":");
                    
                var d = new Date(first[0], first[1], first[2], second[0], second[1], second[2]);


                    
                d.setMinutes(d.getMinutes()+30);
                    

                var month = d.getMonth()<10? "0"+d.getMonth():d.getMonth();
                var day = d.getDate()<10?"0"+d.getDate():d.getDate();
                var hour =  d.getHours()<10?"0"+d.getHours():d.getHours();
                var minutes = d.getMinutes()<10?"0"+d.getMinutes():d.getMinutes();
                var seconds= d.getSeconds()<10?"0"+d.getSeconds():d.getSeconds();

                $(this).parent().find("ul").text(d.getFullYear()+"-"+month+"-"+day+"T"+hour+":"+minutes+":"+seconds);
            });
            $(".subbing").click(function(){
                var time_start = $(this).parent().find("ul").text();
                var fields = time_start.split("T");
                var first = fields[0].split("-");
                var second = fields[1].split(":");
                var d = new Date(first[0], first[1], first[2], second[0], second[1], second[2], 0);
                d.setMinutes(d.getMinutes()-30);

                var month = d.getMonth()<10? "0"+d.getMonth():d.getMonth();
                var day = d.getDate()<10?"0"+d.getDate():d.getDate();
                var hour =  d.getHours()<10?"0"+d.getHours():d.getHours();
                var minutes = d.getMinutes()<10?"0"+d.getMinutes():d.getMinutes();
                var seconds= d.getSeconds()<10?"0"+d.getSeconds():d.getSeconds();

                $(this).parent().find("ul").text(d.getFullYear()+"-"+month+"-"+day+"T"+hour+":"+minutes+":"+seconds);
            });
            

            $("#currentDisplay").find("#delete").click(function(){
                window.historyBar.removeCurrent()
            });
            $("#currentDisplay").find("#label").change(function() {
                window.historyBar.getCurrent().setLabel($(this).val());
                window.historyBar.render(1);
            });

        },
        render: function(key,current) {
            //       if (typeof console!="undefined")console.info("ResultViewer :: render ->"+ key);
            /*

            if(window.historyBar.getCurrentKey() == key){
                    var div = $("<div class='newcurrent'> "+"Result Selection"+" </div>");
                }else{
                    var div = $("<div class='new1'> "+"Result Selection"+" </div>");
                }

            
            
            
            

            
            $("#historyContent").append(div);

           

            div.dblclick(function() {
                if (typeof console!="undefined")console.info("ResultViewer :: item doubleclicked ->"+ key);
                window.historyBar.cleanGhost();
                //var item = window.historyBar.getItem(key);
                window.historyBar.setFocus(key);

            });//end dbclick

return;

  */
            if (typeof console!="undefined")console.info("ResultViewer :: render ->"+ key +" current "+current);

            type = 'solid';
            div = $("<div id='"+key+"' class='floaters resultDraggable'></div>");
            img =   $( "<img alt='" +"image missing"+"'   />" ).attr( "src",imagePath );
            div.append(img);
            if(label != null)div.append("<div class='customLabel'>"+label+"</div>");
            if(key==current){
                div.addClass('current');
            }

            $("#historyContent").append(div);

            var draggable = $("#"+key);
            draggable.data("Left", 0).data("Top", 0);
            draggable.data('returnMe',false);
                
            div.dblclick(function() {
                if (typeof console!="undefined")console.info("ResultViewer :: item doubleclicked ->"+ key);
                window.historyBar.cleanGhost();
                //var item = window.historyBar.getItem(key);
                window.historyBar.setFocus(key);

            });//end dbclick
            $( ".resultDraggable" ).draggable({
                revert: "invalid",



                zIndex: 1700,
                start: function(event,ui ) {

                    //var tooltip =$(this).data('tooltip');
                    //tooltip.getConf().opacity = 0;
                    $(".resultDroppable2").droppable("enable");
                    $(".resultDroppable").droppable("enable");



                },
                stop: function(event,ui ) {

                    //var tooltip =$(this).data('tooltip');
                    //tooltip.getConf().opacity = 1;
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
                            //window.historyBar.render();
                            //fnInitDroppable();
                            });

                    }
                    $(this).data('returnMe',false)






                }
            });//dragable
        }
    };
}
