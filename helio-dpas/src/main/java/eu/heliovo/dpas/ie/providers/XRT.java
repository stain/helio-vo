package eu.heliovo.dpas.ie.providers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import eu.heliovo.dpas.ie.dataProviders.DPASDataProvider;
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

public class XRT implements DPASDataProvider {
	public List<DPASResultItem> query(Calendar dateFrom, Calendar dateTo,
			int maxResults) throws Exception {

		URLConnection c = null;
		BufferedReader in = null;

		String topUrl = "http://www.sdc.uio.no/vol/fits/xrt/level0/";
		String currUrl = "http://www.sdc.uio.no/vol/fits/xrt/level0/";

		/*
		 * Getting the start and stop times
		 */
		int startYear = dateFrom.get(Calendar.YEAR);
		int stopYear = dateTo.get(Calendar.YEAR);
		int startMonth = dateFrom.get(Calendar.MONTH);
		int stopMonth = dateTo.get(Calendar.MONTH);
		int startDay = dateFrom.get(Calendar.DATE);
		int stopDay = dateTo.get(Calendar.DATE);
		/*
		 * Current times being investigated
		 */
		int currYear = -1;
		int currMonth = -1;
		int currDay = -1;

		/*
		 * Open the top url
		 * 
		 * In this case : http://www.sdc.uio.no/vol/fits/xrt/level0/
		 */
		System.out.println("* Now Analyzing " + topUrl);
//		/*
//		 * TODO : Remove before committing
//		 */
//		System.setProperty("http.proxyHost", "proxy.cs.tcd.ie");
//		System.setProperty("http.proxyPort", "8080");

		c = new URL(topUrl).openConnection();
		in = new BufferedReader(new InputStreamReader(c.getInputStream()));
		/*
		 * Parse the top directory
		 */
		String line = null;
		List<String> matches = new LinkedList<String>();
		List<String> tmpMatches = new LinkedList<String>();
		// use the readLine method of the BufferedReader to read one line at a
		// time.
		// the readLine method returns null when there is nothing else to read.
		while ((line = in.readLine()) != null) {
			/*
			 * Parse the line to see if it matches ...href="yyyy/"....
			 */
			if (line.matches(".*href.*") && line.matches(".*DIR.*")
					&& !line.matches(".*Parent Directory.*")) {
				// System.out.println(line);
				/*
				 * Extract the year
				 */
				int startAt = line.indexOf("href=");
				String sYear = line.substring(startAt + 6, startAt + 10);
				int iYear = Integer.parseInt(sYear);

				System.out.println(sYear);
				/*
				 * Add here the check if the year is in between the valid dates
				 */
				if ((dateFrom.get(Calendar.YEAR) <= iYear)
						&& (iYear <= dateTo.get(Calendar.YEAR))) {
					sYear = currUrl + sYear;
					matches.add(sYear);
				}
			}
		}
		printList(matches);
		tmpMatches.clear();
		/*
		 * Now parsing the directories with the months
		 */
		for (int index = 0; index < matches.size(); index++) {
			String currentUrl = matches.get(index);
			/*
			 * Extract the current year
			 */
			int startAt = currentUrl.lastIndexOf("/");

			System.out.println(currentUrl.substring(startAt + 1, currentUrl
					.length()));
			currYear = Integer.parseInt(currentUrl.substring(startAt + 1,
					currentUrl.length()));

			System.out.println("* Now Analyzing " + matches.get(index));

			c = new URL(matches.get(index)).openConnection();
			in = new BufferedReader(new InputStreamReader(c.getInputStream()));
			/*
			 * Parse the top directory
			 */
			// use the readLine method of the BufferedReader to read one line at
			// a time.
			// the readLine method returns null when there is nothing else to
			// read.
			while ((line = in.readLine()) != null) {
				// System.out.println(line);

				/*
				 * Parse the line to see if it matches ...href="yyyy/"....
				 */
				if (line.matches(".*href=.*") && line.matches(".*DIR.*")
						&& !line.matches(".*Parent Directory.*")) {

					System.out.println(line);

					/*
					 * Extract the month
					 */
					startAt = line.indexOf("href=");
					if (startAt > 0) {
						String sMonth = line
								.substring(startAt + 6, startAt + 8);
						int iMonth = Integer.parseInt(sMonth);
						/*
						 * Add here the check if the month is in between the
						 * valid dates only if the year is the first or the last
						 */
						if ((startYear < currYear) && (currYear < stopYear)) {
							sMonth = matches.get(index) + "/" + sMonth;
							tmpMatches.add(sMonth);
						} else {
							if ((currYear == startYear)
									&& (startMonth <= iMonth)) {
								sMonth = matches.get(index) + "/" + sMonth;
								tmpMatches.add(sMonth);
							}
							if ((currYear == stopYear) && (iMonth <= stopMonth)) {
								sMonth = matches.get(index) + "/" + sMonth;
								tmpMatches.add(sMonth);
							}
						}
					}
				}
			}
		}
		matches.clear();
		matches.addAll(tmpMatches);
		tmpMatches.clear();
		printList(matches);

		// /*
		// * Now parsing the directories with the days
		// */
		// for (index = 0; index < matches.size(); index++)
		// {
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
		// // System.out.println(line);
		//
		// /*
		// * Extract the month
		// */
		// startAt = line.indexOf("href=");
		// if (startAt > 0) {
		// String sDay = line
		// .substring(startAt + 6, startAt + 8);
		// int iDay= Integer.parseInt(sDay);
		// /*
		// * Add here the check if the year is in between the
		// * valid dates
		// */
		// if((index == 0) || (index == (matches.size()-1)))
		// {
		// if ((dateFrom.get(Calendar.DATE) <= iDay) && (iDay <=
		// dateTo.get(Calendar.DATE)))
		// {
		// sDay = matches.get(index) + "/" + sDay;
		// tmpMatches.add(sDay);
		// }
		// }
		// else
		// {
		// sDay = matches.get(index) + "/" + sDay;
		// tmpMatches.add(sDay);
		// }
		// }
		// }
		//
		// }
		// }
		// matches.clear();
		// matches.addAll(tmpMatches);
		// tmpMatches.clear();
		//
		// printList(matches);
		return null;
	}

	private void printList(List<String> matches) {
		for (int index = 0; index < matches.size(); index++) {
			System.out.println("[" + index + "] = " + matches.get(index));
		}
	}
}
