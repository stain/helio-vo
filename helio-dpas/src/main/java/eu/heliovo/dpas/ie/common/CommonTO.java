package eu.heliovo.dpas.ie.common;

import java.io.Writer;
import java.util.Calendar;

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
	private Calendar dateFrom;
	private Calendar dateTo;
	private  String url;
	
	private int maxResults;
	
	public String getInstrument() {
		return instrument;
	}
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	public Calendar getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Calendar dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Calendar getDateTo() {
		return dateTo;
	}
	public void setDateTo(Calendar dateTo) {
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
	
	
	
	
}
