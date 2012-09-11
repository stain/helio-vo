<?php
// Make a visual page of HFC database content
require("functions.php");

include("header.php");
include('but_menu.php');
?>
<style type="text/css">
	td.default { border:1px solid #dbd7d4; width:20%; padding: 3px; text-align: center;}
</style>
<?php
echo '</head>';
echo '<h5 class="winA">HFC content</h5>';
//echo '<div style="text-align: right;"><span id="but_back_query"><a href="index.php">Back to query form</a></div>';

// Printing global date ranges for each type of feature
$tab_id = array("fil"=>"Filament", "pro"=>"Prominence", "ar"=>"Active region", "ch"=>"Coronal hole", "sp"=>"Sunspot", "t3"=>"Type III", /*"Type II"=>"t2",*/ "rs"=>"Radio source");

//$instru_by_date = get_minmaxdate_by_instrument();
$instru_by_date = get_minmaxdate_for_instrument_code_from_tstat();
//$query = "SELECT * FROM ASSOC_OBSERVATORY_FRC, OBSERVATORY, FRC_INFO WHERE OBSERVATORY_ID=ID_OBSERVATORY AND FRC_INFO_ID=ID_FRC_INFO";
//$instru_by_date = execute_query($query);
//print_r($instru_by_date);
echo '<div class="ui-widget-content ui-corner-all">';
echo '<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>Date range</p>';
echo '<TABLE>'."\n";
echo "<TR>\n";
echo "<TH>Feature</TH><TH>Observatory/Instrument/Telescop</TH><TH>FRC Code</TH><TH>First date</TH><TH>Last date</TH>\n";
echo "</TR>\n";
foreach($tab_id as $feat_type=>$feat_name) {
	foreach($instru_by_date[$feat_type] as $data) {
		if (strlen($data['TELESCOP'][0]))
			$obsname = $data['OBSERVAT'][0].' '.$data['INSTRUME'][0].' '.$data['TELESCOP'][0];
		else
			$obsname = $data['OBSERVAT'][0].' '.$data['INSTRUME'][0].' '.$data['WAVEMIN'][0];
		echo "<TR>\n";
		echo '<TD class="default"><b>'.$feat_name.'</b></TD><TD class="default">'.$obsname."</TD>\n";
		echo '<TD class="default">'.$data['CODE'][0]."</TD>\n";
		echo '<TD class="default">'.$data['MIN_DATE_OBS'][0].'</TD><TD class="default">'.$data['MAX_DATE_OBS'][0]."</TD>\n";
		echo "</TR>\n";
	}
}
echo '</TABLE>'."\n";
echo '</div>';
//echo time()-$start; echo '<br>';

// Details per year
$sql_query = "SELECT MIN(DATE_OBS) AS MINDATE, MAX(DATE_OBS) AS MAXDATE FROM OBSERVATIONS";
$res = execute_query($sql_query);
$mindate = $res['MINDATE'][0];
$maxdate = $res['MAXDATE'][0];
$tmp = getdate(strtotime($mindate));
$minyear = $tmp['year'];
$tmp = getdate(strtotime($maxdate));
$maxyear = $tmp['year'];

// Draw graph bar for global view of the content of HFC
echo '<div class="ui-widget-content ui-corner-all">';
echo '<div><IMG src="hfc_list_entry.php"></div>';
//echo '<IMG src="drawgraph_globalview.php">';
echo '</div>';
// Draw a graph bar for each year
echo '<div class="ui-widget-content ui-corner-all">';
echo '<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>Year distribution</p>';
for ($year=$minyear; $year<=$maxyear; $year++) {
echo '<IMG src="drawgraph_yearview.php?year='.$year.'">';
}
echo '</div>';
?>
		<div class="ui-widget-content ui-corner-all">
			<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span></p>
			<p>The list of the features for which data are currently available in the HFC is given in the following table</p>
			<TABLE>
			<TR><TH>Feature</TH><TH>Instrument</TH><TH>Recognition code</TH><TH>Bibliography</TH><TH>Tracking information</TH></TR>
			<TR><TD class="default">Active Region</TD><TD class="default">SOHO/MDI<BR>SOHO/EIT</TD><TD class="default">SMART<BR>SPOCA-AR</TD><TD class="default">Higgins et al., 2010<BR>Barra et al., 2009</TD><TD class="default">No</TD></TR>
			<TR><TD class="default">Coronal Hole</TD><TD class="default">SOHO/MDI + SOHO/EIT 195 A<BR>SOHO/EIT</TD><TD class="default">CHARM<BR>SPOCA-CH</TD><TD class="default">Krista and Gallagher, 2009<BR>Barra et al., 2009</TD><TD class="default">No</TD></TR>
			<TR><TD class="default">Filament</TD><TD class="default">Meudon H Alpha Spectroheliograph</TD><TD class="default">SoSoft & TrackFil</TD><TD class="default">Fuller et al., 2005 - Bonnin et al., submitted</TD><TD class="default">Yes</TD></TR>
			<TR><TD class="default">Prominence</TD><TD class="default">Meudon CAII K3 Spectroheliograph</TD><TD class="default">SoSoPro</TD><TD class="default">N. Fuller</TD><TD class="default">No</TD></TR>
			<TR><TD class="default">Sunspot</TD><TD class="default">SOHO/MDI<BR>SDO/HMI</TD><TD class="default">MDISS<BR>SDOSS</TD><TD class="default">Zharkov et al., 2005<BR>http://adsabs.harvard.edu/abs/2005SoPh..228..361Z/
</TD><TD class="default">No</TD></TR>
			<TR><TD class="default">Type III</TD><TD class="default">Wind/Waves, STEREO/Swaves</TD><TD class="default">RABAT3</TD><TD class="default">X. Bonnin</TD><TD class="default">No</TD></TR>
			<TR><TD class="default">Coronal radio emission</TD><TD class="default">Nancay Radio Heliograph</TD><TD class="default">NRH2D</TD><TD class="default">C. Reni&eacute;, X. Bonnin</TD><TD class="default">Yes</TD></TR>
			</TABLE>
		</div>
<?php
include("footer.php");
?>