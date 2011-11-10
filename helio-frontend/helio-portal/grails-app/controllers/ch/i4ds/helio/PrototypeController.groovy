package ch.i4ds.helio;
import ch.i4ds.*;
import java.text.DateFormat
import java.text.SimpleDateFormat

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
//something
import javax.xml.bind.JAXBContext
import javax.xml.bind.Unmarshaller
import javax.xml.transform.stream.StreamSource

import net.ivoa.xml.votable.v1.*
import ch.i4ds.helio.frontend.parser.*
import ch.i4ds.helio.frontend.query.*
import eu.heliovo.clientapi.frontend.*
import eu.heliovo.clientapi.frontend.ResultVT;
import eu.heliovo.clientapi.model.catalog.HelioCatalogDao;
import eu.heliovo.clientapi.model.catalog.impl.DpasDao;
import eu.heliovo.clientapi.model.catalog.impl.HecDao
import eu.heliovo.clientapi.model.catalog.impl.HelioCatalogDaoFactory;
import eu.heliovo.clientapi.model.field.DomainValueDescriptor
import eu.heliovo.clientapi.model.field.HelioField
import eu.heliovo.registryclient.HelioServiceName;

import org.springframework.web.context.request.RequestContextHolder

import eu.heliovo.clientapi.processing.ProcessingResult;
import eu.heliovo.clientapi.processing.context.ContextServiceFactory;
import eu.heliovo.clientapi.processing.context.FlarePlotterService;
import eu.heliovo.clientapi.processing.context.GoesPlotterService;
import eu.heliovo.clientapi.processing.context.SimpleParkerModelService;
import eu.heliovo.registryclient.AccessInterface;
import eu.heliovo.clientapi.linkprovider.impl.MimeTypeConstants;
import eu.heliovo.registryclient.AccessInterface;

import eu.heliovo.shared.props.HelioFileUtil;
import javax.activation.MimeType;
import eu.heliovo.clientapi.linkprovider.*;
import eu.heliovo.clientapi.HelioClient;
import eu.heliovo.clientapi.query.*;
import eu.heliovo.clientapi.query.asyncquery.*;
import eu.heliovo.registryclient.*;

class PrototypeController {

    def DataQueryService;
    
    def ResultVTManagerService;

 
  
   

    

