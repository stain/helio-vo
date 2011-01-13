
<div id ="questions" style="padding-bottom:30px">
  <h1>4. Examine Observations</h1>
  <g:form controller="states"  method="post" name="icsdata" >
    
    <g:actionSubmit action="workflow2"  value="Previous Step" />
    
    <g:actionSubmit action="saveFits" value="Download Fits" />



  </g:form>
<g:each in="${state.r3?.getTables()}"  status="x" var="tables">
  <table cellpadding="0" cellspacing="0" border="0"  class="display">

    

    <thead>
      <tr>
    <g:each in="${tables?.getHeaders()}" status="i" var="testInstance">
      <th>${testInstance}</th>
    </g:each>

    </tr>
    </thead>
    <tbody id="example2">
    <g:each in="${tables?.getData()}" status="i" var="testInstance">
      <tr class="${(i % 2) == 0 ? 'gradeB' : 'gradeC'}">
      <g:each in="${testInstance}" status="j" var="row">
         <g:if test="${j==1}">
           <td><a href="${row}">${row}</a></td>
         </g:if>
        <g:else>
           <td>${row}</td>
        </g:else>
      </g:each>
      </tr>
    </g:each>
 </tbody>
  </table>
</g:each>


</div>
<div style="padding-top:20px;"></div>