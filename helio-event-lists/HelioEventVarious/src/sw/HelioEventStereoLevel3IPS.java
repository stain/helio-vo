package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HelioEventStereoLevel3IPS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String id;
		String flag;
		String spacecraft;
		
		String mag_ratio;
		String norm_angle;
		String beta;
		String mach_no;
		String data_avail;
		String f_r_shock;
		String comment;
			
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\Stereo_IPS\\";
		
		// STEREO A file name
		String inname1 = dir + "STEREOA_Level3_Shock.txt";

		// STEREO B file name
		String inname2 = dir + "STEREOB_Level3_Shock.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 1;
		
		// name of STEREO A output file 
		String outname1 = dir + "stereoa_impactplastic_shock.txt";
		File out1 = new File(outname1);

		// name of STEREO B output file 
		String outname2 = dir + "stereob_impactplastic_shock.txt";
		File out2 = new File(outname2);

		try {
			
			HelioEventStereoLevel3IPS stereoLevel3Shock = new HelioEventStereoLevel3IPS();
			
			FileWriter fw1 = new FileWriter( out1 );
			PrintWriter pw1 = new PrintWriter( fw1 );
			
			pw1.println("id" + "|" + "time_start" + "|" + "mag_ratio" + "|" + "norm_angle"
					+ "|" + "beta" + "|" + "mach_no" + "|" + "data_avail" + "|" + "f_r_shock"
					+ "|" + "comment");
			
			FileWriter fw2 = new FileWriter( out2 );
			PrintWriter pw2 = new PrintWriter( fw2 );
			
			pw2.println("id" + "|" + "time_start" + "|" + "mag_ratio" + "|" + "norm_angle"
					+ "|" + "beta" + "|" + "mach_no" + "|" + "data_avail" + "|" + "f_r_shock"
					+ "|" + "comment");
						
			String delims = "[,]";
			String[] tokens;

			File file1 = new File(inname1);
		
			FileReader fr1 = new FileReader ( file1 );
			BufferedReader in1 = new BufferedReader( fr1);
				
			String line;
			int i = 0;
			while ((line = in1.readLine()) != null) {
					
				//skip lines at start of file 
				if (i>skiplines-1) {

					tokens = line.split(delims);
					
					// id
					id = tokens[0];
										
					//Start time
					year    = tokens[1];
					month   = tokens[2];
					day     = tokens[3];
					hour    = tokens[4];
					minute  = tokens[5];
					secs    = tokens[6];
					
					//*****
					System.out.println(year  + " " + month  + " " + day  + " " + hour + " " + minute + " " + secs);
					
					startDate = stereoLevel3Shock.convertDate(year, month, day, hour, minute, secs);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					
					mag_ratio   = tokens[7];
					norm_angle  = tokens[8];
					beta        = tokens[9];
					if(beta.trim().equals("DG")) beta = "1.0";
					mach_no     = tokens[10];
					data_avail  = tokens[11];
					f_r_shock   = tokens[12];
					if (f_r_shock.length() > 1) f_r_shock = f_r_shock.substring(1, f_r_shock.length());
					
					// comment split into several parts by commas
					comment = " ";
					for (int j=13; j<tokens.length; j++) {
						if (tokens[j]!=null) {
							comment  = comment + tokens[j];
						}
					}
					
					//trim " from string
					if (comment.length() > 2)
						comment = comment.substring(2, comment.length()-1);
					
					pw1.println(id + "|" + sdf.format( startDate.getTime() ) + "Z|" + mag_ratio
							+ "|" + norm_angle + "|" + beta + "|" + mach_no + "|" + data_avail
							+ "|" + f_r_shock + "|" + comment);
	
				}
				
				// line counter
				i++;
		
			}
			
			File file2 = new File(inname2);
			
			FileReader fr2 = new FileReader ( file2 );
			BufferedReader in2 = new BufferedReader( fr2);
				
			i = 0;
			while ((line = in2.readLine()) != null) {
					
				//skip lines at start of file 
				if (i>skiplines-1) {

					tokens = line.split(delims);
					
					// id
					id = tokens[0];
										
					//Start time
					year    = tokens[1];
					month   = tokens[2];
					day     = tokens[3];
					hour    = tokens[4];
					minute  = tokens[5];
					secs    = tokens[6];
					
					//*****
					System.out.println(year  + " " + month  + " " + day  + " " + hour + " " + minute + " " + secs);
					
					startDate = stereoLevel3Shock.convertDate(year, month, day, hour, minute, secs);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					
					mag_ratio   = tokens[7];
					norm_angle  = tokens[8];
					beta        = tokens[9];
					if(beta.trim().equals("DG")) beta = "1.0";
					mach_no     = tokens[10];
					data_avail  = tokens[11];
					f_r_shock   = tokens[12];
					if (f_r_shock.length() > 1) f_r_shock = f_r_shock.substring(1, f_r_shock.length());
					
					// comment split into several parts by commas
					comment = " ";
					for (int j=13; j<tokens.length; j++) {
						if (tokens[j]!=null) {
							comment  = comment + tokens[j];
						}
					}
					
					//trim " from string
					if (comment.length() > 2)
						comment = comment.substring(2, comment.length()-1);
					
					pw2.println(id + "|" + sdf.format( startDate.getTime() ) + "Z|" + mag_ratio
							+ "|" + norm_angle + "|" + beta + "|" + mach_no + "|" + data_avail
							+ "|" + f_r_shock + "|" + comment);
	
				}
				
				// line counter
				i++;
		
			}
			
	
			fw1.close();
			fw2.close();
			
		} catch (Exception e) {System.out.println(e.getMessage());}


	}
	
	private GregorianCalendar convertDate(String yStr, String mStr, String dStr, String hStr, String minStr, String sStr)
	{
		y   = Integer.parseInt(yStr);
		m   = Integer.parseInt(mStr) - 1;
		d   = Integer.parseInt(dStr);		
		h   = Integer.parseInt(hStr); 
		min = Integer.parseInt(minStr);
		s_f   = Float.parseFloat(sStr);
		
		s = Math.round(s_f);
		
		return new GregorianCalendar(y, m, d, h, min, s);
	}
	

	static private int y;
	static private int m;
	static private int d;
	static private int h;
	static private int min;
	static private int s;
	
	static private float s_f;
	
	static private String year;
	static private String month;
	static private String day;
	static private String hour;
	static private String minute;
	static private String secs;
	
	static private Calendar startDate;
	static private Calendar endDate;
	static private Calendar magStartDate;

}
