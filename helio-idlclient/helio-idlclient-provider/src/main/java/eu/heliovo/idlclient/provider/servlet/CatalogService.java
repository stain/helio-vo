package eu.heliovo.idlclient.provider.servlet;


import java.util.ArrayList;
import java.util.List;

public class CatalogService {

	
	private String name;
	
	private String description;
	
	private String[] Stringarray;
	
	private List<HelioCatalog> catalogs = new ArrayList<HelioCatalog>();

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

	public List<HelioCatalog> getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(List<HelioCatalog> catalogs) {
		this.catalogs = catalogs;
	}

	public void setStringarray(String[] stringarray) {
		Stringarray = stringarray;
	}

	public String[] getStringarray() {
		return Stringarray;
	}
	
	
}
