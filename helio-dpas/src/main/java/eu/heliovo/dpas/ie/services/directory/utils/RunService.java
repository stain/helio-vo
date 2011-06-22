package eu.heliovo.dpas.ie.services.directory.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.directory.transfer.FtpDataTO;



public class RunService implements Runnable {
	private boolean isCompleted=false;
	CommonTO comCriteriaTO=null;
	private FtpUtils  ftpUtils=null;
	private FtpDataTO 			ftpTO 		= null;
	public RunService(FtpDataTO ftpTO,FtpUtils  ftpUtils)
	{
		this.comCriteriaTO = comCriteriaTO;
		this.ftpTO=ftpTO;
		this.ftpUtils=ftpUtils;
	}
	
	public void run()
	{
		try{
			SimpleDateFormat df=null;
			FtpUtils  ftpUtils=new FtpUtils(ftpTO.getFtpHost(),ftpTO.getFtpUser(),ftpTO.getFtpPwd());
			String pvdrSrc=ftpTO.getProviderSource();
			if(pvdrSrc!=null && !pvdrSrc.trim().equals("") && pvdrSrc.trim().equals("haf"))
				df=new SimpleDateFormat(ftpTO.getYearPattern()+ftpTO.getMonthPattern()+"/"+ftpTO.getYearPattern()+ftpTO.getMonthPattern()+"dd");
			else if(pvdrSrc!=null && !pvdrSrc.trim().equals("") && pvdrSrc.trim().equals("haf"))
				df=new SimpleDateFormat(ftpTO.getYearPattern());
			else
				df=new SimpleDateFormat(ftpTO.getYearPattern()+ftpTO.getMonthPattern());
			//
			String workingDir=ftpTO.getWorkingDir();
	    	if(workingDir!=null){
	    		String[] dirArray=workingDir.split("::");
		    	for(int count=0;count<dirArray.length;count++){
		    		System.out.println("---------->"+dirArray[count]);
		    		Iterator<Date> i =null;
		    		if(pvdrSrc!=null && !pvdrSrc.trim().equals("") && pvdrSrc.trim().equals("haf"))
		    			i = new DateIterator(ftpTO.getDateValueFrom(), ftpTO.getDateValueTo(),"d");
		    		else if(pvdrSrc!=null && !pvdrSrc.trim().equals("") && pvdrSrc.trim().equals("goes"))
		    			i = new DateIterator(ftpTO.getDateValueFrom(), ftpTO.getDateValueTo(),"y");
		    		else
		    			i = new DateIterator(ftpTO.getDateValueFrom(), ftpTO.getDateValueTo(),"m");
			    	while(i.hasNext())
			    	{
			    		Date date = i.next();
			    		String formatDate=df.format(date);
			    		System.out.println("------------>"+formatDate);
			    		//
			    		ftpTO.setWorkingDir(dirArray[count]+formatDate);
			    		ftpUtils.getFtpFileDetails(ftpTO);
			    	}
		    	}
	    	}
			isCompleted=true;
		}
		catch(Exception ex)
		{
			System.out.println("Exception ex:"+ex);
			isCompleted=false;
		}
		//if it is completed.
		if(isCompleted){
			try {
			
			} catch (Exception e) {
				
				
			}
		}else{
			try {
				
			} catch (Exception e) {
				//Removing status from instance holder.
				
			}
			
		}
		
	}

	public boolean isCompleted()
	{
		return isCompleted;	
	}
	
}
