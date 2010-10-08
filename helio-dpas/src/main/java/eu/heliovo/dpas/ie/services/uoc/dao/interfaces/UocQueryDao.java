/* #ident	"%W%" */
package eu.heliovo.dpas.ie.services.uoc.dao.interfaces;


import eu.heliovo.dpas.ie.services.common.dao.interfaces.DPASDataProvider;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.uoc.dao.exception.DataNotFoundException;
import eu.heliovo.dpas.ie.services.uoc.transfer.UocDataTO;

public interface UocQueryDao extends DPASDataProvider {

	void query(CommonTO commonTO) throws Exception;

	void generateVOTable(UocDataTO vsoTO) throws DataNotFoundException,Exception;
}
