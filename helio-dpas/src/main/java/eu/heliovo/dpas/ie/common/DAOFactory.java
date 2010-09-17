package eu.heliovo.dpas.ie.common;

import eu.heliovo.dpas.ie.dataProviders.DPASDataProvider;
import eu.heliovo.dpas.ie.services.CommonDaoFactory;
import eu.heliovo.dpas.ie.services.cdaweb.dao.interfaces.CdaWebQueryDao;
import eu.heliovo.dpas.ie.services.uoc.dao.interfaces.UocQueryDao;
import eu.heliovo.dpas.ie.services.vso.dao.interfaces.VsoQueryDao;

public abstract class DAOFactory {
	
	  public abstract VsoQueryDao getVsoQueryDao();
	  public abstract UocQueryDao getUocQueryDao();
	  public abstract CdaWebQueryDao getCdaWebQueryDao();
	  
	  public static DPASDataProvider getDAOFactory(
	    String whichFactory) {
		Type type=Type.valueOf(whichFactory);
	    switch (type) {
	      case VSO: 
	          return (VsoQueryDao)CommonDaoFactory.getInstance().getVsoQueryDao();
	      case CDAWEB    : 
	          return (CdaWebQueryDao)CommonDaoFactory.getInstance().getCdaWebQueryDao();      
	      case DIR    : 
	          return null;
	      case UOC		:
	    	  return (UocQueryDao)CommonDaoFactory.getInstance().getUocQueryDao();
	      default        : 
	          return null;
	    }
	  }
	   	  
	  enum Type{
		  VSO,CDAWEB,DIR,UOC
		};


}
