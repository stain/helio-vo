    <table>
      <tr><td></td>
        <td style="padding:10px;"><div><b>Search</b> <input id="input_filter" type="text"></div></td>
      </tr>
      <tr>
        <td style="vertical-align:middle;" >
          <div  class="resultDroppableEvent" style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
            <img style="margin:11px" src="${resource(dir:'images/helio',file:'circle_event.png')}" />
          </div>
        </td>
        <td>
          <table id="input_table" class="resultTable">
            <thead><tr>
            <th>Event Catalogues</th></tr>
            </thead>
            <tbody>
            <g:each in="${hecCatalogs}" status="i" var="rows">
              <tr >
                <td internal="${rows.value}">
                  ${rows.label}
                </td>
              </tr>
            </g:each>
            </tbody>
          </table>
        </td>

    <td class="candybox"><ul id="extra_list_form"></ul></td>

        
      </tr>
     
    </table>

<table id="input_label_table"  style="display:none;width:100%;margin: 30px 0px">
  <tbody>
    <tr >
        <td>
          <h4 style="border-bottom:2px solid grey">Label Management</h4>
        </td>
      </tr>
      <tr>
        <td style="padding:20px">Label: <input type="text" id="input_form_label" tabindex="1"/>
        Group:<input type="text" disabled="true" id="input_form_group"/> </td>
      </tr>


  </tbody>
</table>