<?php
session_start();
// Creation d'une image png d'un ensemble de structures solaires a une date donnee avec les numeros ID des structures + image d'observation
// appel: makemap_pixobs.php?date=datestring&feat=fil|ar|ch|sp&obs&noaa&usesess=1|2
// usesess = 1 use results contents in $_SESSION['rs_fil|ar|ch|sp'])
// usesess = 2 use results contents in $_SESSION['rs_feat_to_print'])

require_once("plot_solar_struct.php");
require("functions.php");

if (isset($_GET['date'])) $date = $_GET['date'];
else exit(0);
if (isset($_GET['feat'])) $feature_type = $_GET['feat'];
else exit(0);
if (isset($_GET['obs'])) {
	$with_obs = true;
	$filled = false;
}
else {
	$with_obs = false;
	$filled = true;
}
if (isset($_GET['zoom'])) $zoom = $_GET['zoom'];
else $zoom = 1;
if (($zoom <= 0) || ($zoom > 3)) $zoom = 1;
if (isset($_GET['noaa'])) $with_noaa = true;
else  $with_noaa = false;
if (isset($_GET['usesess'])) $use_sess = $_GET['usesess'];

date_default_timezone_set('UTC');

$timestmp = strtotime($date);
$date_array = getdate($timestmp);
$start_day=$date_array['mday']; $start_month=$date_array['mon']; $start_year=$date_array['year'];

//$filename = "sunmap".$start_day."_".$start_month."_".$start_year.".png";
$bid = explode(' ', $date);
$filename = "sunmap".$bid[0].'_'.$bid[1].'_'.$feature_type.".png";

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
	$dj->initFromCal($start_year, $start_month, $start_day, $date_array['hours'], $date_array['minutes']);
	solphy($dj->dj, $lo, $bo, $po);
	$bid = debutrot($dj->dj, $datedeb, $numrot);
}
else exit(0);

