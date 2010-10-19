package eu.heliovo.dpas.ie.services.uoc.provider;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

import eu.heliovo.dpas.ie.services.common.utils.DAOFactory;
import eu.heliovo.dpas.ie.services.uoc.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.uoc.dao.interfaces.UocQueryDao;
import eu.heliovo.dpas.ie.services.uoc.service.eu.helio_vo.xml.queryservice.v0.Query;
import eu.heliovo.dpas.ie.services.uoc.service.eu.helio_vo.xml.queryservice.v0.QueryResponse;
import eu.heliovo.dpas.ie.services.uoc.service.eu.helio_vo.xml.queryservice.v0_1.HelioQueryService;
import eu.heliovo.dpas.ie.services.uoc.service.eu.helio_vo.xml.queryservice.v0_1.HelioQueryServiceService;
import eu.heliovo.dpas.ie.services.uoc.transfer.UocDataTO;


public class UOCProvider
{
	private static final QName SERVICE_NAME = new QName("http://helio-vo.eu/xml/QueryService/v0.1", "HelioQueryServiceService");

	public	void query(UocDataTO uocTO) throws DataNotFoundException {
		
		try{
	        
			HelioQueryServiceService ss = new HelioQueryServiceService(new URL("http://140.105.77.30:8080/helio-uoc-r3/HelioService?wsdl"), SERVICE_NAME);
	        HelioQueryService port = ss.getHelioQueryServicePort(); 
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
	        //End Time Clause
	        List<String> endTime=new ArrayList<String>(1);
	        endTime.add(uocTO.getDateTo());
	        queryParameter.setEndtime(endTime);
	        QueryResponse _query__return = port.query(queryParameter);
	        //Setting resource 
		    uocTO.setResource(_query__return.getVOTABLE().getRESOURCE().get(0));
		    //Calling UOC provider
	        UocQueryDao uocQueryDao=(UocQueryDao)DAOFactory.getDAOFactory(uocTO.getWhichProvider());
	        uocQueryDao.generateVOTable(uocTO);

        }catch(Exception e){
        	e.printStackTrace();
        	throw new DataNotFoundException(e.getMessage());
        }

	}
	

      
}
