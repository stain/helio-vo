package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

public class UlyssesHxrFarsideRemoveDoy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\Ulysses_hxr\\";
		
		// input file name
		String inname = dir + "ulysses_hxr_flare_farside_doy.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 1;
		
		// name of output file 
		String outname = dir + "ulysses_hxr_flare_farside.txt";
		File out = new File(outname);

		try {
			
			UlyssesHxrRemoveDoy ulyssesHxr = new UlyssesHxrRemoveDoy();
			
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
			
			pw.println("time_start" + "|" + "r_hgi" + "|" + "countrate_peak" + "|" + "inferred_xray_class" 
					+ "|" + "xray_class_error");
			
			
			String delims = "[|]";
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
					
					pw.println(tokens[0] + "|"  + tokens[2] + "|" + tokens[3]
							+ "|X" + tokens[4].trim() + " |X" + tokens[5].trim());
	
				}
				
				// line counter
				i++;
		
			}
			
			fw.close();
			
		} catch (Exception e) {System.out.println(e.getMessage());}


	}
}

