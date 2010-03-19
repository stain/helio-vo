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
    		Helio System
    </TITLE>
    <META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<%!String contextPath="n";%>
		<%contextPath=request.getContextPath();%>
	<LINK rel="stylesheet" href="<%=contextPath%>/Style/Style.css" type="text/css">
	<script src="<%=contextPath%>/Scripts/common.js"></script>
	<script src="<%=contextPath%>/Scripts/observatory.js"></script>
	<script src="<%=contextPath%>/Scripts/menu.js"></script>
  </HEAD>
  <BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  onLoad="MM_preloadImages('<%=contextPath%>/Images/but_stop_click.gif')">
  <DIV id="ToolTip">
    </DIV>
   <s:form name="frmObservatorytAdmin" id="frmObservatorytAdmin" method="post" action="">
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
				<!-- Saved Report section  start-->
				<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
					<TR class="txtHeading">
						<TD height="20">
							Observatory Search page 
						</TD>
						</TR>
				</TABLE>
			</TD>
		</TR>
    </table>   
    <table>
    <tr>
       <td width="20%" align="right" class="txtblackBP" valign="middle">
        	Observatory Names&nbsp;
       </td>
       <td width="20%" align="left" valign="middle">
        		<s:textfield  cssClass="textfield" size="25" id="insName" name="insName" theme="simple"/>
        </td>
	     <td width="20%" align="left" valign="middle">&nbsp;
		        <a href="javascript:getObservatoryDetails();" onMouseOver="MM_swapImage('go','','<%=contextPath%>/Images/but_search_click.gif',1)" onMouseOut="MM_swapImgRestore()" ><img src="<%=contextPath%>/Images/but_search_normal.gif" name="go" border="0" align="absmiddle" id="go"></a>&nbsp;		   		
        </td>
      </tr>
     <!--  <tr>
        <td>&nbsp;</td>
      </tr>-->       
    </table> 
	<table width="100%" border="0" cellspacing="1" cellpadding="0">
		 <tr class="txtPopupGridHeading">
		      <td class="txtblackBP">&nbsp;</td>
 			  <td class="txtblackBP">Observatory Name</td>             
              <td class="txtblackBP">Observatory Description</td>    
              <td class="txtblackBP">Observatory Type</td>
              <td class="txtblackBP">Observatory First Position</td>  
              <td class="txtblackBP">Observatory Second Position</td>                     
              <td class="txtblackBP">Observatory Start Date Time</td>
              <td class="txtblackBP">Observatory End Date Time </td>
               <td class="txtblackBP">Observatory Operation Type</td>
		  </tr>
		<% int i=0;%>	
		<s:if test='obsDetailsTO!=null'> 
			   <s:iterator value="obsDetailsTO" status="rowstatus" id="obsCriTO">
					 <s:if test="#rowstatus.odd == true">
						<s:set name="classType" value="'PopupAltDataRow'"/>
					 </s:if>
					 <s:else>
						<s:set name="classType" value="'PopupDataRow'"/>
					 </s:else>
				  <tr class='<s:property value="#classType"/>' height="10" align="left" valign="middle">
				    <td>
					   <IMG src="<%=contextPath%>/Images/icon_edit.gif" style="CURSOR:hand" onclick="javascript:editObservatoryPage('<s:property value="#obsCriTO.getId()"/>')"/>
					</td>
					<td>
						<s:property value="#obsCriTO.getObsId()" />
					</td>
					<td>
						<s:property value="#obsCriTO.getObsName()" />
					</td>
					<td  nowrap="nowrap">
						<s:property value="#obsCriTO.getObsType()" />
					</td>	
					<td>
						<s:property value="#obsCriTO.getObsFirstPosition()" />
					</td>
					<td>
						<s:property value="#obsCriTO.getObsSecondPosition()" />
					</td>
					<td>
						<s:property value="#obsCriTO.getObsStartDate()" />
					</td>
					<td  nowrap="nowrap">
						<s:property value="#obsCriTO.getObsEndDate()" />
					</td>	
					<td>
						<s:property value="#obsCriTO.getObsOperationType()" />
					</td>				
				  </tr>
				  <%i++;%>
			  </s:iterator> 
		</s:if>
		<s:else>
			<tr><td colspan=3 class="txtblackBP">
			No Record found
			</td></tr> 
		</s:else>
		<TR class="HeadingPage" height="23">
			<TD colspan="10" width="100%" align="right" >										
				<a href='javascript:getPagination(document.frmObservatorytAdmin.cmbNoOfPage,"F");' style="visibility:<s:property value='previousSt'/>;">|<</a>&nbsp;
				<a href='javascript:getPagination(document.frmObservatorytAdmin.cmbNoOfPage,"B")' style="visibility:<s:property value='previousSt'/>;"><<</a>&nbsp;
				<span id="pageIndicator" style='visibility:<s:property value="pageStatus"/>;'>									
					Page:
					<s:select name="cmbNoOfPage" list="noOfPages" cssClass="stylecombo" onchange='getPagination(this,"P")' theme="simple"/>&nbsp;
				</span>	
				<a href='javascript:getPagination(document.frmObservatorytAdmin.cmbNoOfPage,"N")'  style="visibility:<s:property value='nextSt'/>;">>></a>&nbsp;
				<a href='javascript:getPagination(document.frmObservatorytAdmin.cmbNoOfPage,"L")'  style="visibility:<s:property value='nextSt'/>;">>|</a>&nbsp;
			</TD>
		 </TR>		       
		</table>
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
 </body>	
</HTML>
