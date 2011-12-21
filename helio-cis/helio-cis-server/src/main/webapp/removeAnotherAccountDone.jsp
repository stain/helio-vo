<jsp:useBean 
	id="controller" 
	class="eu.heliovo.cis.service.CisSimpleController" 
	scope="session"/> 
<jsp:setProperty name="controller" property="*"/> 

<HTML>
<BODY>
<% 
 controller.removeAnotherAccount();
 out.println(controller.getAnotherAccount() + " has been removed !");
 out.println("Now the HELIO Accounts are : ");
 out.println(controller.printAllAccounts());
%>
</BODY>
</HTML>