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
        //$f1 = fopen("$tempdir/GHAN-BBSO.postgres.converted",'w');
        $f1 = fopen("$tempdir/GHAN-YNAO.append.postgres.converted",'w');
        //wget -r -l2 -O bbso.raw http://swrl.njit.edu/ghn_web/logs/bbso_log
        //grep --binary-files=text fts bbso.raw > bbso_all_logs.txt
        //$f2 = fopen("$tempdir/bbso_all_logs.txt",'r');
        $cmd = "wget -nc -r -l2 http://swrl.njit.edu/pub/archive/logs/ynao_log/ 2>&1 | grep saved | grep txt";
        echo $cmd."\n";
        echo "wait...\n";
        exec($cmd,$output);
        echo count($output)." files\n";
        foreach ($output as $k => $v) {
          $p = stripos($v,'`');//search next
          $e = stripos($v,"'");//find end
          $name = substr($v,$p+1,$e-$p-1);//extract link
          echo $name."\n";

        $f2 = fopen("$tempdir/$name",'r');
//  bbso_halph_fi_20080409_160454.fts H  1     0 16     0     0
//ynao_halph_fd_20071230_035027.fts
//ynao_20010108_061824509.fts
//YNAO~344 FTS     4,124,160  11-25-00   4:26 ynao_20001125_042641509.fts
        $read_cnt=0;
        $write_cnt=0;
        while (!feof ($f2)) {
          $buffer = fgets($f2);
          $read_cnt++;
          $items = explode(" ", trim($buffer));
//print_r($items);
          if (array_key_exists(12,$items)) $items[0] = $items[12];
          //if (strlen($items[0])>49) $i=-35; 
          //else {
            if (strlen($items[0])>30) $i=0; else $i=9;
          //}
          //$i = strpos($items[0],".")-18+14;
          $timestart = substr($items[0],14-$i,4)."-";//2000-03-26T00:03:29
          $timestart .= substr($items[0],18-$i,2)."-";
          $timestart .= substr($items[0],20-$i,2)."T";
          $timestart .= substr($items[0],23-$i,2).":";
          $timestart .= substr($items[0],25-$i,2).":";
          $timestart .= substr($items[0],27-$i,2);

// if ($i==9) print($timestart."...\n");// exit(0);
          $timeend = $timestart;
		  $observatory = "YunNan";
          $telescope = "\N";
          $instrument = "\N";
          $size_pixels = "\N";
          $filetype = "FITS";
          $filepath = "\N";
          $filename = $items[0];
          $ispubblic = "N";
          $contact ="http://www.ynao.ac.cn";
          if (($filename!="") and
              (strlen($filename)<40) and
              (strlen($timestart)==19) and
              is_numeric(substr($timestart,0,4)) and
              is_numeric(substr($timestart,5,2)) and
              is_numeric(substr($timestart,8,2)) and
              is_numeric(substr($timestart,14,2))) {
            fwrite($f1,$timestart."\t".$timeend."\t".$observatory."\t".$telescope."\t".$instrument."\t".$size_pixels."\t".$filetype."\t".$filepath."\t".$filename."\t".$ispubblic."\t".$contact."\n");
            $write_cnt++;
          } else { 
            print_r($items);
            print($timestart."\n");
          }
        }//while
        fclose($f2);
      }//foreach
        fclose($f1);
        print("\nread_cnt=$read_cnt write_cnt=$write_cnt\n");
?>
