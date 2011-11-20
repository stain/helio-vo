<div id="actionViewer">
  <div class="viewerHeader">
    <table  style="height:30px;width: 100%;">
      <tr>
        <td>
          <h1>Data Mining</h1>
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
        <input type="hidden" id="service_name" name="serviceName" value="DES"/>
        <input type="hidden" id="task_name" name="taskName" value="task_datamining"/>
        <input type="hidden" id="task_label" value="Events"/>
        <table width="100%" cellpadding="0" cellspacing="0">
          <col width="*" />
          <col width="250"/>
          <tbody>
   <%-- date selection area --%>
            <tr>
              <td style="border-top: solid 1px gray;">
                <b>Date Selection</b><br/>
                <g:render template="templates/dates" />
              </td>
              <td style="border-top: solid 1px gray; vertical-align: top;">
                <div class="message"><b>Step 1</b><br/>Click on the 'Select' button to define the time range/s of interest.</div>
              </td>
            </tr>
  <%-- Block Construction Area --%>
            <tr>
              <td colspan="2" style="border-top: solid 1px gray;">
                <b>Argument Selection</b>
              </td>
            </tr>
            <tr>
              <td>
                <table>
                  <tr>
                    <td style="vertical-align:middle;" >
                      <div   class="resultDroppableInst" style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
                        <img id="block_drop" style="margin:11px" src="${resource(dir:'images/helio',file:'circle_block_grey.png')}" />
                      </div>
                    </td>
                    <td id="block_area" class="candybox"></td>
                    <td>
                        <div class="clear_input_summary custom_button">Clear</div>
                    </td>
                  </tr>
                  <tr align="center">
                    <td colspan="3"><div class="custom_button" id="block_button">Select</div></td>
                  </tr>
                </table>
              </td>
              <td valign="top">
                <div class="message"><b>Step 2</b><br/>Click on the 'Select' button to define the arguments for the query. </div>
              </td>
            </tr>
<%-- result overview --%>
            <tr>
              <td>
                <table id="result_overview" style="display:none" width="100%" cellpadding="0" cellspacing="0">
                  <col width="*" />
                  <col width="250"/>
                  <tbody>
                    <tr>
                      <td colspan="2" style="border-top: solid 1px gray;">
                        <b>Result Overview</b>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <table style="margin-bottom: 10px;">
                          <tr>
                            <td style="vertical-align:middle;" >
                              <div   class="resultDroppable4" style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
                                <img id="result_drop" class ="drop_able" style="margin:0px" src="${resource(dir:'images/helio',file:'result.png')}" />
                              </div>
                            </td>
                            <td id="result_area"></td>
                          </tr>
                          <tr align="center">
                            <td colspan="2"><div class="custom_button" id="result_button">Display</div></td>
                          </tr>
                        </table>
                      </td>
                      <td valign="top">
                        <div class="message"><b>Step 3</b><br/>Click on the 'Display' button once you are ready to proceed</div>
                      </td>
                    </tr>
                  </tbody>
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