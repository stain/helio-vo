<?php
session_start();

include("header.php");
include('but_menu.php');
?>
		<script type="text/javascript" src="common_result.js"></script>
		<style type="text/css">
			.ui-tabs .ui-tabs-nav li a { float: left; padding: .2em 0.2em; text-decoration: none; }
		</style>
<!--	</head> -->
	<body>
<?php
require("functions.php");

//$_SESSION['par_post'] = $_POST;
if (isset($_GET['numpage'])) $numpage = $_GET['numpage'];
else {
	$_SESSION['par_post'] = $_POST;
	$numpage = 1;
}

//print_r($_POST);

// Initial check
// At least one feature is selected
if (isset($_SESSION['par_post']['features']) && count($_SESSION['par_post']['features'])) {
	//echo 'ok: at least one feature';
} else {
	print_error_message("No feature selection!");
	exit(0);
}
// Check if there is a date selection
if (strlen($_SESSION['par_post']['from']) == 0) $_SESSION['par_post']['ignore_date'] = 'on';
if (isset($_SESSION['par_post']['date_sample'])) unset($_SESSION['par_post']['ignore_date']);

// Check date selection only if 'ignore date selection' is not checked and no date sample
if (!isset($_SESSION['par_post']['ignore_date']) && !isset($_SESSION['par_post']['date_sample'])) {
	if (strlen($_SESSION['par_post']['from']) && strlen($_SESSION['par_post']['to'])) $date_is_there = true;
	$ustamp_begin = strtotime($_SESSION['par_post']['from']);
	$ustamp_end = strtotime($_SESSION['par_post']['to']);
	$date_begin = getdate($ustamp_begin);
	$date_end = getdate($ustamp_end);

	// check format and value
	$tst_chk_dates = (checkdate($date_begin['mon'], $date_begin['mday'], $date_begin['year'])) && (checkdate($date_end['mon'], $date_end['mday'], $date_end['year']));
	if ($date_is_there && $tst_chk_dates) {
		//echo 'date format et value are ok'."<BR>\n";
	} else {
		print_error_message("date format or value are Nok!");
		exit(0);
	}
	// check if date_begin is smaller or equal to date_end
	if (($ustamp_end - $ustamp_begin) >=0) {
	//	echo 'from is before to: ok!'."<BR>\n";
	} else {
		print_error_message("from is after to!");
		exit(0);
	}
	// check interval between start and end dates
	if (($ustamp_end - $ustamp_begin) > 60*24*60*60) {
		$ustamp_end = $ustamp_begin + 60*24*60*60;
		$_SESSION['par_post']['to'] = strftime('%Y-%m-%d %H:%M:%S', $ustamp_end);
		$_SESSION['par_post']['duration'] = 60;
	}
	// check if duration < 60 days
	if ((abs($_SESSION['par_post']['duration'])) <= 60) {
	//	echo 'from is before to: ok!'."<BR>\n";
	} else {
		$_SESSION['par_post']['duration'] = 60;
		print_error_message("Duration exceed 2 months!");
		exit(0);
	}
}

if (!check_param_criteria()) {
	//print_error_message("Something wrong for criteria specification. Return to query form.");
	exit(0);
}
$start = time();
query_date_obs();
//echo time()-$start;
print_date_results($numpage);
print_query_sameup($message);
print_sql_query();

include("footer.php");

