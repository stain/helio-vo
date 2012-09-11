<?php
// script qui produit un fichier CSV ou VOTable a partir d'une requete sur le HFC
// la requete doit figurer dans le parametre de session ['query']
// make_votable.php?what=fil|ar|ch|sp|sql&fmt=csv|xml

session_start();

require("functions.php");

if (isset($_GET['what'])) $what = $_GET['what'];
else exit(0);
if (isset($_GET['fmt'])) {
	if (in_array($_GET['fmt'], array('csv', 'xml'))) $ofmt = $_GET['fmt'];
	else exit(0);
} else exit(0);

$sql_query = strtoupper($_SESSION['query'][$what]);
$sql_query_from = strstr($sql_query, 'FROM');

switch ($what) {
	case "fil":
		$filename = "hfc_fil";
		// in case there is no tracking info
		if (strpos($sql_query_from, 'FILAMENTS_TRACKING') === false) {
			$fields_to_print = array("ID_FIL", "DATE_FORMAT(DATE_OBS,'%Y-%m-%dT%T') as DATE_OBS", "FEAT_CARR_LAT_DEG", "FEAT_CARR_LONG_DEG", "SKE_LENGTH_DEG", "SKE_CURVATURE", "SKE_ORIENTATION", "FEAT_AREA_DEG2"," FEAT_MEAN2QSUN");
		} else {
			$fields_to_print = array("TRACK_ID", "ID_FIL", "DATE_FORMAT(DATE_OBS,'%Y-%m-%dT%T') as DATE_OBS");
			if (count($_SESSION['par_post']['filopt']))
				foreach($_SESSION['par_post']['filopt'] as $field) $fields_to_print[] = $field;
		}
		$sql_query = 'SELECT '.implode(',', $fields_to_print).' '.$sql_query_from;
		break;
	case "pro":
		$filename = "hfc_pro";
		$fields_to_print = array("ID_PROMINENCE", "DATE_FORMAT(DATE_OBS,'%Y-%m-%dT%T') as DATE_OBS"/* "PHENOM", "SC_ARC_X", "SC_ARC_Y", "SKE_LEN_DEG"*/);
		if (count($_SESSION['par_post']['proopt']))
			foreach($_SESSION['par_post']['proopt'] as $field) $fields_to_print[] = $field;
		$sql_query = 'SELECT '.implode(',', $fields_to_print).' '.$sql_query_from;
		break;

	case "ar":
		$filename = "hfc_ar";
		$fields_to_print = array("ID_AR", "DATE_FORMAT(DATE_OBS,'%Y-%m-%dT%T') as DATE_OBS"/*, "NOAA_NUMBER", "SC_ARC_X", "SC_ARC_Y", "FEAT_AREA"*/);
		if (count($_SESSION['par_post']['aropt']))
			foreach($_SESSION['par_post']['aropt'] as $field) $fields_to_print[] = $field;
		$sql_query = 'SELECT '.implode(',', $fields_to_print).' '.$sql_query_from;
		break;
	case "sp":
		$filename = "hfc_sp";
		$fields_to_print = array("ID_SUNSPOT", "DATE_FORMAT(DATE_OBS,'%Y-%m-%dT%T') as DATE_OBS"/*, "GC_ARC_X", "GC_ARC_Y", "DIAMETER", "FEAT_AREA", "FEAT_MEAN2QSUN", "N_UMBRA", "TOTALFLUX", "ABSTOTALFLUX"*/);
		if (count($_SESSION['par_post']['spopt']))
			foreach($_SESSION['par_post']['spopt'] as $field) $fields_to_print[] = $field;
		$sql_query = 'SELECT '.implode(',', $fields_to_print).' '.$sql_query_from;
		break;
	case "ch":
		$filename = "hfc_ch";
		$fields_to_print = array("ID_CORONALHOLES", "DATE_FORMAT(DATE_OBS,'%Y-%m-%dT%T') as DATE_OBS"/*, "CHC_ARC_X", "CHC_ARC_Y", "CHC_DEG_X", "CHC_DEG_Y", "CH_AREA_MM"*/);
		if (count($_SESSION['par_post']['chopt']))
			foreach($_SESSION['par_post']['chopt'] as $field) $fields_to_print[] = $field;
		$sql_query = 'SELECT '.implode(',', $fields_to_print).' '.$sql_query_from;
		break;
	case "t3":
		$filename = "hfc_t3";
		$fields_to_print = array("ID_TYPE_III", "TELESCOP", "DATE_FORMAT(DATE_OBS,'%Y-%m-%dT%T') as DATE_OBS"/*, "OVERLAP", "CC_MHZ_Y", "FEAT_MAX_INT", "FEAT_MEAN_INT"*/);
		if (count($_SESSION['par_post']['t3opt']))
			foreach($_SESSION['par_post']['t3opt'] as $field) $fields_to_print[] = $field;
		$sql_query = 'SELECT '.implode(',', $fields_to_print).' '.$sql_query_from;
		break;
	case "t2":
		$filename = "hfc_t2";
		$fields_to_print = array("ID_TYPE_II", "TELESCOP", "DATE_FORMAT(DATE_OBS,'%Y-%m-%dT%T') as DATE_OBS");
		if (count($_SESSION['par_post']['t2opt']))
			foreach($_SESSION['par_post']['t2opt'] as $field) $fields_to_print[] = $field;
		$sql_query = 'SELECT '.implode(',', $fields_to_print).' '.$sql_query_from;
		break;
	case "rs":
		$filename = "hfc_rs";
		$fields_to_print = array("ID_RS", "DATE_FORMAT(DATE_OBS,'%Y-%m-%dT%T') as DATE_OBS"/*, "OVERLAP", "CC_MHZ_Y", "FEAT_MAX_INT", "FEAT_MEAN_INT"*/);
		if (count($_SESSION['par_post']['rsopt']))
			foreach($_SESSION['par_post']['rsopt'] as $field) $fields_to_print[] = $field;
		$sql_query = 'SELECT '.implode(',', $fields_to_print).' '.$sql_query_from;
		break;
	case "sql":
		$filename = "hfc_freequery";
		break;
	default:
		exit(0);
}

