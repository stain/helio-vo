/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.common.dao.interfaces;

import eu.heliovo.dpas.ie.services.common.dao.exception.DetailsNotFoundException;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.common.transfer.ResultTO;


public interface ShortNameQueryDao {

	public ResultTO[] getAccessTableBasedOnInst(String fileName,String prvdType) throws DetailsNotFoundException;
	public ResultTO[] getAccessTableDetails() throws DetailsNotFoundException;
	public void generateVOTable(CommonTO commonTO) throws Exception;
	public void loadProviderAccessTable(String fileName,String tableName) throws DetailsNotFoundException;
	public ResultTO[] getFtpAccessTableBasedOnInst(String fileName) throws DetailsNotFoundException;
}
