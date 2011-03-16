package ch.i4ds.helio
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

    
        log.info("queryService :Result:"+ resvt.getStringTable());
        
        return resvt;
    }

    //TODO: need to check if functionality will hold for propagation model usecase
    private ArrayList<String> normalizeList(int max,ArrayList<String> list){
        log.info("normalizeList ::" +max+" "+list);
        ArrayList<String> result = new ArrayList<String>();
        
        for(int i = 0; i< max ;i++){
         result.addAll(list);

        }
        return result;


    }
}