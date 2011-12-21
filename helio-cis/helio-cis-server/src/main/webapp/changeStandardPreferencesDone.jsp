<jsp:useBean 
	id="controller" 
	class="eu.heliovo.cis.service.CisSimpleController" 
	scope="session"/> 
<jsp:setProperty name="controller" property="*"/> 

<HTML>
<BODY>
<% 
controller.changeStandardPreferences();
out.println(controller.printAllStandardPreferences());
%>
</BODY>
</HTML>