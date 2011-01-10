/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function()
{
    $("#section-navigation img[title]").tooltip();

    var dates = $('#minDate, #maxDate').datepicker({
        defaultDate: "+1w",
        changeMonth: true,
        changeYear: true,
        numberOfMonths: 3,
        onSelect: function(selectedDate) {
            var option = this.id == "minDate" ? "minDate" : "maxDate";
            var instance = $(this).data("datepicker");
            var date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
            dates.not(this).datepicker("option", option, date);
        }
    });


    $("#minTimeHEC").AnyTime_picker(
    {
        format: "%H:%i",
        labelTitle: "Hour",
        labelHour: "Hour",
        labelMinute: "Minute"
    } );


    $("#maxTimeHEC").AnyTime_picker(
    {
        format: "%H:%i",
        labelTitle: "Hour",
        labelHour: "Hour",
        labelMinute: "Minute"
    } );

    var history = $( "#history" );

    $( ".draggable" ).draggable({
        opacity:0.7,
        helper:"clone"
    });

    $( "#droppable-inner" ).droppable({
        accept: ".draggable",

        activeClass: "ui-state-hover",
        hoverClass: "ui-state-active",
        drop: function( event, ui ) {
            var text =  ui.draggable.find("img").attr("src");
            var red = 1;
            var result = null;
            switch (text) {
                case './images/icons/toolbar/catalogue50.png':
                     
                    $(".displayable").css("display","none");
                    $("#displayableCatalogue").css("display","block");
                    break;
                case './images/icons/toolbar/event50.png':
                    
                    $(".displayable").css("display","none");
                    $("#displayableEvent").css("display","block");
                    break;
                case './images/icons/toolbar/instrument50.png':
                    
                    $(".displayable").css("display","none");
                    $("#displayableInstrument").css("display","block");
                    break;
                case './images/icons/toolbar/observableentity50.png':

                    $(".displayable").css("display","none");
                    $("#displayableObservable").css("display","block");
                    break;
                case './images/icons/toolbar/timerange50.png':

                    $(".displayable").css("display","none");
                    $("#displayableTime").css("display","block");
                    break;
                case './images/icons/toolbar/date50.png':

                    $(".displayable").css("display","none");
                    $("#displayableDate").css("display","block");
                    break;
                default:
                    $(".displayable").css("display","none");
                    alert(ui.draggable.find("img").attr("src"));
                    ;
            }
   
           
            var imageworks = ui.draggable.find("img").attr("src");
            imageworks =imageworks.replace("50.png",".png");
            var imgString =$( "<img alt='" + "hola" + "' class='floaters' style='float:left; padding:10px;' />" ).attr( "src", imageworks).appendTo("#history");
            // imgString =imgString.replace("50.png",".png")
            //             $(imgString).appendTo("#history");
            // var img = $( "<img alt='" + "hola" + "' class='floaters' style='float:left; padding:10px;width:40px;height:40px;' />" ).attr( "src", ui.draggable.find("img").attr("src") ).appendTo( "#history" );

            //$(this).html("<form>First name: <input type='text' "+"name='firstname' /><br />Last name: <input type='text' name='lastname' /></form>  ");
        }});


     
    });
    function recycleImage( $item ) {
             

             
        $item.fadeOut(function() {
            $item
            .find( "a.ui-icon-refresh" )
            .remove()
            .end()
            .css( "width", "96px")
            .append( trash_icon )
            .find( "img" )
            .css( "height", "72px" )
            .end()
            .appendTo( $gallery )
            .fadeIn();
        });

    };


   