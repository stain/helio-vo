package eu.heliovo.dpas.ie.dataProviders;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import eu.heliovo.dpas.ie.internalData.DPASResultItem;

/**
 * Data provider for XRT.
 * 
 * @author Gabriele Pierantoni (gabriele.pierantoni@cs.tcd.ie)
 */
/*
 * http://www.sdc.uio.no/vol/fits/xrt/level0/2007/10/17/H2300/XRT20071017_235923.9d
 * .fits.gz
 */

public class NewXRT implements DPASDataProvider {

	String urlRoot = "http://www.sdc.uio.no/vol/fits/xrt/level0/";

	public List<DPASResultItem> query(Calendar dateFrom, Calendar dateTo,
			int maxResults) throws Exception {
		/*
		 * First, check if the start directory of the dateFrom exists.
		 */
		// String startUrl =
		// "http://www.sdc.uio.no/vol/fits/xrt/level0/2008/02/17/H0600/";
		String startUrl = createUrlFromCalendar(dateFrom, "yyyy/MM/dd/'H'HHmm");
		System.out.println("Now trying " + startUrl);

		if (!pageExists(startUrl)) {
			System.out.println(startUrl + " does NOT exist !!!");
			boolean pageFound = false;
			/*
			 * If the page does not exist, increment hour by hour until it
			 * reaches midnight
			 */
			GregorianCalendar tentativeTime = new GregorianCalendar();
			tentativeTime.setTime(dateFrom.getTime());
			/*
			 * First increment the hours one by one.
			 */
			while (!pageFound 
					&& 
					(tentativeTime.get(Calendar.HOUR_OF_DAY) < tentativeTime.getMaximum(Calendar.HOUR_OF_DAY))
					&&
					tentativeTime.before(dateTo)) 
			{
				tentativeTime.set(Calendar.HOUR_OF_DAY, tentativeTime.get(Calendar.HOUR_OF_DAY) + 1);
				startUrl = createUrlFromCalendar(tentativeTime, "yyyy/MM/dd/'H'HHmm");
				System.out.println("Now trying " + startUrl);
				if (pageExists(startUrl)) 
				{
					System.out.println(startUrl + " does exists !!!");
					pageFound = true;
				}
			}
			/*
			 * Increment the days one by one.
			 */
			tentativeTime.set(Calendar.HOUR_OF_DAY, 00);
			while (!pageFound 
					&& 
					tentativeTime.get(Calendar.DATE) < tentativeTime.getMaximum(Calendar.DATE)
					&&
					tentativeTime.before(dateTo)) 
			{
				tentativeTime.set(Calendar.DATE, tentativeTime.get(Calendar.DATE) + 1);
				startUrl = createUrlFromCalendar(tentativeTime, "yyyy/MM/dd");
				System.out.println("Now trying " + startUrl);
				if (pageExists(startUrl)) {
					System.out.println(startUrl + " does exists !!!");
					pageFound = true;
				}
			}
			/*
			 * Increment the months one by one.
			 */
			tentativeTime.set(Calendar.HOUR_OF_DAY, 00);
			tentativeTime.set(Calendar.DATE, 01);
			while (!pageFound 
					&& 
					tentativeTime.get(Calendar.MONTH) < tentativeTime.getMaximum(Calendar.MONTH)
					&&
					tentativeTime.before(dateTo)) 
			{
				tentativeTime.set(Calendar.MONTH, tentativeTime.get(Calendar.MONTH) + 1);
				startUrl = createUrlFromCalendar(tentativeTime, "yyyy/MM");
				System.out.println("Now trying " + startUrl);
				if (pageExists(startUrl)) {
					System.out.println(startUrl + " does exists !!!");
					pageFound = true;
				}
			}
			/*
			 * Increment the years one by one.
			 */
			tentativeTime.set(Calendar.HOUR_OF_DAY, 00);
			tentativeTime.set(Calendar.DATE, 01);
			tentativeTime.set(Calendar.MONTH, 00);
			while (!pageFound 
					&& 
					tentativeTime.get(Calendar.YEAR) < tentativeTime.getMaximum(Calendar.YEAR)
					&&
					tentativeTime.before(dateTo)) 
			{
				tentativeTime.set(Calendar.YEAR, tentativeTime.get(Calendar.YEAR) + 1);
				startUrl = createUrlFromCalendar(tentativeTime, "yyyy");
				System.out.println("Now trying " + startUrl);
				if (pageExists(startUrl)) {
					System.out.println(startUrl + " does exists !!!");
					pageFound = true;
				}
			}

			if(!pageFound)
				System.out.println("No data available for this instrument within time range");
				
		} else {
			System.out.println(startUrl + " exists !!!");
		}

		String stopUrl = createUrlFromCalendar(dateTo);

		// URLConnection c = null;
		// BufferedReader in = null;
		//
		// String topUrl = "http://www.sdc.uio.no/vol/fits/xrt/level0/";
		// String currUrl = "http://www.sdc.uio.no/vol/fits/xrt/level0/";
		//
		// /*
		// * Getting the start and stop times
		// */
		// int startYear = dateFrom.get(Calendar.YEAR);
		// int stopYear = dateTo.get(Calendar.YEAR);
		// int startMonth = dateFrom.get(Calendar.MONTH);
		// int stopMonth = dateTo.get(Calendar.MONTH);
		// int startDay = dateFrom.get(Calendar.DATE);
		// int stopDay = dateTo.get(Calendar.DATE);
		// /*
		// * Current times being investigated
		// */
		// int currYear = -1;
		// int currMonth = -1;
		// int currDay = -1;
		//
		// /*
		// * Open the top url
		// *
		// * In this case : http://www.sdc.uio.no/vol/fits/xrt/level0/
		// */
		// System.out.println("* Now Analyzing " + topUrl);
		//
		// c = new URL(topUrl).openConnection();
		// in = new BufferedReader(new InputStreamReader(c.getInputStream()));
		// /*
		// * Parse the top directory
		// */
		// String line = null;
		// List<String> matches = new LinkedList<String>();
		// List<String> tmpMatches = new LinkedList<String>();
		// // use the readLine method of the BufferedReader to read one line at
		// a
		// // time.
		// // the readLine method returns null when there is nothing else to
		// read.
		// while ((line = in.readLine()) != null) {
		// /*
		// * Parse the line to see if it matches ...href="yyyy/"....
		// */
		// if (line.matches(".*href.*") && line.matches(".*DIR.*")
		// && !line.matches(".*Parent Directory.*")) {
		// // System.out.println(line);
		// /*
		// * Extract the year
		// */
		// int startAt = line.indexOf("href=");
		// String sYear = line.substring(startAt + 6, startAt + 10);
		// int iYear = Integer.parseInt(sYear);
		//
		// System.out.println(sYear);
		// /*
		// * Add here the check if the year is in between the valid dates
		// */
		// if ((dateFrom.get(Calendar.YEAR) <= iYear)
		// && (iYear <= dateTo.get(Calendar.YEAR))) {
		// sYear = currUrl + sYear;
		// matches.add(sYear);
		// }
		// }
		// }
		// printList(matches);
		// tmpMatches.clear();
		// /*
		// * Now parsing the directories with the months
		// */
		// for (int index = 0; index < matches.size(); index++) {
		// String currentUrl = matches.get(index);
		// /*
		// * Extract the current year
		// */
		// int startAt = currentUrl.lastIndexOf("/");
		//
		// System.out.println(currentUrl.substring(startAt + 1, currentUrl
		// .length()));
		// currYear = Integer.parseInt(currentUrl.substring(startAt + 1,
		// currentUrl.length()));
		//
		// System.out.println("* Now Analyzing " + matches.get(index));
		//
		// c = new URL(matches.get(index)).openConnection();
		// in = new BufferedReader(new InputStreamReader(c.getInputStream()));
		// /*
		// * Parse the top directory
		// */
		// // use the readLine method of the BufferedReader to read one line at
		// // a time.
		// // the readLine method returns null when there is nothing else to
		// // read.
		// while ((line = in.readLine()) != null) {
		// // System.out.println(line);
		//
		// /*
		// * Parse the line to see if it matches ...href="yyyy/"....
		// */
		// if (line.matches(".*href=.*") && line.matches(".*DIR.*")
		// && !line.matches(".*Parent Directory.*")) {
		//
		// System.out.println(line);
		//
		// /*
		// * Extract the month
		// */
		// startAt = line.indexOf("href=");
		// if (startAt > 0) {
		// String sMonth = line
		// .substring(startAt + 6, startAt + 8);
		// int iMonth = Integer.parseInt(sMonth);
		// /*
		// * Add here the check if the month is in between the
		// * valid dates only if the year is the first or the last
		// */
		// if((startYear < currYear) && (currYear < stopYear))
		// {
		// sMonth = matches.get(index) + "/" + sMonth;
		// tmpMatches.add(sMonth);
		// }
		// else
		// {
		// if ((currYear == startYear) && (startMonth <= iMonth)) {
		// sMonth = matches.get(index) + "/" + sMonth;
		// tmpMatches.add(sMonth);
		// }
		// if ((currYear == stopYear)
		// && (iMonth <= stopMonth)) {
		// sMonth = matches.get(index) + "/" + sMonth;
		// tmpMatches.add(sMonth);
		// }
		// }
		// }
		// }
		// }
		// }
		// matches.clear();
		// matches.addAll(tmpMatches);
		// tmpMatches.clear();
		// printList(matches);
		//
		// // /*
		// // * Now parsing the directories with the days
		// // */
		// // for (index = 0; index < matches.size(); index++)
		// // {
		// // System.out.println("* Now Analyzing " + matches.get(index));
		// //
		// // c = new URL(matches.get(index)).openConnection();
		// // in = new BufferedReader(new
		// InputStreamReader(c.getInputStream()));
		// // /*
		// // * Parse the top directory
		// // */
		// // // use the readLine method of the BufferedReader to read one line
		// at
		// // // a time.
		// // // the readLine method returns null when there is nothing else to
		// // // read.
		// // while ((line = in.readLine()) != null) {
		// // // System.out.println(line);
		// //
		// // /*
		// // * Parse the line to see if it matches ...href="yyyy/"....
		// // */
		// // if (line.matches(".*href=.*") && line.matches(".*DIR.*")
		// // && !line.matches(".*Parent Directory.*")) {
		// // // System.out.println(line);
		// //
		// // /*
		// // * Extract the month
		// // */
		// // startAt = line.indexOf("href=");
		// // if (startAt > 0) {
		// // String sDay = line
		// // .substring(startAt + 6, startAt + 8);
		// // int iDay= Integer.parseInt(sDay);
		// // /*
		// // * Add here the check if the year is in between the
		// // * valid dates
		// // */
		// // if((index == 0) || (index == (matches.size()-1)))
		// // {
		// // if ((dateFrom.get(Calendar.DATE) <= iDay) && (iDay <=
		// // dateTo.get(Calendar.DATE)))
		// // {
		// // sDay = matches.get(index) + "/" + sDay;
		// // tmpMatches.add(sDay);
		// // }
		// // }
		// // else
		// // {
		// // sDay = matches.get(index) + "/" + sDay;
		// // tmpMatches.add(sDay);
		// // }
		// // }
		// // }
		// //
		// // }
		// // }
		// // matches.clear();
		// // matches.addAll(tmpMatches);
		// // tmpMatches.clear();
		// //
		// // printList(matches);
		return null;
	}

	private boolean pageExists(String urlName) {
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(urlName)
					.openConnection();
			con.setInstanceFollowRedirects(false);
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private String createUrlFromCalendar(Calendar dateFrom) {
		DateFormat helioFormatter = new SimpleDateFormat("yyyy/MM/dd/'H'HHmm");
		helioFormatter.setLenient(false);
		String result = urlRoot + helioFormatter.format(dateFrom.getTime())
				+ "/";
		return result;
	}

	private String createUrlFromCalendar(Calendar dateFrom, String formatter) {
		DateFormat helioFormatter = new SimpleDateFormat(formatter);
		helioFormatter.setLenient(false);
		String result = urlRoot + helioFormatter.format(dateFrom.getTime())
				+ "/";
		return result;
	}

	private void printList(List<String> matches) {
		for (int index = 0; index < matches.size(); index++) {
			System.out.println("[" + index + "] = " + matches.get(index));
		}
	}
}
