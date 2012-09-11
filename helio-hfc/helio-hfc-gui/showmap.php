<?php
if (isset($_GET['feat'])) $feature_type = $_GET['feat'];
//else exit(0);
if (isset($_GET['style'])) $style = $_GET['style'];
else exit(0);
if (!strcmp($style, 'track')) {
	if (isset($_GET['id'])) $track_id = $_GET['id'];
	else exit(0);
} else {
	if (isset($_GET['date'])) $date = $_GET['date'];
	else exit(0);
}
if (isset($_GET['zoom'])) $zoom = $_GET['zoom'];
else $zoom = 1;
if (isset($_GET['usesess'])) $use_sess = $_GET['usesess'];

switch ($style) {
	case "pixel":
		switch ($feature_type) {
			case 't3':
				$link = "<IMG src=\"plot_t3.php?date=".$date;
				break;
			case 't2':
				$link = "<IMG src=\"plot_t2.php?date=".$date;
				break;
			default:
				$link = "<IMG src=\"makemap_pixobs.php?date=".$date."&feat=".$feature_type;
		}
		if (isset($_GET['obs'])) $link = $link."&obs";
		if (isset($_GET['zoom'])) $link = $link."&zoom=".$zoom;
		if (isset($_GET['usesess'])) $link = $link."&usesess=".$use_sess;
		print $link."\" alt=\"Sun map\">";
		break;
	case "helio";
		$link = "<IMG src=\"makemap_carr.php?date=".$date."&feat=".$feature_type;
		if (isset($_GET['usesess'])) $link = $link."&usesess=".$use_sess;
		print $link."\" alt=\"Carrington Sun map\">";
		break;
	case "heliofull";
		print "<IMG src=\"makemap_carr.php?date=".$date."&feat=all\" alt=\"Carrington Daily Sun map\">";
		break;
	case "track";
		print "<IMG src=\"makemap_tracking.php?id=".$track_id."&feat=".$feature_type."&zoom=".$zoom."\" alt=\"Tracking map\">";
		break;
}

?>