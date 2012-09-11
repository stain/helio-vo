<?php
// functions used in php scripts of this directory

require("global.php");
require("paramsol.php");
require("classdatej.php");
//require("plot_solar_struct.php");

function execute_query($query) {
	global $global;
	//IDENTIFIANT MYSQL
	$MYSQL_HOST=$global['MYSQL_HOST'];
	$MYSQL_USER=$global['MYSQL_USER'];
	$MYSQL_PASS=$global['MYSQL_PASS'];
	$tbsp=$global['TBSP'];

	$connected = mysql_connect($MYSQL_HOST,$MYSQL_USER,$MYSQL_PASS) or die("Erreur de connexion au serveur.");
	$db = mysql_select_db($tbsp) or die ("erreur de connexion base: $tbsp!!");

	//echo '***'.$query.'***';
	log_query($query);
	$stmt = mysql_query($query, $connected);
	if (gettype($stmt) == "resource") {
		$results = array();
		$nbrows = mysql_num_rows($stmt);
		while ($row = mysql_fetch_assoc($stmt)) {
			foreach($row as $key => $val) {
				$results[strtoupper($key)][] = $val;
			}//finFOREACH
		}//finWHILE
	//print_r($results);
	//echo '<br><br>';
		mysql_free_result($stmt);	//libère la ressource
		mysql_close($connected);	//fin conn
	}//finIF
	else{
		echo '<br> #### Impossible d\'executer la requete : ' . mysql_error().' #### <br>';
		$results = null;
		$nbrows = null;
		}
	return $results;
}

function log_query($query) {
	global $global;
	$logfile = "/tmp/hfcgui_".$global['TBSP'].".log";

	$file = fopen($logfile, 'a+');
	if ($file) {
		fputs($file, '');
		$time = date(DATE_ATOM, time());
		fwrite($file, $_SERVER['PHP_SELF'].' '.$time.' '.$query."\n");
		fclose($file);
	}
}

// determine from chain_code the points of a feature in pix coordinates 
function make_tab_pix_coord($start_pix, $ccode) {
	// mapping between chaincode numbers and pixel to move
	$tab_dec = array('0'=>array(-1,0), '1'=>array(-1,-1), '2'=>array(0,-1), '3'=>array(1,-1),
					 '4'=>array(1,0), '5'=>array(1,1), '6'=>array(0,1), '7'=>array(-1,1));
	// processing the start pixel
	$pix_x = array(); $pix_y = array();
	$pix_x[] = $start_pix[0];
	$pix_y[] = $start_pix[1];
	for($i=1; $i<strlen($ccode)+1; $i++) {
		$pix_x[] = $pix_x[$i-1] + $tab_dec[$ccode[$i-1]][0];
		$pix_y[] = $pix_y[$i-1] + $tab_dec[$ccode[$i-1]][1];
	}
	
	return array($pix_x, $pix_y);
}

// determine from chain_code the points of a feature in lon/lat coordinates 
// parameter $results must contain all the fields necessary for pix2carr
function make_tab_carr_coord($start_pix, $ccode, $results) {
	// retrieving parameters for pix2carr
	$naxis1 = $results['NAXIS1'][0];
	$naxis2 = $results['NAXIS2'][0];
	$cdelt1 = $results['CDELT1'][0];
	$cdelt2 = $results['CDELT2'][0];
	$center_x = $results['CENTER_X'][0];
	$center_y = $results['CENTER_Y'][0];
	$rsun = $results['R_SUN'][0];
	$date_obs = $results['DATE_OBS'][0];
	//$lon_c = $results['SC_CAR_LON'][0];
	
	// first get a pixels array from the chaine_code
	$pix_xy = make_tab_pix_coord($start_pix, $ccode);
	//print_r($pix_xy);
	//print "<BR>";
	// Convert each pixel in carrignton lat/lon
	foreach($pix_xy[0] as $key=>$pix_x) {
		$pix = array($pix_xy[0][$key], $pix_xy[1][$key]);
		$carxy = pix2car($pix,$naxis1,$naxis2,$cdelt1,$cdelt2,$center_x,$center_y,$rsun,$date_obs);
		$car_lon[] = $carxy[0];
		$car_lat[] = $carxy[1];
	}
	return array($car_lon, $car_lat);
}
// Calculate geometry of a feature from start pixel and chain code
// $row is one row from a query result of view filaments, active region or coronal holes
function make_tab_carr_coord2($start_pix, $ccode, $pix_param) {
	// retrieving parameters for pix2carr
	$naxis1 = $pix_param['NAXIS1'];
	$naxis2 = $pix_param['NAXIS2'];
	$cdelt1 = $pix_param['CDELT1'];
	$cdelt2 = $pix_param['CDELT2'];
	$center_x = $pix_param['CENTER_X'];
	$center_y = $pix_param['CENTER_Y'];
	$rsun = $pix_param['R_SUN'];
	$date_obs = $pix_param['DATE_OBS'];
	
	// first get a pixels array from the chaine_code
	$pix_xy = make_tab_pix_coord($start_pix, $ccode);
	//print_r($pix_xy);
	//print "<BR>";
	// Convert each pixel in carrignton lat/lon
	foreach($pix_xy[0] as $key=>$pix_x) {
		$pix = array($pix_xy[0][$key], $pix_xy[1][$key]);
		$carxy = pix2car($pix,$naxis1,$naxis2,$cdelt1,$cdelt2,$center_x,$center_y,$rsun,$date_obs);
		$car_lon[] = $carxy[0];
		$car_lat[] = $carxy[1];
	}
	return array($car_lon, $car_lat);
}

