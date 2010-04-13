package org.egso.common.votable.rdbloader;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.egso.common.votable.rdbloader.fieldhandler.BitField;
import org.egso.common.votable.rdbloader.fieldhandler.BooleanField;
import org.egso.common.votable.rdbloader.fieldhandler.CharField;
import org.egso.common.votable.rdbloader.fieldhandler.DoubleComplexField;
import org.egso.common.votable.rdbloader.fieldhandler.DoubleField;
import org.egso.common.votable.rdbloader.fieldhandler.FieldHandler;
import org.egso.common.votable.rdbloader.fieldhandler.FloatComplexField;
import org.egso.common.votable.rdbloader.fieldhandler.FloatField;
import org.egso.common.votable.rdbloader.fieldhandler.IntField;
import org.egso.common.votable.rdbloader.fieldhandler.ShortField;
import org.egso.common.votable.rdbloader.fieldhandler.LongField;
import org.egso.common.votable.rdbloader.fieldhandler.UnicodeCharField;
import org.egso.common.votable.rdbloader.fieldhandler.UnsignedByteField;
import cds.savot.model.SavotField;

/**
 * Factory for field handlers. A user can register his/her own field handlers
 * for standard and user define VOTable data types.  
 * @author Marco Soldati
 * @created 23.02.2005
 */
public class FieldHandlerFactory
{
    /**
     * get logger for this class.
     */
    private Logger logger = Logger.getLogger(FieldHandlerFactory.class);
    
    /**
     * keep the registered field handlers in a hashmap 
     */
    private final Map<String,Class<?>> fieldHandlerMap = new HashMap<String,Class<?>>();
    

    /**
     * create a new FieldHandlerFactory and initialize with default FieldHandler 
     */
    public FieldHandlerFactory()
    {
        initialize();
    }
    
    /**
     * init the hashmap with default values for standard data types
     */
    private void initialize()
    {
        fieldHandlerMap.put(getKey("boolean", null), BooleanField.class);
        fieldHandlerMap.put(getKey("bit", null), BitField.class);
        fieldHandlerMap.put(getKey("unsignedByte", null), UnsignedByteField.class);
        fieldHandlerMap.put(getKey("short", null), ShortField.class);
        fieldHandlerMap.put(getKey("int", null), IntField.class);
        fieldHandlerMap.put(getKey("long", null), LongField.class);
        fieldHandlerMap.put(getKey("char", null), CharField.class);
        fieldHandlerMap.put(getKey("unicodeChar", null), UnicodeCharField.class);
        fieldHandlerMap.put(getKey("float", null), FloatField.class);
        fieldHandlerMap.put(getKey("double", null), DoubleField.class);
        fieldHandlerMap.put(getKey("floatComplex", null), FloatComplexField.class);
        fieldHandlerMap.put(getKey("doubleComplex", null), DoubleComplexField.class);    
    }
    
    /**
     * registers a user type with the FieldHandlerFactory.
     * Either dataType or uType may be null.
     * 
     * @param dataType the data type attribute of the field element, may be null 
     * @param uType the user type of the field element, may be null
     * @param fieldHandler must be a subclass of @link{FieldHandler FieldHandler}
     */
    public void registerUserType(String dataType, String uType, Class<?> fieldHandler)
    {
        String key = getKey(dataType, uType);
        
        if (key == null)
            throw new IllegalArgumentException("either dataType or uType must not be null.");
        
        if (fieldHandler == null)
            throw new IllegalArgumentException("argument fieldHandler must not be null.");
        
        if (! (fieldHandler.isAssignableFrom(FieldHandler.class)))
            throw new IllegalArgumentException("argument fieldHandler must be a subclass of " + FieldHandler.class);
            
        
        if (fieldHandlerMap.containsKey(key))
        {
            logger.warn("fieldHandler for "  
                    + (dataType != null ? " dataType=" + dataType : "")
                    + (dataType != null && uType != null ? " and " : "")
                    + (uType != null ? " uType=" + uType : "")
                    + " will be overloaded.");
        }
        
        fieldHandlerMap.put(key, fieldHandler);
        
    }
    
    /**
     * Get an instance of a Field handler for a dataType and uType.
     * The param dataType and uType usually match the attributes in the savotFields,
     * unless a custom FieldHandler has been registered for a user defined field.
     * The method create a new instance of a FieldHandler and passes the field to it.
     * Either dataType or uType may be null.
     * @param dataType dataType of the field
     * @param uType uType of the field
     * @param field the savotField that is wrapped by this type
     * @return a new instance of the fieldHandler or null if no type was found.
     * @throws IllegalArgumentException if the arguments are not valid. 
     */
    public FieldHandler createFieldHandler(String dataType, String uType, SavotField field)
    {
        String key = getKey(dataType, uType);
        
        if (key == null)
            throw new IllegalArgumentException("Either param dataType or uType must not be set.");
        
        if (field== null)
            throw new IllegalArgumentException("The param field must not be null.");
        
        Class<?> fieldClass = fieldHandlerMap.get(key);
        if (fieldClass == null)
            return null;
       
        FieldHandler fieldHandler;
        try
        {
            fieldHandler = (FieldHandler)fieldClass.newInstance();
        } catch (InstantiationException e)
        {
            throw new RuntimeException("Internal Error: " + e.getMessage(), e);
        } catch (IllegalAccessException e)
        {
            throw new RuntimeException("Internal Error: " + e.getMessage(), e);
        }
        fieldHandler.setField(field);
        
        return fieldHandler;
    }
    
    /**
     * create the key for storing the data in the hashmap.
     * @param dataType
     * @param uType
     * @return
     */
    private String getKey(String dataType, String uType)
    {
        if ((dataType == null || "".equals(dataType)) && (uType == null || "".equals(uType)))
            return null;
        
        StringBuffer key = new StringBuffer();
        
        if (dataType != null)
            key.append(dataType);        
        key.append('_');
        if (uType != null)
            key.append(uType);

        return key.toString();
    }
}
