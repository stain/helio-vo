function Workspace() {

    var divisions = new Object();
    var element;

  
    var ingestDivision = function(keyName,divisionName){
        divisions[keyName] = $(divisionName).html();
        $(divisionName).remove();
    };

    return {
        
        //Iinitializes by filling the @divisions map with the html contained in the sections, also removes the html to not overlap ids
        init: function() {
            if (typeof console!="undefined")console.info("Workspace :: init");

            ingestDivision("hec_extended","#displayableHEC_extended");
            ingestDivision("hec","#displayableHEC");
            ingestDivision("ics","#displayableICS");
            ingestDivision("ils","#displayableILS");
            ingestDivision("dpas","#displayableDPAS");
            ingestDivision("upload_vot","#displayableUpload");
            ingestDivision("loading","#displayableOnLoading");
            ingestDivision("error","#displayableError");
            ingestDivision("splash","#displayableSplash");
            ingestDivision("selected_result","#displayableSeletedResult");

            this.setDisplay("splash");
            
        },
        getElement: function(){
            if (typeof console!="undefined")console.info("Workspace :: getElement");
            return this.element;
            

        },
        setElement: function(element){
            if (typeof console!="undefined")console.info("Workspace :: setElement");
            //if(element ==null)return;
            this.element = element;
            element.renderContent();
           
        },
        onLoading: function(){
            if (typeof console!="undefined")console.info("Workspace :: onLoading");
         
            var element = window.historyBar.getCurrent();
            element.prepareStep($("#currentDisplay").find("form").serialize(),$('#currentDisplay').find('.columnInputs').html());

            this.setDisplay("loading");
           
        },
        setDisplay: function(key){
            if (typeof console!="undefined")console.info("Workspace :: setDisplay -> " +key);
            this.clear();
            var newDiv = $('<div></div>');
            if(divisions[key] == null){
                key = "error";
            }//end if
            newDiv.html(divisions[key]);
            newDiv.css("display","block");
            newDiv.attr("id","currentDisplay");
            newDiv.attr("class","displayable");
            $("#droppable-inner").append(newDiv);
            fnInitializeDatePicker();
        },
        createItem: function(imagePath){
            if (typeof console!="undefined")console.info("Workspace :: createItem -> " +imagePath);

            var fields =imagePath.split('/');
            var text = fields[fields.length-1];
            fields = text.split(".");
            text = fields[0];
            //this.render(text);

            var element;

            switch (text) {
                case 'hec_extended':
                    element = new ActionViewer(imagePath,"ghost",text,"label","hec");
                    window.historyBar.addItem(element);
                    window.historyBar.render();
                    fnInitHecExtended();
                    break;
                case 'hec':
                    element = new ActionViewer(imagePath,"ghost",text,"label","hec");
                    window.historyBar.addItem(element);
                    window.historyBar.render();
                    break;
                case 'ics':
                    element = new ActionViewer(imagePath,"ghost",text,"label","ics");
                    window.historyBar.addItem(element);
                    window.historyBar.render();
                    $.collapsible(".header", "group1");
                    

                    break;
                case 'ils':
                    element = new ActionViewer(imagePath,"ghost",text,"label","ils");
                    window.historyBar.addItem(element);
                    window.historyBar.render();
                    break;
                case 'dpas':
                    element = new ActionViewer(imagePath,"ghost",text,"label","dpas");
                    window.historyBar.addItem(element);
                    window.historyBar.render();
                    $("#droppable-inner").data("content",$("#instArea").html());
                    break;
                case 'upload_vot':
                    element = new UploadViewer(imagePath,"ghost",text);
                    window.historyBar.addItem(element);
                    window.historyBar.render();
                          var options = {
                target: '#responseDivision',   // target element(s) to be updated with server response
                success: fnOnComplete  // post-submit callback
            };
            $('#myForm').ajaxForm(options);
                    break;
                default:
                    break;
            }//end case

  $("#currentDisplay").find("#delete").click(function(){
                window.historyBar.removeCurrent()
            });
            fnInitializeDatePicker();
            $( "input:button").button();
            $( ".controls").button();
            $( ".custom-button").button();
            $( "input:submit").button();
              //@TODO: tooltips
    //tooltipme
     $(".tooltipme").tooltip({
        position: "top center",
        delay: 0,
        predelay:0
    });

//$("#minDate").val(window.minDate);
//$("#maxDate").val(window.maxDate);
$("#minDate").val($.cookie("minDate"));
$("#maxDate").val($.cookie("maxDate"));


        },
        render: function() {
            if (typeof console!="undefined")console.error("Workspace :: render");
            return;
      
            
            
            
            
            $( "#droppable-inner" ).droppable({
                accept: ".draggable",

                activeClass: "ui-state-hover",
                hoverClass: "ui-state-active",

                drop: function( event, ui ) {
                    var text =  ui.draggable.find("img").attr("src");

                    window.workspace.createItem(text);
                }
            });
            
  
    

            fnInitDroppable();
            fnInitializeDatePicker();

   


        },
        clear: function() {
            if (typeof console!="undefined")console.info("Workspace :: clear");
          
            $("#currentDisplay").remove();
            $(".displayable").css("display","none");
            $("#currentDisplay").remove();
            $(".resCont").remove();
            


        }

    
   
    };
}
