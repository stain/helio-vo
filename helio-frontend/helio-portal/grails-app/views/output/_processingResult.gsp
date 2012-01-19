<%-- Show the result of a processing service. 
Expected model params:
 * optional Map votableModel: a model of the votable as created by VoTableService.createVoTableModel() 
 * optional List plotResults: a list of Maps. Each map contains three values: 
   * String id: id of the plot
   * String label: label of the plot
   * String value: the plot packed as RemotePlotResult
--%><div id="task_output_area">
  <g:if test="${plotResults}">
    <g:render template="/output/plotResult" model="[plotResults:plotResults]" />
  </g:if>
  <g:if test="${votableModel}">
    <g:render template="/output/votableResult" model="[result:votableModel]" />
  </g:if>
  <g:if test="${userLogs && userLogs.size() > 0}">
    <g:render template="/output/userLogs" model="[userLogs:userLogs]" />
  </g:if>
</div>