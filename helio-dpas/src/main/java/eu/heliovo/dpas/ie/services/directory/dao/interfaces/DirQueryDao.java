/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.directory.dao.interfaces;

import eu.heliovo.dpas.ie.services.common.dao.interfaces.DPASDataProvider;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.directory.transfer.DirDataTO;
import eu.heliovo.dpas.ie.services.vso.dao.exception.DataNotFoundException;

public interface DirQueryDao extends DPASDataProvider {

	//public	void query(String instrument,  String dateFrom, String dateTo, int maxResults,String url,Writer output,String providerName) throws Exception;
	public void query(CommonTO commonTO) throws Exception;
	public void generateVOTable(DirDataTO dirTO) throws DataNotFoundException,Exception;

}
