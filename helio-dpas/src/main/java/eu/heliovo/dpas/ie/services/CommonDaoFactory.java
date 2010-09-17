/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services;

import eu.heliovo.dpas.ie.common.DAOFactory;
import eu.heliovo.dpas.ie.services.cdaweb.dao.impl.CdaWebQueryDaoImpl;
import eu.heliovo.dpas.ie.services.cdaweb.dao.interfaces.CdaWebQueryDao;
import eu.heliovo.dpas.ie.services.uoc.dao.impl.UocQueryDaoImpl;
import eu.heliovo.dpas.ie.services.uoc.dao.interfaces.UocQueryDao;
import eu.heliovo.dpas.ie.services.vso.dao.impl.VsoQueryDaoImpl;
import eu.heliovo.dpas.ie.services.vso.dao.interfaces.VsoQueryDao;

public class CommonDaoFactory extends DAOFactory {

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
	
	@Override
	public VsoQueryDao getVsoQueryDao(){
		return new VsoQueryDaoImpl();
	}
	
	@Override
	public UocQueryDao getUocQueryDao(){
		return new UocQueryDaoImpl();
	}

	@Override
	public CdaWebQueryDao getCdaWebQueryDao() {
		return new CdaWebQueryDaoImpl();
	}
	
}
