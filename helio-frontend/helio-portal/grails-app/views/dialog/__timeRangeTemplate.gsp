<%-- 
Helper template to display one time range entry.
Expected variables:
 * timeRange (with start and end Date)
 * i (integer or String as suffix for generated IDs.) 
--%><tr id="input_time_range_${i}" class="input_time_range" <%= hidden ? 'style="display:none"' : '' %> >
  <td align="center" valign="middle" class="input_time_range_label">
    Range ${i}
  </td>
  <td align="center" valign="middle">
    <input size="25" type="text" tabindex="-1" id="minDate_${i}" name="minDate" value="${g.formatDate(date:timeRange.start, format:'yyyy-MM-dd\'T\'HH:mm:ss')}"/>
  </td>
  <td align="center" valign="middle">
    <input size="25" type="text" tabindex="-1" id="maxDate_${i}" name="maxDate" value="${g.formatDate(date:timeRange.end, format:'yyyy-MM-dd\'T\'HH:mm:ss')}"/>
  </td>
  <td><img class="input_time_range_remove" id="input_time_range_remove_${i}" src="${resource(dir:'images/helio',file:'remove.png')}" /></td>
  <td style="width: 100px;"></td>
</tr>