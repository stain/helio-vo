/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.hqi.dao.interfaces;


import eu.heliovo.dpas.ie.services.common.dao.interfaces.DPASDataProvider;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.hqi.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.hqi.transfer.HqiDataTO;

public interface HqiQueryDao extends DPASDataProvider {

	void query(CommonTO commonTO) throws Exception;

	void generateVOTable(HqiDataTO hqiTO) throws DataNotFoundException,Exception;
}
