<jsp:useBean 
	id="controller" 
	class="eu.heliovo.cis.service.CisSimpleController" 
	scope="session"/> 
<jsp:setProperty name="controller" property="*"/> 
<HTML>

<title>HELIO Community Interaction Service</title>
</head>
<body>
<img src="images/helio-logo.jpg"/> 
<BR>HELIO Community Interaction Service<BR> 

<BODY>
   <%if(controller.validUser() && controller.adminUser())
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
   else if(!controller.validUser())
   {%>
   <BR>You are NOT a registered user, please follow the links below to either re-enter your data or create an account<BR>    
   <a href="create.jsp">Create an account</a>
   <BR>Or go back to the login page if you have entered wrong details<BR>    
   <a href="index.jsp">Login Page</a>
   <%}                           
   %>	
</BODY>
</HTML>