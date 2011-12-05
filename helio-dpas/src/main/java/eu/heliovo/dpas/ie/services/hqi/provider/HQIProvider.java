package eu.heliovo.dpas.ie.services.hqi.provider;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

import eu.heliovo.dpas.ie.services.common.utils.DAOFactory;
import eu.heliovo.dpas.ie.services.hqi.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.hqi.dao.interfaces.HqiQueryDao;
import eu.heliovo.dpas.ie.services.hqi.service.eu.helio_vo.xml.instruments.v0.Instrument;
import eu.heliovo.dpas.ie.services.hqi.service.eu.helio_vo.xml.queryservice.v0.Query;
import eu.heliovo.dpas.ie.services.hqi.service.eu.helio_vo.xml.queryservice.v0.QueryResponse;
import eu.heliovo.dpas.ie.services.hqi.service.eu.helio_vo.xml.queryservice.v0_1.HelioQueryService;
import eu.heliovo.dpas.ie.services.hqi.service.eu.helio_vo.xml.queryservice.v0_1.HelioQueryServiceService;
import eu.heliovo.dpas.ie.services.hqi.transfer.HqiDataTO;
import eu.heliovo.dpas.ie.services.hqi.utils.HqiUtils;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;
import java.util.Map;


public class HQIProvider
{
	private static final QName SERVICE_NAME = new QName("http://helio-vo.eu/xml/QueryService/v0.1", "HelioQueryServiceService");

	public	void query(HqiDataTO uocTO) throws DataNotFoundException {
		
		try{
	 		//KMB: HelioQueryServiceService ss = new HelioQueryServiceService(new URL(HqiUtils.getWorkingHQIURLBasedType(uocTO.getWhichProvider())), SERVICE_NAME);
			HelioQueryServiceService ss = new HelioQueryServiceService();
	        HelioQueryService port = ss.getHelioQueryServicePort();
	    	BindingProvider bp = (BindingProvider)port;
	        Map<String, Object> context = bp.getRequestContext();

	        String newAddress = HqiUtils.getWorkingHQIURLBasedType(uocTO.getWhichProvider());
	        //String newAddress = HqiUtils.getWorkingHQIURLBasedType(uocTO.getProviderType());
	        //System.out.println("should be using " + newAddress);
	        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,newAddress);
	        System.out.println("newaddress url = " + newAddress);
	        Query queryParameter=new Query();
	        //queryParameter.setINSTRUMENT(Instrument.valueOf("RHESSI_HESSI_GMR"));
	        //From Clause
	        List<String> from=new ArrayList<String>(1);
	        if(uocTO.getInstrument()!=null)
	        from.add(uocTO.getInstrument().toLowerCase());
	        queryParameter.setFrom(from);
	        //Start Time Clause
	        List<String> startTime=new ArrayList<String>(1);
	        startTime.add(uocTO.getDateFrom());
	        queryParameter.setStarttime(startTime);
	        //Instrument 
	        String inst=uocTO.getHelioInstrument();
	        queryParameter.setINSTRUMENT(Instrument.fromValue(inst));
	       
	        //KMB-CHECK 
	        String whereClause = HqiUtils.getInstrumentWhereClause(uocTO.getWhichProvider(),inst);
	        //String whereClause = HqiUtils.getInstrumentWhereClause(uocTO.getProviderType(),inst);
	        System.out.println("whichprovider = " +uocTO.getWhichProvider() + " inst = " + inst + " prov type: " + uocTO.getProviderType() + " whereclause = " + whereClause);
	        if(whereClause == null) {
	        	queryParameter.setINSTRUMENT(Instrument.fromValue(inst));
	        }else {
	        	queryParameter.setWHERE(whereClause);
	        }
	        
	        //End Time Clause
	        List<String> endTime=new ArrayList<String>(1);
	        endTime.add(uocTO.getDateTo());
	        queryParameter.setEndtime(endTime);
	        QueryResponse _query__return = port.query(queryParameter);
	        //Setting resource 
		    uocTO.setResource(_query__return.getVOTABLE().getRESOURCE().get(0));
		    //Calling UOC provider
	        HqiQueryDao uocQueryDao=(HqiQueryDao)DAOFactory.getDAOFactory(uocTO.getWhichProvider(),uocTO.getProviderType());
	        uocQueryDao.generateVOTable(uocTO);
        }catch(Exception e){
        	e.printStackTrace();
        	throw new DataNotFoundException(e.getMessage());
        }

	}     
}