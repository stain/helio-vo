package eu.heliovo.dpas.ie.common;

import java.io.Writer;

public class CommonTO {

	
	private String[] instruments;
	private String[] startTimes;
	private String[] stopTimes ;
	private boolean partialSorting;
	private String[] dataTypes;
	private int[] dataLevels;
	private Writer printWriter;
	private boolean votable;
	
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
