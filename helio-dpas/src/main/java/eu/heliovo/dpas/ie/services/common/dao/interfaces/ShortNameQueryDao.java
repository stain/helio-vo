/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.common.dao.interfaces;

import eu.heliovo.dpas.ie.services.common.dao.exception.DetailsNotFoundException;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.common.transfer.ResultTO;


public interface ShortNameQueryDao {

	public ResultTO[] getAccessTableBasedOnInst(String strIns,String prvdType) throws DetailsNotFoundException;
	public ResultTO[] getAccessTableDetails(String where) throws DetailsNotFoundException;
	public void generateVOTable(CommonTO commonTO) throws Exception;
	public void loadProviderAccessTable(String fileName,String tableName) throws DetailsNotFoundException;
	public ResultTO[] getFtpAccessTableBasedOnInst(String fileName) throws DetailsNotFoundException;
	public void generatePatVOTable(CommonTO commonTO) throws Exception;
}
