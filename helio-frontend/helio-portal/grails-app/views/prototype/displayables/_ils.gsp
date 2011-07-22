<div id="actionViewer">
  <div class="viewerHeader">
    <table  style="height:30px;width: 100%;">
      <tr >
        <td width="60">
          
        </td>
        <td>
          <h1 style="font-size:2em;font-weight: normal;margin-top: 10px">Instrument Location Service</h1>
          
        </td>
        <td>
    
        </td>
      </tr>
    </table>

  </div>

  <div class="module ">
    <div class="header queryHeader viewerHeader">
      <h1>Query Form</h1>
    </div>
    <div class="content">
      <form id="actionViewerForm"  action="asyncQuery" method="POST">
         
         <input id="service_name" name="serviceName" type="hidden" value="ILS"/>
         <input id="task_name" name="taskName" type="hidden" value="ils"/>
                <input id="extra" name="extra" style="display:none" type="text" value="trajectories"/>
        <table width="100%" cellpadding="0" cellspacing="0">
          <col width="*" />
          <col width="250"/> 
          <tbody>
            <%-- date selection area --%>
            <tr>
              <td style="border-top: solid 1px gray; border-bottom: solid 1px gray;"><b>Date Range</b><br/>
                <g:render template="templates/dates" />
                
                <g:render template="templates/ils_trajectories" />
              </td>
              <td style="border-top: solid 1px gray; border-bottom: solid 1px gray; vertical-align: top;">
                <div class="message"><b>Step 1</b><br/>Choose a date range or drop a result from a previous query into the 'hole'.</div>
              </td>
            <tr>
            <%-- column selection area >
            <tr>
              <td valign="top">
                <b>Data table</b>
                <div class="catalogueSelector">
                  <table>
                    <tr>
                      <td width="24" align="center" valign="top">
                        <%-- create a new column in the selection table>
                        <input type="checkbox" name="extra" value="trajectories" title="Trajectories" checked="checked"/>
                      </td>
                      <td valign="top">
                        <label>Trajectories</label>
                        <span id="info_trajectories" class="labelTooltipMe ui-icon ui-icon-info" style="display:inline-block; vertical-align: top;" ></span>
                        <div class="hecLabelTooltip tooltip_trajectories"><pre style="white-space: pre-wrap">Search for trajectoriess.</pre></div>
                      </td>
                    </tr>
                  </table>
                </div>
              </td>
              <td style="vertical-align: top;"> 
                <div class="message"><b>Step 2</b><br/>Currently only trajectories are enabled with ILS. More catalogs may be included later.</div>
                <div class="message"><b>Step 3</b> (optional)<br/>Now use the advanced parameters below to further qualify your query.</div>
              </td>
            </tr>
            <%-- advanced query >
            <tr>
              <td colspan="2">
                <div class="header queryHeader viewerHeader">
                  <h1>Advanced Parameters</h1>
                </div>
                <div id="advancedParams"></div>
               
              </td>
            </tr>
            <%-- submit button >
            <tr>
              <td style="border-top: solid 1px gray; border-bottom: solid 1px gray;">
                
                <!--g:submitToRemote class="custom_button" before="fnBeforeQuery();" style="float:none;margin-right:50"  action="asyncQuery" onLoading="window.workspace.onLoading();" update="responseDivision" value="Search" onComplete="fnOnComplete();"/-->
              </td>
              <td style="border-top: solid 1px gray; border-bottom: solid 1px gray; vertical-align: top;">
                <div class="message"><b>Step 4</b><br/>Submit query to HELIO</div>
              </td>
            </tr--%>
          </tbody>
        </table>
      </form>
    </div>
  </div>
  <div id="displayableResult" class="displayable" style="display:block"></div>
</div>