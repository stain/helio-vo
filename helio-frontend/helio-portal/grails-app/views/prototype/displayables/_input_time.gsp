<table>
  <tr>
    <td>
      <div class="resultDroppable"style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
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
            <input size="6" type="text" name="minTime" class="minTime" value="00:00" />
          </td>
          <td align="center" valign="center">
            <input size="12" type="text" class="maxDate" name="maxDate" value="2003-01-03"/>


            <input size="6" type="text" name="maxTime" class="maxTime" value="00:00" />
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

<!--tr>
  <td colspan="2">
    <div class="resultDroppable"style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
      <img id="time_button" "style="margin:0px" src="${resource(dir:'images/helio',file:'circle_destination.png')}" />
    </div>
  </td>
  <td>
    <table class="dateTable" style="display:block">

      <tr class="hideDates">
        <td>
          <label style="text-align:right" for="minDate">From</label>
        </td>
        <td>
          <input type="text" id="minDate" name="minDate" value="2003-01-01"/>
        </td>
        <td>
          <input type="text" name="minTime" id="minTime" value="00:00" />
        </td>
      </tr>
      <tr class="hideDates">
        <td>
          <label style="margin-right:20px" for="maxDate">To</label>
        </td>
        <td>
          <input type="text" id="maxDate" name="maxDate" value="2003-01-03"/>
        </td>
        <td>
          <input type="text" name="maxTime" id="maxTime" value="00:00" />
        </td>
      </tr>

    </table>
  </td>
</tr>
</table--!>