function query_date_obs() {
	// date selection part of the query
	if (!isset($_SESSION['par_post']['ignore_date'])) {
		if (isset($_SESSION['par_post']['date_sample'])) {
			foreach($_SESSION['par_post']['date_sample'] as $dt) $tab_dt[] = "'".$dt."'";
			$in_dates = implode(',', $tab_dt);
			$query_date = "(DATE(DATE_OBS) IN (".$in_dates.")) ";
		} else {
			$query_date = "(DATE(DATE_OBS) BETWEEN ";
			$query_date = $query_date."DATE('".$_SESSION['par_post']['from']."') AND DATE('".$_SESSION['par_post']['to']."')) ";
		}
		//echo "date=$query_date<br>\n";
	}
	// region/position part of the query
	if (is_numeric($_SESSION['par_post']['region']['minlat'])) {
		$minlat = $_SESSION['par_post']['region']['minlat']; $maxlat = $_SESSION['par_post']['region']['maxlat'];
		$minlon = $_SESSION['par_post']['region']['minlon']; $maxlon = $_SESSION['par_post']['region']['maxlon'];
		$query_region = "(FEAT_CARR_LAT_DEG BETWEEN ".$minlat." AND ".$maxlat;
		if (strcmp($_SESSION['par_post']['region']['symmetric'], "on") == 0) {
			if (($minlat*$maxlat) >= 0) {
				$query_region = $query_region." OR FEAT_CARR_LAT_DEG BETWEEN ";
				$query_region = $query_region.(-1*$maxlat)." AND ".(-1*$minlat);
			}
		}
		$query_region = $query_region.")";
		if (is_numeric($_SESSION['par_post']['region']['minlon']))
			$query_region = $query_region." AND (FEAT_CARR_LONG_DEG BETWEEN ".$minlon." AND ".$maxlon.')';
		//echo "region=$query_region<br>\n";

// temporary for AR
		$query_region_ar = "(FEAT_HG_LAT_DEG BETWEEN ".$minlat." AND ".$maxlat;
		if (strcmp($_SESSION['par_post']['region']['symmetric'], "on") == 0) {
			if (($minlat*$maxlat) >= 0) {
				$query_region_ar = $query_region_ar." OR FEAT_HG_LAT_DEG BETWEEN ";
				$query_region_ar = $query_region_ar.(-1*$maxlat)." AND ".(-1*$minlat);
			}
		}
		$query_region_ar = $query_region_ar.")";
		if (is_numeric($_SESSION['par_post']['region']['minlon']))
			$query_region_ar = $query_region_ar." AND (FEAT_HG_LONG_DEG BETWEEN ".$minlon." AND ".$maxlon.')';
		//$tab_where_ar[] = $query_region;
	}

	$rs_fil = array();
	if (in_array("fil", $_SESSION['par_post']['features'])) {
		$tab_where_feat = array();
		if (isset($query_date)) $tab_where_feat[] = $query_date;
		if (isset($query_region)) $tab_where_feat[] = $query_region;
		// check if query is on specific observatory
		if (isset($_SESSION['par_post']['obs_fil'])) {
			$query_obs = '(ID_OBSERVATORY IN ('.(implode(',', $_SESSION['par_post']['obs_fil'])).'))';
			$tab_where_feat[] = $query_obs;
			//echo "observatory=$query_obs<br>\n";
		}
		// check if query has criteria
		if (strcmp($_SESSION['par_post']['fil_criter_select'], "None") != 0) {
			switch ($_SESSION['par_post']['fil_criter_select']) {
				case "Length":
					$query_crit = "(SKE_LENGTH_DEG BETWEEN ".$_SESSION['par_post']['fil_length_par']['min']." AND ".$_SESSION['par_post']['fil_length_par']['max'].')';
					break;
				case "Orientation":
					$query_crit = "(SKE_ORIENTATION BETWEEN ".$_SESSION['par_post']['fil_orientation_par']['min']." AND ".$_SESSION['par_post']['fil_orientation_par']['max'].')';
					break;
				case "Area":
					$query_crit = "(FEAT_AREA_DEG2 BETWEEN ".$_SESSION['par_post']['fil_area_par']['min']." AND ".$_SESSION['par_post']['fil_area_par']['max'].')';
					break;
				case "Disappearance":
					$query_crit = "(PHENOM=5)";
					break;
			}
			$tab_where_feat[] = $query_crit;
			//echo "criteria=$query_crit<br>\n";
		}
		//$sql_query = "SELECT v_fil.DATE_OBS FROM VIEW_FIL_GUI v_fil, FILAMENTS_TRACKING t_trckfil ";
		$sql_query = "SELECT DATE_OBS FROM VIEW_FIL_GUI ";
		$query_cond = implode(' AND ', $tab_where_feat);
		$query_where = "WHERE ".$query_cond;
		/*if (strlen($query_cond))
			$sql_query = $sql_query.$query_where." AND (v_fil.ID_FIL =  t_trckfil.FIL_ID) ORDER BY DATE_OBS ASC";
		else
			$sql_query = $sql_query." WHERE (v_fil.ID_FIL =  t_trckfil.FIL_ID) ORDER BY DATE_OBS ASC";*/
		if (strlen($query_cond))
			$sql_query = $sql_query.$query_where." ORDER BY DATE_OBS ASC";
		else
			$sql_query = $sql_query." ORDER BY DATE_OBS ASC";
		//print_r($sql_query); echo "<BR>\n";
		$rs_fil = execute_query($sql_query);
		//$_SESSION['query']['fil'] = str_replace("SELECT v_fil.DATE_OBS", "SELECT v_fil.*, t_trckfil.*", $sql_query);
		$_SESSION['query']['fil'] = str_replace("SELECT DATE_OBS", "SELECT *", $sql_query);
		$_SESSION['query']['fil'] = str_replace("ORDER BY DATE_OBS", "ORDER BY TRACK_ID", $_SESSION['query']['fil']);
		$nb_feat = count($rs_fil['DATE_OBS']);
		/*if ($nb_feat == 0) {
			$sql_query = "SELECT DATE_OBS FROM VIEW_FIL_GUI ";
			if (strlen($query_cond)) $sql_query = $sql_query.$query_where."ORDER BY DATE_OBS ASC";
			else $sql_query = $sql_query." ORDER BY DATE_OBS ASC";
			//print_r($sql_query); echo "<BR>\n";
			$rs_fil = execute_query($sql_query." LIMIT 8000");
			$nb_feat = count($rs_fil['ID_FIL']);
			$_SESSION['query']['fil'] = str_replace("SELECT DATE_OBS", "SELECT *", $sql_query);
		}*/
		if ($nb_feat == 8000) print_error_message("Query limited to 8000 results!");
	}
	$_SESSION['date_fil'] = $rs_fil;

	$rs_pro = array();
	if (in_array("pro", $_SESSION['par_post']['features'])) {
		$tab_where_feat = array();
		if (isset($query_date)) $tab_where_feat[] = $query_date;
		// check if query is on specific observatory
		if (isset($_SESSION['par_post']['obs_pro'])) {
			$query_obs = '(ID_OBSERVATORY IN ('.(implode(',', $_SESSION['par_post']['obs_pro'])).'))';
			$tab_where_feat[] = $query_obs;
			//echo "observatory=$query_obs<br>\n";
		}
		// check if query has criteria
		if (strcmp($_SESSION['par_post']['pro_criter_select'], "None") != 0) {
			switch ($_SESSION['par_post']['pro_criter_select']) {
				case "Height":
					$query_crit = "(FEAT_HEIGHT_KM BETWEEN ".$_SESSION['par_post']['pro_height_par']['min']." AND ".$_SESSION['par_post']['pro_height_par']['max'].')';
					break;
				case "Delta latitude":
					$query_crit = "(DELTA_LAT_DEG BETWEEN ".$_SESSION['par_post']['pro_delta_lat_par']['min']." AND ".$_SESSION['par_post']['pro_delta_lat_par']['max'].')';
					break;
				case "Max. intensity":
					$query_crit = "(FEAT_MAX_INT BETWEEN ".$_SESSION['par_post']['pro_maxint_par']['min']." AND ".$_SESSION['par_post']['pro_maxint_par']['max'].')';
					break;
			}
			$tab_where_feat[] = $query_crit;
			//echo "criteria=$query_crit<br>\n";
		}
		$sql_query = "SELECT DATE_OBS FROM VIEW_PRO_GUI ";
		$query_cond = implode(' AND ', $tab_where_feat);
		$query_where = "WHERE ".$query_cond;
		if (strlen($query_cond)) $sql_query = $sql_query.$query_where." ORDER BY DATE_OBS ASC";
		else $sql_query = $sql_query." ORDER BY DATE_OBS ASC";
			//print_r($sql_query); echo "<BR>\n";
		$rs_pro = execute_query($sql_query);
		$_SESSION['query']['pro'] = str_replace("SELECT DATE_OBS", "SELECT *", $sql_query);
		$nb_feat = count($rs_pro['ID_PROMINENCE']);
		if ($nb_feat == 8000) print_error_message("Query limited to 8000 results!");
	}
	$_SESSION['date_pro'] = $rs_pro;

	$rs_ar = array();
	if (in_array("ar", $_SESSION['par_post']['features'])) {
		$tab_where_feat = array();
		if (isset($query_date)) $tab_where_feat[] = $query_date;
		if (isset($query_region_ar)) $tab_where_feat[] = $query_region_ar;
		// check if query is on specific observatory
		if (isset($_SESSION['par_post']['obs_ar'])) {
			$query_obs = '(ID_OBSERVATORY IN ('.(implode(',', $_SESSION['par_post']['obs_ar'])).'))';
			$tab_where_feat[] = $query_obs;
			//echo "observatory=$query_obs<br>\n";
		}
		// check if query has criteria
		if (strcmp($_SESSION['par_post']['ar_criter_select'], "None") != 0) {
			switch ($_SESSION['par_post']['ar_criter_select']) {
				case "Area":
					$query_crit = "(FEAT_AREA_DEG2 BETWEEN ".$_SESSION['par_post']['ar_area_par']['min']." AND ".$_SESSION['par_post']['ar_area_par']['max'].')';
					break;
				case "Max. intensity":
					$query_crit = "(FEAT_MAX_INT BETWEEN ".$_SESSION['par_post']['ar_maxint_par']['min']." AND ".$_SESSION['par_post']['ar_maxint_par']['max'].')';
					break;
			}
			$tab_where_feat[] = $query_crit;
			//echo "criteria=$query_crit<br>\n";
		}
		$sql_query = "SELECT DATE_OBS FROM VIEW_AR_GUI ";
		$query_cond = implode(' AND ', $tab_where_feat);
		$query_where = "WHERE ".$query_cond;
		if (strlen($query_cond)) $sql_query = $sql_query.$query_where." ORDER BY DATE_OBS ASC";
		else $sql_query = $sql_query." ORDER BY DATE_OBS ASC";
			//print_r($sql_query); echo "<BR>\n";
		$rs_ar = execute_query($sql_query);
		$_SESSION['query']['ar'] = str_replace("SELECT DATE_OBS", "SELECT *", $sql_query);
		$nb_feat = count($rs_pro['ID_AR']);
		if ($nb_feat == 8000) print_error_message("Query limited to 8000 results!");
	}
	$_SESSION['date_ar'] = $rs_ar;

	$rs_ch = array();
	if (in_array("ch", $_SESSION['par_post']['features'])) {
		$tab_where_feat = array();
		if (isset($query_date)) $tab_where_feat[] = $query_date;
		if (isset($query_region)) $tab_where_feat[] = $query_region;
		// check if query is on specific observatory
		if (isset($_SESSION['par_post']['obs_ch'])) {
			$query_obs = '(ID_OBSERVATORY IN ('.(implode(',', $_SESSION['par_post']['obs_ch'])).'))';
			$tab_where_feat[] = $query_obs;
			//echo "observatory=$query_obs<br>\n";
		}
		// check if query has criteria
		if (strcmp($_SESSION['par_post']['ch_criter_select'], "None") != 0) {
			switch ($_SESSION['par_post']['ch_criter_select']) {
				case "Area":
					$query_crit = "(FEAT_AREA_DEG2 BETWEEN ".$_SESSION['par_post']['ch_area_par']['min']." AND ".$_SESSION['par_post']['ch_area_par']['max'].')';
					break;
				case "Width":
					$query_crit = "(FEAT_WIDTH_HG_LONG_DEG BETWEEN ".$_SESSION['par_post']['ch_width_par']['min']." AND ".$_SESSION['par_post']['ch_width_par']['max'].')';
					break;
			}
			$tab_where_feat[] = $query_crit;
			//echo "criteria=$query_crit<br>\n";
		}
		$sql_query = "SELECT DATE_OBS FROM VIEW_CH_GUI ";
		$query_cond = implode(' AND ', $tab_where_feat);
		$query_where = "WHERE ".$query_cond;
		if (strlen($query_cond)) $sql_query = $sql_query.$query_where." ORDER BY DATE_OBS ASC";
		else $sql_query = $sql_query." ORDER BY DATE_OBS ASC";
			//print_r($sql_query); echo "<BR>\n";
		$rs_ch = execute_query($sql_query);
		$_SESSION['query']['ch'] = str_replace("SELECT DATE_OBS", "SELECT *", $sql_query);
		$nb_feat = count($rs_ch['ID_CORONALHOLES']);
		if ($nb_feat == 8000) print_error_message("Query limited to 8000 results!");
	}
	$_SESSION['date_ch'] = $rs_ch;

	$rs_sp = array();
	if (in_array("sp", $_SESSION['par_post']['features'])) {
		$tab_where_feat = array();
		if (isset($query_date)) $tab_where_feat[] = $query_date;
		if (isset($query_region)) $tab_where_feat[] = $query_region;
		// check if query is on specific observatory
		if (isset($_SESSION['par_post']['obs_sp'])) {
			$query_obs = '(ID_OBSERVATORY IN ('.(implode(',', $_SESSION['par_post']['obs_sp'])).'))';
			$tab_where_feat[] = $query_obs;
			//echo "observatory=$query_obs<br>\n";
		}
		// check if query has criteria
		if (strcmp($_SESSION['par_post']['sp_criter_select'], "None") != 0) {
			switch ($_SESSION['par_post']['sp_criter_select']) {
				case "Area":
					$query_crit = "(FEAT_AREA_DEG2 BETWEEN ".$_SESSION['par_post']['sp_area_par']['min']." AND ".$_SESSION['par_post']['sp_area_par']['max'].')';
					break;
			}
			$tab_where_feat[] = $query_crit;
			//echo "criteria=$query_crit<br>\n";
		}
		$tab_where_feat[] = "(FEAT_DIAM_DEG>0)";
		$sql_query = "SELECT DATE_OBS FROM VIEW_SP_GUI ";
		$query_cond = implode(' AND ', $tab_where_feat);
		$query_where = "WHERE ".$query_cond;
		if (strlen($query_cond)) $sql_query = $sql_query.$query_where." ORDER BY DATE_OBS ASC";
		else $sql_query = $sql_query." ORDER BY DATE_OBS ASC";
			//print_r($sql_query); echo "<BR>\n";
		$rs_sp = execute_query($sql_query);
		$_SESSION['query']['sp'] = str_replace("SELECT DATE_OBS", "SELECT *", $sql_query);
		$nb_feat = count($rs_sp['ID_SUNSPOT']);
		if ($nb_feat == 8000) print_error_message("Query limited to 8000 results!");
	}
	$_SESSION['date_sp'] = $rs_sp;

	$rs_t3 = array();
	if (in_array('t3', $_SESSION['par_post']['features'])) {
		$tab_where_feat = array();
		if (isset($query_date)) $tab_where_feat[] = $query_date;
		// check if query is on specific observatory
		if (isset($_SESSION['par_post']['obs_t3'])) {
			$query_obs = '(ID_OBSERVATORY IN ('.(implode(',', $_SESSION['par_post']['obs_t3'])).'))';
			$tab_where_feat[] = $query_obs;
			//echo "observatory=$query_obs<br>\n";
		}
		// check if query has criteria
		if (strcmp($_SESSION['par_post']['t3_criter_select'], "None") != 0) {
			switch ($_SESSION['par_post']['t3_criter_select']) {
				case "Max. intensity":
					$query_crit = "(FEAT_MAX_INT BETWEEN ".$_SESSION['par_post']['t3_maxint_par']['min']." AND ".$_SESSION['par_post']['t3_maxint_par']['max'].')';
					break;
			}
			$tab_where_feat[] = $query_crit;
			//echo "criteria=$query_crit<br>\n";
		}
		$sql_query = "SELECT DATE_OBS FROM VIEW_T3_GUI ";
		$query_cond = implode(' AND ', $tab_where_feat);
		$query_where = "WHERE ".$query_cond;
		if (strlen($query_cond)) $sql_query = $sql_query.$query_where." ORDER BY DATE_OBS ASC";
		else $sql_query = $sql_query." ORDER BY DATE_OBS ASC";
			//print_r($sql_query); echo "<BR>\n";
		$rs_t3 = execute_query($sql_query);
		$_SESSION['query']['t3'] = str_replace("SELECT DATE_OBS", "SELECT *", $sql_query);
		$nb_feat = count($rs_t3['ID_TYPE_III']);
		if ($nb_feat == 8000) print_error_message("Query limited to 8000 results!");
	}
	$_SESSION['date_t3'] = $rs_t3;

	$rs_t2 = array();
	if (in_array('t2', $_SESSION['par_post']['features'])) {
		$tab_where_feat = array();
		if (isset($query_date)) $tab_where_feat[] = $query_date;
		// check if query is on specific observatory
		if (isset($_SESSION['par_post']['obs_t2'])) {
			$query_obs = '(ID_OBSERVATORY IN ('.(implode(',', $_SESSION['par_post']['obs_t2'])).'))';
			$tab_where_feat[] = $query_obs;
			//echo "observatory=$query_obs<br>\n";
		}
		$sql_query = "SELECT DATE_OBS FROM VIEW_T2_GUI ";
		$query_cond = implode(' AND ', $tab_where_feat);
		$query_where = "WHERE ".$query_cond;
		if (strlen($query_cond)) $sql_query = $sql_query.$query_where." ORDER BY DATE_OBS ASC";
		else $sql_query = $sql_query." ORDER BY DATE_OBS ASC";
			//print_r($sql_query); echo "<BR>\n";
		$rs_t2 = execute_query($sql_query);
		$_SESSION['query']['t2'] = str_replace("SELECT DATE_OBS", "SELECT *", $sql_query);
		$nb_feat = count($rs_t2['ID_TYPE_II']);
		if ($nb_feat == 8000) print_error_message("Query limited to 8000 results!");
	}
	$_SESSION['date_t2'] = $rs_t2;

	$rs_rs = array();
	if (in_array("rs", $_SESSION['par_post']['features'])) {
		$tab_where_feat = array();
		if (isset($query_date)) $tab_where_feat[] = $query_date;
		if (isset($query_region)) $tab_where_feat[] = $query_region;
		// check if query is on specific observatory
		if (isset($_SESSION['par_post']['obs_rs'])) {
			$query_obs = '(ID_OBSERVATORY IN ('.(implode(',', $_SESSION['par_post']['obs_rs'])).'))';
			$tab_where_feat[] = $query_obs;
			//echo "observatory=$query_obs<br>\n";
		}
		$sql_query = "SELECT DATE_OBS FROM VIEW_RS_GUI ";
		$query_cond = implode(' AND ', $tab_where_feat);
		$query_where = "WHERE ".$query_cond;
		if (strlen($query_cond)) $sql_query = $sql_query.$query_where." ORDER BY DATE_OBS ASC";
		else $sql_query = $sql_query." ORDER BY DATE_OBS ASC";
			//print_r($sql_query); echo "<BR>\n";
		$rs_rs = execute_query($sql_query);
		$_SESSION['query']['rs'] = str_replace("SELECT DATE_OBS", "SELECT *", $sql_query);
		$nb_feat = count($rs_rs['ID_RS']);
		if ($nb_feat == 8000) print_error_message("Query limited to 8000 results!");
	}
	$_SESSION['date_rs'] = $rs_rs;
}

