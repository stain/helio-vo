<jsp:useBean 
	id="controller" 
	class="eu.heliovo.cis.service.CisSimpleController" 
	scope="session"/> 
<jsp:setProperty name="controller" property="*"/> 

<HTML>

<img src="images/helio-logo.jpg"/> 
<BR>
<title>CIS - Administrator Page</title>


<BODY>
<% 
controller.changeStandardPreferences();
out.println("Now the standard preferences are : ");
%>
<BR>
<BR>
<% 
out.println(controller.printAllStandardPreferences());
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