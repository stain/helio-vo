/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.soda.dao.interfaces;

import eu.heliovo.dpas.ie.services.common.dao.interfaces.DPASDataProvider;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.soda.transfer.SoteriaDataTO;
import eu.heliovo.dpas.ie.services.vso.dao.exception.DataNotFoundException;


public interface SoteriaQueryDao extends DPASDataProvider {

	public void query(CommonTO commonTO) throws Exception;
	public void generateVOTable(SoteriaDataTO soteriaTO) throws DataNotFoundException,Exception;

}
