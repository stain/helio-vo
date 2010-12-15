package eu.heliovo.dpas.ie.services.soda.provider;

import java.util.List;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import eu.heliovo.dpas.ie.services.common.utils.CommonUtils;
import eu.heliovo.dpas.ie.services.soda.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.details.RecordDetail;
import eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.query.MainQueryLogicalBlockItem;
import eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.query.QueryRelationBlock;
import eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.query.RecordId;
import eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.webservice.GetRecordDetailsRequest;
import eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.webservice.QueryRequest;
import eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.schemas.webservice.QueryResponse;
import eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.wsdls.soteria.SoteriaPort;
import eu.heliovo.dpas.ie.services.soda.service.eu.soteriaspace.wsdls.soteria.SoteriaService;
import eu.heliovo.dpas.ie.services.soda.transfer.SoteriaDataTO;
import uk.ac.starlink.table.StarTable;

public class SoteriaProvider
{

	public	void query(SoteriaDataTO soteriaTO) throws DataNotFoundException {
		
		StarTable[] tables=new StarTable[1];
		MainQueryLogicalBlockItem mainQueryBlockItem=new MainQueryLogicalBlockItem();      
		
		try{
        	SoteriaPort soteriaPort=new SoteriaService().getSoteriaPort();
        	XMLGregorianCalendar startDate =DatatypeFactory.newInstance().newXMLGregorianCalendar(CommonUtils.stringToGregorianCalendar(soteriaTO.getDateFrom()));
        	XMLGregorianCalendar endDate =DatatypeFactory.newInstance().newXMLGregorianCalendar(CommonUtils.stringToGregorianCalendar(soteriaTO.getDateTo()));
        	QueryRelationBlock queryBigger=new QueryRelationBlock();
        	QueryRelationBlock queryLesser=new QueryRelationBlock();
        	//
        	queryBigger.setTime(startDate );
        	queryLesser.setTime(endDate);
        	mainQueryBlockItem.setBigger(queryBigger);
        	mainQueryBlockItem.setLesser(queryLesser);
        	QueryRequest queryRequest=new QueryRequest();
        	queryRequest.setQuery(mainQueryBlockItem);
        	//
        	QueryResponse queryResp=soteriaPort.query(queryRequest);
        	//
        	System.out.println(queryResp.getRecords().getRecord()+" : "+queryResp.getState()+" : "+queryResp.getRequestId());
        	List<RecordId> record =queryResp.getRecords().getRecord();
        	for(int i=0;i<record.size();i++)
        	{
        		System.out.println(""+record.get(i).getName()+""+record.get(i).getRowId());
        		GetRecordDetailsRequest sRecordDt=new GetRecordDetailsRequest();
        		sRecordDt.setId(record.get(i).getRowId());
        		sRecordDt.setDataset(record.get(i).getDataset());
        		//
        		RecordDetail recordDetails=soteriaPort.detail(sRecordDt);
        		System.out.println(" : "+recordDetails.getProvider()+" : "+recordDetails.getInstrument()+" : "+recordDetails.getTime());
        	}
        }catch(Exception e){
        	e.printStackTrace();
        	throw new DataNotFoundException(e.getMessage());
        }

	}
	
	/*
	public void details()
	{
		DescriptorPortType portType=new Descriptor().getPort();
    	DescriptorType descType=portType.getProviders("");
    	List<ProviderDescriptionType> listDescType=descType.getProvider();
    	for(int i=0;i<listDescType.size();i++){
    		List<DatasetDescriptionType> datasets =listDescType.get(i).getDatasets();
    		System.out.println(""+listDescType.get(i).getUrl()+"--:--"+listDescType.get(i).getName());
    		for(int j=0;j<datasets.size();j++)
    		{
    			System.out.println(" Instrument "+datasets.get(j).getInstrument()+" Observatory "+datasets.get(j).getObservatory()+" : "+datasets.get(j).getName()+" : "+datasets.get(j).getTimerange());
    		}
    	}
	}
	*/
      
}
