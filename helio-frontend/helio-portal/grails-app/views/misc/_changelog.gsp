<div id="splash_screen" style="margin:20px">
  <h2 style="color:orange;padding-bottom:10px;border-bottom: 1px solid black">Welcome to the HELIO Front End</h2>
  <input id="task_name" name="taskName" type="hidden" value="splash"/>

  <table style="margin-top:20px;">
    <tr>
      <td>
        <h3 style="padding-bottom: 10px">Release 5.5-Snapshot</h3>
        <h4 style="margin-top:10px;">What's new</h4>
        <ul>
            <li>27-Jul-2012: Upgraded plotting library with selection mode.</li>
            <li>24-Jul-2012: Some performance improvements in ICS/ILS/DPAS.</li>
            <li>18-Jul-2012: Download scripts for DPAS results.</li>
            <li>18-Jul-2012: Merge the service menu into the main menu.</li>
            <li>18-Jul-2012: Overhaul of the front page (should be less scary).</li>
            <li>18-Jul-2012: Plotter is now configurable. Some configuration is still missing though.</li>
            <li>05-Jun-2012: Rework of the VOTable plotter, many smaller bug fixes.</li>
            <li>17-May-2012: Add time range inspector.</li>
            <li>11-May-2012: CSS cleanup. Various small bug fixes.</li>
            <li>10-May-2012: Upgrade to Grails 2.0.3.</li>
            <li>29-Feb-2012: Removing unused code. Reworking the event details dialog. Lots of housekeeping.</li>
            <li>07-Feb-2012: Added new ICS/ILS GUIS. Changed the way to process VOTables: can handle up to 50'000 records now.</li>
            <li>06-Feb-2012: Changed DPAS dialog to new style.</li>
            <li>05-Feb-2012: Added extract-button.</li>
            <li>05-Feb-2012: Rework of the "Search Event" task. Swapping the data cart.</li>
            <li>30-Jan-2012: Added new propagation model. Plot options for GOES plot. TimeRangeDialog shows start time or single time range, if required.</li>
            <li>27-Jan-2012: Several smaller bug fixes.</li>
            <li>26-Jan-2012: Addition of Taverna workflow. 
            Minor fixes in SolarWind PM and Parker Spiral plot.</li>
            <li>25-Jan-2012: Speed improvements on query services, some cleaning up</li>
            <li>24-Jan-2012: Propagation Models - "See Advanced-Propagation Model".</li>
            <li>23-Jan-2012: DES plots - see "Advanced-Plot".</li>
        </ul>
                
        <h4 style="margin-top:10px;">How to use this interface</h4>
        The HELIO user interface follows this very generic pattern:
        <ol>
            <li>Select what information you want to get. Choose a <i>task</i> from the tabs at the top of the screen.</li>
            <li>Provide the required <i>parameters</i> for this <i>task</i>. Click on the yellow circles or use parameters from previous tasks (see point 4).</li>
            <li>Send the request to HELIO and wait for the <i>result</i>. 
            (<img style="width: 12px; height: 12px" src="${resource(dir:'images/helio/',file:'load.gif')}" alt="Loading..." />).</li>
            <li>Filter and analyse the <i>result</i> and extract <i>parameters</i> from it (this is currently being reworked).</li>
            <li>Use the extracted <i>parameters</i> as input for new <i>tasks</i>, i.e. continue with step 1.</li>
        </ol>
        
        <h4 style="margin-top:10px;">Feedback</h4>
        <p>Please send any comments to support@helio-vo.eu. Many thanks.</p>
        <p>&nbsp;</p>
      </td>
    </tr>
  </table>
</div>
