package validate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cds.savot.pull.*;
//import cds.savot.common.*;
import cds.savot.model.*;

public class HelioValImp8SolarWindTimes {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// directory containing the event list
		String deldir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\imp8_solar_wind_times\\";
		
		String valdir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\imp8_solar_wind_times\\";
		// input delivered file name
		String delname = deldir + "IMP8_SOLAR_WIND_TIMES.txt";
		
		// input VOTable name
		String valname = valdir + "imp8_solar_wind_times_full.xml";

		// lines to skip at start of list
		int skiplines = 1;
		
		// the whole VOTable file is put into memory
		SavotPullParser sb = new SavotPullParser(valname, SavotPullEngine.FULL); //!!! parsing of  the whole source  
    
		// get the VOTable object
		SavotVOTable sv = sb.getVOTable(); //!!! sv is now a reference to a VOTable object
		
		// get resource
		SavotResource currentResource = (SavotResource)(sv.getResources().getItemAt(0));
		
		// get rows of Table
		TRSet tr = currentResource.getTRSet(0);
    
		try {

			// 
			String delims = "[|]";
			String[] tokens;

			File file = new File(delname);
		
			FileReader fr = new FileReader ( file );
			BufferedReader delin = new BufferedReader( fr);

			String deline;
			
			int i = 0;
			int j = 0;
			while ((deline = delin.readLine()) != null) {
					
				//skip lines at start of file 
				if (i>skiplines-1) {

					tokens = deline.split(delims);
					
					TDSet theTDs = tr.getTDSet(i-skiplines);
					//TDSet theTDs = tr.getTDSet(i);
										
					if (tokens[0].trim().equals(theTDs.getContent(0)+"Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[0].trim() + " " + theTDs.getContent(0) + "Z");
						
					// end time
					if (tokens[1].trim().equals(theTDs.getContent(1)+"Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[1].trim() + " " + theTDs.getContent(1) + "Z");
						
				}
				
				//System.out.println(tokens[6].trim());
				i++;
			} 
			
			System.out.println("Testing");
		} catch (Exception e) {System.out.println(e.getMessage());}
	}

}
