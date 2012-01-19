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
<img src="images/helio-logo.jpg"/> 
<title>CIS - Administrator Page</title>

<BODY>
		<% 
		out.println("Now the standard preferences are : ");
		%>
		<BR>
		<BR>
		<% 
		out.println(controller.printAllStandardPreferences());
		%>
		<FORM METHOD=POST ACTION="removeStandardPreferencesDone.jsp">
		<BR>
		Enter the service: <INPUT TYPE=TEXT NAME=prefService SIZE=20><BR>
		Enter the field: <INPUT TYPE=TEXT NAME=prefField SIZE=20><BR>
		<P><INPUT TYPE=SUBMIT>
		</FORM>
</BODY>
</HTML>
