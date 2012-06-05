/*
* HELIOcharts - A framework to allow the creation of interactive charts for the HELIO project.
*
* Author: Roman Boutellier
*
*/

/*
* Global variable for the line chart
*/
var helioChart;

/*
* Global variable for the chart object
*/
var chartObject;

/*
* Global variables for different names
*/
var chartContainer,additionalContainer,chartTitle;

/*
* The main function of the framework.
* Parameters:	- containerName: name of the container to put the chart into
				- additionalContainerName: name of the additional container to put information and menus into
				- chartTitleName: title of the chart
				- jsonObject: the json object which contains the data
*/
function createHELIOChart(containerName,additionalContainerName,chartTitleName,jsonObject) {
	// Store the containerName, the additionalContainerName and the chartTitle
	chartContainer = containerName;
	additionalContainer = additionalContainerName;
	chartTitle = chartTitleName;
	
	// Create a helioChartObject
	chartObject = new helioChartObject();
	
	// Set the json into the helioChartObject
	chartObject.setJSON(jsonObject);
	
	// Create the data set within the helioChartObject
	// Pass the default array of series names
	chartObject.parseJSON(helioSeriesNames);
	
	// Create a checkbox menu to select the possible series
	var stringDropDown = '<div id="selector"><form><p>';
	for(var m=0;m<chartObject.jsonObject.aoColumns.length;m++){
		var actual_sTitle = chartObject.jsonObject.aoColumns[m].sTitle;
		if((actual_sTitle != 'time_start') && (actual_sTitle != 'time_peak') && (actual_sTitle != 'time_end') && (actual_sTitle != 'event_detail') && (actual_sTitle != 'comment') && (actual_sTitle != 'loc')){
			stringDropDown += '<input type="checkbox" name="seriesName" value="' + actual_sTitle + '">' + actual_sTitle + '<br />';
		}
	}
	stringDropDown += '</p><p><input type="button" value="Display selected series" onclick="seriesSelectedRedraw(this.form)" /></p></form></div><br /><div id="range"></div>';
	document.getElementById(additionalContainerName).innerHTML = stringDropDown;
	
	// Display the chart within the given container
	createLineChart(chartContainer,additionalContainer,chartTitle,chartObject.dataStream,chartObject.yAxisArray);
}

/*
* This function is called, when the user clicks on the redraw button.
* It creates a new dataStream out of the series the user selected.
* Then the chart is redrawn.
*/
function seriesSelectedRedraw(form) {
	// Create the array to put the selected series names into
	var selectedNames = [];
	// Loop over all checkbox elements and add the checked ones into the array
	for(var i=0;i<form.elements.length;i++) {
		var element = form.elements[i];
		if((element.type == 'checkbox')&&(element.checked)) {
			selectedNames.push(form.elements[i].value);
		}
	}
	// Create the new dataStream
	chartObject.parseJSON(selectedNames);
	
	// Display the new chart
	// Display the chart within the given container
	createLineChart(chartContainer,additionalContainer,chartTitle,chartObject.dataStream,chartObject.yAxisArray);
}

/*
* Constructor for a helioChartsObject
* This object is used to store different needed data as:
*	- the JSON
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

/*
* Function to set the JSON of the helioChartObject
* The parameter jsonVar must be a variable containing the json
*/
function setJSON(jsonVar){
	this.jsonObject = jsonVar;
}

/*
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

/*
* An array containing the names of all possible fields to be used as data series
*/
var helioSeriesNames = [
	'aastar_ave',
	'aastar_max',
	'aastar_sum',
	'beta',
	'delta_v',
	'freq_start',
	'freq_end',
	'mag_ratio',
	'pt_max',
	'pt_max_sheath',
	'b_max',
	'b_max_sheath',
	'v',
	'v_init',
	'v_final',
	'v_20r',
	'v_max',
	'v_max_sheath'
];