// Convert pixel from sun centre to Carrington coords.
// return longitude, latitude in degrees
function pix2car($pixel,$naxis1,$naxis2,$cdelt1,$cdelt2,$center_x,$center_y,$rsun,$date_obs) {
	
	//Calculate R0 value from Sun radius for arcmin2hel()
	$Robs = 1.0/tan(deg2rad(($cdelt1*$rsun)/3600));
	
	// calcul de B0
	$timestmp = strtotime($date_obs);
	$date_array = getdate($timestmp);
//	print_r($date_array);
//	print "<BR>";
	$dj = new Datej();
	$dj->initFromCal($date_array['year'], $date_array['mon'], $date_array['mday'], $date_array['hours'], $date_array['minutes']);
	solphy($dj->dj, $lo, $b0, $po);
	
	//print "lo = ".rad2deg($lo).", b0 = ".rad2deg($b0).", po = ".rad2deg($po)."<BR>";
	
	$arrx=$pixel[0];
	$arry=$pixel[1];

	//  Convert pixel to arcseconds
	$ix = $cdelt1*($arrx - $center_x);
	$iy = $cdelt2*($arry - $center_y);

	//print "in arcsec ix = ".$ix.", iy = ".$iy.", Robs = ".$Robs."<BR>";
	$hel_coord = ARCMIN2HEL($ix/60., $iy/60., $Robs, rad2deg($b0));
	
	// problem for structure on 2 rotations
	$lon = fmod(fmod($hel_coord[1] + 360., 360.) + rad2deg($lo), 360.);
/*	if (rad2deg($lo) < 90) {
		// rotation R-1
		if ($lon < 90 && $lon_c > 270) $lon = $lon + 360;
		// rotation R
		if ($lon > 270 && $lon_c < 90) $lon = $lon - 360;
	}
	if (rad2deg($lo) > 270) {
		// rotation R
		if ($lon > 270 && $lon_c < 90) $lon = $lon - 360;
		// rotation R+1
		if ($lon < 90 && $lon_c > 270) $lon = $lon + 360;
	}*/

	$tab_out  = array($lon, $hel_coord[0]);
	
	//print "pix2carr result:<BR>";
	//print_r($tab_out);
	//print "<BR>";
	
//	$tab_out  = array(fmod(fmod($hel_coord[1] + 360., 360.) + rad2deg($lo), 360.), $hel_coord[0]);
//	$tab_out  = array(fmod($hel_coord[1] + rad2deg($lo), 360.), $hel_coord[0]);
	//$tab_out  = array(fmod($hel_coord[1] + 360., 360.) + rad2deg($lo), $hel_coord[0]);
	//$tab_out = array($hel_coord[1], $hel_coord[0]);
	return $tab_out;
}

