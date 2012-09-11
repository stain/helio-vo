<?php
// Fonctions utilis�es pour la cr�taion des cartes du soleil plane ou virtuelle

// lc = longitude centre de la carte, li=amplitude en longitude de la carte (lc-li � lc+li)
function xtrans($x, $lc, $li, $sx) {
	$mx = 45;
	$a = ($sx-2*$mx)/(2*$li);
	$b = $mx-$a*($lc-$li);
	return ($a*$x+$b);
}

// yc = latitude du centre de la carte, yi = amplitude de la carte en latitude (yc-yi � yc+yi)
function ytrans($y, $yc, $yi, $sy) {
	$mh = 20;
	$mb = 20;
	$c = ($mh+$mb-$sy)/(2*$yi);
	$d = $mh-($yc+$yi)*$c;
return ($c*$y+$d);
}

// fonction de transformation des coordonn�es lon,lat en coordonn�es sph�riques
function ToHelio($lon, $lat, $b0, $p0, $r, $sx) {
	$sb=sin($b0);
	$cb=cos($b0);
	$sp=sin($p0);
	$cp=cos($p0);

	$lon = deg2rad($lon);
	$lat = -deg2rad($lat);
	$slg=sin($lon);
	$clg=cos($lon);
	$sla=sin($lat);
	$cla=cos($lat);
	$newX = $r*($cp*$cla*$slg - $sp*$sla*$cb + $sp*$cla*$clg*$sb) + $sx/2;
	$newY = $r*($sp*$cla*$slg + $cp*$sla*$cb - $cp*$cla*$clg*$sb) + $sx/2;
	return array('lon'=>$newX, 'lat'=>$newY);
}


// affiche une grille de coordonn�es planes centr�e sur lo ou sur 180 suivant la valeur de type
function draw_grille($im, $sx, $sy, $lo, $type) {
	$cBlack=ImageColorAllocate($im,0,0,0);
	$cBlue=ImageColorAllocate($im, 0, 0, 255);
	$cRed=ImageColorAllocate($im, 255, 0, 0);
	ImageString ($im, 3, 10, $sy/2, "E", $cBlack);
	ImageString ($im, 3, $sx-10, $sy/2, "W", $cBlack);

	switch($type) {
		case 'synop':
			$mc = 180; $lc = 180; $pas = 20;
			break;
		default:
			$mc = rad2deg($lo); $lc = 90; $pas = 10; $yc=0; $yi=90;
	}
	//dessin du cadre
	ImageLine($im, (int)xtrans($mc-$lc, $mc, $lc, $sx), (int)ytrans(90, $yc, $yi, $sy), (int)xtrans($mc+$lc, $mc, $lc, $sx), (int)ytrans(90, $yc, $yi, $sy), $cBlack);
	ImageLine($im, (int)xtrans($mc-$lc, $mc, $lc, $sx), (int)ytrans(-90, $yc, $yi, $sy), (int)xtrans($mc+$lc, $mc, $lc, $sx), (int)ytrans(-90, $yc, $yi, $sy), $cBlack);
	// dessin des paralleles
	$curX=(int)xtrans($mc-$lc, $mc, $lc, $sx);
	$newX=(int)xtrans($mc+$lc, $mc, $lc, $sx);
	for ($i=-60; $i <= 60; $i=$i+10) {
		$curY=(int)ytrans($i, $yc, $yi, $sy);
		$newY=(int)ytrans($i, $yc, $yi, $sy);
		if ($i == 0) ImageLine($im, $curX, $curY, $newX, $newY, $cBlue);
		else ImageLine($im, $curX, $curY, $newX, $newY, $cBlack);
		ImageString($im, 2, 25 , $curY-5, $i, $cBlack);
	}

	// dessin des m�ridiens
	$curY=(int)ytrans(90, $yc, $yi, $sy);
	$newY=(int)ytrans(-90, $yc, $yi, $sy);
	for ($i=$mc-$lc; $i <= $mc+$lc; $i=$i+$pas) {
		$curX=(int)xtrans($i, $mc, $lc, $sx);
		$newX=(int)xtrans($i, $mc, $lc, $sx);
		ImageLine($im, $curX, $curY, $newX, $newY, $cBlack);
		$mark = $i;
		if ($i>360) $mark = $i-360;
		if ($i<0) $mark = $i+360;
		ImageString($im, 2, $curX-5 , ytrans(-92, $yc, $yi, $sy), round($mark), $cBlack);
	}
	$lon = rad2deg($lo);
	ImageLine($im, (int)xtrans($lon, $mc, $lc, $sx), (int)ytrans(90, $yc, $yi, $sy), (int)xtrans($lon, $mc, $lc, $sx), (int)ytrans(-90, $yc, $yi, $sy), $cBlue);
	ImageString($im, 2, (int)xtrans($lon, $mc, $lc, $sx), (int)ytrans(96, $yc, $yi, $sy), "L0=".(round($lon))."dg", $cBlack);

	// ligne de partage entre rotations
	if ( ($mc-$lc)<360 && 360<($mc+$lc) )
		ImageLine($im, (int)xtrans(360, $mc, $lc, $sx), (int)ytrans(90, $yc, $yi, $sy), (int)xtrans(360, $mc, $lc, $sx), (int)ytrans(-90, $yc, $yi, $sy), $cRed);
	if ( ($mc-$lc)<0 && 0<($mc+$lc) )
		ImageLine($im, (int)xtrans(0, $mc, $lc, $sx), (int)ytrans(90, $yc, $yi, $sy), (int)xtrans(0, $mc, $lc, $sx), (int)ytrans(-90, $yc, $yi, $sy), $cRed);

}

