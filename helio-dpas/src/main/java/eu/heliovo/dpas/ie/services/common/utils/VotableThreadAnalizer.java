package eu.heliovo.dpas.ie.services.common.utils;

import java.io.BufferedWriter;

import eu.heliovo.dpas.ie.services.CommonDaoFactory;
import eu.heliovo.dpas.ie.services.common.dao.interfaces.ShortNameQueryDao;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;

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
			 ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		     shortNameDao.generateVOTable(commonTO);			 
		}catch(Exception e){
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
