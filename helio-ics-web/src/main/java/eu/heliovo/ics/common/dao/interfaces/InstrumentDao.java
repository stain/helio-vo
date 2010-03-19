/* #ident	"%W%" */
package eu.heliovo.ics.common.dao.interfaces;

import java.util.HashMap;

import eu.heliovo.ics.common.dao.exception.DetailsNotFoundException;
import eu.heliovo.ics.common.dao.exception.InstrumentsDetailsNotSavedException;
import eu.heliovo.ics.common.transfer.InstrumentsTO;
import eu.heliovo.ics.common.transfer.criteriaTO.InstrumentCriteriaTO;
 
public interface InstrumentDao {
	public void saveInstrumentDetails(InstrumentsTO insTO) throws InstrumentsDetailsNotSavedException;
	public InstrumentsTO editInstrumentDetails(int insID) throws DetailsNotFoundException;
	public void updateInstrumentDetails(InstrumentsTO insTO) throws InstrumentsDetailsNotSavedException;
}
