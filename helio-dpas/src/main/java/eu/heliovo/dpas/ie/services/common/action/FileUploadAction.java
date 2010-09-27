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

	private HttpServletRequest servletRequest;

	public String execute() {
		try {
			//File name tmp file path
			System.out.println(" File name "+ this.userFileFileName+" User File "+this.userFile);
			String hsqlFilePath=InstanceHolders.getInstance().getProperty("hsqldb.database.path")+"/HelioDB/";
			System.out.println(" : Provider access table path : "+userFile+" : Hsql data path : "+hsqlFilePath);
			File fileToCreate = new File(hsqlFilePath, this.userFileFileName);
			//Copying file HSQL database.
			FileUtils.copyFile(this.userFile, fileToCreate);
			//Setting .txt for 'pat' table.
			HsqlDbUtils.getInstance().loadProviderAccessTable(userFileFileName);		
			//Provider access file name
			InstanceHolders.getInstance().setProperty("patFileName",userFileFileName);
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
