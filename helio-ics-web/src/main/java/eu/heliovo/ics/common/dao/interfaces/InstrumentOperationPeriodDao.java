/* #ident	"%W%" */
package eu.heliovo.ics.common.dao.interfaces;

import java.util.HashMap;
import java.util.List;

import eu.heliovo.ics.common.dao.exception.DetailsNotFoundException;
import eu.heliovo.ics.common.dao.exception.InstrumentsDetailsNotSavedException;
import eu.heliovo.ics.common.transfer.CommonTO;
import eu.heliovo.ics.common.transfer.InstrumentOperationPeriodTO;
import eu.heliovo.ics.common.transfer.InstrumentsTO;
import eu.heliovo.ics.common.transfer.criteriaTO.InstrumentCriteriaTO;
 
public interface InstrumentOperationPeriodDao {
	public void saveInstrumentOperationPeriodDetails(InstrumentOperationPeriodTO insTO) throws InstrumentsDetailsNotSavedException;
	public InstrumentOperationPeriodTO editInstrumentOperationPeriodDetails(int insID) throws DetailsNotFoundException;
	public void updateInstrumentOperationPeriodDetails(InstrumentOperationPeriodTO insTO) throws InstrumentsDetailsNotSavedException;
	public List<CommonTO> getInstrumentNames(String insId);
	public List<CommonTO> getInstrumentDescription(String insId);
}
