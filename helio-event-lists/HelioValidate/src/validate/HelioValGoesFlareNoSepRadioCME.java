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

public class HelioValGoesFlareNoSepRadioCME {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// directory containing the event list
		String deldir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\goes_flare_no_sep_radio_cme\\";
		
		String valdir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\goes_flare_no_sep_radio_cme\\";
		// input delivered file name
		String delname = deldir + "goes_flare_no_sep_radio_cme.txt";
		
		// input VOTable file name
		String valname = valdir + "goes_flare_no_sep_radio_cme_full.xml";

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
					
					//time_peak
					if (tokens[1].trim().equals(theTDs.getContent(2).substring(0, 10) + "T" + theTDs.getContent(2).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[1].trim() + " " + theTDs.getContent(2));
					
					//cutoff_hf
					if (tokens[2].trim().equals(theTDs.getContent(3))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[2].trim() + " " + theTDs.getContent(3));
					
					//time_spec_peak
					if (tokens[3].trim().equals(theTDs.getContent(4).substring(0, 10) + "T" + theTDs.getContent(4).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[3].trim() + " " + theTDs.getContent(4));
					
					//frequ_spec_peak
					if (tokens[4].trim().equals(theTDs.getContent(5))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[4].trim() + " " + theTDs.getContent(5));
					
					//flux_spec_peak
					if (tokens[5].trim().equals(theTDs.getContent(6) + "NaN")) j=0;
					else if (theTDs.getContent(6).equals(tokens[5].trim() + ".0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[5].trim() + " " + theTDs.getContent(6));
					
					//cutoff_lf_hi
					if (tokens[6].trim().equals(theTDs.getContent(7) + "NaN")) j=0;
					else if (theTDs.getContent(7).equals(tokens[6].trim())) j = 0;
					else if (theTDs.getContent(7).equals(tokens[6].trim() + ".0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[6].trim() + " " + theTDs.getContent(7));
					
					//cutoff_lf_lo
					if (tokens[7].trim().equals(theTDs.getContent(8) + "NaN")) j=0;
					else if (theTDs.getContent(8).equals(tokens[7].trim())) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[7].trim() + " " + theTDs.getContent(8));
					
					//alpha_lf
					if (tokens[8].trim().equals(theTDs.getContent(9) + "NaN")) j = 0;
					else if (theTDs.getContent(9).equals(tokens[8].trim())) j = 0;
					else if (theTDs.getContent(9).equals(tokens[8].trim() + ".0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[8].trim() + " " + theTDs.getContent(9));
					
					//deci_metre_waves
					if (tokens[9].trim().equals(theTDs.getContent(10))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[9].trim() + " " + theTDs.getContent(10));
					
					//time_start_dh_typeIII
					if (tokens[10].trim().equals(theTDs.getContent(11) + "NaN")) j = 0;
					else if (tokens[10].trim().equals(theTDs.getContent(11).substring(0, 10) + "T" + theTDs.getContent(11).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[10].trim() + " " + theTDs.getContent(11));
					
					//time_end_dh_typeIII
					if (tokens[11].trim().equals(theTDs.getContent(12) + "NaN")) j = 0;
					else if (tokens[11].trim().equals(theTDs.getContent(12).substring(0, 10) + "T" + theTDs.getContent(12).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[11].trim() + " " + theTDs.getContent(12));
					
					//n_bursts
					if (tokens[12].trim().equals(theTDs.getContent(13) + "NaN")) j = 0;
					else if (tokens[12].trim().equals(theTDs.getContent(13))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[12].trim() + " " + theTDs.getContent(13));
					
					//comment_dh_typeIII
					if (tokens[13].trim().equals(theTDs.getContent(14))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[13].trim() + " " + theTDs.getContent(14));
					
					//flag_dimming
					if (tokens[14].trim().equals(theTDs.getContent(15))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[14].trim() + " " + theTDs.getContent(15));
					
					//time_start_cme
					if (tokens[15].trim().equals(theTDs.getContent(16) + "NaN")) j = 0;
					else if (tokens[15].trim().equals(theTDs.getContent(16).substring(0, 10) + "T" + theTDs.getContent(16).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[15].trim() + " " + theTDs.getContent(16));
					
					//time_cme_solar_radius
					if (tokens[16].trim().equals(theTDs.getContent(17) + "NaN")) j = 0;
					else if (tokens[16].trim().equals(theTDs.getContent(17).substring(0, 10) + "T" + theTDs.getContent(17).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[16].trim() + " " + theTDs.getContent(17));
					
					//cme_speed
					if (tokens[17].trim().equals(theTDs.getContent(18) + "NaN")) j=0;
					else if (theTDs.getContent(18).equals(tokens[17].trim() + ".0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[17].trim() + " " + theTDs.getContent(18));
					
					//pa_width
					if (tokens[18].trim().equals(theTDs.getContent(19) + "NaN")) j=0;
					else if (theTDs.getContent(19).equals(tokens[18].trim() + ".0")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[18].trim() + " " + theTDs.getContent(19));
					
					//comment_cme
					if (tokens[19].trim().equals(theTDs.getContent(20))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[19].trim() + " " + theTDs.getContent(20));
					
				}
				// line counter
				i++;
		
			}
			
			//System.out.println("Testing");
		} catch (Exception e) {System.out.println(e.getMessage());}
	}		

}
