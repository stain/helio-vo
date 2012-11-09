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

public class HelioValCMEInnerHeliosphere {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// directory containing the event list
		String deldir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\cme_inner_heliosphere\\";
		
		String valdir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\cme_inner_heliosphere\\";
		
		// input delivered file name
		String delname = deldir + "cme_inner_heliosphere.txt";
		
		// input VOTable file name
		String valname = valdir + "cme_inner_heliosphere_full.xml";

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
					
					//cme_number
					if (tokens[0].trim().equals(theTDs.getContent(1))) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[0].trim() + " " + theTDs.getContent(1));
					
					//time_start
					if (tokens[1].trim().equals(theTDs.getContent(2).substring(0, 10) + "T" + theTDs.getContent(2).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[1].trim() + " " + theTDs.getContent(2));
					
					//v_cme
					if (tokens[2].trim().equals(theTDs.getContent(3))) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[2].trim() + " " + theTDs.getContent(3));
					
					//cme_type
					if (tokens[3].trim().equals(theTDs.getContent(4))) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[3].trim() + " " + theTDs.getContent(4));
					
					//xray_class
					if (tokens[4].trim().equals(theTDs.getContent(5))) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[4].trim() + " " + theTDs.getContent(5));
					
					//optical_class
					if (tokens[5].trim().equals(theTDs.getContent(6))) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[5].trim() + " " + theTDs.getContent(6));
					
					//lat_hg
					if (tokens[6].trim().equals(theTDs.getContent(7))) j = 0;
					else if (theTDs.getContent(7).equals(tokens[6].trim() + ".0")) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[6].trim() + " " + theTDs.getContent(7));
					
					//long_hg
					if (tokens[7].trim().equals(theTDs.getContent(8))) j = 0;
					else if (theTDs.getContent(8).equals(tokens[7].trim() + ".0")) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[7].trim() + " " + theTDs.getContent(8));
					
					//long_carr
					if (tokens[8].trim().equals(theTDs.getContent(9))) j = 0;
					else if (tokens[8].trim().equals(theTDs.getContent(9) + "0")) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[8].trim() + " " + theTDs.getContent(9));
					
					//time_ips_1au
					if (tokens[9].trim().equals(theTDs.getContent(10).substring(0, 10) + "T" + theTDs.getContent(10).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[9].trim() + " " + theTDs.getContent(10));
										
					//time_cme_1au
					if (tokens[10].trim().equals(theTDs.getContent(11) + "NaN")) j = 0;
					else if (tokens[10].trim().equals(theTDs.getContent(11).substring(0, 10) + "T" + theTDs.getContent(11).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[10].trim() + " " + theTDs.getContent(11));										
					
					//v_icme
					if (tokens[11].trim().equals(theTDs.getContent(12) + "NaN")) j = 0;
					else if (tokens[11].trim().equals(theTDs.getContent(12))) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[11].trim() + " " + theTDs.getContent(12));
					
					//time_transit_1au
					if (tokens[12].trim().equals(theTDs.getContent(13) + "NaN")) j = 0;
					else if (tokens[12].trim().equals(theTDs.getContent(13))) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[12].trim() + " " + theTDs.getContent(13));
										
					//alpha
					if (tokens[13].trim().equals(theTDs.getContent(14))) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[13].trim() + " " + theTDs.getContent(14));
					
					//v_1au_predict
					if (tokens[14].trim().equals(theTDs.getContent(15))) j = 0;
					else if (theTDs.getContent(15).equals(tokens[14].trim() + ".0")) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[14].trim() + " " + theTDs.getContent(15));
										
					//time_transit_1au_predict
					if (tokens[15].trim().equals(theTDs.getContent(16))) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[15].trim() + " " + theTDs.getContent(16));
										
					//flag_cme_interaction
					if (tokens[16].trim().equals(theTDs.getContent(17))) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[16].trim() + " " + theTDs.getContent(17));
					
										
				}
				// line counter
				i++;
		
			}
			
		} catch (Exception e) {System.out.println(e.getMessage());}
		


	}

}
