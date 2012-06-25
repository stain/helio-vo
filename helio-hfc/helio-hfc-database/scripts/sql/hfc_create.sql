-- MySQL dump 10.13  Distrib 5.1.47, for apple-darwin10.3.0 (i386)
--
-- Host: voparis-mysql5-paris.obspm.fr    Database: hfc1
-- ------------------------------------------------------
-- Server version	5.0.95

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Not dumping tablespaces as no INFORMATION_SCHEMA.FILES table on this server
--

--
-- Table structure for table `ACTIVEREGIONS`
--

DROP TABLE IF EXISTS `ACTIVEREGIONS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACTIVEREGIONS` (
  `ID_AR` int(11) NOT NULL auto_increment,
  `FRC_INFO_ID` int(4) NOT NULL COMMENT 'Ref. to FR code information',
  `OBSERVATIONS_ID` int(11) NOT NULL COMMENT 'Pointing to observation',
  `OBSERVATIONS_ID_T` int(11) NOT NULL COMMENT 'Pointing to observation',
  `FEAT_DATE` datetime NOT NULL COMMENT 'Date of the AR (ie. date of the image used)',
  `FEAT_DATE_PREV` datetime NOT NULL COMMENT 'Date of the previous image used for the detection',
  `FEAT_X_ARCSEC` double NOT NULL COMMENT 'X Heliocentric coordinates of the AR gravity centre in arcsec',
  `FEAT_Y_ARCSEC` double NOT NULL COMMENT 'Y Heliocentric coordinates of the AR gravity centre in arcsec',
  `FEAT_X_PIX` int(8) default NULL COMMENT 'X image coordinates of the AR gravity centre in pixels\n',
  `FEAT_Y_PIX` int(8) default NULL COMMENT 'Y image coordinates of the AR gravity centre in pixels',
  `FEAT_HG_LONG_DEG` float NOT NULL COMMENT 'Heliographic longitude of the AR gravity centre in degrees',
  `FEAT_HG_LAT_DEG` float NOT NULL COMMENT 'Heliographic latitude of the AR gravity centre in degrees',
  `FEAT_CARR_LONG_DEG` float default NULL COMMENT 'Carrington longitude of the AR gravity centre in degrees',
  `FEAT_CARR_LAT_DEG` float default NULL COMMENT 'Carrington latitude of the AR gravity centre in degrees',
  `FEAT_AREA_PIX` int(11) NOT NULL COMMENT 'Number of pixels included in the feature',
  `FEAT_AREA_MM2` float default NULL COMMENT 'Area in Mm2 of the AR',
  `FEAT_AREA_DEG2` float NOT NULL COMMENT 'Area of the feature in square degrees',
  `BR_X0_ARCSEC` double NOT NULL,
  `BR_Y0_ARCSEC` double NOT NULL,
  `BR_X1_ARCSEC` double NOT NULL,
  `BR_Y1_ARCSEC` double NOT NULL,
  `BR_X2_ARCSEC` double NOT NULL,
  `BR_Y2_ARCSEC` double NOT NULL,
  `BR_X3_ARCSEC` double NOT NULL,
  `BR_Y3_ARCSEC` double NOT NULL,
  `BR_HG_LONG0_DEG` double NOT NULL,
  `BR_HG_LAT0_DEG` double NOT NULL,
  `BR_HG_LONG1_DEG` double NOT NULL,
  `BR_HG_LAT1_DEG` double NOT NULL,
  `BR_HG_LONG2_DEG` double NOT NULL,
  `BR_HG_LAT2_DEG` double NOT NULL,
  `BR_HG_LONG3_DEG` double NOT NULL,
  `BR_HG_LAT3_DEG` double NOT NULL,
  `BR_CARR_LONG0_DEG` double default NULL,
  `BR_CARR_LAT0_DEG` double default NULL,
  `BR_CARR_LONG1_DEG` double default NULL,
  `BR_CARR_LAT1_DEG` double default NULL,
  `BR_CARR_LONG2_DEG` double default NULL,
  `BR_CARR_LAT2_DEG` double default NULL,
  `BR_CARR_LONG3_DEG` double default NULL,
  `BR_CARR_LAT3_DEG` double default NULL,
  `BR_X0_PIX` double default NULL,
  `BR_Y0_PIX` double default NULL,
  `BR_X1_PIX` int(8) default NULL,
  `BR_Y1_PIX` int(8) default NULL,
  `BR_X2_PIX` int(8) default NULL,
  `BR_Y2_PIX` int(8) default NULL,
  `BR_X3_PIX` double default NULL,
  `BR_Y3_PIX` double default NULL,
  `FEAT_MAX_INT` float NOT NULL COMMENT 'AR max. intensity value, in units of the original observation',
  `FEAT_MIN_INT` float NOT NULL COMMENT 'AR min. intensity value, in units of the original observation',
  `FEAT_MEAN_INT` float NOT NULL COMMENT 'AR mean intensity value in the units of the original observation',
  `FEAT_MEAN2QSUN` double default NULL COMMENT 'Mean of the AR to QS instensity ratio',
  `FEAT_MAX_BZ` float default NULL COMMENT 'AR max. line-of-sight magnetic field value, in Gauss',
  `FEAT_MIN_BZ` float default NULL COMMENT 'AR min. line-of-sight magnetic field value, in Gauss',
  `FEAT_MEAN_BZ` float default NULL COMMENT 'AR mean line-of-sight magnetic field value in Gauss',
  `FEAT_LENGTH_NL` float NOT NULL COMMENT 'AR length of the neutral line in Mm',
  `FEAT_LENGTH_SG` float NOT NULL COMMENT 'AR length of the strong gradient of nl above 50G/Mm',
  `FEAT_MAX_GRAD` float NOT NULL COMMENT 'Maximum of the horizontal gradient polarity separation line (G/Mm)',
  `FEAT_MEAN_GRAD` float NOT NULL COMMENT 'Mean of the horizontal gradient polarity separation line (G/Mm)',
  `FEAT_MEDIAN_GRAD` float NOT NULL COMMENT 'Median of the horizontal gradient polarity separation line (G/Mm)',
  `FEAT_RVAL` float NOT NULL COMMENT 'R schrijver value (Mx): Flux near polarity separation line',
  `FEAT_WLSG` float NOT NULL COMMENT 'WLSG value (G/Mm): Gradient along the polarity separation',
  `CC_X_PIX` int(8) NOT NULL COMMENT 'X coordinate of chain code start position in pixels',
  `CC_Y_PIX` int(8) NOT NULL COMMENT 'Y coordinate of chain code start position in pixels',
  `CC_X_ARCSEC` double NOT NULL COMMENT 'X coordinate of chain code start position in arcsec',
  `CC_Y_ARCSEC` double NOT NULL COMMENT 'Y coordinate of chain code start position in arcsec',
  `CC` text NOT NULL COMMENT 'Boundary chain code',
  `CC_LENGTH` int(11) default NULL,
  `SNAPSHOT_FN` varchar(200) NOT NULL COMMENT 'snapshot of the AR in solarmonitor',
  `SNAPSHOT_PATH` varchar(200) NOT NULL COMMENT 'full URL of the snapshot',
  `FEAT_FILENAME` varchar(150) default NULL COMMENT 'Name of the data file used to fill the ACTIVEREGIONS table',
  `HELIO_AR_NUMBER` int(11) default NULL COMMENT 'HELIO AR number',
  `RUN_DATE` datetime NOT NULL COMMENT 'Date when FR code was run',
  PRIMARY KEY  (`ID_AR`),
  KEY `frc_info_fk_constraint2` (`FRC_INFO_ID`),
  KEY `new_index_ar_date` (`FEAT_DATE`),
  KEY `observations_fk_constraint2` (`OBSERVATIONS_ID`),
  KEY `observations_fk_constraint2_t` (`OBSERVATIONS_ID_T`),
  KEY `index_feat_area_deg2` (`FEAT_AREA_DEG2`),
  KEY `index_feat_max_int` (`FEAT_MAX_INT`),
  CONSTRAINT `frc_info_fk_constraint2` FOREIGN KEY (`FRC_INFO_ID`) REFERENCES `FRC_INFO` (`ID_FRC_INFO`),
  CONSTRAINT `observations_fk_constraint2` FOREIGN KEY (`OBSERVATIONS_ID`) REFERENCES `OBSERVATIONS` (`ID_OBSERVATIONS`),
  CONSTRAINT `observations_fk_constraint2_t` FOREIGN KEY (`OBSERVATIONS_ID_T`) REFERENCES `OBSERVATIONS` (`ID_OBSERVATIONS`)
) ENGINE=InnoDB AUTO_INCREMENT=640514 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ACTIVEREGIONS_TRACKING`
--

DROP TABLE IF EXISTS `ACTIVEREGIONS_TRACKING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACTIVEREGIONS_TRACKING` (
  `ID_AR_TRACK` int(11) NOT NULL auto_increment,
  `TRACK_ID` int(11) NOT NULL COMMENT 'Index of the feature during a rotation',
  `AR_ID` int(11) NOT NULL COMMENT 'Index of the detected active region in the corresponding table (Ref. to ACTIVEREGIONS ID_AR index)',
  `PHENOM` int(6) default NULL COMMENT 'Number referring to the behaviour of the feature',
  `REF_FEAT` int(11) default NULL COMMENT 'Feature on which the phenomen applies',
  `LVL_TRUST` int(6) default NULL COMMENT 'Percentage of confidence of the tracking',
  `TRACK_FILENAME` varchar(150) NOT NULL COMMENT 'Name of the output file produced by the tracking code',
  `FEAT_FILENAME` varchar(150) NOT NULL COMMENT 'Name of the output file produced by the fr code',
  `RUN_DATE` datetime NOT NULL COMMENT 'Date when tracking code was run',
  PRIMARY KEY  (`ID_AR_TRACK`),
  KEY `activeregions_fk_constraint` (`AR_ID`),
  CONSTRAINT `activeregions_fk_constraint` FOREIGN KEY (`AR_ID`) REFERENCES `ACTIVEREGIONS` (`ID_AR`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANNOTATIONS`
--

