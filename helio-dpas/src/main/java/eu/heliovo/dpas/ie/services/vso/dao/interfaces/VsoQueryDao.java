/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.vso.dao.interfaces;

import java.io.Writer;
import java.util.Calendar;

import eu.heliovo.dpas.ie.dataProviders.DPASDataProvider;
import eu.heliovo.dpas.ie.services.vso.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.vso.transfer.VsoDataTO;


public interface VsoQueryDao extends DPASDataProvider {

	//public	void query(String instrument,  String dateFrom, String dateTo, int maxResults,String url,Writer output,String providerName) throws Exception;
	public	void query(VsoDataTO vsoTO) throws Exception;
	public void generateVOTable(VsoDataTO vsoTO) throws DataNotFoundException,Exception;

}
