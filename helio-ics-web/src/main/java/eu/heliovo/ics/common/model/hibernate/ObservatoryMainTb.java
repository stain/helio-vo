package eu.heliovo.ics.common.model.hibernate;

import java.io.Serializable;

public class ObservatoryMainTb implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String obsId;
	private String obsName;
	private String obsType;
	private String obsFirstPosition;
	private String obsSecondPosition;
	private java.util.Date obsStartDate;
	private java.util.Date obsEndDate;
	private String obsOperationType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getObsId() {
		return obsId;
	}
	public void setObsId(String obsId) {
		this.obsId = obsId;
	}
	public String getObsName() {
		return obsName;
	}
	public void setObsName(String obsName) {
		this.obsName = obsName;
	}
	public String getObsType() {
		return obsType;
	}
	public void setObsType(String obsType) {
		this.obsType = obsType;
	}
	public String getObsFirstPosition() {
		return obsFirstPosition;
	}
	public void setObsFirstPosition(String obsFirstPosition) {
		this.obsFirstPosition = obsFirstPosition;
	}	
	public String getObsSecondPosition() {
		return obsSecondPosition;
	}
	public void setObsSecondPosition(String obsSecondPosition) {
		this.obsSecondPosition = obsSecondPosition;
	}
	public String getObsOperationType() {
		return obsOperationType;
	}
	public void setObsOperationType(String obsOperationType) {
		this.obsOperationType = obsOperationType;
	}
	public java.util.Date getObsStartDate() {
		return obsStartDate;
	}
	public void setObsStartDate(java.util.Date obsStartDate) {
		this.obsStartDate = obsStartDate;
	}
	public java.util.Date getObsEndDate() {
		return obsEndDate;
	}
	public void setObsEndDate(java.util.Date obsEndDate) {
		this.obsEndDate = obsEndDate;
	}
	
	
	
	
	
	
}
