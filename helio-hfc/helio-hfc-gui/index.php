<?php
session_start();
//print_r($_SESSION);

require_once("functions.php");
include("header.php");

// retreive comment from database for description of the fields
$table_fil_comments = get_tables_comments(array("VIEW_FIL_HQI"));
//$table_fil_comments['PHENOM'] = "Phenomen";
$table_pro_comments = get_tables_comments(array("VIEW_PRO_HQI"));
$table_ar_comments = get_tables_comments(array("VIEW_AR_HQI"));
$table_ch_comments = get_tables_comments(array("VIEW_CH_HQI"));
$table_sp_comments = get_tables_comments(array("VIEW_SP_HQI"));
$table_t3_comments = get_tables_comments(array("TYPE_III"));
//$table_t2_comments = get_tables_comments(array("TYPE_II"));
$table_rs_comments = get_tables_comments(array("RADIOSOURCES"));
$start = time();

// Traitement de la partie upload VOTable
// renseigne les variables $_SESSION['par_post']['from'] et $_SESSION['par_post']['to'] en fonction des dates figurant dans le fichier VOTable
if (count($_FILES)) {
if ($_FILES['upload_file']['error']) {
	switch ($_FILES['nom_du_fichier']['error']){
		case 1: // UPLOAD_ERR_INI_SIZE
			print_error_message("File size limit exceeded!");
			break;
		case 2: // UPLOAD_ERR_FORM_SIZE
			print_error_message("File size limit exceeded!");
			break;
		case 3: // UPLOAD_ERR_PARTIAL
			print_error_message("File upload partial!");
			break;
		case 4: // UPLOAD_ERR_NO_FILE
			print_error_message("File size is 0!");
			break;
	}
	if (!is_uploaded_file($_FILES['upload_file']['tmp_name'])) print_error_message('Upload error!');
	if(filesize($_FILES['upload_file']['tmp_name']) > 1000000) print_error_message('File size limit exceeded!');
}
else {
$file = $_FILES['upload_file']['tmp_name'];

$date_fields = array('DATE_OBS', 'date_obs', 'AR_DATE', 'OBS_DATE', 'time_start', 'time_end', 'time');

$xml = new DOMDocument();
if ($xml->load($file) == false) {
	print_error_message("Bad VOTable!");
//	exit(0);
} else {
$fields = $xml->getElementsByTagName('FIELD');
$index= -1;
for ($i=0; $i<$fields->length; $i++) {
	$field = $fields->item($i);
	$name = $field->getAttribute('name');
	if (in_array($name, $date_fields)) {
		$index = $i;
		break;
	}
}

if ($index != -1) {
	$rows = $xml->getElementsByTagName('TR');
	$tab_dates = array();
	$nb_rows = $rows->length;
	for ($i=0; $i<$nb_rows; $i++) {
		$dt = $rows->item($i)->getElementsByTagName('TD')->item($index)->nodeValue;
		$dt = str_replace('T', ' ', $dt);
		$tmp = explode(' ', $dt);
		//$tab_dates[] = "'".$tmp[0]."'";
		$tab_dates[] = $tmp[0];
	}
	$tab_dates = array_values(array_unique($tab_dates));
	$_SESSION['par_post']['date_sample'] = $tab_dates;
	$_SESSION['par_post']['from'] = min($tab_dates);
	$_SESSION['par_post']['to'] = max($tab_dates);
}
else print_error_message('No date in the VOTable file ');
}
}
}

