<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<META HTTP-EQUIV="EXPIRES" CONTENT="0">
<title><g:layoutTitle default="HELIO Front End" /></title>
<g:layoutHead />
<link rel="shortcut icon"
	href="${resource(dir:'images/helio',file:'sun.ico')}"
	type="image/x-icon" />
</head>
<body>
	<!-- Logo Elements -->
	<div id="headerbar">
		<div id="headerbar-header-text">
			<table>
				<tr>
					<td>
						<img src="${resource(dir:'images/background',file:'header_text.png')}" />
					</td>
				</tr>
			</table>
		</div>
		<div>
			<div id="headerbar-header-logo">
				<a href="http://helio-vo.eu"><img src="${resource(dir:'images/background',file:'header_logo.png')}" /></a>
			</div>
			<div id="headerbar-glowlogo">
				<img src="${resource(dir:'images/background',file:'glowlogo.png')}" />
			</div>
		</div>
	</div>
	<%-- Navigation Bar --%>
	<g:render template="/misc/navbar" />
	<g:layoutBody />
</body>
</html>