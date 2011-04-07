<div class="ics_instrument">
  <h4>Instrument</h4>
  <table>
    <tr>
      <td align="left" valign="top">  
        <table>
          <g:each in="${catalog}" var="field">
            <g:if test="${!field.startsWith('time_')}">
              <g:set var="hasFields" value="${true}" />
              <tr>
                <td>
                  <label style="display:block; float:left; width:150px;">${field}</label>
                </td>
                <td>
                  <!-- span id="cinfo_instrument_${field}" class="colLabelTooltipMe ui-icon ui-icon-info" style="display:inline-block; vertical-align: top;" ></span -->
                  <div class="hecLabelTooltip ctooltip_instrument_${field}"><pre style="white-space: pre-wrap">n/a</pre></div>
                </td>  
                <td>
                  <input class="columnSelection" name="instrument.${field}" type="text" />
                </td>
              </tr>
            </g:if>
          </g:each>
        </table>
        <g:if test="${!hasFields}">
            <p>No field definition found for the instruments table.</p>
        </g:if>
      </td>
    </tr>
  </table>
  <%--h4>
    Instrument
  </h4>
  <table width="100%" style="margin-left: 5px">
    <tr>
      <td align="left" valign="top" colspan="2">
        <i>Observing Domain 1</i>
      </td>
    </tr>
    <tr>
      <td align="left" valign="top" colspan="2">
        <table cellspacing="0" class="advanced_param_table">
          <tr>
            <td>
              <input name="od1[]" value="Sun" type="checkbox"/></input>
              Sun
            </td>
            <td>
              <input name="od1[]" value="Mercury" type="checkbox"></input>
              Mercury
            </td>
            <td>
              <input name="od1[]" value="Venus" type="checkbox"></input>
              Venus
            </td>
            <td>
              <input name="od1[]" value="Earth" type="checkbox"></input>
              Earth
            </td>
            <td>
              <input name="od1[]" value="Mars" type="checkbox"></input> 
              Mars
            </td>
            <td>
              <input name="od1[]" value="Jupiter" type="checkbox"></input> 
              Jupiter
            </td>
            <td>
              <input name="od1[]" value="Saturn" type="checkbox"></input> 
              Saturn
            </td>
          </tr>
          <tr>
            <td>
              <input name="od1[]" value="heliosphere" type="checkbox"></input> 
              Heliosphere
            </td>
            <td>
              <input name="od1[]" value="planetary" type="checkbox"></input> 
              Planetary
            </td>
            <td>
              <input name="od1[]" value="comet" type="checkbox"></input> 
              Comet    
            </td>
            <td>
              <input name="od1[]" value="heliopause" type="checkbox"></input> 
              Heliopause
            </td>
            <td>
              <input name="od1[]" value="galactic" type="checkbox"></input> 
              Galactic
            </td>
            <td colspan="2">
            </td>
        </table>
     </td>
    </tr>
    <tr>
      <td align="left" valign="top" colspan="2">
      </td>
    </tr>
    <tr>
      <td align="left" valign="top" colspan="2">
        <i>Observing Domain 2</i>: <br>
      </td>
    </tr>
    <tr>
      <td>   
        <i>Solar</i>:
      </td>
      <td>
        <input name="od2[]" value="interior" type="checkbox"></input> &nbsp;&nbsp;
        Interior
<!--      Disk <input name="od2[]" value="disk" type="checkbox"></input> &nbsp;&nbsp-->
        <input name="od2[]" value="disk/inr. cor." type="checkbox"></input> &nbsp;&nbsp;
        Disk/inr. cor.
        <input name="od2[]" value="outer corona" type="checkbox"></input> &nbsp;&nbsp;
        Outer corona
        <input name="od2[]" value="disk/helios." type="checkbox"></input> &nbsp;&nbsp;
        Disk/helios.
        <input name="od2[]" value="solar-wind" type="checkbox"></input> &nbsp;&nbsp;
        Solar-wind
      </td>
   </tr>
   <tr>
     <td>
      <i>Planetary</i>: &nbsp;&nbsp;
     </td>
     <td>
       <input name="od2[]" value="environment" type="checkbox"></input> &nbsp;&nbsp;
       Environment
       <input name="od2[]" value="magneto" type="checkbox"></input> &nbsp;&nbsp;
       Magnetosphere
    <!--
    Magneto/ionosphere <input name="od2[]" value="magneto/ionosphere" type="checkbox"></input> &nbsp;&nbsp;
    -->
       <input name="od2[]" value="ionosphere" type="checkbox"></input> &nbsp;&nbsp;
       Ionosphere
       <input name="od2[]" value="aurora" type="checkbox"></input> &nbsp;&nbsp;
       Aurora
       <input name="od2[]" value="interstellar" type="checkbox"></input> &nbsp;&nbsp;
       Interstellar
       <input name="od2[]" value="energy release" type="checkbox"></input> &nbsp;&nbsp;
       Energy release
       <input name="od2[]" value="structure" type="checkbox"></input> &nbsp;&nbsp;
       Structure
     </td>
   </tr>
   
   <!--    -->
   <i>Instrument Type</i>: <br>

   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   Remote
   <input name="itype[]" value="remote" type="checkbox"></input> &nbsp;&nbsp;
   In-situ
   <input name="itype[]" value="in-situ" type="checkbox"></input> &nbsp;&nbsp;
   <br>