/*
* Parse the json of the given helioChartObject and store the parsed
* object in the dataStream field of the helioChartObject
*
* Parameters: - arrayOfSeriesNames: An array containing the names of the series to be displayed (defaults to the array helioSeriesNames)
*/
function parseJSON(arrayOfSeriesNames) {
	// Get the aaData object
	var aaDataObject = this.jsonObject.aaData;
	
	// Get the number of entries this series has
	var lengthOfSeries = aaDataObject.length;
	
	// Get the aoColumns object to determine the structure of the data
	var aoColumnsObject = this.jsonObject.aoColumns;
	
	// Parse the aoColumns object and create three arrays
	// One for time values, one for series names and one for additionalData
	var timesArray = [];
	var seriesNamesArray = [];
	var additionalDataArray = [];
	parseAoColumns(aoColumnsObject, timesArray, seriesNamesArray, additionalDataArray, arrayOfSeriesNames);
	
	// Check how many time values are present
	var pointsPerObject = timesArray.length;
	// Divide the value by 3 as 3 entries have been created per item
	pointsPerObject = pointsPerObject / 3;
	
	// Check how many series are needed
	var numberOfSeries = seriesNamesArray.length;
	// Divide the value by 3 as 3 entries have been created per item
	numberOfSeries = numberOfSeries / 3;


	// Create the correct number of seriesObjects and store them in an array
	var seriesObjectArray = []; // The array containing the seriesObjects
	
	// Empty the yAxisArray
	this.yAxisArray.length = 0;
	
	// Loop over the number of series to create the seriesObjects
	for(var i=0;i<numberOfSeries;i++) {
		// Create a new series object
		var seriesObject = {};
		
		// Set the name of the series
		seriesObject.name = seriesNamesArray[3*i];
		
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
		yAxisObjectTitle.text = seriesNamesArray[3*i];
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
			// Prepare a variable for storing the latest date object so it can be used
			// within an possible null-element
			var latestDate;
			
			// Loop over the number of points per Object (i.e. the different time points)
			for(var k=0;k<pointsPerObject;k++) {
				// Create a new dataObject
				var dataObject = {};
				// Get the new date for the x value
				latestDate = parseDateFormat(aaDataObject[j][timesArray[(3*k)+1]+1]);
				// Set the x value
				dataObject.x = latestDate;
				// Set the y value
				// If there are only events without y value, set the y value to defaultVal
				if(seriesNamesArray[3*i+1] == -1) {
					dataObject.y = i;
				} else { // Set the correct y value
					dataObject.y = aaDataObject[j][seriesNamesArray[3*i+1]+1];
				}
				// Set the name of the x value
				dataObject.xName = timesArray[3*k];
				// Set the name of the y value
				dataObject.yName = seriesNamesArray[3*i];
				// Create the string which describes the additional data
				var additionalDataString = '';
				for(var l=0;l<(additionalDataArray.length/3);l++){
					additionalDataString += additionalDataArray[l*3];
					additionalDataString += ': ';
					additionalDataString += aaDataObject[j][additionalDataArray[l*3+1]+1];
					additionalDataString += '; ';
				}
				// Delete the last '; '
				additionalDataString = additionalDataString.slice(0,-2);
				// Set additional information
				dataObject.additionalInformation = additionalDataString;
				// Add the object to the array of dataObjects
				dataObjectArray.push(dataObject);
			}
		}
		// Add the dataObjectArray to the seriesObject
		seriesObject.data = dataObjectArray;
		
		// Add the seriesObject to the seriesObjectArray
		seriesObjectArray.push(seriesObject);
	}
	// Set the seriesObject
	this.dataStream = seriesObjectArray;
}

/*
* Parse the aoColumns object.
* Time values found are put into the timesArray
* Series names found are put into the seriesNamesArray
* Fields with other values are put into the additionalDataArray
*
* Always three things are put into the array per item:
* 1: The sTitle of the item
* 2: The index of the item within the aoColumns object
* 3: The item itself (which again is an object)
*
*/
function parseAoColumns(aoColumnsObject,timeArray,seriesNameArray,additionalDataArray,arrayOfSeriesNames) {
	// Get the number of items present in the aoColumnsObject
	var numberOfItems = aoColumnsObject.length;
	
	// Iterate over all those items and put them into the correct array
	for(var i=0;i<numberOfItems;i++){
		// Get the actual item
		var actualItem = aoColumnsObject[i];
		// Get the sTitle of the item
		var sTitelActualItem = actualItem.sTitle;
		
		// Compare the sTitle with different given words
		// Check for time objects
		if((sTitelActualItem == 'time_start') || (sTitelActualItem == 'time_peak') || (sTitelActualItem == 'time_end')){
			// Push the sTitle
			timeArray.push(sTitelActualItem);
			// Push the index
			timeArray.push(i);
			// Push the object
			timeArray.push(actualItem);
		} else if(compareSeriesNames(sTitelActualItem,arrayOfSeriesNames)){
			// Check for series
			// Push the sTitle
			seriesNameArray.push(sTitelActualItem);
			// Push the index
			seriesNameArray.push(i);
			// Push the object
			seriesNameArray.push(actualItem);
		} else {
			// The data will be used as additional data (omit event_detail)
			if(sTitelActualItem != 'event_detail') {
				// Push the sTitle
				additionalDataArray.push(sTitelActualItem);
				// Push the index
				additionalDataArray.push(i);
				// Push the object
				additionalDataArray.push(actualItem);
			}
		}
	}
	
	// If there was no series found, add an 'Event' series
	if(seriesNameArray.length == 0) {
		// Push the sTitle
		seriesNameArray.push('Events');
		// Push the index (as there is actually no index, push '-1')
		seriesNameArray.push(-1);
		// Push a placeholder value for the object
		seriesNameArray.push(-1);
	}
}

/*
* Parse a date string of the following format: 2012-07-05T12:00:00
* into a date object
*/
function parseDateFormat(stringToParse) {
	if(stringToParse == undefined){
		return "-";
	} else {
		// Split the string into a date part and a time part
		var splittedString = stringToParse.split('T');
		if (splittedString.length != 2) {
		    return "-";
		}
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

/*
* Compares a given string to an array of strings.
* This array contains all possible names of series.
* return true if the given string is present in the array,
* return false otherwise
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

/*
* Create the chart and display it within the given container and name
* The helioChartObject which contains the data has to be provided too
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
						/*ex2 = this.xAxis[0].getExtremes;
						min = ex2.dataMin;
						max = ex2.dataMax;*/
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
				turboThreshold: 50000		// After reaching this number of items, only array values are accepted (no objects)
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
	}; // End of options
	
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