//Convert arcmin from sun centre to heliographic coords
// return latitude, longitude in degrees
function ARCMIN2HEL($x, $y, $Robs, $b0) {
	
	$b0 = deg2rad($b0);
	
	$xxat = tan(deg2rad($x/60.)); // (Convert to radians & tanify)
   	$yyat = tan(deg2rad($y/60.)); // (Convert to radians & tanify)
   	
//   	print "xxat = ".$xxat.", yyat = ".$yyat."<BR>";
/* Convert to cylindrical angular coordinates and azimuth -- makes
; the final transformation easier.  Here, ra is the angle out from
; centerline; phi is the azimuth.  This reduces the problem to 2-D
; geometry in the observer -- Sun-center -- viewpoint plane.
*/
// Load phi with atan(xxat,yyat).
   $rat2 = ($xxat*$xxat+$yyat*$yyat);
   if ($rat2 != 0) $phi = atan2($xxat,$yyat);
//	print "phi = ".$phi.", rat2 = ".$rat2."<BR>";
   $max_ra = asin(1.0/$Robs);
   $max_rat2 = tan($max_ra)*tan($max_ra);

   if ($rat2 > $max_rat2) {
		$rat2 = $max_rat2;
   }
//   print "rat2 = ".$rat2."<BR>";
/* Solving for the intersection of the line of sight with the sphere
; gives a z-coordinate (toward the observer) of
;   z = R * (sin(ra))^2 +/- sqrt( Ro^2 - (sin(ra))^2 * R^2 )
; with Ro = the solar radius, ra the angular displacement from disk 
; center, and R the viewpoint distance from Sun center.
;
; We normally want the positive branch, which represents the front
; side of the Sun; but who knows? Someone may want the opposite.
*/
   if ($rat2 != 0) $ras2 = 1/(1+1/$rat2);
   $d1=(1-$ras2);
   if ($d1 < 0) $d1 = 0.;
   $d2=(1-($Robs*$Robs)*$ras2);
   if ($d2 < 0) $d2 = 0.;
   $x = $ras2*$Robs + sqrt($d1) * sqrt($d2);

   	if ($rat2 < 0) $rat2 = 0.; 
	$rr = sqrt($rat2) * ($Robs - $x);
//	print "d1 = ".$d1.", d2 = ".$d2.", x = ".$x.", ras2 = ".$ras2.", rat2 = ".$rat2."<BR>";
/* Now we can finally convert back to xyz coords and do the 
; helioraphic conversion.  x: towards obs., y: west, z: North
*/
   $y = sin($phi)*$rr;
   $z = cos($phi)*$rr;
//   print "y = ".$y.", z = ".$z."<BR>";
/*;---------------------------------------------------------------------------
;  rotate around y axis to correct for B0 angle (B0: hel. lat. of diskcenter)
;---------------------------------------------------------------------------
*/
   $z_bid = $z*cos($b0) + $x*sin($b0);
   $x_bid = $x*cos($b0) - $z*sin($b0);
   $z = $z_bid;
   $x = $x_bid;
//   print "x = ".$x.", z = ".$z."<BR>";

/*;---------------------------------------------------------------------------
;  calc. latitude and longitude.
;---------------------------------------------------------------------------
*/
   $latitude = asin($z);
   if ($latitude > deg2rad(89.99)) $latitude = deg2rad(89.99);
   if ($latitude < deg2rad(-89.99)) $latitude = deg2rad(-89.99);
   $longitude = atan2($y,$x);
 //  print "latitude = ".$latitude.", longitude = ".$longitude." en radians<BR>";
/*;---------------------------------------------------------------------------
;  convert to degrees.
;---------------------------------------------------------------------------
*/
   $latitude = rad2deg($latitude);
   $longitude = rad2deg($longitude);
 //  print "latitude = ".$latitude.", longitude = ".$longitude." en degres<BR>";

   return array($latitude, $longitude);
}

function print_error_message($mess) {
	echo '<div class="ui-widget">';
	echo '<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">';
	echo '<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>';
	echo '<strong>Alert:</strong> '.$mess.'</p>';
	echo '</div>';
	echo '</div>';
}

function get_tables_comments($tables) {
	$tab_comment = array();
	foreach($tables as $table) {
		$sql_query = "SHOW FULL COLUMNS FROM $table";
		$rs = execute_query($sql_query);
		$nb_fields = count($rs['FIELD']);
		for ($i=0; $i<$nb_fields; $i++) {
			if (strlen($rs['COMMENT'][$i])) $tab_comment[$rs['FIELD'][$i]] = $rs['COMMENT'][$i];
			else $tab_comment[$rs['FIELD'][$i]] = $rs['FIELD'][$i];
		}
	}
	//return array_unique($tab_comment);
	return $tab_comment;
}

