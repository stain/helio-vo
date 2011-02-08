function Shelf() {
  // Private variable
  var name;
  var array=[];

  // Private method
  var privateMethod = function(){
    // Access to private fields
    name += " Changed";
  };

  return {
    // Public methods
    addItem: function(item) {
      array.push(item);
      
    },
    getItem: function(index) {
      return array[index];
      
    },

    getName: function() {
      return name;
    },
    getName2: function() {
      return name;
    }
  };
}
