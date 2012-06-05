<%-- 
Template to display the date input dialog.
Expected variables:
 * TimeRangeParam timeRange
 * TimeRange defaultTimeRange
 * Map taskDescriptor: descriptor that describes the task
--%><div class="input-dialog" id="timeRangeDialog" style="display:none">
  <g:set var="paramDescriptor" value="${taskDescriptor.inputParams.timeRanges.timeRanges}" />
  <%--input dialog for dates --%>
  <table class="dialog_table">
    <tr valign="top" align="left" height="*">
      <td valign="top" width="90px">
        <div class="paramDroppable"style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
          <img style="margin:11px" src="${resource(dir:'images/helio',file:'circle_TimeRange.png')}" />
        </div>
      </td>
      <td valign="top"  width="*">
        <div id="timeRangeDialogMessage"></div>
        <table class="dialog_content_table" cellpadding="0" cellspacing="0">
          <thead>
            <tr>
              <th style="width: 50px;"></th>
              <th align="center" style="width:180px;">
                Start Date
              </th>
              <th align="center" style="width:180px;">
                <g:if test="${!paramDescriptor.restriction || !paramDescriptor.restriction.contains('start_time')}">
                  End Date
                </g:if>
              </th>
              <th></th>
            </tr>
          </thead>
          <tbody id="input_time_range_list">
            <%-- template string will be used by javascript to add new time entries. --%>
            <g:render template="/dialog/_timeRangeTemplate" model="[i:'tpl', timeRange:defaultTimeRange, paramDescriptor : paramDescriptor, hidden:true]"></g:render>
            <g:each var="curTimeRange" in="${timeRange.timeRanges}" status="i">
              <g:render template="/dialog/_timeRangeTemplate" model="[i:i+1, timeRange:curTimeRange, paramDescriptor : paramDescriptor]"></g:render>
            </g:each>
          </tbody>
        </table>
      </td>
    </tr>
    <g:if test="${!paramDescriptor.restriction || paramDescriptor.restriction.contains('multi')}">
      <tr valign="top" align="left" style="height:20px;">
        <td></td>
        <td style="padding-bottom:5px;"><div id="input_time_range_button">+ Range</div></td>
      </tr>
    </g:if>
    <%-- label management --%>
    <tr valign="top" align="left" height="20" >
      <td colspan="2" style="padding:5px; border-top:2px solid grey" title="Assign a name to the current date range.">Name: <input tabindex="-1" id="time_range_name" name="time_range_name" type="text" tabindex="1" value="${timeRange.name}"/>
      <div class="message" style="display:inline; float:right; margin:0px; padding:3px">Provide a name to add this date range to your <i>Data Cart</i>.</div>
    </tr>
  </table>
</div>