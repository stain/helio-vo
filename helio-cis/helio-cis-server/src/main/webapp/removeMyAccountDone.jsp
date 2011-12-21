<jsp:useBean 
	id="controller" 
	class="eu.heliovo.cis.service.CisSimpleController" 
	scope="session"/> 
<jsp:setProperty name="controller" property="*"/> 

<HTML>
<BODY>
<% 
 controller.removeAnotherAccount();
 out.println("Your account (" + controller.getUserName() + ") has been removed !");
%>
</BODY>
</HTML>