package eu.heliovo.shared.util;

import static junit.framework.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

/**
 * Unit test for the date util
 * @author marco soldati at fhnw ch
 *
 */
public class DateUtilTest {
	
	@Test
	public void testToIsoDateString() {
		
		
		Date date = new Date(1104534000328l); // "2005-01-01T00:00:00
		assertEquals("2005-01-01T00:00:00", DateUtil.toIsoDateString(date));
		
		date = new Date(0);
		
		// TODO: This should be 1970-01-01T00:00:00
		assertEquals("1970-01-01T01:00:00", DateUtil.toIsoDateString(date));
		
		
		
		
	}
}
