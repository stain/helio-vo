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
<title>CIS - Administrator Page</title>
</head>
<HTML>
<BODY>
        <%
		out.println("Change Password for " + controller.getUserName());
		%>
		<FORM METHOD=POST ACTION="changeMyPasswordDone.jsp">
		<BR>
		Enter your new password: <INPUT TYPE=PASSWORD NAME=newPwd1 SIZE=20><BR>
		Re-enter your new password: <INPUT TYPE=PASSWORD NAME=newPwd2 SIZE=20><BR>
		<P><INPUT TYPE=SUBMIT>
		</FORM>
</BODY>
</HTML>