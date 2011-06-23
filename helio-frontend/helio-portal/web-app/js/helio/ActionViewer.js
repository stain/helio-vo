function ActionViewer() {
 
    /**
     * Called after successful loading of columns
     * @param data HTML stub containing the loaded columns
     * @param textStatus a status message.
     */
    var _onSuccessQuery = function(data,textStatus) {
       
        
    };

    /**
     * Method called in case an error occurs when loading the advanced param table.
     * @param XMLHttpREquest the underlying request
     * @param textStatus status message
     * @param errorThrown error object
     */
    var _onErrorQuery = function(xmlHttpRequest,textStatus,errorThrown) {
        
        
    };
    var _onBeforeQuery = function(){
        
    };

 

    
    
    

    return {
        // Public methods
        init: function(){
            fnInitializeDatePicker();
            if($.cookie("minDate")==null)$.cookie("minDate","2003-01-01");
            if($.cookie("maxDate")==null)$.cookie("maxDate","2003-01-03");
            if($.cookie("minTime")==null)$.cookie("minTime","00:00");
            if($.cookie("maxTime")==null)$.cookie("maxTime","00:00");
            $("#minDate").val($.cookie("minDate"));
            $("#maxDate").val($.cookie("maxDate"));
            $("#minTime").val($.cookie("minTime"));
            $("#maxTime").val($.cookie("maxTime"));

            //$('.submit_button').button({disabled: !$(".catalogueSelector input:checked").val()});
            $.collapsible(".queryHeader","group1");
            $.collapsible(".advancedParameters","group2");
            $( ".custom_button").button();
            $( ".submit").button();
            
            var options = {
                target:        '#responseDivision',   // target element(s) to be updated with server response
                //beforeSerialize: _onBeforeSerQuery,
                beforeSubmit:  _onBeforeQuery,  // pre-submit callback
                success:       _onSuccessQuery,  // post-submit callback
                error:         _onErrorQuery,
                // other available options:
                //url:       "asyncQuery",        // override for form's 'action' attribute
                //type:      'POST'        // 'get' or 'post', override for form's 'method' attribute
                //dataType:  null        // 'xml', 'script', or 'json' (expected server response type)
                //clearForm: true        // clear all form fields after successful submit
                //resetForm: true        // reset the form after successful submit

                // $.ajax options can be used here too, for example:
                timeout:   50000
            };

            // bind form using 'ajaxForm'

            $('#actionViewerForm').ajaxForm(options);

            

            

        
         
       
      


          
        },
       
    
      
        renderContent: function() {
            
            
            if(history.length > 0){

                var result = history[step].result;
                var formData = history[step].formData;
                var advancedSearch= history[step].advancedSearch;
                _unserialize(formData,advancedSearch);
                $("#responseDivision").html(result);
                $('#displayableResult').append($('#tables'));
                $('#displayableResult').css("display","block");
                fnInitSave();
                $("#responseDivision").html("");
               
                $('.resultTable').each(function(){
                    fnFormatTable(this.id);
                });
                _initSolidElements();
            }
            _initGhostElements();           
            $(".tooltipme").tooltip({
                position: "top center",
                delay: 0,
                predelay:0
            });
        },//end renderContent
        render: function(key,current) {
            if (typeof console!="undefined")console.info("ActionViewer :: render ->"+ key +" current "+current);

            if(history.length <= 0){

                //var title ="Element contains no data";
                var title ="";
                var div = $("<div  title='"+title+"' class='floaters'></div>");
                var table =$('<table border="0" cellpadding="0" cellspacing="0"></table>');
                var tr =$("<tr></tr>");
                var td =$("<td></td>");
                var img =   $( "<img alt='" +"image missing"+"' class='ghost'  />" ).attr( "src",imagePath );
                td.append(img);
                tr.append(td);
                if(label != null){
                    td =$("<td></td>");
                    td.css("padding-left","3px");
                    td.append(label);
                    tr.append(td);
                }
                if(key==current){
                    div.addClass('current');
                }
                table.append(tr);
                div.append(table);
                $("#historyContent").append(div);
                type="ghost";
            }else{
                //var title ="<div>Number of elements: "+history.length+"<br>Label: "+label+"<br>Service name: "+serviceName+"</div>";
                var title ="";
                var div = $("<div  title='"+title+"' class='floaters'></div>");
                var table =$('<table border="0" cellpadding="0" cellspacing="0"></table>');
                var tr =$("<tr></tr>");
                var td =$("<td></td>");
                var img =   $( "<img alt='"+"image missing"+"'/>" ).attr( "src",imagePath );
                td.append(img);
                tr.append(td);
                if(label != null){
                    td =$("<td></td>");
                    td.css("padding-left","3px");
                    td.append(label);
                    tr.append(td);
                }
                table.append(tr);
                div.append(table);
                if(key==current){
                    div.addClass('current');
                    
                    for(var i=0;i < history.length;i++){
                        var pageDiv =$("<div style='cursor:pointer' id='"+i+"' class='ui-state-default new1'>"+"Page "+(i+1)+"</div>");
                        pageDiv.click(function(){

                            step = parseInt($(this).attr('id'),10);
                            $('#currentDisplay').fadeOut(300, function(){
                                window.historyBar.cleanGhost();
                                window.historyBar.setFocus(key);
                            //window.historyBar.render();
                            });
                        

                        });
                        div.append(pageDiv);
                    }
                    

                }else{
                    div.css("cursor","pointer");
                    div.click(function() {
                        if (typeof console!="undefined")console.info("ActionViewer :: item clicked ->"+ key);

                        $('#currentDisplay').fadeOut(300, function(){
                            window.historyBar.cleanGhost();
                            window.historyBar.setFocus(key);
                        //window.historyBar.render();
                        });


                    //var item = window.historyBar.getItem(key);


                    });//end dbclick
                }
                
                $("#historyContent").append(div);
                
                type="solid";

               
            }//end else
        }//end render
    };//end public methods
}//end class
