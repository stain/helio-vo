/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.soda.dao.impl;

import java.util.Calendar;
import java.util.List;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.common.utils.VOTableCreator;
import eu.heliovo.dpas.ie.services.directory.utils.DPASResultItem;
import eu.heliovo.dpas.ie.services.soda.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.soda.dao.interfaces.SoteriaQueryDao;
import eu.heliovo.dpas.ie.services.soda.provider.SoteriaProvider;
import eu.heliovo.dpas.ie.services.soda.transfer.SoteriaDataTO;


public class SoteriaQueryDaoImpl implements SoteriaQueryDao {
	SoteriaProvider soteriaProvider=null;
	public SoteriaQueryDaoImpl(){ 
		soteriaProvider=new SoteriaProvider();
	}

	@Override
	public void query(CommonTO commonTO) throws Exception {
		 //VSO Transfer Object
		SoteriaDataTO soteriaTO=new SoteriaDataTO();
		soteriaTO.setUrl(commonTO.getUrl());
		soteriaTO.setInstrument(commonTO.getInstrument());
		soteriaTO.setDateFrom(commonTO.getDateFrom());
		soteriaTO.setDateTo(commonTO.getDateTo());
		soteriaTO.setOutput(commonTO.getPrintWriter());
		soteriaTO.setWhichProvider(commonTO.getWhichProvider());
		soteriaTO.setVotableDescription(commonTO.getVotableDescription());
		soteriaTO.setBufferOutput(commonTO.getBufferOutput());
		soteriaTO.setStatus(commonTO.getStatus());
		soteriaTO.setHelioInstrument(commonTO.getHelioInstrument());
		soteriaProvider.query(soteriaTO) ;
	}
	
	
	
	@Override
	public List<DPASResultItem> query(String instrument, Calendar dateFrom,
			Calendar dateTo, int maxResults) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void generateVOTable(SoteriaDataTO soteriaTO) throws DataNotFoundException,Exception {
		// TODO Auto-generated method stub
		try{
			//VOTable table data.
			VOTableCreator.writeTables(soteriaTO);
		}catch(Exception e){
			 e.printStackTrace();
			 throw new DataNotFoundException(e.getMessage());
		}
	}	
	
}
