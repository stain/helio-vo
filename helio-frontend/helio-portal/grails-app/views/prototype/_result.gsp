
<div id="staticForms">
  <div id="formHeader">
    <img style ="float:left;position:relative;top:10px;left:10px" height="30px" src="${resource(dir:'images/icons/toolbar',file:'result.png')}"  />
    <h1 style ="float:left;position:relative;left:15px" > Result </h1>
  </div>
  
  <div style="clear: both; padding: 20px 5pt 20pt 5px;">
   <g:each in="${result?.getTables()}"  status="x" var="tables">
<!--Table NAME: "${tables.getName()}"-->
     <table cellpadding="0" cellspacing="0" border="0" id='example' class="resultTable">

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
      <tr class="${(i % 2) == 0 ? 'gradeB' : 'gradeZ'}">
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
