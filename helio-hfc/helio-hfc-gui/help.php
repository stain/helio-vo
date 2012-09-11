
<?php
require('functions.php');

include("header.php");

echo '<h5 class="winA">Database tables and fields description</h5>';
// Dynamic tables description
$tab_tables = array("OBSERVATIONS", "PP_OUTPUT", "FILAMENTS", "FILAMENTS_TRACKING", "PROMINENCES", "ACTIVEREGIONS", "CHGROUPS", "CORONALHOLES", "SUNSPOTS", "TYPE_III", /*"TYPE_II",*/ "RADIOSOURCES");
// retrieve comments on each table column
foreach ($tab_tables as $table_name) {
	$sql_query = "SHOW FULL COLUMNS FROM $table_name";
	$rs = execute_query($sql_query);
	print "<h4>Table $table_name:</h4>\n";
	$tab_comments['FIELD'] = $rs['FIELD'];
	$tab_comments['COMMENT'] = $rs['COMMENT'];
	print_table($tab_comments, 'FIELD');
}

// static tables description and content
$tab_tables = array("OBSERVATORY", "PP_INFO", "FRC_INFO");
foreach ($tab_tables as $table_name) {
	$sql_query = "select * FROM $table_name";
	$rs = execute_query($sql_query);
	print "<h4>Table $table_name:</h4>\n";
	print_table($rs, 'ID_'.$table_name);
}

// Views description
$tab_tables = array("VIEW_FIL_GUI", "VIEW_PRO_GUI", "VIEW_AR_GUI", "VIEW_CH_GUI", "VIEW_SP_GUI", "VIEW_T3_GUI", /*"VIEW_T2_GUI",*/ "VIEW_RS_GUI");
// retrieve comments on each table column
foreach ($tab_tables as $table_name) {
	$sql_query = "SHOW FULL COLUMNS FROM $table_name";
	$rs = execute_query($sql_query);
	print "<h4>Table $table_name:</h4>\n";
	$tab_comments['FIELD'] = $rs['FIELD'];
	$tab_comments['COMMENT'] = $rs['COMMENT'];
	print_table($tab_comments, 'FIELD');
}

function print_table($result, $first_col) {
	print "<TABLE border=1><TR>\n";
	foreach ($result as $key=>$val) print "<TH>$key</TH>";
	print "</TR>\n";
	for ($i=0; $i<count($result[$first_col]); $i++) {
		print "<TR>\n";
		foreach ($result as $key=>$val) print "<TD>".$val[$i]."</TD>";
		print "</TR>\n";
	}
	print "</TR></TABLE>\n";
}

?>
