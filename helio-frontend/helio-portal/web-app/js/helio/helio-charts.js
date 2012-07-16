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
	chartObject.parseJSON(window['helioConfObject'][standardPlotType]['yAxisDefault'],window['helioConfObject'][standardPlotType]['xAxisDefault'],window['helioConfObject'][standardPlotType]['addInfo']);
	
	// Create a checkbox menu to select the possible series
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
			stringDropDown += '<input type="checkbox" name="seriesName" value="' + actual_sTitle + '"><label title="' + actual_sDescription + '">' + actual_sTitle + '</label><br />';
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
			// Add the value to the radiobutton menu
			stringDropDown += '<input type="radio" name="xAxisValue" value="' + actual_xAxisValue + '"><label title="' + actual_sDescription_xAxis + '">' + actual_xAxisValue + '</label><br />';
		}
	}
	
	// Create a radiobutton menu to select the kind of plot (only if more than one type exists)
	if(helioConfObject.plots.length > 1){
		stringDropDown += '<br />Type of plot:<br />';
		for(var o=0;o<helioConfObject.plots.length;o++){
			var actual_plotType = helioConfObject.plots[o];
			stringDropDown += '<input type="radio" name="plotType" value="' + actual_plotType + '">' + actual_plotType + '<br />';
		}
	}
	
	// Add a button to redraw the plot
	stringDropDown += '</p><p><input type="button" value="Display selected series" onclick="seriesSelectedRedraw(this.form)" /></p></form></div><br /><div id="range"></div>';
	
	// Set the menu into the additional container
	document.getElementById(additionalContainerName).innerHTML = stringDropDown;
	
	// Display the chart within the given container
	createLineChart(chartContainer,additionalContainer,chartTitle,chartObject.dataStream,chartObject.yAxisArray);
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
	
	// Create variable for the name of the actual plot type
	var actualPlotType = '';
	
	// Set the standard plot type to true
	if(helioConfObject.standardPlot == 'scatter'){
		drawScatter = true;
		actualPlotType = 'scatter';
	} else if(helioConfObject.standardPlot == 'column'){
		drawColumn = true;
		actualPlotType = 'column'
	}
	
	// Create the array to put the selected series names into
	var selectedNames = [];
	
	// Variable for the x axis value name
	var xAxisValueName = '';
	
	// Loop over all checkbox elements and add the checked ones into the array
	// Also set the boolean for the plot to type and the x axis name
	for(var i=0;i<form.elements.length;i++) {
		var element = form.elements[i];
		if((element.type == 'checkbox')&&(element.checked)&&(element.name == 'seriesName')) {
			selectedNames.push(form.elements[i].value);
		} else if((element.type == 'radio')&&(element.checked)&&(element.name == 'plotType')) {
			// If scatter is selected, set scatter to true and the rest to false
			if(element.value == 'scatter') {
				drawScatter = true;
				drawColumn = false;
				// Set the actual plot type
				actualPlotType = 'scatter';
			} else if(element.value == 'column') { // If column is selected, set column to true and the rest to false
				drawScatter = false;
				drawColumn = true;
				// Set the actual plot type
				actualPlotType = 'column';
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
	chartObject.parseJSON(selectedNames,xAxisValueName,window['helioConfObject'][actualPlotType]['addInfo']);
	
	// Display the chart within the given container
	// If scatter was selected, display the scatter chart
	if(drawScatter){
		createLineChart(chartContainer,additionalContainer,chartTitle,chartObject.dataStream,chartObject.yAxisArray);
	} else if(drawColumn) {
		createColumnChart(chartContainer,additionalContainer,chartTitle,chartObject.dataStream,chartObject.yAxisArray);
	} else {
		alert('Error: No Plot type selected');
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
	
	// The dataStream which is used to create the chart
	this.dataStream = {};
	
	// The dataName is the labeling of the y-axis
	this.dataName = "default series name";
	
	// Title of the chart
	this.chartTitle = "default chart title";
	
	// Array for the y-axes options
	this.yAxisArray = [];
	
	// Add a method to get the JSON
	this.setJSON=setJSON;
	
	// Add a method to parse the JSON
	this.parseJSON=parseJSON;
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
* Parse the json of the given helioChartObject and store the parsed
* object in the dataStream field of the helioChartObject
*
* @param arrayOfSeriesNames Array containing the names of the series to be mapped to the y-axis
* @param xAxisValue The value which is to be mapped to the x-axis
* @param arrayAddInfo Array containig the names of all fields within the additional information part
*/
function parseJSON(arrayOfSeriesNames,xAxisValue,arrayAddInfo) {
	// Get the aaData object
	var aaDataObject = this.jsonObject.aaData;
	
	// Get the number of entries this series has
	var lengthOfSeries = aaDataObject.length;

	// Check how many series are needed
	var numberOfSeries = arrayOfSeriesNames.length;

	// The array containing the seriesObjects
	var seriesObjectArray = [];
	
	// Empty the yAxisArray
	this.yAxisArray.length = 0;
	
	// Loop over the number of series to create the seriesObjects
	for(var i=0;i<numberOfSeries;i++) {
		// Create a new series object
		var seriesObject = {};
		
		// Set the name of the series
		seriesObject.name = arrayOfSeriesNames[i];
		
		// Set the number of the y-axis
		seriesObject.yAxis = i;
		
		// Create an object for the y-axes-array
		var yAxisObject = {};
		// Create an object for the label of the y-axis
		var yAxisObjectLabel = {};
		// Create an object for the style of the label of the y-axis
		var yAxisObjectLabelStyle = {};
		// Set the color of the style of the label
		yAxisObjectLabelStyle.color = helioColors[i%9];
		// Set the style of the label
		yAxisObjectLabel.style = yAxisObjectLabelStyle;
		// Set the labels of the axis
		yAxisObject.labels = yAxisObjectLabel;
		// Create an object for the title of the y-axis
		var yAxisObjectTitle = {};
		// Create an object for the style of the title of the y-axis
		var yAxisObjectTitleStyle = {};
		// Set the title of the y-axis
		yAxisObjectTitle.text = arrayOfSeriesNames[i];
		// Set the color of the title
		yAxisObjectTitleStyle.color = helioColors[i%9];
		// Set the style of the title
		yAxisObjectTitle.style = yAxisObjectTitleStyle;
		// Set the title of the axis
		yAxisObject.title = yAxisObjectTitle;

		// Push the object into the array
		this.yAxisArray.push(yAxisObject);
		
		// Create a new dataObject for each point and store it in an array
		var dataObjectArray = [];
		// Loop over the number of entries in this series
		for(var j=0;j<lengthOfSeries;j++) {		
			
			// Create a new dataObject
			var dataObject = {};
			
			// Get the new date for the x value
			// Therefore first get the index of the required value within the aaDataObject array
			var xValueIndex = getIndexInAoColumns(this.jsonObject.aoColumns,xAxisValue);
			var xValueDate = parseDateFormat(aaDataObject[j][xValueIndex+1]); // Add 1 to the xValueIndex as the first (0) value is not described in the aoColumns object
			// Set the x value
			dataObject.x = xValueDate;
			
			// Set the y value
			// If the y value name is 'Events', set the y value to the number of the series + 1
			if(arrayOfSeriesNames[i] == 'Events') {
				dataObject.y = i + 1;
			} else { // Set the correct y value
				// First get the index of the required value within the aaDataObject array
				var yValueIndex = getIndexInAoColumns(this.jsonObject.aoColumns,arrayOfSeriesNames[i]);
				dataObject.y = aaDataObject[j][yValueIndex+1]; // Add 1 to the yValueIndex as the first (0) value is not described in the aoColumns object
			}
			// Set the name of the x value
			dataObject.xName = xAxisValue;
			// Set the name of the y value
			dataObject.yName = arrayOfSeriesNames[i];
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
				if(l%3 == 2) {
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
		
		// Add the seriesObject to the seriesObjectArray
		seriesObjectArray.push(seriesObject);
	}
	// Set the seriesObject
	this.dataStream = seriesObjectArray;
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
		for(var prop in aoColumnsObject[aoVar]){
			// If the property equals the searched value, return its index
			if(aoColumnsObject[aoVar][prop] == searchedValue){
				return aoVar;
			}
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
	if(stringToParse == undefined){
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
* Create the chart as scatter chart and display it within the given container.
*
* @param containerName Name of the container the chart will be plotted into
* @param additionalContainerName Name of the container the menu will be plotted into
* @param chartTitle Title of the chart
* @param dataSeriesObject The dataStream of a helioChartObject containing the data to be plotted in the chart
* @param yAxesObject The yAxisArray of a helioChartObject containing the names of all series which will be plotted
*/
function createLineChart(containerName,additionalContainerName,chartTitle,dataSeriesObject,yAxesObject) {

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
					// Variables neede to enable 'Reset zoom' button
					var min, max, ex2;
					if(event.xAxis){
						min = Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',event.xAxis[0].min);
						max = Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',event.xAxis[0].max);
					} else {
						min = '-';
						max = '-';
					}
					var rangeString = '<b>Range:</b><br />Minimum: ';
					rangeString += min;
					rangeString += '<br />Maximum: ';
					rangeString += max;
					document.getElementById('range').innerHTML = rangeString;
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
				turboThreshold: 50000,		// After reaching this number of items, only array values are accepted (no objects)
				cursor: 'pointer',			// Set the pointer to cursor to signal to the user that points can be clicked (cursor changes to hand when hovering a point)
				events: {
					click: function(event){ // Event listener for clicking a data point
						alert(event.point.xName + ': ' + event.point.x + ';   ' + event.point.yName + ': ' + event.point.y);
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
					// Variables neede to enable 'Reset zoom' button
					var min, max, ex2;
					if(event.xAxis){
						min = Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',event.xAxis[0].min);
						max = Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',event.xAxis[0].max);
					} else {
						min = '-';
						max = '-';
					}
					var rangeString = '<b>Range:</b><br />Minimum: ';
					rangeString += min;
					rangeString += '<br />Maximum: ';
					rangeString += max;
					document.getElementById('range').innerHTML = rangeString;
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
				turboThreshold: 50000,		// After reaching this number of items, only array values are accepted (no objects)
				cursor: 'pointer',			// Set the pointer to cursor to signal to the user that points can be clicked (cursor changes to hand when hovering a point)
				events: {
					click: function(event){ // Event listener for clicking a data point
						alert(event.point.xName + ': ' + event.point.x + '<br />' + event.point.yName + ': ' + event.point.y);
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
