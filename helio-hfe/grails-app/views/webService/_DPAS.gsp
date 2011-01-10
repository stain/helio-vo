<g:form controller="webService">
<fieldset class="results">
  <legend >DPAS</legend>
   <table>
     <tr>
     <td>
     <label for="from">Start Date(MM/dd/yy)</label>
     </td><td>
     <g:if test="${minDate==null}"><g:set var="minDate" value="01/01/2003" /></g:if>
     <input type="text" id="minDateDPAS" name="minDate" value="${minDate}"/>


     </td>
     <td>
       <g:if test="${minTime==null}"><g:set var="minTime" value="00:01" /></g:if>
       Start Time: </td><td><input type="text" name="minTime" id="minTimeDPAS" value="${minTime}" />
     </td>
     </tr>
     <tr>
       <td>
         <label for="to">End Date</label>
         </td><td>
         <g:if test="${maxDate==null}"><g:set var="maxDate" value="01/02/2003" /></g:if>
          <input type="text" id="maxDateDPAS" name="maxDate" value="${maxDate}" />
       </td>
       <td>
         <g:if test="${maxTime==null}"><g:set var="maxTime" value="23:55" /></g:if>
         End Time: </td><td><input type="text" name="maxTime" id="maxTimeDPAS" value="${maxTime}" />
       </td>
     </tr>
     <tr>
       <td>
       <g:select name="extra" value="${extra}" from="${['512-CHANNEL MAGNETOGRAPH'
,'60-FT SHG'
,'BCS'
,'BIG BEAR'
,'CDS'
,'CELIAS'
,'CERRO TOLOLO'
,'CFDT1'
,'CFDT2'
,'CHP'
,'COSTEP'
,'DPM'
,'EIT'
,'EL TEIDE'
,'ERNE'
,'GOLF'
,'HXT'
,'IMPACT'
,'LASCO'
,'LEARMONTH'
,'MAUNA LOA'
,'MDI'
,'MK4'
,'MOF/60'
,'MOTH'
,'O-SPAN'
,'OVSA'
,'PLASTIC'
,'RHESSI'
,'SECCHI'
,'SOLAR FTS SPECTROMETER'
,'SPECTROHELIOGRAPH'
,'SPECTROMAGNETOGRAPH'
,'SUMER'
,'SWAN'
,'SWAVES'
,'SXI-0'
,'SXT'
,'TENERIFE'
,'UDAIPUR'
,'UVCS'
,'VIRGO'
,'VSM'
,'WBS'
,'XRT']}"/>



       </td>
       <td>
         <g:hiddenField name="serviceName" value="DPAS" />
         <g:actionSubmit action="queryServ" value="Search" />
       </td>
     </tr>
</table>
</fieldset>
</g:form>



