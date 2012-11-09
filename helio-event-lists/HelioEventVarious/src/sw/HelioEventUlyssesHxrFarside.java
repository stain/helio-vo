package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;     

public class HelioEventUlyssesHxrFarside {
	public static void main (String[] args)
	{
		
		String cat_y;
		String cat_doy;
		String cat_h;
		String cat_m;
		
		int y;
		int d;
		int doy;
		int m;
		int h;
		int min;
		int s;
		
		String r;
		String peak_cnt;
		String x_class;
		String error;
		
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\Ulysses_hxr\\";
		
		// input fie name
		String inname = dir + "Ulysses_hxr_farside.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 1;
		
		// name of output file 
		String outname = dir + "ulysses_hxr_flare_farside.txt";
		File out = new File(outname);
	
		try {
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
			
			pw.println("time_start" + " | " + "doy" + " | " + "r_hgi" + " | " + "peak_counts" + " | " +
					   "inferred_xray_class" + " | " + "xray_class_error");
			
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
					
					//Last BS crossing
					cat_y = line.substring(0, 4);
					cat_doy = line.substring(5, 8).trim();
					cat_h = line.substring(9, 11);
					cat_m = line.substring(11, 13);
				
					System.out.println(cat_y  + " " + cat_doy + " " + cat_h + " " + cat_m);
					
					// date
					y = Integer.parseInt(cat_y);
					doy = Integer.parseInt(cat_doy);
					

					// hms
					h = Integer.parseInt(cat_h);
					min = Integer.parseInt(cat_m);
					s = 0;
			
					m = 0;
					d = 0;
					startTimeDate = new GregorianCalendar(y, m, d, h, min, s);
					startTimeDate.add(Calendar.DAY_OF_YEAR, doy);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					
					r        = line.substring(14, 18);
					peak_cnt = line.substring(19, 24);
					x_class  = line.substring(25, 29);
					error    = line.substring(30, 33);
					
					pw.println(sdf.format( startTimeDate.getTime() ) + "Z | " + doy + " | " + r 
							+ " | " + peak_cnt + " | " + x_class + " | " + error);
       
				}
				
				// line counter
				i++;
		
			}
			
			fw.close();
			
		} catch (Exception e) {System.out.println(e.getMessage());}
}
	
	static private Calendar startTimeDate;

}