//Dessine une grille de coordonn�es sph�riques
function draw_soleil($im, $l0, $b0, $p0, $sx, $r) {
	$cBlack=ImageColorAllocate($im,0,0,0);
	$cRed=ImageColorAllocate($im, 255, 0, 0);
	$cBlue=ImageColorAllocate($im, 0, 0, 255);

	// paralleles
	for ($i=-80; $i<=80; $i=$i+10) {
		for ($j=-90; $j<=90; $j++) {
			$coord = ToHelio($j, $i, $b0, $p0, $r, $sx);
			if ($i == 0) ImageSetPixel($im, $coord['lon'], $coord['lat'], $cBlue);
			else ImageSetPixel($im, $coord['lon'], $coord['lat'], $cBlack);
    	}
	}
	// meridiens
	for ($i=-90; $i<=90; $i=$i+10) {
		for ($j=-90; $j<=90; $j++) {
			$coord = ToHelio($i, $j, $b0, $p0, $r, $sx);
			if ($i == 0) ImageSetPixel($im, $coord['lon'], $coord['lat'], $cBlue);
			else ImageSetPixel($im, $coord['lon'], $coord['lat'], $cBlack);
    	}
	}
	// ligne de partage entre 2 rotations
	$lon = 360-rad2deg($l0);
	for ($j=-90; $j<=90; $j++) {
		$coord = ToHelio($lon, $j, $b0, $p0, $r, $sx);
//		ImageSetPixel($im, $coord['lon'], $coord['lat'], $cRed);
		ImageFilledEllipse($im, $coord['lon'], $coord['lat'], 2, 2, $cRed);
	}

}

