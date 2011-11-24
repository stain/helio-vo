package eu.heliovo.shared.util;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

/**
 * Unit test for the date util
 * @author marco soldati at fhnw ch
 *
 */
public class DateUtilTest {
	
    /**
     * Test to isa date.
     */
	@Test
	public void testToIsoDateString1() {
		Date date = new Date(1104537600000L); // "2005-01-01T00:00:00   1104534000328l
		assertEquals("2005-01-01T00:00:00", DateUtil.toIsoDateString(date));
	}
	
	/**
	 * Test toIsoDate
	 */
	@Test
	public void testToIsoDateString2() {
		Date date = new Date(0);
		assertEquals("1970-01-01T00:00:00", DateUtil.toIsoDateString(date));
	}

	/**
	 * Test fromIsoDate
	 * @throws Exception
	 */
	@Test public void testFromIsoDate1() throws Exception {
	    Date date = DateUtil.fromIsoDate("2005-01-01T00:00:00");
	    assertEquals(1104537600000L, date.getTime());
	}

	/**
	 * Test fromIsoDate with milliseconds
	 * @throws Exception
	 */
	@Test public void testFromIsoDate2() throws Exception {
	    Date date = DateUtil.fromIsoDate("2005-01-01T00:00:00.123");
	    assertEquals(1104537600123L, date.getTime());
	}
	
	/**
	 * Test permute lists method.
	 */
	@Test public void testPermuteLists() {
	    List<String> list1 = Arrays.asList("A", "B", "C");
	    List<String> list2 = Arrays.asList("1", "2");
	    
	    List<String>[] permutedLists = DateUtil.permuteLists(list1, list2);
	    assertEquals(2, permutedLists.length);
	    assertEquals(permutedLists[0].size(), permutedLists[1].size());
	    assertArrayEquals(new String[] { "A", "B", "C", "A", "B", "C"}, permutedLists[0].toArray(new String[0]));
	    assertArrayEquals(new String[] { "1", "1", "1", "2", "2", "2"}, permutedLists[1].toArray(new String[0]));
	}
}
