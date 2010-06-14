package eu.heliovo.dpas.ie.sensors.archives.tests;

import junit.framework.TestCase;
import eu.heliovo.dpas.ie.sensors.archives.OldPathBuilder;
import eu.heliovo.dpas.ie.sensors.archives.PathStep;


public class OldPathBuilderTest extends TestCase
{
	OldPathBuilder	path	=	new OldPathBuilder();
	
	public void testGetPathForDate()
	{
		build();
		fail("Not yet implemented");
	}

	public void testGetPathForDateInt()
	{
		fail("Not yet implemented");
	}

	public void testGetPatternFor()
	{
		fail("Not yet implemented");
	}

	/*
	 * This test site is of the format : yyyy/MM/dd/'H'HHmm
	 */
	private	void build()
	{
		/*
		 * Adding the root
		 */
		path.add(new PathStep("http://www.sdc.uio.no/vol/fits/xrt/level0", 
				true, 
				true));
		/*
		 * Adding the first separator
		 */
		path.add(new PathStep("/", 
				true, 
				false));
		/*
		 * Adding the year
		 */
		path.add(new PathStep("yyyy", 
				false, 
				true));
		/*
		 * Adding the separator
		 */
		path.add(new PathStep("/", 
				true, 
				false));
		/*
		 * Adding the month
		 */
		path.add(new PathStep("MM", 
				false, 
				true));
		/*
		 * Adding the separator
		 */
		path.add(new PathStep("/", 
				true, 
				false));
		/*
		 * Adding the day of the month
		 */
		path.add(new PathStep("dd", 
				false, 
				true));
		/*
		 * Adding the separator
		 */
		path.add(new PathStep("/", 
				true, 
				false));
		/*
		 * Adding the hour and minute
		 */
		path.add(new PathStep("'H'HHmm", 
				false, 
				true));
		/*
		 * Adding the separator
		 */
		path.add(new PathStep("/", 
				true, 
				false));
	}
}
