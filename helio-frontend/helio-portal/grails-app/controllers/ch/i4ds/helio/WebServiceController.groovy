package ch.i4ds.helio;
import ch.ResultVT;
import net.ivoa.xml.votable.v1.*;
import ch.i4ds.helio.frontend.parser.*;
import ch.i4ds.helio.frontend.query.*;
import ch.PostXML
import ch.EditFile




class WebServiceController {

    def DataQueryService; //Service that runs in an independent thread to query webservices
    
    def tempQuery = {
        println params;
     
     
        WebServiceQuery query = new WebServiceQuery();
        bindData(query, params); // bind parameters from http request for clean manipulation
        Date minDate = Date.parse("yyyy-MM-dd/HH:mm",query.getMinDate()+"/"+params.minTime);
        Date maxDate = Date.parse("yyyy-MM-dd/HH:mm",query.getMaxDate()+"/"+params.maxTime);
        String extra = params.extra;
        
        
        //EditFile ed = new EditFile();
     
        //ed.edit(maxDate.format("yyyy-MM-dd'T'HH:mm:ss"),minDate.format("yyyy-MM-dd'T'HH:mm:ss"));
        // ed =null;
        def f2= new File('tool.xml')
        f2.delete();
        def f1= new File('tool.xml') << '<?xml version="1.0" encoding="UTF-8"?>\n<tool xmlns="http://www.ivoa.net/xml/CEA/types/v1.1"\nid="ivo://helio_ivo.mssl.ucl.ac.uk/helio_cea/goesplotter" interface="simple">\n<input>\n<parameter id="START_DATE" indirect="false">\n<value>'+minDate.format("yyyy-MM-dd'T'HH:mm:ss")+'</value>\n</parameter>\n <parameter id="END_DATE" indirect="false">\n    <value>'+maxDate.format("yyyy-MM-dd'T'HH:mm:ss")+'</value>\n      </parameter> \n   </input>\n  <output>\n      <parameter id="OUTPUT" indirect="false">\n         <value></value>\n  </parameter>\n   </output>\n</tool>\n'

        PostXML p = new PostXML();
        String image = p.service();
     
        [extra:extra,serviceName:query.getServiceName(),image:image,minDate:query.getMinDate(),maxDate:query.getMaxDate()]


    }

    def queryServ = {
        log.info("params =" + params);

      
        //println params;
        //println session;
        
        
        
        WebServiceQuery query = new WebServiceQuery();
        bindData(query, params); // bind parameters from http request for clean manipulation

        LinkedList<String> maxDateList= new LinkedList<String>(); // initialize lists for webservice request
        LinkedList<String> minDateList= new LinkedList<String>();
        LinkedList<String> extraList = new LinkedList<String>(); // extra list stands for FROM parameter however from can be ambiguous within the gsp
        Date minDate = Date.parse("yyyy-MM-dd/HH:mm",query.getMinDate()+"/"+params.minTime);
        Date maxDate = Date.parse("yyyy-MM-dd/HH:mm",query.getMaxDate()+"/"+params.maxTime);
        String extra = params.extra;
        //String extra = "FROM=helio_hec_r3_goes_xray_flare&WHERE=helio_hec_r3_goes_xray_flare.latitude,19.0"

        maxDateList.add(maxDate.format("yyyy-MM-dd'T'HH:mm:ss"));
        minDateList.add(minDate.format("yyyy-MM-dd'T'HH:mm:ss"));
        extraList.add(extra);

        // Gets the address string for the selected service, however doing it dinamically might have some performance impact.
        String addressPort = PortDirectory.class.getField(query.getServiceName()).get(String);

        //println addressPort
        //println maxDate.format("yyyy-MM-dd'T'HH:mm:ss")
        //println minDate.format("yyyy-MM-dd'T'HH:mm:ss")


        ResultVT result = DataQueryService.queryService(minDateList,maxDateList,extraList,addressPort);
        //println result;
        
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

        println session
        println params
        println "hola";
        println session.result;
        //println session.state.getStep();
        //println "step arriba"




//        new File('files/').mkdir();
   //     new File('files/mytest.xml') << session.state.r1.getStringTable();

        if(session.result !=null){


            response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "attachment;filename=myVoTable.xml")
        response.outputStream << session.result.getStringTable()
        }

    }
}