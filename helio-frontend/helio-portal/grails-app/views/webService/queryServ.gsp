<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->
<%@ page import="ch.i4ds.helio.frontend.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'demo_table.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'demo_page.css')}" />
    <g:javascript library="jquery.dataTables" />
    <title>Query Results</title>
     <script type="text/javascript">
	$(function() {
              var dates = $('#minDateHEC, #maxDateHEC').datepicker({
                defaultDate: "+1w",
                changeMonth: true,
                changeYear: true,
                dateFormat: 'yy-mm-dd',
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
                dateFormat: 'yy-mm-dd',
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
                dateFormat: 'yy-mm-dd',
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
                dateFormat: 'yy-mm-dd',
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
                dateFormat: 'yy-mm-dd',
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

    



</script>
  </head>
  <body>
    <div class="navigation">
      <g:render template="/webService/indexbar" />
    </div>

     <g:if test="${flash.message}">
            <div id="message">${flash.message}</div>
            </g:if>
    <div id="content">
     <div class="accord">
	<h3 id="HEC"><a href="#">HEC:</a></h3>
	<div >
		<p>

		</p>
                <g:render template="/webService/HEC" />
	</div>
	<h3 id="ILS"><a href="#">ILS:</a></h3>
	<div class="selected">
		<p>

		</p>
                <g:render template="/webService/ILS" />
	</div>
	<h3><a href="#">ICS:</a></h3>
	<div>
		<p>

		</p>

                <g:render template="/webService/ICS" />
	</div>
	<h3><a href="#">DPAS:</a></h3>
	<div>
		<p>

		</p>
                <g:render template="/webService/DPAS" />
	</div>
         <h3><a href="#">CS:</a></h3>
                <div>

                  <g:render template="/webService/CS" />
                </div>
</div>

      <br>
            

<table cellpadding="0" cellspacing="0" border="0" class="display" id="example">

	<thead>
		<tr>
			<g:each in="${result?.getHeaders()}" status="i" var="testInstance">
                            
                            <th>${testInstance}</th>
                    </g:each>
			

		</tr>
	</thead>
	<tbody>
            <g:each in="${result?.getStack()}" status="i" var="testInstance">
                        <tr class="${(i % 2) == 0 ? 'gradeX' : 'gradeA'}">
                        <g:each in="${testInstance}" status="j" var="row">
                            <td>${row}</td>
                            </g:each>
                        </tr>
                    </g:each>
		
		
	</tbody>
</table>


    
            <div>
    <br>
    <h1>VO Table test</h1>
    <g:form controller="webService">

<g:actionSubmit action="downloadVOTable" value="Download VO-Table" name="download"/>
</g:form>
    <br>
            </div>
            
    </div>

</body>
</html>
