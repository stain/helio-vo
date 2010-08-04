package eu.heliovo.dpas.ie.providers;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class VSOResultItem 
{
	public	String		url					=	"undefined";
	public	String 		provider			=	"undefined";
	public	String		instrument			=	"undefined";
	public 	String 		fileId 				= 	"undefined";
	public 	Calendar 	measurementStart	=	new GregorianCalendar();
	public 	Calendar 	measurementEnd		=	new GregorianCalendar();

	
	@Override
	public String toString() 
	{
		return "[" + url + ", " + instrument + ", " + fileId + ", " + measurementStart + ", " + measurementEnd + "]";
	}	
}
