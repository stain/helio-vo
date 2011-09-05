package model;

import eu.heliovo.clientapi.model.field.FieldType;
import eu.heliovo.clientapi.model.field.HelioField;


public class IdlHelioField {
	
	private HelioField<Object> helioField;

	public IdlHelioField(HelioField<Object> helioField)
	{
		this.helioField = helioField;
	}
	
	/*public String getLabel()
	{
		return helioField.getLabel();
	}*/
	
	@IdlName
	public String Name()
	{
		return "HelioField";
	}
	
	public String getId()
	{
		return helioField.getId();
	}
	
	public String getType()
	{
		return helioField.getType().getName();
	}

}