function draw_filam_spher($im, $results, $flg, $l0, $b0, $p0, $sx, $r) {
	$cBlack=ImageColorAllocate($im,0,0,0);
	$lon=$results['LON'];
	$lat=$results['LAT'];
	$intensity = $results['INTENSITY'];
	if ($flg == 0) $col=ImageColorAllocate($im, 255-$intensity, 255, 255-$intensity);/*$col=ImageColorAllocate($im, 0, 200, 0);*/
	else $col=ImageColorAllocate($im, 200, 200, 0);

	$nb = count($lon);
	$lon[0] = ($lon[0] + 360*$flg) - rad2deg($l0);
	$current = ToHelio($lon[0], $lat[0], $b0, $p0, $r, $sx);
	for($i=1;$i<$nb;$i++){
		if ($lon[$i] == 888) {
			$i++;
			$lon[$i] = ($lon[$i] + 360*$flg) - rad2deg($l0);
			$new = ToHelio($lon[$i], $lat[$i], $b0, $p0, $r, $sx);
			$mid['lon'] = ($current['lon'] + $new['lon'])/2;
			$mid['lat'] = ($current['lat'] + $new['lat'])/2;
			//if ( ($mid['lon'] <= $lims) && ($mid['lat'] >= $limi) )
				ImageSetPixel($im, $mid['lon'], $mid['lat'], $col);
		}
		else {
			$lon[$i] = ($lon[$i] + 360*$flg) - rad2deg($l0);
			//if ( ($lon[$i] <= 90) && ($lon[$i] >= -90) ) {
				$new = ToHelio($lon[$i], $lat[$i], $b0, $p0, $r, $sx);
				ImageLine($im,$current['lon'],$current['lat'],$new['lon'],$new['lat'],$col);
			//}
		}
		$current = $new;
	}
	$lon_b = ($results['LON_B'] + 360*$flg) - rad2deg($l0);
	$current = ToHelio($lon_b, $results['LAT_B'], $b0, $p0, $r, $sx);
	imagesetthickness($im, 1);
	imagefilledellipse($im, $current['lon'], $current['lat'], 2, 2, $cBlack);
}

function draw_filam($im, $results, $flg, $sx, $sy, $xc, $yc, $xi, $yi) {
	$cBlack=ImageColorAllocate($im,0,0,0);
	$lon=$results['LON'];
	$lat=$results['LAT'];

	$nb = count($lon);
	if ($flg == 0) $col=ImageColorAllocate($im, 0, 200, 0);
	else $col=ImageColorAllocate($im, 200, 200, 0);
	$curX = xtrans($lon[0] + 360*$flg, $xc, $xi, $sx);
	$curY = ytrans($lat[0], $yc, $yi, $sy);
	for($i=1;$i<$nb;$i++){
		if ($lon[$i] == 888) {
			$i++;
			$newX=xtrans($lon[$i] + 360*$flg, $xc, $xi, $sx);
			$newY=ytrans($lat[$i], $yc, $yi, $sy);
			$midX = ($curX + $newX)/2;
			$midY = ($curY + $newY)/2;
			ImageSetPixel($im, $midX, $midY, $col);
		}
		else {
			$newX=xtrans($lon[$i] + 360*$flg, $xc, $xi, $sx);
			$newY=ytrans($lat[$i], $yc, $yi, $sy);
			ImageLine($im,$curX,$curY,$newX,$newY,$col);
		}
		$curX=$newX;
		$curY=$newY;
	}
	$newX=xtrans($results['LON_B'] + 360*$flg, $xc, $xi, $sx);
	$newY=ytrans($results['LAT_B'], $yc, $yi, $sy);
	imagefilledellipse($im, $newX, $newY, 2, 2, $cBlack);
	return $im;
}

function draw_noaa($im, $results, $key, $sx, $sy, $lo, $type) {
	$font = 'Times New Roman';
	
	switch($type) {
		case 'synop':
			$mc = 180; $lc = 180;
			break;
		default:
			$mc = rad2deg($lo); $lc = 90; $yc=0; $yi=90;
	}
	if ($results['LON_C'][$key] > ($mc+90)) $lon=xtrans($results['LON_C'][$key] - 360, $mc, $lc, $sx);
	else if ($results['LON_C'][$key] < ($mc-90)) $lon=xtrans($results['LON_C'][$key] + 360, $mc, $lc, $sx);
	else $lon=xtrans($results['LON_C'][$key], $mc, $lc, $sx);
	$lat=ytrans($results['LAT_C'][$key], $yc, $yi, $sy);

	//$col=ImageColorAllocate($im, 234, 168, 0);
	//$col=ImageColorAllocate($im, 0, 255, 0);
	$col=ImageColorAllocate($im, 0, 0, 0);
	//ImageFilledRectangle($im, $lon-1, $lat-1, $lon+1, $lat+1, $col);
	$fontwidth = ImageFontWidth($font);
	$fontheight = ImageFontHeight($font);
	ImageString($im, 2, $lon-($fontwidth/2), $lat-$fontheight, "x", $col);
	ImageString($im, 3, $lon+1, $lat+1, $results['NUM'][$key], $col);
	return $im;
}

