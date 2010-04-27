package eu.heliovo.dpas.ie.dataProviders;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

public class NewXRTTest 
{
	NewXRT 		xrt		=	new NewXRT();
	Calendar	start	=	new GregorianCalendar();	
	Calendar	stop	=	new GregorianCalendar();	
	
	@Test
	public void testQuery() 
	{
		initialize();
		
		try {
			xrt.query(start, stop, 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initialize() 
	{
		start	=	new GregorianCalendar();
		start.set(2001, 00, 01, 00, 00, 00);

		stop	=	new GregorianCalendar();
		stop.set(2004, 11, 31, 23, 59, 59);
	}
}
