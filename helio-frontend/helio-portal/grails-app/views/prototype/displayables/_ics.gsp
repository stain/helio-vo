

<div id="actionViewer">
  <div class="viewerHeader">
    <table  style="height:30px;width: 100%;">
      <tr >
        <td width="60">
           <!--img height="30px" src="${resource(dir:'images/icons/toolbar',file:'hec.png')}" /!-->
        </td>
        <td>
          <h1 style="font-size:2em;font-weight: normal;margin-top: 10px">Instrument Capabilities Service</h1>

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
             <input id="service_name" name="serviceName" type="hidden" value="ICS"/>
        <input id="task_name" name="taskName" type="hidden" value="searchInstruments"/>
        <input id="extra" name="extra" style="display:none" type="text" value="instrument"/>
        <table width="100%" cellpadding="0" cellspacing="0">
          <col width="*" />
          <col width="250"/>
          <tbody>
            <%-- date selection area --%>
            <tr>
              <td style="border-top: solid 1px gray;"><b>Date Selection</b><br/>
                <g:render template="templates/dates" />
                <g:render template="templates/ics_instrument" />
              </td>
              <td style="border-top: solid 1px gray; vertical-align: top;">
                <div class="message"><b>Step 1</b><br/>Click on the 'Select' button to define the time range/s of interest.</div>
              </td>
            <tr>

            <%-- submit button --%>
            <!--tr>
              <td style="border-top: solid 1px gray; border-bottom: solid 1px gray;">
                <input id="service_name" name="serviceName" type="hidden" value="HEC"/>


              </td>
              <td style="border-top: solid 1px gray; border-bottom: solid 1px gray; vertical-align: top;">

              </td>
            </tr!-->
          </tbody>
        </table>
      </form>
    </div>
  </div>
  <div id="displayableResult" class="displayable" style="display:block"></div>
</div>