<?php
// Creation d'une image png d'un ensemble de structures solaires a une date donnee, projection Carrington
// appel: makemap_carr.php?date=datestring&feat=fil[,ar[,ch[,sp]]]|all&usesess=1|2
// usesess = 1 use results contents in $_SESSION['rs_fil|ar|ch|sp'])
// usesess = 2 use results contents in $_SESSION['rs_feat_to_print'])
// Si feat=all: carte synoptique de toutes les structures a la date donnée à midi

session_start();
require_once("plot_solar_struct.php");
require("functions.php");

if (isset($_GET['date'])) $date = $_GET['date'];
else exit(0);
if (isset($_GET['feat'])) $feature_type = $_GET['feat'];
else exit(0);
if (isset($_GET['zoom'])) $zoom = $_GET['zoom'];
else $zoom = 1;
if (isset($_GET['usesess'])) $use_sess = $_GET['usesess'];

$timestmp = strtotime($date);
$date_array = getdate($timestmp);
$start_day=$date_array['mday']; $start_month=$date_array['mon']; $start_year=$date_array['year'];

$filename = "sunmap_daily_carr".$start_day."_".$start_month."_".$start_year.".png";

// Date in the past
header ("Expires: Mon, 26 Jul 1997 05:00:00 GMT");
// always modified
header ("Last-Modified: " . gmdate("D, d M Y H:i:s") . " GMT");
// HTTP/1.1
header ("Cache-Control: no-cache, no-store, must-revalidate");  
// HTTP/1.0
header ("Pragma: No-Cache");
header("Content-Disposition: attachment; filename=$filename");
header("Content-type: image/png");

// determination du numero de rotation correspondant a la date
// et des parametres du soleil
if (checkdate($start_month, $start_day, $start_year)) {
	$dj = new Datej();
	if (!strcmp($feature_type, 'all'))
		$dj->initFromCal($start_year, $start_month, $start_day, 12, 0);
	else
		$dj->initFromCal($start_year, $start_month, $start_day, $date_array['hours'], $date_array['minutes']);
	solphy($dj->dj, $lo, $bo, $po);
	$bid = debutrot($dj->dj, $datedeb, $numrot);
}
else exit(0);

$table_names = array('fil'=>'VIEW_FIL_GUI', 'pro'=>'VIEW_PRO_GUI', 'ar'=>'VIEW_AR_GUI', 'ch'=>'VIEW_CH_GUI', 'sp'=>'VIEW_SP_GUI');
// cas de la carte synoptique complete
if (!strcmp($feature_type, 'all')) {
	// query all the datas for the selected date
	$rs = array();
	foreach ($table_names as $feat=>$table) {
		$sql_query = "SELECT * FROM $table ";
		$sql_query = $sql_query."WHERE DATE(DATE_OBS)='$date'";
		$rs[$feat] = execute_query($sql_query);
	}
	// for each feature, select the nearest date from 12:00
	$noon_date = strtotime($date) + 12*60*60;
	$tab_dt = array();
	foreach ($rs as $feat=>$res) {
		if (count($res['DATE_OBS'])) {
			$tab_date = array_values(array_unique($res['DATE_OBS']));
			$interval = 24*60*60;
			foreach($tab_date as $dt) {
				$dt_timestamp = strtotime($dt);
				if (abs($noon_date-$dt_timestamp) < $interval) {
					$interval = abs($noon_date-$dt_timestamp);
					$tab_dt[$feat] = $dt;
				}
			}
		}
	}
	// query all the datas for each date near noon
	$rs = array();
	foreach ($table_names as $feat=>$table) {
		if (isset($tab_dt[$feat])) {
			$sql_query = "SELECT * FROM $table ";
			$sql_query = $sql_query."WHERE DATE_OBS='".$tab_dt[$feat]."'";
			$rs[$feat] = execute_query($sql_query);
		}
	}
} else {
	$rs = array();
	$tabfeat = explode(',', $feature_type);
	foreach ($tabfeat as $feat) {
		// utilisation des resultats figurant dans les parametres de sessions s'il y en a
		if (isset($use_sess)) {
			switch ($use_sess) {
				case 1: 
					$key = 'rs_'.$feat;
					$tmp = $_SESSION[$key];
					break;
				case 2: 
					$tmp = $_SESSION['rs_feat_to_print'];
					break;
				default: exit(0);
			}
			$keys = array_keys($tmp['DATE_OBS'], $date);
			foreach ($keys as $row)
				foreach ($tmp as $field=>$tab) $rs[$feat][$field][] = $tab[$row];
		} else {
			$sql_query = "SELECT * FROM ".$table_names[$feat]." ";
			$sql_query = $sql_query."WHERE DATE_OBS='$date'";
			$rs[$feat] = execute_query($sql_query);
			if (count($rs[$feat]['DATE_OBS']) == 0) {
				$sql_query = "SELECT * FROM ".$table_names[$feat]." ";
				$sql_query = $sql_query."WHERE DATE(DATE_OBS)='$date'";
				$rs[$feat] = execute_query($sql_query);
			}
		}
	}
}

