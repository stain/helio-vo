package eu.heliovo.dpas.ie.services.vso.provider;

import uk.ac.starlink.table.StarTable;
import eu.heliovo.dpas.ie.services.common.utils.DAOFactory;
import eu.heliovo.dpas.ie.services.vso.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.vso.dao.interfaces.VsoQueryDao;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.ProviderQueryResponse;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.QueryRequest;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.QueryRequestBlock;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.Time;
import eu.heliovo.dpas.ie.services.vso.transfer.VsoDataTO;
import eu.heliovo.dpas.ie.services.vso.utils.PointsStarTable;
import eu.heliovo.dpas.ie.services.vso.utils.VsoUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.QueryResponse;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.QueryResponseBlock;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.QueryResponseBlockArray;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.VSOiPort;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.vsoi.v0_6.VSOiService;


public class VSOProvider
{
	public void query(VsoDataTO vsoTO) throws DataNotFoundException {
		System.out.println("vsoto detectfield  = " + vsoTO.getDetectiveField());
	   StarTable[] tables=null;
	   try{
	   VSOiService service   = new VSOiService();
       VSOiPort    port      = service.getNsoVSOi();
       QueryRequest request = new QueryRequest();
       request.setVersion(new Float("0.6").floatValue());
       QueryRequestBlock block = new QueryRequestBlock();
       //Set Time object
       // This example finds the last record available
       // by using the "near time" feature
       Time timeParam = new Time();
       timeParam.setStart(VsoUtils.getDateFormat(vsoTO.getDateFrom()));
       timeParam.setEnd(VsoUtils.getDateFormat(vsoTO.getDateTo()));
       
       block.setTime(timeParam );
       //Set Provider
       block.setSource(vsoTO.getProviderSource());
       block.setInstrument(vsoTO.getInstrument());
       if(vsoTO.getDetectiveField() != null) {
    	   //System.out.println("Using detector field: " + vsoTO.getDetectiveField().replace('|',','));
    	   block.setDetector(vsoTO.getDetectiveField());
       }
       
       //If Field information is set the provider
       // might sent extra information. 
       // Such as thumnail urls
       // Detector information if applicable etc.
       // In the JSOC case is irrelevant as there are
       // no thumnails available right now.
       
       //Finishing setting up the Query message
       request.setBlock(block );
           
       //Finally perform the request.
       QueryResponse response = port.query(request);
       
       //Request is sent and received.
       //The request is type array.
       List<ProviderQueryResponse> qReturn = response.getProvideritem();
       
       Map<String,List<String>> getDataMap = new HashMap<String,List<String>>();  //Hash map where fileids are kept
                                                                                  //Later on we'll use them to do the
                                                                                  //GetData Request
       int count=0;
       
      
       tables=new StarTable[qReturn.size()];
       //Go over the returned array
       for (ProviderQueryResponse qr:qReturn) {
    	   /*
           logger.info("Provider:" + qr.getProvider());
           
           if (qr.getStatus() != null)
               System.out.println("status:" + qr.getStatus());
           logger.info("Number of records found:" + qr.getNoOfRecordsFound());
           logger.info("Number of records returned:" + qr.getNoOfRecordsReturned());
           */
    	   
           QueryResponseBlockArray blk= qr.getRecord();

           if (blk == null) {
               System.err.println("Record block is empty.");
               continue;
           }
           //The  QueryResponseBlock contains an array of the provider records.
           List<QueryResponseBlock> resp = blk.getRecorditem();
           
           if (resp != null && !resp.isEmpty() &&  resp.size()>0) {
               
        	   tables[count]=new PointsStarTable(resp,vsoTO.getUrl(),qr.getProvider(),vsoTO.getStatus(),vsoTO.getHelioInstrument());
	           tables[count].setName(vsoTO.getHelioInstrument());
        	  
           }
           /*
           for (QueryResponseBlock rec:resp) {
               
               List<String> fileids = null;
               String provider = rec.getProvider();
               if (!getDataMap.containsKey(provider) ) {
                   fileids = new ArrayList<String>();
                   getDataMap.put(provider, fileids);
               } else {
                   fileids = getDataMap.get(provider);
               }
           
               fileids.add(rec.getFileid());
       
               System.out.println("Provider:" + provider + 
                       "; instrument:" + rec.getInstrument() + 
                       "; start time:" + rec.getTime().getStart() +
                       "; FileID:" + rec.getFileid()); 
           }
           */
           count++;
       }
       
       vsoTO.setStarTableArray(tables);
       vsoTO.setQuerystatus("OK");
       vsoTO.setProviderStatus(true);
       //
       System.out.println(" Genrating VOTABLE for VSO ");
       VsoQueryDao vsoQueryDao=(VsoQueryDao) DAOFactory.getDAOFactory(vsoTO.getWhichProvider());
       vsoQueryDao.generateVOTable(vsoTO);
       System.out.println(" DONE !!! ");
      /* //This section simply finalizes the setting of the request structure
       dataList.addAll(dataRequestList);
       List<DataRequestItem> dri = dataType.getDatarequestitem();
       
       dri.addAll(dataRequestList);
       
       List<DataRequestItem> dri2 = dataType.getDatarequestitem();
       
       for (DataRequestItem drItem:dri2) {
           List<String> fileids = drItem.getFileiditem().getFileid();
           System.out.println(fileids);
       }
   
   
       gdRequest.setDatacontainer(dataType);

       vsoGDRequest.setRequest(gdRequest);
       vsoGDRequest.setVersion("1");
       
       
       //Performs the GetData Request
       VSOGetDataResponse gdResponse = port.getData(vsoGDRequest);
       
       List<GetDataResponseItem> gdRespItem = gdResponse.getGetdataresponseitem();

       //Read response
       for (GetDataResponseItem item:gdRespItem) {
           String provider = item.getProvider();
           System.out.println("GetData response provider:" + provider);
           GetDataItem gdItem = item.getGetdataitem();
           List<DataItem> dataItem = gdItem.getDataitem();
           
           for (DataItem di:dataItem) {
               System.out.println(di.getProvider() + ";" + di.getUrl());
           }
       }

       System.out.println("End");
       /*
       The output should be something like:
           Start time:20100909202514
           End time:20100909232514
           Provider:JSOC
           Number of records found:1
           Number of records returned:1
           Provider:JSOC; instrument:AIA; start time:20100909230918; FileID:aia_synoptic2:193:11812767
           Request GetData
           GetData response provider:JSOC
           http://vso.tuc.noao.edu/cgi-bin/drms_test/drms_export.cgi?series=aia_synoptic2;record=193_11812767-11812767
           End                 
        Or for near time
           Start time:20101130214829
           End time:20101204034829
           Provider:JSOC
           Number of records found:1
           Number of records returned:1
           Provider:JSOC; instrument:AIA; start time:20101203213743; FileID:aia_synoptic2:193:11894306
           Request GetData
           GetData response provider:JSOC
           http://vso.tuc.noao.edu/cgi-bin/drms_test/drms_export.cgi?series=aia_synoptic2;record=193_11894306-11894306
           End
       */
	   }catch(Exception e){
       	throw new DataNotFoundException(e.getMessage());
       }
   }
	
