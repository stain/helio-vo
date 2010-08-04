package eu.heliovo.dpas.ie.providers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import org.virtualsolar.VSO.VSOi.DataRequest;
import org.virtualsolar.VSO.VSOi.GetDataRequest;
import org.virtualsolar.VSO.VSOi.ProviderGetDataResponse;
import org.virtualsolar.VSO.VSOi.ProviderQueryResponse;
import org.virtualsolar.VSO.VSOi.QueryRequest;
import org.virtualsolar.VSO.VSOi.QueryRequestBlock;
import org.virtualsolar.VSO.VSOi.Time;
import org.virtualsolar.VSO.VSOi.VSOGetDataRequest;
import org.virtualsolar.VSO.VSOi.VSOiBindingStub;
import org.virtualsolar.VSO.VSOi.VSOiServiceLocator;

import eu.heliovo.dpas.ie.common.DebugUtilities;
import eu.heliovo.dpas.ie.dataProviders.DPASDataProvider;
import eu.heliovo.dpas.ie.internalData.DPASResultItem;

public class VSOProvider implements DPASDataProvider
{
	/*
	 * This translates the names from the helio standard to the vso standard.
	 */
	private	HashMap<String, String>	nameTranslator	=	new HashMap<String, String>();
	/*
	 * Utilities
	 */
	DebugUtilities							debugUtils	=	new DebugUtilities();

	
	public VSOProvider()
	{
		super();
		initialize();
	}

	private void initialize()
	{
		nameTranslator.put("HINODE__XRT", "XRT");
		nameTranslator.put("HINODE__EIS", "EIS");
		nameTranslator.put("YNAO__HALPH", "YNAO");	
		nameTranslator.put("YOHKOH__WBS_GRS", "WBS");
		nameTranslator.put("KPNO__VSM", "vsm");
		nameTranslator.put("SOHO__VIRGO", "VIRGO");
		nameTranslator.put("SOHO__EIT", "EIT");
		nameTranslator.put("SOHO__CDS", "CDS");
		nameTranslator.put("SOHO__SUMER", "SUMER");
		nameTranslator.put("SOHO__UVCS", "UVCS");
		nameTranslator.put("SOHO__LASCO", "LASCO");
		nameTranslator.put("SOHO__SWAN", "SWAN");
		nameTranslator.put("SOHO__MDI", "MDI");
		nameTranslator.put("SOHO__GOLF", "GOLF");
		nameTranslator.put("SOHO__CELIAS", "CELIAS");
		nameTranslator.put("SOHO__COSTEP", "COSTEP");
		nameTranslator.put("SOHO__ERNE", "ERNE");

		nameTranslator.put("TRACE__TRACE_EUV", "TRACE");
	}

	@Override
	public List<DPASResultItem> query(String instrument, Calendar dateFrom, Calendar dateTo,
			int maxResults) throws Exception
	{
		LinkedList<DPASResultItem> 			results 	= 	new LinkedList<DPASResultItem>();
		HashMap<String, VSOResultItem>		tmpResults	=	new HashMap<String, VSOResultItem>();
		
		debugUtils.printLog(this.getClass().getName(), "VSO, querying " + instrument);
		/*
		 * Creating the Query in VSO format.
		 */
		/*
		 * First translate the name of the instrument from the HELIO standard to the VSO standard
		 */
		if(!nameTranslator.containsKey(instrument))
		{
			debugUtils.printLog(this.getClass().getName(), "No such instrument present in VSO provider: " + instrument);
			throw new DPASDataProviderException("No such instrument present in VSO provider: " + instrument);
		}
		/*
		 * This is the name that VSO uses for this instrument
		 */
		String	vsoInstrumentName	=	nameTranslator.get(instrument);
		/*
		 * Creating the query parameter for the time in the VSO format		
		 */
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));		
        Time queryTime = new Time(dateFormat.format(dateFrom.getTime()), dateFormat.format(dateTo.getTime()));
		/*
		 * These lines create the query request in the VSO format
		 */
		QueryRequestBlock rb	=	new QueryRequestBlock();
		
		rb.setInstrument(vsoInstrumentName);
		rb.setTime(queryTime);
				
		QueryRequest	r	=	new QueryRequest();
		r.setBlock(rb);
		/*
		 * Now I create the bindings for the VSO port
		 */
        VSOiBindingStub binding;
        try {
            binding = (VSOiBindingStub)
                          new VSOiServiceLocator().getsdacVSOi();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new Exception("JAX-RPC ServiceException caught: " + jre);
        }
        /*
         * Executing the query
         */
        ProviderQueryResponse[]	resp = binding.query(r);     
        debugUtils.printLog(this.getClass().getName(), "The query returned " + resp.length + " number of records");

        /*
         * This is the vector of the file_ids to be requested...
         */
        Vector<String>	fileIds	=	new Vector<String>();
        String	provider	=	null;
        
        for(int i = 0; i < resp.length; i++)
        {
//        	System.out.println("["+i+"] --> response     : " + resp[i]);
//        	
//        	System.out.println("["+i+"] --> number of records found     : " + resp[i].getNo_of_records_found());
//        	System.out.println("["+i+"] --> number of records returned  : " + resp[i].getNo_of_records_returned());
//        	System.out.println("["+i+"] --> record returned  : " + resp[i].getRecord());
//        	
//            System.out.println("["+i+"] --> record returned has " + resp[i].getRecord().length + " lenght");
//        
            for(int j = 0; j < resp[i].getRecord().length; j++)
            {
            	DPASResultItem	dpasResult	=	new DPASResultItem();
            	VSOResultItem	vsoResult	=	new VSOResultItem();
            	/*
            	 * Instrument Name
            	 */
            	dpasResult.instrument	=	resp[i].getRecord()[j].getInstrument();            	
            	vsoResult.instrument	=	resp[i].getRecord()[j].getInstrument();
//            	System.out.println("["+i+","+j+"] --> Instrument " + resp[i].getRecord()[j].getInstrument());
            	/*
            	 * File ID
            	 */                
//              System.out.println("["+i+","+j+"] --> FileId " + resp[i].getRecord()[j].getFileid());
            	dpasResult.fileId	=	resp[i].getRecord()[j].getFileid();            	
            	vsoResult.fileId	=	resp[i].getRecord()[j].getFileid();            	
                
                fileIds.add(resp[i].getRecord()[j].getFileid());
//                System.out.println("["+i+","+j+"] --> Info " + resp[i].getRecord()[j].getInfo());
            	/*
            	 * Provider
            	 */                
//                System.out.println("["+i+","+j+"] --> Provider " + resp[i].getRecord()[j].getProvider());
            	dpasResult.provider		=	resp[i].getRecord()[j].getProvider();            	
            	vsoResult.provider		=	resp[i].getRecord()[j].getProvider();            	
            	/*
            	 * Start and Stop times
            	 */
//                System.out.println("["+i+","+j+"] --> StartTime " + resp[i].getRecord()[j].getTime().getStart());               
                Date	startTime	=	dateFormat.parse(resp[i].getRecord()[j].getTime().getStart());
//                dpasResult.measurementStart.setTime(startTime);
                Calendar	tmpCalendar	=	new GregorianCalendar();
                tmpCalendar.setTime(startTime);
                vsoResult.measurementStart	=	tmpCalendar;

//                System.out.println("["+i+","+j+"] --> StopTime " + resp[i].getRecord()[j].getTime().getEnd());
                Date	stopTime	=	dateFormat.parse(resp[i].getRecord()[j].getTime().getEnd());
//                dpasResult.measurementEnd.setTime(stopTime);
                tmpCalendar	=	new GregorianCalendar();
                tmpCalendar.setTime(stopTime);
                vsoResult.measurementEnd	=	tmpCalendar;

                provider	=	resp[i].getRecord()[j].getProvider();                      
                /*
                 * Adding the VSO result to the hash map...
                 */
                tmpResults.put(vsoResult.fileId, vsoResult);
            }
        }
		
