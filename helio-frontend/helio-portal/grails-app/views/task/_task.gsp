<div id="task" class="task">
  <div class="task_header_area viewerHeader">
    <h1>${taskDescriptor.label}</h1>
  </div>
  <div id="task_input_area">
    <div class="header queryHeader viewerHeader">
      <h1>Parameters</h1>
    </div>
    <div class="task_input_params task_body">
      <table width="100%" cellpadding="0" cellspacing="0">
        <col width="*" />
        <col width="250"/>
        <tbody>
          <g:set var="step" value="${1}"/>
          <%-- Time Selection Area --%>
          <g:if test="${taskDescriptor.inputParams?.timeRanges}">
            <g:render template="/inputParams/timeRangeSummary" model="[step:step, mode:'first-starttime-only']" />
            <g:set var="step" value="${step+1}"/>
          </g:if>
          <%-- Param Selection Area --%>
          <g:if test="${taskDescriptor.inputParams?.paramSet}">
            <g:render template="/inputParams/paramSetSummary" model="[step:step]" />
            <g:set var="step" value="${step+1}"/>
          </g:if>
          <%-- Execute Query area --%>
          <g:render template="/inputParams/performQuery" model="[step:step]" />
          <g:set var="step" value="${step+1}"/>
        </tbody>
      </table>
    </div>
  </div>
  <div id="task_result_area">
  </div>
</div>