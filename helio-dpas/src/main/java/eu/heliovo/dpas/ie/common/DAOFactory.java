package eu.heliovo.dpas.ie.common;

import eu.heliovo.dpas.ie.services.vso.dao.CommonDaoFactory;
import eu.heliovo.dpas.ie.services.vso.dao.interfaces.VsoQueryDao;

public abstract class DAOFactory {
	
	  public abstract VsoQueryDao getVsoQueryDao();
	
	  public static DAOFactory getDAOFactory(
	    String whichFactory) {
		Type type=Type.valueOf(whichFactory);
	    switch (type) {
	      case VSO: 
	          return CommonDaoFactory.getInstance();
	      case CDAWEB    : 
	          return null;      
	      case DIR    : 
	          return null;
	     
	      default        : 
	          return null;
	    }
	  }
	  
	  
	  enum Type{
		  VSO,CDAWEB,DIR
		};


}
