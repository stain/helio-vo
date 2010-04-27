package org.egso.provider.utils;

import java.util.Calendar;

import org.egso.provider.admin.ProviderMonitor;


/**
 * The Conversion class offers a set of conversion methods used for time and
 * date. These methods take an input and an output formats and the time/date to
 * convert.<BR>
 * <U>For time:</U> <BR>
 * The input and output formats are the following:<BR>
 * <code>HH<BR>
 * MM<BR>
 * SS<BR>
 * xHHxMMxSSx<BR>
 * </code> where <code>HH<code> refers to hour (2 numbers), <code>MM</code> for
 * the minutes, <code>SS</code> for the seconds, and <code>x</code> a String of
 * characters (optional) that DO NOT contains 'H', 'h', 'M', 'm', 'S' or 's'.
 * <BR>
 * <U>Dates:</U> <BR>
 * The input and output formats are the following:<BR>
 * <code>DD<BR>
 * MM / MMM / Mmm / mmm<BR>
 * YY / YYYY<BR>
 * DDxMMxYY / MMxDDxYY / YYxDDxMM / YYxMMxDD</code><BR>
 * where <code>DD<code> refers to the days (2 numbers), <code>MM</code> the
 * month as 2 numbers, <code>MMM</code>, <code>mmm</code> or <code>Mmm</code>
 * for the name of the month (3 letters upper or lower cases, 3 letters with
 * capital letter), <code>YY</code> the year on 2 numbers, <code>YYYY</code> for
 * the year on 4 numbers, and <code>x</code> a String (optional) that DO NOT
 * contains 'D', 'd', 'M', 'm', 'Y' or 'y'.
 *
 * @author    Romain Linsolas (linsolas@gmail.com).
 * @version   1.2-jd - 10/12/2003 [28/01/2002]
 */
public class Conversion {

	/**
	 * Code for the day format.
	 */
	private final static int DAY = 0;
	/**
	 * Code for the month format.
	 */
	private final static int MONTH = 1;
	/**
	 * Code for the year format.
	 */
	private final static int YEAR = 2;
	/**
	 * Code for the Day-Month-Year format.
	 */
	private final static int DMY = 3;
	/**
	 * Code for the Month-Day-Year format.
	 */
	private final static int MDY = 4;
	/**
	 * Code for the Year-Month-Day format.
	 */
	private final static int YMD = 5;
	/**
	 * Code for the Year-Day-Month format.
	 */
	private final static int YDM = 6;
	/**
	 * Code for the Day_of_year format.
	 */
	private final static int Doy = 7;
	/**
	 * Code for the Year-Day_of_year format.
	 */
	private final static int YDoy = 8;
	/**
	 * Code for the Day_of_year-Year format.
	 */
	private final static int DoyY = 9;
	/**
	 * Code for non allowed format.
	 */
	private final static int OTHER = -1;



