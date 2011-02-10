function History() {
  // Private variable
  
  var array=[];
  var limit = 14;
  var offset =0;

  // Private method
  var privateMethod = function(){
    // Access to private fields
    
  };

  return {
    // Public methods
    addItem: function(item) {
      var prevItem =array.pop();
      if(prevItem != null &&prevItem.getType()!="ghost"){
        array.push(prevItem);
      }
      array.push(item);
      array.length >=limit ? offset = array.length-limit: offset =0;

    },
    getItem: function(index) {
      return array[index];

    },
    solidify: function(query){
      array[array.length-1].setType("solid");
      array[array.length-1].setContent(query);
    },
    render: function(){
        //console.log("History => render ");
        $('#history2').html('');
        var key = 0;
        
        
        

        for(key = offset;key < array.length;key++) {
            
            
            if(key <limit+offset)array[key].render(key);
        }
         $("#history2 img[title]").tooltip({position: "top center",delay: 100,predelay:500 });
        


    },

    shiftRight: function() {
      offset--;
      if(offset < 0)offset =0;
      this.render();
    },
    shiftLeft: function() {
      offset++;
      if(offset > array.length-1)offset =array.length-1;
      if(offset < 0)offset =0;
      this.render();
    },
    clear: function(){
        array = [];
        this.render();
    }
  };
}
