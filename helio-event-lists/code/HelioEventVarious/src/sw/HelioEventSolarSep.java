package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;     


public class HelioEventSolarSep {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int no_tokens;
		
		String month;
		
		String startTimeTypeIII;
		String endTimeTypeIII;
		String startTimeFlare;
		String startTimeCme;
		
		String flag_slow_rise;
		String energy_max;
		String proton_intensity_peak;
		String dig;
		
		String metric_typeII;
		
		String dt_rise;

		String ns;
		String ew;
		String long_hg;
		String lat_hg;
		int o_long;
		int o_lat;
		
		String long_carr;
		
		String xray_class;
		
		String pa_width;

		String v_cme;
		String v_shock;
		String flag_pass_shock;
		String group_sep;

		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\solar_sep_events\\";
		
		// input file name
		String inname = dir + "solar_sep_event_table.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 1;
		
		// name of output file 
		String outname = dir + "solar_sep_event_no_carr.txt";
		File out = new File(outname);

		try {
			
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
			
			pw.println("time_start" + "|" + "flag_slow_rise" + "|" + "energy_max" 
					+ "|" + "proton_intensity_peak" + "|" + "time_typeIII_start"
					+ "|" + "time_typeIII_end" + "|" + "metric_typeII" + "|" + "time_start_flare" 
					+ "|" + "dt_rise" + "|" + "lat_hg" + "|" + "long_hg" 
					+ "|" + "long_carr" + "|" + "lat_ns" + "|" + "xray_class"
					+ "|" + "time_start_cme" + "|" + "pa_width" + "|" + "v_cme" + "|" + "v_shock"
					+ "|" + "flag_pass_shock" + "|" + "group_sep" + "|" + "");
			
			
			String delims = " ";
			String del = "/";
			String[] tokens;
			String[] tok;

			File file = new File(inname);
		
			FileReader fr = new FileReader ( file );
			BufferedReader in = new BufferedReader( fr);
				
			String line;
			int len = 0;
			int i = 0;
			while ((line = in.readLine()) != null) {
					
				//skip lines at start of file 
				if (i>skiplines-1) {
					
					//tokens = line.split(delims);
					
					//System.out.println(line);

					//no_tokens = tokens.length;
					//System.out.println("No. of tokens = " + no_tokens);
					
					//Start time					
					y = Integer.parseInt(line.substring(0, 4));
					
					d = Integer.parseInt(line.substring(5, 7).trim());
					month = line.substring(8, 11).toUpperCase();
					m = Enum.valueOf(Months.class, month).ordinal();
					h = Integer.parseInt(line.substring(12, 14));
					min = 0;
					s = 0;
					
					//*****
					System.out.println(y  + " " + m  + " " + d  + " " + h + " " + min);
									
					startDate = new GregorianCalendar(y, m, d, h, min, s);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					
					flag_slow_rise = line.substring(11, 12);
					if (flag_slow_rise.equals("<")) flag_slow_rise = "S";
					
					energy_max = line.substring(15, 18);
					if (energy_max.equals("   ")) energy_max = "NaN";
					energy_max = energy_max.trim();
					
					proton_intensity_peak = line.substring(19, 25);					
					if (proton_intensity_peak.substring(0, 6).equals("      ")) {
						proton_intensity_peak = "NaN";
					}
					else if (proton_intensity_peak.substring(1, 4).equals("e-5")) {
						dig = proton_intensity_peak.substring(0, 1);
						proton_intensity_peak = "0.0000" + dig;
					}
					proton_intensity_peak = proton_intensity_peak.trim();
					
					//start time of Type III event
					if (line.substring(26, 30).equals("    ")) {
						startTimeTypeIII = "NaN";
					} else {
						h = Integer.parseInt(line.substring(26, 28));
						min = Integer.parseInt(line.substring(28, 30));
						s = 0;
				
						//*****
						System.out.println(y  + " " + m  + " " + d  + " " + h + " " + min);
										
						startTypeIIIDate = new GregorianCalendar(y, m, d, h, min, s);
						startTimeTypeIII = sdf.format( startTypeIIIDate.getTime() ) + "Z";
					
					}
					
					//end time of Type III event
					if (line.substring(31, 35).equals("    ")) {
						endTimeTypeIII = "NaN";
					} else {
						h = Integer.parseInt(line.substring(31, 33));
						min = Integer.parseInt(line.substring(33, 35));
						s = 0;
				
						//*****
						System.out.println(y  + " " + m  + " " + d  + " " + h + " " + min);
										
						endTypeIIIDate = new GregorianCalendar(y, m, d, h, min, s);
						endTimeTypeIII = sdf.format( endTypeIIIDate.getTime() ) + "Z";
					
					}
					System.out.println(startTimeTypeIII  + " " + endTimeTypeIII);
					metric_typeII = line.substring(36, 37);

					
					//start time of flare
					if (line.substring(38, 42).equals("    ")) {
						startTimeFlare = "NaN";
					} else {					
						h = Integer.parseInt(line.substring(38, 40));
						min = Integer.parseInt(line.substring(40, 42));
						s = 0;
				
						//*****
						System.out.println(y  + " " + m  + " " + d  + " " + h + " " + min);
					
						startFlareDate = new GregorianCalendar(y, m, d, h, min, s);
						startTimeFlare = sdf.format( startFlareDate.getTime() ) + "Z";
					
					}
					
					dt_rise = line.substring(43, 46);
					if (dt_rise.equals("   ")) dt_rise = "NaN";
					dt_rise = dt_rise.trim();

					ns = line.substring(49, 50);
					if (line.substring(47, 49).equals("  ")) {
						lat_hg = "NaN";
						o_lat = 0;
					} else {
						o_lat = Integer.parseInt(line.substring(47, 49));
						if (ns.equals("S")) o_lat = - o_lat;
						lat_hg = Integer.toString(o_lat);
					}
					
					ew = line.substring(55, 56);
					o_long = Integer.parseInt(line.substring(52, 55).trim());
					if (ew.equals("E")) o_long = - o_long;
					
					long_hg = Integer.toString(o_long);
					
					long_carr = "NaN";
					
					xray_class = line.substring(57, 60).trim();
					
					//start time of CME
					if (line.substring(63, 65).equals("dg")) {
						startTimeCme = "NaN";
					} else {										
						h = Integer.parseInt(line.substring(61, 63));
						min = Integer.parseInt(line.substring(63, 65));
						s = 0;
				
						//*****
						System.out.println(y  + " " + m  + " " + d  + " " + h + " " + min);
					
						startCmeDate = new GregorianCalendar(y, m, d, h, min, s);
						startTimeCme = sdf.format( startCmeDate.getTime() ) + "Z";
					
					}
					
					pa_width = line.substring(66, 69).trim();
					if (pa_width.equals("dg")) pa_width = "NaN";
					
					v_cme = line.substring(70, 74).trim();
					if (v_cme.equals("dg")) v_cme = "NaN";
					
					v_shock = line.substring(75, 79);
					flag_pass_shock = "";
					if (v_shock.trim().equals("PS") || v_shock.trim().equals("ps")) {
						flag_pass_shock = v_shock.trim();
						v_shock = "NaN";
					} else if (v_shock.equals("    ")) v_shock = "NaN";
					v_shock = v_shock.trim();
					
					group_sep = line.substring(80, 82);
					if (group_sep.equals("  ")) group_sep = "NaN";
					else if (group_sep.equals("dg")) group_sep = "NaN";
					group_sep = group_sep.trim();

					pw.println(sdf.format( startDate.getTime() ) + "Z|" + flag_slow_rise
							+ "|" + energy_max + "|" + proton_intensity_peak 
							+ "|" + startTimeTypeIII 
							+ "|" + endTimeTypeIII 
							+ "|" + metric_typeII + "|" +  startTimeFlare
							+ "|" + dt_rise + "|" + lat_hg + "|" + long_hg 
							+ "|" + long_carr + "|" +  ns + "|" +  xray_class
							+ "|" + startTimeCme
							+ "|" + pa_width 
							+ "|" + v_cme + "|" + v_shock + "|" + flag_pass_shock
							+ "|" + group_sep);
	
				}
				
				// line counter
				i++;
		
			}
			
			fw.close();
			
		} catch (Exception e) {System.out.println(e.getMessage());}


	}
	
	static private int y;
	static private int m;
	static private int d;
	static private int h;
	static private int min;
	static private int s;
	
	static private Calendar startDate;
	static private Calendar startTypeIIIDate;
	static private Calendar endTypeIIIDate;
	static private Calendar startFlareDate;
	static private Calendar startCmeDate;


}
