package eu.heliovo.ics.common.model.hibernate;

import java.io.Serializable;

public class InstrumentsMainTb implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String insId;	
	private String insName; 
	private String insObs;
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
	public String getInsName() {
		return insName;
	}
	public void setInsName(String insName) {
		this.insName = insName;
	}
	public String getInsObs() {
		return insObs;
	}
	public void setInsObs(String insObs) {
		this.insObs = insObs;
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
