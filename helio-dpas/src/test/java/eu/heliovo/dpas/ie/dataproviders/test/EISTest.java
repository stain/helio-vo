package eu.heliovo.dpas.ie.dataproviders.test;

import java.text.ParseException;
import java.util.List;

import org.junit.Test;

import eu.heliovo.dpas.ie.common.DpasUtilities;
import eu.heliovo.dpas.ie.dataProviders.EIS;
import eu.heliovo.dpas.ie.internalData.DPASResultItem;

public class EISTest {
	EIS dp = new EIS();
	String startTime = "2006-10-18 00:00:00";
	String stopTime = "2007-10-18 00:00:00";
	DpasUtilities utils = new DpasUtilities();

	@Test
	public void testQuery() {
		List<DPASResultItem> results = null;
		try {
			results = dp.query(utils.HELIOTimeToCalendar(startTime), utils
					.HELIOTimeToCalendar(stopTime), 50);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int k = 0; k < results.size(); k++) {
			System.out.println(k + " --> " + results.get(k));
			/*
			 * Adding now all the fields
			 */
			for (int i = 0; i < DPASResultItem.FIELD_NAMES.length; i++) {
				System.out.println("  [" + i + "] "
						+ DPASResultItem.FIELD_NAMES[i] + " == "
						+ results.get(k).toString(i));

			}
		}
	}
}
