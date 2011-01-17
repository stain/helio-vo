package eu.heliovo.dpas.ie.services.directory.utils;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import eu.heliovo.dpas.ie.services.directory.transfer.FtpDataTO;

public class FtpUtils {
	static FTPClient client=null;
	LinkedList<DPASResultItem> 	results = null;
	public FtpUtils(String host,String user,String pass) throws IOException
	{
		 client = new FTPClient();
		 client.connect(host);
         client.login(user, pass);
         results = new LinkedList<DPASResultItem>();
	}
	
	public LinkedList<DPASResultItem> returnDPASResultItem()
	{
		return results;
	}
	
	public DPASResultItem getFtpFileDetails(FtpDataTO ftpTO) throws IOException
	{
		DPASResultItem				currDpasResult	=	new DPASResultItem();
		Calendar					currCalendar	=	null;
		System.out.println("------------------->"+ftpTO.getWorkingDir());
		client.changeWorkingDirectory(ftpTO.getWorkingDir());       
        FTPFile[] ftpFiles = client.listFiles();
        for (FTPFile ftpFile : ftpFiles) {
        	if (ftpFile.getType() == FTPFile.FILE_TYPE) {
                System.out.println("FTPFile: " + ftpFile.getName() +  ";"+ftpFile.getTimestamp().getTime()+" : "+ FileUtils.byteCountToDisplaySize(ftpFile.getSize()));
                currDpasResult	=	new DPASResultItem();
    			currCalendar	=	new GregorianCalendar();
    			currCalendar.setTime(ftpFile.getTimestamp().getTime());

    			currDpasResult.urlFITS	=	ftpTO.getRootString()+ftpFile.getName();
    			currDpasResult.measurementStart	=	currCalendar;
    			currDpasResult.fileSize =	 FileUtils.byteCountToDisplaySize(ftpFile.getSize());
    			results.add(currDpasResult);
            }
        }
		
		return null;
		
	}
	 
}
