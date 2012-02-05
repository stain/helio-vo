<%-- Template for the time range summary
Expected params:
 * Integer step: step number for help text.
 --%><tr>
  <td style="border-top: solid 1px gray;">
    <b>Select Dates</b><br/>
    <table style="margin-bottom: 10px;">
      <tr>
        <td valign="top">
          <div class="paramDroppable paramDroppableTimeRange showTimeRangeDialog" style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
            <img id="imgTimeRangeSummary" class="paramDraggable paramDraggableTimeRange" style="margin:11px" src="${resource(dir:'images/helio',file:'circle_TimeRange_grey.png')}" />
          </div>
        </td>
        <td rowspan="2" id="textTimeRangeSummary" class="candybox showTimeRangeDialog" >
        </td>
        <td rowspan="2">
           <div class="buttonTimeRange clearTimeRangeSummary">Clear</div>
        </td>
      </tr>
      <tr align="center">
        <td>
          <div class="buttonTimeRange showTimeRangeDialog">Select</div>
        </td>
      </tr>
    </table>
  </td>
  <td style="border-top: solid 1px gray; vertical-align: top;">
    <div class="message"><b>Step ${step}</b><br/>Set a <i>start date</i> or a <i>date range</i>.
    If a start and end date are equal they will be treated as single time, otherwise as time range.</div>
  </td>
</tr>
