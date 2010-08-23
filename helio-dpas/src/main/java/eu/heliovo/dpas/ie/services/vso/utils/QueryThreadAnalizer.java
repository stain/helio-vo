package eu.heliovo.dpas.ie.services.vso.utils;

import java.io.BufferedWriter;

import uk.ac.starlink.votable.VOTableWriter;
import eu.heliovo.dpas.ie.common.DAOFactory;
import eu.heliovo.dpas.ie.common.VOTableCreator;
import eu.heliovo.dpas.ie.services.vso.transfer.VsoDataTO;

public class QueryThreadAnalizer extends Thread
{	
	
	private VsoDataTO vsoDatTO=null;
	public QueryThreadAnalizer(VsoDataTO vsoTO){
		vsoDatTO=vsoTO;
	}
	public void run(){
		  
		BufferedWriter out =null;
		try{
			  DAOFactory daoFactory= DAOFactory.getDAOFactory(vsoDatTO.getWhichProvider());
			  daoFactory.getVsoQueryDao().generateVOTable(vsoDatTO);
		}catch(Exception pe) {			
			System.out.println("  : Exception : "+pe);
		}
	 }
	
}