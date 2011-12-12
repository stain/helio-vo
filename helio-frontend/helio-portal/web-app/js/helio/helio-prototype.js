
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
});
