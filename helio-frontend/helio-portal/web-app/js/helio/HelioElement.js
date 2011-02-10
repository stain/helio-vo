
function HelioElement(imageParam,typeParam,contentParam) {
  // Private variable
  //console.log("HelioElement created "+ imageParam);
  var image;
  var type=typeParam;
  var content=contentParam;
  var imagePath = imageParam;

  // Private method
  var privateMethod = function(){
    // Access to private fields
    //name += " Changed";
  };

  return {
    // Public methods
    setImage: function(image) {
      this.image=image;
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
     getName: function() {
      return 1;
    },
      getType: function() {
      return type;
    },
      setType: function(typeParam) {
      type =typeParam;
    },
    render: function(key) {


        if(type=="ghost"){
            $( "<img alt='" +"image missing"+"' class='floaters ghost'  />" ).attr( "src",imagePath ).appendTo("#history2").fadeIn();
        }
        else if(type == 'solid'){
          $( "<img title='"+content+"' alt='" + "image missing" + "' class='floaters'  />" ).attr( "src",imagePath ).appendTo("#history2").fadeIn();
        }
        else if(type == 'result'){
            
          $( "<img id='"+key+"' title='"+content.count+"' alt='" + "image missing" + "' class='floaters resultDraggable'  />" ).attr( "src",imagePath ).appendTo("#history2").fadeIn();

          var draggable = $("#"+key);
          draggable.data("Left", 0).data("Top", 0);
          draggable.data('returnMe',false);

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
                       
                            fnclearDateTexts();
                            $("#instArea").html($("#instArea").data("content"));
                            $(dropBox).removeClass("ui-state-active");
                            $( dropBox).removeClass( "ui-state-highlight" );
                             $(this).animate({ "left": $(this).data("Left"),"top": $(this).data("Top")}, "slow",function(){
                                window.varx.render();
                            });

                   }
                   $(this).data('returnMe',false)
                           
                           

                            
                  
                  
              }
          });

        }
        
    }

  };
}