//        for(int k = 0; k < fileIds.size(); k++)
//        {
//            System.out.println("["+k+"] --> file Id " + fileIds.get(k));        	
//        }

//        /*
//         * Debug : print the hash map with the fileids
//         */
//        Iterator<String>	key	=	tmpResults.keySet().iterator();
//        while(key.hasNext())
//        {
//        	String	k	=	key.next();
//        	System.out.println(k + " ---> " + tmpResults.get(k));
//        }
        
        /*
         * Now creating the data requests...
         */
        
//        String[]	fids	=	(String[]) fileIds.toArray(new String[fileIds.size()]);
//        String[]	fids	=	tmpResults.keySet().toArray(new String[fileIds.size()]);
        
        /*
         * Now requesting the data....
         */
        DataRequest	dataReq		=	new DataRequest();
        
        dataReq.setFileid(fileIds.toArray(new String[fileIds.size()]));
        dataReq.setProvider(provider);
        
        Vector<DataRequest>		dataReqs	=	new Vector<DataRequest>();
        dataReqs.add(dataReq);
        
        GetDataRequest	getDataReq	=	new GetDataRequest();
        getDataReq.setData(dataReqs.toArray(new DataRequest[dataReqs.size()]));
        
        String []methods = {"URL-FILE", "URL-TAR", "URL-TAR_GZ", "URL-ZIP", "URL"};
        getDataReq.setMethod(methods);
        
        
        
//        Vector<GetDataRequest>	getDataReqs	=	new Vector<GetDataRequest>();
//        getDataReqs.add(getDataReq);
        
        VSOGetDataRequest	getDataReqBody	=	new VSOGetDataRequest();
        
        getDataReqBody.setRequest(getDataReq);
      
        
        ProviderGetDataResponse[]	dataResp	=	binding.getData(getDataReqBody);        
        
        debugUtils.printLog(this.getClass().getName(), "The data request returned " + dataResp.length + " number of records");
        /*
         * Now creating the DPAS results.
         */
        
        for(int m = 0; m < dataResp.length; m++)
        {
        	for(int n = 0; n < dataResp[m].getData().length; n++)
        	{
        		String	currFileId	=	 dataResp[m].getData()[n].getFileid()[0];

//        		System.out.println("["+m+","+n+"] --> file id size is " + dataResp[m].getData()[n].getFileid().length);
//                System.out.println("["+m+","+n+"] --> data url is " + dataResp[m].getData()[n].getUrl());
//                System.out.println("["+m+","+n+"] --> data details are " + dataResp[m].getData()[n].getDetails());
                /*
                 * Find the current record on the VSOResultItem
                 */
                VSOResultItem	currVsoResult	=	tmpResults.get(currFileId);
            	System.out.println(currFileId + " ---> " + currVsoResult);

        		
                DPASResultItem	currDpasResult	=	new DPASResultItem();
                currDpasResult.instrument		=	instrument;
                currDpasResult.measurementStart	=	currVsoResult.measurementStart;
                currDpasResult.measurementEnd	=	currVsoResult.measurementEnd;
                currDpasResult.urlFITS			=	dataResp[m].getData()[n].getUrl();
                
                results.add(currDpasResult);
        	}
        }
        
        
        return results;
	}
}
