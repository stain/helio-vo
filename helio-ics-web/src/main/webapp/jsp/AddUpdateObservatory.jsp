<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java"%>
<%
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache");  
	response.setDateHeader ("Expires", 0); 
%> 
<HTML lang=en xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
  <HEAD>
    <TITLE> 
    		HelioSystem - Observatory 
    </TITLE>
    <META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

    <%!String contextPath="n";%>
    <%contextPath=request.getContextPath();%>
    <LINK rel="stylesheet" href="<%=contextPath%>/Style/Style.css" type="text/css">
      <script src="<%=contextPath%>/Scripts/date-picker.js"></script>
      <script src="<%=contextPath%>/Scripts/common.js"></script>
      <script src="<%=contextPath%>/Scripts/toolTip.js"></script>
      <script src="<%=contextPath%>/Scripts/observatory.js"></script>
      <script src="<%=contextPath%>/Scripts/menu.js"></script>
  </HEAD>
   <script type="text/javascript">
			var CalendarToolTip=new Array();
			CalendarToolTip[0]=new toolTipObj('1','Date Picker')
		</script>
  <BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  onLoad="MM_preloadImages('<%=contextPath%>/Images/but_save_click.gif','<%=contextPath%>Images/but_cancel_click.gif');">
    <DIV id="ToolTip">
    </DIV>
    
    <form name="frmObservatorytAdmin" id="frmObservatorytAdmin" method="post" action="">
     <s:hidden name="obsId" theme="simple"/>
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
		<TR>
			<TD align="left" valign="top">
				<!-- Saved Report section  start-->
				<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
					<TR class="txtHeading">
						<TD height="20">
						<s:if test="statusEdit == false">
							Observatory Add page 
						</s:if>
						<s:else>
							Observatory Update page 
						</s:else>
						</TD>
						</TR>
				</TABLE>
			</TD>
		</TR>		
    </table>
  	<TABLE width="100%" border="0" cellspacing="1" cellpadding="0" >			
			<TR height=20 class="PopupAltDataRow">
				<TD nowrap class="txtblackBP" width="5%">Observatory Name:</TD>
				<td >&nbsp;&nbsp;&nbsp;<s:textfield key="txtObsName" cssClass="textfield" size="20" maxlength="20" value="%{#request.observatoryTO.obsId}" theme="simple"/></td>				
			</TR>
			<TR height=20 class="PopupAltDataRow">
				<TD nowrap class="txtblackBP" >Observatory Description:</TD>
				<td>&nbsp;&nbsp;&nbsp;<s:textfield key="txtObsDes" cssClass="textfield" size="20" maxlength="100" value="%{#request.observatoryTO.obsName}" theme="simple"/></td>				
			</TR>
				<TR height=20 class="PopupAltDataRow">
				<TD nowrap class="txtblackBP" >Observatory Type:</TD>
				<td>&nbsp;&nbsp;&nbsp;<s:textfield key="txtObsType" cssClass="textfield" size="20" maxlength="20" value="%{#request.observatoryTO.obsType}" theme="simple"/></td>				
			</TR>
			<TR height=20 class="PopupAltDataRow">
				<TD nowrap class="txtblackBP" >Observatory First Position:</TD>
				<td>&nbsp;&nbsp;&nbsp;<s:textfield key="txtObsFirstPos" cssClass="textfield" size="20" maxlength="20" value="%{#request.observatoryTO.obsFirstPosition}" theme="simple"/></td>				
			</TR>			
			<TR height=20 class="PopupAltDataRow">
				<TD nowrap class="txtblackBP" >Observatory Second Position:</TD>
				<td>&nbsp;&nbsp;&nbsp;<s:textfield key="txtObsSecondtPos" cssClass="textfield" size="20" maxlength="20" value="%{#request.observatoryTO.obsSecondPosition}" theme="simple"/></td>				
			</TR>		
			<TR height=20 class="PopupAltDataRow">
				<TD nowrap class="txtblackBP" >Observatory Start Date Time:</TD>
				<td nowrap class="txtblackBP" ><s:textfield name="obsStartDate" id="obsStartDate" cssClass="textfield" value="%{#request.observatoryTO.obsStartDate}" theme="simple" onmouseover="tip_it1(1,'Desc','1',CalendarToolTip,CalendarToolTip.length);" onmouseout="tip_it(0, '', '')" />
					<IMG src="<%=contextPath%>/Images/btnCalendar.gif" width="20" height="18" align="absmiddle" alt="click this to get the date" style="CURSOR: hand" onclick="javascript:show_calendar('forms[0].obsStartDate');" onmouseover="window.status='Date Picker';return true;" onmouseout="window.status='';return true;">
														&nbsp;&nbsp; 
				  <s:select name="cmbStartObsHour" id="cmbStartObsHour" cssClass="stylecombo" list="obsHourList" listKey="key" listValue="value" value="%{#request.observatoryTO.obsStartHour}" theme="simple"  />	
				  	&nbsp;&nbsp; 
				  <s:select name="cmbStartObsMinutes" id="cmbStartObsMinutes" cssClass="stylecombo" list="obsMinutesList" listKey="key" listValue="value" value="%{#request.observatoryTO.obsStartMin}" theme="simple"  />	
				</TD>
			</TR>
			<TR height=20 class="PopupAltDataRow">
				<TD nowrap class="txtblackBP" >Observatory End Date Time:</TD>
				<td nowrap class="txtblackBP" ><s:textfield name="obsEndDate" id="obsEndDate" cssClass="textfield" value="%{#request.observatoryTO.obsEndDate}" theme="simple" onmouseover="tip_it1(1,'Desc','1',CalendarToolTip,CalendarToolTip.length);" onmouseout="tip_it(0, '', '')" />
					<IMG src="<%=contextPath%>/Images/btnCalendar.gif" width="20" height="18" align="absmiddle" alt="click this to get the date" style="CURSOR: hand" onclick="javascript:show_calendar('forms[0].obsEndDate');" onmouseover="window.status='Date Picker';return true;" onmouseout="window.status='';return true;">
														&nbsp;&nbsp; 
				  <s:select name="cmbEndObsHour" id="cmbEndObsHour" cssClass="stylecombo" list="obsHourList" listKey="key" listValue="value" value="%{#request.observatoryTO.obsEndHour}" theme="simple"  />	
				  	&nbsp;&nbsp; 
				  <s:select name="cmbEndObsMinutes" id="cmbEndObsMinutes" cssClass="stylecombo" list="obsMinutesList" listKey="key" listValue="value" value="%{#request.observatoryTO.obsEndMin}" theme="simple"  />	
				</TD>
			</TR>
			<TR height=20 class="PopupAltDataRow">
				<TD nowrap class="txtblackBP" >Observatory Operation Type:</TD>
				<td>&nbsp;&nbsp;&nbsp;<s:select name="cmbObsOpsType" id="cmbObsOpsType" cssClass="stylecombo" list="obsOpsTypeList" listKey="key" listValue="value"  value="%{#request.observatoryTO.obsOperationType}" theme="simple"  /></td>				
			</TR>
			<TR class="txtblackBP">              	
					<TD nowrap align="left" valign="top" colspan=2>&nbsp;
					</TD>
			</TR>
			<TR class="txtblackBP">              	
				<TD nowrap align="left" valign="top" colspan=2>
					<s:if test="statusEdit == false">
					 <A  href="javascript:saveObservatoryDetails();" onMouseOver="MM_swapImage('save','','<%=contextPath%>/Images/but_save_click.gif',1)" onMouseOut="MM_swapImgRestore()"><IMG src="<%=contextPath%>/Images/but_save_normal.gif" name="display" border="0" align="absmiddle" id="save" ></A>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</s:if>					  
					 <s:else>
					 <A  href="javascript:updateObservatoryDetails();" onMouseOver="MM_swapImage('update','','<%=contextPath%>/Images/but_update_click.gif',1)" onMouseOut="MM_swapImgRestore()"><IMG src="<%=contextPath%>/Images/but_update_normal.gif" name="display" border="0" align="absmiddle" id="update" ></A>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 </s:else>					
					<A href="javascript:getObservatoryDetails();" onMouseOver="MM_swapImage('Close','','<%=contextPath%>/Images/but_cancel_click.gif',1)" onMouseOut="MM_swapImgRestore()"><img src="<%=contextPath%>/Images/but_cancel_normal.gif" name="Close" border="0" align="absmiddle" id="Close"  ></A>
				</TD>						 
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
</form>
</body></html>







