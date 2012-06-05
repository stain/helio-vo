<%--Dialog to display the DES input dialog.
The dialogs values are read from the DES configuration service passed into this GSP.
Expected parameters:
 * DesConfiguration desConfiguration a bean containing the list of missions, functions and params. 
 * Map taskDescriptor: descriptor that describes the task
 --%><div class="input-dialog" id="paramSetDialog" style="display:none">
  <table class="dialog_table">
    <tr valign="top" align="left" height="*">
      <td valign="top" width="90px">
        <div class="paramDroppable" style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
          <img style="margin:11px" src="${resource(dir:'images/helio',file:'circle_ParamSet.png')}" />
        </div>
      </td>
      <td valign="top" width="*">
        <table class="dialog_content_table" cellpadding="0" cellspacing="0">
          <tr>
            <td>
              <label title="Select the mission you want to process">Mission</label>
              <g:select name="des_mission" 
                noSelection="['':'-Select a mission-']" 
                from="${desConfiguration.missions}" 
                optionKey="id" optionValue="name" />
            </td>
          </tr>
          <tr>
            <td>
              <label title="Select the function to apply to the mission data">Function</label>
              <g:select name="des_function" 
                noSelection="['':'-Select a function-']" 
                from="${desConfiguration.functions}" 
                optionKey="id" optionValue="name" />
            </td>
          </tr>
          <tr>
            <td>
              <label>Parameter</label>
              <div id="des_parameter"></div>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <%-- label management --%>
    <tr valign="top" align="left" height="20" >
      <td colspan="2" style="padding:5px; border-top:2px solid grey" title="Assign a name to the current parameter set.">Name: <input tabindex="1" id="param_set_name" name="param_set_name" type="text" tabindex="1" value="${paramSet.name}"/>
      <div class="message" style="display:inline; float:right; margin:0px; padding:3px">Provide a name to add this parameter set to your <i>Data Cart</i>.</div>
    </tr>
  </table>
</div>