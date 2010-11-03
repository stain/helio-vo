package eu.heliovo.dpas.ie.services.soda.provider;

import eu.heliovo.dpas.ie.services.soda.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.soda.transfer.SoteriaDataTO;
import uk.ac.starlink.table.StarTable;

public class SoteriaProvider
{

	public	void query(SoteriaDataTO soteriaTO) throws DataNotFoundException {
		
		StarTable[] tables=new StarTable[1];
        try{
        	
          
        }catch(Exception e){
        	e.printStackTrace();
        	throw new DataNotFoundException(e.getMessage());
        }

	}
      
}
