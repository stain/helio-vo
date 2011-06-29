<?php
        # =============================================
        # HELIO 2010 UOC server - by Andrej Santin
        # ---------------------------------------------
        # read 
        # web site: 
        # uoc-solar.php
        # 1st 29-sep-2010, last 29-sep-2010
        # =============================================

	//require ("sec_global.php");
	$tempdir = "/var/www/html/radiosun/hec/temp";
$tempdir = ".";
	
  // parse files and create postgres-ready file	
	$f1 = fopen("$tempdir/CDS.postgres.converted",'w');
  $files = array("UOC_cds_01_01.txt","UOC_sxt_00_14.txt","UOC_trace_00_14.txt");

  foreach ($files as $fname) {
    $f2 = fopen("$tempdir/$fname",'r');

    if (strpos($fname,"sxt")>0) {
      fclose($f1);
      $f1 = fopen("$tempdir/SXT.postgres.converted",'w');
    }
    if (strpos($fname,"trace")>0) {
      fclose($f1);
      $f1 = fopen("$tempdir/TRACE.postgres.converted",'w');
    }
//SOHO;CDS;L1;Spectrometer;Spatial Spectrum;Intensity;Spectral;Photon;EUV;Plasma diagnostics;PI at RAL;NIS;2001-01-01T00:05:31.662;2001-01-01T00:47:46.256;s21630r00;Synoptic Meridian Images           ;SYN  ;       ;SYNOP_F ;Heliographic;    0;  877; arcsec; arcsec;Angstrom;2.00000;1.68000;0.06970;0.11640;0.00000;0.00000;  240;  240; 120; 143;  24; 

//Observatory,Telescope,Orbit,InstrumentType,SamplingMethod,DataType,ObservationMode,DomainType,EnergyRegime,PhysicalParam,Contact,Instrument,Date_obs,Date_end,Filename,Sci_Obj,Object,Obj_ID,Obs_prog,CoordSystem,LocationX,LocationY,CoordinateNameX,CoordinateNameY,CoordinateNameW,SpatialSamplingX,SpatialSamplingY,SpatialSamplingLAM1,SpatialSamplingLAM2,SpatialSamplingLAM3,SpatialSamplingLAM4,AreaCoveredDX,AreaCoveredDY,Naxis1,Naxis2,Naxis3,Naxis4, Filter:strarr(24), Wavmin:fltarr(24), Wavmax:fltarr(24),Slit_num,Exp_time,JD1,JD2, SpectSampling
    
    while (!feof ($f2)) {
      $buffer = fgets($f2);
//      echo("buffer=".$buffer);
      $items = explode(";", $buffer);
      for($i=0;$i<count($items);$i++) $items[$i] = trim($items[$i]);
//      print_r($items);
      //exit(0);
      //
      $out = "";
      if (count($items)>2) {
        
        if (strpos($fname,"cds")>0) {
          for($i=0;$i<count($items)-5;$i++)
            if ($items[$i]!="") $out .= $items[$i]."\t";
              else $out .= "\N"."\t";
          //JD
          $out .= $items[$i++].".".substr($items[$i++],2,100)."\t";
          $out .= $items[$i++].".".substr($items[$i++],2,100)."\t";
          //$out .= "\t\N";//spectsampling
          fwrite($f1,$out."\n");
//          if ($items[2]=="") {print_r($items); exit(0);}
        }//cds

        if (strpos($fname,"sxt")>0) {

          $tabshift_sxt = array(0,1,3,4,5,6,7,8,9,11,12,2,13,14,16,15,99,99,99,17,18,19,20,21,22,23,24,99,99,99,99,25,26,27,28,29,99,31,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,32,33,10);
//          echo("count=".count($tabshift_trace)."\n");exit(0);

          for($i=0;$i<34-2;$i++) {
						//echo($i." ".$tabshift_sxt[$i]."\n");
            if (($items[$i]=="")) $out .= "\N"."\t";
              else $out .= $items[$i]."\t";
						}
          //JD
          $i = 32;//$tabshift_sxt[$i++];
          $out .= $items[$i++].".".substr($items[$i++],2,100)."\t";
          $out .= $items[$i++].".".substr($items[$i++],2,100);//."\t";
          //$out .= "\t\N";//spectsampling
         fwrite($f1,$out."\n");
//         echo(">>>".$items[32]."\n");
//         echo($out."\n");exit(0);
         if ($items[2]=="") {print_r($items); exit(0);}
         }//sxt

        if (strpos($fname,"trace")>0) {

          $tabshift_trace = array(0,1,3,4,5,6,7,8,9,11,12,2,13,14,99,15,99,99,99,16,17,18,19,20,21,22,23,99,99,99,99,24,25,26,27,28,99,30,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,99,31,32,10);
//          echo("count=".count($tabshift_trace)."\n");exit(0);

          for($i=0;$i<33-2;$i++) {
						//echo($i." ".$tabshift_sxt[$i]."\n");
            if (($items[$i]=="")) $out .= "\N"."\t";
              else $out .= $items[$i]."\t";
						}
          //JD
          $i = 31;//$tabshift_trace[$i++];
          $out .= $items[$i++].".".substr($items[$i++],2,100)."\t";
          $out .= $items[$i++].".".substr($items[$i++],2,100);//."\t";
          //$out .= "\t\N";//spectsampling
         fwrite($f1,$out."\n");
//         echo(">>>".$items[32]."\n");
//         echo($out."\n");exit(0);
         if ($items[2]=="") {print_r($items); exit(0);}
         }//trace

//      $out = substr($out,0,-2);
      }//if count
    }//while

    fclose($f2);
  }//foreach
	fclose($f1);


