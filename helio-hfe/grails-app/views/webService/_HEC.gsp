<g:form controller="webService">
<fieldset class="results">
  <legend >HEC</legend>

 
   <table>
     <tr>
     <td>
     <label for="from">Start Date(MM/dd/yy)</label>
     </td><td>
     <g:if test="${minDate==null}"><g:set var="minDate" value="01/01/2003" /></g:if>
     <input type="text" id="minDateHEC" name="minDate" value="${minDate}"/>


     </td>
     <td>
       <g:if test="${minTime==null}"><g:set var="minTime" value="00:01" /></g:if>
       Start Time
       </td><td><input type="text" name="minTime" id="minTimeHEC" value="${minTime}" />
     </td>
     </tr>
     <tr>
       <td>
         <label for="to">End Date</label>
         </td><td>
         <g:if test="${maxDate==null}"><g:set var="maxDate" value="01/02/2003" /></g:if>
          <input type="text" id="maxDateHEC" name="maxDate" value="${maxDate}" />
       </td>
       <td>
         <g:if test="${maxTime==null}"><g:set var="maxTime" value="23:55" /></g:if>
         
         End Time
         </td><td><input type="text" name="maxTime" id="maxTimeHEC" value="${maxTime}" />
       </td>
     </tr>
     <tr>
       <td>

         
         
         
         
         

             <g:select name="extra" value="${extra}" from="${[
'goes_xray_flare'
,'halpha_flares_event'
,'sgas_event'
,'yohkoh_flare_list'
,'hessi_flare'
,'kso_flare'
,'eit_list'
,'yohkoh_sxt_trace_list'
,'noaa_proton_event'
,'lasco_cme_cat'
,'lasco_cme_list'
,'bas_magnetic_storms'
,'srs_list'
,'soho_camp'
,'dsd_list'
,'sidc_sunspot_number',
'drao_10cm_flux'
   ]}" />




       </td>
       <td>
         <g:hiddenField name="serviceName" value="HEC" />
         
         <g:actionSubmit action="queryServ" value="Search" />
       </td>
     
     </tr>
     
   
  


</table>


</fieldset>
  </g:form>

 

  