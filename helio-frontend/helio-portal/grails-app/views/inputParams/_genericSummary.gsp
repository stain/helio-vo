<%-- Template for the param set summary
Expected params:
 * Integer step: step number for help text.
 * String paramName : name of the param
 * String title
 --%><tr>
  <td style="border-top: solid 1px gray;">
    <b>${title}</b>
    <table style="margin-bottom: 10px;">
      <tr>
        <td valign="top">
          <div class="paramDroppable paramDroppable${paramName} show${paramName}Dialog" style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
            <img id="img${paramName}Summary" class="paramDraggable paramDraggable${paramName}"  style="margin:11px" src="${resource(dir:'images/helio',file:'circle_' + paramName + '_grey.png')}" />
          </div>
        </td>
        <td rowspan="2" id="text${paramName}Summary" class="candybox showDialog show${paramName}Dialog" >
        </td>
        <td rowspan="2">
           <div class="button${paramName} clear${paramName}Summary">Clear</div>
        </td>
      </tr>
      <tr align="center">
        <td>
          <div class="button${paramName} show${paramName}Dialog">Select</div>
        </td>
      </tr>
    </table>
  </td>
  <td style="border-top: solid 1px gray; vertical-align: top;">
    <div class="message"><b>Step ${step}</b><br/>Specify further values of this task.</div>
  </td>
</tr>
