/*
* HELIOcharts - A framework to allow the creation of interactive charts for the HELIO project.
*
* Author: Roman Boutellier
*
*/

/**
* Global variable for the chart
*/
var helioChart;

/**
* Global variable for the chart object
*/
var chartObject;

/**
* Global variables for different names
*/
var chartContainer,additionalContainer,chartTitle;

/**
* Global variable for the configuration object
*/
var helioConfObject;

/**
* Global variable to store the type of graph actually displayed
*/
var actualPlotType = 'default';

/**
* The main function of the framework. It creates a HELIOchart in the given container.
* @param containerName Name of the container to plot the chart into
* @param additionalContainerName Name of the container to display the additional Information (checkboxes to chose series, start/end-date of the selected part of the chart)
* @param chartTitleName Title of the chart
* @param jsonObject The object containing the data for the chart
* @param catalogueName Name of the catalogue from which data is displayed.
*/
function createHELIOChart(containerName,additionalContainerName,chartTitleName,jsonObject,catalogueName) {
	// Store the containerName, the additionalContainerName and the chartTitle
	chartContainer = containerName;
	additionalContainer = additionalContainerName;
	chartTitle = chartTitleName;
	
	// Create a helioChartObject
	chartObject = new helioChartObject();
	
	// Set the json into the helioChartObject
	chartObject.setJSON(jsonObject);
	
	// Get the configuration object
	helioConfObject = getHELIOconfiguration(catalogueName);
	
	// Create the data set within the helioChartObject
	// Pass the default array of series names
	var standardPlotType = helioConfObject.standardPlot;
	chartObject.parseJSON(window['helioConfObject'][standardPlotType]['yAxisDefault'][0],window['helioConfObject'][standardPlotType]['xAxisDefault'][0],window['helioConfObject'][standardPlotType]['addInfo'],0);
	
	/////////////////////
	// Create the menu //
	/////////////////////
	
	// Create a checkbox menu to select the possible series
	var yAxisDefault = window['helioConfObject'][standardPlotType]['yAxisDefault'];

	var stringDropDown = '<div id="selector"><form><p>';
	for(var m=0;m<window['helioConfObject'][standardPlotType]['yAxis']['length'];m++){
		// Get the name of the value
		var actualYAxisArray = window['helioConfObject'][standardPlotType]['yAxis'];
		var actual_sTitle = actualYAxisArray[m];
		// Get the description of the value (which is then displayed in the tooltip)
		var actual_sDescription = '';
		for(var descr_var=0;descr_var<chartObject.jsonObject.aoColumns.length;descr_var++){
			if(chartObject.jsonObject.aoColumns[descr_var].sTitle == actual_sTitle){
				actual_sDescription = chartObject.jsonObject.aoColumns[descr_var].sDescription;
			}
		}
		// Add the value to the checkbox menu only if it is present in the json object
		if(getIndexInAoColumns(chartObject.jsonObject.aoColumns,actual_sTitle) != -1){
	        var checked = yAxisDefault.indexOf(actual_sTitle) >= 0 ? 'checked="checked"' : '';
			stringDropDown += '<input type="checkbox" name="seriesName" value="' + actual_sTitle + '" ' + checked + '><label title="' + actual_sDescription + '">' + actual_sTitle + '</label><br />';
		}
	}
	
	// Create a radiobutton menu to select the value to be put on the x-axis (only if more than one type exists)
	// Therefore first get the actual type of plot
	var plotType_xAxisMenu = '';
	if(actualPlotType == 'default'){
		plotType_xAxisMenu = helioConfObject.standardPlot;
	} else {
		plotType_xAxisMenu = actualPlotType;
	}
	// Get the possible x-axis values
	var xAxis_values = window['helioConfObject'][plotType_xAxisMenu]['xAxis'];
	var xAxisDefault = window['helioConfObject'][plotType_xAxisMenu]['xAxisDefault'];
	
	// Create the radiobutton menu
	if(xAxis_values.length > 1){
		stringDropDown += '<br />Value for x-axis:<br />';
		for(var p=0;p<xAxis_values.length;p++){
			// Get the name of the value
			var actual_xAxisValue = xAxis_values[p];
			// Get the description of the value (which is then displayed in the tooltip)
			var actual_sDescription_xAxis = '';
			for(var descr_var_xAxis=0;descr_var_xAxis<chartObject.jsonObject.aoColumns.length;descr_var_xAxis++){
				if(chartObject.jsonObject.aoColumns[descr_var_xAxis].sTitle == actual_xAxisValue){
					actual_sDescription_xAxis = chartObject.jsonObject.aoColumns[descr_var_xAxis].sDescription;
				}
			}
			
			// test if checked should be set
	        var checked = xAxisDefault == actual_xAxisValue ? 'checked="checked"' : '';

	        // Add the value to the radiobutton menu
			stringDropDown += '<input type="radio" name="xAxisValue" value="' + actual_xAxisValue + '" ' + checked + '><label title="' + actual_sDescription_xAxis + '">' + actual_xAxisValue + '</label><br />';
		}
	}
	
	// Create a radiobutton menu to select the kind of plot (only if more than one type exists)
	if((helioConfObject.plots.length > 1) || (helioConfObject.yAxisDivide != undefined)){
		stringDropDown += '<br />Type of plot:<br />';
		for(var o=0;o<helioConfObject.plots.length;o++){
			// Variable to store the actual plot type
			var actual_plotType = helioConfObject.plots[o];
			// Variable to store the label for the tooltip (only used for the histogram)
			var actual_sDescription_xAxis_2 = '';
			// If the actual plot type is 'histogram', add the name of the series that is available
			var checked = helioConfObject.standardPlot == actual_plotType ? 'checked="checked"' : '';
			if(actual_plotType == 'histogram'){
				actual_plotType += ' (' + helioConfObject.histogram.xAxisName + ')';
				
				// Get the description of the value (which is then displayed in the tooltip)
				for(var descr_var_xAxis=0;descr_var_xAxis<chartObject.jsonObject.aoColumns.length;descr_var_xAxis++){
					if(chartObject.jsonObject.aoColumns[descr_var_xAxis].sTitle == helioConfObject.histogram.xAxisName){
						actual_sDescription_xAxis_2 = chartObject.jsonObject.aoColumns[descr_var_xAxis].sDescription;
					}
				}
				// Plot the string
				stringDropDown += '<input type="radio" name="plotType" ' + checked + ' value="' + actual_plotType + '"><label title="' + actual_sDescription_xAxis_2 + '">' + actual_plotType + '<br />';
			} else {
				// Plot the string without the label
				stringDropDown += '<input type="radio" name="plotType" ' + checked + ' value="' + actual_plotType + '">' + actual_plotType + '<br />';
			}
		}
	}
	
	// Add a button to redraw the plot
	stringDropDown += '</p><p><input type="button" value="Display selected series" onclick="seriesSelectedRedraw(this.form)" /></p></form></div><br /><div id="zoomSelect"><b>Zooming is enabled</b></div><br /><div id="range"></div>';

	// Set the menu into the additional container
	document.getElementById(additionalContainerName).innerHTML = stringDropDown;
	
	////////////////////
	// Draw the chart //
	////////////////////
	
	// Display the chart within the given container
	createScatterChart(chartContainer,additionalContainer,chartTitle,chartObject.dataStream,chartObject.yAxisArray);
}

