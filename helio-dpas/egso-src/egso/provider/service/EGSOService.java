package org.egso.provider.service;


public abstract class EGSOService {

	protected ServiceDescriptor descriptor = null;

	public EGSOService() {
	}
	
	public void access() {
		descriptor.access();
	}
	
	public void setDescriptor(ServiceDescriptor sd) {
		descriptor = sd;
	}
	
	
	public ServiceDescriptor getDescriptor() {
		return (descriptor);
	}
	
	
	public abstract void start();
	
	
	public abstract void stop();
	
	
}
