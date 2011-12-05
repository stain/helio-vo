package eu.heliovo.dpas.ie.services.common.utils;


import eu.heliovo.dpas.ie.services.CommonDaoFactory;
import eu.heliovo.dpas.ie.services.cdaweb.dao.interfaces.CdaWebQueryDao;
import eu.heliovo.dpas.ie.services.common.dao.interfaces.DPASDataProvider;
import eu.heliovo.dpas.ie.services.directory.dao.interfaces.DirQueryDao;
import eu.heliovo.dpas.ie.services.soda.dao.interfaces.SoteriaQueryDao;
import eu.heliovo.dpas.ie.services.hqi.dao.interfaces.HqiQueryDao;
import eu.heliovo.dpas.ie.services.vso.dao.interfaces.VsoQueryDao;

public abstract class DAOFactory {
	
	  public abstract VsoQueryDao getVsoQueryDao();
	  public abstract HqiQueryDao getHqiQueryDao();
	  public abstract CdaWebQueryDao getCdaWebQueryDao();
	  public abstract DirQueryDao getDirQueryDao();
	  
	  public static DPASDataProvider getDAOFactory(
			    String whichFactory) {
				Type type=Type.valueOf(whichFactory);
			    switch (type) {
			      case VSO: 
			          return (VsoQueryDao)CommonDaoFactory.getInstance().getVsoQueryDao();
			      case CDAWEB    : 
			          return (CdaWebQueryDao)CommonDaoFactory.getInstance().getCdaWebQueryDao();      
			      case DIR    :   
			          return (DirQueryDao)CommonDaoFactory.getInstance().getDirQueryDao();
			      case UOC		:
			    	  return (HqiQueryDao)CommonDaoFactory.getInstance().getHqiQueryDao();
			      case HQI		:
			    	  return (HqiQueryDao)CommonDaoFactory.getInstance().getHqiQueryDao();
			      case SODA		:
			    	  return (SoteriaQueryDao)CommonDaoFactory.getInstance().getSoteriaQueryDao();
			      default        : 
			          return null;
			    }
			  }
	  
	  
	  public static DPASDataProvider getDAOFactory(
	    String whichFactory, String providerType) {
		System.out.println("Ain daofactory whichfactory: " + whichFactory + " prov type: " + providerType);
		
		
		Type type= null;
		try {
			type = Type.valueOf(whichFactory);
		} catch (IllegalArgumentException ex) {  
	        //nope
			type = Type.valueOf(providerType);
		}
		System.out.println("in daofactory whichfactory: " + whichFactory + " prov type: " + providerType);
	    switch (type) {
	      case VSO: 
	          return (VsoQueryDao)CommonDaoFactory.getInstance().getVsoQueryDao();
	      case CDAWEB    : 
	          return (CdaWebQueryDao)CommonDaoFactory.getInstance().getCdaWebQueryDao();      
	      case DIR    :   
	          return (DirQueryDao)CommonDaoFactory.getInstance().getDirQueryDao();
	      case UOC		:
	    	  return (HqiQueryDao)CommonDaoFactory.getInstance().getHqiQueryDao();
	      case HQI		:
	    	  return (HqiQueryDao)CommonDaoFactory.getInstance().getHqiQueryDao();
	      case SODA		:
	    	  return (SoteriaQueryDao)CommonDaoFactory.getInstance().getSoteriaQueryDao();
	      default        :
	    	  System.out.println("provtype default" + providerType);
	    	  if(providerType.equals("HQI")) {
	    		  System.out.println("returning hqi provtype default" + providerType);
	    		  return (HqiQueryDao)CommonDaoFactory.getInstance().getHqiQueryDao();
	    	  }
	          return null;
	    }
	  }
	   	  
	  enum Type{
		  VSO,CDAWEB,DIR,UOC,HQI,SODA
		};
}
