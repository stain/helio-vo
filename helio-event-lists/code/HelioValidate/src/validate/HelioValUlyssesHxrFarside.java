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


public class HelioValUlyssesHxrFarside {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		float cr_in;
		float cr_hec;
		
		// directory containing the event list
		String deldir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\ulysses_hxr_flare_farside\\";
		
		String valdir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\ulysses_hxr_flare_farside\\";
		// input delivered file name
		String delname = deldir + "ulysses_hxr_flare_farside.txt";
		
		// input VOTable file name
		String valname = valdir + "ulysses_hxr_flare_farside_full.xml";

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
					
				//skip lines at start of file 
				if (i>skiplines-1) {

					tokens = deline.split(delims);
					
					TDSet theTDs = tr.getTDSet(i-skiplines);
										
					if (tokens[0].trim().equals(theTDs.getContent(1) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[0].trim() + " " + theTDs.getContent(1));
					
					if (tokens[1].trim().equals(theTDs.getContent(2) + "0") || tokens[1].trim().equals(theTDs.getContent(2))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[1].trim() + " " + theTDs.getContent(2));
					
					cr_in = Float.parseFloat(tokens[2]);
					cr_hec = Float.parseFloat(theTDs.getContent(3));
					
					if (cr_in == cr_hec) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[2].trim() + " " + theTDs.getContent(3));					
					
					if (tokens[3].trim().equals(theTDs.getContent(4))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[3].trim() + " " + theTDs.getContent(4));
					
					if (tokens[4].trim().equals(theTDs.getContent(5))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[4].trim() + " " + theTDs.getContent(5));
					
				}
				// line counter
				i++;
		
			}
			
			//System.out.println("Testing");
		} catch (Exception e) {System.out.println(e.getMessage());}
}


}
