<?php
// Make a visual page of HFC database content for a year
// Parameter: dt=yyyy what=fil|pro|ar|ch|sp|rs|t3

require("functions.php");
include("header.php");

?>
<style type="text/css">
table.padded-table td { 
	padding:1px; 
	}
</style>
<?php

if (isset($_GET['dt'])) $year = $_GET['dt'];
else exit(0);
if (isset($_GET['what'])) $feat_type = $_GET['what'];
else exit(0);
if (isset($_GET['endy'])) $endyear = $_GET['endy'];
else $endyear = $year;
if ($endyear < $year) exit(0);

$cur_year = $year;
$tabyear = array();
while($cur_year <= $endyear) {
	$tabyear[] = $cur_year;
	$cur_year++;
}

$tabmonths = array("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
$tab_id = array("fil"=>"Filament", "pro"=>"Prominence", "ar"=>"Active region", "ch"=>"Coronal hole", "sp"=>"Sunspot", "t3"=>"Type III", /*"Type II"=>"t2",*/ "rs"=>"Radio source");

$t_instrum = get_distinct_instrume($feat_type);
$ids_observatory = $t_instrum['ID_OBSERVATORY'];
foreach($tabyear as $year) {
$tab_results = array();
foreach($ids_observatory as $key=>$id_observatory) {
	$total_year = check_nbfeat_for_year($feat_type, $id_observatory, $year);
	if ($total_year != 0) {
		$observat = $t_instrum['OBSERVAT'][$key].'-'.$t_instrum['INSTRUME'][$key];
		if (strlen($t_instrum['TELESCOP'][$key])) $observat = $observat.'-'.$t_instrum['TELESCOP'][$key];
		else $observat = $observat.'-'.$t_instrum['WAVEMIN'][$key];
		$observat = $observat.' | '.$t_instrum['CODE'][$key];
		foreach ($tabmonths as $month) {
			$dt = $year.'-'.$month;
			date_default_timezone_set('UTC');
			$starttime = strtotime($dt.'-01');
			$endtime = strtotime('+ 1 month', $starttime);
			$bid = getstat_2dates2($feat_type, $id_observatory, $starttime, $endtime);
			$tab_results[$feat_type][$observat][] = $bid;
		}
	}
	echo '<h4>'.$year.' '.$tab_id[$feat_type].' '.$observat.' Total = '.$total_year.'</h4>';
	if ($total_year != 0) print_results($feat_type, $observat, $tab_results, $year);
	flush();
	//echo "<br>\n";
}
}

date_default_timezone_set('Europe/Paris');
echo date("c").' '.$global['MYSQL_HOST'].' '.$global['TBSP'];

function print_results($feat_type, $observat, $tab_results, $year) {
?>
<script type="text/javascript">
$(function(){
	   $('#tab_data<?php echo $observat.$year; ?>').dataTable({
//		"bJQueryUI": true,
		"bPaginate": false,
		"bSort": false,
		"bAutoWidth": false,
		"bFilter": false,
		"bInfo": false
	});
});
</script>
<?php
	echo '<TABLE id="tab_data'.$observat.$year.'" class="padded-table">';
	echo '<THEAD><TR>';
	echo '<TD><B>Month</B></TD>';
	for ($i=1; $i<32; $i++) echo "<TD><B>$i</B></TD>";
		echo '<TD><B>Total</B></TD>';
	echo "</TR></THEAD>\n";
	echo '<TBODY>';
	foreach($tab_results[$feat_type][$observat] as $month=>$res) {
		$nbtot = 0;
		$cmpt = 0;
		echo '<TR><TD><B>'.($month+1)."</B></TD>\n";
		foreach($res as $dt=>$nbfeat) {
			echo '<TD>'.$nbfeat."</TD>\n";
			$nbtot = $nbtot + $nbfeat;
			$cmpt++;
		}
		while ($cmpt < 31) {
			echo '<TD>&nbsp;</TD>';
			$cmpt++;
		}
		echo '<TD><B>'.$nbtot.'</B></TD>';
		echo "</TR>\n";
	}
	echo "</TBODY></TABLE>\n";
}

function check_nbfeat_for_year($feat_type, $id_observatory, $year) {

	$tab_tables = array("fil"=>"FILAMENTS", "pro"=>"PROMINENCES", "ar"=>"ACTIVEREGIONS", "ch"=>"CORONALHOLES", "sp"=>"SUNSPOTS", "t3"=>"TYPE_III", /*"Type II"=>"TYPE_II",*/ "rs"=>"RADIOSOURCES");
	$tab_idname = array("fil"=>"ID_FIL", "pro"=>"ID_PROMINENCE", "ar"=>"ID_AR", "ch"=>"ID_CORONALHOLES", "sp"=>"ID_SUNSPOT", "t3"=>"ID_TYPE_III", /*"Type II"=>"ID_TYPE_II",*/ "rs"=>"ID_RS");

	switch($feat_type) {
		case 'fil':
		case 'pro':
			$qfrom = ", OBSERVATIONS, OBSERVATORY, PP_OUTPUT ";
			$qwhere = "(PP_OUTPUT_ID=ID_PP_OUTPUT) AND (OBSERVATIONS_ID=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
			break;
		case 'ch';
			$qfrom = ", OBSERVATIONS, OBSERVATORY ";
			$qwhere = "(OBSERVATIONS_ID_EIT=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
			break;
		case 'sp';
			$qfrom = ", OBSERVATIONS, OBSERVATORY ";
			$qwhere = "(OBSERVATIONS_ID_IC=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
			break;
		default:
			$qfrom = ", OBSERVATIONS, OBSERVATORY ";
			$qwhere = "(OBSERVATIONS_ID=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
			break;
	}
	$table = $tab_tables[$feat_type];
	$sql_query = "SELECT count(".$tab_idname[$feat_type].") as ID FROM $table";
	$qwherecommon = " WHERE ID_OBSERVATORY=\"".$id_observatory."\" AND YEAR(DATE_OBS)='$year' ";
	$sql_query = $sql_query.$qfrom.$qwherecommon." AND ".$qwhere;
	//echo $sql_query."<br>\n";
	$rs = execute_query($sql_query);

	return $rs['ID'][0];	
}

function getstat_2dates2($feat_type, $id_observatory, $startdate, $enddate) {

	$tab_tables = array("fil"=>"FILAMENTS", "pro"=>"PROMINENCES", "ar"=>"ACTIVEREGIONS", "ch"=>"CORONALHOLES", "sp"=>"SUNSPOTS", "t3"=>"TYPE_III", /*"Type II"=>"TYPE_II",*/ "rs"=>"RADIOSOURCES");
	$tab_idname = array("fil"=>"ID_FIL", "pro"=>"ID_PROMINENCE", "ar"=>"ID_AR", "ch"=>"ID_CORONALHOLES", "sp"=>"ID_SUNSPOT", "t3"=>"ID_TYPE_III", /*"Type II"=>"ID_TYPE_II",*/ "rs"=>"ID_RS");

	$table = $tab_tables[$feat_type];
	//$one_day = new DateInterval('P1D');
	$one_day = 24*3600;

	switch($feat_type) {
		case 'fil':
		case 'pro':
			$qfrom = ", OBSERVATORY, PP_OUTPUT ";
			$qwhere = "(PP_OUTPUT_ID=ID_PP_OUTPUT) AND (OBSERVATIONS_ID=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
			break;
		case 'ch';
			$qfrom = ", OBSERVATORY ";
			$qwhere = "(OBSERVATIONS_ID_EIT=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
			break;
		case 'sp';
			$qfrom = ", OBSERVATORY ";
			$qwhere = "(OBSERVATIONS_ID_IC=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
			break;
		default:
			$qfrom = ", OBSERVATORY ";
			$qwhere = "(OBSERVATIONS_ID=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
			break;
	}
	$sql_query = "SELECT DATE(DATE_OBS) as DATE_OBS FROM OBSERVATIONS, $table";
	$sql_query = $sql_query.$qfrom;
	$qwherecommon = " WHERE ID_OBSERVATORY=\"".$id_observatory."\" AND DATE(DATE_OBS) BETWEEN '".date('Y-m-d', $startdate)."' AND '".date('Y-m-d', $enddate)."' ";
	$query = $sql_query.$qwherecommon." AND ".$qwhere;
	//echo $query."<br>\n";
	$rs = execute_query($query);
	$tab_results = array();
	$cur_date = $startdate;
	while ($cur_date != $enddate) {
		$date_obs = date('Y-m-d', $cur_date);
		$key = array_keys($rs['DATE_OBS'], $date_obs);
		$tab_results[$date_obs] = count($key);
		$cur_date = $cur_date + $one_day;
	}
	/*$cur_date = $rs['DATE_OBS'][0];
	$nbfeat = 0;
	foreach ($rs['DATE_OBS'] as $dt) {
		if ($dt == $cur_date) $nbfeat++;
		else {
			$tab_results[$cur_date] = $nbfeat;
			$cur_date = $dt;
			$nbfeat = 1;
		}
	}*/
	return $tab_results;
}

function getstat_2dates($feat_type, $id_observatory, $startdate, $enddate) {

	$tab_tables = array("fil"=>"FILAMENTS", "pro"=>"PROMINENCES", "ar"=>"ACTIVEREGIONS", "ch"=>"CORONALHOLES", "sp"=>"SUNSPOTS", "t3"=>"TYPE_III", /*"Type II"=>"TYPE_II",*/ "rs"=>"RADIOSOURCES");
	$tab_idname = array("fil"=>"ID_FIL", "pro"=>"ID_PROMINENCE", "ar"=>"ID_AR", "ch"=>"ID_CORONALHOLES", "sp"=>"ID_SUNSPOT", "t3"=>"ID_TYPE_III", /*"Type II"=>"ID_TYPE_II",*/ "rs"=>"ID_RS");

	$table = $tab_tables[$feat_type];
	//$one_day = new DateInterval('P1D');
	$one_day = 24*3600;

	switch($feat_type) {
		case 'fil':
		case 'pro':
			$qfrom = ", OBSERVATIONS, OBSERVATORY, PP_OUTPUT ";
			$qwhere = "(PP_OUTPUT_ID=ID_PP_OUTPUT) AND (OBSERVATIONS_ID=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
			break;
		case 'ch';
			$qfrom = ", OBSERVATIONS, OBSERVATORY ";
			$qwhere = "(OBSERVATIONS_ID_EIT=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
			break;
		case 'sp';
			$qfrom = ", OBSERVATIONS, OBSERVATORY ";
			$qwhere = "(OBSERVATIONS_ID_IC=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
			break;
		default:
			$qfrom = ", OBSERVATIONS, OBSERVATORY ";
			$qwhere = "(OBSERVATIONS_ID=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
			break;
	}
	$sql_query = "SELECT count(".$tab_idname[$feat_type].") as ID FROM $table";
	$sql_query = $sql_query.$qfrom;
	//$cur_date = new DateTime($startdate->format('Y-m-d'), new DateTimeZone('UTC'));
	$cur_date = $startdate;
	while ($cur_date != $enddate) {
		//$date_obs = $cur_date->format('Y-m-d');
		$date_obs = date('Y-m-d', $cur_date);
		$qwherecommon = " WHERE ID_OBSERVATORY=\"".$id_observatory."\" AND DATE(DATE_OBS)='$date_obs' ";
		$query = $sql_query.$qwherecommon." AND ".$qwhere;
		//echo $sql_query."<br>\n";
		$rs = execute_query($query);
		//echo $date_obs.' '.$rs['ID'][0]."<br>\n";
		$tab_results[$date_obs] = $rs['ID'][0];
		$cur_date = $cur_date + $one_day;
		//$cur_date->add($one_day);
	}

	return $tab_results;
}

function get_distinct_instrume($feat_type) {

	$tab_tables = array("fil"=>"FILAMENTS", "pro"=>"PROMINENCES", "ar"=>"ACTIVEREGIONS", "ch"=>"CORONALHOLES", "sp"=>"SUNSPOTS", "t3"=>"TYPE_III", /*"Type II"=>"TYPE_II",*/ "rs"=>"RADIOSOURCES");
	$table = $tab_tables[$feat_type];
	switch($feat_type) {
		case 'fil':
		case 'pro':
			$qfrom = ", OBSERVATIONS, OBSERVATORY, PP_OUTPUT, FRC_INFO ";
			$qwhere = "(PP_OUTPUT_ID=ID_PP_OUTPUT) AND (OBSERVATIONS_ID=ID_OBSERVATIONS) AND (FRC_INFO_ID=ID_FRC_INFO) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
			break;
		case 'ch';
			$qfrom = ", OBSERVATIONS, OBSERVATORY, FRC_INFO ";
			$qwhere = "(OBSERVATIONS_ID_EIT=ID_OBSERVATIONS) AND (FRC_INFO_ID=ID_FRC_INFO) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
			break;
		case 'sp';
			$qfrom = ", OBSERVATIONS, OBSERVATORY, FRC_INFO ";
			$qwhere = "(OBSERVATIONS_ID_IC=ID_OBSERVATIONS) AND (FRC_INFO_ID=ID_FRC_INFO) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
			break;
		default:
			$qfrom = ", OBSERVATIONS, OBSERVATORY, FRC_INFO ";
			$qwhere = "(OBSERVATIONS_ID=ID_OBSERVATIONS) AND (FRC_INFO_ID=ID_FRC_INFO) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
			break;
	}
	$sql_query = "SELECT DISTINCT(ID_OBSERVATORY), OBSERVAT, INSTRUME, TELESCOP, WAVEMIN, CODE FROM $table";
	$sql_query = $sql_query.$qfrom." WHERE ".$qwhere;
	//echo $sql_query."<br>\n";
	$rs = execute_query($sql_query);

	return $rs;
}
?>