DROP TABLE IF EXISTS `ANNOTATIONS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANNOTATIONS` (
  `ID_ANNOTATION` int(11) NOT NULL auto_increment,
  `FIELD_NAME` varchar(100) NOT NULL COMMENT 'Name of the field',
  `TABLE_NAME` varchar(100) NOT NULL COMMENT 'Name of the table(s) where the field is present',
  `UCD` varchar(150) default NULL COMMENT 'UCD keyword(s) related to the field',
  `UTYPE` varchar(150) default NULL COMMENT 'UType keyword(s) related to the field',
  `DESCRIPTION` text COMMENT 'Description of the field',
  PRIMARY KEY  (`ID_ANNOTATION`),
  UNIQUE KEY `FIELD_NAME` (`FIELD_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=291 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CHGROUPS`
--

DROP TABLE IF EXISTS `CHGROUPS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CHGROUPS` (
  `ID_CHGROUPS` int(11) NOT NULL auto_increment,
  `CH_NUMS` varchar(150) NOT NULL COMMENT 'CHs belonging to group numbered as in image',
  `BR_X0_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in arcsec',
  `BR_Y0_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in arcsec',
  `BR_X1_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in arcsec',
  `BR_Y1_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in arcsec',
  `BR_X2_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in arcsec',
  `BR_Y2_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in arcsec',
  `BR_X3_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in arcsec',
  `BR_Y3_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in arcsec',
  `BR_X0_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in pixels',
  `BR_Y0_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in pixels',
  `BR_X1_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in pixels',
  `BR_Y1_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in pixels',
  `BR_X2_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in pixels',
  `BR_Y2_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in pixels',
  `BR_X3_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in pixels',
  `BR_Y3_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in pixels',
  `BR_HG_LONG0_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South East most point in degrees',
  `BR_HG_LAT0_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South East most point in degrees',
  `BR_HG_LONG1_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North East most point in degrees',
  `BR_HG_LAT1_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North East most point in degrees',
  `BR_HG_LONG2_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South West most point in degrees',
  `BR_HG_LAT2_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South West most point in degrees',
  `BR_HG_LONG3_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North West most point in degrees',
  `BR_HG_LAT3_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North West most point in degrees',
  `BR_CARR_LONG0_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South East most point in degrees',
  `BR_CARR_LAT0_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South East most point in degrees',
  `BR_CARR_LONG1_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North East most point in degrees',
  `BR_CARR_LAT1_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North East most point in degrees',
  `BR_CARR_LONG2_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South West most point in degrees',
  `BR_CARR_LAT2_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South West most point in degrees',
  `BR_CARR_LONG3_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North West most point in degrees',
  `BR_CARR_LAT3_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North West most point in degrees',
  `GROUP_X_ARCSEC` double NOT NULL COMMENT 'X Heliocentric coordinates of the CH group gravity centre in arcsec',
  `GROUP_Y_ARCSEC` double NOT NULL COMMENT 'Y Heliocentric coordinates of the CH group gravity centre in arcsec',
  `GROUP_X_PIX` int(8) NOT NULL COMMENT 'X image coordinates of the CH group gravity center in pixels',
  `GROUP_Y_PIX` int(8) NOT NULL COMMENT 'Y image coordinates of the CH group gravity center in pixels',
  `GROUP_HG_LONG_DEG` float NOT NULL COMMENT 'Heliographic longitude of the CH group Gravity centre in degrees',
  `GROUP_HG_LAT_DEG` float NOT NULL COMMENT 'Heliographic latitude of the CH group Gravity centre in degrees',
  `GROUP_CARR_LONG_DEG` float NOT NULL COMMENT 'Carrington longitude of the CH group gravity centre in degrees',
  `GROUP_CARR_LAT_DEG` float NOT NULL COMMENT 'Carrington latitude of the CH group gravity centre in degrees',
  `GROUP_WIDTH_X_ARCSEC` double NOT NULL COMMENT 'X width of the CH group in HC arcsec',
  `GROUP_WIDTH_Y_ARCSEC` double NOT NULL COMMENT 'Y width of the CH group in HC arcsec',
  `GROUP_WIDTH_X_PIX` int(8) NOT NULL COMMENT 'X width of the CH group in image pixels',
  `GROUP_WIDTH_Y_PIX` int(8) NOT NULL COMMENT 'Y width of the CH group in image pixels',
  `GROUP_WIDTH_HG_LONG_DEG` double NOT NULL COMMENT 'Longitude width of the CH group in HG degrees',
  `GROUP_WIDTH_HG_LAT_DEG` double NOT NULL COMMENT 'Latitude width of the CH group in HG degrees',
  `GROUP_WIDTH_CARR_LONG_DEG` double NOT NULL COMMENT 'Longitude width of the CH group in Carrington degrees',
  `GROUP_WIDTH_CARR_LAT_DEG` double NOT NULL COMMENT 'Latitude width of the CH group in Carrington degrees',
  `GROUP_AREA_PIX` int(11) NOT NULL COMMENT 'Number of pixels included in the feature group',
  `GROUP_AREA_MM2` float NOT NULL COMMENT 'Area in Mm2 of the feature group',
  `GROUP_AREA_DEG2` float NOT NULL COMMENT 'Area of the group in square degrees',
  `GROUP_MEAN_BZ` float NOT NULL COMMENT 'Feature mean line-of-sight magnetic field in Gauss',
  PRIMARY KEY  (`ID_CHGROUPS`)
) ENGINE=InnoDB AUTO_INCREMENT=6880 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CORONALHOLES`
--

DROP TABLE IF EXISTS `CORONALHOLES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CORONALHOLES` (
  `ID_CORONALHOLES` int(11) NOT NULL auto_increment,
  `CHGROUPS_ID` int(11) NOT NULL COMMENT 'Pointing to group number',
  `IMAGE_ID` int(11) NOT NULL COMMENT 'Number on the daily image',
  `IMAGE_GROUP_ID` int(11) NOT NULL COMMENT 'Group number on the daily image',
  `FRC_INFO_ID` int(11) NOT NULL COMMENT 'Ref. to FR code information',
  `OBSERVATIONS_ID_EIT` int(11) NOT NULL COMMENT 'Pointing to observation in EIT',
  `OBSERVATIONS_ID_MDI` int(11) NOT NULL COMMENT 'Pointing to observation in MDI',
  `FEAT_DATE` datetime NOT NULL COMMENT 'Date when CH was observed',
  `FEAT_THRESHOLD` double NOT NULL COMMENT 'Threshold value used on the image',
  `BR_X0_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in arcsec',
  `BR_Y0_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in arcsec',
  `BR_X1_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in arcsec',
  `BR_Y1_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in arcsec',
  `BR_X2_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in arcsec',
  `BR_Y2_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in arcsec',
  `BR_X3_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in arcsec',
  `BR_Y3_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in arcsec',
  `BR_X0_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in pixels',
  `BR_Y0_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in pixels',
  `BR_X1_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in pixels',
  `BR_Y1_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in pixels',
  `BR_X2_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in pixels',
  `BR_Y2_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in pixels',
  `BR_X3_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in pixels',
  `BR_Y3_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in pixels',
  `BR_HG_LONG0_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South East most point in degrees',
  `BR_HG_LAT0_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South East most point in degrees',
  `BR_HG_LONG1_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North East most point in degrees',
  `BR_HG_LAT1_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North East most point in degrees',
  `BR_HG_LONG2_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South West most point in degrees',
  `BR_HG_LAT2_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South West most point in degrees',
  `BR_HG_LONG3_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North West most point in degrees',
  `BR_HG_LAT3_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North West most point in degrees',
  `BR_CARR_LONG0_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South East most point in degrees',
  `BR_CARR_LAT0_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South East most point in degrees',
  `BR_CARR_LONG1_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North East most point in degrees',
  `BR_CARR_LAT1_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North East most point in degrees',
  `BR_CARR_LONG2_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South West most point in degrees',
  `BR_CARR_LAT2_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South West most point in degrees',
  `BR_CARR_LONG3_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North West most point in degrees',
  `BR_CARR_LAT3_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North West most point in degrees',
  `FEAT_X_ARCSEC` double NOT NULL COMMENT 'X Heliocentric coordinates of the CH gravity centre in arcsec',
  `FEAT_Y_ARCSEC` double NOT NULL COMMENT 'Y Heliocentric coordinates of the CH gravity centre in arcsec',
  `FEAT_X_PIX` int(8) NOT NULL COMMENT 'X image coordinates of the CH gravity centre in pixels',
  `FEAT_Y_PIX` int(8) NOT NULL COMMENT 'Y image coordinates of the CH gravity centre in pixels',
  `FEAT_HG_LONG_DEG` float NOT NULL COMMENT 'Heliographic longitude of the CH gravity centre in degrees',
  `FEAT_HG_LAT_DEG` float NOT NULL COMMENT 'Heliographic latitude of the CH gravity centre in degrees',
  `FEAT_CARR_LONG_DEG` float NOT NULL COMMENT 'Carrington longitude of the CH gravity centre in degrees',
  `FEAT_CARR_LAT_DEG` float NOT NULL COMMENT 'Carrington latitude of the CH gravity centre in degrees',
  `FEAT_WIDTH_X_ARCSEC` double NOT NULL COMMENT 'X width of the CH in HC arcsec',
  `FEAT_WIDTH_Y_ARCSEC` double NOT NULL COMMENT 'Y width of the CH in HC arcsec',
  `FEAT_WIDTH_X_PIX` int(8) NOT NULL COMMENT 'X width of the CH in image pixels',
  `FEAT_WIDTH_Y_PIX` int(8) NOT NULL COMMENT 'Y width of the CH in image pixels',
  `FEAT_WIDTH_HG_LONG_DEG` float NOT NULL COMMENT 'Longitude width of the CH in HG deg',
  `FEAT_WIDTH_HG_LAT_DEG` float NOT NULL COMMENT 'Latitude width of the CH in HG deg',
  `FEAT_WIDTH_CARR_LONG_DEG` float NOT NULL COMMENT 'Longitude width of the CH in Carrington deg',
  `FEAT_WIDTH_CARR_LAT_DEG` float NOT NULL COMMENT 'Latitude width of the CH in Carrington deg',
  `FEAT_AREA_PIX` int(11) NOT NULL COMMENT 'Number of pixels included in the CH',
  `FEAT_AREA_MM2` float NOT NULL COMMENT 'Area in Mm2 of the CH',
  `FEAT_AREA_DEG2` float NOT NULL COMMENT 'Area of the CH in square degrees',
  `FEAT_MIN_INT` float NOT NULL COMMENT 'CH min. value, in units of the original observation',
  `FEAT_MAX_INT` float NOT NULL COMMENT 'CH max. value, in units of the original observation',
  `FEAT_MEAN_INT` float NOT NULL COMMENT 'CH mean intensity value in the units of the original obs',
  `FEAT_MEAN2QSUN` double NOT NULL COMMENT 'Mean of the CH to QS instensity ratio',
  `FEAT_MIN_BZ` float NOT NULL COMMENT 'CH min. line-of-sight magnetic field in Gauss',
  `FEAT_MAX_BZ` float NOT NULL COMMENT 'CH max. line-of-sight magnetic field in Gauss',
  `FEAT_MEAN_BZ` float NOT NULL COMMENT 'CH mean line-of-sight magnetic field in Gauss',
  `FEAT_SKEW_BZ` double NOT NULL COMMENT 'CH skewness of the line-of-sight magnetic field in Gaus',
  `CC_X_PIX` int(8) NOT NULL COMMENT 'X coordinate of chain code start position in pixels',
  `CC_Y_PIX` int(8) NOT NULL COMMENT 'Y coordinate of chain code start position in pixels',
  `CC_X_ARCSEC` double NOT NULL COMMENT 'X coordinate of chain code start position in arcsec',
  `CC_Y_ARCSEC` double NOT NULL COMMENT 'Y coordinate of chain code start position in arcsec',
  `CC` text NOT NULL COMMENT 'Boundary chain code',
  `CC_LENGTH` int(11) NOT NULL,
  `SNAPSHOT_FN` varchar(200) NOT NULL COMMENT 'snapshot of the CH in solarmonitor',
  `SNAPSHOT_PATH` varchar(200) NOT NULL COMMENT 'URL of the snapshot path',
  `FEAT_FILENAME` varchar(150) default NULL COMMENT 'Name of the data file used to fill the CORONALHOLES table',
  `HELIO_CH_NUMBER` int(11) default NULL COMMENT 'HELIO CH number',
  `RUN_DATE` datetime NOT NULL COMMENT 'Date when FR code was run',
  PRIMARY KEY  (`ID_CORONALHOLES`),
  KEY `frc_info_fk_constraint_charm` (`FRC_INFO_ID`),
  KEY `chgroups_fk_constraint` (`CHGROUPS_ID`),
  KEY `index_obs_date` (`FEAT_DATE`),
  KEY `observations_eit_fk_constraint` (`OBSERVATIONS_ID_EIT`),
  KEY `observations_mdi_fk_constraint` (`OBSERVATIONS_ID_MDI`),
  CONSTRAINT `chgroups_fk_constraint` FOREIGN KEY (`CHGROUPS_ID`) REFERENCES `CHGROUPS` (`ID_CHGROUPS`),
  CONSTRAINT `frc_info_fk_constraint_charm` FOREIGN KEY (`FRC_INFO_ID`) REFERENCES `FRC_INFO` (`ID_FRC_INFO`),
  CONSTRAINT `observations_eit_fk_constraint` FOREIGN KEY (`OBSERVATIONS_ID_EIT`) REFERENCES `OBSERVATIONS` (`ID_OBSERVATIONS`),
  CONSTRAINT `observations_mdi_fk_constraint` FOREIGN KEY (`OBSERVATIONS_ID_MDI`) REFERENCES `OBSERVATIONS` (`ID_OBSERVATIONS`)
) ENGINE=InnoDB AUTO_INCREMENT=11145 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CORONALHOLES_TRACKING`
--

DROP TABLE IF EXISTS `CORONALHOLES_TRACKING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CORONALHOLES_TRACKING` (
  `ID_CH_TRACK` int(11) NOT NULL auto_increment,
  `TRACK_ID` int(11) NOT NULL COMMENT 'Index of the feature during a rotation',
  `CH_ID` int(11) NOT NULL COMMENT 'Index of the detected coronal hole in the corresponding table (Ref. to CORONALHOLES ID_CORONALHOLES index)',
  `PHENOM` int(6) default NULL COMMENT 'Number referring to the behaviour of the feature',
  `REF_FEAT` int(11) default NULL COMMENT 'Feature on which the phenomen applies',
  `LVL_TRUST` int(6) default NULL COMMENT 'Percentage of confidence of the tracking',
  `TRACK_FILENAME` varchar(150) NOT NULL COMMENT 'Name of the output file produced by the tracking code',
  `FEAT_FILENAME` varchar(150) NOT NULL COMMENT 'Name of the output file produced by the fr code',
  `RUN_DATE` datetime NOT NULL COMMENT 'Date when tracking code was run',
  PRIMARY KEY  (`ID_CH_TRACK`),
  KEY `coronalholes_fk_constraint` (`CH_ID`),
  CONSTRAINT `coronalholes_fk_constraint` FOREIGN KEY (`CH_ID`) REFERENCES `CORONALHOLES` (`ID_CORONALHOLES`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FILAMENTS`
--

DROP TABLE IF EXISTS `FILAMENTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FILAMENTS` (
  `ID_FIL` int(11) NOT NULL auto_increment,
  `FRC_INFO_ID` int(4) NOT NULL COMMENT 'Ref. to FR code information',
  `PP_OUTPUT_ID` int(11) NOT NULL COMMENT 'Ref. to processed observation where detection was made',
  `FEAT_X_ARCSEC` double NOT NULL COMMENT 'X Heliocentric coordinates of the filament skeleton gravity centre in arcsec',
  `FEAT_Y_ARCSEC` double NOT NULL COMMENT 'Y Heliocentric coordinates of the filament skeleton gravity centre in arcsec',
  `FEAT_X_PIX` int(8) NOT NULL COMMENT 'X image coordinates of the filament skeleton gravity centre in pixels',
  `FEAT_Y_PIX` int(8) NOT NULL COMMENT 'Y image coordinates of the filament skeleton gravity centre in pixels',
  `FEAT_HG_LONG_DEG` float NOT NULL COMMENT 'Heliographic longitude of the filament skeleton gravity centre in degrees',
  `FEAT_HG_LAT_DEG` float NOT NULL COMMENT 'Heliographic latitude of the filament skeleton gravity centre in degrees',
  `FEAT_CARR_LONG_DEG` float NOT NULL COMMENT 'Carrington longitude of the filament skeleton gravity centre in degrees',
  `FEAT_CARR_LAT_DEG` float NOT NULL COMMENT 'Carrington latitude of the filament skeleton gravity centre in degrees',
  `BR_X0_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in arcsec',
  `BR_Y0_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in arcsec',
  `BR_X1_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in arcsec',
  `BR_Y1_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in arcsec',
  `BR_X2_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in arcsec',
  `BR_Y2_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in arcsec',
  `BR_X3_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in arcsec',
  `BR_Y3_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in arcsec',
  `BR_X0_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle image coordinate South East most point in pixels',
  `BR_Y0_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle image coordinate South East most point in pixels',
  `BR_X1_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle image coordinate North East most point in pixels',
  `BR_Y1_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle image coordinate North East most point in pixels',
  `BR_X2_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle image coordinate South West most point in pixels',
  `BR_Y2_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle image coordinate South West most point in pixels',
  `BR_X3_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle image coordinate North West most point in pixels',
  `BR_Y3_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle image coordinate North West most point in pixels',
  `BR_HG_LONG0_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South East most point in degrees',
  `BR_HG_LAT0_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South East most point in degrees',
  `BR_HG_LONG1_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North East most point in degrees',
  `BR_HG_LAT1_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North East most point in degrees',
  `BR_HG_LONG2_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South West most point in degrees',
  `BR_HG_LAT2_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South West most point in degrees',
  `BR_HG_LONG3_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North West most point in degrees',
  `BR_HG_LAT3_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North West most point in degrees',
  `BR_CARR_LONG0_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South East most point in degrees',
  `BR_CARR_LAT0_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South East most point in degrees',
  `BR_CARR_LONG1_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North East most point in degrees',
  `BR_CARR_LAT1_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North East most point in degrees',
  `BR_CARR_LONG2_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South West most point in degrees',
  `BR_CARR_LAT2_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South West most point in degrees',
  `BR_CARR_LONG3_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North West most point in degrees',
  `BR_CARR_LAT3_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North West most point in degrees',
  `FEAT_AREA_PIX` int(11) NOT NULL COMMENT 'Number of pixels included in the Filament',
  `FEAT_AREA_MM2` float NOT NULL COMMENT 'Area in Mm2 of the Filament',
  `FEAT_AREA_DEG2` float NOT NULL COMMENT 'Area of the Filament in square degrees',
  `FEAT_MEAN2QSUN` double NOT NULL COMMENT 'Mean of the Filament to QS instensity ratio',
  `FEAT_MAX_INT` float NOT NULL COMMENT 'Filament max. intensity value, in units of the original observation',
  `FEAT_MIN_INT` float NOT NULL COMMENT 'Filament min. intensity value, in units of the original observation',
  `FEAT_MEAN_INT` float NOT NULL COMMENT 'Filament mean intensity value in the units of the original obs.',
  `CC_X_PIX` int(8) NOT NULL COMMENT 'X coordinate of chain code start position in pixels',
  `CC_Y_PIX` int(8) NOT NULL COMMENT 'Y coordinate of chain code start position in pixels',
  `CC_X_ARCSEC` double NOT NULL COMMENT 'X coordinate of chain code start position in arcsec',
  `CC_Y_ARCSEC` double NOT NULL COMMENT 'Y coordinate of chain code start position in arcsec',
  `SKE_LENGTH_DEG` float NOT NULL COMMENT 'Length of the filament skeleton in degrees',
  `SKE_CURVATURE` float NOT NULL COMMENT 'Index of curvature of the skeleton',
  `FEAT_ELONG` float NOT NULL COMMENT 'Elongation factor',
  `SKE_ORIENTATION` float NOT NULL COMMENT 'Orientation of the filament skeleton',
  `SKE_CC_X_PIX` int(8) NOT NULL COMMENT 'X coordinate of skeleton chain code start in pixels',
  `SKE_CC_Y_PIX` int(8) NOT NULL COMMENT 'Y coordinate of skeleton chain code start in pixels',
  `SKE_CC_X_ARCSEC` double NOT NULL COMMENT 'X coordinate of skeleton chain code start in arcsec',
  `SKE_CC_Y_ARCSEC` double NOT NULL COMMENT 'Y coordinate of skeleton chain code start in arcsec',
  `CC` text NOT NULL COMMENT 'Boundary chain code',
  `SKE_CC` text NOT NULL COMMENT 'Skeleton chain code',
  `CC_LENGTH` int(11) NOT NULL COMMENT 'Chain code length',
  `SKE_CC_LENGTH` int(11) NOT NULL COMMENT 'Skeleton chain code length',
  `SNAPSHOT_FN` varchar(200) default NULL COMMENT 'snapshot of the filament',
  `SNAPSHOT_PATH` varchar(200) default NULL COMMENT 'URL of the snapshot path',
  `PR_LOCFNAME` varchar(150) NOT NULL COMMENT 'Name of the pre processed image used to perform detection.',
  `FEAT_FILENAME` varchar(150) default NULL COMMENT 'Name of the data file used to fill the FILAMENTS table',
  `HELIO_FI_NUMBER` int(11) default NULL COMMENT 'HELIO filament number',
  `RUN_DATE` datetime NOT NULL COMMENT 'Date when FR code was run',
  PRIMARY KEY  (`ID_FIL`),
  KEY `frc_info_fk_constraint_fil` (`FRC_INFO_ID`),
  KEY `pp_output_fk_constraint_fil` (`PP_OUTPUT_ID`),
  CONSTRAINT `frc_info_fk_constraint_fil` FOREIGN KEY (`FRC_INFO_ID`) REFERENCES `FRC_INFO` (`ID_FRC_INFO`),
  CONSTRAINT `pp_output_fk_constraint_fil` FOREIGN KEY (`PP_OUTPUT_ID`) REFERENCES `PP_OUTPUT` (`ID_PP_OUTPUT`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=89049 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FILAMENTS_TRACKING`
--

DROP TABLE IF EXISTS `FILAMENTS_TRACKING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FILAMENTS_TRACKING` (
  `ID_FIL_TRACK` int(11) NOT NULL auto_increment,
  `TRACK_ID` int(11) NOT NULL COMMENT 'Index of the feature during a rotation',
  `FIL_ID` int(11) NOT NULL COMMENT 'Index of the detected Filament in the corresponding table (Ref. to FILAMENTS ID_FIL index)',
  `PHENOM` int(6) default NULL COMMENT 'Number referring to the behaviour of the feature',
  `REF_FEAT` int(11) default NULL COMMENT 'Feature on which the phenomen applies',
  `LVL_TRUST` int(6) default NULL COMMENT 'Percentage of confidence of the tracking',
  `TRACK_FILENAME` varchar(150) NOT NULL COMMENT 'Name of the output file produced by the tracking code',
  `FEAT_FILENAME` varchar(150) NOT NULL COMMENT 'Name of the output file produced by the fr code',
  `RUN_DATE` datetime NOT NULL COMMENT 'Date when tracking code was run',
  PRIMARY KEY  (`ID_FIL_TRACK`),
  KEY `filaments_fk_constraint` (`FIL_ID`),
  CONSTRAINT `filaments_fk_constraint` FOREIGN KEY (`FIL_ID`) REFERENCES `FILAMENTS` (`ID_FIL`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=104905 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FRC_INFO`
--

DROP TABLE IF EXISTS `FRC_INFO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FRC_INFO` (
  `ID_FRC_INFO` int(11) NOT NULL auto_increment,
  `INSTITUT` varchar(150) NOT NULL COMMENT 'Institute responsible for running the FR code',
  `CODE` varchar(100) NOT NULL COMMENT 'Name of the FR code',
  `VERSION` varchar(50) NOT NULL COMMENT 'Version of the FR code',
  `FEATURE_NAME` varchar(100) NOT NULL COMMENT 'Features detected',
  `ENC_MET` varchar(50) default NULL COMMENT 'Encoding method (raster, chain code, none...)',
  `PERSON` varchar(150) default NULL,
  `CONTACT` varchar(150) NOT NULL COMMENT 'Person responsible for running the FR code',
  `REFERENCE` varchar(150) default NULL COMMENT 'Any reference to a document or article that describes the fr code',
  PRIMARY KEY  (`ID_FRC_INFO`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NOAA`
--

DROP TABLE IF EXISTS `NOAA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NOAA` (
  `ID_NOAA` int(11) NOT NULL auto_increment,
  `NOAA_NUMBER` int(11) NOT NULL,
  `DATE_OBS` datetime NOT NULL COMMENT 'Date of observation of the NOAA Region Number',
  `NOAA_HG_LONG_DEG` float default NULL COMMENT 'Heliographic longitude of the NOAA Region gravity centre in degrees',
  `NOAA_HG_LAT_DEG` float default NULL COMMENT 'Heliographic latitude of the NOAA Region gravity centre in degrees',
  `NOAA_CARR_LONG_DEG` float default NULL COMMENT 'Carrington longitude of the NOAA Region gravity centre in degrees',
  `FILENAME` varchar(150) default NULL COMMENT 'Name of the data file used to fill the ASSOC_AR_NOAA table',
  PRIMARY KEY  (`ID_NOAA`)
) ENGINE=InnoDB AUTO_INCREMENT=5002 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NOAA_AR`
--

DROP TABLE IF EXISTS `NOAA_AR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NOAA_AR` (
  `NOAA_ID` int(11) NOT NULL COMMENT 'ID of a NOAA region in table NOAA',
  `AR_ID` int(11) NOT NULL COMMENT 'ID of an active region in table ACTIVEREGIONS',
  KEY `NOAA_ID_fk_noaa_ar` (`NOAA_ID`),
  KEY `AR_ID_fk_noaa_ar` (`AR_ID`),
  CONSTRAINT `AR_ID_fk_noaa_ar` FOREIGN KEY (`AR_ID`) REFERENCES `ACTIVEREGIONS` (`ID_AR`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `NOAA_ID_fk_noaa_ar` FOREIGN KEY (`NOAA_ID`) REFERENCES `NOAA` (`ID_NOAA`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NOAA_SP`
--

DROP TABLE IF EXISTS `NOAA_SP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NOAA_SP` (
  `NOAA_ID` int(11) NOT NULL COMMENT 'ID of a NOAA region in table NOAA',
  `SUNSPOT_ID` int(11) NOT NULL COMMENT 'ID of a sunspot in table SUNSPOTS',
  KEY `NOAA_ID_fk_noaa_sp` (`NOAA_ID`),
  KEY `SUNSPOT_ID_fk_noaa_sp` (`SUNSPOT_ID`),
  CONSTRAINT `NOAA_ID_fk_noaa_sp` FOREIGN KEY (`NOAA_ID`) REFERENCES `NOAA` (`ID_NOAA`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `SUNSPOT_ID_fk_noaa_sp` FOREIGN KEY (`SUNSPOT_ID`) REFERENCES `SUNSPOTS` (`ID_SUNSPOT`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `OBSERVATIONS`
--

DROP TABLE IF EXISTS `OBSERVATIONS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OBSERVATIONS` (
  `ID_OBSERVATIONS` int(11) NOT NULL auto_increment,
  `OBSERVATORY_ID` int(11) NOT NULL COMMENT 'Ref. to OBSERVATORY information',
  `DATE_OBS` datetime NOT NULL COMMENT 'Start date of the observation',
  `DATE_END` datetime NOT NULL COMMENT 'End date of the observation',
  `JDINT` int(11) default NULL COMMENT 'Julian day of the observation, integer part',
  `JDFRAC` double default NULL COMMENT 'Julian day of the observation, fraction part',
  `EXP_TIME` float default NULL COMMENT 'Exposure time (if available), in seconds and fraction of s',
  `C_ROTATION` int(7) default NULL COMMENT 'Carrington rotation',
  `BSCALE` double default NULL COMMENT 'as extracted from the header',
  `BZERO` double default NULL COMMENT 'As extracted from the header',
  `BITPIX` int(3) default NULL COMMENT 'Coding of the original image',
  `NAXIS1` int(8) default NULL COMMENT 'First dimension of the original image (X)',
  `NAXIS2` int(8) default NULL COMMENT 'Second dimension of the original image (Y)',
  `R_SUN` double default NULL COMMENT 'Radius of the Sun in pixels',
  `CENTER_X` double default NULL COMMENT 'X coordinate of Sun centre in pixels',
  `CENTER_Y` double default NULL COMMENT 'Y coordinate of Sun centre in pixels',
  `CDELT1` double default NULL COMMENT 'Spatial scale of the original observation (X axis) (in arsec)',
  `CDELT2` double default NULL COMMENT 'Spatial scale of the original observation (Y axis) (in arsec)',
  `QUALITY` varchar(20) default NULL COMMENT 'Quality of the original image (in terms of processing)',
  `FILENAME` varchar(100) NOT NULL COMMENT 'Name of the original file',
  `FILE_FORMAT` varchar(20) default NULL,
  `COMMENT` text COMMENT 'As extracted from the header',
  `LOC_FILENAME` varchar(200) NOT NULL COMMENT 'Name of the original file on local disk (including full path)',
  `URL` text COMMENT 'URL of the original file',
  `QCLK_URL` text,
  `QCLK_FNAME` varchar(200) default NULL,
  PRIMARY KEY  (`ID_OBSERVATIONS`),
  KEY `new_fk_constraint` (`OBSERVATORY_ID`),
  KEY `new_index_loc_filename` (`LOC_FILENAME`),
  KEY `index_date_obs` (`DATE_OBS`),
  CONSTRAINT `new_fk_constraint` FOREIGN KEY (`OBSERVATORY_ID`) REFERENCES `OBSERVATORY` (`ID_OBSERVATORY`)
) ENGINE=InnoDB AUTO_INCREMENT=248014 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `OBSERVATORY`
--

DROP TABLE IF EXISTS `OBSERVATORY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OBSERVATORY` (
  `ID_OBSERVATORY` int(11) NOT NULL auto_increment,
  `OBSERVAT` varchar(255) NOT NULL COMMENT 'Name of the observatory/spacecraft',
  `INSTRUME` varchar(150) NOT NULL COMMENT 'Name of the instrument',
  `TELESCOP` varchar(150) default NULL COMMENT 'Name of the sub-part (telescope/receiver)',
  `UNITS` varchar(100) NOT NULL COMMENT 'Intensity units on the original observation',
  `WAVEMIN` float NOT NULL COMMENT 'Minimum wavelength (or frequency) of the observation',
  `WAVEMAX` float default NULL COMMENT 'Maximum wavelength (or frequency) of the observation',
  `WAVENAME` varchar(50) default NULL COMMENT 'Name of the wavelength of the observation',
  `WAVEUNIT` varchar(10) default NULL COMMENT 'Units of the wavelength (or frequency) of the observation',
  `SPECTRAL_NAME` varchar(100) default NULL COMMENT 'Spectral domain of the observation wavelength',
  `OBS_TYPE` varchar(100) default NULL COMMENT 'remote-sensing, in-situ, or both',
  `COMMENT` text,
  PRIMARY KEY  (`ID_OBSERVATORY`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PP_INFO`
--

DROP TABLE IF EXISTS `PP_INFO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PP_INFO` (
  `ID_PP_INFO` int(11) NOT NULL auto_increment,
  `INSTITUT` varchar(150) NOT NULL COMMENT 'Institut responsible for running the cleaning code',
  `CODE` varchar(150) NOT NULL COMMENT 'Name of the cleaning code',
  `VERSION` varchar(50) NOT NULL COMMENT 'Version of the cleaning code',
  `PERSON` varchar(150) default NULL,
  `CONTACT` varchar(150) NOT NULL COMMENT 'Person responsible for running cleaning code',
  `REFERENCE` varchar(150) default NULL COMMENT 'Any document or article that describes the fr code',
  PRIMARY KEY  (`ID_PP_INFO`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PP_OUTPUT`
--

DROP TABLE IF EXISTS `PP_OUTPUT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PP_OUTPUT` (
  `ID_PP_OUTPUT` int(11) NOT NULL auto_increment,
  `PP_INFO_ID` int(6) NOT NULL COMMENT 'Pointing to information about pre_processing code, version, institute',
  `OBSERVATIONS_ID` int(11) NOT NULL COMMENT 'Pointing to observation',
  `RUN_DATE` datetime NOT NULL COMMENT 'Date where the PP code was run',
  `PR_LOCFNAME` varchar(150) NOT NULL COMMENT 'Name of the local pre-processed file',
  `ORG_FNAME` varchar(150) NOT NULL COMMENT 'Name of the original file',
  `LOC_FILE` varchar(100) NOT NULL COMMENT 'Name of the pre_processed file, includng the path from local organization',
  `EL_CEN_X` double NOT NULL default '0' COMMENT 'X coordinate of ellipse centre in pixels (0 if no ellipse fitting used)',
  `EL_CEN_Y` double NOT NULL default '0' COMMENT 'Y coordinate of ellipse centre in pixels',
  `EL_AXIS1` double NOT NULL default '0' COMMENT 'Ellipse long axis (in pixels)',
  `EL_AXIS2` double NOT NULL default '0' COMMENT 'Ellipse short axis in pixels',
  `EL_ANGLE` double NOT NULL default '0' COMMENT 'Ellipse angle (degrees)',
  `STDEV` double default '0' COMMENT 'Standard deviation in pixels',
  `STDEVGEO` double default '0' COMMENT 'Standard deviation geometric in pixels',
  `ALGERR` double default '0' COMMENT 'Algebraic error in pixels',
  `CDELT1` double NOT NULL COMMENT 'Spatial scale of the pre-processed observation (X axis) in arcsec',
  `CDELT2` double NOT NULL COMMENT 'Spatial scale of the pre-processed observation (Y axis) in arcsec',
  `BITPIX` int(3) default NULL COMMENT 'Coding of the pre-processed image',
  `QSUN_INT` float NOT NULL COMMENT 'Quiet Sun value estimated after pre-processing',
  `EFIT` tinyint(1) NOT NULL default '0' COMMENT 'Has ellipse fitting been used yes(1) or no(0)',
  `STANDARD` tinyint(1) NOT NULL default '0' COMMENT 'Has standardization been used yes(1) or no(0)',
  `LIMBDARK` tinyint(1) NOT NULL default '0' COMMENT 'Has limb darkening removal been used yes(1) or no(0)',
  `BACKGROUND` tinyint(1) NOT NULL default '0' COMMENT 'Has background cleaning been used yes(1) or no(0)',
  `LINECLEAN` tinyint(1) NOT NULL default '0' COMMENT 'Has line cleaning been used yes(1) or no(0)',
  `LINEC_MAIND` float default NULL COMMENT 'main direction of line cleaning in degrees',
  `PERCENT` float default '0.5' COMMENT 'Used in ellipse fitting',
  `NAXIS1` int(8) NOT NULL COMMENT 'First dimension of the pre-processed image (X)',
  `NAXIS2` int(8) NOT NULL COMMENT 'Second dimension of the pre-processed image (Y)',
  `CENTER_X` double NOT NULL COMMENT 'X coordinate of Sun center in pixels',
  `CENTER_Y` double NOT NULL COMMENT 'Y coordinate of Sun center in pixels',
  `R_SUN` double default '0' COMMENT 'Radius of th Sun in pixels',
  `DIVISION` tinyint(1) NOT NULL default '0' COMMENT 'Method used to normalise image: division(1), substraction(0)',
  `INORM` float default NULL COMMENT 'Normalizing parameter for division method',
  `URL` text COMMENT 'URL of the pre-processed file',
  PRIMARY KEY  (`ID_PP_OUTPUT`),
  UNIQUE KEY `PR_LOCFNAME` (`PR_LOCFNAME`),
  KEY `pp_info_fk_constraint_pp` (`PP_INFO_ID`),
  KEY `observations_fk_constraint_pp` (`OBSERVATIONS_ID`),
  CONSTRAINT `observations_fk_constraint_pp` FOREIGN KEY (`OBSERVATIONS_ID`) REFERENCES `OBSERVATIONS` (`ID_OBSERVATIONS`) ON DELETE CASCADE,
  CONSTRAINT `pp_info_fk_constraint_pp` FOREIGN KEY (`PP_INFO_ID`) REFERENCES `PP_INFO` (`ID_PP_INFO`)
) ENGINE=InnoDB AUTO_INCREMENT=15134 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PROMINENCES`
--

DROP TABLE IF EXISTS `PROMINENCES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PROMINENCES` (
  `ID_PROMINENCE` int(11) NOT NULL auto_increment,
  `FRC_INFO_ID` int(4) NOT NULL COMMENT 'Ref. to FR code information',
  `PP_OUTPUT_ID` int(11) NOT NULL COMMENT 'Ref. to processed observation where detection was made',
  `BASE_MID_X_ARCSEC` double NOT NULL COMMENT 'X Heliocentric coordinate of the point on the sun limb corresponding to the middle of the base of the prominence in arcsec',
  `BASE_MID_Y_ARCSEC` double NOT NULL COMMENT 'Y Heliocentric coordinate of the point on the sun limb corresponding to the middle of the base of the prominence in arcsec',
  `BASE_MID_X_PIX` int(8) NOT NULL COMMENT 'X image coordinate of the point ont the sun limb corresponding to the middle of the base of the prominence in pixels',
  `BASE_MID_Y_PIX` int(8) NOT NULL COMMENT 'Y image coordinate of the point ont the sun limb corresponding to the middle of the base of the prominence in pixels',
  `BASE_MID_HG_LONG_DEG` float NOT NULL COMMENT 'Heliographic longitude of the point ont the sun limb corresponding to the middle of the base of the prominence in degrees',
  `BASE_MID_HG_LAT_DEG` float NOT NULL COMMENT 'Heliographic latitude of the point ont the sun limb corresponding to the middle of the base of the prominence in degrees',
  `BASE_MID_CARR_LONG_DEG` float NOT NULL COMMENT 'Carrington longitude of the point ont the sun limb corresponding to the middle of the base of the prominence in degrees',
  `BASE_MID_CARR_LAT_DEG` float NOT NULL COMMENT 'Carrington latitude of the point ont the sun limb corresponding to the middle of the base of the prominence in degrees',
  `BR_X0_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in arcsec',
  `BR_Y0_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in arcsec',
  `BR_X1_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in arcsec',
  `BR_Y1_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in arcsec',
  `BR_X2_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in arcsec',
  `BR_Y2_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in arcsec',
  `BR_X3_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in arcsec',
  `BR_Y3_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in arcsec',
  `BR_X0_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in pixels',
  `BR_Y0_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in pixels',
  `BR_X1_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in pixels',
  `BR_Y1_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in pixels',
  `BR_X2_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in pixels',
  `BR_Y2_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in pixels',
  `BR_X3_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in pixels',
  `BR_Y3_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in pixels',
  `FEAT_AREA_PIX` int(11) NOT NULL COMMENT 'Number of pixels included in the prominence',
  `FEAT_AREA_ARCSEC2` float NOT NULL COMMENT 'Area of the prominence in square degrees',
  `MEAN_INT_RATIO` float NOT NULL COMMENT 'TBC',
  `FEAT_MAX_INT` float NOT NULL COMMENT 'Prominence max. intensity value, in units of the original observation',
  `FEAT_MIN_INT` float NOT NULL COMMENT 'Prominence min. intensity value, in units of the original observation',
  `FEAT_MEAN_INT` float NOT NULL COMMENT 'Prominence mean intensity value in the units of the original observation',
  `DELTA_LAT_DEG` float NOT NULL COMMENT 'Latitudinal extension in degrees',
  `FEAT_HEIGHT_ARCSEC` double NOT NULL COMMENT 'Height of the prominence in arcsec',
  `FEAT_HEIGHT_KM` double NOT NULL COMMENT 'Height of the prominence in km',
  `BLOB_COUNT` int(8) NOT NULL COMMENT 'Number of blobs in the prominence (separate detections belonging to the same structure)',
  `BLOB_SEPARATOR` varchar(1) NOT NULL COMMENT 'B letter to separate the different blobs in a given field (eg chain code)',
  `CC_X_PIX` text NOT NULL COMMENT 'X coordinate of chain code start position in pixels',
  `CC_Y_PIX` text NOT NULL COMMENT 'Y coordinate of chain code start position in pixels',
  `CC_X_ARCSEC` text NOT NULL COMMENT 'X coordinate of chain code start position in arcsec',
  `CC_Y_ARCSEC` text NOT NULL COMMENT 'Y coordinate of chain code start position in arcsec',
  `CC` text NOT NULL COMMENT 'Boundary chain code',
  `CC_LENGTH` text NOT NULL COMMENT 'Chain code length',
  `N_LEVEL` int(8) NOT NULL COMMENT 'Number of intensity levels in the raster scan',
  `LEVEL_SEPARATOR` varchar(1) NOT NULL COMMENT 'L letter to separate levels in the raster scan',
  `RS` text NOT NULL,
  `RS_LENGTH` int(11) NOT NULL,
  `LEV_MIN_INT` text NOT NULL COMMENT 'Min intensity of the feature by level (eg 1021L1155L1433 min intensity of level 0 is 1021)',
  `LEV_MAX_INT` text NOT NULL COMMENT 'Max intensity of the feature by level (eg 995L1101L1299 max intensity of highest level is 1299)',
  `LEV_MEAN_INT` text NOT NULL COMMENT 'Mean intensity of the feature by level ',
  `SNAPSHOT_FN` varchar(200) default NULL COMMENT 'Snapshot of the prominence',
  `SNAPSHOT_PATH` varchar(200) default NULL COMMENT 'URL of the snapshot path',
  `PR_LOCFNAME` varchar(150) NOT NULL COMMENT 'Name of the pre processed image used to perform detection.',
  `FEAT_FILENAME` varchar(150) default NULL COMMENT 'Name of the data file used to fill the PROMINENCES table',
  `HELIO_PRO_NUMBER` int(11) default NULL COMMENT 'HELIO prominence number',
  `RUN_DATE` datetime NOT NULL COMMENT 'Date when FR code was run',
  PRIMARY KEY  (`ID_PROMINENCE`),
  KEY `frc_info_fk_constraint_pro` (`FRC_INFO_ID`),
  KEY `pp_output_fk_constraint_pro` (`PP_OUTPUT_ID`),
  CONSTRAINT `frc_info_fk_constraint_pro` FOREIGN KEY (`FRC_INFO_ID`) REFERENCES `FRC_INFO` (`ID_FRC_INFO`),
  CONSTRAINT `pp_output_fk_constraint_pro` FOREIGN KEY (`PP_OUTPUT_ID`) REFERENCES `PP_OUTPUT` (`ID_PP_OUTPUT`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39154 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `RADIOSOURCES`
--

DROP TABLE IF EXISTS `RADIOSOURCES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RADIOSOURCES` (
  `ID_RS` int(11) NOT NULL auto_increment,
  `FRC_INFO_ID` int(4) NOT NULL COMMENT 'Ref. to FR code information',
  `OBSERVATIONS_ID` int(11) NOT NULL COMMENT 'Pointing to observation',
  `FEAT_X_PIX` int(8) NOT NULL COMMENT 'X image coordinates of the RS gravity centre in pixels',
  `FEAT_Y_PIX` int(8) NOT NULL COMMENT 'Y image coordinates of the RS gravity centre in pixels',
  `FEAT_X_ARCSEC` double NOT NULL COMMENT 'X Heliocentric coordinates of the RS gravity centre in arcsec',
  `FEAT_Y_ARCSEC` double NOT NULL COMMENT 'Y Heliocentric coordinates of the RS gravity centre in arcsec',
  `FEAT_HG_LONG_DEG` float NOT NULL COMMENT 'Heliographic longitude of the RS gravity centre in degrees',
  `FEAT_HG_LAT_DEG` float NOT NULL COMMENT 'Heliographic latitude of the RS gravity centre in degrees',
  `FEAT_CARR_LONG_DEG` float NOT NULL COMMENT 'Carrington longitude of the RS gravity centre in degrees',
  `FEAT_CARR_LAT_DEG` float NOT NULL COMMENT 'Carrington latitude of the RS gravity centre in degrees',
  `BR_X0_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in pixels',
  `BR_Y0_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in pixels',
  `BR_X1_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in pixels',
  `BR_Y1_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in pixels',
  `BR_X2_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in pixels',
  `BR_Y2_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in pixels',
  `BR_X3_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in pixels',
  `BR_Y3_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in pixels',
  `BR_X0_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in arcsec',
  `BR_Y0_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in arcsec',
  `BR_X1_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in arcsec',
  `BR_Y1_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in arcsec',
  `BR_X2_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in arcsec',
  `BR_Y2_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in arcsec',
  `BR_X3_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in arcsec',
  `BR_Y3_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in arcsec',
  `BR_HG_LONG0_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South East most point in degrees',
  `BR_HG_LAT0_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South East most point in degrees',
  `BR_HG_LONG1_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North East most point in degrees',
  `BR_HG_LAT1_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North East most point in degrees',
  `BR_HG_LONG2_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South West most point in degrees',
  `BR_HG_LAT2_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South West most point in degrees',
  `BR_HG_LONG3_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North West most point in degrees',
  `BR_HG_LAT3_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North West most point in degrees',
  `BR_CARR_LONG0_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South East most point in degrees',
  `BR_CARR_LAT0_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South East most point in degrees',
  `BR_CARR_LONG1_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North East most point in degrees',
  `BR_CARR_LAT1_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North East most point in degrees',
  `BR_CARR_LONG2_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South West most point in degrees',
  `BR_CARR_LAT2_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South West most point in degrees',
  `BR_CARR_LONG3_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North West most point in degrees',
  `BR_CARR_LAT3_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North West most point in degrees',
  `FEAT_MAX_INT` float NOT NULL COMMENT 'RS max. intensity value in flux units',
  `FEAT_MEAN_INT` float NOT NULL COMMENT 'RS mean intensity value in flux units',
  `FEAT_AREA_PIX` int(11) NOT NULL COMMENT 'Area of the RS in pixels number',
  `FEAT_AREA_DEG2` float NOT NULL COMMENT 'Area of the RS in square degrees',
  `FEAT_AREA_MM2` float NOT NULL COMMENT 'Area of the RS in square megameter',
  `EL_AXIS1` float NOT NULL COMMENT 'Ellipse long axis in pixels',
  `EL_AXIS2` float NOT NULL COMMENT 'Ellipse short axis in pixels',
  `EL_ANGLE` float NOT NULL COMMENT 'Ellipse direction angle in degrees',
  `CC_X_PIX` int(8) NOT NULL COMMENT 'X coordinate of chain code start position in pixels',
  `CC_Y_PIX` int(8) NOT NULL COMMENT 'Y coordinate of chain code start position in pixels',
  `CC_X_ARCSEC` double NOT NULL COMMENT 'X coordinate of chain code start position in arcsec',
  `CC_Y_ARCSEC` double NOT NULL COMMENT 'Y coordinate of chain code start position in arcsec',
  `CC` text NOT NULL COMMENT 'Boundary chain code',
  `CC_LENGTH` int(11) NOT NULL COMMENT 'Chain code length',
  `SNAPSHOT_FN` varchar(200) default NULL COMMENT 'snapshot of the RS',
  `SNAPSHOT_PATH` varchar(200) default NULL COMMENT 'URL of the snapshot path',
  `FEAT_FILENAME` varchar(150) NOT NULL COMMENT 'Name of the data file used to fill the RADIOSOURCES table',
  `HELIO_RS_NUMBER` int(11) default NULL COMMENT 'HELIO RS number',
  `RUN_DATE` datetime NOT NULL COMMENT 'Date when FR code was run',
  PRIMARY KEY  (`ID_RS`),
  KEY `frc_info_fk_constraint_rs` (`FRC_INFO_ID`),
  KEY `observations_fk_constraint_rs` (`OBSERVATIONS_ID`),
  CONSTRAINT `frc_info_fk_constraint_rs` FOREIGN KEY (`FRC_INFO_ID`) REFERENCES `FRC_INFO` (`ID_FRC_INFO`),
  CONSTRAINT `observations_fk_constraint_rs` FOREIGN KEY (`OBSERVATIONS_ID`) REFERENCES `OBSERVATIONS` (`ID_OBSERVATIONS`)
) ENGINE=InnoDB AUTO_INCREMENT=23551 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SUNSPOTS`
--

DROP TABLE IF EXISTS `SUNSPOTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SUNSPOTS` (
  `ID_SUNSPOT` int(11) NOT NULL auto_increment COMMENT 'Sunspot ID',
  `FRC_INFO_ID` int(11) NOT NULL COMMENT 'Ref. to FR code information',
  `OBSERVATIONS_ID_IC` int(11) default NULL COMMENT 'Pointing to observation in HMI/Ic',
  `OBSERVATIONS_ID_M` int(11) default NULL COMMENT 'Pointing to observation in HMI/M',
  `FEAT_X_ARCSEC` double NOT NULL COMMENT 'X Heliocentric coordinates of the SS gravity centre in arcsec',
  `FEAT_Y_ARCSEC` double NOT NULL COMMENT 'Y Heliocentric coordinates of the SS gravity centre in arcsec',
  `FEAT_X_PIX` int(8) NOT NULL COMMENT 'X image coordinates of the SS gravity centre in pixels',
  `FEAT_Y_PIX` int(8) NOT NULL COMMENT 'Y image coordinates of the SS gravity centre in pixels',
  `FEAT_HG_LONG_DEG` float NOT NULL COMMENT 'Heliographic longitude of the SS gravity centre in degrees',
  `FEAT_HG_LAT_DEG` float NOT NULL COMMENT 'Heliographic latitude of the SS gravity centre in degrees',
  `FEAT_CARR_LONG_DEG` float NOT NULL COMMENT 'Carrington longitude of the SS gravity centre in degrees',
  `FEAT_CARR_LAT_DEG` float NOT NULL COMMENT 'Carrington latitude of the SS gravity centre in degrees',
  `BR_X0_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in arcsec',
  `BR_Y0_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in arcsec',
  `BR_X1_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in arcsec',
  `BR_Y1_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in arcsec',
  `BR_X2_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in arcsec',
  `BR_Y2_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in arcsec',
  `BR_X3_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in arcsec',
  `BR_Y3_ARCSEC` double NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in arcsec',
  `BR_X0_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in pixels',
  `BR_Y0_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South East most point in pixels',
  `BR_X1_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in pixels',
  `BR_Y1_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North East most point in pixels',
  `BR_X2_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in pixels',
  `BR_Y2_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate South West most point in pixels',
  `BR_X3_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in pixels',
  `BR_Y3_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate North West most point in pixels',
  `BR_HG_LONG0_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South East most point in degrees',
  `BR_HG_LAT0_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South East most point in degrees',
  `BR_HG_LONG1_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North East most point in degrees',
  `BR_HG_LAT1_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North East most point in degrees',
  `BR_HG_LONG2_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South West most point in degrees',
  `BR_HG_LAT2_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate South West most point in degrees',
  `BR_HG_LONG3_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North West most point in degrees',
  `BR_HG_LAT3_DEG` float NOT NULL COMMENT 'Bounding rectangle heliographic coordinate North West most point in degrees',
  `BR_CARR_LONG0_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South East most point in degrees',
  `BR_CARR_LAT0_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South East most point in degrees',
  `BR_CARR_LONG1_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North East most point in degrees',
  `BR_CARR_LAT1_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North East most point in degrees',
  `BR_CARR_LONG2_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South West most point in degrees',
  `BR_CARR_LAT2_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate South West most point in degrees',
  `BR_CARR_LONG3_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North West most point in degrees',
  `BR_CARR_LAT3_DEG` float NOT NULL COMMENT 'Bounding rectangle Carrington coordinate North West most point in degrees',
  `FEAT_AREA_PIX` int(11) NOT NULL COMMENT 'Number of pixels included in the SS',
  `FEAT_AREA_DEG2` float NOT NULL COMMENT 'Area of the SS in square degrees',
  `FEAT_AREA_MM2` float NOT NULL COMMENT 'Area in Mm2 of the SS',
  `FEAT_DIAM_DEG` float NOT NULL COMMENT 'Diameter of the SS in degrees',
  `FEAT_DIAM_MM` float NOT NULL COMMENT 'Diameter of the SS in Mm',
  `FEAT_MEAN2QSUN` double NOT NULL COMMENT 'Mean of the SS to QS instensity ratio',
  `FEAT_MAX_INT` float NOT NULL COMMENT 'SS max. intensity value, in units of the original observation',
  `FEAT_MIN_INT` float NOT NULL COMMENT 'SS min. intensity value, in units of the original observation',
  `FEAT_MEAN_INT` float NOT NULL COMMENT 'SS mean intensity value in the units of the original observation.',
  `FEAT_TOT_BZ` float default NULL COMMENT 'SS total line-of-sight magnetic field in Gauss',
  `FEAT_ABS_BZ` float NOT NULL COMMENT 'SS total abs. line-of-sight magnetic field in Gauss',
  `FEAT_MAX_BZ` float default NULL COMMENT 'SS max. line-of-sight magnetic field in Gauss',
  `FEAT_MIN_BZ` float default NULL COMMENT 'SS min. line-of-sight magnetic field in Gauss',
  `FEAT_MEAN_BZ` float default NULL COMMENT 'SS mean line-of-sight magnetic field in Gauss',
  `UMBRA_NUMBER` int(4) NOT NULL COMMENT 'Number of umbras',
  `UMBRA_AREA_PIX` int(11) NOT NULL COMMENT 'Number of pixels included in the umbra',
  `UMBRA_AREA_MM2` float NOT NULL COMMENT 'Area in Mm2 of the umbra',
  `UMBRA_AREA_DEG2` float NOT NULL COMMENT 'Area of the umbra in square degrees',
  `UMBRA_DIAM_DEG` float NOT NULL COMMENT 'Diameter of the umbra in degrees',
  `UMBRA_DIAM_MM` float NOT NULL COMMENT 'Diameter of the umbra in Mm',
  `UMBRA_MAX_INT` float NOT NULL COMMENT 'Umbra max. intensity value, in units of the original observation',
  `UMBRA_MIN_INT` float NOT NULL COMMENT 'Umbra min. intensity value, in units of the original observation',
  `UMBRA_MEAN_INT` float NOT NULL COMMENT 'Umbra mean intensity value in the units of the original observation.',
  `UMBRA_TOT_BZ` float NOT NULL COMMENT 'Umbra total line-of-sight magnetic field in Gauss',
  `UMBRA_ABS_BZ` float default NULL COMMENT 'Umbra total abs. line-of-sight magnetic field in Gauss',
  `UMBRA_MAX_BZ` float default NULL COMMENT 'Umbra max. line-of-sight magnetic field in Gauss',
  `UMBRA_MIN_BZ` float default NULL COMMENT 'Umbra min. line-of-sight magnetic field in Gauss',
  `UMBRA_MEAN_BZ` float default NULL COMMENT 'Umbra mean line-of-sight magnetic field in Gauss',
  `CC_X_PIX` int(8) NOT NULL COMMENT 'X coordinate of chain code start position in pixels',
  `CC_Y_PIX` int(8) NOT NULL COMMENT 'Y coordinate of chain code start position in pixels',
  `CC_X_ARCSEC` double NOT NULL COMMENT 'X coordinate of chain code start position in arcsec',
  `CC_Y_ARCSEC` double NOT NULL COMMENT 'Y coordinate of chain code start position in arcsec',
  `CC` text COMMENT 'Boundary chain code',
  `CC_LENGTH` int(11) NOT NULL COMMENT 'Chain code length',
  `RS` text NOT NULL COMMENT 'Raster scan',
  `RS_LENGTH` int(11) NOT NULL COMMENT 'Raster scan length',
  `SNAPSHOT_FN` varchar(200) default NULL COMMENT 'snapshot of the SS',
  `SNAPSHOT_PATH` varchar(200) default NULL COMMENT 'URL of the snapshot path',
  `HELIO_SS_NUMBER` int(11) NOT NULL COMMENT 'HELIO SS number',
  `FEAT_FILENAME` varchar(150) default NULL COMMENT 'Name of the data file used to fill the SUNSPOTS table',
  `RUN_DATE` datetime NOT NULL COMMENT 'Date when FR code was run',
  PRIMARY KEY  (`ID_SUNSPOT`),
  KEY `frc_info_fk_constraint_ss` (`FRC_INFO_ID`),
  KEY `observations_hmi_ic_fk_constraint` (`OBSERVATIONS_ID_IC`),
  KEY `observations_hmi_m_fk_constraint` (`OBSERVATIONS_ID_M`),
  KEY `index_feat_area_deg2` (`FEAT_AREA_DEG2`),
  KEY `index_feat_filename_sp` (`FEAT_FILENAME`),
  CONSTRAINT `frc_info_fk_constraint_ss` FOREIGN KEY (`FRC_INFO_ID`) REFERENCES `FRC_INFO` (`ID_FRC_INFO`),
  CONSTRAINT `observations_hmi_ic_fk_constraint` FOREIGN KEY (`OBSERVATIONS_ID_IC`) REFERENCES `OBSERVATIONS` (`ID_OBSERVATIONS`) ON DELETE CASCADE,
  CONSTRAINT `observations_hmi_m_fk_constraint` FOREIGN KEY (`OBSERVATIONS_ID_M`) REFERENCES `OBSERVATIONS` (`ID_OBSERVATIONS`)
) ENGINE=InnoDB AUTO_INCREMENT=1177762 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TYPE_II`
--

DROP TABLE IF EXISTS `TYPE_II`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TYPE_II` (
  `ID_TYPE_II` int(11) NOT NULL auto_increment,
  `FRC_INFO_ID` int(4) NOT NULL COMMENT 'Ref. to FR code information',
  `OBSERVATIONS_ID` int(4) NOT NULL COMMENT 'Ref. to Observations information',
  `RS` text NOT NULL,
  `RS_LENGTH` int(11) NOT NULL,
  `SKE_CC_X_PIX` int(8) NOT NULL COMMENT 'Coding 1st skeleton position in pixels for X axis',
  `SKE_CC_Y_PIX` int(8) NOT NULL COMMENT 'Coding 1st skeleton position in pixels for Y axis',
  `SKE_CC_X_UTC` datetime NOT NULL COMMENT 'Coding 1st skeleton position in UTC for X axis',
  `SKE_CC_Y_MHZ` float NOT NULL COMMENT 'Coding 1st skeleton position in MHz for Y axis',
  `SKE_CC` text NOT NULL COMMENT 'Chain code of skeleton',
  `SKE_CC_LENGTH` int(11) NOT NULL COMMENT 'Length of chain code for the skeleton',
  `BR_X0_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate lower left most point in pixels',
  `BR_Y0_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate lower left most point in pixels',
  `BR_X1_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate upper left most point in pixels',
  `BR_Y1_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate upper left most point in pixels',
  `BR_X2_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate lower right most point in pixels',
  `BR_Y2_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate lower right most point in pixels',
  `BR_X3_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate upper right most point in pixels',
  `BR_Y3_PIX` int(8) NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate upper right most point in pixels',
  `BR_X0_UTC` double NOT NULL,
  `BR_Y0_MHZ` float NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate lower left most point in MHz',
  `BR_X1_UTC` double NOT NULL,
  `BR_Y1_MHZ` float NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate upper left most point in MHz',
  `BR_X2_UTC` double NOT NULL,
  `BR_Y2_MHZ` float NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate lower right most point in MHz',
  `BR_X3_UTC` double NOT NULL,
  `BR_Y3_MHZ` float NOT NULL COMMENT 'Bounding rectangle heliocentric coordinate upper right most point in MHz',
  `TIME_START` datetime NOT NULL,
  `TIME_END` datetime NOT NULL,
  `DRIFT_START` float default NULL,
  `DRIFT_END` float default NULL,
  `FEAT_MAX_INT` float NOT NULL COMMENT 'Type 2 max. intensity value, in units of the original observation',
  `FEAT_MEAN_INT` float NOT NULL COMMENT 'Type 2 mean intensity value, in units of the original observation',
  `LVL_TRUST` int(8) NOT NULL COMMENT 'Level of confidence of the detection',
  `COMPONENT` varchar(1) NOT NULL COMMENT 'When it is possible, give the component observed (F=Fondamental, H=second harmonic)',
  `SNAPSHOT_FN` varchar(200) default NULL COMMENT 'snapshot of the t2',
  `SNAPSHOT_PATH` varchar(200) default NULL COMMENT 'URL of the snapshot path',
  `FEAT_FILENAME` varchar(150) NOT NULL COMMENT 'Name of the data file used to fill the TYPE_II table',
  `HELIO_T2_NUMBER` int(11) default NULL COMMENT 'HELIO T2 number',
  `RUN_DATE` datetime NOT NULL COMMENT 'Data at which the code was run',
  PRIMARY KEY  (`ID_TYPE_II`),
  KEY `frc_info_fk_constraint_type2` (`FRC_INFO_ID`),
  KEY `observations_constraint_type2` (`OBSERVATIONS_ID`),
  CONSTRAINT `frc_info_fk_constraint_type2` FOREIGN KEY (`FRC_INFO_ID`) REFERENCES `FRC_INFO` (`ID_FRC_INFO`),
  CONSTRAINT `observations_fk_constraint_type2` FOREIGN KEY (`OBSERVATIONS_ID`) REFERENCES `OBSERVATIONS` (`ID_OBSERVATIONS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TYPE_III`
--

DROP TABLE IF EXISTS `TYPE_III`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TYPE_III` (
  `ID_TYPE_III` int(11) NOT NULL auto_increment,
  `FRC_INFO_ID` int(4) NOT NULL COMMENT 'Ref. to FR code information',
  `OBSERVATIONS_ID` int(4) NOT NULL COMMENT 'Ref. to Observations information',
  `CC_X_PIX` int(8) NOT NULL COMMENT 'Coding 1st position in pixels for X axis',
  `CC_Y_PIX` int(8) NOT NULL COMMENT 'Coding 1st position in pixels for Y axis',
  `CC_X_UTC` double NOT NULL COMMENT 'Start time in UTC for X axis',
  `CC_Y_MHZ` float NOT NULL COMMENT 'Frequency at the start time in MHz for Y axis',
  `CC` text NOT NULL COMMENT 'Chain code of the burst boundaries',
  `CC_LENGTH` int(11) NOT NULL COMMENT 'Length of the chain code',
  `SKE_CC_X_PIX` int(8) NOT NULL COMMENT 'Coding 1st skeleton position in pixels for X axis',
  `SKE_CC_Y_PIX` int(8) NOT NULL COMMENT 'Coding 1st skeleton position in pixels for Y axis',
  `SKE_CC_X_UTC` double NOT NULL COMMENT 'Coding 1st skeleton position in UTC for X axis',
  `SKE_CC_Y_MHZ` float NOT NULL COMMENT 'Coding 1st skeleton position in MHz for Y axis',
  `SKE_CC` text NOT NULL COMMENT 'Chain code of skeleton',
  `SKE_CC_LENGTH` int(11) NOT NULL COMMENT 'Length of chain code for the skeleton',
  `BR_X0_PIX` int(8) default NULL COMMENT 'Bounding rectangle heliocentric coordinate lower left most point in pixels',
  `BR_Y0_PIX` int(8) default NULL COMMENT 'Bounding rectangle heliocentric coordinate lower left most point in pixels',
  `BR_X1_PIX` int(8) default NULL COMMENT 'Bounding rectangle heliocentric coordinate upper left most point in pixels',
  `BR_Y1_PIX` int(8) default NULL COMMENT 'Bounding rectangle heliocentric coordinate upper left most point in pixels',
  `BR_X2_PIX` int(8) default NULL COMMENT 'Bounding rectangle heliocentric coordinate lower right most point in pixels',
  `BR_Y2_PIX` int(8) default NULL COMMENT 'Bounding rectangle heliocentric coordinate lower right most point in pixels',
  `BR_X3_PIX` int(8) default NULL COMMENT 'Bounding rectangle heliocentric coordinate upper right most point in pixels',
  `BR_Y3_PIX` int(8) default NULL COMMENT 'Bounding rectangle heliocentric coordinate upper right most point in pixels',
  `BR_X0_UTC` double default NULL COMMENT 'Bounding rectangle heliocentric coordinate lower left most point in utc',
  `BR_Y0_MHZ` float default NULL COMMENT 'Bounding rectangle heliocentric coordinate lower left most point in MHz',
  `BR_X1_UTC` double default NULL COMMENT 'Bounding rectangle heliocentric coordinate upper left most point in utc',
  `BR_Y1_MHZ` float default NULL COMMENT 'Bounding rectangle heliocentric coordinate upper left most point in MHz',
  `BR_X2_UTC` double default NULL COMMENT 'Bounding rectangle heliocentric coordinate lower right most point in utc',
  `BR_Y2_MHZ` float default NULL COMMENT 'Bounding rectangle heliocentric coordinate lower right most point in MHz',
  `BR_X3_UTC` double default NULL COMMENT 'Bounding rectangle heliocentric coordinate upper right most point in utc',
  `BR_Y3_MHZ` float default NULL COMMENT 'Bounding rectangle heliocentric coordinate upper right most point in MHz',
  `TIME_START` datetime NOT NULL,
  `TIME_END` datetime NOT NULL,
  `FEAT_MAX_INT` float NOT NULL COMMENT 'Type 3 max. intensity value, in units of the original observation',
  `FEAT_MEAN_INT` float NOT NULL COMMENT 'Type 3 mean intensity value, in units of the original observation',
  `FIT_A0` float default NULL,
  `FIT_A1` float default NULL,
  `DRIFT_START` float default NULL COMMENT 'Drift rate (in MHz/s) at start time',
  `DRIFT_END` float default NULL COMMENT 'Drift rate (in MHz/s) at end time',
  `LVL_TRUST` int(8) NOT NULL COMMENT 'Level of confidence of the detection',
  `MULTIPLE` int(8) NOT NULL COMMENT 'Equal to 1 if the burst overlaps an other one, 0 else',
  `SNAPSHOT_FN` varchar(200) default NULL COMMENT 'snapshot of the t3',
  `SNAPSHOT_PATH` varchar(200) default NULL COMMENT 'URL of the snapshot path',
  `FEAT_FILENAME` varchar(150) NOT NULL COMMENT 'Name of the data file used to fill the TYPE_III table',
  `HELIO_T3_NUMBER` int(11) default NULL COMMENT 'HELIO T3 number',
  `RUN_DATE` datetime NOT NULL COMMENT 'Data at which the code was run',
  PRIMARY KEY  (`ID_TYPE_III`),
  KEY `frc_info_fk_constraint_type3` (`FRC_INFO_ID`),
  KEY `observations_constraint_type3` (`OBSERVATIONS_ID`),
  KEY `index_feat_max_int` (`FEAT_MAX_INT`),
  CONSTRAINT `frc_info_fk_constraint_type3` FOREIGN KEY (`FRC_INFO_ID`) REFERENCES `FRC_INFO` (`ID_FRC_INFO`),
  CONSTRAINT `observations_fk_constraint_type3` FOREIGN KEY (`OBSERVATIONS_ID`) REFERENCES `OBSERVATIONS` (`ID_OBSERVATIONS`)
) ENGINE=InnoDB AUTO_INCREMENT=90846 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `VIEW_AR_GUI`
--

DROP TABLE IF EXISTS `VIEW_AR_GUI`;
/*!50001 DROP VIEW IF EXISTS `VIEW_AR_GUI`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VIEW_AR_GUI` (
  `ID_OBSERVATORY` int(11),
  `OBSERVAT` varchar(255),
  `INSTRUME` varchar(150),
  `TELESCOP` varchar(150),
  `DATE_OBS` datetime,
  `DATE_END` datetime,
  `FILENAME` varchar(100),
  `URL` text,
  `BITPIX` int(3),
  `CDELT1` double,
  `CDELT2` double,
  `NAXIS1` int(8),
  `NAXIS2` int(8),
  `CENTER_X` double,
  `CENTER_Y` double,
  `R_SUN` double,
  `NOAA_NUMBER` int(11),
  `ID_AR` int(11),
  `OBSERVATIONS_ID` int(11),
  `FEAT_X_PIX` int(8),
  `FEAT_Y_PIX` int(8),
  `FEAT_X_ARCSEC` double,
  `FEAT_Y_ARCSEC` double,
  `FEAT_HG_LONG_DEG` float,
  `FEAT_HG_LAT_DEG` float,
  `FEAT_CARR_LONG_DEG` float,
  `FEAT_CARR_LAT_DEG` float,
  `FEAT_AREA_PIX` int(11),
  `FEAT_AREA_DEG2` float,
  `FEAT_AREA_MM2` float,
  `FEAT_MAX_INT` float,
  `FEAT_MIN_INT` float,
  `FEAT_MEAN_INT` float,
  `FEAT_MEAN2QSUN` double,
  `FEAT_MIN_BZ` float,
  `FEAT_MAX_BZ` float,
  `FEAT_MEAN_BZ` float,
  `FEAT_LENGTH_NL` float,
  `FEAT_LENGTH_SG` float,
  `FEAT_MAX_GRAD` float,
  `FEAT_MEAN_GRAD` float,
  `FEAT_MEDIAN_GRAD` float,
  `FEAT_RVAL` float,
  `FEAT_WLSG` float,
  `CC_X_PIX` int(8),
  `CC_Y_PIX` int(8),
  `CC_X_ARCSEC` double,
  `CC_Y_ARCSEC` double,
  `CC` text,
  `CC_LENGTH` int(11),
  `SNAPSHOT_PATH` varchar(200),
  `SNAPSHOT_FN` varchar(200)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `VIEW_AR_HQI`
--

DROP TABLE IF EXISTS `VIEW_AR_HQI`;
/*!50001 DROP VIEW IF EXISTS `VIEW_AR_HQI`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VIEW_AR_HQI` (
  `INSTITUT` varchar(150),
  `CODE` varchar(100),
  `VERSION` varchar(50),
  `FEATURE_NAME` varchar(100),
  `ENC_MET` varchar(50),
  `PERSON` varchar(150),
  `CONTACT` varchar(150),
  `REFERENCE` varchar(150),
  `OBSERVAT` varchar(255),
  `INSTRUME` varchar(150),
  `TELESCOP` varchar(150),
  `UNITS` varchar(100),
  `WAVEMIN` float,
  `WAVEMAX` float,
  `WAVENAME` varchar(50),
  `WAVEUNIT` varchar(10),
  `SPECTRAL_NAME` varchar(100),
  `OBS_TYPE` varchar(100),
  `DATE_OBS` datetime,
  `DATE_END` datetime,
  `JDINT` int(11),
  `JDFRAC` double,
  `C_ROTATION` int(7),
  `FILENAME` varchar(100),
  `URL` text,
  `CDELT1` double,
  `CDELT2` double,
  `NAXIS1` int(8),
  `NAXIS2` int(8),
  `CENTER_X` double,
  `CENTER_Y` double,
  `R_SUN` double,
  `BITPIX` int(3),
  `BSCALE` double,
  `BZERO` double,
  `EXP_TIME` float,
  `FILE_FORMAT` varchar(20),
  `QCLK_URL` text,
  `QCLK_FNAME` varchar(200),
  `ID_AR` int(11),
  `NOAA_NUMBER` int(11),
  `FEAT_X_PIX` int(8),
  `FEAT_Y_PIX` int(8),
  `FEAT_X_ARCSEC` double,
  `FEAT_Y_ARCSEC` double,
  `FEAT_HG_LONG_DEG` float,
  `FEAT_HG_LAT_DEG` float,
  `FEAT_CARR_LONG_DEG` float,
  `FEAT_CARR_LAT_DEG` float,
  `BR_X0_PIX` double,
  `BR_Y0_PIX` double,
  `BR_X1_PIX` int(8),
  `BR_Y1_PIX` int(8),
  `BR_X2_PIX` int(8),
  `BR_Y2_PIX` int(8),
  `BR_X3_PIX` double,
  `BR_Y3_PIX` double,
  `BR_X0_ARCSEC` double,
  `BR_Y0_ARCSEC` double,
  `BR_X1_ARCSEC` double,
  `BR_Y1_ARCSEC` double,
  `BR_X2_ARCSEC` double,
  `BR_Y2_ARCSEC` double,
  `BR_X3_ARCSEC` double,
  `BR_Y3_ARCSEC` double,
  `BR_HG_LONG0_DEG` double,
  `BR_HG_LAT0_DEG` double,
  `BR_HG_LONG1_DEG` double,
  `BR_HG_LAT1_DEG` double,
  `BR_HG_LONG2_DEG` double,
  `BR_HG_LAT2_DEG` double,
  `BR_HG_LONG3_DEG` double,
  `BR_HG_LAT3_DEG` double,
  `BR_CARR_LONG0_DEG` double,
  `BR_CARR_LAT0_DEG` double,
  `BR_CARR_LONG1_DEG` double,
  `BR_CARR_LAT1_DEG` double,
  `BR_CARR_LONG2_DEG` double,
  `BR_CARR_LAT2_DEG` double,
  `BR_CARR_LONG3_DEG` double,
  `BR_CARR_LAT3_DEG` double,
  `FEAT_AREA_PIX` int(11),
  `FEAT_AREA_DEG2` float,
  `FEAT_AREA_MM2` float,
  `FEAT_MAX_INT` float,
  `FEAT_MIN_INT` float,
  `FEAT_MEAN_INT` float,
  `FEAT_MEAN2QSUN` double,
  `FEAT_MIN_BZ` float,
  `FEAT_MAX_BZ` float,
  `FEAT_MEAN_BZ` float,
  `FEAT_MEAN_GRAD` float,
  `FEAT_MEDIAN_GRAD` float,
  `FEAT_MAX_GRAD` float,
  `FEAT_LENGTH_NL` float,
  `FEAT_LENGTH_SG` float,
  `CC_X_PIX` int(8),
  `CC_Y_PIX` int(8),
  `CC_X_ARCSEC` double,
  `CC_Y_ARCSEC` double,
  `CC` text,
  `CC_LENGTH` int(11),
  `SNAPSHOT` varchar(400)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `VIEW_CH_GUI`
--

DROP TABLE IF EXISTS `VIEW_CH_GUI`;
/*!50001 DROP VIEW IF EXISTS `VIEW_CH_GUI`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VIEW_CH_GUI` (
  `ID_OBSERVATORY` int(11),
  `OBSERVAT` varchar(255),
  `INSTRUME` varchar(150),
  `TELESCOP` varchar(150),
  `DATE_OBS` datetime,
  `DATE_END` datetime,
  `FILENAME` varchar(100),
  `URL` text,
  `BITPIX` int(3),
  `CDELT1` double,
  `CDELT2` double,
  `NAXIS1` int(8),
  `NAXIS2` int(8),
  `CENTER_X` double,
  `CENTER_Y` double,
  `R_SUN` double,
  `ID_CORONALHOLES` int(11),
  `OBSERVATIONS_ID` int(11),
  `FEAT_X_PIX` int(8),
  `FEAT_Y_PIX` int(8),
  `FEAT_X_ARCSEC` double,
  `FEAT_Y_ARCSEC` double,
  `FEAT_HG_LONG_DEG` float,
  `FEAT_HG_LAT_DEG` float,
  `FEAT_CARR_LONG_DEG` float,
  `FEAT_CARR_LAT_DEG` float,
  `BR_HG_LONG3_DEG` float,
  `BR_CARR_LONG2_DEG` float,
  `BR_CARR_LAT2_DEG` float,
  `BR_CARR_LONG3_DEG` float,
  `BR_CARR_LAT3_DEG` float,
  `FEAT_WIDTH_X_ARCSEC` double,
  `FEAT_WIDTH_Y_ARCSEC` double,
  `FEAT_WIDTH_HG_LONG_DEG` float,
  `FEAT_WIDTH_HG_LAT_DEG` float,
  `FEAT_AREA_PIX` int(11),
  `FEAT_AREA_DEG2` float,
  `FEAT_AREA_MM2` float,
  `FEAT_MAX_INT` float,
  `FEAT_MIN_INT` float,
  `FEAT_MEAN_INT` float,
  `FEAT_MEAN2QSUN` double,
  `FEAT_MIN_BZ` float,
  `FEAT_MAX_BZ` float,
  `FEAT_MEAN_BZ` float,
  `FEAT_SKEW_BZ` double,
  `CC_X_PIX` int(8),
  `CC_Y_PIX` int(8),
  `CC_X_ARCSEC` double,
  `CC_Y_ARCSEC` double,
  `CC` text,
  `CC_LENGTH` int(11),
  `SNAPSHOT_PATH` varchar(200),
  `SNAPSHOT_FN` varchar(200)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `VIEW_CH_HQI`
--

DROP TABLE IF EXISTS `VIEW_CH_HQI`;
/*!50001 DROP VIEW IF EXISTS `VIEW_CH_HQI`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VIEW_CH_HQI` (
  `INSTITUT` varchar(150),
  `CODE` varchar(100),
  `VERSION` varchar(50),
  `FEATURE_NAME` varchar(100),
  `ENC_MET` varchar(50),
  `PERSON` varchar(150),
  `CONTACT` varchar(150),
  `REFERENCE` varchar(150),
  `OBSERVAT` varchar(255),
  `INSTRUME` varchar(150),
  `TELESCOP` varchar(150),
  `UNITS` varchar(100),
  `WAVEMIN` float,
  `WAVEMAX` float,
  `WAVENAME` varchar(50),
  `WAVEUNIT` varchar(10),
  `SPECTRAL_NAME` varchar(100),
  `OBS_TYPE` varchar(100),
  `DATE_OBS` datetime,
  `DATE_END` datetime,
  `JDINT` int(11),
  `JDFRAC` double,
  `C_ROTATION` int(7),
  `FILENAME` varchar(100),
  `URL` text,
  `CDELT1` double,
  `CDELT2` double,
  `NAXIS1` int(8),
  `NAXIS2` int(8),
  `CENTER_X` double,
  `CENTER_Y` double,
  `R_SUN` double,
  `BITPIX` int(3),
  `BSCALE` double,
  `BZERO` double,
  `EXP_TIME` float,
  `FILE_FORMAT` varchar(20),
  `QCLK_URL` text,
  `QCLK_FNAME` varchar(200),
  `ID_CORONALHOLES` int(11),
  `FEAT_X_PIX` int(8),
  `FEAT_Y_PIX` int(8),
  `FEAT_X_ARCSEC` double,
  `FEAT_Y_ARCSEC` double,
  `FEAT_HG_LONG_DEG` float,
  `FEAT_HG_LAT_DEG` float,
  `FEAT_CARR_LONG_DEG` float,
  `FEAT_CARR_LAT_DEG` float,
  `BR_X0_PIX` int(8),
  `BR_Y0_PIX` int(8),
  `BR_X1_PIX` int(8),
  `BR_Y1_PIX` int(8),
  `BR_X2_PIX` int(8),
  `BR_Y2_PIX` int(8),
  `BR_X3_PIX` int(8),
  `BR_Y3_PIX` int(8),
  `BR_X0_ARCSEC` double,
  `BR_Y0_ARCSEC` double,
  `BR_X1_ARCSEC` double,
  `BR_Y1_ARCSEC` double,
  `BR_X2_ARCSEC` double,
  `BR_Y2_ARCSEC` double,
  `BR_X3_ARCSEC` double,
  `BR_Y3_ARCSEC` double,
  `BR_HG_LONG0_DEG` float,
  `BR_HG_LAT0_DEG` float,
  `BR_HG_LONG1_DEG` float,
  `BR_HG_LAT1_DEG` float,
  `BR_HG_LONG2_DEG` float,
  `BR_HG_LAT2_DEG` float,
  `BR_HG_LONG3_DEG` float,
  `BR_HG_LAT3_DEG` float,
  `BR_CARR_LONG0_DEG` float,
  `BR_CARR_LAT0_DEG` float,
  `BR_CARR_LONG1_DEG` float,
  `BR_CARR_LAT1_DEG` float,
  `BR_CARR_LONG2_DEG` float,
  `BR_CARR_LAT2_DEG` float,
  `BR_CARR_LONG3_DEG` float,
  `BR_CARR_LAT3_DEG` float,
  `FEAT_WIDTH_X_ARCSEC` double,
  `FEAT_WIDTH_Y_ARCSEC` double,
  `FEAT_WIDTH_HG_LONG_DEG` float,
  `FEAT_WIDTH_HG_LAT_DEG` float,
  `FEAT_AREA_PIX` int(11),
  `FEAT_AREA_DEG2` float,
  `FEAT_AREA_MM2` float,
  `FEAT_MAX_INT` float,
  `FEAT_MIN_INT` float,
  `FEAT_MEAN_INT` float,
  `FEAT_MEAN2QSUN` double,
  `FEAT_MIN_BZ` float,
  `FEAT_MAX_BZ` float,
  `FEAT_MEAN_BZ` float,
  `FEAT_SKEW_BZ` double,
  `CC_X_PIX` int(8),
  `CC_Y_PIX` int(8),
  `CC_X_ARCSEC` double,
  `CC_Y_ARCSEC` double,
  `CC` text,
  `CC_LENGTH` int(11),
  `SNAPSHOT` varchar(400)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `VIEW_FIL_GUI`
--

DROP TABLE IF EXISTS `VIEW_FIL_GUI`;
/*!50001 DROP VIEW IF EXISTS `VIEW_FIL_GUI`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VIEW_FIL_GUI` (
  `ID_OBSERVATORY` int(11),
  `OBSERVAT` varchar(255),
  `INSTRUM` varchar(150),
  `TELESCOP` varchar(150),
  `DATE_OBS` datetime,
  `DATE_END` datetime,
  `FILENAME` varchar(150),
  `URL` text,
  `BITPIX` int(3),
  `CDELT1` double,
  `CDELT2` double,
  `NAXIS1` int(8),
  `NAXIS2` int(8),
  `CENTER_X` double,
  `CENTER_Y` double,
  `R_SUN` double,
  `ID_FIL` int(11),
  `FRC_INFO_ID` int(4),
  `PP_OUTPUT_ID` int(11),
  `FEAT_X_ARCSEC` double,
  `FEAT_Y_ARCSEC` double,
  `FEAT_X_PIX` int(8),
  `FEAT_Y_PIX` int(8),
  `FEAT_HG_LONG_DEG` float,
  `FEAT_HG_LAT_DEG` float,
  `FEAT_CARR_LONG_DEG` float,
  `FEAT_CARR_LAT_DEG` float,
  `BR_X0_ARCSEC` double,
  `BR_Y0_ARCSEC` double,
  `BR_X1_ARCSEC` double,
  `BR_Y1_ARCSEC` double,
  `BR_X2_ARCSEC` double,
  `BR_Y2_ARCSEC` double,
  `BR_X3_ARCSEC` double,
  `BR_Y3_ARCSEC` double,
  `BR_X0_PIX` int(8),
  `BR_Y0_PIX` int(8),
  `BR_X1_PIX` int(8),
  `BR_Y1_PIX` int(8),
  `BR_X2_PIX` int(8),
  `BR_Y2_PIX` int(8),
  `BR_X3_PIX` int(8),
  `BR_Y3_PIX` int(8),
  `BR_HG_LONG0_DEG` float,
  `BR_HG_LAT0_DEG` float,
  `BR_HG_LONG1_DEG` float,
  `BR_HG_LAT1_DEG` float,
  `BR_HG_LONG2_DEG` float,
  `BR_HG_LAT2_DEG` float,
  `BR_HG_LONG3_DEG` float,
  `BR_HG_LAT3_DEG` float,
  `BR_CARR_LONG0_DEG` float,
  `BR_CARR_LAT0_DEG` float,
  `BR_CARR_LONG1_DEG` float,
  `BR_CARR_LAT1_DEG` float,
  `BR_CARR_LONG2_DEG` float,
  `BR_CARR_LAT2_DEG` float,
  `BR_CARR_LONG3_DEG` float,
  `BR_CARR_LAT3_DEG` float,
  `FEAT_AREA_PIX` int(11),
  `FEAT_AREA_MM2` float,
  `FEAT_AREA_DEG2` float,
  `FEAT_MEAN2QSUN` double,
  `FEAT_MAX_INT` float,
  `FEAT_MIN_INT` float,
  `FEAT_MEAN_INT` float,
  `CC_X_PIX` int(8),
  `CC_Y_PIX` int(8),
  `CC_X_ARCSEC` double,
  `CC_Y_ARCSEC` double,
  `SKE_LENGTH_DEG` float,
  `SKE_CURVATURE` float,
  `FEAT_ELONG` float,
  `SKE_ORIENTATION` float,
  `SKE_CC_X_PIX` int(8),
  `SKE_CC_Y_PIX` int(8),
  `SKE_CC_X_ARCSEC` double,
  `SKE_CC_Y_ARCSEC` double,
  `CC` text,
  `SKE_CC` text,
  `CC_LENGTH` int(11),
  `SKE_CC_LENGTH` int(11),
  `SNAPSHOT_FN` varchar(200),
  `SNAPSHOT_PATH` varchar(200),
  `PR_LOCFNAME` varchar(150),
  `FEAT_FILENAME` varchar(150),
  `HELIO_FI_NUMBER` int(11),
  `RUN_DATE` datetime
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `VIEW_FIL_HQI`
--

DROP TABLE IF EXISTS `VIEW_FIL_HQI`;
/*!50001 DROP VIEW IF EXISTS `VIEW_FIL_HQI`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VIEW_FIL_HQI` (
  `INSTITUT` varchar(150),
  `CODE` varchar(100),
  `VERSION` varchar(50),
  `FEATURE_NAME` varchar(100),
  `ENC_MET` varchar(50),
  `PERSON` varchar(150),
  `CONTACT` varchar(150),
  `REFERENCE` varchar(150),
  `OBSERVAT` varchar(255),
  `INSTRUME` varchar(150),
  `TELESCOP` varchar(150),
  `UNITS` varchar(100),
  `WAVEMIN` float,
  `WAVEMAX` float,
  `WAVENAME` varchar(50),
  `WAVEUNIT` varchar(10),
  `SPECTRAL_NAME` varchar(100),
  `OBS_TYPE` varchar(100),
  `DATE_OBS` datetime,
  `DATE_END` datetime,
  `JDINT` int(11),
  `JDFRAC` double,
  `C_ROTATION` int(7),
  `FILENAME` varchar(100),
  `URL` text,
  `CDELT1` double,
  `CDELT2` double,
  `NAXIS1` int(8),
  `NAXIS2` int(8),
  `CENTER_X` double,
  `CENTER_Y` double,
  `R_SUN` double,
  `BITPIX` int(3),
  `BSCALE` double,
  `BZERO` double,
  `EXP_TIME` float,
  `FILE_FORMAT` varchar(20),
  `QCLK_URL` text,
  `QCLK_FNAME` varchar(200),
  `ID_FIL` int(11),
  `FEAT_X_PIX` int(8),
  `FEAT_Y_PIX` int(8),
  `FEAT_X_ARCSEC` double,
  `FEAT_Y_ARCSEC` double,
  `FEAT_HG_LONG_DEG` float,
  `FEAT_HG_LAT_DEG` float,
  `FEAT_CARR_LONG_DEG` float,
  `FEAT_CARR_LAT_DEG` float,
  `BR_X0_PIX` int(8),
  `BR_Y0_PIX` int(8),
  `BR_X1_PIX` int(8),
  `BR_Y1_PIX` int(8),
  `BR_X2_PIX` int(8),
  `BR_Y2_PIX` int(8),
  `BR_X3_PIX` int(8),
  `BR_Y3_PIX` int(8),
  `BR_X0_ARCSEC` double,
  `BR_Y0_ARCSEC` double,
  `BR_X1_ARCSEC` double,
  `BR_Y1_ARCSEC` double,
  `BR_X2_ARCSEC` double,
  `BR_Y2_ARCSEC` double,
  `BR_X3_ARCSEC` double,
  `BR_Y3_ARCSEC` double,
  `BR_HG_LONG0_DEG` float,
  `BR_HG_LAT0_DEG` float,
  `BR_HG_LONG1_DEG` float,
  `BR_HG_LAT1_DEG` float,
  `BR_HG_LONG2_DEG` float,
  `BR_HG_LAT2_DEG` float,
  `BR_HG_LONG3_DEG` float,
  `BR_HG_LAT3_DEG` float,
  `BR_CARR_LONG0_DEG` float,
  `BR_CARR_LAT0_DEG` float,
  `BR_CARR_LONG1_DEG` float,
  `BR_CARR_LAT1_DEG` float,
  `BR_CARR_LONG2_DEG` float,
  `BR_CARR_LAT2_DEG` float,
  `BR_CARR_LONG3_DEG` float,
  `BR_CARR_LAT3_DEG` float,
  `FEAT_AREA_PIX` int(11),
  `FEAT_AREA_DEG2` float,
  `FEAT_MAX_INT` float,
  `FEAT_MIN_INT` float,
  `FEAT_MEAN_INT` float,
  `FEAT_MEAN2QSUN` double,
  `CC_X_PIX` int(8),
  `CC_Y_PIX` int(8),
  `CC_X_ARCSEC` double,
  `CC_Y_ARCSEC` double,
  `CC` text,
  `CC_LENGTH` int(11),
  `SKE_LENGTH_DEG` float,
  `SKE_CURVATURE` float,
  `SKE_ORIENTATION` float,
  `FEAT_ELONG` float,
  `SKE_CC_X_PIX` int(8),
  `SKE_CC_Y_PIX` int(8),
  `SKE_CC_X_ARCSEC` double,
  `SKE_CC_Y_ARCSEC` double,
  `SKE_CC` text,
  `SKE_CC_LENGTH` int(11),
  `SNAPSHOT` varchar(400),
  `FEAT_FILENAME` varchar(150),
  `PR_LOCFNAME` varchar(150),
  `TRACK_ID` int(11),
  `PHENOM` int(6),
  `REF_FEAT` int(11),
  `TRACK_LVL_TRUST` int(6)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `VIEW_OBS_HQI`
--

DROP TABLE IF EXISTS `VIEW_OBS_HQI`;
/*!50001 DROP VIEW IF EXISTS `VIEW_OBS_HQI`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VIEW_OBS_HQI` (
  `OBSERVAT` varchar(255),
  `INSTRUME` varchar(150),
  `TELESCOP` varchar(150),
  `UNITS` varchar(100),
  `WAVEMIN` float,
  `WAVEMAX` float,
  `WAVENAME` varchar(50),
  `WAVEUNIT` varchar(10),
  `SPECTRAL_NAME` varchar(100),
  `OBS_TYPE` varchar(100),
  `DATE_OBS` datetime,
  `DATE_END` datetime,
  `JDINT` int(11),
  `JDFRAC` double,
  `C_ROTATION` int(7),
  `QUALITY` varchar(20),
  `FILENAME` varchar(100),
  `URL` text,
  `BITPIX` int(3),
  `BSCALE` double,
  `BZERO` double,
  `EXP_TIME` float,
  `CDELT1` double,
  `CDELT2` double,
  `NAXIS1` int(8),
  `NAXIS2` int(8),
  `CENTER_X` double,
  `CENTER_Y` double,
  `R_SUN` double,
  `FILE_FORMAT` varchar(20),
  `QCLK_URL` text,
  `QCLK_FNAME` varchar(200)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `VIEW_PP_HQI`
--

DROP TABLE IF EXISTS `VIEW_PP_HQI`;
/*!50001 DROP VIEW IF EXISTS `VIEW_PP_HQI`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VIEW_PP_HQI` (
  `OBSERVAT` varchar(255),
  `INSTRUME` varchar(150),
  `TELESCOP` varchar(150),
  `UNITS` varchar(100),
  `WAVEMIN` float,
  `WAVEMAX` float,
  `WAVENAME` varchar(50),
  `WAVEUNIT` varchar(10),
  `SPECTRAL_NAME` varchar(100),
  `OBS_TYPE` varchar(100),
  `DATE_OBS` datetime,
  `DATE_END` datetime,
  `JDINT` int(11),
  `JDFRAC` double,
  `C_ROTATION` int(7),
  `QUALITY` varchar(20),
  `FILENAME` varchar(100),
  `URL` text,
  `BITPIX` int(3),
  `BSCALE` double,
  `BZERO` double,
  `EXP_TIME` float,
  `CDELT1` double,
  `CDELT2` double,
  `NAXIS1` int(8),
  `NAXIS2` int(8),
  `CENTER_X` double,
  `CENTER_Y` double,
  `R_SUN` double,
  `FILE_FORMAT` varchar(20),
  `QCLK_URL` text,
  `QCLK_FNAME` varchar(200)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `VIEW_PRO_GUI`
--

DROP TABLE IF EXISTS `VIEW_PRO_GUI`;
/*!50001 DROP VIEW IF EXISTS `VIEW_PRO_GUI`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VIEW_PRO_GUI` (
  `ID_OBSERVATORY` int(11),
  `OBSERVAT` varchar(255),
  `INSTRUME` varchar(150),
  `TELESCOP` varchar(150),
  `DATE_OBS` datetime,
  `DATE_END` datetime,
  `FILENAME` varchar(150),
  `URL` text,
  `BITPIX` int(3),
  `CDELT1` double,
  `CDELT2` double,
  `NAXIS1` int(8),
  `NAXIS2` int(8),
  `CENTER_X` double,
  `CENTER_Y` double,
  `R_SUN` double,
  `ID_PROMINENCE` int(11),
  `FRC_INFO_ID` int(4),
  `PP_OUTPUT_ID` int(11),
  `BASE_MID_X_ARCSEC` double,
  `BASE_MID_Y_ARCSEC` double,
  `BASE_MID_X_PIX` int(8),
  `BASE_MID_Y_PIX` int(8),
  `BASE_MID_HG_LONG_DEG` float,
  `BASE_MID_HG_LAT_DEG` float,
  `BASE_MID_CARR_LONG_DEG` float,
  `BASE_MID_CARR_LAT_DEG` float,
  `BR_X0_ARCSEC` double,
  `BR_Y0_ARCSEC` double,
  `BR_X1_ARCSEC` double,
  `BR_Y1_ARCSEC` double,
  `BR_X2_ARCSEC` double,
  `BR_Y2_ARCSEC` double,
  `BR_X3_ARCSEC` double,
  `BR_Y3_ARCSEC` double,
  `BR_X0_PIX` int(8),
  `BR_Y0_PIX` int(8),
  `BR_X1_PIX` int(8),
  `BR_Y1_PIX` int(8),
  `BR_X2_PIX` int(8),
  `BR_Y2_PIX` int(8),
  `BR_X3_PIX` int(8),
  `BR_Y3_PIX` int(8),
  `FEAT_AREA_PIX` int(11),
  `FEAT_AREA_ARCSEC2` float,
  `MEAN_INT_RATIO` float,
  `FEAT_MAX_INT` float,
  `FEAT_MIN_INT` float,
  `FEAT_MEAN_INT` float,
  `DELTA_LAT_DEG` float,
  `FEAT_HEIGHT_ARCSEC` double,
  `FEAT_HEIGHT_KM` double,
  `BLOB_COUNT` int(8),
  `BLOB_SEPARATOR` varchar(1),
  `CC_X_PIX` text,
  `CC_Y_PIX` text,
  `CC_X_ARCSEC` text,
  `CC_Y_ARCSEC` text,
  `CC` text,
  `CC_LENGTH` text,
  `N_LEVEL` int(8),
  `LEVEL_SEPARATOR` varchar(1),
  `RS` text,
  `RS_LENGTH` int(11),
  `LEV_MIN_INT` text,
  `LEV_MAX_INT` text,
  `LEV_MEAN_INT` text,
  `SNAPSHOT_FN` varchar(200),
  `SNAPSHOT_PATH` varchar(200),
  `PR_LOCFNAME` varchar(150),
  `FEAT_FILENAME` varchar(150),
  `HELIO_PRO_NUMBER` int(11),
  `RUN_DATE` datetime
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `VIEW_PRO_HQI`
--

DROP TABLE IF EXISTS `VIEW_PRO_HQI`;
/*!50001 DROP VIEW IF EXISTS `VIEW_PRO_HQI`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VIEW_PRO_HQI` (
  `INSTITUT` varchar(150),
  `CODE` varchar(100),
  `VERSION` varchar(50),
  `FEATURE_NAME` varchar(100),
  `ENC_MET` varchar(50),
  `PERSON` varchar(150),
  `CONTACT` varchar(150),
  `REFERENCE` varchar(150),
  `OBSERVAT` varchar(255),
  `INSTRUME` varchar(150),
  `TELESCOP` varchar(150),
  `UNITS` varchar(100),
  `WAVEMIN` float,
  `WAVEMAX` float,
  `WAVENAME` varchar(50),
  `WAVEUNIT` varchar(10),
  `SPECTRAL_NAME` varchar(100),
  `OBS_TYPE` varchar(100),
  `DATE_OBS` datetime,
  `DATE_END` datetime,
  `JDINT` int(11),
  `JDFRAC` double,
  `C_ROTATION` int(7),
  `FILENAME` varchar(100),
  `URL` text,
  `CDELT1` double,
  `CDELT2` double,
  `NAXIS1` int(8),
  `NAXIS2` int(8),
  `CENTER_X` double,
  `CENTER_Y` double,
  `R_SUN` double,
  `BITPIX` int(3),
  `BSCALE` double,
  `BZERO` double,
  `EXP_TIME` float,
  `FILE_FORMAT` varchar(20),
  `QCLK_URL` text,
  `QCLK_FNAME` varchar(200),
  `ID_PROMINENCE` int(11),
  `BASE_MID_X_PIX` int(8),
  `BASE_MID_Y_PIX` int(8),
  `BASE_MID_X_ARCSEC` double,
  `BASE_MID_Y_ARCSEC` double,
  `BASE_MID_HG_LONG_DEG` float,
  `BASE_MID_HG_LAT_DEG` float,
  `BASE_MID_CARR_LONG_DEG` float,
  `BASE_MID_CARR_LAT_DEG` float,
  `BR_X0_PIX` int(8),
  `BR_Y0_PIX` int(8),
  `BR_X1_PIX` int(8),
  `BR_Y1_PIX` int(8),
  `BR_X2_PIX` int(8),
  `BR_Y2_PIX` int(8),
  `BR_X3_PIX` int(8),
  `BR_Y3_PIX` int(8),
  `BR_X0_ARCSEC` double,
  `BR_Y0_ARCSEC` double,
  `BR_X1_ARCSEC` double,
  `BR_Y1_ARCSEC` double,
  `BR_X2_ARCSEC` double,
  `BR_Y2_ARCSEC` double,
  `BR_X3_ARCSEC` double,
  `BR_Y3_ARCSEC` double,
  `FEAT_AREA_PIX` int(11),
  `FEAT_AREA_ARCSEC2` float,
  `FEAT_MAX_INT` float,
  `FEAT_MEAN_INT` float,
  `FEAT_MIN_INT` float,
  `MEAN_INT_RATIO` float,
  `CC_X_PIX` text,
  `CC_Y_PIX` text,
  `CC_X_ARCSEC` text,
  `CC_Y_ARCSEC` text,
  `CC` text,
  `CC_LENGTH` text,
  `DELTA_LAT_DEG` float,
  `FEAT_HEIGHT_ARCSEC` double,
  `FEAT_HEIGHT_KM` double,
  `BLOB_COUNT` int(8),
  `BLOB_SEPARATOR` varchar(1),
  `N_LEVEL` int(8),
  `LEVEL_SEPARATOR` varchar(1),
  `RS` text,
  `RS_LENGTH` int(11),
  `LEV_MIN_INT` text,
  `LEV_MAX_INT` text,
  `LEV_MEAN_INT` text,
  `SNAPSHOT` varchar(400)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `VIEW_RS_GUI`
--

DROP TABLE IF EXISTS `VIEW_RS_GUI`;
/*!50001 DROP VIEW IF EXISTS `VIEW_RS_GUI`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VIEW_RS_GUI` (
  `ID_OBSERVATORY` int(11),
  `OBSERVAT` varchar(255),
  `INSTRUME` varchar(150),
  `TELESCOP` varchar(150),
  `UNITS` varchar(100),
  `DATE_OBS` datetime,
  `DATE_END` datetime,
  `FILENAME` varchar(100),
  `URL` text,
  `BITPIX` int(3),
  `CDELT1` double,
  `CDELT2` double,
  `NAXIS1` int(8),
  `NAXIS2` int(8),
  `CENTER_X` double,
  `CENTER_Y` double,
  `R_SUN` double,
  `ID_RS` int(11),
  `FRC_INFO_ID` int(4),
  `OBSERVATIONS_ID` int(11),
  `FEAT_X_PIX` int(8),
  `FEAT_Y_PIX` int(8),
  `FEAT_X_ARCSEC` double,
  `FEAT_Y_ARCSEC` double,
  `FEAT_HG_LONG_DEG` float,
  `FEAT_HG_LAT_DEG` float,
  `FEAT_CARR_LONG_DEG` float,
  `FEAT_CARR_LAT_DEG` float,
  `BR_X0_PIX` int(8),
  `BR_Y0_PIX` int(8),
  `BR_X1_PIX` int(8),
  `BR_Y1_PIX` int(8),
  `BR_X2_PIX` int(8),
  `BR_Y2_PIX` int(8),
  `BR_X3_PIX` int(8),
  `BR_Y3_PIX` int(8),
  `BR_X0_ARCSEC` double,
  `BR_Y0_ARCSEC` double,
  `BR_X1_ARCSEC` double,
  `BR_Y1_ARCSEC` double,
  `BR_X2_ARCSEC` double,
  `BR_Y2_ARCSEC` double,
  `BR_X3_ARCSEC` double,
  `BR_Y3_ARCSEC` double,
  `BR_HG_LONG0_DEG` float,
  `BR_HG_LAT0_DEG` float,
  `BR_HG_LONG1_DEG` float,
  `BR_HG_LAT1_DEG` float,
  `BR_HG_LONG2_DEG` float,
  `BR_HG_LAT2_DEG` float,
  `BR_HG_LONG3_DEG` float,
  `BR_HG_LAT3_DEG` float,
  `BR_CARR_LONG0_DEG` float,
  `BR_CARR_LAT0_DEG` float,
  `BR_CARR_LONG1_DEG` float,
  `BR_CARR_LAT1_DEG` float,
  `BR_CARR_LONG2_DEG` float,
  `BR_CARR_LAT2_DEG` float,
  `BR_CARR_LONG3_DEG` float,
  `BR_CARR_LAT3_DEG` float,
  `FEAT_MAX_INT` float,
  `FEAT_MEAN_INT` float,
  `FEAT_AREA_PIX` int(11),
  `FEAT_AREA_DEG2` float,
  `FEAT_AREA_MM2` float,
  `EL_AXIS1` float,
  `EL_AXIS2` float,
  `EL_ANGLE` float,
  `CC_X_PIX` int(8),
  `CC_Y_PIX` int(8),
  `CC_X_ARCSEC` double,
  `CC_Y_ARCSEC` double,
  `CC` text,
  `CC_LENGTH` int(11),
  `SNAPSHOT_FN` varchar(200),
  `SNAPSHOT_PATH` varchar(200),
  `FEAT_FILENAME` varchar(150),
  `HELIO_RS_NUMBER` int(11),
  `RUN_DATE` datetime
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `VIEW_RS_HQI`
--

DROP TABLE IF EXISTS `VIEW_RS_HQI`;
/*!50001 DROP VIEW IF EXISTS `VIEW_RS_HQI`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VIEW_RS_HQI` (
  `INSTITUT` varchar(150),
  `CODE` varchar(100),
  `VERSION` varchar(50),
  `FEATURE_NAME` varchar(100),
  `ENC_MET` varchar(50),
  `PERSON` varchar(150),
  `CONTACT` varchar(150),
  `REFERENCE` varchar(150),
  `OBSERVAT` varchar(255),
  `INSTRUME` varchar(150),
  `TELESCOP` varchar(150),
  `UNITS` varchar(100),
  `WAVEMIN` float,
  `WAVEMAX` float,
  `WAVENAME` varchar(50),
  `WAVEUNIT` varchar(10),
  `SPECTRAL_NAME` varchar(100),
  `OBS_TYPE` varchar(100),
  `DATE_OBS` datetime,
  `DATE_END` datetime,
  `JDINT` int(11),
  `JDFRAC` double,
  `C_ROTATION` int(7),
  `FILENAME` varchar(100),
  `URL` text,
  `CDELT1` double,
  `CDELT2` double,
  `NAXIS1` int(8),
  `NAXIS2` int(8),
  `CENTER_X` double,
  `CENTER_Y` double,
  `R_SUN` double,
  `BITPIX` int(3),
  `BSCALE` double,
  `BZERO` double,
  `EXP_TIME` float,
  `FILE_FORMAT` varchar(20),
  `QCLK_URL` text,
  `QCLK_FNAME` varchar(200),
  `ID_RS` int(11),
  `FEAT_X_PIX` int(8),
  `FEAT_Y_PIX` int(8),
  `FEAT_X_ARCSEC` double,
  `FEAT_Y_ARCSEC` double,
  `FEAT_HG_LONG_DEG` float,
  `FEAT_HG_LAT_DEG` float,
  `FEAT_CARR_LONG_DEG` float,
  `FEAT_CARR_LAT_DEG` float,
  `BR_X0_PIX` int(8),
  `BR_Y0_PIX` int(8),
  `BR_X1_PIX` int(8),
  `BR_Y1_PIX` int(8),
  `BR_X2_PIX` int(8),
  `BR_Y2_PIX` int(8),
  `BR_X3_PIX` int(8),
  `BR_Y3_PIX` int(8),
  `BR_X0_ARCSEC` double,
  `BR_Y0_ARCSEC` double,
  `BR_X1_ARCSEC` double,
  `BR_Y1_ARCSEC` double,
  `BR_X2_ARCSEC` double,
  `BR_Y2_ARCSEC` double,
  `BR_X3_ARCSEC` double,
  `BR_Y3_ARCSEC` double,
  `BR_HG_LONG0_DEG` float,
  `BR_HG_LAT0_DEG` float,
  `BR_HG_LONG1_DEG` float,
  `BR_HG_LAT1_DEG` float,
  `BR_HG_LONG2_DEG` float,
  `BR_HG_LAT2_DEG` float,
  `BR_HG_LONG3_DEG` float,
  `BR_HG_LAT3_DEG` float,
  `BR_CARR_LONG0_DEG` float,
  `BR_CARR_LAT0_DEG` float,
  `BR_CARR_LONG1_DEG` float,
  `BR_CARR_LAT1_DEG` float,
  `BR_CARR_LONG2_DEG` float,
  `BR_CARR_LAT2_DEG` float,
  `BR_CARR_LONG3_DEG` float,
  `BR_CARR_LAT3_DEG` float,
  `FEAT_AREA_PIX` int(11),
  `FEAT_AREA_DEG2` float,
  `FEAT_AREA_MM2` float,
  `FEAT_MAX_INT` float,
  `FEAT_MEAN_INT` float,
  `EL_AXIS1` float,
  `EL_AXIS2` float,
  `EL_ANGLE` float,
  `CC_X_PIX` int(8),
  `CC_Y_PIX` int(8),
  `CC_X_ARCSEC` double,
  `CC_Y_ARCSEC` double,
  `CC` text,
  `CC_LENGTH` int(11),
  `SNAPSHOT` varchar(400)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `VIEW_SP_GUI`
--

DROP TABLE IF EXISTS `VIEW_SP_GUI`;
/*!50001 DROP VIEW IF EXISTS `VIEW_SP_GUI`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VIEW_SP_GUI` (
  `ID_OBSERVATORY` int(11),
  `OBSERVAT` varchar(255),
  `INSTRUME` varchar(150),
  `TELESCOP` varchar(150),
  `DATE_OBS` datetime,
  `DATE_END` datetime,
  `FILENAME` varchar(100),
  `URL` text,
  `BITPIX` int(3),
  `CDELT1` double,
  `CDELT2` double,
  `NAXIS1` int(8),
  `NAXIS2` int(8),
  `CENTER_X` double,
  `CENTER_Y` double,
  `R_SUN` double,
  `NOAA_NUMBER` int(11),
  `ID_SUNSPOT` int(11),
  `FRC_INFO_ID` int(11),
  `OBSERVATIONS_ID_IC` int(11),
  `OBSERVATIONS_ID_M` int(11),
  `FEAT_X_ARCSEC` double,
  `FEAT_Y_ARCSEC` double,
  `FEAT_X_PIX` int(8),
  `FEAT_Y_PIX` int(8),
  `FEAT_HG_LONG_DEG` float,
  `FEAT_HG_LAT_DEG` float,
  `FEAT_CARR_LONG_DEG` float,
  `FEAT_CARR_LAT_DEG` float,
  `BR_X0_ARCSEC` double,
  `BR_Y0_ARCSEC` double,
  `BR_X1_ARCSEC` double,
  `BR_Y1_ARCSEC` double,
  `BR_X2_ARCSEC` double,
  `BR_Y2_ARCSEC` double,
  `BR_X3_ARCSEC` double,
  `BR_Y3_ARCSEC` double,
  `BR_X0_PIX` int(8),
  `BR_Y0_PIX` int(8),
  `BR_X1_PIX` int(8),
  `BR_Y1_PIX` int(8),
  `BR_X2_PIX` int(8),
  `BR_Y2_PIX` int(8),
  `BR_X3_PIX` int(8),
  `BR_Y3_PIX` int(8),
  `BR_HG_LONG0_DEG` float,
  `BR_HG_LAT0_DEG` float,
  `BR_HG_LONG1_DEG` float,
  `BR_HG_LAT1_DEG` float,
  `BR_HG_LONG2_DEG` float,
  `BR_HG_LAT2_DEG` float,
  `BR_HG_LONG3_DEG` float,
  `BR_HG_LAT3_DEG` float,
  `BR_CARR_LONG0_DEG` float,
  `BR_CARR_LAT0_DEG` float,
  `BR_CARR_LONG1_DEG` float,
  `BR_CARR_LAT1_DEG` float,
  `BR_CARR_LONG2_DEG` float,
  `BR_CARR_LAT2_DEG` float,
  `BR_CARR_LONG3_DEG` float,
  `BR_CARR_LAT3_DEG` float,
  `FEAT_AREA_PIX` int(11),
  `FEAT_AREA_DEG2` float,
  `FEAT_AREA_MM2` float,
  `FEAT_DIAM_DEG` float,
  `FEAT_DIAM_MM` float,
  `FEAT_MEAN2QSUN` double,
  `FEAT_MAX_INT` float,
  `FEAT_MIN_INT` float,
  `FEAT_MEAN_INT` float,
  `FEAT_TOT_BZ` float,
  `FEAT_ABS_BZ` float,
  `FEAT_MAX_BZ` float,
  `FEAT_MIN_BZ` float,
  `FEAT_MEAN_BZ` float,
  `UMBRA_NUMBER` int(4),
  `UMBRA_AREA_PIX` int(11),
  `UMBRA_AREA_MM2` float,
  `UMBRA_AREA_DEG2` float,
  `UMBRA_DIAM_DEG` float,
  `UMBRA_DIAM_MM` float,
  `UMBRA_MAX_INT` float,
  `UMBRA_MIN_INT` float,
  `UMBRA_MEAN_INT` float,
  `UMBRA_TOT_BZ` float,
  `UMBRA_ABS_BZ` float,
  `UMBRA_MAX_BZ` float,
  `UMBRA_MIN_BZ` float,
  `UMBRA_MEAN_BZ` float,
  `CC_X_PIX` int(8),
  `CC_Y_PIX` int(8),
  `CC_X_ARCSEC` double,
  `CC_Y_ARCSEC` double,
  `CC` text,
  `CC_LENGTH` int(11),
  `RS` text,
  `RS_LENGTH` int(11),
  `SNAPSHOT_FN` varchar(200),
  `SNAPSHOT_PATH` varchar(200),
  `HELIO_SS_NUMBER` int(11),
  `FEAT_FILENAME` varchar(150),
  `RUN_DATE` datetime
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `VIEW_SP_HQI`
--

DROP TABLE IF EXISTS `VIEW_SP_HQI`;
/*!50001 DROP VIEW IF EXISTS `VIEW_SP_HQI`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VIEW_SP_HQI` (
  `INSTITUT` varchar(150),
  `CODE` varchar(100),
  `VERSION` varchar(50),
  `FEATURE_NAME` varchar(100),
  `ENC_MET` varchar(50),
  `PERSON` varchar(150),
  `CONTACT` varchar(150),
  `REFERENCE` varchar(150),
  `OBSERVAT` varchar(255),
  `INSTRUME` varchar(150),
  `TELESCOP` varchar(150),
  `UNITS` varchar(100),
  `WAVEMIN` float,
  `WAVEMAX` float,
  `WAVENAME` varchar(50),
  `WAVEUNIT` varchar(10),
  `SPECTRAL_NAME` varchar(100),
  `OBS_TYPE` varchar(100),
  `DATE_OBS` datetime,
  `DATE_END` datetime,
  `JDINT` int(11),
  `JDFRAC` double,
  `C_ROTATION` int(7),
  `FILENAME` varchar(100),
  `URL` text,
  `CDELT1` double,
  `CDELT2` double,
  `NAXIS1` int(8),
  `NAXIS2` int(8),
  `CENTER_X` double,
  `CENTER_Y` double,
  `R_SUN` double,
  `BITPIX` int(3),
  `BSCALE` double,
  `BZERO` double,
  `EXP_TIME` float,
  `FILE_FORMAT` varchar(20),
  `QCLK_URL` text,
  `QCLK_FNAME` varchar(200),
  `ID_SUNSPOT` int(11),
  `NOAA_NUMBER` int(11),
  `FEAT_X_PIX` int(8),
  `FEAT_Y_PIX` int(8),
  `FEAT_X_ARCSEC` double,
  `FEAT_Y_ARCSEC` double,
  `FEAT_HG_LONG_DEG` float,
  `FEAT_HG_LAT_DEG` float,
  `FEAT_CARR_LONG_DEG` float,
  `FEAT_CARR_LAT_DEG` float,
  `BR_X0_PIX` int(8),
  `BR_Y0_PIX` int(8),
  `BR_X1_PIX` int(8),
  `BR_Y1_PIX` int(8),
  `BR_X2_PIX` int(8),
  `BR_Y2_PIX` int(8),
  `BR_X3_PIX` int(8),
  `BR_Y3_PIX` int(8),
  `BR_X0_ARCSEC` double,
  `BR_Y0_ARCSEC` double,
  `BR_X1_ARCSEC` double,
  `BR_Y1_ARCSEC` double,
  `BR_X2_ARCSEC` double,
  `BR_Y2_ARCSEC` double,
  `BR_X3_ARCSEC` double,
  `BR_Y3_ARCSEC` double,
  `BR_HG_LONG0_DEG` float,
  `BR_HG_LAT0_DEG` float,
  `BR_HG_LONG1_DEG` float,
  `BR_HG_LAT1_DEG` float,
  `BR_HG_LONG2_DEG` float,
  `BR_HG_LAT2_DEG` float,
  `BR_HG_LONG3_DEG` float,
  `BR_HG_LAT3_DEG` float,
  `BR_CARR_LONG0_DEG` float,
  `BR_CARR_LAT0_DEG` float,
  `BR_CARR_LONG1_DEG` float,
  `BR_CARR_LAT1_DEG` float,
  `BR_CARR_LONG2_DEG` float,
  `BR_CARR_LAT2_DEG` float,
  `BR_CARR_LONG3_DEG` float,
  `BR_CARR_LAT3_DEG` float,
  `FEAT_AREA_PIX` int(11),
  `FEAT_AREA_DEG2` float,
  `FEAT_AREA_MM2` float,
  `FEAT_DIAM_DEG` float,
  `FEAT_DIAM_MM` float,
  `FEAT_MAX_INT` float,
  `FEAT_MIN_INT` float,
  `FEAT_MEAN_INT` float,
  `FEAT_MEAN2QSUN` double,
  `FEAT_MIN_BZ` float,
  `FEAT_MAX_BZ` float,
  `FEAT_MEAN_BZ` float,
  `FEAT_ABS_BZ` float,
  `FEAT_TOT_BZ` float,
  `UMBRA_NUMBER` int(4),
  `UMBRA_AREA_PIX` int(11),
  `UMBRA_AREA_MM2` float,
  `UMBRA_AREA_DEG2` float,
  `UMBRA_DIAM_DEG` float,
  `UMBRA_DIAM_MM` float,
  `UMBRA_MAX_INT` float,
  `UMBRA_MIN_INT` float,
  `UMBRA_MEAN_INT` float,
  `UMBRA_MIN_BZ` float,
  `UMBRA_MAX_BZ` float,
  `UMBRA_MEAN_BZ` float,
  `UMBRA_ABS_BZ` float,
  `UMBRA_TOT_BZ` float,
  `CC_X_PIX` int(8),
  `CC_Y_PIX` int(8),
  `CC_X_ARCSEC` double,
  `CC_Y_ARCSEC` double,
  `CC` text,
  `CC_LENGTH` int(11),
  `RS` text,
  `RS_LENGTH` int(11),
  `SNAPSHOT` varchar(400)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `VIEW_T2_GUI`
--

DROP TABLE IF EXISTS `VIEW_T2_GUI`;
/*!50001 DROP VIEW IF EXISTS `VIEW_T2_GUI`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VIEW_T2_GUI` (
  `ID_OBSERVATORY` int(11),
  `OBSERVAT` varchar(255),
  `INSTRUME` varchar(150),
  `TELESCOP` varchar(150),
  `UNITS` varchar(100),
  `DATE_OBS` datetime,
  `DATE_END` datetime,
  `FILENAME` varchar(100),
  `URL` text,
  `CDELT1` double,
  `CDELT2` double,
  `NAXIS1` int(8),
  `NAXIS2` int(8),
  `ID_TYPE_II` int(11),
  `FEAT_MAX_INT` float,
  `FEAT_MEAN_INT` float,
  `COMPONENT` varchar(1),
  `FEAT_FILENAME` varchar(150),
  `RS` text,
  `RS_LENGTH` int(11),
  `SKE_CC_X_PIX` int(8),
  `SKE_CC_Y_PIX` int(8),
  `SKE_CC_X_UTC` datetime,
  `SKE_CC_Y_MHZ` float,
  `SKE_CC` text,
  `SKE_CC_LENGTH` int(11)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `VIEW_T2_HQI`
--

DROP TABLE IF EXISTS `VIEW_T2_HQI`;
/*!50001 DROP VIEW IF EXISTS `VIEW_T2_HQI`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VIEW_T2_HQI` (
  `INSTITUT` varchar(150),
  `CODE` varchar(100),
  `VERSION` varchar(50),
  `FEATURE_NAME` varchar(100),
  `ENC_MET` varchar(50),
  `PERSON` varchar(150),
  `CONTACT` varchar(150),
  `REFERENCE` varchar(150),
  `OBSERVAT` varchar(255),
  `INSTRUME` varchar(150),
  `TELESCOP` varchar(150),
  `UNITS` varchar(100),
  `WAVEMIN` float,
  `WAVEMAX` float,
  `WAVENAME` varchar(50),
  `WAVEUNIT` varchar(10),
  `SPECTRAL_NAME` varchar(100),
  `OBS_TYPE` varchar(100),
  `DATE_OBS` datetime,
  `DATE_END` datetime,
  `JDINT` int(11),
  `JDFRAC` double,
  `C_ROTATION` int(7),
  `FILENAME` varchar(100),
  `URL` text,
  `CDELT1` double,
  `CDELT2` double,
  `NAXIS1` int(8),
  `NAXIS2` int(8),
  `CENTER_X` double,
  `CENTER_Y` double,
  `FILE_FORMAT` varchar(20),
  `QCLK_URL` text,
  `QCLK_FNAME` varchar(200),
  `ID_TYPE_II` int(11),
  `FEAT_MAX_INT` float,
  `FEAT_MEAN_INT` float,
  `COMPONENT` varchar(1),
  `FEAT_FILENAME` varchar(150),
  `SNAPSHOT` varchar(400),
  `RS` text,
  `RS_LENGTH` int(11),
  `SKE_CC_X_PIX` int(8),
  `SKE_CC_Y_PIX` int(8),
  `SKE_CC_X_UTC` datetime,
  `SKE_CC_Y_MHZ` float,
  `SKE_CC` text,
  `SKE_CC_LENGTH` int(11),
  `BR_X0_PIX` int(8),
  `BR_Y0_PIX` int(8),
  `BR_X1_PIX` int(8),
  `BR_Y1_PIX` int(8),
  `BR_X2_PIX` int(8),
  `BR_Y2_PIX` int(8),
  `BR_X3_PIX` int(8),
  `BR_Y3_PIX` int(8),
  `BR_X0_UTC` double,
  `BR_Y0_MHZ` float,
  `BR_X1_UTC` double,
  `BR_Y1_MHZ` float,
  `BR_X2_UTC` double,
  `BR_Y2_MHZ` float,
  `BR_X3_UTC` double,
  `BR_Y3_MHZ` float,
  `TIME_START` datetime,
  `TIME_END` datetime,
  `DRIFT_START` float,
  `DRIFT_END` float,
  `LVL_TRUST` int(8)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `VIEW_T3_GUI`
--

DROP TABLE IF EXISTS `VIEW_T3_GUI`;
/*!50001 DROP VIEW IF EXISTS `VIEW_T3_GUI`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VIEW_T3_GUI` (
  `ID_OBSERVATORY` int(11),
  `OBSERVAT` varchar(255),
  `INSTRUME` varchar(150),
  `TELESCOP` varchar(150),
  `UNITS` varchar(100),
  `DATE_OBS` datetime,
  `DATE_END` datetime,
  `FILENAME` varchar(100),
  `URL` text,
  `CDELT1` double,
  `CDELT2` double,
  `NAXIS1` int(8),
  `NAXIS2` int(8),
  `ID_TYPE_III` int(11),
  `FEAT_MAX_INT` float,
  `FEAT_MEAN_INT` float,
  `MULTIPLE` int(8),
  `FEAT_FILENAME` varchar(150),
  `CC_X_PIX` int(8),
  `CC_Y_PIX` int(8),
  `CC_X_UTC` double,
  `CC_Y_MHZ` float,
  `CC` text,
  `CC_LENGTH` int(11),
  `SKE_CC_X_PIX` int(8),
  `SKE_CC_Y_PIX` int(8),
  `SKE_CC_X_UTC` double,
  `SKE_CC_Y_MHZ` float,
  `SKE_CC` text,
  `SKE_CC_LENGTH` int(11)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `VIEW_T3_HQI`
--

DROP TABLE IF EXISTS `VIEW_T3_HQI`;
/*!50001 DROP VIEW IF EXISTS `VIEW_T3_HQI`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `VIEW_T3_HQI` (
  `INSTITUT` varchar(150),
  `CODE` varchar(100),
  `VERSION` varchar(50),
  `FEATURE_NAME` varchar(100),
  `ENC_MET` varchar(50),
  `PERSON` varchar(150),
  `CONTACT` varchar(150),
  `REFERENCE` varchar(150),
  `OBSERVAT` varchar(255),
  `INSTRUME` varchar(150),
  `TELESCOP` varchar(150),
  `UNITS` varchar(100),
  `WAVEMIN` float,
  `WAVEMAX` float,
  `WAVENAME` varchar(50),
  `WAVEUNIT` varchar(10),
  `SPECTRAL_NAME` varchar(100),
  `OBS_TYPE` varchar(100),
  `DATE_OBS` datetime,
  `DATE_END` datetime,
  `JDINT` int(11),
  `JDFRAC` double,
  `C_ROTATION` int(7),
  `FILENAME` varchar(100),
  `URL` text,
  `CDELT1` double,
  `CDELT2` double,
  `NAXIS1` int(8),
  `NAXIS2` int(8),
  `CENTER_X` double,
  `CENTER_Y` double,
  `FILE_FORMAT` varchar(20),
  `QCLK_URL` text,
  `QCLK_FNAME` varchar(200),
  `ID_TYPE_III` int(11),
  `FEAT_MAX_INT` float,
  `FEAT_MEAN_INT` float,
  `MULTIPLE` int(8),
  `FEAT_FILENAME` varchar(150),
  `SNAPSHOT` varchar(400),
  `CC_X_PIX` int(8),
  `CC_Y_PIX` int(8),
  `CC_X_UTC` double,
  `CC_Y_MHZ` float,
  `CC` text,
  `CC_LENGTH` int(11),
  `SKE_CC_X_PIX` int(8),
  `SKE_CC_Y_PIX` int(8),
  `SKE_CC_X_UTC` double,
  `SKE_CC_Y_MHZ` float,
  `SKE_CC` text,
  `SKE_CC_LENGTH` int(11),
  `BR_X0_PIX` int(8),
  `BR_Y0_PIX` int(8),
  `BR_X1_PIX` int(8),
  `BR_Y1_PIX` int(8),
  `BR_X2_PIX` int(8),
  `BR_Y2_PIX` int(8),
  `BR_X3_PIX` int(8),
  `BR_Y3_PIX` int(8),
  `BR_X0_UTC` double,
  `BR_Y0_MHZ` float,
  `BR_X1_UTC` double,
  `BR_Y1_MHZ` float,
  `BR_X2_UTC` double,
  `BR_Y2_MHZ` float,
  `BR_X3_UTC` double,
  `BR_Y3_MHZ` float,
  `TIME_START` datetime,
  `TIME_END` datetime,
  `DRIFT_START` float,
  `DRIFT_END` float,
  `FIT_A0` float,
  `FIT_A1` float,
  `LVL_TRUST` int(8)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `VIEW_AR_GUI`
--

/*!50001 DROP TABLE IF EXISTS `VIEW_AR_GUI`*/;
/*!50001 DROP VIEW IF EXISTS `VIEW_AR_GUI`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=CURRENT_USER SQL SECURITY DEFINER VIEW `VIEW_AR_GUI` AS select `t_oby`.`ID_OBSERVATORY` AS `ID_OBSERVATORY`,`t_oby`.`OBSERVAT` AS `OBSERVAT`,`t_oby`.`INSTRUME` AS `INSTRUME`,`t_oby`.`TELESCOP` AS `TELESCOP`,`t_obs`.`DATE_OBS` AS `DATE_OBS`,`t_obs`.`DATE_END` AS `DATE_END`,`t_obs`.`FILENAME` AS `FILENAME`,`t_obs`.`URL` AS `URL`,`t_obs`.`BITPIX` AS `BITPIX`,`t_obs`.`CDELT1` AS `CDELT1`,`t_obs`.`CDELT2` AS `CDELT2`,`t_obs`.`NAXIS1` AS `NAXIS1`,`t_obs`.`NAXIS2` AS `NAXIS2`,`t_obs`.`CENTER_X` AS `CENTER_X`,`t_obs`.`CENTER_Y` AS `CENTER_Y`,`t_obs`.`R_SUN` AS `R_SUN`,`t_noa`.`NOAA_NUMBER` AS `NOAA_NUMBER`,`t_ar`.`ID_AR` AS `ID_AR`,`t_ar`.`OBSERVATIONS_ID` AS `OBSERVATIONS_ID`,`t_ar`.`FEAT_X_PIX` AS `FEAT_X_PIX`,`t_ar`.`FEAT_Y_PIX` AS `FEAT_Y_PIX`,`t_ar`.`FEAT_X_ARCSEC` AS `FEAT_X_ARCSEC`,`t_ar`.`FEAT_Y_ARCSEC` AS `FEAT_Y_ARCSEC`,`t_ar`.`FEAT_HG_LONG_DEG` AS `FEAT_HG_LONG_DEG`,`t_ar`.`FEAT_HG_LAT_DEG` AS `FEAT_HG_LAT_DEG`,`t_ar`.`FEAT_CARR_LONG_DEG` AS `FEAT_CARR_LONG_DEG`,`t_ar`.`FEAT_CARR_LAT_DEG` AS `FEAT_CARR_LAT_DEG`,`t_ar`.`FEAT_AREA_PIX` AS `FEAT_AREA_PIX`,`t_ar`.`FEAT_AREA_DEG2` AS `FEAT_AREA_DEG2`,`t_ar`.`FEAT_AREA_MM2` AS `FEAT_AREA_MM2`,`t_ar`.`FEAT_MAX_INT` AS `FEAT_MAX_INT`,`t_ar`.`FEAT_MIN_INT` AS `FEAT_MIN_INT`,`t_ar`.`FEAT_MEAN_INT` AS `FEAT_MEAN_INT`,`t_ar`.`FEAT_MEAN2QSUN` AS `FEAT_MEAN2QSUN`,`t_ar`.`FEAT_MIN_BZ` AS `FEAT_MIN_BZ`,`t_ar`.`FEAT_MAX_BZ` AS `FEAT_MAX_BZ`,`t_ar`.`FEAT_MEAN_BZ` AS `FEAT_MEAN_BZ`,`t_ar`.`FEAT_LENGTH_NL` AS `FEAT_LENGTH_NL`,`t_ar`.`FEAT_LENGTH_SG` AS `FEAT_LENGTH_SG`,`t_ar`.`FEAT_MAX_GRAD` AS `FEAT_MAX_GRAD`,`t_ar`.`FEAT_MEAN_GRAD` AS `FEAT_MEAN_GRAD`,`t_ar`.`FEAT_MEDIAN_GRAD` AS `FEAT_MEDIAN_GRAD`,`t_ar`.`FEAT_RVAL` AS `FEAT_RVAL`,`t_ar`.`FEAT_WLSG` AS `FEAT_WLSG`,`t_ar`.`CC_X_PIX` AS `CC_X_PIX`,`t_ar`.`CC_Y_PIX` AS `CC_Y_PIX`,`t_ar`.`CC_X_ARCSEC` AS `CC_X_ARCSEC`,`t_ar`.`CC_Y_ARCSEC` AS `CC_Y_ARCSEC`,`t_ar`.`CC` AS `CC`,`t_ar`.`CC_LENGTH` AS `CC_LENGTH`,`t_ar`.`SNAPSHOT_PATH` AS `SNAPSHOT_PATH`,`t_ar`.`SNAPSHOT_FN` AS `SNAPSHOT_FN` from ((((`OBSERVATIONS` `t_obs` join `OBSERVATORY` `t_oby`) join `ACTIVEREGIONS` `t_ar`) left join `NOAA_AR` `t_noa_ar` on((`t_noa_ar`.`AR_ID` = `t_ar`.`ID_AR`))) left join `NOAA` `t_noa` on((`t_noa`.`ID_NOAA` = `t_noa_ar`.`NOAA_ID`))) where ((`t_obs`.`ID_OBSERVATIONS` = `t_ar`.`OBSERVATIONS_ID`) and (`t_obs`.`OBSERVATORY_ID` = `t_oby`.`ID_OBSERVATORY`)) */;

--
-- Final view structure for view `VIEW_AR_HQI`
--

/*!50001 DROP TABLE IF EXISTS `VIEW_AR_HQI`*/;
/*!50001 DROP VIEW IF EXISTS `VIEW_AR_HQI`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=CURRENT_USER SQL SECURITY DEFINER VIEW `VIEW_AR_HQI` AS select `t_frc`.`INSTITUT` AS `INSTITUT`,`t_frc`.`CODE` AS `CODE`,`t_frc`.`VERSION` AS `VERSION`,`t_frc`.`FEATURE_NAME` AS `FEATURE_NAME`,`t_frc`.`ENC_MET` AS `ENC_MET`,`t_frc`.`PERSON` AS `PERSON`,`t_frc`.`CONTACT` AS `CONTACT`,`t_frc`.`REFERENCE` AS `REFERENCE`,`t_oby`.`OBSERVAT` AS `OBSERVAT`,`t_oby`.`INSTRUME` AS `INSTRUME`,`t_oby`.`TELESCOP` AS `TELESCOP`,`t_oby`.`UNITS` AS `UNITS`,`t_oby`.`WAVEMIN` AS `WAVEMIN`,`t_oby`.`WAVEMAX` AS `WAVEMAX`,`t_oby`.`WAVENAME` AS `WAVENAME`,`t_oby`.`WAVEUNIT` AS `WAVEUNIT`,`t_oby`.`SPECTRAL_NAME` AS `SPECTRAL_NAME`,`t_oby`.`OBS_TYPE` AS `OBS_TYPE`,`t_obs`.`DATE_OBS` AS `DATE_OBS`,`t_obs`.`DATE_END` AS `DATE_END`,`t_obs`.`JDINT` AS `JDINT`,`t_obs`.`JDFRAC` AS `JDFRAC`,`t_obs`.`C_ROTATION` AS `C_ROTATION`,`t_obs`.`FILENAME` AS `FILENAME`,`t_obs`.`URL` AS `URL`,`t_obs`.`CDELT1` AS `CDELT1`,`t_obs`.`CDELT2` AS `CDELT2`,`t_obs`.`NAXIS1` AS `NAXIS1`,`t_obs`.`NAXIS2` AS `NAXIS2`,`t_obs`.`CENTER_X` AS `CENTER_X`,`t_obs`.`CENTER_Y` AS `CENTER_Y`,`t_obs`.`R_SUN` AS `R_SUN`,`t_obs`.`BITPIX` AS `BITPIX`,`t_obs`.`BSCALE` AS `BSCALE`,`t_obs`.`BZERO` AS `BZERO`,`t_obs`.`EXP_TIME` AS `EXP_TIME`,`t_obs`.`FILE_FORMAT` AS `FILE_FORMAT`,`t_obs`.`QCLK_URL` AS `QCLK_URL`,`t_obs`.`QCLK_FNAME` AS `QCLK_FNAME`,`t_rs`.`ID_AR` AS `ID_AR`,`t_noaa`.`NOAA_NUMBER` AS `NOAA_NUMBER`,`t_rs`.`FEAT_X_PIX` AS `FEAT_X_PIX`,`t_rs`.`FEAT_Y_PIX` AS `FEAT_Y_PIX`,`t_rs`.`FEAT_X_ARCSEC` AS `FEAT_X_ARCSEC`,`t_rs`.`FEAT_Y_ARCSEC` AS `FEAT_Y_ARCSEC`,`t_rs`.`FEAT_HG_LONG_DEG` AS `FEAT_HG_LONG_DEG`,`t_rs`.`FEAT_HG_LAT_DEG` AS `FEAT_HG_LAT_DEG`,`t_rs`.`FEAT_CARR_LONG_DEG` AS `FEAT_CARR_LONG_DEG`,`t_rs`.`FEAT_CARR_LAT_DEG` AS `FEAT_CARR_LAT_DEG`,`t_rs`.`BR_X0_PIX` AS `BR_X0_PIX`,`t_rs`.`BR_Y0_PIX` AS `BR_Y0_PIX`,`t_rs`.`BR_X1_PIX` AS `BR_X1_PIX`,`t_rs`.`BR_Y1_PIX` AS `BR_Y1_PIX`,`t_rs`.`BR_X2_PIX` AS `BR_X2_PIX`,`t_rs`.`BR_Y2_PIX` AS `BR_Y2_PIX`,`t_rs`.`BR_X3_PIX` AS `BR_X3_PIX`,`t_rs`.`BR_Y3_PIX` AS `BR_Y3_PIX`,`t_rs`.`BR_X0_ARCSEC` AS `BR_X0_ARCSEC`,`t_rs`.`BR_Y0_ARCSEC` AS `BR_Y0_ARCSEC`,`t_rs`.`BR_X1_ARCSEC` AS `BR_X1_ARCSEC`,`t_rs`.`BR_Y1_ARCSEC` AS `BR_Y1_ARCSEC`,`t_rs`.`BR_X2_ARCSEC` AS `BR_X2_ARCSEC`,`t_rs`.`BR_Y2_ARCSEC` AS `BR_Y2_ARCSEC`,`t_rs`.`BR_X3_ARCSEC` AS `BR_X3_ARCSEC`,`t_rs`.`BR_Y3_ARCSEC` AS `BR_Y3_ARCSEC`,`t_rs`.`BR_HG_LONG0_DEG` AS `BR_HG_LONG0_DEG`,`t_rs`.`BR_HG_LAT0_DEG` AS `BR_HG_LAT0_DEG`,`t_rs`.`BR_HG_LONG1_DEG` AS `BR_HG_LONG1_DEG`,`t_rs`.`BR_HG_LAT1_DEG` AS `BR_HG_LAT1_DEG`,`t_rs`.`BR_HG_LONG2_DEG` AS `BR_HG_LONG2_DEG`,`t_rs`.`BR_HG_LAT2_DEG` AS `BR_HG_LAT2_DEG`,`t_rs`.`BR_HG_LONG3_DEG` AS `BR_HG_LONG3_DEG`,`t_rs`.`BR_HG_LAT3_DEG` AS `BR_HG_LAT3_DEG`,`t_rs`.`BR_CARR_LONG0_DEG` AS `BR_CARR_LONG0_DEG`,`t_rs`.`BR_CARR_LAT0_DEG` AS `BR_CARR_LAT0_DEG`,`t_rs`.`BR_CARR_LONG1_DEG` AS `BR_CARR_LONG1_DEG`,`t_rs`.`BR_CARR_LAT1_DEG` AS `BR_CARR_LAT1_DEG`,`t_rs`.`BR_CARR_LONG2_DEG` AS `BR_CARR_LONG2_DEG`,`t_rs`.`BR_CARR_LAT2_DEG` AS `BR_CARR_LAT2_DEG`,`t_rs`.`BR_CARR_LONG3_DEG` AS `BR_CARR_LONG3_DEG`,`t_rs`.`BR_CARR_LAT3_DEG` AS `BR_CARR_LAT3_DEG`,`t_rs`.`FEAT_AREA_PIX` AS `FEAT_AREA_PIX`,`t_rs`.`FEAT_AREA_DEG2` AS `FEAT_AREA_DEG2`,`t_rs`.`FEAT_AREA_MM2` AS `FEAT_AREA_MM2`,`t_rs`.`FEAT_MAX_INT` AS `FEAT_MAX_INT`,`t_rs`.`FEAT_MIN_INT` AS `FEAT_MIN_INT`,`t_rs`.`FEAT_MEAN_INT` AS `FEAT_MEAN_INT`,`t_rs`.`FEAT_MEAN2QSUN` AS `FEAT_MEAN2QSUN`,`t_rs`.`FEAT_MIN_BZ` AS `FEAT_MIN_BZ`,`t_rs`.`FEAT_MAX_BZ` AS `FEAT_MAX_BZ`,`t_rs`.`FEAT_MEAN_BZ` AS `FEAT_MEAN_BZ`,`t_rs`.`FEAT_MEAN_GRAD` AS `FEAT_MEAN_GRAD`,`t_rs`.`FEAT_MEDIAN_GRAD` AS `FEAT_MEDIAN_GRAD`,`t_rs`.`FEAT_MAX_GRAD` AS `FEAT_MAX_GRAD`,`t_rs`.`FEAT_LENGTH_NL` AS `FEAT_LENGTH_NL`,`t_rs`.`FEAT_LENGTH_SG` AS `FEAT_LENGTH_SG`,`t_rs`.`CC_X_PIX` AS `CC_X_PIX`,`t_rs`.`CC_Y_PIX` AS `CC_Y_PIX`,`t_rs`.`CC_X_ARCSEC` AS `CC_X_ARCSEC`,`t_rs`.`CC_Y_ARCSEC` AS `CC_Y_ARCSEC`,`t_rs`.`CC` AS `CC`,`t_rs`.`CC_LENGTH` AS `CC_LENGTH`,concat(`t_rs`.`SNAPSHOT_PATH`,`t_rs`.`SNAPSHOT_FN`) AS `SNAPSHOT` from (((((`OBSERVATIONS` `t_obs` join `OBSERVATORY` `t_oby`) join `ACTIVEREGIONS` `t_rs`) join `FRC_INFO` `t_frc`) left join `NOAA_AR` `t_noaa_ar` on((`t_noaa_ar`.`AR_ID` = `t_rs`.`ID_AR`))) left join `NOAA` `t_noaa` on((`t_noaa`.`ID_NOAA` = `t_noaa_ar`.`NOAA_ID`))) where ((`t_obs`.`ID_OBSERVATIONS` = `t_rs`.`OBSERVATIONS_ID`) and (`t_frc`.`ID_FRC_INFO` = `t_rs`.`FRC_INFO_ID`) and (`t_obs`.`OBSERVATORY_ID` = `t_oby`.`ID_OBSERVATORY`)) */;

--
-- Final view structure for view `VIEW_CH_GUI`
--

/*!50001 DROP TABLE IF EXISTS `VIEW_CH_GUI`*/;
/*!50001 DROP VIEW IF EXISTS `VIEW_CH_GUI`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=CURRENT_USER SQL SECURITY DEFINER VIEW `VIEW_CH_GUI` AS select `t_oby`.`ID_OBSERVATORY` AS `ID_OBSERVATORY`,`t_oby`.`OBSERVAT` AS `OBSERVAT`,`t_oby`.`INSTRUME` AS `INSTRUME`,`t_oby`.`TELESCOP` AS `TELESCOP`,`t_obs`.`DATE_OBS` AS `DATE_OBS`,`t_obs`.`DATE_END` AS `DATE_END`,`t_obs`.`FILENAME` AS `FILENAME`,`t_obs`.`URL` AS `URL`,`t_obs`.`BITPIX` AS `BITPIX`,`t_obs`.`CDELT1` AS `CDELT1`,`t_obs`.`CDELT2` AS `CDELT2`,`t_obs`.`NAXIS1` AS `NAXIS1`,`t_obs`.`NAXIS2` AS `NAXIS2`,`t_obs`.`CENTER_X` AS `CENTER_X`,`t_obs`.`CENTER_Y` AS `CENTER_Y`,`t_obs`.`R_SUN` AS `R_SUN`,`t_ch`.`ID_CORONALHOLES` AS `ID_CORONALHOLES`,`t_ch`.`OBSERVATIONS_ID_EIT` AS `OBSERVATIONS_ID`,`t_ch`.`FEAT_X_PIX` AS `FEAT_X_PIX`,`t_ch`.`FEAT_Y_PIX` AS `FEAT_Y_PIX`,`t_ch`.`FEAT_X_ARCSEC` AS `FEAT_X_ARCSEC`,`t_ch`.`FEAT_Y_ARCSEC` AS `FEAT_Y_ARCSEC`,`t_ch`.`FEAT_HG_LONG_DEG` AS `FEAT_HG_LONG_DEG`,`t_ch`.`FEAT_HG_LAT_DEG` AS `FEAT_HG_LAT_DEG`,`t_ch`.`FEAT_CARR_LONG_DEG` AS `FEAT_CARR_LONG_DEG`,`t_ch`.`FEAT_CARR_LAT_DEG` AS `FEAT_CARR_LAT_DEG`,`t_ch`.`BR_HG_LONG3_DEG` AS `BR_HG_LONG3_DEG`,`t_ch`.`BR_CARR_LONG2_DEG` AS `BR_CARR_LONG2_DEG`,`t_ch`.`BR_CARR_LAT2_DEG` AS `BR_CARR_LAT2_DEG`,`t_ch`.`BR_CARR_LONG3_DEG` AS `BR_CARR_LONG3_DEG`,`t_ch`.`BR_CARR_LAT3_DEG` AS `BR_CARR_LAT3_DEG`,`t_ch`.`FEAT_WIDTH_X_ARCSEC` AS `FEAT_WIDTH_X_ARCSEC`,`t_ch`.`FEAT_WIDTH_Y_ARCSEC` AS `FEAT_WIDTH_Y_ARCSEC`,`t_ch`.`FEAT_WIDTH_HG_LONG_DEG` AS `FEAT_WIDTH_HG_LONG_DEG`,`t_ch`.`FEAT_WIDTH_HG_LAT_DEG` AS `FEAT_WIDTH_HG_LAT_DEG`,`t_ch`.`FEAT_AREA_PIX` AS `FEAT_AREA_PIX`,`t_ch`.`FEAT_AREA_DEG2` AS `FEAT_AREA_DEG2`,`t_ch`.`FEAT_AREA_MM2` AS `FEAT_AREA_MM2`,`t_ch`.`FEAT_MAX_INT` AS `FEAT_MAX_INT`,`t_ch`.`FEAT_MIN_INT` AS `FEAT_MIN_INT`,`t_ch`.`FEAT_MEAN_INT` AS `FEAT_MEAN_INT`,`t_ch`.`FEAT_MEAN2QSUN` AS `FEAT_MEAN2QSUN`,`t_ch`.`FEAT_MIN_BZ` AS `FEAT_MIN_BZ`,`t_ch`.`FEAT_MAX_BZ` AS `FEAT_MAX_BZ`,`t_ch`.`FEAT_MEAN_BZ` AS `FEAT_MEAN_BZ`,`t_ch`.`FEAT_SKEW_BZ` AS `FEAT_SKEW_BZ`,`t_ch`.`CC_X_PIX` AS `CC_X_PIX`,`t_ch`.`CC_Y_PIX` AS `CC_Y_PIX`,`t_ch`.`CC_X_ARCSEC` AS `CC_X_ARCSEC`,`t_ch`.`CC_Y_ARCSEC` AS `CC_Y_ARCSEC`,`t_ch`.`CC` AS `CC`,`t_ch`.`CC_LENGTH` AS `CC_LENGTH`,`t_ch`.`SNAPSHOT_PATH` AS `SNAPSHOT_PATH`,`t_ch`.`SNAPSHOT_FN` AS `SNAPSHOT_FN` from ((`OBSERVATIONS` `t_obs` join `OBSERVATORY` `t_oby`) join `CORONALHOLES` `t_ch`) where ((`t_obs`.`ID_OBSERVATIONS` = `t_ch`.`OBSERVATIONS_ID_EIT`) and (`t_obs`.`OBSERVATORY_ID` = `t_oby`.`ID_OBSERVATORY`)) */;

--
-- Final view structure for view `VIEW_CH_HQI`
--

/*!50001 DROP TABLE IF EXISTS `VIEW_CH_HQI`*/;
/*!50001 DROP VIEW IF EXISTS `VIEW_CH_HQI`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=CURRENT_USER SQL SECURITY DEFINER VIEW `VIEW_CH_HQI` AS select `t_frc`.`INSTITUT` AS `INSTITUT`,`t_frc`.`CODE` AS `CODE`,`t_frc`.`VERSION` AS `VERSION`,`t_frc`.`FEATURE_NAME` AS `FEATURE_NAME`,`t_frc`.`ENC_MET` AS `ENC_MET`,`t_frc`.`PERSON` AS `PERSON`,`t_frc`.`CONTACT` AS `CONTACT`,`t_frc`.`REFERENCE` AS `REFERENCE`,`t_oby`.`OBSERVAT` AS `OBSERVAT`,`t_oby`.`INSTRUME` AS `INSTRUME`,`t_oby`.`TELESCOP` AS `TELESCOP`,`t_oby`.`UNITS` AS `UNITS`,`t_oby`.`WAVEMIN` AS `WAVEMIN`,`t_oby`.`WAVEMAX` AS `WAVEMAX`,`t_oby`.`WAVENAME` AS `WAVENAME`,`t_oby`.`WAVEUNIT` AS `WAVEUNIT`,`t_oby`.`SPECTRAL_NAME` AS `SPECTRAL_NAME`,`t_oby`.`OBS_TYPE` AS `OBS_TYPE`,`t_obs`.`DATE_OBS` AS `DATE_OBS`,`t_obs`.`DATE_END` AS `DATE_END`,`t_obs`.`JDINT` AS `JDINT`,`t_obs`.`JDFRAC` AS `JDFRAC`,`t_obs`.`C_ROTATION` AS `C_ROTATION`,`t_obs`.`FILENAME` AS `FILENAME`,`t_obs`.`URL` AS `URL`,`t_obs`.`CDELT1` AS `CDELT1`,`t_obs`.`CDELT2` AS `CDELT2`,`t_obs`.`NAXIS1` AS `NAXIS1`,`t_obs`.`NAXIS2` AS `NAXIS2`,`t_obs`.`CENTER_X` AS `CENTER_X`,`t_obs`.`CENTER_Y` AS `CENTER_Y`,`t_obs`.`R_SUN` AS `R_SUN`,`t_obs`.`BITPIX` AS `BITPIX`,`t_obs`.`BSCALE` AS `BSCALE`,`t_obs`.`BZERO` AS `BZERO`,`t_obs`.`EXP_TIME` AS `EXP_TIME`,`t_obs`.`FILE_FORMAT` AS `FILE_FORMAT`,`t_obs`.`QCLK_URL` AS `QCLK_URL`,`t_obs`.`QCLK_FNAME` AS `QCLK_FNAME`,`t_ch`.`ID_CORONALHOLES` AS `ID_CORONALHOLES`,`t_ch`.`FEAT_X_PIX` AS `FEAT_X_PIX`,`t_ch`.`FEAT_Y_PIX` AS `FEAT_Y_PIX`,`t_ch`.`FEAT_X_ARCSEC` AS `FEAT_X_ARCSEC`,`t_ch`.`FEAT_Y_ARCSEC` AS `FEAT_Y_ARCSEC`,`t_ch`.`FEAT_HG_LONG_DEG` AS `FEAT_HG_LONG_DEG`,`t_ch`.`FEAT_HG_LAT_DEG` AS `FEAT_HG_LAT_DEG`,`t_ch`.`FEAT_CARR_LONG_DEG` AS `FEAT_CARR_LONG_DEG`,`t_ch`.`FEAT_CARR_LAT_DEG` AS `FEAT_CARR_LAT_DEG`,`t_ch`.`BR_X0_PIX` AS `BR_X0_PIX`,`t_ch`.`BR_Y0_PIX` AS `BR_Y0_PIX`,`t_ch`.`BR_X1_PIX` AS `BR_X1_PIX`,`t_ch`.`BR_Y1_PIX` AS `BR_Y1_PIX`,`t_ch`.`BR_X2_PIX` AS `BR_X2_PIX`,`t_ch`.`BR_Y2_PIX` AS `BR_Y2_PIX`,`t_ch`.`BR_X3_PIX` AS `BR_X3_PIX`,`t_ch`.`BR_Y3_PIX` AS `BR_Y3_PIX`,`t_ch`.`BR_X0_ARCSEC` AS `BR_X0_ARCSEC`,`t_ch`.`BR_Y0_ARCSEC` AS `BR_Y0_ARCSEC`,`t_ch`.`BR_X1_ARCSEC` AS `BR_X1_ARCSEC`,`t_ch`.`BR_Y1_ARCSEC` AS `BR_Y1_ARCSEC`,`t_ch`.`BR_X2_ARCSEC` AS `BR_X2_ARCSEC`,`t_ch`.`BR_Y2_ARCSEC` AS `BR_Y2_ARCSEC`,`t_ch`.`BR_X3_ARCSEC` AS `BR_X3_ARCSEC`,`t_ch`.`BR_Y3_ARCSEC` AS `BR_Y3_ARCSEC`,`t_ch`.`BR_HG_LONG0_DEG` AS `BR_HG_LONG0_DEG`,`t_ch`.`BR_HG_LAT0_DEG` AS `BR_HG_LAT0_DEG`,`t_ch`.`BR_HG_LONG1_DEG` AS `BR_HG_LONG1_DEG`,`t_ch`.`BR_HG_LAT1_DEG` AS `BR_HG_LAT1_DEG`,`t_ch`.`BR_HG_LONG2_DEG` AS `BR_HG_LONG2_DEG`,`t_ch`.`BR_HG_LAT2_DEG` AS `BR_HG_LAT2_DEG`,`t_ch`.`BR_HG_LONG3_DEG` AS `BR_HG_LONG3_DEG`,`t_ch`.`BR_HG_LAT3_DEG` AS `BR_HG_LAT3_DEG`,`t_ch`.`BR_CARR_LONG0_DEG` AS `BR_CARR_LONG0_DEG`,`t_ch`.`BR_CARR_LAT0_DEG` AS `BR_CARR_LAT0_DEG`,`t_ch`.`BR_CARR_LONG1_DEG` AS `BR_CARR_LONG1_DEG`,`t_ch`.`BR_CARR_LAT1_DEG` AS `BR_CARR_LAT1_DEG`,`t_ch`.`BR_CARR_LONG2_DEG` AS `BR_CARR_LONG2_DEG`,`t_ch`.`BR_CARR_LAT2_DEG` AS `BR_CARR_LAT2_DEG`,`t_ch`.`BR_CARR_LONG3_DEG` AS `BR_CARR_LONG3_DEG`,`t_ch`.`BR_CARR_LAT3_DEG` AS `BR_CARR_LAT3_DEG`,`t_ch`.`FEAT_WIDTH_X_ARCSEC` AS `FEAT_WIDTH_X_ARCSEC`,`t_ch`.`FEAT_WIDTH_Y_ARCSEC` AS `FEAT_WIDTH_Y_ARCSEC`,`t_ch`.`FEAT_WIDTH_HG_LONG_DEG` AS `FEAT_WIDTH_HG_LONG_DEG`,`t_ch`.`FEAT_WIDTH_HG_LAT_DEG` AS `FEAT_WIDTH_HG_LAT_DEG`,`t_ch`.`FEAT_AREA_PIX` AS `FEAT_AREA_PIX`,`t_ch`.`FEAT_AREA_DEG2` AS `FEAT_AREA_DEG2`,`t_ch`.`FEAT_AREA_MM2` AS `FEAT_AREA_MM2`,`t_ch`.`FEAT_MAX_INT` AS `FEAT_MAX_INT`,`t_ch`.`FEAT_MIN_INT` AS `FEAT_MIN_INT`,`t_ch`.`FEAT_MEAN_INT` AS `FEAT_MEAN_INT`,`t_ch`.`FEAT_MEAN2QSUN` AS `FEAT_MEAN2QSUN`,`t_ch`.`FEAT_MIN_BZ` AS `FEAT_MIN_BZ`,`t_ch`.`FEAT_MAX_BZ` AS `FEAT_MAX_BZ`,`t_ch`.`FEAT_MEAN_BZ` AS `FEAT_MEAN_BZ`,`t_ch`.`FEAT_SKEW_BZ` AS `FEAT_SKEW_BZ`,`t_ch`.`CC_X_PIX` AS `CC_X_PIX`,`t_ch`.`CC_Y_PIX` AS `CC_Y_PIX`,`t_ch`.`CC_X_ARCSEC` AS `CC_X_ARCSEC`,`t_ch`.`CC_Y_ARCSEC` AS `CC_Y_ARCSEC`,`t_ch`.`CC` AS `CC`,`t_ch`.`CC_LENGTH` AS `CC_LENGTH`,concat(`t_ch`.`SNAPSHOT_PATH`,`t_ch`.`SNAPSHOT_FN`) AS `SNAPSHOT` from (((`OBSERVATIONS` `t_obs` join `OBSERVATORY` `t_oby`) join `CORONALHOLES` `t_ch`) join `FRC_INFO` `t_frc`) where ((`t_obs`.`ID_OBSERVATIONS` = `t_ch`.`OBSERVATIONS_ID_EIT`) and (`t_frc`.`ID_FRC_INFO` = `t_ch`.`FRC_INFO_ID`) and (`t_obs`.`OBSERVATORY_ID` = `t_oby`.`ID_OBSERVATORY`)) */;

--
-- Final view structure for view `VIEW_FIL_GUI`
--

/*!50001 DROP TABLE IF EXISTS `VIEW_FIL_GUI`*/;
/*!50001 DROP VIEW IF EXISTS `VIEW_FIL_GUI`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=CURRENT_USER SQL SECURITY DEFINER VIEW `VIEW_FIL_GUI` AS select `t_oby`.`ID_OBSERVATORY` AS `ID_OBSERVATORY`,`t_oby`.`OBSERVAT` AS `OBSERVAT`,`t_oby`.`INSTRUME` AS `INSTRUM`,`t_oby`.`TELESCOP` AS `TELESCOP`,`t_obs`.`DATE_OBS` AS `DATE_OBS`,`t_obs`.`DATE_END` AS `DATE_END`,`t_ppo`.`PR_LOCFNAME` AS `FILENAME`,`t_ppo`.`URL` AS `URL`,`t_ppo`.`BITPIX` AS `BITPIX`,`t_ppo`.`CDELT1` AS `CDELT1`,`t_ppo`.`CDELT2` AS `CDELT2`,`t_ppo`.`NAXIS1` AS `NAXIS1`,`t_ppo`.`NAXIS2` AS `NAXIS2`,`t_ppo`.`CENTER_X` AS `CENTER_X`,`t_ppo`.`CENTER_Y` AS `CENTER_Y`,`t_ppo`.`R_SUN` AS `R_SUN`,`t_fil`.`ID_FIL` AS `ID_FIL`,`t_fil`.`FRC_INFO_ID` AS `FRC_INFO_ID`,`t_fil`.`PP_OUTPUT_ID` AS `PP_OUTPUT_ID`,`t_fil`.`FEAT_X_ARCSEC` AS `FEAT_X_ARCSEC`,`t_fil`.`FEAT_Y_ARCSEC` AS `FEAT_Y_ARCSEC`,`t_fil`.`FEAT_X_PIX` AS `FEAT_X_PIX`,`t_fil`.`FEAT_Y_PIX` AS `FEAT_Y_PIX`,`t_fil`.`FEAT_HG_LONG_DEG` AS `FEAT_HG_LONG_DEG`,`t_fil`.`FEAT_HG_LAT_DEG` AS `FEAT_HG_LAT_DEG`,`t_fil`.`FEAT_CARR_LONG_DEG` AS `FEAT_CARR_LONG_DEG`,`t_fil`.`FEAT_CARR_LAT_DEG` AS `FEAT_CARR_LAT_DEG`,`t_fil`.`BR_X0_ARCSEC` AS `BR_X0_ARCSEC`,`t_fil`.`BR_Y0_ARCSEC` AS `BR_Y0_ARCSEC`,`t_fil`.`BR_X1_ARCSEC` AS `BR_X1_ARCSEC`,`t_fil`.`BR_Y1_ARCSEC` AS `BR_Y1_ARCSEC`,`t_fil`.`BR_X2_ARCSEC` AS `BR_X2_ARCSEC`,`t_fil`.`BR_Y2_ARCSEC` AS `BR_Y2_ARCSEC`,`t_fil`.`BR_X3_ARCSEC` AS `BR_X3_ARCSEC`,`t_fil`.`BR_Y3_ARCSEC` AS `BR_Y3_ARCSEC`,`t_fil`.`BR_X0_PIX` AS `BR_X0_PIX`,`t_fil`.`BR_Y0_PIX` AS `BR_Y0_PIX`,`t_fil`.`BR_X1_PIX` AS `BR_X1_PIX`,`t_fil`.`BR_Y1_PIX` AS `BR_Y1_PIX`,`t_fil`.`BR_X2_PIX` AS `BR_X2_PIX`,`t_fil`.`BR_Y2_PIX` AS `BR_Y2_PIX`,`t_fil`.`BR_X3_PIX` AS `BR_X3_PIX`,`t_fil`.`BR_Y3_PIX` AS `BR_Y3_PIX`,`t_fil`.`BR_HG_LONG0_DEG` AS `BR_HG_LONG0_DEG`,`t_fil`.`BR_HG_LAT0_DEG` AS `BR_HG_LAT0_DEG`,`t_fil`.`BR_HG_LONG1_DEG` AS `BR_HG_LONG1_DEG`,`t_fil`.`BR_HG_LAT1_DEG` AS `BR_HG_LAT1_DEG`,`t_fil`.`BR_HG_LONG2_DEG` AS `BR_HG_LONG2_DEG`,`t_fil`.`BR_HG_LAT2_DEG` AS `BR_HG_LAT2_DEG`,`t_fil`.`BR_HG_LONG3_DEG` AS `BR_HG_LONG3_DEG`,`t_fil`.`BR_HG_LAT3_DEG` AS `BR_HG_LAT3_DEG`,`t_fil`.`BR_CARR_LONG0_DEG` AS `BR_CARR_LONG0_DEG`,`t_fil`.`BR_CARR_LAT0_DEG` AS `BR_CARR_LAT0_DEG`,`t_fil`.`BR_CARR_LONG1_DEG` AS `BR_CARR_LONG1_DEG`,`t_fil`.`BR_CARR_LAT1_DEG` AS `BR_CARR_LAT1_DEG`,`t_fil`.`BR_CARR_LONG2_DEG` AS `BR_CARR_LONG2_DEG`,`t_fil`.`BR_CARR_LAT2_DEG` AS `BR_CARR_LAT2_DEG`,`t_fil`.`BR_CARR_LONG3_DEG` AS `BR_CARR_LONG3_DEG`,`t_fil`.`BR_CARR_LAT3_DEG` AS `BR_CARR_LAT3_DEG`,`t_fil`.`FEAT_AREA_PIX` AS `FEAT_AREA_PIX`,`t_fil`.`FEAT_AREA_MM2` AS `FEAT_AREA_MM2`,`t_fil`.`FEAT_AREA_DEG2` AS `FEAT_AREA_DEG2`,`t_fil`.`FEAT_MEAN2QSUN` AS `FEAT_MEAN2QSUN`,`t_fil`.`FEAT_MAX_INT` AS `FEAT_MAX_INT`,`t_fil`.`FEAT_MIN_INT` AS `FEAT_MIN_INT`,`t_fil`.`FEAT_MEAN_INT` AS `FEAT_MEAN_INT`,`t_fil`.`CC_X_PIX` AS `CC_X_PIX`,`t_fil`.`CC_Y_PIX` AS `CC_Y_PIX`,`t_fil`.`CC_X_ARCSEC` AS `CC_X_ARCSEC`,`t_fil`.`CC_Y_ARCSEC` AS `CC_Y_ARCSEC`,`t_fil`.`SKE_LENGTH_DEG` AS `SKE_LENGTH_DEG`,`t_fil`.`SKE_CURVATURE` AS `SKE_CURVATURE`,`t_fil`.`FEAT_ELONG` AS `FEAT_ELONG`,`t_fil`.`SKE_ORIENTATION` AS `SKE_ORIENTATION`,`t_fil`.`SKE_CC_X_PIX` AS `SKE_CC_X_PIX`,`t_fil`.`SKE_CC_Y_PIX` AS `SKE_CC_Y_PIX`,`t_fil`.`SKE_CC_X_ARCSEC` AS `SKE_CC_X_ARCSEC`,`t_fil`.`SKE_CC_Y_ARCSEC` AS `SKE_CC_Y_ARCSEC`,`t_fil`.`CC` AS `CC`,`t_fil`.`SKE_CC` AS `SKE_CC`,`t_fil`.`CC_LENGTH` AS `CC_LENGTH`,`t_fil`.`SKE_CC_LENGTH` AS `SKE_CC_LENGTH`,`t_fil`.`SNAPSHOT_FN` AS `SNAPSHOT_FN`,`t_fil`.`SNAPSHOT_PATH` AS `SNAPSHOT_PATH`,`t_fil`.`PR_LOCFNAME` AS `PR_LOCFNAME`,`t_fil`.`FEAT_FILENAME` AS `FEAT_FILENAME`,`t_fil`.`HELIO_FI_NUMBER` AS `HELIO_FI_NUMBER`,`t_fil`.`RUN_DATE` AS `RUN_DATE` from (((`OBSERVATORY` `t_oby` join `OBSERVATIONS` `t_obs`) join `PP_OUTPUT` `t_ppo`) join `FILAMENTS` `t_fil`) where ((`t_obs`.`ID_OBSERVATIONS` = `t_ppo`.`OBSERVATIONS_ID`) and (`t_obs`.`OBSERVATORY_ID` = `t_oby`.`ID_OBSERVATORY`) and (`t_fil`.`PP_OUTPUT_ID` = `t_ppo`.`ID_PP_OUTPUT`)) */;

--
-- Final view structure for view `VIEW_FIL_HQI`
--

/*!50001 DROP TABLE IF EXISTS `VIEW_FIL_HQI`*/;
/*!50001 DROP VIEW IF EXISTS `VIEW_FIL_HQI`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=CURRENT_USER SQL SECURITY DEFINER VIEW `VIEW_FIL_HQI` AS select `t_frc`.`INSTITUT` AS `INSTITUT`,`t_frc`.`CODE` AS `CODE`,`t_frc`.`VERSION` AS `VERSION`,`t_frc`.`FEATURE_NAME` AS `FEATURE_NAME`,`t_frc`.`ENC_MET` AS `ENC_MET`,`t_frc`.`PERSON` AS `PERSON`,`t_frc`.`CONTACT` AS `CONTACT`,`t_frc`.`REFERENCE` AS `REFERENCE`,`t_oby`.`OBSERVAT` AS `OBSERVAT`,`t_oby`.`INSTRUME` AS `INSTRUME`,`t_oby`.`TELESCOP` AS `TELESCOP`,`t_oby`.`UNITS` AS `UNITS`,`t_oby`.`WAVEMIN` AS `WAVEMIN`,`t_oby`.`WAVEMAX` AS `WAVEMAX`,`t_oby`.`WAVENAME` AS `WAVENAME`,`t_oby`.`WAVEUNIT` AS `WAVEUNIT`,`t_oby`.`SPECTRAL_NAME` AS `SPECTRAL_NAME`,`t_oby`.`OBS_TYPE` AS `OBS_TYPE`,`t_obs`.`DATE_OBS` AS `DATE_OBS`,`t_obs`.`DATE_END` AS `DATE_END`,`t_obs`.`JDINT` AS `JDINT`,`t_obs`.`JDFRAC` AS `JDFRAC`,`t_obs`.`C_ROTATION` AS `C_ROTATION`,`t_obs`.`FILENAME` AS `FILENAME`,`t_obs`.`URL` AS `URL`,`t_pp`.`CDELT1` AS `CDELT1`,`t_pp`.`CDELT2` AS `CDELT2`,`t_pp`.`NAXIS1` AS `NAXIS1`,`t_pp`.`NAXIS2` AS `NAXIS2`,`t_pp`.`CENTER_X` AS `CENTER_X`,`t_pp`.`CENTER_Y` AS `CENTER_Y`,`t_pp`.`R_SUN` AS `R_SUN`,`t_obs`.`BITPIX` AS `BITPIX`,`t_obs`.`BSCALE` AS `BSCALE`,`t_obs`.`BZERO` AS `BZERO`,`t_obs`.`EXP_TIME` AS `EXP_TIME`,`t_obs`.`FILE_FORMAT` AS `FILE_FORMAT`,`t_obs`.`QCLK_URL` AS `QCLK_URL`,`t_obs`.`QCLK_FNAME` AS `QCLK_FNAME`,`t_fil`.`ID_FIL` AS `ID_FIL`,`t_fil`.`FEAT_X_PIX` AS `FEAT_X_PIX`,`t_fil`.`FEAT_Y_PIX` AS `FEAT_Y_PIX`,`t_fil`.`FEAT_X_ARCSEC` AS `FEAT_X_ARCSEC`,`t_fil`.`FEAT_Y_ARCSEC` AS `FEAT_Y_ARCSEC`,`t_fil`.`FEAT_HG_LONG_DEG` AS `FEAT_HG_LONG_DEG`,`t_fil`.`FEAT_HG_LAT_DEG` AS `FEAT_HG_LAT_DEG`,`t_fil`.`FEAT_CARR_LONG_DEG` AS `FEAT_CARR_LONG_DEG`,`t_fil`.`FEAT_CARR_LAT_DEG` AS `FEAT_CARR_LAT_DEG`,`t_fil`.`BR_X0_PIX` AS `BR_X0_PIX`,`t_fil`.`BR_Y0_PIX` AS `BR_Y0_PIX`,`t_fil`.`BR_X1_PIX` AS `BR_X1_PIX`,`t_fil`.`BR_Y1_PIX` AS `BR_Y1_PIX`,`t_fil`.`BR_X2_PIX` AS `BR_X2_PIX`,`t_fil`.`BR_Y2_PIX` AS `BR_Y2_PIX`,`t_fil`.`BR_X3_PIX` AS `BR_X3_PIX`,`t_fil`.`BR_Y3_PIX` AS `BR_Y3_PIX`,`t_fil`.`BR_X0_ARCSEC` AS `BR_X0_ARCSEC`,`t_fil`.`BR_Y0_ARCSEC` AS `BR_Y0_ARCSEC`,`t_fil`.`BR_X1_ARCSEC` AS `BR_X1_ARCSEC`,`t_fil`.`BR_Y1_ARCSEC` AS `BR_Y1_ARCSEC`,`t_fil`.`BR_X2_ARCSEC` AS `BR_X2_ARCSEC`,`t_fil`.`BR_Y2_ARCSEC` AS `BR_Y2_ARCSEC`,`t_fil`.`BR_X3_ARCSEC` AS `BR_X3_ARCSEC`,`t_fil`.`BR_Y3_ARCSEC` AS `BR_Y3_ARCSEC`,`t_fil`.`BR_HG_LONG0_DEG` AS `BR_HG_LONG0_DEG`,`t_fil`.`BR_HG_LAT0_DEG` AS `BR_HG_LAT0_DEG`,`t_fil`.`BR_HG_LONG1_DEG` AS `BR_HG_LONG1_DEG`,`t_fil`.`BR_HG_LAT1_DEG` AS `BR_HG_LAT1_DEG`,`t_fil`.`BR_HG_LONG2_DEG` AS `BR_HG_LONG2_DEG`,`t_fil`.`BR_HG_LAT2_DEG` AS `BR_HG_LAT2_DEG`,`t_fil`.`BR_HG_LONG3_DEG` AS `BR_HG_LONG3_DEG`,`t_fil`.`BR_HG_LAT3_DEG` AS `BR_HG_LAT3_DEG`,`t_fil`.`BR_CARR_LONG0_DEG` AS `BR_CARR_LONG0_DEG`,`t_fil`.`BR_CARR_LAT0_DEG` AS `BR_CARR_LAT0_DEG`,`t_fil`.`BR_CARR_LONG1_DEG` AS `BR_CARR_LONG1_DEG`,`t_fil`.`BR_CARR_LAT1_DEG` AS `BR_CARR_LAT1_DEG`,`t_fil`.`BR_CARR_LONG2_DEG` AS `BR_CARR_LONG2_DEG`,`t_fil`.`BR_CARR_LAT2_DEG` AS `BR_CARR_LAT2_DEG`,`t_fil`.`BR_CARR_LONG3_DEG` AS `BR_CARR_LONG3_DEG`,`t_fil`.`BR_CARR_LAT3_DEG` AS `BR_CARR_LAT3_DEG`,`t_fil`.`FEAT_AREA_PIX` AS `FEAT_AREA_PIX`,`t_fil`.`FEAT_AREA_DEG2` AS `FEAT_AREA_DEG2`,`t_fil`.`FEAT_MAX_INT` AS `FEAT_MAX_INT`,`t_fil`.`FEAT_MIN_INT` AS `FEAT_MIN_INT`,`t_fil`.`FEAT_MEAN_INT` AS `FEAT_MEAN_INT`,`t_fil`.`FEAT_MEAN2QSUN` AS `FEAT_MEAN2QSUN`,`t_fil`.`CC_X_PIX` AS `CC_X_PIX`,`t_fil`.`CC_Y_PIX` AS `CC_Y_PIX`,`t_fil`.`CC_X_ARCSEC` AS `CC_X_ARCSEC`,`t_fil`.`CC_Y_ARCSEC` AS `CC_Y_ARCSEC`,`t_fil`.`CC` AS `CC`,`t_fil`.`CC_LENGTH` AS `CC_LENGTH`,`t_fil`.`SKE_LENGTH_DEG` AS `SKE_LENGTH_DEG`,`t_fil`.`SKE_CURVATURE` AS `SKE_CURVATURE`,`t_fil`.`SKE_ORIENTATION` AS `SKE_ORIENTATION`,`t_fil`.`FEAT_ELONG` AS `FEAT_ELONG`,`t_fil`.`SKE_CC_X_PIX` AS `SKE_CC_X_PIX`,`t_fil`.`SKE_CC_Y_PIX` AS `SKE_CC_Y_PIX`,`t_fil`.`SKE_CC_X_ARCSEC` AS `SKE_CC_X_ARCSEC`,`t_fil`.`SKE_CC_Y_ARCSEC` AS `SKE_CC_Y_ARCSEC`,`t_fil`.`SKE_CC` AS `SKE_CC`,`t_fil`.`SKE_CC_LENGTH` AS `SKE_CC_LENGTH`,concat(`t_fil`.`SNAPSHOT_PATH`,`t_fil`.`SNAPSHOT_FN`) AS `SNAPSHOT`,`t_fil`.`FEAT_FILENAME` AS `FEAT_FILENAME`,`t_fil`.`PR_LOCFNAME` AS `PR_LOCFNAME`,`t_tck`.`TRACK_ID` AS `TRACK_ID`,`t_tck`.`PHENOM` AS `PHENOM`,`t_tck`.`REF_FEAT` AS `REF_FEAT`,`t_tck`.`LVL_TRUST` AS `TRACK_LVL_TRUST` from (((((`OBSERVATIONS` `t_obs` join `OBSERVATORY` `t_oby`) join `PP_OUTPUT` `t_pp`) join `FILAMENTS` `t_fil`) join `FRC_INFO` `t_frc`) left join `FILAMENTS_TRACKING` `t_tck` on((`t_tck`.`FIL_ID` = `t_fil`.`ID_FIL`))) where ((`t_obs`.`ID_OBSERVATIONS` = `t_pp`.`OBSERVATIONS_ID`) and (`t_frc`.`ID_FRC_INFO` = `t_fil`.`FRC_INFO_ID`) and (`t_obs`.`OBSERVATORY_ID` = `t_oby`.`ID_OBSERVATORY`) and (`t_pp`.`ID_PP_OUTPUT` = `t_fil`.`PP_OUTPUT_ID`)) */;

--
-- Final view structure for view `VIEW_OBS_HQI`
--

/*!50001 DROP TABLE IF EXISTS `VIEW_OBS_HQI`*/;
/*!50001 DROP VIEW IF EXISTS `VIEW_OBS_HQI`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=CURRENT_USER SQL SECURITY DEFINER VIEW `VIEW_OBS_HQI` AS select `t_oby`.`OBSERVAT` AS `OBSERVAT`,`t_oby`.`INSTRUME` AS `INSTRUME`,`t_oby`.`TELESCOP` AS `TELESCOP`,`t_oby`.`UNITS` AS `UNITS`,`t_oby`.`WAVEMIN` AS `WAVEMIN`,`t_oby`.`WAVEMAX` AS `WAVEMAX`,`t_oby`.`WAVENAME` AS `WAVENAME`,`t_oby`.`WAVEUNIT` AS `WAVEUNIT`,`t_oby`.`SPECTRAL_NAME` AS `SPECTRAL_NAME`,`t_oby`.`OBS_TYPE` AS `OBS_TYPE`,`t_obs`.`DATE_OBS` AS `DATE_OBS`,`t_obs`.`DATE_END` AS `DATE_END`,`t_obs`.`JDINT` AS `JDINT`,`t_obs`.`JDFRAC` AS `JDFRAC`,`t_obs`.`C_ROTATION` AS `C_ROTATION`,`t_obs`.`QUALITY` AS `QUALITY`,`t_obs`.`FILENAME` AS `FILENAME`,`t_obs`.`URL` AS `URL`,`t_obs`.`BITPIX` AS `BITPIX`,`t_obs`.`BSCALE` AS `BSCALE`,`t_obs`.`BZERO` AS `BZERO`,`t_obs`.`EXP_TIME` AS `EXP_TIME`,`t_obs`.`CDELT1` AS `CDELT1`,`t_obs`.`CDELT2` AS `CDELT2`,`t_obs`.`NAXIS1` AS `NAXIS1`,`t_obs`.`NAXIS2` AS `NAXIS2`,`t_obs`.`CENTER_X` AS `CENTER_X`,`t_obs`.`CENTER_Y` AS `CENTER_Y`,`t_obs`.`R_SUN` AS `R_SUN`,`t_obs`.`FILE_FORMAT` AS `FILE_FORMAT`,`t_obs`.`QCLK_URL` AS `QCLK_URL`,`t_obs`.`QCLK_FNAME` AS `QCLK_FNAME` from (`OBSERVATIONS` `t_obs` join `OBSERVATORY` `t_oby`) where (`t_obs`.`OBSERVATORY_ID` = `t_oby`.`ID_OBSERVATORY`) */;

--
-- Final view structure for view `VIEW_PP_HQI`
--

/*!50001 DROP TABLE IF EXISTS `VIEW_PP_HQI`*/;
/*!50001 DROP VIEW IF EXISTS `VIEW_PP_HQI`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=CURRENT_USER SQL SECURITY DEFINER VIEW `VIEW_PP_HQI` AS select `t_oby`.`OBSERVAT` AS `OBSERVAT`,`t_oby`.`INSTRUME` AS `INSTRUME`,`t_oby`.`TELESCOP` AS `TELESCOP`,`t_oby`.`UNITS` AS `UNITS`,`t_oby`.`WAVEMIN` AS `WAVEMIN`,`t_oby`.`WAVEMAX` AS `WAVEMAX`,`t_oby`.`WAVENAME` AS `WAVENAME`,`t_oby`.`WAVEUNIT` AS `WAVEUNIT`,`t_oby`.`SPECTRAL_NAME` AS `SPECTRAL_NAME`,`t_oby`.`OBS_TYPE` AS `OBS_TYPE`,`t_obs`.`DATE_OBS` AS `DATE_OBS`,`t_obs`.`DATE_END` AS `DATE_END`,`t_obs`.`JDINT` AS `JDINT`,`t_obs`.`JDFRAC` AS `JDFRAC`,`t_obs`.`C_ROTATION` AS `C_ROTATION`,`t_obs`.`QUALITY` AS `QUALITY`,`t_obs`.`FILENAME` AS `FILENAME`,`t_obs`.`URL` AS `URL`,`t_obs`.`BITPIX` AS `BITPIX`,`t_obs`.`BSCALE` AS `BSCALE`,`t_obs`.`BZERO` AS `BZERO`,`t_obs`.`EXP_TIME` AS `EXP_TIME`,`t_pp`.`CDELT1` AS `CDELT1`,`t_pp`.`CDELT2` AS `CDELT2`,`t_pp`.`NAXIS1` AS `NAXIS1`,`t_pp`.`NAXIS2` AS `NAXIS2`,`t_pp`.`CENTER_X` AS `CENTER_X`,`t_pp`.`CENTER_Y` AS `CENTER_Y`,`t_pp`.`R_SUN` AS `R_SUN`,`t_obs`.`FILE_FORMAT` AS `FILE_FORMAT`,`t_obs`.`QCLK_URL` AS `QCLK_URL`,`t_obs`.`QCLK_FNAME` AS `QCLK_FNAME` from ((`OBSERVATIONS` `t_obs` join `OBSERVATORY` `t_oby`) join `PP_OUTPUT` `t_pp`) where ((`t_obs`.`OBSERVATORY_ID` = `t_oby`.`ID_OBSERVATORY`) and (`t_pp`.`OBSERVATIONS_ID` = `t_obs`.`ID_OBSERVATIONS`)) */;

--
-- Final view structure for view `VIEW_PRO_GUI`
--

/*!50001 DROP TABLE IF EXISTS `VIEW_PRO_GUI`*/;
/*!50001 DROP VIEW IF EXISTS `VIEW_PRO_GUI`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=CURRENT_USER SQL SECURITY DEFINER VIEW `VIEW_PRO_GUI` AS select `t_oby`.`ID_OBSERVATORY` AS `ID_OBSERVATORY`,`t_oby`.`OBSERVAT` AS `OBSERVAT`,`t_oby`.`INSTRUME` AS `INSTRUME`,`t_oby`.`TELESCOP` AS `TELESCOP`,`t_obs`.`DATE_OBS` AS `DATE_OBS`,`t_obs`.`DATE_END` AS `DATE_END`,`t_ppo`.`PR_LOCFNAME` AS `FILENAME`,`t_ppo`.`URL` AS `URL`,`t_ppo`.`BITPIX` AS `BITPIX`,`t_ppo`.`CDELT1` AS `CDELT1`,`t_ppo`.`CDELT2` AS `CDELT2`,`t_ppo`.`NAXIS1` AS `NAXIS1`,`t_ppo`.`NAXIS2` AS `NAXIS2`,`t_ppo`.`CENTER_X` AS `CENTER_X`,`t_ppo`.`CENTER_Y` AS `CENTER_Y`,`t_ppo`.`R_SUN` AS `R_SUN`,`t_pro`.`ID_PROMINENCE` AS `ID_PROMINENCE`,`t_pro`.`FRC_INFO_ID` AS `FRC_INFO_ID`,`t_pro`.`PP_OUTPUT_ID` AS `PP_OUTPUT_ID`,`t_pro`.`BASE_MID_X_ARCSEC` AS `BASE_MID_X_ARCSEC`,`t_pro`.`BASE_MID_Y_ARCSEC` AS `BASE_MID_Y_ARCSEC`,`t_pro`.`BASE_MID_X_PIX` AS `BASE_MID_X_PIX`,`t_pro`.`BASE_MID_Y_PIX` AS `BASE_MID_Y_PIX`,`t_pro`.`BASE_MID_HG_LONG_DEG` AS `BASE_MID_HG_LONG_DEG`,`t_pro`.`BASE_MID_HG_LAT_DEG` AS `BASE_MID_HG_LAT_DEG`,`t_pro`.`BASE_MID_CARR_LONG_DEG` AS `BASE_MID_CARR_LONG_DEG`,`t_pro`.`BASE_MID_CARR_LAT_DEG` AS `BASE_MID_CARR_LAT_DEG`,`t_pro`.`BR_X0_ARCSEC` AS `BR_X0_ARCSEC`,`t_pro`.`BR_Y0_ARCSEC` AS `BR_Y0_ARCSEC`,`t_pro`.`BR_X1_ARCSEC` AS `BR_X1_ARCSEC`,`t_pro`.`BR_Y1_ARCSEC` AS `BR_Y1_ARCSEC`,`t_pro`.`BR_X2_ARCSEC` AS `BR_X2_ARCSEC`,`t_pro`.`BR_Y2_ARCSEC` AS `BR_Y2_ARCSEC`,`t_pro`.`BR_X3_ARCSEC` AS `BR_X3_ARCSEC`,`t_pro`.`BR_Y3_ARCSEC` AS `BR_Y3_ARCSEC`,`t_pro`.`BR_X0_PIX` AS `BR_X0_PIX`,`t_pro`.`BR_Y0_PIX` AS `BR_Y0_PIX`,`t_pro`.`BR_X1_PIX` AS `BR_X1_PIX`,`t_pro`.`BR_Y1_PIX` AS `BR_Y1_PIX`,`t_pro`.`BR_X2_PIX` AS `BR_X2_PIX`,`t_pro`.`BR_Y2_PIX` AS `BR_Y2_PIX`,`t_pro`.`BR_X3_PIX` AS `BR_X3_PIX`,`t_pro`.`BR_Y3_PIX` AS `BR_Y3_PIX`,`t_pro`.`FEAT_AREA_PIX` AS `FEAT_AREA_PIX`,`t_pro`.`FEAT_AREA_ARCSEC2` AS `FEAT_AREA_ARCSEC2`,`t_pro`.`MEAN_INT_RATIO` AS `MEAN_INT_RATIO`,`t_pro`.`FEAT_MAX_INT` AS `FEAT_MAX_INT`,`t_pro`.`FEAT_MIN_INT` AS `FEAT_MIN_INT`,`t_pro`.`FEAT_MEAN_INT` AS `FEAT_MEAN_INT`,`t_pro`.`DELTA_LAT_DEG` AS `DELTA_LAT_DEG`,`t_pro`.`FEAT_HEIGHT_ARCSEC` AS `FEAT_HEIGHT_ARCSEC`,`t_pro`.`FEAT_HEIGHT_KM` AS `FEAT_HEIGHT_KM`,`t_pro`.`BLOB_COUNT` AS `BLOB_COUNT`,`t_pro`.`BLOB_SEPARATOR` AS `BLOB_SEPARATOR`,`t_pro`.`CC_X_PIX` AS `CC_X_PIX`,`t_pro`.`CC_Y_PIX` AS `CC_Y_PIX`,`t_pro`.`CC_X_ARCSEC` AS `CC_X_ARCSEC`,`t_pro`.`CC_Y_ARCSEC` AS `CC_Y_ARCSEC`,`t_pro`.`CC` AS `CC`,`t_pro`.`CC_LENGTH` AS `CC_LENGTH`,`t_pro`.`N_LEVEL` AS `N_LEVEL`,`t_pro`.`LEVEL_SEPARATOR` AS `LEVEL_SEPARATOR`,`t_pro`.`RS` AS `RS`,`t_pro`.`RS_LENGTH` AS `RS_LENGTH`,`t_pro`.`LEV_MIN_INT` AS `LEV_MIN_INT`,`t_pro`.`LEV_MAX_INT` AS `LEV_MAX_INT`,`t_pro`.`LEV_MEAN_INT` AS `LEV_MEAN_INT`,`t_pro`.`SNAPSHOT_FN` AS `SNAPSHOT_FN`,`t_pro`.`SNAPSHOT_PATH` AS `SNAPSHOT_PATH`,`t_pro`.`PR_LOCFNAME` AS `PR_LOCFNAME`,`t_pro`.`FEAT_FILENAME` AS `FEAT_FILENAME`,`t_pro`.`HELIO_PRO_NUMBER` AS `HELIO_PRO_NUMBER`,`t_pro`.`RUN_DATE` AS `RUN_DATE` from (((`OBSERVATORY` `t_oby` join `OBSERVATIONS` `t_obs`) join `PP_OUTPUT` `t_ppo`) join `PROMINENCES` `t_pro`) where ((`t_obs`.`ID_OBSERVATIONS` = `t_ppo`.`OBSERVATIONS_ID`) and (`t_obs`.`OBSERVATORY_ID` = `t_oby`.`ID_OBSERVATORY`) and (`t_pro`.`PP_OUTPUT_ID` = `t_ppo`.`ID_PP_OUTPUT`)) */;

--
-- Final view structure for view `VIEW_PRO_HQI`
--

/*!50001 DROP TABLE IF EXISTS `VIEW_PRO_HQI`*/;
/*!50001 DROP VIEW IF EXISTS `VIEW_PRO_HQI`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=CURRENT_USER SQL SECURITY DEFINER VIEW `VIEW_PRO_HQI` AS select `t_frc`.`INSTITUT` AS `INSTITUT`,`t_frc`.`CODE` AS `CODE`,`t_frc`.`VERSION` AS `VERSION`,`t_frc`.`FEATURE_NAME` AS `FEATURE_NAME`,`t_frc`.`ENC_MET` AS `ENC_MET`,`t_frc`.`PERSON` AS `PERSON`,`t_frc`.`CONTACT` AS `CONTACT`,`t_frc`.`REFERENCE` AS `REFERENCE`,`t_oby`.`OBSERVAT` AS `OBSERVAT`,`t_oby`.`INSTRUME` AS `INSTRUME`,`t_oby`.`TELESCOP` AS `TELESCOP`,`t_oby`.`UNITS` AS `UNITS`,`t_oby`.`WAVEMIN` AS `WAVEMIN`,`t_oby`.`WAVEMAX` AS `WAVEMAX`,`t_oby`.`WAVENAME` AS `WAVENAME`,`t_oby`.`WAVEUNIT` AS `WAVEUNIT`,`t_oby`.`SPECTRAL_NAME` AS `SPECTRAL_NAME`,`t_oby`.`OBS_TYPE` AS `OBS_TYPE`,`t_obs`.`DATE_OBS` AS `DATE_OBS`,`t_obs`.`DATE_END` AS `DATE_END`,`t_obs`.`JDINT` AS `JDINT`,`t_obs`.`JDFRAC` AS `JDFRAC`,`t_obs`.`C_ROTATION` AS `C_ROTATION`,`t_obs`.`FILENAME` AS `FILENAME`,`t_obs`.`URL` AS `URL`,`t_pp`.`CDELT1` AS `CDELT1`,`t_pp`.`CDELT2` AS `CDELT2`,`t_pp`.`NAXIS1` AS `NAXIS1`,`t_pp`.`NAXIS2` AS `NAXIS2`,`t_pp`.`CENTER_X` AS `CENTER_X`,`t_pp`.`CENTER_Y` AS `CENTER_Y`,`t_pp`.`R_SUN` AS `R_SUN`,`t_obs`.`BITPIX` AS `BITPIX`,`t_obs`.`BSCALE` AS `BSCALE`,`t_obs`.`BZERO` AS `BZERO`,`t_obs`.`EXP_TIME` AS `EXP_TIME`,`t_obs`.`FILE_FORMAT` AS `FILE_FORMAT`,`t_obs`.`QCLK_URL` AS `QCLK_URL`,`t_obs`.`QCLK_FNAME` AS `QCLK_FNAME`,`t_pro`.`ID_PROMINENCE` AS `ID_PROMINENCE`,`t_pro`.`BASE_MID_X_PIX` AS `BASE_MID_X_PIX`,`t_pro`.`BASE_MID_Y_PIX` AS `BASE_MID_Y_PIX`,`t_pro`.`BASE_MID_X_ARCSEC` AS `BASE_MID_X_ARCSEC`,`t_pro`.`BASE_MID_Y_ARCSEC` AS `BASE_MID_Y_ARCSEC`,`t_pro`.`BASE_MID_HG_LONG_DEG` AS `BASE_MID_HG_LONG_DEG`,`t_pro`.`BASE_MID_HG_LAT_DEG` AS `BASE_MID_HG_LAT_DEG`,`t_pro`.`BASE_MID_CARR_LONG_DEG` AS `BASE_MID_CARR_LONG_DEG`,`t_pro`.`BASE_MID_CARR_LAT_DEG` AS `BASE_MID_CARR_LAT_DEG`,`t_pro`.`BR_X0_PIX` AS `BR_X0_PIX`,`t_pro`.`BR_Y0_PIX` AS `BR_Y0_PIX`,`t_pro`.`BR_X1_PIX` AS `BR_X1_PIX`,`t_pro`.`BR_Y1_PIX` AS `BR_Y1_PIX`,`t_pro`.`BR_X2_PIX` AS `BR_X2_PIX`,`t_pro`.`BR_Y2_PIX` AS `BR_Y2_PIX`,`t_pro`.`BR_X3_PIX` AS `BR_X3_PIX`,`t_pro`.`BR_Y3_PIX` AS `BR_Y3_PIX`,`t_pro`.`BR_X0_ARCSEC` AS `BR_X0_ARCSEC`,`t_pro`.`BR_Y0_ARCSEC` AS `BR_Y0_ARCSEC`,`t_pro`.`BR_X1_ARCSEC` AS `BR_X1_ARCSEC`,`t_pro`.`BR_Y1_ARCSEC` AS `BR_Y1_ARCSEC`,`t_pro`.`BR_X2_ARCSEC` AS `BR_X2_ARCSEC`,`t_pro`.`BR_Y2_ARCSEC` AS `BR_Y2_ARCSEC`,`t_pro`.`BR_X3_ARCSEC` AS `BR_X3_ARCSEC`,`t_pro`.`BR_Y3_ARCSEC` AS `BR_Y3_ARCSEC`,`t_pro`.`FEAT_AREA_PIX` AS `FEAT_AREA_PIX`,`t_pro`.`FEAT_AREA_ARCSEC2` AS `FEAT_AREA_ARCSEC2`,`t_pro`.`FEAT_MAX_INT` AS `FEAT_MAX_INT`,`t_pro`.`FEAT_MEAN_INT` AS `FEAT_MEAN_INT`,`t_pro`.`FEAT_MIN_INT` AS `FEAT_MIN_INT`,`t_pro`.`MEAN_INT_RATIO` AS `MEAN_INT_RATIO`,`t_pro`.`CC_X_PIX` AS `CC_X_PIX`,`t_pro`.`CC_Y_PIX` AS `CC_Y_PIX`,`t_pro`.`CC_X_ARCSEC` AS `CC_X_ARCSEC`,`t_pro`.`CC_Y_ARCSEC` AS `CC_Y_ARCSEC`,`t_pro`.`CC` AS `CC`,`t_pro`.`CC_LENGTH` AS `CC_LENGTH`,`t_pro`.`DELTA_LAT_DEG` AS `DELTA_LAT_DEG`,`t_pro`.`FEAT_HEIGHT_ARCSEC` AS `FEAT_HEIGHT_ARCSEC`,`t_pro`.`FEAT_HEIGHT_KM` AS `FEAT_HEIGHT_KM`,`t_pro`.`BLOB_COUNT` AS `BLOB_COUNT`,`t_pro`.`BLOB_SEPARATOR` AS `BLOB_SEPARATOR`,`t_pro`.`N_LEVEL` AS `N_LEVEL`,`t_pro`.`LEVEL_SEPARATOR` AS `LEVEL_SEPARATOR`,`t_pro`.`RS` AS `RS`,`t_pro`.`RS_LENGTH` AS `RS_LENGTH`,`t_pro`.`LEV_MIN_INT` AS `LEV_MIN_INT`,`t_pro`.`LEV_MAX_INT` AS `LEV_MAX_INT`,`t_pro`.`LEV_MEAN_INT` AS `LEV_MEAN_INT`,concat(`t_pro`.`SNAPSHOT_PATH`,`t_pro`.`SNAPSHOT_FN`) AS `SNAPSHOT` from ((((`OBSERVATIONS` `t_obs` join `OBSERVATORY` `t_oby`) join `PP_OUTPUT` `t_pp`) join `PROMINENCES` `t_pro`) join `FRC_INFO` `t_frc`) where ((`t_obs`.`ID_OBSERVATIONS` = `t_pp`.`OBSERVATIONS_ID`) and (`t_frc`.`ID_FRC_INFO` = `t_pro`.`FRC_INFO_ID`) and (`t_obs`.`OBSERVATORY_ID` = `t_oby`.`ID_OBSERVATORY`) and (`t_pp`.`ID_PP_OUTPUT` = `t_pro`.`PP_OUTPUT_ID`)) */;

--
-- Final view structure for view `VIEW_RS_GUI`
--

/*!50001 DROP TABLE IF EXISTS `VIEW_RS_GUI`*/;
/*!50001 DROP VIEW IF EXISTS `VIEW_RS_GUI`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=CURRENT_USER SQL SECURITY DEFINER VIEW `VIEW_RS_GUI` AS select `t_oby`.`ID_OBSERVATORY` AS `ID_OBSERVATORY`,`t_oby`.`OBSERVAT` AS `OBSERVAT`,`t_oby`.`INSTRUME` AS `INSTRUME`,`t_oby`.`TELESCOP` AS `TELESCOP`,`t_oby`.`UNITS` AS `UNITS`,`t_obs`.`DATE_OBS` AS `DATE_OBS`,`t_obs`.`DATE_END` AS `DATE_END`,`t_obs`.`FILENAME` AS `FILENAME`,`t_obs`.`URL` AS `URL`,`t_obs`.`BITPIX` AS `BITPIX`,`t_obs`.`CDELT1` AS `CDELT1`,`t_obs`.`CDELT2` AS `CDELT2`,`t_obs`.`NAXIS1` AS `NAXIS1`,`t_obs`.`NAXIS2` AS `NAXIS2`,`t_obs`.`CENTER_X` AS `CENTER_X`,`t_obs`.`CENTER_Y` AS `CENTER_Y`,`t_obs`.`R_SUN` AS `R_SUN`,`t_rs`.`ID_RS` AS `ID_RS`,`t_rs`.`FRC_INFO_ID` AS `FRC_INFO_ID`,`t_rs`.`OBSERVATIONS_ID` AS `OBSERVATIONS_ID`,`t_rs`.`FEAT_X_PIX` AS `FEAT_X_PIX`,`t_rs`.`FEAT_Y_PIX` AS `FEAT_Y_PIX`,`t_rs`.`FEAT_X_ARCSEC` AS `FEAT_X_ARCSEC`,`t_rs`.`FEAT_Y_ARCSEC` AS `FEAT_Y_ARCSEC`,`t_rs`.`FEAT_HG_LONG_DEG` AS `FEAT_HG_LONG_DEG`,`t_rs`.`FEAT_HG_LAT_DEG` AS `FEAT_HG_LAT_DEG`,`t_rs`.`FEAT_CARR_LONG_DEG` AS `FEAT_CARR_LONG_DEG`,`t_rs`.`FEAT_CARR_LAT_DEG` AS `FEAT_CARR_LAT_DEG`,`t_rs`.`BR_X0_PIX` AS `BR_X0_PIX`,`t_rs`.`BR_Y0_PIX` AS `BR_Y0_PIX`,`t_rs`.`BR_X1_PIX` AS `BR_X1_PIX`,`t_rs`.`BR_Y1_PIX` AS `BR_Y1_PIX`,`t_rs`.`BR_X2_PIX` AS `BR_X2_PIX`,`t_rs`.`BR_Y2_PIX` AS `BR_Y2_PIX`,`t_rs`.`BR_X3_PIX` AS `BR_X3_PIX`,`t_rs`.`BR_Y3_PIX` AS `BR_Y3_PIX`,`t_rs`.`BR_X0_ARCSEC` AS `BR_X0_ARCSEC`,`t_rs`.`BR_Y0_ARCSEC` AS `BR_Y0_ARCSEC`,`t_rs`.`BR_X1_ARCSEC` AS `BR_X1_ARCSEC`,`t_rs`.`BR_Y1_ARCSEC` AS `BR_Y1_ARCSEC`,`t_rs`.`BR_X2_ARCSEC` AS `BR_X2_ARCSEC`,`t_rs`.`BR_Y2_ARCSEC` AS `BR_Y2_ARCSEC`,`t_rs`.`BR_X3_ARCSEC` AS `BR_X3_ARCSEC`,`t_rs`.`BR_Y3_ARCSEC` AS `BR_Y3_ARCSEC`,`t_rs`.`BR_HG_LONG0_DEG` AS `BR_HG_LONG0_DEG`,`t_rs`.`BR_HG_LAT0_DEG` AS `BR_HG_LAT0_DEG`,`t_rs`.`BR_HG_LONG1_DEG` AS `BR_HG_LONG1_DEG`,`t_rs`.`BR_HG_LAT1_DEG` AS `BR_HG_LAT1_DEG`,`t_rs`.`BR_HG_LONG2_DEG` AS `BR_HG_LONG2_DEG`,`t_rs`.`BR_HG_LAT2_DEG` AS `BR_HG_LAT2_DEG`,`t_rs`.`BR_HG_LONG3_DEG` AS `BR_HG_LONG3_DEG`,`t_rs`.`BR_HG_LAT3_DEG` AS `BR_HG_LAT3_DEG`,`t_rs`.`BR_CARR_LONG0_DEG` AS `BR_CARR_LONG0_DEG`,`t_rs`.`BR_CARR_LAT0_DEG` AS `BR_CARR_LAT0_DEG`,`t_rs`.`BR_CARR_LONG1_DEG` AS `BR_CARR_LONG1_DEG`,`t_rs`.`BR_CARR_LAT1_DEG` AS `BR_CARR_LAT1_DEG`,`t_rs`.`BR_CARR_LONG2_DEG` AS `BR_CARR_LONG2_DEG`,`t_rs`.`BR_CARR_LAT2_DEG` AS `BR_CARR_LAT2_DEG`,`t_rs`.`BR_CARR_LONG3_DEG` AS `BR_CARR_LONG3_DEG`,`t_rs`.`BR_CARR_LAT3_DEG` AS `BR_CARR_LAT3_DEG`,`t_rs`.`FEAT_MAX_INT` AS `FEAT_MAX_INT`,`t_rs`.`FEAT_MEAN_INT` AS `FEAT_MEAN_INT`,`t_rs`.`FEAT_AREA_PIX` AS `FEAT_AREA_PIX`,`t_rs`.`FEAT_AREA_DEG2` AS `FEAT_AREA_DEG2`,`t_rs`.`FEAT_AREA_MM2` AS `FEAT_AREA_MM2`,`t_rs`.`EL_AXIS1` AS `EL_AXIS1`,`t_rs`.`EL_AXIS2` AS `EL_AXIS2`,`t_rs`.`EL_ANGLE` AS `EL_ANGLE`,`t_rs`.`CC_X_PIX` AS `CC_X_PIX`,`t_rs`.`CC_Y_PIX` AS `CC_Y_PIX`,`t_rs`.`CC_X_ARCSEC` AS `CC_X_ARCSEC`,`t_rs`.`CC_Y_ARCSEC` AS `CC_Y_ARCSEC`,`t_rs`.`CC` AS `CC`,`t_rs`.`CC_LENGTH` AS `CC_LENGTH`,`t_rs`.`SNAPSHOT_FN` AS `SNAPSHOT_FN`,`t_rs`.`SNAPSHOT_PATH` AS `SNAPSHOT_PATH`,`t_rs`.`FEAT_FILENAME` AS `FEAT_FILENAME`,`t_rs`.`HELIO_RS_NUMBER` AS `HELIO_RS_NUMBER`,`t_rs`.`RUN_DATE` AS `RUN_DATE` from ((`OBSERVATORY` `t_oby` join `OBSERVATIONS` `t_obs`) join `RADIOSOURCES` `t_rs`) where ((`t_obs`.`ID_OBSERVATIONS` = `t_rs`.`OBSERVATIONS_ID`) and (`t_obs`.`OBSERVATORY_ID` = `t_oby`.`ID_OBSERVATORY`)) */;

--
-- Final view structure for view `VIEW_RS_HQI`
--

/*!50001 DROP TABLE IF EXISTS `VIEW_RS_HQI`*/;
/*!50001 DROP VIEW IF EXISTS `VIEW_RS_HQI`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=CURRENT_USER SQL SECURITY DEFINER VIEW `VIEW_RS_HQI` AS select `t_frc`.`INSTITUT` AS `INSTITUT`,`t_frc`.`CODE` AS `CODE`,`t_frc`.`VERSION` AS `VERSION`,`t_frc`.`FEATURE_NAME` AS `FEATURE_NAME`,`t_frc`.`ENC_MET` AS `ENC_MET`,`t_frc`.`PERSON` AS `PERSON`,`t_frc`.`CONTACT` AS `CONTACT`,`t_frc`.`REFERENCE` AS `REFERENCE`,`t_oby`.`OBSERVAT` AS `OBSERVAT`,`t_oby`.`INSTRUME` AS `INSTRUME`,`t_oby`.`TELESCOP` AS `TELESCOP`,`t_oby`.`UNITS` AS `UNITS`,`t_oby`.`WAVEMIN` AS `WAVEMIN`,`t_oby`.`WAVEMAX` AS `WAVEMAX`,`t_oby`.`WAVENAME` AS `WAVENAME`,`t_oby`.`WAVEUNIT` AS `WAVEUNIT`,`t_oby`.`SPECTRAL_NAME` AS `SPECTRAL_NAME`,`t_oby`.`OBS_TYPE` AS `OBS_TYPE`,`t_obs`.`DATE_OBS` AS `DATE_OBS`,`t_obs`.`DATE_END` AS `DATE_END`,`t_obs`.`JDINT` AS `JDINT`,`t_obs`.`JDFRAC` AS `JDFRAC`,`t_obs`.`C_ROTATION` AS `C_ROTATION`,`t_obs`.`FILENAME` AS `FILENAME`,`t_obs`.`URL` AS `URL`,`t_obs`.`CDELT1` AS `CDELT1`,`t_obs`.`CDELT2` AS `CDELT2`,`t_obs`.`NAXIS1` AS `NAXIS1`,`t_obs`.`NAXIS2` AS `NAXIS2`,`t_obs`.`CENTER_X` AS `CENTER_X`,`t_obs`.`CENTER_Y` AS `CENTER_Y`,`t_obs`.`R_SUN` AS `R_SUN`,`t_obs`.`BITPIX` AS `BITPIX`,`t_obs`.`BSCALE` AS `BSCALE`,`t_obs`.`BZERO` AS `BZERO`,`t_obs`.`EXP_TIME` AS `EXP_TIME`,`t_obs`.`FILE_FORMAT` AS `FILE_FORMAT`,`t_obs`.`QCLK_URL` AS `QCLK_URL`,`t_obs`.`QCLK_FNAME` AS `QCLK_FNAME`,`t_fil`.`ID_RS` AS `ID_RS`,`t_fil`.`FEAT_X_PIX` AS `FEAT_X_PIX`,`t_fil`.`FEAT_Y_PIX` AS `FEAT_Y_PIX`,`t_fil`.`FEAT_X_ARCSEC` AS `FEAT_X_ARCSEC`,`t_fil`.`FEAT_Y_ARCSEC` AS `FEAT_Y_ARCSEC`,`t_fil`.`FEAT_HG_LONG_DEG` AS `FEAT_HG_LONG_DEG`,`t_fil`.`FEAT_HG_LAT_DEG` AS `FEAT_HG_LAT_DEG`,`t_fil`.`FEAT_CARR_LONG_DEG` AS `FEAT_CARR_LONG_DEG`,`t_fil`.`FEAT_CARR_LAT_DEG` AS `FEAT_CARR_LAT_DEG`,`t_fil`.`BR_X0_PIX` AS `BR_X0_PIX`,`t_fil`.`BR_Y0_PIX` AS `BR_Y0_PIX`,`t_fil`.`BR_X1_PIX` AS `BR_X1_PIX`,`t_fil`.`BR_Y1_PIX` AS `BR_Y1_PIX`,`t_fil`.`BR_X2_PIX` AS `BR_X2_PIX`,`t_fil`.`BR_Y2_PIX` AS `BR_Y2_PIX`,`t_fil`.`BR_X3_PIX` AS `BR_X3_PIX`,`t_fil`.`BR_Y3_PIX` AS `BR_Y3_PIX`,`t_fil`.`BR_X0_ARCSEC` AS `BR_X0_ARCSEC`,`t_fil`.`BR_Y0_ARCSEC` AS `BR_Y0_ARCSEC`,`t_fil`.`BR_X1_ARCSEC` AS `BR_X1_ARCSEC`,`t_fil`.`BR_Y1_ARCSEC` AS `BR_Y1_ARCSEC`,`t_fil`.`BR_X2_ARCSEC` AS `BR_X2_ARCSEC`,`t_fil`.`BR_Y2_ARCSEC` AS `BR_Y2_ARCSEC`,`t_fil`.`BR_X3_ARCSEC` AS `BR_X3_ARCSEC`,`t_fil`.`BR_Y3_ARCSEC` AS `BR_Y3_ARCSEC`,`t_fil`.`BR_HG_LONG0_DEG` AS `BR_HG_LONG0_DEG`,`t_fil`.`BR_HG_LAT0_DEG` AS `BR_HG_LAT0_DEG`,`t_fil`.`BR_HG_LONG1_DEG` AS `BR_HG_LONG1_DEG`,`t_fil`.`BR_HG_LAT1_DEG` AS `BR_HG_LAT1_DEG`,`t_fil`.`BR_HG_LONG2_DEG` AS `BR_HG_LONG2_DEG`,`t_fil`.`BR_HG_LAT2_DEG` AS `BR_HG_LAT2_DEG`,`t_fil`.`BR_HG_LONG3_DEG` AS `BR_HG_LONG3_DEG`,`t_fil`.`BR_HG_LAT3_DEG` AS `BR_HG_LAT3_DEG`,`t_fil`.`BR_CARR_LONG0_DEG` AS `BR_CARR_LONG0_DEG`,`t_fil`.`BR_CARR_LAT0_DEG` AS `BR_CARR_LAT0_DEG`,`t_fil`.`BR_CARR_LONG1_DEG` AS `BR_CARR_LONG1_DEG`,`t_fil`.`BR_CARR_LAT1_DEG` AS `BR_CARR_LAT1_DEG`,`t_fil`.`BR_CARR_LONG2_DEG` AS `BR_CARR_LONG2_DEG`,`t_fil`.`BR_CARR_LAT2_DEG` AS `BR_CARR_LAT2_DEG`,`t_fil`.`BR_CARR_LONG3_DEG` AS `BR_CARR_LONG3_DEG`,`t_fil`.`BR_CARR_LAT3_DEG` AS `BR_CARR_LAT3_DEG`,`t_fil`.`FEAT_AREA_PIX` AS `FEAT_AREA_PIX`,`t_fil`.`FEAT_AREA_DEG2` AS `FEAT_AREA_DEG2`,`t_fil`.`FEAT_AREA_MM2` AS `FEAT_AREA_MM2`,`t_fil`.`FEAT_MAX_INT` AS `FEAT_MAX_INT`,`t_fil`.`FEAT_MEAN_INT` AS `FEAT_MEAN_INT`,`t_fil`.`EL_AXIS1` AS `EL_AXIS1`,`t_fil`.`EL_AXIS2` AS `EL_AXIS2`,`t_fil`.`EL_ANGLE` AS `EL_ANGLE`,`t_fil`.`CC_X_PIX` AS `CC_X_PIX`,`t_fil`.`CC_Y_PIX` AS `CC_Y_PIX`,`t_fil`.`CC_X_ARCSEC` AS `CC_X_ARCSEC`,`t_fil`.`CC_Y_ARCSEC` AS `CC_Y_ARCSEC`,`t_fil`.`CC` AS `CC`,`t_fil`.`CC_LENGTH` AS `CC_LENGTH`,concat(`t_fil`.`SNAPSHOT_PATH`,`t_fil`.`SNAPSHOT_FN`) AS `SNAPSHOT` from (((`OBSERVATIONS` `t_obs` join `OBSERVATORY` `t_oby`) join `RADIOSOURCES` `t_fil`) join `FRC_INFO` `t_frc`) where ((`t_obs`.`ID_OBSERVATIONS` = `t_fil`.`OBSERVATIONS_ID`) and (`t_frc`.`ID_FRC_INFO` = `t_fil`.`FRC_INFO_ID`) and (`t_obs`.`OBSERVATORY_ID` = `t_oby`.`ID_OBSERVATORY`)) */;

--
-- Final view structure for view `VIEW_SP_GUI`
--

/*!50001 DROP TABLE IF EXISTS `VIEW_SP_GUI`*/;
/*!50001 DROP VIEW IF EXISTS `VIEW_SP_GUI`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=CURRENT_USER SQL SECURITY DEFINER VIEW `VIEW_SP_GUI` AS select `t_oby`.`ID_OBSERVATORY` AS `ID_OBSERVATORY`,`t_oby`.`OBSERVAT` AS `OBSERVAT`,`t_oby`.`INSTRUME` AS `INSTRUME`,`t_oby`.`TELESCOP` AS `TELESCOP`,`t_obs`.`DATE_OBS` AS `DATE_OBS`,`t_obs`.`DATE_END` AS `DATE_END`,`t_obs`.`FILENAME` AS `FILENAME`,`t_obs`.`URL` AS `URL`,`t_obs`.`BITPIX` AS `BITPIX`,`t_obs`.`CDELT1` AS `CDELT1`,`t_obs`.`CDELT2` AS `CDELT2`,`t_obs`.`NAXIS1` AS `NAXIS1`,`t_obs`.`NAXIS2` AS `NAXIS2`,`t_obs`.`CENTER_X` AS `CENTER_X`,`t_obs`.`CENTER_Y` AS `CENTER_Y`,`t_obs`.`R_SUN` AS `R_SUN`,`t_noa`.`NOAA_NUMBER` AS `NOAA_NUMBER`,`t_sp`.`ID_SUNSPOT` AS `ID_SUNSPOT`,`t_sp`.`FRC_INFO_ID` AS `FRC_INFO_ID`,`t_sp`.`OBSERVATIONS_ID_IC` AS `OBSERVATIONS_ID_IC`,`t_sp`.`OBSERVATIONS_ID_M` AS `OBSERVATIONS_ID_M`,`t_sp`.`FEAT_X_ARCSEC` AS `FEAT_X_ARCSEC`,`t_sp`.`FEAT_Y_ARCSEC` AS `FEAT_Y_ARCSEC`,`t_sp`.`FEAT_X_PIX` AS `FEAT_X_PIX`,`t_sp`.`FEAT_Y_PIX` AS `FEAT_Y_PIX`,`t_sp`.`FEAT_HG_LONG_DEG` AS `FEAT_HG_LONG_DEG`,`t_sp`.`FEAT_HG_LAT_DEG` AS `FEAT_HG_LAT_DEG`,`t_sp`.`FEAT_CARR_LONG_DEG` AS `FEAT_CARR_LONG_DEG`,`t_sp`.`FEAT_CARR_LAT_DEG` AS `FEAT_CARR_LAT_DEG`,`t_sp`.`BR_X0_ARCSEC` AS `BR_X0_ARCSEC`,`t_sp`.`BR_Y0_ARCSEC` AS `BR_Y0_ARCSEC`,`t_sp`.`BR_X1_ARCSEC` AS `BR_X1_ARCSEC`,`t_sp`.`BR_Y1_ARCSEC` AS `BR_Y1_ARCSEC`,`t_sp`.`BR_X2_ARCSEC` AS `BR_X2_ARCSEC`,`t_sp`.`BR_Y2_ARCSEC` AS `BR_Y2_ARCSEC`,`t_sp`.`BR_X3_ARCSEC` AS `BR_X3_ARCSEC`,`t_sp`.`BR_Y3_ARCSEC` AS `BR_Y3_ARCSEC`,`t_sp`.`BR_X0_PIX` AS `BR_X0_PIX`,`t_sp`.`BR_Y0_PIX` AS `BR_Y0_PIX`,`t_sp`.`BR_X1_PIX` AS `BR_X1_PIX`,`t_sp`.`BR_Y1_PIX` AS `BR_Y1_PIX`,`t_sp`.`BR_X2_PIX` AS `BR_X2_PIX`,`t_sp`.`BR_Y2_PIX` AS `BR_Y2_PIX`,`t_sp`.`BR_X3_PIX` AS `BR_X3_PIX`,`t_sp`.`BR_Y3_PIX` AS `BR_Y3_PIX`,`t_sp`.`BR_HG_LONG0_DEG` AS `BR_HG_LONG0_DEG`,`t_sp`.`BR_HG_LAT0_DEG` AS `BR_HG_LAT0_DEG`,`t_sp`.`BR_HG_LONG1_DEG` AS `BR_HG_LONG1_DEG`,`t_sp`.`BR_HG_LAT1_DEG` AS `BR_HG_LAT1_DEG`,`t_sp`.`BR_HG_LONG2_DEG` AS `BR_HG_LONG2_DEG`,`t_sp`.`BR_HG_LAT2_DEG` AS `BR_HG_LAT2_DEG`,`t_sp`.`BR_HG_LONG3_DEG` AS `BR_HG_LONG3_DEG`,`t_sp`.`BR_HG_LAT3_DEG` AS `BR_HG_LAT3_DEG`,`t_sp`.`BR_CARR_LONG0_DEG` AS `BR_CARR_LONG0_DEG`,`t_sp`.`BR_CARR_LAT0_DEG` AS `BR_CARR_LAT0_DEG`,`t_sp`.`BR_CARR_LONG1_DEG` AS `BR_CARR_LONG1_DEG`,`t_sp`.`BR_CARR_LAT1_DEG` AS `BR_CARR_LAT1_DEG`,`t_sp`.`BR_CARR_LONG2_DEG` AS `BR_CARR_LONG2_DEG`,`t_sp`.`BR_CARR_LAT2_DEG` AS `BR_CARR_LAT2_DEG`,`t_sp`.`BR_CARR_LONG3_DEG` AS `BR_CARR_LONG3_DEG`,`t_sp`.`BR_CARR_LAT3_DEG` AS `BR_CARR_LAT3_DEG`,`t_sp`.`FEAT_AREA_PIX` AS `FEAT_AREA_PIX`,`t_sp`.`FEAT_AREA_DEG2` AS `FEAT_AREA_DEG2`,`t_sp`.`FEAT_AREA_MM2` AS `FEAT_AREA_MM2`,`t_sp`.`FEAT_DIAM_DEG` AS `FEAT_DIAM_DEG`,`t_sp`.`FEAT_DIAM_MM` AS `FEAT_DIAM_MM`,`t_sp`.`FEAT_MEAN2QSUN` AS `FEAT_MEAN2QSUN`,`t_sp`.`FEAT_MAX_INT` AS `FEAT_MAX_INT`,`t_sp`.`FEAT_MIN_INT` AS `FEAT_MIN_INT`,`t_sp`.`FEAT_MEAN_INT` AS `FEAT_MEAN_INT`,`t_sp`.`FEAT_TOT_BZ` AS `FEAT_TOT_BZ`,`t_sp`.`FEAT_ABS_BZ` AS `FEAT_ABS_BZ`,`t_sp`.`FEAT_MAX_BZ` AS `FEAT_MAX_BZ`,`t_sp`.`FEAT_MIN_BZ` AS `FEAT_MIN_BZ`,`t_sp`.`FEAT_MEAN_BZ` AS `FEAT_MEAN_BZ`,`t_sp`.`UMBRA_NUMBER` AS `UMBRA_NUMBER`,`t_sp`.`UMBRA_AREA_PIX` AS `UMBRA_AREA_PIX`,`t_sp`.`UMBRA_AREA_MM2` AS `UMBRA_AREA_MM2`,`t_sp`.`UMBRA_AREA_DEG2` AS `UMBRA_AREA_DEG2`,`t_sp`.`UMBRA_DIAM_DEG` AS `UMBRA_DIAM_DEG`,`t_sp`.`UMBRA_DIAM_MM` AS `UMBRA_DIAM_MM`,`t_sp`.`UMBRA_MAX_INT` AS `UMBRA_MAX_INT`,`t_sp`.`UMBRA_MIN_INT` AS `UMBRA_MIN_INT`,`t_sp`.`UMBRA_MEAN_INT` AS `UMBRA_MEAN_INT`,`t_sp`.`UMBRA_TOT_BZ` AS `UMBRA_TOT_BZ`,`t_sp`.`UMBRA_ABS_BZ` AS `UMBRA_ABS_BZ`,`t_sp`.`UMBRA_MAX_BZ` AS `UMBRA_MAX_BZ`,`t_sp`.`UMBRA_MIN_BZ` AS `UMBRA_MIN_BZ`,`t_sp`.`UMBRA_MEAN_BZ` AS `UMBRA_MEAN_BZ`,`t_sp`.`CC_X_PIX` AS `CC_X_PIX`,`t_sp`.`CC_Y_PIX` AS `CC_Y_PIX`,`t_sp`.`CC_X_ARCSEC` AS `CC_X_ARCSEC`,`t_sp`.`CC_Y_ARCSEC` AS `CC_Y_ARCSEC`,`t_sp`.`CC` AS `CC`,`t_sp`.`CC_LENGTH` AS `CC_LENGTH`,`t_sp`.`RS` AS `RS`,`t_sp`.`RS_LENGTH` AS `RS_LENGTH`,`t_sp`.`SNAPSHOT_FN` AS `SNAPSHOT_FN`,`t_sp`.`SNAPSHOT_PATH` AS `SNAPSHOT_PATH`,`t_sp`.`HELIO_SS_NUMBER` AS `HELIO_SS_NUMBER`,`t_sp`.`FEAT_FILENAME` AS `FEAT_FILENAME`,`t_sp`.`RUN_DATE` AS `RUN_DATE` from ((((`OBSERVATIONS` `t_obs` join `OBSERVATORY` `t_oby`) join `SUNSPOTS` `t_sp`) left join `NOAA_SP` `t_noa_sp` on((`t_noa_sp`.`SUNSPOT_ID` = `t_sp`.`ID_SUNSPOT`))) left join `NOAA` `t_noa` on((`t_noa`.`ID_NOAA` = `t_noa_sp`.`NOAA_ID`))) where ((`t_obs`.`ID_OBSERVATIONS` = `t_sp`.`OBSERVATIONS_ID_IC`) and (`t_obs`.`OBSERVATORY_ID` = `t_oby`.`ID_OBSERVATORY`)) */;

--
-- Final view structure for view `VIEW_SP_HQI`
--

/*!50001 DROP TABLE IF EXISTS `VIEW_SP_HQI`*/;
/*!50001 DROP VIEW IF EXISTS `VIEW_SP_HQI`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=CURRENT_USER SQL SECURITY DEFINER VIEW `VIEW_SP_HQI` AS select `t_frc`.`INSTITUT` AS `INSTITUT`,`t_frc`.`CODE` AS `CODE`,`t_frc`.`VERSION` AS `VERSION`,`t_frc`.`FEATURE_NAME` AS `FEATURE_NAME`,`t_frc`.`ENC_MET` AS `ENC_MET`,`t_frc`.`PERSON` AS `PERSON`,`t_frc`.`CONTACT` AS `CONTACT`,`t_frc`.`REFERENCE` AS `REFERENCE`,`t_oby`.`OBSERVAT` AS `OBSERVAT`,`t_oby`.`INSTRUME` AS `INSTRUME`,`t_oby`.`TELESCOP` AS `TELESCOP`,`t_oby`.`UNITS` AS `UNITS`,`t_oby`.`WAVEMIN` AS `WAVEMIN`,`t_oby`.`WAVEMAX` AS `WAVEMAX`,`t_oby`.`WAVENAME` AS `WAVENAME`,`t_oby`.`WAVEUNIT` AS `WAVEUNIT`,`t_oby`.`SPECTRAL_NAME` AS `SPECTRAL_NAME`,`t_oby`.`OBS_TYPE` AS `OBS_TYPE`,`t_obs`.`DATE_OBS` AS `DATE_OBS`,`t_obs`.`DATE_END` AS `DATE_END`,`t_obs`.`JDINT` AS `JDINT`,`t_obs`.`JDFRAC` AS `JDFRAC`,`t_obs`.`C_ROTATION` AS `C_ROTATION`,`t_obs`.`FILENAME` AS `FILENAME`,`t_obs`.`URL` AS `URL`,`t_obs`.`CDELT1` AS `CDELT1`,`t_obs`.`CDELT2` AS `CDELT2`,`t_obs`.`NAXIS1` AS `NAXIS1`,`t_obs`.`NAXIS2` AS `NAXIS2`,`t_obs`.`CENTER_X` AS `CENTER_X`,`t_obs`.`CENTER_Y` AS `CENTER_Y`,`t_obs`.`R_SUN` AS `R_SUN`,`t_obs`.`BITPIX` AS `BITPIX`,`t_obs`.`BSCALE` AS `BSCALE`,`t_obs`.`BZERO` AS `BZERO`,`t_obs`.`EXP_TIME` AS `EXP_TIME`,`t_obs`.`FILE_FORMAT` AS `FILE_FORMAT`,`t_obs`.`QCLK_URL` AS `QCLK_URL`,`t_obs`.`QCLK_FNAME` AS `QCLK_FNAME`,`t_sp`.`ID_SUNSPOT` AS `ID_SUNSPOT`,`t_noaa`.`NOAA_NUMBER` AS `NOAA_NUMBER`,`t_sp`.`FEAT_X_PIX` AS `FEAT_X_PIX`,`t_sp`.`FEAT_Y_PIX` AS `FEAT_Y_PIX`,`t_sp`.`FEAT_X_ARCSEC` AS `FEAT_X_ARCSEC`,`t_sp`.`FEAT_Y_ARCSEC` AS `FEAT_Y_ARCSEC`,`t_sp`.`FEAT_HG_LONG_DEG` AS `FEAT_HG_LONG_DEG`,`t_sp`.`FEAT_HG_LAT_DEG` AS `FEAT_HG_LAT_DEG`,`t_sp`.`FEAT_CARR_LONG_DEG` AS `FEAT_CARR_LONG_DEG`,`t_sp`.`FEAT_CARR_LAT_DEG` AS `FEAT_CARR_LAT_DEG`,`t_sp`.`BR_X0_PIX` AS `BR_X0_PIX`,`t_sp`.`BR_Y0_PIX` AS `BR_Y0_PIX`,`t_sp`.`BR_X1_PIX` AS `BR_X1_PIX`,`t_sp`.`BR_Y1_PIX` AS `BR_Y1_PIX`,`t_sp`.`BR_X2_PIX` AS `BR_X2_PIX`,`t_sp`.`BR_Y2_PIX` AS `BR_Y2_PIX`,`t_sp`.`BR_X3_PIX` AS `BR_X3_PIX`,`t_sp`.`BR_Y3_PIX` AS `BR_Y3_PIX`,`t_sp`.`BR_X0_ARCSEC` AS `BR_X0_ARCSEC`,`t_sp`.`BR_Y0_ARCSEC` AS `BR_Y0_ARCSEC`,`t_sp`.`BR_X1_ARCSEC` AS `BR_X1_ARCSEC`,`t_sp`.`BR_Y1_ARCSEC` AS `BR_Y1_ARCSEC`,`t_sp`.`BR_X2_ARCSEC` AS `BR_X2_ARCSEC`,`t_sp`.`BR_Y2_ARCSEC` AS `BR_Y2_ARCSEC`,`t_sp`.`BR_X3_ARCSEC` AS `BR_X3_ARCSEC`,`t_sp`.`BR_Y3_ARCSEC` AS `BR_Y3_ARCSEC`,`t_sp`.`BR_HG_LONG0_DEG` AS `BR_HG_LONG0_DEG`,`t_sp`.`BR_HG_LAT0_DEG` AS `BR_HG_LAT0_DEG`,`t_sp`.`BR_HG_LONG1_DEG` AS `BR_HG_LONG1_DEG`,`t_sp`.`BR_HG_LAT1_DEG` AS `BR_HG_LAT1_DEG`,`t_sp`.`BR_HG_LONG2_DEG` AS `BR_HG_LONG2_DEG`,`t_sp`.`BR_HG_LAT2_DEG` AS `BR_HG_LAT2_DEG`,`t_sp`.`BR_HG_LONG3_DEG` AS `BR_HG_LONG3_DEG`,`t_sp`.`BR_HG_LAT3_DEG` AS `BR_HG_LAT3_DEG`,`t_sp`.`BR_CARR_LONG0_DEG` AS `BR_CARR_LONG0_DEG`,`t_sp`.`BR_CARR_LAT0_DEG` AS `BR_CARR_LAT0_DEG`,`t_sp`.`BR_CARR_LONG1_DEG` AS `BR_CARR_LONG1_DEG`,`t_sp`.`BR_CARR_LAT1_DEG` AS `BR_CARR_LAT1_DEG`,`t_sp`.`BR_CARR_LONG2_DEG` AS `BR_CARR_LONG2_DEG`,`t_sp`.`BR_CARR_LAT2_DEG` AS `BR_CARR_LAT2_DEG`,`t_sp`.`BR_CARR_LONG3_DEG` AS `BR_CARR_LONG3_DEG`,`t_sp`.`BR_CARR_LAT3_DEG` AS `BR_CARR_LAT3_DEG`,`t_sp`.`FEAT_AREA_PIX` AS `FEAT_AREA_PIX`,`t_sp`.`FEAT_AREA_DEG2` AS `FEAT_AREA_DEG2`,`t_sp`.`FEAT_AREA_MM2` AS `FEAT_AREA_MM2`,`t_sp`.`FEAT_DIAM_DEG` AS `FEAT_DIAM_DEG`,`t_sp`.`FEAT_DIAM_MM` AS `FEAT_DIAM_MM`,`t_sp`.`FEAT_MAX_INT` AS `FEAT_MAX_INT`,`t_sp`.`FEAT_MIN_INT` AS `FEAT_MIN_INT`,`t_sp`.`FEAT_MEAN_INT` AS `FEAT_MEAN_INT`,`t_sp`.`FEAT_MEAN2QSUN` AS `FEAT_MEAN2QSUN`,`t_sp`.`FEAT_MIN_BZ` AS `FEAT_MIN_BZ`,`t_sp`.`FEAT_MAX_BZ` AS `FEAT_MAX_BZ`,`t_sp`.`FEAT_MEAN_BZ` AS `FEAT_MEAN_BZ`,`t_sp`.`FEAT_ABS_BZ` AS `FEAT_ABS_BZ`,`t_sp`.`FEAT_TOT_BZ` AS `FEAT_TOT_BZ`,`t_sp`.`UMBRA_NUMBER` AS `UMBRA_NUMBER`,`t_sp`.`UMBRA_AREA_PIX` AS `UMBRA_AREA_PIX`,`t_sp`.`UMBRA_AREA_MM2` AS `UMBRA_AREA_MM2`,`t_sp`.`UMBRA_AREA_DEG2` AS `UMBRA_AREA_DEG2`,`t_sp`.`UMBRA_DIAM_DEG` AS `UMBRA_DIAM_DEG`,`t_sp`.`UMBRA_DIAM_MM` AS `UMBRA_DIAM_MM`,`t_sp`.`UMBRA_MAX_INT` AS `UMBRA_MAX_INT`,`t_sp`.`UMBRA_MIN_INT` AS `UMBRA_MIN_INT`,`t_sp`.`UMBRA_MEAN_INT` AS `UMBRA_MEAN_INT`,`t_sp`.`UMBRA_MIN_BZ` AS `UMBRA_MIN_BZ`,`t_sp`.`UMBRA_MAX_BZ` AS `UMBRA_MAX_BZ`,`t_sp`.`UMBRA_MEAN_BZ` AS `UMBRA_MEAN_BZ`,`t_sp`.`UMBRA_ABS_BZ` AS `UMBRA_ABS_BZ`,`t_sp`.`UMBRA_TOT_BZ` AS `UMBRA_TOT_BZ`,`t_sp`.`CC_X_PIX` AS `CC_X_PIX`,`t_sp`.`CC_Y_PIX` AS `CC_Y_PIX`,`t_sp`.`CC_X_ARCSEC` AS `CC_X_ARCSEC`,`t_sp`.`CC_Y_ARCSEC` AS `CC_Y_ARCSEC`,`t_sp`.`CC` AS `CC`,`t_sp`.`CC_LENGTH` AS `CC_LENGTH`,`t_sp`.`RS` AS `RS`,`t_sp`.`RS_LENGTH` AS `RS_LENGTH`,concat(`t_sp`.`SNAPSHOT_PATH`,`t_sp`.`SNAPSHOT_FN`) AS `SNAPSHOT` from (((((`OBSERVATIONS` `t_obs` join `OBSERVATORY` `t_oby`) join `SUNSPOTS` `t_sp`) join `FRC_INFO` `t_frc`) left join `NOAA_SP` `t_noaa_sp` on((`t_noaa_sp`.`SUNSPOT_ID` = `t_sp`.`ID_SUNSPOT`))) left join `NOAA` `t_noaa` on((`t_noaa`.`ID_NOAA` = `t_noaa_sp`.`NOAA_ID`))) where ((`t_obs`.`ID_OBSERVATIONS` = `t_sp`.`OBSERVATIONS_ID_IC`) and (`t_frc`.`ID_FRC_INFO` = `t_sp`.`FRC_INFO_ID`) and (`t_obs`.`OBSERVATORY_ID` = `t_oby`.`ID_OBSERVATORY`)) */;

--
-- Final view structure for view `VIEW_T2_GUI`
--

/*!50001 DROP TABLE IF EXISTS `VIEW_T2_GUI`*/;
/*!50001 DROP VIEW IF EXISTS `VIEW_T2_GUI`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=CURRENT_USER SQL SECURITY DEFINER VIEW `VIEW_T2_GUI` AS select `t_oby`.`ID_OBSERVATORY` AS `ID_OBSERVATORY`,`t_oby`.`OBSERVAT` AS `OBSERVAT`,`t_oby`.`INSTRUME` AS `INSTRUME`,`t_oby`.`TELESCOP` AS `TELESCOP`,`t_oby`.`UNITS` AS `UNITS`,`t_obs`.`DATE_OBS` AS `DATE_OBS`,`t_obs`.`DATE_END` AS `DATE_END`,`t_obs`.`FILENAME` AS `FILENAME`,`t_obs`.`URL` AS `URL`,`t_obs`.`CDELT1` AS `CDELT1`,`t_obs`.`CDELT2` AS `CDELT2`,`t_obs`.`NAXIS1` AS `NAXIS1`,`t_obs`.`NAXIS2` AS `NAXIS2`,`t_t2`.`ID_TYPE_II` AS `ID_TYPE_II`,`t_t2`.`FEAT_MAX_INT` AS `FEAT_MAX_INT`,`t_t2`.`FEAT_MEAN_INT` AS `FEAT_MEAN_INT`,`t_t2`.`COMPONENT` AS `COMPONENT`,`t_t2`.`FEAT_FILENAME` AS `FEAT_FILENAME`,`t_t2`.`RS` AS `RS`,`t_t2`.`RS_LENGTH` AS `RS_LENGTH`,`t_t2`.`SKE_CC_X_PIX` AS `SKE_CC_X_PIX`,`t_t2`.`SKE_CC_Y_PIX` AS `SKE_CC_Y_PIX`,`t_t2`.`SKE_CC_X_UTC` AS `SKE_CC_X_UTC`,`t_t2`.`SKE_CC_Y_MHZ` AS `SKE_CC_Y_MHZ`,`t_t2`.`SKE_CC` AS `SKE_CC`,`t_t2`.`SKE_CC_LENGTH` AS `SKE_CC_LENGTH` from ((`OBSERVATORY` `t_oby` join `OBSERVATIONS` `t_obs`) join `TYPE_II` `t_t2`) where ((`t_obs`.`ID_OBSERVATIONS` = `t_t2`.`OBSERVATIONS_ID`) and (`t_obs`.`OBSERVATORY_ID` = `t_oby`.`ID_OBSERVATORY`)) */;

--
-- Final view structure for view `VIEW_T2_HQI`
--

/*!50001 DROP TABLE IF EXISTS `VIEW_T2_HQI`*/;
/*!50001 DROP VIEW IF EXISTS `VIEW_T2_HQI`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=CURRENT_USER SQL SECURITY DEFINER VIEW `VIEW_T2_HQI` AS select `t_frc`.`INSTITUT` AS `INSTITUT`,`t_frc`.`CODE` AS `CODE`,`t_frc`.`VERSION` AS `VERSION`,`t_frc`.`FEATURE_NAME` AS `FEATURE_NAME`,`t_frc`.`ENC_MET` AS `ENC_MET`,`t_frc`.`PERSON` AS `PERSON`,`t_frc`.`CONTACT` AS `CONTACT`,`t_frc`.`REFERENCE` AS `REFERENCE`,`t_oby`.`OBSERVAT` AS `OBSERVAT`,`t_oby`.`INSTRUME` AS `INSTRUME`,`t_oby`.`TELESCOP` AS `TELESCOP`,`t_oby`.`UNITS` AS `UNITS`,`t_oby`.`WAVEMIN` AS `WAVEMIN`,`t_oby`.`WAVEMAX` AS `WAVEMAX`,`t_oby`.`WAVENAME` AS `WAVENAME`,`t_oby`.`WAVEUNIT` AS `WAVEUNIT`,`t_oby`.`SPECTRAL_NAME` AS `SPECTRAL_NAME`,`t_oby`.`OBS_TYPE` AS `OBS_TYPE`,`t_obs`.`DATE_OBS` AS `DATE_OBS`,`t_obs`.`DATE_END` AS `DATE_END`,`t_obs`.`JDINT` AS `JDINT`,`t_obs`.`JDFRAC` AS `JDFRAC`,`t_obs`.`C_ROTATION` AS `C_ROTATION`,`t_obs`.`FILENAME` AS `FILENAME`,`t_obs`.`URL` AS `URL`,`t_obs`.`CDELT1` AS `CDELT1`,`t_obs`.`CDELT2` AS `CDELT2`,`t_obs`.`NAXIS1` AS `NAXIS1`,`t_obs`.`NAXIS2` AS `NAXIS2`,`t_obs`.`CENTER_X` AS `CENTER_X`,`t_obs`.`CENTER_Y` AS `CENTER_Y`,`t_obs`.`FILE_FORMAT` AS `FILE_FORMAT`,`t_obs`.`QCLK_URL` AS `QCLK_URL`,`t_obs`.`QCLK_FNAME` AS `QCLK_FNAME`,`t_t2`.`ID_TYPE_II` AS `ID_TYPE_II`,`t_t2`.`FEAT_MAX_INT` AS `FEAT_MAX_INT`,`t_t2`.`FEAT_MEAN_INT` AS `FEAT_MEAN_INT`,`t_t2`.`COMPONENT` AS `COMPONENT`,`t_t2`.`FEAT_FILENAME` AS `FEAT_FILENAME`,concat(`t_t2`.`SNAPSHOT_PATH`,`t_t2`.`SNAPSHOT_FN`) AS `SNAPSHOT`,`t_t2`.`RS` AS `RS`,`t_t2`.`RS_LENGTH` AS `RS_LENGTH`,`t_t2`.`SKE_CC_X_PIX` AS `SKE_CC_X_PIX`,`t_t2`.`SKE_CC_Y_PIX` AS `SKE_CC_Y_PIX`,`t_t2`.`SKE_CC_X_UTC` AS `SKE_CC_X_UTC`,`t_t2`.`SKE_CC_Y_MHZ` AS `SKE_CC_Y_MHZ`,`t_t2`.`SKE_CC` AS `SKE_CC`,`t_t2`.`SKE_CC_LENGTH` AS `SKE_CC_LENGTH`,`t_t2`.`BR_X0_PIX` AS `BR_X0_PIX`,`t_t2`.`BR_Y0_PIX` AS `BR_Y0_PIX`,`t_t2`.`BR_X1_PIX` AS `BR_X1_PIX`,`t_t2`.`BR_Y1_PIX` AS `BR_Y1_PIX`,`t_t2`.`BR_X2_PIX` AS `BR_X2_PIX`,`t_t2`.`BR_Y2_PIX` AS `BR_Y2_PIX`,`t_t2`.`BR_X3_PIX` AS `BR_X3_PIX`,`t_t2`.`BR_Y3_PIX` AS `BR_Y3_PIX`,`t_t2`.`BR_X0_UTC` AS `BR_X0_UTC`,`t_t2`.`BR_Y0_MHZ` AS `BR_Y0_MHZ`,`t_t2`.`BR_X1_UTC` AS `BR_X1_UTC`,`t_t2`.`BR_Y1_MHZ` AS `BR_Y1_MHZ`,`t_t2`.`BR_X2_UTC` AS `BR_X2_UTC`,`t_t2`.`BR_Y2_MHZ` AS `BR_Y2_MHZ`,`t_t2`.`BR_X3_UTC` AS `BR_X3_UTC`,`t_t2`.`BR_Y3_MHZ` AS `BR_Y3_MHZ`,`t_t2`.`TIME_START` AS `TIME_START`,`t_t2`.`TIME_END` AS `TIME_END`,`t_t2`.`DRIFT_START` AS `DRIFT_START`,`t_t2`.`DRIFT_END` AS `DRIFT_END`,`t_t2`.`LVL_TRUST` AS `LVL_TRUST` from (((`OBSERVATORY` `t_oby` join `OBSERVATIONS` `t_obs`) join `TYPE_II` `t_t2`) join `FRC_INFO` `t_frc`) where ((`t_obs`.`ID_OBSERVATIONS` = `t_t2`.`OBSERVATIONS_ID`) and (`t_frc`.`ID_FRC_INFO` = `t_t2`.`FRC_INFO_ID`) and (`t_obs`.`OBSERVATORY_ID` = `t_oby`.`ID_OBSERVATORY`)) */;

--
-- Final view structure for view `VIEW_T3_GUI`
--

/*!50001 DROP TABLE IF EXISTS `VIEW_T3_GUI`*/;
/*!50001 DROP VIEW IF EXISTS `VIEW_T3_GUI`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=CURRENT_USER SQL SECURITY DEFINER VIEW `VIEW_T3_GUI` AS select `t_oby`.`ID_OBSERVATORY` AS `ID_OBSERVATORY`,`t_oby`.`OBSERVAT` AS `OBSERVAT`,`t_oby`.`INSTRUME` AS `INSTRUME`,`t_oby`.`TELESCOP` AS `TELESCOP`,`t_oby`.`UNITS` AS `UNITS`,`t_obs`.`DATE_OBS` AS `DATE_OBS`,`t_obs`.`DATE_END` AS `DATE_END`,`t_obs`.`FILENAME` AS `FILENAME`,`t_obs`.`URL` AS `URL`,`t_obs`.`CDELT1` AS `CDELT1`,`t_obs`.`CDELT2` AS `CDELT2`,`t_obs`.`NAXIS1` AS `NAXIS1`,`t_obs`.`NAXIS2` AS `NAXIS2`,`t_t3`.`ID_TYPE_III` AS `ID_TYPE_III`,`t_t3`.`FEAT_MAX_INT` AS `FEAT_MAX_INT`,`t_t3`.`FEAT_MEAN_INT` AS `FEAT_MEAN_INT`,`t_t3`.`MULTIPLE` AS `MULTIPLE`,`t_t3`.`FEAT_FILENAME` AS `FEAT_FILENAME`,`t_t3`.`CC_X_PIX` AS `CC_X_PIX`,`t_t3`.`CC_Y_PIX` AS `CC_Y_PIX`,`t_t3`.`CC_X_UTC` AS `CC_X_UTC`,`t_t3`.`CC_Y_MHZ` AS `CC_Y_MHZ`,`t_t3`.`CC` AS `CC`,`t_t3`.`CC_LENGTH` AS `CC_LENGTH`,`t_t3`.`SKE_CC_X_PIX` AS `SKE_CC_X_PIX`,`t_t3`.`SKE_CC_Y_PIX` AS `SKE_CC_Y_PIX`,`t_t3`.`SKE_CC_X_UTC` AS `SKE_CC_X_UTC`,`t_t3`.`SKE_CC_Y_MHZ` AS `SKE_CC_Y_MHZ`,`t_t3`.`SKE_CC` AS `SKE_CC`,`t_t3`.`SKE_CC_LENGTH` AS `SKE_CC_LENGTH` from ((`OBSERVATORY` `t_oby` join `OBSERVATIONS` `t_obs`) join `TYPE_III` `t_t3`) where ((`t_obs`.`ID_OBSERVATIONS` = `t_t3`.`OBSERVATIONS_ID`) and (`t_obs`.`OBSERVATORY_ID` = `t_oby`.`ID_OBSERVATORY`)) */;

--
-- Final view structure for view `VIEW_T3_HQI`
--

/*!50001 DROP TABLE IF EXISTS `VIEW_T3_HQI`*/;
/*!50001 DROP VIEW IF EXISTS `VIEW_T3_HQI`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=CURRENT_USER SQL SECURITY DEFINER VIEW `VIEW_T3_HQI` AS select `t_frc`.`INSTITUT` AS `INSTITUT`,`t_frc`.`CODE` AS `CODE`,`t_frc`.`VERSION` AS `VERSION`,`t_frc`.`FEATURE_NAME` AS `FEATURE_NAME`,`t_frc`.`ENC_MET` AS `ENC_MET`,`t_frc`.`PERSON` AS `PERSON`,`t_frc`.`CONTACT` AS `CONTACT`,`t_frc`.`REFERENCE` AS `REFERENCE`,`t_oby`.`OBSERVAT` AS `OBSERVAT`,`t_oby`.`INSTRUME` AS `INSTRUME`,`t_oby`.`TELESCOP` AS `TELESCOP`,`t_oby`.`UNITS` AS `UNITS`,`t_oby`.`WAVEMIN` AS `WAVEMIN`,`t_oby`.`WAVEMAX` AS `WAVEMAX`,`t_oby`.`WAVENAME` AS `WAVENAME`,`t_oby`.`WAVEUNIT` AS `WAVEUNIT`,`t_oby`.`SPECTRAL_NAME` AS `SPECTRAL_NAME`,`t_oby`.`OBS_TYPE` AS `OBS_TYPE`,`t_obs`.`DATE_OBS` AS `DATE_OBS`,`t_obs`.`DATE_END` AS `DATE_END`,`t_obs`.`JDINT` AS `JDINT`,`t_obs`.`JDFRAC` AS `JDFRAC`,`t_obs`.`C_ROTATION` AS `C_ROTATION`,`t_obs`.`FILENAME` AS `FILENAME`,`t_obs`.`URL` AS `URL`,`t_obs`.`CDELT1` AS `CDELT1`,`t_obs`.`CDELT2` AS `CDELT2`,`t_obs`.`NAXIS1` AS `NAXIS1`,`t_obs`.`NAXIS2` AS `NAXIS2`,`t_obs`.`CENTER_X` AS `CENTER_X`,`t_obs`.`CENTER_Y` AS `CENTER_Y`,`t_obs`.`FILE_FORMAT` AS `FILE_FORMAT`,`t_obs`.`QCLK_URL` AS `QCLK_URL`,`t_obs`.`QCLK_FNAME` AS `QCLK_FNAME`,`t_t3`.`ID_TYPE_III` AS `ID_TYPE_III`,`t_t3`.`FEAT_MAX_INT` AS `FEAT_MAX_INT`,`t_t3`.`FEAT_MEAN_INT` AS `FEAT_MEAN_INT`,`t_t3`.`MULTIPLE` AS `MULTIPLE`,`t_t3`.`FEAT_FILENAME` AS `FEAT_FILENAME`,concat(`t_t3`.`SNAPSHOT_PATH`,`t_t3`.`SNAPSHOT_FN`) AS `SNAPSHOT`,`t_t3`.`CC_X_PIX` AS `CC_X_PIX`,`t_t3`.`CC_Y_PIX` AS `CC_Y_PIX`,`t_t3`.`CC_X_UTC` AS `CC_X_UTC`,`t_t3`.`CC_Y_MHZ` AS `CC_Y_MHZ`,`t_t3`.`CC` AS `CC`,`t_t3`.`CC_LENGTH` AS `CC_LENGTH`,`t_t3`.`SKE_CC_X_PIX` AS `SKE_CC_X_PIX`,`t_t3`.`SKE_CC_Y_PIX` AS `SKE_CC_Y_PIX`,`t_t3`.`SKE_CC_X_UTC` AS `SKE_CC_X_UTC`,`t_t3`.`SKE_CC_Y_MHZ` AS `SKE_CC_Y_MHZ`,`t_t3`.`SKE_CC` AS `SKE_CC`,`t_t3`.`SKE_CC_LENGTH` AS `SKE_CC_LENGTH`,`t_t3`.`BR_X0_PIX` AS `BR_X0_PIX`,`t_t3`.`BR_Y0_PIX` AS `BR_Y0_PIX`,`t_t3`.`BR_X1_PIX` AS `BR_X1_PIX`,`t_t3`.`BR_Y1_PIX` AS `BR_Y1_PIX`,`t_t3`.`BR_X2_PIX` AS `BR_X2_PIX`,`t_t3`.`BR_Y2_PIX` AS `BR_Y2_PIX`,`t_t3`.`BR_X3_PIX` AS `BR_X3_PIX`,`t_t3`.`BR_Y3_PIX` AS `BR_Y3_PIX`,`t_t3`.`BR_X0_UTC` AS `BR_X0_UTC`,`t_t3`.`BR_Y0_MHZ` AS `BR_Y0_MHZ`,`t_t3`.`BR_X1_UTC` AS `BR_X1_UTC`,`t_t3`.`BR_Y1_MHZ` AS `BR_Y1_MHZ`,`t_t3`.`BR_X2_UTC` AS `BR_X2_UTC`,`t_t3`.`BR_Y2_MHZ` AS `BR_Y2_MHZ`,`t_t3`.`BR_X3_UTC` AS `BR_X3_UTC`,`t_t3`.`BR_Y3_MHZ` AS `BR_Y3_MHZ`,`t_t3`.`TIME_START` AS `TIME_START`,`t_t3`.`TIME_END` AS `TIME_END`,`t_t3`.`DRIFT_START` AS `DRIFT_START`,`t_t3`.`DRIFT_END` AS `DRIFT_END`,`t_t3`.`FIT_A0` AS `FIT_A0`,`t_t3`.`FIT_A1` AS `FIT_A1`,`t_t3`.`LVL_TRUST` AS `LVL_TRUST` from (((`OBSERVATORY` `t_oby` join `OBSERVATIONS` `t_obs`) join `TYPE_III` `t_t3`) join `FRC_INFO` `t_frc`) where ((`t_obs`.`ID_OBSERVATIONS` = `t_t3`.`OBSERVATIONS_ID`) and (`t_frc`.`ID_FRC_INFO` = `t_t3`.`FRC_INFO_ID`) and (`t_obs`.`OBSERVATORY_ID` = `t_oby`.`ID_OBSERVATORY`)) */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-06-18 11:04:11
