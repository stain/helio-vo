<jsp:useBean 
	id="controller" 
	class="eu.heliovo.cis.service.CisSimpleController" 
	scope="session"/> 
<jsp:setProperty name="controller" property="*"/> 

<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HELIO Community Interaction Service</title>
</head>
<body>
<img src="images/helio-logo.jpg"/> 
<BR>HELIO Community Interaction Service<BR> 

<BODY>
<% 
 controller.removeAccount();
%>
<BR>
<BR>
<% 
 out.println("Your account (" + controller.getUserName() + ") has been removed !");
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