// Requete au HFC pour la date donnee
switch ($feature_type) {
	case 'fil':
		// utilisation des resultats figurant dans les parametres de sessions s'il y en a
		if (isset($use_sess)) {
			switch ($use_sess) {
				case 1: 
					$tmp = $_SESSION['rs_fil'];
					break;
				case 2: 
					$tmp = $_SESSION['rs_feat_to_print'];
					break;
				default: exit(0);
			}
			$rs = array();
			/*foreach ($tmp['ID_FIL'] as $key=>$id_fil)
				if (!strcmp($tmp['DATE_OBS'][$key], $date)) 
					foreach ($tmp as $field=>$tab) $rs[$field][] = $tab[$key];*/
			$keys = array_keys($tmp['DATE_OBS'], $date);
			foreach ($keys as $row)
				foreach ($tmp as $field=>$tab) $rs[$field][] = $tab[$row];
		} else {
			$sql_query = "SELECT * FROM VIEW_FIL_HQI ";
			$sql_query = $sql_query."WHERE DATE_OBS='$date'";
			$rs = execute_query($sql_query);
			if (count($rs['ID_FIL']) == 0) {
				$sql_query = "SELECT * FROM VIEW_FIL_HQI ";
				$sql_query = $sql_query."WHERE DATE(DATE_OBS)='$date'";
				$rs = execute_query($sql_query);
			}
		}
		break;
	case 'pro':
		// utilisation des resultats figurant dans les parametres de sessions s'il y en a
		if (isset($use_sess)) {
			switch ($use_sess) {
				case 1: 
					$tmp = $_SESSION['rs_pro'];
					break;
				case 2: 
					$tmp = $_SESSION['rs_feat_to_print'];
					break;
				default: exit(0);
			}
			$rs = array();
			$keys = array_keys($tmp['DATE_OBS'], $date);
			foreach ($keys as $row)
				foreach ($tmp as $field=>$tab) $rs[$field][] = $tab[$row];
		} else {
			$sql_query = "SELECT * FROM VIEW_PRO_HQI ";
			$sql_query = $sql_query."WHERE DATE_OBS='$date'";
			$rs = execute_query($sql_query);
			if (count($rs['ID_PROMINENCE']) == 0) {
				$sql_query = "SELECT * FROM VIEW_PRO_HQI ";
				$sql_query = $sql_query."WHERE DATE(DATE_OBS)='$date'";
				$rs = execute_query($sql_query);
			}
		}
		break;
	case 'ch':
		if (isset($use_sess)) {
			switch ($use_sess) {
				case 1: 
					$tmp = $_SESSION['rs_ch'];
					break;
				case 2: 
					$tmp = $_SESSION['rs_feat_to_print'];
					break;
				default: exit(0);
			}
			$rs = array();
			$keys = array_keys($tmp['DATE_OBS'], $date);
			foreach ($keys as $row)
				foreach ($tmp as $field=>$tab) $rs[$field][] = $tab[$row];
		} else {
			$sql_query = "SELECT * FROM VIEW_CH_HQI ";
			$sql_query = $sql_query."WHERE DATE_OBS='$date'";
			$rs = execute_query($sql_query);
			if (count($rs['ID_CORONALHOLES']) == 0) {
				$sql_query = "SELECT * FROM VIEW_CH_HQI ";
				$sql_query = $sql_query."WHERE DATE(DATE_OBS)='$date'";
				$rs = execute_query($sql_query);
			}
		}
		break;
	case 'ar':
		if (isset($use_sess)) {
			switch ($use_sess) {
				case 1: 
					$tmp = $_SESSION['rs_ar'];
					break;
				case 2: 
					$tmp = $_SESSION['rs_feat_to_print'];
					break;
				default: exit(0);
			}
			$rs = array();
			$keys = array_keys($tmp['DATE_OBS'], $date);
			foreach ($keys as $row)
				foreach ($tmp as $field=>$tab) $rs[$field][] = $tab[$row];
			/*foreach ($tmp['ID_AR'] as $key=>$id_ch)
				if (!strcmp($tmp['AR_DATE'][$key], $date)) 
					foreach ($tmp as $field=>$tab) $rs[$field][] = $tab[$key];*/
		} else {
			$sql_query = "SELECT * FROM VIEW_AR_HQI ";
			$sql_query = $sql_query."WHERE DATE_OBS='$date'";
			$rs = execute_query($sql_query);
			if (count($rs['ID_AR']) == 0) {
				$sql_query = "SELECT * FROM VIEW_AR_HQI ";
				$sql_query = $sql_query."WHERE DATE(DATE_OBS)='$date'";
				$rs = execute_query($sql_query);
			}
		}
		break;
	case 'sp':
		if (isset($use_sess)) {
			switch ($use_sess) {
				case 1: 
					$tmp = $_SESSION['rs_sp'];
					break;
				case 2: 
					$tmp = $_SESSION['rs_feat_to_print'];
					break;
				default: exit(0);
			}
			$rs = array();
			$keys = array_keys($tmp['DATE_OBS'], $date);
			foreach ($keys as $row)
				foreach ($tmp as $field=>$tab) $rs[$field][] = $tab[$row];
		} else {
			$sql_query = "SELECT * FROM VIEW_SP_HQI ";
			$sql_query = $sql_query."WHERE DATE_OBS='$date'";
			//$sql_query = $sql_query."WHERE DATE_OBS='$date' AND FEAT_DIAM_DEG>=CDELT1";
			$rs = execute_query($sql_query);
			if (count($rs['ID_SUNSPOT']) == 0) {
				$sql_query = "SELECT * FROM VIEW_SP_HQI ";
				$sql_query = $sql_query."WHERE DATE(DATE_OBS)='$date'";
				//$sql_query = $sql_query."WHERE DATE(DATE_OBS)='$date' AND FEAT_DIAM_DEG>=CDELT1";
				$rs = execute_query($sql_query);
			}
		}
		break;
	case 'rs':
		if (isset($use_sess)) {
			switch ($use_sess) {
				case 1: 
					$tmp = $_SESSION['rs_rs'];
					break;
				case 2: 
					$tmp = $_SESSION['rs_feat_to_print'];
					break;
				default: exit(0);
			}
			$rs = array();
			$keys = array_keys($tmp['DATE_OBS'], $date);
			foreach ($keys as $row)
				foreach ($tmp as $field=>$tab) $rs[$field][] = $tab[$row];
		} else {
			$sql_query = "SELECT * FROM VIEW_RS_HQI ";
			$sql_query = $sql_query."WHERE DATE_OBS='$date'";
			$rs = execute_query($sql_query);
			if (count($rs['ID_RS']) == 0) {
				$sql_query = "SELECT * FROM VIEW_RS_HQI ";
				$sql_query = $sql_query."WHERE DATE(DATE_OBS)='$date'";
				$rs = execute_query($sql_query);
			}
		}
		$zoom = 3;
		break;
	default:
		exit(0);
}