function print_numberof_results() {
	$feat_list = array('fil'=>'Filament', 'pro'=>'Prominence', 'ar'=>'Active region', 'sp'=>'Sun spot',
						'ch'=>'Coronal hole', 't3'=>'Type III', 't2'=>'Type II', 'rs'=>'Radio source'
				);
	echo '<div class="ui-widget">'."\n";
	echo '<div class="ui-widget-content ui-corner-all" style="margin-top: 5px; padding: 0 .7em;">'."\n";
	echo '<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>';
	echo 'Number of features retrieved:';
	$total = 0;
	foreach($feat_list as $feat_type=>$feat_name) {
		$var_sess = 'date_'.$feat_type;
		$nbres = count($_SESSION[$var_sess]['DATE_OBS']);
		$total = $total + $nbres;
		if ($nbres) {
			echo ' | <b>'.$feat_name.'</b>: '.$nbres;
			if (in_array("csv",$_SESSION['par_post']['output_format']))
				echo '&nbsp;<a href="hfc_to_csvxml.php?what='.$feat_type.'&fmt=csv">CSV</a>';
			if (in_array("xml",$_SESSION['par_post']['output_format']))
				echo '&nbsp;<a href="hfc_to_csvxml.php?what='.$feat_type.'&fmt=xml">VOTable</a>';
		}
	}
	echo "\n";
	echo '</div></div>'."\n";
	if ($total == 0) print_error_message("Your query returns no result for specified feature(s) with these dates or criteria selection!");
}

