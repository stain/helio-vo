package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;     

public class HelioEventFlaresNoSEP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int y;
		int m;
		int d;
		int h;
		int min;
		int s;

		String r_hgi;
		String long_hgi;
		String lat_hgi;
		
		String optical;
		String xray;

		String loc;
		String ns;
		String ew;
		String opt_long_hg;
		String opt_lat_hg;
		int o_long;
		int o_lat;
		
		String loc_source;
		String carr_long;
		
		boolean sep_date = true;
		
		String sep_int;
		String up_lim;
		String sep_exp;
		String comment;
		
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\Flare_No_SEPs\\";
		
		// input fie name
		String inname = dir + "Flares_Without_SEPs.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 1;
		
		// name of output file 
		String outname = dir + "goes_flare_no_sep_no_carr.txt";
		File out = new File(outname);
	
		try {
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
			
			pw.println("time_start" + "|" + "time_end" + "|" + "time_peak" + "|" + "loc_source"
					+ "|" + "lat_hg" + "|" + "long_hg" + "|" + "long_carr" + "|" + "optical_class"
					+ "|" + "xray_class" + "|" + "time_start_rise_sep" + "|" + "upper_limit"
					+ "|" + "intensity_sep_peak" + "|" + "intensity_sep_exp" + "|" + "comment");
			
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

					//Start time
					y = Integer.parseInt(line.substring(0, 4));
					m = Integer.parseInt(line.substring(5, 7)) -1;
					d = Integer.parseInt(line.substring(8, 10));
					h = Integer.parseInt(line.substring(11, 13));
					min = Integer.parseInt(line.substring(14, 16));
					s = 0;
					//*****
					System.out.println(y  + " " + m  + " " + d  + " " + h + " " + min);
				
					startDate = new GregorianCalendar(y, m, d, h, min, s);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

					//End time
					h = Integer.parseInt(line.substring(17, 19));
					min = Integer.parseInt(line.substring(20, 22));				
				
					endDate = new GregorianCalendar(y, m, d, h, min, s);
				
					// Peak time
					h = Integer.parseInt(line.substring(23, 25));
					min = Integer.parseInt(line.substring(26, 28));				
				
					peakDate = new GregorianCalendar(y, m, d, h, min, s);

					//*****
					System.out.println(y  + " " + m  + " " + d  + " " + h + " " + min);					
				
					loc     = line.substring(29,35).trim();
					if (loc.substring(0,2).equals("NW") || loc.substring(0,2).equals("SW")) {
						loc = "NaN";
						opt_lat_hg  = "NaN";
						opt_long_hg = "NaN";
					} else {
						ns = loc.substring(0, 1);
						o_lat = Integer.parseInt(loc.substring(1, 3));
						if (ns.equals("S")) o_lat = - o_lat;
						ew = loc.substring(3, 4);
						o_long = Integer.parseInt(loc.substring(4, 6));
					
						opt_lat_hg  = Integer.toString(o_lat);
						opt_long_hg  = Integer.toString(o_long);
					}
				
					carr_long = "NaN";
				
					if (line.substring(28,29).equals("(")) {
						loc_source = "S";
					} else {
						loc_source = "G";
					}
				
					optical   = line.substring(37, 40);
					xray      = line.substring(41, 46).trim();
				
					//SEP time
					String x = line.substring(48, 49);
					if ("-".equals(x)) {
						sep_date = false;
					} else {
						sep_date = true;
						h = Integer.parseInt(line.substring(48, 50));
						min = Integer.parseInt(line.substring(51, 53));	
					
						sepDate = new GregorianCalendar(y, m, d, h, min, s);
					}
					
					//SEP integrated intensity
					sep_int = line.substring(54, 59);
					if (sep_int.substring(0, 1).equals("<")) {
						up_lim = "Y";
						sep_int = sep_int.substring(1).trim();
					} else {
						up_lim = "N";
						if (sep_int.equals("     ")) {
							sep_int = "NaN";
						} else {
							sep_int = sep_int.trim();
						}
					}
					
					//expected SEP integrated intensity
					sep_exp = line.substring(61, 65);
					if (sep_exp.equals("    ")) sep_exp = "NaN";
				
					//comment
					comment = line.substring(68);
				
					if (loc.equals("NaN"))
					{
						if (sep_date) {
							pw.println(sdf.format( startDate.getTime() ) + "Z|" + 
									sdf.format( endDate.getTime() ) + "Z|" + 
									sdf.format( peakDate.getTime() ) + "Z|" + 			    			
									loc_source + "|" + "NaN" + "|" + "NaN" + "|" + 
									carr_long + "|" + optical + "|" + xray + "|" + 
									sdf.format( sepDate.getTime() ) + "Z|" +
									up_lim + "|" + sep_int + "|" + sep_exp + "|" + 
									comment);
						} else {
							pw.println(sdf.format( startDate.getTime() ) + "Z|" + 
			    				sdf.format( endDate.getTime() ) + "Z|" + 
			    				sdf.format( peakDate.getTime() ) + "Z|" + 			    			
			    				loc_source + "|" + "NaN" + "|" + "NaN" + "|" + 
			    				carr_long + "|" + optical +  "|" + xray + "|" + 
			    				"NaN" + "|" +
			    				up_lim + "|" + sep_int + "|" + sep_exp + "|" + 
			    				comment);			    		
						}
					} else {
						if (sep_date) {
							pw.println(sdf.format( startDate.getTime() ) + "Z|" + 
			    				sdf.format( endDate.getTime() ) + "Z|" + 
			    				sdf.format( peakDate.getTime() ) + "Z|" + 			    			
			    				loc_source + "|" + opt_lat_hg + "|" + opt_long_hg + "|" + 
			    				carr_long + "|" + optical +  "|" + xray + "|" + 
			    				sdf.format( sepDate.getTime() ) + "Z|" +
			    				up_lim + "|" + sep_int + "|" + sep_exp + "|" + 
			    				comment);
						} else {
							pw.println(sdf.format( startDate.getTime() ) + "Z|" + 
			    				sdf.format( endDate.getTime() ) + "Z|" + 
			    				sdf.format( peakDate.getTime() ) + "Z|" + 			    			
			    				loc_source + "|" + opt_lat_hg + "|" + opt_long_hg + "|" + 
			    				carr_long + "|" + optical +  "|" + xray + "|" + 
			    				"NaN" + "|" +
			    				up_lim + "|" + sep_int + "|" + sep_exp + "|" + 
			    				comment);			    		
						}
			    	
					}
			    
				}
			
			// line counter
			i++;
	
		}
		
		fw.close();
		
	} catch (Exception e) {System.out.println(e.getMessage());}
}

static private Calendar startDate;
static private Calendar endDate;
static private Calendar peakDate;
static private Calendar sepDate;

}