/**
* This function is called, when the user clicks on the redraw button.
* It creates a new dataStream out of the series the user selected.
* Then the chart is redrawn.
*
* @param form The form from which the function has been called.
*/
function seriesSelectedRedraw(form) {
	// Create booleans for different plot types
	var drawScatter = false;
	var drawColumn = false;
	var drawHistogram = false;
	
	// Create variable for the name of the actual plot type
	var actualPlotType = '';
	
	// Set the standard plot type to true
	if(helioConfObject.standardPlot == 'scatter'){
		drawScatter = true;
		actualPlotType = 'scatter';
	} else if(helioConfObject.standardPlot == 'column'){
		drawColumn = true;
		actualPlotType = 'column'
	} else if(helioConfObject.standardPlot == 'histogram'){
		drawHistogram = true;
		actualPlotType = 'histogram'
	}
	
	// Create the array to put the selected series names into
	var selectedNames = [];
	
	// Variable for the x-axis value name
	var xAxisValueName = '';
	
	// Loop over all checkbox elements and add the checked ones into the array
	// Also set the boolean for the plot to type and the x-axis name
	for(var i=0;i<form.elements.length;i++) {
		var element = form.elements[i];
		if((element.type == 'checkbox')&&(element.checked)&&(element.name == 'seriesName')) {
			selectedNames.push(form.elements[i].value);
		} else if((element.type == 'radio')&&(element.checked)&&(element.name == 'plotType')) {
			// If scatter is selected, set scatter to true and the rest to false
			if(element.value == 'scatter') {
				drawScatter = true;
				drawColumn = false;
				drawHistogram = false;
				// Set the actual plot type
				actualPlotType = 'scatter';
			} else if(element.value == 'column') { // If column is selected, set column to true and the rest to false
				drawScatter = false;
				drawColumn = true;
				drawHistogram = false;
				// Set the actual plot type
				actualPlotType = 'column';
			} else if(element.value.indexOf('histogram') != -1) { // If histogram is selected, set histogram to true and the rest to false
				drawScatter = false;
				drawColumn = false;
				drawHistogram = true;
				// Set the actual plot type
				actualPlotType = 'histogram';
			}
		} else if((element.type == 'radio')&&(element.checked)&&(element.name == 'xAxisValue')) {
			xAxisValueName = element.value;
		}
	}
	
	// If no special x axis has been selected, take the default one
	if(xAxisValueName == ''){
		xAxisValueName = window['helioConfObject'][actualPlotType]['xAxisDefault'];
	}
	
	// If no y axis value has been selected, take 'Events'
	if(selectedNames.length == 0){
		selectedNames.push('Events');
	}
	
	// Create the new dataStream
	if(drawHistogram){ // Parse for a histogram
		// Clear the dataStream and the yAxisArray
		chartObject.dataStream = [];
		chartObject.yAxisArray = [];
		chartObject.dividingSeries = [];
		chartObject.parseJSONhistogram(helioConfObject.histogram.yAxisName,helioConfObject.histogram.xAxisName,helioConfObject.histogram.xAxisCategories,helioConfObject.histogram.xFunction,0);
	} else { // Parse for a scatter chart
		// Clear the dataStream and the yAxisArray
		chartObject.dataStream = [];
		chartObject.yAxisArray = [];
		chartObject.dividingSeries = [];
		for(var numberName=0;numberName<selectedNames.length;numberName++){
			// Get the actual name
			var actualName = selectedNames[numberName];
			// Check if the actual series is 'normal' or divides the y-axis in a special way
			if(isDividingSeries(actualName,window['helioConfObject'][actualPlotType])){
				chartObject.parseJSONdividedScatter(window['helioConfObject'][actualPlotType][actualName]['values'],actualName,xAxisValueName,window['helioConfObject'][actualPlotType]['addInfo'],numberName);
			} else {
				chartObject.parseJSON(selectedNames[numberName],xAxisValueName,window['helioConfObject'][actualPlotType]['addInfo'],numberName);
			}
		}
	}
	
	// Display the chart within the given container
	// If scatter was selected, display the scatter chart
	if(drawScatter){
		createScatterChart(chartContainer,additionalContainer,chartTitle,chartObject.dataStream,chartObject.yAxisArray);
		if(chartObject.zoomEnabled){
			// Change text of the zoom/selection div
			document.getElementById('zoomSelect').innerHTML = '<b>Zooming is enabled</b>';
		} else {
			// Change text of the zoom/selection div
			document.getElementById('zoomSelect').innerHTML = '<b>Selection is enabled</b>';
		}
	} else if(drawColumn) {
		createColumnChart(chartContainer,additionalContainer,chartTitle,chartObject.dataStream,chartObject.yAxisArray);
		if(chartObject.zoomEnabled){
			// Change text of the zoom/selection div
			document.getElementById('zoomSelect').innerHTML = '<b>Zooming is enabled</b>';
		} else {
			// Change text of the zoom/selection div
			document.getElementById('zoomSelect').innerHTML = '<b>Selection is enabled</b>';
		}
	} else if(drawHistogram) {
		createHistogramChart(chartContainer,additionalContainer,chartTitle,chartObject.dataStream,chartObject.yAxisArray,chartObject.xAxisCategories,chartObject.xAxisValueName);
		// Change text of the zoom/selection div
		document.getElementById('zoomSelect').innerHTML = '';
	} else {
		alert('Error: No Plot type selected');
	}
}

/**
* Function to enable/disable zooming/selection.
*/
function buttonZoom(){
	if(chartObject.zoomEnabled){ // Zooming is enabled
		// Disable zooming
		chartObject.zoomEnabled = false;
	} else { // Selecting is enabled
		// Disable selecting
		chartObject.zoomEnabled = true;
	}
}

