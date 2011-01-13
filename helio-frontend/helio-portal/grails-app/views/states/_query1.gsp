


<div id ="questions" style="padding-bottom:30px">
  <h1>1. Select a Time Range</h1>
  <g:form controller="states" action="workflow1" name="data" method="post" >
<g:actionSubmit action="workflow1" value="Next Step" />
<g:hiddenField name="serviceName" value="HEC" />
    <table>
      <tr>
        <td>
           <label for="startDate">Start Time:(mm/dd/yy)</label>
        </td>
         <td>
           <g:if test="${minDate==null}"><g:set var="minDate" value="2003-01-01" /></g:if>
             <input type="text" id="minDateHEC" name="minDate" value="${minDate}"/>
              <g:if test="${minTime==null}"><g:set var="minTime" value="00:01" /></g:if>
       Start Time: <input type="text" name="minTime" id="minTimeHEC" value="${minTime}" />
        </td>
      </tr>
      <tr>
        <td>
             <label for="endDate">End Time:(dd/mm/yy)</label>
        </td>
         <td>
            <g:if test="${maxDate==null}"><g:set var="maxDate" value="2003-01-02" /></g:if>
          <input type="text" id="maxDateHEC" name="maxDate" value="${maxDate}" />
           <g:if test="${maxTime==null}"><g:set var="maxTime" value="23:55" /></g:if>
         End Time: <input type="text" name="maxTime" id="maxTimeHEC" value="${maxTime}" />
        </td>
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
      </tr>
    </table>
    
         
   </g:form>
</div>

