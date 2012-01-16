<%-- Template for the time range summary
Expected params:
 * Integer step: step number for help text.
 --%><tr>
  <td style="border-top: solid 1px gray;">
    <b>Select Dates</b><br/>
    <table style="margin-bottom: 10px;">
      <tr>
        <td valign="top">
          <div class="resultDroppable showTimeRangeDialog" style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
            <img id="time_range_summary_drop"  style="margin:11px" src="${resource(dir:'images/helio',file:'circle_time_grey.png')}" />
          </div>
        </td>
        <td rowspan="2" id="time_range_summary_text" class="candybox showTimeRangeDialog" >
        </td>
        <td rowspan="2">
           <div id="time_range_summary_clear" class="custom_button">Clear</div>
        </td>
      </tr>
      <tr align="center">
        <td>
          <div id="time_range_summary_select" class="custom_button showTimeRangeDialog">Select</div>
        </td>
      </tr>
    </table>
  </td>
  <td style="border-top: solid 1px gray; vertical-align: top;">
    <div class="message"><b>Step ${step}</b><br/>Click on the 'Select' button to set the initial start date.</div>
  </td>
</tr>
