package eu.heliovo.dpas.ie.services.directory.transfer;


import java.util.Date;

import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;

public class FtpDataTO extends CommonTO{

	private Date dateValueTo;
	private Date dateValueFrom;
	
	
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
	
	
	
	
	
}
