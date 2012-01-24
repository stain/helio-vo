<%--
Render a VOTable result
Expected model:
 * TableModel result : a map holding the components of a VOTable (as defined by VoTableService).
 * Map taskDescriptor : the task descriptor  
 --%><div class="header votableResultHeader viewerHeader">
  <h1>VOTable for task '${taskDescriptor.label}'</h1>
</div>
<div class="content task_body">
  <div>
    <div id="voTables" style="clear: both; padding: 0px pt 20pt 5px;">
      <g:set var="result" value="${result}" />
      <table style="width:100%">
        <tr>
          <td valign="top">
            <%-- table actions --%>
            <g:form target="_blank" controller="voTable" method="get" action="download" style="display: inline">
              <input id="resultId" name="resultId" type="hidden" value="${result.id}" />
              <g:actionSubmit class="custom_button" value="Save as VOTable" name="download" action="download"  />
            </g:form>
  
            <%--info action --%>
            <g:if test="${result.actions.contains('info')}">
              <div style="width: 600px;" id="table_info_all" class="ok_dialog table_info" title="Common information about the table (for debugging)">
                <dl>
                  <g:each in="${result.infos}" status="h" var="info">
                    <dt title="${result.description}"><b>${info.name}</b></dt>
                    <dd class="indented">${info.value ? info.value : 'no value'}</dd>
                  </g:each>
                </dl>
              </div>
              <div id="table_info_all_button" class="table_info_button custom_button" style="margin-left: 5px;">
                Info
              </div>
            </g:if>
            
            <%-- download all actions --%>
            <g:if test="${result.actions.contains('download')}">
              <div id="download_selection_button" class="custom_button" style="margin-left: 5px;" >
                Download selected files/all
              </div>
            </g:if>            
          </td>
          <td width="400px">
            <div class="message"
              style="margin: 0; clear: both; padding: 0px 10px 0pt 0px; max-width: 400px;">
              <b>Select result</b><br /> To save your results you
              can click on 'Save as VOTable', you can also transform
              them into parameters to use in another query by
              selecting the rows of interest and then clicking on
              "Save selection to Data-Cart" or download the data by
              clicking on "Download Selected files/all". These
              options will only be avaliable where applicable.
            </div>
          </td>
        </tr>
      </table>          
      <%-- loop over tables --%>
      <div>
        <g:each var="table" in="${result.tables}" status="h">
          <g:if test="${table.type == 'empty_resource'}">
             No data available. This is most likely because of an empty or invalid VOTable.
          </g:if>
          <g:else>
            <div style="margin-top: 20px; margin-bottom: 5px; border-bottom: 3px solid black">
              <h3>
                ${table.name ? table.name : 'Un-named table'}
              </h3>
            </div>
            
            <div id="table_info_${result.id}_${h}" class="ok_dialog table_info" title="Table info for ${table.name ? table.name :  'Un-named table' } (for debugging)">
              <g:if test="${table.infos.size() > 0}">
                <h2>Info Tags</h2>
                <br>
                <div><dl>
                <g:each in="${table.infos}" var="info">
                	<dt title="${info.description}" ><b>${info.name}</b></dt>
                	<dd class="indented">${info.value ? info.value : 'undefined value'}</dd>
                </g:each>
                </dl></div>
              </g:if>
              <g:if test="${table.params.size() > 0}">
                <h2>Param Tags</h2>
                <ul>
                  <g:each in="${table.params}" status="paramNo" var="param" >
                  	  <dt title="${param.description}" ><b>${param.name}</b></dt>
                	  <dd class="indented">${param.value ? param.value : 'undefined value'}</dd>        
                  </g:each>
                </ul>
              </g:if>            
              <g:if test="${table.links.size() > 0}">
                <h2>Links</h2>
                <ul>
                  <g:each in="${table.links}" status="linkNo" var="plink" >
                      <li title="${link.description}"><a href="${link.href}">${link.handle ? link.handle : link.name? link.name : link.href}</a></li>        
                  </g:each>
                </ul>
              </g:if>
            </div>

            <%-- TODO: add table actions --%>
            <g:if test="${table.actions.contains('info')}">
              <div id="table_info_${h}_button" class="table_info_button custom_button">
                Info
              </div>
            </g:if>
            <!--div reference="resultTable${h}" id="resultSelectionSelectAll" class="custom_button" style="margin-right:10px;float:left;">Select All</div-->
            
            <table cellpadding="0" cellspacing="0" border="0" class='resultTable' id="table_${result.id}_${h}">
              <thead>
                <tr>
                  <g:each var="field" in="${table.fields}" status="i">
                    <th title="${field.description}">
                      ${field.name}
                      <g:if env="dev">
                      ${field.utype}
                      ${field.ucd}
                      ${field.unit}
                      </g:if>
                    </th>
                  </g:each>
                </tr>
              </thead>
              <g:if test="${table.data.rowCount > 0}">
                <tbody>
                  <g:each var="i" in="${0..<table.data.rowCount}">
                    <tr id="table_${result.id}_${h}_row_${i}" >
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
                        <g:else>
                          <td>${cell}</td>
                        </g:else>
                      </g:each>
                    </tr>
                  </g:each>
                </tbody>
              </g:if>
            </table>
          </g:else>
        </g:each>
      </div>
    </div>
  </div>
</div>