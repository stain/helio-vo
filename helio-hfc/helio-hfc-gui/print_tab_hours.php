<?php
// Script renvoyant le résultat d'une requete de type un crtitere sur un type de feature
// formats: html, csv, xml (VoTable)
// appel: hfc_criteria_query.php?date=1999-03-09 06:23:02&feat=fil|ar|ch&format=html&map=helio,pixel,flat&crit=pos|len|area|orient
// la date peut inclure l'heure ou non
// feat = fil | ar | ch, format = html | csv | xml
session_start();
require("functions.php");

if (isset($_GET['date'])) $date = $_GET['date'];
else exit(0);
if (isset($_GET['feat'])) $feature_type = explode(',', $_GET['feat']);
else exit(0);
if (isset($_GET['format'])) $output_format = $_GET['format'];
else $output_format = 'html';
if (isset($_GET['map'])) $map = explode(',', $_GET['map']);
else $map = array();

$timestmp = strtotime($date);
$date_array = getdate($timestmp);
$start_day=$date_array['mday']; $start_month=$date_array['mon']; $start_year=$date_array['year'];

// determination du numero de rotation correspondant a la date
// et des parametres du soleil
if (checkdate($start_month, $start_day, $start_year)) {
	$dj = new Datej();
	$dj->initFromCal($start_year, $start_month, $start_day, $date_array['hours'], $date_array['minutes']);
	solphy($dj->dj, $lo, $bo, $po);
	$bid = debutrot($dj->dj, $datedeb, $numrot);
}
else exit(0);
?>
<script type="text/javascript">
$(function(){
<?php
	$feat_list = array('fil', 'pro', 'ar', 'sp', 'ch', 't3', 't2', 'rs');
	foreach($feat_list as $feat) {
		$tab_name = 'tabs_res_'.$feat.$start_year.$start_month.$start_day;
		echo "$('#$tab_name').tabs({";
		echo '
				ajaxOptions: {
					async: true,
					error: function( xhr, status, index, anchor ) {
						$( anchor.hash ).html(
							"Couldn\'t load this tab. We\'ll try to fix this as soon as possible. " +
							"If this wouldn\'t be a demo." );
					}
				}
			});';
	}
?>
});
</script>
<style type="text/css">
	table.tabresults { font: 80% "Trebuchet MS", sans-serif; border-collapse:collapse; width:100%;}
	th.tabresults, td.tabresults { border:1px solid #dbd7d4; background-color:white; width:20%; padding: 3px; text-align: center;}
</style>
<?php
if (count($map)) {
	if (in_array("day_syn_carr", $map)) {
		$script_link = "showmap.php?date=".$date.'&style=heliofull';
		$link = "javascript:void(window.open('".$script_link."','','scrollbars=1,resizable=1,status=1'))";
		echo '<h4>Daily Synoptic map</h4>';
		print "<A href=\"".$link."\">";
		print '<img src="makemap_carr.php?date='.$date.'&zoom=0.5&feat=all" width="200">';
		print "</A>\n";
	}
}
		
$feat_list = array('fil'=>'Filament', 'pro'=>'Prominence', 'ar'=>'Active region', 'sp'=>'Sun spot',
						'ch'=>'Coronal hole', 't3'=>'Type III', 't2'=>'Type II', 'rs'=>'Radio source'
				);

foreach($feature_type as $type) {
	$var_sess = 'date_'.$type;
	// gets all the dates+hours for this $date
	$tab_dates = array_values(array_unique($_SESSION[$var_sess]['DATE_OBS']));
	$tab_dt = array();
	foreach($tab_dates as $dt) {
		$pos = strpos($dt, $date);
		if ($pos !== false) $tab_dt[] = $dt;
	}
	$nbhours = count($tab_dt);
	if ($nbhours) {
		echo '<div id="tabs_res_'.$type.$start_year.$start_month.$start_day.'">'."\n";
		echo '<b>'.$feat_list[$type].'</b>: results per hour at '.$date;
		echo '<ul>'."\n";
		for ($i=0; $i<$nbhours; $i++) {
			$tmp = explode(" ", $tab_dt[$i]);
			$url_content = "print_res_hour.php?date=".$tab_dt[$i]."&feat=".$type."&map=".implode(',', $map);
			echo '<li><a href="'.$url_content.'"><span>'.$tmp[1].'</span></a></li>';
		}
		echo '</ul>'."\n";
		echo '</div>';
	}
	else {
		echo '<p>'.$feat_list[$type].' => ';
		print_why_nothing_found($date, $type);
		echo '</p>';
	}
}

function print_why_nothing_found($date, $type) {
$tab_views = array("fil"=>"VIEW_FIL_GUI", "pro"=>"VIEW_PRO_GUI", "ar"=>"VIEW_AR_GUI", "sp"=>"VIEW_SP_GUI", "ch"=>"VIEW_CH_GUI", "t3"=>"VIEW_T3_GUI", /*"t2"=>"VIEW_T2_GUI",*/ "rs"=>"VIEW_RS_GUI");
	// retreive instrument for this feature
	$sql_query = "SELECT DISTINCT(ID_OBSERVATORY) FROM ".$tab_views[$type];
	$res = execute_query($sql_query);
	$sql_query = "SELECT distinct time(DATE_OBS) AS HOURS FROM OBSERVATIONS WHERE ";
	$sql_query = $sql_query."OBSERVATORY_ID IN (".implode(',', $res['ID_OBSERVATORY']).")";
	$sql_query = $sql_query." AND DATE(DATE_OBS)='".$date."'";
	$res = execute_query($sql_query);

	$nb_obs = count($res['HOURS']);
	if ($nb_obs) {
		print "Observations: ".$nb_obs." at ";
		foreach($res['HOURS'] as $hour) print $hour."&nbsp;";
		print ", but no feature detected or in criteria";
	}
	else print "No observation found at this date";
}
?>
