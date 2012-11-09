package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;     

public class HelioEventWindTypeII2 {

	public static void main (String[] args)
	{
		
	String st_y;
	String st_m;
	String st_d;
	String st_h;
	String st_min;

	//String end_y;
	String end_m;
	String end_d;
	String end_h;
	String end_min;

	String st_freq;
	String end_freq;
	
	String loc;
	String ns;
	String ew;
	String lat_hg;
	String long_hg;
	Boolean lat_nan;
	Boolean long_nan;
	
	String noaa;
	String filar;
	String imp;
	
	String cpa;
	
	String width;
	String wid_ll;
	
	String speed;
	
	String comments;
	String rem = " ";
	
	int y;
	int m;
	int d;
	int h;
	int min;
	int s;
	
	
	// directory containing the event list
	String dir  = "C:\\Development\\HELIO\\Event_lists\\";
	
	// input fie name
	String inname = dir + "waves_type2.txt";

	// args[2] - lines to skip at start of list
	int skiplines = 8;
	
	// name of output file 
	String outname = dir + "RADIO_LOUD_CMES.txt";
	File out = new File(outname);

	try {
		FileWriter fw = new FileWriter( out );
		PrintWriter pw = new PrintWriter( fw );
		
		//Old header
		//pw.println("Start Time" + "|" + "End Time" + "|" + "Start Frequency (kHz)"
		//		+ "|" + "End Frequency (kHz)" + "|" + "Latitude" + "|" + "Longitude"
		//		+ "|" + "loc" + "|" + "NOAA" + "|" + "filament region"
		//		+ "|" + "Importance" + "|" + "CME Time" + "|" + "Central PA (deg)"
		//		+ "|" + "Width lower limit" + "|" + "Width (deg)" + "|" + "Speed (km/s)" + "|" + "Comments");
		pw.println("time_start" + "|" + "time_end" + "|" + "freq_start"  + "|" + "freq_end"
				+ "|" + "lat_hg"+ "|" + "long_hg"  + "|" + "long_carr"   + "|" + "loc"
				+ "|" + "nar"   + "|" + "filar"    + "|" + "xray_class"  + "|" + "time_cme"
				+ "|" + "pa"    + "|" + "pa_width_ll" + "|" + "pa_width" + "|" + "v_ps"
				+ "|" + "comment");
		
		
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
				st_h = line.substring(11, 13);
				st_min = line.substring(14, 16);
				
				//*****
				System.out.println(st_y  + " " + st_m  + " " + st_d  + " " + st_h + " " + st_min);
				
				// date
				y = Integer.parseInt(st_y);
				m = Integer.parseInt(st_m) - 1;
				d = Integer.parseInt(st_d);
				
				// hms
				if (st_h.equals("??"))
				{
					h   = 0;
					min = 0;
				}
				else
				{
					h = Integer.parseInt(st_h);
					min = Integer.parseInt(st_min);
				}
				s = 0;
				
				//*****
				System.out.println(y  + " " + m  + " " + d  + " " + h + " " + min);
				
						
				startDate = new GregorianCalendar(y, m, d, h, min, s);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

				//End time
				end_m = line.substring(17, 19);
				end_d = line.substring(20, 22);
				end_h = line.substring(23, 25);
				end_min = line.substring(26, 28);
				
				//*****
				System.out.println(st_y  + " " + end_m  + " " + end_d + " " + end_h + " " + end_min);
				
				// date
				m = Integer.parseInt(end_m) - 1;
				d = Integer.parseInt(end_d);
				
				// hms
				h = Integer.parseInt(end_h);
				min = Integer.parseInt(end_min);
				s = 0;
		
				endDate = new GregorianCalendar(y, m, d, h, min, s);
				
				st_freq  = line.substring(29, 34).trim();
				if (st_freq.equals("????")) st_freq="NaN";
				end_freq = line.substring(35, 40).trim();
				if (end_freq.equals("????")) end_freq="NaN";
				
				loc = line.substring(43,49).trim();

				ns = loc.substring(0,1);
				ew = loc.substring(1,2);
				if (loc.equals("S90b")) {
					lat_hg  = "NaN";
					long_hg = "NaN";	
				} else if (ns.equals("N") || ns.equals("S")) {
					if (ew.equals("E") || ew.equals("W")) {
						lat_hg  = "NaN";
						long_hg = "NaN";
					} else {
					lat_hg = loc.substring(1, 3);
					if (ns.equals("S")) lat_hg = "-" + lat_hg;
					ew = loc.substring(3, 4);
					long_hg = loc.substring(4, 6);
					if (ew.equals("E")) long_hg =  "-" + long_hg;
					}
				}
				else {
					lat_hg  = "NaN";
					long_hg = "NaN";
				}
				
				//
				filar = "  ";
				noaa = line.substring(50,55).trim();
				if (noaa.equals("-----")) noaa="NaN";
				if (noaa.equals("FILA")) {
					filar = noaa;
					noaa = "NaN";
				}
				if (noaa.equals("DSF")) {
					filar = noaa;
					noaa = "NaN";
				}
				
				imp = line.substring(56,60).trim();
				if (imp.equals("----")) imp="NaN";
				
				// for one event noaa active region is given a value altr
				// the FILA flag is allocated to flare importance in error
				// this corrects that record.
				if (noaa.equals("altr")) {
					filar = imp;
					noaa = "NaN";
					imp = "NaN";
				}


				cpa   = line.substring(75, 79);
				if (cpa.equals("Halo")) {
					rem = cpa + ". ";
					cpa = "NaN";
				} else {
					rem = " ";
				}
					
				wid_ll = " ";
				width = line.substring(80, 84);
				if (width.substring(0, 1).equals(">")) {
					wid_ll = ">";
					width = width.substring(1);
				} else if (width.substring(1, 2).equals(">")) {     // only case where lower limit is angle < 100
					wid_ll = ">";
					width = width.substring(2);
				}
					
				
				speed = line.substring(85, 89);
				
				System.out.println(line.length());
				if (line.length() > 96)
					comments = line.substring(98);
				else
					comments = " ";
				rem = rem + comments;
				
				//CME time
				end_m = line.substring(63, 65);
				
				if (end_m.equals("  ") || end_m.equals("--"))
				{
					pw.println(sdf.format( startDate.getTime() ) + "Z|" +
							sdf.format( endDate.getTime() ) + "Z|" + st_freq + "|" + end_freq
							+ "|" + lat_hg + "|" + long_hg + "|" + "NaN" + "|" + loc + "|" + noaa + "|" + filar 
							+ "|" + imp + "|" + "NaN" + "|" + "NaN"
							+ "|" + wid_ll + "|" + "NaN"
							+ "|" + "NaN" + "|" + rem);
				}
				else
				{
					end_d = line.substring(66, 68);
					end_h = line.substring(69, 71);
					end_min = line.substring(72, 74);
				
					//*****
					System.out.println("CME: " + st_y  + " " + end_m  + " " + end_d + " " + end_h + " " + end_min);

					// date
					m = Integer.parseInt(end_m) - 1;
					d = Integer.parseInt(end_d);
					
					// hms
					h = Integer.parseInt(end_h);
					min = Integer.parseInt(end_min);
					s = 0;
				
					cmeDate = new GregorianCalendar(y, m, d, h, min, s);
					pw.println(sdf.format( startDate.getTime() ) + "Z|" +
							sdf.format( endDate.getTime() ) + "Z|" + st_freq + "|" + end_freq
							+ "|" + lat_hg + "|" + long_hg + "|" + "NaN" + "|" + loc + "|" + noaa + "|" + filar 
							+ "|" + imp + "|" + sdf.format( cmeDate.getTime() ) + "Z|" + cpa 
							+ "|" + wid_ll + "|" + width
							+ "|" + speed + "|" + rem);
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
static private Calendar cmeDate;


}
