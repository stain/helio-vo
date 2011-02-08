<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java"%>

 
<HTML lang=en xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
  <HEAD>
    <TITLE> 
    		Helio Query Service - Database configuration. 
    </TITLE>
    <META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

    <%!String contextPath="n";%>
    <%contextPath=request.getContextPath();%>
    <LINK rel="stylesheet" href="<%=contextPath%>/Style/Style.css" type="text/css">
      <script src="<%=contextPath%>/Scripts/common.js"></script>
      <script src="<%=contextPath%>/Scripts/toolTip.js"></script>
      <script src="<%=contextPath%>/Scripts/menu.js"></script>
  </HEAD>

  <BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  onLoad="MM_preloadImages('<%=contextPath%>/Images/but_save_click.gif','<%=contextPath%>Images/but_cancel_click.gif');">
    <DIV id="ToolTip">
    </DIV>
    
    <s:form method="post" action="patFileUpload" enctype="multipart/form-data">
     <s:hidden name="insId" theme="simple"/>
     <s:hidden name="uploadedStatus" theme="simple" />
    <TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <TR>
          <TD align="left" valign="top">
            <%@ include file="./includes/MenuHeader.jsp" %>
          </TD>
        </TR>
        <TR>
          <TD align="left" valign="top">
            <!-- Body starts here -->
            <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
              <TR>
                <TD  align="left" valign="middle" class="txtError"> 
                	<!--Error message can be displayed here....-->
                	<s:actionerror/>
                </TD>
              </TR>
            </TABLE>
           </TD>
        </TR>
	    <TR>
          <TD align="left" valign="top">
          &nbsp;
          </TD>
       </TR>
	  </table>
  	
		<TABLE width="100%" border="0" cellspacing="1" cellpadding="0" >
			<TR height=20 class="PopupAltDataRow"  >
			 <s:if test="uploadedStatus == 'ftp'">
				<TD nowrap class="txtblackBP" width="15%"> Ftp Provider access table upload page  </TD>
			</s:if>
			<s:else>
				<TD nowrap class="txtblackBP" width="15%"> Provider access table upload page  </TD>
			</s:else>
			</TR>
	     </TABLE>
	     <TABLE width="100%" border="0" cellspacing="1" cellpadding="0" >
	   		<TR height=20 class="PopupAltDataRow">
				<TD nowrap class="txtblackBP" width="15%">File path : </TD>
				<td >&nbsp;&nbsp;&nbsp;<s:file  name="userFile" cssClass="textfield"  theme="simple"/> </td>	
			</TR>
	     <TR height=20 >
				<TD nowrap class="txtblackBP" width="15%">&nbsp; </TD>
				<td >&nbsp;&nbsp;&nbsp;<s:submit value="Upload" name="Upload" align="left" theme="simple" /></td>				
			</TR>
	     </TABLE>
	  	<table width="100%">
		<tr>
			 <td align="left" valign="top">
			   <!-- Footer starts here --> 
			 		 <%@ include file="./includes/footer.jsp" %>
			   <!-- Footer ends here -->
			 </td>
		 </tr> 
		</table>
</s:form>
</body></html>







