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
   out.print("Select the account from this list : ");
   out.println(controller.printAllNormalAccounts());
   %>
   <FORM METHOD=POST ACTION="promoteUserDone.jsp">
   <BR>
   Enter the account to be promoted to administrator: <INPUT TYPE=TEXT NAME=anotherAccount SIZE=20><BR>
   <P>
   <INPUT TYPE=SUBMIT>
   </FORM>
</body>
</html>