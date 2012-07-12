package eu.heliovo.idlclient.model;

import eu.heliovo.clientapi.model.catalog.HelioCatalog;
import eu.heliovo.clientapi.model.field.HelioField;

public class IdlHelioCatalog {

	private HelioCatalog helioCatalog;

	public IdlHelioCatalog(HelioCatalog helioCatalog)
	{
		this.helioCatalog = helioCatalog;
	}

	@IdlName
	public String name()
	{
		return "HelioCatalog";
	}
	
	public String getCatalogName() {
		return helioCatalog.getCatalogName();
	}

	public String getValue() {
		return helioCatalog.getValue();
	}

	public String getLabel() {
		return helioCatalog.getLabel();
	}

	public HelioField<?>[] getFields() {
		return helioCatalog.getFields();
	}
}
