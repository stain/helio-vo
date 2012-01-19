<jsp:useBean 
	id="controller" 
	class="eu.heliovo.cis.service.CisSimpleController" 
	scope="session"/> 
<jsp:setProperty name="controller" property="*"/> 

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HELIO Community Interaction Service</title>
</head>
<body>
<img src="images/helio-logo.jpg"/> 
<BR>HELIO Community Interaction Service<BR> 
<BR>
<BR>

<BODY>
        <%
		out.println(controller.printAllPreferences());
		%>
		<FORM METHOD=POST ACTION="changeMyPreferencesDone.jsp">
		<BR>
		Enter the service: <INPUT TYPE=TEXT NAME=prefService SIZE=20><BR>
		Enter the field: <INPUT TYPE=TEXT NAME=prefField SIZE=20><BR>
		Enter the value: <INPUT TYPE=TEXT NAME=prefValue SIZE=20><BR>
		<P><INPUT TYPE=SUBMIT>
		</FORM>
</BODY>
</HTML>