if (isset($rs['fil'])) $rs_fil = $rs['fil'];
if (isset($rs['ar'])) $rs_ar = $rs['ar'];
if (isset($rs['ch'])) $rs_ch = $rs['ch'];
if (isset($rs['sp'])) $rs_sp = $rs['sp'];
if (isset($rs['pro'])) $rs_pro = $rs['pro'];

	$sizeX = 1024*$zoom;
	$sizeY = 1024*$zoom;
	$r = ($sizeX-40)/2;
	$im = imagecreatetruecolor($sizeX, $sizeY);
	$cWhite=ImageColorAllocate($im, 255, 255, 255);
	ImageFill($im, 0, 0, $cWhite);
	draw_soleil($im, $lo, $bo, $po, $sizeX, $r);
	$cBlack=ImageColorAllocate($im, 0, 0, 0);
	$font_ttf = $global['FONT_PATH'];
	ImageTTFText($im, 8, 0, 3, 15, $cBlack, $font_ttf, 'HELIO features catalog/Paris Observatory');
	//ImageTTFText($im, 10, 0, 3, 35, $cBlack, $font_ttf, $start_year."-".$start_month."-".$start_day);
	ImageTTFText($im, 10, 0, 3, 35, $cBlack, $font_ttf, str_replace(' ', 'T', $date));
	ImageTTFText($im, 8, 0, 3, 50, $cBlack, $font_ttf, "Rotation: ".$numrot);
	ImageTTFText($im, 8, 0, 3, 65, $cBlack, $font_ttf, "L0: ".(round(rad2deg($lo)))."° P0: ".(round(rad2deg($po)))."°");
	// encadrement
	ImageRectangle($im, 0, 0, $sizeX-1, $sizeY-1, $cBlack);
	// ajout de la legende
	$img_leg = ImageCreateFromPNG('legende_map_carr.png');
	imagecopyresampled($im,$img_leg,1,$sizeY-imagesy($img_leg)-1,0,0, $zoom*imagesx($img_leg), $zoom*imagesy($img_leg),imagesx($img_leg),imagesy($img_leg));
	ImageTTFText($im, 8, 0, 3, $sizeY-imagesy($img_leg)-10, $cBlack, $font_ttf, "x NNNN: NOAA number");
	imagedestroy($img_leg);
	// ajout du logo
	$img_logo = ImageCreateFromJPEG('helio_logo4_sm.jpg');
	imagecopyresampled($im, $img_logo, $sizeX-imagesx($img_logo)/2-1, 1, 0, 0,  imagesx($img_logo)/2, imagesy($img_logo)/2, imagesx($img_logo), imagesy($img_logo));
	imagedestroy($img_logo);

if (count($rs_ch['ID_CORONALHOLES'])) {
	$max_intensity = min($rs_ch['FEAT_MEAN2QSUN']);
	$min_intensity = max($rs_ch['FEAT_MEAN2QSUN']);
	foreach($rs_ch['ID_CORONALHOLES'] as $key=>$id_ch) {
		$row = array();
		foreach($rs_ch as $field=>$val) $row[$field] = $val[$key];

		$pix_param = array('NAXIS1'=>$row['NAXIS1'], 'NAXIS2'=>$row['NAXIS2'], 'CDELT1'=>$row['CDELT1'],
						'CDELT2'=>$row['CDELT2'], 'CENTER_X'=>$row['CENTER_X'], 'CENTER_Y'=>$row['CENTER_Y'],
						'R_SUN'=>$row['R_SUN'], 'DATE_OBS'=>$row['DATE_OBS']);
		$start_pix = array($row['CC_X_PIX'], $row['CC_Y_PIX']);

		$ar_info = array();
		$ar_info['ID'] = $row['ID_CORONALHOLES'];
		if ($max_intensity == $min_intensity) $ar_info['INTENSITY'] = 155;
		else $ar_info['INTENSITY'] = (100/($max_intensity-$min_intensity))*($row['FEAT_MEAN2QSUN']-$min_intensity)+55;
		$lon_lat_feature = make_tab_carr_coord2($start_pix, $row['CC'], $pix_param);
		$ar_info['LON'] = $lon_lat_feature[0]; $ar_info['LAT'] = $lon_lat_feature[1];
		$col=ImageColorAllocate($im, 255-$ar_info['INTENSITY'], 255-$ar_info['INTENSITY'], 255-$ar_info['INTENSITY']);
		draw_plage_spher($im, $ar_info, 0, $lo, $bo, $po, $sizeX, $r, $col);
	}
}

