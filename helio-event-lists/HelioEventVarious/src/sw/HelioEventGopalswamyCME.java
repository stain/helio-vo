package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;     

public class HelioEventGopalswamyCME {
	
	public static void main (String[] args)
	{
		
		String id;
		
		String st_y;
		String st_m;
		String st_d;
		String st_h;
		String st_min;

		String flare_y;
		String flare_m;
		String flare_d;
		String flare_h;
		String flare_min;
		Boolean flare_nan;

		String loc;
		String ns;
		String ew;
		String lat_hg;
		String long_hg;
		//Boolean lat_nan;
		//Boolean long_nan;

		//int long_hg;
		//int lat_hg;
		String posflag;
		
		String speed;
		String pa;
		String goes;
		
		String storm_y;
		String storm_m;
		String storm_d;
		String storm_h;
		String storm_min;
		String dst;
		
		int y;
		int m;
		int d;
		int h;
		int min;
		int s;
		
		
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\Gopalswamy\\";
		
		// input fie name
		String inname = dir + "Gopalswamy_2007_list.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 0;
		
		// name of output file 
		String outname = dir + "gopalswamy_cme_list.txt";
		File out = new File(outname);
	
		try {
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
			
			pw.println("cme_id" + "|" + "time_start" + "|" + "cme_speed" + "|" + "pa_measure" 
					+ "|" + "lat_hg" + "|" + "long_hg" + "|" + "long_carr" + "|" + "pos_flag"
					+ "|" + "time_flare" + "|" + "xray_class" + "|" + "time_storm"
					+ "|" + "dst");
			
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
					st_m = line.substring(10, 12);
					st_d = line.substring(13, 15);
					st_h = line.substring(16, 18);
					st_min = line.substring(19, 21);
					
					//*****
					//System.out.println(st_y  + " " + st_m  + " " + st_d  + " " + st_h + " " + st_min);
					
					// date
					y = Integer.parseInt(st_y);
					m = Integer.parseInt(st_m) -1;
					d = Integer.parseInt(st_d);
					h = Integer.parseInt(st_h);
					min = Integer.parseInt(st_min);
					s = 0;
					//*****
					System.out.println(y  + " " + m  + " " + d  + " " + h + " " + min);
					
							
					startDate = new GregorianCalendar(y, m, d, h, min, s);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					
					speed = line.substring(22, 26);
					pa    = line.substring(27, 30);
					
					loc = line.substring(32, 40).trim();
					ns = loc.substring(0, 1);
					if (ns.equals("N") || ns.equals("S"))
					{
						lat_hg = loc.substring(1, 3);
						if (ns.equals("S")) lat_hg = "-" + lat_hg;
						ew = loc.substring(3, 4);
						long_hg = loc.substring(4, 6);
						if (ew.equals("E")) long_hg = "-" + long_hg;
						posflag = "  ";
					} else {
						posflag = loc;
						lat_hg = "NaN";
						long_hg = "NaN";
					}
					

					//flare time
					flare_y = st_y;
					flare_m = line.substring(41, 43);
					
					flare_nan = false;
					if (flare_m.equals("--"))
					{
						flare_nan = true;
					} else
					{
						flare_d = line.substring(44, 46);
						flare_h = line.substring(47, 49);
						flare_min = line.substring(50, 52);
					
						y = Integer.parseInt(flare_y);
						m = Integer.parseInt(flare_m) - 1;
						d = Integer.parseInt(flare_d);
						h = Integer.parseInt(flare_h);
						min = Integer.parseInt(flare_min);
						s = 0;
						//*****
						System.out.println("flare: " + y  + " " + m  + " " + d  + " " + h + " " + min);

						flareDate = new GregorianCalendar(y, m, d, h, min, s);
					}
					
					goes = line.substring(53,57);
					if (goes.equals("----")) goes = "NaN";
					
					//storm time
					storm_y = st_y;
					storm_m = line.substring(59, 61);
					storm_d = line.substring(62, 64);
					storm_h = line.substring(65, 67);
					storm_min = line.substring(68, 70);
					
					y = Integer.parseInt(storm_y);
					m = Integer.parseInt(storm_m) - 1;
					d = Integer.parseInt(storm_d);
					h = Integer.parseInt(storm_h);
					min = Integer.parseInt(storm_min);
					s = 0;
					//*****
					System.out.println("storm: " + y  + " " + m  + " " + d  + " " + h + " " + min);

					stormDate = new GregorianCalendar(y, m, d, h, min, s);					
					
					dst = line.substring(71,75);
					
				    if (flare_nan)
				    {
				    	pw.println(id + "|" + sdf.format( startDate.getTime() ) + "Z|" + 
								speed + "|" + pa + "|" + lat_hg + "|" + long_hg + "|" + 
								"NaN" +  "|" + posflag + "|" + "NaN" +  "|" + 
								goes + "|" + sdf.format( stormDate.getTime() ) + "Z|" +
								dst);
				    } else {
				    	pw.println(id + "|" + sdf.format( startDate.getTime() ) + "Z|" + 
				    			speed + "|" + pa + "|" + lat_hg + "|" + long_hg + "|" + 
				    			"NaN" +  "|" + posflag + "|" + sdf.format( flareDate.getTime() ) + "Z|" +  
				    			goes + "|" + sdf.format( stormDate.getTime() ) + "|" +
				    			dst);
				    }
				}
				
				// line counter
				i++;
		
			}
			
			fw.close();
			
		} catch (Exception e) {System.out.println(e.getMessage());}
}
	
	static private Calendar startDate;
	static private Calendar flareDate;
	static private Calendar stormDate;

}
