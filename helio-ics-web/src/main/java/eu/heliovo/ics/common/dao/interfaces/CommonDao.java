/* #ident	"%W%" */
package eu.heliovo.ics.common.dao.interfaces;

import java.util.HashMap;

import eu.heliovo.ics.common.dao.exception.DetailsNotFoundException;
import eu.heliovo.ics.common.transfer.criteriaTO.InstrumentCriteriaTO;
import eu.heliovo.ics.common.transfer.criteriaTO.InstrumentOperationPeriodCriteriaTO;
import eu.heliovo.ics.common.transfer.criteriaTO.ObservatoryCriteriaTO;
 
public interface CommonDao {
	public InstrumentCriteriaTO getInstrumentDetails(InstrumentCriteriaTO insCriteriaTO) throws DetailsNotFoundException ;
	public InstrumentOperationPeriodCriteriaTO getInstrumentOperationPeriodDetails(InstrumentOperationPeriodCriteriaTO insOpsPeriodCriteriaTO) throws DetailsNotFoundException ;
	public ObservatoryCriteriaTO getObservatoryDetails(ObservatoryCriteriaTO obsCriteriaTO) throws DetailsNotFoundException ;
}
