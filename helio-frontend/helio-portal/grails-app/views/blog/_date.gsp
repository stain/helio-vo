<div id="staticForms">
  <div id="formHeader">
    <img style ="float:left;position:relative;top:10px;left:10px"  src="./images/icons/toolbar/date50.png" alt="Angry face" />
    <h1 style ="float:left;position:relative;left:15px" > Date Range </h1>
  </div>
  <div style="clear:both;padding:50px 0 0 80px;">

    <table>

    <tr><td>Start Date: <input type="text" id="minDate" name="minDate" value="${minDate}"/> </td></tr>
    <tr><td>Start Time: <input type="text" name="minTime" id="minTime" value="${minTime}" /></td></tr>
    <tr><td>End Date: <input type="text" id="maxDate" name="maxDate" value="${maxDate}" /> </td></tr>
    <tr><td>End Time: <input type="text" name="maxTime" id="maxTime" value="${maxTime}" /> </td></tr>
    
   
   <tr><td> Catalogue: <g:select style ="float:none;display:inline;" name="extra" value="${extra}" from="${[
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
   ]}" />  </td></tr>
   

 <tr><td>
     <g:form controller="webService">
    <g:actionSubmit style="float:none;margin-right:50" action="queryServ" value="Search" />
     </g:form>
     </td></tr>
    </table>
  </div>
  

</div>
