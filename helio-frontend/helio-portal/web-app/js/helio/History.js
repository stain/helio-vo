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

            $( "#history" ).droppable({
                accept: ".drop_able",
                activeClass: "ui-state-hover",
                hoverClass: "ui-state-active",
                drop: function( event, ui ) {
                    

                    var testver =ui.draggable.attr('src');
                    var title ="";
                    var div = $("<div  title='"+"noTitle"+"' class='floaters'></div>");
                    var table =$('<table border="0" cellpadding="0" cellspacing="0"></table>');
                    var tr =$("<tr></tr>");
                    var td =$("<td></td>");
                    var img =   $( "<img class='history_draggable' alt='"+"image missing"+"'/>" ).attr( "src",ui.draggable.attr('src') );
                    if(testver.indexOf('time') != -1){
                        img.data('time_data',$("#time_area").html());
                        img.attr('time_data',$("#time_area").html());
                    }
                    if(testver.indexOf('event')!= -1){
                        img.data('event_data',$("#extra_list").html());
                        img.attr('event_data',$("#extra_list").html());
                    }
                    if(testver.indexOf('inst')!= -1){
                        img.data('inst_data',$("#extra_list").html());
                        img.attr('inst_data',$("#extra_list").html());
                    }
                    td.append(img);
                    img.draggable({
                        revert: "invalid",
                        helper:"clone",
                        zIndex: 1700
                    });
                    
                    
                    
                    tr.append(td);

                    table.append(tr);
                    div.append(table);

                  



                    $("#historyContent").append(div);
                    saveHistoryBar();


                }
            });
 
        },
        initSaved: function() {

            
            
            $(".history_draggable").each(function(){
               // alert("called inside");
                var img = $(this);
                img.draggable({
                        revert: "invalid",
                        helper:"clone",
                        zIndex: 1700
                    });
                    if(img.attr('time_data') != -1){
                        img.data('time_data',img.attr('time_data'));
                        
                    }
                    if(img.attr('event_data') != -1){
                        img.data('event_data',img.attr('event_data'));
                        
                    }
                    if(img.attr('inst_data') != -1){
                        img.data('inst_data',img.attr('inst_data'));

                    }
            });
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
            //if(array.length >0)current--;
            current =-1;
            
          
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
            $(".floadters[title]").tooltip({
                position: "center right",
                delay: 0,
                predelay:500
            });
            
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
