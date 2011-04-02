<div id="tables">
<div class="module">
  <div class="header queryHeader viewerHeader">
    <h1>Result</h1>
  </div>
  <div class="content">


   
<div >
  
  
  
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
    <g:form target="_blank" controller="prototype"><g:actionSubmit class="custom_button" style="padding:3px;float:left" action="downloadVOTable" value="Save as VOTable" name="download"/></g:form>
    <div id="resultSelectionCounter" class="custom_button" style="margin-right:10px;float:right;">0</div>
    <div id="resultSelectionSave" class="custom_button" style="margin-right:10px;float:right;">Save Selection</div>

    <div style="clear:both;width:100%"></div>

<g:if test="${result?.queryInfo.contains('QUERY_ERROR')}" >
  "An error has occurred on the server side. Please download the response VOTable to see the reported cause and revise your query."
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
</div>


</div>
<g:hiddenField name="notField" id="totalSize" value="${responseObject?.result.getTotalSize()}" />
<div id="previousQuery" style="display:none">
  <g:set var="previousQuery" value="${responseObject?.previousQuery}" />
  ${previousQuery}
</div>