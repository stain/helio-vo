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
	<script src="<%=contextPath%>/Scripts/instrument.js"></script>
	<script src="<%=contextPath%>/Scripts/menu.js"></script>
  </HEAD>
  <BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  onLoad="MM_preloadImages('<%=contextPath%>/Images/but_stop_click.gif')">
  <DIV id="ToolTip">
    </DIV>
   <s:form name="frmInstrumentAdmin" id="frmInstrumentAdmin" method="post" action="">
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
							Instrument Search page 
						</TD>
						</TR>
				</TABLE>
			</TD>
		</TR>
    </table>   
    <table>
    <tr>
       <td width="20%" align="right" class="txtblackBP" valign="middle">
        	Instrument Names&nbsp;
       </td>
       <td width="20%" align="left" valign="middle">
        		<s:textfield  cssClass="textfield" size="25" id="insName" name="insName" theme="simple"/>
        </td>
	     <td width="20%" align="left" valign="middle">&nbsp;
		        <a href="javascript:getInstrumentDetails();" onMouseOver="MM_swapImage('go','','<%=contextPath%>/Images/but_search_click.gif',1)" onMouseOut="MM_swapImgRestore()" ><img src="<%=contextPath%>/Images/but_search_normal.gif" name="go" border="0" align="absmiddle" id="go"></a>&nbsp;		   		
        </td>
      </tr>
     <!--  <tr>
        <td>&nbsp;</td>
      </tr>-->      
    </table> 
	<table width="100%" border="0" cellspacing="1" cellpadding="0">
		 <tr class="txtPopupGridHeading">
		      <td class="txtblackBP">&nbsp;</td>
 			  <td class="txtblackBP">Instrument Name</td>
              <td class="txtblackBP">Instrument Observatory</td>
              <td class="txtblackBP">Instrument Description</td>                       
              <td class="txtblackBP">Instrument Start Date Time</td>
              <td class="txtblackBP">Instrument End Date Time </td>
		  </tr>
		<% int i=0;%>	
		<s:if test='insDetailsTO!=null'> 
			   <s:iterator value="insDetailsTO" status="rowstatus" id="insCriTO">
					 <s:if test="#rowstatus.odd == true">
						<s:set name="classType" value="'PopupAltDataRow'"/>
					 </s:if>
					 <s:else>
						<s:set name="classType" value="'PopupDataRow'"/>
					 </s:else>
				  <tr class='<s:property value="#classType"/>' height="10" align="left" valign="middle">
				    <td>
					   <IMG src="<%=contextPath%>/Images/icon_edit.gif" style="CURSOR:hand" onclick="javascript:editInstrumentPage('<s:property value="#insCriTO.getId()"/>')"/>
					</td>
					<td>
						<s:property value="#insCriTO.getInsId()" />
					</td>
					<td>
						<s:property value="#insCriTO.getInsObs()" />
					</td>
					<td  nowrap="nowrap">
						<s:property value="#insCriTO.getInsName()" />
					</td>	
					<td>
						<s:property value="#insCriTO.getInsStartDate()" />
					</td>
					<td>
						<s:property value="#insCriTO.getInsEndDate()" />
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
			<TD colspan="8" width="100%" align="right" >										
				<a href='javascript:getPagination(document.frmInstrumentAdmin.cmbNoOfPage,"F");' style="visibility:<s:property value='previousSt'/>;">|<</a>&nbsp;
				<a href='javascript:getPagination(document.frmInstrumentAdmin.cmbNoOfPage,"B")' style="visibility:<s:property value='previousSt'/>;"><<</a>&nbsp;
				<span id="pageIndicator" style='visibility:<s:property value="pageStatus"/>;'>									
					Page:
					<s:select name="cmbNoOfPage" list="noOfPages" cssClass="stylecombo" onchange='getPagination(this,"P")' theme="simple"/>&nbsp;
				</span>	
				<a href='javascript:getPagination(document.frmInstrumentAdmin.cmbNoOfPage,"N")'  style="visibility:<s:property value='nextSt'/>;">>></a>&nbsp;
				<a href='javascript:getPagination(document.frmInstrumentAdmin.cmbNoOfPage,"L")'  style="visibility:<s:property value='nextSt'/>;">>|</a>&nbsp;
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
