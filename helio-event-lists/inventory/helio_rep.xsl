<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="4.0" encoding="iso-8859-1" indent="yes"/>
	<xsl:key name="HEClist" match="ListID" use="."/>
	<xsl:template match="/">
		<html>
			<head>
				<title>HELIO Event Catalogue Inventory</title>
				<style type="text/css"> 
body,td,th {
font-family: Arial, Helvetica, sans-serif; 
font-size: x-small }
.style1 {
font-size: xx-small;
font-style: italic;
}

</style>
			</head>
			<body>
				<h1>HELIO HEC Lists</h1>
				<table>
					<tr>
						<th>List ID</th>
						<th>List Name</th>
					</tr>
					<xsl:for-each select="/dataroot/Query1/ListID[generate-id(.)=generate-id(key('HEClist',.)[1])]">
						<tr>
							<td>
								<xsl:element name="a">
									<xsl:attribute name="href">#<xsl:value-of select="."/></xsl:attribute>
									<xsl:value-of select="."/>
								</xsl:element>
							</td>
							<td>
								<xsl:value-of select="../ListName"/>
							</td>
						</tr>
					</xsl:for-each>
				</table>
				<hr/>
				<xsl:for-each select="/dataroot/Query1/ListID[generate-id(.)=generate-id(key('HEClist',.)[1])]">
					<xsl:variable name="LID" select="."/>
					<p>
						<u>
							<xsl:value-of select="."/>
						</u>
						<xsl:element name="a">
							<xsl:attribute name="name"><xsl:value-of select="."/></xsl:attribute>
						</xsl:element>
						<br/>
						<br/>
						<center>
							<u>
								<b>
									<xsl:value-of select="../ListName"/>
								</b>
							</u>
						</center>
					</p>
					<table>
						<tr>
							<td>
								<b>Purpose:</b>
							</td>
						</tr>
						<tr>
							<td>
								<xsl:value-of select="../ListPurpose"/>
							</td>
						</tr>
						<tr>
							<td>
								<b>Description</b>
							</td>
						</tr>
						<tr>
							<td>
								<xsl:value-of select="../ListDesc"/>
							</td>
						</tr>
						<tr>
							<td>
								<b>Caveats</b>
							</td>
						</tr>
						<tr>
							<td class="style1">
								<xsl:value-of select="../ListCaveats"/>
							</td>
						</tr>
						<tr>
							<td>
								<b>Acknowledgement/References</b>
							</td>
						</tr>
						<tr>
							<td class="style1">
								<xsl:value-of select="../ListAck"/>
							</td>
						</tr>
						<tr>
							<td class="style1">
								<xsl:element name="a">
									<xsl:attribute name="href"><xsl:value-of select="../ListRefURL"/></xsl:attribute>
									<xsl:value-of select="../ListRefURL"/>
								</xsl:element>
							</td>
						</tr>
						<tr>
							<td>
								<b>Parameters:</b>
							</td>
						</tr>
						<tr>
							<td>
								<table width="100%">
									<xsl:for-each select="/dataroot/Query1[ListID=$LID]">
										<tr>
											<td width="25px"/>
											<td width="150px">
												<u>
													<b>
														<xsl:value-of select="FieldName"/>
													</b>
												</u>
											</td>
											<td width="25px"/>
											<td width="200px">
												<i>
													<xsl:value-of select="FieldUnits"/>
													<xsl:text>  </xsl:text>(<xsl:value-of select="FieldSIConv"/>)
<xsl:text>  </xsl:text>
													<xsl:value-of select="FieldCoordSystem"/>
												</i>
											</td>
										</tr>
										<tr>
											<td/>
											<td colspan="4">
												<xsl:value-of select="FieldDesc"/>
												<br/>
												<i class="style1">(UCD: <xsl:value-of select="FieldUCD"/>, UTYPE: <xsl:value-of select="FieldUTYPE"/>)</i>
											</td>
										</tr>
									</xsl:for-each>
								</table>
							</td>
						</tr>
						<br/>
					</table>
					<hr/>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
