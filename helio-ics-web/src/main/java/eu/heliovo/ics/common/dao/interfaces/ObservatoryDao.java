/* #ident	"%W%" */
package eu.heliovo.ics.common.dao.interfaces;

import java.util.HashMap;

import eu.heliovo.ics.common.dao.exception.DetailsNotFoundException;
import eu.heliovo.ics.common.dao.exception.InstrumentsDetailsNotSavedException;
import eu.heliovo.ics.common.dao.exception.ObservatoryDetailsNotSavedException;
import eu.heliovo.ics.common.transfer.InstrumentsTO;
import eu.heliovo.ics.common.transfer.ObservatoryTO;
import eu.heliovo.ics.common.transfer.criteriaTO.InstrumentCriteriaTO;
 
public interface ObservatoryDao {
	public void saveObservatoryDetails(ObservatoryTO obsTO) throws ObservatoryDetailsNotSavedException;
	public ObservatoryTO editObservatoryDetails(int obsID) throws DetailsNotFoundException;
	public void updateObservatoryDetails(ObservatoryTO obsTO) throws ObservatoryDetailsNotSavedException;
}
