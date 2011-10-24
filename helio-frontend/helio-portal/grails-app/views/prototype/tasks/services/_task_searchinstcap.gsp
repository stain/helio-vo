

<div id="actionViewer">
  <div class="viewerHeader">
    <table  style="height:30px;width: 100%;">
      <tr >
        <td>
           <!--img height="30px" src="${resource(dir:'images/icons/toolbar',file:'hec.png')}" /!-->
        </td>
        <td>
          <h1 >Search Instruments by Capability</h1>

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
        <input id="service_name" name="serviceName" type="hidden" value="ICS"/>
        <input id="task_name" name="taskName" type="hidden" value="task_searchInstCap"/>
        <input id="task_label" type="hidden" value="Instrument"/>
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
                        <li>Number of tables: 2</li>
                        <li>Number of entries: 4</li>
                        <li>TimeStamp: 13/12/2005</li>
                      </ul></td>


                  </tr>
                  <tr align="center"><td><div class="custom_button" id="result_button">Display</div></td></tr>
                </table>
              </td>
              <td valign="top">
                <div class="message"><b>Step 2</b><br/>Click on the 'Display' button once you are ready to proceed</div>
              </td>
            </tr>
          </tbody>
          </table>
          </tr>
            </tbody>
        </table>
      </form>
    </div>
  </div>
  <div id="displayableResult" class="displayable" style="display:block"></div>
</div>