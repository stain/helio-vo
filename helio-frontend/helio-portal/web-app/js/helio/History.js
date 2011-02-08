function History() {
  // Private variable
  var name;
  var array=[];

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
        for (key in array){
            array[key].render(key);
        }
         $("#history2 img[title]").tooltip({position: "top center",delay: 100,predelay:500 });
        


    },
    getName: function() {
      return name;
    },
    getName2: function() {
      return name;
    },
    clear: function(){
        array = [];
        this.render();
    }
  };
}