function print_navigation_bar($numpage) {
	$feat_list = array('fil'=>'Filament', 'pro'=>'Prominence', 'ar'=>'Active region', 'sp'=>'Sun spot',
				'ch'=>'Coronal hole', 't3'=>'Type III', 't2'=>'Type II', 'rs'=>'Radio source');

	$tab_date = array();
	foreach ($feat_list as $key=>$feat_name) {
		$var_sess = 'date_'.$key;
		if (count($_SESSION[$var_sess]['DATE_OBS']))
			$tab_date = array_merge($tab_date, array_values(array_unique($_SESSION[$var_sess]['DATE_OBS'])));
	}
	$tab_date= array_values(array_unique($tab_date));
	foreach($tab_date as $dt) {
		$timestmp = strtotime($dt);
		$date_array = getdate($timestmp);
		if($date_array['mon'] < 10) $date_array['mon'] = '0'.$date_array['mon'];
		if($date_array['mday'] < 10) $date_array['mday'] = '0'.$date_array['mday'];
		$tab_short_date[] = $date_array['year'].'-'.$date_array['mon'].'-'.$date_array['mday'];
	}
	$tab_date = array_values(array_unique($tab_short_date));
	sort($tab_date);
	$nbpages = ceil(count($tab_date)/10);
	echo '<div><center>Page: '."\n";
	for ($i=1; $i<=$nbpages; $i++) {
		/*if ($i == $numpage) echo " | <b>".$tab_date[10*($i-1)]."</b>";
		else echo ' | <a href="results.php?numpage='.$i.'">'.$tab_date[10*($i-1)].'</a>';*/
		if ($i == $numpage) echo " | <b>".$i."</b>";
		else echo ' | <a href="results.php?numpage='.$i.'">'.$i.'</a>';
	}
	echo '</center></div>'."\n";
}

