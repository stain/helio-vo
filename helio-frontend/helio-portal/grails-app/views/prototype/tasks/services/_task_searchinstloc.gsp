<div id="actionViewer">
  <div class="viewerHeader">
    <table  style="height:30px;width: 100%;">
      <tr >
        <td>
        </td>
        <td>
          <h1 >Search Instrument by Location</h1>
        </td>
        <td>
        </td>
      </tr>
    </table>

  </div>

<%-- Query Form --%>
  <div  id="query_form" class="module ">
    <div class="header queryHeader viewerHeader">
      <h1>Query Form</h1>
    </div>
    <div class="content">
      <form id="actionViewerForm"  action="asyncQuery" method="POST">

        <input id="service_name" name="serviceName" type="hidden" value="ILS"/>
        <input id="task_name" name="taskName" type="hidden" value="task_searchInstLoc"/>
        <input id="task_label" type="hidden" value="Instrument"/>

        <input id="extra" name="extra" style="display:none" type="text" value="trajectories"/>
        <table width="100%" cellpadding="0" cellspacing="0">
          <col width="*" />
          <col width="250"/> 
          <tbody>
<%-- date selection area --%>
            <tr>
              <td style="border-top: solid 1px gray; "><b>Date Range</b><br/>
          <g:render template="templates/dates" />

          <g:render template="templates/ils_trajectories" />
          </td>
          <td style="border-top: solid 1px gray; vertical-align: top;">
            <div class="message"><b>Step 1</b><br/>Click on the 'Select' button to define the time range/s of interest.</div>
          </td>
          <tr>
          <tr >
          <table id="result_overview" style="display:none" width="100%" cellpadding="0" cellspacing="0">
            <col width="*" />
            <col width="250"/>
            <tbody>
              <tr >
                <td colspan="2" style="border-top: solid 1px gray;">
                  <b>Result Overview</b>

                </td>
              </tr>
              <tr>
                <td>
                  <table style="margin-bottom: 10px;">
                    <tr>
                      <td style="vertical-align:middle;" >
                        <div class="resultDroppable4" style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
                          <img id="result_drop" class ="drop_able" style="margin:0px" src="${resource(dir:'images/helio',file:'result.png')}" />
                        </div>
                      </td>

                      <td><ul id="result_area">
                          <li>Number of tables: </li>
                          <li>Number of entries: </li>
                          <li>TimeStamp: </li>
                        </ul></td>


                    </tr>
                    <tr align="center"><td><div class="custom_button" id="result_button">Display</div></td></tr>
                  </table>
                </td>
                <td valign="top">
                  <div class="message"><b>Step 2</b><br/>Click on the 'Display' button once you are ready to proceed</div>
                </td>
              </tr>
          </table>
          </tbody>
        </table>
      </form>
    </div>
  </div>
  <div id="displayableResult" class="displayable" style="display:block"></div>
</div>