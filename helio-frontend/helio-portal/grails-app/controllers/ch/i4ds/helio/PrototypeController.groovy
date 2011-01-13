package ch.i4ds.helio
import ch.ResultVT;
import net.ivoa.xml.votable.v1.*;
import ch.i4ds.helio.frontend.parser.*;
import ch.i4ds.helio.frontend.query.*;

class PrototypeController {

    def DataQueryService;

    def action1 ={
        log.info("action1 =>" +params)
        return 2;
    }
    def index = {

     redirect(action:explorer);

    }

    def explorer={
        log.info("Explorer =>" +params)
        if(params.maxDate != null){
          ResultVT  result = search(params);
          [result:result]
        }
       
    }
        
    
    def search = {
        log.info("Search =>" +params)
        Date minDate = Date.parse("yyyy-MM-dd/HH:mm",params.minDate+"/"+params.minTime);
        Date maxDate = Date.parse("yyyy-MM-dd/HH:mm",params.maxDate+"/"+params.maxTime);
        String extra = params.extra;
        String where = "";
        
        LinkedList<String> maxDateList= new LinkedList<String>(); // initialize lists for webservice request
        LinkedList<String> minDateList= new LinkedList<String>();
        LinkedList<String> extraList = new LinkedList<String>(); // extra list stands for FROM parameter however from can be ambiguous within the gsp

        maxDateList.add(maxDate.format("yyyy-MM-dd'T'HH:mm:ss"));
        minDateList.add(minDate.format("yyyy-MM-dd'T'HH:mm:ss"));
        extraList.add(extra);
        
        String addressPort = PortDirectory.class.getField(params.serviceName).get(String);
        ResultVT result = DataQueryService.queryService2(minDateList,maxDateList,extraList,addressPort,where);
        println result;

//        redirect(action:index);
        return result;


    }
}
