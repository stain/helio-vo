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
<title>CIS - Profile Page</title>
</head>
<body>
   <% 
   if(!controller.nameOk())
   {
	   out.println(controller.getUserName() + " is already present, please select a new one !"); 
	   %>
	   <BR>
	   <BR>
	   Please click <a href="create.jsp"> here</a> to try again
	   <% 
   }
   else if(!controller.pwdOk())
   {
	   out.println(" Your passwords do not match ! Please try again "); 
	   %>
	   <BR>
	   <BR>
	   Please click <a href="create.jsp"> here</a> to try again
	   <% 
   }
   else if(controller.createNewUser())
   {
	 out.println("An account with the following details has been created ! <BR>");    
     out.println(" - Name  : " + controller.getUserName() + "<BR>");
     out.println();
     if(controller.getUserEmail() != null)
     {
     	out.println(" - Email : " + controller.getUserEmail() + "<BR>");
     	out.println();
     }
     out.println(" - Roles : Normal user <BR>");
    %>
    <a href="normal.jsp">Click here to modify your account</a>     
    <%
    }
   else
    {
   %>
   <BR>There was a problem creating your account, please contact pierang@cs.tcd.ie.<BR>    
   <%}%>  
</body>
</html>