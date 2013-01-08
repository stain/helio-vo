<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tutorial 1: Search for Events</title>
</head>
<body>
  <h2>Tutorial: Search for Events</h2>
  <p>
    <img width="70%" src="hfe_tutorial_v0.1-7-6_1.png"><br /> Figure 1: The HFE welcome screen
  </p>

  <ol start="1" type="1">
    <li>Click on "Search Events"</li>
  </ol>
  <p>
    <img width="70%" src="hfe_tutorial_v0.1-7-7_1.png" /><br /> Figure 2: The empty form to search the HELIO event lists
  </p>


  <h3>
    <a name="_Toc204246075">Select time range</a>
  </h3>

  <ol start="2" type="1">
    <li>Click on the <img height="16" style="margin: 0px; padding: 0px; border: none" src="hfe_tutorial_v0.1-7-7_2.png" />-icon or on the "Select" button
      below or in the white area next to it to select a time range.
    </li>
  </ol>

  <p>
    <img width="70%" src="hfe_tutorial_v0.1-7-7_3.png" /><br /> Figure 3 Area to enter date ranges. Depending on the selected task only a start date can be
    entered. Click on icon 'A' to popup the calendar to enter a date. Button 'B' allows adding another time range, i.e. to cover multiple events in one query.
    Icon 'C' allows removing a time range and is active only if there is more than one time range. Click on icon 'D' to open the date range inspector for the
    selected data range line. See below for more information.
  </p>

  <ol start="3" type="1">
    <li>Click the magnifying glass ('D') on the right side of a time range. This will open the time range inspector, which provides a collection of tools
      to qualify and narrow a given time range.</li>
    <ol start="1" type="a">
      <li>Select one of the tabs. Click on the link to inspect the current time range.</li>
    </ol>
  </ol>
  <table border="1" cellpadding="0" cellspacing="0" style="width:600px;">
    <tbody>
      <tr>
        <td valign="top"><img width="300" src="hfe_tutorial_v0.1-7-8_1.png"/></td>
        <td valign="top"><img width="300" src="hfe_tutorial_v0.1-7-8_2.png"/></td>
      </tr>

      <tr>
        <td valign="top"><img width="300" src="hfe_tutorial_v0.1-7-8_3.png"/></td>
        <td valign="top"><img width="300" src="hfe_tutorial_v0.1-7-8_4.png"/></td>
      </tr>
    </tbody>
  </table>
  <p>
    Figure 4: Snapshot of different date inspector tools for the time range analysis
  </p>

  <ol start="3" type="1">
    <ol start="2" type="a">
      <li>Adjust the time period on the top of the dialog. You can click the small + and - icons to add and remove 6 hours.</li>
      <li>Click again on the link to refresh the page</li>
      <li>Nearby: you can click on an image to load it in a new window.</li>
    </ol>
    <li>Click the 'Ok' button to accept the adjusted time range in your query dialog. Click 'cancel' to keep the original value.</li>
  </ol>
 
  <p><img width="70%" src="hfe_tutorial_v0.1-7-9_1.png"><br/>
  Figure 5: Sample of an adjusted GOES proton plot. (The yellow popup is shown to advertise the HFE-click-on-image&trade; functionality :-) ).</p>

  <ol start="5" type="1">
    <li>Click Ok to close the data range dialog. This will update the summary box on your main screen.</li>
  </ol>
  
  <p><img width="70%" src="hfe_tutorial_v0.1-7-9_2.png"><br/>
 Figure 6: Summary box with the selected date range</p>

  <h3>
    <a name="_Toc204246076">Select Event list</a>
  </h3>

  <ol start="6" type="1">
    <li>Now click on the Event list icon, the "Select"-button below or the white rectangle on the right side to open the Event list dialog</li>
  </ol>
  <p><img width="70%" src="hfe_tutorial_v0.1-7-10_1.png"/><br/>
  Figure 7: Event list selection dialog. Use the Checkboxes on top to filter the list. Click the list items to select one. Click on the x in the right
  side panel to remove an item from the current selection.</p>

  <ol start="7" type="1">
    <li>Click "ok" to accept your selection</li>
  </ol>
  <p><img width="70%" src="hfe_tutorial_v0.1-7-10_2.png"><br/>
  Figure 8: The task is ready to be submitted</p>

  <ol start="8" type="1">
    <li>Click the "Submit"-button. This sends the query to HELIO and waits for the result. The result is a VOTable which is visualised in the HFE.</li>
  </ol>
  <p><img width="70%" src="hfe_tutorial_v0.1-7-11_1.png"><br/>
  Figure 9: Result from a query to the NOAA Energetic Event list and the RHESSI HXR Flare list</p>
  
  <h3>
    <a name="_Toc204246077">Analyse event list results</a>
  </h3>

  <ol start="9" type="1">
    <li>The result view presents various tools to analyse the retrieved data. The main goal is to extract narrowed time ranges that are of interest.
    <ol start="1" type="a">
      <li>Click on the table header to sort the data. Clicking twice on "xray_class will first list the X, then the M, the C, the B, the A and finally list
        the uncategorized flares.</li>
      <li>The "Search" box on the top right of the result table allows to filter the table by some text. In the current sample entering 'X' will present
        the only X2.2-Flare in this list.</li>
      <li>Clicking on the magnifying glass will bring up the date inspector (see Figure 4). The date range of the event gets automatically expanded by 6
        hours on both sides. This is because the time range for most of the lists is too narrow.</li>
    </ol>
    </li>
    <li>Once the events of interest are identified they can be selected and the time ranges cab be extracted.</li>
  </ol>
  
  <p>
    <img width="70%" src="hfe_tutorial_v0.1-7-12_1.png"><br/>
    Figure 10: Selected events in the NOAA Energetic Event list. The list is sorted by "xray_class"</p>

  <ol start="11" type="1">
    <li>Click on the extract icon (<img height="16" src="hfe_tutorial_v0.1-7-12_2.png">) to add the selected date ranges to the "data cart". See next section to learn more about the data cart.</li>
    <li>An alternative to analyse the data is the plotter. Currently it is in prototype status and not any further described here.</li>
  </ol>
  
  <p>
  <img width="70%" src="hfe_tutorial_v0.1-7-12_3.png"><br/>
  Figure 11: Plot of the results from the RHESSI HXR Flare list</p>
  <p></p>
  <p></p>
  <p></p>
  <p></p>
</body>
</html>