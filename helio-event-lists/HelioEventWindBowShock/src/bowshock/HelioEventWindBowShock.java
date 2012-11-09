package bowshock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;     

public class HelioEventWindBowShock {
		
		public static void main (String[] args)
		{
			
			String lbs_y;
			String lbs_doy;
			String lbs_h;
			String lbs_m;
			String lbs_s;
			String lbs_f;
			
			String fhr_y;
			String fhr_doy;
			String fhr_h;
			String fhr_f;
			
			String lhr_y;
			String lhr_doy;
			String lhr_h;
			String lhr_f;
			
			String fbs_y;
			String fbs_doy;
			String fbs_h;
			String fbs_m;
			String fbs_s;
			String fbs_f;
			
			int y;
			int d;
			int doy;
			int m;
			int h;
			int min;
			int s;
			
			
			// directory containing the event list
			String dir  = "C:\\Development\\HELIO\\Event_lists\\";
			
			// input fie name
			String inname = dir + "Bow_Shock_Crossings_notes.txt";

			// args[2] - lines to skip at start of list
			int skiplines = 2;
			
			// name of output file 
			String outname = dir + "WIND_IMF_BOW_SHOCK.txt";
			File out = new File(outname);
		
			try {
				FileWriter fw = new FileWriter( out );
				PrintWriter pw = new PrintWriter( fw );
				
				pw.println("LAST BS CROSSING" + " | " + "LAST BS FLAG" + " | " + "1ST HR KEPT" + " | " + "1ST HR FLAG"
						+ " | " + "LAST HR KEPT" + " | " + "LAST HR FLAG" + " | " + "FIRST BS CROSSING" + " | " + "FIRST BS FLAG");
				
				//String delims = "[ :.]";
				//String[] tokens;

				File file = new File(inname);
			
				FileReader fr = new FileReader ( file );
				BufferedReader in = new BufferedReader( fr);
					
				String line;
				int i = 0;
				while ((line = in.readLine()) != null) {
						
					//skip lines at start of file 
					if (i>skiplines-1) {

						//tokens = line.split(delims);
						
						//Last BS crossing
						lbs_y = line.substring(0, 2);
						lbs_doy = line.substring(3, 6);
						lbs_h = line.substring(7, 9);
						lbs_m = line.substring(10, 12);
						lbs_s = line.substring(13, 14);
						lbs_f = line.substring(14, 15);
					
						System.out.println(lbs_y  + " " + lbs_doy + " " + lbs_h + " " + lbs_m + " " + lbs_s + " " + lbs_f);
						
						// date
						y = Integer.parseInt(lbs_y);
						doy = Integer.parseInt(lbs_doy);
						
						//years 
						if (y>20) y = 1900 + y;
						else y = 2000 + y;
						

						// hms
						h = Integer.parseInt(lbs_h);
						min = Integer.parseInt(lbs_m);
						s = Integer.parseInt(lbs_s)*6;
				
						m = 0;
						d = 0;
						lastBSDate = new GregorianCalendar(y, m, d, h, min, s);
						lastBSDate.add(Calendar.DAY_OF_YEAR, doy);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
						
						//1st hr kept
						fhr_y = line.substring(23, 25);
						fhr_doy = line.substring(26, 29);
						fhr_h = line.substring(30, 32);
						fhr_f = line.substring(32, 33);
					
						System.out.println(fhr_y  + " " + fhr_doy + " " + fhr_h + " " + fhr_f);

						// date
						y = Integer.parseInt(fhr_y);
						doy = Integer.parseInt(fhr_doy);
						
						//years 
						if (y>20) y = 1900 + y;
						else y = 2000 + y;
						

						// hms
						h = Integer.parseInt(fhr_h);
						min = 0;
						s = 0;
				
						m = 0;
						d = 0;
						firstHrDate = new GregorianCalendar(y, m, d, h, min, s);
						firstHrDate.add(Calendar.DAY_OF_YEAR, doy);
						
						//Last hr kept
						lhr_y = line.substring(41, 43);
						lhr_doy = line.substring(44, 47);
						lhr_h = line.substring(48, 50);
						lhr_f = line.substring(50, 51);
					
						System.out.println(lhr_y  + " " + lhr_doy + " " + lhr_h + " " + lhr_f);

						// date
						y = Integer.parseInt(lhr_y);
						doy = Integer.parseInt(lhr_doy);
						
						//years 
						if (y>20) y = 1900 + y;
						else y = 2000 + y;
						
						// hms
						h = Integer.parseInt(lhr_h);
						min = 0;
						s = 0;
				
						m = 0;
						d = 0;
						lastHrDate = new GregorianCalendar(y, m, d, h, min, s);
						lastHrDate.add(Calendar.DAY_OF_YEAR, doy);
						
						//First BS crossing
						fbs_y = line.substring(60, 62);
						fbs_doy = line.substring(63, 66);
						fbs_h = line.substring(67, 69);
						fbs_m = line.substring(70, 72);
						fbs_s = line.substring(73, 74);
						fbs_f = line.substring(74, 75);
					
						System.out.println(fbs_y  + " " + fbs_doy + " " + fbs_h + " " + fbs_m + " " + fbs_s + " " + fbs_f);
						
						// date
						y = Integer.parseInt(fbs_y);
						doy = Integer.parseInt(fbs_doy);
						
						//years 
						if (y>20) y = 1900 + y;
						else y = 2000 + y;
						

						// hms
						h = Integer.parseInt(fbs_h);
						min = Integer.parseInt(fbs_m);
						s = Integer.parseInt(fbs_s)*6;
				
						m = 0;
						d = 0;
						firstBSDate = new GregorianCalendar(y, m, d, h, min, s);
						firstBSDate.add(Calendar.DAY_OF_YEAR, doy);
						pw.println(sdf.format( lastBSDate.getTime() ) + "Z | " + lbs_f + " | " +
								sdf.format( firstHrDate.getTime() ) + "Z | " + fhr_f + " | " +
								sdf.format( lastHrDate.getTime() ) + "Z | " + lhr_f + " | " +
								sdf.format( firstBSDate.getTime() ) + "Z | " + fbs_f);
	        
					}
					
					// line counter
					i++;
			
				}
				
				fw.close();
				
			} catch (Exception e) {System.out.println(e.getMessage());}
	}
		
		static private Calendar lastBSDate;
		static private Calendar firstHrDate;
		static private Calendar lastHrDate;
		static private Calendar firstBSDate;
		
	}

