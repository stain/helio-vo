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
              <input class="observingDomain1" name="Heliosphere" column="8" type="checkbox">
              Heliosphere
            </td>
            <td>
              <input class="observingDomain1" name="Planetary" column="8" type="checkbox">
              Planetary
            </td>
            <td>
              <input class="observingDomain1" name="Comet" column="8" type="checkbox">
              Comet    
            </td>
            <td>
              <input class="observingDomain1" name="Heliopause" column="8" type="checkbox">
              Heliopause
            </td>
            <td>
              <input class="observingDomain1" name="Galactic" column="8" type="checkbox">
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
              <input class="observingDomain2" column="9" name="Interior" type="checkbox">
              Interior
            </td>
            <td>

              <input class="observingDomain2" column="9" name="Disk/Inr. Cor." type="checkbox">
              Disk/inr. cor.
            </td>
            <td>
              <input class="observingDomain2" column="9" name="Outer Corona" type="checkbox">
              Outer corona
            </td>
            <td>
              <input class="observingDomain2" column="9" name="Disk/Helios." type="checkbox">
              Disk/helios.
            </td>
            <td>
              <input class="observingDomain2" column="9" name="Solar-Wind" type="checkbox">
              Solar-wind
            </td>
          </tr>
          <tr>
            <td rowspan="2" valign="top">
              <i>Planetary</i>:
            </td>
            <td>
              <input class="observingDomain2" column="9" name="Environment" type="checkbox">
              Environment
            </td>
            <td>
              <input class="observingDomain2" column="9" name="Magnetosphere" type="checkbox">
              Magnetosphere
            </td>
            <td>
              <!--
              Magneto/ionosphere <input column="9" value="magneto/ionosphere" type="checkbox">
              -->
              <input class="observingDomain2" column="9" name="Ionosphere" type="checkbox">
              Ionosphere
            </td>
            <td>
              <input class="observingDomain2" column="9" name="Aurora" type="checkbox">
              Aurora
            </td>
          </tr>
          <tr>
            <td>
              <input class="observingDomain2" column="9" name="Interstellar" type="checkbox">
              Interstellar
            </td>
            <td>
              <input class="observingDomain2" column="9" name="Energy release" type="checkbox">
              Energy release
            </td>
            <td>
              <input class="observingDomain2" column="9" name="Structure" type="checkbox">
              Structure
            </td>
          </tr>
        </table>
      </div>
      <div id="instrumentType">
        <table cellspacing="0" class="advanced_param_table">
          <tr>
            <td>
              <input class="instrumentType" column="7" value="remote" name="Instrument Type" type="radio">
              Remote
            </td>    
            <td>
              <input class="instrumentType" column="7" value="in-situ" name="Instrument Type" type="radio">
              In-situ
            </td>
            <td>
              <input class="instrumentType" column="7" value="in-situ|remote" name="Instrument Type" type="radio" checked="checked">
              Both
            </td>
          </tr>
        </table>
      </div>
      <div id="oe">
        <table cellspacing="0" class="advanced_param_table">
          <tr>
            <td width="80" rowspan="2" valign="top">
              <input class="obsEntityPhotons" column="10" name="Photons" type="checkbox">
              <i>Photons</i>
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="11" name="GMR" type="checkbox">
              GMR
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="11" name="HXR" type="checkbox">
              HXR
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="11" name="SXR" type="checkbox">
              SXR
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="11" name="EUV" type="checkbox">
              EUV
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="11" name="UV" type="checkbox">
              UV
            </td>
            <td>
              <input class="obsEntityPhotonsType" filterClass="visible" column="11" name="Visible" type="checkbox">
              Visible
            </td>
            <td></td>
            <td>
              <input class="obsEntityPhotonsType" column="11" name="Microwave" type="checkbox">
              &mu;-wave
            </td>
            <td>
              <input class="obsEntityPhotonsType" column="11" name="Radio" type="checkbox">
              Radio
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
              <input class="obsEntityParticles" column="10" name="Particles" type="checkbox">
              <i>Particles</i>
            </td>
            <td>   
              <input class="obsEntityParticlesType" column="11" name="Charged" type="checkbox">
              Charged
            </td>
            <td>
              <input class="obsEntityParticlesType" column="11" name="Energy" type="checkbox">
              Energetic
            </td>
            <td>
            <input class="obsEntityParticlesType" column="11" name="Neut" type="checkbox">
              Neutral
            </td>
            <td>
              <input class="obsEntityParticlesType" column="11" name="Dust" type="checkbox">
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
              <input class="obsEntityFields" column="10" name="Fields" type="checkbox">
              <i>Fields</i>
            </td>
            <td>
              <input class="obsEntityFieldsType" column="11" name="Elect" type="checkbox">
              Electric
            </td>
            <td>
              <input class="obsEntityFieldsType" column="11" name="Magn" type="checkbox">
              Magnetic
            </td>
            <!--
            elect/magn. <input column="11" name="uv" type="checkbox">
            -->
            <td>
              <input class="obsEntityFieldsType" column="11" name="Gravity" type="checkbox">
              Gravity
            </td>
          </tr>
        </table>
      </div>
      <div id="keywords">
        <table cellspacing="0" class="advanced_param_table">
          <tr>
            <td>
              <input column="15" name="Imager" type="checkbox">
              Imager
            </td>
            <td>
              <input column="15" name="Spectrometer" type="checkbox">
              Spectrometer
            </td>
            <td>
              <input column="15" name="Polarimeter" type="checkbox">
              Polarimeter
            </td>
            <td>
              <input column="15" name="Coronagraph" type="checkbox">
              Coronagraph
            </td>
            <td>
              <input column="15" name="Magnetograph" type="checkbox">
              Magnetograph
            </td>
            <td>             
              <input column="15" name="Magnetometer" type="checkbox">
              Magnetometer
            </td>
          </tr>
          <tr>
            <td>
              <!--
              Radioheliograph <input column="15" name="radioheliograph" type="checkbox">
              -->
              <input column="15" name="Oscillations" type="checkbox">
              Oscillations
            </td>
            <td>
              <input column="15" name="Composition" type="checkbox">
              Composition
            </td>
            <td>              
              <input column="15" name="Irradiance" type="checkbox">
              Irradiance
            </td>
            <td>
              <input column="15" name="Photometer" type="checkbox">
              Photometer
            </td>
            <td>
              <input column="15" name="Radiometer" type="checkbox">
              Radiometer
            </td>
          </tr>
        </table>
      </div>
  <div style="height: 20px;"><span class="filterInstrumentsText"></span></div>
  <div id="show_accessible_panel" class="ui-tabs-panel ui-widget-content ui-corner-bottom">
    <input id="show_accessible" column="17" name="true" type="checkbox" />
    Show accessible instruments only ('Note: Instruments in <span style="color:red">red</span> cannot be accessed through HELIO')
  </div>
</div>