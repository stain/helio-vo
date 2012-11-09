package sohoips;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class HelioEventSohoPmIpsAsc {
	
	public static void main (String[] args)
	{
		
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\";
		
		// input fie name
		String inname = dir + "SOHO_PM_IPS_ASCEND.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 1;
		
		// name of output file 
		String outname = dir + "SOHO_PM_IPS_FINAL.txt";
		File out = new File(outname);
	
		try {
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
			
			pw.println("TIME" + " | " + "DOY" + " | " + "ZONE" + " | " + "COMMENTS");
			
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
					
					//*****
					System.out.println(tokens[0].trim() + " | " + tokens[1].trim() + " | " +
							tokens[2].trim() + " | " + tokens[3].trim());
					pw.println(tokens[0].trim() + " | " + tokens[1].trim() + " | " +
							tokens[2].trim() + " | " + tokens[3].trim());
				}
				
				// line counter
				i++;
		
			}
			
			fw.close();
			
		} catch (Exception e) {System.out.println(e.getMessage());}
}
	


}
