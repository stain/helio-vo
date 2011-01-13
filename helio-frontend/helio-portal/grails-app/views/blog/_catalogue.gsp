<div id="staticForms">
  <div id="formHeader">
    <img style ="float:left;position:relative;top:10px;left:10px"  src="./images/icons/toolbar/catalogue50.png" alt="Angry face" />
    <h1 style ="float:left;position:relative;left:15px" > Catalogues </h1>
  </div>
  <div style="clear:both;padding:50px 0 0 80px;">
    

 



  </div>

  <div>
    <table>

      <tr>
        <td>
          top five
          <ul style="padding:0;list-style-type: none;" >
            <li><input type="checkbox"  />GOES X-Ray</li>
            <li><input type="checkbox"  />Yohkoh HXT  </li>
            <li><input type="checkbox"  />SOHO / LASCO CME</li>
            <li><input type="checkbox"  />RHESSI Flare</li>
            <li><input type="checkbox"  />NOOA X-Ray</li>
          </ul>
        </td>
        <td>
          <g:select  name="extra" value="${extra}" from="${[
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

    
        </td>
      </tr>
      <tr>
        <td>
 
        </td>
      </tr>
    </table>
    <div float="right" style="width:50">
      <g:actionSubmit style="float:right;display:inline;"action="queryServ" value="Confirm" />
    <g:actionSubmit style="float:right;display:inline;"action="queryServ" value="Cancel" />
    <g:actionSubmit style="float:right;display:inline;"action="queryServ" value="Advanced" />
    <g:actionSubmit style="float:right;display:inline;"action="queryServ" value="add time" />

    </div>
    
  </div>

</div>

<!--
<g:form controller="webService">
<fieldset class="results">
  <legend >Dates</legend>


   <table>
     <tr>
     <td>
     <label for="from">Start Date(MM/dd/yy)</label>
     </td><td>
     <g:if test="${minDate==null}"><g:set var="minDate" value="01/01/2003" /></g:if>
     <input type="text" id="minDate" name="minDate" value="${minDate}"/>


     </td>

     </tr>
     <tr>
       <td>
         <label for="to">End Date</label>
         </td><td>
         <g:if test="${maxDate==null}"><g:set var="maxDate" value="01/02/2003" /></g:if>
          <input type="text" id="maxDate" name="maxDate" value="${maxDate}" />
       </td>

     </tr>
     <tr>

       <td>
         <g:hiddenField name="serviceName" value="HEC" />
         <g:actionSubmit action="queryServ" value="Search" />
       </td>
     </tr>





</table>


</fieldset>
  </g:form>



-->