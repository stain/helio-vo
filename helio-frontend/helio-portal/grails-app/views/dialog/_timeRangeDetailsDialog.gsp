<%--Dialog to show the details of a given time range
Expected parameters:
 * Map timeRangeParam param containing the start and end time.
 * Map timeRangeDescriptor description of the time range.
 * String endTime overall end time.
 * List<Map> plots a list containing the potential plots. The map keys are: task, startTime, endTime
 * List<Map> links a list of external links to show. The map keys are: title, link
 --%><%@page import="eu.heliovo.shared.util.DateUtil"%>
<div class="input-dialog" id="timeRangeDetailsDialog" style="display:none; width:100%; height:100%">
  <table class="dialog_table" style="width:100%">
    <tr style="height: 20px;" valign="top">
      <td id="details">
        <form action="#"> <%--a form for reading the params --%>
          <table style="width:100%; height: 20px; border: none;">
            <tr>
              <td style="width:100px">
                <b>Date range:</b>
              </td>
              <td align="left" valign="top" style="width:250px">
                <div style="height:22px; width: 11px; line-height: 1px; display:inline-block; float:left;">
                  <img id="inspect_time_range_start_inc" src="${resource(dir:'images/helio',file:'plus.png')}" style="float:top" 
                    title="Adjust time range by adding 6 hours" width="11" height="11"/>
                  <img id="inspect_time_range_start_dec" src="${resource(dir:'images/helio',file:'minus.png')}" style="float:bottom" 
                    title="Adjust time range by subtracting 6 hours" width="11" height="11"/>
                </div><%--
                --%><input style="width:150px" type="text" tabindex="-1" id="inspectStartTime" name="startTime" value="${g.formatDate(date:timeRangeParam.startTime, format:'yyyy-MM-dd\'T\'HH:mm:ss')}"/>
              </td>
              <td align="left" valign="top" tyle="width:250px">
                <div style="height:22px; width: 11px; line-height: 1px; display:inline-block; float:left;">
                  <img id="inspect_time_range_end_inc" src="${resource(dir:'images/helio',file:'plus.png')}" style="float:top" 
                    title="Adjust time range by adding 6 hours" width="11" height="11"/>
                  <img id="inspect_time_range_end_dec" src="${resource(dir:'images/helio',file:'minus.png')}" style="float:bottom" 
                    title="Adjust time range by subtracting 6 hours" width="11" height="11"/>
                </div><%--
                --%><input style="width:150px" type="text" tabindex="-1" id="inspectEndTime" name="endTime" value="${g.formatDate(date:timeRangeParam.endTime, format:'yyyy-MM-dd\'T\'HH:mm:ss')}"/>
              </td>
              <td width="*"></td>
            </tr>
          </table>
        </form>
      </td>
    </tr>
    <tr valign="top">
      <td>
        <div id="tabs_time_range_details">
          <ul>
            <g:each var="plot" in="${plots}">
              <li><a href="#tab_${plot.plotName}">${plot.label}</a></li>
            </g:each>
            <li><a href="#tab_external_pages">External links</a></li>
          </ul>
          <%-- plot tabs --%>
          <g:javascript>
              /**
               * plot the error
               */
              helio.plotError = function(targetDiv, responseText) {
                  var json;
                  try {
                    json = $.parseJSON(responseText);
                  } catch (err) {
                    json = responseText;
                  }
                  
                  targetDiv.empty();
                  var div = $('<div><h3>An error occurred while processing the request. Please verify the time range.</h3></div>');
                  targetDiv.append(div);
                  if (json.status) {
                    div.append(json.status);
                    
                    if (json.userLogs) {
                      div.append("<h3>UserLogs:</h3>")
                      var table = $('<table border="1" cellpadding="0" cellspacing="0"></table>');
                      div.append(table);
                      for(var i = 0; i < json.userLogs.length; i++) {
                          var tr = $("<tr></tr>");
                          table.append(tr);
                          var log = json.userLogs[i];
                          tr.append("<td>" + log.sequenceNumber + "</td>");
                          tr.append("<td>" + log.level.localizedName + "</td>");
                          tr.append("<td><pre>" + log.message + "</pre></td>");
                      }
                    }
                  } else {
                    div.append(json);
                  }
              }
              
              helio.plotSuccess = function(targetDiv, json) {
                  for (var i = 0; i < json.plotResults.length; i++) {
                      var plot = json.plotResults[i];
                      var div= $('<div style="padding:5px;width:100%;height:100%;overflow:auto"></div>');
                      targetDiv.append(div);
                      
                      var a = $('<a href="' + plot.url +'" target="_blank"></a>');
                      div.append(a);
                      
                      var img = $('<img id="plot_' + plot.id + '" class="task_output_plot" src="' + plot.url + '" title="' + plot.label + '. Click to open in new window." width="100%" />');
                      a.append(img);              
                  }
              }
          
              helio.plot = function(plotName, taskName) {
                  var query = $("#query_" + plotName).val();
                  ${ remoteFunction (controller: 'plot', action: 'asyncplot',
                     onSuccess: 'helio.plotSuccess($("#target_" + plotName), data)',
                     onFailure: 'helio.plotError($("#target_" + plotName), XMLHttpRequest.responseText)',
                     params: '"taskName=" + taskName + "&startTime=" + $("#inspectStartTime").val() + "&endTime="+$("#inspectEndTime").val() + "&" + query',
                     onLoading : '$("#target_" + plotName).prepend(\'' +
                                 '<img style="height:16px;width:16px;" src="' + resource(dir:'images/helio/',file:'load.gif') + '" alt="Loading..." /> '
                                  + ' Loading ... \');', 
                     onLoaded  : '$("#target_" + plotName).empty();',
                     method : 'get')
                  }
              };
          </g:javascript>
          
          <g:each var="plot" in="${plots}">
            <div id="tab_${plot.plotName}" class="dialog_tab_area">
              <input id="query_${plot.plotName}" type="hidden" value="${plot.query ? plot.query:''}">
              Click to load or reload the
              <a class="reload_link" onclick="helio.plot('${plot.plotName}', '${plot.taskName}');return false;">${plot.label}</a>
              <hr/>
              <div id="target_${plot.plotName}"></div>
            </div>
          </g:each>
          
          <%--external links --%>
          <div id="tab_external_pages">
            <p>Links to external sites</p>
            <ul>
              <g:each var="link" in="${links}">
                 <li><a target="_blank" href="${link.link}">${link.title}</a></li>
              </g:each>
            </ul>
          </div>
        </div>
      </td>
    </tr>
  </table>
</div>