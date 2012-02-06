<%-- 
Template to display the instrument selection dialog.
Expected variables:
 * map taskDescriptor
 * InstrumentParam instrument: the instrument parameter
 * Map taskDescriptor: descriptor that describes the task
--%><div class="input-dialog" id="instrumentDialog" style="display:none">
  <g:set var="paramDescriptor" value="${taskDescriptor.inputParams.instruments}" />
  <%--input dialog for instruments --%>
  <table class="dialog_table">
    <tr valign="top" align="left" height="*">
      <td valign="top" width="90px">
        <div class="paramDroppable" style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
          <img style="margin:11px" src="${resource(dir:'images/helio',file:'circle_Instrument.png')}" />
        </div>
      </td>
      <td>
        <p style="margin-bottom: 7px">Select the instrument of interest.</p>
      </td>
    </tr>
    <tr>
      <td colspan="2" style="height: 20px;">
        <div style="float:left; display:block; font-size: 0.8em;"><b>Search</b> <input id="input_filter" type="text" title="Enter a free search text"></div>
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <table style="width:750">
          <tr>
            <td width="500" valign="top">
              <table id="selectInstrument" class="resultTable" >
                <thead>
                  <tr>
                    <th>Label</th>
                    <th>Internal Name</th>
                  </tr>
                </thead>
                <tbody>
                  <g:each in="${taskDescriptor.inputParams.instruments.instruments.selectionDescriptor}" status="i" var="rows">
                    <tr id="selectInstrument_row_${i}">
                      <td>
                        ${rows.label}
                      </td>
                      <td>
                        ${rows.value}
                      </td>
                    </tr>
                  </g:each>
                </tbody>
            </table>
            </td>
            <td width="250" valign="top">
                <div id="summaryInstrument" class="candybox dialog_selection_area"></div>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <%-- label management --%>
    <tr valign="top" align="left" height="20" >
      <td colspan="2" style="padding:5px; border-top:2px solid grey" title="Assign a name to the current instrument List.">Name: <input tabindex="-1" id="nameInstrument" name="nameInstrument" type="text" tabindex="1" value="${instrument.name}"/>
      <div class="message" style="display:inline; float:right; margin:0px; padding:3px">Provide a name to add this instrument list to your <i>Data Cart</i>.</div>
    </tr>
  </table>
</div>