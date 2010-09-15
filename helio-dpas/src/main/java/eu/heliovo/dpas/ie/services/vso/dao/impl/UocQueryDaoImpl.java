/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.uoc.dao.impl;

import java.util.Calendar;
import java.util.List;
import eu.heliovo.dpas.ie.internalData.DPASResultItem;
import eu.heliovo.dpas.ie.services.uoc.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.uoc.dao.interfaces.UocQueryDao;
import eu.heliovo.dpas.ie.services.uoc.provider.UOCProvider;
import eu.heliovo.dpas.ie.services.uoc.transfer.UocDataTO;

public class UocQueryDaoImpl implements UocQueryDao {
	UOCProvider uocProvider=null;
	public UocQueryDaoImpl(){ 
		uocProvider=new UOCProvider();
	}

	@Override
	public void query(UocDataTO uocTO) throws Exception {
		// TODO Auto-generated method stu
		uocProvider.query(uocTO) ;
	}
	
	
	@Override
	public List<DPASResultItem> query(String instrument, Calendar dateFrom,
			Calendar dateTo, int maxResults) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generateVOTable(UocDataTO vsoTO) throws DataNotFoundException,Exception {
		// TODO Auto-generated method stub
		try{
			
		}catch(Exception e){
			 e.printStackTrace();
			 throw new DataNotFoundException("EXCEPTION ", e);
		}
	}
	
}
