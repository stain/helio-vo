<?php
session_start();
if (isset($_GET['id'])) $track_feat_id = $_GET['id'];
else exit(0);
if (isset($_GET['what'])) $feat_type = $_GET['what'];
else exit(0);
if (!in_array($feat_type, array("fil", "rs"))) exit(0);

// Affiche les informations de tracking pour le feature dont l'ID est passé en paramètre
require("functions.php");

include ('header.php');
?>
<script type="text/javascript" src="js/carousel/jcarousellite_1.0.1c5.js"></script>
<style type="text/css">
	table.tabresults { font: 80% "Trebuchet MS", sans-serif; border-collapse:collapse; width:100%;}
	th.tabresults, td.tabresults { border:1px solid #4297d7; background-color:white; width:20%; padding: 3px; text-align: center;}
</style>

<?php
// utilisé par print_diapo
//if (isset($_GET['date'])) $date = $_GET['date'];

//$query = "SELECT v_fil.*, t_trckfil.* FROM VIEW_FIL_GUI v_fil, FILAMENTS_TRACKING t_trckfil WHERE TRACK_ID=".$track_feat_id;
//$query = $query." AND (v_fil.ID_FIL =  t_trckfil.FIL_ID) ORDER BY TRACK_ID ASC";
switch($feat_type) {
	case "fil":
		$query = "SELECT * FROM VIEW_FIL_GUI WHERE TRACK_ID=".$track_feat_id." ORDER BY DATE_OBS ASC";
		$evol_param = array("SKE_LENGTH_DEG", "SKE_ORIENTATION", "FEAT_MEAN2QSUN", "FEAT_AREA_DEG2");
		break;
	case "rs":
		$query = "SELECT * FROM VIEW_RS_GUI WHERE TRACK_ID=".$track_feat_id." ORDER BY DATE_OBS ASC";
		$evol_param = array("FEAT_MAX_INT", "FEAT_AREA_DEG2");
		break;
}
$rs = execute_query($query);
$_SESSION['rs_feat_to_print'] = $rs;

if (count($rs['DATE_OBS']) != 0) {
	echo '<p> Results for feature tracking ID '.$track_feat_id.'</p>';

	$tab_date = array_values(array_unique($rs['DATE_OBS']));
	//$id_curr_date = array_search($date, $tab_date);
	//print_diapo($id_curr_date, $track_feat_id, $tab_date);
	echo '<div class="ui-widget-content ui-corner-all"><center>';
	print_carousel($tab_date, $feat_type);
	echo '</center></div>';
	echo '<div class="ui-widget-content ui-corner-all">';
	print_results2($rs, $feat_type);
	echo '</div>';
/*	echo '<div class="ui-widget-content ui-corner-all">';
	print_results($rs);
	echo '</div>';*/
	if (count($rs['DATE_OBS']) > 1) {
	echo '<div class="ui-widget-content ui-corner-all">';
	
	foreach($evol_param as $field) {
		$link = 'graph_param_feat.php?id='.$track_feat_id.'&feat='.$feat_type.'&param='.$field;
		print '<IMG src="'.$link.'">';
	}
	echo '</div>';
	}
}
else echo 'No result for tracking ID '.$track_feat_id;

include ('footer.php');

/*function print_diapo($id_curr_date, $id_track, $tab_date) {

	$script_link = "showmap.php?date=".$tab_date[$id_curr_date]."&feat=fil&style=pixel&usesess=2";
	$link = "javascript:void(window.open('".$script_link."','','scrollbars=1,resizable=1,status=1'))";
	print "<div><A href=\"".$link."\">";
	print "<IMG src=\"makemap_pixobs.php?date=".$tab_date[$id_curr_date]."&feat=fil&zoom=0.5&usesess=2\" alt=\"Sun map\">";
	print '</A></div>';
	if ($id_curr_date != 0)
		echo '<A href="get_track_info.php?id='.$id_track."&date=".$tab_date[$id_curr_date-1].'">'.$tab_date[$id_curr_date-1].'</A>';
	else echo '<==';
	echo ' || '.$tab_date[$id_curr_date].' || ';
	if ($id_curr_date < count($tab_date))
		echo '<A href="get_track_info.php?id='.$id_track."&date=".$tab_date[$id_curr_date+1].'">'.$tab_date[$id_curr_date+1].'</A>';
	else echo '==>';
}*/

function print_carousel($tab_date, $feat_type) {
?>
<style type="text/css">
<!--
#slidebox{position:relative; border:1px solid #ccc; margin:10px auto;overflow:hidden;}
#slidebox, #slidebox ul {width:600px;height:350px;}
#slidebox, #slidebox ul li{width:310px;height:350px;}
#slidebox ul li{position:relative; left:0; background:#eee; float:left;list-style: none; font-family:Verdana, Geneva, sans-serif; font-size:13px;}
#slidebox .next, #slidebox .previous{position:absolute; z-index:2; display:block; width:21px; height:21px;top:10px;}
#slidebox .next{right:0; margin-right:10px; background:url(js/carousel/slidebox_next.png) no-repeat left top;}
#slidebox .next:hover{background:url(js/carousel/slidebox_next_hover.png) no-repeat left top;}
#slidebox .previous{margin-left:10px; background:url(js/carousel/slidebox_previous.png) no-repeat left top;}
#slidebox .previous:hover{background:url(js/carousel/slidebox_previous_hover.png) no-repeat left top;}
-->
</style>

<script type="text/javascript">
$(function() {
	$("#slidebox").jCarouselLite({
		vertical: false,
		hoverPause:true,
		btnPrev: ".previous",
		btnNext: ".next",
		visible: 3,
		start: 0,
//		scroll: 1,
		circular: false
//		auto:1000,
//		speed:500,
	});
});
</script>
<?php
//	echo '<div class="externalControl">'."\n";
	echo '<div id="slidebox">'."\n";
	echo '<div class="next"></div>'."\n";
	echo '<div class="previous"></div>'."\n";
	echo '<ul>'."\n";
	foreach($tab_date as $date) {
		$date_iso = str_replace(' ', 'T', $date);
		$script_link = "showmap.php?date=".$date."&feat=fil&style=pixel&usesess=2&obs";
		$link = "javascript:void(window.open('".$script_link."','','scrollbars=1,resizable=1,status=1'))";
		print '<li>';
		print '<p>'.$date_iso.'</p>';
		print "<A href=\"".$link."\">";
		print "<IMG src=\"makemap_pixobs.php?date=".$date."&feat=".$feat_type."&zoom=0.5&usesess=2&obs\" alt=\"Sun map\" width=\"300\" height=\"300\">";
		print '</A></li>'."\n";
		//echo '<li>'.$date.'</li>';
	}
	echo '</ul>'."\n";
	echo '</div>'."\n";
}
/*
function print_carousel2($tab_date) {
?>
<script type="text/javascript">
$(function() {
	$("#carousel").jCarouselLite({
    visible: 3,
    start: 0,
	circular: false,
    btnNext: ".next",
    btnPrev: ".prev",
    btnGo:
<?php
	echo "\n[";
	for ($i=1; $i<count($tab_date); $i++) echo '".'.$i.'",';
	echo "]";
?>
	});

});
</script
<?php
//	echo '<div class="externalControl">'."\n";
	echo '<div id="carousel">'."\n";
	echo '<button class="prev"><<</button>';
	for ($i=1; $i<count($tab_date); $i++) {
		$tmp = explode(" ", $tab_date[$i-1]);
		echo '<button class="'.$i.'">'.$tmp[0].'</button>';
	}
	echo '<button class="next">>></button>'."\n";
//	echo '<div class="carousel">'."\n";
	echo '<ul>'."\n";
	foreach($tab_date as $date) {
		$script_link = "showmap.php?date=".$date."&feat=fil&style=pixel&usesess=2";
		$link = "javascript:void(window.open('".$script_link."','','scrollbars=1,resizable=1,status=1'))";
		print "<li><A href=\"".$link."\">";
		print "<IMG src=\"makemap_pixobs.php?date=".$date."&feat=fil&zoom=0.5&usesess=2\" alt=\"Sun map\" width=\"300\" height=\"300\">";
		print '</A></li>'."\n";
	}
	echo '</ul>'."\n";
//	echo '</div>'."\n";
	echo '</div>'."\n";
}
*/
function print_results($rs) {
	$headers = array("Observation date", "Phenomen", "Filament parts IDs", "Total length (dg)", "Orientation (dg)");

	$tab_dates = array();
	echo '<TABLE class="tabresults">';
	echo '<TR class="tabresults">';
	foreach ($headers as $h) echo '<TH class="tabresults">'.$h.'</TH>';
	echo '</TR>';
	foreach ($rs['DATE_OBS'] as $key=>$date_obs) {
		if(!in_array($date_obs, $tab_dates)) {
			$tab_dates[] = $date_obs;
			$date_iso = str_replace(' ', 'T', $date_obs);
			echo '<TR class="tabresults">';
			echo '<TD class="tabresults">'.$date_iso.'</TD>';
			echo '<TD class="tabresults">';
/*			if ($rs['PHENOM'][$key] == 1) echo 'Appearence after the east limb';
			if ($rs['PHENOM'][$key] == 2) echo 'Disappearence before the west limb';
			if ($rs['PHENOM'][$key] == 5) echo 'Disparition brusque';
			if ($rs['PHENOM'][$key] == 6) echo 'Disparition brusque';
			if ($rs['PHENOM'][$key] == 7) echo 'Abnormal behavior';*/
			echo $global['PHENOM'][$rs['PHENOM'][$key]];
			echo '</TD>';
			echo '<TD class="tabresults">';
			echo '| ';
			foreach ($rs['ID_FIL'] as $key2=>$id_fil)
				if (!strcmp($rs['DATE_OBS'][$key2], $date_obs)) echo $rs['ID_FIL'][$key2].' | ';
			echo '</TD>';
			echo '<TD class="tabresults">';
			$total_length = 0;
			foreach ($rs['ID_FIL'] as $key2=>$id_fil)
				if (!strcmp($rs['DATE_OBS'][$key2], $date_obs)) $total_length = $total_length + $rs['SKE_LENGTH_DEG'][$key2];
			echo $total_length;
			echo '</TD>';
			echo '<TD class="tabresults">';
			echo '| ';
			foreach ($rs['ID_FIL'] as $key2=>$id_fil)
				if (!strcmp($rs['DATE_OBS'][$key2], $date_obs)) echo $rs['SKE_ORIENTATION'][$key2].' | ';
			echo '</TD>';
			echo '</TR>';
		}
	}
	echo '</TABLE>';
	
/*	$fields_to_print = array("FEAT_ID", "FEAT_DETEC_ID", "ID_FIL", "PHENOM", "REF_FEAT", "LVL_TRUST", "DATE_OBS", "SC_CAR_LON", "SC_CAR_LAT", "SKE_LEN_DEG");
	echo '<TABLE>';
	echo '<TR>';
	foreach ($fields_to_print as $field) print '<TH>'.$field.'</TH>';
	echo '</TR>';
	for ($i=0; $i<count($rs['ID_FIL_TRACK']); $i++) {
		echo '<TR>';
		foreach ($fields_to_print as $field) {
			if (is_numeric($rs[$field][$i])) $value = round($rs[$field][$i], 2);
			else $value = $rs[$field][$i];
			print '<TD>'.$value.'</TD>';
		}
		echo '</TR>';
	}
	echo '</TABLE>';*/
}

function print_results2($rs, $feat_type) {
	global $global;

	switch($feat_type) {
		case 'fil':
			$headers = array("DATE_OBS"=>"Observation date", "PHENOM"=>"Phenomen");
			$last_header = "Filament component(s)";
			$sub_headers = array("Filament part ID", "Length (dg)", "Orientation (dg)", "Longitude (dg)", "Latitude (dg)");
			$field_to_print = array("ID_FIL", "SKE_LENGTH_DEG", "SKE_ORIENTATION", "FEAT_CARR_LONG_DEG", "FEAT_CARR_LAT_DEG");
			$id_feat_name = 'ID_FIL';
			break;
		case 'rs':
			$headers = array("DATE_OBS"=>"Observation date");
			$last_header = "Radio source parameters";
			$sub_headers = array("RS ID", "Max intensity (units)", "Area (dg)", "Longitude (dg)", "Latitude (dg)");
			$field_to_print = array("ID_RS", "FEAT_MAX_INT", "FEAT_AREA_DEG2", "FEAT_CARR_LONG_DEG", "FEAT_CARR_LAT_DEG");
			$id_feat_name = 'ID_RS';
			break;
	}
	$tab_dates = array();
	echo '<TABLE class="tabresults">';
	echo '<TR class="tabresults">';
	foreach($headers as $hd) echo '<TH class="tabresults">'.$hd.'</TH>';
	echo '<TH class="tabresults">'.$last_header;
	/*echo '<TH class="tabresults">'."Observation date".'</TH>';
	echo '<TH class="tabresults">'."Phenomen".'</TH>';
	echo '<TH class="tabresults">'."Filament component(s)";*/
	echo '<TABLE class="tabresults">';
	echo '<TR class="tabresults">';
	foreach($sub_headers as $h) echo '<TH class="tabresults">'.$h.'</TH>';
	echo '</TR>';
	echo '</TABLE>';
	echo '</TH>';
	echo '</TR>';
	foreach ($rs['DATE_OBS'] as $key=>$date_obs) {
		if(!in_array($date_obs, $tab_dates)) {
			$tab_dates[] = $date_obs;
			$date_iso = str_replace(' ', 'T', $date_obs);
			echo '<TR class="tabresults">';
			foreach($headers as $field=>$hd) 
				if ($field === "DATE_OBS") echo '<TD class="tabresults">'.$date_iso.'</TD>';
				else if ($field === "PHENOM") echo '<TD class="tabresults">'.$global['PHENOM'][$rs['PHENOM'][$key]].'</TD>';
				else echo '<TD class="tabresults">'.$rs[$field][$key].'</TD>';
			/*echo '<TD class="tabresults">';
			echo $global['PHENOM'][$rs['PHENOM'][$key]];
			echo '</TD>';*/
			echo '<TD class="tabresults">';
			// tableau contenant les infos sur les differentes parties du feature
			echo '<TABLE class="tabresults">';
			/*echo '<TR class="tabresults">';
				foreach($sub_headers as $h) echo '<TH class="tabresults">'.$h.'</TH>';
			echo '</TR>';*/
			foreach ($rs[$id_feat_name] as $key2=>$id_fil)
				if (!strcmp($rs['DATE_OBS'][$key2], $date_obs)) {
					echo '<TR class="tabresults">';
					foreach($field_to_print as $field) echo '<TD class="tabresults">'.$rs[$field][$key2].'</TD>';
					echo '</TR>';
				}
			echo '</TABLE>';
			echo '</TD>';
			echo '</TR>';
		}
	}
	echo '</TABLE>';
}
?>
