/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.vso.dao;

import eu.heliovo.dpas.ie.common.DAOFactory;
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

	public VsoQueryDao getVsoQueryDao(){
		return new VsoQueryDaoImpl();
	}
	
}
