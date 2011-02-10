package ch.i4ds.helio;
import grails.converters.JSON;
import ch.ResultVT;
import net.ivoa.xml.votable.v1.*;
import ch.i4ds.helio.frontend.parser.*;
import ch.i4ds.helio.frontend.query.*;
import java.text.SimpleDateFormat
import java.text.DateFormat;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource


class PrototypeController {

    def DataQueryService;
    def TableInfoService;


    /** TODO: need to improve this method to get the hash from url or memory and saves it so every call is not opening a file on the system **/
    def asyncGetColumns = {
        log.info("asyncGetColumns =>" +params);

        if(params.extra == null || params.serviceName == null)return;
        
        Hashtable hash;
        if(params.serviceName == "HEC")
        {
            hash = TableInfoService.serviceMethod("files/tableshec.xml");
        }
        else if(params.serviceName == "ICS")
        {
            hash = TableInfoService.serviceMethod("files/tablesics.xml");
        }
        else if(params.serviceName == "ILS")
        {
            hash = TableInfoService.serviceMethod("files/tablesils.xml");
        }
        else if(params.serviceName == "DPAS")
        {
            hash = TableInfoService.getDpasHash();
        }

        def extraList = [params.extra].flatten();

        def resultMap =[:];

        
        for (String table : extraList){
            resultMap[table] = hash.get(table);
        }
        render template:'templates/columns', bean:resultMap, var:'resultMap'

    }
    def asyncUpload ={
        log.info("asyncUpload =>" +params);
        //def f = request.getFile('fileInput')
        //println f.getOriginalFilename();
        //println request.getFile("fileInput").inputStream.text


        JAXBContext context = JAXBContext.newInstance(VOTABLE.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        VOTABLE votable = (VOTABLE) unmarshaller.unmarshal(new StreamSource( new StringReader( request.getFile("fileInput").inputStream.text ) ));
    
        ResultVT result = new ResultVT(votable);

        
            def previousQuery = "uploaded file";
            def responseObject = [result:result,previousQuery:previousQuery ];

        
            render template:'response', bean:responseObject, var:'responseObject'

        
    }
    def asyncQuery ={
        log.info("asyncQuery =>" +params);

       
        if(params.maxDate != null){
            ResultVT  result = search(params);
          
            // TODO: need to fix this argument once the data object is here
            session.serviceq=params.serviceName;
            params.remove("action");
            params.remove("controller");
            params.remove("serviceName");
            if(params.where =="")params.remove("where");
            def previousQuery = params;
            def responseObject = [result:result,previousQuery:previousQuery ];
          
            render template:'response', bean:responseObject, var:'responseObject'
        }
        else {
            //TODO: need to send java stacktrace back
            render "error"
        }
      
    }
    
    def index = {
        redirect(action:"explorer");
    }

    def explorer={
        
        log.info("Explorer =>" +params)
       
       
    }
        
    
    def search = {
        log.info("Search =>" +params);
        ArrayList<String> maxDateList= new ArrayList<String>(); // initialize lists for webservice request
        ArrayList<String> minDateList= new ArrayList<String>();

        if(params.maxDateList.trim() != "" && params.minDateList.trim() != "")
        {
            maxDateList = [params.maxDateList.split(",")].flatten();
            minDateList = [params.minDateList.split(",")].flatten();
      
        }else
        {

            Date minDate = Date.parse("yyyy-MM-dd/HH:mm",params.minDate+"/"+params.minTime);
            Date maxDate = Date.parse("yyyy-MM-dd/HH:mm",params.maxDate+"/"+params.maxTime);
            maxDateList.add(maxDate.format("yyyy-MM-dd'T'HH:mm:ss"));
            minDateList.add(minDate.format("yyyy-MM-dd'T'HH:mm:ss"));
        }
        def extraList = [params.extra].flatten();
        String where ="";

        if(params.where != null)where = params.where;
        String addressPort = PortDirectory.class.getField(params.serviceName).get(String);
        ResultVT result = DataQueryService.queryService(minDateList,maxDateList,extraList,addressPort,where);
        println result;

        
        return result;


    }

    def downloadVOTable = {
        log.info("downloadVOTable =>" + params  + session)
        if(session.result !=null){
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            def name= formatter.format(date);
            name = session.serviceq +"-"+name;
            response.setContentType("application/xml")
            response.setHeader("Content-disposition", "attachment;filename="+name+".xml");
            response.outputStream << session.result.getStringTable()
        }

    }
}
