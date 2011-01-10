$(function() {
              var dates = $('#minDateHEC, #maxDateHEC').datepicker({
                defaultDate: "+1w",
                changeMonth: true,
                changeYear: true,
                numberOfMonths: 3,
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
                changeYear: true,
                numberOfMonths: 3,
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
                changeYear: true,
                numberOfMonths: 3,
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
                changeYear: true,
                numberOfMonths: 3,
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
                changeYear: true,
                numberOfMonths: 3,
                onSelect: function(selectedDate) {
                  var option = this.id == "minDateCS" ? "minDateCS" : "maxDateCS";
                  var instance = $(this).data("datepicker");
                  var date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
                  dates.not(this).datepicker("option", option, date);
		}
		});





        $(function() {
            $( ".accord" ).accordion({ active: ${queryIndex} });
	});


$('#example').dataTable({
		"bJQueryUI": true,
		"sPaginationType": "full_numbers",
                 "sScrollX": "100%",
          "sScrollXInner": "110%",
          "bScrollCollapse": true
	});

  $("#minTimeHEC").AnyTime_picker(
      { format: "%H:%i", labelTitle: "Hour",
        labelHour: "Hour", labelMinute: "Minute" } );


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


  $("#maxTimeDPAS").AnyTime_picker({ format: "%H:%i", labelTitle: "Hour",labelHour: "Hour", labelMinute: "Minute" } );


$("#minTimeCS").AnyTime_picker(
        { format: "%H:%i", labelTitle: "Hour",
          labelHour: "Hour", labelMinute: "Minute" } );


    $("#maxTimeCS").AnyTime_picker(
        { format: "%H:%i", labelTitle: "Hour",
          labelHour: "Hour", labelMinute: "Minute" } );





});



