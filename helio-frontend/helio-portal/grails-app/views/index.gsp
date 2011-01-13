


<html>
  <head>
    <meta name="layout" content="main" />
    <style type="text/css">
      #sortable1 li, #sortable2 li { margin: 0 5px 5px 5px; padding: 5px; font-size: 1.2em; }

      #tabs-1 a:hover {
        color:white
      }
	</style>


    <title>Heliophysics Integrated Observatory index</title>
    <script type="text/javascript">
          $(function() {
                var dates = $('#minDateHEC, #maxDateHEC').datepicker({
                  defaultDate: "+1w",
                  changeMonth: true,
                  dateFormat: 'yy-mm-dd',
                  changeYear: true,
                  numberOfMonths: 1,
                  onSelect: function(selectedDate) {
                    var option = this.id == "minDateHEC" ? "minDateHEC" : "maxDateHEC";
                    var instance = $(this).data("datepicker");
                    var date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
                    dates.not(this).datepicker("option", option, date);
                  }
                  });
                  var dates = $('#minDateICS, #maxDateICS').datepicker({
                  defaultDate: "+1w",
                  changeMonth: true,
                  dateFormat: 'yy-mm-dd',
                  changeYear: true,
                  numberOfMonths: 1,
                  onSelect: function(selectedDate) {
                    var option = this.id == "minDateICS" ? "minDateICS" : "maxDateICS";
                    var instance = $(this).data("datepicker");
                    var date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
                    dates.not(this).datepicker("option", option, date);
                  }
                  });
                   var dates = $('#minDateILS, #maxDateILS').datepicker({
                  defaultDate: "+1w",
                  changeMonth: true,
                  dateFormat: 'yy-mm-dd',
                  changeYear: true,
                  numberOfMonths: 1,
                  onSelect: function(selectedDate) {
                    var option = this.id == "minDateILS" ? "minDateILS" : "maxDateILS";
                    var instance = $(this).data("datepicker");
                    var date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
                    dates.not(this).datepicker("option", option, date);
                  }
                  });
                    var dates = $('#minDateDPAS, #maxDateDPAS').datepicker({
                  defaultDate: "+1w",
                  changeMonth: true,
                  dateFormat: 'yy-mm-dd',
                  changeYear: true,
                  numberOfMonths: 1,
                  onSelect: function(selectedDate) {
                    var option = this.id == "minDateDPAS" ? "minDateDPAS" : "maxDateDPAS";
                    var instance = $(this).data("datepicker");
                    var date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
                    dates.not(this).datepicker("option", option, date);
                  }
                  });
                       var dates = $('#minDateCS, #maxDateCS').datepicker({
                defaultDate: "+1w",
                changeMonth: true,
                dateFormat: 'yy-mm-dd',
                changeYear: true,
                numberOfMonths: 1,
                onSelect: function(selectedDate) {
                  var option = this.id == "minDateCS" ? "minDateCS" : "maxDateCS";
                  var instance = $(this).data("datepicker");
                  var date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
                  dates.not(this).datepicker("option", option, date);
                }
                });

$(function() {
                $("#sortable1, #sortable2").sortable().disableSelection();

                var $tabs = $("#tabs").tabs();

                var $tab_items = $("ul:first li",$tabs).droppable({
                        accept: ".connectedSortable li",
                        hoverClass: "ui-state-hover",
                        drop: function(ev, ui) {
                                var $item = $(this);
                                var $list = $($item.find('a').attr('href')).find('.connectedSortable');

                                ui.draggable.hide('slow', function() {
                                        $tabs.tabs('select', $tab_items.index($item));
                                        $(this).appendTo($list).show('slow');
                                });
                        }
                });
        });



	$(function() {
		$( "button, input:submit, a", ".demo" ).button();
		$( "#login" ).click(function() {
                //alert("hola" + $( "#loginid" ).val());
                return false;
                });
	});




          $(function() {
              $("#accordion").accordion();
          });


    $("#minTimeHEC").AnyTime_picker(
        { format: "%H:%i", labelTitle: "Hour",
          labelHour: "Hour", labelMinute: "Minute" } );
$('#workflow1').click(function() {

  window.location.replace("./states/workflow0");

});


    $("#maxTimeHEC").AnyTime_picker(
        { format: "%H:%i", labelTitle: "Hour",
          labelHour: "Hour", labelMinute: "Minute" } );

    $("#minTimeICS").AnyTime_picker(
        { format: "%H:%i", labelTitle: "Hour",
          labelHour: "Hour", labelMinute: "Minute" } );


    $("#maxTimeICS").AnyTime_picker(
        { format: "%H:%i", labelTitle: "Hour",
          labelHour: "Hour", labelMinute: "Minute" } );

      $("#minTimeILS").AnyTime_picker(
        { format: "%H:%i", labelTitle: "Hour",
          labelHour: "Hour", labelMinute: "Minute" } );


    $("#maxTimeILS").AnyTime_picker(
        { format: "%H:%i", labelTitle: "Hour",
          labelHour: "Hour", labelMinute: "Minute" } );


      $("#minTimeDPAS").AnyTime_picker(
        { format: "%H:%i", labelTitle: "Hour",
          labelHour: "Hour", labelMinute: "Minute" } );


    $("#maxTimeDPAS").AnyTime_picker(
        { format: "%H:%i", labelTitle: "Hour",
          labelHour: "Hour", labelMinute: "Minute" } );

    $("#minTimeCS").AnyTime_picker(
        { format: "%H:%i", labelTitle: "Hour",
          labelHour: "Hour", labelMinute: "Minute" } );


    $("#maxTimeCS").AnyTime_picker(
        { format: "%H:%i", labelTitle: "Hour",
          labelHour: "Hour", labelMinute: "Minute" } );


  });
    </script>



  </head>
  <body>
    <div class="navigation">
      <g:render template="/indexbar" />
    </div>
    <div id="content">
      <div id="header">
        <h1>Helio v0.3 (beta)</h1>
        <h2>Third release of the Helio Services.</h2>
      </div>
      <!--div class="demo">
        <button id="login">Login</button>
        <input type="text" name="loginid" value=" " id="loginid" />

      </div-->




      <p class="introduction">
         <span class="blue">Welcome to the FrontEnd for the Helio Services, Currently we are developing our third milestone and we welcome you to try some of the workflows and services we have avaliable at the moment