function get_minmaxdate_by_instrument() {
	$tab_tables1 = array("fil"=>"FILAMENTS", "pro"=>"PROMINENCES");
	$tab_tables2 = array("ar"=>"ACTIVEREGIONS", "ch"=>"CORONALHOLES", "sp"=>"SUNSPOTS", "t3"=>"TYPE_III", /*"Type II"=>"TYPE_II",*/ "rs"=>"RADIOSOURCES");
	
	$tab_results = array();
	foreach($tab_tables1 as $feat_type=>$table) {
		$sql_query = "SELECT DISTINCT(ID_OBSERVATORY) FROM $table t, OBSERVATIONS o, OBSERVATORY i, PP_OUTPUT p WHERE ";
		$sql_query = $sql_query."(t.PP_OUTPUT_ID=p.ID_PP_OUTPUT) AND (p.OBSERVATIONS_ID=o.ID_OBSERVATIONS) AND (o.OBSERVATORY_ID=i.ID_OBSERVATORY)";
		$res = execute_query($sql_query);
		foreach($res['ID_OBSERVATORY'] as $id_observatory) {
			$sql_query = "SELECT DATE(MIN(DATE_OBS)) AS MINDATE, DATE(MAX(DATE_OBS)) AS MAXDATE, ID_OBSERVATORY, OBSERVAT, INSTRUME, TELESCOP, WAVEMIN FROM OBSERVATIONS o, OBSERVATORY i WHERE ";
			$sql_query = $sql_query."ID_OBSERVATORY=\"".$id_observatory."\"";
			$sql_query = $sql_query." AND (o.OBSERVATORY_ID=i.ID_OBSERVATORY)";
			//$rs = execute_query($sql_query);
			$tab_results[$feat_type][] = execute_query($sql_query);
		}
	}
	foreach($tab_tables2 as $feat_type=>$table) {
		$sql_query = "SELECT DISTINCT(ID_OBSERVATORY) FROM $table t, OBSERVATIONS o, OBSERVATORY i WHERE ";
		switch ($feat_type) {
			case 'ch';
				$sql_query = $sql_query."(t.OBSERVATIONS_ID_EIT=o.ID_OBSERVATIONS) AND (o.OBSERVATORY_ID=i.ID_OBSERVATORY)";
				break;
			case 'sp';
				$sql_query = $sql_query."(t.OBSERVATIONS_ID_IC=o.ID_OBSERVATIONS) AND (o.OBSERVATORY_ID=i.ID_OBSERVATORY)";
				break;
			default:
				$sql_query = $sql_query."(t.OBSERVATIONS_ID=o.ID_OBSERVATIONS) AND (o.OBSERVATORY_ID=i.ID_OBSERVATORY)";
				break;
		}
		$res = execute_query($sql_query);
		foreach($res['ID_OBSERVATORY'] as $id_observatory) {
			$sql_query = "SELECT DATE(MIN(DATE_OBS)) AS MINDATE, DATE(MAX(DATE_OBS)) AS MAXDATE, ID_OBSERVATORY, OBSERVAT, INSTRUME, TELESCOP, WAVEMIN FROM OBSERVATIONS o, OBSERVATORY i WHERE ";
			$sql_query = $sql_query."ID_OBSERVATORY=\"".$id_observatory."\"";
			$sql_query = $sql_query." AND (o.OBSERVATORY_ID=i.ID_OBSERVATORY)";
			$tab_results[$feat_type][] = execute_query($sql_query);
		}
	}
	return $tab_results;
}

