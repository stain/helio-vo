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

public class HelioValHaloCmeFlareMagStorm {
	
	public static void main (String[] args)
	{

	String st_time;

	String end_time;

	
	int id_del;
	int id_hec;
	
	String lat_hg;
	String long_hg;
	
	String flare_time;
	String storm_time;

	// directory containing the event list
	String deldir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\halo_cme_flare_magnetic_storm\\";
	
	String valdir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\halo_cme_flare_magnetic_storm\\";
	// input delivered file name
	String delname = deldir + "gopalswamy_cme_list.txt";
	
	// input VOTable file name
	String valname = valdir + "halo_cme_flare_magnetic_storm_full.xml";

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
				
				id_del = Integer.parseInt(tokens[0].trim());
				id_hec = Integer.parseInt(theTDs.getContent(1));
				
				if (id_del == id_hec) j = 0;
				else System.out.println(tokens[1].trim() + " " + id_del + " " + id_hec);
				
				builder.append(theTDs.getContent(2).substring(0, 10));
				builder.append('T');
				builder.append(theTDs.getContent(2).substring(11, 19));
				st_time = builder.toString();
				builder.delete(0, builder.length());
				if (tokens[1].trim().equals(st_time + "Z")) j = 0;
				else System.out.println(tokens[1].trim() + " " + tokens[1].trim() + " " + st_time);
				
				if (theTDs.getContent(3).equals(tokens[2].trim() + ".0")) j = 0;
				else System.out.println(tokens[1].trim() + " " + tokens[2].trim() + " " + theTDs.getContent(3));
				
				if (theTDs.getContent(4).equals(tokens[3].trim() + ".0")) j = 0;
				else System.out.println(tokens[1].trim() + " " + tokens[3].trim() + " " + theTDs.getContent(4));
				
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
				else System.out.println(tokens[1].trim() + " " + lat_hg + " " + theTDs.getContent(5));
				
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
				else System.out.println(tokens[1].trim() + " " + long_hg + " " + theTDs.getContent(6));
				
				if (theTDs.getContent(7)!=null) j = 0;
				else System.out.println(tokens[1].trim() + " " + tokens[6].trim() + " " + theTDs.getContent(7));
				
				if (theTDs.getContent(8).equals(tokens[7].trim())) j = 0;
				else System.out.println(tokens[1].trim() + " " + tokens[7].trim() + " " + theTDs.getContent(8));
				
				if (tokens[8].trim().equals("NaN")) j = 0;
				else {
					builder.append(theTDs.getContent(9).substring(0, 10));
					builder.append('T');
					builder.append(theTDs.getContent(9).substring(11, 19));
					flare_time = builder.toString();
					builder.delete(0, builder.length());
					if (tokens[8].trim().equals(flare_time + "Z")) j = 0;
					else System.out.println(tokens[1].trim() + " " + tokens[8].trim() + " " + flare_time);
				}
				
				if (tokens[9].trim().equals(theTDs.getContent(10)) || tokens[9].trim().equals("NaN")) j = 0;
				else System.out.println(tokens[1].trim() + " " + tokens[9].trim() + " " + theTDs.getContent(10));
				
				builder.append(theTDs.getContent(11).substring(0, 10));
				builder.append('T');
				builder.append(theTDs.getContent(11).substring(11, 19));
				storm_time = builder.toString();
				builder.delete(0, builder.length());
				if (tokens[10].trim().equals(storm_time + "Z") || tokens[10].trim().equals(storm_time)) j = 0;
				else System.out.println(tokens[1].trim() + " " + tokens[10].trim() + " " + storm_time);
				
				if (theTDs.getContent(12).equals(tokens[11].trim() + ".0")) j = 0;
				else System.out.println(tokens[1].trim() + " " + tokens[11].trim() + " " + theTDs.getContent(12));
				
			}
			// line counter
			i++;
	
		}
		
		System.out.println("Testing");
	} catch (Exception e) {System.out.println(e.getMessage());}
}


}
