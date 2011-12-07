<jsp:useBean 
	id="controller" 
	class="eu.heliovo.cis.service.CisSimpleController" 
	scope="session"/> 
<jsp:setProperty name="controller" property="*"/> 
<HTML>
<BODY>
   <%  
   if(controller.validUser())
		out.println(controller.getUserName() + " is a valid HELIO user");
   else
		out.println(controller.getUserName() + " is NOT a valid HELIO user");	   
	%>		
</BODY>
</HTML>