/**
* Constructor for a helioChartsObject
* This object is used to store different needed data:
*	- the JSON object
*	- the parsed dataStream
*	- additional information about the chart needed
*/
function helioChartObject() {
	// The JSON stored within this object
	this.jsonObject = {};
	
	// The name of the data which is described by the x-axis
	this.xAxisValueName = "";
	
	// The dataStream which is used to create the chart
	this.dataStream = [];
	
	// Title of the chart
	this.chartTitle = "default chart title";
	
	// Array for the y-axes options
	this.yAxisArray = [];
	
	// Array for the x-axis categories (used for histogram chart)
	this.xAxisCategories = [];
	
	// Array containing the points selected by zooming
	this.zoomPointsArray = [];
	
	// Array containing all actually shown series which divide the y-axis into categories
	this.dividingSeries = [];
	
	// Boolean to decide if zooming or selecting is enabled
	this.zoomEnabled = true;
	
	// Add a method to get the JSON
	this.setJSON=setJSON;
	
	// Add a method to parse the JSON
	this.parseJSON=parseJSON;
	
	// Add a method to parse the JSON for the histogram
	this.parseJSONhistogram=parseJSONhistogram;
	
	// Add a method to parse the JSON for the divided scatter chart
	this.parseJSONdividedScatter=parseJSONdividedScatter;
}

/**
* Function to set the JSON of the helioChartObject
* @param jsonVar Variable containing the json object with all the data
*/
function setJSON(jsonVar){
	this.jsonObject = jsonVar;
}

/**
* An array containing the colors which are used for the charts
*/
var helioColors = [
					'#4572A7',
					'#AA4643',
					'#89A54E',
					'#80699B',
					'#3D96AE',
					'#DB843D',
					'#92A8CD',
					'#A47D7C',
					'#B5CA92'
];

/**
* Parse the JSON object containing the data and create a dataSeries object as well as
* a yAxis object which contain the data belonging to the given series name. Those two
* objects are then pushed into the according arrays inside the helioChartsObject.
*
* @param seriesName Name of the serie to be mapped against the y-axis
* @param xAxisValue Name of the value to be mapped against the x-axis
* @param arrayAddInfo Array containing the names of all fields within the additional information part
* @param yAxisNumber Number of the y-axis for the dataStream object
*/
function parseJSON(seriesName,xAxisValue,arrayAddInfo,yAxisNumber){
	// Set the name of the x-axis
	this.xAxisValueName = xAxisValue;
	
	// Get the aaData object containing all data
	var aaDataObject = this.jsonObject.aaData;
	
	// Get the number of entries within the aaDataObject
	var numberOfEntries = aaDataObject.length;
	
	/////////////////////////////
	// Create the yAxis object //
	/////////////////////////////
	
	// Create an object for the y-axes-array
	var yAxisObject = {};
	// Create an object for the label of the y-axis
	var yAxisObjectLabel = {};
	// Create an object for the style of the label of the y-axis
	var yAxisObjectLabelStyle = {};
	// Set the color of the style of the label
	yAxisObjectLabelStyle.color = helioColors[yAxisNumber%9];
	// Set the style of the label
	yAxisObjectLabel.style = yAxisObjectLabelStyle;
	// Set the labels of the axis
	yAxisObject.labels = yAxisObjectLabel;
	// Create an object for the title of the y-axis
	var yAxisObjectTitle = {};
	// Create an object for the style of the title of the y-axis
	var yAxisObjectTitleStyle = {};
	// Set the title of the y-axis
	yAxisObjectTitle.text = seriesName;
	// Set the color of the title
	yAxisObjectTitleStyle.color = helioColors[yAxisNumber%9];
	// Set the style of the title
	yAxisObjectTitle.style = yAxisObjectTitleStyle;
	// Set the title of the axis
	yAxisObject.title = yAxisObjectTitle;
	// Push the object into the array
	this.yAxisArray.push(yAxisObject);
	
	
	//////////////////////////////////
	// Create the dataSeries object //
	//////////////////////////////////
	
	// Create a new series object
	var seriesObject = {};
	// Set the name of the series
	seriesObject.name = seriesName;
	// Set the number of the y-axis
	seriesObject.yAxis = yAxisNumber;
	// Create a new dataObject for each point and store it in an array
	// First create the new array
	var dataObjectArray = [];
	// Get the index of the required x-value within the aaDataObject array
	var xValueIndex = getIndexInAoColumns(this.jsonObject.aoColumns,xAxisValue);
	// Get the index of the required y-value within the aaDataObject array
	var yValueIndex = getIndexInAoColumns(this.jsonObject.aoColumns,seriesName);
	// Loop over the number of entries in this series
	for(var j=0;j<numberOfEntries;j++) {
		// Create a new dataObject
		var dataObject = {};
		// Get the new date for the x value
		var xValueDate = parseDateFormat(aaDataObject[j][xValueIndex+1]); // Add 1 to the xValueIndex as the first (0) value is not described in the aoColumns object
		// Set the x value
		dataObject.x = xValueDate;
		// Set the y value
		// If the y value name is 'Events', set the y value to the number of the series + 1
		if(seriesName == 'Events') {
			dataObject.y = yAxisNumber + 1;
		} else { // Set the correct y value
			dataObject.y = aaDataObject[j][yValueIndex+1]; // Add 1 to the yValueIndex as the first (0) value is not described in the aoColumns object
		}
		// Set the name of the x value
		dataObject.xName = xAxisValue;
		// Set the name of the y value
		dataObject.yName = seriesName;
		// Create the string which describes the additional data
		var additionalDataString = '';
		for(var l=0;l<arrayAddInfo.length;l++){
			// First get the index of the required value within the aaDataObject array
			var addValueIndex = getIndexInAoColumns(this.jsonObject.aoColumns,arrayAddInfo[l]);
			// Set the name of the actual additional information parameter
			additionalDataString += arrayAddInfo[l];
			additionalDataString += ': ';
			// Set the value of the actual additional information parameter
			// If the index is -1, the value isn't present
			if(addValueIndex == -1){
				additionalDataString += '-'; // Write '-' as the value isn't available
				additionalDataString += '; ';
			} else {
				additionalDataString += aaDataObject[j][addValueIndex+1]; // Add 1 to the addValueIndex as the first (0) value is not described in the aoColumns object
				additionalDataString += '; ';
			}
			// Only 3 different information on one line
			// Therefore add a <br /> after 3 data points
			if((l%3 == 2) && (l < (arrayAddInfo.length - 1))) {
				additionalDataString += '<br />';
			}
		}
		// Delete the last '; '
		additionalDataString = additionalDataString.slice(0,-2);
		// Set additional information
		dataObject.additionalInformation = additionalDataString;
		// Add the object to the array of dataObjects
		dataObjectArray.push(dataObject);
	}
	// Add the dataObjectArray to the seriesObject
	seriesObject.data = dataObjectArray;
	// Set the seriesObject
	this.dataStream.push(seriesObject);
}

