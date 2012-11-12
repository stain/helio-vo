package istpsolar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class IstpEventCompare {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\istp_solar_wind\\";
		
		// file name directly from web page
		String inname = dir + "ISTP_Solar_Wind_Catalog_Candidate_Events_v3.txt";
		
		// file currently in HEC
		String inname2 = dir + "ISTP_Solar_Wind_Catalog_Candidate_Events_correct.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 2;
		
		try {
			File file = new File(inname);
		
			FileReader fr = new FileReader ( file );
			BufferedReader in = new BufferedReader( fr);
				
			File file2 = new File(inname2);
			
			FileReader fr2 = new FileReader ( file2 );
			BufferedReader in2 = new BufferedReader( fr2);
				
			String line = " ";
			String line2;
			int i = 0;
			int nev = 0;
			int date = 0;
			int date2 = 0;
			boolean first = true;
			while ((line2 = in2.readLine()) != null) {
							
				if (first) line = in.readLine();
		
				//skip lines at start of file 
				if (i>skiplines-1) {
					
					first = false;
					
					date2 = Integer.parseInt(line2.substring(0,5));
					
					while (line.substring(0,1).equals("	") || line.substring(0,1).equals(" ") || line.substring(0,1)== null) {
						line = in.readLine();
						System.out.println("1: " + line.substring(0,5));
					}
					date = Integer.parseInt(line.substring(0,5));
					
					if (date != date2) {
						while (date != date2) {
							System.out.println("Missing event: " + date + "  " + date2);
							line = in.readLine();
							while (line.substring(0,1).equals("	") || line.substring(0,1).equals(" ") || line.substring(0,1)== null) {
								line = in.readLine();
								System.out.println(line.substring(0,5));
							}
							date = Integer.parseInt(line.substring(0,5));
						}
					}
				//	else {
					line = in.readLine();
				//	}							
				}
				
				i++;

			}
				
			//System.out.println("Number of events: " + nev);
				
			} catch (Exception e) {System.out.println(e.getMessage());}

	}

}
