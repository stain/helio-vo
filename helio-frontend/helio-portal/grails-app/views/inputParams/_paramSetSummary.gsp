<%-- Template for the param set summary
Expected params:
 * Integer step: step number for help text.
 --%><tr>
  <td style="border-top: solid 1px gray;">
    <b>Parameters</b>
    <table style="margin-bottom: 10px;">
      <tr>
        <td valign="top">
          <div class="resultDroppable showParamSetDialog" style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
            <img id="paramset_summary_drop" style="margin:11px" src="${resource(dir:'images/helio',file:'circle_block_grey.png')}" />
          </div>      
        </td>
        <td rowspan="2" id="paramset_summary_text" class="candybox showParamSetDialog" >
        </td>
        <td rowspan="2">
           <div id="paramset_summary_clear" class="custom_button">Clear</div>
        </td>
      </tr>
      <tr align="center">
        <td>
          <div id="paramset_summary_select" class="custom_button showParamSetDialog">Select</div>
        </td>
      </tr>
    </table>
  </td>
  <td style="border-top: solid 1px gray; vertical-align: top;">
    <div class="message"><b>Step ${step}</b><br/>Click on the 'Select' button to define the input values for the Propagation Model.</div>
  </td>
</tr>
