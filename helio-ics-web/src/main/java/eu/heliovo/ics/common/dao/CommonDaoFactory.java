/* #ident	"%W%" */
package eu.heliovo.ics.common.dao;

import eu.heliovo.ics.common.dao.impl.CommonDaoImpl;
import eu.heliovo.ics.common.dao.impl.InstrumentDaoImpl;
import eu.heliovo.ics.common.dao.impl.InstrumentOperationPeriodDaoImpl;
import eu.heliovo.ics.common.dao.impl.ObservatoryDaoImpl;
import eu.heliovo.ics.common.dao.impl.ShortNameQueryDaoImpl;
import eu.heliovo.ics.common.dao.interfaces.CommonDao;
import eu.heliovo.ics.common.dao.interfaces.InstrumentDao; 
import eu.heliovo.ics.common.dao.interfaces.InstrumentOperationPeriodDao;
import eu.heliovo.ics.common.dao.interfaces.ObservatoryDao;
import eu.heliovo.ics.common.dao.interfaces.ShortNameQueryDao;



public class CommonDaoFactory {

	private static CommonDaoFactory instance = null;
	
	private CommonDaoFactory(){
		//Constructor
	}

	public static CommonDaoFactory getInstance(){
		if(instance==null){
			instance = new CommonDaoFactory();
		}
		return instance;
	}

	public CommonDao getCommonDAO(){
		return new CommonDaoImpl();
	}
	public ShortNameQueryDao getShortNameQueryDao(){
		return new ShortNameQueryDaoImpl();
	}
	public InstrumentDao getInstrumentDao(){
		return new InstrumentDaoImpl();
	}
	public InstrumentOperationPeriodDao getInstrumentOperationPeriodDao(){
		return new InstrumentOperationPeriodDaoImpl();
	}
	public ObservatoryDao getObservatoryDao(){
		return new ObservatoryDaoImpl();
	}
	
}
