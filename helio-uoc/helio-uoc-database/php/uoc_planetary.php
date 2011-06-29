<?php
  # =============================================
  # HELIO 2010 UOC server - by Andrej Santin
  # ---------------------------------------------
  # read GLE
  # web site:
  # helio_planetary.php
  # 1st 02-jul-2010, last 02-jul-2010
  # =============================================

  //require ("sec_global.php");
  $tempdir = "/var/www/html/radiosun/hec/temp";
$tempdir = ".";

  // get files from HTTP
  //exec ("wget http://www.");
  //copy ("current.txt",$tempdir."/current.txt");
  //unlink ("current.txt");

  // parse files and create postgres-ready file
  $f1 = fopen("$tempdir/PLANETARY.postgres.converted",'w');
//  $files = array("CASSINI__MAG_catalogue.txt","MESSENGER__MAG_cataloguex.txt","MGS__MAG_ER_catalogue.txt");
  $files = array(
    "CASSINI__MAG_1_catalogue.txt",
    "CASSINI__MAG_2_catalogue.txt",
    "MESSENGER__MAG_catalogue.txt",
    "MESSENGER__XRS_catalogue.txt",
    "MEX__ELS_catalogue.txt",
    "MGS__ER_catalogue.txt",
    "MGS__MAG_catalogue.txt",
    "NEAR__MAG_catalogue.txt",
    "VEX__ELS_catalogue.txt",
    "VEX__IMA_catalogue.txt",
    "VEX__MAG4_catalogue.txt",
    "MESSENGER__EPS_catalogue_01.txt",
    "MESSENGER__EPS_catalogue_02.txt",
    "MESSENGER__EPS_catalogue_03.txt",
    "MESSENGER__EPS_catalogue_04.txt",
    "MESSENGER__EPS_catalogue_05.txt",
    "MESSENGER__EPS_catalogue_06.txt",
    "MESSENGER__FIPS_catalogue_07.txt",
    "MESSENGER__FIPS_catalogue_08.txt",
    "MESSENGER__FIPS_catalogue_09.txt",
    "MESSENGER__FIPS_catalogue_10.txt",
    "MESSENGER__FIPS_catalogue_11.txt");

  foreach ($files as $fname) {
    $f2 = fopen("$tempdir/$fname",'r');
    if ($f2 == null) {
      echo("$tempdir/$fname"." not found\n");
      exit(0);
    }
    $buffer = fgets($f2);//skip first line

    $buffer = fgets($f2);//provaider
    $buffer = trim($buffer);
    $provaider = substr($buffer,strpos($buffer,":")+2,100);

    $buffer = fgets($f2);//Obsinst_key
    $buffer = trim($buffer);
    $obsinst_key = substr($buffer,strpos($buffer,":")+2,100);

    $buffer = fgets($f2);//Ancillary Info
    $buffer = trim($buffer);
    $ancil_info = substr($buffer,strpos($buffer,":")+2,100);
    if ($ancil_info=='') $ancil_info = '\N';

    $buffer = fgets($f2);//skip 5th line
    $buffer = fgets($f2);//root
    $buffer = trim($buffer);
    $root = substr($buffer,strpos($buffer," ")+1,100);
    echo ">$provaider< >$obsinst_key< >$ancil_info< >$root<\n";

    while (!feof ($f2)) {
      $buffer = fgets($f2);
      echo("buffer=".$buffer);
      $items = explode(";", $buffer);
      print_r($items);
      $ancil_filename = $items[0];
      $url = $root.trim($items[3]);
      //obsinst_key,provaider,time_start,time_end,url,ancil_filename,ancil_info
      if (($items[0]!="") and
          ($items[1]!="")) fwrite($f1,$obsinst_key."\t".$provaider."\t".$items[1]."\t".$items[2]."\t".$url."\t".$ancil_filename."\t".$ancil_info."\n");//
    }//while
    fclose($f2);

  }//foreach
  fclose($f1);

?>
