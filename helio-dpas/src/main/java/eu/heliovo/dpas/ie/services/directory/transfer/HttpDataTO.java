package eu.heliovo.dpas.ie.services.directory.transfer;


import java.util.Date;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;

public class HttpDataTO extends CommonTO{

	private Date dateValueTo;
	private Date dateValueFrom;
	private String endUrl;
	
	public Date getDateValueTo() {
		return dateValueTo;
	}
	public void setDateValueTo(Date dateValueTo) {
		this.dateValueTo = dateValueTo;
	}
	public Date getDateValueFrom() {
		return dateValueFrom;
	}
	public void setDateValueFrom(Date dateValueFrom) {
		this.dateValueFrom = dateValueFrom;
	}
	public String getEndUrl() {
		return endUrl;
	}
	public void setEndUrl(String endUrl) {
		this.endUrl = endUrl;
	}
}
