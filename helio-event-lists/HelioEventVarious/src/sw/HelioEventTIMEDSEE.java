package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;     

public class HelioEventTIMEDSEE {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String doy;
	
		String lat_hg;
		String long_hg;
		String long_carr = "NaN";
		String xray;
		String region;
		String event;
		String see_xps_index;
		String see_egs_index;
		String time_obs_offset;
		String url_ftp_file;

		int y;
		int m;
		int d;
		int h;
		int min;
		int s;
	
	
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\TIMED_SEE\\";
	
		// input fie name
		String inname = dir + "see_flare_catalog.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 1;
	
		// name of output file 
		String outname = dir + "timed_see_flare_no_carr.txt";
	
		File out = new File(outname);

		try {
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
		
			pw.println("time_start" + "|" + "time_peak" + "|" +
					"time_end" + "|" + "doy" + "|" + 
					"lat_hg" + "|" + "long_hg" + "|" + "long_carr" + "|" +
					"xray_class" + "|" +
					"nar" + "|" + "nevent" + "|" + "see_xps_index" + "|" +
					"see_egs_index" + "|" + "obs_time_offset" + "|" +"url_ftp_file");
		
			String delims = "[,]";
			String[] tokens;

			File file = new File(inname);
	
			FileReader fr = new FileReader ( file );
			BufferedReader in = new BufferedReader( fr);
			
			String line;
			int i = 0;
			while ((line = in.readLine()) != null) {
				
				//skip lines at start of file 
				if (i>skiplines-1) {

					tokens = line.split(delims);
					
					// doy of event
					doy  = tokens[1];

					//Start time
					y = Integer.parseInt(tokens[0]);
					m = Integer.parseInt(tokens[2])-1;
					d = Integer.parseInt(tokens[3]);
				
					h = Integer.parseInt(tokens[4].substring(0,2));
					min = Integer.parseInt(tokens[4].substring(2,4));
					s = 0;
					
					//*****
					System.out.println(y  + " " + m  + " " + d + " " + h + " " + min);
	
					startDate = new GregorianCalendar(y, m, d, h, min, s);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

			 	   //peak time
					h = Integer.parseInt(tokens[5].substring(0,2));
					min = Integer.parseInt(tokens[5].substring(2,4));

					peakDate = new GregorianCalendar(y, m, d, h, min, s);
										
					// end time
					h = Integer.parseInt(tokens[6].substring(0,2));
					min = Integer.parseInt(tokens[6].substring(2,4));
		
					//*****
					System.out.println(y  + " " + m  + " " + d + " " + h + " " + min);

					endDate = new GregorianCalendar(y, m, d, h, min, s);
					
					xray = tokens[7];
					String x1 = tokens[7].substring(0,1);
					String x2 = tokens[7].substring(2);
					xray = x1+ x2;
				
					lat_hg = tokens[8];
					long_hg = tokens[9];
					if (lat_hg.equals("-999")) {
						lat_hg = "NaN";
						long_hg = "NaN";
					}
					
					region = tokens[10];
					if(region.equals("")) region = "NaN";
					event  = tokens[11];
					see_xps_index   = tokens[12];
					see_egs_index   = tokens[13];
					time_obs_offset = tokens[14];
					url_ftp_file    = tokens[15];
				
					pw.println(sdf.format(startDate.getTime() ) + "Z|" + sdf.format(peakDate.getTime() ) + "Z|" + 
							sdf.format( endDate.getTime() ) + "Z|" + doy + "|" +
							lat_hg + "|" + long_hg + "|" + long_carr + "|" + 
							xray + "|" + region + "|" + event + "|" + 
							see_xps_index + "|" + see_egs_index + "|" +
							time_obs_offset + "|" + url_ftp_file);
		
				}
			
				// line counter
				i++;
	
		}
		
		fw.close();
		
	} catch (Exception e) {System.out.println(e.getMessage());}
}
	
	static private Calendar startDate;
	static private Calendar peakDate;
	static private Calendar endDate;

}
