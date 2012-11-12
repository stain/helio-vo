package validate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cds.savot.pull.*;
//import cds.savot.common.*;
import cds.savot.model.*;

public class HelioValStereoHiCme {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// directory containing the event list
		String deldir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\stereo_hi_cme\\";
		
		String valdir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\stereo_hi_cme\\";
		// input delivered file name
		String delname = deldir + "HI_CME_LIST.txt";
		
		// input VOTable name
		String valname = valdir + "stereo_hi_cme_full.xml";

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
					
					/**
					for (int j = 0; j < theTDs.getItemCount(); j++) {
						if (tokens[j].trim().length() >= 20) {
							if (tokens[j].trim().subSequence(10, 11).equals("T") && tokens[j].trim().subSequence(19, 20).equals("Z")) {
								if (tokens[j].trim().equals(theTDs.getContent(j)+"Z")) continue;
								else System.out.println(tokens[6].trim() + " " + tokens[j].trim() + " " + theTDs.getContent(j));
							}
							else {
								if (tokens[j].trim().equals(theTDs.getContent(j))) continue;
								else System.out.println(tokens[6].trim() + " " + tokens[j].trim() + " " + theTDs.getContent(j));
							}
						}
						else {
							if (tokens[j].trim().equals(theTDs.getContent(j))) continue;
							else System.out.println(tokens[6].trim() + " " + tokens[j].trim() + " " + theTDs.getContent(j));
						}
					**/
					
					// Time Enter FOV
					if (tokens[0].trim().equals(theTDs.getContent(0) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[0].trim() + " " + theTDs.getContent(0));
						
					// Instrument
					if (tokens[1].trim().equals(theTDs.getContent(1))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[1].trim() + " " + theTDs.getContent(1));
						
					// Time Exit FOV
					if (tokens[2].trim().equals("NaN")) j = 0;
					else if (tokens[2].trim().equals(theTDs.getContent(2) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[2].trim() + " " + theTDs.getContent(2));
						
					// Fades or Data Gap
					if (tokens[3].trim().equals("NaN")) j = 0;
					else if (tokens[3].trim().equals(theTDs.getContent(3))) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[3].trim() + " " + theTDs.getContent(3));
						
					// CME Type
					if (tokens[4].trim().equals(theTDs.getContent(4))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[4].trim() + " " + theTDs.getContent(4));
						
					// Brightness
					if (tokens[5].trim().equals(theTDs.getContent(5))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[5].trim() + " " + theTDs.getContent(5));
					
					// PA (deg)
					if (tokens[6].trim().equals("NaN")) j = 0;
					else if ((tokens[6].trim() + ".0").equals(theTDs.getContent(6))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[6].trim() + " " + theTDs.getContent(6));
						
					// Angular Width (deg)
					if (tokens[7].trim().equals("NaN")) j = 0;
					else if ((tokens[7].trim() + ".0").equals(theTDs.getContent(7))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[7].trim() + " " + theTDs.getContent(7));
					
					// Time onset
					if (tokens[8].trim().equals("NaN")) j = 0;
					else if (tokens[8].trim().equals(theTDs.getContent(8) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[8].trim() + " " + theTDs.getContent(8));
					
					// Not in event list
					if (tokens[9].trim().equals("NaN")) j = 0;
					else if (tokens[9].trim().equals(theTDs.getContent(9))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[9].trim() + " " + theTDs.getContent(9));
					
					// velocity
					if (tokens[10].trim().equals("NaN")) j = 0;
					else if ((tokens[10].trim() + ".0").equals(theTDs.getContent(10))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[10].trim() + " " + theTDs.getContent(10));

					// Longitude S-E Line (deg)
					if (tokens[11].trim().equals("NaN")) j = 0;
					else if ((tokens[11].trim() + ".0").equals(theTDs.getContent(11))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[11].trim() + " " + theTDs.getContent(11));

					// Longitude Error (deg)
					if (tokens[12].trim().equals("NaN")) j = 0;
					else if ((tokens[12].trim() + ".0").equals(theTDs.getContent(12))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[12].trim() + " " + theTDs.getContent(12));

					// Time 1AU
					if (tokens[13].trim().equals("NaN")) j = 0;
					else if (tokens[13].trim().equals(theTDs.getContent(13) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[13].trim() + " " + theTDs.getContent(13));
					
					// Comment
					if (tokens[14].trim().equals(theTDs.getContent(14))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[14].trim() + " " + theTDs.getContent(14));
					
				}
				
				//System.out.println(tokens[6].trim());
				i++;
			
			} 
			
			System.out.println("Testing");
		} catch (Exception e) {System.out.println(e.getMessage());}
	}

}
