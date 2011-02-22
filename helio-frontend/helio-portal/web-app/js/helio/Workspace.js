function Workspace() {



  
    var privateMethod = function(){
    // Access to private fields
    
    };

    return {
        // Public methods


        init: function() {

            $(this).data("hec",$("#displayableCatalogue").html());
            $(this).data("ics",$("#displayableICS").html());
            $(this).data("ils",$("#displayableILS").html());
            $(this).data("dpas",$("#displayableDPAS").html());
            $(this).data("upload",$("#displayableUpload").html());
            $("#displayableCatalogue").remove();
            $("#displayableICS").remove();
            $("#displayableILS").remove();
            $("#displayableDPAS").remove();
            $("#displayableUpload").remove();

        },
        render: function(imagePath) {
            $("#currentDisplay").remove();
            //console.log("Workspace => "+ imagePath);
            var fields =imagePath.split('/');
            text = fields[fields.length-1];
            $(".displayable").css("display","none");

            var result = null;
            var temp = $('<div></div>');
            switch (text) {
                case 'event.png':

                    temp.html($(this).data("hec"));

                    $(temp).css("display","block");
                    $(temp).attr("id","currentDisplay");
                    $(temp).attr("class","displayable");

                    $("#droppable-inner").append(temp);


                    var element = new HelioElement(imagePath,"ghost");
                    window.historyBar.addItem(element);
                    window.historyBar.render();
                    $(".resCont").remove();
                    break;
                case 'ics.png':

                
                    temp.html($(this).data("ics"));
                    $(temp).css("display","block");
                    $(temp).attr("id","currentDisplay");
                    $(temp).attr("class","displayable");

                    $("#droppable-inner").append(temp);


                    var element = new HelioElement(imagePath,"ghost");
                    window.historyBar.addItem(element);
                    window.historyBar.render();
                    $(".resCont").remove();
                    break;
                case 'ils.png':

                
                    temp.html($(this).data("ils"));
                    $(temp).css("display","block");
                    $(temp).attr("id","currentDisplay");
                    $(temp).attr("class","displayable");

                    $("#droppable-inner").append(temp);


                    var element = new HelioElement(imagePath,"ghost");
                    window.historyBar.addItem(element);
                    window.historyBar.render();
                    $(".resCont").remove();
                    break;
                case 'dpas.png':

                
                    temp.html($(this).data("dpas"));
                    $(temp).css("display","block");
                    $(temp).attr("id","currentDisplay");
                    $(temp).attr("class","displayable");

                    $("#droppable-inner").append(temp);


                    var element = new HelioElement(imagePath,"ghost");
                    window.historyBar.addItem(element);
                    window.historyBar.render();
                    $(".resCont").remove();
                    $("#droppable-inner").data("content",$("#instArea").html());
                    break;
                case 'upload_vot.png':
                    temp.html($(this).data("upload"));
                    $(temp).css("display","block");
                    $(temp).attr("id","currentDisplay");
                    $(temp).attr("class","displayable");

                    $("#droppable-inner").append(temp);


                    var element = new HelioElement(imagePath,"ghost");
                    window.historyBar.addItem(element);
                    window.historyBar.render();
                    $(".resCont").remove();
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

   


        },
        getItem: function(index) {
            return array[index];

        }

    
   
    };
}
