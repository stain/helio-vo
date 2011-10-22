<table style="height:50%;">
  <tr>
    <td>
      <div class="resultDroppable"style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
        <input style="display:none" type="text" id="focus_steal"/>
        <img id="time_button"  style="margin:11px" src="${resource(dir:'images/helio',file:'circle_time.png')}" />
      </div>
    </td>
    <td>
      <table  width="500px" cellpadding="0" cellspacing="0">
        <thead>
          <tr>
            <th>
            </th>
            <th align="center" valign="bottom">
              Start Date
            </th>
            <th  align="center" >
              End Date
            </th>


          </tr>
        </thead>

        <tbody id="input_time_range_list">

          <tr id="input_time_range">
            <td align="center" valign="center">
              Range 1
            </td>
            <td align="center" valign="center">
              <input size="12" type="text" class="minDate" name="minDate" value="2003-01-01"/>
              <input size="8" type="text" name="minTime" class="minTime" value="00:00:00" />
            </td>
            <td align="center" valign="center">
              <input size="12" type="text" class="maxDate" name="maxDate" value="2003-01-03"/>


              <input size="8" type="text" name="maxTime" class="maxTime" value="00:00:00" />
            </td>
          </tr>

        </tbody>



      </table>
    </td>
  </tr>
  <tr>
    <td></td>
    <td> <div  id="input_time_range_button" class="custom_button">+ Range</div></td>
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
        <%--Group:<input type="text" disabled="true" id="input_form_group"/>--%> </td>
      </tr>


  </tbody>
</table>