package eu.heliovo.dpas.ie.services.common.action;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import com.opensymphony.xwork2.ActionSupport;

import eu.heliovo.dpas.ie.services.common.utils.HsqlDbUtils;
import eu.heliovo.dpas.ie.services.common.utils.InstanceHolders;

public class FileUploadAction extends ActionSupport implements
		ServletRequestAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File userFile;
	private String userFileContentType;
	private String userFileFileName;
	private boolean statusDisplay;
	private String uploadedFileName;
	private String uploadedFtpFileName;
	private String uploadedStatus;
	
	public boolean isStatusDisplay() {
		return statusDisplay;
	}

	public void setStatusDisplay(boolean statusDisplay) {
		this.statusDisplay = statusDisplay;
	}

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}
	
	public String getUploadedStatus() {
		return uploadedStatus;
	}

	public void setUploadedStatus(String uploadedStatus) {
		this.uploadedStatus = uploadedStatus;
	}
	
	public String getUploadedFtpFileName() {
		return uploadedFtpFileName;
	}

	public void setUploadedFtpFileName(String uploadedFtpFileName) {
		this.uploadedFtpFileName = uploadedFtpFileName;
	}



	private HttpServletRequest servletRequest;

	public String execute() {
		try {
			//File name tmp file path
			System.out.println(" File name "+ this.userFileFileName+" User File "+this.userFile);
			String hsqlFilePath=InstanceHolders.getInstance().getProperty("hsqldb.database.path")+"/HelioDB/";
			System.out.println(" : Provider access table path : "+userFile+" : Hsql data path : "+hsqlFilePath);
			//deleting old file uploaded
			String uploadedFileName=InstanceHolders.getInstance().getProperty("patFileName");
			//if(uploadedFileName!=null && this.userFileFileName!=null && !this.userFileFileName.equals(uploadedFileName)){
				if(uploadedFileName!=null && !uploadedFileName.equals("")){
					if(FileUtils.deleteQuietly(new File(hsqlFilePath+"/"+uploadedFileName)))
						System.out.println("File "+uploadedFileName+ " deleted successfully.");
					else
						System.out.println("File "+uploadedFileName+ " could not be deleted.");
				}
				InstanceHolders.getInstance().setProperty("patFileName",null);
				File fileToCreate = new File(hsqlFilePath, this.userFileFileName);
				//Copying file HSQL database.
				FileUtils.copyFile(this.userFile, fileToCreate);
				String upload=getUploadedStatus();
				System.out.println("--------------------> "+upload);
				//
				if(upload!=null && !upload.trim().equals("")){
					//
					InstanceHolders.getInstance().removeProperty("patFtpFileName");
					//Setting .txt for 'pat' table.
					HsqlDbUtils.getInstance().loadProviderAccessTable(this.userFileFileName,"ftppat");		
					//Provider access file name
					InstanceHolders.getInstance().setProperty("patFtpFileName",this.userFileFileName);
					//
					setUploadedFtpFileName(this.userFileFileName);
				}else{
					//
					InstanceHolders.getInstance().removeProperty("patFileName");
					//Setting .txt for 'pat' table.
					HsqlDbUtils.getInstance().loadProviderAccessTable(this.userFileFileName,"pat");		
					//Provider access file name
					InstanceHolders.getInstance().setProperty("patFileName",this.userFileFileName);
				}
				setStatusDisplay(true);
				setUploadedFileName(this.userFileFileName);
			//}else{
				//addActionError("'"+userFileFileName+"' file is already uploaded, please upload the file with different name.");
				//return INPUT;
			//}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Exception occured while uploading file '"+userFileFileName+"'");

			return INPUT;
		}
		return SUCCESS;
	}

	public File getUserFile() {
		return userFile;
	}

	public void setUserFile(File userFile) {
		this.userFile = userFile;
	}

	public String getUserFileContentType() {
		return userFileContentType;
	}

	public void setUserFileContentType(String userFileContentType) {
		this.userFileContentType = userFileContentType;
	}

	public String getUserFileFileName() {
		return userFileFileName;
	}

	public void setUserFileFileName(String userFileFileName) {
		this.userFileFileName = userFileFileName;
	}

	@Override
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;

	}
}