	/**
	 * 
	 * @param vsoTO
	 * @throws DataNotFoundException
	 */
	/*public	void query(VsoDataTO vsoTO) throws DataNotFoundException {
		
		/*
		 * Creating the query parameter for the time in the VSO format		
		*/
		/* StarTable[] tables=null;
		 System.out.println(" Executing VSO provider ");
         try{
	        Time queryTime = new Time(VsoUtils.getDateFormat(vsoTO.getDateFrom()), VsoUtils.getDateFormat(vsoTO.getDateTo()));
			/*
			 * These lines create the query request in the VSO format.
			 */
			/*QueryRequestBlock rb	=	new QueryRequestBlock();
			rb.setInstrument(vsoTO.getInstrument());
			rb.setTime(queryTime);
			System.out.println("---> : Provider name for Votable response : "+vsoTO.getProviderSource());
			//Setting provider 
			rb.setProvider(vsoTO.getProviderSource());
			QueryRequest	r	=	new QueryRequest();
			r.setBlock(rb);
			/*
			 * Now I create the bindings for the VSO port
			 */
	       /* VSOiBindingStub binding;
	        binding = (VSOiBindingStub) new VSOiServiceLocator().getsdacVSOi();
	        /*
	         * Executing the query
	        */
	       /* System.out.println(" Getting provider response for VSO ");
	        ProviderQueryResponse[]	resp = binding.query(r);    
	        //
	        if(resp!=null && resp.length>0){
	        	System.out.println("Response length/ no of providers : "+ resp.length);
		        tables=new StarTable[resp.length];
		        System.out.println( "The query returned " + resp.length + " number of records");
		        for(int count=0;count<resp.length;count++){
		        	tables[count]=new PointsStarTable(resp[count],vsoTO.getUrl(),resp[count].getProvider(),vsoTO.getStatus(),vsoTO.getHelioInstrument());
		        	tables[count].setName(vsoTO.getHelioInstrument());
		        }
		        vsoTO.setStarTableArray(tables);
		        vsoTO.setQuerystatus("OK");
		        vsoTO.setProviderStatus(VsoUtils.getProviderResultCount(resp));
		        //
		        System.out.println(" Genrating VOTABLE for VSO ");
		       	VsoQueryDao vsoQueryDao=(VsoQueryDao) DAOFactory.getDAOFactory(vsoTO.getWhichProvider());
		        vsoQueryDao.generateVOTable(vsoTO);
		        System.out.println(" DONE !!! ");
	        }
	        
        }catch(Exception e){
        	throw new DataNotFoundException(e.getMessage());
        }

	}
	*/
	
