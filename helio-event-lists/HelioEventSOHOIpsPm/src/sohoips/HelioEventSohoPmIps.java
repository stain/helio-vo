package sohoips;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HelioEventSohoPmIps {
	
	public static final String shortNames[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	
	public static void main (String[] args)
	{
		
		String st_y;
		String st_m;
		String st_d;
		String st_doy;
		String st_h;
		String st_min;
		
		String zone  = " ";
		String comments  = " ";

		int y;
		int doy;
		int m;
		int d;
		int h;
		int min;
		int s;
		
		
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\";
		
		// input fie name
		String inname = dir + "SOHO_IPS_from_the_PM.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 2;
		
		// name of output file 
		String outname = dir + "SOHO_PM_IPS.txt";
		File out = new File(outname);
	
		try {
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
			
			pw.println("TIME" + " | " + "DOY" + " | " + "ZONE" + " | " + "COMMENTS");
			
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
					
					st_d = line.substring(0, 2);
					st_m = line.substring(3, 6);
					st_y = line.substring(7, 11);
					st_doy = line.substring(17, 20);
					st_h = line.substring(21, 23);
					st_min = line.substring(23, 25);
					
					if (line.length() > 29 ) {
						zone = line.substring(35, 36);
						if (zone.equals(" ")) zone = "NaN";
					}
					else
						zone = "NaN";
					
					if (line.length() > 37)
						comments = line.substring(41);
					else
						comments = " ";
					
					//*****
					System.out.println(st_y + " " + st_m + " " + st_d + " " + st_doy + " " + st_h + " " + st_min);
					
					// date
					y = Integer.parseInt(st_y);
					d = Integer.parseInt(st_d);
					doy = Integer.parseInt(st_doy);
					
					//convert month to integer
					for (i=0; i<12; i++)
					{
						if (shortNames[i].equals(st_m)) m = i+1;
					}
					
					// hms
					h = Integer.parseInt(st_h);
					min = Integer.parseInt(st_min);
					s = 0;
			
					m = 0;
					d = 0;
					startDate = new GregorianCalendar(y, m, d, h, min, s);
					startDate.add(Calendar.DAY_OF_YEAR, doy);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

					pw.println(sdf.format( startDate.getTime() ) + "Z | " +
							st_doy + " | " + zone + " | " + comments);
			
				}
				
				// line counter
				i++;
		
			}
			
			fw.close();
			
		} catch (Exception e) {System.out.println(e.getMessage());}
}
	
	static private Calendar startDate;
	static private Calendar endDate;
	


}
