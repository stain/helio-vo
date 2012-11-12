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

public class HelioValYohkohHxr {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// directory containing the event list
		String deldir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\yohkoh_hxr_flare\\";
		
		String valdir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\yohkoh_hxr_flare\\";
		
		// input delivered file name
		String delname = deldir + "yohkoh_hxr_flare.txt";
		
		// input VOTable file name
		String valname = valdir + "yohkoh_hxr_flare_full.xml";

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
					
					if (tokens[0].trim().equals(theTDs.getContent(1).substring(0, 10) + "T" + theTDs.getContent(1).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[0].trim() + " " + theTDs.getContent(1));
					
					if (tokens[1].trim().equals(theTDs.getContent(2) + "NaN")) j=0;
					else if (tokens[1].trim().equals(theTDs.getContent(2).substring(0, 10) + "T" + theTDs.getContent(2).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[1].trim() + " " + theTDs.getContent(2));
					
					if (tokens[2].trim().equals(theTDs.getContent(3).substring(0, 10) + "T" + theTDs.getContent(3).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[2].trim() + " " + theTDs.getContent(3));
					
					if (tokens[3].trim().equals(theTDs.getContent(4) + "NaN")) j = 0;
					else if (tokens[3].trim().equals(theTDs.getContent(4))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[3].trim() + " " + theTDs.getContent(4));
					
					if (tokens[4].trim().equals(theTDs.getContent(5) + "NaN")) j = 0;
					else if (theTDs.getContent(5).equals(tokens[4].trim() + ".0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[4].trim() + " " + theTDs.getContent(5));
					
					if (tokens[5].trim().equals(theTDs.getContent(6) + "NaN")) j = 0;
					else if (theTDs.getContent(6).equals(tokens[5].trim() + ".0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[5].trim() + " " + theTDs.getContent(6));
					
					if (tokens[6].trim().equals(theTDs.getContent(7) + "NaN")) j = 0;
					else if (tokens[6].trim().equals(theTDs.getContent(7))) j = 0;
					else if (tokens[6].trim().equals(theTDs.getContent(7) + "0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[6].trim() + " " + theTDs.getContent(7));
					
					if (tokens[7].trim().equals(theTDs.getContent(8) + "NaN")) j=0;
					else if (tokens[7].trim().equals(theTDs.getContent(8))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[7].trim() + " " + theTDs.getContent(8));
					
					if (tokens[8].trim().equals(theTDs.getContent(9) + "NaN")) j=0;
					else if (tokens[8].trim().equals(theTDs.getContent(9))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[8].trim() + " " + theTDs.getContent(9));
					
					if (tokens[9].trim().equals(theTDs.getContent(10) + "NaN")) j=0;
					else if (tokens[9].trim().equals(theTDs.getContent(10))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[9].trim() + " " + theTDs.getContent(10));
					
					if (tokens[10].trim().equals(theTDs.getContent(11) + "NaN")) j=0;
					else if (tokens[10].trim().equals(theTDs.getContent(11))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[10].trim() + " " + theTDs.getContent(11));
					
					if (tokens[11].trim().equals(theTDs.getContent(12) + "NaN")) j=0;
					else if (tokens[11].trim().equals(theTDs.getContent(12))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[11].trim() + " " + theTDs.getContent(12));
					
					if (tokens[12].trim().equals(theTDs.getContent(13) + "NaN")) j=0;
					else if (tokens[12].trim().equals(theTDs.getContent(13))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[12].trim() + " " + theTDs.getContent(13));
					
					if (tokens[13].trim().equals(theTDs.getContent(14) + "NaN")) j=0;
					else if (theTDs.getContent(14).equals(tokens[13].trim() + ".0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[13].trim() + " " + theTDs.getContent(14));
					
					if (tokens[14].trim().equals(theTDs.getContent(15) + "NaN")) j=0;
					else if (theTDs.getContent(15).equals(tokens[14].trim() + ".0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[14].trim() + " " + theTDs.getContent(15));
					
					if (tokens[15].trim().equals(theTDs.getContent(16) + "NaN")) j=0;
					else if (theTDs.getContent(16).equals(tokens[15].trim() + ".0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[15].trim() + " " + theTDs.getContent(16));
					
					if (tokens[16].trim().equals(theTDs.getContent(17) + "NaN")) j=0;
					else if (theTDs.getContent(17).equals(tokens[16].trim() + ".0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[16].trim() + " " + theTDs.getContent(17));
					
					if (tokens[17].trim().equals(theTDs.getContent(18) + "NaN")) j=0;
					else if (tokens[17].trim().equals(theTDs.getContent(18))) j = 0;
					else if (tokens[17].trim().equals(theTDs.getContent(18) + "0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[17].trim() + " " + theTDs.getContent(18));
					
					if (tokens[18].trim().equals(theTDs.getContent(19) + "NaN")) j=0;
					else if (tokens[18].trim().equals(theTDs.getContent(19))) j = 0;
					else if (tokens[18].trim().equals(theTDs.getContent(19) + "0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[18].trim() + " " + theTDs.getContent(19));
					
					if (tokens[19].trim().equals(theTDs.getContent(20) + "NaN")) j=0;
					else if (tokens[19].trim().equals(theTDs.getContent(20))) j = 0;
					else if (tokens[19].trim().equals(theTDs.getContent(20) + "0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[19].trim() + " " + theTDs.getContent(20));
					
					if (tokens[20].trim().equals(theTDs.getContent(21))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[20].trim() + " " + theTDs.getContent(21));
					
					if (Integer.parseInt(tokens[21].trim()) == Integer.parseInt(theTDs.getContent(22))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[21].trim() + " " + theTDs.getContent(22));
					
				}
				// line counter
				i++;
		
			}
			
			//System.out.println("Testing");
		} catch (Exception e) {System.out.println(e.getMessage());}
	}


}
