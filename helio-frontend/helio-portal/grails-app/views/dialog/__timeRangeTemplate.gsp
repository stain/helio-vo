<%-- 
Helper template to display one time range entry.
Expected variables:
 * timeRange (with start and end Date)
 * i (integer or String as suffix for generated IDs.) 
 * Map paramDescriptor: descriptor that describes the time param.
--%><tr id="input_time_range_${i}" class="input_time_range" <%= hidden ? 'style="display:none"' : '' %> >
  <td align="center" valign="middle" class="input_time_range_label">
    # ${i}
  </td>
  <td align="center" valign="middle">
    <input style="width:150px" type="text" tabindex="-1" id="minDate_${i}" name="minDate" value="${g.formatDate(date:timeRange.startTime, format:'yyyy-MM-dd\'T\'HH:mm:ss')}"/>
  </td>
  <td align="center" valign="middle">
    <g:if test="${!paramDescriptor.restriction || !paramDescriptor.restriction.contains('start_time')}">
      <input style="width:150px" type="text" tabindex="-1" id="maxDate_${i}" name="maxDate" value="${g.formatDate(date:timeRange.endTime, format:'yyyy-MM-dd\'T\'HH:mm:ss')}"/>
    </g:if>
  </td>
  <td>
    <img class="input_time_range_inspect" id="input_time_range_inspect_${i}" src="${resource(dir:'images',file:'search.png')}" 
      title="Inspect current time range" width="16" height="16"/>
    <g:if test="${!paramDescriptor.restriction || paramDescriptor.restriction.contains('multi')}">
      <img class="input_time_range_remove" id="input_time_range_remove_${i}" src="${resource(dir:'images/helio',file:'remove.png')}" />
    </g:if>    
    
  </td>
  <td style="width: 100px;"></td>
</tr>