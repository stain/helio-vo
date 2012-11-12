package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;  

public class HelioEventYohkohHXT {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String id;
		
		String st_y;
		String st_m;
		String st_d;
		
		String st_hms;

		String st_flag;
		
		String peak_hms;
		Boolean peak_time = true;

		String peak_flag;
		
		String end_hms;

		String end_flag;
		
		String hxt_x;
		String hxt_y;
		
		String lat_hg;
		String long_hg;
		Boolean lat_nan;
		Boolean long_nan;
		
		String hxt_lo;
		String hxt_m1;
		String hxt_m2;
		String hxt_hi;
		
		String xray;
		String optical;
		
		String loc;
		String ns;
		String ew;
		String opt_long_hg;
		String opt_lat_hg;
		int o_long;
		int o_lat;
		
		String noaa;
		
		String comment;
		String rem = " ";
		
		String id2;
		
		String sxs_pc21 = " ";
		String sxs_pc22 = " ";
		String hxs_pc1  = " ";
		String hxs_pc2  = " ";

		String x_long_carr = "NaN";
		String long_carr   = "NaN";
			
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\Yohkoh\\";
		
		// input file names for HXT and WBS
		String inname = dir + "HXT_flare.txt";
		
		String inname2 = dir + "WBS_flare.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 2;
		
		// name of output file 
		String outname = dir + "YOHKOH_HXT_FLARE.txt";
		File out = new File(outname);

		try {
			
			HelioEventYohkohHXT yohkohHXT = new HelioEventYohkohHXT();
			
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
			
			pw.println("yoh_event" + "|" + "time_start" + "|" + "time_peak" + "|" + "time_end"
					+ "|" + "hxt_x"+ "|" + "hxt_y"  + "|" + "x_long_carr"   + "|" + "hxt_lo"
					+ "|" + "hxt_m1"   + "|" + "hxt_m2" + "|" + "hxt_hi" + "|" + "sxs_pc21"
					+ "|" + "sxs_pc22" + "|" + "hxs_pc1" + "|" + "hxs_pc2" + "|" + "xray_class" 
					+ "|" + "optical_class" + "|" + "opt_lat" + "|" + "opt_long"
					+ "|" + "long_carr" + "|" + "loc" + "|" + "nar" + "|" + "comment");
			
			
			//String delims = "[ :.]";
			//String[] tokens;
            
			// HXT_flare
			File file = new File(inname);
		
			FileReader fr = new FileReader ( file );
			BufferedReader in = new BufferedReader( fr);
			
			// WBS_flare
			File file2 = new File(inname2);
		
			FileReader fr2 = new FileReader ( file2 );
			BufferedReader in2 = new BufferedReader( fr2);
						
				
			String line;
			String line2;
			int i = 0;
			while ((line = in.readLine()) != null) {
					
				line2 = in2.readLine();
				
				//skip lines at start of file 
				if (i>skiplines-1) {

					//tokens = line.split(delims);
					
					//HXT file
					//Event ID
					id = line.substring(0, 5);
					
					//Start time
					st_y = line.substring(6, 10);
					st_m = line.substring(11, 13);
					st_d = line.substring(14, 16);
					st_hms = line.substring(17, 23);
					
					st_flag = line.substring(23, 24);
					
					startDate = yohkohHXT.convertDate(st_y, st_m, st_d, st_hms);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

					//*****
					System.out.println(st_y  + " " + st_m  + " " + st_d  + " " + st_hms);
					
					// Peak time
					peak_hms = line.substring(25, 31);
					
					peak_flag = line.substring(31, 32);
					
					if (peak_hms.equals("-     "))
					{
						peak_time   = false;
					}
					else
					{
						peak_time = true;
						peakDate = yohkohHXT.convertDate(st_y, st_m, st_d, peak_hms);
					}
					
					//End time
					end_hms = line.substring(33, 39);
					
					end_flag = line.substring(39, 40);
			
					endDate = yohkohHXT.convertDate(st_y, st_m, st_d, end_hms);
					
					//*****
					System.out.println(st_y  + " " + st_m  + " " + st_d  + " " + end_hms);
					
					
					hxt_x = line.substring(41,47).trim();
					if (hxt_x.equals("")) hxt_x = "NaN";
					hxt_y = line.substring(48,54).trim();
					if (hxt_y.equals("")) hxt_y = "NaN";
					
					hxt_lo = line.substring(55,60).trim();
					if (hxt_lo.equals("-")) hxt_lo = "NaN";
					hxt_m1 = line.substring(60,65).trim();
					if (hxt_m1.equals("-")) hxt_m1 = "NaN";
					hxt_m2 = line.substring(65,70).trim();
					if (hxt_m2.equals("-")) hxt_m2 = "NaN";
					hxt_hi = line.substring(70,75).trim();
					if (hxt_hi.equals("-")) hxt_hi = "NaN";
					
					xray    = line.substring(75,80).trim();
					if (xray.equals("")) xray = "NaN";
					optical = line.substring(81,83).trim();
					if (optical.equals("")) optical = "NaN";
					
					loc     = line.substring(84,90).trim();
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
					
					noaa    = line.substring(91,95).trim();
					if (noaa.equals("")) noaa = "NaN";
					
					comment = line.substring(96,98);
					
					//HXT file
					//Event ID
					id2 = line2.substring(0, 5);
					
					if (id2.equals(id)) {
						
						sxs_pc21 = line2.substring(5, 18).trim();
						if (sxs_pc21.equals("-")) sxs_pc21 = "NaN";
						sxs_pc22 = line2.substring(18, 31).trim();
						if (sxs_pc22.equals("-")) sxs_pc22 = "NaN";
						
						hxs_pc1  = line2.substring(31, 44).trim();
						if (hxs_pc1.equals("-")) hxs_pc1 = "NaN";
						hxs_pc2  = line2.substring(44, 57).trim();
						if (hxs_pc2.equals("-")) hxs_pc2 = "NaN";
					}
					
					if (peak_time) {
						pw.println(id + "|" + sdf.format( startDate.getTime() ) + "Z|" + sdf.format( peakDate.getTime() )
								+ "Z|" + sdf.format( endDate.getTime() ) + "Z|" + hxt_x + "|" + hxt_y + "|" + x_long_carr 
								+ "|" + hxt_lo + "|" + hxt_m1 + "|" + hxt_m2 + "|" + hxt_hi + "|" + sxs_pc21 + "|" + sxs_pc22
								+ "|" + hxs_pc1 + "|" + hxs_pc2 + "|" + xray + "|" + optical 
								+ "|" + opt_lat_hg + "|" + opt_long_hg + "|" + long_carr + "|" + loc + "|" + noaa + "|" + comment);
					} else {
						pw.println(id + "|" + sdf.format( startDate.getTime() ) + "Z|" + "NaN"
								+ "|" + sdf.format( endDate.getTime() ) + "Z|" + hxt_x + "|" + hxt_y + "|" + x_long_carr
								+ "|" + hxt_lo + "|" + hxt_m1 + "|" + hxt_m2 + "|" + hxt_hi + "|" + sxs_pc21 + "|" + sxs_pc21
								+ "|" + hxs_pc1 + "|" + hxs_pc2 + "|" + xray + "|" + optical 
								+ "|" + opt_lat_hg + "|" + opt_long_hg + "|" + long_carr + "|" + loc + "|" + noaa + "|" + comment);
					}
			
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
