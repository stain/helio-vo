<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->
<%@ page import="ch.i4ds.helio.frontend.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>

    <link rel="stylesheet" href="${resource(dir:'css',file:'demo_page.css')}" />
    <meta name="layout" content="main3" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'demo_table.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'useCase.css')}" />
  <g:javascript library="jquery" />
  <g:javascript library="jquery.dataTables" />
 

  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Helio: Science Test Workflow</title>
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
   $("#minTimeHEC").AnyTime_picker(
        { format: "%H:%i", labelTitle: "Hour",
          labelHour: "Hour", labelMinute: "Minute" } );


    $("#maxTimeHEC").AnyTime_picker(
        { format: "%H:%i", labelTitle: "Hour",
          labelHour: "Hour", labelMinute: "Minute" } );

  });
    </script>
</head>
<body>
  <div class="navigation">
    <g:render template="/webService/indexbar" />
  </div>


  <div id ="contenido">
 
    <div id="questions">
      <h1>Science Test Case</h1>

      <table style="width:100%;">
        <tr>
          <td class ="${state?.getQClass(1)}">
             <g:link action="workflow0"><h2>Step 1. Select a Time Range</h2></g:link>
          </td>
          <td class ="${state?.getQClass(2)}">
             <h2>Step 2. Select Goes Solar Events</h2>
          </td>
          <td class ="${state?.getQClass(3)}">
              <h2>Step 3. Select Instruments</h2>
          </td>
          <td class ="${state?.getQClass(4)}">
             <h2>Step 4. Examine Observations</h2>
          </td>
        </tr>
      </table>
<!--
      <div class ="${state?.getQClass(1)}">
       <g:link action="workflow0"><h2>Step 1: Select a Time Range</h2></g:link>
       
      </div>
      <div class ="${state?.getQClass(2)}">
       <h2>Step 2: Select Goes Solar Events</h2>
      </div>
      <div class ="${state?.getQClass(3)}">
       <h2>Step 3: Select Instruments</h2>
      </div>
      <div class ="${state?.getQClass(4)}">
       <h2>Step 4: Examine Observations</h2>
      </div>
-->
</div>
        <g:if test="${flash.message}"><div style="clear:both;" id="message1">${flash.message}</div></g:if>
   

      <g:if test="${state?.getStep()==1}"><g:render template="query1"/></g:if>
  </div>
</body>
</html>
