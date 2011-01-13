<g:form controller="webService">
<fieldset class="results">
  <legend >ILS</legend>
   <table>
     <tr>
     <td>
     <label for="from">Start Date(MM/dd/yy)</label>
     </td><td>
     <g:if test="${minDate==null}"><g:set var="minDate" value="2003-01-01" /></g:if>
     <input type="text" id="minDateILS" name="minDate" value="${minDate}"/>


     </td>
     <td>
       <g:if test="${minTime==null}"><g:set var="minTime" value="00:01" /></g:if>
       Start Time:</td><td> <input type="text" name="minTime" id="minTimeILS" value="${minTime}" />
     </td>
     </tr>
     <tr>
       <td>
         <label for="to">End Date</label>
         </td><td>
         <g:if test="${maxDate==null}"><g:set var="maxDate" value="2003-01-02" /></g:if>
          <input type="text" id="maxDateILS" name="maxDate" value="${maxDate}" />
       </td>
       <td>
         <g:if test="${maxTime==null}"><g:set var="maxTime" value="23:55" /></g:if>
         End Time: </td><td><input type="text" name="maxTime" id="maxTimeILS" value="${maxTime}" />
       </td>
     </tr>
     <tr>
       <td>
        <g:select name="extra" from="${['trajectories']}" />


       </td>
       <td>
         <g:hiddenField name="serviceName" value="ILS" />
         <g:actionSubmit action="queryServ" value="Search" />
       </td>
     </tr>
</table>
</fieldset>
</g:form>



