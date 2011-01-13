<g:form controller="webService">
<fieldset class="results">
  <legend >HEC</legend>

 
   <table>
     <tr>
     <td>
     <label for="from">Start Date(yy/mm/dd)</label>
     </td><td>
     <g:if test="${minDate==null}"><g:set var="minDate" value="2003-01-01" /></g:if>
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
         <g:if test="${maxDate==null}"><g:set var="maxDate" value="2003-01-02" /></g:if>
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
'hi_cme_list'
,'bas_magnetic_storms'
,'wind_imf_bow_shock'
,'yohkoh_flare_list'
,'dsd_list'
,'hi_event'
,'goes_xray_flare'
,'drao_10cm_flux'
,'apstar_list'
,'lasco_cme_list'
,'ssc_list'
,'kso_flare'
,'forbush_list'
,'gle_list'
,'srs_list'
,'solar_wind_event'
,'soho_camp'
,'noaa_proton_event'
,'hessi_flare'
,'yohkoh_sxt_trace_list'
,'halpha_flares_event'
,'sgas_event'
,'wind_imf_mag_cloud'
,'lasco_cme_cat'
,'eit_list'
,'aastar_list'
,'sidc_sunspot_number'
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

 

  