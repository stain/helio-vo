package validate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cds.savot.pull.*;
//import cds.savot.common.*;
import cds.savot.model.*;

//import org.kxml2.*;
//import org.xmlpull.v1.*;

public class HelioValIstpSolarWind {
	
	public static void main (String[] args)
	{
		
		// directory containing the event list
		String deldir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\istp_solar_wind\\";
		
		String valdir  = "C:\\Development\\HELIO\\Event_lists\\Tests\\istp_solar_wind\\";
		// input delivered file name
		String delname = deldir + "ISTP_SOLAR_WIND_CATALOG.txt";
		
		// input VOTable name
		String valname = valdir + "ISTP_Solar_WindFull_list.xml";

		// lines to skip at start of list
		int skiplines = 0;
		
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
			String delims = "[$]";
			String[] tokens;

			File file = new File(delname);
		
			FileReader fr = new FileReader ( file );
			BufferedReader delin = new BufferedReader( fr);

			String deline;
			
			int i = 0;
			int k = 0;
			while ((deline = delin.readLine()) != null) {
					
				//skip lines at start of file 
				if (i>skiplines-1) {

					tokens = deline.split(delims);
					
					TDSet theTDs = tr.getTDSet(i);
					
					for (int j = 0; j < theTDs.getItemCount(); j++) {
						if (tokens[j].trim().length() >= 20) {
							if (tokens[j].trim().subSequence(10, 11).equals("T") && tokens[j].trim().subSequence(19, 20).equals("Z")) {
								if (tokens[j].trim().equals(theTDs.getContent(j)+"Z")) k=0;
								else System.out.println(i + " " + tokens[0].trim() + " " + tokens[j].trim() + " " + theTDs.getContent(j));
							}
							else {
								if (tokens[j].trim().equals(theTDs.getContent(j))) k=0;
								else System.out.println(i + " " + tokens[0].trim() + " " + tokens[j].trim() + " " + theTDs.getContent(j));
							}
						}
						else {
							if (tokens[j].trim().equals(theTDs.getContent(j))) k=0;
							else System.out.println(i + " " + tokens[0].trim() + " " + tokens[j].trim() + " " + theTDs.getContent(j));
						}
					
					}

				}
				i++;
			}
		} catch (Exception e) {System.out.println(e.getMessage());}
	}
}
