<?php
// Draw a graph bar giving the number of features for each month of a given year

require("functions.php");
require_once ('jpgraph/src/jpgraph.php');
require_once ('jpgraph/src/jpgraph_bar.php');

if (isset($_GET['year'])) $year = $_GET['year'];
else exit(0);
if (!is_numeric($year)) exit(0);

$datax = array("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
	$tab_tables = array("fil"=>"FILAMENTS", "pro"=>"PROMINENCES", "ar"=>"ACTIVEREGIONS", "ch"=>"CORONALHOLES", "sp"=>"SUNSPOTS", "t3"=>"TYPE_III", "rs"=>"RADIOSOURCES");
$tab_results = array();

foreach($tab_tables as $feat_type=>$table) {
	foreach($datax as $month) {
		$sql_query = "SELECT NB_FEAT FROM DATE_STAT WHERE ";
		$sql_query = $sql_query." TIME_RANGE >= '$year-$month-01' AND TIME_RANGE<DATE_ADD('$year-$month-01', INTERVAL 1 MONTH) ";
		$sql_query = $sql_query." AND FEAT_TYPE='".$table."'";
		$sql_query = $sql_query." ORDER BY TIME_RANGE ASC";
		$rs = execute_query($sql_query);
		$tab_results[$feat_type][] = array_sum($rs['NB_FEAT']);
	}
}
/*
foreach($datax as $month) {
	// retrieve number of filaments for the current year and month
	$sql_query = "SELECT count(DATE_OBS) as NBDATE FROM VIEW_FIL_GUI WHERE DATE_OBS BETWEEN ";
	$sql_query = $sql_query."'$year-$month-01' AND DATE_ADD('$year-$month-01', INTERVAL 1 MONTH) ";
	$sql_query = $sql_query."ORDER BY DATE_OBS ASC";
	$res = execute_query($sql_query);
	$datay_fil[] = $res['NBDATE'][0];

	// retrieve number of prominences for the current year and month
	$sql_query = "SELECT count(DATE_OBS) as NBDATE FROM VIEW_PRO_GUI WHERE DATE_OBS BETWEEN ";
	$sql_query = $sql_query."'$year-$month-01' AND DATE_ADD('$year-$month-01', INTERVAL 1 MONTH) ";
	$sql_query = $sql_query."ORDER BY DATE_OBS ASC";
	$res = execute_query($sql_query);
	$datay_pro[] = $res['NBDATE'][0];

	// retrieve number of active regions for the current year and month
	$sql_query = "SELECT count(FEAT_DATE) as NBDATE FROM ACTIVEREGIONS WHERE FEAT_DATE BETWEEN ";
	$sql_query = $sql_query."'$year-$month-01' AND DATE_ADD('$year-$month-01', INTERVAL 1 MONTH) ";
	$sql_query = $sql_query."ORDER BY FEAT_DATE ASC";
	$res = execute_query($sql_query);
	$datay_ar[] = $res['NBDATE'][0];

	// retrieve number of coronal holes for the current year and month
	$sql_query = "SELECT count(FEAT_DATE) as NBDATE FROM CORONALHOLES WHERE FEAT_DATE BETWEEN ";
	$sql_query = $sql_query."'$year-$month-01' AND DATE_ADD('$year-$month-01', INTERVAL 1 MONTH) ";
	$sql_query = $sql_query."ORDER BY FEAT_DATE ASC";
	$res = execute_query($sql_query);
	$datay_ch[] = $res['NBDATE'][0];

	// retrieve number of sun spots for the current year and month
	$sql_query = "SELECT count(DATE_OBS) as NBDATE FROM VIEW_SP_GUI WHERE DATE_OBS BETWEEN ";
	$sql_query = $sql_query."'$year-$month-01' AND DATE_ADD('$year-$month-01', INTERVAL 1 MONTH) ";
	$sql_query = $sql_query."ORDER BY DATE_OBS ASC";
	$res = execute_query($sql_query);
	$datay_sp[] = $res['NBDATE'][0];

	// retrieve number of Type III for the current year and month
	$sql_query = "SELECT count(DATE_OBS) as NBDATE FROM OBSERVATIONS o, TYPE_III t WHERE DATE_OBS BETWEEN ";
	$sql_query = $sql_query."'$year-$month-01' AND DATE_ADD('$year-$month-01', INTERVAL 1 MONTH) ";
	$sql_query = $sql_query."AND (t.OBSERVATIONS_ID = o.ID_OBSERVATIONS)";
	$sql_query = $sql_query."ORDER BY DATE_OBS ASC";
	$res = execute_query($sql_query);
	$datay_t3[] = $res['NBDATE'][0];

	// retrieve number of radio sources for the current year and month
	$sql_query = "SELECT count(DATE_OBS) as NBDATE FROM VIEW_RS_GUI WHERE DATE_OBS BETWEEN ";
	$sql_query = $sql_query."'$year-$month-01' AND DATE_ADD('$year-$month-01', INTERVAL 1 MONTH) ";
	$sql_query = $sql_query."ORDER BY DATE_OBS ASC";
	$res = execute_query($sql_query);
	$datay_rs[] = $res['NBDATE'][0];
}
*/
// Draw graph bar
$width=400;
$height=360;

// Set the basic parameters of the graph
$graph = new Graph($width,$height);
$graph->SetScale('textlin');

// Nice shadow
$graph->SetShadow();
$graph->img->SetMargin(50,30,20,60);
// Setup title
$graph->title->Set($year);
$graph->xaxis->title->Set('Month');
$graph->yaxis->SetTitleMargin(40);
$graph->yaxis->title->Set('Nb of features');

// Setup X-axis
$graph->xaxis->SetTickLabels($datax);

$barplots = array();
// Now create a bar pot
$bplot_fil = new BarPlot($tab_results["fil"]);
$bplot_fil->SetLegend('Filaments');
$barplots[] = $bplot_fil;
$bplot_pro = new BarPlot($tab_results["pro"]);
$bplot_pro->SetLegend('Prominences');
$barplots[] = $bplot_pro;
$bplot_ar = new BarPlot($tab_results["ar"]);
$bplot_ar->SetLegend('AR');
$barplots[] = $bplot_ar;
$bplot_ch = new BarPlot($tab_results["ch"]);
$bplot_ch->SetLegend('CH');
$barplots[] = $bplot_ch;
$bplot_sp = new BarPlot($tab_results["sp"]);
$bplot_sp->SetLegend('Sunspots');
$barplots[] = $bplot_sp;
$bplot_t3 = new BarPlot($tab_results["t3"]);
$bplot_t3->SetLegend('Type III');
$barplots[] = $bplot_t3;
//$bplot_t2 = new BarPlot($datay_t2);
//$bplot_t2->SetLegend('Type II');
//$barplots[] = $bplot_t2;
$bplot_rs = new BarPlot($tab_results["rs"]);
$bplot_rs->SetLegend('Radio sources');
$barplots[] = $bplot_rs;
/*
// Now create a bar pot
$bplot_fil = new BarPlot($datay_fil);
$bplot_fil->SetLegend('Filaments');
$barplots[] = $bplot_fil;
$bplot_pro = new BarPlot($datay_pro);
$bplot_pro->SetLegend('Prominences');
$barplots[] = $bplot_pro;
$bplot_ar = new BarPlot($datay_ar);
$bplot_ar->SetLegend('AR');
$barplots[] = $bplot_ar;
$bplot_ch = new BarPlot($datay_ch);
$bplot_ch->SetLegend('CH');
$barplots[] = $bplot_ch;
$bplot_sp = new BarPlot($datay_sp);
$bplot_sp->SetLegend('Sunspots');
$barplots[] = $bplot_sp;
$bplot_t3 = new BarPlot($datay_t3);
$bplot_t3->SetLegend('Type III');
$barplots[] = $bplot_t3;
//$bplot_t2 = new BarPlot($datay_t2);
//$bplot_t2->SetLegend('Type II');
//$barplots[] = $bplot_t2;
$bplot_rs = new BarPlot($datay_rs);
$bplot_rs->SetLegend('Radio sources');
$barplots[] = $bplot_rs;
*/
// Create the grouped bar plot
//$gbplot = new GroupBarPlot(array($bplot_fil,$bplot_pro,$bplot_ar,$bplot_ch,$bplot_sp,$bplot_t3));
$gbplot = new GroupBarPlot($barplots);
 
// ...and add it to the graPH
$graph->Add($gbplot);
$bplot_fil->SetFillColor('orange'); $bplot_fil->SetColor('orange');
$bplot_pro->SetFillColor('lightred'); $bplot_pro->SetColor('lightred');
$bplot_ar->SetFillColor('lightblue'); $bplot_ar->SetColor('lightblue');
$bplot_ch->SetFillColor('lightgreen'); $bplot_ch->SetColor('lightgreen');
$bplot_sp->SetFillColor('lightgray'); $bplot_sp->SetColor('lightgray');
$bplot_t3->SetFillColor('lightyellow'); $bplot_t3->SetColor('lightyellow');
//$bplot_t2->SetFillColor('red');
$bplot_rs->SetFillColor('blue'); $bplot_rs->SetColor('blue');
$graph->title->SetFont(FF_FONT1,FS_BOLD);
$graph->yaxis->title->SetFont(FF_FONT1,FS_BOLD);
$graph->xaxis->title->SetFont(FF_FONT1,FS_BOLD);


// .. and stroke the graph
$graph->Stroke();
