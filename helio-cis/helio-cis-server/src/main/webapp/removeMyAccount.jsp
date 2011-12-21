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
   <FORM METHOD=POST ACTION="removeMyAccountDone.jsp">
   <BR>
   <%
   System.out.println("Are you sure that you want to remove " + controller.getUserName());
   %>
   <P><INPUT TYPE=SUBMIT>
   </FORM>
</body>
</html>