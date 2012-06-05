<%--Dialog to display a parameter set.
Currently the set is read from a definition object passed into this GSP.
Expected parameters:
 * Map paramSet a map containing the list of fields to be rendered. 
   Each field is a map that contains "label", "description", "type"
 * Map taskDescriptor: descriptor that describes the task
 --%><div class="input-dialog" id="paramSetDialog" style="display:none">
  <table class="dialog_table" >
    <tr valign="top" align="left" height="*">
      <td valign="top" width="90px">
        <div class="paramDroppable" style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
          <img style="margin:11px" src="${resource(dir:'images/helio',file:'circle_ParamSet.png')}" />
        </div>
      </td>
      <td valign="top" width="*">
        <table class="dialog_content_table" cellpadding="0" cellspacing="0">
          <thead>
            <tr >
              <th align="left" valign="top" style="padding-bottom: 0.5em;">
                Parameter
              </th>
              <th align="left" valign="top">
                Value
              </th>
              <th valign="top"></th>
            </tr>
          </thead>
          <tbody>
            <g:each var="paramDescriptorEntry" in="${paramSet.config}" status="i">
              <g:set var="paramName" value="${paramDescriptorEntry.key}" />
              <g:set var="param" value="${paramSet.params[paramName]}" />
              <g:set var="paramDescriptor" value="${paramDescriptorEntry.value}" />
              <g:set var="value" value="${param?.value ?:paramDescriptor.defaultValue}" />
              <g:set var="template" value="${paramDescriptor.template ?: '/dialog/_paramSetParamRow'}" />
              <g:if test="${paramDescriptor.render == null || paramDescriptor.render == true}">
                <g:render template="${template}" model="[paramName : paramName, param : param, paramDescriptor : paramDescriptor, value : value, paramSet : paramSet]"></g:render>
              </g:if>
            </g:each>
            <g:if test="${taskDescriptor.helpImage}" >
              <tr>
                <td colspan="3" > 
                  <img src="${resource(dir:'images/helio/hps', file:taskDescriptor.helpImage)}" style="height:220px; margin-top:10px"/>
                </td>
              </tr>
            </g:if>
          </tbody>
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