function draw_noaa_helio($im, $results, $key, $l0, $b0, $p0, $sx, $r) {
	global $global;
	$font_ttf = $global['FONT_PATH'];
	//$font = 'Times New Roman';
	
	$col=ImageColorAllocate($im,0,0,0);
	//$col=ImageColorAllocate($im, 234, 168, 0);
	$lon=$results['LON_C'][$key] - rad2deg($l0);
	$lat=$results['LAT_C'][$key];

	$coord = ToHelio($lon, $lat, $b0, $p0, $r, $sx);
	$newX = $coord['lon'];
	$newY = $coord['lat'];

	//$fontwidth = ImageFontWidth($font);
	//$fontheight = ImageFontHeight($font);
	//ImageString($im, 2, $newX-($fontwidth/2), $newY-$fontheight, "x", $col);
	ImageString($im, 2, $newX, $newY, "x", $col);
	//ImageFilledRectangle($im, $newX-1, $newY-1, $newX+1, $newY+1, $col);
	//ImageString($im, 3, $newX, $newY, $results['NUM'][$key], $col);
	imagettftext($im, 10, 0, $newX+10, $newY+20, $col, $font_ttf, $results['NUM'][$key]);
	return $im;
}

function draw_spot($im, $results, $flg, $sx, $sy, $xc, $yc, $xi, $yi) {

	$lon=$results['LON_C'];
	$lat=$results['LAT_C'];
	$diam = $results['DIAM'];
	$nb = count($lon);
	if ($flg == 0)
		$col=ImageColorAllocate($im, 0, 0, 255 - 5*$nb);
	else
		$col=ImageColorAllocate($im, 0, 255 - 5*$nb, 255 - 5*$nb);
	for($i=0;$i<$nb;$i++){
		$centX=xtrans($lon[$i] + 360*$flg, $xc, $xi, $sx);
		$centY=ytrans($lat[$i], $yc, $yi, $sy);
		$d=xtrans($lon[$i]+$diam[$i]+360*$flg, $xc, $xi, $sx)-$centX;
		ImageArc($im, $centX, $centY, $d, $d, 0, 360, $col);
		//ImageFilledRectangle($im, $centX-$d, $centY-$d, $centX+$d, $centY+$d, $col);
		// remplissage
		$diameter = $d;
		while($diameter > 0) {
			imagearc($im, $centX, $centY, $diameter, $diameter, 0, 361, $col);
			$diameter--;
		}
	}
	return $im;
}

