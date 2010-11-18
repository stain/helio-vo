package eu.heliovo.dpas.ie.services.common.utils;

import eu.heliovo.dpas.ie.services.CommonDaoFactory;
import eu.heliovo.dpas.ie.services.common.dao.interfaces.ShortNameQueryDao;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;



public class RunService implements Runnable {
	private String statusValue="PENDING";
	private boolean isCompleted=false;
	private String errorDes=null;
	CommonTO comCriteriaTO=null;
	String randomUUIDString=null;
	
	public RunService(CommonTO comCriteriaTO,String uuid)
	{
		this.randomUUIDString=uuid;
		this.comCriteriaTO = comCriteriaTO;
	}
	
	public void run()
	{
		try{
			System.out.println(" Thread executing... ");
			LongRunningQueryIdHolders.getInstance().setProperty(randomUUIDString, statusValue+"");
			//Calling DAO to generate VOTable.
			ShortNameQueryDao shortNameDao= CommonDaoFactory.getInstance().getShortNameQueryDao();
		    shortNameDao.generateVOTable(comCriteriaTO);
			System.out.println(" VOTable created succesfully! ");
			isCompleted=true;
		}
		catch(Exception ex)
		{
			System.out.println("Exception ex:"+ex);
			errorDes=ex.getMessage();
			isCompleted=false;
		}
		//if it is completed.
		if(isCompleted){
			try {
				//Inserting status into HSQL database.
				System.out.println(" Adding Status in to database... ");
				HsqlDbUtils.getInstance().insertStatusIntoHsqlDB(randomUUIDString, "COMPLETED");
				//Removing status from instance holder.
				LongRunningQueryIdHolders.getInstance().removeProperty(randomUUIDString);
				//Inserting URL into HSQLDB.
				System.out.println(" Adding URL in to database... ");
				HsqlDbUtils.getInstance().insertURLToHsqlDB(randomUUIDString, comCriteriaTO.getSaveto()+"/votable_"+randomUUIDString+".xml");
				System.out.println(" Done. Returning response..!!! ");
			} catch (Exception e) {
				LongRunningQueryIdHolders.getInstance().removeProperty(randomUUIDString);
				
			}
		}else{
			try {
				//Inserting status into HSQL database
				HsqlDbUtils.getInstance().insertStatusIntoHsqlDB(randomUUIDString, "ERROR::"+errorDes);
				LongRunningQueryIdHolders.getInstance().removeProperty(randomUUIDString);
			} catch (Exception e) {
				//Removing status from instance holder.
				LongRunningQueryIdHolders.getInstance().removeProperty(randomUUIDString);
			}
			
		}
		
	}
	
	public String getReturnStatus()
	{
		return statusValue;
	}
	public boolean isCompleted()
	{
		return isCompleted;	
	}
	
}
