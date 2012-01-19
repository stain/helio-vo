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
</head>
<body>
<a href="changeMyPassword.jsp">Change My Password</a><BR>
<a href="seeMyPreference.jsp">See my preferences</a><BR>
<a href="changeMyPreference.jsp">Modify my preferences</a><BR>
<a href="removeMyAccount.jsp">Delete my account</a><BR>
</body>
</html>