function print_date_results($numpage) {

	print_numberof_results();
	print_navigation_bar($numpage);

	$feat_list = array('fil'=>'Filament', 'pro'=>'Prominence', 'ar'=>'Active region', 'sp'=>'Sun spot',
				'ch'=>'Coronal hole', 't3'=>'Type III', 't2'=>'Type II', 'rs'=>'Radio source');

	$tab_date = array();
	foreach ($feat_list as $key=>$feat_name) {
		$var_sess = 'date_'.$key;
		if (count($_SESSION[$var_sess]['DATE_OBS']))
			$tab_date = array_merge($tab_date, array_values(array_unique($_SESSION[$var_sess]['DATE_OBS'])));
	}
	$tab_date= array_values(array_unique($tab_date));
	foreach($tab_date as $dt) {
		$timestmp = strtotime($dt);
		$date_array = getdate($timestmp);
		if($date_array['mon'] < 10) $date_array['mon'] = '0'.$date_array['mon'];
		if($date_array['mday'] < 10) $date_array['mday'] = '0'.$date_array['mday'];
		$tab_short_date[] = $date_array['year'].'-'.$date_array['mon'].'-'.$date_array['mday'];
	}
	$tab_date = array_values(array_unique($tab_short_date));
	sort($tab_date);
	$nbpages = count($tab_date)/10;
	$start_id = 10*($numpage-1);
	$end_id = $start_id + 9;
	$nbdate = count($tab_date);
	if ($end_id >= $nbdate) $end_id = $nbdate-1;

	if ($nbdate) {
		echo '<div id="tabs_results" class="bg-lightgray ui-corner-all">'."\n";
		echo '<ul>'."\n";
		$previous_date = "";
		for ($i=$start_id; $i<=$end_id; $i++) {
			$current_date = $tab_date[$i];
			if (strcmp($current_date, $previous_date)) {
				$lst_feat = implode(',', $_SESSION['par_post']['features']);
				$lst_map = implode(',', $_SESSION['par_post']['map_type']);
				$url_content = "print_tab_hours.php?date=".$current_date."&feat=$lst_feat";
				if (strlen($lst_map)) $url_content = $url_content."&map=$lst_map";
				echo '<li><a href="'.$url_content.'"><span>'.$current_date.'</span></a></li>'."\n";
			}
			$previous_date = $current_date;
		}
		echo '</ul>'."\n";
		//echo '</div></div>'."\n";
	}
	echo '</div>'."\n"; // end of tab_results
	echo "<BR>\n";
}

