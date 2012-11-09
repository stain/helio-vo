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

public class HelioValWindTypeIISohoCme {
	
	public static void main (String[] args)
	{

	String st_time;
	String st_m;
	String st_d;
	String st_h;
	String st_min;

	String end_time;
	String end_m;
	String end_d;
	String end_h;
	String end_min;

	String st_freq;
	String end_freq;
	
	String lat_hg;
	String long_hg;
	String loc;
	String noaa;
	String imp;
	
	String cme_time;
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
	String deldir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\wind_typeii_soho_cme\\";
	
	String valdir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\wind_typeii_soho_cme\\";
	// input delivered file name
	String delname = deldir + "WIND_WAVES_TYPE_II_BURST.txt";
	
	// input VOTable file name
	String valname = valdir + "wind_typeii_soho_cme_full.xml";

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

	StringBuilder builder = new StringBuilder();
	
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
				
				//if (tokens[0].trim().equals("1998-03-29T03:40:00Z"))
				//System.out.println(tokens[0].trim() + " " + tokens[11].trim() + " " + theTDs.getContent(11));
				
				builder.append(theTDs.getContent(1).substring(0, 10));
				builder.append('T');
				builder.append(theTDs.getContent(1).substring(11, 19));
				st_time = builder.toString();
				builder.delete(0, builder.length());
				if (tokens[0].trim().equals(st_time + "Z")) j = 0;
				else System.out.println(tokens[0].trim() + " " + tokens[0].trim() + " " + st_time);
				
				builder.append(theTDs.getContent(2).substring(0, 10));
				builder.append('T');
				builder.append(theTDs.getContent(2).substring(11, 19));
				end_time = builder.toString();
				builder.delete(0, builder.length());
				if (tokens[1].trim().equals(end_time + "Z")) j = 0;
				else System.out.println(tokens[0].trim() + " " + tokens[1].trim() + " " + end_time);
				
				if (tokens[2].trim().equals(theTDs.getContent(3)) || tokens[2].trim().equals("NaN")) j = 0;
				else System.out.println(tokens[0].trim() + " " + tokens[2].trim() + " " + theTDs.getContent(3));
				
				if (tokens[3].trim().equals(theTDs.getContent(4)) || tokens[3].trim().equals("NaN")) j = 0;
				else System.out.println(tokens[0].trim() + " " + tokens[3].trim() + " " + theTDs.getContent(4));
				
				// Latitude - remove 0 from integers <10 - handle + and - separately
				lat_hg = tokens[4].trim();
				if (lat_hg.substring(0,1).equals("-") && lat_hg.substring(1,2).equals("0")) {
					builder.append(lat_hg.substring(0, 1));
					builder.append(lat_hg.substring(2,3));
					lat_hg = builder.toString();
					builder.delete(0, builder.length());
				} else if (lat_hg.substring(0,1).equals("0")) {
					lat_hg = lat_hg.substring(1,2);
				}
				if (theTDs.getContent(5).equals(lat_hg + ".0") || tokens[4].trim().equals("NaN")) j = 0;
				else System.out.println(tokens[0].trim() + " " + lat_hg + " " + theTDs.getContent(5));
				
				// Longitude - remove 0 from integers <10 - handle + and - separately
				long_hg = tokens[5].trim();
				if (long_hg.substring(0,1).equals("-") && long_hg.substring(1,2).equals("0")) {
					builder.append(long_hg.substring(0, 1));
					builder.append(long_hg.substring(2,3));
					long_hg = builder.toString();
					builder.delete(0, builder.length());
				} else if (long_hg.substring(0,1).equals("0")) {
					long_hg = long_hg.substring(1,2);
				}
				if (theTDs.getContent(6).equals(long_hg + ".0") || tokens[5].trim().equals("NaN")) j = 0;
				else System.out.println(tokens[0].trim() + " " + long_hg + " " + theTDs.getContent(6));
				
				if (theTDs.getContent(7)!=null) j = 0;
				else System.out.println(tokens[0].trim() + " " + tokens[6].trim() + " " + theTDs.getContent(7));
				
				if (tokens[7].trim().equals(theTDs.getContent(8))) j = 0;
				else System.out.println(tokens[0].trim() + " " + tokens[7].trim() + " " + theTDs.getContent(8));
				
				if (tokens[8].trim().equals("NaN")) {
					
					if (theTDs.getContent(10)!=null) {
						
						if (tokens[9].trim().equals(theTDs.getContent(10))) j = 0;
						else System.out.println(tokens[0].trim() + " " + tokens[9].trim() + " " + theTDs.getContent(10));
						
					}
					
				} else if (tokens[8].trim().equals(theTDs.getContent(9))) j = 0;
				else System.out.println(tokens[0].trim() + " " + tokens[8].trim() + " " + theTDs.getContent(9));
				
				if (tokens[10].trim().equals(theTDs.getContent(11)) || tokens[10].trim().equals("NaN")) j = 0;
				else System.out.println(tokens[0].trim() + " " + tokens[10].trim() + " " + theTDs.getContent(11));
				
				if (tokens[11].trim().equals("NaN")) j = 0;
				else {
					builder.append(theTDs.getContent(12).substring(0, 10));
					builder.append('T');
					builder.append(theTDs.getContent(12).substring(11, 19));
					cme_time = builder.toString();
					builder.delete(0, builder.length());
					if (tokens[11].trim().equals(cme_time + "Z")) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[11].trim() + " " + cme_time);
				}
				
				if (theTDs.getContent(13).equals(tokens[12].trim() + ".0") || tokens[12].trim().equals("NaN")) j = 0;
				else System.out.println(tokens[0].trim() + " " + tokens[12].trim() + " " + theTDs.getContent(13));
				
				if (theTDs.getContent(14)!=null) {
					
					if (tokens[13].trim().equals(theTDs.getContent(14))) j = 0;
					else System.out.println(tokens[0].trim() + " " + tokens[13].trim() + " " + theTDs.getContent(14));
					
				}
				
				if (theTDs.getContent(15).equals(tokens[14].trim() + ".0") || tokens[14].trim().equals("NaN")) j = 0;
				else System.out.println(tokens[0].trim() + " " + tokens[14].trim() + " " + theTDs.getContent(15));
				
				if (theTDs.getContent(16).equals(tokens[15].trim() + ".0") || tokens[14].trim().equals("NaN")) j = 0;
				else System.out.println(tokens[0].trim() + " " + tokens[15].trim() + " " + theTDs.getContent(16));
				

			}
			// line counter
			i++;
	
		}
		
		//System.out.println("Testing");
	} catch (Exception e) {System.out.println(e.getMessage());}
}


}
