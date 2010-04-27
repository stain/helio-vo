package org.egso.common.votable.rdbloader.fieldhandler;

import java.sql.Types;

/**
 * Default FieldHandler for floatComplex
 * @author Marco Soldati
 * @created 23.02.2005
 */    
public class FloatComplexField extends FieldHandler
{
    /** 
     * @see org.egso.consumer.ssr.FieldHandler#format(java.lang.String)
     */
    public Object format(String content)
    {
        return content;
    }
    
    /**
     * 
     * @see org.egso.consumer.ssr.FieldHandler#getJDBCType()
     */
    public int getJDBCType()
    {
        return Types.VARCHAR;
    }
}
