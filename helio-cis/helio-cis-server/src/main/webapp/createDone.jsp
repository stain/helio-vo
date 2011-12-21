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
<title>CIS - Profile Page</title>
</head>
<body>
   <%if(controller.createNewUser())
   {%>
   <BR>Your account has been created !.<BR>    
   <%}
   else
   {%>
   <BR>There was a problem, try another user name !<BR>    
   <%}%>
</body>
</html>