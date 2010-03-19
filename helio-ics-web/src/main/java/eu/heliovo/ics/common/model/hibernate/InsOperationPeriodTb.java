package eu.heliovo.ics.common.model.hibernate;

import java.io.Serializable;

public class InsOperationPeriodTb implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	private int id;
	private String insId;
	private String operationType; 
	private String insName;
	private java.util.Date insStartDate;
	private java.util.Date insEndDate;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInsId() {
		return insId;
	}
	public void setInsId(String insId) {
		this.insId = insId;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getInsName() {
		return insName;
	}
	public void setInsName(String insName) {
		this.insName = insName;
	}
	public java.util.Date getInsStartDate() {
		return insStartDate;
	}
	public void setInsStartDate(java.util.Date insStartDate) {
		this.insStartDate = insStartDate;
	}
	public java.util.Date getInsEndDate() {
		return insEndDate;
	}
	public void setInsEndDate(java.util.Date insEndDate) {
		this.insEndDate = insEndDate;
	}
	
	
	
}
