package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;     

public class HelioEventUlyssesGrb {
	
	public static void main (String[] args)
	{
		
		String id;
		
		String st_y;
		String st_m;
		String st_d;
		String st_h;
		String st_min;
		String st_s;
		
		String earth_y;
		String earth_m;
		String earth_d;
		String earth_h;
		String earth_min;
		String earth_s;

		String r_hgi;
		String lat_hgi;
		String long_hgi;

		//int long_hg;
		//int lat_hg;

		int y;
		int m;
		int d;
		int h;
		int min;
		int s;
		
		
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\Ulysses_grb\\";
		
		// input fie name
		String inname = dir + "grb_solar_events.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 0;
		
		// name of output file 
		String outname = dir + "ulysses_grb_flare_list.txt";
		File out = new File(outname);
	
		try {
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
			
			pw.println("ulysses_grb_id" + "|" + "time_start" + "|" + "time_start_earth"
					+ "|" + "r_hgi" + "|" + "lat_hgi" + "|" + "long_hgi");
			
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
					
					id = line.substring(0, 5);
					
					//Start time
					st_y = line.substring(13, 17);
					st_m = line.substring(10, 12);
					st_d = line.substring(7, 9);
					st_h = line.substring(18, 20);
					st_min = line.substring(21, 23);
					st_s = line.substring(24, 26);
					
					//*****
					//System.out.println(st_y  + " " + st_m  + " " + st_d  + " " + st_h + " " + st_min);
					
					// date
					y = Integer.parseInt(st_y);
					m = Integer.parseInt(st_m) - 1;
					d = Integer.parseInt(st_d);
					h = Integer.parseInt(st_h);
					min = Integer.parseInt(st_min);
					s = Integer.parseInt(st_s);
					//*****
					System.out.println(y  + " " + m  + " " + d  + " " + h + " " + min + " " + s);
					
							
					startDate = new GregorianCalendar(y, m, d, h, min, s);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					
					//earth time
				    earth_y = line.substring(33, 37);
					earth_m = line.substring(30, 32);
            		earth_d = line.substring(27, 29);
            		earth_h = line.substring(38, 40);
            		earth_min = line.substring(41, 43);
            		earth_s = line.substring(44, 46);
					
            		y = Integer.parseInt(earth_y);
            		m = Integer.parseInt(earth_m) - 1;
            		d = Integer.parseInt(earth_d);
            		h = Integer.parseInt(earth_h);
            		min = Integer.parseInt(earth_min);
            		s = Integer.parseInt(earth_s);
            		
            		//*****
            		System.out.println("flare: " + y  + " " + m  + " " + d  + " " + h + " " + min + " " + s);

            		earthDate = new GregorianCalendar(y, m, d, h, min, s);

					
					r_hgi = line.substring(47,51);
					lat_hgi = line.substring(52,59);
					long_hgi = line.substring(60,66);

					pw.println(id + "|" + sdf.format( startDate.getTime() ) + "Z|" + 
							sdf.format( earthDate.getTime() ) + "Z|" + r_hgi + "|" + 
							lat_hgi + "|" + long_hgi);

				}
				
				// line counter
				i++;
		
			}
			
			fw.close();
			
		} catch (Exception e) {System.out.println(e.getMessage());}
	}
	
	static private Calendar startDate;
	static private Calendar earthDate;



}
