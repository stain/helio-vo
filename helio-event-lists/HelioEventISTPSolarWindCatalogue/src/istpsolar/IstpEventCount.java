package istpsolar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class IstpEventCount {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\istp_solar_wind\\";
		
		// file name directly from web page
		String inname = dir + "ISTP_Solar_Wind_Catalog_Candidate_Events_correct.txt";
		
		System.out.println("ISTP_Solar_Wind_Catalog_Candidate_Events_correct.txt");
		
		// args[2] - lines to skip at start of list
		int skiplines = 2;
		
		try {
			File file = new File(inname);
		
			FileReader fr = new FileReader ( file );
			BufferedReader in = new BufferedReader( fr);
				
			String line;

			int i = 0;
			int nev = 0;
			String date;
			while ((line = in.readLine()) != null) {
				
				//skip lines at start of file 
				if (i>skiplines-1) {
					date = line.substring(0,11);				
					if (i == skiplines) System.out.println("Date: " + date);
					nev++;
				}
				
				i++;

			}
			
			System.out.println("Number of events: " + nev);
				
		} catch (Exception e) {System.out.println(e.getMessage());}
			
		// file name directly from web page
		inname = dir + "ISTP_SOLAR_WIND_CATALOG.txt";
		
		System.out.println(" ");
		System.out.println("ISTP_SOLAR_WIND_CATALOG.txt");

		skiplines = 1;
			
		try {
			File file = new File(inname);
		
			FileReader fr = new FileReader ( file );
			BufferedReader in = new BufferedReader( fr);
				
			String line;

			int i = 0;
			int nev = 0;
			String date;
			while ((line = in.readLine()) != null) {
				
				//skip lines at start of file 
				if (i>skiplines-1) {
					date = line.substring(0,19);				
					if (i == skiplines) System.out.println("Date: " + date);
					nev++;
				}
				
				i++;

			}
			
			System.out.println("Number of events: " + nev);
				
		} catch (Exception e) {System.out.println(e.getMessage());}


	}


}