/*
solar_cat

Observatory VARCHAR(8),
Telescope VARCHAR(8),
Orbit VARCHAR(8),
InstrumentType VARCHAR(32),
SamplingMethod VARCHAR(32),
DataType VARCHAR(16),
ObservationMode VARCHAR(32),
DomainType VARCHAR(16),
EnergyRegime VARCHAR(8),
PhysicalParam VARCHAR(32),
Contact VARCHAR(64),
Instrument VARCHAR(8),
Date_obs TIMESTAMP,
Date_end TIMESTAMP,
Filename VARCHAR(128),
Sci_Obj VARCHAR(128),
Object VARCHAR(8),
Obj_ID VARCHAR(32),
Obs_prog VARCHAR(128),
CoordSystem VARCHAR(16),
LocationX INTEGER,
LocationY INTEGER,
CoordinateNameX  VARCHAR(8),
CoordinateNameY VARCHAR(8),
CoordinateNameW VARCHAR(8),
SpatialSamplingX FLOAT,
SpatialSamplingY FLOAT,
SpatialSamplingLAM1 FLOAT,
SpatialSamplingLAM2 FLOAT,
SpatialSamplingLAM3 FLOAT,
SpatialSamplingLAM4 FLOAT,
AreaCoveredDX INTEGER,
AreaCoveredDY INTEGER,
Naxis1 LONG,
Naxis2 LONG,
Naxis3 LONG,
Naxis4 LONG,
Filter1 VARCHAR(16),
Filter2 VARCHAR(16),
Filter3 VARCHAR(16),
Filter4 VARCHAR(16),
Filter5 VARCHAR(16),
Filter6 VARCHAR(16),
Filter7 VARCHAR(16),
Filter8 VARCHAR(16),
Filter9 VARCHAR(16),
Filter10 VARCHAR(16),
Filter11 VARCHAR(16),
Filter12 VARCHAR(16),
Filter13 VARCHAR(16),
Filter14 VARCHAR(16),
Filter15 VARCHAR(16),
Filter16 VARCHAR(16),
Filter17 VARCHAR(16),
Filter18 VARCHAR(16),
Filter19 VARCHAR(16),
Filter20 VARCHAR(16),
Filter21 VARCHAR(16),
Filter22 VARCHAR(16),
Filter23 VARCHAR(16),
Filter24 VARCHAR(16),
Wavmin1 FLOAT,
Wavmin2 FLOAT,
Wavmin3 FLOAT,
Wavmin4 FLOAT,
Wavmin5 FLOAT,
Wavmin6 FLOAT,
Wavmin7 FLOAT,
Wavmin8 FLOAT,
Wavmin9 FLOAT,
Wavmin10 FLOAT,
Wavmin11 FLOAT,
Wavmin12 FLOAT,
Wavmin13 FLOAT,
Wavmin14 FLOAT,
Wavmin15 FLOAT,
Wavmin16 FLOAT,
Wavmin17 FLOAT,
Wavmin18 FLOAT,
Wavmin19 FLOAT,
Wavmin20 FLOAT,
Wavmin21 FLOAT,
Wavmin22 FLOAT,
Wavmin23 FLOAT,
Wavmin24 FLOAT,
Wavmax1 FLOAT,
Wavmax2 FLOAT,
Wavmax3 FLOAT,
Wavmax4 FLOAT,
Wavmax5 FLOAT,
Wavmax6 FLOAT,
Wavmax7 FLOAT,
Wavmax8 FLOAT,
Wavmax9 FLOAT,
Wavmax10 FLOAT,
Wavmax11 FLOAT,
Wavmax12 FLOAT,
Wavmax13 FLOAT,
Wavmax14 FLOAT,
Wavmax15 FLOAT,
Wavmax16 FLOAT,
Wavmax17 FLOAT,
Wavmax18 FLOAT,
Wavmax19 FLOAT,
Wavmax20 FLOAT,
Wavmax21 FLOAT,
Wavmax22 FLOAT,
Wavmax23 FLOAT,
Wavmax24 FLOAT,
Slit_num INTEGER,
Exp_time FLOAT,
JD1 FLOAT,
JD2 FLOAT, 
SpectSampling VARCHAR(32)
* 
* Campaign
* 
*/
?>
