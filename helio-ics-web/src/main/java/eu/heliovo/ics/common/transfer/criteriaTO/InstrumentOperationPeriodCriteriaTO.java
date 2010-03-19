/* #ident	"%W%" */
package  eu.heliovo.ics.common.transfer.criteriaTO;

import eu.heliovo.ics.common.transfer.InstrumentOperationPeriodTO;
import eu.heliovo.ics.common.transfer.InstrumentsTO;



public class InstrumentOperationPeriodCriteriaTO extends CommonCriteriaTO{

	// use Page Id and rowperpage from commoncriteria

	private static final long serialVersionUID = 1L;
    private String query;
	private String colNum = "1"; 
    private InstrumentOperationPeriodTO[] insOpsPeriodDetailsTO;   

	public InstrumentOperationPeriodCriteriaTO(){
		this.setIPageNumber(0);
		this.setIRowsPerPage(10);
	}

	public String getColNum() {
		return colNum;
	}
	public void setColNum(String colNum) {
		this.colNum = colNum;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}

	public InstrumentOperationPeriodTO[] getInsOpsPeriodDetailsTO() {
		return insOpsPeriodDetailsTO;
	}

	public void setInsOpsPeriodDetailsTO(
			InstrumentOperationPeriodTO[] insOpsPeriodDetailsTO) {
		this.insOpsPeriodDetailsTO = insOpsPeriodDetailsTO;
	}

	
	
}
