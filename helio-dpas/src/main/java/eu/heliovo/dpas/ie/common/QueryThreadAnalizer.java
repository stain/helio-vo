package eu.heliovo.dpas.ie.common;

import eu.heliovo.dpas.ie.classad.ClassAdMapperException;
import eu.heliovo.dpas.ie.classad.ClassAdUtilitiesException;
import eu.heliovo.dpas.ie.controller.ServiceEngine;


public class QueryThreadAnalizer extends Thread
{	
	private CommonTO comCriteriaTO;
	/** The service engine. */
	ServiceEngine serviceEngine = null;

	public QueryThreadAnalizer(CommonTO comCriTO){
		comCriteriaTO=comCriTO;
		try 
		{
			serviceEngine = new ServiceEngine();
		} 
		catch (ArithmeticException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassAdMapperException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassAdUtilitiesException e) 
		{
			e.printStackTrace();
		}
	}
	public void run(){
		  			
			try {		
				
				serviceEngine.executeQuery(comCriteriaTO.getPrintWriter(),comCriteriaTO.getInstruments(), 
						comCriteriaTO.getStartTimes(), 
						comCriteriaTO.getStopTimes(), 
						comCriteriaTO.isPartialSorting(),
						comCriteriaTO.getDataTypes(), 
						comCriteriaTO.getDataLevels(),comCriteriaTO.isVotable());
				
			}
			catch (Exception e) {			
				e.printStackTrace();
			}
		
	 }
}
