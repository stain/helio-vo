/* #ident	"%W%" */
package eu.heliovo.ics.common.dao.impl;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;

import org.hibernate.Query;

import eu.heliovo.ics.common.dao.CommonDaoFactory;
import eu.heliovo.ics.common.dao.exception.DetailsNotFoundException;
import eu.heliovo.ics.common.dao.exception.InstrumentsDetailsNotSavedException;
import eu.heliovo.ics.common.dao.interfaces.InstrumentDao;
import eu.heliovo.ics.common.dao.interfaces.InstrumentOperationPeriodDao;
import eu.heliovo.ics.common.dao.interfaces.ShortNameQueryDao;
import eu.heliovo.ics.common.model.hibernate.InsOperationPeriodTb;
import eu.heliovo.ics.common.model.hibernate.InstrumentsMainTb;
import eu.heliovo.ics.common.transfer.CommonResultTO;
import eu.heliovo.ics.common.transfer.CommonTO;
import eu.heliovo.ics.common.transfer.InstrumentOperationPeriodTO;
import eu.heliovo.ics.common.transfer.InstrumentsTO;
import eu.heliovo.ics.common.util.ConstantKeywords;
import eu.heliovo.ics.common.util.HibernateSessionFactory;


public class InstrumentOperationPeriodDaoImpl implements InstrumentOperationPeriodDao { 
	

	public List<CommonTO> getInstrumentNames(String insId){
		try{
			HashMap<String,String> params = new HashMap<String,String>();
			params.put("kwinsid", insId);
			List<CommonTO> commonTO = new ArrayList<CommonTO>();
			 Object[] inArray = null;
			 ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
			 CommonResultTO result=shortNameDao.getSNQueryResult("select * from INSTRUMENTS where INS_ID like '%"+insId+"%'",null,0,-1);
			 Object[] objArr = result.getResult();;
			if(objArr!=null)
			{				
				for(int i =0;i<objArr.length;i++)
				{
					Object[] arrObj=(Object[])objArr[i];
					CommonTO insNameTO=new CommonTO();
					insNameTO.setInsId(arrObj[1].toString().trim());
					insNameTO.setInsName(arrObj[1].toString().trim());
					commonTO.add(insNameTO);
				}
			}
			return commonTO;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public List<CommonTO> getInstrumentDescription(String insId){
		try{
			HashMap<String,String> params = new HashMap<String,String>();
			params.put("kwinsid", insId);
			List<CommonTO> commonTO = new ArrayList<CommonTO>();
			 Object[] inArray = null;
			 ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
			 CommonResultTO result=shortNameDao.getSNQueryResult("select * from INSTRUMENTS where INS_ID like '%"+insId+"%'",null,0,-1);
			 Object[] objArr = result.getResult();;
			if(objArr!=null)
			{				
				for(int i =0;i<objArr.length;i++)
				{
					Object[] arrObj=(Object[])objArr[i];
					CommonTO insNameTO=new CommonTO();
					insNameTO.setInsDesId(arrObj[3].toString().trim());
					insNameTO.setInsDesName(arrObj[3].toString().trim());
					commonTO.add(insNameTO);
				}
			}
			return commonTO;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void saveInstrumentOperationPeriodDetails(InstrumentOperationPeriodTO insTO) throws InstrumentsDetailsNotSavedException
	{		
		try{
			DateFormat myDateFormat = new SimpleDateFormat(ConstantKeywords.Hrs24DATEFORMAT) ;
			Session session = HibernateSessionFactory.getSession();	
			InsOperationPeriodTb insOpsMainTb=new InsOperationPeriodTb();
			insOpsMainTb.setInsId(insTO.getInsId());
			insOpsMainTb.setOperationType(insTO.getInsOperationType());
			insOpsMainTb.setInsName(insTO.getInsName());
			insOpsMainTb.setInsStartDate(myDateFormat.parse(insTO.getInsStartDate()));
			insOpsMainTb.setInsEndDate(myDateFormat.parse(insTO.getInsEndDate()));
			session.saveOrUpdate(insOpsMainTb);			
		}catch(Exception e){
			throw new InstrumentsDetailsNotSavedException("Exception : ",e);
		}
		
	}
	
	public InstrumentOperationPeriodTO editInstrumentOperationPeriodDetails(int insID) throws DetailsNotFoundException
	{
		try{
		InstrumentOperationPeriodTO instrumentTo=new InstrumentOperationPeriodTO();
	     Object[] inArray = null;
		 ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		 CommonResultTO result=shortNameDao.getSNQueryResult("select * from INS_OPERATION_PERIOD where ID="+insID,null,0,1);
		 Object[] objArr = result.getResult();
		 if(objArr!=null){ 			
			for(int i=0;i<objArr.length;i++)
			{
				inArray = (Object[])objArr[i];				
				instrumentTo.setId(Integer.parseInt(inArray[0].toString()));
				instrumentTo.setInsId((String)inArray[1]);
				instrumentTo.setInsOperationType((String)inArray[2]);
	            instrumentTo.setInsName((String)inArray[3]);	           
                instrumentTo.setInsStartDate(inArray[4].toString().substring(0, 10).replace("-", "/"));
                instrumentTo.setInsStartHour(inArray[4].toString().substring(11, 13));
                instrumentTo.setInsStartMin(inArray[4].toString().substring(14, 16));
                instrumentTo.setInsEndDate(inArray[5].toString().substring(0, 10).replace("-", "/"));
                instrumentTo.setInsEndHour(inArray[5].toString().substring(11, 13));
                instrumentTo.setInsEndMin(inArray[5].toString().substring(14, 16));
			}
		 }
		 return instrumentTo;
		}catch(Exception ex)
		{			
			throw new DetailsNotFoundException("Exception ",ex);
		}
		
	}
	
	public void updateInstrumentOperationPeriodDetails(InstrumentOperationPeriodTO insTO) throws InstrumentsDetailsNotSavedException
	{
		try{
			DateFormat myDateFormat = new SimpleDateFormat(ConstantKeywords.Hrs24DATEFORMAT) ;
			Session session = HibernateSessionFactory.getSession();	
			String sQuery="update InsOperationPeriodTb as INS set INS.insId=:insId,INS.insName=:insName,INS.operationType=:operationType,INS.insStartDate=:insStartDate,INS.insEndDate=:insEndDate  " +
			"where INS.id=:id" ;
			Query query=session.createQuery(sQuery);
			query.setString("insId", insTO.getInsId());
			query.setString("insName", insTO.getInsName());
			query.setString("operationType", insTO.getInsOperationType());
			query.setString("insStartDate", insTO.getInsStartDate());
			query.setString("insEndDate", insTO.getInsEndDate());
			query.setInteger("id", insTO.getId());
			query.executeUpdate();
		}catch(Exception e){
			throw new InstrumentsDetailsNotSavedException("Exception : ",e);
		}
	}
	
	
}