// retreive comments from all the tables
$query_comment = "SHOW TABLES FROM ".$global['TBSP'];
$res = execute_query($query_comment);
$field = 'TABLES_IN_'.strtoupper($global['TBSP']);
$tab_comment = get_tables_comments($res[$field]);

//$sql_query = strtoupper($_SESSION['query'][$what]);
//print_r($sql_query);
if (strlen($sql_query)) {

header ("Expires: Mon, 26 Jul 1997 05:00:00 GMT");
header ("Last-Modified: " . gmdate("D, d M Y H:i:s") . " GMT");
header ("Cache-Control: no-cache, no-store, must-revalidate");  
header ("Pragma: No-Cache");
header("Content-type: application/octet-stream");
header("Content-Disposition: attachment; filename=\"$filename.$ofmt\"");

// use STILTS to make a SQL query with votable as output format
	//$cmd = "/var/www/cgi-bin/stilts/stilts ";
	$cmd = $global['STILTS_EXE'];
	//$cmd = $cmd."-classpath /home/renie/develop/workspace/lib/mysql-connector-java-5.0.8-bin.jar -Djdbc.drivers=com.mysql.jdbc.Driver sqlclient ";
	$cmd = $cmd."db='jdbc:mysql://".$global['MYSQL_HOST'].":3306/".$global['TBSP']."' user='".$global['MYSQL_USER']."' password='".$global['MYSQL_PASS']."' ";
	switch ($ofmt) {
		case 'xml':
			$cmd = $cmd."sql=\"".$sql_query."\" ofmt=votable-tabledata > /tmp/".$filename.".xml";
			exec($cmd);
			// Adds DESCRIPTION element to FIELD elements
			Add_Field_Description("/tmp/".$filename.".xml", $tab_comment, $what, $sql_query);
			// sends the file to browser
			$fp = fopen("/tmp/".$filename.".xml", 'r');
			break;
		case 'csv':
			$cmd = $cmd."sql=\"".$sql_query."\" ofmt=csv > /tmp/".$filename.".csv";
			exec($cmd);
			$fp = fopen("/tmp/".$filename.".csv", 'r');
			break;
	}

	fpassthru($fp);
}
else print_error_message('Bad query!');

function Add_Field_Description($file, $tab_comment, $feat, $query) {
	global $global;

	$xml = new DOMDocument();
	$xml->load($file);
	$xml->formatOutput = true;
	//$newline = $xml->createTextNode("\n");
	$fields = $xml->getElementsByTagName('RESOURCE');
	$node_resource = $fields->item(0);
	$node_desc = $xml->createElement("DESCRIPTION", 'HFC GUI');
	$node_info = $xml->createElement("INFO", $query);
	$node_info->setAttribute ('name', 'QUERY_STRING');
	$tables = $xml->getElementsByTagName('TABLE');
	$sql_query = "SHOW TABLES FROM ".$global['TBSP'];
	$res = execute_query($sql_query);
	$field = 'TABLES_IN_'.strtoupper($global['TBSP']);
	$tables_name = $res[$field];
	$tables_in_query = array();
	foreach($tables_name as $table) {
		$pattern = '/'.$table.'/i';
		if (preg_match($pattern, $_SESSION['query'][$feat])) $tables_in_query[] = $table;
	}
	$tables->item(0)->setAttribute ('name', implode(',', $tables_in_query));
	$node_resource->insertBefore($node_desc, $tables->item(0));
	$node_resource->insertBefore($node_info, $tables->item(0));
	$node_resource->insertBefore($xml->createTextNode("\n"), $node_info);
	$node_resource->insertBefore($xml->createTextNode("\n"), $tables->item(0));

	$fields = $xml->getElementsByTagName('FIELD');
	for ($i=0; $i<$fields->length; $i++) {
		$field = $fields->item($i);
		//echo $field->getAttribute('name')."<BR>\n";
		// retreive UCD and UTYPE from table ANNOTAIONS
		$query = "SELECT UCD, UTYPE FROM ANNOTATIONS WHERE FIELD_NAME='".$field->getAttribute('name')."'";
		$res = execute_query($query);
		if (strlen($res['UCD'][0])) $field->setAttribute('ucd', $res['UCD'][0]);
		if (strlen($res['UTYPE'][0])) $field->setAttribute('utype', $res['UTYPE'][0]);
		$node_desc = $xml->createElement("DESCRIPTION", $tab_comment[$field->getAttribute('name')]);
		$field->appendChild($node_desc);
	//	echo $fields->item($i)->nodeType."<BR>\n";
	}
	$xml->save($file);
}

?>