// dessin d'un groupe de taches sur une grille h�liographique
function draw_spot_spher($im, $results, $flg, $l0, $b0, $p0, $sx, $r) {

	$lon=$results['LON_C'];
	$lat=$results['LAT_C'];
	$diam = $results['DIAM'];
	$max_intensity = min($results['INTENSITY']);
	$min_intensity = max($results['INTENSITY']);

	$nb = count($lon);

	// pour chaque taches on calcule les points d'un cercle de meme diametre
	for($t=0;$t<$nb;$t++){
		if ($max_intensity == $min_intensity) $intensity = 255;
		else $intensity = (200/($max_intensity-$min_intensity))*($results['INTENSITY'][$t]-$min_intensity)+55;
		if ($flg == 0)
			$col=ImageColorAllocate($im, 255-$intensity, 255-$intensity, 255);/*$col=ImageColorAllocate($im, 0, 0, 255 - $nb);*/
		else
			$col=ImageColorAllocate($im, 0, 255 - $nb, 255 - $nb);
		$lon[$t] = ($lon[$t] + 360*$flg) - rad2deg($l0);
		//points du cercle
		for ($theta=0; $theta<=(2*pi()); $theta=$theta+(pi()/10.)) {
			$x[] =  ($diam[$t]*cos($theta))/2. + $lon[$t];
            $y[] =  ($diam[$t]*sin($theta))/2. + $lat[$t];
		}
        $nbpts = count($x);

		for($i=0;$i<$nbpts;$i++){
			if ( ($x[$i] <= 90) && ($x[$i] >= -90) ) {
				$coord = ToHelio($x[$i], $y[$i], $b0, $p0, $r, $sx);
				$points[] = $coord['lon'];
				$points[] = $coord['lat'];
			}
		}
		if (count($points) > 0)
			imagefilledpolygon ($im, $points, count($points)/2, $col);

		//ImageString ($im, 2, $points[0]+5, $points[1]+5, $results['ID'][$t], $col);
		unset($x); unset($y); unset($points);
	}
}

function draw_plage($im, $results, $flg, $sx, $sy, $xc, $yc, $xi, $yi) {
	reset($results);
	$lon=$results['LON'];
	$lat=$results['LAT'];

	$lon_b=xtrans($results['LON_B'] + 360*$flg, $xc, $xi, $sx);
	$lat_b=ytrans($results['LAT_B'], $yc, $yi, $sy);

	$nb = count($lon);

	/*if ($flg == 0)
		$col=ImageColorAllocate($im, 105+50*$results['INTENSITY'][0], 0, 0);
	else
		$col=ImageColorAllocate($im, 105+50*$results['INTENSITY'][0], 0, 105+50*$results['INTENSITY'][0]);*/
	if ($flg == 0)
		$col=ImageColorAllocate($im, 255, 200-66*$results['INTENSITY'], 200-66*$results['INTENSITY']);
	else
		$col=ImageColorAllocate($im, 255, 200-66*$results['INTENSITY'], 255);
	$cBlack=ImageColorAllocate($im,0,0,0);
	for($i=0;$i<$nb;$i++){
		$points[] = xtrans($lon[$i] + 360*$flg, $xc, $xi, $sx);
		$points[] = ytrans($lat[$i], $yc, $yi, $sy);
	}
	if (count($points) > 0) {
		ImageFilledPolygon ($im, $points, count($points)/2, $col);
		ImagePolygon ($im, $points, count($points)/2, $cBlack);
	}
	imagefilledellipse  ($im, $lon_b, $lat_b, 2, 2, $cBlack);
	//ImageFilledRectangle($im, $lon_b-1, $lat_b-1, $lon_b+1, $lat_b+1, $cBlack);
	//$fontwidth = ImageFontWidth($font);
	//$fontheight = ImageFontHeight($font);
	//ImageString($im, 2, $lon_b-($fontwidth/2), $lat_b-$fontheight, "x", $cBlack);
	unset($points);
}

function draw_plage_spher($im, $results, $flg, $l0, $b0, $p0, $sx, $r, $col) {

	$lon=$results['LON'];
	$lat=$results['LAT'];
	/*if ($flg == 0)
		$col=ImageColorAllocate($im, 255, 255-$results['INTENSITY'], 255-$results['INTENSITY']);
	else
		$col=ImageColorAllocate($im, 255, 255-$results['INTENSITY'], 255);*/
	$cBlack=ImageColorAllocate($im, 0, 0, 0);

	$nb = count($lon);

	for($i=0;$i<$nb;$i++){
		$lon[$i] = ($lon[$i] + 360*$flg) - rad2deg($l0);
		//if ( ($lon[$i] <= 90) && ($lon[$i] >= -90) ) {
			$coord = ToHelio($lon[$i], $lat[$i], $b0, $p0, $r, $sx);
			$points[] = $coord['lon'];
			$points[] = $coord['lat'];
		//}
	}
	if (count($points) > 0) {
		ImageFilledPolygon ($im, $points, count($points)/2, $col);
		imagepolygon ($im, $points, count($points)/2, $cBlack);
	}
	unset($points);
}

