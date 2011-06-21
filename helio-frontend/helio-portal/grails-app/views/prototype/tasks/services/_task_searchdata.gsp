

<div id="actionViewer">
  <div class="viewerHeader">
    <table  style="height:30px;width: 100%;">
      <tr >
        <td width="60">
           <!--img height="30px" src="${resource(dir:'images/icons/toolbar',file:'hec.png')}" /!-->
        </td>
        <td>
          <h1 style="font-size:2em;font-weight: normal;margin-top: 10px">Search Data</h1>

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
        <input id="service_name" name="serviceName" type="hidden" value="DPAS"/>
        <input id="service_name" name="taskName" type="hidden" value="searchData"/>
        <table width="100%" cellpadding="0" cellspacing="0">
          <col width="*" />
          <col width="250"/>
          <tbody>
<%-- date selection area --%>
            <tr>
              <td style="border-top: solid 1px gray;"><b>Date Selection</b><br/>
                <g:render template="templates/dates" />
              </td>
              <td style="border-top: solid 1px gray; vertical-align: top;">
                <div class="message"><b>Step 1</b><br/>Click on the 'Select' button to define the time range/s of interest.</div>
              </td>
            <tr>
            <%-- instrument selection area --%>
            <tr>
              <td colspan="2" style="border-top: solid 1px gray;">
                <b>Instrument Selection</b>

              </td>
            </tr>
            <tr>
              <td>
                <table>
                  <tr>
                    <td style="vertical-align:middle;" >
                      <div  id="instruments_drop" class="resultDroppable2" style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
                          <img style="margin:0px" src="${resource(dir:'images/helio',file:'circle_destination.png')}" />
                      </div>
                    </td>
                    <td><ul id="extra_list"></ul></td>
                  <td><div class="custom_button" id="instruments_button">Select</div></td>

                  </tr>
                </table>
              </td>
              <td valign="top">
                <div class="message"><b>Step 2</b><br/>Click on the 'Select' button to define the instrument/s of interest</div>
              </td>
            </tr>
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