/**
 * Main startup method to load and initialize the frontend.
 * 
 */
$(document).ready(function() {
	//Init time cookies to persist last selection.
    if($.cookie("minDate") == null)$.cookie("minDate","2003-01-01T00:00:00",{
        expires: 30
    });
    if($.cookie("maxDate") == null)$.cookie("maxDate","2003-03-01T00:00:00",{
        expires: 30
    });
    
    //Init helio id cookie to persist user interaction, HUID value comes from prototype controller.
    if($.cookie("helioSession")==null){
        $.cookie("helioSession",$("#HUID").val(),{
            expires: 30
        });
    }

    var workspace = new Workspace();
    window.workspace = workspace;
    window.workspace.init();

    var history = new History();
    window.historyBar = history;
    window.historyBar.init();

    $( "#tabs" ).tabs();//inits the main task selector
    
    //Creates dialog for session reset.
    $( ".reset_session" ).click(function(){
        $("#dialog-message").remove();
        var div =$('<div></div>');
        div.attr('id','dialog-message');
        div.attr('title','Session Change');
        var html = $("<div>Do you want to start a new session?</div>");
        div.append(html);
        $("#testdiv").append(div);
        $('#dialog-message').dialog({
            modal: true,
            height:200,
            width:200,
            buttons: {
                Yes: function() {
                    $.cookie("helioSession",$("#HUID").val(),{
                        expires: 30
                    });
                    deleteSession();
                    $("#historyContent").html("");
                    saveHistoryBar();//Saves empty history bar.
                    $("#dialog-message").dialog( "close" );
                    $("#dialog-message").remove();
                },
                No: function(){
                    $("#dialog-message").dialog( "close" );
                    $("#dialog-message").remove();

                }
            }
        });
    });

    //Get the contents from the history bar from previous session if they exist.
    getHistoryBar();

    formatButton($(".custom_button"));// makes sure every button is formatted correctly

    //Enables the taskbar buttons
    $( ".menu_item" ).click(function() {
        var task_name = $(this).attr("id");
        window.workspace.createItem(task_name);
    });
    //History-bar slider
    $("#content-slider").slider({
        animate: true,
        change: handleSliderChange,
        slide: handleSliderSlide
    });
    
    $("#task_upload2").click(function() {
        $('#content').load('../task/uploadVoTable', initVoTableUpload);
    });    
});

/**
 * Callback after loading the upload form
 */
var initVoTableUpload = function(responseText, textStatus, XMLHttpRequest) {
    // 1, format the button
    formatButton($("#btn_upload"));
    
    // 2. init the collapsible sections
    $.collapsible(".queryHeader","group1");

    //$.fn.ajaxSubmit.debug = true;
    
    // connect the upload button
    $("#btn_upload").click(function(){
        $("#upload2Form").ajaxForm({
            beforeSubmit: function() {
                $('#msg_upload').html('Submitting...');
            },
            target: '#task_result_area',   // target element(s) to be updated with server response
            success: function(data) {
                $('#msg_upload').html('');
                
                // format the reponse elements
                // 1. buttons
                formatButton($(".custom_button"));

                // 2. result table
                $(".resultTable").each(function() {fnFormatTable(this.id);});
                
                // 3. enable ok-dialogs
                $(".ok_dialog").dialog({ autoOpen: false, modal: true, width: 600,
                    buttons: { "Ok": function() { $(this).dialog("close"); }} 
                });
                
                // 4. make result area collapsible
                $.collapsible("#task_result_area .queryHeader","group2");
                
                // 5. connect table info buttons
                $(".table_info_button").click(function() {
                    var dialogId = "#" + this.id.substring(0, this.id.length - '_button'.length);
                    $(dialogId).dialog('open');
                });
                
                // 6. download all/selection button
                $("#download_selection_button").click(function(){
                    var itr= 0;
                    $(".resultTable").each(function(){
                        //console.debug($(this));
                        itr++;
                    });
                    itr = itr/2;
                    var table =$("<table></table>");
                    var download_array = $("<ul></ul>");

                    for(var i = 0;i<itr;i++){
                        var dataTable =$("#resultTable"+i).dataTable();
                        var settings = dataTable.fnSettings();
                        var download_url = -1;

                        for(var j = 0;j< settings.aoColumns.length;j++){
                            if($.trim(settings.aoColumns[j].sTitle) == 'url'){
                                download_url=j;
                            }
                        }//end j

                        $("#resultTable"+i+" .even_selected").each(function(){
                            download_array.append("<li>"+$(this).children().eq(download_url).html()+"</li>");
                        });
                        $("#resultTable"+i+" .odd_selected").each(function(){
                            download_array.append("<li>"+$(this).children().eq(download_url).html()+"</li>");
                        });
                        if(download_array.html().indexOf('li') < 0){
                            var nNodes = dataTable.fnGetNodes();
                            for(var node in nNodes){
                                download_array.append("<li>"+$(nNodes[node]).children().eq(download_url).html()+"</li>");
                            }
                        }
                    }//end i
                    
                    var recipe =  window.open('','_blank','width=600,height=600');
                    var html = '<html><head><title>Helio Downloads</title></head><body><div id="links">'+$("#time_area").html()+$("#extra_list").html() + download_array.html() + '</div></body></html>';
                    recipe.document.open();
                    recipe.document.write(html);
                    recipe.document.close();
                });
            }
        }).submit();
    });
};