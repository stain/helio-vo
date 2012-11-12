package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;     

public class HelioEventStereoEuvi2 {

	public static void main (String[] args)
	{
		
		final String shortNames[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		
		String id;
		
		String st_y;
		String st_m;
		String st_d;
		String st_h;
		String st_min;

		//String end_y;
		//String end_m;
		//String end_d;
		String end_h;
		String end_min;

		String location;
		String ns;
		String ew;
		int long_hg;
		int lat_hg;
		
		String cadence;
		String sc;
		String goes;
		String rhessi;
		
		String cts;
		String comments;
		String cme;
		
		int y;
		int m;
		int d;
		int h;
		int min;
		int s;
		
		int counts;
		
		
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\EUVI\\";
		
		// input fie name
		String inname = dir + "stereo_euvi_catalog_orig.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 1;
		
		// name of output file 
		String outname = dir + "STEREO_EUVI_CATALOG_2.txt";
		File out = new File(outname);
	
		try {
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
			
			//Old header
			//pw.println("ID Number" + "|" + "Start Time" + "|" + "End Time" + "|" + "latitude" + "|" + "longitude"
			//		+ "|" + "Cadence (s)" + "|" + "Spacecraft" + "|" + "GOES Flare Class"
			//		+ "|" + "RHESSI Peak Energy Range (keV)" + "|" + "RHESSI Peak Count (cts/s)" + "|" + "Comments"
			//		+ "|" + "CME Event Source");
			pw.println("id_num" + "|" + "time_start" + "|" + "time_end" + "|" + "lat_hg"     + "|" + "long_hg"
					            + "|" + "long_carr" + "|" + "loc" + "|" + "cadence"  + "|" + "spacecraft" + "|" + "xray_class"
					            + "|" + "rhessi_peak_range" + "|" + "rhessi_peak_count" + "|" + "rem"
					            + "|" + "cme_event_source");
			
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
					
					id = line.substring(0, 3);
					
					//Start time
					st_y = line.substring(5, 9);
					st_m = line.substring(10, 13);
					st_d = line.substring(14, 16);
					st_h = line.substring(18, 20);
					st_min = line.substring(21, 23);
					
					//*****
					System.out.println(st_y  + " " + st_m  + " " + st_d  + " " + st_h + " " + st_min);
					
					// date
					y = Integer.parseInt(st_y);
					m = 0;
					for (int j=0; j<shortNames.length; j++)
						if (st_m.equals(shortNames[j])) m = j;
					
					d = Integer.parseInt(st_d);
					h = Integer.parseInt(st_h);
					min = Integer.parseInt(st_min);
					s = 0;
					//*****
					System.out.println(y  + " " + m  + " " + d  + " " + h + " " + min);
					
							
					startDate = new GregorianCalendar(y, m, d, h, min, s);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

					//End time
					end_h = line.substring(24, 26);
					end_min = line.substring(27, 29);
					
					//*****
					System.out.println(end_h + " " + end_min);
					
					// hms
					h = Integer.parseInt(end_h);
					min = Integer.parseInt(end_min);
					s = 0;
			
					endDate = new GregorianCalendar(y, m, d, h, min, s);
					
					location = line.substring(31, 37);
					ns = location.substring(0, 1);
					lat_hg = Integer.parseInt(location.substring(1, 3));
					if (ns.equals("S")) lat_hg = - lat_hg;
					ew = location.substring(3, 4);
					long_hg = Integer.parseInt(location.substring(4, 6));
					if (ew.equals("E")) long_hg = - long_hg;
					
					cadence  = line.substring(39, 43).trim();
					
					sc = line.substring(45,47).trim();
					goes = line.substring(49,53).trim();
					
					//if (noaa.equals("-----")) noaa="NaN";
					//imp = line.substring(56,60).trim();
					//if (imp.equals("----")) imp="NaN";
					
					rhessi   = line.substring(55, 61);
					if (rhessi.equals("      ")) rhessi = " ";
					else rhessi = rhessi.trim();
					
					cts = line.substring(62, 66);
					if (cts.equals("    ")) cts = "NaN";
					else cts = cts.trim();

					comments = line.substring(68, 73).trim();
					cme = line.substring(76, 78);
					if (cme.equals("  ")) cme = " ";
					else cme = cme.trim();
					
					//*****
					//System.out.println(location  + " " + cadence  + " " + sc  + " " + goes + " " + rhessi
							//+ " " + cts + " " + comments + " " + cme);
					
					pw.println(id + "|" + sdf.format( startDate.getTime() ) + "Z|" + 
							sdf.format( endDate.getTime() ) + "Z|" + lat_hg + "|" + 
							long_hg + "|" + "NaN" + "|" + location + "|" +
							cadence + "|" + sc + "|" + goes + "|" + rhessi + "|" + 
							cts + "|" + comments + "|" + cme);
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
