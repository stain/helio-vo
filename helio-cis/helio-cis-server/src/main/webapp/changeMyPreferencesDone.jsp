<jsp:useBean 
	id="controller" 
	class="eu.heliovo.cis.service.CisSimpleController" 
	scope="session"/> 
<jsp:setProperty name="controller" property="*"/> 

<HTML>
<BODY>
<% 
controller.changePreferences();
out.println(controller.printAllPreferences());
%>
</BODY>
</HTML>