/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.hqi.dao.impl;

import java.util.Calendar;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.directory.utils.DPASResultItem;
import eu.heliovo.dpas.ie.services.hqi.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.hqi.dao.interfaces.HqiQueryDao;
import eu.heliovo.dpas.ie.services.hqi.provider.HQIProvider;
import eu.heliovo.dpas.ie.services.hqi.service.net.ivoa.xml.votable.v1.RESOURCE;
import eu.heliovo.dpas.ie.services.hqi.transfer.HqiDataTO;

public class HqiQueryDaoImpl implements HqiQueryDao {
	HQIProvider hqiProvider=null;
	public HqiQueryDaoImpl(){ 
		hqiProvider=new HQIProvider();
	}

	@Override
	public void query(CommonTO commonTO) throws Exception {
		 HqiDataTO uocDataTO=new HqiDataTO();
		 uocDataTO.setInstrument(commonTO.getInstrument());
		 uocDataTO.setDateFrom(commonTO.getDateFrom());
		 uocDataTO.setDateTo(commonTO.getDateTo());
		 uocDataTO.setOutput(commonTO.getPrintWriter());
		 uocDataTO.setWhichProvider(commonTO.getWhichProvider());
		 uocDataTO.setVotableDescription(commonTO.getVotableDescription());
		 uocDataTO.setBufferOutput(commonTO.getBufferOutput());
		 uocDataTO.setStatus(commonTO.getStatus());
		 uocDataTO.setHelioInstrument(commonTO.getHelioInstrument());
		 uocDataTO.setContextUrl(commonTO.getContextUrl());
		hqiProvider.query(uocDataTO) ;
	}
	
	
	@Override
	public List<DPASResultItem> query(String instrument, Calendar dateFrom,
			Calendar dateTo, int maxResults) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generateVOTable(HqiDataTO hqiTO) throws DataNotFoundException,Exception {
		// TODO Auto-generated method stub
		try{
			 RESOURCE resource=hqiTO.getResource();
			 JAXBContext context = JAXBContext.newInstance(resource.getClass());
			 Marshaller m = context.createMarshaller();
			 m.setProperty(Marshaller.JAXB_FRAGMENT, true);
			 m.marshal(resource, hqiTO.getBufferOutput());
		}catch(Exception e){
			 e.printStackTrace();
			 throw new DataNotFoundException(e.getMessage());
		}
	}
	
}
