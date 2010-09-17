package eu.heliovo.dpas.ie.services.cdaweb.provider;


import eu.heliovo.dpas.ie.services.cdaweb.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.cdaweb.transfer.CdaWebDataTO;
import uk.ac.starlink.table.StarTable;



public class CDAWEBProvider
{

	public	void query(CdaWebDataTO vsoTO) throws DataNotFoundException {
		
		/*
		 * Creating the query parameter for the time in the VSO format		
		*/
		 StarTable[] tables=null;
		
        try{
	        
	        
        }catch(Exception e){
        	throw new DataNotFoundException(" Could not retrieve data: ",e);
        }

	}
      
}
