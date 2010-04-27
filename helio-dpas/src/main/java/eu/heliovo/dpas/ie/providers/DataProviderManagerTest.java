package eu.heliovo.dpas.ie.providers;

import java.util.List;

import junit.framework.TestCase;
import eu.heliovo.dpas.ie.classad.ClassAdMapperException;
import eu.heliovo.dpas.ie.classad.ClassAdUtilitiesException;

public class DataProviderManagerTest extends TestCase 
{
	DataProviderManager	dpManager	=	null;
	
	
	public DataProviderManagerTest() 
	{
		try {
			DataProviderManager	dpManager	=	new DataProviderManager();
		} catch (ArithmeticException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassAdMapperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassAdUtilitiesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Instruments
	 */
	String	inst1		=	"Instrument_1";
	String	inst2		=	"Instrument_2";
	String	inst3		=	"Instrument_3";
	/*
	 * Data Sources for instrument 1
	 */
	String	source11	=	"DataSource_1_for_instrument_1";
	String	source21	=	"DataSource_2_for_instrument_1";
	String	source31	=	"DataSource_3_for_instrument_1";
	/*
	 * Data Sources for instrument 2
	 */
	String	source12	=	"DataSource_1_for_instrument_2";
	String	source22	=	"DataSource_2_for_instrument_2";
	String	source32	=	"DataSource_3_for_instrument_2";
	/*
	 * Data Sources for instrument 3
	 */
	String	source13	=	"DataSource_1_for_instrument_3";
	String	source23	=	"DataSource_2_for_instrument_3";
	String	source33	=	"DataSource_2_for_instrument_3";
	
	public void testAddProvider() 
	{
		try 
		{
			setUpProvider();
		} catch (ArithmeticException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassAdMapperException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassAdUtilitiesException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testGetBest() {
		try 
		{
			setUpProvider();
		} catch (ArithmeticException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassAdMapperException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassAdUtilitiesException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String	source	=	null;	
		try {
			source	=	(String) dpManager.getBest(inst1);
		} catch (DataProviderManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(inst1 + " ===> " + source);
	}

	public void testGetAll() {
		try 
		{
			setUpProvider();
		} catch (ArithmeticException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassAdMapperException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassAdUtilitiesException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List sources	=	null;	
		try {
			sources	=	dpManager.getAll(inst1);
		} catch (DataProviderManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(inst1 + " ===> " + sources);
	}

	private	void	setUpProvider() throws ArithmeticException, ClassAdMapperException, ClassAdUtilitiesException
	{
		dpManager.addProvider(inst1, 1, source11);
		dpManager.addProvider(inst1, 2, source21);
		dpManager.addProvider(inst1, 3, source31);

		dpManager.addProvider(inst2, 1, source12);
		dpManager.addProvider(inst2, 2, source22);
		dpManager.addProvider(inst2, 3, source32);

		dpManager.addProvider(inst3, 1, source13);
		dpManager.addProvider(inst3, 2, source23);
		dpManager.addProvider(inst3, 3, source33);
	}
}
