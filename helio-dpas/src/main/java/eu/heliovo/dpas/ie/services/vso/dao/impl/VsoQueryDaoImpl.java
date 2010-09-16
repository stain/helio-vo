/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.vso.dao.impl;

import java.io.BufferedWriter;
import java.util.Calendar;
import java.util.List;

import eu.heliovo.dpas.ie.common.CommonTO;
import eu.heliovo.dpas.ie.common.VOTableCreator;
import eu.heliovo.dpas.ie.internalData.DPASResultItem;
import eu.heliovo.dpas.ie.services.vso.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.vso.dao.interfaces.VsoQueryDao;
import eu.heliovo.dpas.ie.services.vso.provider.VSOProvider;
import eu.heliovo.dpas.ie.services.vso.transfer.VsoDataTO;
import eu.heliovo.dpas.ie.services.vso.utils.VsoUtils;


public class VsoQueryDaoImpl implements VsoQueryDao {
	VSOProvider vsoProvider=null;
	public VsoQueryDaoImpl(){ 
		vsoProvider=new VSOProvider();
	}

	@Override
	public void query(CommonTO commonTO) throws Exception {
		 //VSO Transfer Object
		 VsoDataTO vsoTO=new VsoDataTO();
		 vsoTO.setUrl(commonTO.getUrl());
	     vsoTO.setInstrument(commonTO.getInstrument());
	     vsoTO.setDateFrom(commonTO.getDateFrom());
	     vsoTO.setDateTo(commonTO.getDateTo());
	     vsoTO.setOutput(commonTO.getPrintWriter());
	     vsoTO.setWhichProvider(commonTO.getWhichProvider());
	     vsoTO.setVotableDescription(commonTO.getVotableDescription());
		vsoProvider.query(vsoTO) ;
	}
	
	
	@Override
	public List<DPASResultItem> query(String instrument, Calendar dateFrom,
			Calendar dateTo, int maxResults) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generateVOTable(VsoDataTO vsoTO) throws DataNotFoundException,Exception {
		// TODO Auto-generated method stub
		try{
			if(vsoTO.isProviderStatus()){
				//VOTable header
				VOTableCreator.writeHeaderOfTables(vsoTO);
				//VOTable table data.
				VOTableCreator.writeTables(vsoTO);
				//VOTable footer.
				VOTableCreator.writeFooterOfTables(vsoTO);
			}else{
				throw new DataNotFoundException(" No data for this instrument");
			}
		}catch(Exception e){
			 e.printStackTrace();
			 vsoTO.setBufferOutput(new BufferedWriter(vsoTO.getOutput()));
			 vsoTO.setQuerystatus("ERROR");
			 vsoTO.setQuerydescription(e.getMessage());
			 VOTableCreator.writeErrorTables(vsoTO);
			 throw new DataNotFoundException("EXCEPTION ", e);
		}
	}
	
	public void getFitsFile(VsoDataTO vsoTO) throws Exception
	{
		// TODO Auto-generated method stu
		vsoProvider.getFitsFile(vsoTO) ;
	}

	
	
}