?>
		<style type="text/css">
			td.default { width:20%; padding: 3px; text-align: center;}
			td.contrib { border:1px solid #dbd7d4; width:20%; padding: 3px; text-align: center;}
			td.feat_sel { border:1px solid #eaf4fd;}
			ul#icons {margin: 0; padding: 0;}
			ul#icons li {margin: 2px; position: relative; padding: 4px 0; cursor: pointer; float: left;  list-style: none;}
			ul#icons span.ui-icon {float: left; margin: 0 4px;}
			.crit_name {background-color: #eaf4fd; font-weight:bold; }
		</style>	
<!--	</head> -->
<?php
include("common.php");
?>
	<div class="ui-widget">
		<div class="ui-widget-content ui-corner-all">
			<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
			<p>The <strong>Heliophysics Feature Catalogue</strong> (HFC) provides access to existing solar and 
heliophysics feature data, extracted from images by automated recognition codes.</p>
			 <p>The catalogue contains geometrical (e.g., gravity center coordinates, contours, area, etc.) and 
photometric feature parameters (e.g., average, minimum, and maximum intensity, etc.) 
, but also tracking information to identify co-rotating feature on the solar disc.</p>
		</div>
	</div>
<?php
include('but_menu.php');
?>
<p></p>
		<h4 class="winA">Query form</h4>
		<form id="form_simple" method="post" ACTION="results.php">
		<div id="tabs_simple" class="bg-lightgray ui-corner-all">
			<ul>
				<li><a href="#tabs-date">1 - Date and time selection</a></li>
				<li><a href="#tabs-feature">2 - Features selection</a></li>
				<li><a href="#tabs-option">3 - Output options</a></li>
			</ul>

			<div id="tabs-date">	<!-- begin and end dates -->
				<div class="ui-corner-all">
				<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
				If 'From' and 'to' are empty, date selection is ignored and query applies to the whole database!
				</div>
				<p>
				<label for="from">From</label>
				<input type="text" id="from" name="from" value="<?php echo $_SESSION['par_post']['from']; ?>" />
				<label for="to">to</label>
				<input type="text" id="to" name="to" value="<?php echo $_SESSION['par_post']['to']; ?>" />
				<label for="duration"> Or Duration between 0 and 60 days </label>
				<input type="text" id="duration" name="duration" size="5" value="<?php echo $_SESSION['par_post']['duration']; ?>" />
				</p>
 				<p>Or <span id="but_upload">Upload dates sample from VOTable</span></p>
				<?php if (count($_FILES)) {
						if (isset($_SESSION['par_post']['date_sample'])) {
							echo '<div class="ui-widget-content ui-corner-all">';
							echo "<label style=\"text-align: left; vertical-align: top;\" for=\"date_sample[]\">Date sample extracted from VOTable:</label>\n";
							echo '<SELECT name="date_sample[]" multiple size="6">';
							foreach($_SESSION['par_post']['date_sample'] as $dt)
								echo '<OPTION selected value="'.$dt.'">'.$dt."\n";
							echo '</SELECT>';
							echo '</div>';
						}
					} ?>
			</div>	<!-- end begin and end dates -->
			<div id="tabs-feature">	<!-- features selection -->
				<?php	print_feature_sel();
						print_solar_region_sel(); 
				?>
			</div>	<!-- end tab features selection -->
			<div id="tabs-option">	<!-- output options -->
				<label>Fields to include in results:</label>
				<div id="accordion_output_options">
					<h3><a href="#">For filaments</a></h3>
					<div id="chkbox_fil_opt_fields" style="text-align: left;">
					<?php
					print_optional_fields('fil', $global['DEFAULT_FIELDS_FIL'], $global['OPT_FIELDS_FIL'], $table_fil_comments);
					?>
					</div>
					<h3><a href="#">For prominences</a></h3>
					<div id="chkbox_pro_opt_fields" style="text-align: left;">
					<?php
					print_optional_fields('pro', $global['DEFAULT_FIELDS_PRO'], $global['OPT_FIELDS_PRO'], $table_pro_comments);
					?>
					</div>
					<h3><a href="#">For active regions</a></h3>
					<div id="chkbox_ar_opt_fields" style="text-align: left;">
					<?php
					print_optional_fields('ar', $global['DEFAULT_FIELDS_AR'], $global['OPT_FIELDS_AR'], $table_ar_comments);
					?>
					</div>
					<h3><a href="#">For coronal holes</a></h3>
					<div id="chkbox_ch_opt_fields" style="text-align: left;">
					<?php
					print_optional_fields('ch', $global['DEFAULT_FIELDS_CH'], $global['OPT_FIELDS_CH'], $table_ch_comments);
					?>
					</div>
					<h3><a href="#">For sunspots</a></h3>
					<div id="chkbox_sp_opt_fields" style="text-align: left;">
					<?php
					print_optional_fields('sp', $global['DEFAULT_FIELDS_SP'], $global['OPT_FIELDS_SP'], $table_sp_comments);
					?>
					</div>
					<h3><a href="#">For type III</a></h3>
					<div id="chkbox_t3_opt_fields" style="text-align: left;">
					<?php
					print_optional_fields('t3', $global['DEFAULT_FIELDS_T3'], $global['OPT_FIELDS_T3'], $table_t3_comments);
					?>
					</div>
					<?php
					/*<h3><a href="#">For type II</a></h3>
					<div id="chkbox_t2_opt_fields" style="text-align: left;">
					print_optional_fields('t2', $global['DEFAULT_FIELDS_T2'], $global['OPT_FIELDS_T2'], $table_t2_comments);
					</div>*/
					?>
					<h3><a href="#">For radio source</a></h3>
					<div id="chkbox_rs_opt_fields" style="text-align: left;">
					<?php
					print_optional_fields('rs', $global['DEFAULT_FIELDS_RS'], $global['OPT_FIELDS_RS'], $table_rs_comments);
					?>
					</div>
				</div>
				<p id="output_format"><label>Additional output format:</label>
<!--					<input type="checkbox" id="html" name="output_format[]" value="html" <?php //if (in_array("html",$_SESSION['par_post']['output_format'])) echo 'checked'; ?> checked /><label for="html">HTML</label> -->
					<input type="checkbox" id="xml" name="output_format[]" value="xml" <?php if (isset($_SESSION['par_post']['output_format']) && !in_array("xml",$_SESSION['par_post']['output_format'])) echo 'unchecked'; else echo 'checked'; ?> /><label for="xml">VOTable</label>
					<input type="checkbox" id="csv" name="output_format[]" value="csv" <?php if (isset($_SESSION['par_post']['output_format']) && in_array("csv",$_SESSION['par_post']['output_format'])) echo 'checked'; ?> /><label for="csv">ASCII (CSV)</label>
				</p>
				<p id="map_type"><label>Daily map:</label>
					<input type="checkbox" id="pixel" name="map_type[]" value="pixel" <?php if (isset($_SESSION['par_post']['map_type']) && !in_array("pixel",$_SESSION['par_post']['map_type'])) echo 'unchecked'; else echo 'checked';?> /><label for="pixel">Pixel</label>
					<input type="checkbox" id="carr" name="map_type[]" value="helio" <?php if (isset($_SESSION['par_post']['map_type']) && in_array("helio",$_SESSION['par_post']['map_type'])) echo 'checked'; ?> /><label for="carr">Carrington</label>
					<input type="checkbox" id="day_syn_carr" name="map_type[]" value="day_syn_carr" <?php if (isset($_SESSION['par_post']['map_type']) && in_array("day_syn_carr",$_SESSION['par_post']['map_type'])) echo 'checked'; ?> /><label for="day_syn_carr">Daily Synoptic map</label>
				</p>
				</div><!-- End accordion output options -->
			</div><!-- End tabs-option -->
		<div style="text-align: right;">
		<input value="Submit" type="submit"><!-- <input value="Reset" type="reset"> -->
 		<span id="but_help" class="ui-icon ui-icon-help"></span>
		</div>
		<p></p>
		<div class="ui-widget-content ui-corner-all">
			<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span></p>
			<p>The list of the features for which data are currently available in the HFC is given in the following table</p>
			<TABLE>
			<TR><TH>Feature</TH><TH>Instrument</TH><TH>Recognition code</TH><TH>Bibliography</TH><TH>Tracking information</TH></TR>
			<TR><TD class="contrib">Active Region</TD><TD class="contrib">SOHO/MDI<BR>SOHO/EIT</TD><TD class="contrib">SMART<BR>SPOCA-AR</TD><TD class="contrib">Higgins et al., 2010<BR>Barra et al., 2009</TD><TD class="contrib">No</TD></TR>
			<TR><TD class="contrib">Coronal Hole</TD><TD class="contrib">SOHO/MDI + SOHO/EIT 195 A<BR>SOHO/EIT</TD><TD class="contrib">CHARM<BR>SPOCA-CH</TD><TD class="default">Krista and Gallagher, 2009<BR>Barra et al., 2009</TD><TD class="contrib">No</TD></TR>
			<TR><TD class="contrib">Filament</TD><TD class="contrib">Meudon H Alpha Spectroheliograph</TD><TD class="contrib">SoSoft & TrackFil</TD><TD class="contrib">Fuller et al., 2005 - Bonnin et al., submitted</TD><TD class="contrib">Yes</TD></TR>
			<TR><TD class="contrib">Prominence</TD><TD class="contrib">Meudon CAII K3 Spectroheliograph</TD><TD class="contrib">SoSoPro</TD><TD class="contrib">N. Fuller</TD><TD class="contrib">No</TD></TR>
			<TR><TD class="contrib">Sunspot</TD><TD class="contrib">SOHO/MDI<BR>SDO/HMI</TD><TD class="contrib">MDISS<BR>SDOSS</TD><TD class="contrib">Zharkov et al., 2005<BR>http://adsabs.harvard.edu/abs/2005SoPh..228..361Z/
</TD><TD class="contrib">No</TD></TR>
			<TR><TD class="contrib">Type III</TD><TD class="contrib">Wind/Waves, STEREO/Swaves</TD><TD class="contrib">RABAT3</TD><TD class="contrib">X. Bonnin</TD><TD class="contrib">No</TD></TR>
			<TR><TD class="contrib">Coronal radio emission</TD><TD class="contrib">Nancay Radio Heliograph</TD><TD class="contrib">NRH2D</TD><TD class="contrib">C. Reni&eacute;, X. Bonnin</TD><TD class="contrib">Yes</TD></TR>
			</TABLE>
		</div>
		</form>
		</div><!-- End tabs -->
<!-- 		</div> -->
<!-- Widget Help -->
<div id="dialog_help" title="Help">
	<p><ul>
	<li>First, choose a time range int the "Date and time selection" tab</li>
	<li>Then, select the Feature(s) for which you want data using the "Feature selection" tab. For a single feature, you can specify other criteria than date using the dropdown menu of the "Extended criteria" box</li>
	<li>For each selected feature, you may specify one or more "Observatory" (use Ctrl key to multiselect). The query will apply only on features detected from observations of the selected Observatory.</li>
	<li>Finally, enter the type(s) of output requested in the "Output options" tab. For each feature, you can also include optional parameters in the results page using corresponding "Optional fields" tab.</li>
	</ul></p>
</div>
<!-- Widget Upload VOTable -->
<div id="dialog-form-upload" title="Load VOTable">
	<form id="upload_file" action="index.php" method="POST" enctype="multipart/form-data">
	<input type="hidden" name="MAX_FILE_SIZE" value="1000000" />
	<label for="name">File</label>
	<input type="file" name="upload_file" id="upload" class="text ui-widget-content ui-corner-all" />
	</form>
</div>
<?php

include("footer.php");

function get_minmax_values($table, $fields) {
	$sql_query = "SELECT ";
	if (count($fields)) {
		foreach ($fields as $key=>$field_name) {
			if ($key != 0) $sql_query = $sql_query.", ";
			$sql_query = $sql_query."MIN($field_name) as MIN_$field_name, MAX($field_name) as MAX_$field_name";
		}
		$sql_query = $sql_query." FROM $table";
	}
	$res = execute_query($sql_query);
	foreach($fields as $key=>$field_name) {
		$result[$field_name]['MIN'] = $res["MIN_$field_name"][0];
		$result[$field_name]['MAX'] = $res["MAX_$field_name"][0];
	}
	return $result;
}

// Request from the tables (faster than from the views)
function print_feature_sel() {
	global $minmax_fil_val, $minmax_pro_val, $minmax_ar_val, $minmax_ch_val, $minmax_sp_val, $minmax_t3_val;

	//$tab_tables1 = array("Filament"=>"FILAMENTS", "Prominence"=>"PROMINENCES");
	//$tab_tables2 = array("Active region"=>"ACTIVEREGIONS", "Coronal hole"=>"CORONALHOLES", "Sunspot"=>"SUNSPOTS", "Type III"=>"TYPE_III", /*"Type II"=>"TYPE_II",*/ "Radio source"=>"RADIOSOURCES");
	$tab_id = array("fil"=>"Filament", "pro"=>"Prominence", "ar"=>"Active region", "ch"=>"Coronal hole", "sp"=>"Sunspot", "t3"=>"Type III", /*"Type II"=>"t2",*/ "rs"=>"Radio source");

	//$instru_by_date = get_minmaxdate_by_instrument();
	$instru_by_date = get_minmaxdate_for_instrument_code_from_tstat();

	echo "<TABLE><TR>\n";
	echo "<TH>Feature type</TH><TH>Observatory</TH>\n";
	echo "</TR>\n";
	// for features with pre-processing
	foreach($tab_id as $feat_type=>$feat_name) {
		echo "<TR>\n";
		echo '<TD NOWRAP class="feat_sel"><input type="checkbox" id="'.$feat_type.'" name="features[]" value="'.$feat_type.'" ';
		if (isset($_SESSION['par_post']['features']) &&  in_array($feat_type,$_SESSION['par_post']['features'])) echo 'checked';
		echo ' /><label for="'.$feat_type.'">'.$feat_name.'</label></TD>';
		echo '<TD  class="feat_sel">';
		echo '<SELECT name="obs_'.$feat_type.'[]" multiple size="2">';
		foreach($instru_by_date[$feat_type] as $data) {
			echo '<OPTION value="'.$data['ID_OBSERVATORY'][0].'">'.$data['OBSERVAT'][0].'/'.$data['INSTRUME'][0].' - '.$data['CODE'][0].' '.$data['MIN_DATE_OBS'][0].' '.$data['MAX_DATE_OBS'][0];
		}
		echo '</SELECT>';
		echo '</TD><TD>';
		print_ext_criteria_feat($feat_type);
		/*switch ($feat_type) {
			case 'fil';
				print_ext_criteria_feat('fil', $minmax_fil_val);
				break;
			case 'pro';
				print_ext_criteria_feat('pro', $minmax_pro_val);
				break;
			case 'ar';
				print_ext_criteria_feat('ar', $minmax_ar_val);
				break;
			case 'ch';
				print_ext_criteria_feat('ch', $minmax_ch_val);
				break;
			case 'sp';
				print_ext_criteria_feat('sp', $minmax_sp_val);
				break;
			case 't3';
				print_ext_criteria_feat('t3', $minmax_t3_val);
				break;
		}*/
		echo "</TD></TR>\n";
	}
	echo "</TABLE>\n";
}

function print_solar_region_sel() {
?>
<div id="win_region" class="ui-widget-content ui-corner-all">
<p class="crit_name">Solar region selection</p>
	<TABLE width="100%"><TR valign="top">
<!--	<TD><div id="region_coord" class="ui-widget-content ui-corner-all">
	<h7 class="ui-widget ui-corner-all winA">System coordinates / Units</h7>
	<SELECT name="region[units]">
	<OPTION selected>Carrington degrees
	<OPTION>Image pixels
	<OPTION>Heliocentric arcsec
	<OPTION>Heliographic degrees
	</SELECT>
	</TD> -->
	<TD><div id="region_lat" class="ui-widget-content ui-corner-all">
<!--	<h6 class="ui-widget-header ui-corner-all winA">Latitude in degrees (-90, +90)</h6> -->
	<span  class="crit_name">Latitude</span>
	<label for="minlat">Min.</label>
	<input type="text" id="minlat" name="region[minlat]" size="5" value="
<?php if (isset($_SESSION['par_post']['region'])) echo $_SESSION['par_post']['region']['minlat']; ?>" />
	<label for="maxlat">Max.</label>
	<input type="text" id="maxlat" name="region[maxlat]" size="5" value="
<?php if (isset($_SESSION['par_post']['region'])) echo $_SESSION['par_post']['region']['maxlat']; ?>" />
	in degrees (-90, 90)
	<p><input type="checkbox" id="region_symband" name="region[symmetric]" /> Extend query to symmetric latitude band</p>
	</div></TD>
	<TD rowspan="2" width="40%"><div class="ui-widget">
	<div class="ui-widget-content ui-corner-all">
	<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
	Position query provide a way to search for features having a specific position on the Sun.<br>
	Specify at least a latitude band.
	</div></div></TD>
	</TR>
	<TR><TD><div id="winpos_lon" class="ui-widget-content ui-corner-all">
<!--	<h5 class="ui-widget-header ui-corner-all winA">Longitude or X</h5> -->
	<span class="crit_name">Longitude</span>
	<label for="minlongitude">Min.</label>
	<input type="text" id="minlon" name="region[minlon]" size="5" value="
<?php if (isset($_SESSION['par_post']['region'])) echo $_SESSION['par_post']['region']['minlon']; ?>" />
	<label for="maxlongitude">Max.</label>
	<input type="text" id="maxlon" name="region[maxlon]" size="5" value="
<?php if (isset($_SESSION['par_post']['region'])) echo $_SESSION['par_post']['region']['maxlon']; ?>" />
	in degrees (0, 360)
	</div></TD>
	</TR></TABLE>
	</div>
<?php
}

function print_ext_criteria_feat($feat_type/*, $minmax_val*/) {

switch($feat_type) {
	case 'fil':
		$minmax_val = get_minmax_values("FILAMENTS", array("SKE_LENGTH_DEG", "SKE_ORIENTATION", "FEAT_AREA_DEG2"));
		$tab_crit = array('Filament'=>array('length'=>array('name'=>'Length', 'comment'=>'in degrees (0,180)', 'helptext'=>'help', 'field'=>'SKE_LENGTH_DEG'),
									'orientation'=>array('name'=>'Orientation', 'comment'=>'in degrees (-90, 90)', 'helptext'=>'help', 'field'=>'SKE_ORIENTATION'),
									'area'=>array('name'=>'Area', 'comment'=>'in square degrees', 'helptext'=>'help', 'field'=>'FEAT_AREA_DEG2'),
									'db'=>array('name'=>'Disappearance', 'comment'=>'', 'helptext'=>'help', 'field'=>'')));
		$tab_crit['Filament']['length']['helptext'] = 'Length query provide a way to search for filament having a specific size.<br>
		Min and Max length currently in the database:'.intval($minmax_val['SKE_LENGTH_DEG']['MIN']).'/'.intval($minmax_val['SKE_LENGTH_DEG']['MAX']);
		$tab_crit['Filament']['orientation']['helptext'] = 'Orientation query allows the user to select filament of specific orientations.<br>
			Orientation corresponds to the global orientation of the filament counterclockwise from Ox axis (ie E-W axis).<br>
			<p> Min and Max orientation currently in the database: '.intval($minmax_val['SKE_ORIENTATION']['MIN']).'/'.intval($minmax_val['SKE_ORIENTATION']['MAX']);
		$tab_crit['Filament']['area']['helptext'] = 'Area query allows the user to select filament of specific visible area.<br>
					Min and Max area currently in the database: '.intval($minmax_val['FEAT_AREA_DEG2']['MIN']).'/'.intval($minmax_val['FEAT_AREA_DEG2']['MAX']);
		break;
	case 'pro':
		$minmax_val = get_minmax_values("PROMINENCES", array("FEAT_HEIGHT_KM", "DELTA_LAT_DEG", "FEAT_MAX_INT"));
		$tab_crit = array('Prominence'=>array('height'=>array('name'=>'Height', 'comment'=>'in km', 'helptext'=>'help', 'field'=>'FEAT_HEIGHT_KM'),
									'delta_lat'=>array('name'=>'Delta latitude', 'comment'=>'in degrees', 'helptext'=>'help', 'field'=>'DELTA_LAT_DEG'),
									'maxint'=>array('name'=>'Max. intensity', 'comment'=>' ', 'helptext'=>'help', 'field'=>'FEAT_MAX_INT')));
		$tab_crit['Prominence']['height']['helptext'] = 'Height query provide a way to search for prominence having a specific height.<br>
		Min and Max length currently in the database:'.intval($minmax_val['FEAT_HEIGHT_KM']['MIN']).'/'.intval($minmax_val['FEAT_HEIGHT_KM']['MAX']);
		$tab_crit['Prominence']['delta_lat']['helptext'] = 'Allows the user to select prominence of specific latitude elongation.<br>
			Min and Max orientation currently in the database: '.intval($minmax_val['DELTA_LAT_DEG']['MIN']).'/'.intval($minmax_val['DELTA_LAT_DEG']['MAX']);
		$tab_crit['Prominence']['maxint']['helptext'] = 'Intensity query allows the user to select prominence of specific max. intensity.<br>
					Min and Max area currently in the database: '.intval($minmax_val['FEAT_MAX_INT']['MIN']).'/'.intval($minmax_val['FEAT_MAX_INT']['MAX']);
		break;
	case 'ar':
		$minmax_val = get_minmax_values("ACTIVEREGIONS", array("FEAT_AREA_DEG2", "FEAT_MAX_INT"));
		$tab_crit = array('Active region'=>array('area'=>array('name'=>'Area', 'comment'=>'in square degrees', 'helptext'=>'help', 'field'=>'FEAT_AREA_DEG2'),
									'maxint'=>array('name'=>'Max. intensity', 'comment'=>' ', 'helptext'=>'help', 'field'=>'FEAT_MAX_INT')));
		$tab_crit['Active region']['area']['helptext'] = 'Area query allows the user to select active region of specific visible area.<br>
					Min and Max area currently in the database: '.intval($minmax_val['FEAT_AREA_DEG2']['MIN']).'/'.intval($minmax_val['FEAT_AREA_DEG2']['MAX']);
		$tab_crit['Active region']['maxint']['helptext'] = 'Intensity query allows the user to select active region of specific max. intensity.<br>
					Min and Max area currently in the database: '.intval($minmax_val['FEAT_MAX_INT']['MIN']).'/'.intval($minmax_val['FEAT_MAX_INT']['MAX']);
		break;
	case 'ch':
		$minmax_val = get_minmax_values("CORONALHOLES", array("FEAT_AREA_MM2", "FEAT_WIDTH_HG_LONG_DEG", "FEAT_WIDTH_HG_LAT_DEG"));
		$tab_crit = array('Coronal hole'=>array('area'=>array('name'=>'Area', 'comment'=>'in square mega meters', 'helptext'=>'help', 'field'=>'FEAT_AREA_MM2'),
												'width'=>array('name'=>'Width', 'comment'=>'in degrees', 'helptext'=>'help', 'field'=>'FEAT_WIDTH_HG_LONG_DEG')));
		$tab_crit['Coronal hole']['area']['helptext'] = 'Area query allows the user to select coronal hole of specific visible area.<br>
					Min and Max area currently in the database: '.intval($minmax_val['FEAT_AREA_MM2']['MIN']).'/'.intval($minmax_val['FEAT_AREA_MM2']['MAX']);
		$tab_crit['Coronal hole']['width']['helptext'] = 'Width query allows the user to select coronal hole of specific longitude width.<br>
					Min and Max area currently in the database: '.intval($minmax_val['FEAT_WIDTH_HG_LONG_DEG']['MIN']).'/'.intval($minmax_val['FEAT_WIDTH_HG_LONG_DEG']['MAX']);
		break;
	case 'sp':
		$minmax_val = get_minmax_values("SUNSPOTS", array("FEAT_AREA_DEG2"));
		$tab_crit = array('Sunspot'=>array('area'=>array('name'=>'Area', 'comment'=>'in square degrees', 'helptext'=>'help', 'field'=>'FEAT_AREA_DEG2')));
		$tab_crit['Sunspot']['area']['helptext'] = 'Area query allows the user to select sunspot of specific visible area.<br>
					Min and Max area currently in the database: '.intval($minmax_val['FEAT_AREA_DEG2']['MIN']).'/'.intval($minmax_val['FEAT_AREA_DEG2']['MAX']);
		break;
	case 't3':
		$minmax_val = get_minmax_values("TYPE_III", array("FEAT_MAX_INT"));
		$tab_crit = array('Type III'=>array('maxint'=>array('name'=>'Max. intensity', 'comment'=>'in dB', 'helptext'=>'help', 'field'=>'FEAT_MAX_INT')));
		$tab_crit['Type III']['maxint']['helptext'] = 'Intensity query allows the user to select type III of specific max intensity.<br>
					Min and Max area currently in the database: '.intval($minmax_val['FEAT_MAX_INT']['MIN']).'/'.intval($minmax_val['FEAT_MAX_INT']['MAX']);
		break;
}

foreach($tab_crit as $feat_name=>$crits) {
	echo '<div id="win_ext_criteria_'.$feat_type.'" class="ui-widget-content ui-corner-all">';
	// criteria drop down list selection
	echo '<span class="crit_name">'.$feat_name.' criteria</span>&nbsp;';
	echo '<select id="'.$feat_type.'_criter_select" name="'.$feat_type.'_criter_select">';
	echo '<option>None</option>';
	foreach($crits as $crit) {
		$var_name = $feat_type.'_criter_select';
		if (!strcmp($_SESSION['par_post'][$var_name],$crit['name'] )) echo '<option selected>';
		else echo '<option>';
		echo $crit['name'].'</option>';
	}
	echo "</select>\n";

	// widget corresponding to the selected criteria (only one is printed)
	foreach($crits as $key=>$crit) {
		if (strlen($crit['comment'])) {
		echo '<div id="win_'.$feat_type.'_'.$key.'">';
		echo "<TABLE width=\"100%\"><TR>\n";
		echo "<TD NOWRAP>\n";
		echo '<div id="win'.$key.'_val" class="ui-widget-content ui-corner-all">';
		echo 'Min.';
		echo '<input type="text" id="'.$feat_type.'_min'.$key.'" name="'.$feat_type.'_'.$key.'_par[min]" size="5" value="';
		$bid = $feat_type.'_'.$key.'_par';
		if (isset($_SESSION['par_post'][$bid])) echo $_SESSION['par_post'][$bid]['min'];
		else echo intval($minmax_val[$crit['field']]['MIN']);
		echo '" /><BR>';
		echo 'Max.';
		echo '<input type="text" id="'.$feat_type.'_max'.$key.'" name="'.$feat_type.'_'.$key.'_par[max]" size="5" value="';
		if (isset($_SESSION['par_post'][$bid])) echo $_SESSION['par_post'][$bid]['max'];
		else echo intval($minmax_val[$crit['field']]['MAX']);
		echo '" /><BR>';
		echo $crit['comment'];
		echo "</div>\n";
		echo "</TD>\n";
		echo "<TD width=\"70%\">\n";
		echo '<div class="ui-widget-content ui-corner-all">';
		echo '<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>';
		echo $crit['helptext'];
		echo "</div>\n";
		echo "</TD>\n";
		echo "</TR></TABLE>\n";
		echo "</div>\n";
		}
	}
	echo "</div>\n";
}
}

function print_optional_fields($feat_type, $default_fields, $opt_fields, $tab_comments) {
	$sess_varname = $feat_type.'opt';
	foreach ($default_fields as $field) {
		echo '<input type="checkbox" id="'.strtolower($field).'" name="'.$sess_varname.'[]" value="'.$field.'"';
		if (isset($_SESSION['par_post'][$sess_varname]) && !in_array($field,$_SESSION['par_post'][$sess_varname]))
			echo 'unchecked';
		else echo 'checked';
		echo '/><label for="'.strtolower($field).'">'.$tab_comments[$field].' ('.$field.')</label>';
		echo '<br>';
	}
	// Optional fields
	foreach ($opt_fields as $field) {
		echo '<input type="checkbox" id="'.strtolower($field).'" name="'.$sess_varname.'[]" value="'.$field.'"';
		if (isset($_SESSION['par_post'][$sess_varname]) && in_array($field,$_SESSION['par_post'][$sess_varname]))
			echo 'checked';
		echo '/><label for="'.strtolower($field).'">'.$tab_comments[$field].' ('.$field.')</label>';
		echo '<br>';
	}
}
?>