	/**
	 *    
	 * @param vsoTO
	 * @throws IOException
	 */
   /* public void getFitsFile(VsoDataTO vsoTO) throws IOException {
    	/*
		 * Now I create the bindings for the VSO port
		*/
     /* try {
    	   VSOiBindingStub binding;
	       binding = (VSOiBindingStub) new VSOiServiceLocator().getsdacVSOi();
	       Float versionNumber = new Float(1.0);
	       GetDataRequest gdr = new GetDataRequest();
	       String []methods = {"URL-FILE", "URL-TAR", "URL-TAR_GZ", "URL-ZIP", "URL"};
	       String[] fileId=new String[1];
	       //setting file to a array
	       fileId[0]=vsoTO.getFileId();
	       gdr.setMethod(methods);
	       DataRequest[]  dr= new DataRequest[1];
	       System.out.println(" VSO file if for retrieving fits data : "+vsoTO.getFileId()+" : Provider Name : "+vsoTO.getProvider()+" : Array of file Id : "+fileId[0]);
	       dr[0] = new DataRequest(vsoTO.getProvider(),fileId);
	       gdr.setData(dr);
	       VSOGetDataRequest vdr = new VSOGetDataRequest(versionNumber,gdr);
	       ProviderGetDataResponse []pdr = binding.getData(vdr);
	       System.out.println(": Provider get data response : "+pdr.length);
	       Data []data;
	       String urlString = null;
	       URL url;
	       int temp = 0;
	       InputStream is;
	       for(int j = 0;j < pdr.length;j++) {
	           data = pdr[j].getData();
	           for(int i = 0;i < data.length;i++) {
	               urlString = data[i].getUrl();
	               System.out.println(" VSO fits data URL  : "+urlString);
	               url = new URL(urlString);
	               is = url.openStream();
	               //is = new BufferedInputStream(url.openStream());
	               temp = 0;
	               while( (temp = is.read()) >= 0) {
	            	   vsoTO.getOutput().write(temp);
	               }
	               vsoTO.getOutput().flush();
	           }//for            
	       }//for
      }catch(Exception e2) {
          System.out.println("could not parse version");
          e2.printStackTrace();
      }
   }	
    
    /**
     * 
     * @param resp
     * @param sProvider
     * @return
     */
   /* public String[] getVsoURL(ProviderQueryResponse	resp,String sProvider)  {
    	/*
		 * Now I create the bindings for the VSO port
		*/
     /* try {
    	   System.out.println("  : In method getVsoURL() , getting list of File Id :  ");
    	   VSOiBindingStub binding;
	       binding = (VSOiBindingStub) new VSOiServiceLocator().getsdacVSOi();
	       Float versionNumber = new Float(1.0);
	       GetDataRequest gdr = new GetDataRequest();
	       String []methods = {"URL-FILE", "URL-TAR", "URL-TAR_GZ", "URL-ZIP", "URL"};
	       String[] fileId=getVsoURL(resp);
	       System.out.println("  : No of File Id :  "+fileId.length);
	       gdr.setMethod(methods);
	       DataRequest[]  dr= new DataRequest[1];
	       dr[0] = new DataRequest(sProvider,fileId);
	       gdr.setData(dr);
	       VSOGetDataRequest vdr = new VSOGetDataRequest(versionNumber,gdr);
	       ProviderGetDataResponse []pdr = binding.getData(vdr);
	       System.out.println(": Provider get data response : "+pdr.length);
	       Data []data;
	       String[] urlString = new String[resp.getNo_of_records_returned()];
	       URL url;
	       int temp = 0;
	       InputStream is;
	       int count=0;
	       System.out.println("  :Getting list of URL'S :  ");
	       for(int j = 0;j < pdr.length;j++) {
	           data = pdr[j].getData();
	           for(int i = 0;i < data.length;i++) {
	               urlString[count] = data[i].getUrl();
	               count++;
	           }//for            
	       }//for
	       System.out.println("  : NO of URL returned :  "+urlString.length);
	       System.out.println("  : Returning the list :  ");
	       return urlString;
      }catch(Exception e2) {
          try {
        	  e2.printStackTrace();
			throw new Exception(" Exception occured while retrieving fits URL from VSO provider ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
	return null;
   }	
      
  
   /**
    * 
    * @param resp
    * @return
    * @throws IOException
    */
  /* public String[] getVsoURL(ProviderQueryResponse	resp) throws IOException {
	   System.out.println(" VSO URLS List Size "+resp.getNo_of_records_returned());
    	String[] fileId=new String[resp.getNo_of_records_returned()];
    	
    	for(int count=0;count<resp.getNo_of_records_returned();count++){
    		fileId[count]=resp.getRecord()[count].getFileid();
    	}
    	//Return file id array
    	return fileId;
    }
   */
   
  
}
