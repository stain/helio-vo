package eu.heliovo.dpas.ie.services.vso.transfer;

import java.io.BufferedWriter;
import java.io.Writer;
import java.util.Calendar;

import uk.ac.starlink.table.StarTable;

import eu.heliovo.dpas.ie.common.CommonTO;

public class VsoDataTO extends CommonTO{

	private  Writer output;
	private boolean providerStatus;	
	
	public Writer getOutput() {
		return output;
	}

	public void setOutput(Writer output) {
		this.output = output;
	}

	public boolean isProviderStatus() {
		return providerStatus;
	}

	public void setProviderStatus(boolean providerStatus) {
		this.providerStatus = providerStatus;
	}
	
}
