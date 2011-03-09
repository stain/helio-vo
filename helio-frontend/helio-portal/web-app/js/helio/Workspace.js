function Workspace() {

  var divisions = new Object();
  var element;

  
    var ingestDivision = function(keyName,divisionName){
        divisions[keyName] = $(divisionName).html();
        $(divisionName).remove();
    };

    return {
        // Public methods


        init: function() {
            if (typeof console!="undefined")console.info("Workspace :: init");

            ingestDivision("hec","#displayableHEC");
            ingestDivision("ics","#displayableICS");
            ingestDivision("ils","#displayableILS");
            ingestDivision("dpas","#displayableDPAS");
            ingestDivision("upload_vot","#displayableUpload");
            ingestDivision("loading","#displayableOnLoading");
            ingestDivision("error","#displayableError");
            ingestDivision("splash","#displayableSplash");
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
            element.prepareStep($("#currentDisplay").find("form").serialize());
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
            
       },

        render: function(imagePath) {
            
            if (typeof console!="undefined")console.info("Workspace :: render -> " +imagePath);

            
            
            var fields =imagePath.split('/');
            text = fields[fields.length-1];
            fields = text.split(".");
            text = fields[0];
            
            
            
            

            var element;
            
            
            switch (text) {
                case 'hec':
                    element = new ActionViewer(imagePath,"ghost",text);
                    window.historyBar.addItem(element);
                    window.historyBar.render();
        
                    break;
                case 'ics':
                    element = new ActionViewer(imagePath,"ghost",text);
                    window.historyBar.addItem(element);
                    window.historyBar.render();
                
            
                    break;
                case 'ils':
                    element = new ActionViewer(imagePath,"ghost",text);
                    window.historyBar.addItem(element);
                    window.historyBar.render();
                
                   
                    break;
                case 'dpas':
                    element = new ActionViewer(imagePath,"ghost",text);
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
            }

            

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
