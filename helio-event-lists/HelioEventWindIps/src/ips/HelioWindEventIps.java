package ips;

//import Date;
//import Months;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

public class HelioWindEventIps {
	
	public static void main (String[] args)
	{
		
		String s_y;
		String s_m;
		String s_d;
		String s_h;
		String s_min;
		
		int y;
		int d;
		int m;
		int h;
		int min;
		int s;
		
		
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\";
		
		// input fie name
		String inname = dir + "Interplanetary_Shocks.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 2;
		
		// name of output file 
		String outname = dir + "WIND_IMF_IPS.txt";
		File out = new File(outname);
	
		try {
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
			
			pw.println("TIME");
			
			String delims = "[,:-]";
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
					
					s_y = tokens[0];
					s_d = tokens[1];
					s_m = tokens[2].toUpperCase();
					s_h = tokens[3];
					s_min = tokens[4];
				
					System.out.println(s_y  + " " + s_m + " " + s_d + " " + s_h + " " + s_min);
					
					// start date
					y = Integer.parseInt(s_y);
					d = Integer.parseInt(s_d);
					
					//months
					m = Enum.valueOf(Months.class, s_m).ordinal()+1;
					
					// hms
					h = Integer.parseInt(s_h);
					min = Integer.parseInt(s_min);
					s = 0;
			
					start_date = new Date(y, m, d, h, min, s);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        			
					//System.out.println(sdf.format( start_date.toJavaDate() ) + "Z" + " | " +
					//		s_doy + " | " + sdf.format( end_date.toJavaDate() ) + "Z" + " | " + 
					//		e_doy + " | " + code + " | " + qual);
					pw.println(sdf.format( start_date.toJavaDate() ) + "Z" );
        
				}
				
				// line counter
				i++;
		
			}
			
			fw.close();
			
		} catch (Exception e) {System.out.println(e.getMessage());}
}
	
	static private Date start_date;

}

