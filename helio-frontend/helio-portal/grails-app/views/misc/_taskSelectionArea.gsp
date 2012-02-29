<!-- Content container -->
<div id="tabs">
  <ul>
    <li><a href="#tabs-2">Services</a></li>
    <li><a id="advanced_tab" href="#tabs-3">Advanced</a></li>
    <li><a href="#tabs-4">Beta</a></li>
  </ul>
  <div id="tabs-2">
    <table>
      <tr>
        <td>                
          <div id="task_eventlist" title="Search Events">Search Events</div>
        </td>
        <td>
          <div id="task_ics" title="Find instruments by capability">Find instruments by capability</div>
        </td>
        <td>
          <div id="task_ils" title="Locate planets/instruments by time">Locate planets/instruments by time</div>
        </td>
        <td>                
          <div id="task_dataaccess" title="Access Data">Search Data</div>
        </td>
      </tr>
    </table>
  </div>
  <div id="tabs-3">
    <table>
      <tr>
        <td>
          <div>
            <div id="task_plotservice" title="Select a Plot">Plot Data</div><div
            id="task_plotservice_select">Select a Plot</div>
          </div>
          <ul id="task_plotservice_menu" class="menu" style="display: none;">
            <li id="task_plotservice_goesplot">GOES timeline plot</li>
            <li id="task_plotservice_flareplot">Flare plot</li>
            <li id="task_plotservice_parkerplot">Simple Parker Spiral</li>
            <li id="task_plotservice_aceplot">ACE timeline</li>
            <li id="task_plotservice_staplot">STEREO-A timeline</li>
            <li id="task_plotservice_stbplot">STEREO-B timeline</li>
            <li id="task_plotservice_ulyssesplot">Ulysses timeline</li>
            <li id="task_plotservice_windplot">WIND timeline</li>
          </ul>
        </td>
        <td>
          <div>
            <div id="task_propagationmodel" title="Select a Propagation Model">Propagation Model</div><div
            id="task_propagationmodel_select">Select a Propagation Model</div>
          </div>
          <ul id="task_propagationmodel_menu" class="menu" style="display: none;">
              <li id="task_propagationmodel_pm_cme_fw" >Coronal Mass Ejections (CME) Forward PM</li>
              <li id="task_propagationmodel_pm_cme_back" >Coronal Mass Ejections (CME) Backward PM</li>
              <li id="task_propagationmodel_pm_cir_fw" >Co-rotating Interaction Region (CIR) Forward PM</li>
              <li id="task_propagationmodel_pm_cir_back" >Co-rotating Interaction Region (CIR) Backward PM</li>
              <li id="task_propagationmodel_pm_sep_fw" >Solar Energetic Particles (SEP) Forward PM</li>
              <li id="task_propagationmodel_pm_sep_back" >Solar Energetic Particles (SEP) Backward PM</li>
          </ul>
        </td>
        <td>
          <div id="task_upload2">Upload VOTable</div>
        </td>
        <td>
          <div>
            <div id="task_taverna" title="Select a Taverna Workflow">Workflows</div><div
            id="task_taverna_select">Select a Taverna Workflow</div>
          </div>
          <ul id="task_taverna_menu" class="menu" style="display: none;">
              <li id="task_taverna_tav_2283">Combine two event lists</li>
          </ul>
        </td>
      </tr>
    </table>
  </div>
  <div id="tabs-4">
    <table>
      <tr>
        <td>
          <div style="display:block" class="menu_item"  id="task_datamining">In-situ Data Mining (beta)</div>
        </td>            
      </tr>
    </table>
  </div>
</div>
