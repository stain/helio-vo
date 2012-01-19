<jsp:useBean 
	id="controller" 
	class="eu.heliovo.cis.service.CisSimpleController" 
	scope="session"/> 
<jsp:setProperty name="controller" property="*"/> 

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HELIO Community Interaction Service</title>
</head>
<body>
<img src="images/helio-logo.jpg"/> 
<BR>HELIO Community Interaction Service<BR> 

<HTML>
<BODY>
<BR>
<BR>
<% 
if(!controller.pwdOk())
{
	   out.println("Your new passwords do not match ! "); 
	   %>
	   <BR>
	   <BR>
	   Please click <a href="changeMyPassword.jsp"> here</a> to try again
	   <% 
}
else
{
 	   controller.changePwd();
	   out.println("Your password has been changed ! "); 
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
	   <%}
}%>

</BODY>
</HTML> 