function print_query_sameup($message) {
	print '<div class="portlet">';
	print '<div class="portlet-header">Query sameup</div>';
	print '<div class="portlet-content">';
	echo $message;
	echo 'Date selection: ';
	if (!isset($_SESSION['par_post']['ignore_date']))
		echo 'from '.$_SESSION['par_post']['from'].' to '.$_SESSION['par_post']['to']."<BR>\n";
	else
		echo 'ignore'."<BR>\n";
	echo 'Features selection: ';
	if (in_array("fil", $_SESSION['par_post']['features'])) echo ' | Filaments | ';
	if (in_array("pro", $_SESSION['par_post']['features'])) echo ' | Prominences | ';
	if (in_array("ar", $_SESSION['par_post']['features'])) echo ' | Active regions | ';
	if (in_array("sp", $_SESSION['par_post']['features'])) echo ' | Sun spots | ';
	if (in_array("ch", $_SESSION['par_post']['features'])) echo ' | Coronal holes | ';
	if (in_array("t3", $_SESSION['par_post']['features'])) echo ' | Types III | ';
	if (in_array("t2", $_SESSION['par_post']['features'])) echo ' | Types II | ';
	if (in_array("rs", $_SESSION['par_post']['features'])) echo ' | Radio sources | ';
	echo "<BR>\n";
	echo 'Solar region selection: ';
	if (is_numeric($_SESSION['par_post']['region']['minlat'])) {
		echo ' Latitude band '.$_SESSION['par_post']['region']['minlat'].'/'.$_SESSION['par_post']['region']['maxlat'].' | ';
		if (strcmp($_SESSION['par_post']['region']['symmetric'], "on") == 0)
			echo 'Symmetric band | ';
		echo 'Longitude '.$_SESSION['par_post']['region']['minlon'].'/'.$_SESSION['par_post']['region']['maxlon']."<BR>\n";
	} else echo 'None'."<BR>\n";
	if (strcmp($_SESSION['par_post']['fil_criter_select'], "None") != 0) {
		echo 'Criteria for filaments: '.$_SESSION['par_post']['fil_criter_select'];
		switch ($_SESSION['par_post']['fil_criter_select']) {
			case "Length":
				echo ' min/max '.$_SESSION['par_post']['fil_length_par']['min'].'/'.$_SESSION['par_post']['fil_length_par']['max'];
				break;
			case "Orientation":
				echo ' min/max '.$_SESSION['par_post']['fil_orientation_par']['min'].'/'.$_SESSION['par_post']['fil_orientation_par']['max'];
				break;
			case "Area":
				echo ' min/max '.$_SESSION['par_post']['fil_area_par']['min'].'/'.$_SESSION['par_post']['fil_area_par']['max'];
				break;
		}
		echo "<BR>\n";
	}
	if (strcmp($_SESSION['par_post']['pro_criter_select'], "None") != 0) {
		echo 'Criteria for prominences: '.$_SESSION['par_post']['pro_criter_select'];
		switch ($_SESSION['par_post']['pro_criter_select']) {
			case "Height":
				echo ' min/max '.$_SESSION['par_post']['pro_height_par']['min'].'/'.$_SESSION['par_post']['pro_height_par']['max'];
				break;
			case "Delta latitude":
				echo ' min/max '.$_SESSION['par_post']['pro_delta_lat_par']['min'].'/'.$_SESSION['par_post']['pro_delta_lat_par']['max'];
				break;
			case "Max. intensity":
				echo ' min/max '.$_SESSION['par_post']['pro_maxint_par']['min'].'/'.$_SESSION['par_post']['pro_maxint_par']['max'];
				break;
		}
		echo "<BR>\n";
	}
	if (strcmp($_SESSION['par_post']['ar_criter_select'], "None") != 0) {
		echo 'Criteria for active regions: '. $_SESSION['par_post']['ar_criter_select'];
		switch ($_SESSION['par_post']['ar_criter_select']) {
			case "Area":
				echo ' min/max '.$_SESSION['par_post']['ar_area_par']['min'].'/'.$_SESSION['par_post']['ar_area_par']['max'];
				break;
			case "Max. intensity":
				echo ' min/max '.$_SESSION['par_post']['ar_maxint_par']['min'].'/'.$_SESSION['par_post']['ar_maxint_par']['max'];
				break;
		}
		echo "<BR>\n";
	}
	if (strcmp($_SESSION['par_post']['ch_criter_select'], "None") != 0) {
		echo 'Criteria for coronal holes: '. $_SESSION['par_post']['ch_criter_select'];
		switch ($_SESSION['par_post']['ch_criter_select']) {
			case "Area":
				echo ' min/max '.$_SESSION['par_post']['ch_area_par']['min'].'/'.$_SESSION['par_post']['ch_area_par']['max'];
				break;
			case "Width":
				echo ' min/max '.$_SESSION['par_post']['ch_width_par']['min'].'/'.$_SESSION['par_post']['ch_width_par']['max'];
				break;
		}
		echo "<BR>\n";
	}
	if (strcmp($_SESSION['par_post']['sp_criter_select'], "None") != 0) {
		echo 'Criteria for sunspots: '. $_SESSION['par_post']['sp_criter_select'];
		switch ($_SESSION['par_post']['sp_criter_select']) {
			case "Area":
				echo ' min/max '.$_SESSION['par_post']['sp_area_par']['min'].'/'.$_SESSION['par_post']['sp_area_par']['max'];
				break;
		}
		echo "<BR>\n";
	}
	if (strcmp($_SESSION['par_post']['t3_criter_select'], "None") != 0) {
		echo 'Criteria for type III: '. $_SESSION['par_post']['t3_criter_select'];
		switch ($_SESSION['par_post']['t3_criter_select']) {
			case "Max. intensity":
				echo ' min/max '.$_SESSION['par_post']['t3_maxint_par']['min'].'/'.$_SESSION['par_post']['t3_maxint_par']['max'];
				break;
		}
		echo "<BR>\n";
	}
	echo 'Ouput format:';
	if (in_array("html", $_SESSION['par_post']['output_format'])) echo ' | HTML | ';
	if (in_array("xml", $_SESSION['par_post']['output_format'])) echo ' | XML | ';
	if (in_array("csv", $_SESSION['par_post']['output_format'])) echo ' | CSV | ';
	//echo "<BR>\n";
	echo "  \n";
	echo 'Maps:';
	if (in_array("pixel", $_SESSION['par_post']['map_type'])) {
		echo ' | PIXEL | ';
		if (in_array("helio", $_SESSION['par_post']['map_type'])) echo ' | CARRINGTON | '."\n";
		else echo ' | None | '."\n";
	}
	echo '</div>'."\n";
	echo '</div>'."\n";
	//echo "<BR>\n";
}

