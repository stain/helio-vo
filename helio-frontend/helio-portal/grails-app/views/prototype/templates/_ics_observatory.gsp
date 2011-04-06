<div class="ics_observatory">
  <h4>Observatory</h4>
  <table>
    <tr>
      <td align="left" valign="top">
        <table>
          <g:each in="${catalog}" var="field">
            <g:if test="${!field.startsWith('time_')}">
              <g:set var="hasFields" value="${true}" />
              <tr>
                <td>
                  <label style="display:block; float:left; width:150px;">${field}</label>
                </td>
                <td>
                  <!-- span id="cinfo_observatory_${field}" class="colLabelTooltipMe ui-icon ui-icon-info" style="display:inline-block; vertical-align: top;" ></span-->
                  <div class="hecLabelTooltip ctooltip_observatory_${field}"><pre style="white-space: pre-wrap">n/a</pre></div>
                </td>  
                <td>
                  <input class="columnSelection" name="observatory.${field}" type="text"/>
                </td>
              </tr>
            </g:if>
          </g:each>
        </table>
        <g:if test="${!hasFields}">
            <p>No field definition found for the observatorys table.</p>
        </g:if>
      </td>
    </tr>
  </table>
</div>