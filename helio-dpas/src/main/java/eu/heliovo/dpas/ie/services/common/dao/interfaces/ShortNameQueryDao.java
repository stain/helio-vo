/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.common.dao.interfaces;

import java.sql.Connection;
import java.util.HashMap;

import eu.heliovo.dpas.ie.services.cdaweb.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.cdaweb.transfer.CdaWebDataTO;
import eu.heliovo.dpas.ie.services.common.dao.exception.DetailsNotFoundException;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.common.transfer.ResultTO;


public interface ShortNameQueryDao {

	public ResultTO[] getAccessTableBasedOnInst(String fileName) throws DetailsNotFoundException;
	public ResultTO[] getAccessTableDetails() throws DetailsNotFoundException;
	public void generateVOTable(CommonTO commonTO) throws Exception;
	public void loadProviderAccessTable(String fileName) throws DetailsNotFoundException;
}
