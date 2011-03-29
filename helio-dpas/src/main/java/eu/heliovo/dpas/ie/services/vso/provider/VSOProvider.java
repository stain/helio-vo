package eu.heliovo.dpas.ie.services.vso.provider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import uk.ac.starlink.table.StarTable;
import eu.heliovo.dpas.ie.services.common.utils.DAOFactory;
import eu.heliovo.dpas.ie.services.vso.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.vso.dao.interfaces.VsoQueryDao;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.Data;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.DataRequest;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.GetDataRequest;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.ProviderGetDataResponse;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.ProviderQueryResponse;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.QueryRequest;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.QueryRequestBlock;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.Time;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.VSOGetDataRequest;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.VSOiBindingStub;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.VSOiServiceLocator;
import eu.heliovo.dpas.ie.services.vso.transfer.VsoDataTO;
import eu.heliovo.dpas.ie.services.vso.utils.PointsStarTable;
import eu.heliovo.dpas.ie.services.vso.utils.VsoUtils;


public class VSOProvider
{
	/**
	 * 
	 * @param vsoTO
	 * @throws DataNotFoundException
	 */
	public	void query(VsoDataTO vsoTO) throws DataNotFoundException {
		
		/*
		 * Creating the query parameter for the time in the VSO format		
		*/
		 StarTable[] tables=null;
		 System.out.println(" Executing VSO provider ");
         try{
	        Time queryTime = new Time(VsoUtils.getDateFormat(vsoTO.getDateFrom()), VsoUtils.getDateFormat(vsoTO.getDateTo()));
			/*
			 * These lines create the query request in the VSO format.
			 */
			QueryRequestBlock rb	=	new QueryRequestBlock();
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
	        VSOiBindingStub binding;
	        binding = (VSOiBindingStub) new VSOiServiceLocator().getsdacVSOi();
	        /*
	         * Executing the query
	        */
	        System.out.println(" Getting provider response for VSO ");
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
	
	
	/**
	 *    
	 * @param vsoTO
	 * @throws IOException
	 */
    public void getFitsFile(VsoDataTO vsoTO) throws IOException {
    	/*
		 * Now I create the bindings for the VSO port
		*/
      try {
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
    public String[] getVsoURL(ProviderQueryResponse	resp,String sProvider)  {
    	/*
		 * Now I create the bindings for the VSO port
		*/
      try {
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
   public String[] getVsoURL(ProviderQueryResponse	resp) throws IOException {
	   System.out.println(" VSO URLS List Size "+resp.getNo_of_records_returned());
    	String[] fileId=new String[resp.getNo_of_records_returned()];
    	
    	for(int count=0;count<resp.getNo_of_records_returned();count++){
    		fileId[count]=resp.getRecord()[count].getFileid();
    	}
    	//Return file id array
    	return fileId;
    }
    
}
