<?php
	# =============================================
	# Helio 2010-2011 HEC server - by Andrej Santin
	# INAF - Trieste Astronomical Observatory
	# ---------------------------------------------
	# read OACT Observatory GHAN log
	# web site: http://bbso.njit.edu
    #  11-mar-2011
	# last rev. 11-mar-2011 
	# =============================================
	define("DEBUG","0");
	
	//http://swrl.njit.edu/ghn_web/logs/cao_log/ (ftp://inaf-node-83.oact.inaf.it)
	//http://swrl.njit.edu/ghn_web/logs/ynao_log/

	//http://sun10.bao.ac.cn/hsos_datas/full_disk/h-alpha/
	//http://bass2000.obspm.fr/home.php
	
        $tempdir = ".";///var/www/hec/temp";

        // parse files and create postgres-ready file
        $f1 = fopen("$tempdir/GHAN-OACT.append.postgres.converted",'w');
        //wget -r http://swrl.njit.edu/pub/archive/logs/cao_log/
        //find swrl.njit.edu/pub/archive/logs/cao_log/ -iname "*txt" -exec cp "{}" alltxt/. \;
        //cat alltxt/*txt >oact_all_new.txt
        $cmd = "wget -nc -r -l2 http://swrl.njit.edu/pub/archive/logs/cao_log/ 2>&1 | grep saved | grep txt";
        echo $cmd."\n";
        echo "wait...\n";
        exec($cmd,$output);
        //12:55:59 (13.55 MB/s) - `swrl.njit.edu/pub/archive/logs/cao_log/2011/oact_log_20110509.txt' saved [1020/1020]
        echo count($output)." files\n";
        foreach ($output as $k => $v) {
          $p = stripos($v,'`');//search next
          $e = stripos($v,"'");//find end
          $name = substr($v,$p+1,$e-$p-1);//extract link
          echo $name."\n";

        $f2 = fopen("$tempdir/$name",'r');
//oact_halph_fi_20020301_085049.fts
//oact_halph_color_20060211_104340.fts
//oact_halph+0.5_fi_20030208_110256.fts
        while (!feof ($f2)) {
          $buffer = fgets($f2);
          $items = explode(" ", $buffer);
          //print_r($items);
          $i = strpos($items[0],'.fts')-29;
          $timestart = substr($items[0],$i+14,4)."-";//2000-03-26T00:03:29
          $timestart .= substr($items[0],$i+18,2)."-";
          $timestart .= substr($items[0],$i+20,2)."T";
          $timestart .= substr($items[0],$i+23,2).":";
          $timestart .= substr($items[0],$i+25,2).":";
          $timestart .= substr($items[0],$i+27,2);
//print_r($timestart); exit(0);
          $timeend = $timestart;
    		  $observatory = "OACT";
          $telescope = "\N";
          $instrument = "\N";
          $size_pixels = "\N";
          $filetype = "FITS";
          $filepath = "\N";
          $filename = trim($items[0]);
          $ispubblic = "N";
          $contact ="prom@sunct.ct.astro.it";
          if (($filename!="") and
              (strlen($filename)<40) and
              (strlen($timestart)==19) and
              is_numeric(substr($timestart,0,4)) and
              is_numeric(substr($timestart,14,2)))
            fwrite($f1,$timestart."\t".$timeend."\t".$observatory."\t".$telescope."\t".$instrument."\t".$size_pixels."\t".$filetype."\t".$filepath."\t".$filename."\t".$ispubblic."\t".$contact."\n");
          else  print_r($items);
        }//while
        fclose($f2);
      }//foreach
        fclose($f1);

?>
