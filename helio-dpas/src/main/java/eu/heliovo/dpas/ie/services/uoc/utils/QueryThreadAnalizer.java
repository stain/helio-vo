package eu.heliovo.dpas.ie.services.uoc.utils;

import java.io.BufferedWriter;

import uk.ac.starlink.votable.VOTableWriter;
import eu.heliovo.dpas.ie.common.DAOFactory;
import eu.heliovo.dpas.ie.common.VOTableCreator;
import eu.heliovo.dpas.ie.services.uoc.dao.interfaces.UocQueryDao;
import eu.heliovo.dpas.ie.services.uoc.transfer.UocDataTO;
import eu.heliovo.dpas.ie.services.vso.dao.interfaces.VsoQueryDao;
import eu.heliovo.dpas.ie.services.vso.transfer.VsoDataTO;

public class QueryThreadAnalizer extends Thread
{	
	
	private UocDataTO uocTO=null;
	public QueryThreadAnalizer(UocDataTO uocTO){
		uocTO=uocTO;
	}
	public void run(){
		  
		BufferedWriter out =null;
		try{
			UocQueryDao uocQueryDao= (UocQueryDao) DAOFactory.getDAOFactory(uocTO.getWhichProvider());
			uocQueryDao.generateVOTable(uocTO);
		}catch(Exception pe) {			
			System.out.println("  : Exception : "+pe);
		}
	 }
	
}