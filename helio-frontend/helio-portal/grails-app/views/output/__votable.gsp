<%-- Render a single table of a full votable.
Expected params: 
* String tableId: a unique identifier of the table for a prefix of the table rows.
* String tableName: name of the table. Will be used to attach specific JavaScripts to a table.
* Map table : the particular table model as created by votableService.
* boolean renderData : render the body section, if true
 --%>
<table cellpadding="0" cellspacing="0" border="0" class='resultTable' id="${tableId}" name="${tableName}">
  <thead>
    <tr>
      <g:each var="action" in="${table?.rowactions}" status="i">
        <th class="th_${action}"></th>
      </g:each>
      <g:each var="field" in="${table.fields}" status="i" >
        <th title="${field.description}" class="${field?.rendering_hint=='hidden' ? 'hiddenRow' :''}" >${field.name}</th>
      </g:each>
    </tr>
  </thead>
  <g:if test="${renderData && table.data.rowCount > 0}">
    <tbody>
      <g:each var="i" in="${0..<table.data.rowCount}">
        <tr id="${tableId}_row_${i}" >
          <g:each var="action" in="${table?.rowactions}" status="j">
            <td class="${action}"></td>
          </g:each>
          <g:each var="cell" in="${table.data.getRow(i)}" status="j" >
            <g:if test="${cell == table.fields[j].nullValue}">
              <td>-</td>
            </g:if>
            <g:elseif test="${table.fields[j].rendering_hint=='url' && cell != null}">
              <td>
                <a target="_blank" href="${cell}">
                  ${cell.substring(cell.lastIndexOf('/')+1,cell.length())}
                </a>
              </td>
            </g:elseif>
            <g:elseif test="${table.fields[j].rendering_hint.startsWith('url=') && cell != null}">
              <td>
                <a target="_blank" href="${String.format(table.fields[j].rendering_hint.substring(4), cell)}" >
                  ${cell}
                </a>
              </td>
            </g:elseif>
            
            <g:else>
              <td>${cell}</td>
            </g:else>
          </g:each>
        </tr>
      </g:each>
    </tbody>
  </g:if>
</table>