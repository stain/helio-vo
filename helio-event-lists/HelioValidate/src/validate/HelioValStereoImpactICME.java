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

public class HelioValStereoImpactICME {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// directory containing the event list
		String deldir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\stereo_impactplastic_icme\\";
		
		String valdir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\stereo_impactplastic_icme\\";
		
		// input delivered file name
		String delname = deldir + "stereo_impactplastic_icme.txt";
		
		// input VOTable file name
		String valname = valdir + "stereo_impactplastic_icme_full.xml";

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
					
					//
					if (tokens[0].trim().equals(theTDs.getContent(1))) j = 0;
					else System.out.println(tokens[3].trim() + " " + tokens[0].trim() + " " + theTDs.getContent(1));
					
					if (tokens[1].trim().equals(theTDs.getContent(2))) j = 0;
					else System.out.println(tokens[3].trim() + " " + tokens[1].trim() + " " + theTDs.getContent(2));
					
					if (tokens[2].trim().equals(theTDs.getContent(3))) j = 0;
					else System.out.println(tokens[3].trim() + " " + tokens[2].trim() + " " + theTDs.getContent(3));
					
					if (tokens[3].trim().equals(theTDs.getContent(4).substring(0, 10) + "T" + theTDs.getContent(4).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[3].trim() + " " + tokens[3].trim() + " " + theTDs.getContent(4));
					
					if (tokens[4].trim().equals(theTDs.getContent(5).substring(0, 10) + "T" + theTDs.getContent(5).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[3].trim() + " " + tokens[4].trim() + " " + theTDs.getContent(5));
					
					if (tokens[5].trim().equals(theTDs.getContent(6).substring(0, 10) + "T" + theTDs.getContent(6).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[3].trim() + " " + tokens[5].trim() + " " + theTDs.getContent(6));
					
					if (tokens[6].trim().equals(theTDs.getContent(7) + "NaN")) j = 0;
					else if (tokens[6].trim().equals(theTDs.getContent(7))) j = 0;
					else if (theTDs.getContent(7).equals(tokens[6].trim() + ".0")) j = 0;
					else System.out.println(tokens[3].trim() + " " + tokens[6].trim() + " " + theTDs.getContent(7));
					
					if (tokens[7].trim().equals(theTDs.getContent(8) + "NaN")) j = 0;
					else if (tokens[7].trim().equals(theTDs.getContent(8))) j = 0;
					else if (theTDs.getContent(8).equals(tokens[7].trim() + ".0")) j = 0;
					else System.out.println(tokens[3].trim() + " " + tokens[7].trim() + " " + theTDs.getContent(8));
					
					if (tokens[8].trim().equals(theTDs.getContent(9) + "NaN")) j = 0;
					else if (tokens[8].trim().equals(theTDs.getContent(9))) j = 0;
					else if (theTDs.getContent(9).equals(tokens[8].trim() + ".0")) j = 0;
					else System.out.println(tokens[3].trim() + " " + tokens[8].trim() + " " + theTDs.getContent(9));
					
					if (tokens[9].trim().equals(theTDs.getContent(10) + "NaN")) j = 0;
					else if (tokens[9].trim().equals(theTDs.getContent(10))) j = 0;
					else if (theTDs.getContent(10).equals(tokens[9].trim() + ".0")) j = 0;
					else System.out.println(tokens[3].trim() + " " + tokens[9].trim() + " " + theTDs.getContent(10));
					
					if (tokens[10].trim().equals(theTDs.getContent(11) + "NaN")) j = 0;
					else if (theTDs.getContent(11).equals(tokens[10].trim() + ".0")) j = 0;
					else System.out.println(tokens[3].trim() + " " + tokens[10].trim() + " " + theTDs.getContent(11));
					
					if (tokens[11].trim().equals(theTDs.getContent(12) + "NaN")) j = 0;
					else if (theTDs.getContent(12).equals(tokens[11].trim() + ".0")) j = 0;
					else System.out.println(tokens[3].trim() + " " + tokens[11].trim() + " " + theTDs.getContent(12));
					
					if (tokens[12].trim().equals(theTDs.getContent(13) + "NaN")) j = 0;
					else if (theTDs.getContent(13).equals(tokens[12].trim() + ".0")) j = 0;
					else System.out.println(tokens[3].trim() + " " + tokens[12].trim() + " " + theTDs.getContent(13));
					
					if (tokens[13].trim().equals(theTDs.getContent(14) + "NaN")) j = 0;
					else if (theTDs.getContent(14).equals(tokens[13].trim())) j = 0;
					else System.out.println(tokens[3].trim() + " " + tokens[13].trim() + " " + theTDs.getContent(14));
					
					if (tokens[14].trim().equals(null)) j = 0;
					else if (tokens[14].trim().equals(theTDs.getContent(15))) j = 0;
					else System.out.println(tokens[3].trim() + " " + tokens[14].trim() + " " + theTDs.getContent(15));
					
				}
				// line counter
				i++;
		
			}
			
			//System.out.println("Testing");
		} catch (Exception e) {System.out.println(e.getMessage());}
	}

}
