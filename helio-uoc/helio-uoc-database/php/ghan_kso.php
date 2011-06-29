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
	
	
        $tempdir = ".";///var/www/hec/temp";

        // parse files and create postgres-ready file
        $f1 = fopen("$tempdir/GHAN-KSO.append.postgres.converted",'w');
        //create
        //$cmd = 'find swrl.njit.edu -iname "*txt"|sed \'s/swrl/`swrl/g\'|sed "s/.txt/.txt\'/g"';
        //append
        $cmd = "wget -nc -r -l2 http://swrl.njit.edu/pub/archive/logs/kso_log/ 2>&1 | grep saved | grep txt";
        
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
///data/halpha3/patrol/20110101/processed/kanz_halph_fi_20110101_070156.fts.gz

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
    		  $observatory = "KSO";
          $telescope = "\N";
          $instrument = "\N";
          $size_pixels = "\N";
          $filetype = "FITS";
          $filepath = "\N";
          $filename = substr(trim($items[0]),$i,100);
          $ispubblic = "N";
          $contact ="arh@igam.uni-graz.ac.at";
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