// get distinct couple of instrument/frc code
function get_distinct_instrume($feat_type) {

	$tab_tables = array("fil"=>"FILAMENTS", "pro"=>"PROMINENCES", "ar"=>"ACTIVEREGIONS", "ch"=>"CORONALHOLES", "sp"=>"SUNSPOTS", "t3"=>"TYPE_III", "rs"=>"RADIOSOURCES");
	$table = $tab_tables[$feat_type];

	$tab_res = array();
	$query = "SELECT DISTINCT(ID_OBSERVATORY), FRC_INFO_ID FROM ".$table;
		switch($feat_type) {
			case 'fil':
			case 'pro':
				$qfrom = ", OBSERVATIONS, OBSERVATORY, PP_OUTPUT ";
				$qwhere = "(PP_OUTPUT_ID=ID_PP_OUTPUT) AND (OBSERVATIONS_ID=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
				break;
			case 'ch';
				$qfrom = ", OBSERVATIONS, OBSERVATORY ";
				$qwhere = "(OBSERVATIONS_ID_EIT=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
				break;
			case 'sp';
				$qfrom = ", OBSERVATIONS, OBSERVATORY ";
				$qwhere = "(OBSERVATIONS_ID_IC=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
				break;
			default:
				$qfrom = ", OBSERVATIONS, OBSERVATORY ";
				$qwhere = "(OBSERVATIONS_ID=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
				break;
		}
		$query = $query.$qfrom." WHERE ".$qwhere;
		$rsb = execute_query($query);
		foreach($rsb['ID_OBSERVATORY'] as $key=>$id_observatory) {
			$query = "SELECT ID_OBSERVATORY, OBSERVAT, INSTRUME, TELESCOP, WAVEMIN, CODE FROM FRC_INFO, OBSERVATORY WHERE ID_FRC_INFO=".$rsb['FRC_INFO_ID'][$key]." AND ID_OBSERVATORY=".$id_observatory;
			$rs = execute_query($query);
			foreach($rs as $field=>$res) {
				foreach($res as $val) $tab_res[$field][] = $val;
			}
		}
	
	return $tab_res;
}

