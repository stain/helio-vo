<?php
// declaration du tableau $global qui contient les parametres qui different entre helio-fc1 et voparis-helio

// parametres de la base
//$global['MYSQL_HOST'] = "helio-fc1.obspm.fr";
//$global['MYSQL_HOST'] = "solpcr.obspm.fr";
$global['MYSQL_HOST'] = "voparis-mysql5-paris.obspm.fr";
$global['MYSQL_USER'] = "guest";
$global['MYSQL_PASS'] = "guest";
$global['TBSP'] = "hfc1";
//$global['TBSP'] = "hfc1test";
//$global['TBSP'] = "hfc1tmp";

// chemin pour les executables systeme (wget par exemple)
//$global['BIN_PATH'] = "/usr/bin/";
$global['BIN_PATH'] = "/usr/local/bin/";
//$global['STILTS_EXE'] = "/var/www/cgi-bin/stilts/stilts -classpath /home/renie/develop/workspace/lib/mysql-connector-java-5.0.8-bin.jar -Djdbc.drivers=com.mysql.jdbc.Driver sqlclient ";
$global['STILTS_EXE'] = "/usr/local/www/apache22/cgi-bin/stilts/stilts -Djdbc.drivers=com.mysql.jdbc.Driver sqlclient ";

// chemin pour le fichier police des images
//$global['FONT_PATH'] = "/usr/share/fonts/bitstream-vera/Vera.ttf";
//$global['FONT_PATH'] = "/data/wwwhelio/html/gui/jpgraph/src/fonts/DejaVuSans.ttf";
$global['FONT_PATH'] = "/usr/local/www/apache22/data/hfc-gui/jpgraph/src/fonts/DejaVuSans.ttf";

// Tableau des significations du champs PHENOM
$global['PHENOM'] = array(0=>'-', 1=>'Appearence after the east limb', 2=>'Disappearence before the west limb',
						5=>'Disparition brusque', 6=>'Reappearance after disparition brusque', 7=>'Abnormal behavior');

// Tableaux des champs affichés par défaut
$global['DEFAULT_FIELDS_FIL'] = array("PHENOM", "FEAT_CARR_LAT_DEG", "FEAT_CARR_LONG_DEG", "SKE_LENGTH_DEG");
$global['OPT_FIELDS_FIL'] = array("FEAT_X_ARCSEC", "FEAT_Y_ARCSEC", "SKE_CURVATURE", "SKE_ORIENTATION", "FEAT_AREA_DEG2", "FEAT_MEAN2QSUN");
$global['DEFAULT_FIELDS_PRO'] = array("BASE_MID_CARR_LAT_DEG", "BASE_MID_CARR_LONG_DEG", "DELTA_LAT_DEG", "FEAT_HEIGHT_KM");
$global['OPT_FIELDS_PRO'] = array("BASE_MID_X_ARCSEC", "BASE_MID_Y_ARCSEC", "FEAT_AREA_ARCSEC2", "FEAT_MEAN_INT", "FEAT_HEIGHT_ARCSEC");
$global['DEFAULT_FIELDS_AR'] = array("NOAA_NUMBER","FEAT_HG_LAT_DEG"/*, "FEAT_HG_LONG_DEG"*/, "FEAT_AREA_DEG2");
$global['OPT_FIELDS_AR'] = array("FEAT_X_ARCSEC", "FEAT_Y_ARCSEC", "FEAT_MAX_INT", "FEAT_MIN_INT", "FEAT_MEAN_INT");
//$global['DEFAULT_FIELDS_CH'] = array("CHC_ARC_X", "CHC_ARC_Y", "CH_AREA_MM");
//$global['OPT_FIELDS_CH'] = array("LAT_WIDTH_DEG", "LON_WIDTH_DEG", "CH_MEAN2QSUN", "CH_MEAN_BZ");
$global['DEFAULT_FIELDS_CH'] = array("FEAT_CARR_LAT_DEG", "FEAT_CARR_LONG_DEG", "FEAT_AREA_MM2", "BR_HG_LONG3_DEG"/*, "BR_CARR_LAT2_DEG", "BR_CARR_LONG3_DEG", "BR_CARR_LAT3_DEG"*/);
$global['OPT_FIELDS_CH'] = array("FEAT_X_PIX", "FEAT_Y_PIX", "FEAT_X_ARCSEC", "FEAT_Y_ARCSEC", "FEAT_MEAN2QSUN", "FEAT_MEAN_BZ", "FEAT_WIDTH_HG_LONG_DEG", "FEAT_WIDTH_HG_LAT_DEG");
$global['DEFAULT_FIELDS_SP'] = array("NOAA_NUMBER","FEAT_CARR_LAT_DEG", "FEAT_CARR_LONG_DEG", "FEAT_DIAM_DEG", "FEAT_AREA_DEG2", "FEAT_MEAN2QSUN", "UMBRA_NUMBER");
$global['OPT_FIELDS_SP'] = array("FEAT_TOT_BZ", "FEAT_ABS_BZ", "FEAT_X_ARCSEC", "FEAT_Y_ARCSEC");
$global['DEFAULT_FIELDS_T3'] = array("CC_X_UTC", "CC_Y_MHZ", "FEAT_MAX_INT", "FEAT_MEAN_INT");
$global['OPT_FIELDS_T3'] = array("SKE_CC_X_UTC", "SKE_CC_Y_MHZ");
$global['DEFAULT_FIELDS_T2'] = array("SKE_CC_X_UTC", "SKE_CC_Y_MHZ", "FEAT_MAX_INT", "FEAT_MEAN_INT");
$global['OPT_FIELDS_T2'] = array("COMPONENT");
$global['DEFAULT_FIELDS_RS'] = array("FEAT_CARR_LAT_DEG", "FEAT_CARR_LONG_DEG", "EL_AXIS1", "EL_AXIS2", "EL_ANGLE", "FEAT_AREA_DEG2", "FEAT_MEAN_INT");
$global['OPT_FIELDS_RS'] = array("FEAT_X_ARCSEC", "FEAT_Y_ARCSEC", "FEAT_AREA_MM2", "FEAT_MAX_INT");

?>
