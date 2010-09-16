package eu.heliovo.dpas.ie.common;

import java.io.BufferedWriter;
import java.io.Writer;
import java.util.Calendar;

import uk.ac.starlink.table.StarTable;

public class CommonTO {

	private String[] instruments;
	private String[] startTimes;
	private String[] stopTimes ;
	private boolean partialSorting;
	private String[] dataTypes;
	private int[] dataLevels;
	private Writer printWriter;
	private boolean votable;
	private String instrument;
	private String dateFrom;
	private String dateTo;
	private  String url;
	private String status;
	private String querystatus;
	private int maxResults;
	private String querydescription;
	private StarTable starTable;
	private BufferedWriter bufferOutput;
	private String votableDescription;
	private String whichProvider; 
	private StarTable[] starTableArray;
	
	public StarTable[] getStarTableArray() {
		return starTableArray;
	}

	public void setStarTableArray(StarTable[] starTableArray) {
		this.starTableArray = starTableArray;
	}

	public StarTable getStarTable() {
		return starTable;
	}

	public void setStarTable(StarTable starTable) {
		this.starTable = starTable;
	}

	public BufferedWriter getBufferOutput() {
		return bufferOutput;
	}

	public void setBufferOutput(BufferedWriter bufferOutput) {
		this.bufferOutput = bufferOutput;
	}
	
	public String getWhichProvider() {
		return whichProvider;
	}

	public void setWhichProvider(String whichProvider) {
		this.whichProvider = whichProvider;
	}

	public String getInstrument() {
		return instrument;
	}
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getMaxResults() {
		return maxResults;
	}
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
	
	
	public String[] getInstruments() {
		return instruments;
	}
	public void setInstruments(String[] instruments) {
		this.instruments = instruments;
	}
	public String[] getStartTimes() {
		return startTimes;
	}
	public void setStartTimes(String[] startTimes) {
		this.startTimes = startTimes;
	}
	public String[] getStopTimes() {
		return stopTimes;
	}
	public void setStopTimes(String[] stopTimes) {
		this.stopTimes = stopTimes;
	}
	public boolean isPartialSorting() {
		return partialSorting;
	}
	public void setPartialSorting(boolean partialSorting) {
		this.partialSorting = partialSorting;
	}
	public String[] getDataTypes() {
		return dataTypes;
	}
	public void setDataTypes(String[] dataTypes) {
		this.dataTypes = dataTypes;
	}
	public int[] getDataLevels() {
		return dataLevels;
	}
	public void setDataLevels(int[] dataLevels) {
		this.dataLevels = dataLevels;
	}
	public Writer getPrintWriter() {
		return printWriter;
	}
	public void setPrintWriter(Writer printWriter) {
		this.printWriter = printWriter;
	}
	public boolean isVotable() {
		return votable;
	}
	public void setVotable(boolean votable) {
		this.votable = votable;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getQuerystatus() {
		return querystatus;
	}

	public void setQuerystatus(String querystatus) {
		this.querystatus = querystatus;
	}

	public String getQuerydescription() {
		return querydescription;
	}

	public void setQuerydescription(String querydescription) {
		this.querydescription = querydescription;
	}

	public String getVotableDescription() {
		return votableDescription;
	}

	public void setVotableDescription(String votableDescription) {
		this.votableDescription = votableDescription;
	}	
}
