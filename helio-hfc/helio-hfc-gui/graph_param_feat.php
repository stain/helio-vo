<?php
// Creation d'une image avec le graph d'evolution d'un paramatre physique d'une struture solaire
// graph_param_feat.php?id=value&feat=fil|ar|ch|sp|t3&param=field
// id = numero de tracking du feature
// feat = type de feature
// param = nom du champs de la table pour lequel il faut tracer l'évolution

if (isset($_GET['id'])) $track_feat_id = $_GET['id'];
else exit(0);
if (isset($_GET['feat'])) $feature_type = $_GET['feat'];
else exit(0);
if (isset($_GET['param'])) $field = $_GET['param'];
else exit(0);

require("functions.php");

switch($feature_type) {
	case 'fil':
		$query = "SELECT * FROM VIEW_FIL_GUI WHERE TRACK_ID=".$track_feat_id." ORDER BY TRACK_ID ASC";
		//$query = "SELECT v_fil.*, t_trckfil.* FROM VIEW_FIL_GUI v_fil, FILAMENTS_TRACKING t_trckfil WHERE TRACK_ID=".$track_feat_id;
		//$query = $query." AND (v_fil.ID_FIL =  t_trckfil.FIL_ID) ORDER BY TRACK_ID ASC";;
		$query_comments = "SHOW FULL COLUMNS FROM VIEW_FIL_GUI";
		$id_feat_name = 'ID_FIL';
		break;
	case 'rs';
		$query = "SELECT * FROM VIEW_RS_GUI WHERE TRACK_ID=".$track_feat_id." ORDER BY TRACK_ID ASC";
		$query_comments = "SHOW FULL COLUMNS FROM VIEW_RS_GUI";
		$id_feat_name = 'ID_RS';
		break;
	default : exit(0);
}

$rs = execute_query($query);
$rs_com = execute_query($query_comments);
$tab_comment = array();
for ($i=0; $i<count($rs_com['FIELD']); $i++) {
	if (strlen($rs_com['COMMENT'][$i])) $tab_comment[strtoupper($rs_com['FIELD'][$i])] = $rs_com['COMMENT'][$i];
	else $tab_comment[strtoupper($rs_com['FIELD'][$i])] = $rs_com['FIELD'][$i];
}
$tab_comment['TRACK_ID'] = "Index of the feature during a rotation";

if (count($rs['DATE_OBS'])) {
	$tab_date = array_values(array_unique($rs['DATE_OBS']));
	$values_to_plot = array();
	foreach($tab_date as $date) {
		$total = 0;
		foreach($rs[$id_feat_name] as $key=>$id_fil)
			if (!strcmp($rs['DATE_OBS'][$key], $date)) $total = $total + $rs[$field][$key];
		$values_to_plot[] = $total;
	}

	//print_r($values_to_plot);

require_once ('jpgraph/src/jpgraph.php');
require_once ('jpgraph/src/jpgraph_bar.php');
require_once ('jpgraph/src/jpgraph_line.php');
require_once ('jpgraph/src/jpgraph_polar.php');

$width = 300; $height = 200;
//if (strcmp($field, "ORIENTATION")) {
$xLabels = array();
foreach($tab_date as $date) {
	$tmp = explode(" ", $date);
	$xLabels[] = $tmp[0];
}

$graph = new Graph($width,$height);
$graph->SetScale('textint', min($values_to_plot), max($values_to_plot));
$graph->title->Set($tab_comment[$field]);
$graph->xaxis->SetPos("min");
$graph->xaxis->SetTickLabels($xLabels);
$graph->xaxis->SetLabelAngle(90);
$graph->img->SetMargin(40,50,30,70);
$graph->xaxis->SetLabelMargin(2);
//$plot = new BarPlot($values_to_plot);
$plot=new LinePlot($values_to_plot);
$graph->Add($plot);
/*} else {
// $val = array(angle1, radius1, angle2, radius2, ...)
$val = array();
foreach($values_to_plot as $value) {
	$val[] = $value;
	$val[] = 10;
}
$graph = new PolarGraph($width,$height);
$graph->SetScale('log',100);
$graph->SetType(POLAR_360);
$polarplot = new PolarPlot($val);
$polarplot->mark->SetType(MARK_SQUARE);
$graph->Add($polarplot);
}*/
$graph->Stroke();
}
?>
