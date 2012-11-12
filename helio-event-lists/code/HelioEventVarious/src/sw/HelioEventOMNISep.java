package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;     

public class HelioEventOMNISep {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String id;
		
		String st_y;
		String st_d;
		String st_m;

		String pk_d;
		String pk_h;
		
		String temp;
		
		String duration;
		String pk_flux;
		String fluence;
		String coverage;

		int y;
		int m;
		int d;
		int h;
		int min;
		int s;
		
		
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\gradual_SEPs\\";
		
		// input fie name
		String inname = dir + "2010ja016133-ds01.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 1;
		
		// name of output file 
		String outname = dir + "gradual_sep_event.txt";
		File out = new File(outname);
	
		try {
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
			
			pw.println("id" + "|" + "time_start" + "|" + "time_peak" + "|" +
					"duration" + "|" + "flux_peak" + "|" +
					"fluence" + "|" + "coverage");
			
			String delims = "[	]";
			String[] tokens;

			File file = new File(inname);
		
			FileReader fr = new FileReader ( file );
			BufferedReader in = new BufferedReader( fr);
				
			String line;
			//int i = 0;
			//while ((line = in.readLine()) != null) {
			for (int i=0;i<113;i++) {
				
				line = in.readLine();
				
				//skip lines at start of file 
				if (i>skiplines-1) {

					tokens = line.split(delims);
					System.out.println(line);
					
					id = tokens[0].trim();
					
					//Start time
					st_y = tokens[1].substring(6, 10);
					st_m = tokens[1].substring(3, 5);
					st_d = tokens[1].substring(0, 2);
					
					//*****
					System.out.println(st_y  + " " + st_m + " " + st_d);
					
					// date
					y = Integer.parseInt(st_y);
					m = Integer.parseInt(st_m) - 1;
					d = Integer.parseInt(st_d);
					
					// hms
					h = 0;
					min = 0;
					s = 0;
			
					startDate = new GregorianCalendar(y, m, d, h, min, s);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					
					

					//peak time
					temp = tokens[3].trim();
					pk_d = temp.substring(0, 2);
					pk_h = temp.substring(3, 5);
					
					d = Integer.parseInt(pk_d);
					h = Integer.parseInt(pk_h);
					
					peakDate = new GregorianCalendar(y, m, d, h, min, s);

					duration = tokens[2];
					
					pk_flux = tokens[4];
					fluence = tokens[5];
					coverage = tokens[6];
					
					pw.println(id + "|" + df.format(startDate.getTime() ) + "Z|" +
							sdf.format( peakDate.getTime() ) + "Z|" +
							duration + "|" + pk_flux + "|" + fluence + "|" +
							coverage);
		
				}
				
				// line counter
				//i++;
		
			}
			
			fw.close();
			
		} catch (Exception e) {System.out.println(e.getMessage());}
	}
	
	static private Calendar startDate;
	static private Calendar peakDate;

}