</span>
        <br>
        <br>

      </p>


      <div id="mainbar">






        <h1>Select a Service:</h1>
        <div class="demo">
        </div>
        <div id="accordion">
          <h3><a href="#">Heliophysics Event Catalogue</a></h3>
          <div>
            <p>
              The HEC provides access to event data from all domains. The contents of the catalogues consists on existing event
              lists from various sources This service is an extension of the EGSO Solar Event Catalogue (SEC) with some additional sources.

            </p>
            <g:render template="/webService/HEC" />
          </div>
          <h3><a href="#">Instrument Location Service</a></h3>
          <div>
            <p>
              The ILS determines whether instruments were located where they could have observed
              an effect during the specified time interval. That is, it knows where the observatories are
              with respect to the Sun and planetary bodies.

            </p>
            <g:render template="/webService/ILS" />
          </div>
          <h3><a href="#">Instrument Capabilities Service</a></h3>
          <div>
            <p>

              The ICS identifies which instruments have made observations of a certain desired type.
              This is an extension of the EGSO Static Search Registry (SSR)
            </p>

            <g:render template="/webService/ICS" />
          </div>
          <h3><a href="#">Data Provider Access Service</a></h3>
          <div>
            <p>
              The DPAS isolates the main HELIO Search Engine, and its Taverna Workflow engine,
              from the foibles of the data providers, allowing the system to use a standard interface for all
              data providers. If the DPAS is used as a stand-alone service, it presents users with a very
              capable standardized way of access much of the data they require.
            </p>
            <g:render template="/webService/DPAS" />
          </div>
          <h3><a href="#">Context Service</a></h3>
          <div>
            <p>
              The Context Service provides context information that can be used to assist the user make a
              selection based on search metadata, it will returns image & plots; over-plot location and timing of
              events etc. The service is a compound in nature consisting of a synoptic archive with
              processing capabilities; it needs to hold a representative selection of images, etc., and time
              series of observed and derived parameters and have good access to other sources of this
              type of data.
            </p>



            <g:render template="/webService/CS" />
          </div>
        </div>




        <br>
        <br>


        <h1>Select a Workflow:</h1>



        <div class="demo">

          <div id="tabs">
            <ul>
              <li><a href="#tabs-1">Pre-made Workflows</a></li>
              <li><a href="#tabs-2">User-created Workflows</a></li>
            </ul>
            <div id="tabs-1">
              <ul id="sortable1" class="connectedSortable ui-helper-reset">
                
                <li class="ui-widget-content"><g:link controller="singleService" >Iterative Workflow</g:link></li>
              <li class="ui-widget-content"><g:link controller="states">Science Test Workflow</g:link></li>
                <li class="ui-widget-content">Instrument Workflow</li>





              </ul>
            </div>
            <div id="tabs-2">
              <ul id="sortable2" class="connectedSortable ui-helper-reset">

                <li class="ui-state-default">User Created 1</li>
              </ul>
            </div>
          </div>

        </div><!-- End demoWorkflows -->



      </div>
    </div><!-- End Content -->






  </body>
</html>