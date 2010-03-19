/* #ident	"%W%" */
package eu.heliovo.ics.common.dao.impl;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import eu.heliovo.ics.common.dao.CommonDaoFactory;
import eu.heliovo.ics.common.dao.exception.DetailsNotFoundException;
import eu.heliovo.ics.common.dao.interfaces.CommonDao;
import eu.heliovo.ics.common.dao.interfaces.ShortNameQueryDao;
import eu.heliovo.ics.common.transfer.CommonResultTO;
import eu.heliovo.ics.common.transfer.InstrumentOperationPeriodTO;
import eu.heliovo.ics.common.transfer.InstrumentsTO;
import eu.heliovo.ics.common.transfer.ObservatoryTO;
import eu.heliovo.ics.common.transfer.criteriaTO.InstrumentCriteriaTO;
import eu.heliovo.ics.common.transfer.criteriaTO.InstrumentOperationPeriodCriteriaTO;
import eu.heliovo.ics.common.transfer.criteriaTO.ObservatoryCriteriaTO;

public class CommonDaoImpl implements CommonDao { 

	public InstrumentCriteriaTO getInstrumentDetails(InstrumentCriteriaTO insCriteriaTO) throws DetailsNotFoundException {
		Object[] inArray = null;
		InstrumentsTO[]  instrumentTo = null;			
		try{			
			 if(insCriteriaTO.getInsName()!=null && !insCriteriaTO.getInsName().trim().equals(""))
		 		 insCriteriaTO.setQuery("select * from INSTRUMENTS where INS_NAME like '%"+insCriteriaTO.getInsName()+"%'");
		     else
		    	 insCriteriaTO.setQuery("select * from INSTRUMENTS where INS_NAME like '%%'");
			 ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
			 CommonResultTO result=shortNameDao.getSNQueryResult(insCriteriaTO.getQuery(),null,insCriteriaTO.getIPageNumber(),insCriteriaTO.getIRowsPerPage());
			 Object[] objArr = result.getResult();
			 if(objArr!=null){ 			
				 instrumentTo = new InstrumentsTO[objArr.length];
					for(int i=0;i<objArr.length;i++)
					{
						instrumentTo[i]=new InstrumentsTO();
						inArray = (Object[])objArr[i];
						instrumentTo[i]=new InstrumentsTO();
						instrumentTo[i].setId(Integer.parseInt(inArray[0].toString()));
						instrumentTo[i].setInsId((String)inArray[1]);
						instrumentTo[i].setInsObs((String)inArray[2]);
			            instrumentTo[i].setInsName((String)inArray[3]);
			            System.out.println(" +++++++++++++ Start Date ++++++++++"+inArray[4].toString());
			            System.out.println(" +++++++++++++ End Date ++++++++++"+inArray[5].toString());
                        instrumentTo[i].setInsStartDate(inArray[4].toString());
                        instrumentTo[i].setInsEndDate(inArray[5].toString());                 
					}
			 }
			 int icount =  result.getCount();
			 int noOfRows = insCriteriaTO.getIRowsPerPage();
			insCriteriaTO.setNoOfPages((icount%noOfRows == 0)? icount/noOfRows : (icount/noOfRows)+1 );
			insCriteriaTO.setNoOfRecords(icount);
			insCriteriaTO.setInsDetailsTO(instrumentTo);
			return insCriteriaTO;
		}catch(Exception ex)
		{			
			throw new DetailsNotFoundException("Exception ",ex);
		}
		
	}

	
	public ObservatoryCriteriaTO getObservatoryDetails(ObservatoryCriteriaTO obsCriteriaTO) throws DetailsNotFoundException
	{
		Object[] inArray = null;
		ObservatoryTO[]  obervatoryTo = null;		 
		try{			
			if(obsCriteriaTO.getObsName()!=null && !obsCriteriaTO.getObsName().trim().equals(""))
				obsCriteriaTO.setQuery("select * from OBSERVATORY where OBS_NAME like '%"+obsCriteriaTO.getObsName()+"%'");
	    	 else
	    		 obsCriteriaTO.setQuery("select * from OBSERVATORY where OBS_NAME like '%%'");
			 ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
			 CommonResultTO result=shortNameDao.getSNQueryResult(obsCriteriaTO.getQuery(),null,obsCriteriaTO.getIPageNumber(),obsCriteriaTO.getIRowsPerPage());
			 Object[] objArr = result.getResult();
			 if(objArr!=null){ 			
				 obervatoryTo = new ObservatoryTO[objArr.length];
					for(int i=0;i<objArr.length;i++)
					{
						obervatoryTo[i]=new ObservatoryTO();
						inArray = (Object[])objArr[i];
						obervatoryTo[i]=new ObservatoryTO();
						obervatoryTo[i].setId(Integer.parseInt(inArray[0].toString()));
						obervatoryTo[i].setObsId((String)inArray[1]);
						obervatoryTo[i].setObsName((String)inArray[2]);
			            obervatoryTo[i].setObsType((String)inArray[3]);
			            System.out.println(" +++++++++++++ Start Date ++++++++++"+inArray[4].toString());
			            System.out.println(" +++++++++++++ End Date ++++++++++"+inArray[5].toString());
			            obervatoryTo[i].setObsFirstPosition((String)inArray[4]);
			            obervatoryTo[i].setObsSecondPosition((String)inArray[5]);
                        obervatoryTo[i].setObsStartDate(inArray[6].toString());
                        obervatoryTo[i].setObsEndDate(inArray[7].toString()); 
                        obervatoryTo[i].setObsOperationType(inArray[8].toString());
                        
					}
			 }
			 int icount =  result.getCount();
			 int noOfRows = obsCriteriaTO.getIRowsPerPage();
			obsCriteriaTO.setNoOfPages((icount%noOfRows == 0)? icount/noOfRows : (icount/noOfRows)+1 );
			obsCriteriaTO.setNoOfRecords(icount);
			obsCriteriaTO.setObsDetailsTO(obervatoryTo);
			return obsCriteriaTO;
		}catch(Exception ex)
		{			
			throw new DetailsNotFoundException("Exception ",ex);
		}
	}
	