/**
* Parse the json of the actual helioChartObject and push the parsed
* object into the dataStream array of the helioChartObject.
* This is a special version of the parseJSON function only used for histograms.
* It uses the function from the configuration object.
*
* @param seriesName Name of the serie to be mapped against the y-axis
* @param xAxisName Name of the serie to be mapped against the x-axis
* @param xAxisArray Array containing the categories which are mapped against the x-axis
* @param xAxisCategoryFunction The function which returns the category given a y-value
* @param yAxisNumber Number of the y-axis for the dataStream object
*/
function parseJSONhistogram(seriesName,xAxisName,xAxisArray,xAxisCategoryFunction,yAxisNumber){
	// Set the name of the x-axis
	this.xAxisValueName = xAxisName;
	
	// Set the xAxisCategories
	this.xAxisCategories = xAxisArray;
	
	// Get the aaData object containing all data
	var aaDataObject = this.jsonObject.aaData;
	
	// Get the number of entries within the aaDataObject
	var numberOfEntries = aaDataObject.length;
	
	/////////////////////////////
	// Create the yAxis object //
	/////////////////////////////
	
	// Create an object for the y-axes-array
	var yAxisObject = {};
	// Create an object for the label of the y-axis
	var yAxisObjectLabel = {};
	// Create an object for the style of the label of the y-axis
	var yAxisObjectLabelStyle = {};
	// Set the color of the style of the label
	yAxisObjectLabelStyle.color = helioColors[yAxisNumber%9];
	// Set the style of the label
	yAxisObjectLabel.style = yAxisObjectLabelStyle;
	// Set the labels of the axis
	yAxisObject.labels = yAxisObjectLabel;
	// Create an object for the title of the y-axis
	var yAxisObjectTitle = {};
	// Create an object for the style of the title of the y-axis
	var yAxisObjectTitleStyle = {};
	// Set the title of the y-axis
	yAxisObjectTitle.text = seriesName;
	// Set the margin between the titel of the y-axis and the y-axis itself
	yAxisObjectTitle.margin = 60;
	// Set the color of the title
	yAxisObjectTitleStyle.color = helioColors[yAxisNumber%9];
	// Set the style of the title
	yAxisObjectTitle.style = yAxisObjectTitleStyle;
	// Set the title of the axis
	yAxisObject.title = yAxisObjectTitle;
	// Push the object into the array
	this.yAxisArray.push(yAxisObject);
	
	
	//////////////////////////////////
	// Create the dataSeries object //
	//////////////////////////////////
	
	// Create a new series object
	var seriesObject = {};
	// Set the name of the series
	seriesObject.name = xAxisName;
	// Set the number of the y-axis
	seriesObject.yAxis = yAxisNumber;
	// Get the number of x-axis categories
	var numberXAxisCategories = xAxisArray.length;
	// Create a new dataObject for each point and store it in an array
	// First create the new array
	var dataObjectArray = [];
	// Initialize the dataObjectArray to 0
	for(var i=0;i<numberXAxisCategories;i++){
		dataObjectArray[i] = 0;
	}
	// First get the index of the required value within the aaDataObject array
	var yValueIndex = getIndexInAoColumns(this.jsonObject.aoColumns,xAxisName);
	// Loop over every entry in the aaDataObject
	for(var actualIndex=0;actualIndex<numberOfEntries;actualIndex++){
		// Get the category of the actual item
		var categoryItem = xAxisCategoryFunction(aaDataObject[actualIndex][yValueIndex+1]);
		// Loop over every category
		for(var actualCategory=0;actualCategory<numberXAxisCategories;actualCategory++){
			// Check if the category of the actual item is equal to the actual category
			// and if that's true increase the number at the according place within the
			// dataObjectArray by 1
			if(categoryItem == xAxisArray[actualCategory]){
				dataObjectArray[actualCategory] += 1;
			}
		}
	}
	// Add the dataObjectArray to the seriesObject
	seriesObject.data = dataObjectArray;
	// Set the seriesObject
	this.dataStream.push(seriesObject);
}