<!--    -->
   <i>Observable Entity</i>: <br>

   <table cellpadding=0 cellspacing=0>
    <tr>
     <td width="150">
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      Photons
      <input name="oe1[]" value="photons" type="checkbox"></input>
     </td>
     <td>
      GMR
      <input name="oe2[]" value="gmr" type="checkbox"></input> &nbsp;&nbsp;

      HXR
      <input name="oe2[]" value="hxr" type="checkbox"></input> &nbsp;&nbsp;
      SXR
      <input name="oe2[]" value="sxr" type="checkbox"></input> &nbsp;&nbsp;
      EUV
      <input name="oe2[]" value="euv" type="checkbox"></input> &nbsp;&nbsp;
      UV
      <input name="oe2[]" value="uv" type="checkbox"></input> &nbsp;&nbsp;
      visible
      <input name="oe2[]" value="visible" type="checkbox"></input> &nbsp;&nbsp;

      &mu;-wave
      <input name="oe2[]" value="microwave" type="checkbox"></input> &nbsp;&nbsp;
      radio
      <input name="oe2[]" value="radio" type="checkbox"></input> &nbsp;&nbsp;
      <br>
      H-alpha
      <input name="oe2p[]" value="H-alpha" type="checkbox"></input> &nbsp;&nbsp;
      He 10830
      <input name="oe2p[]" value="He10830" type="checkbox"></input> &nbsp;&nbsp;

     </td>
    </tr>
    <tr>
     <td width="150">
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      Particles
      <input name="oe1[]" value="particles" type="checkbox"></input>
     </td>
     <td>

      Charged
      <input name="oe2[]" value="charged" type="checkbox"></input> &nbsp;&nbsp;
      Energetic
      <input name="oe2[]" value="energ" type="checkbox"></input> &nbsp;&nbsp;
<!--
energ/neut <input name="oe2[]" value="energ/neut" type="checkbox"></input> &nbsp;&nbsp;
-->
      Neutral
      <input name="oe2[]" value="neut" type="checkbox"></input> &nbsp;&nbsp;
      Dust
      <input name="oe2[]" value="dust" type="checkbox"></input> &nbsp;&nbsp;

      Cosmic-ray
      <input name="oe2p[]" value="cosmic-ray" type="checkbox"></input> &nbsp;&nbsp;
     </td>
    </tr>
    <tr>
     <td width="150">
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      Fields
      <input name="oe1[]" value="fields" type="checkbox"></input>

     </td>
     <td>
      Electric
      <input name="oe2[]" value="elect" type="checkbox"></input> &nbsp;&nbsp;
      Magnetic
      <input name="oe2[]" value="magn" type="checkbox"></input> &nbsp;&nbsp;
<!--
elect/magn. <input name="oe2[]" value="uv" type="checkbox"></input> &nbsp;&nbsp;
-->
      Gravity
      <input name="oe2[]" value="gravity" type="checkbox"></input> &nbsp;&nbsp;

     </td>
    </tr>
   </table>
   <p>
<!--    -->
<!--
dust    ?????
-->
<!--    -->
   <i>Keywords</i>: <br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

   Imager
   <input name="key[]" value="imager" type="checkbox"></input> &nbsp;&nbsp;
   Spectrometer
   <input name="key[]" value="spectrometer" type="checkbox"></input> &nbsp;&nbsp;
   Polarimeter
   <input name="key[]" value="polarimeter" type="checkbox"></input> &nbsp;&nbsp;
   Coronagraph
   <input name="key[]" value="coronagraph" type="checkbox"></input> &nbsp;&nbsp;
   Magnetograph
   <input name="key[]" value="magnetograph" type="checkbox"></input> &nbsp;&nbsp;

   Magnetometer
   <input name="key[]" value="magnetometer" type="checkbox"></input> &nbsp;&nbsp;
   <br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<!--
Radioheliograph <input name="key[]" value="radioheliograph" type="checkbox"></input> &nbsp;&nbsp;
-->
   Oscillations
   <input name="key[]" value="oscillations" type="checkbox"></input> &nbsp;&nbsp;
   Composition
   <input name="key[]" value="composition" type="checkbox"></input> &nbsp;&nbsp;

   Irradiance
   <input name="key[]" value="irradiance" type="checkbox"></input> &nbsp;&nbsp;
   Photometer
   <input name="key[]" value="photometer" type="checkbox"></input> &nbsp;&nbsp;
   Radiometer
   <input name="key[]" value="radiometer" type="checkbox"></input> &nbsp;&nbsp;
   
   
   
  </table --%>
</div>