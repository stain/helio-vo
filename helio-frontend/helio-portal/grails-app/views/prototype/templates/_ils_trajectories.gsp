<div class="ils_trajectories">
  <h4>Trajectories</h4>
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
                  <!-- span id="cinfo_trajectories_${field}" class="colLabelTooltipMe ui-icon ui-icon-info" style="display:inline-block; vertical-align: top;" ></span-->
                  <div class="hecLabelTooltip ctooltip_trajectories_${field}"><pre style="white-space: pre-wrap">n/a</pre></div>
                </td>  
                <td>
                  <input class="columnSelection" name="trajectories.${field}" type="text"/>
                </td>
              </tr>
            </g:if>
          </g:each>
        </table>
        <g:if test="${!hasFields}">
            <p>No field definition found for the Trajectories table.</p>
        </g:if>
      </td>
    </tr>
  </table>
</div>