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
    <div id="voTables" style="clear: both; padding: 0px 0px 10px 0px;">
      <g:set var="result" value="${result}" />
      <table style="width:100%">
        <tr>
          <td valign="top">
            <div id="votable_toolbar" class="toolbar candybox">
              <%-- download action --%>
              <g:link target="_blank"
                      id="download_as_votable"
                      controller="voTable"
                      action="download" 
                      params="[resultId : result.id]"
                      title="Download the VOTable as a file for further processing in external applications." ><%-- 
                --%><div class="toolbar-icon toolbar-icon-savevotable" ></div><%--
              --%></g:link>
              <%--info action --%>
              <div style="width: 600px;" id="table_info_all" class="ok_dialog table_info" title="Common information about the table (for debugging)">
                <g:if test="${result.actions.contains('info')}">
                  <dl>
                    <g:each in="${result.infos}" status="h" var="info">
                      <dt title="${result.description}"><b>${info.name}</b></dt>
                      <dd class="indented">${info.value ? info.value : 'no value'}</dd>
                    </g:each>
                  </dl>
                </g:if>
                <g:else>
                    No additional information available for this table.
                </g:else>
              </div>
              <div id="table_info_all_button" class="table_info_button toolbar-icon toolbar-icon-info"
                 title="Get advanced information about the retrieved VOTable">
              </div>
            </div>
          </td>
          <td width="250px" rowspan="2">
            <div class="message" style="margin-top:0;">
              Hover over the toolbar buttons to get more information about what they do.
            </div>
          </td>
        </tr>
        <tr>
          <td><span id="result_message"></span></td>
        </tr>
      </table>          
      <%-- loop over tables --%>
      <div id="tabs_votables">
        <ul>
          <g:each var="table" in="${result.tables}" status="h">
            <li><a href="#tab_votable_${h}">${table.name ? table.name : 'Un-named table'} (${table.data.rowCount})</a></li>
          </g:each>
        </ul>
        <g:each var="table" in="${result.tables}" status="h">
          <div id="tab_votable_${h}" style="padding: 0.4em 0;">
            <g:if test="${table.type == 'empty_resource'}">
               No data available.
            </g:if>
            <g:else>
              <g:if test="${taskDescriptor.resultfilter}">
                  <g:render template="/output/${taskDescriptor.resultfilter}" model="${[tableId:'table_' + result.id + '_' + h, table:table, taskDescriptor:taskDescriptor]}" />
              </g:if>
              <div class="tabs_votable_result">
                <ul>
                  <li><a href="#tab_votable_table_${result.id}_${h}">Show as table</a></li>
                  <li><a href="#tab_votable_plot_${result.id}_${h}">Show as plot</a></li>
                </ul>
                <div id="tab_votable_table_${result.id}_${h}">
          
                  <%-- table actions --%>
                  <div id="votable_toolbar" class="toolbar candybox">
                    <g:if test="${table.actions.contains('info')}">
                      <div id="table_info_${h}_button" class="table_info_button toolbar-icon toolbar-icon-info"
                          title="Get advanced information about the retrieved VOTable"></div>
                          
                      <div id="table_info_${h}" class="ok_dialog table_info" title="Table info for ${table.name ? table.name :  'Un-named table' } (for debugging)">
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
                    </g:if>
                    <%-- extract actions --%>
                    <g:if test="${table.actions.contains('extract')}">
                      <div id="extract_param_${h}_button" class="extract_param_button toolbar-icon toolbar-icon-extract" title="Extract the time ranges from the selected rows and add them to the data cart.">
                      </div>
                    </g:if>            
                    <g:if test="${table.actions.contains('extract_instrument')}">
                      <div id="extract_instrument_param_${h}_button" class="extract_instrument_param_button toolbar-icon toolbar-icon-extract" title="Extract the instrument labels from the selected rows and add them to the data cart.">
                      </div>
                    </g:if>                  
                    <%-- download selection action --%>
                    <g:if test="${table.actions.contains('download')}">
                      <div id="download_selection_${h}_button" class="download_selection_button toolbar-icon toolbar-icon-downloadfiles" title="Open a dialog with all (selected) URLs."></div>
                    </g:if>
                  </div>

                  <g:render template="/output/_votable" model="${[tableId:'table_' + result.id + '_' + h, table:table, tableName : 'initTable_' + taskDescriptor.serviceName, renderData:false]}"/>
                </div>
                <div id="tab_votable_plot_${result.id}_${h}">
                    <g:render template="/output/_votablePlot" model="${[tableId:'table_' + result.id + '_' + h, plotTitle : table.name ? table.name : taskDescriptor.label]}"/>
                </div>
              </div>
            </g:else>
          </div>
        </g:each>
      </div>
    </div>
  </div>
</div>