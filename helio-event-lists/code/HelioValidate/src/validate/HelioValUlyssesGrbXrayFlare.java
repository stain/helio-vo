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

public class HelioValUlyssesGrbXrayFlare {
	
	public static void main (String[] args)
	{

	String st_time;

	String end_time;

	
	int id_del;
	int id_hec;
	
	float r_hgi;
	float lat_hgi;
	float long_hgi;
	
	float r_hgi_hec;
	float lat_hgi_hec;
	float long_hgi_hec;

	
	// directory containing the event list
	String deldir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\ulysses_grb_xray_flare\\";
	
	String valdir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\ulysses_grb_xray_flare\\";
	// input delivered file name
	String delname = deldir + "ulysses_grb_flare_list.txt";
	
	// input VOTable file name
	String valname = valdir + "ulysses_grb_xray_flare_full.xml";

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
				
				if (tokens[0].trim().equals(theTDs.getContent(1))) j = 0;
				else System.out.println(tokens[1].trim() + " " + tokens[0].trim() + " " + theTDs.getContent(1));
				
				builder.append(theTDs.getContent(2).substring(0, 10));
				builder.append('T');
				builder.append(theTDs.getContent(2).substring(11, 19));
				st_time = builder.toString();
				builder.delete(0, builder.length());
				if (tokens[1].trim().equals(st_time + "Z")) j = 0;
				else System.out.println(tokens[1].trim() + " " + tokens[1].trim() + " " + st_time);
				
				builder.append(theTDs.getContent(3).substring(0, 10));
				builder.append('T');
				builder.append(theTDs.getContent(3).substring(11, 19));
				end_time = builder.toString();
				builder.delete(0, builder.length());
				if (tokens[2].trim().equals(end_time + "Z")) j = 0;
				else System.out.println(tokens[1].trim() + " " + tokens[2].trim() + " " + end_time);
				
				r_hgi = Float.parseFloat(tokens[3].trim());
				r_hgi_hec = Float.parseFloat(theTDs.getContent(4));
				if (r_hgi == r_hgi_hec) j = 0;
				else System.out.println(tokens[1].trim() + " " + tokens[3].trim() + " " + theTDs.getContent(4));
				
				lat_hgi = Float.parseFloat(tokens[4].trim());
				lat_hgi_hec = Float.parseFloat(theTDs.getContent(5));
				if (lat_hgi == lat_hgi_hec) j = 0;
				else System.out.println(tokens[1].trim() + " " + tokens[4].trim() + " " + theTDs.getContent(5));
				
				long_hgi = Float.parseFloat(tokens[5].trim());
				long_hgi_hec = Float.parseFloat(theTDs.getContent(6));
				if (long_hgi == long_hgi_hec) j = 0;
				else System.out.println(tokens[1].trim() + " " + tokens[5].trim() + " " + theTDs.getContent(6));
				

			}
			// line counter
			i++;
	
		}
		
		System.out.println("Testing");
	} catch (Exception e) {System.out.println(e.getMessage());}
}



}
