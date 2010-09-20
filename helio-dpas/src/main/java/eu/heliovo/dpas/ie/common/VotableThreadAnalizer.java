package eu.heliovo.dpas.ie.common;

import java.util.HashMap;

import eu.heliovo.dpas.ie.common.CommonTO;
import eu.heliovo.dpas.ie.services.cdaweb.dao.interfaces.CdaWebQueryDao;
import eu.heliovo.dpas.ie.services.uoc.dao.interfaces.UocQueryDao;
import eu.heliovo.dpas.ie.services.vso.dao.interfaces.VsoQueryDao;
import eu.heliovo.dpas.ie.services.vso.utils.VsoUtils;

public class VotableThreadAnalizer extends Thread
{	
	private CommonTO commonTO;


	public VotableThreadAnalizer(CommonTO comCriTO){
		commonTO=comCriTO;
		
	}
	@SuppressWarnings("null")
	public void run(){
		  			
			try {		
				 String[] startTime =commonTO.getStartTimes();
			     String[] stopTime =commonTO.getStopTimes();
			     String[] instruments =commonTO.getInstruments();
			     
			    //VOTable header
				 VOTableCreator.writeHeaderOfTables(commonTO);
				 //For loop
		    	 for(int count=0;count<instruments.length;count++){
			    	 commonTO.setInstrument(instruments[count].split("__")[1]);
			    	 commonTO.setDateFrom(startTime[count]);
			    	 commonTO.setDateTo(stopTime[count]);
			    	 commonTO.setWhichProvider(instruments[count].split("__")[0]);
				     //Calling DAO factory to connect PROVIDERS
				     if (DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof VsoQueryDao ){
				    	 commonTO.setVotableDescription("VSO query response");
				    	 VsoQueryDao vsoQueryDao= (VsoQueryDao) DAOFactory.getDAOFactory(commonTO.getWhichProvider());
			         	 vsoQueryDao.query(commonTO);
				     }
				     else if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof UocQueryDao ){
				    	 UocQueryDao uocQueryDao=(UocQueryDao)DAOFactory.getDAOFactory(commonTO.getWhichProvider());
				    	 uocQueryDao.query(commonTO);
				     }else if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof CdaWebQueryDao ){
				    	 commonTO.setVotableDescription("CDAWEB query response");
				    	 CdaWebQueryDao cdaWebQueryDao=(CdaWebQueryDao)DAOFactory.getDAOFactory(commonTO.getWhichProvider());
				    	 cdaWebQueryDao.query(commonTO);
				    	 
				     }else{
				    	 
				     }
		    	 }
		    	//VOTable footer.
				VOTableCreator.writeFooterOfTables(commonTO);
				
			}
			catch (Exception e) {			
				e.printStackTrace();
			}
		
	 }
}
