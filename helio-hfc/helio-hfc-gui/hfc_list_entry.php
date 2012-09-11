<?php
// Draw a graph bar giving a global view of how the HFC is populated => nb of features for each year per month
require("functions.php");
require_once ('jpgraph/src/jpgraph.php');
require_once ('jpgraph/src/jpgraph_bar.php');
require_once ('jpgraph/src/jpgraph_mgraph.php');
$start = time();
$sql_query = "SELECT MIN(YEAR(DATE_OBS)) AS MINDATE, MAX(YEAR(DATE_OBS)) AS MAXDATE FROM OBSERVATIONS";
$res = execute_query($sql_query);
if (isset($_GET['st'])) $minyear = $_GET['st'];
else $minyear = $res['MINDATE'][0];
if (isset($_GET['et'])) $maxyear = $_GET['et'];
else $maxyear = $res['MAXDATE'][0];
if (isset($_GET['small'])) $small = true;
else $small = false;

$tab_month = array("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
$tab_feat = array("fil"=>"FILAMENTS", "pro"=>"PROMINENCES", "ar"=>"ACTIVEREGIONS", "ch"=>"CORONALHOLES", "sp"=>"SUNSPOTS", "t3"=>"TYPE_III", /*"Type II"=>"TYPE_II",*/"rs"=>"RADIOSOURCES");

for ($year=$minyear; $year<=$maxyear; $year++)
	foreach ($tab_month as $month) {
		$datay_bid[] = 0;
		$datax[] = $year;
}

	//$tab_minmax_dt = get_minmaxdate_for_instrument_code4();
foreach($tab_feat as $feat_type=>$feat_name) {
	//$t_instrum = get_distinct_instrume($feat_type);
	$t_instrum = get_distinct_instrume_from_tstat($feat_type);
	//$t_instrum = $tab_minmax_dt[$feat_type];
//print_r($t_instrum); echo '<br>';
	$ids_observatory = $t_instrum['ID_OBSERVATORY'];
	foreach($ids_observatory as $key=>$id_observatory) {
		$frc_info_id = $t_instrum['FRC_INFO_ID'][$key];
		$observat = $feat_name.'|'.$t_instrum['OBSERVAT'][$key];
		if (strlen($t_instrum['TELESCOP'][$key]) == 0) $observat = $observat.'|'.$t_instrum['WAVEMIN'][$key];
		$observat = $observat.'|'.$t_instrum['CODE'][$key];
		$bid = array();
		for ($year=$minyear; $year<=$maxyear; $year++) {
			//$tmp = getmonthstat_for_year($feat_type, $id_observatory, $year);
			$tmp = getmonthstat_for_year_from_tstat($feat_type, $id_observatory, $frc_info_id, $year);
			$bid = array_merge($bid, array_values($tmp));
		}
		$datay[$feat_type][$observat] = $bid;
		//echo $observat.' '.(time()-$start).'<BR>';
	}
}
/*
foreach($datay as $key=>$val) {
	echo "$key<BR>";
	foreach($val as $instrume=>$res) echo "$instrume: ".count($res)."<BR>";
	print_r($res);
	echo '<BR>';
}
exit(0);
*/
// Draw graph bar
if ($small) {
	$width=400;
	$height=55;
	$height_bid=60;
	$marge=30;
	$margex=140;
} else {
	$width=900;
	$height=55;
	$height_bid=60;
	$marge=30;
	$margex=140;
}

// Set the basic parameters of the graph
$graph = new Graph($width,$height_bid);
$graph->SetScale('textlin');
$graph->title->Set('HFC list entry population'/*.(time()-$start)*/);
$graph->yaxis->Hide();
$graph->yaxis->HideLabels();
$graph->img->SetMargin($margex,1,1,$marge);
$graph->SetFrame(true,'black',0);
$graph->SetBox(true,'black',0);
$graph->ygrid->SetWeight(0,0);
$graph->xaxis->HideLine();
$graph->xaxis->SetTickLabels($datax);
$graph->xaxis->SetLabelAngle(90);
$graph->xaxis->SetTextTickInterval(12,0);
$graph->xaxis->SetLabelMargin(2);

// Now create a bar pot
$bplot = new BarPlot($datay_bid);
$bplot->SetFillColor('white');
// ...and add it to the graPH
$graph->Add($bplot);

$mgraph = new MGraph();
$xpos1=3;$ypos1=0;
$xpos2=3;$ypos2=100;
$mgraph->Add($graph,3,0);
for($i=0; $i<count($feat_graph); $i++) $mgraph->Add($feat_graph[$i],3,$height_bid + $i*$height);
$cnt = 0;
foreach($datay as $feat_type=>$val) {
	foreach($val as $instrume=>$res) {
		$mgraph->Add(create_graph_barplot($instrume, $res, $width, $height),3,$height_bid + $cnt*$height);
		$cnt++;
	}
}
$mgraph->Stroke();

function create_graph_barplot($text, $datay, $width, $height) {
	$margex=140;

	$graph = new Graph($width,$height);
	$tab_txt = explode('|', $text);
	$txt = new Text($tab_txt[0]);
	$txt->SetPos(0, $height-40);
	$txt->SetColor('red');
	$txtb = new Text($tab_txt[1].' '.$tab_txt[2].' '.$tab_txt[3]);
	$txtb->SetPos(0, $height/2);
	$graph->AddText($txt);
	$graph->AddText($txtb);
	$graph->SetScale('textlin', 0, max($datay));
	$graph->SetBox();
	$graph->xaxis->HideLabels();
	$graph->yaxis->HideLabels();
	$graph->yaxis->SetPos('max');
	$graph->yaxis->SetTitle(max($datay), 'high');
	$graph->yaxis->SetTitleMargin(15);
	$graph->yaxis->SetTitleSide(SIDE_RIGHT);
	$graph->xgrid->Show();
	$graph->img->SetMargin($margex,15,1,1);
	$graph->SetFrame(true,'black',0);
	$graph->ygrid->SetWeight(0,0);
	$graph->ygrid->SetFill(false);
	$graph->xaxis->SetTextTickInterval(12,0);
	$bplot = new BarPlot($datay);
	$graph->Add($bplot);

	return $graph;
}

function getmonthstat_for_year($feat_type, $id_observatory, $year) {

	$tab_tables = array("fil"=>"FILAMENTS", "pro"=>"PROMINENCES", "ar"=>"ACTIVEREGIONS", "ch"=>"CORONALHOLES", "sp"=>"SUNSPOTS", "t3"=>"TYPE_III", /*"Type II"=>"TYPE_II",*/ "rs"=>"RADIOSOURCES");
	$tab_month = array("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");

	$tab_results = array();
	$table = $tab_tables[$feat_type];

	if (in_array($feat_type, array("ar", "sp"))) {
	foreach($tab_month as $id=>$month) {
	if ($id == 11) {
		$next_month = "01";
		$year2 = $year+1;
	}
	else {
		$next_month = $tab_month[$id+1];
		$year2 = $year;
	}
	switch($feat_type) {
		case 'ar':
			$sql_query = "SELECT count(ID_AR) as NBDATE FROM ACTIVEREGIONS, OBSERVATIONS WHERE DATE_OBS BETWEEN ";
			$sql_query = $sql_query."'$year-$month-01' AND '$year2-$next_month-01'";
			$sql_query = $sql_query."AND OBSERVATORY_ID=".$id_observatory;
			$sql_query = $sql_query." AND (OBSERVATIONS_ID=ID_OBSERVATIONS)";
			break;
		case 'sp';
			$sql_query = "SELECT count(ID_SUNSPOT) as NBDATE FROM SUNSPOTS, OBSERVATIONS WHERE DATE_OBS BETWEEN ";
			$sql_query = $sql_query."'$year-$month-01' AND '$year2-$next_month-01'";
			$sql_query = $sql_query."AND OBSERVATORY_ID=".$id_observatory;
			$sql_query = $sql_query." AND (OBSERVATIONS_ID_IC=ID_OBSERVATIONS)";
			break;
	}
	//echo $query."<br>\n";
	$rs = execute_query($sql_query);
	$tab_results[$month] = $rs['NBDATE'][0];
	}
	} else {
	switch($feat_type) {
		case 'fil':
		case 'pro':
			$qfrom = ", PP_OUTPUT ";
			$qwhere = "(PP_OUTPUT_ID=ID_PP_OUTPUT) AND (OBSERVATIONS_ID=ID_OBSERVATIONS)";
			break;
		case 'ch';
			$qfrom = " ";
			$qwhere = "(OBSERVATIONS_ID_EIT=ID_OBSERVATIONS)";
			break;
		default:
			$qfrom = " ";
			$qwhere = "(OBSERVATIONS_ID=ID_OBSERVATIONS)";
			break;
	}
	//$sql_query = "SELECT MONTH(DATE_OBS) as DATE_OBS FROM OBSERVATIONS, $table";
	$sql_query = "SELECT DATE_FORMAT(DATE_OBS, '%c') as DATE_OBS FROM OBSERVATIONS, $table";
	$sql_query = $sql_query.$qfrom;
	$qwherecommon = " WHERE OBSERVATORY_ID=\"".$id_observatory."\" AND YEAR(DATE_OBS) = '".$year."' ";
	$query = $sql_query.$qwherecommon." AND ".$qwhere;
	//echo $query."<br>\n";
	$rs = execute_query($query);

	if (count($rs)) {
		foreach($tab_month as $month) {
			$tab_results[$month] = count(array_keys($rs['DATE_OBS'], $month));
		}
	} else {
		foreach($tab_month as $month) $tab_results[$month] = 0;
	}
	}
	return $tab_results;
}

function getmonthstat_for_year_from_tstat($feat_type, $id_observatory, $frc_info_id, $year) {

	$tab_tables = array("fil"=>"FILAMENTS", "pro"=>"PROMINENCES", "ar"=>"ACTIVEREGIONS", "ch"=>"CORONALHOLES", "sp"=>"SUNSPOTS", "t3"=>"TYPE_III", /*"Type II"=>"TYPE_II",*/ "rs"=>"RADIOSOURCES");
	$tab_month = array("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");

	$tab_results = array();
	$table = $tab_tables[$feat_type];

	//foreach($tab_month as $month) {
		$sql_query = "SELECT NB_FEAT FROM DATE_STAT WHERE ";
		//$sql_query = $sql_query."date_format(TIME_RANGE, '%Y-%m')='".$year."-".$month."'";
		$sql_query = $sql_query."YEAR(TIME_RANGE)=".$year;
		$sql_query = $sql_query." AND OBSERVATORY_ID=".$id_observatory;
		$sql_query = $sql_query." AND FRC_INFO_ID=".$frc_info_id;
		$sql_query = $sql_query." AND FEAT_TYPE='".$table."'";
		$sql_query = $sql_query." ORDER BY TIME_RANGE ASC";
		$rs = execute_query($sql_query);
		//$tab_results[$month] = $rs['NB_FEAT'][0];
	//}
	return array_values($rs['NB_FEAT']);
	//return $tab_results;
}

/*
function getmonthstat_for_year2($feat_type, $id_observatory, $year) {

	$tab_tables = array("fil"=>"FILAMENTS", "pro"=>"PROMINENCES", "ar"=>"ACTIVEREGIONS", "ch"=>"CORONALHOLES", "sp"=>"SUNSPOTS", "t3"=>"TYPE_III",  "rs"=>"RADIOSOURCES");
	$tab_idname = array("fil"=>"ID_FIL", "pro"=>"ID_PROMINENCE", "ar"=>"ID_AR", "ch"=>"ID_CORONALHOLES", "sp"=>"ID_SUNSPOT", "t3"=>"ID_TYPE_III",  "rs"=>"ID_RS");
	$tab_month = array("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");

	$tab_results = array();

	$table = $tab_tables[$feat_type];
	foreach($tab_month as $id=>$month) {
	if ($id == 11) {
		$next_month = "01";
		$year2 = $year+1;
	}
	else {
		$next_month = $tab_month[$id+1];
		$year2 = $year;
	}
	switch($feat_type) {
		case 'fil':
			$sql_query = "SELECT count(".$tab_idname[$feat_type].") as NBDATE FROM FILAMENTS, OBSERVATIONS, PP_OUTPUT WHERE DATE_OBS BETWEEN ";
			//$sql_query = $sql_query."'$year-$month-01' AND DATE_ADD('$year-$month-01', INTERVAL 1 MONTH) ";
			$sql_query = $sql_query."'$year-$month-01' AND '$year2-$next_month-01'";
			$sql_query = $sql_query."AND OBSERVATORY_ID=".$id_observatory;
			$sql_query = $sql_query." AND (PP_OUTPUT_ID=ID_PP_OUTPUT) AND (OBSERVATIONS_ID=ID_OBSERVATIONS)";
			break;
		case 'pro':
			$sql_query = "SELECT count(".$tab_idname[$feat_type].") as NBDATE FROM PROMINENCES, OBSERVATIONS, PP_OUTPUT WHERE DATE_OBS BETWEEN ";
			$sql_query = $sql_query."'$year-$month-01' AND '$year2-$next_month-01'";
			$sql_query = $sql_query."AND OBSERVATORY_ID=".$id_observatory;
			$sql_query = $sql_query." AND (PP_OUTPUT_ID=ID_PP_OUTPUT) AND (OBSERVATIONS_ID=ID_OBSERVATIONS)";
			break;
		case 'ar':
			$sql_query = "SELECT count(".$tab_idname[$feat_type].") as NBDATE FROM ACTIVEREGIONS, OBSERVATIONS WHERE DATE_OBS BETWEEN ";
			$sql_query = $sql_query."'$year-$month-01' AND '$year2-$next_month-01'";
			$sql_query = $sql_query."AND OBSERVATORY_ID=".$id_observatory;
			$sql_query = $sql_query." AND (OBSERVATIONS_ID=ID_OBSERVATIONS)";
			break;
		case 'ch';
			$sql_query = "SELECT count(".$tab_idname[$feat_type].") as NBDATE FROM CORONALHOLES, OBSERVATIONS WHERE DATE_OBS BETWEEN ";
			$sql_query = $sql_query."'$year-$month-01' AND '$year2-$next_month-01'";
			$sql_query = $sql_query."AND OBSERVATORY_ID=".$id_observatory;
			$sql_query = $sql_query." AND (OBSERVATIONS_ID_EIT=ID_OBSERVATIONS)";
			break;
		case 'sp';
			$sql_query = "SELECT count(".$tab_idname[$feat_type].") as NBDATE FROM SUNSPOTS, OBSERVATIONS WHERE DATE_OBS BETWEEN ";
			$sql_query = $sql_query."'$year-$month-01' AND '$year2-$next_month-01'";
			$sql_query = $sql_query."AND OBSERVATORY_ID=".$id_observatory;
			$sql_query = $sql_query." AND (OBSERVATIONS_ID_IC=ID_OBSERVATIONS)";
			break;
		case 't3';
			$sql_query = "SELECT count(".$tab_idname[$feat_type].") as NBDATE FROM OBSERVATIONS, TYPE_III WHERE DATE_OBS BETWEEN ";
			$sql_query = $sql_query."'$year-$month-01' AND '$year2-$next_month-01'";
			$sql_query = $sql_query."AND OBSERVATORY_ID=".$id_observatory;
			$sql_query = $sql_query." AND (OBSERVATIONS_ID = ID_OBSERVATIONS)";
			break;
		case 'rs';
			$sql_query = "SELECT count(".$tab_idname[$feat_type].") as NBDATE FROM OBSERVATIONS, RADIOSOURCES WHERE DATE_OBS BETWEEN ";
			$sql_query = $sql_query."'$year-$month-01' AND '$year2-$next_month-01'";
			$sql_query = $sql_query."AND OBSERVATORY_ID=".$id_observatory;
			$sql_query = $sql_query." AND (OBSERVATIONS_ID = ID_OBSERVATIONS)";
			break;
	}
	//echo $query."<br>\n";
	$rs = execute_query($sql_query);
	$tab_results[$month] = $rs['NBDATE'][0];
	}

	return $tab_results;
}


function getmonthstat_for_year3($feat_type, $id_observatory, $year) {

	$tab_tables = array("fil"=>"FILAMENTS", "pro"=>"PROMINENCES", "ar"=>"ACTIVEREGIONS", "ch"=>"CORONALHOLES", "sp"=>"SUNSPOTS", "t3"=>"TYPE_III", "rs"=>"RADIOSOURCES");
	$tab_month = array("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");

	$tab_results = array();

	$table = $tab_tables[$feat_type];
	switch($feat_type) {
		case 'fil':
		case 'pro':
			$qfrom = ", PP_OUTPUT ";
			$qwhere = "(PP_OUTPUT_ID=ID_PP_OUTPUT) AND (OBSERVATIONS_ID=ID_OBSERVATIONS)";
			break;
		case 'ch';
			$qfrom = " ";
			$qwhere = "(OBSERVATIONS_ID_EIT=ID_OBSERVATIONS)";
			break;
		case 'sp';
			$qfrom = " ";
			$qwhere = "(OBSERVATIONS_ID_IC=ID_OBSERVATIONS)";
			break;
		default:
			$qfrom = " ";
			$qwhere = "(OBSERVATIONS_ID=ID_OBSERVATIONS)";
			break;
	}
	//$sql_query = "SELECT MONTH(DATE_OBS) as DATE_OBS FROM OBSERVATIONS, $table";
	$sql_query = "SELECT DATE_FORMAT(DATE_OBS, '%c') as DATE_OBS FROM OBSERVATIONS, $table";
	$sql_query = $sql_query.$qfrom;
	$qwherecommon = " WHERE OBSERVATORY_ID=\"".$id_observatory."\" AND YEAR(DATE_OBS) = '".$year."' ";
	$query = $sql_query.$qwherecommon." AND ".$qwhere;
	//echo $query."<br>\n";
	$rs = execute_query($query);

	if (count($rs)) {
		foreach($tab_month as $month) {
			$tab_results[$month] = count(array_keys($rs['DATE_OBS'], $month));
		}
	} else {
		foreach($tab_month as $month) $tab_results[$month] = 0;
	}

	return $tab_results;
}
*/