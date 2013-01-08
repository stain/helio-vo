<div class="task candybox">
  <div class="task_header_area viewerHeader">
    <h1>HELIO Front End Changelog</h1>
  </div>
  <table style="margin-top:20px; padding:10px">
    <tr>
      <td>
        <h3 style="padding-bottom: 10px">Release 5.5-Snapshot</h3>
        <h4 style="margin-top:10px;">What's new</h4>
        <table class="changelog">
          <thead>
            <tr>
              <th>Release Date</th>
              <th>Changes</th>
            </tr>
          </thead>
          <tbody>
            <%-- template for new rows
            <tr>
                <td></td>
                <td></td>
            </tr>
             --%>
            <tr>
                <td>07-Jan-2013</td>
                <td>
                  <ul>
                    <li>Online Help (tutorials).</li>
                    <li>PQL queries for HEC.</li>
                    <li>Many bug fixes</li>
                  </ul>
                </td>
            </tr>
            <tr>
                <td>03-Sep-2012</td>
                <td>
                  Cleanup-release for CDAW
                  <ul>
                    <li>New DPAS Instrument selection dialog</li>
                    <li>Refactoring of client side data model: Users will see the proper names of instruments, event lists, etc. rather than internal IDs.</li>
                    <li>Addition of HELIO screen cast to HELP menu.</li>
                    <li>Propagation model allows to hide missed objects</li>
                    <li>Cookie-based user management. From the same browser you should always get back your saved data.</li>
                    <li>Many bug fixes</li>
                  </ul>
                </td>
            </tr>
            <tr>
                <td>24-Aug-2012</td>
                <td>Maintenance release to disable unstable catalog servers.</td>
            </tr>
            <tr>
                <td>23-Aug-2012</td>
                <td>Prepare for the PQL queries (back-end is ready), bugfixes in HEC</td>
            </tr>
            <tr>
                <td>17-Aug-2012</td>
                <td>
                  <ul>
                    <li>Event list selection overhaul.</li>
                    <li>Plotter upgrade<>li>
                    <li>New version of datatables plugin.</li>
                  </ul>
                </td>
            </tr>
            <tr>
                <td>27-Jul-2012</td>
                <td>Upgraded plotting library with selection mode.</td>
            </tr>
            <tr>
                <td>24-Jul-2012</td>
                <td>Some performance improvements in ICS/ILS/DPAS.</td>
            </tr>
            <tr>
                <td>18-Jul-2012</td>
                <td>
                  <ul>
                    <li>Overhaul of the front page (hopefully less scary now ;-) ).</li>
                    <li>Merge the service menu into the main menu.</li>
                    <li>Download scripts for DPAS results.</li>
                  </ul>
                </td>
            </tr>
            <tr>
                <td>05-Jun-2012</td>
                <td>
                  <ul>
                    <li>Added the prototype VOTable plotter.</li>
                    <li>Many bugfixed and cleanup.</li>
                  </ul>
                </td>
            </tr>
            <tr>
                <td>17-May-2012</td>
                <td>Add time range inspector.</td>
            </tr>
            <tr>
                <td>11-May-2012</td>
                <td>
                  <ul>
                    <li>CSS cleanup. Various small bug fixes.</li>
                    <li>Upgrade to Grails 2.0.3.</li>
                  </ul>
                </td>
            </tr>
            <tr>
                <td>29-Feb-2012</td>
                <td>Removing unused code. Reworking the event details dialog. Lots of housekeeping.</td>
            </tr>
            <tr>
                <td>07-Feb-2012</td>
                <td>Added new ICS/ILS GUIS. Changed the way to process VOTables: can handle up to 50'000 records now.</td>
            </tr>
            <tr>
                <td>06-Feb-2012</td>
                <td>Changed DPAS dialog to new style.</td>
            </tr>
            <tr>
                <td>05-Feb-2012</td>
                <td>
                  <ul>
                    <li>Rework of the "Search Event" task. Swapping the data cart.</li>
                    <li>Added extract time and instruments buttons.</li>
                  </ul>
                </td>
            </tr>
            <tr>
                <td>30-Jan-2012</td>
                <td>Added new propagation model. Plot options for GOES plot. TimeRangeDialog shows start time or single time range, if required.</td>
            </tr>
            <tr>
                <td>26-Jan-2012</td>
                <td>Addition of Taverna workflow.</td>
            </tr>
            
          </tbody>
        </table>
                
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
