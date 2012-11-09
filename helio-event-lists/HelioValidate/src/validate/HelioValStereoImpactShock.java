package validate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cds.savot.model.SavotResource;
import cds.savot.model.SavotVOTable;
import cds.savot.model.TDSet;
import cds.savot.model.TRSet;
import cds.savot.pull.SavotPullEngine;
import cds.savot.pull.SavotPullParser;

public class HelioValStereoImpactShock {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// directory containing the event list
		String deldir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\stereo_impactplastic_ips\\";
		
		String valdir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\stereo_impactplastic_ips\\";
		
		// input delivered file name
		String delname = deldir + "stereoa_impactplastic_shock.txt";
		
		// input VOTable file name
		String valname = valdir + "stereoa_impactplastic_shock_full.xml";

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

			String delims = "[|]";
			String[] tokens;

			File file = new File(delname);
		
			FileReader fr = new FileReader ( file );
			BufferedReader delin = new BufferedReader( fr);
				
			String deline;
			
			int i = 0;
			int j = 0;
			while ((deline = delin.readLine()) != null) {
					
				//skip line(s) at start of devilered file 
				if (i>skiplines-1) {

					tokens = deline.split(delims);
					
					TDSet theTDs = tr.getTDSet(i-skiplines);
					
					//id
					if (tokens[0].trim().equals(theTDs.getContent(1))) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[0].trim() + " " + theTDs.getContent(1));
					
					//time_start
					if (tokens[1].trim().equals(theTDs.getContent(2).substring(0, 10) + "T" + theTDs.getContent(2).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[1].trim() + " " + theTDs.getContent(2));
					
					//mag_ratio
					if (tokens[2].trim().equals(theTDs.getContent(3))) j = 0;
					else if (tokens[2].trim().equals(theTDs.getContent(3) + "0")) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[2].trim() + " " + theTDs.getContent(3));
					
					//norm_angle
					if (tokens[3].trim().equals(theTDs.getContent(4))) j = 0;
					else if (tokens[3].trim().equals(theTDs.getContent(4) + "0")) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[3].trim() + " " + theTDs.getContent(4));
					
					//beta
					if (tokens[4].trim().equals(theTDs.getContent(5))) j = 0;
					else if (tokens[4].trim().equals(theTDs.getContent(5) + "0")) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[4].trim() + " " + theTDs.getContent(5));
					
					//mach_no
					if (tokens[5].trim().equals(theTDs.getContent(6))) j = 0;
					else if (tokens[5].trim().equals(theTDs.getContent(6) + "0")) j = 0;
					else if (theTDs.getContent(6).equals(tokens[5].trim() + ".0")) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[5].trim() + " " + theTDs.getContent(6));
					
					//data_avail
					if (tokens[6].trim().equals(theTDs.getContent(7))) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[6].trim() + " " + theTDs.getContent(7));
					
					//f_r_shock
					if (tokens[7].trim().equals(theTDs.getContent(8))) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[7].trim() + " " + theTDs.getContent(8));
					
					//comment
					if (tokens[8].trim().equals(theTDs.getContent(9))) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[8].trim() + " " + theTDs.getContent(9));
										
				}
				// line counter
				i++;
		
			}
			
		} catch (Exception e) {System.out.println(e.getMessage());}
		
		// input delivered file name
		delname = deldir + "stereob_impactplastic_shock.txt";
		
		// input VOTable file name
		valname = valdir + "stereob_impactplastic_shock_full.xml";

		// lines to skip at start of list
		skiplines = 1;
		
		// the whole VOTable file is put into memory
		sb = new SavotPullParser(valname, SavotPullEngine.FULL); //!!! parsing of  the whole source  
    
		// get the VOTable object
		sv = sb.getVOTable(); //!!! sv is now a reference to a VOTable object
		
		// get resource
		currentResource = (SavotResource)(sv.getResources().getItemAt(0));
		
		// get rows of Table
		tr = currentResource.getTRSet(0);

		try {

			String delims = "[|]";
			String[] tokens;

			File file = new File(delname);
		
			FileReader fr = new FileReader ( file );
			BufferedReader delin = new BufferedReader( fr);
				
			String deline;
			
			int i = 0;
			int j = 0;
			while ((deline = delin.readLine()) != null) {
					
				//skip line(s) at start of devilered file 
				if (i>skiplines-1) {

					tokens = deline.split(delims);
					
					TDSet theTDs = tr.getTDSet(i-skiplines);
					
					//id
					if (tokens[0].trim().equals(theTDs.getContent(1))) j = 0;
					else System.out.println(tokens[4].trim() + " " + tokens[0].trim() + " " + theTDs.getContent(1));
					
					//time_start
					if (tokens[1].trim().equals(theTDs.getContent(2).substring(0, 10) + "T" + theTDs.getContent(2).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[1].trim() + " " + theTDs.getContent(2));
					
					//mag_ratio
					if (tokens[2].trim().equals(theTDs.getContent(3))) j = 0;
					else if (tokens[2].trim().equals(theTDs.getContent(3) + "0")) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[2].trim() + " " + theTDs.getContent(3));
					
					//norm_angle
					if (tokens[3].trim().equals(theTDs.getContent(4))) j = 0;
					else if (tokens[3].trim().equals(theTDs.getContent(4) + "0")) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[3].trim() + " " + theTDs.getContent(4));
					
					//beta
					if (tokens[4].trim().equals(theTDs.getContent(5))) j = 0;
					else if (tokens[4].trim().equals(theTDs.getContent(5) + "0")) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[4].trim() + " " + theTDs.getContent(5));
					
					//mach_no
					if (tokens[5].trim().equals(theTDs.getContent(6))) j = 0;
					else if (tokens[5].trim().equals(theTDs.getContent(6) + "0")) j = 0;
					else if (theTDs.getContent(6).equals(tokens[5].trim() + ".0")) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[5].trim() + " " + theTDs.getContent(6));
					
					//data_avail
					if (tokens[6].trim().equals(theTDs.getContent(7))) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[6].trim() + " " + theTDs.getContent(7));
					
					//f_r_shock
					if (tokens[7].trim().equals(theTDs.getContent(8))) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[7].trim() + " " + theTDs.getContent(8));
					
					//comment
					if (tokens[8].trim().equals(theTDs.getContent(9))) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[8].trim() + " " + theTDs.getContent(9));
																				
				}
				// line counter
				i++;
		
			}
			
			//System.out.println("Testing");
		} catch (Exception e) {System.out.println(e.getMessage());}

	}

}
