function ResultViewer(imageParam,typeParam,actionNameParam,contentParam,labelParam) {

    // Private variable


    var className = "ResultViewer";
    var actionName = actionNameParam;
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

$('.displayable').css("display","none");
            $("#staticFormContent").html("");

        var content = window.historyBar.getCurrent().getContent();
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
            return;
            if (typeof console!="undefined")console.info("ResultViewer :: renderContent");
            window.workspace.setDisplay(actionName);

            $("#currentDisplay").find("#label").val(label);

            if(result != null){


                $("#responseDivision").html(result);
                $('.resultTable').each(function(){

                    fnFormatTable(this.id);

                });

                $('#displayableResult').append($('#tables'));


                $('#displayableResult').css("display","block");
                $("#responseDivision").html("");

                fnInitSave();
            }





            $("#currentDisplay").find("#label").change(function() {
                window.historyBar.getCurrent().setLabel($(this).val());
                window.historyBar.render(1);
            });


        },
        render: function(key) {
            if (typeof console!="undefined")console.info("ResultViewer :: render ->"+ key);

                type = 'solid';
                div = $("<div id='"+key+"' class='floaters resultDraggable'></div>");
                img =   $( "<img alt='" +"image missing"+"'   />" ).attr( "src",imagePath );
                div.append(img);
                if(label != null)div.append("<div class='customLabel'>"+label+"</div>");
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
                                window.historyBar.render();
                            });

                        }
                        $(this).data('returnMe',false)






                    }
                });
            }};
}
