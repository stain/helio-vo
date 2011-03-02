package eu.heliovo.dpas.ie.services.common.transfer;

import java.io.BufferedWriter;
import java.io.Writer;
import javax.servlet.http.HttpServletRequest;
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
	private HttpServletRequest request;
	private String missionName;
	private String From;
	private String exceptionStatus;
	private String helioInstrument;
	private String providerSource;
	private String contextUrl;
	private String allInstrument;
	private String allDateFrom;
	private String allDateTo;
	private String paraInstrument;
	private String saveto;
	private String longRunningQueryStatus;
	private String dataXml;
	private Writer longRunningPrintWriter;
	private String yearPattern;
	private String monthPattern;	
	private String ftpDateFormat;
	private String workingDir;
	private String ftpPattern;
	private String ftpHost;
	private String ftpUser;	
	private String ftpPwd;
	private String ftpFileName;
	private String ftpDateFileName;
	private String providerType;
	private String longrunningRequestStatus;
	
	
	public String getFtpHost() {
		return ftpHost;
	}

	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}

	public String getFtpUser() {
		return ftpUser;
	}

	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}

	public String getFtpPwd() {
		return ftpPwd;
	}

	public void setFtpPwd(String ftpPwd) {
		this.ftpPwd = ftpPwd;
	}

	public String getFtpFileName() {
		return ftpFileName;
	}

	public void setFtpFileName(String ftpFileName) {
		this.ftpFileName = ftpFileName;
	}

	public String getFtpDateFileName() {
		return ftpDateFileName;
	}

	public void setFtpDateFileName(String ftpDateFileName) {
		this.ftpDateFileName = ftpDateFileName;
	}
	
	
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

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getMissionName() {
		return missionName;
	}

	public void setMissionName(String missionName) {
		this.missionName = missionName;
	}

	public String getFrom() {
		return From;
	}

	public void setFrom(String from) {
		From = from;
	}

	public String getExceptionStatus() {
		return exceptionStatus;
	}

	public void setExceptionStatus(String exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}

	public String getHelioInstrument() {
		return helioInstrument;
	}

	public void setHelioInstrument(String helioInstrument) {
		this.helioInstrument = helioInstrument;
	}

	public String getProviderSource() {
		return providerSource;
	}

	public void setProviderSource(String providerSource) {
		this.providerSource = providerSource;
	}

	public String getContextUrl() {
		return contextUrl;
	}

	public void setContextUrl(String contextUrl) {
		this.contextUrl = contextUrl;
	}

	public String getAllInstrument() {
		return allInstrument;
	}

	public void setAllInstrument(String allInstrument) {
		this.allInstrument = allInstrument;
	}

	public String getAllDateFrom() {
		return allDateFrom;
	}

	public void setAllDateFrom(String allDateFrom) {
		this.allDateFrom = allDateFrom;
	}

	public String getAllDateTo() {
		return allDateTo;
	}

	public void setAllDateTo(String allDateTo) {
		this.allDateTo = allDateTo;
	}

	public String getParaInstrument() {
		return paraInstrument;
	}

	public void setParaInstrument(String paraInstrument) {
		this.paraInstrument = paraInstrument;
	}

	public String getSaveto() {
		return saveto;
	}

	public void setSaveto(String saveto) {
		this.saveto = saveto;
	}

	public String getLongRunningQueryStatus() {
		return longRunningQueryStatus;
	}

	public void setLongRunningQueryStatus(String longRunningQueryStatus) {
		this.longRunningQueryStatus = longRunningQueryStatus;
	}

	public String getDataXml() {
		return dataXml;
	}

	public void setDataXml(String dataXml) {
		this.dataXml = dataXml;
	}

	public Writer getLongRunningPrintWriter() {
		return longRunningPrintWriter;
	}

	public void setLongRunningPrintWriter(Writer longRunningPrintWriter) {
		this.longRunningPrintWriter = longRunningPrintWriter;
	}

	public String getYearPattern() {
		return yearPattern;
	}

	public void setYearPattern(String yearPattern) {
		this.yearPattern = yearPattern;
	}

	public String getMonthPattern() {
		return monthPattern;
	}

	public void setMonthPattern(String monthPattern) {
		this.monthPattern = monthPattern;
	}
	
	public String getFtpDateFormat() {
		return ftpDateFormat;
	}

	public void setFtpDateFormat(String ftpDateFormat) {
		this.ftpDateFormat = ftpDateFormat;
	}

	public String getWorkingDir() {
		return workingDir;
	}

	public void setWorkingDir(String workingDir) {
		this.workingDir = workingDir;
	}

	public String getFtpPattern() {
		return ftpPattern;
	}

	public void setFtpPattern(String ftpPattern) {
		this.ftpPattern = ftpPattern;
	}

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	public String getLongrunningRequestStatus() {
		return longrunningRequestStatus;
	}

	public void setLongrunningRequestStatus(String longrunningRequestStatus) {
		this.longrunningRequestStatus = longrunningRequestStatus;
	}	
}
