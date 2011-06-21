<div id="actionViewer">
  <div class="viewerHeader">
    <table  style="height:30px;width: 100%;">
      <tr >
        <td width="60">
          <img height="30px" src="${resource(dir:'images/icons/toolbar',file:'ics.png')}" />
        </td>
        <td>
          <h1 style="font-size:2em;font-weight: normal;margin-top: 10px">Instrument Capabilities Service</h1>

        </td>
        <td>

          <div style="float:right;display:none" class="controls custom_button" id="forward">Next</div>
          <div style="float:right;display:none" class="controls custom_button" id="counter" ></div>
          <div style="float:right;display:none" class="controls custom_button" id="backward" >Prev</div>
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
        <input id="extra" name="extra" style="display:none" type="text" value="instrument"/>
        <table width="100%" cellpadding="0" cellspacing="0">
          <col width="*" />
          <col width="250"/> 
          <tbody>
<%-- date selection area --%>
            <tr>
              <td style="border-top: solid 1px gray; border-bottom: solid 1px gray;"><b>Date Range</b><br/>
          <g:render template="templates/dates" />

          <g:render template="templates/ics_instrument" />

          </td>
          <td style="border-top: solid 1px gray; border-bottom: solid 1px gray; vertical-align: top;">
            <div class="message"><b>Step 1</b><br/>Choose a date range or drop a result from a previous query into the 'hole'.</div>
          </td>
          


          <tr>
            <td style="border-top: solid 1px gray; border-bottom: solid 1px gray; vertical-align: top;">
              <b>Result Overview</b>


              <table style="margin-top: 20px">
                <tr>
                  <td colspan="2">

                  </td>
                  <td>
                    <table style="margin-left: 30px">
                      <tr>
                        <td><b>Query Date</b></td>
                        <td>2011/10/10</td>
                      </tr>
                      <tr>
                        <td><b>Amount of Entries</b></td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>




            </td>
          </tr>
          </tbody>
        </table>
      </form>
    </div>
  </div>
  <div id="displayableResult" class="displayable" style="display:block"></div>
</div>