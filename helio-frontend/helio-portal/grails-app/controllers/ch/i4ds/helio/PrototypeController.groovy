package ch.i4ds.helio;

import java.text.DateFormat
import java.text.SimpleDateFormat

import javax.xml.bind.JAXBContext
import javax.xml.bind.Unmarshaller
import javax.xml.transform.stream.StreamSource

import net.ivoa.xml.votable.v1.*
import ch.i4ds.helio.frontend.parser.*
import ch.i4ds.helio.frontend.query.*
import eu.heliovo.clientapi.frontend.*
import eu.heliovo.clientapi.model.catalog.impl.DpasStaticCatalogRegistry;
import eu.heliovo.clientapi.model.catalog.impl.HecStaticCatalogRegistry
import eu.heliovo.clientapi.model.field.DomainValueDescriptor
import eu.heliovo.clientapi.model.field.HelioField
import ch.i4ds.*;

class PrototypeController {

    def DataQueryService;
    def TableInfoService;
    def ResultVTManagerService;

    /** TODO: need to improve this method to get the hash from url or memory and saves it so every call is not opening a file on the system **/
    def asyncGetColumns = {
        log.info("asyncGetColumns =>" +params);
        //render "Advanced search temporarily disabled";return;

        if(params.extra == null || params.serviceName == null)
        throw new java.lang.IllegalArgumentException("Parameter 'extra' AND 'serviceName' must be set.");

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

    /**
     * Action to asynchronously get advanced columns of a service.
     * Expects parameter: serviceName=SERVICE_NAME, catalog=CATALOG_NAME.
     */
    def getAdvancedParams = {
        //log.info("getAdvancedParams =>" +params);

        if(params.serviceName == null)
        throw new java.lang.IllegalArgumentException("Parameter 'service' must be set.");
        if(params.catalog == null)
        throw new java.lang.IllegalArgumentException("Parameter 'catalog' must be set.");

        def template;  // name of the template to use
        if(params.serviceName == "ics")	{
            def hash = TableInfoService.serviceMethod("files/tablesics.xml");
            def catalog = hash.get(params.catalog);
            template = "ics_" + params.catalog;
            render template:'templates/' + template, bean:catalog, var:'catalog';
        } else if(params.serviceName == "ils")	{
            def hash = TableInfoService.serviceMethod("files/tablesils.xml");
            def catalog = hash.get(params.catalog);
            template = "ils_" + params.catalog;
            render template:'templates/' + template, bean:catalog, var:'catalog';
        } else {
            throw new java.lang.IllegalArgumentException("Service " + params.serviceName + " is not supported through this method.");
        }
    }
	
    /**
     * Action to asynchronously get the HEC columns.
     * Expects parameter: catalog=CATALOG_NAME.
     */
    def getHecColumns = {
        //log.info("getHecColumns =>" +params);
			
        if(params.catalog == null)
        throw new java.lang.IllegalArgumentException("Parameter 'catalog' must be set.");

        HecStaticCatalogRegistry registry = HecStaticCatalogRegistry.getInstance();
        def catalog = registry.getCatalogById(params.catalog);
                
        if (catalog != null) {
            render template:'templates/columns_extended', bean:catalog, var:'catalog';
        } else {
            render "<p>Unable to load catalog defintion with id '" + params.catalog + "'</p>";
        }
    }

    /**
     * Send a query to the HEC and render the result in an jquery datatables object.
     */
    def asyncHecQuery ={
        log.info("asyncHecQuery =>" +params);

        try {
            // prepare query
                    
            ArrayList<String> startTime= new ArrayList<String>(); // initialize lists for web service request
            ArrayList<String> endTime= new ArrayList<String>();
		
            // use ingested parameter list
            if(params.maxDateList.trim() != "" && params.minDateList.trim() != "") {
                startTime = [params.minDateList.split(",")].flatten();
                endTime = [params.maxDateList.split(",")].flatten();
            } else 	{
                // use user specified date range, if provided
                if(params.maxDate == null) {
                    throw new RuntimeException("Parameter 'maxDate' must be set.");
                }
                if (params.minDate == null) {
                    throw new RuntimeException("Parameter 'minDate' must be set.");
                }
		
                Date minDate = Date.parse("yyyy-MM-dd/HH:mm",params.minDate+"/"+params.minTime);
                Date maxDate = Date.parse("yyyy-MM-dd/HH:mm",params.maxDate+"/"+params.maxTime);
                startTime.add(minDate.format("yyyy-MM-dd'T'HH:mm:ss"));
                endTime.add(maxDate.format("yyyy-MM-dd'T'HH:mm:ss"));
            }
            def extraList = [params.extra].flatten();
            String where ="";
		
            if(params.where != null) {
                where = params.where;
            }
			
            String serviceName = params.serviceName;
			
			
            ResultVT result = DataQueryService.queryService(serviceName, startTime, endTime, extraList, where);
            int resultId= ResultVTManagerService.addResult(result,serviceName);
        
            def responseObject = [result:result,resultId:resultId ];

            render template:'templates/response', bean:responseObject, var:'responseObject'
        }catch(Exception e){
            e.printStackTrace();

                        
        }
    }


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


	if(params.maxDate != null){
            try{
                //throw new RuntimeException("let me see this error.");
                ResultVT  result = search(params);

                String serviceName = params.serviceName;
                int resultId= ResultVTManagerService.addResult(result,serviceName);

                def responseObject = [result:result,resultId:resultId ];
                

                render template:'templates/response', bean:responseObject, var:'responseObject'
            }catch(Exception e){

                println e.printStackTrace();
                
                def responseObject = [error:e.getMessage() ];
                render template:'templates/response', bean:responseObject, var:'responseObject'
            }

	}
	else {
            //TODO: need to send java stacktrace back
            
	}

    }

    def index = {
	redirect(action:"explorer");
    }

    def explorer={
        log.info("Explorer =>" +params)
	
    	// init calalog list for HEC GUI
    	HecStaticCatalogRegistry registry = HecStaticCatalogRegistry.getInstance();
    	HelioField<String> catalogField = registry.getCatalogField();
    	DomainValueDescriptor<String>[] valueDomain = catalogField.getValueDomain();
    	
        // init catalog list for DPAS GUI
        DpasStaticCatalogRegistry dpasRegistry = DpasStaticCatalogRegistry.getInstance();
        HelioField<String> dpasInstrumentsField = dpasRegistry.getCatalogById('dpas').getFieldById('instrument');
        DomainValueDescriptor<String>[] dpasInstruments = dpasInstrumentsField.getValueDomain();
        
        def initParams = [hecCatalogs:valueDomain, dpasInstruments: dpasInstruments];
        
        render view:'explorer', model:initParams
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
	String serviceName = params.serviceName;
	ResultVT result = DataQueryService.queryService(serviceName, minDateList, maxDateList, extraList, where);

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
}