	/**
	 * Convert a date to an output format.
	 *
	 * @param day                      Day of the date.
	 * @param month                    Month (as number) of the date.
	 * @param year                     Year of the date.
	 * @param format                   Output format.
	 * @return                         Formated date, respecting the given output
	 *      format.
	 * @exception ConversionException  Occurs if the conversion can't be made.
	 */
	private static String setOut(String day, int month, String year, String format)
			 throws ConversionException {
		String separ = getSeparator(format);
		String doy = null;
		if (format.indexOf("Doy") != -1) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(year));
			cal.set(Calendar.MONTH, month - 1);
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
			doy = "" + cal.get(Calendar.DAY_OF_YEAR);
		}
		// Modification of the year format.
		if (format.indexOf('Y') != -1) {
			year = getYear(year, ((format.lastIndexOf('Y') - format.indexOf('Y')) > 2));
		}
		String monthOk = "";
		if (format.toUpperCase().indexOf('M') != -1) {
			int deb = format.toUpperCase().indexOf('M');
			int fin = format.toUpperCase().lastIndexOf('M');
			if ((fin - deb) == 1) {
				// Case MM (month as integer).
				monthOk = String.valueOf(month);
				if (monthOk.length() == 1) {
					monthOk = "0" + monthOk;
				}
			} else {
				// Cases MMM, Mmm et mmm (months written with their 3 first characters).
				try {
					monthOk = getMonth(month, format.substring(deb, fin + 1));
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
					ProviderMonitor.getInstance().reportException(e);
					throw new ConversionException("Wrong date out-format. Correct month formats are : \"MM\", \"MMM\", \"Mmm\" and \"mmm\".");
				}
			}
		}
		switch (getOrder(format)) {
			case DAY:
				return (day);
			case MONTH:
				return (monthOk);
			case YEAR:
				return (year);
			case DMY:
				return (day + separ + monthOk + separ + year);
			case MDY:
				return (monthOk + separ + day + separ + year);
			case YMD:
				return (year + separ + monthOk + separ + day);
			case YDM:
				return (year + separ + day + separ + monthOk);
			case Doy:
				return (doy);
			case YDoy:
				return (year + separ + doy);
			case DoyY:
				return (doy + separ + year);
		}
		throw new ConversionException("Invalid date output format.");
	}


	/**
	 * Convert a time (HH:MM:SS) given an output format.
	 *
	 * @param hour                     Hours of the time.
	 * @param minute                   Minutes of the time.
	 * @param second                   Seconds of the time.
	 * @param format                   Ouput format.
	 * @return                         Formatted time.
	 * @exception ConversionException  Occurs if the time can't be converted.
	 */
	private static String setOut(String hour, String minute, String second, String format)
			 throws ConversionException {
		if (format.equals("HH")) {
			return (hour);
		}
		if (format.equals("MM")) {
			return (minute);
		}
		if (format.equals("SS")) {
			return (second);
		}
		String separ = "";
		if (format.length() != 6) {
			try {
				separ = format.substring(format.lastIndexOf('H') + 1, format.indexOf('M'));
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
				ProviderMonitor.getInstance().reportException(e);
				throw new ConversionException("Wrong time out-format. 'H' must be before 'M'.");
			}
		}
		return (hour + separ + minute + separ + second);
	}


	/**
	 * Convert a time (HH:MM) given an output format.
	 *
	 * @param hour                     Hours of the time.
	 * @param minute                   Minutes of the time.
	 * @param format                   Ouput format.
	 * @return                         Formatted time.
	 * @exception ConversionException  Occurs if the time can't be converted.
	 */
	private static String setOut(String hour, String minute, String format)
			 throws ConversionException {
		if (format.equals("HH")) {
			return (hour);
		}
		if (format.equals("MM")) {
			return (minute);
		}
		String separ = "";
		if (format.length() != 4) {
			try {
				separ = format.substring(format.lastIndexOf('H') + 1, format.indexOf('M'));
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
				ProviderMonitor.getInstance().reportException(e);
				throw new ConversionException("Wrong time out-format. 'H' must be before 'M'.");
			}
		}
		return (hour + separ + minute);
	}


	/**
	 * Return the name of the month that corresponds to the given number. The
	 * returned value is formatted in accordance to the given output format.
	 *
	 * @param m                        Month number.
	 * @param format                   Ouput format. The authorised values are:
	 *      <code>MM</code> for a 2-numbers month, <code>MMM</code> for the name of
	 *      the month in upper case (e.g. JAN, FEB...), <code>mmm</code> for the
	 *      name of the month in lower case (e.g. jan, feb...) and <code>Mmm</code>
	 *      for the name of the month with a capital initial (e.g. Jan, Feb...).
	 * @return                         Name of the month according to the given
	 *      output format.
	 * @exception ConversionException  Occurs if the month can't be converted.
	 */
	private static String getMonth(int m, String format)
			 throws ConversionException {
		String month = "";
		String[] months = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
		month = months[m - 1];
		// Managing the output format.
		if (format.equals("MM")) {
			return (((m < 10) ? "0" : "") + m);
		}
		if (format.equals("MMM")) {
			return (month.toUpperCase());
		}
		if (format.equals("Mmm")) {
			try {
				return (Character.toUpperCase(month.charAt(0)) + month.substring(1));
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
				ProviderMonitor.getInstance().reportException(e);
				throw new ConversionException("Wrong month format.");
			}
		}
		return (month);
	}


	/**
	 * Returns the year on 2 or 4 numbers. If 2 numbers is selected, the considered
	 * date interval is between 1970 (70) and 2069 (69).
	 *
	 * @param year                     The given year.
	 * @param complete                 <code>true</code> if the year must have 4
	 *      numbers, <code>false</code> otherwise (2 numbers).
	 * @return                         The formatted year.
	 * @exception ConversionException  Occurs if the conversion can't be made.
	 */
	private static String getYear(String year, boolean complete)
			 throws ConversionException {
		if (complete) {
			// We want YYYY
			int a = 0;
			try {
				a = Integer.parseInt(year);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				ProviderMonitor.getInstance().reportException(e);
				throw new ConversionException("Wrong date in-format. Year is not an integer.");
			}
			if (a < 70) {
				// Case of a year with 2 numbers < 70 => 20xx
				return ("20" + year);
			} else {
				if (a < 100) {
					// Case of a year with 2 numbers >= 70 => 19xx
					return ("19" + year);
				}
			}
			// Case of a complete year (4 numbers).
			return (year);
		}
		// We want YY
		String tmp = null;
		try {
			tmp = year.substring(year.length() - 2);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			ProviderMonitor.getInstance().reportException(e);
			throw new ConversionException("Wrong date in-format. Year must be \"YYYY\" or \"YY\".");
		}
		return (tmp);
	}


	/**
	 * Returns the number of the given month.
	 *
	 * @param month                    Name of the month.
	 * @return                         Number of the month, <code>-1</code> if an
	 *      error occurs.
	 * @exception ConversionException  The number can't be found.
	 */
	private static int getMonthNumber(String month)
			 throws ConversionException {
		month = month.toLowerCase();
		String[] months = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
		int i = 0;
		boolean found = false;
		while (!found && (i < months.length)) {
			found = month.equals(months[i]);
			i++;
		}
		if (found) {
			return (i - 1);
		}
		try {
			// In case that we have the month as an integer.
			int m = Integer.parseInt(month);
			if ((m > 0) && (m < 13)) {
				return (m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ProviderMonitor.getInstance().reportException(e);
			throw new ConversionException("Month must be between 1 and 12");
		}
		return (-1);
	}


	/**
	 * Determines in which order are written the days, months and years for the
	 * output format.
	 *
	 * @param format  Given format.
	 * @return        Code for the output format.
	 */
	private static int getOrder(String format) {
		int j = format.indexOf('D');
		int doy = format.indexOf("Doy");
		int m = format.toUpperCase().indexOf('M');
		int a = format.indexOf('Y');
		if ((doy != -1) && (m == -1) && (a == -1)) {
			return (Doy);
		}
		if ((j != -1) && (m == -1) && (a == -1)) {
			return (DAY);
		}
		if ((j == -1) && (m != -1) && (a == -1)) {
			return (MONTH);
		}
		if ((j == -1) && (m == -1) && (a != -1)) {
			return (YEAR);
		}
		if (doy != -1) {
			if (doy > a) {
				return (YDoy);
			} else {
				return (DoyY);
			}
		}
		if ((j < m) && (m < a)) {
			return (DMY);
		}
		if ((m < j) && (j < a)) {
			return (MDY);
		}
		if ((a < m) && (m < j)) {
			return (YMD);
		}
		if ((a < j) && (j < m)) {
			return (YDM);
		}
		return (OTHER);
	}


	/**
	 * Finds and returns the separator between the day, the month and the year. The
	 * separator must <b>always</b> be the same between all the part (e.g. D-M-Y,
	 * D_M_Y are good, D-M_Y is not allowed).
	 *
	 * @param format  Input format of the date.
	 * @return        The separator.
	 */
	private static String getSeparator(String format) {
		int i = 2;
		if (format.toUpperCase().startsWith("MMM")) {
			i = 3;
		}
		if (format.startsWith("YYYY")) {
			i = 4;
		}
		String f = format.substring(i);
		if (f.equals("") || f.startsWith("D") || f.startsWith("M") || f.startsWith("Y") || format.equals("Doy")) {
			return ("");
		}
		int j = f.indexOf('D');
		int m = f.toUpperCase().indexOf('M');
		int a = f.indexOf('Y');
		int doy = f.indexOf("Doy");
		if (doy != -1) {
			return (f.substring(0, doy));
		}
		i = (j == -1) ? (Math.min(m, a)) : ((m == -1) ? (Math.min(j, a)) : (Math.min(j, m)));
		return (f.substring(0, i));
	}


	/**
	 * JAVADOC: Gets the weekFromDate attribute of the Conversion class
	 *
	 * @param date    JAVADOC: Description of the Parameter
	 * @param format  JAVADOC: Description of the Parameter
	 * @param us      JAVADOC: Description of the Parameter
	 * @return        JAVADOC: The weekFromDate value
	 */
	public static int getWeekFromDate(String date, String format, boolean us) {
		String tmp = convertDate(format, "YYYYMMDD", date);
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Integer.parseInt(tmp.substring(0, 4)), Integer.parseInt(tmp.substring(4, 6)) - 1, Integer.parseInt(tmp.substring(6, 8)));
		cal.setFirstDayOfWeek(us ? Calendar.SUNDAY : Calendar.MONDAY);
		return (cal.get(Calendar.WEEK_OF_YEAR));
	}


	/**
	 * Converts the month from an input format to an output format.
	 *
	 * @param in                       Input format.
	 * @param out                      Output format.
	 * @param month                    Month, written according to the input
	 *      format.
	 * @return                         Formatted month.
	 * @exception ConversionException  Occurs if the conversion can't be made.
	 */
	private static String convertMonth(String in, String out, String month)
			 throws ConversionException {
		if (in.equals(out)) {
			return (month);
		}
		if (in.equals("MM")) {
			try {
				return (getMonth(Integer.parseInt(month), out));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				ProviderMonitor.getInstance().reportException(e);
				throw new ConversionException("Wrong date in-format. Month with format \"MM\" must be an integer.");
			}
		}
		if (out.equals("MM")) {
			String tmp = String.valueOf(getMonthNumber(month));
			if (tmp.length() == 1) {
				tmp = "0" + tmp;
			}
			return (tmp);
		}
		try {
			month = Character.toUpperCase(month.charAt(0)) + month.substring(1).toLowerCase();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			ProviderMonitor.getInstance().reportException(e);
			throw new ConversionException("Wrong date in-format. Correct month formats are : \"MM\", \"MMM\", \"Mmm\" and \"mmm\".");
		}
		if (out.equals("MMM")) {
			return (month.toUpperCase());
		}
		if (out.equals("mmm")) {
			return (month.toLowerCase());
		}
		return (month);
	}


	/**
	 * Converts a date.
	 *
	 * @param in                       Input format.
	 * @param out                      Output format.
	 * @param convert                  Date to convert.
	 * @return                         Formatted date.
	 * @exception ConversionException  Occurs if the conversion can't be made.
	 */
	public static String convertDate(String in, String out, String convert)
			 throws ConversionException {
		if (in.equals("DD")) {
			if (out.equals("DD")) {
				return (convert);
			}
			return ("N/A");
		}
		if (in.equals("MM") || in.toUpperCase().equals("MMM")) {
			if (out.equals("MM") || out.toUpperCase().equals("MMM")) {
				return (convertMonth(in, out, convert));
			} else {
				return ("N/A");
			}
		}
		if (in.equals("YY") || in.equals("YYYY")) {
			if (out.equals("YY") || out.equals("YYYY")) {
				return (getYear(convert, out.length() > 2));
			}
			return ("N/A");
		}
		if (out.equals("WW")) {
			return ("" + getWeekFromDate(convert, in, true));
		}
		int doy = in.indexOf("Doy");
		if (doy != -1) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(convert.substring(in.indexOf('Y'), in.lastIndexOf('Y') + 1)));
			cal.set(Calendar.DAY_OF_YEAR, Integer.parseInt(convert.substring(doy, doy + 3)));
			return (setOut("" + cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, "" + cal.get(Calendar.YEAR), out));
		}
		String day = null;
		String month = null;
		String year = null;
		int debJ = in.indexOf('D');
		int debM = in.toUpperCase().indexOf('M');
		int finM = in.toUpperCase().lastIndexOf('M');
		int debY = in.indexOf('Y');
		int finY = in.lastIndexOf('Y');
		try {
			day = convert.substring(debJ, debJ + 2);
			month = convert.substring(debM, finM + 1);
			year = convert.substring(debY, finY + 1);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			ProviderMonitor.getInstance().reportException(e);
			throw new ConversionException("Wrong date in-format. No 'D' or 'M' or 'Y'.");
		}
		int numMois = ((finM - debM) == 1) ? Integer.parseInt(month) : getMonthNumber(month);
		return (setOut(day, numMois, year, out));
	}


	/**
	 * Converts a time (HH:MM).
	 *
	 * @param in                       Input format.
	 * @param out                      Output format.
	 * @param convert                  Time to convert.
	 * @return                         Converted time.
	 * @exception ConversionException  Occurs if the conversion can't be made.
	 */
	public static String convertTime(String in, String out, String convert)
			 throws ConversionException {
		if (in.equals("HH")) {
			if (out.equals("HH")) {
				return (convert);
			}
			return ("N/A");
		}
		if (in.equals("MM")) {
			if (out.equals("MM")) {
				return (convert);
			}
			return ("N/A");
		}
		int debH = in.indexOf('H');
		int debM = in.indexOf('M');
		String heure = null;
		String minute = null;
		try {
			heure = convert.substring(debH, debH + 2);
			minute = convert.substring(debM, debM + 2);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			ProviderMonitor.getInstance().reportException(e);
			throw new ConversionException("Wrong time in-format. No 'H' or 'M'.");
		}
		return (setOut(heure, minute, out));
	}

	public static String convertAllTime(String in, String out, String convert) {
		if (in.indexOf('S') != -1) {
			return (convertTimeMS(in, out, convert));
		}
		return (convertTime(in, out, convert));
	}

	/**
	 * Converts a time (HH:MM:SS).
	 *
	 * @param in                       Input format.
	 * @param out                      Output format.
	 * @param convert                  Time to convert.
	 * @return                         Converted time.
	 * @exception ConversionException  Occurs if the conversion can't be made.
	 */
	public static String convertTimeMS(String in, String out, String convert)
			 throws ConversionException {
		if (in.equals("HH")) {
			if (out.equals("HH")) {
				return (convert);
			}
			return ("N/A");
		}
		if (in.equals("MM")) {
			if (out.equals("MM")) {
				return (convert);
			}
			return ("N/A");
		}
		if (in.equals("SS")) {
			if (out.equals("SS")) {
				return (convert);
			}
			return ("N/A");
		}
		int debH = in.indexOf('H');
		int debM = in.indexOf('M');
		int debS = in.indexOf('S');
		String heure = null;
		String minute = null;
		String seconde = null;
		try {
			heure = convert.substring(debH, debH + 2);
			minute = convert.substring(debM, debM + 2);
			seconde = convert.substring(debS, debS + 2);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			ProviderMonitor.getInstance().reportException(e);
			throw new ConversionException("Wrong time in-format. No 'H', 'M' or 'S'.");
		}
		return (setOut(heure, minute, seconde, out));
	}

}