/**
* Parse the json of the actual helioChartObject and store the parsed
* object in the dataStream field of the helioChartObject.
* This is a special version of the parseJSON function only used for divided scatter charts.
* It uses the given 'dividing data' from the configuration object.
*
* @param yAxisNames Array containing the names which define the division of the y-axis
* @param yAxisDesc Name of the values plotted against the y-axis
* @param xAxisName Name of the values plotted against the x-axis
* @param arrayAddInfo Array containing the names of all additional information
* @param yAxisNumber The number of the according y-axis
*/
function parseJSONdividedScatter(yAxisNames,yAxisDesc,xAxisName,arrayAddInfo,yAxisNumber){
	// Set the name of the x-axis
	this.xAxisValueName = xAxisName;
	
	// Get the aaData object
	var aaDataObject = this.jsonObject.aaData;
	
	// Get the aoColumns object
	var aoColumnsObject = this.jsonObject.aoColumns;
	
	// Push the name of the y-axis into the dividingSeries array
	this.dividingSeries.push(yAxisDesc);
	
	
	/////////////////////////////
	// Create the yAxis object //
	/////////////////////////////
	
	// Create the y-axis object
	var yAxisObject = {}
	// Set the categories of the y-axis
	yAxisObject.categories = yAxisNames;
	// Create the title of the y-axis
	var yAxisObjectTitle = {};
	// Set the text of the title
	yAxisObjectTitle.text = yAxisDesc;
	// Create the style object for the title
	var yAxisObjectTitleStyle = {};
	// Set the color
	yAxisObjectTitleStyle.color = helioColors[yAxisNumber%9];
	// Set the style of the title
	yAxisObjectTitle.style = yAxisObjectTitleStyle;
	// Set the title
	yAxisObject.title = yAxisObjectTitle;
	// Create the labels object of the y-axis
	var yAxisObjectLabels = {};
	// Create the style object for the labels
	var yAxisObjectLabelsStyle = {};
	// Set the color
	yAxisObjectLabelsStyle.color = helioColors[yAxisNumber%9];
	// Set the style of the labels
	yAxisObjectLabels.style = yAxisObjectLabelsStyle;
	// Set the labels
	yAxisObject.labels = yAxisObjectLabels;
	// Set min and max
	yAxisObject.min = -1;
	yAxisObject.max = yAxisNames.length;
	// Disable the first and the last label
	yAxisObject.showFirstLabel = false;
	yAxisObject.showLastLabel = false;
	// Set the yAxisArray
	this.yAxisArray.push(yAxisObject);
	
	
	//////////////////////////////////
	// Create the dataSeries object //
	//////////////////////////////////
	
	// Create a new series object
	var seriesObject = {};
	// Set the name of the series
	seriesObject.name = 'Events';
	// Set the number of the y-axis
	seriesObject.yAxis = yAxisNumber;
	// Create a new dataObject for each point and store it in an array
	var dataObjectArray = [];
	// Get the index of the required x-value within the aaDataObject array
	var xValueIndex = getIndexInAoColumns(aoColumnsObject,xAxisName);
	// Get the index of the according y-value of the actual item
	var yValueIndex = getIndexInAoColumns(aoColumnsObject,yAxisDesc);
	// Loop over the number of entries in this series
	for(var j=0;j<aaDataObject.length;j++) {		
		// Create a new dataObject
		var dataObject = {};
		
		// Get the actual item
		var actualItem = aaDataObject[j];
		
		// Get the new date for the x value
		var xValueDate = parseDateFormat(actualItem[xValueIndex+1]); // Add 1 to the xValueIndex as the first (0) value is not described in the aoColumns object
		// Set the x value
		dataObject.x = xValueDate;
		
		// Set the y value
		var yValue = actualItem[yValueIndex+1]; // Add 1 to the yValueIndex as the first (0) value is not described in the aoColumns object
		// Set the correct y value
		dataObject.y = yAxisNames.indexOf(yValue);
		
		// Set the name of the x value
		dataObject.xName = xAxisName;
		// Set the name of the y value
		dataObject.yName = yAxisDesc;
		// Create the string which describes the additional data
		var additionalDataString = '';
		for(var l=0;l<arrayAddInfo.length;l++){
			// First get the index of the required value within the aaDataObject array
			var addValueIndex = getIndexInAoColumns(aoColumnsObject,arrayAddInfo[l]);
			// Set the name of the actual additional information parameter
			additionalDataString += arrayAddInfo[l];
			additionalDataString += ': ';
			// Set the value of the actual additional information parameter
			// If the index is -1, the value isn't present
			if(addValueIndex == -1){
				additionalDataString += '-'; // Write '-' as the value isn't available
				additionalDataString += '; ';
			} else {
				additionalDataString += aaDataObject[j][addValueIndex+1]; // Add 1 to the addValueIndex as the first (0) value is not described in the aoColumns object
				additionalDataString += '; ';
			}
			// Only 3 different information on one line
			// Therefore add a <br /> after 3 data points
			if((l%3 == 2) && (l < (arrayAddInfo.length - 1))) {
				additionalDataString += '<br />';
			}
		}
		// Delete the last '; '
		additionalDataString = additionalDataString.slice(0,-2);
		// Set additional information
		dataObject.additionalInformation = additionalDataString;
		// Add the object to the array of dataObjects
		dataObjectArray.push(dataObject);
	
	}
	// Add the dataObjectArray to the seriesObject
	seriesObject.data = dataObjectArray;
	
	// Set the seriesObject
	this.dataStream.push(seriesObject);
}

/**
* Get the index of the object which contains the given value inside the aoColumns array
*
* @param aoColumnsObject The aoColumns object
* @param searchedValue The value of which the index is needed
* @return -1 if the searched property couldn't be found, otherwise the index of the searched property
*/
function getIndexInAoColumns(aoColumnsObject,searchedValue){
	// Loop over every object in the aoColumnsObject
	for(var aoVar=0;aoVar<aoColumnsObject.length;aoVar++){
		// Loop over all properties inside the actual object
		if(aoColumnsObject[aoVar].sTitle == searchedValue){
				return aoVar;
		}
	}
	
	// If the property couldn't be found, return -1
	return -1;
}

/**
* Parse a date string of the following format: 2012-07-05T12:00:00
* into a date object.
*
* @param stringToParse The string which is to convert into a date object (format: 2012-07-05T12:00:00)
*/
function parseDateFormat(stringToParse) {
	if((stringToParse == undefined) || (stringToParse == '-')){
		return "-";
	} else {
		// Split the string into a date part and a time part
		var splittedString = stringToParse.split('T');
		var dateString = splittedString[0];
		var timeString = splittedString[1];
		
		// Split the date part
		var splittedDateString = dateString.split('-');
		
		// Split the time part
		var splittedTimeString = timeString.split(':');
		
		// Put the parsed string together
		var dateToReturn = new Date(splittedDateString[0],splittedDateString[1]-1,splittedDateString[2],splittedTimeString[0],splittedTimeString[1],splittedTimeString[2]);

		return dateToReturn;
	}
}

/**
* Compares a given string to an array of strings.
* This array contains all possible names of series.
*
* @param stringToCompare String which is to be searched inside the given array
* @param arrayOfSeriesNames Array containing several strings against which the given string is to be compared
* 
* @return true if the given string is present in the array, false otherwise
*/
function compareSeriesNames(stringToCompare,arrayOfSeriesNames) {
	for(var i=0;i<arrayOfSeriesNames.length;i++){
		// Compare the string
		if(stringToCompare == arrayOfSeriesNames[i]){
			// The string is present in the array, therefore return true
			return true;
		}
	}
	// The string isn't present, therefore return false
	return false;
}

