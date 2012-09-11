<?php
// Constantes pour les procedures suivantes
$PI = 3.14159265358979;
$DPI = 6.283185307179586;
$DGRAD = 0.01745329251994330;
$T2000 = 2451545.0;
$RSYN = 27.2753;
$GI = 0.126536;
$PRE = 1.0E-4;
$NBJOUR = 365.2422;
$NMAX = 5;

/*function fmod($x, $y) {

        $a = (int)($x/$y);
        //$a = ceil($x/$y);
        $f = $x - ($a*$y);

        return $f;
}*/


function lonsol($dj) {
global $T2000, $DPI;

$c = array(403406E-7,195207E-7,119433E-7,112392E-7,3891E-7,
	2819E-7,1721E-7,-834E-7,660E-7,350E-7,334E-7,314E-7,
	268E-7,242E-7,234E-7,158E-7,132E-7,129E-7,114E-7,
	99E-7,93E-7,86E-7,78E-7,72E-7,68E-7,64E-7,
	-64E-7,46E-7,38E-7,37E-7,32E-7,29E-7,28E-7,
	27E-7,27E-7,25E-7,24E-7,21E-7,21E-7,20E-7,
	18E-7,17E-7,17E-7,14E-7,13E-7,13E-7,13E-7,
	12E-7,10E-7,10E-7,10E-7,10E-7);
$p = array(4.721964,5.937458,1.115589,5.781616,5.5474,
	1.5120,4.1897,2.180,5.415,4.315,4.553,5.198,
	5.989,2.911,1.423,0.061,2.317,3.193,2.828,
	0.52,4.65,4.35,2.75,4.50,3.23,1.22,3.51,
	0.14,3.44,4.37,1.14,2.84,5.96,5.09,1.72,
	2.56,1.92,0.09,5.98,4.03,4.27,0.79,3.10,
	4.24,2.01,2.65,4.98,0.93,2.21,3.59,1.50,
	2.55);
$f = array(0.0001621043,6.2830348067,6.2830821524,
	6.2829634302,12.56605691,12.56609845,6.28324766,
	-0.3375700,12.5659310,5.7533850,-0.0033931,77.7137715,
	7.8604191,0.0005412,3.9302098,-0.0034861,11.5067698,
	1.5774337,0.5296670,5.884927,0.529611,-0.398070,
	5.223769,5.507647,0.026108,1.577385,12.566639,
	18.849103,-0.775655,0.026489,11.790627,5.507575,
	-0.796139,18.848981,0.213219,10.977103,5.486856,
	2.544393,-5.573143,6.069774,0.213279,10.977163,
	6.283014,-0.775282,18.849191,0.020781,2.942463,
	-0.000799,4.694114,-0.006829,2.146325,15.720840);

	$t=($dj-$T2000)/365.25;
	$gl=(-1.3478917)+$DPI*$t;
	for ($i=0;$i<52;$i++)
		$gl=$gl+$c[$i]*sin($p[$i]+$f[$i]*$t);
	$gl=fmod($gl, $DPI);
	if ($gl<0) $gl+=$DPI;
	return $gl;
}

/***************************************************************************/
/*                  CALCUL DES PARAMETRES DU SOLEIL                        */
/*                                                                         */
/*entree : dj : date julienne                                             */
/*         lo,bo,p : sans importance                                       */
/*sortie : lo : longitude au meridien central                              */
/*         bo : inclinaison de l'axe de rotation                           */
/*         p  : angle au pole geographique du pole solaire                 */
/***************************************************************************/

function solphy($dj, &$lo, &$bo, &$p)
{
	global $T2000, $DGRAD, $DPI;
	
	$t=($dj-$T2000)/3652500;
	$t2=$t*$t;
	$t3=pow($t,3);
	$t4=pow($t,4);
	$t5=pow($t,5);
	$gi=7.25*$DGRAD;
	$go=73+(40./60.)+50.25/3600*($dj-2396758.5)/365.25;
	$gm=112.766+(2430000.5-$dj)*14.18439716+180;
	$go=fmod($go*$DGRAD, $DPI);
	$gm=fmod($gm*$DGRAD, $DPI);
	$a1=2.18-3375.70*$t+0.36*$t2;
	$a2=3.51+125666.39*$t+0.10*$t2;
	$dpsi=1E-7*(-834*sin($a1)-64*sin($a2));
	$epsv=0.4090928+1E-7*(-226938*$t-75*$t2+96926*$t3-2491*$t4-12104*$t5+
		446*cos($a1)+28*cos($a2));
	$gla=lonsol($dj);
	$gl=$gla-$dpsi;
	$c=cos($gl-$go);
	$s=sin($gl-$go);
	//print "a1: $a1 a2: $a2 dpsi: $dpsi gla: $gla gl: $gl c: $c s: $s<BR>";
	$lo=atan2(-$s*cos($gi),-$c)+$gm;
	$lo= fmod($lo ,$DPI);
	if ($lo<0) $lo+=$DPI;
	$bo=asin($s*sin($gi));
	$x=atan(-cos($gla)*tan($epsv));
	$y=atan((-$c)*tan($gi));
	$p=$x+$y;
}

// calcul date de debut (datedeb) et numero de rotation (numrot) si dj (date julienne) est different de 0
// sinon calcul dj et datedeb en fonction de numrot
function debutrot($dj, &$datedeb, &$numrot) {
	global $NMAX, $T2000, $NBJOUR, $DGRAD, $DPI, $RSYN, $PRE, $GI, $PI;
	
	if (!$dj) $dj = 2398166.5 + ($numrot-1)*27.2753+10;

	$datedeb = 0.0;
	$numrot = 0;
	$ti = $dj;

	for ($n = 1; $n <= $NMAX; $n++) {
		
		$t1=($dj-$T2000)/($NBJOUR*10000.0);
		$t2=$t1*$t1;
		$go = 73.0 + 40.0 / 60.0 + (50.25 * ($ti - 2396758.5))/(3600 * $NBJOUR);
		$gm=112.766+(2430000.5-$ti)*14.18439716+180.0;
		$go = fmod($go*$DGRAD, $DPI);
		$gm = fmod($gm*$DGRAD, $DPI);
		//print "go: $go gm: $gm<BR>";
		$a1=2.18-3375.70*$t1+0.36*$t2;
		$a2=3.51+125666.39*$t1+0.10*$t2;
		$dpsi=1.0E-7*(-834*sin($a1)-64*sin($a2));
		$gla=lonsol($ti);
		$gl=$gla-$dpsi;
		$c=cos($gl-$go);
		$s=sin($gl-$go);
		//print "t1: $t1 t2: $t2 a1: $a1 a2: $a2 dpsi: $dpsi gla: $gla gl: $gl c: $c s: $s<BR>";
		$l0=atan2(-$s*cos($GI),-$c)+$gm;
		$l0= fmod($l0, $DPI);
		
		if ($n == 1 && $l0 > 0)
			$l0 -= $DPI;
		if ($n != 1 && abs($l0) > $PI)
			$l0 -= $DPI*$l0/abs($l0);
		//print "lo: $l0<BR>\n";
		$tf = $ti + $l0 * $RSYN / $DPI;
		//print "tf: $tf ti: $ti<BR>";
		if (abs($tf-$ti) < $PRE) {
			$datedeb = $tf;
			$numrot = (int)((($datedeb+1)-2398167.4)/$RSYN)+1;
			return 1;
		}
		$ti = $tf;
	}
	return 0;
}

?>
