package eu.heliovo.dpas.ie.services.uoc.provider;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import uk.ac.starlink.table.StarTable;
import eu.heliovo.dpas.ie.common.DAOFactory;
import eu.heliovo.dpas.ie.services.uoc.transfer.UocDataTO;
import eu.heliovo.dpas.ie.services.vso.dao.exception.DataNotFoundException;
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
import eu.heliovo.dpas.ie.services.vso.utils.QueryThreadAnalizer;
import eu.heliovo.dpas.ie.services.vso.utils.VsoUtils;


public class UOCProvider
{

	public	void query(UocDataTO uocTO) throws DataNotFoundException {
		
		try{
	        
        }catch(Exception e){
        	throw new DataNotFoundException(" Could not retrieve data: ",e);
        }

	}
	
	
	   
    public void getFitsFile(UocDataTO uocTO) throws IOException {
    	/*
		 * Now I create the bindings for the VSO port
		*/
      try {
    	   
      }catch(Exception e2) {
          System.out.println("could not parse version");
          e2.printStackTrace();
      }
   }	
      
}
