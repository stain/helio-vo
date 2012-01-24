<%--Dialog to display a parameter set.
Currently the set is read from a definition object passed into this GSP.
Expected parameters:
 * Map paramSet a map containing the list of fields to be rendered. 
   Each field is a map that contains "label", "description", "type"
 *
 --%><div class="input-dialog" id="paramSetDialog" style="display:none">
  <table class="dialog_table" >
    <tr valign="top" align="left" height="*">
      <td valign="top" width="90px">
        <div class="resultDroppable" style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
          <img id="drop_paramset" style="margin:11px" src="${resource(dir:'images/helio',file:'circle_block.png')}" />
        </div>
      </td>
      <td valign="top" width="*">
        <table class="dialog_content_table" cellpadding="0" cellspacing="0">
          <thead>
            <tr>
              <th align="left">
                Parameter
              </th>
              <th align="left" >
                Value
              </th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <g:each var="param" in="${paramSet.params}" status="i">
              <g:set var="paramDescriptor" value="${taskDescriptor.inputParams.paramSet[param.key]}" />
              <tr>
                <td>
                  ${paramDescriptor.label}
                </td>
                <td>
                  <g:if test="${paramDescriptor.valueDomain}" >
                    <g:select class="paramSetEntry" name="${param.key}" value="${param.value}" from="${paramDescriptor.valueDomain}"/>
                  </g:if>
                  <g:else>
                    <input size="7" type="text" class="paramSetEntry" name="${param.key}" title="${paramDescriptor.label}" value="${param.value}" />
                  </g:else>
                </td>
                <td>
                  <div class="message" style="display:inline; width:250px; float:right; margin:2px 0px; padding:3px">
                    ${paramDescriptor.description}
                  </div>
                </td>
              </tr>
            </g:each>
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