$im_tmp = false;
// Chargement de l'image d'observation
if ($with_obs) {
	$png_file = $rs['QCLK_URL'][0].'/'.$rs['QCLK_FNAME'][0];

	if (file_exists($png_file)) {
		$im_tmp = @imagecreatefrompng($png_file);
		$msg = $filename['basename'];
	} else {
		$msg = "Error while downloading observation file ".$rsobs['LOC_FILENAME'][0];
		$filled = true; // cela revient a tracer uniquement les structures
	}
}

$sizeX = $rs['NAXIS1'][0]*$zoom;
$sizeY = $rs['NAXIS2'][0]*$zoom;
$xc = $rs['CENTER_X'][0]*$zoom;
$yc=$rs['CENTER_Y'][0]*$zoom;
if (!$im_tmp) {
	$im = imagecreatetruecolor($sizeX, $sizeY);
	$background = ImageColorAllocate($im, 255, 255, 255);
	ImageFill($im, 0, 0, $background);
	$txt_color = ImageColorAllocate($im, 0, 0, 0);
	// ajout de la legende
	$img_leg = ImageCreateFromPNG('feat_int.png');
	imagecopyresampled($im,$img_leg,1,$sizeY-imagesy($img_leg)-1,0,0, imagesx($img_leg),imagesy($img_leg),imagesx($img_leg),imagesy($img_leg));
	imagedestroy($img_leg);
} else {
	$im = imagecreatetruecolor($sizeX,$sizeY);
	imagecopyresampled($im,$im_tmp,0,0,0,0, $sizeX,$sizeY,imagesx($im_tmp),imagesy($im_tmp));
	$txt_color = ImageColorAllocate($im, 255, 255, 255);
	imagedestroy($im_tmp);
}

// ajout du logo
$img_logo = ImageCreateFromJPEG('helio_logo4_sm.jpg');
imagecopyresampled($im,$img_logo,$sizeX-imagesx($img_logo)/2-1,2,0,0, imagesx($img_logo)/2,imagesy($img_logo)/2,imagesx($img_logo),imagesy($img_logo));
//imagecopyresized($im,$img_logo,$sizeX-imagesx($img_logo)/2-1,2,0,0, imagesx($img_logo)/2,imagesy($img_logo)/2,imagesx($img_logo),imagesy($img_logo));
imagedestroy($img_logo);

// dessin du soleil
$font_ttf = $global['FONT_PATH'];
ImageTTFText($im, 8, 0, 3, 15, $txt_color, $font_ttf, 'HELIO features catalog/Paris Observatory');
$date_iso = str_replace(' ', 'T', $date);
ImageTTFText($im, 10, 0, 3, 35, $txt_color, $font_ttf, $date_iso);
ImageTTFText($im, 8, 0, 3, 50, $txt_color, $font_ttf, "Rotation ".$numrot);
ImageTTFText($im, 8, 0, 3, 65, $txt_color, $font_ttf, "P0:".(round(rad2deg($po)))."°");
// Adds code detection name
if (count($rs['CODE'])) ImageTTFText($im, 8, 0, 3, 80, $txt_color, $font_ttf, $rs['CODE'][0]);
if ($with_obs) ImageTTFText($im, 8, 0, 10, $sizeY - 20, $txt_color, $font_ttf, $msg);
ImageEllipse($im, $xc, $sizeY-$yc, 2*$rs['R_SUN'][0]*$zoom, 2*$rs['R_SUN'][0]*$zoom, $txt_color);

// dessin des axes
$c_blue = ImageColorAllocate($im, 0, 0, 255);
$c_white = ImageColorAllocate($im, 255, 255, 255);
$style = array($c_blue, $c_blue, $c_blue, $c_blue, $c_blue, $c_white, $c_white, $c_white, $c_white, $c_white);
imagesetstyle($im, $style);
ImageLine($im, 0, $sizeY-$yc, $sizeX, $sizeY-$yc, IMG_COLOR_STYLED);
ImageLine($im, $xc, 0, $xc, $sizeY, IMG_COLOR_STYLED);
// encadrement
ImageRectangle($im, 0, 0, $sizeX-1, $sizeY-1, $txt_color);

