function History() {
    // Private variable
  
    var array=[];
    var limit = 13;
    var offset =0;
    var filter = "all";

    // Private method
    var privateMethod = function(){
    // Access to private fields
    
    };

    return {
        // Public methods
        lastItem:  function() {
            return array[array.length-1]
        },
        addItem: function(item) {
            var prevItem =array.pop();
            if(prevItem != null &&prevItem.getType()!="ghost"){
                array.push(prevItem);
            }
            array.push(item);
            array.length >=limit ? offset = array.length-limit: offset =0;

        },
        setFilter: function(filterParam) {
            filter = filterParam;
        },
        getItem: function(index) {
            return array[index];

        },
        removeItem : function(index) {
          array.splice(index, 1);
          
          this.render();

        },
        cleanGhost: function(){
        
            var element = array.pop();
            if(element.getType()=="ghost"){
                this.render();
                return;
            };
            array.push(element);
        
        },
        solidify: function(query){
            var element = array.pop();
        
            if(element.getType()=="ghost"){
                element.setType("query");
                element.setContent(query);
                $(element).data("query",$("#currentDisplay").html());
      

                var serialized = $("#currentDisplay").find("form").serialize();
      
                $(element).data("serialized",serialized);
                $("#currentDisplay").remove();
      
            }
            array.push(element);


        },
        render: function(){
            //console.log("History => render ");

            $('#historyContent').html('');
            var key = 0;
        
            var arrayToRender = [];
            var arrayToIndex = [];
            if(filter=='all'){
                arrayToRender=array;
                for(var i = 0;i < array.length;i++) {
                    arrayToIndex.push(i);
                }
            }
            else if(filter=='results'){
                for(i = 0;i < array.length;i++) {
                    

                    if(array[i].getType() == 'nativeResult'){
                        arrayToRender.push(array[i]);
                        arrayToIndex.push(i);
                    }

                }

            }
            else if(filter=='actions'){
                for(i = 0;i < array.length;i++) {
                    

                    if(array[i].getType() == 'query'){
                        arrayToRender.push(array[i]);
                        arrayToIndex.push(i);
                    }

                }

            }
            else if(filter=='selections'){
                for(i = 0;i < array.length;i++) {
                    

                    if(array[i].getType() == 'resultSelection'){
                        arrayToRender.push(array[i]);
                        arrayToIndex.push(i);
                    }

                }

            }

        

            for(key = offset;key < arrayToRender.length;key++) {
            
            
                if(key <limit+offset)arrayToRender[key].render(arrayToIndex[key]);
            }
            $("#historyContent img[title]").tooltip({
                position: "top center",
                delay: 100,
                predelay:500
            });
        


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
