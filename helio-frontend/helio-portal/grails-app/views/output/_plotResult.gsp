<%-- Template to display a collection of plots.
Expected parameters:
 * List<RemotePlotResult> plotResults : a list of plot results to be displayed. 
   The plots must have three properties: url, id, label.
 * Map taskDescriptor : the task descriptor  
 --%><div id="task_output_plot_area">
  
  <div class="header plotResultHeader viewerHeader">
    <h1>
      Plot${plotResults.size() == 1 ? '' : 's'} for task '${taskDescriptor.label}'
    </h1>
  </div>
  <div class="task_output_params task_body">
    <g:each var="plot" in="${plotResults}">
      <div class="plot_floating_box">
        <a href="${plot.value.url}" target="_blank">
          <img id="plot_${plot.id}" class="task_output_plot" src="${plot.value.url}" title="${plot.label}" height="190" />
        </a><br/>
        ${plot.label}
      </div>
    </g:each>
  </div>
</div>