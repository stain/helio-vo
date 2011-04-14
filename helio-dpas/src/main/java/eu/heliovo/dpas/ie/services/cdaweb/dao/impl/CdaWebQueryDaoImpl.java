/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.cdaweb.dao.impl;

import java.io.BufferedWriter;
import java.util.Calendar;
import java.util.List;
import eu.heliovo.dpas.ie.services.cdaweb.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.cdaweb.dao.interfaces.CdaWebQueryDao;
import eu.heliovo.dpas.ie.services.cdaweb.provider.CDAWEBProvider;
import eu.heliovo.dpas.ie.services.cdaweb.transfer.CdaWebDataTO;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.common.utils.VOTableCreator;
import eu.heliovo.dpas.ie.services.directory.utils.DPASResultItem;


public class CdaWebQueryDaoImpl implements CdaWebQueryDao {
	CDAWEBProvider cdaWebProvider=null;
	public CdaWebQueryDaoImpl(){ 
		cdaWebProvider=new CDAWEBProvider();
	}

	@Override
	public void query(CommonTO commonTO) throws Exception {
		 //VSO Transfer Object
		 CdaWebDataTO cdaWebTO=new CdaWebDataTO();
		 cdaWebTO.setUrl(commonTO.getUrl());
	     cdaWebTO.setInstrument(commonTO.getInstrument());
	     cdaWebTO.setDateFrom(commonTO.getDateFrom());
	     cdaWebTO.setDateTo(commonTO.getDateTo());
	     cdaWebTO.setOutput(commonTO.getPrintWriter());
	     cdaWebTO.setWhichProvider(commonTO.getWhichProvider());
	     cdaWebTO.setVotableDescription(commonTO.getVotableDescription());
	     cdaWebTO.setBufferOutput(commonTO.getBufferOutput());
	     cdaWebTO.setStatus(commonTO.getStatus());
	     cdaWebTO.setHelioInstrument(commonTO.getHelioInstrument());
	     cdaWebTO.setContextUrl(commonTO.getContextUrl());
	     cdaWebTO.setMissionName(commonTO.getMissionName());
	     cdaWebTO.setProviderSource(commonTO.getProviderSource());
	     cdaWebTO.setDetectiveField(commonTO.getDetectiveField());
	     cdaWebProvider.query(cdaWebTO) ;
	}
	
	
	@Override
	public List<DPASResultItem> query(String instrument, Calendar dateFrom,
			Calendar dateTo, int maxResults) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void generateVOTable(CdaWebDataTO cdaWebTO) throws DataNotFoundException,Exception {
		// TODO Auto-generated method stub
		try{
			//VOTable table data.
			VOTableCreator.writeTables(cdaWebTO);
		}catch(Exception e){
			 e.printStackTrace();
			 throw new DataNotFoundException(e.getMessage());
		}
	}
	
	
	
}
