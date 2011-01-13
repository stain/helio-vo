<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->
<%@ page import="ch.i4ds.helio.frontend.model.*" %>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>

    <link rel="stylesheet" href="${resource(dir:'css',file:'demo_page.css')}" />
    <meta name="layout" content="main2" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'demo_table.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'useCase.css')}" />
  <g:javascript library="jquery" />
  <g:javascript library="jquery.dataTables" />
  <script type="text/javascript" charset="utf-8">



   
          $(document).ready(function() {
               var oTable = $('#hecTable').dataTable({
		"bPaginate": true,

                     "sScrollX": "100%",
                    "iDisplayLength": 250,
                  "bScrollCollapse": true
		 } );
                  /* Add a click handler to the rows - this could be used as a callback */

      


                  $('#hecTable tr').click( function() {
                          if ( $(this).hasClass('row_selected') )
                                  $(this).removeClass('row_selected');
                          else
                                  $(this).addClass('row_selected');
                  } );
                


                  /* Init the table */
                  oTable = $('#hecTable').dataTable( );
                
          } );

          /*
           * I don't actually use this here, but it is provided as it might be useful and demonstrates
           * getting the TR nodes from DataTables
           */
          function hecSubmit()
          {

             var oTable = $('#hecTable').dataTable( );

                  var aReturn = new Array();
                  var aTrs = oTable.fnGetNodes();

                  for ( var i=0 ; i<aTrs.length ; i++ )
                  {
                          if ( $(aTrs[i]).hasClass('row_selected') )
                          {
                                  aReturn.push(i);

                          }
                  }
                     document.hecdata.data.value = aReturn;
                     document.data.submit();
                  //return aReturn;
          }

     
          

  </script>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Helio: Science Test Workflow</title>
</head>
<body>
  <div class="navigation">
    <g:render template="/webService/indexbar" />
  </div>


  <div id ="contenido">
  <!--
    <table>
      <tr>
        <td><a>1. Query for Solar Events</a></td>
        <td><a>2. Query for Instruments</a></td>
        <td><a>3. Query for Observations</a></td>
      </tr>

    </table>
-->

    <div id="questions">
      <h1>Science Test Case</h1>



     <table style="width:100%;">
        <tr>
          <td class ="${state?.getQClass(1)}">
             <g:link action="workflow0"><h2>Step 1. Select a Time Range</h2></g:link>
             
          </td>
          <td class ="${state?.getQClass(2)}">
             <g:link action="workflow1"><h2>Step 2. Select Goes Solar Events</h2></g:link>
          </td>
          <td class ="${state?.getQClass(3)}">
             <h2>Step 3. Select Instruments</h2>
          </td>
          <td class ="${state?.getQClass(4)}">
             <h2>Step 4. Examine Observations</h2>
          </td>
        </tr>
              <tr>
          <td class ="${state?.getQClass(1)}">
              ${state.q1}
            
          </td>
         
        </tr>
      </table>

        </div>


        <g:if test="${flash.message}"><div style="clear:both;" id="message1">${flash.message}</div></g:if>



      <g:if test="${state?.isQ1()}"><g:render template="query2"/></g:if>

      





  </div>










</body>
</html>
