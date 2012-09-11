<?php
session_start();
// Creation d'une image png d'un ensemble de types III a une date donnee
// appel: plot_t3.php?date=datestring&&usesess=1|2&zoom=0.5&obs
// usesess = 1 use results contents in $_SESSION['t3'])
// usesess = 2 use results contents in $_SESSION['rs_feat_to_print'])

require("functions.php");

if (isset($_GET['date'])) $date = $_GET['date'];
else exit(0);
if (isset($_GET['obs'])) $with_obs = true;
else $with_obs = false;
if (isset($_GET['zoom'])) $zoom = $_GET['zoom'];
else $zoom = 1;
if (($zoom <= 0) || ($zoom > 2)) $zoom = 1;
if (isset($_GET['usesess'])) $use_sess = $_GET['usesess'];

$timestmp = strtotime($date);
$date_array = getdate($timestmp);
$start_day=$date_array['mday']; $start_month=$date_array['mon']; $start_year=$date_array['year'];

$filename = "t3_quicklook_".$date.'.png';

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

if (isset($use_sess)) {
	switch ($use_sess) {
		case 1: 
			$tmp = $_SESSION['rs_t3'];
			break;
		case 2: 
			$tmp = $_SESSION['rs_feat_to_print'];
			break;
		default: exit(0);
	}
	foreach ($tmp['ID_TYPE_III'] as $key=>$id_ar)
		if (!strcmp($tmp['DATE_OBS'][$key], $date)) 
			foreach ($tmp as $field=>$tab) $rs[$field][] = $tab[$key];
} else {
	//$sql_query = "SELECT * FROM VIEW_T3_GUI WHERE DATE_OBS='$date' ";
	$sql_query = "SELECT t.*, o.*, i.* FROM TYPE_III t, OBSERVATIONS o, OBSERVATORY i WHERE DATE_OBS='$date' ";
	$sql_query = $sql_query."AND (t.OBSERVATIONS_ID=o.ID_OBSERVATIONS) AND (o.OBSERVATORY_ID=i.ID_OBSERVATORY) ";
	$rs = execute_query($sql_query);
	if (count($rs['ID_TYPE_III']) == 0) {
		//$sql_query = "SELECT * FROM VIEW_T3_GUI ";
		$sql_query = "SELECT t.*, o.* FROM TYPE_III t, OBSERVATIONS o, OBSERVATORY i ";
		$sql_query = $sql_query."WHERE DATE(DATE_OBS)='$date' ";
		$sql_query = $sql_query."AND (t.OBSERVATIONS_ID=o.ID_OBSERVATIONS) AND (o.OBSERVATORY_ID=i.ID_OBSERVATORY) ";
		$rs = execute_query($sql_query);
	}
}

