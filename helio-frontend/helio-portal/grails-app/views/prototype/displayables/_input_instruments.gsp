    <table>
      <tr><td></td>
        <td style="padding:10px;"><div><b>Search</b> <input id="input_filter" type="text"></div></td>
      </tr>
      <tr>
        <td style="vertical-align:middle;" >
          <div  class="resultDroppable2" style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
            <img style="margin:0px" src="${resource(dir:'images/helio',file:'circle_destination.png')}" />
          </div>
        </td>
        <td>
          <table id="input_table" class="resultTable">
            <thead>
            <th>Instruments</th>
            </thead>
            <tbody>
            <g:each in="${dpasInstruments}" status="i" var="rows">
              <tr >
                <td internal="${rows.value}">
                  ${rows.label}
                </td>
              </tr>
            </g:each>
            </tbody>
          </table>
        </td>

    <td><ul id="extra_list_form"></ul></td>


      </tr>
    </table>