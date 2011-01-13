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

    /**
    *@todo deprecate
    *need to merge into one and change calls on services not using where on their statement
     */
    def queryService(LinkedList<String> minDate,LinkedList<String> maxDate,LinkedList<String> from,String portAddress) {
        log.info("queryService ::" +minDate+" "+maxDate+" "+from+" "+portAddress );

        int maxrecords = 0;
        int startindex = 0;
        service = new HelioQueryServiceService();
        port = service.getHelioQueryServicePort();

        //service behavior only accepts the same number of elements on each of the input lists, normalizing adds elements as buffer
        int numberOfDatePairs = minDate.size();
        int numberOfFromSingles = from.size();
        minDate = normalizeList(numberOfFromSingles,minDate);
        maxDate = normalizeList(numberOfFromSingles,maxDate);
        from = normalizeList(numberOfDatePairs,from);


        BindingProvider bp = (BindingProvider) port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, portAddress);
        VOTABLE result = port.timeQuery(minDate,maxDate, from, maxrecords, startindex);
        ResultVT resvt= new ResultVT(result);

        return resvt;
    }

    //@todo deprecate
    def queryService2(LinkedList<String> minDate,LinkedList<String> maxDate,LinkedList<String> from,String portAddress,String where) {
        log.info("queryService2 ::" +minDate+" "+maxDate+" "+from+" "+portAddress );
        int maxrecords = 0;
        int startindex = 0;
     
        if(minDate.size()==0){
            println "empty list";
            return;
        }
        if(maxDate.size()==0){
            return;
        }
        service = new HelioQueryServiceService();
        port = service.getHelioQueryServicePort();

        int numberOfDatePairs = minDate.size();
        int numberOfFromSingles = from.size();
        if(portAddress.equals(ch.i4ds.helio.frontend.query.PortDirectory.DPAS))minDate = normalizeList(numberOfFromSingles,minDate);
        if(portAddress.equals(ch.i4ds.helio.frontend.query.PortDirectory.DPAS))maxDate = normalizeList(numberOfFromSingles,maxDate);
        if(portAddress.equals(ch.i4ds.helio.frontend.query.PortDirectory.DPAS))from = normalizeList(numberOfDatePairs,from);

    
        BindingProvider bp = (BindingProvider) port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, portAddress);
        VOTABLE result = port.query(minDate,maxDate, from,where,null, maxrecords, startindex);
        ResultVT resvt= new ResultVT(result);
    
        log.info("Result ::"+ resvt.getStringTable());
        println resvt.getStringTable();
        return resvt;
    }

    private LinkedList<String> normalizeList(int max,LinkedList<String> list){
        log.info("normalizeList ::" +max+" "+list);
        LinkedList<String> result = new LinkedList<String>();
        //result.addAll(list);
        for(int i = 0; i< max ;i++){
         result.addAll(list);

        }
        return result;


    }
}