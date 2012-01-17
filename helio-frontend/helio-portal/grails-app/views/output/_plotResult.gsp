<%-- Template to display a collection of plots.
Expected parameters:
 * List<RemotePlotResult> plotResults : a list of plot results to be displayed. 
   The plots must have three properties: url, id, label.
 --%><div id="task_output_plot_area">
  <div class="header queryHeader viewerHeader">
    <h1>
      Plot${plotResults.size() == 1 ? '' : 's'}
    </h1>
  </div>
  <div class="task_output_params">
    <table width="100%" cellpadding="0" cellspacing="0">
      <tbody>
        <g:each var="y" in="0..plotResults.size() / 3">
          <tr>
            <g:each var="x" in="0..plotResults.size() % 3">
              <g:set var="pos" value="3*y + x" />
              <g:if test="pos < plotResults.size()">
                <g:set var="plot" value="plotResults[pos]"></g:set>
                <td><img id="plot_${plot.id}" src="${plot.url}" title="${plot.label}" height="120" /></td>
              </g:if>
              <g:else>
                <td></td>
              </g:else>
            </g:each>
          </tr>
        </g:each>
      </tbody>
    </table>
  </div>

</div>
