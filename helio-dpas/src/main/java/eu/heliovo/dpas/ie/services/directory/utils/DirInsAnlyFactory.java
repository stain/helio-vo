package eu.heliovo.dpas.ie.services.directory.utils;

import eu.heliovo.dpas.ie.services.common.dao.interfaces.DPASDataProvider;
import eu.heliovo.dpas.ie.services.directory.provider.FtpProvider;
import eu.heliovo.dpas.ie.services.directory.provider.NOBEProvider;
import eu.heliovo.dpas.ie.services.directory.provider.Phoenix2Provider;
import eu.heliovo.dpas.ie.services.directory.provider.RhessiProvider;
import eu.heliovo.dpas.ie.services.directory.provider.SOT_FGProvider;
import eu.heliovo.dpas.ie.services.directory.provider.SOT_SPProvider;
import eu.heliovo.dpas.ie.services.directory.provider.XRTProvider;
import eu.heliovo.dpas.ie.services.directory.transfer.DirDataTO;
import eu.heliovo.dpas.ie.services.directory.transfer.FtpDataTO;

public abstract class DirInsAnlyFactory {
	 private static FtpDataTO ftpTO=new FtpDataTO();
	  public static DPASDataProvider getDirProvider(DirDataTO dirTO) {
		DirType type=DirType.valueOf(dirTO.getInstrument());
	    switch (type) {
	      case PHOENIX_2: 
	          return new Phoenix2Provider();
	      case HESSI_GMR    : 
	          return new RhessiProvider();      
	      case HESSI_HXR    :   
	    	  return new RhessiProvider();
	      case SP4D		:
	    	  return new SOT_SPProvider();
	      case FGIV		:
	    	  return new SOT_FGProvider();
	      case XRT		:
	    	  return new XRTProvider();
	      case NORH		:
	    	  return new NOBEProvider();
	      case FTP		:
	  		ftpTO.setYearPattern(dirTO.getYearPattern());
	  		ftpTO.setMonthPattern(dirTO.getMonthPattern());
	  		ftpTO.setFtpHost(dirTO.getFtpHost());
	  		ftpTO.setWorkingDir(dirTO.getWorkingDir());
	  		ftpTO.setFtpUser(dirTO.getFtpUser());
	  		ftpTO.setFtpPwd(dirTO.getFtpPwd());
	  		ftpTO.setFtpPattern(dirTO.getFtpPattern());
			ftpTO.setFtpDateFormat(dirTO.getFtpDateFormat());
			//
	    	return new FtpProvider(ftpTO);
	      default        : 
	          return null;
	    }
	  }
	   	  

	enum DirType{
		PHOENIX_2,HESSI_GMR,HESSI_HXR,SP4D,FGIV,XRT,NORH,FTP
	};


}
