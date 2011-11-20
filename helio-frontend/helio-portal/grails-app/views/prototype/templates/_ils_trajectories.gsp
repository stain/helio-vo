<div id="ils_trajectories" style="display:none">
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
    Trajectories
  </h4>
  <table width="100%" style="margin-left: 5px" cellspacing="4">
    <tr>
      <td align="left" valign="top" colspan="2">
        <b>Planet</b>
      </td>
    </tr>
    <tr>
      <td align="left" valign="top" colspan="2">
        <table cellspacing="0" class="advanced_param_table">
          <tr>
            <td>
              <input column="0" name="Mercury" type="checkbox">
              Mercury
            </td>
            <td>
              <input column="0" name="Venus" type="checkbox">
              Venus
            </td>
            <td>
              <input column="0" name="Earth" type="checkbox">
              Earth
            </td>
            <td>
              <input column="0" name="Mars" type="checkbox">
              Mars
            </td>
            <td>
              <input column="0" name="Jupiter" type="checkbox">
              Jupiter
            </td>
            <td>
              <input column="0" name="Saturn" type="checkbox">
              Saturn
            </td>
            <td>
              <input column="0" name="Uranus" type="checkbox">
              Uranus
            </td>
            <td>
              <input column="0" name="Neptune" type="checkbox">
              Neptune
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td align="left" valign="top" colspan="2">
        <b>Spacecraft</b>
      </td>
    </tr>
    <tr>
      <td align="left" valign="top" colspan="2">
        <table cellspacing="0" class="advanced_param_table">
          <tr>
            <td>
              <input column="0" name="Ulysses" type="checkbox">
              Ulysses
            </td>
            <td>
              <input column="0" name="STEREOA" type="checkbox">
              STEREO-A
            </td>
            <td>
              <input column="0" name="STEREOB" type="checkbox">
              STEREO-B
            </td>
            <td>
              <input column="0" name="Messenger" type="checkbox">
              Messenger
            </td>
            <td>
              <input column="0" name="Voyager1" type="checkbox">
              Voyager 1
            </td>
            <td>
              <input column="0" name="Voyager2" type="checkbox">
              Voyager 2
            </td>
          </tr>
           <tr>
            <td>
              <input column="0" name="Galileo" type="checkbox">
              Galileo
            </td>
            <td>
              <input column="0" name="Cassini" type="checkbox">
              Cassini
            </td>
            <td>
              <input column="0" name="NewHorizons" type="checkbox">
              New Horizons
            </td>
            <td>
              <input column="0" name="Rosetta" type="checkbox">
              Rosetta
            </td>
            <td>
              <input column="0" name="Dawn" type="checkbox">
              Dawn
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table >
</div>