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
    		HelioSystem - Instrument Operation Period 
    </TITLE>
    <META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

    <%!String contextPath="n";%>
    <%contextPath=request.getContextPath();%>
    <LINK rel="stylesheet" href="<%=contextPath%>/Style/Style.css" type="text/css">
      <script src="<%=contextPath%>/Scripts/date-picker.js"></script>
      <script src="<%=contextPath%>/Scripts/common.js"></script>
      <script src="<%=contextPath%>/Scripts/toolTip.js"></script>
      <script src="<%=contextPath%>/Scripts/instrument-operation-period.js"></script>
      <script src="<%=contextPath%>/Scripts/menu.js"></script>
  </HEAD>
   <script type="text/javascript">
			var CalendarToolTip=new Array();
			CalendarToolTip[0]=new toolTipObj('1','Date Picker')
		</script>
  <BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  onLoad="MM_preloadImages('<%=contextPath%>/Images/but_save_click.gif','<%=contextPath%>Images/but_cancel_click.gif');">
    <DIV id="ToolTip">
    </DIV>
    
    <form name="frmInstrumentAdmin" id="frmInstrumentAdmin" method="post" action="">
     <s:hidden name="insId" theme="simple"/>
     <s:hidden name="insOpsName" id="insOpsName" theme="simple" />
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
							Instrument Operation Period Add page 
						</s:if>
						<s:else>
						    Instrument Operation Period Update page
						</s:else>
						</TD>
						</TR>
				</TABLE>
			</TD>
		</TR>
		
    </table>
  	 <% 
        	String st="true";
        %>
		<TABLE width="100%" border="0" cellspacing="1" cellpadding="0" >
			
			<TR height=20 class="PopupAltDataRow">
				<TD nowrap class="txtblackBP" width="5%">Instrument Name:</TD>
				<td>&nbsp;&nbsp;&nbsp;<s:select name="cmbInsOpsName" id="cmbInsOpsName" cssClass="stylecombo" list="insOpsNameList" listKey="insId" listValue="insName" headerKey="s" headerValue="-Select-" value="%{#request.insOpsPeriodTO.insId}" theme="simple" onchange="getInsDescDetails(this)" /></td>				
			</TR>
			<TR height=20 class="PopupAltDataRow">
				<TD nowrap class="txtblackBP" >Instrument Operation Type:</TD>
				<td>&nbsp;&nbsp;&nbsp;<s:select name="cmbInsOpsType" id="cmbInsOpsType" cssClass="stylecombo" list="insOpsTypeList" listKey="key" listValue="value"  value="%{#request.insOpsPeriodTO.insOperationType}" theme="simple"  /></td>				
			</TR>
			<TR height=20 class="PopupAltDataRow">
				<TD nowrap class="txtblackBP" >Instrument Description:</TD>
				<td>&nbsp;&nbsp;&nbsp;<s:select name="cmbInsDes" id="cmbInsDes" cssClass="stylecombo" list="insOpsDesList" listKey="insDesId" listValue="insDesName" headerKey="s" headerValue="-Select-" value="%{#request.insOpsPeriodTO.insName}" theme="simple"  /></td>				
			</TR>
			<TR height=20 class="PopupAltDataRow">
				<TD nowrap class="txtblackBP" >Instrument Start Date:</TD>
				<td nowrap class="txtblackBP" ><s:textfield name="insStartDate" id="insStartDate" cssClass="textfield" value="%{#request.insOpsPeriodTO.insStartDate}" theme="simple" onmouseover="tip_it1(1,'Desc','1',CalendarToolTip,CalendarToolTip.length);" onmouseout="tip_it(0, '', '')"  />
					<IMG src="<%=contextPath%>/Images/btnCalendar.gif" width="20" height="18" align="absmiddle" alt="click this to get the date" style="CURSOR: hand" onclick="javascript:show_calendar('forms[0].insStartDate');" onmouseover="window.status='Date Picker';return true;" onmouseout="window.status='';return true;">
														&nbsp;&nbsp; 
				  <s:select name="cmbStartInsHour" id="cmbStartInsHour" cssClass="stylecombo" list="insHourList" listKey="key" listValue="value" value="%{#request.insOpsPeriodTO.insStartHour}" theme="simple"  />	
				  	&nbsp;&nbsp; 
				  <s:select name="cmbStartInsMinutes" id="cmbStartInsMinutes" cssClass="stylecombo" list="insMinutesList" listKey="key" listValue="value" value="%{#request.insOpsPeriodTO.insStartMin}" theme="simple"  />	
				</TD>
			</TR>
			<TR height=20 class="PopupAltDataRow">
				<TD nowrap class="txtblackBP" >Instrument End Date:</TD>
				<td nowrap class="txtblackBP" ><s:textfield name="insEndDate" id="insEndDate" cssClass="textfield" value="%{#request.insOpsPeriodTO.insEndDate}" theme="simple" onmouseover="tip_it1(1,'Desc','1',CalendarToolTip,CalendarToolTip.length);" onmouseout="tip_it(0, '', '')"  />
					<IMG src="<%=contextPath%>/Images/btnCalendar.gif" width="20" height="18" align="absmiddle" alt="click this to get the date" style="CURSOR: hand" onclick="javascript:show_calendar('forms[0].insEndDate');" onmouseover="window.status='Date Picker';return true;" onmouseout="window.status='';return true;">
														&nbsp;&nbsp; 
				  <s:select name="cmbEndInsHour" id="cmbEndInsHour" cssClass="stylecombo" list="insHourList" listKey="key" listValue="value" value="%{#request.insOpsPeriodTO.insEndHour}" theme="simple"  />	
				  	&nbsp;&nbsp; 
				  <s:select name="cmbEndInsMinutes" id="cmbEndInsMinutes" cssClass="stylecombo" list="insMinutesList" listKey="key" listValue="value" value="%{#request.insOpsPeriodTO.insEndMin}" theme="simple"  />	
				</TD>
			</TR>
			<TR class="txtblackBP">              	
				<TD nowrap align="left" valign="top" colspan=2>&nbsp;
				</TD>
			</TR>
			<TR class="txtblackBP">              	
					<TD nowrap align="left" valign="top" colspan=2>
					<s:if test="statusEdit == false">
					 <A  href="javascript:saveInstrumentOperationPeriod();" onMouseOver="MM_swapImage('save','','<%=contextPath%>/Images/but_save_click.gif',1)" onMouseOut="MM_swapImgRestore()"><IMG src="<%=contextPath%>/Images/but_save_normal.gif" name="display" border="0" align="absmiddle" id="save" ></A>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</s:if>					  
					 <s:else>
					 <A  href="javascript:updateInstrumentOperationPeriodDetails();" onMouseOver="MM_swapImage('update','','<%=contextPath%>/Images/but_update_click.gif',1)" onMouseOut="MM_swapImgRestore()"><IMG src="<%=contextPath%>/Images/but_update_normal.gif" name="display" border="0" align="absmiddle" id="update" ></A>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 </s:else>
					
					<A href="javascript:getInstrumentOperationPeriodDetails();" onMouseOver="MM_swapImage('Close','','<%=contextPath%>/Images/but_cancel_click.gif',1)" onMouseOut="MM_swapImgRestore()"><img src="<%=contextPath%>/Images/but_cancel_normal.gif" name="Close" border="0" align="absmiddle" id="Close"  ></A>
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







