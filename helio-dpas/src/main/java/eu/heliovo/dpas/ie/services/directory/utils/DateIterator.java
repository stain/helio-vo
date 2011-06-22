package eu.heliovo.dpas.ie.services.directory.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

public class DateIterator implements Iterator<Date>, Iterable<Date>
{

    private Calendar end = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    private Calendar current = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    private String type=null;
    public DateIterator(Date start, Date end,String type)
    {
        this.end.setTime(end);
        this.current.setTime(start);
        this.type=type;
        //
        if(type.equalsIgnoreCase("y")){
          this.end.add(Calendar.YEAR, -1);
          this.current.add(Calendar.YEAR, -1);
        }else if(type.equalsIgnoreCase("d"))
        {
        	//this.end.add(Calendar.DAY_OF_MONTH, -1);
            this.current.add(Calendar.DAY_OF_MONTH, -1);
        }else
        {
        	//this.end.add(Calendar.MONTH, -1);
            this.current.add(Calendar.MONTH, -1);
        }
    }

    public boolean hasNext()
    {
    	//System.out.println(" : Current : "+current.getTime()+"  : end : "+end.getTime());
        return !current.after(end);
    }

    
    public Date next()
    {
    	if(type.equalsIgnoreCase("y")){
            this.current.add(Calendar.YEAR, 1);
    	}else if(type.equalsIgnoreCase("d")){
            this.current.add(Calendar.DAY_OF_MONTH, 1);
    	}else{
            this.current.add(Calendar.MONTH, 1);
    	}
        return current.getTime();
    }

    public void remove()
    {
        throw new UnsupportedOperationException("Cannot remove");
    }

    public Iterator<Date> iterator()
    {
        return this;
    }
}
