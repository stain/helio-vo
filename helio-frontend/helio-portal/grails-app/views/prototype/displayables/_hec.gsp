<div id="actionViewer">
  <div class="viewerHeader">
    <table  style="height:30px;width: 100%;">
      <tr >
        <td>
          <img height="30px" src="${resource(dir:'images/icons/toolbar',file:'hec.png')}" />
        </td>
        <td>
          <h1 style="font-size:2em;font-weight: normal;margin-top: 10px">Event Search</h1>
          Label: <input style="margin-top:5px;" id="label" type="text"/>
        </td>
        <td>
         <div style="float:right;" class="controls custom_button" id="delete">X</div>
          <div style="float:right;display:none" class="controls custom_button" id="forward">Next</div>
          <div style="float:right;display:none" class="controls custom_button" id="counter" ></div>
          <div style="float:right;display:none" class="controls custom_button" id="backward" >Prev</div>
        </td>
      </tr>
    </table>

  </div>

  <div class="module ">
    <div class="header queryHeader viewerHeader">
      <h1>Query Form</h1>
    </div>
    <div class="content">


<g:form controller="prototype">

<g:render template="templates/dates" />
<table style="">
<tr><td style="text-align:right"> Catalogue:</td><td> <g:select class="catalogueSelector" name="extra" size="10" MULTIPLE="yes" value="goes_sxr_flare" from="${[
'goes_sxr_flare',
'ngdc_halpha_flare',
'noaa_energetic_event',
'stereo_euvi_event',
'soho_lasco_cme',
'stereo_hi_cme',
'goes_proton_event',
'cme_forbush_event',
'aad_gle',
'ngdc_aastar_storm',
'ngdc_apstar_storm',
'ngdc_ssc',
'wind_mfi_mag_cloud',
'wind_mfi_ip_shock',
'soho_pm_ip_shock',
'istp_sw_event',
'stereo_hi_sw_transient',
'ulysses_swoops_icme',
'wind_stereo_ii_iv_radioburst',
'wind_typeii_soho_cme',
'yohkoh_hxr_flare',
'rhessi_hxr_flare',
'kso_halpha_flare',
'tsrs_solar_radio_event',
'soho_eit_wave_transient',
'wind_mfi_bs_crossing_time',
'wind_sw_crossing_time',
'imp8_sw_crossing_time',
'cactus_soho_cme',
'cactus_stereoa_cme',
'cactus_stereob_cme',
'cactus_soho_flow',
'cactus_stereoa_flow',
'cactus_stereob_flow',
'ulysses_grb_xray_flare',
'halo_cme_flare_magnetic_storm',
'noaa_active_region_summary',
'noaa_daily_solar_data']}" />
  </td><td><img class="tooltipme" title="Use Ctrl/Cmd-Click to select multiple entries" style ="position:relative;right:40px;" height="20px" src="${resource(dir:'images/icons',file:'info.png')}" /></td>

</tr>
 <tr><td></td><td>
<g:hiddenField name="serviceName" value="HEC" />
<input id="whereField" name="where" style="display:none" type="text"/>

    <g:submitToRemote class="custom_button" before="fnBeforeQuery();" style="float:none;margin-right:50"  action="asyncQuery" onLoading="window.workspace.onLoading();" update="responseDivision" value="Search" onComplete="fnOnComplete();"/>
    <g:submitToRemote class="custom_button" style="float:none;margin-right:50" action="asyncGetColumns"  onComplete="fnOnCompleteGetColumns();" update="hecResponse" value="Advanced Search" />

     </td></tr>
    </table>
<div id="hecResponse" class="columnInputs" >
  </div>
</g:form>

</div>
</div>

 <div id="displayableResult" class="displayable" style="display:block">

    </div>




</div>





