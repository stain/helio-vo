function History() {
    
  
    var array=[];
    var limit = 13;
    var offset =0;
    var filter = "all";
    var current = 0;

    return {
        clear: function(){
            if (typeof console!="undefined")console.info("History :: clear");
            array = [];
            this.render();
        },
        init: function(){
            if (typeof console!="undefined")console.info("History :: init");

            $( ".draggable" ).click(function() {
                window.workspace.createItem($(this).find("img").attr("src"));
            });
             $( "#clearSystem" ).click(function() {
                window.historyBar.clear();
            });
/*
            
            $( ".draggable" ).draggable({
                opacity:0.7,
                zIndex: 5700,
                helper:"clone"
            });

  */
        },
        
        getCurrentKey:  function() {
            if (typeof console!="undefined")console.info("History :: getCurrentKey " +current);
            return current;
        },
        getCurrent:  function() {
            if (typeof console!="undefined")console.info("History :: getCurrent");
            return array[current];
        },
        removeCurrent:  function() {
            if (typeof console!="undefined")console.info("History :: removeCurrent" + current);
            
            this.removeItem(current);
            window.workspace.clear();
        },

        lastItem:  function() {
            if (typeof console!="undefined")console.info("History :: lastItem");
            return array[array.length-1]
        },
        addItem: function(item) {
            if (typeof console!="undefined")console.info("History :: addItem ->"+ item.getClassName());
            var prevItem =array.pop();
            if(prevItem != null &&prevItem.getType()!="ghost"){
                array.push(prevItem);
                
            }
            array.push(item);
            current=array.length-1;
            array.length >=limit ? offset = array.length-limit: offset =0;

        },
        setFilter: function(filterParam) {
            if (typeof console!="undefined")console.info("History :: setFilter ->"+ filterParam);
            filter = filterParam;
        },
        getItem: function(index) {
            if (typeof console!="undefined")console.info("History :: getItem ->"+ index);
            return array[index];

        },
        removeItem : function(index) {
            if (typeof console!="undefined")console.info("History :: removeItem ->"+ index);
            
            
            array.splice(index, 1);
            if(array.length >0)current--;
            
          
            this.render();

        },
        cleanGhost: function(){
            if (typeof console!="undefined")console.info("History :: cleanGhost ->");
            var element = array.pop();
            if(element.getType()=="ghost"){
                
                return;
            };
            array.push(element);
        
        },
        solidify: function(html){
            if (typeof console!="undefined")console.info("History :: solidify ->"+ html);
            
            //get current
            var element = array.pop();
            
            if(element.getType()=="ghost"){
                element.setType("query");
                element.setHtml(html);
            //$(element).data("query",$("#currentDisplay").html());
      

            //var serialized = $("#currentDisplay").find("form").serialize();
      
            //$(element).data("serialized",serialized);
            //$("#currentDisplay").remove();
      
            }
            array.push(element);
            
            this.render();


        },
        render: function(param){
            if (typeof console!="undefined")console.info("History :: render ->" + current +" param "+ param);
            
            if(param !=1){
                if(array.length >0 && current >=0){

                    window.workspace.setElement(array[current]);
                }else{
                    window.workspace.setDisplay("splash");
                }
            }
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
            
                
                if(key <limit+offset)arrayToRender[key].render(arrayToIndex[key],current);
                
            }

            fnInitDroppable();
        },

        shiftRight: function() {
            if (typeof console!="undefined")console.info("History :: shiftRight");
            offset--;
            if(offset < 0)offset =0;
            this.render();
        },
        shiftLeft: function() {
            if (typeof console!="undefined")console.info("History :: shiftLeft");
            offset++;
            if(offset > array.length-1)offset =array.length-1;
            if(offset < 0)offset =0;
            this.render();
        },
        setFocus: function(key){
            current = key;
            this.render();
        }
        
    };
}
