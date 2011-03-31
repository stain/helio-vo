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
          <div style="float:right;" class="controls" id="delete">X</div>
          <div style="float:right;" class="controls" id="forward">Next</div>

          <div style="float:right;" class="controls" id="counter" ></div>

          <div style="float:right;" class="controls" id="backward" >Prev</div>
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

    <g:submitToRemote before="fnBeforeQuery();" style="float:none;margin-right:50"  action="asyncQuery" onLoading="window.workspace.onLoading();" update="responseDivision" value="Search" onComplete="fnOnComplete();"/>
    <g:submitToRemote style="float:none;margin-right:50" action="asyncGetColumns"  onComplete="fnOnCompleteGetColumns();" update="hecResponse" value="Advanced Search" />

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





