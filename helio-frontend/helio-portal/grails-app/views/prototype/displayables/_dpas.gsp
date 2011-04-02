<div id="actionViewer">
  <div class="viewerHeader">
    <table  style="height:30px;width: 100%;">
      <tr >
        <td>
          <img height="30px" src="${resource(dir:'images/icons/toolbar',file:'dpas.png')}" />
        </td>
        <td>
          <h1 style="font-size:2em;font-weight: normal;margin-top: 10px">Data Provider Access Service</h1>
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
<div class="resultDroppable2"style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
<img style="margin:0px" src="${resource(dir:'images/helio',file:'circle_destination.png')}" />
</div>


   <table>

   <tr><td style="text-align:right"> Provider:</td><td> <g:select id="instArea" class="catalogueSelector" name="extra" size="10" MULTIPLE="yes" value="SOHO__CDS" from="${[
'SOHO__EIT'
,'SOHO__CDS'
,'SOHO__SUMER'
,'SOHO__UVCS'
,'SOHO__LASCO'
,'SOHO__SWAN'
,'SOHO__MDI'
,'SOHO__MDI'
,'SOHO__GOLF'
,'SOHO__VIRGO'
,'SOHO__CELIAS'
,'SOHO__COSTEP'
,'SOHO__ERNE'
,'SOHO__SEM'
,'STEREO_A__EUVI'
,'STEREO_A__COR'
,'STEREO_A__HI'
,'STEREO_A__SWAVES'
,'STEREO_A__SWEA'
,'STEREO_A__MAG'
,'STEREO_A__SEP'
,'STEREO_A__PLASTIC'
,'STEREO_B__EUVI'
,'STEREO_B__COR'
,'STEREO_B__HI'
,'STEREO_B__SWAVES'
,'STEREO_B__SWEA'
,'STEREO_B__MAG'
,'STEREO_B__SEP'
,'STEREO_B__PLASTIC'
,'TRACE__TRACE_EUV'
,'TRACE__TRACE_UV'
,'TRACE__TRACE_VIS'
,'HINODE__SOT_SP'
,'HINODE__SOT_FG'
,'HINODE__XRT'
,'HINODE__EIS'
,'YOHKOH__SXT'
,'YOHKOH__HXT'
,'YOHKOH__BCS'
,'YOHKOH__WBS_GRS'
,'YOHKOH__WBS_HXS'
,'YOHKOH__WBS_SXS'
,'RHESSI__HESSI_GMR'
,'RHESSI__HESSI_HXR'
,'GOES_12__SXI'
,'KPNO__MAGMP'
,'KPNO__SPMAG'
,'KPNO__VSM'
,'KPNO__FTS'
,'KSAC__SHELIO'
,'KSFO__CFDT1'
,'KSFO__CFDT2'
,'MLSO__MK4'
,'MLSO__DPM'
,'MLSO__CHIP'
,'MLSO__GONG'
,'MLSO__GONG'
,'BBSO__GONG'
,'BBSO__GONG'
,'MWSO__SHELIO'
,'OVRO__OVSA'
,'NANC__NRH'
,'NANC__NDA'
,'OACT__HALPH'
,'MEUD__MSH'
,'KANZ__HALPH'
,'PDMO__COGHA'
,'YNAO__HALPH'
,'LEAR__GONG'
,'LEAR__GONG'
,'TEID__GONG'
,'TEID__GONG'
,'CTIO__GONG'
,'CTIO__GONG'
,'UDPR__GONG'
,'ACE__CRIS'
,'ACE__EPAM'
,'ACE__MAG'
,'ACE__SEPICA'
,'ACE__SIS'
,'ACE__SWEPAM'
,'ACE__SWEPAM'
,'ACE__SWICS'
,'ACE__ULEIS'
,'CLUSTER_1__CIS'
,'CLUSTER_1__DWP'
,'CLUSTER_1__EDI'
,'CLUSTER_1__EFW'
,'CLUSTER_1__FGM'
,'CLUSTER_1__PEACE'
,'CLUSTER_1__RAPID'
,'CLUSTER_1__STAFF'
,'CLUSTER_1__WBD'
,'CLUSTER_1__WHISPER'
,'CLUSTER_2__CIS'
,'CLUSTER_2__EDI'
,'CLUSTER_2__EFW'
,'CLUSTER_2__FGM'
,'CLUSTER_2__PEACE'
,'CLUSTER_2__RAPID'
,'CLUSTER_2__STAFF'
,'CLUSTER_2__WBD'
,'CLUSTER_2__WHISPER'
,'CLUSTER_3__CIS'
,'CLUSTER_3__EDI'
,'CLUSTER_3__EFW'
,'CLUSTER_3__FGM'
,'CLUSTER_3__PEACE'
,'CLUSTER_3__RAPID'
,'CLUSTER_3__STAFF'
,'CLUSTER_3__WBD'
,'CLUSTER_3__WHISPER'
,'CLUSTER_4__CIS'
,'CLUSTER_4__EDI'
,'CLUSTER_4__EFW'
,'CLUSTER_4__FGM'
,'CLUSTER_4__PEACE'
,'CLUSTER_4__RAPID'
,'CLUSTER_4__STAFF'
,'CLUSTER_4__WBD'
,'CLUSTER_4__WHISPER'
,'GEOTAIL__CPI'
,'GEOTAIL__EFD'
,'GEOTAIL__EPIC'
,'GEOTAIL__LEP'
,'GEOTAIL__MGF'
,'GEOTAIL__PWI'
,'IMAGE__EUV'
,'IMAGE__HENA'
,'IMAGE__LENA'
,'IMAGE__MENA'
,'IMAGE__RPI'
,'POLAR__CAMMICE'
,'POLAR__CEPPAD'
,'POLAR__EFI'
,'POLAR__HYDRA'
,'POLAR__MFE'
,'POLAR__PIXIE'
,'POLAR__PWI'
,'POLAR__TIDE'
,'POLAR__TIMAS'
,'POLAR__UVI'
,'POLAR__VIS'
,'SOHO__CELIAS'
,'SOHO__COSTEP'
,'SOHO__ERNE'
,'STEREO_A__MAG'
,'TIMED__GUVI'
,'TIMED__SABER'
,'TIMED__SEE'
,'TIMED__TIDI'
,'ULYSSES__EPAC'
,'ULYSSES__GRB'
,'ULYSSES__SWICS'
,'ULYSSES__VHM_FGM'
,'WIND__3DP'
,'WIND__EPACT'
,'WIND__MFI'
,'WIND__SWE'
,'WIND__WAVES'
,'KANZ__HALPH'
,'CASSINI__MAG'
,'MESSENGER__EPPS'
,'MESSENGER__FIPS'
,'MESSENGER__MAG'
,'MESSENGER__XRS'
,'MEX__ELS'
,'MGS__ER'
,'MGS__MAG'
,'NEAR__MAG'
,'VEX__ELS'
,'VEX__MAG'
,'NOBE__NORH'
,'BLEIEN__PHOENIX_2'
,'RHESSI__HESSI_GMR'
,'RHESSI__HESSI_HXR'
,'HINODE__EIS'
,'HINODE__SOT_FG'
,'HINODE__SOT_SP'
,'HINODE__XRT'].sort()}"/>
  </td><td>
    <!--img class="tooltipme" title="Use Ctrl/Cmd-Click to select multiple entries" style ="position:relative;right:10px;" height="20px" src="${resource(dir:'images/icons',file:'info.png')}" /-->
    </td></tr>


 <tr><td></td><td>
<g:hiddenField name="serviceName"  value="DPAS" />
    <g:submitToRemote class="custom_button" before="fnBeforeQuery();" style="float:none;margin-right:50"  action="asyncQuery" onLoading="window.workspace.onLoading();" update="responseDivision" value="Search" onComplete="fnOnComplete();"/>

     </td></tr>
    </table>
  </g:form>
</div>
</div>

 <div id="displayableResult" class="displayable" style="display:block">

    </div>




</div>


