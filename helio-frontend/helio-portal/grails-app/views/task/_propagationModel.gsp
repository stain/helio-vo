<div id="task" class="task">
  <div class="task_header_area viewerHeader">
    <table style="height:30px;width: 100%;">
      <tr>
        <td>
        </td>
        <td>
          <h1>${title}</h1>
        </td>
        <td>
        </td>
      </tr>
    </table>
  </div>
  <div id="task_input_area">
    <div class="header queryHeader viewerHeader">
      <h1>Parameters</h1>
    </div>
    
    <div class="task_input_params task_body">
      <g:form name="propagation_model_form" controller="procesing" action="propagationModel" method="post" > 
        <table width="100%" cellpadding="0" cellspacing="0">
          <col width="*" />
          <col width="250"/>
          <tbody>
            <%-- Time Selection Area --%>
            <g:render template="/inputParams/timeRangeSummary" model="[step:1, mode:'first-startdate-only']" />
            <%-- Param Selection Area --%>
            <g:render template="/inputParams/paramSetSummary" model="[step:2]" />
            <%-- Execute Query area --%>
            <g:render template="/inputParams/performQuery" model="[step:3]" />
          </tbody>
        </table>
      </g:form>
    </div>
  </div>
  <div id="task_result_area">
  </div>
</div>