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

public class HelioValUlyssesHxr {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
			float cr_in;
			float cr_hec;
			
			// directory containing the event list
			String deldir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\ulysses_hxr_flare\\";
			
			String valdir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\ulysses_hxr_flare\\";
			// input delivered file name
			String delname = deldir + "ulysses_hxr_flare.txt";
			
			// input VOTable file name
			String valname = valdir + "ulysses_hxr_flare_full_gui.xml";

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
						
						
						if (tokens[0].substring(0,10).equals(theTDs.getContent(1).substring(0,10)) &&
								tokens[0].substring(11,19).equals(theTDs.getContent(1).substring(11,19))) j = 0;
						else System.out.println(tokens[0].trim() + " " + tokens[0].trim() + " " + theTDs.getContent(1));
						
						if (tokens[1].substring(0,10).equals(theTDs.getContent(2).substring(0,10)) &&
								tokens[1].substring(11,19).equals(theTDs.getContent(2).substring(11,19))) j = 0;
						else System.out.println(tokens[0].trim() + " " + tokens[1].trim() + " " + theTDs.getContent(2));
						
						if (tokens[2].substring(0,10).equals(theTDs.getContent(3).substring(0,10)) &&
								tokens[2].substring(11,19).equals(theTDs.getContent(3).substring(11,19))) j = 0;
						else System.out.println(tokens[0].trim() + " " + tokens[2].trim() + " " + theTDs.getContent(3));
						
						if (theTDs.getContent(4).equals(tokens[3].trim() + ".0") || tokens[3].trim().equals("NaN")) j = 0;
						else System.out.println(tokens[0].trim() + " " + tokens[3].trim() + " " + theTDs.getContent(4));
						
						if (theTDs.getContent(5).equals(tokens[4].trim() + ".0") || tokens[4].trim().equals("NaN")) j = 0;
						else System.out.println(tokens[0].trim() + " " + tokens[4].trim() + " " + theTDs.getContent(5));
						
						if (tokens[5].trim().equals(theTDs.getContent(6) + "0") || tokens[5].trim().equals(theTDs.getContent(6)) || tokens[5].trim().equals("NaN")) j = 0;
						else System.out.println(tokens[0].trim() + " " + tokens[5].trim() + " " + theTDs.getContent(6));
						
						if (tokens[6].trim().equals(theTDs.getContent(7))) j = 0;
						else System.out.println(tokens[0].trim() + " " + tokens[6].trim() + " " + theTDs.getContent(7));
						
						if (tokens[7].trim().equals(theTDs.getContent(8) + "0") || tokens[7].trim().equals(theTDs.getContent(8))) j = 0;
						else System.out.println(tokens[0].trim() + " " + tokens[7].trim() + " " + theTDs.getContent(8));
						
						if (tokens[8].trim().equals(theTDs.getContent(9))) j = 0;
						else System.out.println(tokens[0].trim() + " " + tokens[8].trim() + " " + theTDs.getContent(9));
						
						if (tokens[9].trim().equals(theTDs.getContent(10))) j = 0;
						else System.out.println(tokens[0].trim() + " " + tokens[9].trim() + " " + theTDs.getContent(10));
						
						if (tokens[10].trim().equals(theTDs.getContent(11))) j = 0;
						else System.out.println(tokens[0].trim() + " " + tokens[10].trim() + " " + theTDs.getContent(11));
						
						if (tokens[11].trim().equals(theTDs.getContent(12) + "0") || tokens[11].trim().equals(theTDs.getContent(12))
								|| tokens[11].trim().equals("NaN")) j = 0;
						else System.out.println(tokens[0].trim() + " " + tokens[11].trim() + " " + theTDs.getContent(12));
						
						cr_in = Float.parseFloat(tokens[12]);
						cr_hec = Float.parseFloat(theTDs.getContent(13));
						
						if (cr_in == cr_hec) j = 0;
						else System.out.println(tokens[0].trim() + " " + tokens[12].trim() + " " + theTDs.getContent(13));					

					}
					// line counter
					i++;
			
				}
				
				//System.out.println("Testing");
			} catch (Exception e) {System.out.println(e.getMessage());}
	}


}