function check_param_criteria() {
	$flag = true;

	// checks that there is at least a criteria when start_date is empty
	if (isset($_SESSION['par_post']['ignore_date'])) {
		$tst_criteria = (strcmp($_SESSION['par_post']['fil_criter_select'], "None") != 0) +
				(strcmp($_SESSION['par_post']['pro_criter_select'], "None") != 0) +
				(strcmp($_SESSION['par_post']['ar_criter_select'], "None") != 0) +
				(strcmp($_SESSION['par_post']['sp_criter_select'], "None") != 0) +
				(strcmp($_SESSION['par_post']['ch_criter_select'], "None") != 0) +
				(strcmp($_SESSION['par_post']['t3_criter_select'], "None") != 0) +
				is_numeric($_SESSION['par_post']['region']['minlat']);
		if (!$tst_criteria) {
			print_error_message("Date selection or criteria are required. Return to query form.");
			$flag = false;
		}
	}

	// checks solar region values if they exist
	if (is_numeric($_SESSION['par_post']['region']['minlat']) && is_numeric($_SESSION['par_post']['region']['maxlat'])) {
		if (($_SESSION['par_post']['region']['minlat']<-90) || ($_SESSION['par_post']['region']['minlat']>90)) {
			print_error_message("Min value is out of [-90, 90] degrees. Return to query form.");
			$flag = false;break;
		}
		if (($_SESSION['par_post']['region']['maxlat']<-90) || ($_SESSION['par_post']['region']['maxlat']>90)) {
			print_error_message("Max value is out of [-90, 90] degrees. Return to query form.");
			$flag = false;break;
		}
		if (is_numeric($_SESSION['par_post']['region']['minlon'])) {
			if (($_SESSION['par_post']['region']['minlon']<0) || ($_SESSION['par_post']['region']['minlon']>360)) {
				print_error_message("Min value is out of [0, 360] degrees. Return to query form.");
				$flag = false;break;
			}
		} //else $_SESSION['par_post']['region']['minlon'] = 0;
		if (is_numeric($_SESSION['par_post']['region']['maxlon'])) {
			if (($_SESSION['par_post']['region']['maxlon']<0) || ($_SESSION['par_post']['region']['maxlon']>360)) {
				print_error_message("Max value is out of [0, 360] degrees. Return to query form.");
				$flag = false;break;
			}
		} //else $_SESSION['par_post']['region']['maxlon'] = 360;
		if ($_SESSION['par_post']['region']['minlat'] > $_SESSION['par_post']['region']['maxlat']) {
			print_error_message("Min > Max !. Return to query form.");
			$flag = false;
		}
	}
	if (is_numeric($_SESSION['par_post']['region']['minlon']) && is_numeric($_SESSION['par_post']['region']['maxlon'])) {
		if(!is_numeric($_SESSION['par_post']['region']['minlat'])) $_SESSION['par_post']['region']['minlat'] = -90;
		if(!is_numeric($_SESSION['par_post']['region']['maxlat'])) $_SESSION['par_post']['region']['maxlat'] = 90;
	}

	// checks filaments criteria values
	if (in_array("fil", $_SESSION['par_post']['features'])) {
	if (strcmp($_SESSION['par_post']['fil_criter_select'], "None") != 0) {
		// test if there are criteria values
		switch($_SESSION['par_post']['fil_criter_select']) {
			case "Length":
				if (!array_sum($_SESSION['par_post']['fil_length_par'])) {
					print_error_message("Min and Max length values are not specified. Return to query form.");
					$flag = false;
				}
				if ($_SESSION['par_post']['fil_length_par']['min'] > $_SESSION['par_post']['fil_length_par']['max']) {
					print_error_message("Min > Max !. Return to query form.");
					$flag = false;
				}
				break;
			case "Orientation":
				if (!array_sum($_SESSION['par_post']['fil_orientation_par'])) {
					print_error_message("Min and Max orientation values are not specified. Return to query form.");
					$flag = false;
				}
				if ($_SESSION['par_post']['fil_orientation_par']['min'] > $_SESSION['par_post']['fil_orientation_par']['max']) {
					print_error_message("Min > Max !. Return to query form.");
					$flag = false;
				}
				break;
			case "Area":
				if (!array_sum($_SESSION['par_post']['fil_area_par'])) {
					print_error_message("Min and Max area values are not specified. Return to query form.");
					$flag = false;
				}
				if ($_SESSION['par_post']['fil_area_par']['min'] > $_SESSION['par_post']['fil_area_par']['max']) {
					print_error_message("Min > Max !. Return to query form.");
					$flag = false;
				}
				break;
		}
	}
	}
	// checks prominences criteria values
	if (in_array("pro", $_SESSION['par_post']['features'])) {
	if (strcmp($_SESSION['par_post']['pro_criter_select'], "None") != 0) {
		// test if there are criteria values
		switch($_SESSION['par_post']['pro_criter_select']) {
			case "Height":
				if (!array_sum($_SESSION['par_post']['pro_height_par'])) {
					print_error_message("Min and Max height values are not specified. Return to query form.");
					$flag = false;
				}
				if ($_SESSION['par_post']['pro_height_par']['min'] > $_SESSION['par_post']['pro_height_par']['max']) {
					print_error_message("Min > Max !. Return to query form.");
					$flag = false;
				}
				break;
			case "Delta latitude":
				if (!array_sum($_SESSION['par_post']['pro_delta_lat_par'])) {
					print_error_message("Min and Max latitude values are not specified. Return to query form.");
					$flag = false;
				}
				if ($_SESSION['par_post']['pro_delta_lat_par']['min'] > $_SESSION['par_post']['pro_delta_lat_par']['max']) {
					print_error_message("Min > Max !. Return to query form.");
					$flag = false;
				}
				break;
			case "Max. intensity":
				if (!array_sum($_SESSION['par_post']['pro_maxint_par'])) {
					print_error_message("Min and Max intensity values are not specified. Return to query form.");
					$flag = false;
				}
				if ($_SESSION['par_post']['pro_maxint_par']['min'] > $_SESSION['par_post']['pro_maxint_par']['max']) {
					print_error_message("Min > Max !. Return to query form.");
					$flag = false;
				}
				break;
		}
	}
	}
	if (in_array("ar", $_SESSION['par_post']['features'])) {
	if (strcmp($_SESSION['par_post']['ar_criter_select'], "None") != 0) {
		// test if there are criteria values
		switch($_SESSION['par_post']['ar_criter_select']) {
			case "Area":
				if (!array_sum($_SESSION['par_post']['ar_area_par'])) {
					print_error_message("Min and Max area values are not specified. Return to query form.");
					$flag = false;
				}
				if ($_SESSION['par_post']['ar_area_par']['min'] > $_SESSION['par_post']['ar_area_par']['max']) {
					print_error_message("Min > Max !. Return to query form.");
					$flag = false;
				}
				break;
			case "Max. intensity":
				if (!array_sum($_SESSION['par_post']['ar_maxint_par'])) {
					print_error_message("Min and Max intensity values are not specified. Return to query form.");
					$flag = false;
				}
				if ($_SESSION['par_post']['ar_maxint_par']['min'] > $_SESSION['par_post']['ar_maxint_par']['max']) {
					print_error_message("Min > Max !. Return to query form.");
					$flag = false;
				}
				break;
		}
	}
	}
	if (in_array("ch", $_SESSION['par_post']['features'])) {
	if (strcmp($_SESSION['par_post']['ch_criter_select'], "None") != 0) {
		// test if there are criteria values
		switch($_SESSION['par_post']['ch_criter_select']) {
			case "Area":
				if (!array_sum($_SESSION['par_post']['ch_area_par'])) {
					print_error_message("Min and Max area values are not specified. Return to query form.");
					$flag = false;
				}
				if ($_SESSION['par_post']['ch_area_par']['min'] > $_SESSION['par_post']['ch_area_par']['max']) {
					print_error_message("Min > Max !. Return to query form.");
					$flag = false;
				}
				break;
			case "Width":
				if (!array_sum($_SESSION['par_post']['ch_width_par'])) {
					print_error_message("Min and Max area values are not specified. Return to query form.");
					$flag = false;
				}
				if ($_SESSION['par_post']['ch_width_par']['min'] > $_SESSION['par_post']['ch_width_par']['max']) {
					print_error_message("Min > Max !. Return to query form.");
					$flag = false;
				}
				break;
		}
	}
	}
	if (in_array("sp", $_SESSION['par_post']['features'])) {
	if (strcmp($_SESSION['par_post']['sp_criter_select'], "None") != 0) {
		// test if there are criteria values
		switch($_SESSION['par_post']['sp_criter_select']) {
			case "Area":
				if (!array_sum($_SESSION['par_post']['sp_area_par'])) {
					print_error_message("Min and Max area values are not specified. Return to query form.");
					$flag = false;
				}
				if ($_SESSION['par_post']['sp_area_par']['min'] > $_SESSION['par_post']['sp_area_par']['max']) {
					print_error_message("Min > Max !. Return to query form.");
					$flag = false;
				}
		}
	}
	}
	// checks type III criteria values
	if (in_array("t3", $_SESSION['par_post']['features'])) {
	if (strcmp($_SESSION['par_post']['t3_criter_select'], "None") != 0) {
		// test if there are criteria values
		switch($_SESSION['par_post']['t3_criter_select']) {
			case "Max. intensity":
				if (!array_sum($_SESSION['par_post']['t3_maxint_par'])) {
					print_error_message("Min and Max intensity values are not specified. Return to query form.");
					$flag = false;
				}
				if ($_SESSION['par_post']['t3_maxint_par']['min'] > $_SESSION['par_post']['t3_maxint_par']['max']) {
					print_error_message("Min > Max !. Return to query form.");
					$flag = false;
				}
				break;
		}
	}
	}

	return $flag;
}

function print_sql_query() {
	print '<div class="portlet">';
	print '<div class="portlet-header">SQL log</div>';
	print '<div class="portlet-content">';
	foreach ($_SESSION['par_post']['features'] as $feat_type) {
		echo '<p>';
		echo '<form action="hfc_sql_query.php" method="POST">';
		echo '<input type="hidden" name="sql" value="'.$_SESSION['query'][$feat_type].'">';
		echo '<input type="submit" value="Edit">';
		echo $_SESSION['query'][$feat_type];
		echo '</form>';
		echo '</p>';
	}
	echo '</div></div>'."\n";
}
?>