if (isset($rs['ID_CORONALHOLES'])) {
	//$max_intensity = min($rs['CH_MEAN2QSUN']);
	//$min_intensity = max($rs['CH_MEAN2QSUN']);
	$max_intensity = min($rs['FEAT_MEAN2QSUN']);
	$min_intensity = max($rs['FEAT_MEAN2QSUN']);
	foreach($rs['ID_CORONALHOLES'] as $key=>$id_ch) {
		$row = array();
		foreach($rs as $field=>$val) $row[$field] = $val[$key];

		$start_pix = array($row['CC_X_PIX'], $row['CC_Y_PIX']);
		$pix_xy = make_tab_pix_coord($start_pix, $row['CC']);
		$feat_id = $row['ID_CORONALHOLES'];
		//$intensity = 10*$row['CH_MEAN2QSUN'];
		if ($max_intensity == $min_intensity) $intensity = 255;
		else $intensity = (200/($max_intensity-$min_intensity))*($row['FEAT_MEAN2QSUN']-$min_intensity)+55;
		$c_x = $row['FEAT_X_PIX'];
		$c_y = $row['FEAT_Y_PIX'];
		draw_feat_pix($im, $pix_xy[0], $pix_xy[1], $c_x, $c_y, $intensity, $feat_id, $filled, $zoom);
	}
}

if (isset($rs['ID_AR'])) {
	ImageTTFText($im, 8, 0, $sizeX/4, $sizeY-10, $txt_color, $font_ttf, 'NOAA numbers are in brackets');
	$max_intensity = max($rs['FEAT_MEAN_INT']);
	$min_intensity = min($rs['FEAT_MEAN_INT']);
	foreach($rs['ID_AR'] as $key=>$id_ar) {
		$row = array();
		foreach($rs as $field=>$val) $row[$field] = $val[$key];

		$start_pix = array($row['CC_X_PIX'], $row['CC_Y_PIX']);
		$pix_xy = make_tab_pix_coord($start_pix, $row['CC']);
		if ($max_intensity == $min_intensity) $intensity = 255;
		else $intensity = (200/($max_intensity-$min_intensity))*($row['FEAT_MEAN_INT']-$min_intensity)+55;
		$feat_id = $row['ID_AR'];
		if ($row['NOAA_NUMBER'] != null) $feat_id = $feat_id.' ('.$row['NOAA_NUMBER'].')';

		// calculate ar center position in pixel as these values doesn't exist in table
		$c_x = $row['FEAT_X_ARCSEC']/$row['CDELT1'] + $row['NAXIS1']/2;
		$c_y = $row['FEAT_Y_ARCSEC']/$row['CDELT2'] + $row['NAXIS2']/2;
		draw_feat_pix($im, $pix_xy[0], $pix_xy[1], $c_x, $c_y, $intensity, $feat_id, $filled, $zoom);
	}
}

if (isset($rs['ID_SUNSPOT'])) {
	ImageTTFText($im, 8, 0, $sizeX/4, $sizeY-10, $txt_color, $font_ttf, 'NOAA numbers are in brackets');
	$max_intensity = min($rs['FEAT_MEAN2QSUN']);
	$min_intensity = max($rs['FEAT_MEAN2QSUN']);
	foreach($rs['ID_SUNSPOT'] as $key=>$id_ch) {
		$row = array();
		foreach($rs as $field=>$val) $row[$field] = $val[$key];

		$feat_id = $row['ID_SUNSPOT'];
		if ($row['NOAA_NUMBER'] != null) $feat_id = $feat_id.' ('.$row['NOAA_NUMBER'].')';
		if ($max_intensity == $min_intensity) $intensity = 255;
		else $intensity = (200/($max_intensity-$min_intensity))*($row['FEAT_MEAN2QSUN']-$min_intensity)+55;
		$c_x = $row['FEAT_X_PIX'];
		$c_y = $row['FEAT_Y_PIX'];
		if (strlen($row['CC'])) {
			$start_pix = array($row['CC_X_PIX'], $row['CC_Y_PIX']);
			$pix_xy = make_tab_pix_coord($start_pix, $row['CC']);
			if ($row['FEAT_DIAM_DEG'] >= 0.5) draw_feat_pix($im, $pix_xy[0], $pix_xy[1], $c_x, $c_y, $intensity, $feat_id, $filled, $zoom);
		} else {
			$diam_x = $row['BR_X2_PIX'] - $row['BR_X0_PIX'];
			$diam_y = $row['BR_Y1_PIX'] - $row['BR_Y0_PIX'];
			if ($row['FEAT_DIAM_DEG'] >= 0.5) {
				//draw_feat_pix($im, $pix_xy[0], $pix_xy[1], $c_x, $c_y, $intensity, $feat_id, $filled, $zoom);
				draw_spot_pix($im, array($diam_x, $diam_y), $c_x, $c_y, $intensity, $feat_id, $filled, $zoom);
			}
		}
	}
}

