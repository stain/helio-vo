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
  <%--hr/>
  <h4>
    Instrument
  </h4>
  <table width="100%" style="margin-left: 5px" cellspacing="4">
    <tr>
      <td align="left" valign="top" colspan="2">
        <b>Observing Domain 1</b>
      </td>
    </tr>
    <tr>
      <td align="left" valign="top" colspan="2">
        <table cellspacing="0" class="advanced_param_table">
          <tr>
            <td>
              <input class="columnInput" name="od1[]" value="Sun" type="checkbox"/></input>
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
        <b>Observing Domain 2</b>
      </td>
    </tr>
    <tr>
      <td>
        <table cellspacing="0" class="advanced_param_table">
          <tr>
            <td>
              <i>Solar</i>:
            </td>
            <td>
              <input name="od2[]" value="interior" type="checkbox"></input>
              Interior
            </td>
            <td>
      <!--      Disk <input name="od2[]" value="disk" type="checkbox"></input> &nbsp;&nbsp-->
              <input name="od2[]" value="disk/inr. cor." type="checkbox"></input>
              Disk/inr. cor.
            </td>
            <td>
              <input name="od2[]" value="outer corona" type="checkbox"></input>
              Outer corona
            </td>
            <td>
              <input name="od2[]" value="disk/helios." type="checkbox"></input>
              Disk/helios.
            </td>
            <td>
              <input name="od2[]" value="solar-wind" type="checkbox"></input>
              Solar-wind
            </td>
          </tr>
          <tr>
            <td rowspan="2" valign="top">
              <i>Planetary</i>:
            </td>
            <td>
              <input name="od2[]" value="environment" type="checkbox"></input>
              Environment
            </td>
            <td>
              <input name="od2[]" value="magneto" type="checkbox"></input>
              Magnetosphere
            </td>
            <td>
              <!--
              Magneto/ionosphere <input name="od2[]" value="magneto/ionosphere" type="checkbox"></input>
              -->
              <input name="od2[]" value="ionosphere" type="checkbox"></input>
              Ionosphere
            </td>
            <td>
              <input name="od2[]" value="aurora" type="checkbox"></input>
              Aurora
            </td>
          </tr>
          <tr>
            <td>
              <input name="od2[]" value="interstellar" type="checkbox"></input>
              Interstellar
            </td>
            <td>
              <input name="od2[]" value="energy release" type="checkbox"></input>
              Energy release
            </td>
            <td>
              <input name="od2[]" value="structure" type="checkbox"></input>
              Structure
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td align="left" valign="top" colspan="2">
        <b>Instrument Type</b>
      </td>
    </tr>
    <tr>
      <td>
        <table cellspacing="0" class="advanced_param_table">
          <tr>
            <td>
              <input name="itype[]" value="remote" type="checkbox"></input>
              Remote
            </td>    
            <td>
              <input name="itype[]" value="in-situ" type="checkbox"></input>
              In-situ
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td align="left" valign="top" colspan="2">
        <b>Observable Entity</b>
      </td>
    </tr>
    <tr>
      <td>
        <table cellspacing="0" class="advanced_param_table">
          <tr>
            <td width="80" rowspan="2" valign="top">
              <input name="oe1[]" value="photons" type="checkbox"></input>
              <i>Photons</i>
            </td>
            <td>
              <input name="oe2[]" value="gmr" type="checkbox"></input>
              GMR
            </td>
            <td>
              <input name="oe2[]" value="hxr" type="checkbox"></input>
              HXR
            </td>
            <td>
              <input name="oe2[]" value="sxr" type="checkbox"></input>
              SXR
            </td>
            <td>
              <input name="oe2[]" value="euv" type="checkbox"></input>
              EUV
            </td>
            <td>
              <input name="oe2[]" value="uv" type="checkbox"></input>
              UV
            </td>
            <td>
              <input name="oe2[]" value="visible" type="checkbox"></input>
              visible
            </td>
            <td>
              <input name="oe2[]" value="microwave" type="checkbox"></input>
              &mu;-wave
            </td>
            <td>
              <input name="oe2[]" value="radio" type="checkbox"></input>
              radio
            </td>
          </tr>
          <tr>            
            <td>
              <input name="oe2p[]" value="H-alpha" type="checkbox"></input>
              H-alpha
            </td>
            <td>
              <input name="oe2p[]" value="He10830" type="checkbox"></input>
              He 10830
            </td>
          </tr>
          <tr>
            <td width="80">
              <input name="oe1[]" value="particles" type="checkbox"></input>
              <i>Particles</i>
            </td>
            <td>   
              <input name="oe2[]" value="charged" type="checkbox"></input>
              Charged
            </td>
            <td>
              <input name="oe2[]" value="energ" type="checkbox"></input>
              Energetic
            </td>
            <td>
              <!--
              energ/neut <input name="oe2[]" value="energ/neut" type="checkbox"></input>
              -->
              <input name="oe2[]" value="neut" type="checkbox"></input>
              Neutral
            </td>
            <td>
              <input name="oe2[]" value="dust" type="checkbox"></input>
              Dust
            </td>
            <td>        
              <input name="oe2p[]" value="cosmic-ray" type="checkbox"></input>
              Cosmic-ray
            </td>
          </tr>
          <tr>
            <td width="80">
              <input name="oe1[]" value="fields" type="checkbox"></input>
              <i>Fields</i>
            </td>
            <td>
              <input name="oe2[]" value="elect" type="checkbox"></input>
              Electric
            </td>
            <td>
              <input name="oe2[]" value="magn" type="checkbox"></input>
              Magnetic
            </td>
              <!--
              elect/magn. <input name="oe2[]" value="uv" type="checkbox"></input>
              --> 
            <td>
              <input name="oe2[]" value="gravity" type="checkbox"></input>
              Gravity
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td align="left" valign="top" colspan="2">
        <b>Keywords</b>
      </td>
    </tr>
    <tr>
      <td>
        <table cellspacing="0" class="advanced_param_table">
          <tr>
            <td>
              <input name="key[]" value="imager" type="checkbox"></input>
              Imager
            </td>
            <td>
              <input name="key[]" value="spectrometer" type="checkbox"></input>
              Spectrometer
            </td>
            <td>
              <input name="key[]" value="polarimeter" type="checkbox"></input>
              Polarimeter
            </td>
            <td>
              <input name="key[]" value="coronagraph" type="checkbox"></input>
              Coronagraph
            </td>
            <td>
              <input name="key[]" value="magnetograph" type="checkbox"></input>
              Magnetograph
            </td>
            <td>             
              <input name="key[]" value="magnetometer" type="checkbox"></input>
              Magnetometer
            </td>
          </tr>
          <tr>
            <td>
              <!--
              Radioheliograph <input name="key[]" value="radioheliograph" type="checkbox"></input>
              -->
              <input name="key[]" value="oscillations" type="checkbox"></input>
              Oscillations
            </td>
            <td>
              <input name="key[]" value="composition" type="checkbox"></input>
              Composition
            </td>
            <td>              
              <input name="key[]" value="irradiance" type="checkbox"></input>
              Irradiance
            </td>
            <td>
              <input name="key[]" value="photometer" type="checkbox"></input>
              Photometer
            </td>
            <td>
              <input name="key[]" value="radiometer" type="checkbox"></input>
              Radiometer
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table --%>
</div>