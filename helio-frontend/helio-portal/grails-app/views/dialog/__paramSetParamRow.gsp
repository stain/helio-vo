<%-- Render one row of a param set.
* String paramName: name of the param
* Object param: the stored param and it actual value
* Map paramDescriptor: map that describes the param to render
* Object value: the currently stored value or the default value.
 --%><tr>
  <td valign="top">
    <b>${paramDescriptor.label}&nbsp;</b>
  </td>
  <td valign="top">
    <g:if test="${paramDescriptor.valueDomain}" >
      <select id="${paramName}" name="${paramName}" class="paramSetEntry" 
          title="${paramDescriptor.label}" >
          <g:each var="opt" in="${paramDescriptor.valueDomain}">
              <option value="${opt.value}" title="${opt.description}" >${opt.label}</option>
          </g:each>
      </select>
    </g:if>
    <g:elseif test="${paramDescriptor.type.isEnum()}" >
      <g:each var="type" in="${paramDescriptor.type.values()}">
        <g:radio class="paramSetEntry" name="${paramName}" value="${type.toString()}" checked="${type.toString() == value.toString()}" title="${paramDescriptor.label}"/>
        <g:message code="${type.label}" /><br/>
      </g:each>
    </g:elseif>
    <g:else>
      <input size="7" type="text" class="paramSetEntry" name="${paramName}" title="${paramDescriptor.label}" value="${value.toString()}" />
    </g:else>
  </td>
  <td valign="top">
    <div class="message" style="display:inline; width:250px; float:right; margin:2px 0px; padding:3px">
      ${paramDescriptor.description}
    </div>
  </td>
</tr>