if (count($rs_ar['ID_AR'])) {
	$max_intensity = max($rs_ar['FEAT_MEAN_INT']);
	$min_intensity = min($rs_ar['FEAT_MEAN_INT']);
	foreach($rs_ar['ID_AR'] as $key=>$id_ar) {
		$row = array();
		foreach($rs_ar as $field=>$val) $row[$field] = $val[$key];

		$pix_param = array('NAXIS1'=>$row['NAXIS1'], 'NAXIS2'=>$row['NAXIS2'], 'CDELT1'=>$row['CDELT1'],
						'CDELT2'=>$row['CDELT2'], 'CENTER_X'=>$row['CENTER_X'], 'CENTER_Y'=>$row['CENTER_Y'],
						'R_SUN'=>$row['R_SUN'], 'DATE_OBS'=>$row['DATE_OBS']);
		$start_pix = array($row['CC_X_PIX'], $row['CC_Y_PIX']);

		$ar_info = array();
		if ($max_intensity == $min_intensity) $ar_info['INTENSITY'] = 255;
		else $ar_info['INTENSITY'] = (200/($max_intensity-$min_intensity))*($row['FEAT_MEAN_INT']-$min_intensity)+55;
		$lon_lat_feature = make_tab_carr_coord2($start_pix, $row['CC'], $pix_param);
		$ar_info['LON'] = $lon_lat_feature[0]; $ar_info['LAT'] = $lon_lat_feature[1];
		$ar_info['LON_B'] = $row['FEAT_CARR_LONG_DEG']; $ar_info['LAT_B'] = $row['FEAT_CARR_LAT_DEG'];
		$col=ImageColorAllocate($im, 255, 255-$ar_info['INTENSITY'], 255-$ar_info['INTENSITY']);
		draw_plage_spher($im, $ar_info, 0, $lo, $bo, $po, $sizeX, $r, $col);
	}
}

if (count($rs_sp['ID_SUNSPOT'])) {
	$ar_info = array();
	$ar_info['ID'] = $rs_sp['ID_SUNSPOT'];
	$ar_info['DIAM'] = $rs_sp['FEAT_DIAM_DEG'];
	$ar_info['LON_C'] = $rs_sp['FEAT_CARR_LONG_DEG']; $ar_info['LAT_C'] = $rs_sp['FEAT_CARR_LAT_DEG'];
	$ar_info['INTENSITY'] = $rs_sp['FEAT_MEAN2QSUN'];
	draw_spot_spher($im, $ar_info, 0, $lo, $bo, $po, $sizeX, $r);
}

if (count($rs_fil['ID_FIL'])) {
	$max_intensity = min($rs_fil['FEAT_MEAN2QSUN']);
	$min_intensity = max($rs_fil['FEAT_MEAN2QSUN']);
	foreach($rs_fil['ID_FIL'] as $key=>$id_fil) {
		// draw filament
		//extract the current row from $rs1 and put in a simple array
		$row = array();
		foreach($rs_fil as $field=>$val) $row[$field] = $val[$key];

		$pix_param = array('NAXIS1'=>$row['NAXIS1'], 'NAXIS2'=>$row['NAXIS2'], 'CDELT1'=>$row['CDELT1'],
						'CDELT2'=>$row['CDELT2'], 'CENTER_X'=>$row['CENTER_X'], 'CENTER_Y'=>$row['CENTER_Y'],
						'R_SUN'=>$row['R_SUN'], 'DATE_OBS'=>$row['DATE_OBS']);
		$start_pix = array($row['CC_X_PIX'], $row['CC_Y_PIX']);
	
		// draw filament skeleton
		// only need LON and LAT
		$ar_info = array();
		$ar_info['ID'] = $row['ID_FIL'];
		if ($max_intensity == $min_intensity) $ar_info['INTENSITY'] = 255;
		else $ar_info['INTENSITY'] = (200/($max_intensity-$min_intensity))*($row['FEAT_MEAN2QSUN']-$min_intensity)+55;
		$start_pix = array($row['SKE_CC_X_PIX'], $row['SKE_CC_Y_PIX']);
		$lon_lat_feature = make_tab_carr_coord2($start_pix, $row['SKE_CC'], $pix_param);
		$ar_info['LON'] = $lon_lat_feature[0]; $ar_info['LAT'] = $lon_lat_feature[1];
		$ar_info['LON_B'] = $row['FEAT_CARR_LONG_DEG']; $ar_info['LAT_B'] = $row['FEAT_CARR_LAT_DEG'];
		imagesetthickness($im, 5);
		draw_filam_spher($im, $ar_info, 0, $lo, $bo, $po, $sizeX, $r);
	}
}

