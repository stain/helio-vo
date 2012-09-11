<?php
// Script affichant la partie onglet heure d'une page de resultats
// appel: print_res_hour.php?date=1999-03-09 06:23:02&feat=fil|ar|ch&map=helio,pixel
// la date peut inclure l'heure ou non
session_start();
require("functions.php");

if (isset($_GET['date'])) $date = $_GET['date'];
else exit(0);
if (isset($_GET['feat'])) $feature_type = $_GET['feat'];
else exit(0);
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
<!--<script type="text/javascript" charset="utf-8" src="js/DataTables-1.9.0/media/js/jquery.dataTables.min.js"></script>-->
<script type="text/javascript">
$(function(){
	$( ".portlet_<?php echo $feature_type; ?>" ).addClass( "ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" )
	//$( ".portlet" ).addClass( "ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" )
		.find( ".portlet-header" )
			.addClass( "ui-widget ui-corner-all" )
			.prepend( "<span class='ui-icon ui-icon-plusthick'></span>")
			.end()
		.find( ".portlet-content" )
		.hide();

	$( ".portlet-header .ui-icon" ).click(function() {
		$( this ).toggleClass( "ui-icon-minusthick" ).toggleClass( "ui-icon-plusthick" );
		//$( this ).parents( ".portlet:first" ).find( ".portlet-content" ).toggle();
		$( this ).parents( ".portlet_<?php echo $feature_type; ?>:first" ).find( ".portlet-content" ).toggle();
	});

    $('#tab_data_<?php echo $feature_type.strtotime($date); ?>').dataTable({
//		"bJQueryUI": true,
		"bAutoWidth": false,
		"bFilter": false
	});
});
</script>
<style type="text/css">
	table.tabresults { font: 80% "Trebuchet MS", sans-serif; border-collapse:collapse; width:100%;}
	th.tabresults, td.tabresults { border:1px solid #dbd7d4; background-color:white; width:20%; padding: 3px; text-align: center;}
	table.dataTable tr.odd { background-color: #f2e4d5; }
	.portlet { margin: 0 1em 1em 0; }
	.portlet-header { margin: 0.3em; padding-bottom: 4px; padding-left: 0.2em; }
	.portlet-header .ui-icon { float: right; }
	.portlet-content { padding: 0.4em; }
</style>
<?php
		
if ($feature_type == "fil") {
?>
<script>
		$(function() {
			$('#but_help_phenom<?php echo $start_year.$start_month.$start_day; ?>').button({
				icons: {primary:'ui-icon-help'},
				text: false
			});
			$( '#dialog_phenom' ).dialog({ 
				autoOpen: false,
				width: 500
			});
			$('#but_help_phenom<?php echo $start_year.$start_month.$start_day; ?>').click(function(){
				$('#dialog_phenom').dialog('open');
				return false;
			});
		});
	</script>
<div id="dialog_phenom" title="Phenomen">
	<ul>
		<li>Appearence after the east limb</li>
		<li>Disparition brusque</li>
		<li>Abnormal behavior</li>
		<li>Disappearence before the west limb</li>
	</ul>
</div>
<?php
}
$rs = query_feat($date, $feature_type);
$var_sess = 'rs_'.$feature_type;
$_SESSION[$var_sess] = $rs;
if (count($rs['DATE_OBS'])) {
	print_results_bydate($rs, $feature_type, $map/*, $keys*/);
}

function query_feat($date, $feat_type) {

		$tab_results = array();
	// date selection part of the query
		$query_date = "DATE_OBS='".$date."' ";

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

	if ($feat_type == "fil") {
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
		$sql_query = "SELECT * FROM VIEW_FIL_GUI ";
		//$sql_query = "SELECT v_fil.*, t_trckfil.* FROM VIEW_FIL_GUI v_fil, FILAMENTS_TRACKING t_trckfil ";
		$query_cond = implode(' AND ', $tab_where_feat);
		$query_where = "WHERE ".$query_cond;
		if (strlen($query_cond)) $sql_query = $sql_query.$query_where."ORDER BY TRACK_ID ASC";
			//$sql_query = $sql_query.$query_where." AND (v_fil.ID_FIL =  t_trckfil.FIL_ID) ORDER BY TRACK_ID ASC";
		else $sql_query = $sql_query." ORDER BY TRACK_ID ASC";
			//$sql_query = $sql_query." WHERE (v_fil.ID_FIL =  t_trckfil.FIL_ID) ORDER BY TRACK_ID ASC";
		//print_r($sql_query); echo "<BR>\n";
		$tab_results = execute_query($sql_query);
//		$_SESSION['query']['fil'] = $sql_query;
		$nb_feat = count($tab_results['ID_FIL']);
		if (($nb_feat == 0) && strcmp($_SESSION['par_post']['fil_criter_select'], "Disappearance")) {
			$sql_query = "SELECT v_fil.* FROM VIEW_FIL_GUI v_fil ";
			$query_where = "WHERE ".$query_cond;
			if (strlen($query_cond)) $sql_query = $sql_query.$query_where."ORDER BY DATE_OBS ASC";
			else $sql_query = $sql_query." ORDER BY DATE_OBS ASC";
			//print_r($sql_query); echo "<BR>\n";
			$tab_results = execute_query($sql_query." LIMIT 8000");
			$nb_feat = count($tab_results['ID_FIL']);
		}
		if ($nb_feat == 8000) print_error_message("Query limited to 8000 results!");
	}

	if ($feat_type == "pro") {
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
		$sql_query = "SELECT * FROM VIEW_PRO_GUI ";
		$query_cond = implode(' AND ', $tab_where_feat);
		$query_where = "WHERE ".$query_cond;
		if (strlen($query_cond)) $sql_query = $sql_query.$query_where." ORDER BY DATE_OBS ASC";
		else $sql_query = $sql_query." ORDER BY DATE_OBS ASC";
			//print_r($sql_query); echo "<BR>\n";
		$tab_results = execute_query($sql_query);
		$nb_feat = count($tab_results['ID_PROMINENCE']);
		if ($nb_feat == 8000) print_error_message("Query limited to 8000 results!");
	}

	if ($feat_type == "ar") {
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
		$sql_query = "SELECT * FROM VIEW_AR_GUI ";
		$query_cond = implode(' AND ', $tab_where_feat);
		$query_where = "WHERE ".$query_cond;
		if (strlen($query_cond)) $sql_query = $sql_query.$query_where." ORDER BY DATE_OBS ASC";
		else $sql_query = $sql_query." ORDER BY DATE_OBS ASC";
			//print_r($sql_query); echo "<BR>\n";
		$tab_results = execute_query($sql_query);
		$nb_feat = count($tab_results['ID_AR']);
		if ($nb_feat == 8000) print_error_message("Query limited to 8000 results!");
	}

	if ($feat_type == "ch") {
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
		$sql_query = "SELECT * FROM VIEW_CH_GUI ";
		$query_cond = implode(' AND ', $tab_where_feat);
		$query_where = "WHERE ".$query_cond;
		if (strlen($query_cond)) $sql_query = $sql_query.$query_where." ORDER BY DATE_OBS ASC";
		else $sql_query = $sql_query." ORDER BY DATE_OBS ASC";
			//print_r($sql_query); echo "<BR>\n";
		$tab_results = execute_query($sql_query);
		$nb_feat = count($tab_results['ID_CORONALHOLES']);
		if ($nb_feat == 8000) print_error_message("Query limited to 8000 results!");
	}

	if ($feat_type == "sp") {
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
		$sql_query = "SELECT * FROM VIEW_SP_GUI ";
		$query_cond = implode(' AND ', $tab_where_feat);
		$query_where = "WHERE ".$query_cond;
		if (strlen($query_cond)) $sql_query = $sql_query.$query_where." ORDER BY DATE_OBS ASC";
		else $sql_query = $sql_query." ORDER BY DATE_OBS ASC";
			//print_r($sql_query); echo "<BR>\n";
		$tab_results = execute_query($sql_query);
		$nb_feat = count($tab_results['ID_SUNSPOT']);
		if ($nb_feat == 8000) print_error_message("Query limited to 8000 results!");
	}

	if ($feat_type == "t3") {
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
		$sql_query = "SELECT * FROM VIEW_T3_GUI ";
		$query_cond = implode(' AND ', $tab_where_feat);
		$query_where = "WHERE ".$query_cond;
		if (strlen($query_cond)) $sql_query = $sql_query.$query_where." ORDER BY DATE_OBS ASC";
		else $sql_query = $sql_query." ORDER BY DATE_OBS ASC";
			//print_r($sql_query); echo "<BR>\n";
		$tab_results = execute_query($sql_query);
		$nb_feat = count($tab_results['ID_TYPE_III']);
		if ($nb_feat == 8000) print_error_message("Query limited to 8000 results!");
	}

	if ($feat_type == "t2") {
		$tab_where_feat = array();
		if (isset($query_date)) $tab_where_feat[] = $query_date;
		// check if query is on specific observatory
		if (isset($_SESSION['par_post']['obs_t2'])) {
			$query_obs = '(ID_OBSERVATORY IN ('.(implode(',', $_SESSION['par_post']['obs_t2'])).'))';
			$tab_where_feat[] = $query_obs;
			//echo "observatory=$query_obs<br>\n";
		}
		$sql_query = "SELECT * FROM VIEW_T2_GUI ";
		$query_cond = implode(' AND ', $tab_where_feat);
		$query_where = "WHERE ".$query_cond;
		if (strlen($query_cond)) $sql_query = $sql_query.$query_where." ORDER BY DATE_OBS ASC";
		else $sql_query = $sql_query." ORDER BY DATE_OBS ASC";
			//print_r($sql_query); echo "<BR>\n";
		$tab_results = execute_query($sql_query);
		$nb_feat = count($tab_results['ID_TYPE_II']);
		if ($nb_feat == 8000) print_error_message("Query limited to 8000 results!");
	}

	if ($feat_type == "rs") {
		$tab_where_feat = array();
		if (isset($query_date)) $tab_where_feat[] = $query_date;
		if (isset($query_region)) $tab_where_feat[] = $query_region;
		// check if query is on specific observatory
		if (isset($_SESSION['par_post']['obs_rs'])) {
			$query_obs = '(ID_OBSERVATORY IN ('.(implode(',', $_SESSION['par_post']['obs_rs'])).'))';
			$tab_where_feat[] = $query_obs;
			//echo "observatory=$query_obs<br>\n";
		}
		$sql_query = "SELECT * FROM VIEW_RS_GUI ";
		$query_cond = implode(' AND ', $tab_where_feat);
		$query_where = "WHERE ".$query_cond;
		if (strlen($query_cond)) $sql_query = $sql_query.$query_where." ORDER BY TRACK_ID ASC";
		else $sql_query = $sql_query." ORDER BY TRACK_ID ASC";
			//print_r($sql_query); echo "<BR>\n";
		$tab_results = execute_query($sql_query);
		$nb_feat = count($tab_results['ID_RS']);
		if ($nb_feat == 8000) print_error_message("Query limited to 8000 results!");
	}

	return $tab_results;
}


function print_results_bydate($results, $feature_type, $map/*, $keys*/) {
global $global;

//$date = $results['DATE_OBS'][$keys[0]];
$date = $results['DATE_OBS'][0];
switch ($feature_type) {
	case "fil":
		if (isset($results['TRACK_ID']))
			$fields_to_print = array("TRACK_ID", "ID_FIL", "OBSERVAT"/*, "DATE_OBS" "PHENOM", "SC_ARC_X", "SC_ARC_Y", "SKE_LEN_DEG"*/);
		else $fields_to_print = array("ID_FIL"/*, "DATE_OBS" "PHENOM", "SC_ARC_X", "SC_ARC_Y", "SKE_LEN_DEG"*/);
		if (count($_SESSION['par_post']['filopt']))
			foreach($_SESSION['par_post']['filopt'] as $field) $fields_to_print[] = $field;
		$query_comments = "SHOW FULL COLUMNS FROM VIEW_FIL_GUI";
		break;
	case "pro":
		$fields_to_print = array("ID_PROMINENCE");
		if (count($_SESSION['par_post']['proopt']))
			foreach($_SESSION['par_post']['proopt'] as $field) $fields_to_print[] = $field;
		$query_comments = "SHOW FULL COLUMNS FROM VIEW_PRO_GUI";
		break;
	case "ar":
		$fields_to_print = array("ID_AR", "OBSERVAT"/*, "NOAA_NUMBER", "AR_DATE", "SC_ARC_X", "SC_ARC_Y", "FEAT_AREA"*/);
		if (count($_SESSION['par_post']['aropt']))
			foreach($_SESSION['par_post']['aropt'] as $field) $fields_to_print[] = $field;
		$query_comments = "SHOW FULL COLUMNS FROM VIEW_AR_GUI";
		break;
	case "sp":
		$fields_to_print = array("ID_SUNSPOT", "OBSERVAT"/*, "DATE_OBS", "GC_ARC_X", "GC_ARC_Y", "DIAMETER", "FEAT_AREA", "FEAT_MEAN2QSUN", "N_UMBRA", "TOTALFLUX", "ABSTOTALFLUX"*/);
		if (count($_SESSION['par_post']['spopt']))
			foreach($_SESSION['par_post']['spopt'] as $field) $fields_to_print[] = $field;
		$query_comments = "SHOW FULL COLUMNS FROM VIEW_SP_GUI";
		break;
	case "ch":
		$fields_to_print = array("ID_CORONALHOLES", "OBSERVAT"/*, "OBS_DATE", "CHC_ARC_X", "CHC_ARC_Y", "CHC_DEG_X", "CHC_DEG_Y", "CH_AREA_MM"*/);
		if (count($_SESSION['par_post']['chopt']))
			foreach($_SESSION['par_post']['chopt'] as $field) $fields_to_print[] = $field;
		$query_comments = "SHOW FULL COLUMNS FROM VIEW_CH_GUI";
		break;
	case "t3":
		$fields_to_print = array("ID_TYPE_III", "OBSERVAT", "UNITS"/*, "DATE_OBS", "OVERLAP", "CC_MHZ_Y", "FEAT_MAX_INT", "FEAT_MEAN_INT"*/);
		if (count($_SESSION['par_post']['t3opt']))
			foreach($_SESSION['par_post']['t3opt'] as $field) $fields_to_print[] = $field;
		$query_comments = "SHOW FULL COLUMNS FROM VIEW_T3_GUI";
		break;
	case "t2":
		$fields_to_print = array("ID_TYPE_II", "OBSERVAT", "UNITS"/*, "DATE_OBS", "OVERLAP", "CC_MHZ_Y", "FEAT_MAX_INT", "FEAT_MEAN_INT"*/);
		if (count($_SESSION['par_post']['t2opt']))
			foreach($_SESSION['par_post']['t2opt'] as $field) $fields_to_print[] = $field;
		$query_comments = "SHOW FULL COLUMNS FROM VIEW_T2_GUI";
	case "rs":
		if (isset($results['TRACK_ID']))
			$fields_to_print = array("TRACK_ID", "ID_RS", "OBSERVAT", "UNITS"/*, "DATE_OBS" "PHENOM", "SC_ARC_X", "SC_ARC_Y", "SKE_LEN_DEG"*/);
		else 
		$fields_to_print = array("ID_RS", "OBSERVAT", "UNITS"/*, "DATE_OBS", "OVERLAP", "CC_MHZ_Y", "FEAT_MAX_INT", "FEAT_MEAN_INT"*/);
		if (count($_SESSION['par_post']['rsopt']))
			foreach($_SESSION['par_post']['rsopt'] as $field) $fields_to_print[] = $field;
		$query_comments = "SHOW FULL COLUMNS FROM VIEW_RS_GUI";
		break;
	default:
		exit(0);
}

$rs = execute_query($query_comments);

$tab_comment = array();
$nbfields = count($rs['FIELD']);
for ($i=0; $i<$nbfields; $i++) {
	if (strlen($rs['COMMENT'][$i])) $tab_comment[strtoupper($rs['FIELD'][$i])] = $rs['COMMENT'][$i];
	else $tab_comment[strtoupper($rs['FIELD'][$i])] = $rs['FIELD'][$i];
}

$tmp = getdate(strtotime($date));
$tab_comment['TRACK_ID'] = "Index of the feature during a rotation<br>Click for tracking info";
$tab_comment['ID_FIL'] = "Id of filament's component(s)";
$but_help_name = 'but_help_phenom'.$tmp['year'].$tmp['mon'].$tmp['mday'];
$tab_comment['PHENOM'] = 'Phenomen <button id="'.$but_help_name.'"></button>';
$tab_comment['PROPA_MOD'] = "Link to the SHEBA forward propagation model";
//$tab_comment['PHENOM'] = 'Phenomen';
//$tab_comment['UNITS'] = 'Intensity/Flux units';
//$tab_comment['TELESCOP'] = "Telescop";
//$tab_comment['ID_TYPE_III'] = "Index of the feature in the database";
//$tab_comment['ID_AR'] = "Index of the feature in the database";

$tst_criteria = (strcmp($_SESSION['par_post']['fil_criter_select'], "None") != 0) +
				(strcmp($_SESSION['par_post']['pro_criter_select'], "None") != 0) +
				(strcmp($_SESSION['par_post']['ar_criter_select'], "None") != 0) +
				(strcmp($_SESSION['par_post']['sp_criter_select'], "None") != 0) +
				(strcmp($_SESSION['par_post']['ch_criter_select'], "None") != 0) +
				(strcmp($_SESSION['par_post']['t3_criter_select'], "None") != 0) +
				is_numeric($_SESSION['par_post']['region']['minlat']);
// si requete sur critere, on utilise les parametres de session pour faire les cartes
if ($tst_criteria) $usesess = "&usesess=1";
else $usesess = "";
if (count($map)) {
	if (in_array("pixel", $map)) {
		switch ($feature_type) {
			case 't3':
				$script_link = "showmap.php?date=".$date."&feat=".$feature_type."&style=pixel".$usesess;
				$link = "javascript:void(window.open('".$script_link."','','scrollbars=1,resizable=1,status=1'))";
				print "<A href=\"".$link."\">";
				print "<IMG src=\"plot_t3.php?date=".$date."&zoom=0.5".$usesess."\" alt=\"Sun map\" width=\"400\">";
				print "</A>\n";
				// link to map with obs
				$script_link = "showmap.php?date=".$date."&feat=".$feature_type."&style=pixel&obs".$usesess;
				$link = "javascript:void(window.open('".$script_link."','','scrollbars=1,resizable=1,status=1'))";
				print "<A href=\"".$link."\">";
				//print 'Map with observation image and features';
				print "<IMG src=\"plot_t3.php?date=".$date."&zoom=0.5&obs".$usesess."\" alt=\"Sun map\" width=\"400\">";
				print "</A>\n";
				break;
			case 't2':
				$script_link = "showmap.php?date=".$date."&feat=".$feature_type."&style=pixel".$usesess;
				$link = "javascript:void(window.open('".$script_link."','','scrollbars=1,resizable=1,status=1'))";
				print "<A href=\"".$link."\">";
				print "<IMG src=\"plot_t2.php?date=".$date."&zoom=0.5".$usesess."\" alt=\"Sun map\" width=\"400\">";
				print "</A>\n";
				// link to map with obs
				$script_link = "showmap.php?date=".$date."&feat=".$feature_type."&style=pixel&obs".$usesess;
				$link = "javascript:void(window.open('".$script_link."','','scrollbars=1,resizable=1,status=1'))";
				print "<A href=\"".$link."\">";
				print 'Map with observation image and features';
				print "</A>\n";
				break;
			default:
				$script_link = "showmap.php?date=".$date."&feat=".$feature_type."&style=pixel".$usesess;
				$link = "javascript:void(window.open('".$script_link."','','scrollbars=1,resizable=1,status=1'))";
				print "<A href=\"".$link."\">";
				print "<IMG src=\"makemap_pixobs.php?date=".$date."&feat=".$feature_type."&zoom=0.5".$usesess."\" alt=\"Sun map\" width=\"400\">";
				print "</A>\n";
				// link to map with obs
				//if (strcmp($feature_type, 'rs')) {
					$script_link = "showmap.php?date=".$date."&feat=".$feature_type."&style=pixel&obs".$usesess;
					$link = "javascript:void(window.open('".$script_link."','','scrollbars=1,resizable=1,status=1'))";
					print "<A href=\"".$link."\">";
					//print 'Map with observation image and features';
					print "<IMG src=\"makemap_pixobs.php?date=".$date."&feat=".$feature_type."&zoom=0.5&obs".$usesess."\" alt=\"Sun map\" width=\"400\">";
					print "</A>\n";
				//}
		}
	}
	if (in_array("helio", $map)) {
		switch ($feature_type) {
			case 't3':
			case 't2':
			case 'rs':
				break;
			default:
				$script_link = "showmap.php?date=".$date."&feat=".$feature_type."&style=helio".$usesess;
				$link = "javascript:void(window.open('".$script_link."','','scrollbars=1,resizable=1,status=1'))";
				print "<A href=\"".$link."\">";
				print "<IMG src=\"makemap_carr.php?zoom=0.5&date=".$date."&feat=".$feature_type.$usesess."\" alt=\"Sun map\" width=\"400\">";
				print "</A>\n";
		}
	}
}
	// creation du tableau a afficher
	$tab_to_print = array();
	foreach ($fields_to_print as $field) {
		foreach ($results[$field] as $key=>$val) {
			if (is_numeric($val)) $tab_to_print[$field][] = round($val, 2);
			else {
				if (!strcmp($field, 'OBSERVAT')) {
					if (strlen($results['TELESCOP'][$key]))
						$tab_to_print[$field][] = $val.'/'.$results['INSTRUME'][$key].'/'.$results['TELESCOP'][$key];
					else
						$tab_to_print[$field][] = $val.'/'.$results['INSTRUME'][$key].'/'.$results['WAVEMIN'][$key];
					
				} else $tab_to_print[$field][] = $val;
			}
		}
	}
	// ajout du champ propagation model
	switch ($feature_type) {
		case "ch":
			$fields_to_print[] = 'PROPA_MOD';
			foreach ($results['DATE_OBS'] as $key=>$val) {
				$script = "javascript:void(window.open('hps_pm.php?m=CIR&dt=".$val."&lon=".$results['BR_HG_LONG3_DEG'][$key]."','','toolbar=1,scrollbars=1,resizable=1,status=1'))";
				$tab_to_print['PROPA_MOD'][] = '<a href="'.$script.'" title="Forward Propagation Model for Co-rotating Interactive Regions (CIR)">CIR-SW</a>';
			}
			break;
		case "ar":
		case "sp":
		case "rs":
			$fields_to_print[] = 'PROPA_MOD';
			foreach ($results['DATE_OBS'] as $key=>$val) {
				$script1 = "javascript:void(window.open('hps_pm.php?m=CME&dt=".$val."&lon=".$results['FEAT_HG_LONG_DEG'][$key]."','','toolbar=1,scrollbars=1,resizable=1,status=1'))";
				$script2 = "javascript:void(window.open('hps_pm.php?m=SEP&dt=".$val."&lon=".$results['FEAT_HG_LONG_DEG'][$key]."','','toolbar=1,scrollbars=1,resizable=1,status=1'))";
				$tab_to_print['PROPA_MOD'][] = '<a href="'.$script1.'" title="Forward Propagation Model for Coronal Mass Ejections (CMEs)">CME</a> or <a href="'.$script2.'" title="Forward Propagation Model for Solar Energetic Particles (SEP)">SEP</a>';
			}
			break;
		case "fil":
			$fields_to_print[] = 'PROPA_MOD';
			foreach ($results['DATE_OBS'] as $key=>$val) {
				$script1 = "javascript:void(window.open('hps_pm.php?m=CME&dt=".$val."&lon=".$results['FEAT_HG_LONG_DEG'][$key]."','','toolbar=1,scrollbars=1,resizable=1,status=1'))";
				$tab_to_print['PROPA_MOD'][] = '<a href="'.$script1.'" title="Forward Propagation Model for Coronal Mass Ejections (CMEs)">CME</a>';
			}
			break;
	}

	// ajout du champ Raster Scan
	switch ($feature_type) {
		case "pro":
			$fields_to_print[] = 'RS';
			foreach ($results['DATE_OBS'] as $key=>$val) {
				$tab_to_print['RS'][] = '<IMG src="make_rs.php?what=pro&id='.$results['ID_PROMINENCE'][$key].'" title="Raster Scan"</IMG>';
			}
			break;
		case "sp":
			$fields_to_print[] = 'RS';
			foreach ($results['DATE_OBS'] as $key=>$val) {
				$tab_to_print['RS'][] = '<IMG src="make_rs.php?what=sp&id='.$results['ID_SUNSPOT'][$key].'" title="Raster Scan"</IMG>';
			}
			break;
	}

	// get min max date_obs for each TRACK_ID
	if (in_array($feature_type, array("fil", "rs"))) {
		$bid = array_unique(array_values($tab_to_print['TRACK_ID']));
		if ($bid[0] != null)
			$minmax_dt_by_track_id = get_minmax_date_for_track2($feature_type, array_unique($tab_to_print['TRACK_ID']));
		}
	print '<div class="portlet_'.$feature_type.'">';
	print '<div class="portlet-header">Tabular result</div>';
	print '<div class="portlet-content">';
	//print '<TABLE class="tabresults" id="tab_data_'.$feature_type.strtotime($date).'">';
	print '<TABLE id="tab_data_'.$feature_type.strtotime($date).'">';
	print '<THEAD><TR>';
	foreach ($fields_to_print as $field) {
		//print '<TH class="tabresults">'.$tab_comment[$field]."</TH>";
		if (isset($tab_comment[$field])) print '<TH>'.$tab_comment[$field]."</TH>";
		else print '<TH>'.$field."</TH>";
	}
	print "</TR></THEAD>\n";
	print '<TBODY>';
	$curr_feat_id = -1;
	$nbfields = count($tab_to_print[$fields_to_print[0]]);
	for ($j=0; $j<$nbfields; $j++) {
		$style = ($style == "odd") ? "odd" : "even";
		switch ($feature_type) {
			case "rs":
			case "fil": // on groupe une ligne par TRACK_ID
				//if (isset($tab_to_print['TRACK_ID'])) {
				if ($tab_to_print['TRACK_ID'][$j] != null) {
				if ($tab_to_print['TRACK_ID'][$j] != $curr_feat_id) {
					$curr_feat_id = $tab_to_print['TRACK_ID'][$j];
					print "<TR>\n";
				}
				foreach ($fields_to_print as $field) {
					print '<TD>';
					$cnt = 0;
					while ($tab_to_print['TRACK_ID'][$j+$cnt] == $curr_feat_id) {
						if (strcmp($field, 'TRACK_ID') === 0) {
							$script_link = "get_track_info.php?what=".$feature_type."&id=".$tab_to_print['TRACK_ID'][$j + $cnt];
							$link = "javascript:void(window.open('".$script_link."','','scrollbars=1,resizable=1,status=1'))";
							print "<A href=\"".$link."\" title=\"Tracking data\">";
							print $tab_to_print['TRACK_ID'][$j + $cnt];
							print "</A><br>\n";
							// print max and min DATE_OBS for this tack_id
							/*$bid = get_minmax_date_for_track($feature_type, $tab_to_print['TRACK_ID'][$j + $cnt]);
							echo $bid['MIN'].' to<BR>';
							echo $bid['MAX'].'<BR>';*/
							$key_dt = array_search($curr_feat_id, $minmax_dt_by_track_id['TRACK_ID']);
							echo $minmax_dt_by_track_id['MIN'][$key_dt].' to<BR>';
							echo $minmax_dt_by_track_id['MAX'][$key_dt].'<BR>';
							break;
						}
						else if (strcmp($field, 'PHENOM') === 0){
							print $global['PHENOM'][$tab_to_print[$field][$j + $cnt]].'<br>';
							break;
						} 
						else print $tab_to_print[$field][$j + $cnt].'<br>';
						$cnt++;
					}
					print "</TD>";
				}
				if ($tab_to_print['TRACK_ID'][$j] != $curr_feat_id) print "</TR>\n";
				$j = $j + $cnt - 1;
				} else {
					print "<TR>\n";
					foreach ($fields_to_print as $field) {
						if (!strcmp($tab_to_print[$field][$j], 'NULL')) $tab_to_print[$field][$j] = '-';
						print '<TD class="tabresults">'.$tab_to_print[$field][$j]."</TD>";
					}
					print "</TR>\n";
				}
				break;
			default:
				print "<TR>\n";
				foreach ($fields_to_print as $field) {
					if (!strcmp($tab_to_print[$field][$j], 'NULL')) $tab_to_print[$field][$j] = '-';
					//print '<TD class="tabresults">'.$tab_to_print[$field][$j]."</TD>";
					print '<TD>'.$tab_to_print[$field][$j]."</TD>";
				}
				print "</TR>\n";
				break;
		}
	}
	print '</TBODY>';
	print "</TABLE>\n";
	echo '</div></div>';

}

function get_minmax_date_for_track($feat_type, $track_id) {
	switch($feat_type) {
		case 'fil':
			$query = "SELECT DATE_FORMAT(min(DATE_OBS), '%Y-%m-%d') as MIN, DATE_FORMAT(max(DATE_OBS), '%Y-%m-%d') as MAX ";
			$query = $query." FROM FILAMENTS,OBSERVATIONS,PP_OUTPUT,FILAMENTS_TRACKING ";
			$query = $query."WHERE TRACK_ID=".$track_id." AND PP_OUTPUT_ID=ID_PP_OUTPUT AND FIL_ID=ID_FIL AND OBSERVATIONS_ID=ID_OBSERVATIONS";
			break;
		case 'rs':
			$query = "SELECT DATE_FORMAT(min(DATE_OBS), '%Y-%m-%d') as MIN, DATE_FORMAT(max(DATE_OBS), '%Y-%m-%d') as MAX ";
			$query = $query." FROM RADIOSOURCES,OBSERVATIONS,RS_TRACKING ";
			$query = $query."WHERE TRACK_ID=".$track_id." AND RS_ID=ID_RS AND OBSERVATIONS_ID=ID_OBSERVATIONS";
			break;
	}
	
	$rs = execute_query($query);

	return array('MIN'=>$rs['MIN'][0], 'MAX'=>$rs['MAX'][0]);
}

function get_minmax_date_for_track2($feat_type, $track_ids) {

	$tab_res = array();
	$query_in = implode(",", $track_ids);
	switch($feat_type) {
		case 'fil':
			$query = "SELECT TRACK_ID, DATE_FORMAT(DATE_OBS, '%Y-%m-%d') AS DATE_OBS";
			$query = $query." FROM FILAMENTS,OBSERVATIONS,PP_OUTPUT,FILAMENTS_TRACKING ";
			$query = $query."WHERE TRACK_ID IN (".$query_in.") AND PP_OUTPUT_ID=ID_PP_OUTPUT AND FIL_ID=ID_FIL AND OBSERVATIONS_ID=ID_OBSERVATIONS";
			break;
		case 'rs':
			$query = "SELECT TRACK_ID, DATE_FORMAT(DATE_OBS, '%Y-%m-%d') AS DATE_OBS";
			$query = $query." FROM RADIOSOURCES,OBSERVATIONS,RS_TRACKING ";
			$query = $query."WHERE TRACK_ID IN (".$query_in.") AND RS_ID=ID_RS AND OBSERVATIONS_ID=ID_OBSERVATIONS";
			break;
	}
	
	$rs = execute_query($query);
	foreach($track_ids as $track_id) {
		$keys = array_keys($rs['TRACK_ID'], $track_id);
		$tab_dt = array_intersect_key($rs['DATE_OBS'], array_flip($keys));
		$tab_res['TRACK_ID'][] = $track_id;
		$tab_res['MIN'][] = min($tab_dt);
		$tab_res['MAX'][] = max($tab_dt);
	}

	return $tab_res;
}
?>
