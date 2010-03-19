

function getNodeValue(obj,tag)
{ 
	return obj.getElementsByTagName(tag)[0].firstChild.nodeValue;
}
function removeOptions(obj)
{
	while(obj.firstChild){
		obj.removeChild(obj.firstChild);
	}
}
	
function getObservatoryDetails(){
	   document.frmObservatorytAdmin.target="_self";
	   document.frmObservatorytAdmin.action="getSearchUpdateObservatory.action";
	   document.frmObservatorytAdmin.method="post";
	   document.frmObservatorytAdmin.submit();
}


function saveObservatoryDetails(){
	   document.frmObservatorytAdmin.target="_self";
	   document.frmObservatorytAdmin.action="saveObservatory.action";
	   document.frmObservatorytAdmin.method="post";
	   document.frmObservatorytAdmin.submit();
}

function editObservatoryPage(obsId)
{
	//alert(obsId);
	document.frmObservatorytAdmin.target="_self";
	document.frmObservatorytAdmin.action="editObservatorytDetails.action?obsId="+obsId;
	document.frmObservatorytAdmin.method="post";
	document.frmObservatorytAdmin.submit();
}

function updateObservatoryDetails(){
	   document.frmObservatorytAdmin.target="_self";
	   document.frmObservatorytAdmin.action="updateObservatoryDetails.action";
	   document.frmObservatorytAdmin.method="post";
	   document.frmObservatorytAdmin.submit();
}

function getPagination(comboVal,option)
{	
	
	if(option=="P")
	{
		document.frmObservatorytAdmin.cmbNoOfPage.value=comboVal.value;
    	status="true";
	}	
	
	if(option=="F")
	{
		if(comboVal.value==1)
		{
			document.frmObservatorytAdmin.cmbNoOfPage.value=comboVal.value;
    		
    	}
    	else
    	{
    		document.frmObservatorytAdmin.cmbNoOfPage.value=1;
    		status="true";
    	}
	}	

	if(option=="B")
	{
		if(comboVal.value==1)
		{
			document.frmObservatorytAdmin.cmbNoOfPage.value=comboVal.value;
    	}
		else
		{
			backVal = parseInt(comboVal.value)-1;
			document.frmObservatorytAdmin.cmbNoOfPage.value=backVal;
    		status="true";
		}
		
	}	
	
	if(option=="N")
	{
		//alert("Next Option")
		pageLen = comboVal.length;
		nextVal = parseInt(comboVal.value)+1;
		if(nextVal > pageLen)
		{
			document.frmObservatorytAdmin.cmbNoOfPage.value=pageLen;
		}	
		else
		{
			document.frmObservatorytAdmin.cmbNoOfPage.value=nextVal;
    		status="true";
		}
	}
	

	if(option=="L")
	{
		pageLen = comboVal.length;
		if(comboVal.value==pageLen)
		{
			document.frmObservatorytAdmin.cmbNoOfPage.value=comboVal.value;
    	}
    	else
    	{	
    		document.frmObservatorytAdmin.cmbNoOfPage.value=pageLen;
    		status="true";
    	}
	}
    document.frmObservatorytAdmin.action ="getSearchUpdateObservatory.action";
	document.frmObservatorytAdmin.method = "post";
	document.frmObservatorytAdmin.submit();
	return true;
}


function calculateLoadingTime(){
	try{
			request=new ActiveXObject("Microsoft.XMLHTTP");
	}
	catch(other)
	{
		try{
				request=new XMLHttpRequest();
		}
		catch(browser)
		{
			alert("Ajax Not Supported")	
		}
	}
	var rowsPerPage=10;
	if(document.getElementById('rowsPerPage')){
		rowsPerPage=document.getElementById('rowsPerPage').value;
	}
	commonAjaxOpen(request, "calculateLoadingTime.action?rowsPerPage="+rowsPerPage);
	request.onreadystatechange=getLoadingTimeCallback;
	request.send(null);
}
var timerequired=8;
function getLoadingTimeCallback(){
	var ready=request.readyState;
	if(ready == 4)
	{
		var data=request.responseText;
		if(data!=null && data!=""){
			try{
				timerequired=parseInt(data);
				//alert("hi")
				//showLoadingIndicatorScreen(timerequired);
			}catch(Err){}
		}
	}
	
}	
function closeIndicatorPopupFromChild(){
		window.opener.document.getElementById('ExportDiv').style.visibility="hidden";
		window.opener.document.getElementById('ExportTable').style.visibility="hidden";
		window.opener.document.getElementById('ExportiFrame').style.visibility="hidden";
		window.opener.document.onreadystatechange=null;
		window.resizeTo(900,500);
		window.focus();
	}
								
				