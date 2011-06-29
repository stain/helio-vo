<?php
	# =============================================
	# Helio 2010-2011 HEC server - by Andrej Santin
	# INAF - Trieste Astronomical Observatory
	# ---------------------------------------------
	# read Big Bear Solar Observatory GHAN log
	# web site: http://bbso.njit.edu
    #  2-feb-2011
	# last rev. 2-feb-2011
	# =============================================
	define("DEBUG","0");
	
	//http://swrl.njit.edu/ghn_web/logs/cao_log/ (ftp://inaf-node-83.oact.inaf.it)
	//http://swrl.njit.edu/ghn_web/logs/ynao_log/

	//http://sun10.bao.ac.cn/hsos_datas/full_disk/h-alpha/
	//http://bass2000.obspm.fr/home.php
	
        $tempdir = ".";///var/www/hec/temp";

        // parse files and create postgres-ready file
        $f1 = fopen("$tempdir/GHAN-BBSO.postgres.converted",'w');
        //wget -r -l2 -O bbso.raw http://swrl.njit.edu/ghn_web/logs/bbso_log
        //grep --binary-files=text fts bbso.raw > bbso_all_logs.txt
        $f2 = fopen("$tempdir/bbso.log",'r');
//  bbso_halph_fi_20080409_160454.fts H  1     0 16     0     0

        while (!feof ($f2)) {
          $buffer = fgets($f2);
          if (strpos("file",$buffer)>0) $buffer = fgets($f2);
          $items = explode(" ", $buffer);
//print_r($items);
          $timestart = substr($items[2],14,4)."-";//2000-03-26T00:03:29
          $timestart .= substr($items[2],18,2)."-";
          $timestart .= substr($items[2],20,2)."T";
          $timestart .= substr($items[2],23,2).":";
          $timestart .= substr($items[2],25,2).":";
          $timestart .= substr($items[2],27,2);
//print_r($timestart);
          $timeend = $timestart;
		  $observatory = "BigBear";
          $telescope = "\N";
          $instrument = "\N";
          $size_pixels = "\N";
          $filetype = "FITS";
          $filepath = "\N";
          $filename = $items[2];
          $ispubblic = "N";
          $contact ="http://bbso.njit.edu";
          if (($filename!="") and
              (strlen($filename)<40) and
              (strlen($timestart)==19) and
              is_numeric(substr($timestart,0,4)) and
              is_numeric(substr($timestart,14,2)))
            fwrite($f1,$timestart."\t".$timeend."\t".$observatory."\t".$telescope."\t".$instrument."\t".$size_pixels."\t".$filetype."\t".$filepath."\t".$filename."\t".$ispubblic."\t".$contact."\n");
          else  print_r($items);
        }//while
        fclose($f2);
        fclose($f1);

?>
