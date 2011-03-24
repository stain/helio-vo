<div id="tables">
   
<div style="border:1px solid blue;margin-top:40px;" id="staticForms">
  
  
  <div id="formHeader">
    
    <div style="padding:4px;background-color:white;float:left;position:relative;top:-30px;left:15px;">
      <img src="${resource(dir:'images/icons/toolbar',file:'result.png')}"  />
    </div>
    <span style="font-size:1.5em;position:relative;top:-25px;left:20px;">Result Display</span>
  </div>
  <!--div style="border-bottom:1px solid blue;margin:20px;padding-left:20px" id>
    <b>Previous Query:</b>
    <ol >
     <g:each in="${responseObject?.previousQuery}" var="queries">
       <li >${queries}</li>
     </g:each>
    </ol>
  </div-->

  <div id="voTables" style="clear: both; padding: 20px 5pt 20pt 5px;">


<g:set var="result" value="${responseObject?.result}" />
<g:set var="result" scope="session" value="${responseObject?.result}" />
    <g:form target="_blank" controller="prototype"><g:actionSubmit style="padding:3px;float:left" action="downloadVOTable" value="Save as VOTable" name="download"/></g:form>
    <div id="resultSelectionCounter" class="custom-button" style="margin-right:10px;float:right;">0</div>
    <div id="resultSelectionSave" class="custom-button" style="margin-right:10px;float:right;">Save Selection</div>

    <div style="clear:both;width:100%"></div>

<g:if test="${result?.queryInfo.contains('QUERY_ERROR')}" >
  "An error has occured with the service and has been logged, perhaps revise your query or download the response VO-Table contaning the error state"
</g:if>
 <g:each in="${result?.getTables()}"  status="x" var="tables">
   <span style="clear:both"> NAME: "${tables.getName()}"</span>
  <table cellpadding="0" cellspacing="0" border="0" class='resultTable' id="resultTable${x}">

    <thead>
      <tr>
    <g:each in="${tables?.getHeaders()}" status="i" var="testInstance">

      <g:if test="${testInstance == 'URL'}"><g:set var="urls" value="${i}" /></g:if>
      <th>${testInstance} </th>

    </g:each>

    </tr>
    </thead>
    <tbody>
    <g:each in="${tables?.getData()}" status="i" var="testInstance">
      <tr class="${(i % 2) == 0 ? 'gradeB' : 'gradeB'}">
      <g:each in="${testInstance}" status="j" var="row">
        <g:if test="${urls == j}"><td><a href="${row}">${row.substring(row.lastIndexOf('/')+1,row.length())}</a></td></g:if>
        <g:else><td>${row}</td></g:else>
      </g:each>
      </tr>
    </g:each>


    </tbody>
  </table>
</g:each>

</div>
   </div>


</div>
<g:hiddenField name="notField" id="totalSize" value="${responseObject?.result.getTotalSize()}" />
<div id="previousQuery" style="display:none">
  <g:set var="previousQuery" value="${responseObject?.previousQuery}" />
  ${previousQuery}
</div>