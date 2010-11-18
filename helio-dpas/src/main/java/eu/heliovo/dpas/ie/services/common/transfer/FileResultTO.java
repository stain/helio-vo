/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.common.transfer;

import java.io.Serializable;

public class FileResultTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private String jdbcDriverName;
	private String jdbcUrl;
	private String jdbcUser;
	private String jdbcPassword;
	private String randomUUIDString;
	private String Status;
	private String sUrl;
	private String fileInfo;
	private String columnUnits;
	
	public String getJdbcDriverName() {
		return jdbcDriverName;
	}
	public void setJdbcDriverName(String jdbcDriverName) {
		this.jdbcDriverName = jdbcDriverName;
	}
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	public String getJdbcUser() {
		return jdbcUser;
	}
	public void setJdbcUser(String jdbcUser) {
		this.jdbcUser = jdbcUser;
	}
	public String getJdbcPassword() {
		return jdbcPassword;
	}
	public void setJdbcPassword(String jdbcPassword) {
		this.jdbcPassword = jdbcPassword;
	}
	public String getRandomUUIDString() {
		return randomUUIDString;
	}
	public void setRandomUUIDString(String randomUUIDString) {
		this.randomUUIDString = randomUUIDString;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getsUrl() {
		return sUrl;
	}
	public void setsUrl(String sUrl) {
		this.sUrl = sUrl;
	}
	public String getFileInfo() {
		return fileInfo;
	}
	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}
	public String getColumnUnits() {
		return columnUnits;
	}
	public void setColumnUnits(String columnUnits) {
		this.columnUnits = columnUnits;
	}
}
