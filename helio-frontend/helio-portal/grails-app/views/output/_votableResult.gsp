    <div class="header queryHeader viewerHeader">
      <h1>Result</h1>
    </div>
    <div class="content">
      <div>
        <table width="100%">
          <tr>
            <td>
            </td>
          </tr>
        </table>
        <div id="voTables" style="clear: both; padding: 0px pt 20pt 5px;">
          <g:set var="result" value="${votableResult.result}" />
          
          <table width="100%">
            <tr>
              <td valign="top">
                <%-- table actions --%>
                <g:form target="_blank" controller="voTable" method="get" action="download" style="display: inline">
                  <input id="resultId" name="resultId" type="hidden" value="${result.id}" />
                  <g:actionSubmit class="custom_button" value="Save as VOTable" name="download" action="download"  />
                </g:form>
      
                <%-- VOTable header info. dialog --%>
                <g:if test="${result.infos.size() > 0}">
                  <div id="table_info_all" class="ok_dialog">
                    <h1>Common information about the table</h1>
                    <ul>
                      <g:each in="${result.infos}" status="status" var="info">
                        <li>${info.name}=${info.value ? info.value : 'no value'}</li>
                      </g:each>
                    </ul>
                  </div>
                  <div id="table_info_all_button" class="table_info_button custom_button" style="margin-left: 5px;">
                    Info
                  </div>
                </g:if>
      
                <div id="download_selection_button" class="custom_button" style="margin-left: 5px;" >
                  Download selected files/all
                </div>
                <g:if test="${votableResult.message}">
                  <div class="message">${votableResult.message}</div>
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
            <g:each in="${result.tables}" status="status" var="table">
              <g:if test="${table.type == 'empty_resource'}">
                 No data available. This is most likely because of an empty or invalid VOTable.
              </g:if>
              <g:else>
                <div style="margin-top: 20px; margin-bottom: 5px; border-bottom: 3px solid black">
                  <h3>
                    ${table.name ? table.name : 'Un-named table'}
                  </h3>
                </div>
                
                <div id="table_info_${status}" class="ok_dialog">
                  <h1>Table info for ${table.name ? table.name :  'Un-named table' }</h1>
                  <g:if test="${table.infos.size() > 0}">
                    <h2>Info Tags</h2>
                    <div><ul>
                    <g:each in="${table.infos}" var="info">
                      <li title="${info.description}">${info.name}=${info.value ? info.value : 'undefined value'}</li>
                    </g:each>
                    </ul></div>
                  </g:if>
                  <g:if test="${table.params.size() > 0}">
                    <h2>Param Tags</h2>
                    <ul>
                      <g:each in="${table.params}" status="paramNo" var="param" >
                          <li title="${param.description}">${param.name}=${param.value ? param.value : 'undefined value'}</li>        
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
                <div id="table_info_${status}_button" class="table_info_button custom_button">
                  Info
                </div>
                
                <!--div reference="resultTable${status}" id="resultSelectionSelectAll" class="custom_button" style="margin-right:10px;float:left;">Select All</div-->
                
                <table cellpadding="0" cellspacing="0" border="0" class='resultTable' id="resultTable${status}">
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
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                          <g:each in="${table.data.getRow(i)}" status="j" var="cell">
                            <g:if test="${table.fields[j].name=='url'}">
                              <td>
                                <a target="_blank" href="${cell}">
                                  ${cell.substring(cell.lastIndexOf('/')+1,cell.length())}
                                </a>
                              </td>
                            </g:if>
                            <g:elseif test="${cell == table.fields[j].nullValue}">
                              <td>-</td>
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
  <%-- show the user log if available --%>
  <g:if test="${votableResult?.result && votableResult.result?.logRecords?.length > 0}">
    <div class="module">
      <div class="header queryHeader viewerHeader collapsed">
        <h1>Log</h1>
      </div>
      <div class="content">
        <table cellpadding="0" cellspacing="0" border="1">
          <g:each in="${votableResult.result.logRecords}" var="record">
            <tr>
              <td valign="top" align="left"><%=record.level %></td>
              <td valign="top" align="left"><%=record.message %></td>
            </tr>
          </g:each>
        </table>
      </div>
    </div>
  </g:if>
