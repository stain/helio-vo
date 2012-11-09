package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;  

public class HelioEventUlyssesHxr {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String id;
		
		String st_y;
		String st_m;
		String st_d;
		
		String doy;
		
		String st_hms;
		
		String peak_hms;
		
		String end_hms;

		String r_hgi;
		String long_hgi;
		String lat_hgi;
		
		String xray;

		String loc;
		String ns;
		String ew;
		String opt_long_hg;
		String opt_lat_hg;
		int o_long;
		int o_lat;
		
		String carr_long;
		
		String incr;
		String fsu;
		String peak;
			
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\Ulysses_hxr\\";
		
		// input file name
		String inname = dir + "Ulysses_hxr.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 1;
		
		// name of output file 
		String outname = dir + "ulysses_hxr_flare.txt";
		File out = new File(outname);

		try {
			
			HelioEventUlyssesHxr ulyssesHxr = new HelioEventUlyssesHxr();
			
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
			
			pw.println("time_start" + "|" + "time_peak" + "|" + "time_end" + "|" + "doy"
					+ "|" + "lat_hg" + "|" + "long_hg" + "|" + "long_carr" + "|" + "xray_class" 
					+ "|" + "r_hgi" + "|" + "lat_hgi" + "|" + "long_hgi" + "|" + "incr"
					+ "|" + "fsu_angle" + "|" + "peak_counts");
			
			
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
					st_y = line.substring(0, 4);
					st_m = line.substring(5, 7);
					st_d = line.substring(8, 10);
					
					doy = line.substring(11, 14);
					
					st_hms = line.substring(15, 19) + "00";
					
					//*****
					System.out.println(st_y  + " " + st_m  + " " + st_d  + " " + st_hms);
					
					startDate = ulyssesHxr.convertDate(st_y, st_m, st_d, st_hms);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

					end_hms = line.substring(20, 24) + "00";
					
					endDate = ulyssesHxr.convertDate(st_y, st_m, st_d, end_hms);
					
					//*****
					System.out.println(st_y  + " " + st_m  + " " + st_d  + " " + end_hms);
					
					// Peak time
					peak_hms = line.substring(25, 29) + "00";

					peakDate = ulyssesHxr.convertDate(st_y, st_m, st_d, peak_hms);

					//*****
					System.out.println(st_y  + " " + st_m  + " " + st_d  + " " + peak_hms);					
					
					loc     = line.substring(30,36).trim();
					if (loc.equals("")) {
						loc = "NaN";
						opt_lat_hg  = "NaN";
						opt_long_hg = "NaN";
					} else {
						ns = loc.substring(0, 1);
						o_lat = Integer.parseInt(loc.substring(1, 3));
						if (ns.equals("S")) o_lat = - o_lat;
						ew = loc.substring(3, 4);
						o_long = Integer.parseInt(loc.substring(4, 6));
						if (ew.equals("E")) o_long = - o_long;
						
						opt_lat_hg  = Integer.toString(o_lat);
						opt_long_hg  = Integer.toString(o_long);
					}
					
					carr_long = "NaN";
					
					xray      = line.substring(37,42).trim();
					//if (xray.equals("")) xray = "NaN";
					r_hgi     = line.substring(43,47).trim();
					long_hgi  = line.substring(48,55).trim();
					lat_hgi   = line.substring(55,60).trim();

					incr      = line.substring(61,62).trim();
					fsu       = line.substring(63,69).trim();
					if (fsu.equals("N/A")) fsu = "NaN";
					
					peak = line.substring(70,75).trim();
			

					pw.println(sdf.format( startDate.getTime() ) + "Z|" + sdf.format( peakDate.getTime() )
							+ "Z|" + sdf.format( endDate.getTime() ) + "Z|" + doy + "|" + opt_lat_hg
							+ "|" + opt_long_hg + "|" + carr_long + "|" + xray + "|" + r_hgi + "|" + lat_hgi + "|" + long_hgi
							+ "|" + incr + "|" + fsu + "|" + peak);
	
				}
				
				// line counter
				i++;
		
			}
			
			fw.close();
			
		} catch (Exception e) {System.out.println(e.getMessage());}


	}
	
	private GregorianCalendar convertDate(String yStr, String mStr, String dStr, String hms)
	{
		y = Integer.parseInt(yStr);
		m = Integer.parseInt(mStr) - 1;
		d = Integer.parseInt(dStr);
		
		//System.out.println(y  + " " + m  + " " + d);
		
		h   = Integer.parseInt(hms.substring(0, 2)); 
		min = Integer.parseInt(hms.substring(2, 4));
		String ss  = hms.substring(4, 6);
		if (ss.equals("- "))
		{
			s = 0;
		} else {
			s = Integer.parseInt(ss);
		}
		
		
		return new GregorianCalendar(y, m, d, h, min, s);
	}

	static private int y;
	static private int m;
	static private int d;
	static private int h;
	static private int min;
	static private int s;
	
	static private Calendar startDate;
	static private Calendar endDate;
	static private Calendar peakDate;


}
