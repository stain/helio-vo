package eu.heliovo.dpas.ie.services.directory.transfer;


import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;

public class FtpDataTO extends CommonTO{

	private String ftpHost;
	private String ftpUser;	
	private String ftpPwd;
	private String ftpFileName;
	private String ftpDateFileName;
	
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
}
