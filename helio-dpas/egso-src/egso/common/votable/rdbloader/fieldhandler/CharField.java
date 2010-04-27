package org.egso.common.votable.rdbloader.fieldhandler;

import java.sql.Date;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cds.savot.model.SavotField;

/**
 * Default FieldHandler for char
 * @author Marco Soldati
 * @created 23.02.2005
 */    
public class CharField extends FieldHandler
{
    private static final Pattern pattern = Pattern.compile("(\\d*|\\*)[s](.)");
    
    /**
     *  set to the separator for variable lenght string arrays.
     */
    protected char separator = '\0'; 
    
    protected boolean isDate = false;
    
    
    /**
     * overload setField to handle VOTable String Arrays and fields of unit "date"
     * @param field The field to set.
     */
    public void setField(SavotField field)
    {
        super.setField(field);
        
        if (arraySize != null && !arraySize.equals(""))
        {
            Matcher matcher = pattern.matcher(arraySize);
            if (matcher.matches())
            {
                isArray = true;
                String s = matcher.group(2);
                separator = s != null ? s.charAt(0) : separator;
                return;
            }
        }
        
        // check if unit equals date
        if ("\"date\"".equals(field.getUnit()))
        {
            isDate = true;
        }
        
    }

    /** 
     * @see org.egso.consumer.ssr.FieldHandler#format(java.lang.String)
     */
    public Object format(String content)
    {
        if (isDate)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date;
            try
            {
                date = sdf.parse(content);
            } catch (ParseException e)
            {
                return null;
            }
            if (date.getTime() == 0)
                return null;
            return new Date(date.getTime());
        }
        
        if (content == null || content.equals(""))
        {
            return "";
        }
        
        if (isArray)
            return content;
        
            
        
        return new Character(content.charAt(0));
    }
    
    /**
     * 
     * @see org.egso.consumer.ssr.FieldHandler#getJDBCType()
     */
    public int getJDBCType()
    {
        if (isDate)
            return Types.DATE;
        if (isArray)
            return Types.VARCHAR;        
        else
            return Types.CHAR;
    }
}

