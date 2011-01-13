
<div id ="questions" style="padding-bottom:30px">

  <h1>2. Select Goes Solar Events</h1>
  


<g:form controller="states"  method="post" name="hecdata" >
    <g:hiddenField  name="data" />
    <g:actionSubmit action="workflow0"  value="Previous Step" />
    <g:actionSubmit action="workflow2" onClick="hecSubmit()" value="Next Step" />
    <g:actionSubmit action="downloadVOTable" value="Download VO-Table" name="query1" />
    
</g:form>


    <table cellpadding="0" cellspacing="0" border="0" class="display" id="hecTable">
      <thead>
        <tr>
      <g:each in="${state.r1.getHeaders()}" var="headers">
      <th>${headers}</th>
      </g:each>
         
        </tr>
      </thead>
      <tbody>

      <g:each in="${state.r1.getStack()}" var="rows">
        <tr class="gradeC">
        <g:each in="${rows}" status="j" var="cells">
          <td>${cells}</td>
        </g:each>
        </tr>
      </g:each>

      </tbody>
      <tfoot>
        <tr>
           <g:each in="${state.r1.getHeaders()}" var="headers">
      <th>${headers}</th>
      </g:each>
        </tr>
      </tfoot>
    </table>

</div>

