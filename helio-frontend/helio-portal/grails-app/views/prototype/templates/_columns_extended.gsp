<div class="hec_${catalog.catalogName}">
  <table width="100%">
    <tr>
      <g:set var="hasFields" value="${false}" />
      <td valign="top" align="left" colspan="3">
        <h4>
          Event list: <span style="color:highlight">${catalog.label} (${catalog.catalogName})</span>
          <span id="cinfo_${catalog.catalogName}" class="hecColLabelTooltipMe ui-icon ui-icon-info" style="display:inline-block; vertical-align: top;" ></span>          
        </h4>
        <div class="hecLabelTooltip ctooltip_${catalog.catalogName}"><pre style="white-space: pre-wrap">${catalog.description ? catalog.description : 'n/a'}</pre></div>
      </td>
    </tr>
    <tr>
      <td align="left" valign="top">  
        <table>
          <g:each in="${catalog.fields}" var="field">
            <g:if test="${field.name != '#' && field.name != 'HEC_id' && !field.name.startsWith('time_')}">
              <g:set var="hasFields" value="${true}" />
              <tr>
                <td>
                  <label style="display:block; float:left; width:150px;">${field.label}</label>
                </td>
                <td>
                  <span id="cinfo_${catalog.catalogName}_${field.name}" class="hecColLabelTooltipMe ui-icon ui-icon-info" style="display:inline-block; vertical-align: top;" ></span>                                  
                  <div class="hecLabelTooltip ctooltip_${catalog.catalogName}_${field.name}"><pre style="white-space: pre-wrap">${field.description? field.description : 'n/a'}</pre></div>
                </td>  
                <td>
                  <input class="columnSelection" name="${catalog.catalogName}.${field.name}" type="text"/>
                </td>
              </tr>
            </g:if>
          </g:each>
        </table>
        <g:if test="${!hasFields}">
            <p>No field definition found for this event list.</p>
        </g:if>
      </td>
      <g:if test="${hasFields}">
        <td width="*" align="left" valign="top">
          <input style="padding: 0 0.2em 0.2em 0.2em" class="column-reset" type="button" value="Reset Fields" title="Reset this form" onclick="resetHecForm('${catalog.catalogName}')">
        </td>
        <td align="right">
          <div style="" class="message pqlmessage">
            <b>Usage:</b>
            <p>The current version of HELIO supports 
              <a href="http://www.ivoa.net/internal/IVOA/TableAccess/PQL-0.2-20090520.pdf" target="blank" title="Parameterized Query Language">PQL</a> only.
            </p>
            <table border="0" cellpadding="0" cellspacing="0">
              <tbody>
              	<tr><td><code>'/25.2'</code></td><td> for&nbsp;</td><td><code>&lt;=25.2</code></td><td>(less than equal)</td></tr>
              	<tr><td><code>'25.2/'</code></td><td> for </td><td><code>&gt;=25.2</code></td><td>(greater than equal)</td></tr>
              	<tr><td><code>'3/6'</code></td><td> for </td><td><code>between(3, 6)</code></td><td>(between)</td></tr>
              	<tr><td><code>'*text*'</code></td><td> for </td><td><code>like %text%</code></td><td> (like)</td></tr>
              	<tr><td><code>'3'</code></td><td> for </td><td><code>=3</code></td><td>(number equals)</td></tr>
              	<tr><td><code>'text'</code></td><td> for </td><td><code>='text'</code></td><td>(string equals)</td></tr>
              	<tr><td><code>'/2,7/'</code></td><td> for </td><td><code>x&lt;=2 OR x&gt;=7</code></td><td>(or constraint)</td></tr>
              	<tr><td><code>'2/;/7'</code></td><td> for </td><td><code>x&gt;=2 AND x&lt;=7</code></td><td>(and constraint)</td></tr>
              </tbody>
            </table>
          </div>
        </td>
      </g:if>
      <g:else>
        <td></td><td></td>
      </g:else>
    </tr>
  </table>
</div>
