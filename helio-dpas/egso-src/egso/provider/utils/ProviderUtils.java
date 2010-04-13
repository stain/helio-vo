package org.egso.provider.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;


/**
 * This class regroups some useful static methods.
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.0.1-jd - 01/06/2004 [11/12/2003]
 */
/*
1.0.1 - 01/06/2004:
	Adding getCalendar(String): Calendar.
	Adding uptime(Calendar, Calendar): String.
*/
public class ProviderUtils {

	/**
	 * Code for a warning exception.
	 */
	public final static int EXCEPTION_WARNING = 0;
	/**
	 * Code for an error exception.
	 */
	public final static int EXCEPTION_ERROR = 1;
	/**
	 * Code for a critical exception.
	 */
	public final static int EXCEPTION_CRITICAL = 2;
	/**
	 * List of codes for exceptions.
	 */
	private final static String[] types = {"warning", "error", "critical"};


	/**
	 * Returns the current date as "DD-MM-YYYY HH:MM:SS".
	 *
	 * @return   The current date.
	 */
	public static String getDate() {
		Calendar cal = Calendar.getInstance();
		String month = (cal.get(Calendar.MONTH) < 9 ? "0" : "") + (cal.get(Calendar.MONTH) + 1);
		String day = (cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "") + cal.get(Calendar.DAY_OF_MONTH);
		String time = " " + (cal.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "") + cal.get(Calendar.HOUR_OF_DAY) + ":" +
				(cal.get(Calendar.MINUTE) < 10 ? "0" : "") + cal.get(Calendar.MINUTE) + ":" +
				(cal.get(Calendar.SECOND) < 10 ? "0" : "") + cal.get(Calendar.SECOND);
		return (day + "-" + month + "-" + cal.get(Calendar.YEAR) + time);
	}


	/**
	 * Returns the date considering the given format, which can be one of the
	 * following: "DD-MM-YYYY HH:MM:SS" or "YYYY-MM-DD HH:MM:SS" or "YYYY-DD-MM
	 * HH:MM:SS". Only these formats are allowed. Others formats may be available
	 * by using the Conversion class methods.
	 *
	 * @param format  The output format for the date.
	 * @return        The current date.
	 */
	public static String getDate(String format) {
		Calendar cal = Calendar.getInstance();
		String month = (cal.get(Calendar.MONTH) < 9 ? "0" : "") + (cal.get(Calendar.MONTH) + 1);
		String day = (cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "") + cal.get(Calendar.DAY_OF_MONTH);
		String time = " " + (cal.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "") + cal.get(Calendar.HOUR_OF_DAY) + ":" +
				(cal.get(Calendar.MINUTE) < 10 ? "0" : "") + cal.get(Calendar.MINUTE) + ":" +
				(cal.get(Calendar.SECOND) < 10 ? "0" : "") + cal.get(Calendar.SECOND);
		if (format.toUpperCase().equals("YYYY-MM-DD")) {
			return (cal.get(Calendar.YEAR) + "-" + month + "-" + day + time);
		}
		if (format.toUpperCase().equals("YYYY-DD-MM")) {
			return (cal.get(Calendar.YEAR) + "-" + day + "-" + month + time);
		}
		return (day + "-" + month + "-" + cal.get(Calendar.YEAR) + time);
	}


	/**
	 * Returns the Calendar object related to the date given as a String. The
	 * format of this date <b>must</b> be: "DD-MM-YYYY HH:MM:SS" or
	 * "YYYY-MM-DD HH:MM:SS".
	 *
	 * @param date  The date that must be converted to a Calendar.
	 * @return      The corresponding Calendar object.
	 */
	public static Calendar getCalendar(String date) {
		Calendar cal = Calendar.getInstance();
		boolean startsWithYYYY = (date.indexOf("-") ==  4);
		int yea = Integer.parseInt(startsWithYYYY ? date.substring(0, 4) : date.substring(6, 10));
		int mon = Integer.parseInt(startsWithYYYY ? date.substring(5, 7) : date.substring(3, 5));
		int day = Integer.parseInt(startsWithYYYY ? date.substring(8, 10) : date.substring(0, 2));
		int hou = Integer.parseInt(date.substring(11, 13));
		int min = Integer.parseInt(date.substring(14, 16));
		int sec = Integer.parseInt(date.substring(17));
		cal.set(yea, --mon, day, hou, min, sec);
		return (cal);
	}



	/**
	 * JAVADOC: Description of the Method
	 *
	 * @param cal1  JAVADOC: Description of the Parameter
	 * @param cal2  JAVADOC: Description of the Parameter
	 * @return      JAVADOC: Description of the Return Value
	 */
	public static String uptime(Calendar cal1, Calendar cal2) {
		long diff = cal2.getTimeInMillis() - cal1.getTimeInMillis();
		String tmp = "";
		if (diff < 0) {
			diff = -diff;
			tmp = "- ";
		}
		long days = diff / (24 * 3600000);
		tmp += days + " day" + ((days > 1) ? "s" : "") + ", ";
		diff -= (days * 24 * 3600000);
		long hours = diff / 3600000;
		tmp += ((hours < 10) ? "0" : "") + hours + "h";
		diff -= (hours * 3600000);
		long minutes = diff / 60000;
		tmp += ((minutes < 10) ? "0" : "") + minutes + "m";
		diff -= (minutes * 60000);
		long seconds = diff / 1000;
		tmp += ((seconds < 10) ? "0" : "") + seconds + "s";
		return (tmp);
	}


	/**
	 * Transforms a "normal" String into a light 1337 String :)
	 *
	 * @param x  Normal String.
	 * @return   A String that has been 1337-ed...
	 */
	public static String to1337(String x) {
		x = x.toLowerCase();
		x = x.replace('a', '@');
		x = x.replace('b', '8');
		x = x.replace('c', '(');
		x = x.replace('e', '3');
		x = x.replace('g', '9');
		x = x.replace('i', '1');
		x = x.replace('l', '|');
		x = x.replace('o', '0');
		x = x.replace('q', '&');
		x = x.replace('s', '5');
		x = x.replace('t', '7');
		x = x.replace('z', '2');
		return (x);
	}


	/**
	 * Report an error exception.
	 *
	 * @param origin  Origin of the exception.
	 * @param t       Throwable of the exception
	 * @return        Returns the exception description.
	 */
	public static String reportException(String origin, Throwable t) {
		return (reportException(EXCEPTION_ERROR, origin, t));
	}


	/**
	 * Reports an exception.
	 *
	 * @param type    Type of the exception (warning, error, critical).
	 * @param origin  Origin of the exception.
	 * @param t       Throwable of the exception
	 * @return        Returns the exception description.
	 */
	public static String reportException(int type, String origin, Throwable t) {
		t.printStackTrace();
		StringBuffer error = new StringBuffer();
		error.append("<exception role=\"provider\" origin=\"" + origin + "\" type=\"" + types[type] + "\">\n  <message>");
		error.append(t.getMessage());
		error.append("</message>\n  <class>");
		error.append(t.getClass().getName());
		error.append("</class>\n  <stacktrace>\n");
		error.append("<![CDATA[\n");
		StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw));
		error.append(sw.getBuffer());
		error.append("]]>\n");
		error.append("  </stacktrace>\n</exception>");
		return (error.toString());
	}

}

