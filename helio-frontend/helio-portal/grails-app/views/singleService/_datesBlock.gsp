<div class="block" id="dateBlock">
  <h3>Select a date and time range to query:</h3>
  <table>
    <tr>
      <td>
        <label for="from">Start Date(yyyy/mm/dd)</label>
        <g:if test="${minDate==null}"><g:set var="minDate" value="2003-01-01" /></g:if>
        <input type="text" id="minDate" name="minDate" value="${minDate}"/>
    </td>
    <td>
        <g:if test="${minTime==null}"><g:set var="minTime" value="00:01" /></g:if>
        Start Time
       <input type="text" name="minTime" id="minTime" value="${minTime}" />
    </td>
    </tr>
    <tr>
      <td align="right">
        <label for="to">End Date</label>

          <g:if test="${maxDate==null}"><g:set var="maxDate" value="2003-01-02" /></g:if>
          <input type="text" id="maxDate" name="maxDate" value="${maxDate}" />
      </td>
      <td align="right">
    <g:if test="${maxTime==null}"><g:set var="maxTime" value="23:55" /></g:if>

    End Time
    <input type="text" name="maxTime" id="maxTime" value="${maxTime}" />
    </td>






    </tr>
  </table>
</div><!--endblock dateBlock-->