if (count($rs_pro['ID_PROMINENCE'])) {
	$max_intensity = max($rs_pro['FEAT_MEAN_INT']);
	$min_intensity = min($rs_pro['FEAT_MEAN_INT']);
	foreach($rs_pro['ID_PROMINENCE'] as $key=>$id) {
		//extract the current row from $rs1 and put in a simple array
		$row = array();
		foreach($rs_pro as $field=>$val) $row[$field] = $val[$key];

		$par_info = array();
		$par_info['LAT1'] = $row['BASE_MID_CARR_LAT_DEG'] + $row['DELTA_LAT_DEG']/2;
		$par_info['LAT2'] = $row['BASE_MID_CARR_LAT_DEG'] - $row['DELTA_LAT_DEG']/2;
		$par_info['LON'] = $row['BASE_MID_CARR_LONG_DEG'];
		$par_info['HEIGHT'] = $row['FEAT_HEIGHT_KM'];
		if (rad2deg($lo) < 180) {
			if (abs((90+rad2deg($lo)) - $par_info['LON']) <  10) $par_info['EDGE'] = 'W';
			else $par_info['EDGE'] = 'E';
		} else {
			if (abs((rad2deg($lo)-90) - $par_info['LON']) <  10) $par_info['EDGE'] = 'E';
			else $par_info['EDGE'] = 'W';
		}
		if ($max_intensity == $min_intensity) $par_info['INTENSITY'] = 255;
		else $par_info['INTENSITY'] = (200/($max_intensity-$min_intensity))*($row['FEAT_MEAN_INT']-$min_intensity)+55;
		draw_promi_sphere($im, $par_info, 0, $lo, $bo, $po, $sizeX, $r);
	}
}

// Traitement de la partie noaa
$query = "SELECT * FROM t_noaa WHERE DATE(DATE_OBS)=DATE('$date')";
$rs_noaa = query_BASS2000($query);
if (count($rs_noaa['ID_NOAA'])) {
	foreach($rs_noaa['ID_NOAA'] as $key=>$id) {
		draw_noaa_helio($im, $rs_noaa, $key, $lo, $bo, $po, $sizeX, $r);
	}
}

/*
// Traitement de la partie protu
if (isset($tab_result['prom']) && count($tab_result['prom']['ID_PROMI'])) {
	$rs = $tab_result['prom'];
	$nb = count($rs['ID_PROMI']);
	foreach($rs['ID_PROMI'] as $key=>$id) {
		switch($rs['SOLROT_N'][$key]) {
			case $numrot-1: $flg = 1; break;
			case $numrot+1: $flg = -1; break;
			default: $flg = 0; break;
		}
		unset($tmp);
		$tmp['LON'] = $rs['LON'][$key]; $tmp['LAT1'] = $rs['LAT1'][$key]; $tmp['LAT2'] = $rs['LAT2'][$key];
		$tmp['HEIGHT'] = $rs['HEIGHT'][$key]; $tmp['EDGE'] = $rs['EDGE'][$key];
		switch ($style) {
			case "flat":
				draw_promi($im, $tmp, $flg, $sizeX, $sizeY, $xc, $yc, $xi, $yi);
				break;
			default:
				draw_promi_sphere($im, $tmp, $flg, $lo, $bo, $po, $sizeX, $r);
				break;
		}
	}
}

*/

imagePNG($im);
imagedestroy($im);

function query_BASS2000($query) {
	$tbsp = 'devprod';
	//IDENTIFIANT MYSQL
	$MYSQL_HOST="wb2000.obspm.fr";
	$MYSQL_USER="guest";
	$MYSQL_PASS="guest";

	$connected = mysql_connect($MYSQL_HOST,$MYSQL_USER,$MYSQL_PASS) or die("Erreur de connexion au serveur.");
	$db = mysql_select_db($tbsp) or die ("erreur de connexion base: $tbsp!!");

	//echo '***'.$query.'***';
	$stmt = mysql_query($query, $connected);
	if (gettype($stmt) == "resource") {
		$results = array();
		$nbrows = mysql_num_rows($stmt);
		while ($row = mysql_fetch_assoc($stmt)) {
			foreach($row as $key => $val) {
				$results[strtoupper($key)][] = $val;
			}//finFOREACH
		}//finWHILE
	//print_r($results);
	//echo '<br><br>';
		mysql_free_result($stmt);	//libère la ressource
		mysql_close($connected);	//fin conn
	}//finIF
	else{
		echo '<br> #### Impossible d\'executer la requete : ' . mysql_error().' #### <br>';
		$results = null;
		$nbrows = null;
		}
	return $results;
}
?>
