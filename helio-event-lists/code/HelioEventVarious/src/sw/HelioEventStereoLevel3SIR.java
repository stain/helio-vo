package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;  

public class HelioEventStereoLevel3SIR {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String comp_id;
		String id;
		String flag;
		String spacecraft;
		
		String pt_max;
		String b_max;
		String np_max;
		String v_min;
		String v_max;
		
		Boolean no_ptm_time = false;
			
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\Stereo_SIRs\\";
		
		// input file name
		String inname = dir + "Stereo_Level3_SIR.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 1;
		
		// name of output files 
		String outname1 = dir + "stereoa_impactplastic_sir.txt";
		File out1 = new File(outname1);

		String outname2 = dir + "stereob_impactplastic_sir.txt";
		File out2 = new File(outname2);

		try {
			
			HelioEventStereoLevel3SIR stereoLevel3SIR = new HelioEventStereoLevel3SIR();
			
			FileWriter fw1 = new FileWriter( out1 );
			PrintWriter pw1 = new PrintWriter( fw1 );
			
			FileWriter fw2 = new FileWriter( out2 );
			PrintWriter pw2 = new PrintWriter( fw2 );
			
			pw1.println("comp_id" + "|" + "id" + "|" + "flag" + "|" + "observatory" + "|" + "time_start" 
					+ "|" + "time_end" + "|" + "time_pt_max" + "|" + "pt_max" + "|" + "b_max"
					+ "|" + "np_max" + "|" + "v_min" + "|" + "v_max");
			
			pw2.println("comp_id" + "|" + "id" + "|" + "flag" + "|" + "observatory" + "|" + "time_start" 
					+ "|" + "time_end" + "|" + "time_pt_max" + "|" + "pt_max" + "|" + "b_max"
					+ "|" + "np_max" + "|" + "v_min" + "|" + "v_max");
			
			String delims = "[,]";
			String[] tokens;

			File file = new File(inname);
		
			FileReader fr = new FileReader ( file );
			BufferedReader in = new BufferedReader( fr );
				
			String line;
			int i = 0;
			while ((line = in.readLine()) != null) {
				
				//add space if no comp id
				if (line.substring(0, 1).equals(",")) line = " " + line;
				
				//skip lines at start of file 
				if (i>skiplines-1) {

					tokens = line.split(delims);
					
					//id
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

					//check for year
					if (Integer.parseInt(id) > 100) {
						
						year = id;
						y = Integer.parseInt(id);
						
					} else {
						
						spacecraft = tokens[1];
						if (spacecraft.equals("A")) spacecraft = "STEREO_A";
						if (spacecraft.equals("B")) spacecraft = "STEREO_B";
					
						//Start time
						stereoLevel3SIR.extractDate(tokens[2]);
					
						//*****
						System.out.println(year  + " " + month  + " " + day  + " " + hms);
					
						startDate = stereoLevel3SIR.convertDate(year, month, day, hms);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					
						// end time
						stereoLevel3SIR.extractDate(tokens[3]);

						endDate = stereoLevel3SIR.convertDate(year, month, day, hms);
						
						// Pt max time
						no_ptm_time = false;
						if (tokens[4].equals("DG")) {
							no_ptm_time = true;
						} else {
							stereoLevel3SIR.extractDate(tokens[4]);
							ptMaxDate = stereoLevel3SIR.convertDate(year, month, day, hms);
						}

						pt_max  = tokens[5];
						b_max   = tokens[6];
						np_max  = tokens[7];
						v_min   = tokens[8];
						v_max   = tokens[9];
						
						comp_id = " ";
						if (tokens.length>10) comp_id = tokens[10];
						
						if (no_ptm_time) {
							
							if (spacecraft.equals("STEREO_A")) {
								pw1.println(comp_id + "|" + id + "|" + flag + "|" + spacecraft + "|" + sdf.format( startDate.getTime() )
										+ "Z|" + sdf.format( endDate.getTime() )
										+ "Z|" + tokens[4] 
										+ "|" + pt_max + "|" + b_max + "|" + np_max + "|" + v_min + "|" + v_max);
							
							} else if (spacecraft.equals("STEREO_B")) {
								pw2.println(comp_id + "|" + id + "|" + flag + "|" + spacecraft + "|" + sdf.format( startDate.getTime() )
										+ "Z|" + sdf.format( endDate.getTime() )
										+ "Z|" + tokens[4] 
										+ "|" + pt_max + "|" + b_max + "|" + np_max + "|" + v_min + "|" + v_max);
								
							} else System.out.println("Invalid spcecraft: " + spacecraft);

						} else {
							
							if (spacecraft.equals("STEREO_A")) {
								pw1.println(comp_id + "|" + id + "|" + flag + "|" + spacecraft + "|" + sdf.format( startDate.getTime() )
										+ "Z|" + sdf.format( endDate.getTime() )
										+ "Z|" + sdf.format( ptMaxDate.getTime() ) 
										+ "Z|" + pt_max + "|" + b_max + "|" + np_max + "|" + v_min + "|" + v_max);
						
							} else if (spacecraft.equals("STEREO_B")) {
								pw2.println(comp_id + "|" + id + "|" + flag + "|" + spacecraft + "|" + sdf.format( startDate.getTime() )
										+ "Z|" + sdf.format( endDate.getTime() )
										+ "Z|" + sdf.format( ptMaxDate.getTime() ) 
										+ "Z|" + pt_max + "|" + b_max + "|" + np_max + "|" + v_min + "|" + v_max);
							
							} else System.out.println("Invalid spcecraft: " + spacecraft);
						
						}
					
					
					}
	
				}
				
				// line counter
				i++;
		
			}
			
			fw1.close();
			fw2.close();
			
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

		//year  = timtoks[0];
		month = timtoks[1];
		day   = timtoks[2];
		
		//check for hours < 10 without preceeding 0
		if (timtoks[3].length()==1) timtoks[3] = "0" + timtoks[3];
		
		//check for fractions of a minute
		if (timtoks[4].length()>2) {
			float m_f   = Float.parseFloat(timtoks[4]);
			
			min = Math.round(m_f);
			timtoks[4] = Integer.toString(min);
			if (timtoks[4].length()==1) timtoks[4] = "0" + timtoks[4];
			
		}

		//check for minutes < 10 without preceeding 0
		if (timtoks[4].length()==1) timtoks[4] = "0" + timtoks[4];

	    hms = timtoks[3] + timtoks[4] + "00";

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
	static private Calendar ptMaxDate;

}

