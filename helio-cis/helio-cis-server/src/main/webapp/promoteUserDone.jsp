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

<% 
 controller.promoteAccount();
 out.println("Now the HELIO administrator are : ");
 out.println(controller.printAllAdministratorAccounts());
%>


	   <BR>
	   <BR>
	   <% 
	   if(controller.validUser() && controller.adminUser())
	   {%>
	      <BR>You have administrator privileges, select what type of action you want to perform.<BR>    
	      <BR>Modify other user's accounts :     
	      <a href="admin.jsp">Click here to the administrator page</a>
	      <BR>
	      <BR>Modify your own account :     
	      <a href="normal.jsp">Click here to modify your account</a>
	   <%}                           
	   else if(controller.validUser() && !controller.adminUser())
	   {%>
	      <BR>You have normal user privileges:      
	      <a href="normal.jsp">Click here to modify your account</a>
	   <%}%>


</BODY>
</HTML>