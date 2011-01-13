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
    
    <link rel="stylesheet" href="${resource(dir:'css',file:'demo_table.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'demo_page.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'singleService.css')}" />
  <g:javascript src="singleService/index.js"/>
  <g:javascript library="jquery.dataTables" />

  <title>Query Results</title>
  <script type="text/javascript">
	     

   var selected_counter = 1;


     $(function() {
       $( "#tabs" ).tabs();
        $('.display').dataTable({
          "sScrollX": "100%",
          "sScrollXInner": "110%",
          "bScrollCollapse": true,
"oLanguage": {
			"sSearch": "Search all columns:"
		},

             "bJQueryUI": true,
             "sPaginationType": "full_numbers"
     });
        $('#example2 tr').click( function() {
          $('#tableCurrentSelection').css('display','block');
                            
                          if ( $(this).hasClass('row_selected') ){
                                  $(this).removeClass('row_selected');
                                  var toDelete = $(this).attr('name');
                                  //console.log("to del" +toDelete);
                                  $("#"+toDelete).remove();
                                  $("#table"+toDelete).remove();
                                  $("#list"+toDelete).remove();
                                  refreshHistory();
                          }else{
                            
                            var valueRow = $(this).clone();
                            $(this).attr('name',"selected_"+selected_counter);
                            valueRow.attr("id","selected_"+selected_counter);
                            //valueRow.prependTo("<table>");
                            //valueRow.appendTo("</table>");
                            
                            //valueRow.append("</tr>start date</li><li><input type='text' name='startDate' value='' min'/> end date <input name='endDate' type='text' value=''/></li>");
                            //$("#tableCurrentSelection table").append(valueRow);
                            var tableH = $("<table>");
                            tableH.attr("id","tableselected_"+selected_counter);
                            tableH.attr("class","tableCurrentSelectionClass");
                            var input = $("<li>");
                            input.append("start date<input type='text' name='startDate' value='' min'/> end date <input name='endDate' type='text' value=''/>  instrument <input name='instrument' type='text' value=''/>");
                            input.attr("id","listselected_"+selected_counter);
                            input.attr("class","tableCurrentSelectionClass");
                            
                            $("#tableCurrentSelection ul").append(tableH);
                            $("#tableCurrentSelection ul").append(input);
                            $("#tableselected_"+selected_counter++).append(valueRow);
                            
                            //$("#tableCurrentSelection table").

                             
                             $(this).addClass('row_selected');

                             $('.tableCurrentSelectionClass td').unbind();

                             $('.tableCurrentSelectionClass td').click( function() {
                               //console.log("im clicking ");
                               var id = $(this).parent().attr('id');
                               //console.log(id);
                               var text = $(this).text();
                               var parent = $("#list"+id);
                               var endDateInput = parent.find('input[name|="endDate"]');
                               var startDateInput = parent.find('input[name|="startDate"]');
                               var instrumentInput = parent.find('input[name|="instrument"]');

                               
                          if ( $(this).hasClass('box_selected') ){
                                   
                                  $(this).attr('id','');
                                  $(this).attr('class','');
                                  if(startDateInput.val()== $(this).text())startDateInput.val("");
                                  if(endDateInput.val()== $(this).text())endDateInput.val("");
                                  if(instrumentInput.val()== $(this).text())instrumentInput.val("");
                                  

                          }else{
                           var temp=false;
                             $("#dpasTableDiv input").each(function(){
                             
                             if($(this).val()==text){temp= true}
                           });
                           
                           
                           
                           if(temp){
                              $(this).attr('id','');
                                 $(this).attr('class','');
                                  $(this).attr('class','box_selected');
                                  startDateInput.val("");
                                  endDateInput.val("");
                              instrumentInput.val($(this).text());
                           }
                          else if(instrumentInput.val()=="" &&startDateInput.val()== ""){
                                  $(this).attr('id','');
                                 $(this).attr('class','');
                                  $(this).attr('class','box_selected');

                              startDateInput.val($(this).text());
                            }else if(instrumentInput.val()=="" && endDateInput.val()== ""){
                                       $(this).attr('id','');
                                 $(this).attr('class','');
                                  $(this).attr('class','box_selected');

                              endDateInput.val($(this).text());

                        }
                        refreshHistory();

                          }
                              // console.log($(this).text());
                             });
                              //var aTrs = valueRow.children();


/*

                  for ( var i=0 ; i<aTrs.length ; i++ )
                  {
                          //console.log($(aTrs[i]).text());
                  }*/
                  } });





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
  <form controller="singleService">
<div class="demo">
<g:render template="pqlBlock" />
<div id="tabs">
	<ul>
		<li><a href="#tabs-1">Dates</a></li>
		<li><a href="#tabs-2">Services</a></li>
		<li><a href="#tabs-3">Tables</a></li>
                <li><a href="#tabs-4">Columns</a></li>
	</ul>
	<div id="tabs-1">
		<g:render template="datesBlock" />
	</div>
	<div id="tabs-2">
		<g:render template="servicesBlock" />
	</div>
   <div id="tabs-3">
		<g:render template="tablesBlock" />

	</div>
	<div id="tabs-4">
		<g:render template="columnsBlock" />
		
	</div>
</div>

</div><!-- End demo -->

   <div class="block" id="tableCurrentSelection" style="display:none">
      <h4>Result Selection</h4>
      <ul>

      </ul>
    </div>
  

  
    <g:each in="${result?.getTables()}"  status="x" var="tables">
<!--Table NAME: "${tables.getName()}"-->
  <table cellpadding="0" cellspacing="0" border="0" class='display'>

    <thead>
      <tr>
    <g:each in="${tables?.getHeaders()}" status="i" var="testInstance">
       
      <g:if test="${testInstance == 'URL'}"><g:set var="urls" value="${i}" /></g:if>
      <th>${testInstance} </th>
      
    </g:each>

    </tr>
    </thead>
    <tbody id="example2">
    <g:each in="${tables?.getData()}" status="i" var="testInstance">
      <tr class="${(i % 2) == 0 ? 'gradeB' : 'gradeC'}">
      <g:each in="${testInstance}" status="j" var="row">
        <g:if test="${urls == j}"><td><a href="${row}">${row.substring(row.lastIndexOf('/')+1,row.length())}</a></td></g:if>
        <g:else><td>${row}</td></g:else>
      </g:each>
      </tr>
    </g:each>


    </tbody>
  </table>
</g:each>



   

<g:render template="blocks" />

    
  </form>
  </div>
  
    
  
  





<!--div>
  <br>
  <h1>VO Table</h1>
  <textarea rows="2" cols="20">
${result?.getStringTable()}
  </textarea>

  <br>
</div-->

</body>
</html>