function draw_promi($im, $results, $flg, $sx, $sy, $xc, $yc, $xi, $yi) {

	$lon1=$results['LON'];
	$lat1=$results['LAT1'];
	$lat2=$results['LAT2'];
	$height=$results['HEIGHT'];
	$edge=$results['EDGE'];

	if ($edge == 'O') $lon2 = $lon1 + 5.0;
	else $lon2 = $lon1 - 5.0;

	$col=ImageColorAllocate($im, 0, 0, 255);

	$cBlack=ImageColorAllocate($im,0,0,0);
	$x2 = xtrans($lon1 + 360*$flg, $xc, $xi, $sx);
	$y2 = ytrans($lat1, $yc, $yi, $sy);
	ImageLine($im, xtrans($lon2 + 360*$flg, $xc, $xi, $sx), ytrans($lat1, $yc, $yi, $sy), $x2, $y2, $col);
	$x1 = $x2;
	$y1 = $y2;
	$x2 = xtrans($lon1 + 360*$flg, $xc, $xi, $sx);
	$y2 = ytrans($lat2, $yc, $yi, $sy);
	ImageLine($im, $x1, $y1, $x2, $y2, $col);
	$x1 = $x2;
	$y1 = $y2;
	$x2 = xtrans($lon2 + 360*$flg, $xc, $xi, $sx);
	$y2 = ytrans($lat2, $yc, $yi, $sy);
	ImageLine($im, $x1, $y1, $x2, $y2, $col);

}

function draw_promi_sphere($im, $results, $flg, $l0, $b0, $p0, $sx, $r) {
	$lon1=$results['LON'];
	$lat1=$results['LAT1'];
	$lat2=$results['LAT2'];
	$height=$results['HEIGHT'];
	$edge=$results['EDGE'];
	$intensity=$results['INTENSITY'];

	if ($edge == 'E') { $lon1 = -90; $lon2 = -100;}
	else { $lon1 = 90; $lon2 = 105;}

	/*$cBlue=ImageColorAllocate($im, 0, 0, 255);
	$cRed=ImageColorAllocate($im, 255, 0, 0);*/
	$col=ImageColorAllocate($im, 255-$intensity, 255, 255-$intensity);
	$min = min(array($lat1, $lat2));
	$max = max(array($lat1, $lat2));
	imagesetthickness($im, 3);
	// meridiens
	$cur = ToHelio($lon1, $min, $b0, $p0, $r, $sx);
	for ($i=$min+1; $i<=$max; $i++) {
		$new = ToHelio($lon1, $i, $b0, $p0, $r, $sx);
		ImageLine($im, $cur['lon'], $cur['lat'], $new['lon'], $new['lat'], $col);
		$cur = $new;
	}
	$cur = ToHelio($lon1, $lat1, $b0, $p0, $r, $sx);
	$new = ToHelio($lon1, $lat1, $b0, $p0, $r, $sx);
	ImageFilledRectangle($im, $cur['lon']-1, $cur['lat']-1, $new['lon']+1, $new['lat']+1, $col);
	//ImageLine($im, $cur['lon'], $cur['lat'], $new['lon'], $new['lat'], $cRed);
	$cur = ToHelio($lon1, $lat2, $b0, $p0, $r, $sx);
	$new = ToHelio($lon1, $lat2, $b0, $p0, $r, $sx);
	//ImageLine($im, $cur['lon'], $cur['lat'], $new['lon'], $new['lat'], $cRed);
	ImageFilledRectangle($im, $cur['lon']-1, $cur['lat']-1, $new['lon']+1, $new['lat']+1, $col);
}
?>
