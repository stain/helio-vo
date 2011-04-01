<div id="actionViewer">
  <div class="viewerHeader">
    <table  style="height:30px;width: 100%;">
      <tr >
        <td>
          <img height="30px" src="${resource(dir:'images/icons/toolbar',file:'hec.png')}" />
        </td>
        <td>
          <h1 style="font-size:2em;font-weight: normal;margin-top: 10px">Event Catalog Service</h1>
          Label: <input style="margin-top:5px;" id="label" type="text"/>
        </td>
        <td>
          <div style="float:right;" class="controls" id="delete">X</div>
          <div style="float:right;" class="controls" id="forward">Next</div>
          <div style="float:right;" class="controls" id="counter" ></div>
          <div style="float:right;" class="controls" id="backward" >Prev</div>
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
              <td style="border-top: solid 1px gray; border-bottom: solid 1px gray;"><b>Data Range</b><br/>
              <g:render template="templates/dates" /></td>
              <td style="border-top: solid 1px gray; border-bottom: solid 1px gray; vertical-align: top;">
                <div class="message"><b>Step 1</b><br/>Choose a date range or drop a result from a previous query into the 'hole'.</div>
              </td>
            <tr>
            <%-- column selection area --%>
            <tr>
              <td>
                <table>
                  <tr>
                    <td colspan="2"><b>Event list</b></td>
                  </tr>
                  <tr>
                    <td colspan="2">
                      <div id="hecExtendedCatalogSelector">
                        <table cellpadding="0" cellspacing="1"><tr>
                        <g:each status="status" var="catalog" in="${hecCatalogs}">
                          <td width="24" align="center">
                               <%-- create a new column in the selection table --%>
                              <input class="columnInputs" type="checkbox" name="extra" value="${catalog.value}" title="${catalog.label}" />
                          </td>
                          <td class="hecLabelTooltipMe" title="&lt;pre style=&quot;white-space: pre-wrap;&quot;&gt;${catalog.description?.encodeAsHTML()}&lt;/pre&gt;">
                              <label>${catalog.label ? catalog.label : catalog.value}</label>
                          </td>
                          <g:if test="${(status+1) % 2 == 0}"><%="</tr><tr>"%></g:if>
                        </g:each> 
                        </tr></table>
                      </div>
                    </td>
                  </tr>
                </table>
              </td>
              <td style="vertical-align: top;"> 
                <div class="message"><b>Step 2</b><br/>Select the event list(s) you are interested in. <br/>
                Hover with your mouse over the event lists to get more information.</div>
                <div class="message"><b>Step 3</b> (optional)<br/>Now use the advanced parameters below to further qualify your query.</div>
              </td>
            </tr>
            <%-- advanced query --%>
            <tr>
              <td colspan="2">
                <div class="header queryHeader viewerHeader">
                  <h1>Advanced Parameters</h1>
                </div>
                <div id="hecExtendedQueryContent" class="content collapsed">
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