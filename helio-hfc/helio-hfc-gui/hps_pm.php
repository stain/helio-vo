<?php
if (isset($_GET['m'])) $modele = $_GET['m'];
else exit(0);
if (isset($_GET['dt'])) {
	$date_obs = str_replace(' ', 'T', $_GET['dt']);
}
else exit(0);
if (isset($_GET['lon'])) $start_lon = $_GET['lon'];
else exit(0);

$map_app = array('CME'=>2, 'SEP'=>4, 'CIR'=>5);
if (!isset($map_app[$modele])) exit(0);

$soapClient1 = new SoapClient("http://cagnode58.cs.tcd.ie:8080/helio-hps-server/hpsService?wsdl", array("trace"=>1, "exceptions"=>1));

// Create PHP class from wsdl type definition
$clientName = "hpsclient";
$types = array();
foreach($soapClient1->__getTypes() as $type) { 
	/* match the type information  using a regualr expession */
	preg_match("/([a-z0-9_]+)\s+([a-z0-9_]+(\[\])?)(.*)?/si", $type, $matches);
	$type = $matches[1];
	//var_dump($matches);print "<BR><BR>\n";
	switch($type) {
		/* if the data type is struct, we create a class with this name */
		case 'struct':
			$name = $matches[2];
			/* the PHP class name will be ClientName_WebServiceName */
			$className = $clientName . '_' . $name;
			/* store the data type information in an array for later use in the classmap */
			$types[$name] = $className;
			/* check the class does not exsits before creating it */
			if (! class_exists($className)) {
				eval("class $className {}");
			}
			break;
	} 

}

$app_number = $map_app[$modele];

$soapClient = new SoapClient("http://cagnode58.cs.tcd.ie:8080/helio-hps-server/hpsService?wsdl", array("trace"=>1, "exceptions"=>1,"classmap"=>$types));

$app = $soapClient->getPresentApplications();

$app_to_run = new hpsclient_abstractApplicationDescription();
$app_to_run->parameters = new hpsclient_applicationParameter();

$app_to_run->description = $app->presentApplications[$app_number]->description;
$app_to_run->id = $app->presentApplications[$app_number]->id;
$app_to_run->name = $app->presentApplications[$app_number]->name;
$app_to_run->parameters = $app->presentApplications[$app_number]->parameters;
//print_r($app_to_run);print "<BR><BR>\n";
//$param = $app_to_run->parameters;
//print_r($param);print "<BR><BR>\n";
// for CME
foreach($app_to_run->parameters as $key=>$par) {
	switch($app_number) {
		case 2:
			if ($par->name == "CME's starting time") $app_to_run->parameters[$key]->value = $date_obs;
			if ($par->name == "CME's starting point") $app_to_run->parameters[$key]->value = $start_lon;
			if ($par->name == "CME's starting width") $app_to_run->parameters[$key]->value = 45;
			if ($par->name == "CME's starting speed") $app_to_run->parameters[$key]->value = 800;
			if ($par->name == "CME's error speed") $app_to_run->parameters[$key]->value = 0;
			break;
		case 4:
			if ($par->name == "SEP's starting time") $app_to_run->parameters[$key]->value = $date_obs;
			if ($par->name == "SEP's starting point") $app_to_run->parameters[$key]->value = $start_lon;
			if ($par->name == "SEP's starting speed") $app_to_run->parameters[$key]->value = 400;
			if ($par->name == "SEP's error speed") $app_to_run->parameters[$key]->value = 0;
			if ($par->name == "Beta Parameter") $app_to_run->parameters[$key]->value = 0.9;
			break;
		case 5:
			if ($par->name == "SW's starting time") $app_to_run->parameters[$key]->value = $date_obs;
			if ($par->name == "SW's starting point") $app_to_run->parameters[$key]->value = $start_lon;
			if ($par->name == "SW's starting speed") $app_to_run->parameters[$key]->value = 600;
			if ($par->name == "SW's error speed") $app_to_run->parameters[$key]->value = 0;
			break;
	}
}
$par = new hpsclient_executeApplication();
$par->selectedApplication = $app_to_run;
$par->fastExecution = true;
$par->numOfParallelJobs=1;
//print_r($par);print "<BR>\n";
try {
	//$app_exe = $soapClient->executeApplication("selectedApplication"=>$app_to_run, "fastExecution"=>0 ,"numOfParallelJobs"=>1);
	$app_exe = $soapClient->executeApplication($par);
	//print_r($app_exe);
	//print "<BR><BR>\n";
	//print "Request :\n".htmlspecialchars($soapClient->__getLastRequest()) ."<br><br>\n";
	//print "Response:\n".htmlspecialchars($soapClient->__getLastResponse())."<br><br>\n";
	//print_r($app_exe->executionId);
	//print "<BR><BR>\n";
	$stat = $soapClient->getStatusOfExecution(array("executionId"=>$app_exe->executionId));
	//print_r($stat);
	//print "<BR><BR>\n";
	//print "Running";
	//print "<BR><BR>\n";
	while ( $stat->executionStatus !== "Completed") {
		$stat = $soapClient->getStatusOfExecution(array("executionId"=>$app_exe->executionId));
		//var_dump($stat);
		//print "<BR><BR>\n";
		//print "."; flush();
	}
	//print "<BR><BR>\n";
	$output = $soapClient->getOutputOfExecution(array("executionId"=>$app_exe->executionId));
	$outdir = $output->outputLocation.'/';
	//print "Output location is ".$outdir."<BR><BR>\n";
	if (file_exists($outdir)) print "Output directory exists";
	else print "No output directory";
	header("Location:".$outdir);
} catch (SoapFault $exception) {
	print "Request :\n".htmlspecialchars($soapClient->__getLastRequest()) ."<BR><BR>\n";
	print "Response:\n".htmlspecialchars($soapClient->__getLastResponse())."<BR><BR>\n";
	echo $exception;      
}
?>