package eu.heliovo.dpas.ie.common;

import java.io.BufferedWriter;
import java.util.HashMap;

import eu.heliovo.dpas.ie.common.CommonTO;
import eu.heliovo.dpas.ie.services.cdaweb.dao.interfaces.CdaWebQueryDao;
import eu.heliovo.dpas.ie.services.common.transfer.ResultTO;
import eu.heliovo.dpas.ie.services.common.utils.HsqlDbUtils;
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
		    		 //getting details from Provider access table
		    		 ResultTO[] resultTo=HsqlDbUtils.getInstance().getAccessTableBasedOnInst(instruments[count]);
		    		 if(resultTo!=null && resultTo.length>0){
				    	 commonTO.setInstrument(resultTo[0].getInst());
				    	 commonTO.setDateFrom(startTime[count]);
				    	 commonTO.setDateTo(stopTime[count]);
				    	 commonTO.setWhichProvider(resultTo[0].getProviderType());
					     //Calling DAO factory to connect PROVIDERS
					     if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof VsoQueryDao ){
					    	 commonTO.setVotableDescription("VSO query response");
					    	 commonTO.setUrl(VsoUtils.getUrl(commonTO.getRequest()));
					    	 VsoQueryDao vsoQueryDao= (VsoQueryDao) DAOFactory.getDAOFactory(commonTO.getWhichProvider());
				         	 vsoQueryDao.query(commonTO);
					     }else if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof UocQueryDao ){
					    	 commonTO.setVotableDescription("UOC query response");
					    	 UocQueryDao uocQueryDao=(UocQueryDao)DAOFactory.getDAOFactory(commonTO.getWhichProvider());
					    	 uocQueryDao.query(commonTO);
					     }else if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof CdaWebQueryDao ){
					    	 commonTO.setVotableDescription("CDAWEB query response");
					    	 CdaWebQueryDao cdaWebQueryDao=(CdaWebQueryDao)DAOFactory.getDAOFactory(commonTO.getWhichProvider());
					    	 cdaWebQueryDao.query(commonTO);
					     }else{
					    	 String []instr = new String[1];
					    	 instr[0]="RHESSI__HESSI_GMR";
					    	 //
					    	 String []starttime = new String[1];
					    	 starttime[0]=commonTO.getDateFrom();
					    	 //
					    	 String []stoptime = new String[1];
					    	 stoptime[0]=commonTO.getDateTo();
					    	 //serviceEngine.executeQuery(pw,instr,starttime,stoptime,false, null, null,true);
					     }
		    		 }else{
		    			 commonTO.setBufferOutput(new BufferedWriter(commonTO.getPrintWriter()));
				    	 commonTO.setVotableDescription("Error votable response, no data");
				    	 commonTO.setQuerystatus("ERROR");
				    	 commonTO.setQuerydescription("No data avialable for Instrument: "+instruments[count]);
						 VOTableCreator.writeErrorTables(commonTO);
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
