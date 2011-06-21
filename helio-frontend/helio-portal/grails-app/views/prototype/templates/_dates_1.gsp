<table>
  <tr>
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
</table>