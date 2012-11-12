package istpsolar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;     

public class HelioEventIstpSolarWindCatalogue {
	
	public static void main (String[] args)
	{
		
		String st_y;
		String st_doy;
		String st_h;
		String st_m;

		String end_y;
		String end_doy;
		String end_h;
		String end_m;

		String cat;
		String sc;
		String comments;
		
		int y;
		int doy;
		int m;
		int d;
		int h;
		int min;
		int s;
		
		
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\istp_solar_wind\\";
		
		// input fie name
		// the original file name which contains sections (3) with missing events
		// from the web page
		// String inname = dir + "ISTP_Solar_Wind_Catalog_Candidate_Events_edit2.txt";
		
		// file containing the missing events
		String inname = dir + "ISTP_Solar_Wind_Catalog_Candidate_Events_correct.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 2;
		
		// name of output file 
		String outname = dir + "ISTP_SOLAR_WIND_CATALOG_reformat.txt";
		File out = new File(outname);
	
		try {
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
			
			pw.println("START TIME" + " | " + "END TIME" + " | " + "CATEGORY" + " | " + "SPACECRAFT" + " | " + "COMMENTS");
			
			//String delims = "[ :.]";
			//String[] tokens;

			File file = new File(inname);
		
			FileReader fr = new FileReader ( file );
			BufferedReader in = new BufferedReader( fr);
				
			String line;
			int i = 0;
			while ((line = in.readLine()) != null) {
					
				//skip lines at start of file 
				if (i>skiplines-1) {

					//tokens = line.split(delims);
					
					//Start time
					st_y = line.substring(0, 2);
					st_doy = line.substring(2, 5);
					st_h = line.substring(7, 9);
					st_m = line.substring(9, 11);
					
					//*****
					System.out.println(st_y  + " " + st_doy + " " + st_h + " " + st_m);
					
					// date
					y = Integer.parseInt(st_y);
					doy = Integer.parseInt(st_doy);
					
					//years 
					if (y>20) y = 1900 + y;
					else y = 2000 + y;

					// hms
					h = Integer.parseInt(st_h);
					min = Integer.parseInt(st_m);
					s = 0;
			
					m = 0;
					d = 0;
					startDate = new GregorianCalendar(y, m, d, h, min, s);
					startDate.add(Calendar.DAY_OF_YEAR, doy);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

					//End time
					end_y = line.substring(16, 18);
					end_doy = line.substring(18, 21);
					end_h = line.substring(23, 25);
					end_m = line.substring(25, 27);
					
					//*****
					System.out.println(end_y  + " " + end_doy + " " + end_h + " " + end_m);
					
					// date
					y = Integer.parseInt(end_y);
					doy = Integer.parseInt(end_doy);
					
					//years 
					if (y>20) y = 1900 + y;
					else y = 2000 + y;

					// hms
					h = Integer.parseInt(end_h);
					min = Integer.parseInt(end_m);
					s = 0;
			
					m = 0;
					d = 0;
					endDate = new GregorianCalendar(y, m, d, h, min, s);
					endDate.add(Calendar.DAY_OF_YEAR, doy);
					
					//Category
					cat = line.substring(31, 46).trim();
					//Spacecraft
					sc  = line.substring(46, 53).trim();
					//Comments
					comments = line.substring(55);
					
					//*****
					System.out.println(cat + " " + sc  + " " + comments);
					
					pw.println(sdf.format( startDate.getTime() ) + "Z | " +
							sdf.format( endDate.getTime() ) + "Z | " + cat + " | " +
							sc + " | " + comments);
			
				}
				
				// line counter
				i++;
		
			}
			
			int nev;
			nev = i - 2;
			System.out.println("Number of events: " + nev);
			fw.close();
			
		} catch (Exception e) {System.out.println(e.getMessage());}
}
	
	static private Calendar startDate;
	static private Calendar endDate;
	

}
