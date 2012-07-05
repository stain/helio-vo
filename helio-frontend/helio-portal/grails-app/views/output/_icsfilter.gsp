<div id="instrument_filter">  
  Check options to filter the results below. 
  <ul>
    <li><a href="#od1">Observing Domain 1</a></li>
    <li><a href="#od2">Observing Domain 2</a></li>
    <li><a href="#instrumentType">Instrument Type</a></li>
    <li><a href="#oe">Observable Entity</a></li>
    <li><a href="#keywords">Keywords</a></li>
  </ul>  
      <div id="od1">
        <table cellspacing="0" class="advanced_param_table">
          <tr>
            <td>
              <input class="observingDomain1" name="Sun" column="8" type="checkbox">
              Sun
            </td>
            <td>
              <input class="observingDomain1" name="Mercury" column="8" type="checkbox">
              Mercury
            </td>
            <td>
              <input class="observingDomain1" name="Venus" column="8" type="checkbox">
              Venus
            </td>
            <td>
              <input class="observingDomain1" name="Earth" column="8" type="checkbox">
              Earth
            </td>
            <td>
              <input class="observingDomain1" name="Mars" column="8" type="checkbox">
              Mars
            </td>
            <td>
              <input class="observingDomain1" name="Jupiter" column="8" type="checkbox">
              Jupiter
            </td>
            <td>
              <input class="observingDomain1" name="Saturn" column="8" type="checkbox">
              Saturn
            </td>
          </tr>
          <tr>
            <td>
              <input class="observingDomain1" name="heliosphere" column="8" type="checkbox">
              Heliosphere
            </td>
            <td>
              <input class="observingDomain1" name="planetary" column="8" type="checkbox">
              Planetary
            </td>
            <td>
              <input class="observingDomain1" name="comet" column="8" type="checkbox">
              Comet    
            </td>
            <td>
              <input class="observingDomain1" name="heliopause" column="8" type="checkbox">
              Heliopause
            </td>
            <td>
              <input class="observingDomain1" name="galactic" column="8" type="checkbox">
              Galactic
            </td>
            <td colspan="2">
            </td>
        </table>
      </div>
      <div id="od2">
        <table cellspacing="0" class="advanced_param_table">
          <tr>
            <td>
              <i>Solar</i>:
            </td>
            <td>
              <input class="observingDomain2" column="9" name="interior" type="checkbox">
              Interior
            </td>
            <td>

              <input class="observingDomain2" column="9" name="disk/inr. cor." type="checkbox">
              Disk/inr. cor.
            </td>
            <td>
              <input class="observingDomain2" column="9" name="outer corona" type="checkbox">
              Outer corona
            </td>
            <td>
              <input class="observingDomain2" column="9" name="disk/helios." type="checkbox">
              Disk/helios.
            </td>
            <td>
              <input class="observingDomain2" column="9" name="solar-wind" type="checkbox">
              Solar-wind
            </td>
          </tr>
          <tr>
            <td rowspan="2" valign="top">
              <i>Planetary</i>:
            </td>
            <td>
              <input class="observingDomain2" column="9" name="environment" type="checkbox">
              Environment
            </td>
            <td>
              <input class="observingDomain2" column="9" name="magnetosphere" type="checkbox">
              Magnetosphere
            </td>
            <td>
              <!--
              Magneto/ionosphere <input column="9" value="magneto/ionosphere" type="checkbox">
              -->
              <input class="observingDomain2" column="9" name="ionosphere" type="checkbox">
              Ionosphere
            </td>
            <td>
              <input class="observingDomain2" column="9" name="aurora" type="checkbox">
              Aurora
            </td>
          </tr>
          <tr>
            <td>
              <input class="observingDomain2" column="9" name="interstellar" type="checkbox">
              Interstellar
            </td>
            <td>
              <input class="observingDomain2" column="9" name="energy release" type="checkbox">
              Energy release
            </td>
            <td>
              <input class="observingDomain2" column="9" name="structure" type="checkbox">
              Structure
            </td>
          </tr>
        </table>
      </div>
      <div id="instrumentType">
        <table cellspacing="0" class="advanced_param_table">
          <tr>
            <td>
              <input class="instrumentType" column="7" value="remote" name="instrumentType" type="radio">
              Remote
            </td>    
            <td>
              <input class="instrumentType" column="7" value="in-situ" name="instrumentType" type="radio">
              In-situ
            </td>
            <td>
              <input class="instrumentType" column="7" value="in-situ|remote" name="instrumentType" type="radio" checked="checked">
              Both
            </td>
          </tr>
        </table>
      </div>
      <div id="oe">
        <table cellspacing="0" class="advanced_param_table">
          <tr>
            <td width="80" rowspan="2" valign="top">
              <input class="obsEntityPhotons" column="10" name="photons" type="checkbox">
              <i>Photons</i>
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="11" name="gmr" type="checkbox">
              GMR
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="11" name="hxr" type="checkbox">
              HXR
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="11" name="sxr" type="checkbox">
              SXR
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="11" name="euv" type="checkbox">
              EUV
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="11" name="uv" type="checkbox">
              UV
            </td>
            <td>
              <input class="obsEntityPhotonsType" filterClass="visible" column="11" name="visible" type="checkbox">
              visible
            </td>
            <td></td>
            <td>
              <input class="obsEntityPhotonsType" column="11" name="microwave" type="checkbox">
              &mu;-wave
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="11" name="radio" type="checkbox">
              radio
            </td>
          </tr>
          
          <tr>
          <!-- Disabled because of technical filter problem
          	<td></td>
          	<td></td>
          	<td></td>
          	<td></td>
          	<td></td>
            <td>
              <input class="obsEntityPhotonsType" filterClass="visibleType" column="15" name="H-alpha" type="checkbox">
              H-alpha
            </td>
            <td>
              <input class="obsEntityPhotonsType" filterClass="visibleType" column="15" name="He10830" type="checkbox">
              He 10830
            </td>
            -->
          </tr>
          <tr>
            <td width="80">
              <input class="obsEntityParticles" column="10" name="particles" type="checkbox">
              <i>Particles</i>
            </td>
            <td>   
              <input class="obsEntityParticlesType" column="11" name="charged" type="checkbox">
              Charged
            </td>
            <td>
              <input class="obsEntityParticlesType" column="11" name="energ" type="checkbox">
              Energetic
            </td>
            <td>
            <input class="obsEntityParticlesType" column="11" name="neut" type="checkbox">
              Neutral
            </td>
            <td>
              <input class="obsEntityParticlesType" column="11" name="dust" type="checkbox">
              Dust
            </td>
            <!-- Disabled because of technical filter problem
            <td>        
              <input class="obsEntityParticlesType" column="15" name="cosmic-ray" type="checkbox">
              Cosmic-ray
            </td>
            -->
          </tr>
          <tr>
            <td width="80">
              <input class="obsEntityFields" column="10" name="fields" type="checkbox">
              <i>Fields</i>
            </td>
            <td>
              <input class="obsEntityFieldsType" column="11" name="elect" type="checkbox">
              Electric
            </td>
            <td>
              <input class="obsEntityFieldsType" column="11" name="magn" type="checkbox">
              Magnetic
            </td>
            <!--
            elect/magn. <input column="11" name="uv" type="checkbox">
            -->
            <td>
              <input class="obsEntityFieldsType" column="11" name="gravity" type="checkbox">
              Gravity
            </td>
          </tr>
        </table>
      </div>
      <div id="keywords">
        <table cellspacing="0" class="advanced_param_table">
          <tr>
            <td>
              <input column="15" name="imager" type="checkbox">
              Imager
            </td>
            <td>
              <input column="15" name="spectrometer" type="checkbox">
              Spectrometer
            </td>
            <td>
              <input column="15" name="polarimeter" type="checkbox">
              Polarimeter
            </td>
            <td>
              <input column="15" name="coronagraph" type="checkbox">
              Coronagraph
            </td>
            <td>
              <input column="15" name="magnetograph" type="checkbox">
              Magnetograph
            </td>
            <td>             
              <input column="15" name="magnetometer" type="checkbox">
              Magnetometer
            </td>
          </tr>
          <tr>
            <td>
              <!--
              Radioheliograph <input column="15" name="radioheliograph" type="checkbox">
              -->
              <input column="15" name="oscillations" type="checkbox">
              Oscillations
            </td>
            <td>
              <input column="15" name="composition" type="checkbox">
              Composition
            </td>
            <td>              
              <input column="15" name="irradiance" type="checkbox">
              Irradiance
            </td>
            <td>
              <input column="15" name="photometer" type="checkbox">
              Photometer
            </td>
            <td>
              <input column="15" name="radiometer" type="checkbox">
              Radiometer
            </td>
          </tr>
        </table>
      </div>
  <div style="height: 20px;"><span class="filterInstrumentsText"></span></div>
</div>
<input id="show_accessible" column="17" name="true" type="checkbox" />
Show accessible instruments only ('Note: Instruments in <span style="color:red">red</span> cannot be accessed through HELIO')