	public InstrumentOperationPeriodCriteriaTO getInstrumentOperationPeriodDetails(InstrumentOperationPeriodCriteriaTO insOpsPeriodCriteriaTO) throws DetailsNotFoundException {
		Object[] inArray = null;
		InstrumentOperationPeriodTO[]  insOpsPeriodTo = null;		
		if(insOpsPeriodCriteriaTO.getInsName()!=null && !insOpsPeriodCriteriaTO.getInsName().trim().equals(""))
	 		 insOpsPeriodCriteriaTO.setQuery("select * from INS_OPERATION_PERIOD where INS_NAME like '%"+insOpsPeriodCriteriaTO.getInsName()+"%'");
	    else
	    	 insOpsPeriodCriteriaTO.setQuery("select * from INS_OPERATION_PERIOD where INS_NAME like '%%'");
		try{			
			 ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
			 CommonResultTO result=shortNameDao.getSNQueryResult(insOpsPeriodCriteriaTO.getQuery(),null,insOpsPeriodCriteriaTO.getIPageNumber(),insOpsPeriodCriteriaTO.getIRowsPerPage());
			 Object[] objArr = result.getResult();
			 if(objArr!=null){ 			
				 insOpsPeriodTo = new InstrumentOperationPeriodTO[objArr.length];
					for(int i=0;i<objArr.length;i++)
					{
						insOpsPeriodTo[i]=new InstrumentOperationPeriodTO();
						inArray = (Object[])objArr[i];
						insOpsPeriodTo[i]=new InstrumentOperationPeriodTO();
						insOpsPeriodTo[i].setId(Integer.parseInt(inArray[0].toString()));
						insOpsPeriodTo[i].setInsId((String)inArray[1]);
						insOpsPeriodTo[i].setInsOperationType((String)inArray[2]);
						insOpsPeriodTo[i].setInsName((String)inArray[3]);
			            System.out.println(" +++++++++++++ Start Date ++++++++++"+inArray[4].toString());
			            System.out.println(" +++++++++++++ End Date ++++++++++"+inArray[5].toString());
			            insOpsPeriodTo[i].setInsStartDate(inArray[4].toString());
			            insOpsPeriodTo[i].setInsEndDate(inArray[5].toString());                 
					}
			 }
			 int icount =  result.getCount();
			 int noOfRows = insOpsPeriodCriteriaTO.getIRowsPerPage();
			 insOpsPeriodCriteriaTO.setNoOfPages((icount%noOfRows == 0)? icount/noOfRows : (icount/noOfRows)+1 );
			 insOpsPeriodCriteriaTO.setNoOfRecords(icount);
			 insOpsPeriodCriteriaTO.setInsOpsPeriodDetailsTO(insOpsPeriodTo);
			return insOpsPeriodCriteriaTO;
		}catch(Exception ex)
		{			
			throw new DetailsNotFoundException("Exception ",ex);
		}
		
	}
	
}