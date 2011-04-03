function UploadViewer(imageParam,typeParam,actionNameParam,contentParam,labelParam) {

    var className = "UploadViewer";
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


    
   

    return {
        

        
        getClassName: function() {
            if (typeof console!="undefined")console.info("UploadViewer :: getClassName");
            return className;
        },
        addStep: function(resultParam) {
            if (typeof console!="undefined")console.info("UploadViewer :: addStep -> Html not shown");

            result =resultParam;



        },
      
        setLabel: function(labelParam) {
            if (typeof console!="undefined")console.info("UploadViewer :: setLabel -> " +labelParam);
            label=labelParam;

        },
        getLabel: function() {
            if (typeof console!="undefined")console.info("UploadViewer :: getLabel");
            return label;
        },


        setImagePath: function(path) {
            if (typeof console!="undefined")console.info("UploadViewer :: setImagePath -> " +path);
            imagePath = path;
        },
        getImagePath: function() {
            if (typeof console!="undefined")console.info("UploadViewer :: getImagePath");
            return imagePath;
        },
        setContent: function(contentParam) {
            if (typeof console!="undefined")console.info("UploadViewer :: setContent -> " +contentParam);
            content = contentParam;
        },
        getContent: function() {
            if (typeof console!="undefined")console.info("UploadViewer :: getContent");
            return content;
        },

        getType: function() {
            if (typeof console!="undefined")console.info("UploadViewer :: getType -> " + type);
            return type;
        },
        setType: function(typeParam) {
            if (typeof console!="undefined")console.info("UploadViewer :: setType -> " +typeParam);
            type =typeParam;
        },
        renderContent: function() {
            if (typeof console!="undefined")console.info("UploadViewer :: renderContent");
            try{
                
                window.workspace.setDisplay(actionName);
            
                $("#currentDisplay").find("#label").val(label);
                
                if(result != null){
                

            
                    $("#responseDivision").html(result);
                   
                
                    $('#displayableResult').append($('#tables'));
                    $("#myForm").remove();

                    $('#displayableResult').css("display","block");
                    $("#responseDivision").html("");
                     $('.resultTable').each(function(){

                        fnFormatTable(this.id);

                    });

                    fnInitSave();
                }
            


            
            
                $("#currentDisplay").find("#delete").click(function(){
                    window.historyBar.removeCurrent();
                });
                $("#currentDisplay").find("#label").change(function() {
                    window.historyBar.getCurrent().setLabel($(this).val());
                    window.historyBar.render(1);
                });
            }catch(err){
                $("#responseDivision").html("");
                //$("#currentDisplay").remove();
                
                result=null;
                //window.workspace.setDisplay(actionName);
                
                this.renderContent();
                var options = {
                    target: '#responseDivision',   // target element(s) to be updated with server response
                    success: fnOnComplete  // post-submit callback
                };
                $('#myForm').ajaxForm(options);
                
                
                
                
                $("#myForm").append("<br><br><span style='color:red'>Error Ocurred when parsing the VoTable please revise your syntax.</span>");
                
            //window.historyBar.removeCurrent();

                
            }
            $( "input:submit").button();
            $( ".custom_button").button();
            
        },
        render: function(key) {

            if (typeof console!="undefined")console.info("UploadViewer :: render ->"+ key);
            

            if(result == null){

                // console.log("rendering wild ghost");
                var div = $("<div class='floaters'></div>");
                var img =   $( "<img alt='" +"image missing"+"' class='ghost'  />" ).attr( "src",imagePath );
                div.append(img);
                type = 'ghost';
                if(label != null)div.append("<div class='customLabel'>"+label+"</div>");
                $("#historyContent").append(div);
                
            }else{
                // console.log("im at query");

                type = 'solid';
                div = $("<div class='floaters'></div>");
                img =   $( "<img alt='" +"image missing"+"'   />" ).attr( "src",imagePath );
                div.append(img);
                if(label != null)div.append("<div class='customLabel'>"+label+"</div>");
                $("#historyContent").append(div);
                

                div.dblclick(function() {
                    if (typeof console!="undefined")console.info("UploadViewer :: item doubleclicked ->"+ key);
                    window.historyBar.cleanGhost();
                    //var item = window.historyBar.getItem(key);
                    window.historyBar.setFocus(key);

                });//end dbclick
            }
        }

    };
}
