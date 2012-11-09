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

public class HelioValSolarSepEvent {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// directory containing the event list
		String deldir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\solar_sep_event\\";
		
		String valdir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\solar_sep_event\\";
		// input delivered file name
		String delname = deldir + "solar_sep_event.txt";
		
		// input VOTable file name
		String valname = valdir + "solar_sep_event_full.xml";

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
					
					//time_start
					if (tokens[0].trim().equals(theTDs.getContent(1).substring(0, 10) + "T" + theTDs.getContent(1).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[0].trim() + " " + theTDs.getContent(1));
					
					//flag_slow_rise
					if (tokens[1].trim().equals(theTDs.getContent(2))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[1].trim() + " " + theTDs.getContent(2));
					
					//energy_max
					if (tokens[2].trim().equals(theTDs.getContent(3) + "NaN")) j=0;
					else if (theTDs.getContent(3).equals(tokens[2].trim() + ".0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[2].trim() + " " + theTDs.getContent(3));
					
					//proton_intensity_max
					if (tokens[3].trim().equals(theTDs.getContent(4) + "NaN")) j=0;
					else if (theTDs.getContent(4).equals(tokens[3].trim())) j=0;
					else if (theTDs.getContent(4).equals(tokens[3].trim() + "0")) j = 0;
					else if (theTDs.getContent(4).equals(tokens[3].trim() + ".0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[3].trim() + " " + theTDs.getContent(4));
					
					//time_start_typeIII
					if (tokens[4].trim().equals(theTDs.getContent(5) + "NaN")) j = 0;
					else if (tokens[4].trim().equals(theTDs.getContent(5).substring(0, 10) + "T" + theTDs.getContent(5).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[4].trim() + " " + theTDs.getContent(5));
					
					//time_end_typeIII
					if (tokens[5].trim().equals(theTDs.getContent(6) + "NaN")) j = 0;
					else if (tokens[5].trim().equals(theTDs.getContent(6).substring(0, 10) + "T" + theTDs.getContent(6).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[5].trim() + " " + theTDs.getContent(6));
					
					//metric_typeII
					if (tokens[6].trim().equals(theTDs.getContent(7))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[6].trim() + " " + theTDs.getContent(7));
					
					//time_start_flare
					if (tokens[7].trim().equals(theTDs.getContent(8) + "NaN")) j = 0;
					else if (tokens[7].trim().equals(theTDs.getContent(8).substring(0, 10) + "T" + theTDs.getContent(8).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[7].trim() + " " + theTDs.getContent(8));
					
					//dt_rise
					if (tokens[8].trim().equals(theTDs.getContent(9) + "NaN")) j = 0;
					else if (theTDs.getContent(9).equals(tokens[8].trim() + ".0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[8].trim() + " " + theTDs.getContent(9));
					
					//lat_hg
					if (tokens[9].trim().equals(theTDs.getContent(10) + "NaN")) j=0;
					else if (theTDs.getContent(10).equals(tokens[9].trim() + ".0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[9].trim() + " " + theTDs.getContent(10));
					
					//long_hg
					if (tokens[10].trim().equals(theTDs.getContent(11) + "NaN")) j=0;
					else if (theTDs.getContent(11).equals(tokens[10].trim())) j = 0;
					else if (theTDs.getContent(11).equals(tokens[10].trim() + ".0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[10].trim() + " " + theTDs.getContent(11));
					
					//long_carr
					if (tokens[11].trim().equals(theTDs.getContent(12) + "NaN")) j=0;
					else if (tokens[11].trim().equals(theTDs.getContent(12))) j = 0;
					else if (tokens[11].trim().equals(theTDs.getContent(12) + "0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[11].trim() + " " + theTDs.getContent(12));
					
					//lat_ns
					if (tokens[12].trim().equals(theTDs.getContent(13))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[12].trim() + " " + theTDs.getContent(13));
					
					//xray_class
					if (tokens[13].trim().equals(theTDs.getContent(14))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[13].trim() + " " + theTDs.getContent(14));
					
					//time_start_cme
					if (tokens[14].trim().equals(theTDs.getContent(15) + "NaN")) j = 0;
					else if (tokens[14].trim().equals(theTDs.getContent(15).substring(0, 10) + "T" + theTDs.getContent(15).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[14].trim() + " " + theTDs.getContent(15));
					
					//pa_width
					if (tokens[15].trim().equals(theTDs.getContent(16) + "NaN")) j = 0;
					else if (theTDs.getContent(16).equals(tokens[15].trim())) j = 0;
					else if (theTDs.getContent(16).equals(tokens[15].trim() + ".0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[15].trim() + " " + theTDs.getContent(16));
					
					//v_cme
					if (tokens[16].trim().equals(theTDs.getContent(17) + "NaN")) j = 0;
					else if (theTDs.getContent(17).equals(tokens[16].trim())) j = 0;
					else if (theTDs.getContent(17).equals(tokens[16].trim() + ".0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[16].trim() + " " + theTDs.getContent(17));
					
					//v_shock
					if (tokens[17].trim().equals(theTDs.getContent(18) + "NaN")) j=0;
					else if (theTDs.getContent(18).equals(tokens[17].trim())) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[17].trim() + " " + theTDs.getContent(18));
					
					//flag_pass_shock
					if (tokens[18].trim().equals(theTDs.getContent(19))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[18].trim() + " " + theTDs.getContent(19));
					
					//group_sep
					if (tokens[19].trim().equals(theTDs.getContent(20) + "NaN")) j=0;
					else if (tokens[19].trim().equals(theTDs.getContent(20))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[19].trim() + " " + theTDs.getContent(20));
					
				}
				// line counter
				i++;
		
			}
			
			//System.out.println("Testing");
		} catch (Exception e) {System.out.println(e.getMessage());}
	}		

}
