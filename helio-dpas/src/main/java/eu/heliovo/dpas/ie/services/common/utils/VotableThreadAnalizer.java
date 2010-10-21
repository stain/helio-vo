package eu.heliovo.dpas.ie.services.common.utils;

import java.io.BufferedWriter;
import java.util.HashMap;
import eu.heliovo.dpas.ie.services.cdaweb.dao.interfaces.CdaWebQueryDao;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.common.transfer.ResultTO;
import eu.heliovo.dpas.ie.services.common.utils.HsqlDbUtils;
import eu.heliovo.dpas.ie.services.directory.dao.interfaces.DirQueryDao;
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
		 String[] startTime =commonTO.getStartTimes();
	     String[] stopTime =commonTO.getStopTimes();
	     String[] instruments =commonTO.getInstruments();
	     
		 try {		
			     ResultTO[] resultTo=null;
			    //VOTable header
				 VOTableCreator.writeHeaderOfTables(commonTO);
				 //For loop
				 for(int count=0;count<instruments.length;count++){
		    		 try{
		    		 //getting details from Provider access table
		    		 resultTo=HsqlDbUtils.getInstance().getAccessTableBasedOnInst(instruments[count]);
		    		 if(resultTo!=null && resultTo.length>0 && resultTo[0]!=null){
				    	 commonTO.setHelioInstrument(resultTo[0].getHelioInst());
				    	 System.out.println(" : Helio Instrument : "+resultTo[0].getHelioInst());
				    	 commonTO.setDateFrom(startTime[count]);
				    	 System.out.println(" : Start Date Contraint : "+stopTime[count]);
				    	 commonTO.setDateTo(stopTime[count]);
				    	 System.out.println(" : Stop Date Contraint : "+stopTime[count]);
				    	 commonTO.setWhichProvider(resultTo[0].getProviderName());
				    	 System.out.println(" : Provider Type : "+resultTo[0].getProviderName());
				    	 commonTO.setInstrument(resultTo[0].getInst());
				    	 System.out.println(" : Instrument : "+resultTo[0].getInst());
					     //Calling DAO factory to connect PROVIDERS
					     if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof VsoQueryDao ){
					    	 System.out.println("--->  VSO Provider intiated--->");
					    	 System.out.println(" : VSO Provider Name : "+resultTo[0].getProviderSource());
					    	 commonTO.setVotableDescription("VSO query response"+ resultTo[0].getProviderSource());
					    	 commonTO.setUrl(VsoUtils.getUrl(commonTO.getRequest()));
					    	 commonTO.setProviderSource(resultTo[0].getProviderSource());
					    	 VsoQueryDao vsoQueryDao= (VsoQueryDao) DAOFactory.getDAOFactory(commonTO.getWhichProvider());
				         	 vsoQueryDao.query(commonTO);
					     }else if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof UocQueryDao ){
					    	 commonTO.setVotableDescription("UOC query response");
					    	 System.out.println("--->  UOC Provider intiated--->");
					    	 System.out.println(" : Table name for UOC  : "+resultTo[0].getObsId());
					    	 commonTO.setInstrument(resultTo[0].getObsId());
					    	 UocQueryDao uocQueryDao=(UocQueryDao)DAOFactory.getDAOFactory(commonTO.getWhichProvider());
					    	 uocQueryDao.query(commonTO);
					     }else if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof CdaWebQueryDao ){
					    	 System.out.println("--->  CDAWEB Provider intiated--->");
					    	 System.out.println(" : Mission name for CDAWEB  : "+resultTo[0].getObsId());
					    	 commonTO.setMissionName(resultTo[0].getObsId());
					    	 commonTO.setVotableDescription("CDAWEB query response");
					    	 CdaWebQueryDao cdaWebQueryDao=(CdaWebQueryDao)DAOFactory.getDAOFactory(commonTO.getWhichProvider());
					    	 cdaWebQueryDao.query(commonTO);
					     }else if(DAOFactory.getDAOFactory(commonTO.getWhichProvider()) instanceof DirQueryDao ){
					    	 commonTO.setVotableDescription("Archive query response");
					    	 System.out.println("--->  Directory Provider intiated--->");
					    	 DirQueryDao dirQueryDao=(DirQueryDao)DAOFactory.getDAOFactory(commonTO.getWhichProvider());
					    	 dirQueryDao.query(commonTO);
					     }
		    		 }else{
		    			 //commonTO.setBufferOutput(new BufferedWriter(pw));
				    	 commonTO.setVotableDescription("Error votable response, no data");
				    	 commonTO.setQuerystatus("ERROR");
				    	 commonTO.setQuerydescription("No data avialable for Instrument: "+instruments[count]);
						 VOTableCreator.writeErrorTables(commonTO);
		    		 }
		    		 //catch exception if there is error.
		    		 }catch (Exception e) {
						// TODO: handle exception
		    			System.out.println(" : Exception occured while creating the file :  "+e.getMessage());
	    				commonTO.setVotableDescription("Could not create VOTABLE, exception occured : "+e.getMessage()+" : "+instruments[count]);
	    				commonTO.setQuerystatus("ERROR");
	    				commonTO.setQuerydescription(e.getMessage()+" : "+instruments[count]);
	    				try {
	    					//Sending error messages
	    					VOTableCreator.writeErrorTables(commonTO);
	    				} catch (Exception e1) {
	    					// TODO Auto-generated catch block
	    					e1.printStackTrace();
	    				}
					}
		    	 }
		    	//VOTable footer.
				VOTableCreator.writeFooterOfTables(commonTO);
				 
			}
			catch(Exception e){
				e.printStackTrace();
				System.out.println(" : Exception occured while creating the file :  "+e.getMessage());
				if(instruments!=null && instruments.length==1)
					commonTO.setExceptionStatus("exception");
				commonTO.setBufferOutput(new BufferedWriter(commonTO.getPrintWriter()));
				commonTO.setVotableDescription("Could not create VOTABLE, exception occured : "+e.getMessage());
				commonTO.setQuerystatus("ERROR");
				commonTO.setQuerydescription(e.getMessage());
				try {
					//Sending error messages
					VOTableCreator.writeErrorTables(commonTO);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}	
		
	 }
}
