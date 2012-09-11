<?php
session_start();
// Make a visual page of HFC database content
require("functions.php");

include("header.php");
include('but_menu.php');
?>
<script>
	$(function() {
		//$('#but_back_query').button();
		$("input:submit").button();
		});
</script>
<style type="text/css">
	td.default { border:1px solid #dbd7d4; width:20%; padding: 3px; text-align: center;}
</style>
<?php
echo '</head>';
echo '<h5 class="winA">Free SQL search</h5>';
//echo '<div style="text-align: right;"><span id="but_back_query"><a href="index.php">Back to query form</a></div>';
?>
<div class="ui-widget-content ui-corner-all">
	<form action="hfc_sql_query.php" method="POST">
	<textarea name="sql" cols="80" rows="5">
<?php
		if (isset($_POST['sql'])) echo $_POST['sql'];
		else echo 'select observat,instrume,telescop,units,wavename from observatory';
?>
	</textarea>
	<input type="submit" value="Submit">
	</form>
</div>
<?php
if (isset($_POST['sql'])) {
// Analyse de la requete SQL
// validation of the user query
// must begins with select or show
// doesn t content insert update or union
// contents the name of the table of the schema
$check_query = true;
$pattern = '/^select|show/i';
if (preg_match($pattern, $_POST['sql']) == 0) {
	echo 'Bad query'."<BR>";
	$check_query = false;
}
$pattern = '/insert|update|union/i';
if (preg_match($pattern, $_POST['sql'])) {
	echo 'Bad query'."<BR>";
	$check_query = false;
}
$sql_query = "SHOW TABLES FROM ".$global['TBSP'];
$res = execute_query($sql_query);
$field = 'TABLES_IN_'.strtoupper($global['TBSP']);
$tables_name = $res[$field];
$pattern = '/'.implode('|', $tables_name).'/i';
if (preg_match($pattern, $_POST['sql']) == 0) {
	echo 'Bad query'."<BR>";
	$check_query = false;
}
$tables_in_query = array();
foreach($tables_name as $table) {
	$pattern = '/'.$table.'/i';
	if (preg_match($pattern, $_POST['sql'])) $tables_in_query[] = $table;
}
if (count($tables_in_query) == 0) {
	echo 'Bad query'."<BR>";
	$check_query = false;
}

if ($check_query) {
	// select query are limited to 2000 rows
	$pattern = '/^select/i';
	$sql_query = rtrim(strtoupper($_POST['sql']));
	if (preg_match($pattern, $_POST['sql'])) $sql_query = $sql_query." LIMIT 2000";
	$res = execute_query($sql_query);
	// store session parameters used by make_csv(votable).php
	$_SESSION['query']['sql'] = $sql_query;

?>
<p></p>
<div class="ui-widget-content ui-corner-all">
Download as&nbsp;<a href="hfc_to_csvxml.php?what=sql&fmt=xml">VOTable</a>&nbsp;or&nbsp;<a href="hfc_to_csvxml.php?what=sql&fmt=csv">CSV</a><br>
<?php
echo '<b>'.$_POST['sql'].'</b>';

echo '<TABLE>'."\n";
echo '<TR>'."\n";
$fields = array();
foreach ($res as $field=>$tab) {
	echo '<TH>'.$field.'</TH>';
	$fields[] = $field;
}
$nb_rows = count($res[$fields[0]]);
for ($i=0; $i<$nb_rows; $i++) {
	echo '<TR>'."\n";
	foreach ($res as $field=>$tab) {
		echo '<TD class="default">'."\n";
		echo $tab[$i];
		echo '</TD>'."\n";
	}
	echo '</TR>'."\n";
}
echo '</TABLE>'."\n";

?>
</div>
<?php
}
}
include("footer.php");
?>