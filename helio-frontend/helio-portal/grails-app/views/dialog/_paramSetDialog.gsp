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
              <g:set var="param" value="${paramSet.params[paramDescriptorEntry.key]}" />
              <g:set var="paramDescriptor" value="${paramDescriptorEntry.value}" />
              <g:set var="value" value="${param?.value ?:paramDescriptor.defaultValue}" />
              <tr>
                <td valign="top">
                  <b>${paramDescriptor.label}&nbsp;</b>
                </td>
                <td valign="top">
                  <g:if test="${paramDescriptor.valueDomain}" >
                    <g:select class="paramSetEntry" id="${paramDescriptorEntry.key}" name="${paramDescriptorEntry.key}" value="${value.toString()}" from="${paramDescriptor.valueDomain}" title="${paramDescriptor.label}"/>
                  </g:if>
                  <g:elseif test="${paramDescriptor.type.isEnum()}" >
                    <g:each var="type" in="${paramDescriptor.type.values()}">
                      <g:radio class="paramSetEntry" name="${paramDescriptorEntry.key}" value="${type.toString()}" checked="${type.toString() == value.toString()}" title="${paramDescriptor.label}"/>
                      <g:message code="${type.label}" /><br/>
                    </g:each>
                  </g:elseif>
                  <g:else>
                    <input size="7" type="text" class="paramSetEntry" name="${paramDescriptorEntry.key}" title="${paramDescriptor.label}" value="${value}" />
                  </g:else>
                </td>
                <td valign="top">
                  <div class="message" style="display:inline; width:250px; float:right; margin:2px 0px; padding:3px">
                    ${paramDescriptor.description}
                  </div>
                </td>
              </tr>
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