/*
// get distinct couple of instrument/frc code
function get_distinct_instrume($feat_type) {

	$tab_tables = array("fil"=>"FILAMENTS", "pro"=>"PROMINENCES", "ar"=>"ACTIVEREGIONS", "ch"=>"CORONALHOLES", "sp"=>"SUNSPOTS", "t3"=>"TYPE_III", "rs"=>"RADIOSOURCES");
	$table = $tab_tables[$feat_type];

	$tab_res = array();
	// first query frc_info_id
	$query = "SELECT DISTINCT(FRC_INFO_ID) FROM ".$table;
	$rs = execute_query($query);
	foreach($rs['FRC_INFO_ID'] as $frc_info_id) {
		switch($feat_type) {
			case 'fil':
			case 'pro':
				$qfrom = ", OBSERVATIONS, OBSERVATORY, PP_OUTPUT ";
				$qwhere = "(PP_OUTPUT_ID=ID_PP_OUTPUT) AND (OBSERVATIONS_ID=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
				break;
			case 'ch';
				$qfrom = ", OBSERVATIONS, OBSERVATORY ";
				$qwhere = "(OBSERVATIONS_ID_EIT=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
				break;
			case 'sp';
				$qfrom = ", OBSERVATIONS, OBSERVATORY ";
				$qwhere = "(OBSERVATIONS_ID_IC=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
				break;
			default:
				$qfrom = ", OBSERVATIONS, OBSERVATORY ";
				$qwhere = "(OBSERVATIONS_ID=ID_OBSERVATIONS) AND (OBSERVATORY_ID=ID_OBSERVATORY)";
				break;
		}
		$query = "SELECT DISTINCT(ID_OBSERVATORY) FROM ".$table;
		$query = $query.$qfrom." WHERE ".$qwhere." AND FRC_INFO_ID=".$frc_info_id;
		$rsb = execute_query($query);
		foreach($rsb['ID_OBSERVATORY'] as $id_observatory) {
			$query = "SELECT ID_OBSERVATORY, OBSERVAT, INSTRUME, TELESCOP, WAVEMIN, CODE FROM FRC_INFO, OBSERVATORY WHERE ID_FRC_INFO=".$frc_info_id." AND ID_OBSERVATORY=".$id_observatory;
			$rsc = execute_query($query);
			foreach($rsc as $field=>$res) {
				$nbres= count($res);
				foreach($res as $val) $tab_res[$field][] = $val;
			}
		}
	}
	
	return $tab_res;
}

function get_minmaxdate_for_instrument_code() {
	$tab_tables = array("fil"=>"FILAMENTS", "pro"=>"PROMINENCES", "ar"=>"ACTIVEREGIONS", "ch"=>"CORONALHOLES", "sp"=>"SUNSPOTS", "t3"=>"TYPE_III", "rs"=>"RADIOSOURCES");
	$tab_results = array();
	foreach($tab_tables as $feat_type=>$table) {
		$instru_code = get_distinct_instrume($feat_type);
		foreach($instru_code['ID_OBSERVATORY'] as $key=>$id_observatory) {
			switch($feat_type) {
				case 'fil':
				case 'pro':
					$qfrom = ", OBSERVATIONS, PP_OUTPUT ";
					$qwhere = "(PP_OUTPUT_ID=ID_PP_OUTPUT) AND (OBSERVATIONS_ID=ID_OBSERVATIONS)";
					break;
				case 'ch';
					$qfrom = ", OBSERVATIONS ";
					$qwhere = "(OBSERVATIONS_ID_EIT=ID_OBSERVATIONS)";
					break;
				case 'sp';
					$qfrom = ", OBSERVATIONS ";
					$qwhere = "(OBSERVATIONS_ID_IC=ID_OBSERVATIONS)";
					break;
				default:
					$qfrom = ", OBSERVATIONS ";
					$qwhere = "(OBSERVATIONS_ID=ID_OBSERVATIONS)";
					break;
			}
			$sql_query = "SELECT DATE(MIN(DATE_OBS)) AS MINDATE, DATE(MAX(DATE_OBS)) AS MAXDATE FROM ".$table;
			$sql_query = $sql_query.$qfrom;
			$sql_query = $sql_query." WHERE OBSERVATORY_ID=\"".$id_observatory."\"";
			$sql_query = $sql_query." AND ".$qwhere;
			$rs = execute_query($sql_query);
			$instru_code['MINDATE'][$key] = $rs['MINDATE'][0];
			$instru_code['MAXDATE'][$key] = $rs['MAXDATE'][0];
		}
		$tab_results[$feat_type][] = $instru_code;
	}
	
	return $tab_results;
}
*/
function get_minmaxdate_for_instrument_code_from_tstat() {
	$tab_tables = array("fil"=>"FILAMENTS", "pro"=>"PROMINENCES", "ar"=>"ACTIVEREGIONS", "ch"=>"CORONALHOLES", "sp"=>"SUNSPOTS", "t3"=>"TYPE_III", "rs"=>"RADIOSOURCES");
	$query = "SELECT * FROM DATE_STAT, OBSERVATORY, FRC_INFO WHERE OBSERVATORY_ID=ID_OBSERVATORY AND FRC_INFO_ID=ID_FRC_INFO GROUP_BY OBSERVATORY_ID ORDER BY FEAT_TYPE";
	$query = "SELECT *, date_format(min(TIME_RANGE), '%Y-%m') as MIN_DATE_OBS, date_format(max(TIME_RANGE), '%Y-%m') as MAX_DATE_OBS FROM DATE_STAT, OBSERVATORY, FRC_INFO WHERE NB_FEAT!=0 AND OBSERVATORY_ID=ID_OBSERVATORY AND FRC_INFO_ID=ID_FRC_INFO GROUP BY OBSERVATORY_ID, FRC_INFO_ID ORDER BY FEAT_TYPE";
	$rs = execute_query($query);
	$tab_results = array();
	foreach($rs['ID_OBSERVATORY'] as $key=>$id_observatory) {
		$feat_type = array_search($rs['FEAT_TYPE'][$key], $tab_tables);
		//echo "feat_type=".$feat_type."<BR>";
		$tmp =array();
		foreach($rs as $field=>$tab) {
			//echo "field=".$field." value=".$tab[$key]."<BR>";
			$tmp[$field][] = $tab[$key];
		}
		$tab_results[$feat_type][] = $tmp;
	}
	return $tab_results;
}

// get distinct couple of instrument/frc code
function get_distinct_instrume_from_tstat($feat_type) {

	$tab_tables = array("fil"=>"FILAMENTS", "pro"=>"PROMINENCES", "ar"=>"ACTIVEREGIONS", "ch"=>"CORONALHOLES", "sp"=>"SUNSPOTS", "t3"=>"TYPE_III", "rs"=>"RADIOSOURCES");
	$table = $tab_tables[$feat_type];

	$tab_res = array();
	$query = "SELECT * FROM DATE_STAT, OBSERVATORY, FRC_INFO WHERE FEAT_TYPE='".$table."'";
	$query = $query." AND OBSERVATORY_ID=ID_OBSERVATORY AND FRC_INFO_ID=ID_FRC_INFO";
	$query = $query." GROUP BY OBSERVATORY_ID";
	$tab_res = execute_query($query);
	
	return $tab_res;
}

?>
