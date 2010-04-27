package org.egso.common.votable.rdbloader.fieldhandler;

import java.sql.Types;


/**
 * Default FieldHandler for unsignedByte
 * @author Marco Soldati
 * @created 23.02.2005
 */    
public class UnsignedByteField extends FieldHandler
{
    /** 
     * @see org.egso.consumer.ssr.FieldHandler#format(java.lang.String)
     */
    public Object format(String content)
    {
        if (isArray)
            return content;
        return new Byte(content);            
    }
    
    /**
     * 
     * @see org.egso.consumer.ssr.FieldHandler#getJDBCType()
     */
    public int getJDBCType()
    {
        if (isArray)
            return Types.VARCHAR;
        else
            return Types.TINYINT;
    }
}
