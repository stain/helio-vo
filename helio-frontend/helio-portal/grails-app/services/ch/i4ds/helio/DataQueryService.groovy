package ch.i4ds.helio;
import java.util.logging.Logger;

import eu.heliovo.clientapi.frontend.ResultVT;
import eu.heliovo.clientapi.frontend.SimpleInterface;

class DataQueryService {

    boolean transactional = true

    def serviceMethod() {

    }

    def queryService(ArrayList<String> minDate,ArrayList<String> maxDate,ArrayList<String> from,String portAddress,String where) {
        log.info("queryService ::" +minDate+" "+maxDate+" "+from+" "+portAddress );

        int maxrecords = 0;
        int startindex = 0;
     
        if(minDate.size()==0){
            log.info("queryService :: cant complete, date fields empty" );
            return;
        }
        if(maxDate.size()==0){
            log.info("queryService :: cant complete, date fields empty" );
            return;
        }
         
        ResultVT resvt = SimpleInterface.queryService(minDate,maxDate,from,portAddress,where);
    
        log.info("queryService ::Result:"+ resvt.getStringTable());
        
        return resvt;
    }

	    def queryService(String serviceName, List<String> minDate, List<String> maxDate, List<String> from, String where) {
    	log.info("queryServic  ::" + serviceName + ", " + minDate+", "+maxDate+", "+from+", " + where);
    	
    	int maxrecords = 0;
    	int startindex = 0;
    	
    	if(minDate.size()==0){
    		log.info("queryService :: cant complete, date fields empty" );
    		return;
    	}
    	if(maxDate.size()==0){
    		log.info("queryService :: cant complete, date fields empty" );
    		return;
    	}
    	
    	ResultVT resvt = SimpleInterface.queryService(serviceName, minDate, maxDate, from, where);
    	
    	
    	log.info("queryService :Result:"+ resvt.getStringTable());
    	
    	return resvt;
    }
}