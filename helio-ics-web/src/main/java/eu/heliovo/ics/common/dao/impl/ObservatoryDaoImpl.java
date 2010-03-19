/* #ident	"%W%" */
package eu.heliovo.ics.common.dao.impl;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.hibernate.Session;

import org.hibernate.Query;
import eu.heliovo.ics.common.dao.CommonDaoFactory;
import eu.heliovo.ics.common.dao.exception.DetailsNotFoundException;
import eu.heliovo.ics.common.dao.exception.InstrumentsDetailsNotSavedException;
import eu.heliovo.ics.common.dao.exception.ObservatoryDetailsNotSavedException;
import eu.heliovo.ics.common.dao.interfaces.InstrumentDao;
import eu.heliovo.ics.common.dao.interfaces.ObservatoryDao;
import eu.heliovo.ics.common.dao.interfaces.ShortNameQueryDao;
import eu.heliovo.ics.common.model.hibernate.InstrumentsMainTb;
import eu.heliovo.ics.common.model.hibernate.ObservatoryMainTb;
import eu.heliovo.ics.common.transfer.CommonResultTO;
import eu.heliovo.ics.common.transfer.InstrumentsTO;
import eu.heliovo.ics.common.transfer.ObservatoryTO;
import eu.heliovo.ics.common.util.ConstantKeywords;
import eu.heliovo.ics.common.util.HibernateSessionFactory;


public class ObservatoryDaoImpl implements ObservatoryDao { 

	public void saveObservatoryDetails(ObservatoryTO obsTO) throws ObservatoryDetailsNotSavedException
	{		
		try{
			DateFormat myDateFormat = new SimpleDateFormat(ConstantKeywords.Hrs24DATEFORMAT) ;
			Session session = HibernateSessionFactory.getSession();	
			ObservatoryMainTb obsMainTb=new ObservatoryMainTb();
			obsMainTb.setObsId(obsTO.getObsId());			
			obsMainTb.setObsName(obsTO.getObsName());
			obsMainTb.setObsStartDate(myDateFormat.parse(obsTO.getObsStartDate()));
			obsMainTb.setObsEndDate(myDateFormat.parse(obsTO.getObsEndDate()));
			obsMainTb.setObsType(obsTO.getObsType());
			obsMainTb.setObsFirstPosition(obsTO.getObsFirstPosition());
			obsMainTb.setObsSecondPosition(obsTO.getObsSecondPosition());
			obsMainTb.setObsOperationType(obsTO.getObsOperationType());
			session.saveOrUpdate(obsMainTb);			
		}catch(Exception e){ 
			throw new ObservatoryDetailsNotSavedException("Exception : ",e);
		}
		
	}
	
	public ObservatoryTO editObservatoryDetails(int obsID) throws DetailsNotFoundException
	{
		try{
		 ObservatoryTO observatoryTo=new ObservatoryTO();
	     Object[] inArray = null;
		 ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		 CommonResultTO result=shortNameDao.getSNQueryResult("select * from OBSERVATORY where ID="+obsID,null,0,1);
		 Object[] objArr = result.getResult();
		 if(objArr!=null){ 			
			for(int i=0;i<objArr.length;i++)
			{
				inArray = (Object[])objArr[i];				
				observatoryTo.setId(Integer.parseInt(inArray[0].toString()));
				observatoryTo.setObsId((String)inArray[1]);
				observatoryTo.setObsName((String)inArray[2]);
	            observatoryTo.setObsType((String)inArray[3]);
	            observatoryTo.setObsFirstPosition((String)inArray[4]);
	            observatoryTo.setObsSecondPosition((String)inArray[5]);	        
                observatoryTo.setObsStartDate(inArray[6].toString().substring(0, 10).replace("-", "/"));
                observatoryTo.setObsStartHour(inArray[6].toString().substring(11, 13));
                observatoryTo.setObsStartMin(inArray[6].toString().substring(14, 16));
                observatoryTo.setObsEndDate(inArray[7].toString().substring(0, 10).replace("-", "/"));
                observatoryTo.setObsEndHour(inArray[7].toString().substring(11, 13));
                observatoryTo.setObsEndMin(inArray[7].toString().substring(14, 16));
                observatoryTo.setObsOperationType((String)inArray[8]);
			}
		 }
		 return observatoryTo;
		}catch(Exception ex)
		{			
			throw new DetailsNotFoundException("Exception ",ex);
		}
		
	}
	
	public void updateObservatoryDetails(ObservatoryTO obsTO) throws ObservatoryDetailsNotSavedException
	{
		try{			
			Session session = HibernateSessionFactory.getSession();	
			String sQuery="update ObservatoryMainTb as OBS set OBS.obsId=:obsId,OBS.obsName=:obsName,OBS.obsType=:obsType,OBS.obsStartDate=:obsStartDate,OBS.obsEndDate=:obsEndDate"+
			",OBS.obsFirstPosition=:obsFirstPosition,OBS.obsSecondPosition=:obsSecondPosition,OBS.obsOperationType=:obsOperationType " +
			"where OBS.id=:id" ;
			Query query=session.createQuery(sQuery);
			query.setString("obsId", obsTO.getObsId());
			query.setString("obsName", obsTO.getObsName());
			query.setString("obsType", obsTO.getObsType());
			query.setString("obsFirstPosition", obsTO.getObsFirstPosition());
			query.setString("obsSecondPosition", obsTO.getObsSecondPosition());
			query.setString("obsStartDate", obsTO.getObsStartDate());
			query.setString("obsEndDate", obsTO.getObsEndDate());
			query.setString("obsOperationType", obsTO.getObsOperationType());
			query.setInteger("id", obsTO.getId());
			query.executeUpdate();
		}catch(Exception e){
			throw new ObservatoryDetailsNotSavedException("Exception : ",e);
		}
	}
	
	
}