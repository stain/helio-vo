<div id="actionViewer">
  <div class="viewerHeader">
    <table  style="height:30px;width: 100%;">
      <tr >
        <td width="60">
          <img height="30px" src="${resource(dir:'images/icons/toolbar',file:'hec.png')}" />
        </td>
        <td>
          <h1 style="font-size:2em;font-weight: normal;margin-top: 10px">HELIO Event Catalog Service</h1>
          Label: <input style="margin-top:5px;" id="label" type="text"/>
        </td>
        <td>
          <div style="float:right;" class="controls custom_button" id="delete">X</div>
          <div style="float:right;display:none" class="controls custom_button" id="forward">Next</div>
          <div style="float:right;display:none" class="controls custom_button" id="counter" ></div>
          <div style="float:right;display:none" class="controls custom_button" id="backward" >Prev</div>
        </td>
      </tr>
    </table>
  </div>

  <div class="module">
    <div class="header queryHeader viewerHeader">
      <h1>Query Form</h1>
    </div>
    <div class="content">
      <g:form controller="prototype">
        <table width="100%" cellpadding="0" cellspacing="0">
          <col width="*" />
          <col width="250"/> 
          <tbody>
            <%-- date selection area --%>
            <tr>
              <td style="border-top: solid 1px gray; border-bottom: solid 1px gray;"><b>Date Range</b><br/>
              <g:render template="templates/dates" /></td>
              <td style="border-top: solid 1px gray; border-bottom: solid 1px gray; vertical-align: top;">
                <div class="message"><b>Step 1</b><br/>Choose a date range or drop a result from a previous query into the 'hole'.</div>
              </td>
            <tr>
            <%-- column selection area --%>
            <tr>
              <td>
                <b>Event list</b>
                <div id="hecExtendedCatalogSelector">
                  <table>
                    <tr>
                      <td width="50%" valign="top">
                      <g:each status="status" var="catalog" in="${hecCatalogs}">
                          <table cellpadding="0" cellspacing="1"><tr>
                            <tr>
                              <td width="24" align="center" valign="top">
                                   <%-- create a new column in the selection table --%>
                                  <input class="columnInputs" type="checkbox" name="extra" value="${catalog.value}" title="${catalog.label}" />
                              </td>
                              <td valign="top">
                                  <label>${catalog.label ? catalog.label : catalog.value}</label>
                                  <span id="info_${catalog.value}" class="hecLabelTooltipMe ui-icon ui-icon-info" style="display:inline-block; vertical-align: top;" ></span>
                                  <div class="hecLabelTooltip tooltip_${catalog.value}"><pre style="white-space: pre-wrap">${catalog.description ? catalog.description : 'n/a'}</pre></div>
                              </td>
                            </tr>
                            <g:if test="${(status) == (int)(hecCatalogs.size()/2)}"><%='</table></td><td width="50%" valign="top"><table cellpadding="0" cellspacing="1">'%></g:if>
                          </table>                            
                      </g:each> 
                        </td>
                    </tr>
                  </table>
                </div>
              </td>
              <td style="vertical-align: top;"> 
                <div class="message"><b>Step 2</b><br/>Select the event list(s) you are interested in. <br/>
                Hover with your mouse over the info icons to get more information.</div>
                <div class="message"><b>Step 3</b> (optional)<br/>Now use the advanced parameters below to further qualify your query.</div>
              </td>
            </tr>
            <%-- advanced query --%>
            <tr>
              <td colspan="2">
                <div class="header queryHeader viewerHeader">
                  <h1>Advanced Parameters</h1>
                </div>
                <div id="hecExtendedQueryContent" >
                </div>
                <input name="serviceName" type="hidden" value="HEC"/>
                <input id="whereField" name="where" style="display:none" type="text"/>
              </td>
            </tr>
            <%-- submit button --%>
            <tr>
              <td style="border-top: solid 1px gray; border-bottom: solid 1px gray;">
                <g:submitToRemote name="hecSearchButton" before="beforeHecQuery();" style="float:none;margin-right:50"  action="asyncHecQuery" onLoading="window.workspace.onLoading();" update="[success : 'responseDivision', failure : 'responseDivision']" value="Search" onComplete="afterHecQuery();"/>
              </td>
              <td style="border-top: solid 1px gray; border-bottom: solid 1px gray; vertical-align: top;">
                <div class="message"><b>Step 4</b><br/>Submit query to HELIO</div>
              </td>
            </tr>
          </tbody>
        </table>
      </g:form>
    </div>
  </div>  
  <div id="displayableResult" class="displayable" style="display:block"></div>
</div>