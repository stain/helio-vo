<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<META HTTP-EQUIV="EXPIRES" CONTENT="0">
<title><g:layoutTitle default="HELIO Front End Help Pages" /></title>
<g:layoutHead />
<link rel="shortcut icon"
	href="${resource(dir:'images/helio',file:'sun.ico')}"
	type="image/x-icon" />
<link rel="stylesheet" href="${resource(dir:'css',file:'explorer.css')}" />
<g:javascript library="jquery" plugin="jquery"/>
<g:javascript src="/helio/helio-help.js"/>

</head>
<body>
	<!-- Logo Elements -->
	<div id="headerbar">
		<div id="headerbar-header-text">
			<table width="100%">
				<tr>
					<td>
						HELIO Help Pages
					</td>
				</tr>
			</table>
		</div>
		<div>
			<div id="headerbar-header-logo">
				<a href="http://helio-vo.eu" target="_blank"><img src="${resource(dir:'images/background',file:'header_logo.png')}" /></a>
			</div>
			<div id="headerbar-glowlogo">
				<a href="http://ec.europa.eu/research/fp7/index_en.cfm?pg=capacities" target="_blank"><img src="${resource(dir:'images/background',file:'glowlogo.png')}" /></a>
			</div>
		</div>
	</div>
	<div id="menubar">
		<ul id="menu">
			<li id="helpBack"><a>Back</a></li>
			<li id="helpClose"><a>Close</a></li>
		</ul>
	</div>
	<div id="helpContent">
		<g:layoutBody />
	</div>
</body>
</html>