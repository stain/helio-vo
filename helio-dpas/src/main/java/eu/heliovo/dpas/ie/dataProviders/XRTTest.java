package eu.heliovo.dpas.ie.dataProviders;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;


public class XRTTest 
{
	XRT 		xrt		=	new XRT();
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
		start.set(2009, 01, 01, 00, 00, 00);

		stop	=	new GregorianCalendar();
		stop.set(2009, 02, 01, 00, 00, 00);
	}
}