if (isset($rs['ID_FIL'])) {
	$max_intensity = min($rs['FEAT_MEAN2QSUN']);
	$min_intensity = max($rs['FEAT_MEAN2QSUN']);
	foreach($rs['ID_FIL'] as $key=>$id_fil) {
		$row = array();
		foreach($rs as $field=>$val) $row[$field] = $val[$key];

		$start_pix = array($row['CC_X_PIX'], $row['CC_Y_PIX']);
		$pix_xy = make_tab_pix_coord($start_pix, $row['CC']);
		$feat_id = $row['ID_FIL'];
		if ($max_intensity == $min_intensity) $intensity = 255;
		else $intensity = (200/($max_intensity-$min_intensity))*($row['FEAT_MEAN2QSUN']-$min_intensity)+55;
		$c_x = $row['FEAT_X_ARCSEC']/$row['CDELT1'] + $row['NAXIS1']/2;
		$c_y = $row['FEAT_Y_ARCSEC']/$row['CDELT2'] + $row['NAXIS2']/2;
		draw_feat_pix($im, $pix_xy[0], $pix_xy[1], $c_x, $c_y, $intensity, $feat_id, $filled, $zoom);
			
		// draw filament skeleton
		$start_pix = array($row['SKE_CC_X_PIX'], $row['SKE_CC_Y_PIX']);
		$pix_xy = make_tab_pix_coord($start_pix, $row['SKE_CC']);
		$c_x = $row['FEAT_X_ARCSEC']/$row['CDELT1'] + $row['NAXIS1']/2;
		$c_y = $row['FEAT_Y_ARCSEC']/$row['CDELT2'] + $row['NAXIS2']/2;
		draw_skel_filam_pix($im, $pix_xy[0], $pix_xy[1], $c_x, $c_y, $intensity, $feat_id, $zoom);

	}
}

if (isset($rs['ID_PROMINENCE'])) {
	$max_intensity = max($rs['FEAT_MEAN_INT']);
	$min_intensity = min($rs['FEAT_MEAN_INT']);
	foreach($rs['ID_PROMINENCE'] as $key=>$id_pro) {
		$row = array();
		foreach($rs as $field=>$val) $row[$field] = $val[$key];

		$ccs = explode($row['BLOB_SEPARATOR'], $row['CC']);
		$ccs_x_pix = explode($row['BLOB_SEPARATOR'], $row['CC_X_PIX']);
		$ccs_y_pix = explode($row['BLOB_SEPARATOR'], $row['CC_Y_PIX']);
		$nbcc = count($ccs);
		for ($i=0; $i<$nbcc; $i++) {
			$start_pix = array($ccs_x_pix[$i], $ccs_y_pix[$i]);
			$pix_xy = make_tab_pix_coord($start_pix, $ccs[$i]);
			$feat_id = $row['ID_PROMINENCE'];
			if ($max_intensity == $min_intensity) $intensity = 255;
			else $intensity = (200/($max_intensity-$min_intensity))*($row['FEAT_MEAN_INT']-$min_intensity)+55;
			$c_x = $row['BASE_MID_X_PIX'];
			$c_y = $row['BASE_MID_Y_PIX'];
			draw_feat_pix($im, $pix_xy[0], $pix_xy[1], $c_x, $c_y, $intensity, $feat_id, $filled, $zoom);
			draw_skel_filam_pix($im, $pix_xy[0], $pix_xy[1], $c_x, $c_y, $intensity, $feat_id, $zoom);
		}
	}
}

if (isset($rs['ID_RS'])) {
	$max_intensity = min($rs['FEAT_MEAN_INT']);
	$min_intensity = max($rs['FEAT_MEAN_INT']);
	foreach($rs['ID_RS'] as $key=>$id_rs) {
		$row = array();
		foreach($rs as $field=>$val) $row[$field] = $val[$key];

		$start_pix = array($row['CC_X_PIX'], $row['CC_Y_PIX']);
		$pix_xy = make_tab_pix_coord($start_pix, $row['CC']);
		$feat_id = $row['ID_RS'];
		if ($max_intensity == $min_intensity) $intensity = 255;
		else $intensity = (200/($max_intensity-$min_intensity))*($row['FEAT_MEAN_INT']-$min_intensity)+55;
		$c_x = $row['FEAT_X_PIX'];
		$c_y = $row['FEAT_Y_PIX'];
		draw_feat_pix($im, $pix_xy[0], $pix_xy[1], $c_x, $c_y, $intensity, $feat_id, $filled, $zoom);
		//draw_skel_filam_pix($im, $pix_xy[0], $pix_xy[1], $c_x, $c_y, $intensity, $feat_id, $zoom);
	}
}

