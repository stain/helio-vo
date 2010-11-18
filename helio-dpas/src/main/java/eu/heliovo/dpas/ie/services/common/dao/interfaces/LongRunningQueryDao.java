/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.common.dao.interfaces;

import eu.heliovo.dpas.ie.services.common.dao.exception.DetailsNotFoundException;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;

public interface LongRunningQueryDao {
	
		public void insertStatusToHsqlDB(String randomUUIDString,String status) throws DetailsNotFoundException; 
	    public void insertURLToHsqlDB(String randomUUIDString,String url) throws DetailsNotFoundException;
		public String getStatusFromHsqlDB(String randomUUIDString) throws DetailsNotFoundException;
		public String getUrlFromHsqlDB(String randomUUIDString) throws DetailsNotFoundException;
		public void generatelongRunningQueryXML(CommonTO commonTO) throws Exception;
}
