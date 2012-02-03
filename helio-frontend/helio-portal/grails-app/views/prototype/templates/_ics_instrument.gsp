<div id="ics_instrument" style="display:none">
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
      </td>
    </tr>
  </table>
  <hr/>
  <h4>
    Filters
  </h4>
  <input column="19" name="T" type="checkbox" checked="checked"/>
  Show only accessible instruments
            
  <table width="100%" style="margin-left: 5px;margin-top:10px" cellspacing="4">
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
              <input class="observingDomain1" name="Sun" column="7" type="checkbox">
              Sun
            </td>
            <td>
              <input class="observingDomain1" name="Mercury" column="7" type="checkbox">
              Mercury
            </td>
            <td>
              <input class="observingDomain1" name="Venus" column="7" type="checkbox">
              Venus
            </td>
            <td>
              <input class="observingDomain1" name="Earth" column="7" type="checkbox">
              Earth
            </td>
            <td>
              <input class="observingDomain1" name="Mars" column="7" type="checkbox">
              Mars
            </td>
            <td>
              <input class="observingDomain1" name="Jupiter" column="7" type="checkbox">
              Jupiter
            </td>
            <td>
              <input class="observingDomain1" name="Saturn" column="7" type="checkbox">
              Saturn
            </td>
          </tr>
          <tr>
            <td>
              <input class="observingDomain1" name="heliosphere" column="7" type="checkbox">
              Heliosphere
            </td>
            <td>
              <input class="observingDomain1" name="planetary" column="7" type="checkbox">
              Planetary
            </td>
            <td>
              <input class="observingDomain1" name="comet" column="7" type="checkbox">
              Comet    
            </td>
            <td>
              <input class="observingDomain1" name="heliopause" column="7" type="checkbox">
              Heliopause
            </td>
            <td>
              <input class="observingDomain1" name="galactic" column="7" type="checkbox">
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
              <input class="observingDomain2" column="8" name="interior" type="checkbox">
              Interior
            </td>
            <td>

              <input class="observingDomain2" column="8" name="disk/inr. cor." type="checkbox">
              Disk/inr. cor.
            </td>
            <td>
              <input class="observingDomain2" column="8" name="outer corona" type="checkbox">
              Outer corona
            </td>
            <td>
              <input class="observingDomain2" column="8" name="disk/helios." type="checkbox">
              Disk/helios.
            </td>
            <td>
              <input class="observingDomain2" column="8" name="solar-wind" type="checkbox">
              Solar-wind
            </td>
          </tr>
          <tr>
            <td rowspan="2" valign="top">
              <i>Planetary</i>:
            </td>
            <td>
              <input class="observingDomain2" column="8" name="environment" type="checkbox">
              Environment
            </td>
            <td>
              <input class="observingDomain2" column="8" name="magnetosphere" type="checkbox">
              Magnetosphere
            </td>
            <td>
              <!--
              Magneto/ionosphere <input column="8" value="magneto/ionosphere" type="checkbox">
              -->
              <input class="observingDomain2" column="8" name="ionosphere" type="checkbox">
              Ionosphere
            </td>
            <td>
              <input class="observingDomain2" column="8" name="aurora" type="checkbox">
              Aurora
            </td>
          </tr>
          <tr>
            <td>
              <input class="observingDomain2" column="8" name="interstellar" type="checkbox">
              Interstellar
            </td>
            <td>
              <input class="observingDomain2" column="8" name="energy release" type="checkbox">
              Energy release
            </td>
            <td>
              <input class="observingDomain2" column="8" name="structure" type="checkbox">
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
              <input class="instrumentType" column="6" value="remote" name="instrumentType" type="radio">
              Remote
            </td>    
            <td>
              <input class="instrumentType" column="6" value="in-situ" name="instrumentType" type="radio">
              In-situ
            </td>
            <td>
              <input class="instrumentType" column="6" value="in-situ|remote" name="instrumentType" type="radio" checked="checked">
              Both
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
              <input class="obsEntityPhotons" column="9" name="photons" type="checkbox" checked="checked">
              <i>Photons</i>
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="10" name="gmr" type="checkbox">
              GMR
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="10" name="hxr" type="checkbox">
              HXR
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="10" name="sxr" type="checkbox">
              SXR
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="10" name="euv" type="checkbox">
              EUV
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="10" name="uv" type="checkbox">
              UV
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="10" name="visible" type="checkbox">
              visible
            </td>
            
          </tr>
          <tr>            
            <td>
              <input class="" column="18" name="H-alpha" type="checkbox">
              H-alpha
            </td>
            <td>
              <input class="" column="18" name="He10830" type="checkbox">
              He 10830
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="10" name="microwave" type="checkbox">
              &mu;-wave
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="10" name="radio" type="checkbox">
              radio
            </td>
          </tr>
          <tr>
            <td width="80">
              <input class="obsEntityParticles" column="9" name="particles" type="checkbox">
              <i>Particles</i>
            </td>
            <td>   
              <input class="obsEntityParticlesType" column="10" name="charged" type="checkbox">
              Charged
            </td>
            <td>
              <input class="obsEntityParticlesType" column="10" name="energ" type="checkbox">
              Energetic
            </td>
            <td>
            <input class="obsEntityParticlesType" column="10" name="neut" type="checkbox">
              Neutral
            </td>
            <td>
              <input class="obsEntityParticlesType" column="10" name="dust" type="checkbox">
              Dust
            </td>
            <td>        
              <input class="" column="18" name="cosmic-ray" type="checkbox">
              Cosmic-ray
            </td>
          </tr>
          <tr>
            <td width="80">
              <input class="obsEntityFields" column="9" name="fields" type="checkbox">
              <i>Fields</i>
            </td>
            <td>
              <input class="obsEntityFieldsType" column="10" name="elect" type="checkbox">
              Electric
            </td>
            <td>
              <input class="obsEntityFieldsType" column="10" name="magn" type="checkbox">
              Magnetic
            </td>
            <!--
            elect/magn. <input column="10" name="uv" type="checkbox">
            -->
            <td>
              <input class="obsEntityFieldsType" column="10" name="gravity" type="checkbox">
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
              <input column="18" name="imager" type="checkbox">
              Imager
            </td>
            <td>
              <input column="18" name="spectrometer" type="checkbox">
              Spectrometer
            </td>
            <td>
              <input column="18" name="polarimeter" type="checkbox">
              Polarimeter
            </td>
            <td>
              <input column="18" name="coronagraph" type="checkbox">
              Coronagraph
            </td>
            <td>
              <input column="18" name="magnetograph" type="checkbox">
              Magnetograph
            </td>
            <td>             
              <input column="18" name="magnetometer" type="checkbox">
              Magnetometer
            </td>
          </tr>
          <tr>
            <td>
              <!--
              Radioheliograph <input column="18" name="radioheliograph" type="checkbox">
              -->
              <input column="18" name="oscillations" type="checkbox">
              Oscillations
            </td>
            <td>
              <input column="18" name="composition" type="checkbox">
              Composition
            </td>
            <td>              
              <input column="18" name="irradiance" type="checkbox">
              Irradiance
            </td>
            <td>
              <input column="18" name="photometer" type="checkbox">
              Photometer
            </td>
            <td>
			  <input column="18" name="radiometer" type="checkbox">
              Radiometer
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <div style="height: 20px;"><span class="filterInstrumentsText">This will be a filter text.</span></div>
</div>