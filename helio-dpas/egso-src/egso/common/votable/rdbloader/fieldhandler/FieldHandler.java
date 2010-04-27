package org.egso.common.votable.rdbloader.fieldhandler;

import java.sql.Types;
import cds.savot.model.SavotField;
import cds.savot.model.SavotValues;

/**
 * Subclasses of the FieldHandler map a VOTable data type to a SQL data type. 
 * @author Marco Soldati
 * @created 23.02.2005
 */
public abstract class FieldHandler
{
    protected String fieldName = null;
    
    protected SavotField field = null;

    protected String arraySize = null;
    
    protected boolean isArray = false;
    
    
    /**
     *  the default constructor
     */
    public FieldHandler()
    {
        
    }
    
    /**
     * @return Returns the field.
     */
    public SavotField getField()
    {
        return field;
    }
    
    /**
     * @return Returns the fieldName.
     */
    public String getFieldName()
    {
        return fieldName;
    }
    
    /**
     * Changes the fieldName. Make sure not to change this after
     * creating the database tables unless you know exactly what you're doing.
     * Any non alphanumeric character is converted to '_' in order to avoid 
     * database problems.  
     * @param fieldName The fieldName to set.
     */
    public void setFieldName(String fieldName)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < fieldName.length(); i++)
        {
            char c = fieldName.charAt(i);
            if (Character.isLetterOrDigit(c))
                sb.append(c);
            else
                sb.append('_');
        }
        this.fieldName = sb.toString();
    }
    
    /**
     * @param field The field to set.
     */
    public void setField(SavotField field)
    {
        this.field = field;
        setFieldName(field.getName());

        if (fieldName == null)
            throw new IllegalArgumentException("Attribute fieldName must not be null");
        
         arraySize = field.getArraySize();
         
         try
         {
             if (arraySize != null && (arraySize.equals("*") || Integer.parseInt(arraySize) > 1))
                 isArray = true;
         } catch (NumberFormatException e)
         {
            // ignore
         }
    }
    
    /**
     * map the VOTable content to a JDBC data type
     * @param content
     * @return
     */
    public abstract Object format(String content);
    
    /**
     * get the JDBC type
     * @return
     */
    public abstract int getJDBCType();    
    
    
    /**
     * SQL query part that describes a single field
     * @return SQL description of a single field
     */
    public StringBuffer getSQLFieldDesc()
    {
        StringBuffer fieldDesc = new StringBuffer()
            .append(fieldName)
            .append(" ");
        
        switch (getJDBCType())
        {
            case Types.VARCHAR: fieldDesc.append("VARCHAR"); break;
            case Types.BOOLEAN: fieldDesc.append("BOOLEAN"); break;
            case Types.BIT: fieldDesc.append("BIT"); break;
            case Types.INTEGER: fieldDesc.append("INTEGER"); break;
            case Types.TINYINT: fieldDesc.append("TINYINT"); break;
            case Types.SMALLINT: fieldDesc.append("SMALLINT"); break;
            case Types.BIGINT: fieldDesc.append("BIGINT"); break;
            case Types.FLOAT: fieldDesc.append("FLOAT"); break;
            case Types.DOUBLE: fieldDesc.append("DOUBLE"); break;
            case Types.CHAR: fieldDesc.append("CHAR"); break;
            case Types.DATE: fieldDesc.append("DATE"); break;
            
            default: throw new IllegalArgumentException("Unsupported SQL Type from " + getClass().getName() + "." + getJDBCType()); 
        }
        
        // add columnsize and precision
        String arraySize = field.getArraySize(); 
        if (arraySize != null)
        {
            // TODO: set field size depending on field Size and type    
        }
        
        SavotValues values = field.getValues();
        if (values != null)
        {
            String nullValue = values.getNull();
            if (nullValue != null && !nullValue.equals(""))
                fieldDesc.append(" DEFAULT '").append(nullValue).append("'");
        }

        return fieldDesc;
    }

}