if (count($rs['ID_TYPE_III'])) {
require_once ('jpgraph/src/jpgraph.php');
require_once( "jpgraph/src/jpgraph_line.php" );
require_once( "jpgraph/src/jpgraph_date.php" );
require_once ('jpgraph/src/jpgraph_mgraph.php');

	// etablit la liste des instruments par frequences decroissantes
	foreach($rs['OBSERVAT'] as $key=>$val) {
		$data[] = array('observatory'=>($val.'/'.$rs['INSTRUME'][$key].'/'.$rs['TELESCOP'][$key]), 'wavemax'=>$rs[$key]);
		$telescops[] = $rs['OBSERVAT'][$key].'/'.$rs['INSTRUME'][$key].'/'.$rs['TELESCOP'][$key];
	}
	/*$telescops = $rs['OBSERVAT'];*/ $wavemax = $rs['WAVEMAX'];
	array_multisort($wavemax, SORT_DESC, $telescops, SORT_ASC, $data);
	$instrume = array_values(array_unique($telescops));

	$tmp = explode(" ", $date);
	$timestamp_date = strtotime($tmp[0]);
	$mgraph = new MGraph();
	$mgraph->title->Set("HELIO features catalog/Paris Observatory    ".$tmp[0]);
	$mgraph->SetFrame(true);
	//$mgraph->SetBackgroundImagePos(1, 1);
	$graph = array();
	//$numgraph = 0;
	for ($i=0; $i<count($instrume); $i++) {
		// retourne tous les rangs pour l'instrument courant
		$keys = array();
		foreach($rs['OBSERVAT'] as $key=>$val) {
			if (!strcmp($instrume[$i], $val.'/'.$rs['INSTRUME'][$key].'/'.$rs['TELESCOP'][$key])) $keys[] = $key;
		}

		if ($with_obs) {
			$tmp = array_intersect_key($rs['QCLK_URL'], array_flip($keys));
			$qclk_url = array_values(array_unique($tmp));
			$tmp = array_intersect_key($rs['QCLK_FNAME'], array_flip($keys));
			$qclk_fname = array_values(array_unique($tmp));
			$dest_png = $qclk_url[0].'/'.$qclk_fname[0];
		}

		$tmp = array_intersect_key($rs['WAVEMAX'], array_flip($keys));
		$max_freq = max(array_unique($tmp));
		$tmp = array_intersect_key($rs['WAVEMIN'], array_flip($keys));
		$min_freq = max(array_unique($tmp));
		$tmp = array_intersect_key($rs['DATE_OBS'], array_flip($keys));
		$start_date = strtotime(min(array_unique($tmp)));
		$tmp = array_intersect_key($rs['DATE_END'], array_flip($keys));
		$end_date = strtotime(max(array_unique($tmp)));
		$tmp = array_intersect_key($rs['FEAT_MEAN_INT'], array_flip($keys));
		$max_intensity = max(array_unique($tmp));
		$tmp = array_intersect_key($rs['FEAT_MEAN_INT'], array_flip($keys));
		$min_intensity = min(array_unique($tmp));
		$sizeX = $rs['NAXIS1'][0]*$zoom;
		$sizeY = $rs['NAXIS2'][0]*$zoom;
		$graph[$i] = new Graph($sizeX+100, $sizeY+100);

		//$graph[$i]->SetFrame(false);
		$graph[$i]->title->Set($instrume[$i].' '.$dest_png/*.' '.$rs['FEAT_FILENAME'][$keys[0]]$min_freq.' '.$max_freq.' '.$start_date.' '.$end_date*/);
		//$graph[$i]->title->SetPos('SIDE_UP');
		$graph[$i]->SetScale('datlin', $min_freq, $max_freq, $start_date, $end_date);
		$graph[$i]->img->SetMargin(50,10,70,5);
		$graph[$i]->xaxis->scale->SetDateFormat('H:i');
		$graph[$i]->xaxis->SetPos('max');
		$graph[$i]->xaxis->SetLabelAngle(90);
		$graph[$i]->xaxis->SetLabelSide('SIDE_TOP');
		$graph[$i]->xaxis->SetTitleMargin(35);
		$graph[$i]->xaxis->SetTitle("Hours");
		$graph[$i]->xaxis->SetTitleSide('SIDE_TOP');
		$graph[$i]->yaxis->SetTitleMargin(40);
		$graph[$i]->yaxis->SetTitle("Frequency (Mhz)");
		$graph[$i]->ygrid->SetFill(false);
		$graph[$i]->SetBackgroundImage("feat_int.png", BGIMG_COPY);

		$lineplot = array();
		$numplot = 0;
		foreach($keys as $key) {
			$row = array();
			foreach($rs as $field=>$val) $row[$field] = $val[$key];
			if (!$row['MULTIPLE']) {
				$start_pix = array($row['SKE_CC_X_PIX'], $row['SKE_CC_Y_PIX']);
				$pix_xy = make_tab_pix_coord($start_pix, $row['SKE_CC']);
				// transformation pix => valeurs physiques (heures, frequence)
				$tab_hours = array(); $tab_freq = array();
				foreach($pix_xy[0] as $pixX) {
					$tab_hours[] = $pixX*$row['CDELT1']*60*60 + $timestamp_date;
				}
				foreach($pix_xy[1] as $pixY) {
					$tab_freq[] = $pixY*$row['CDELT2'] + $row['WAVEMIN'];
				}
				$intensity = (200/($max_intensity-$min_intensity))*($row['FEAT_MEAN_INT']-$min_intensity)+55;
				$lineplot[$i][$numplot] = new LinePlot($tab_freq, $tab_hours);
				$graph[$i]->Add($lineplot[$i][$numplot]);
				//$lineplot[$i][$numplot]->SetWeight(10);
				//$lineplot[$i][$numplot]->SetStyle('dotted');
				//$lineplot[$i][$numplot]->SetColor(array(255, 200-66*$intensity, 200-66*$intensity));
				$lineplot[$i][$numplot]->SetColor(array(255, 255-$intensity, 255-$intensity));
				//$lineplot[$i][$numplot]->SetFillColor(array(255, 200-66*$intensity, 200-66*$intensity), false);
				$numplot++;
			}
		}
		if ($with_obs && file_exists($dest_png)) $graph[$i]->SetBackgroundImage($dest_png, BGIMG_FILLPLOT);

		$mgraph->Add($graph[$i], 5, $i*($sizeY+75) + 15);

	}
	$mgraph->Stroke();
}

?>
