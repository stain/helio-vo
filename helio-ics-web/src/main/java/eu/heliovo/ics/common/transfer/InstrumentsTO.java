/* #ident	"%W%" */
package eu.heliovo.ics.common.transfer;

import java.io.Serializable;

public class InstrumentsTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String insId;	
	private String insName; 
	private String insObs;
	private String insStartDate;
	private String insEndDate;
	private String insStartHour;
	private String insStartMin;
	private String insEndHour;
	private String insEndMin;
	
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
	public String getInsStartDate() {
		return insStartDate;
	}
	public void setInsStartDate(String insStartDate) {
		this.insStartDate = insStartDate;
	}
	public String getInsEndDate() {
		return insEndDate;
	}
	public void setInsEndDate(String insEndDate) {
		this.insEndDate = insEndDate;
	}
	public String getInsStartHour() {
		return insStartHour;
	}
	public void setInsStartHour(String insStartHour) {
		this.insStartHour = insStartHour;
	}
	public String getInsStartMin() {
		return insStartMin;
	}
	public void setInsStartMin(String insStartMin) {
		this.insStartMin = insStartMin;
	}
	public String getInsEndHour() {
		return insEndHour;
	}
	public void setInsEndHour(String insEndHour) {
		this.insEndHour = insEndHour;
	}
	public String getInsEndMin() {
		return insEndMin;
	}
	public void setInsEndMin(String insEndMin) {
		this.insEndMin = insEndMin;
	}
    

}
