package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;  

public class HelioEventStereoLevel3ICME {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String id;
		String flag;
		String spacecraft;
		
		String pt_max = "NaN";
		String pt_max_sheath = "NaN";
		String b_max = "NaN";
		String b_max_sheath = "NaN";
		String v_max = "NaN";
		String v_max_sheath = "NaN";
		String delta_v = "NaN";
		String group = "NaN";
		String comment;
			
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\STEREO_ICMEs\\";
		
		// input file name
		String inname = dir + "STEREO_Level3_ICME.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 1;
		
		// name of output file 
		String outname = dir + "stereo_impactplastic_icme.txt";
		File out = new File(outname);

		try {
			
			HelioEventStereoLevel3ICME stereoLevel3ICME = new HelioEventStereoLevel3ICME();
			
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
			
			pw.println("id" + "|" + "flag" + "|" + "observatory" + "|" + "time_start" 
					+ "|" + "time_mag_obstacle_start" + "|" + "time_end" + "|" + "pt_max" + "|" + "pt_max_sheath"
					+ "|" + "b_max" + "|" + "b_max_sheath"
					+ "|" + "v_max" + "|" + "v_max_sheath"
					+ "|" + "delta_v" + "|" + "group" + "|" + "comment");
			
			
			String delims = "[,]";
			String[] tokens;
			String[] f_tokens;


			File file = new File(inname);
		
			FileReader fr = new FileReader ( file );
			BufferedReader in = new BufferedReader( fr);
				
			String line;
			int i = 0;
			while ((line = in.readLine()) != null) {
					
				//skip lines at start of file 
				if (i>skiplines-1) {

					tokens = line.split(delims);
					
					// id
					id = tokens[0];
					
					// search id for flag
					flag = " ";
					for (int j=0; j<id.length(); j++) {
						String idss = id.substring(j, j+1);
						if (idss.equals("*") || idss.equals("?")) {
							flag = idss;
							
						    //check for extra flag
							if (id.length()>j+1) {
								flag = flag + id.substring(j+1, j+2);
								id = id.substring(0, id.length()-2);
							} else {
								id = id.substring(0, id.length()-1);
							}
						}
					}
					
					spacecraft = tokens[1];
					if (spacecraft.equals("A")) spacecraft = "STEREO_A";
					if (spacecraft.equals("B")) spacecraft = "STEREO_B";
					if (spacecraft.equals("A & B")) spacecraft = "STEREO_A,STEREO_B";
					
					//Start time
					stereoLevel3ICME.extractDate(tokens[2]);
					
					//*****
					System.out.println(year  + " " + month  + " " + day  + " " + hms);
					
					startDate = stereoLevel3ICME.convertDate(year, month, day, hms);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					
					// magnetic obstacle start date
					stereoLevel3ICME.extractDate(tokens[3]);
					
					magStartDate = stereoLevel3ICME.convertDate(year, month, day, hms);
							
					// end time
					stereoLevel3ICME.extractDate(tokens[4]);

					endDate = stereoLevel3ICME.convertDate(year, month, day, hms);

					// comment split into several parts by commas
					comment = " ";
					for (int j=10; j<tokens.length; j++) {
						if (tokens[j]!=null) {
							if (j==10) {
								comment  = comment + tokens[j];
							} else {
							comment  = comment + "," + tokens[j];
							}
						}
					}
					
					//trim " from string
					if (comment.length() > 2)
						comment = comment.substring(2, comment.length()-1);

					//replace ? with beta
					f_tokens = comment.split("[ ]");
					comment = "";
					for (int j=0; j<f_tokens.length; j++) {
						if (f_tokens[j].equals("?")) {
							comment  = comment + "beta" + " ";
						} else {
							comment  = comment + f_tokens[j] + " ";
						}
					}

					// check if token present
					if (tokens.length>5) {
						f_tokens = tokens[5].split("[()]");
						if (f_tokens[0].equals("NaN")) {
							pt_max        = "NaN";
							pt_max_sheath = "NaN";
						} else {
							pt_max = f_tokens[0];
							if (f_tokens.length > 1) pt_max_sheath = f_tokens[1];
							else pt_max_sheath = "NaN";
						}
					} else {
						pt_max        = "NaN";
						pt_max_sheath = "NaN";						
					}
					
					// check if token present
					if (tokens.length>6) {
						f_tokens = tokens[6].split("[()]");
						if (f_tokens[0].equals("NaN")) {
							b_max        = "NaN";
							b_max_sheath = "NaN";
						} else {
							b_max = f_tokens[0];
							if (f_tokens.length > 1) b_max_sheath = f_tokens[1];
							else b_max_sheath = "NaN";
						}
					} else {
						b_max        = "NaN";
						b_max_sheath = "NaN";												
					}
					
					// check if token present
					if (tokens.length>7) {
						f_tokens = tokens[7].split("[()]");
						if (f_tokens[0].equals("NaN") || f_tokens[0].equals("NA")) {
							v_max        = "NaN";
							v_max_sheath = "NaN";
						} else {
							v_max = f_tokens[0];
							if (f_tokens.length > 1) v_max_sheath = f_tokens[1];
							else v_max_sheath = "NaN";
						}
					} else {
						v_max        = "NaN";
						v_max_sheath = "NaN";						
					}
					
					// check if token present
					if (tokens.length>8) {
						if (tokens[8].trim().equals("NaN") || tokens[8].trim().equals("~") ||
								tokens[8].trim().equals("NA")) delta_v = "NaN";
						else delta_v = tokens[8];
					} else {
						delta_v = "NaN";
					}
					
					// check if token present
					if (tokens.length>9) {
						if (tokens[9].trim().equals("NaN") || tokens[9].trim().equals("/")) group ="NaN";
						else group = tokens[9];
					} else {
						group ="NaN";
					}
					
					pw.println(id + "|" + flag + "|" + spacecraft + "|" + sdf.format( startDate.getTime() )
							+ "Z|" + sdf.format( magStartDate.getTime() )
							+ "Z|" + sdf.format( endDate.getTime() ) 
							+ "Z|" + pt_max + "|" + pt_max_sheath
							+ "|" + b_max + "|" + b_max_sheath
							+ "|" + v_max + "|" + v_max_sheath
							+ "|" + delta_v + "|" + group
							+ "|" + comment);
	
				}
				
				// line counter
				i++;
		
			}
			
			fw.close();
			
		} catch (Exception e) {System.out.println(e.getMessage());}


	}
	
	private GregorianCalendar convertDate(String yStr, String mStr, String dStr, String hms)
	{
		y = Integer.parseInt(yStr);
		m = Integer.parseInt(mStr) - 1;
		d = Integer.parseInt(dStr);
		
		//System.out.println(y  + " " + m  + " " + d);
		
		h   = Integer.parseInt(hms.substring(0, 2)); 
		min = Integer.parseInt(hms.substring(2, 4));
		String ss  = hms.substring(4, 6);
		if (ss.equals("- "))
		{
			s = 0;
		} else {
			s = Integer.parseInt(ss);
		}
		
		
		return new GregorianCalendar(y, m, d, h, min, s);
	}
	
	private void extractDate(String time)
	{
		String delims = "[ /:]";
		String[] timtoks = time.split(delims);

		year  = timtoks[0];
		month = timtoks[2];
		day   = timtoks[3];
		
		//check for hours < 10 without preceeding 0
		if (timtoks[4].length()==1) timtoks[4] = "0" + timtoks[4];
		
		//check for fractions of a minute
		if (timtoks[5].length()>2) {
			float m_f   = Float.parseFloat(timtoks[5]);
			
			min = Math.round(m_f);
			timtoks[5] = Integer.toString(min);
			if (timtoks[5].length()==1) timtoks[5] = "0" + timtoks[5];

		}
	    hms = timtoks[4] + timtoks[5] + "00";

	}

	static private int y;
	static private int m;
	static private int d;
	static private int h;
	static private int min;
	static private int s;
	
	static private String year;
	static private String month;
	static private String day;
	static private String hms;
	
	static private Calendar startDate;
	static private Calendar endDate;
	static private Calendar magStartDate;

}

