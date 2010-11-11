/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.uoc.dao.impl;

import java.util.Calendar;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.directory.utils.DPASResultItem;
import eu.heliovo.dpas.ie.services.uoc.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.uoc.dao.interfaces.UocQueryDao;
import eu.heliovo.dpas.ie.services.uoc.provider.UOCProvider;
import eu.heliovo.dpas.ie.services.uoc.service.net.ivoa.xml.votable.v1.RESOURCE;
import eu.heliovo.dpas.ie.services.uoc.transfer.UocDataTO;

public class UocQueryDaoImpl implements UocQueryDao {
	UOCProvider uocProvider=null;
	public UocQueryDaoImpl(){ 
		uocProvider=new UOCProvider();
	}

	@Override
	public void query(CommonTO commonTO) throws Exception {
		 UocDataTO uocDataTO=new UocDataTO();
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
		uocProvider.query(uocDataTO) ;
	}
	
	
	@Override
	public List<DPASResultItem> query(String instrument, Calendar dateFrom,
			Calendar dateTo, int maxResults) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generateVOTable(UocDataTO uocTO) throws DataNotFoundException,Exception {
		// TODO Auto-generated method stub
		try{
			 RESOURCE resource=uocTO.getResource();
			 JAXBContext context = JAXBContext.newInstance(resource.getClass());
			 Marshaller m = context.createMarshaller();
			 m.setProperty(Marshaller.JAXB_FRAGMENT, true);
			 m.marshal(resource, uocTO.getBufferOutput());
		}catch(Exception e){
			 e.printStackTrace();
			 throw new DataNotFoundException(e.getMessage());
		}
	}
	
}
