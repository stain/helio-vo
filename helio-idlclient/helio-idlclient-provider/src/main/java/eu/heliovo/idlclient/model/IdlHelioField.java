package eu.heliovo.idlclient.model;

import eu.heliovo.clientapi.model.field.HelioField;

/**
 * Wrapper for a HELIO field that ought to be converted to IDL
 * @author MarcoSoldati
 *
 */
public class IdlHelioField {
	
	private HelioField<Object> helioField;

	public IdlHelioField(HelioField<Object> helioField)
	{
		this.helioField = helioField;
	}
	
	@IdlName
	public String name()
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
