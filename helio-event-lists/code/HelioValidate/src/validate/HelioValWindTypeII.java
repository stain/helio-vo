package validate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import cds.savot.model.SavotResource;
import cds.savot.model.SavotVOTable;
import cds.savot.model.TDSet;
import cds.savot.model.TRSet;
import cds.savot.pull.SavotPullEngine;
import cds.savot.pull.SavotPullParser;

public class HelioValWindTypeII {

	public static void main (String[] args)
	{
		
		String st_y;
		String st_m;
		String st_d;
		String st_h;
		String st_min;

		String end_y;
		String end_m;
		String end_d;
		String end_h;
		String end_min;

		String st_freq;
		String end_freq;
		String loc;
		String noaa;
		String imp;
		
		String cpa;
		String width;
		String speed;
		
		String comments;
		
		int y;
		int m;
		int d;
		int h;
		int min;
		int s;
		
		
		// directory containing the event list
		String deldir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\wind_waves_type_ii_burst\\";
		
		String valdir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\wind_waves_type_ii_burst\\";
		// input delivered file name
		String delname = deldir + "WIND_WAVES_TYPE_II_BURST.txt";
		
		// input VOTable file name
		String valname = valdir + "wind_waves_type_ii_burst_full.xml";

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
					
					if (tokens[0].trim().equals("1998-03-29T03:40:00Z"))
					System.out.println(tokens[0].trim() + " " + tokens[11].trim() + " " + theTDs.getContent(11));
					
					if (tokens[0].trim().equals(theTDs.getContent(0) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[0].trim() + " " + theTDs.getContent(0));
					
					if (tokens[1].trim().equals(theTDs.getContent(1) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[1].trim() + " " + theTDs.getContent(1));
					
					if (tokens[2].trim().equals(theTDs.getContent(2)) || tokens[2].trim().equals("NaN")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[2].trim() + " " + theTDs.getContent(2));
					
					if (tokens[3].trim().equals(theTDs.getContent(3)) || tokens[3].trim().equals("NaN")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[3].trim() + " " + theTDs.getContent(3));
					
					if (tokens[4].trim().equals(theTDs.getContent(4))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[4].trim() + " " + theTDs.getContent(4));
					
					if (tokens[5].trim().equals(theTDs.getContent(5)) || tokens[5].trim().equals("NaN")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[5].trim() + " " + theTDs.getContent(5));
					
					if (tokens[6].trim().equals(theTDs.getContent(6)) || tokens[6].trim().equals("NaN")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[6].trim() + " " + theTDs.getContent(6));
					
					if (tokens[7].trim().equals(theTDs.getContent(7) + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[7].trim() + " " + theTDs.getContent(7));
					
					if ((tokens[8].trim() + ".0").equals(theTDs.getContent(8)) ||
							tokens[8].trim().equals("NaN") || tokens[8].trim().equals("Halo")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[8].trim() + " " + theTDs.getContent(8));
					
					if ((tokens[9].trim() + ".0").equals(theTDs.getContent(9)) || tokens[9].trim().equals("NaN")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[9].trim() + " " + theTDs.getContent(9));
					
					if ((tokens[10].trim() + ".0").equals(theTDs.getContent(10)) || tokens[10].trim().equals("NaN")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[10].trim() + " " + theTDs.getContent(10));
					
					if (tokens[11].trim().equals(theTDs.getContent(11))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[11].trim() + " " + theTDs.getContent(11));
					
					//if (tokens[11].trim().equals(theTDs.getContent(11))
					//		|| tokens[8].trim().equals("Halo")) j = 0;
					//else System.out.println(tokens[0].trim() + " " + tokens[11].trim() + " " + theTDs.getContent(11));
					
					//if (tokens[11].trim().equals(theTDs.getContent(11)) || tokens[5].trim().equals("NaN")
					//		|| tokens[8].trim().equals("Halo")) j = 0;
					//else System.out.println(tokens[0].trim() + " " + tokens[11].trim() + " " + theTDs.getContent(11));
					

				}
				// line counter
				i++;
		
			}
			
			//System.out.println("Testing");
		} catch (Exception e) {System.out.println(e.getMessage());}
}
	
}
