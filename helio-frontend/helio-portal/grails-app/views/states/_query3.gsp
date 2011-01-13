
<div id ="questions" style="padding-bottom:30px">
  <h1>3. Select Instruments</h1>
  <g:form controller="states"  method="post" name="icsdata" >
    <g:hiddenField  name="data" />
    <g:actionSubmit action="workflow1"  value="Previous Step" />
    <g:actionSubmit action="workflow3" onClick="icsSubmit()" value="Next Step" />
    <g:actionSubmit action="downloadVOTable" value="Download VO-Table" />


  </g:form>
    <table cellpadding="0" cellspacing="0" border="0" class="display" id="icsTable">
      <thead>
        <tr>
         <g:each in="${state.r2.getHeaders()}" var="headers">
      <th>${headers}</th>
      </g:each>




        </tr>
      </thead>
      <tbody>

      <g:each in="${state.r2.getStack()}"  var="rows">
        <tr class="gradeC">
        <g:each in="${rows}" var="cells">
          <td>${cells}</td>
        </g:each>
        </tr>
      </g:each>

      </tbody>
      <tfoot>
        <tr>
           <g:each in="${state.r2.getHeaders()}" var="headers">
      <th>${headers}</th>
      </g:each>

        </tr>
      </tfoot>
    </table>
</div>
<div style="padding-top:20px;">






</div>
