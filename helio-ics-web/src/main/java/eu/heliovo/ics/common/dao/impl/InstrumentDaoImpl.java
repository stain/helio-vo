/* #ident	"%W%" */
package eu.heliovo.ics.common.dao.impl;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.hibernate.Session;

import org.hibernate.Query;
import eu.heliovo.ics.common.dao.CommonDaoFactory;
import eu.heliovo.ics.common.dao.exception.DetailsNotFoundException;
import eu.heliovo.ics.common.dao.exception.InstrumentsDetailsNotSavedException;
import eu.heliovo.ics.common.dao.interfaces.InstrumentDao;
import eu.heliovo.ics.common.dao.interfaces.ShortNameQueryDao;
import eu.heliovo.ics.common.model.hibernate.InstrumentsMainTb;
import eu.heliovo.ics.common.transfer.CommonResultTO;
import eu.heliovo.ics.common.transfer.InstrumentsTO;
import eu.heliovo.ics.common.util.ConstantKeywords;
import eu.heliovo.ics.common.util.HibernateSessionFactory;


public class InstrumentDaoImpl implements InstrumentDao { 

	public void saveInstrumentDetails(InstrumentsTO insTO) throws InstrumentsDetailsNotSavedException
	{		
		try{
			DateFormat myDateFormat = new SimpleDateFormat(ConstantKeywords.Hrs24DATEFORMAT) ;
			Session session = HibernateSessionFactory.getSession();	
			InstrumentsMainTb insMainTb=new InstrumentsMainTb();
			insMainTb.setInsId(insTO.getInsId());
			insMainTb.setInsObs(insTO.getInsObs());
			insMainTb.setInsName(insTO.getInsName());
			insMainTb.setInsStartDate(myDateFormat.parse(insTO.getInsStartDate()));
			insMainTb.setInsEndDate(myDateFormat.parse(insTO.getInsEndDate()));
			session.saveOrUpdate(insMainTb);			
		}catch(Exception e){
			throw new InstrumentsDetailsNotSavedException("Exception : ",e);
		}
		
	}
	
	public InstrumentsTO editInstrumentDetails(int insID) throws DetailsNotFoundException
	{
		try{
	     InstrumentsTO instrumentTo=new InstrumentsTO();
	     Object[] inArray = null;
		 ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		 CommonResultTO result=shortNameDao.getSNQueryResult("select * from INSTRUMENTS where ID="+insID,null,0,1);
		 Object[] objArr = result.getResult();
		 if(objArr!=null){ 			
			for(int i=0;i<objArr.length;i++)
			{
				inArray = (Object[])objArr[i];				
				instrumentTo.setId(Integer.parseInt(inArray[0].toString()));
				instrumentTo.setInsId((String)inArray[1]);
				instrumentTo.setInsObs((String)inArray[2]);
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
	
	public void updateInstrumentDetails(InstrumentsTO insTO) throws InstrumentsDetailsNotSavedException
	{
		try{
			DateFormat myDateFormat = new SimpleDateFormat(ConstantKeywords.Hrs24DATEFORMAT) ;
			Session session = HibernateSessionFactory.getSession();	
			String sQuery="update InstrumentsMainTb as INS set INS.insId=:insId,INS.insName=:insName,INS.insObs=:insObs,INS.insStartDate=:insStartDate,INS.insEndDate=:insEndDate  " +
			"where INS.id=:id" ;
			Query query=session.createQuery(sQuery);
			query.setString("insId", insTO.getInsId());
			query.setString("insName", insTO.getInsName());
			query.setString("insObs", insTO.getInsObs());
			query.setString("insStartDate", insTO.getInsStartDate());
			query.setString("insEndDate", insTO.getInsEndDate());
			query.setInteger("id", insTO.getId());
			query.executeUpdate();
		}catch(Exception e){
			throw new InstrumentsDetailsNotSavedException("Exception : ",e);
		}
	}
	
	
}