package validate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cds.savot.pull.*;
//import cds.savot.common.*;
import cds.savot.model.*;

public class HelioValHIEvent {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// directory containing the event list
		String deldir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\stereo_hi_event\\";
		
		String valdir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\stereo_hi_event\\";
		// input delivered file name
		String delname = deldir + "HI_Event_List.txt";
		
		// input VOTable name
		String valname = valdir + "stereo_hi_event_Full.xml";

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
			String speed;
			String sunc;
			String longee;
			String lunc;
			
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
					
						if (tokens[0].trim().equals(theTDs.getContent(1))) j = 0;
						else System.out.println(tokens[6].trim() + " " + tokens[0].trim() + " " + theTDs.getContent(1));
						
						if (tokens[1].trim().equals(theTDs.getContent(2))) j = 0;
						else System.out.println(tokens[6].trim() + " " + tokens[1].trim() + " " + theTDs.getContent(2));
						
						// speed
						speed = tokens[2].trim() + ".0";
						if (speed.equals(theTDs.getContent(3))) j = 0;
						else System.out.println(tokens[6].trim() + " " + tokens[2].trim() + " " + theTDs.getContent(3));
						
						// speed uncertainty
						sunc = tokens[3].trim();
						if (sunc.length() == 1) sunc = sunc + ".0";
						else if (sunc.substring(sunc.length()-2, sunc.length()-1).equals(".")) j=0;
						else sunc = sunc + ".0";
						
						if (sunc.equals(theTDs.getContent(4))) j = 0;
						else System.out.println(tokens[6].trim() + " " + sunc + " " + theTDs.getContent(4));
						
						// longitude
						longee = tokens[4].trim();
						if (longee.length() == 1) longee = longee + ".0";
						else if (longee.substring(longee.length()-2, longee.length()-1).equals(".")) j=0;
						else longee = longee + ".0";
						
						if (longee.equals(theTDs.getContent(5))) j = 0;
						else System.out.println(tokens[6].trim() + " " + longee + " " + theTDs.getContent(5));
						
						// longitude uncertainty
						lunc = tokens[5].trim();
						if (lunc.length() == 1) lunc = lunc + ".0";
						else if (lunc.substring(lunc.length()-2, lunc.length()-1).equals(".")) j=0;
						else lunc = lunc + ".0";
						
						if (lunc.equals(theTDs.getContent(6))) j = 0;
						else System.out.println(tokens[6].trim() + " " + lunc + " " + theTDs.getContent(6));
						
						if (tokens[6].trim().equals(theTDs.getContent(0)+"Z")) j = 0;
						else System.out.println(tokens[6].trim() + " " + tokens[6].trim() + " " + theTDs.getContent(0));
						
						if (tokens[7].trim().equals(theTDs.getContent(7)+"Z")) j = 0;
						else System.out.println(tokens[6].trim() + " " + tokens[7].trim() + " " + theTDs.getContent(7));
						
						//System.out.println(tokens[6].trim());
						i++;
				
				}
			} 
			
			System.out.println("Testing");
		} catch (Exception e) {System.out.println(e.getMessage());}
	}
}
