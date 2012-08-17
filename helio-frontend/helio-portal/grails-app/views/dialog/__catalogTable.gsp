<%-- Render a single table of a full votable.
Expected params: 
* String tableId : id of the table.
* String tableName : name attribute of the table.
* Set<ConfigurablePropertyDescriptor> columnDescriptors : a set of ConfigurablePropertyDescriptors that describe 
  the properties of the catalog descriptor. One entry per column.
* Set<> rowDescriptors : a set of catalog descriptors, one entry per line
 --%><table cellpadding="0" cellspacing="0" border="0" class="resultTable" id="${tableId}" name="${tableName}">
  <thead>
    <tr>
      <th title="Default sort order" id="sortOrder" class="sortOrder">Sort order</th>
      <g:each var="col" in="${columnDescriptors}" status="i" >
        <th title="${col.shortDescription}" id="${col.name}" class="${col.name}" >${col.displayName ?: col.name}</th>
      </g:each>
    </tr>
  </thead>
  <g:if test="${rowDescriptors.size() > 0}">
    <tbody>
      <g:each var="row" in="${rowDescriptors}" status="i">
        <tr id="${tableId}_row_${i}" >
          <td>${i+1}</td>
          <g:each var="col" in="${columnDescriptors}" status="j" >
            <g:set var="value" value="${col.readMethod.invoke(row)}"/>
            <td>
              <g:if test="${value instanceof Date}">
                <g:formatDate date="${value}" format="yyyy-MM-dd"/>
              </g:if>
              <g:else>
                ${value}
              </g:else>
            </td>
          </g:each>
        </tr>
      </g:each>
    </tbody>
  </g:if>
</table>