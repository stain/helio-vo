	
	<%contextPath=request.getContextPath();%>
  	<!-- Footer starts here -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="txtHeading">
        <td height="5" align="left" valign="top"><img src="<%=contextPath%>/Images/spacer.gif" width="1" height="1"></td>
      </tr>
      <tr>
        <td align="left" valign="top">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="txtFooter">
                    <td>&nbsp;<img src="<%=contextPath%>/Images/Titel-Admin.gif" ></td>
              		<td width="31%" align="right" valign="top" class="txtFooter">&nbsp;<a href="http://www.mssl.ucl.ac.uk/tos/">&nbsp;Terms and Conditions</a>&nbsp;<br>Copyright © 2001 - 2007 UCL MSSL &nbsp; </td>
           
                  </tr>
            </table>
        </td>
      </tr>
    </table>
    


<iframe id="XSX"
src ="<%=contextPath%>/jsp/blank.jsp?"+Math.random()
width="0%" height="0%" >
</iframe>

	<script>
	function keepMeAlive(imgName) {
		var X= "<%=contextPath%>/jsp/blank.jsp?";
		X=X.replace(/\?.*$/, '?' + Math.random());
		document.getElementById(imgName).src=X;
	}
	window.setInterval("keepMeAlive('XSX')", 1200000); // 20 mins = 1200000 miliseconds
	</script>
 
     <!-- Footer ends here -->
  
