<%-- 
Template to display the date input dialog.
Expected variables:
 * TimeRangeParam timeRange
 * TimeRange defaultTimeRange
 *  
--%><div class="input-dialog" id="timeRangeDialog" style="display:none">
  <%--input dialog for dates --%>
  <table class="dialog_table">
    <tr valign="top" align="left" height="*">
      <td valign="top" width="90px">
        <div class="resultDroppable"style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
          <img id="drop_time" style="margin:11px" src="${resource(dir:'images/helio',file:'circle_time.png')}" />
        </div>
      </td>
      <td valign="top"  width="*">
        <table class="dialog_content_table" cellpadding="0" cellspacing="0">
          <thead>
            <tr>
              <th></th>
              <th align="center">
                Start Date
              </th>
              <th align="center">
                End Date
              </th>
              <th></th>
            </tr>
          </thead>
          <tbody id="input_time_range_list">
            <%-- template string will be used by javascript to add new time entries. --%>
            <g:render template="/dialog/_timeRangeTemplate" model="[i:'tpl', timeRange:defaultTimeRange, hidden:true]"></g:render>
            <g:each var="curTimeRange" in="${timeRange.timeRanges}" status="i">
              <g:render template="/dialog/_timeRangeTemplate" model="[i:i+1, timeRange:curTimeRange]"></g:render>
            </g:each>
          </tbody>
        </table>
      </td>
    </tr>
    <tr valign="top" align="left" style="height:20px;">
      <td></td>
      <td style="padding-bottom:5px;"><div id="input_time_range_button" class="custom_button">+ Range</div></td>
    </tr>
    <%-- label management --%>
    <tr valign="top" align="left" height="20" >
      <td colspan="2" style="padding:5px; border-top:2px solid grey" title="Assign a name to the current date range.">Name: <input tabindex="-1" id="time_range_name" name="time_range_name" type="text" tabindex="1" value="${timeRange.name}"/>
      <div class="message" style="display:inline; float:right; margin:0px; padding:3px">Provide a name to add this date range to your <i>Data Cart</i>.</div>
    </tr>
  </table>
</div>