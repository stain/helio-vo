<div id="staticForms">
  <div id="formHeader">
    <div class="deleteViewer">X</div>
    <img style ="float:left;position:relative;top:10px;left:10px" height="30px" src="${resource(dir:'images/icons/toolbar',file:'event.png')}" />
    <h1 style ="float:left;position:relative;left:15px" > Event Search </h1>
  </div>
  <div style="clear:both;padding:50px 0 0 80px;">
<g:form controller="prototype">
    
<g:render template="dates" />
<table style="">
<tr><td style="text-align:right"> Catalogue:</td><td> <g:select class="catalogueSelector" name="extra" size="10" MULTIPLE="yes" value="hi_cme_list" from="${[
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
   ].sort()}" />
  </td><td><img class="tooltipme" title="Use Ctrl/Cmd-Click to select multiple entries" style ="position:relative;right:40px;" height="20px" src="${resource(dir:'images/icons',file:'info.png')}" /></td>
 
</tr>
 <tr><td></td><td>
<g:hiddenField name="serviceName" value="HEC" />
<input id="whereField" name="where" style="display:none" type="text"/>
    
    <g:submitToRemote before="fnBeforeQuery();" style="float:none;margin-right:50"  action="asyncQuery" onLoading="fnOnLoading();" update="responseDivision" value="Search" onComplete="fnOnComplete();"/>
    <g:submitToRemote style="float:none;margin-right:50" action="asyncGetColumns"  onComplete="fnOnCompleteGetColumns();" update="hecResponse" value="Advanced Search" />
    
     </td></tr>
    </table>
</g:form>
  <div id="hecResponse" class="columnInputs" >
  </div>
  
  </div>


</div>
