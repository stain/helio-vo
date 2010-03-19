/* #ident	"%W%" */
package eu.heliovo.ics.common.transfer;

import java.io.Serializable;

public class ObservatoryTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String obsId;
	private String obsName;
	private String obsType;
	private String obsFirstPosition;
	private String obsSecondPosition;
	private String obsStartDate;
	private String obsEndDate;
	private String obsStartHour;
	private String obsStartMin;
	private String obsEndHour;
	private String obsEndMin;
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
	public String getObsStartDate() {
		return obsStartDate;
	}
	public void setObsStartDate(String obsStartDate) {
		this.obsStartDate = obsStartDate;
	}
	public String getObsEndDate() {
		return obsEndDate;
	}
	public void setObsEndDate(String obsEndDate) {
		this.obsEndDate = obsEndDate;
	}
	public String getObsStartHour() {
		return obsStartHour;
	}
	public void setObsStartHour(String obsStartHour) {
		this.obsStartHour = obsStartHour;
	}
	public String getObsStartMin() {
		return obsStartMin;
	}
	public void setObsStartMin(String obsStartMin) {
		this.obsStartMin = obsStartMin;
	}
	public String getObsEndHour() {
		return obsEndHour;
	}
	public void setObsEndHour(String obsEndHour) {
		this.obsEndHour = obsEndHour;
	}
	public String getObsEndMin() {
		return obsEndMin;
	}
	public void setObsEndMin(String obsEndMin) {
		this.obsEndMin = obsEndMin;
	}
	
		
}
