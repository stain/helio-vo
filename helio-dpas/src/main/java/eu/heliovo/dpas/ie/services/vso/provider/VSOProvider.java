package eu.heliovo.dpas.ie.services.vso.provider;

import java.io.BufferedWriter;
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

	public	void query(VsoDataTO vsoTO) throws DataNotFoundException {
		
		/*
		 * Creating the query parameter for the time in the VSO format		
		*/
		 StarTable[] tables=null;
		
        try{
	        Time queryTime = new Time(VsoUtils.getDateFormat(vsoTO.getDateFrom()), VsoUtils.getDateFormat(vsoTO.getDateTo()));
			/*
			 * These lines create the query request in the VSO format.
			 */
			QueryRequestBlock rb	=	new QueryRequestBlock();
			rb.setInstrument(vsoTO.getInstrument());
			rb.setTime(queryTime);
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
	        ProviderQueryResponse[]	resp = binding.query(r);    
	        //
	        if(resp!=null && resp.length>0){
		        tables=new StarTable[resp.length];
		        System.out.println( "The query returned " + resp.length + " number of records");
		        for(int count=0;count<resp.length;count++){
		        	tables[count]=new PointsStarTable(resp[count],vsoTO.getUrl(),resp[count].getProvider(),vsoTO.getStatus());
		        }
		        vsoTO.setStarTableArray(tables);
		        vsoTO.setQuerystatus("OK");
		        vsoTO.setProviderStatus(VsoUtils.getProviderResultCount(resp));
		        //
		       	VsoQueryDao vsoQueryDao=(VsoQueryDao) DAOFactory.getDAOFactory(vsoTO.getWhichProvider());
		        vsoQueryDao.generateVOTable(vsoTO);
	        }
	        
        }catch(Exception e){
        	throw new DataNotFoundException(" Could not retrieve data: ",e);
        }

	}
	
	
	   
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
	       gdr.setMethod(methods);
	       DataRequest[]  dr= new DataRequest[1];
	       dr[0] = new DataRequest(vsoTO.getProvider(),vsoTO.getFileId().split(","));
	       gdr.setData(dr);
	       VSOGetDataRequest vdr = new VSOGetDataRequest(versionNumber,gdr);
	       ProviderGetDataResponse []pdr = binding.getData(vdr);
	       Data []data;
	       String urlString = null;
	       URL url;
	       int temp = 0;
	       InputStream is;
	       for(int j = 0;j < pdr.length;j++) {
	           data = pdr[j].getData();
	           for(int i = 0;i < data.length;i++) {
	               urlString = data[i].getUrl();
	               System.out.println("here is the urlString = " + urlString);
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
      
}
