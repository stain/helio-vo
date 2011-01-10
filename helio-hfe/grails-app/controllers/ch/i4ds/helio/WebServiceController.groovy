package ch.i4ds.helio;
import ch.ResultVT;
import net.ivoa.xml.votable.v1.*;
import ch.i4ds.helio.frontend.parser.*;
import ch.i4ds.helio.frontend.query.*;
import ch.PostXML
import ch.EditFile




class WebServiceController {

    def DataQueryService; //Service that runs in an independent thread to query webservices
    
    

    def queryServ = {
        
        WebServiceQuery query = new WebServiceQuery();
        bindData(query, params); // bind parameters from http request for clean manipulation

        LinkedList<String> maxDateList= new LinkedList<String>(); // initialize lists for webservice request
        LinkedList<String> minDateList= new LinkedList<String>();
        LinkedList<String> extraList = new LinkedList<String>(); // extra list stands for FROM parameter however from can be ambiguous within the gsp
        Date minDate = Date.parse("MM/dd/yy/HH:mm",query.getMinDate()+"/"+params.minTime);
        Date maxDate = Date.parse("MM/dd/yy/HH:mm",query.getMaxDate()+"/"+params.maxTime);
        String extra = params.extra;

        maxDateList.add(maxDate.format("yyyy-MM-dd'T'HH:mm:ss"));
        minDateList.add(minDate.format("yyyy-MM-dd'T'HH:mm:ss"));
        extraList.add(extra);
        
        String addressPort = PortDirectory.class.getField(query.getServiceName()).get(String);
        ResultVT result = DataQueryService.queryService(minDateList,maxDateList,extraList,addressPort);
        flash.message = "Search Successful with parameters "+query.getMinDate()+" and "+query.getMaxDate()+" for "+result.getStack().size()+" results.";
       
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        flash.message = "Search Successful with parameters "+query.getMinDate()+" and "+query.getMaxDate()+" for "+result.getStack().size()+" results.";
        def queryIndex = 0;
        switch (query.getServiceName()){
            case "HEC":
            queryIndex = 0;
            break;
            case "ILS":
            queryIndex = 1;
            break;
            case "ICS":
            queryIndex = 2;
            break;
            case "DPAS":
            queryIndex = 3;
            break;
            case "CS":
            queryIndex = 4;
            break;
            default:
            break;

        }

        session.result =result;
        [queryIndex:queryIndex,result:result,testInstanceTotal:result.getStack().size(),extra:extra,serviceName:query.getServiceName(),minDate:query.getMinDate(),maxDate:query.getMaxDate()]
    }

    def downloadVOTable = {
        if(session.result !=null){
            response.setContentType("application/octet-stream")
            response.setHeader("Content-disposition", "attachment;filename=myVoTable.xml")
            response.outputStream << session.result.getStringTable()
        }

    }
}