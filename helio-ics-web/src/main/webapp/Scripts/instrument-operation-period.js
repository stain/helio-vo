
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
	
function getInsDescDetails(obj){ 
		
		 document.frmInstrumentAdmin.insOpsName.value=obj.value;
		var sSelected=obj.value;
		//alert(obj.value+" : ID : "+document.frmInstrumentAdmin.insOpsName.value+" sSelected : "+sSelected);
		
		if(window.ActiveXObject){
			request = new ActiveXObject("Microsoft.XMLHTTP");
		}
		else if(window.XMLHttpRequest){
			request = new XMLHttpRequest();
		}

	   if(request!=null){
		//alert(" before call ");
		commonAjaxOpen(request, "getInsDescDetails.action?insID="+sSelected);
		//alert(" after call ");
		request.onreadystatechange=insDescLoadCallback;
		//alert(" setting call ");
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.send(null);
	 }
}


function insDescLoadCallback()
{
	//alert(" load call back");
	var ready=request.readyState;
	
	if(ready == 4)
	{
		//alert(request.responseText)
		
		var cmbInsDes=document.getElementById("cmbInsDes");
		
		var instruments=request.responseXML.getElementsByTagName("instrument");
	
		//alert("cmbInsDes "+cmbInsDes+" instruments"+instruments);
		
		removeOptions(cmbInsDes);
		var opt=document.createElement('option');
			opt.value="s";
			opt.text="--Select--";
			cmbInsDes.appendChild(opt);
		for(var i=0;i<instruments.length;i++){
			//Populating Combo box
			var opt=document.createElement('option');
			opt.value=getNodeValue(instruments[i],"execN");
			opt.text=getNodeValue(instruments[i],"displayN");
			cmbInsDes.appendChild(opt);
			
			
		}
	}
}

function getInstrumentOperationPeriodDetails(){
	   document.frmInstrumentAdmin.target="_self";
	   document.frmInstrumentAdmin.action="getSearchUpdateInstrumentOperationPeriod.action";
	   document.frmInstrumentAdmin.method="post";
	   document.frmInstrumentAdmin.submit();
}


function saveInstrumentOperationPeriod(){
	   document.frmInstrumentAdmin.target="_self";
	   document.frmInstrumentAdmin.action="saveInstrumentOperationPeriod.action";
	   document.frmInstrumentAdmin.method="post";
	   document.frmInstrumentAdmin.submit();
}

function editInstrumentOperationPeriodtPage(insId)
{
	//alert(insId);
	document.frmInstrumentAdmin.target="_self";
	document.frmInstrumentAdmin.action="editInstrumentOperationPeriodDetails.action?insId="+insId;
	document.frmInstrumentAdmin.method="post";
	document.frmInstrumentAdmin.submit();
}

function updateInstrumentOperationPeriodDetails(){
	   document.frmInstrumentAdmin.target="_self";
	   document.frmInstrumentAdmin.action="updateInstrumentOperationPeriodDetails.action";
	   document.frmInstrumentAdmin.method="post";
	   document.frmInstrumentAdmin.submit();
}

function getPagination(comboVal,option)
{	
	
	if(option=="P")
	{
		document.frmInstrumentAdmin.cmbNoOfPage.value=comboVal.value;
    	status="true";
	}	
	
	if(option=="F")
	{
		if(comboVal.value==1)
		{
			document.frmInstrumentAdmin.cmbNoOfPage.value=comboVal.value;
    		
    	}
    	else
    	{
    		document.frmInstrumentAdmin.cmbNoOfPage.value=1;
    		status="true";
    	}
	}	

	if(option=="B")
	{
		if(comboVal.value==1)
		{
			document.frmInstrumentAdmin.cmbNoOfPage.value=comboVal.value;
    	}
		else
		{
			backVal = parseInt(comboVal.value)-1;
			document.frmInstrumentAdmin.cmbNoOfPage.value=backVal;
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
			document.frmInstrumentAdmin.cmbNoOfPage.value=pageLen;
		}	
		else
		{
			document.frmInstrumentAdmin.cmbNoOfPage.value=nextVal;
    		status="true";
		}
	}
	

	if(option=="L")
	{
		pageLen = comboVal.length;
		if(comboVal.value==pageLen)
		{
			document.frmInstrumentAdmin.cmbNoOfPage.value=comboVal.value;
    	}
    	else
    	{	
    		document.frmInstrumentAdmin.cmbNoOfPage.value=pageLen;
    		status="true";
    	}
	}
    document.frmInstrumentAdmin.action ="getSearchUpdateInstrumentOperationPeriod.action";
	document.frmInstrumentAdmin.method = "post";
	document.frmInstrumentAdmin.submit();
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
								
				