<div id="staticForms">
  <div id="formHeader">
    <img style ="float:left;position:relative;top:10px;left:10px" height="30px" src="${resource(dir:'images/icons/toolbar',file:'hec.png')}" />
    <h1 style ="float:left;position:relative;left:15px" > Event Search</h1>
    <g:render template="templates/controls" />
  </div>
  <div style="clear:both;padding:50px 0 0 80px;">  
    <g:form controller="prototype">
      <table width="100%" cellpadding="0" cellspacing="0">
        <col width="*" />
        <col width="250"/> 
        <tbody>
        <%-- date selection area --%>
        <tr>
          <td style="border-top: solid 1px gray; border-bottom: solid 1px gray;"><b>Data Range</b><br/>
          <g:render template="templates/dates" /></td>
          <td  style="border-top: solid 1px gray; border-bottom: solid 1px gray;">
            <div class="message"><b>Step 1</b><br/>Choose a date range or drop a result from a previous query into the 'hole'.</div>
          </td>
        <tr>
        <%-- column selection area --%>
        <tr>
          <td>
            <table>
              <tr>
                <td colspan="2"><b>Event list</b></td>
              </tr>
              <tr>
                <td colspan="2">
                  <div id="hecExtendedCatalogSelector">
                    <table cellpadding="0" cellspacing="0"><tr>
                      <td>
                      <g:each status="status" var="list" in="${[
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
                           ].sort()}">
                           <%-- create a new column in the selection table --%>
                          <g:if test="${status % 9 == 0}"><%="</td><td>"%></g:if>
                          <input type="checkbox" name="extra" value="${list}" />${list}<br/>
                        </g:each> 
                      </td>
                    </tr></table>
                  </div>
                </td>
              </tr>
            </table>
          </td>
          <td>
            <div class="message"><b>Step 2</b><br/>Select the catalog(s) you are interested in.</div>
          </td>
        </tr>
        <%-- advanced query --%>
        <%-- g:submitToRemote style="float:none;margin-right:50" action="asyncGetColumns"  onComplete="fnOnCompleteGetColumns();" update="hecResponse" value="Advanced Search" --%>
        <tr>
          <td style="border-top: solid 1px gray;">
            <div id="hecExtendedQueryHeadingClosed">&gt; Advanced Query <span id="hecExtendedQueryHeadingError"></span></div>
            <div id="hecExtendedQueryHeadingOpen">v Advanced Query</div>
          </td>
          <td style="border-top: solid 1px gray;">
            <div class="message"><b>Step 3</b> (optional)<br/>Select advanced qualifiers for selected columns<br/>Close this section to select different catalogs</div>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <div id="hecExtendedQueryContent" class="columnInputs"></div>
            <input name="serviceName" type="hidden" value="HEC"/>          
            <input id="whereField" name="where" style="display:none" type="text"/>          
          </td>
        </tr>
        <%-- submit button --%>
        <tr>
          <td style="border-top: solid 1px gray; border-bottom: solid 1px gray;">
            <g:submitToRemote  before="fnBeforeQuery();" style="float:none;margin-right:50"  action="asyncHecQuery" onLoading="window.workspace.onLoading();" update="responseDivision" value="Search" onComplete="fnOnComplete();"/>
          </td>
          <td style="border-top: solid 1px gray; border-bottom: solid 1px gray;">
            <div class="message"><b>Step 4</b><br/>Submit query to HELIO</div>
          </td>
        </tr>
        </tbody>
      </table>
    </g:form>
  </div>  
  <div id="displayableResult" class="displayable" style="display:block"></div>
</div>