package org.egso.provider.service;


import java.util.Hashtable;

import org.egso.provider.utils.ProviderUtils;


public class ServiceDescriptor {

	private Hashtable<String,String> options = null;
	private String name = null;
	private String id = null;
	private String description = null;
	private String author = null;
	private String version = null;
	private String date = null;
	private String mainClass = null;
	private String jarName = null;
	private String lastAccess = null;
	private int numberOfAccesses = 0;


	public ServiceDescriptor(Hashtable<String,String> serviceProperties, Hashtable<String,String> serviceOptions) {
		options = serviceOptions;
		readProperties(serviceProperties);
		lastAccess = "none";
		numberOfAccesses = 0;
	}

	public void access() {
		lastAccess = ProviderUtils.getDate();
		numberOfAccesses++;
	}

	public String getLastAccess() {
		return(lastAccess);
	}
	
	public int getNumberOfAccesses() {
		return(numberOfAccesses);
	}

	public String getName() {
		return (name);
	}
	
	public String getID() {
		return (id);
	}

	public String getDescription() {
		return (description);
	}

	public String getAuthor() {
		return (author);
	}

	public String getVersion() {
		return (version);
	}

	public String getDate() {
		return (date);
	}

	public String getMainClass() {
		return (mainClass);
	}

	public String getJarName() {
		return (jarName);
	}

	public String getOption(String option) {
		return ((String) options.get(option));
	}

	public Hashtable<String,String> getOptions() {
		return (options);
	}

	private void readProperties(Hashtable<String,String> properties) {
		name = (String) properties.get("name");
		if (name == null) {
			System.out.println("The Service can't be loaded because the NAME of the Service is not specified.");
		}
		id = (String) properties.get("id");
		if (id == null) {
			System.out.println("The Service can't be loaded because the ID of the Service is not specified.");
		}
		description = (String) properties.get("description");
		if (description == null) {
			System.out.println("The Service can't be loaded because the DESCRIPTION of the Service is not specified.");
		}
		version = (String) properties.get("version");
		if (version == null) {
			System.out.println("The Service can't be loaded because the VERSION of the Service is not specified.");
		}
		author = (String) properties.get("author");
		if (author == null) {
			System.out.println("The Service can't be loaded because the AUTHOR of the Service is not specified.");
		}
		date = (String) properties.get("date");
		if (date == null) {
			System.out.println("The Service can't be loaded because the DATE of the Service is not specified.");
		}
		mainClass = (String) properties.get("main-class");
		if (mainClass == null) {
			System.out.println("The Service can't be loaded because the MAIN-CLASS of the Service is not specified.");
		}
		jarName = (String) properties.get("jar");
		if (jarName == null) {
			System.out.println("The Service can't be loaded because the JAR of the Service is not specified.");
		}
	}


}
