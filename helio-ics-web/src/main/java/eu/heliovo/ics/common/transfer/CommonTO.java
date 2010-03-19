/* #ident	"%W%" */
package eu.heliovo.ics.common.transfer;

import java.io.Serializable;

public class CommonTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private String insId;
	private String insName;
	private String insDesId;
	private String insDesName;
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
	public String getInsDesId() {
		return insDesId;
	}
	public void setInsDesId(String insDesId) {
		this.insDesId = insDesId;
	}
	public String getInsDesName() {
		return insDesName;
	}
	public void setInsDesName(String insDesName) {
		this.insDesName = insDesName;
	}
	
	
	

}
