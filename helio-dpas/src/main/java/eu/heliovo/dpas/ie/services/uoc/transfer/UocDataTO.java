package eu.heliovo.dpas.ie.services.uoc.transfer;

import java.io.Writer;

import eu.heliovo.dpas.ie.common.CommonTO;
import eu.heliovo.dpas.ie.services.uoc.service.net.ivoa.xml.votable.v1.RESOURCE;

public class UocDataTO extends CommonTO{

	private boolean providerStatus;	
	private String fileId;
	private String provider;
	
	private Writer output;
	private RESOURCE resource;
	
	
	public void setOutput(Writer output) {
		this.output = output;
	}

	public Writer getOutput() {
		return output;
	}

	public boolean isProviderStatus() {
		return providerStatus;
	}

	public void setProviderStatus(boolean providerStatus) {
		this.providerStatus = providerStatus;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public RESOURCE getResource() {
		return resource;
	}

	public void setResource(RESOURCE resource) {
		this.resource = resource;
	}

	
	
}
