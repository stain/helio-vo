<?php
/*
<script>
		$(function() {
			$('#but_query').button();
			$('#but_db_desc').button();
			$('#but_db_cont').button();
			$('#but_freesql').button();
			$('#but_webservices').button();
			$('#but_about').button();
			$( '#dialog_webservices' ).dialog({ 
				autoOpen: false,
				width: 500
			});
			$( '#dialog_about' ).dialog({ 
				autoOpen: false,
				width: 500
			});
			$('#but_webservices').click(function(){
				$('#dialog_webservices').dialog('open');
				return false;
			});
			$('#but_about').click(function(){
				$('#dialog_about').dialog('open');
				return false;
			});
		});
	</script>
<p></p>
	<center>
	<div class="ui-widget-content ui-corner-all">
	<span id="but_query">
	<a href="index.php">Query form</a>
	</span>
	<span id="but_db_desc">
	<a href=javascript:void(window.open('help.php','','toolbar=1,scrollbars=1,resizable=1,status=1,width=600'));>Database and fields description</a>
	</span>
	<span id="but_db_cont">
	<a href="db_content.php">Database content</a>
	</span>
	<span id="but_freesql">
	<a href="hfc_sql_query.php">Free SQL query</a>
	</span>
	<span id="but_webservices">Web Service</span>
	<span id="but_about">About HFC</span>
	</div>
	</center>
<div id="dialog_webservices" title="Web services">
	<p>
	The catalog can also be queried by distant web service clients using the dedicated Helio Query Interface (HQI). WSDL files that describe the types of request permitted can be found at:</p>
	<p><a href="http://voparis-helio.obspm.fr/helio-hfc/HelioService">http://voparis-helio.obspm.fr/helio-hfc/HelioService</a></p>
	<p> The HQI allows PQL queries in the HFC database (more information about PQL can be found <a href="http://www.ivoa.net/internal/IVOA/TableAccess/PQL-0.2-20090520.pdf">here</a>).</p>
	<p>The "Database and fields description" page gives a description of the fields readable in the database.
	</p>
</div>
<div id="dialog_about" title="About HFC">
	<h4>Feedback</h4>
	The HFC is still under development, so any feedback is much appreciated. Please send your comments to Jean dot Aboudarham at obspm dot fr and Christian dot Renie at obspm dot fr.
	<h4>About the HELIO project</h4>
	The HFC is a service of the Heliophysics Integrated Observatory <a href="http://www.helio-vo.eu">HELIO</a>, a virtual observatory dedicated to solar and heliophysics.
	<p>
	HELIO is a Research Infrastructures funded under the Capacities Specfific Programme within the European Commission's Seventh Framework Programme (FP7, project No. 238969). The project started on 1st june 2009 and has a duration of 36 months.
	</p>
</div>
*/
include('but_menu.php');
?>
<script type="text/javascript">
	$(function(){
		/*$('#but_webservices').button();
		$('#but_about').button();*/
		$( '#dialog_webservices' ).dialog( {
			autoOpen: false,
			width: 500
		});
		$( '#dialog_about' ).dialog( {
			autoOpen: false,
			width: 500
		});
		$('#but_webservices').click(function(){
			$('#dialog_webservices').dialog('open');
			return false;
		});
		$('#but_about').click(function(){
			$('#dialog_about').dialog('open');
			return false;
		});
	});
</script>
<div class="ui-widget">
	<center>
	<span>FP7, project No. 238969</span>
	<span id="but_webservices" class="but_menu ui-widget-content ui-corner-all"><a href="#">Web Service</a></span>
	<span id="but_about" class="but_menu ui-widget-content ui-corner-all"><a href="#">About HFC</a></span>
	<p><TABLE><TR>
	<TD><IMG src ="logo-lesia-full.jpg"></TD>
	</TR></TABLE>
	</center>
</div>
<div id="dialog_webservices" title="Web services">
	<p>
	The catalog can also be queried by distant web service clients using the dedicated Helio Query Interface (HQI). WSDL files that describe the types of request permitted can be found at:</p>
	<p><a href="http://voparis-helio.obspm.fr/helio-hfc/HelioService">http://voparis-helio.obspm.fr/helio-hfc/HelioService</a></p>
	<p> The HQI allows PQL queries in the HFC database (more information about PQL can be found <a href="http://www.ivoa.net/internal/IVOA/TableAccess/PQL-0.2-20090520.pdf">here</a>).</p>
	<p>The "Database and fields description" page gives a description of the fields readable in the database.
	</p>
</div>
<div id="dialog_about" title="About HFC">
	<h4>Feedback</h4>
	The HFC is still under development, so any feedback is much appreciated. Please send your comments to Jean dot Aboudarham at obspm dot fr and Christian dot Renie at obspm dot fr.
	<h4>About the HELIO project</h4>
	The HFC is a service of the Heliophysics Integrated Observatory <a href="http://www.helio-vo.eu">HELIO</a>, a virtual observatory dedicated to solar and heliophysics.
	<p>
	HELIO is a Research Infrastructures funded under the Capacities Specfific Programme within the European Commission's Seventh Framework Programme (FP7, project No. 238969). The project started on 1st june 2009 and has a duration of 36 months.
	</p>
</div>
</body>
</html>