package eu.heliovo.idlclient.provider.dummy;


import java.util.ArrayList;
import java.util.List;

public class HelioCatalog {

	private String name;

	private String description;
	
	private HelioField HelioArray[];
	
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

	public void setHelioArray(HelioField helioArray[]) {
		HelioArray = helioArray;
	}

	public HelioField[] getHelioArray() {
		return HelioArray;
	}
	
	
}
