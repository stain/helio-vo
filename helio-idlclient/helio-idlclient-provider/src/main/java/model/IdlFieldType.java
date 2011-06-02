package model;

import eu.heliovo.clientapi.model.field.FieldType;

public class IdlFieldType {

	private FieldType fieldType;
	
	public IdlFieldType(FieldType fieldType)
	{
		this.fieldType = fieldType;
	}

	public String getName()
	{
		return fieldType.getName();
	}
	
	public Class<?> getJavaType()
	{
		return fieldType.getJavaType();
	}
}
