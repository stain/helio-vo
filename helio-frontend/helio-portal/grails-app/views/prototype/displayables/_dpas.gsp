<div id="actionViewer">
  <div class="viewerHeader">
    <table  style="height:30px;width: 100%;">
      <tr >
        <td width="60">
          <img height="30px" src="${resource(dir:'images/icons/toolbar',file:'dpas.png')}" />
        </td>
        <td>
          <h1 style="font-size:2em;font-weight: normal;margin-top: 10px">Data Provider Access Service</h1>
          
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
        <table width="100%" cellpadding="0" cellspacing="0">
          <col width="*" />
          <col width="250"/> 
          <tbody>
            <%-- date selection area --%>
            <tr>
              <td style="border-top: solid 1px gray;"><b>Date Range</b><br/>
                <g:render template="templates/dates" />
              </td>
              <td style="border-top: solid 1px gray; vertical-align: top;">
                <div class="message"><b>Step 1</b><br/>Choose a date range or drop a result from a previous query into the 'hole'.</div>
              </td>
            <tr>
            <%-- instrument selection area --%>
            <tr>
              <td colspan="2" style="border-top: solid 1px gray;">
                <b>Instrument selection</b>
                
              </td>
            </tr>
            <tr>
              <td>
                <table>
                  <tr>
                    <td style="vertical-align:middle;" >
                      <div class="resultDroppable2" style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
                          <img style="margin:0px" src="${resource(dir:'images/helio',file:'circle_destination.png')}" />
                      </div>
                    </td>
                    <td>
                      
                      
                    </td>
                    <td id="extra">
                    
                    </td><td><div id="instrument_button" class="custom_button">Select</div></td>
                  </tr>
                </table>
              </td>
              <td valign="top">
                <div class="message"><b>Step 2</b><br/>Manually choose a set of instruments or drop a result from a previous query into the 'hole'.</div>
              </td>
            </tr>
            <%-- submit button --%>
            <tr>
              <td style="border-top: solid 1px gray; border-bottom: solid 1px gray;">
                <g:hiddenField name="serviceName"  value="DPAS" />
                
                <!--g:submitToRemote class="custom_button" before="fnBeforeQuery();" style="float:none;margin-right:50"  action="asyncQuery" onLoading="window.workspace.onLoading();" update="responseDivision" value="Search" onComplete="fnOnComplete();"/-->
              </td>
              <td style="border-top: solid 1px gray; border-bottom: solid 1px gray; vertical-align: top;">
                
              </td>
            </tr> 
          </tbody>
        </table>
      </form>
    </div>
  </div>
  <div id="displayableResult" class="displayable" style="display:block"></div>
</div>