<jsp:useBean 
	id="controller" 
	class="eu.heliovo.cis.service.CisSimpleController" 
	scope="session"/> 
<jsp:setProperty name="controller" property="*"/> 
<HTML>
<BODY>
   <%if(controller.validUser() && controller.adminUser())
   {%>
   <BR>You are Administrator privileges, select what type of action you want to perform.<BR>    
   <BR>Modify other user's accounts.<BR>    
   <a href="admin.jsp">Click here to the Administrator Page</a>
   <BR>
   <BR>Modify your own account.<BR>    
   <a href="normal.jsp">Click here to modify your account</a>
   <%}                           
   else if(controller.validUser() && !controller.adminUser())
   {%>
   <BR>You normal user privileges, you can modify only your own account<BR>    
   <a href="normal.jsp">Click here to modify your account</a>
   <%}                           
   else if(!controller.validUser())
   {%>
   <BR>You are NOT a normal user, please follow the link below to create an account<BR>    
   <a href="create.jsp">Create an account</a>
   <BR>Or go back to the login page if you have entered wrong details<BR>    
   <a href="login.jsp">Login Page</a>
   <%}                           
   %>	
</BODY>
</HTML>