package eu.heliovo.dpas.ie.services.directory.transfer;

import java.io.Writer;
import java.util.LinkedList;

import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.directory.utils.DPASResultItem;


public class FtpDataTO extends CommonTO{
	
	private String rootString;	
	private String yearPattern;
	private String monthPattern;	
	private String ftpHost;
	private String ftpUser;	
	private String ftpPwd;
	private String ftpDateFormat;
	private String workingDir;
	private LinkedList<DPASResultItem> 	results;
	
	public String getRootString() {
		return rootString;
	}
	public void setRootString(String rootString) {
		this.rootString = rootString;
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
	public LinkedList<DPASResultItem> getResults() {
		return results;
	}
	public void setResults(LinkedList<DPASResultItem> results) {
		this.results = results;
	}
}
