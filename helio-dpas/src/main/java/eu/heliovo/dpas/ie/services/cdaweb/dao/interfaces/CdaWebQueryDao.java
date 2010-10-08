/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.cdaweb.dao.interfaces;

import java.io.Writer;
import java.util.Calendar;
import eu.heliovo.dpas.ie.services.cdaweb.transfer.CdaWebDataTO;
import eu.heliovo.dpas.ie.services.common.dao.interfaces.DPASDataProvider;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.vso.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.vso.transfer.VsoDataTO;


public interface CdaWebQueryDao extends DPASDataProvider {

	//public	void query(String instrument,  String dateFrom, String dateTo, int maxResults,String url,Writer output,String providerName) throws Exception;
	public void query(CommonTO commonTO) throws Exception;
	public void generateVOTable(CdaWebDataTO cdaWebTO) throws DataNotFoundException,Exception;

}
