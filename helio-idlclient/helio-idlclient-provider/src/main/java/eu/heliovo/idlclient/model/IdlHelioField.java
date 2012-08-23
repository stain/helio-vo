package eu.heliovo.idlclient.model;

import eu.heliovo.clientapi.model.field.descriptor.HelioFieldDescriptor;

/**
 * Wrapper for a HELIO field that ought to be converted to IDL
 * @author MarcoSoldati
 *
 */
public class IdlHelioField {
	
	private HelioFieldDescriptor<Object> helioField;

	public IdlHelioField(HelioFieldDescriptor<Object> helioField)
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
