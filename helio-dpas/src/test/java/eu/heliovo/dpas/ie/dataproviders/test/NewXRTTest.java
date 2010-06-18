package eu.heliovo.dpas.ie.dataproviders.test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import eu.heliovo.dpas.ie.dataProviders.NewXRT;

public class NewXRTTest 
{
	NewXRT 		xrt		=	new NewXRT();
	Calendar	start	=	new GregorianCalendar();	
	Calendar	stop	=	new GregorianCalendar();	

	@Before
	public void initialize() 
	{
		start	=	new GregorianCalendar();
		start.set(2001, 00, 01, 00, 00, 00);

		stop	=	new GregorianCalendar();
		stop.set(2004, 11, 31, 23, 59, 59);
	}

	
	@Test
	public void testQuery() throws Exception 
	{
		xrt.query(start, stop, 0);
	}

}
