/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.directory.dao.impl;

import java.io.BufferedWriter;
import java.util.Calendar;
import java.util.List;
import eu.heliovo.dpas.ie.services.cdaweb.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.common.utils.VOTableCreator;
import eu.heliovo.dpas.ie.services.directory.dao.interfaces.DirQueryDao;
import eu.heliovo.dpas.ie.services.directory.provider.DirProvider;
import eu.heliovo.dpas.ie.services.directory.transfer.DirDataTO;
import eu.heliovo.dpas.ie.services.directory.utils.DPASResultItem;
import eu.heliovo.dpas.ie.services.directory.utils.DirInsAnlyFactory;


public class DirQueryDaoImpl implements DirQueryDao {
	DirProvider dirProvider=null;
	public DirQueryDaoImpl(){ 
		dirProvider=new DirProvider();
	}

	@Override
	public void query(CommonTO commonTO) throws Exception {
		 //VSO Transfer Object
		 DirDataTO dirTO=new DirDataTO();
		 dirTO.setUrl(commonTO.getUrl());
		 dirTO.setInstrument(commonTO.getInstrument());
		 dirTO.setDateFrom(commonTO.getDateFrom());
		 dirTO.setDateTo(commonTO.getDateTo());
		 dirTO.setOutput(commonTO.getPrintWriter());
		 dirTO.setWhichProvider(commonTO.getWhichProvider());
		 dirTO.setVotableDescription(commonTO.getVotableDescription());
		 dirTO.setBufferOutput(commonTO.getBufferOutput());
		 dirTO.setStatus(commonTO.getStatus());
	     dirProvider.query(dirTO) ;
	}
		
	@Override
	public List<DPASResultItem> query(String instrument, Calendar dateFrom,
			Calendar dateTo, int maxResults) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void generateVOTable(DirDataTO dirTO) throws DataNotFoundException,Exception {
		// TODO Auto-generated method stub
		try{
			//VOTable table data.
			VOTableCreator.writeTables(dirTO);
		}catch(Exception e){
			 e.printStackTrace();
			 dirTO.setBufferOutput(new BufferedWriter(dirTO.getOutput()));
			 dirTO.setQuerystatus("ERROR");
			 dirTO.setQuerydescription(e.getMessage());
			 VOTableCreator.writeErrorTables(dirTO);
			 throw new DataNotFoundException("EXCEPTION ", e);
		}
	}
	
	
	
}
