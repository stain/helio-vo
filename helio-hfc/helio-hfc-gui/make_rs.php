<?php
// Make raster scan image of a feature (prominence or sunspot)
// paramaters: what=pro|sp, id=feat_id

require("functions.php");

// checks parameters, feature type and id are both required
if (isset($_GET['what'])) $feat_type = $_GET['what'];
else exit(0);
if (isset($_GET['id'])) $feat_id = $_GET['id'];
else exit(0);

$tables_name = array('pro'=>"VIEW_PRO_GUI", 'sp'=>"VIEW_SP_GUI");
$field_id_name = array('pro'=>"ID_PROMINENCE", 'sp'=>"ID_SUNSPOT");

// Query data for the specified feat_id
$query = "SELECT * FROM ".$tables_name[$feat_type]." WHERE ".$field_id_name[$feat_type]."=".$feat_id;
//echo $query."<br>\n";
$rs = execute_query($query);
if (count($rs[$field_id_name[$feat_type]]) == 0) {
	echo "ID not found";
	exit(0);
}
if (strpos($rs['RS'][0], ':') === false) {
	echo "Raster Scan not valid for ID ".$feat_id;
	exit(0);
}
if ((count($rs['RS'][0]) == 0) || ($rs['RS'][0] == "NULL")) {
		echo "Raster Scan not found or not valid for ID ".$feat_id;
}
else {
	$line_sep = ':';
	$lev_sep = '.';
	$rasterscan = array();
	$rs_lines = explode($line_sep, $rs["RS"][0]);
	$nb_lines = count($rs_lines)-1;
	$nb_col = 0;
	for($i=0; $i<$nb_lines; $i++) {
		$bid = array();
		$comp = explode($lev_sep, $rs_lines[$i]);
		foreach($comp as $sect_lev) {
			$level = $sect_lev[0];
			$level_length = substr($sect_lev, 1);
			$bid[$level] = $level_length;
			if ($level_length > $nb_col) $nb_col = $level_length;
		}
		$rasterscan[] = $comp;
	}
	/*echo "Raster scan size: ".$nb_lines." ".$nb_col."<br>\n";
	foreach ($rasterscan as $row) {
		print_r($row);
		echo "<br>\n";
	}*/

	$filename = "rs".'_'.$feat_type.'_'.$feat_id.".png";

	header ("Expires: Mon, 26 Jul 1997 05:00:00 GMT");
	header ("Last-Modified: " . gmdate("D, d M Y H:i:s") . " GMT");
	header ("Cache-Control: no-cache, no-store, must-revalidate");  
	header ("Pragma: No-Cache");
	header("Content-Disposition: attachment; filename=$filename");
	header("Content-type: image/png");

	$im = imagecreatetruecolor($nb_col, $nb_lines);
	$txt_color = ImageColorAllocate($im, 255, 255, 255);
	switch($feat_type) {
		case 'pro':
			$nb_level = $rs["N_LEVEL"][0];
			break;
		default:
			$nb_level = 3;
			break;
	}
	$nb_rows = count($rasterscan);
	foreach($rasterscan as $row_num=>$row) {
		$x = 0;
		foreach($row as $sect_lev) {
			$lev = $sect_lev[0];
			$level_length = substr($sect_lev, 1);
			$intensity = 240*(1-$lev/$nb_level);
			$col = ImageColorAllocate($im, $intensity, $intensity, $intensity);
			imageline($im, $x, $nb_rows-1-$row_num, $x+$level_length, $nb_rows-1-$row_num, $col);
			$x = $x+$level_length;
		}
	}
	imagePNG($im);
	imagedestroy($im);
}
?>