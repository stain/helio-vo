package istpsolar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class IstpEventCheck {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\istp_solar_wind\\";
		
		// file name directly from web page
		String inname = dir + "ISTP_Solar_Wind_Catalog_Candidate_Events_v3.txt";
		
		// args[2] - lines to skip at start of list
		int skiplines = 2;
		
		try {
			File file = new File(inname);
		
			FileReader fr = new FileReader ( file );
			BufferedReader in = new BufferedReader( fr);
				
			String line;

			int i = 0;
			int nev = 0;
			int date;
			while ((line = in.readLine()) != null) {
				
				//skip lines at start of file 
				if (i>skiplines-1) {
					
					if (!line.substring(0,1).equals("	") && !line.substring(0,1).equals(" ") && line.substring(0,1)!= null) {
						date = Integer.parseInt(line.substring(0,5));				
						System.out.println("Date: " + date);
						if (date > 0) nev++;
					}
				}
				
				i++;

			}
			
			System.out.println("Number of events: " + nev);
				
			} catch (Exception e) {System.out.println(e.getMessage());}

	}

}
