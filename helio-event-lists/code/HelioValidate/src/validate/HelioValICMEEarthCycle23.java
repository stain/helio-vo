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

public class HelioValICMEEarthCycle23 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// directory containing the event list
		String deldir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\icme_earth_cycle23\\";
		
		String valdir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\icme_earth_cycle23\\";
		
		// input delivered file name
		String delname = deldir + "icme_earth_cycle23.txt";
		
		// input VOTable file name
		String valname = valdir + "icme_earth_cycle23_full.xml";

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
					
					//time_start
					if (tokens[0].trim().equals(theTDs.getContent(1).substring(0, 10) + "T" + theTDs.getContent(1).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[0].trim() + " " + theTDs.getContent(1));
					
					//time_start_icme
					if (tokens[1].trim().equals(theTDs.getContent(2).substring(0, 10) + "T" + theTDs.getContent(2).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[1].trim() + " " + theTDs.getContent(2));
					
					//time_end_icme
					if (tokens[2].trim().equals(theTDs.getContent(3).substring(0, 10) + "T" + theTDs.getContent(3).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[2].trim() + " " + theTDs.getContent(3));
					
					//flag_a_w
					if (tokens[3].trim().equals(theTDs.getContent(4))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[3].trim() + " " + theTDs.getContent(4));
					
					//time_offset_start_sig
					if (tokens[4].trim().equals(theTDs.getContent(5) + "NaN")) j = 0;
					else if (theTDs.getContent(5).equals(tokens[4].trim())) j = 0;
					else if (tokens[4].trim().equals("+" + theTDs.getContent(5))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[4].trim() + " " + theTDs.getContent(5));
					
					//flag_toff_start_sig
					if (tokens[5].trim().equals(theTDs.getContent(6))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[5].trim() + " " + theTDs.getContent(6));
					
					//time_offset_end_sig
					if (tokens[6].trim().equals(theTDs.getContent(7) + "NaN")) j = 0;
					else if (tokens[6].trim().equals(theTDs.getContent(7))) j = 0;
					else if (tokens[6].trim().equals("+" + theTDs.getContent(7))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[6].trim() + " " + theTDs.getContent(7));
					
					//flag_toff_end_sig
					if (tokens[7].trim().equals(theTDs.getContent(8))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[7].trim() + " " + theTDs.getContent(8));
					
					//time_offset_start_mc
					if (tokens[8].trim().equals(theTDs.getContent(9) + "NaN")) j=0;
					else if (tokens[8].trim().equals(theTDs.getContent(9))) j = 0;
					else if (tokens[8].trim().equals("+" + theTDs.getContent(9))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[8].trim() + " " + theTDs.getContent(9));
					
					//flag_toff_start_mc
					if (tokens[9].trim().equals(theTDs.getContent(10))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[9].trim() + " " + theTDs.getContent(10));
					
					//time_offset_end_mc
					if (tokens[10].trim().equals(theTDs.getContent(11) + "NaN")) j=0;
					else if (tokens[10].trim().equals(theTDs.getContent(11))) j = 0;
					else if (tokens[10].trim().equals("+" + theTDs.getContent(11))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[10].trim() + " " + theTDs.getContent(11));
					
					//flag_toff_end_mc
					if (tokens[11].trim().equals(theTDs.getContent(12))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[11].trim() + " " + theTDs.getContent(12));
					
					//bidir_flow_e
					if (tokens[12].trim().equals(theTDs.getContent(13))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[12].trim() + " " + theTDs.getContent(13));
					
					//bidir_flow_ion
					if (tokens[13].trim().equals(theTDs.getContent(14))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[13].trim() + " " + theTDs.getContent(14));
					
					//qual_time_est
					if (tokens[14].trim().equals(theTDs.getContent(15))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[14].trim() + " " + theTDs.getContent(15));
					
					//flag_weak_event
					if (tokens[15].trim().equals(theTDs.getContent(16))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[15].trim() + " " + theTDs.getContent(16));
					
					//v_incr_sw
					if (tokens[16].trim().equals(theTDs.getContent(17) + "NaN")) j=0;
					else if (theTDs.getContent(17).equals(tokens[16].trim())) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[16].trim() + " " + theTDs.getContent(17));
					
					//flag_upstream_shock
					if (tokens[17].trim().equals(theTDs.getContent(18))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[17].trim() + " " + theTDs.getContent(18));
					
					//v_mean_sw
					if (tokens[18].trim().equals(theTDs.getContent(19) + "NaN")) j=0;
					else if (tokens[18].trim().equals(theTDs.getContent(19))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[18].trim() + " " + theTDs.getContent(19));
					
					//v_max_sw
					if (tokens[19].trim().equals(theTDs.getContent(20) + "NaN")) j=0;
					else if (tokens[19].trim().equals(theTDs.getContent(20))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[19].trim() + " " + theTDs.getContent(20));
					
					//b_mean
					if (tokens[20].trim().equals(theTDs.getContent(21) + "NaN")) j=0;
					else if (tokens[20].trim().equals(theTDs.getContent(21))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[20].trim() + " " + theTDs.getContent(21));
					
					//flag_mag_cloud
					if (tokens[21].trim().equals(theTDs.getContent(22))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[21].trim() + " " + theTDs.getContent(22));
					
					//flag_mc_list
					if (tokens[22].trim().equals(theTDs.getContent(23))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[22].trim() + " " + theTDs.getContent(23));
					
					//dst_min
					if (tokens[23].trim().equals(theTDs.getContent(24) + "NaN")) j=0;
					else if (tokens[23].trim().equals(theTDs.getContent(24))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[23].trim() + " " + theTDs.getContent(24));
					
					//flag_dst
					if (tokens[24].trim().equals(theTDs.getContent(25))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[24].trim() + " " + theTDs.getContent(25));
					
					//v_1au
					if (tokens[25].trim().equals(theTDs.getContent(26) + "NaN")) j=0;
					else if (tokens[25].trim().equals(theTDs.getContent(26))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[25].trim() + " " + theTDs.getContent(26));
					
					//time_lasco
					if (tokens[26].trim().equals(theTDs.getContent(27) + "NaN")) j=0;
					else if (tokens[26].trim().equals(theTDs.getContent(27).substring(0, 10) + "T" + theTDs.getContent(27).substring(11, 19) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[26].trim() + " " + theTDs.getContent(27));
					
					//flag_halo
					if (tokens[27].trim().equals(theTDs.getContent(28))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[27].trim() + " " + theTDs.getContent(28));
										
					
				}
				// line counter
				i++;
		
			}
			
			//System.out.println("Testing");
		} catch (Exception e) {System.out.println(e.getMessage());}
	}

}
