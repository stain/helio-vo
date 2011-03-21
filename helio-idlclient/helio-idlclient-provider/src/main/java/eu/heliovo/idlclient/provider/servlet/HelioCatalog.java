package eu.heliovo.idlclient.provider.servlet;


import java.util.ArrayList;
import java.util.List;

public class HelioCatalog {

	private String name;
	
	private String description;
	
	private List<HelioField> fields = new ArrayList<HelioField>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<HelioField> getFields() {
		return fields;
	}

	public void setFields(List<HelioField> fields) {
		this.fields = fields;
	}
	
	
}