/**
* Returns the points of the given array which are within the given range of
* x-axis- and y-axis-values.
*
* @param array The array from which to take the points (this should be the original JSON object)
* @param lowX The lower bound of the x-axis for the new array
* @param highX The higher bound of the x-axis for the new array
* @param xAxisName Name of the x-axis (used to search the correct index)
* @param lowY The lower bound of the y-axis for the new array
* @param highY The higher bound of the y-axis for the new array
* @param yAxisName Array containing the y-axis objects (used to search the correct index)
* @param dividedArray Array containing the names of all plotted series which divide the y-axis into categories
*/
function pointsInArray(array,lowX,highX,xAxisName,lowY,highY,yAxisName,dividedArray) {
	// Create an array containing the names of all y-axes
	var yAxesNamesArray = [];
	// Fill the array
	for(var j=0;j<yAxisName.length;j++){
		yAxesNamesArray.push(yAxisName[j].title.text);
	}
	// Create the new array which will be returned later
	var newArray = [];
	// Get the index of the value to compare within the objects
	var xIndexOfObject = getIndexInAoColumns(array.aoColumns,xAxisName) + 1;
	// Fill the new array
	for(var i = 0; i<array.aaData.length; i++){ // Loop over the objects regarding the x-axis
		if((parseDateFormat(array.aaData[i][xIndexOfObject]) >= lowX) && (parseDateFormat(array.aaData[i][xIndexOfObject]) <= highX)) { // Check if the date is between the lower and the higher bound
			for(var k=0;k<yAxesNamesArray.length;k++){ // Loop over the objects regarding the y-axis
				// Get the actual y-value name
				var actualYValueName = yAxesNamesArray[k];
				// Get the index of the value
				var yIndexOfObject = getIndexInAoColumns(array.aoColumns,actualYValueName) + 1;
				
				// Check if the actual y-axis value divides the axis into categories
				if(dividedArray.indexOf(actualYValueName) > -1){
					// Calculate the value
					// First correct the min/max values so that they are normal numbers
					var normalizedYMin = Math.ceil(lowY);
					var normalizedYMax = Math.ceil(highY);
					// Create a new array containing the values which are within the calculated bounds
					var arrayValuesInBounds = [];
					// Get the array containing the categories from the heliConfObject
					var categoriesArray = helioConfObject['scatter'][actualYValueName]['values'];
					// Fill the array
					for(var l=normalizedYMin;l<normalizedYMax;l++){
						arrayValuesInBounds.push(categoriesArray[l]);
					}
					// Check if the y-value of the actual item is within the bounds
					if(arrayValuesInBounds.indexOf(array.aaData[i][yIndexOfObject]) > -1){
						// Add the value to the newArray
						newArray.push(array.aaData[i]);
					}
				} else { // The actual y-axis value is a 'normal' numerical value
					// Check if the y-value is within the bounds
					if((array.aaData[i][yIndexOfObject] >= lowY) && (array.aaData[i][yIndexOfObject] <= highY) && (array.aaData[i][yIndexOfObject] != '-') && (array.aaData[i][yIndexOfObject] != undefined)) {
						// Add the value to the newArray
						newArray.push(array.aaData[i]);
					}
				}
			}
		}
	}
	
	// Return the new array
	return newArray;
}

/**
* Returns the data point from within the given array which has the given hec_id.
*
* @param array Array in which the point is searched (this has to be the JSON object containing the aoColumns and the aaData object)
* @param hecId The hec_id to be searched for
*/
function singlePointInArray(array,hecId) {
	// Get the index of the hec_id
	var hecIndex = getIndexInAoColumns(array.aoColumns,'hec_id') + 1; // Add 1 as the aaData starts with an unused field
	// Loop over all the objects
	for(var i=0;i<array.aaData.length;i++){
		// Get the actual item
		var actualItem = array.aaData[i];
		// Check if the x-Value and the y-Value are correct
		if(actualItem[hecIndex] == hecId){
			// Return the item
			return actualItem;
		}
	}
}

/**
* Returns the hec_id from an additionalInformation string.
*
* @param searchString The additionalInformation string to be searched for the hec_id
*/
function getHecId(searchString){
	// Divide the string (dividing separator: ';')
	var splittedString = searchString.split(';');
	// Get the string which contains the hec_id
	var hecString = '';
	for(var i=0;i<splittedString.length;i++){
		if(splittedString[i].indexOf('hec_id') != -1){
			hecString = splittedString[i];
		}
	}
	// Check if the hec_id is present (otherwise return '-')
	if(hecString == ''){
		return '-';
	} else {
		// Get the number
		var splittedHecString = hecString.split(' ');
		var hecNumber = parseInt(splittedHecString[1]);
		return hecNumber;
	}
}

/**
* This function checks if the given series name exists as subobject in the given
* part of the HELIOconfiguration object. If that's true, the series is dividing the
* y-axis and the function returns true.
*
* @param seriesName Name of the series to check
* @param confObject Subobject of the HELIOconfiguration object which is used to test the seriesName
*/
function isDividingSeries(seriesName,confObject) {
	if(confObject[seriesName] != undefined){
		return true;
	} else {
		return false;
	}
}


