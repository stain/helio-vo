package eu.heliovo.shared.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Utilities to handle Java Dates. 
 * @author marco soldati at fhnw ch
 *
 */
public class DateUtil {

	/**
	 * Formatter for the ISO format.
	 */
	private static DateFormat ISO8601_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss", Locale.UK);
	static {
		ISO8601_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	/**
	 * Convert a date object to an ISO date string such as used by XML docs.
	 * 
	 * @param date
	 *            the date to convert.
	 * @return the date as string.
	 */
	public static String toIsoDateString(Date date) {
		if (date == null) {
			return null;
		}

		// format in (almost) ISO8601 format
		String dateStr = ISO8601_FORMAT.format(date);

		// remap the timezone from 0000 to 00:00 (starts at char 22)
		// return dateStr.substring (0, 22) + ":" + dateStr.substring (22);
		return dateStr;
	};
}