imagePNG($im);
imagedestroy($im);

function draw_feat_pix($im, $tab_xpix, $tab_ypix, $c_x, $c_y, $intensity, $feat_id, $filled, $zoom) {
	global $global;
	$font_ttf = $global['FONT_PATH'];
	$sizey = imagesy($im);
	$nb = count($tab_xpix);
	$points = array();
	if ($filled) $col=ImageColorAllocate($im, 255, 255-$intensity, 255-$intensity);
	else $col=ImageColorAllocate($im, 255, 255, 255);
	if ($filled) $gc_col=ImageColorAllocate($im,0,0,0);
	else $gc_col=ImageColorAllocate($im, 0, 0, 255);
	for($i=0;$i<$nb;$i++){
		$points[] = $tab_xpix[$i]*$zoom;
		$points[] = $sizey - $tab_ypix[$i]*$zoom; // en PHP, l'origine de l'image est coin gauche haut
	}
	if (count($points) > 0) {
		if ($filled) ImageFilledPolygon ($im, $points, $nb, $col);
		ImagePolygon ($im, $points, $nb, $col);
	}
	imagefilledellipse($im, $c_x*$zoom, $sizey - $c_y*$zoom, 2, 2, $gc_col);
	//ImageString ($im, 3, $c_x*$zoom-strlen($feat_id), $sizey-$c_y*$zoom+5, $feat_id, $gc_col);
	$textArray = explode(' ', $feat_id);
	$y = $sizey-$c_y*$zoom+15;
	foreach ($textArray as $txt) {
		ImageTTFText($im, 8, 0, $c_x*$zoom-strlen($feat_id)+10, $y, $gc_col, $font_ttf, $txt);
		$y += 15;
	}
	//ImageTTFText($im, 8, 0, $c_x*$zoom-strlen($feat_id)+10, $sizey-$c_y*$zoom+15, $gc_col, $font_ttf, $feat_id);
}

function draw_spot_pix($im, $diam, $c_x, $c_y, $intensity, $feat_id, $filled, $zoom) {
	global $global;
	$font_ttf = $global['FONT_PATH'];
	$sizey = imagesy($im);
	if ($filled) $col=ImageColorAllocate($im, 255, 255-$intensity, 255-$intensity);
	else $col=ImageColorAllocate($im, 255, 255, 255);
	if ($filled) $gc_col=ImageColorAllocate($im,0,0,0);
	else $gc_col=ImageColorAllocate($im, 0, 0, 255);

	if ($filled) imagefilledellipse($im, $c_x*$zoom, $sizey - $c_y*$zoom, $diam[0]*$zoom, $diam[1]*$zoom, $col);
	imageellipse($im, $c_x*$zoom, $sizey - $c_y*$zoom, $diam[0]*$zoom, $diam[1]*$zoom, $col);

	imagefilledellipse($im, $c_x*$zoom, $sizey - $c_y*$zoom, 2, 2, $gc_col);
	$textArray = explode(' ', $feat_id);
	$y = $sizey-$c_y*$zoom+15;
	foreach ($textArray as $txt) {
		ImageTTFText($im, 8, 0, $c_x*$zoom-strlen($feat_id)+10, $y, $gc_col, $font_ttf, $txt);
		$y += 15;
	}
	//ImageTTFText($im, 8, 0, $c_x*$zoom-strlen($feat_id)+10, $sizey-$c_y*$zoom+15, $gc_col, $font_ttf, $feat_id);
}

function draw_skel_filam_pix($im, $tab_xpix, $tab_ypix, $c_x, $c_y, $intensity, $feat_id, $zoom) {
	$sizey = imagesy($im);
	$nb = count($tab_xpix);
	$col=ImageColorAllocate($im, 0, 200, 0);
	$cBlack=ImageColorAllocate($im,0,0,0);
	$curX = $tab_xpix[0]*$zoom;
	$curY = $sizey-$tab_ypix[0]*$zoom;
	for($i=1;$i<$nb;$i++){
		$newX=$tab_xpix[$i]*$zoom;
		$newY=$sizey-$tab_ypix[$i]*$zoom;
		ImageLine($im,$curX,$curY,$newX,$newY,$col);
		$curX=$newX;
		$curY=$newY;
	}
	imagefilledellipse($im, $c_x*$zoom, $sizey-$c_y*$zoom, 2, 2, $cBlack);
}

?>