    def asyncUpload ={
        log.info("asyncUpload =>" +params);

        try{
    
            if(request.getFile("fileInput").getOriginalFilename()=="")throw new RuntimeException("A valid xml VO-table file must be selected to continue.");
            if(!request.getFile("fileInput").getOriginalFilename().endsWith(".xml"))throw new RuntimeException("Not a valid xml file.");
            JAXBContext context = JAXBContext.newInstance(VOTABLE.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            VOTABLE votable = (VOTABLE) unmarshaller.unmarshal(new StreamSource( new StringReader( request.getFile("fileInput").inputStream.text ) ));
            def serviceName = 'upload';
            ResultVT result = new ResultVT(votable);
            int resultId= ResultVTManagerService.addResult(result,serviceName);
            def uploadId =request.getFile("fileInput").getOriginalFilename();
            
            def responseObject = [result:result,resultId:resultId,uploadId:uploadId];
            render template:'templates/response', bean:responseObject, var:'responseObject'

        }catch(Exception e){

            println e.printStackTrace();
            def responseObject = [error:e.getMessage() ];
            render template:'templates/response', bean:responseObject, var:'responseObject'
        }

    }
    
   


    def asyncQuery ={
        log.info("asyncQuery =>" +params);
        String sessionId = RequestContextHolder.getRequestAttributes()?.getSessionId()

        if(params.maxDate != null){
            try{
               
                ResultVT  result = search(params);
                String serviceName = params.serviceName;
                
                int resultId= ResultVTManagerService.addResult(result,serviceName);
                def responseObject = [result:result,resultId:resultId ];
                //helioquery.result = result.getStringTable();
                
                render template:'templates/response', bean:responseObject, var:'responseObject'
            }catch(Exception e){
               
                println e.getMessage();
                
                
                def responseObject = [error:e.getMessage() ];
                
                render template:'templates/response', bean:responseObject, var:'responseObject';
            }
        }
        else {
            //TODO: need to send java stacktrace back
        }

    }

    def index = {
        redirect(action:"explorer");
    }

    def deleteSession = {
        log.info("deleteSession =>" +params)
        HelioMemoryBar item = HelioMemoryBar.findByHUID(params.HUID);
        if(item!=null)item.delete();
        def queries = HelioQuery.findAllByHUID(params.HUID);
        for(HelioQuery query : queries){
            query.delete();
        }
        
        render "success"
    }

    def explorer={
        log.info("Explorer =>" +params)
        def helioClient = new HelioClient();
     
        
        //Get sessionId to persist client history
        String sessionId = RequestContextHolder.getRequestAttributes()?.getSessionId()
        // init calalog list for HEC GUI
        
        HelioCatalogDao hecDao = HelioCatalogDaoFactory.getInstance().getHelioCatalogDao(HelioServiceName.HEC);
        HelioField<String> catalogField = hecDao.getCatalogField();
        
        DomainValueDescriptor<String>[] valueDomain = catalogField.getValueDomain();
    	
        // init catalog list for DPAS GUI
        
        HelioCatalogDao dpasDao = HelioCatalogDaoFactory.getInstance().getHelioCatalogDao(HelioServiceName.DPAS);
        if (dpasDao == null) {
            throw new NullPointerException("Unable to find service DPAS");
        }
        HelioField<String> dpasInstrumentsField = dpasDao.getCatalogById('dpas').getFieldById('instrument');
        DomainValueDescriptor<String>[] dpasInstruments = dpasInstrumentsField.getValueDomain();
        

       
         
        
        AsyncQueryService service = (AsyncQueryService)helioClient.getServiceInstance(HelioServiceName.HEC, ServiceCapability.ASYNC_QUERY_SERVICE,null );
        
        HelioQueryResult hecQueryResult = service.query(Arrays.asList("1900-01-01T00:00:00"), Arrays.asList("3000-12-31T00:00:00"),
            Arrays.asList("hec_catalogue"), null, 0, 0, null);

        
        int timeout = 300;
        VOTABLE voTable = hecQueryResult.asVOTable(timeout, TimeUnit.SECONDS);
        
        ResultVT   resvt = new ResultVT(voTable, hecQueryResult.getUserLogs());
    
        //def initParams = [hecCatalogs:valueDomain, dpasInstruments: dpasInstruments, HUID:sessionId];
        def initParams = [hecCatalogs:resvt, dpasInstruments: dpasInstruments, HUID:sessionId];
        render view:'explorer', model:initParams
    }


    def search = {
        log.info("Search =>" +params);
        ArrayList<String> maxDateList= new ArrayList<String>(); // initialize lists for webservice request
        ArrayList<String> minDateList= new ArrayList<String>();

        if(params.maxDate.contains(",")) {
            
            
            ArrayList<String> tempMaxDateList = params.maxDate.split(",");
            ArrayList<String> tempMinDateList = params.minDate.split(",");
            for(int i = 0; i<tempMaxDateList.size();i++){
                Date minDate = Date.parse("yyyy-MM-dd'T'HH:mm:ss",tempMinDateList.get(i));
                Date maxDate = Date.parse("yyyy-MM-dd'T'HH:mm:ss",tempMaxDateList.get(i));
                maxDateList.add(maxDate.format("yyyy-MM-dd'T'HH:mm:ss"));
                minDateList.add(minDate.format("yyyy-MM-dd'T'HH:mm:ss"));
                
            }

        }else{
            Date minDate = Date.parse("yyyy-MM-dd'T'HH:mm:ss",params.minDate);
            Date maxDate = Date.parse("yyyy-MM-dd'T'HH:mm:ss",params.maxDate);
            maxDateList.add(maxDate.format("yyyy-MM-dd'T'HH:mm:ss"));
            minDateList.add(minDate.format("yyyy-MM-dd'T'HH:mm:ss"));
        

            
        }
        ArrayList<String> extraList = new ArrayList<String>();
        if(params.extra.contains(",")){
            extraList = params.extra.split(",")
        }else{
            extraList.push(params.extra);
            
        }
         
        String where ="";
	if(params.where != null)where = params.where;
	HelioServiceName serviceName = HelioServiceName.valueOf(params.serviceName.toUpperCase());
	ResultVT result = DataQueryService.queryService(serviceName.getServiceName(), minDateList, maxDateList, extraList, where);
        return result;

    }

    def downloadVOTable = {
        log.info("downloadVOTable =>" + params);
        ResultVT result = ResultVTManagerService.getResult(Integer.parseInt(params.resultId));
        if(result !=null){
            
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Date date = new Date();
            def name= formatter.format(date);
            name = ResultVTManagerService.getResultServiceReference(Integer.parseInt(params.resultId)) +"-"+name;
            response.setContentType("application/xml")
            response.setHeader("Content-disposition", "attachment;filename="+name+".xml");
            response.outputStream << result.getStringTable()
        }

    }


    def downloadPartialVOTable = {
        log.info("downloadPartialVOTable =>" + params  + session)
        ResultVT result = ResultVTManagerService.getResult(Integer.parseInt(params.resultId));
        if(result !=null){

            String indexes =params.indexes;
            String[] fields = indexes.split(",");
            def rowIndexSelection = [];
            def tableIndexSelection = [];
            for(String field:fields){
                String[] holder = field.split("resultTable");
                int row = Integer.parseInt(holder[0]);
                int table = Integer.parseInt(holder[1]);
                rowIndexSelection.add(table+"."+row);
                if(!tableIndexSelection.contains(table))tableIndexSelection.add(table);
            }
            VOTABLE res = result.getVOTABLE();
            for(int resourceIndex =0;resourceIndex < res.getRESOURCE().size();resourceIndex++)
            {
                RESOURCE resource = res.getRESOURCE().get(resourceIndex);
                if(!tableIndexSelection.contains(resourceIndex)){
                    res.getRESOURCE().set(resourceIndex,null);
                    continue;
                }
                for(int tableIndex =0;tableIndex < resource.getTABLE().size();tableIndex++)
                {
                    TABLE table= resource.getTABLE().get(tableIndex);
                    for(int trIndex=0;trIndex < table.getDATA().getTABLEDATA().getTR().size();trIndex++)
                    {
                        if(!rowIndexSelection.contains(resourceIndex+"."+trIndex)){

                            table.getDATA().getTABLEDATA().getTR().set(trIndex,null);
                        }
                    }
                }
            }
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Date date = new Date();
            def name= formatter.format(date);
            name = ResultVTManagerService.getResultServiceReference(Integer.parseInt(params.resultId)) +"-reduced-"+name;
            
            response.setContentType("application/xml")
            response.setHeader("Content-disposition", "attachment;filename="+name+".xml");
            response.outputStream << result.getStringTable()

        }

    }

    def asyncGetSavedResult = {
        log.info("asyncGetSavedResult =>" + params);
        
        ResultVT result = ResultVTManagerService.getResult(Integer.parseInt(params.resultId));
        def responseObject = [result:result,resultId:params.resultId ];
        //helioquery.result = result.getStringTable();
        //helioquery.save();




        render template:'templates/response', bean:responseObject, var:'responseObject'
        
    }
    def asyncSaveHistoryBar = {
        log.info("asyncSaveHistoryBar =>" + params);

        
        HelioMemoryBar item = HelioMemoryBar.findByHUID(params.HUID);
        
        if(item == null)item = new HelioMemoryBar(hUID:params.HUID,html:params.html);

        item.html = params.html;
        item.save();
        
        for(HelioMemoryBar temp :HelioMemoryBar.list()){
            println temp.hUID;
        }

        render ""
        
    }
    def asyncGetHistoryBar = {
        log.info("asyncGetHistoryBar =>" + params);
        try{

            HelioMemoryBar item = HelioMemoryBar.findByHUID(params.HUID);
            if(item==null)render "";
            render item.html;

        }catch(Exception e){
            render "";

        }
    }

    def asyncGetPreviousTaskState ={
        log.info("asyncGetPreviousTaskState =>" + params);
        
        HelioQuery query = HelioQuery.findByHUIDAndTaskName(params.HUID,params.taskName);

        if(query == null){
            render ""
            //query = new HelioMemoryBar(hUID:params.HUID,taskName:params.taskName,html:params.html);
        }else{
            println query;
            render query.html
        }
   
    }
    def asyncSetPreviousTaskState ={
        log.info("asyncGetPreviousTaskState =>" + params);
        
        HelioQuery query = HelioQuery.findByHUIDAndTaskName(params.HUID,params.taskName);

        if(query == null){
            query = new HelioQuery(hUID:params.HUID,taskName:params.taskName,html:params.html);
            query.save();
        }else {
            query.html = params.html;
            query.save();
        }
        
        render ""

    }

    def asyncQueryContextService = {
        log.info("asyncQueryContextService =>" + params);

        ContextServiceFactory factory = ContextServiceFactory.getInstance();
        Date minDate = Date.parse("yyyy-MM-dd'T'HH:mm:ss",params.minDate);
        Date maxDate = Date.parse("yyyy-MM-dd'T'HH:mm:ss",params.maxDate);
        if(params.type =="fplot"){
            
            FlarePlotterService flarePlotterService = factory.getFlarePlotterService((AccessInterface[])null);
            
            ProcessingResult result = flarePlotterService.flarePlot(minDate);

            URL url = result.asURL(60, TimeUnit.SECONDS);

            println url;
            render url;
        }else if(params.type =="gplot"){
            GoesPlotterService goesPlotterService = factory.getGoesPlotterService((AccessInterface[])null);
            Calendar cal = Calendar.getInstance();
            cal.set(2003, Calendar.JANUARY, 1, 0, 0, 0);
            Date startDate =  cal.getTime();
            cal.set(2003, Calendar.JANUARY, 3, 0, 0, 0);
            Date endDate =  cal.getTime();
            ProcessingResult result = goesPlotterService.goesPlot(minDate, maxDate);

            URL url = result.asURL(60, TimeUnit.SECONDS);
            println url;
            render url;
        }else if(params.type =="pplot"){
            SimpleParkerModelService parkerModelService = factory.getSimpleParkerModelService((AccessInterface[])null);
            
            ProcessingResult result = parkerModelService.parkerModel(minDate);

            URL url = result.asURL(60, TimeUnit.SECONDS);
            render url;
            
        }
    }
    def asyncQueryLinkService = {
        log.info("asyncQueryLinkService =>" + params);

        
        
        ContextServiceFactory factory = ContextServiceFactory.getInstance();
        Date minDate = Date.parse("yyyy-MM-dd'T'HH:mm:ss",params.minDate);
        Date maxDate =null;
        if( params.maxDate != ""){
            maxDate = Date.parse("yyyy-MM-dd'T'HH:mm:ss",params.maxDate);
        }else{
            maxDate = minDate;
        }
        URL url = null;
       

        
        if(params.type =="fplot"){

            FlarePlotterService flarePlotterService = factory.getFlarePlotterService((AccessInterface[])null);

            ProcessingResult result = flarePlotterService.flarePlot(minDate);

            render url = result.asURL(60, TimeUnit.SECONDS);

            
            
        }else if(params.type =="cplot"){
            GoesPlotterService goesPlotterService = factory.getGoesPlotterService((AccessInterface[])null);
           
            ProcessingResult result = goesPlotterService.goesPlot(minDate, maxDate);

            render url = result.asURL(60, TimeUnit.SECONDS);
            
            
        }else if(params.type =="pplot"){
            SimpleParkerModelService parkerModelService = factory.getSimpleParkerModelService((AccessInterface[])null);
          
            ProcessingResult result = parkerModelService.parkerModel(minDate);

            render url = result.asURL(60, TimeUnit.SECONDS);
            

        }else if(params.type =="link"){
            LinkProviderFactory lfactory = LinkProviderFactory.getInstance();
            LinkProviderService[] linkProviders = lfactory.getLinkProviders();

            String result = "";

            for (LinkProviderService provider : linkProviders) {
                
                URL link = provider.getLink(minDate, maxDate);
                if(link == null)continue;
                String title = provider.getTitle(minDate, maxDate);


                result = result +"<tr><td><a target='_blank' href='"+ link+"'>"+title+"</a></td></tr>";
             
            }
            render result;
        }
    }
}