/**
* Create the chart as scatter chart and display it within the given container.
*
* @param containerName Name of the container the chart will be plotted into
* @param additionalContainerName Name of the container the menu will be plotted into
* @param chartTitle Title of the chart
* @param dataSeriesObject The dataStream of a helioChartObject containing the data to be plotted in the chart
* @param yAxesObject The yAxisArray of a helioChartObject containing the names of all series which will be plotted
*/
function createScatterChart(containerName,additionalContainerName,chartTitle,dataSeriesObject,yAxesObject) {

	// Options for the line chart
	var options = {
		chart: {
			ignoreHiddenSeries: true,		// When a data serie is hidden, the axes are automatically scaled
			renderTo: containerName,		// The container the chart is rendered to
			spacingTop: 20,					// The space between the top edge of the chart and the content
			spacingRight: 20,				// The space between the right edge of the chart and the content
			spacingBottom: 30,				// The space between the bottom edge of the chart and the content
			spacingLeft:20,					// The space between the left edge of the chart and the content
			type: 'scatter',				// The type of the chart
			zoomType: 'xy',					// The type of zoom (which axis can be zoomed)
			events: {						// Event listeners
				selection: function(event) {// Fires when an area of the chart has been selected (through zooming)
					// Variables needed to enable 'Reset zoom' button
					var min, max, ex2;
					if(!chartObject.zoomEnabled){
						if(event.xAxis){ // A selection has been done
						// Do not zoom
						event.preventDefault();
						// Get the selected points
						var selectedPointsArray = pointsInArray(chartObject.jsonObject,event.xAxis[0].min,event.xAxis[0].max,chartObject.xAxisValueName,event.yAxis[0].min,event.yAxis[0].max,chartObject.yAxisArray,chartObject.dividingSeries);
						// Call the callback function and pass the selected points as parameter
						selectedPointsOnChart(selectedPointsArray);
						// Set the min and max of the selected points to be displayed in the menu
						min = Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',event.xAxis[0].min);
						max = Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',event.xAxis[0].max);
						} else {
							min = '-';
							max = '-';
						}
					} else {
						if(event.xAxis){ // Zooming has been done
							// Set the min and max of the new extract which is displayed. This values will be displayed in the menu
							min = Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',event.xAxis[0].min);
							max = Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',event.xAxis[0].max);
						} else {
							min = '-';
							max = '-';
						}
					}
					// Display the min and max in the menu
					var rangeString = '<b>Range:</b><br />Minimum: ';
					rangeString += min;
					rangeString += '<br />Maximum: ';
					rangeString += max;
					document.getElementById('range').innerHTML = rangeString;
				},
				load: function() { // Add a fake button to change between zoom and selection
			        var img = this.renderer.image('images/helio/plot_zoom.jpg',this.chartWidth-80,10,20,20); 
			        img.add() ; 
			        img.css({'cursor':'pointer'});
			        img.attr({'title':'Toggle Select/Zoom: Zoom enabled'});
			        img.attr({zIndex:5});
			        img.on('click', function() {
                        if(chartObject.zoomEnabled){ // Zooming is enabled
                            // Disable zooming
                            chartObject.zoomEnabled = false;
                            this.href.baseVal = "images/helio/plot_select.jpg";
                            $(this).attr({'title':'Toggle Select/Zoom: Select enabled'});
                            // Change text of the according div
                            document.getElementById('zoomSelect').innerHTML = '<b>Selection is enabled</b>';
                        } else { // Selecting is enabled
                            // Disable selecting
                            chartObject.zoomEnabled = true;
                            this.href.baseVal = "images/helio/plot_zoom.jpg";
                            $(this).attr({'title':'Toggle Select/Zoom: Zoom enabled'});
                            // Change text of the according div
                            document.getElementById('zoomSelect').innerHTML = '<b>Zooming is enabled</b>';
                        }
			        });
			     }
			}
		},
		credits: {
			enabled: false					// No "highcharts" tag in the lower right corner of the chart
		},
		title: {
			text: chartTitle				// The title of the chart
		},
		plotOptions: {
			series: {
				turboThreshold: 50000,		// After reaching this number of items, only array values are accepted (no objects) - so this is the maximum amount of points
				cursor: 'pointer',			// Set the pointer to cursor to signal to the user that points can be clicked (cursor changes to hand when hovering a point)
				events: {
					click: function(event){ // Event listener for clicking a data point
						// Get the values of the clicked point
						var pointValues = singlePointInArray(chartObject.jsonObject,getHecId(event.point.additionalInformation));
						dataPointClicked(pointValues); // Callback function with the point as argument
					}
				}
			}
		},
		tooltip: {
			crosshairs: [true,true],		// Crosshairs are displayed towards both axes when hovering a point
			formatter: function() {
				return '<b>' + this.point.xName + ':</b> ' + this.x + '; <b>' + this.point.yName + ':</b> ' + this.y + ';<br /><b>Additional information:</b> ' + this.point.additionalInformation;
			},								// A function to describe the formatting of the tooltip
			shadow: false,					// Disable the shadow of the tooltip for better performance
			snap: 2							// Proximity snap for the graph in pixels
		},
		xAxis: {							// Parameters of the x axis
			type: 'datetime',				// Type of data which is displayed on the x axis
			minRange: 1000					// Set the maximum zoom level to one second
		},
		yAxis: [],							// The different y-axes (title and style)
		series: [],							// The data which is to be displayed (must be an array of objects with a name and data each)
		exporting: {
			enabled: true					// Exporting the chart is enabled
		}
	} // End of options
	
	// Push every data object into the series array
	for(var i=0;i<dataSeriesObject.length;i++){
		options.series.push(dataSeriesObject[i]);
	}
	
	// Push every y-axis object into the yAxis array
	for(var j=0;j<dataSeriesObject.length;j++){
		options.yAxis.push(yAxesObject[j]);
	}

	// Create the chart
	helioChart = new Highcharts.Chart(options);
}


