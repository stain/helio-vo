package ch.i4ds.helio
import net.ivoa.xml.votable.v1.VOTABLE
import net.ivoa.xml.votable.v1.RESOURCE
import net.ivoa.xml.votable.v1.TABLE
import net.ivoa.xml.votable.v1.TABLEDATA
import net.ivoa.xml.votable.v1.TD
import net.ivoa.xml.votable.v1.TR
import net.ivoa.xml.votable.v1.FIELD
import ch.ResultVT
import eu.helio_vo.xml.queryservice.v0.HelioQueryServiceService
import eu.helio_vo.xml.queryservice.v0.HelioQueryService
import javax.xml.ws.BindingProvider


class DataQueryService {

    boolean transactional = true
    HelioQueryServiceService service;
    HelioQueryService port;

    def serviceMethod() {

    }
    def queryService(LinkedList<String> minDate,LinkedList<String> maxDate,LinkedList<String> from,String portAddress) {
        
        int maxrecords = 0;
        int startindex = 0;
        service = new HelioQueryServiceService();
        port = service.getHelioQueryServicePort();
        int numberOfDatePairs = minDate.size();
        int numberOfFromSingles = from.size();
        minDate = normalizeList(numberOfFromSingles,minDate);
        maxDate = normalizeList(numberOfFromSingles,maxDate);
        from = normalizeList(numberOfDatePairs,from);
        println "min date"
        println minDate
        println "max date"
        println maxDate
        println "from"
        println from
        BindingProvider bp = (BindingProvider) port;


        
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, portAddress);
        

        VOTABLE result = port.timeQuery(minDate,maxDate, from, maxrecords, startindex);

        ResultVT resvt= new ResultVT(result);




        return resvt;
    }
    
    private LinkedList<String> normalizeList(int max,LinkedList<String> list){
        LinkedList<String> result = new LinkedList<String>();
        //result.addAll(list);
        for(int i = 0; i< max ;i++){
         result.addAll(list);

        }
        return result;


    }
}