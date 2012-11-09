package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HelioEventIstpYearCheck {
	
	public static void main (String[] args)
	{
		
		String st_y;
		String time;

		String end_y;

		int j = 0;
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\";
		
		// input fie name
		String inname = dir + "ISTP_SOLAR_WIND_CATALOG.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 1;
		
		try {
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
					time = line.substring(0, 20);
					
					//Start year
					st_y = line.substring(0, 4);

					//End time
					end_y = line.substring(23, 27);
					
					if (st_y.equals(end_y)) j = 0;
					else System.out.println(time + " " + st_y + " " + end_y);
				}
				
				// line counter
				i++;
		
			}
		
		} catch (Exception e) {System.out.println(e.getMessage());}
}


}
