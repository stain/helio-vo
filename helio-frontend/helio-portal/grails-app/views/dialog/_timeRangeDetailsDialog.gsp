<%--Dialog to show the details of a given time range
Expected parameters:
 * String startTime overall start time.
 * String endTime overall end time.
 * List<Map> plots a list containing the potential plots. The map keys are: task, startTime, endTime
 * List<Map> links a list of external links to show. The map keys are: title, link
 --%><%@page import="eu.heliovo.shared.util.DateUtil"%>
<div class="input-dialog" id="timeRangeDetailsDialog" style="display:none">
  <table class="dialog_table" >
    <tr>
      <td id="details">
        <b>Start Date (added +/- 6 hours)</b> ${startTime}
        <b>End Date</b> ${endTime}
      </td>
    </tr>
    <tr style="margin-top:30px">
      <td><h5>Plots</h5>
        <ul>
          <g:each var="plot" in="${plots}">
            <li>
              <g:remoteLink controller="plot" action="syncplot" params="[taskName:plot.taskName, startTime:plot.startTime, endTime:plot.endTime]" update="[success:'target_' + plot.taskName,failure:'target_' + plot.taskName]" onLoading="jQuery('#target_${plot.taskName}').append('Loading ...');" onLoaded="jQuery('#target_${plot.taskName}').empty();" >${plot.task.label}</g:remoteLink>
              <div id="target_${plot.taskName}"></div>          
            </li>
          </g:each>
        </ul>
      </td>
    </tr>
    <tr>
      <td id="links">
        <h5>Links to external sites</h5>
        <ul>
          <g:each var="link" in="${links}">
             <li><a target="_blank" href="${link.link}">${link.title}</a></li>
          </g:each>
        </ul>
      </td>
    </tr>
  </table>
</div>