<div class="hec_${catalog.catalogName}">
  <table width="100%">
    <tr>
      <g:set var="hasFields" value="${false}" />
      <td valign="top" align="left">
        <h4 title="&lt;pre style=&quot;white-space: pre-wrap;&quot;&gt;${catalog.description?.encodeAsHTML()}&lt;/pre&gt;" class="hecColLabelTooltipMe">
          Event list: <span style="color:highlight">${catalog.label} (${catalog.catalogName})</span>
        </h4>
        <ul style="list-style-type:none;">
          <g:each in="${catalog.fields}" var="field">
            <g:if test="${field.name != '#' && field.name != 'HEC_id' && !field.name.startsWith('time_')}">
              <g:set var="hasFields" value="${true}" />
              <li>
                <label class="hecColLabelTooltipMe" style="display:block; float:left; width:150px;" title="&lt;pre style=&quot;white-space: pre-wrap;&quot;&gt;${field.description?.encodeAsHTML()}&lt;/pre&gt;">${field.label}</label>
                <input class="columnSelection" name="${catalog.catalogName}.${field.name}" type="text"/>
              </li>
            </g:if>
          </g:each>
        </ul>
        <g:if test="${!hasFields}">
            No field definition found for this event list.
        </g:if>
      </td>
      <g:if test="${hasFields}">
        <td align="left" valign="top">
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
    </tr>
  </table>
</div>
