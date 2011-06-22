package eu.heliovo.dpas.ie.services.directory.utils;

import eu.heliovo.dpas.ie.services.common.dao.interfaces.DPASDataProvider;
import eu.heliovo.dpas.ie.services.common.transfer.ResultTO;
import eu.heliovo.dpas.ie.services.common.utils.HsqlDbUtils;
import eu.heliovo.dpas.ie.services.directory.provider.FtpProvider;
import eu.heliovo.dpas.ie.services.directory.provider.HttpProvider;
import eu.heliovo.dpas.ie.services.directory.provider.NOBEProvider;
import eu.heliovo.dpas.ie.services.directory.provider.Phoenix2Provider;
import eu.heliovo.dpas.ie.services.directory.provider.ProbaLyraProvider;
import eu.heliovo.dpas.ie.services.directory.provider.RhessiProvider;
import eu.heliovo.dpas.ie.services.directory.provider.SOT_FGProvider;
import eu.heliovo.dpas.ie.services.directory.provider.SOT_SPProvider;
import eu.heliovo.dpas.ie.services.directory.provider.XRTProvider;
import eu.heliovo.dpas.ie.services.directory.transfer.DirDataTO;
import eu.heliovo.dpas.ie.services.directory.transfer.FtpDataTO;
import eu.heliovo.dpas.ie.services.directory.transfer.HttpDataTO;

public abstract class DirInsAnlyFactory {
	 private static FtpDataTO ftpTO=new FtpDataTO();
	 private static HttpDataTO httpTO=new HttpDataTO();
	  public static DPASDataProvider getDirProvider(DirDataTO dirTO) throws Exception {
		DirType type=DirType.valueOf(dirTO.getInstrument());
	    switch (type) {
	      case PHOENIX_2: 
	          return new Phoenix2Provider();
	      case HESSI_GMR    : 
	          return new RhessiProvider();      
	      case HESSI_HXR    :   
	    	  return new RhessiProvider();
	      case HALPHA    :   
	    	  return new RhessiProvider();
	      case SP4D		:
	    	  return new SOT_SPProvider();
	      case FGIV		:
	    	  return new SOT_FGProvider();
	      case XRT		:
	    	  return new XRTProvider();   
	      case LYRA		:
	    	  return new ProbaLyraProvider();
	      case NORH		:
	    	  return new NOBEProvider();
	      case FTP	:
	    	     ResultTO[] resultTo=HsqlDbUtils.getInstance().getFtpAccessTableBasedOnInst(dirTO.getParaInstrument());
				 //
				 if(resultTo!=null && resultTo.length>0 && resultTo[0]!=null){
					 ftpTO.setYearPattern(resultTo[0].getYearPattern());
			  		 ftpTO.setMonthPattern(resultTo[0].getMonthPattern());
			  		 ftpTO.setFtpHost(resultTo[0].getFtpHost());
			  		 ftpTO.setWorkingDir(resultTo[0].getWorkingDir());
			  		 ftpTO.setFtpUser(resultTo[0].getFtpUser());
			  		 ftpTO.setFtpPwd(resultTo[0].getFtpPwd());
			  		 ftpTO.setFtpPattern(resultTo[0].getFtpPattern());
					 ftpTO.setFtpDateFormat(resultTo[0].getFtpDatePattern());
					 ftpTO.setProviderSource(dirTO.getProviderSource());
				 }
				 
	    	return new FtpProvider(ftpTO);
	    	case Halpha	:
	    		 ResultTO[] resTo=HsqlDbUtils.getInstance().getFtpAccessTableBasedOnInst(dirTO.getParaInstrument());
				 //
				 if(resTo!=null && resTo.length>0 && resTo[0]!=null){
					 httpTO.setYearPattern(resTo[0].getYearPattern());
					 httpTO.setMonthPattern(resTo[0].getMonthPattern());
					 httpTO.setHttpHost(resTo[0].getFtpHost());
					 httpTO.setWorkingDir(resTo[0].getWorkingDir());
					 httpTO.setHttpUser(resTo[0].getFtpUser());
					 httpTO.setHttpPwd(resTo[0].getFtpPwd());
					 httpTO.setHttpPattern(resTo[0].getFtpPattern());
					 httpTO.setHttpDateFormat(resTo[0].getFtpDatePattern());
					 httpTO.setProviderSource(dirTO.getProviderSource());
					 httpTO.setEndUrl("/JPEG/");
				 }
				 
	    	return new HttpProvider(httpTO);
	    	case HalphaHASTA	:
	    		 ResultTO[] resHastaTo=HsqlDbUtils.getInstance().getFtpAccessTableBasedOnInst(dirTO.getParaInstrument());
				 //
				 if(resHastaTo!=null && resHastaTo.length>0 && resHastaTo[0]!=null){
					 httpTO.setYearPattern(resHastaTo[0].getYearPattern());
					 httpTO.setMonthPattern(resHastaTo[0].getMonthPattern());
					 httpTO.setHttpHost(resHastaTo[0].getFtpHost());
					 httpTO.setWorkingDir(resHastaTo[0].getWorkingDir());
					 httpTO.setHttpUser(resHastaTo[0].getFtpUser());
					 httpTO.setHttpPwd(resHastaTo[0].getFtpPwd());
					 httpTO.setHttpPattern(resHastaTo[0].getFtpPattern());
					 httpTO.setHttpDateFormat(resHastaTo[0].getFtpDatePattern());
					 httpTO.setProviderSource(dirTO.getProviderSource());
					 httpTO.setEndUrl("");
				 }
				 
	    	return new HttpProvider(httpTO);
	    	case SWAP		:
		    	 ResultTO[] resSwapTo=HsqlDbUtils.getInstance().getFtpAccessTableBasedOnInst(dirTO.getParaInstrument());
				 //
				 if(resSwapTo!=null && resSwapTo.length>0 && resSwapTo[0]!=null){
					 httpTO.setYearPattern(resSwapTo[0].getYearPattern());
					 httpTO.setMonthPattern(resSwapTo[0].getMonthPattern());
					 httpTO.setHttpHost(resSwapTo[0].getFtpHost());
					 httpTO.setWorkingDir(resSwapTo[0].getWorkingDir());
					 httpTO.setHttpUser(resSwapTo[0].getFtpUser());
					 httpTO.setHttpPwd(resSwapTo[0].getFtpPwd());
					 httpTO.setHttpPattern(resSwapTo[0].getFtpPattern());
					 httpTO.setHttpDateFormat(resSwapTo[0].getFtpDatePattern());
					 httpTO.setProviderSource(dirTO.getProviderSource());
					 httpTO.setEndUrl("");
				 }
			//
		    	return new HttpProvider(httpTO);
	      default        : 
	          return null;
	    }
	  }
	
	enum DirType{
		PHOENIX_2,HESSI_GMR,HESSI_HXR,SP4D,FGIV,XRT,NORH,FTP,HALPHA,SWAP,LYRA,Halpha,HalphaHASTA
	};
}
