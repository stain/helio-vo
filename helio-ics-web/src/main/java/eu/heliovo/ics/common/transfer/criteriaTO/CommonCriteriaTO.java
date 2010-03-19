/* #ident	"%W%" */
package eu.heliovo.ics.common.transfer.criteriaTO;

import java.io.Serializable;

public class CommonCriteriaTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer iPageNumber;
	private Integer iRowsPerPage;
	private Integer noOfPages;
	private String userId;
	private String insName;
	private String obsName;
	public String getObsName() {
		return obsName;
	}
	public void setObsName(String obsName) {
		this.obsName = obsName;
	}
	public String getInsName() {
		return insName;
	}
	public void setInsName(String insName) {
		this.insName = insName;
	}
	private Integer noOfRecords;
	
	private boolean paginated = false;
	
	public Integer getNoOfPages() {
		return noOfPages;
	}
	public void setNoOfPages(Integer noOfPages) {
		this.noOfPages = noOfPages;
	}
	public Integer getIPageNumber() {
		return iPageNumber;
	}
	public void setIPageNumber(Integer pageNumber) {
		iPageNumber = pageNumber;
	}
	public Integer getIRowsPerPage() {
		return iRowsPerPage;
	}
	public void setIRowsPerPage(Integer rowsPerPage) {
		iRowsPerPage = rowsPerPage;
	}
	public void setPaginated(boolean paginated){
		this.paginated = paginated;
	}
	
	public boolean isPaginated(){
		return paginated;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getNoOfRecords() {
		return noOfRecords;
	}
	public void setNoOfRecords(Integer noOfRecords) {
		this.noOfRecords = noOfRecords;
	}
	
	
}
