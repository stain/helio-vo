package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;     

public class HelioEventWavesWindStereo {
	
	public static void main (String[] args)
	{
		

	
		String st_y;
		String st_m;
		String st_d;
	
		String st_h;
		String st_min;

		String end_y;
		String end_m;
		String end_d;
		String end_h;
		String end_min;
		
		String id;
		String freq;
		String hi_freq;
		String lo_freq;
		String comments;
		String dyn;

		int y;
		int m;
		int d;
		int hm;
		int h;
		int min;
		int s;
	
	
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\Waves\\";
	
		// input fie name
		String inname = dir + "WAVES_Experiment_WIND_STEREO.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 2;
	
		// name of output file 
		String outname = dir + "WAVES_EXP_WIND_STEREO.txt";
	
		File out = new File(outname);

		try {
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
		
			pw.println("IDENTIFIER" + " | " + "START TIME" + " | " + "END TIME" + " | " + "HIGH FREQ (kHz)" + " | " + "LOW FREQ (kHz)"
					+ " | " + "DYN SPEC" + " | " + "COMMENTS");
		
			String delims = "[	]";
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
					
					//event id
					id = tokens[0].trim();
				
					//Start time
					st_y = tokens[1].substring(0, 4);
					st_m = tokens[1].substring(4, 6);
					st_d = tokens[1].substring(6, 8);
					
					st_h = tokens[2];
				
					//*****
					//System.out.println(st_y  + " " + st_m  + " " + st_d + " " + st_h);
				
					// date
					y = Integer.parseInt(st_y);
					m = Integer.parseInt(st_m)-1;
					d = Integer.parseInt(st_d);
				
					// hms
					hm = Integer.parseInt(st_h);
					h = (int)(((float)hm)/100.0);
					min = hm - h*100;
					s = 0;
					
					//*****
					System.out.println(y  + " " + m  + " " + d + " " + h + " " + min);
	
					startDate = new GregorianCalendar(y, m, d, h, min, s);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

				//End time
					end_y = tokens[3].substring(0, 4);
					end_m = tokens[3].substring(4, 6);
					end_d = tokens[3].substring(6, 8);
				
					end_h = tokens[4];

					//*****
					//System.out.println(end_y  + " " + end_doy + " " + end_h + " " + end_m);
					
					// date
					y = Integer.parseInt(end_y);
					m = Integer.parseInt(end_m)-1;
					d = Integer.parseInt(end_d);
				
					// hms
					hm = Integer.parseInt(end_h);
					h = (int)(((float)hm)/100.0);
					min = hm - h*100;
					s = 0;
		
					//*****
					System.out.println(y  + " " + m  + " " + d + " " + h + " " + min);

					endDate = new GregorianCalendar(y, m, d, h, min, s);
				
					freq = tokens[5];
				
					if (tokens.length>6) 
					{
						comments = removeSpecialCharacters(tokens[6]);
					}
					else comments = " ";
					
					if (tokens.length>7) dyn = removeSpecialCharacters(tokens[7]);
					else dyn = " ";
				
					//split frequency into upper and lower
					tokens = freq.split("-");
					hi_freq = tokens[0];
					lo_freq = tokens[1];

					pw.println(y + "-" + id + "|" + sdf.format( startDate.getTime() ) + "Z|" +
							sdf.format( endDate.getTime() ) + "Z|" + hi_freq + "|" + lo_freq + "|" +
							dyn + "|" + comments);
		
				}
			
				// line counter
				i++;
	
		}
		
		fw.close();
		
	} catch (Exception e) {System.out.println(e.getMessage());}
}
	
	 public static String removeSpecialCharacters(String instr) 
	    { 
		 int j =0;
		 String str = instr;
		 StringBuilder sb = new StringBuilder(); 
		 for (int i = 0; i < str.length(); i++) 
		 { 
			 if (str.substring(i,i+1).equals("\"") ) j=0;
			 else sb.append(str.substring(i,i+1)); 
		 } 	

		 return sb.toString(); 
	    } 

	static private Calendar startDate;
	static private Calendar endDate;


}
