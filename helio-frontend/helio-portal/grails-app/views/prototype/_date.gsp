<div id="staticForms">
  <div id="formHeader">
    <img style ="float:left;position:relative;top:10px;left:10px"  src="./images/icons/toolbar/date50.png" alt="Angry face" />
    <h1 style ="float:left;position:relative;left:15px" > Date Range </h1>
  </div>
  <div style="clear:both;padding:50px 0 0 80px;">
<g:form controller="prototype">
    <table>

 <g:render template="dates" />
   
   <tr><td> Catalogue:
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
  </td></tr>
   

 <tr><td>
     
    <g:actionSubmit style="float:none;margin-right:50" action="explorer" value="Search" />
     
     </td></tr>
    </table>
  </g:form>
  </div>
  

</div>