/**
* Create the chart as column chart and display it within the given container.
*
* @param containerName Name of the container the chart will be plotted into
* @param additionalContainerName Name of the container the menu will be plotted into
* @param chartTitle Title of the chart
* @param dataSeriesObject The dataStream of a helioChartObject containing the data to be plotted in the chart
* @param yAxesObject The yAxisArray of a helioChartObject containing the names of all series which will be plotted
*/
function createColumnChart(containerName,additionalContainerName,chartTitle,dataSeriesObject,yAxesObject) {

	// Options for the line chart
	var options = {
		chart: {
			ignoreHiddenSeries: true,		// When a data serie is hidden, the axes are automatically scaled
			renderTo: containerName,		// The container the chart is rendered to
			spacingTop: 20,					// The space between the top edge of the chart and the content
			spacingRight: 20,				// The space between the right edge of the chart and the content
			spacingBottom: 30,				// The space between the bottom edge of the chart and the content
			spacingLeft:20,					// The space between the left edge of the chart and the content
			type: 'column',					// The type of the chart
			zoomType: 'x',					// The type of zoom (which axis can be zoomed)
			events: {						// Event listeners
				selection: function(event) {// Fires when an area of the chart has been selected (through zooming)
					// Variables needed to enable 'Reset zoom' button
					var min, max, ex2;
					if(!chartObject.zoomEnabled){
						if(event.xAxis){ // A selection has been done
						// Do not zoom
						event.preventDefault();
						// Get the selected points
						var selectedPointsArray = pointsInArray(chartObject.jsonObject,event.xAxis[0].min,event.xAxis[0].max,chartObject.xAxisValueName,event.yAxis[0].min,event.yAxis[0].max,chartObject.yAxisArray,chartObject.dividingSeries);
						// Call the callback function and pass the selected points as parameter
						selectedPointsOnChart(selectedPointsArray);
						// Set the min and max of the selected points to be displayed in the menu
						min = Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',event.xAxis[0].min);
						max = Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',event.xAxis[0].max);
						} else {
							min = '-';
							max = '-';
						}
					} else {
						if(event.xAxis){ // Zooming has been done
						// Set the min and max of the new extract which is displayed. This values will be displayed in the menu
							min = Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',event.xAxis[0].min);
							max = Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',event.xAxis[0].max);
						} else {
							min = '-';
							max = '-';
						}
					}
					
					// Create the string in the menu to display the min and the max
					var rangeString = '<b>Range:</b><br />Minimum: ';
					rangeString += min;
					rangeString += '<br />Maximum: ';
					rangeString += max;
					document.getElementById('range').innerHTML = rangeString;
				},
				load: function() { // Add a fake button to change between zoom and selection
			        var img = this.renderer.image('images/helio/plot_zoom.jpg',this.chartWidth-80,10,20,20); 
			        img.add() ; 
			        img.css({'cursor':'pointer'});
			        img.attr({'title':'Toggle Select/Zoom: Zoom enabled'});
			        img.attr({zIndex:5});
			        img.on('click', function() {
                        if(chartObject.zoomEnabled){ // Zooming is enabled
                            // Disable zooming
                            chartObject.zoomEnabled = false;
                            this.href.baseVal = "images/helio/plot_select.jpg";
                            $(this).attr({'title':'Toggle Select/Zoom: Select enabled'});
                            // Change text of the according div
                            document.getElementById('zoomSelect').innerHTML = '<b>Selection is enabled</b>';
                        } else { // Selecting is enabled
                            // Disable selecting
                            chartObject.zoomEnabled = true;
                            this.href.baseVal = "images/helio/plot_zoom.jpg";
                            $(this).attr({'title':'Toggle Select/Zoom: Zoom enabled'});
                            // Change text of the according div
                            document.getElementById('zoomSelect').innerHTML = '<b>Zooming is enabled</b>';
                        }
			        });
			     }
			}
		},
		credits: {
			enabled: false					// No "highcharts" tag in the lower right corner of the chart
		},
		title: {
			text: chartTitle				// The title of the chart
		},
		plotOptions: {
			series: {
				turboThreshold: 50000,		// After reaching this number of items, only array values are accepted (no objects) - so this is the maximum amount of points
				cursor: 'pointer',			// Set the pointer to cursor to signal to the user that points can be clicked (cursor changes to hand when hovering a point)
				events: {
					click: function(event){ // Event listener for clicking a data point
						dataPointClicked(event.point); // Callback function with the point as argument
					}
				}
			}
		},
		tooltip: {
			crosshairs: [true,true],		// Crosshairs are displayed towards both axes when hovering a point
			formatter: function() {
				return '<b>' + this.point.xName + ':</b> ' + this.x + '; <b>' + this.point.yName + ':</b> ' + this.y + ';<br /><b>Additional information:</b> ' + this.point.additionalInformation;
			},								// A function to describe the formatting of the tooltip
			shadow: false,					// Disable the shadow of the tooltip for better performance
			snap: 2							// Proximity snap for the graph in pixels
		},
		xAxis: {							// Parameters of the x axis
			type: 'datetime',				// Type of data which is displayed on the x axis
			minRange: 1000					// Set the maximum zoom level to one second
		},
		yAxis: [],							// The different y-axes (title and style)
		series: [],							// The data which is to be displayed (must be an array of objects with a name and data each)
		exporting: {
			enabled: true					// Exporting the chart is enabled
		}
	} // End of options
	
	// Push every data object into the series array
	for(var i=0;i<dataSeriesObject.length;i++){
		options.series.push(dataSeriesObject[i]);
	}
	
	// Push every y-axis object into the yAxis array
	for(var j=0;j<dataSeriesObject.length;j++){
		options.yAxis.push(yAxesObject[j]);
	}

	// Create the chart
	helioChart = new Highcharts.Chart(options);
}

/**
* Create the chart as histogram and display it within the given container
*
* @param containerName Name of the container the chart will be plotted into
* @param additionalContainerName Name of the container the menu will be plotted into
* @param chartTitle Title of the chart
* @param dataSeriesObject The dataStream of a helioChartObject containing the data to be plotted in the chart
* @param yAxesObject The yAxisArray of a helioChartObject containing the names of all series which will be plotted
* @param xAxisCategories An array containing the categories for the x-axis
* @param xAxisName Name of the values on the x-axis
*/
function createHistogramChart(containerName,additionalContainerName,chartTitle,dataSeriesObject,yAxesObject,xAxisCategories,xAxisName){	
	// Options for the column chart
	var options = {
		chart: {
			ignoreHiddenSeries: true,		// When a data serie is hidden, the axes are automatically scaled
			renderTo: containerName,		// The container the chart is rendered to
			spacingTop: 20,					// The space between the top edge of the chart and the content
			spacingRight: 20,				// The space between the right edge of the chart and the content
			spacingBottom: 30,				// The space between the bottom edge of the chart and the content
			spacingLeft:20,					// The space between the left edge of the chart and the content
			type: 'column'					// The type of the chart
		},
		credits: {
			enabled: false					// No "highcharts" tag in the lower right corner of the chart
		},
		title: {
			text: chartTitle				// The title of the chart
		},
		plotOptions: {
			series: {
				turboThreshold: 50000,		// After reaching this number of items, only array values are accepted (no objects)
				cursor: 'pointer'			// Set the pointer to cursor to signal to the user that points can be clicked (cursor changes to hand when hovering a point)
			}
		},
		tooltip: {
			crosshairs: [false,false],		// Crosshairs are displayed towards both axes when hovering a point
			formatter: function() {
				return '<b>' + this.x + ':</b> ' + this.y;
			},								// A function to describe the formatting of the tooltip
			shadow: false,					// Disable the shadow of the tooltip for better performance
			snap: 2							// Proximity snap for the graph in pixels
		},
		xAxis: {							// Parameters of the x axis
			categories: []
		},
		yAxis: [],							// The different y-axes (title and style)
		series: [{
			data: [],
			name: xAxisName
		}],							// The data which is to be displayed (must be an array of objects with a name and data each)
		exporting: {
			enabled: true					// Exporting the chart is enabled
		}
	} // End of options
	
	// Push the x-axis categories into the categories array
	for(var k=0;k<xAxisCategories.length;k++){
		options.xAxis.categories.push(xAxisCategories[k]);
	}
	
	// Push every data object into the series array
	for(var i=0;i<dataSeriesObject.length;i++){
		// Loop over every item in the data and push it to the series data
		for(var l=0;l<dataSeriesObject[i].data.length;l++){
			options.series[0].data.push(dataSeriesObject[i].data[l]);
		}
	}
	
	// Push every y-axis object into the yAxis array
	for(var j=0;j<yAxesObject.length;j++){
		options.yAxis.push(yAxesObject[j]);
	}

	// Create the chart
	helioChart = new Highcharts.Chart(options);
}


/*
**********************
* Callback functions *
**********************
*/

/**
* This function gets called when a point in the chart is clicked.
*
* @param point The point object which has been clicked (the data from the aaData object)
*/
function dataPointClicked(point){
    
}

/**
* This function gets called when some points are selected.
*
* @param arrayOfPoints An array containing all points (the data from the aaData object) which have been selected
*/
function selectedPointsOnChart(arrayOfPoints){




}