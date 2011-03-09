package ch.i4ds.helio
import net.ivoa.xml.votable.v1.VOTABLE
import net.ivoa.xml.votable.v1.RESOURCE
import net.ivoa.xml.votable.v1.TABLE
import net.ivoa.xml.votable.v1.TABLEDATA
import net.ivoa.xml.votable.v1.TD
import net.ivoa.xml.votable.v1.TR
import net.ivoa.xml.votable.v1.FIELD
//import ch.ResultVT
import eu.helio_vo.xml.queryservice.v0.HelioQueryServiceService
import eu.helio_vo.xml.queryservice.v0.HelioQueryService
import javax.xml.ws.BindingProvider;
import eu.heliovo.clientapi.frontend.*;
import eu.heliovo.clientapi.frontend.SimpleInterface;
import eu.heliovo.clientapi.frontend.ResultVT;
import eu.heliovo.clientapi.frontend.*;

class DataQueryService {

    boolean transactional = true
    HelioQueryServiceService service;
    HelioQueryService port;
    

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
        //service = new HelioQueryServiceService();
       // port = service.getHelioQueryServicePort();

        int numberOfDatePairs = minDate.size();
        int numberOfFromSingles = from.size();

        if(portAddress.equals(PortDirectory.DPAS))
        minDate = normalizeList(numberOfFromSingles,minDate);
        if(portAddress.equals(PortDirectory.DPAS))
        maxDate = normalizeList(numberOfFromSingles,maxDate);
        if(portAddress.equals(PortDirectory.DPAS))
        from = normalizeList(numberOfDatePairs,from);

    
        //BindingProvider bp = (BindingProvider) port;
        //bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, portAddress);
        //ResultVT resvt = SimpleInterface.queryService(minDate,maxDate, from,where,null, maxrecords, startindex);
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