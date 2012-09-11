<?php
$igreg = 15+31*(10+12*1582);
$tu_te = 58.0;

class Datej {
	var $dj; //representation en date julienne
	var $j, $m, $a, $hh, $mn; //representation calendaire
	var $Lo, $B, $P; //parametres du Soleil
	
	function Datej() {
		$this->dj=0.0;
		$this->a = 0; $this->m = 0;  $this->j = 0;
		$this->hh = 0; $this->mn = 0;
	}
	
	function initFromJul($datejul) {
		$this->dj=$datejul;
		$this->Julian2Date();
		$this->Julian2Hour();
	}
	
	function initFromCal($a, $m, $j, $hh, $mn) {
		$this->a = $a; $this->m = $m;  $this->j = $j;
		$this->hh = $hh; $this->mn = $mn;
		$this->Date2Julian();
	}
	
	function Verifier() {

		$mois=array(31,29,31,30,31,30,31,31,30,31,30,31);
		
		// test de la coherence de la date
		$ok = 1;
		if ($this->a >= 1 && $this->a <= 99)    $this->a+=1900;
		if ($this->a <= 1800 || $this->a > 3000) $ok = 0;   // date incorrecte
		if ($this->m < 1 || $this->m > 12)       $ok = 0;   // date incorrecte
		if ($this->j < 1 || $this->j > $mois[$m-1])	$ok = 0; // date incorrecte
		
		// test de la coherence de l'heure
		if ( $this->hh<24 && $this->mn<60 ) $ok=1;
		else $ok=0;
		return $ok;
	}
	
	function Get($fmt) {
	 	switch($fmt) {
	 		case "jul":
	 			return $this->dj;
	 		default:
	 			return  $this->a."-".$this->m."-".$this->j."/".$this->hh.":".$this->mn;
	 	}
	}

	// calcul $dj en fonction $j, $m, $a	
	function Date2Julian() {
		global $igreg, $tu_te;
	
		if ($this->a < 0) ++$this->a;
		if ($this->m > 2) {
			$jy=$this->a;
			$jm=$this->m+1;
		} else {
			$jy=$this->a-1;
			$jm=$this->m+13;
		}
		$this->dj = (double)(floor(365.25*$jy)+floor(30.6001*$jm)+$this->j+1720995.0);
		
		if ($this->j+31*($this->m+12*$this->a) >= $igreg) {
			$ja=(int)(0.01*$jy);
			$this->dj += 2-$ja+(int)(0.25*$ja);
		}
		$this->dj -= 0.5;     /* centrage sur 00h00, la date julienne est centree sur midi*/
		$this->dj += ((double)$this->hh / 24.0 );     /* midi */
		$this->dj += ((double)$this->mn / 1440.0 );
		$this->dj += ((double)$tu_te/86400.0);
	}

	// calcul $hh, $mn  en fonction de dj
	function Julian2Hour() {
		$ijd = (int)floor($this->dj);
		$frc= $this->dj - $ijd;
		if ($frc != 0) {
			if ($frc >= 0.5) {
				$ijd = $ijd + 1;
				$frc = $frc - 0.5;
		}
		else $frc = $frc + 0.5;
		$this->hh = (int)floor($frc*24);
		$this->mn = (int)floor((24*$frc - $this->hh)*60);
		}
		else {
			$this->hh = 12;
			$this->mn = 0;
		}
	}

	// calcul $a, $m, $j en fonction de $dj
	function Julian2Date() {
		global $igreg;

		$julian = (int)floor($this->dj);

		if ($julian >= $igreg) {
			$jalpha=(int)(((float) ($julian-1867216.0)-0.25)/36524.25);
			$ja=$julian+1+$jalpha-(int)(0.25*$jalpha);
		}
		else
			$ja=$julian;
		$jb=$ja+1524;
		$jc=(int)(6680.0+((float) ($jb-2439870.0)-122.1)/365.25);
		$jd=365*$jc + (int)(0.25*$jc);
		$je=(int)(($jb-$jd)/30.6001);
		$this->j=(int)($jb-$jd-(int)(30.6001*$je));
		$this->m=(int)$je-1;
		if ($this->m > 12) $this->m -= 12;
		$this->a=(int)$jc-4715.0;
		if ($this->m > 2) --$this->a;
		if ($this->a <= 0) --$this->a;
		//if ( ($this->dj - (int)($this->dj)) >= 0.5 ) $this->AjouteJour();
		if ( ($this->dj - floor($this->dj)) >= 0.5 ) $this->AjouteJour();
	}

	function AjouteJour() {
		$this->j++;

		if ($this->m==2) {
			if (4*($this->a/4)==$this->a && 400*($this->a/400)==$this->a)  // si annee bisextile
				$max=29;
			else
				$max=28;
			if ($this->j>$max) {
				$this->j=1;
				$this->m=3;
			}
		}
		if (($this->j==31 && ($this->m==4 || $this->m==6 || $this->m==9 || $this->m==11)) || $this->j==32) {
			$this->j=1;
			$this->m++;
		}
		if ($this->m==13) {
			$this->m=1;
			$this->a++;
		}
	}
}
?>
