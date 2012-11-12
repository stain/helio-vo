package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;     

public class HelioEventLanJianSIR {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String yf;
		
		String sir;
		String cir;
		String hyb_flag = " ";
		
		int next;
		
		boolean first;
		
		String st_y = "1995";
		
		String time;
		
		String st_m;
		String st_d;
		String st_h;
		String st_min;

		String end_m;
		String end_d;
		String end_h;
		String end_min;
		
		boolean disc_flag;
		String discon_m;
		String discon_d;
		String discon_h;
		String discon_min;
		
		boolean si_flag;
		String si_m;
		String si_d;
		String si_h;
		String si_min;

		String f_r_shock1;
		String f_r_shock2 = " ";
		String f_r_shock3 = " ";
		String pt_max;
		
		String v_max;
		String v_min;
		String delta_v;
		String b_max;

		String comment;
		String rem = " ";
		
		int y;
		int m;
		int d;
		int h;
		int min;
		int s;
		
		
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\Wind_ACE_SIR\\";
		
		// input fie name
		String inname = dir + "Jian_SIR_List.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 1;
		
		// name of output file 
		String outname = dir + "wind_ace_sir.txt";
		File out = new File(outname);

		try {
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
			
			pw.println("sir_id" + "|" + "hybrid_flag" + "|" + "cir_id" + "|" + "start_time"  + "|" + "end_time"
					+ "|" + "discon_time1" + "|" + "f_r_shock1"  
					+ "|" + "discon_time2" + "|" + "f_r_shock2"
					+ "|" + "discon_time3" + "|" + "f_r_shock3" 
					+ "|" + "si_time"   + "|" + "pt_max" + "|" + "v_max"  
					+ "|" + "v_min"    + "|" + "delta_v"  + "|" + "b_max"
					+ "|" + "comment");
			
			
			String delims = "	";
			String[] tokens;

			File file = new File(inname);
		
			FileReader fr = new FileReader ( file );
			BufferedReader in = new BufferedReader( fr);
				
			String line;
			int extra = 0;
			int i = 0;
			while ((line = in.readLine()) != null) {
					
				//skip lines at start of file 
				if (i>skiplines-1) {
					
					extra = 0;
					hyb_flag = " ";
					tokens = line.split(delims);
										
					System.out.println(tokens[0] + " " + tokens[1]);
					
					if (tokens[0].equals("**")) {
						st_y = tokens[1];
					} else if (tokens[0].equals(" ")) {
						System.out.println("skip");
					} else {

						//sir = tokens[0];
						if (tokens[0].length() == 4) {
							sir = tokens[0].substring(0,2).trim();
							hyb_flag = tokens[0].substring(3,4);
						} else if (tokens[0].length() == 6) {
							sir = tokens[0].substring(0,2).trim();
							hyb_flag = tokens[0].substring(3,4);
							extra = Integer.parseInt(tokens[0].substring(5,6));
						} else {
							sir = tokens[0];
						}
						
						cir = tokens[1];
				
						time = tokens[2];
						st_m = time.substring(0, 2);
						st_d = time.substring(3, 5);
						st_h = time.substring(6, 8);
						st_min = time.substring(8, 10);
					
						//*****
						System.out.println(st_y  + " " + st_m  + " " + st_d  + " " + st_h + " " + st_min);
					
						// date
						y = Integer.parseInt(st_y);
						m = Integer.parseInt(st_m) - 1;
						d = Integer.parseInt(st_d);
						h = Integer.parseInt(st_h);
						min = Integer.parseInt(st_min);
						s = 0;
					
						//*****
						System.out.println(y  + " " + m  + " " + d  + " " + h + " " + min);
										
						startDate = new GregorianCalendar(y, m, d, h, min, s);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

						//End time
						time = tokens[3];
						end_m = time.substring(0, 2);
						end_d = time.substring(3, 5);
						end_h = time.substring(6, 8);
						end_min = time.substring(8, 10);
					
						//*****
						System.out.println(st_y  + " " + end_m  + " " + end_d + " " + end_h + " " + end_min);
					
						// date
						m = Integer.parseInt(end_m) - 1;
						d = Integer.parseInt(end_d);
					
						// hms
						h = Integer.parseInt(end_h);
						min = Integer.parseInt(end_min);
						s = 0;
			
						endDate = new GregorianCalendar(y, m, d, h, min, s);

						//Discontinuity time
						disc_flag = true;
						if (tokens[4].length()==0){
							disc_flag = false;
						} else {
							time = tokens[4];
							discon_m = time.substring(0, 2);
							discon_d = time.substring(3, 5);
							discon_h = time.substring(6, 8);
							discon_min = time.substring(8, 10);
					
							//*****
							System.out.println(st_y  + " " + discon_m  + " " + discon_d + " " + discon_h + " " + discon_min);
					
							// date
							m = Integer.parseInt(discon_m) - 1;
							d = Integer.parseInt(discon_d);
					
							// hms
							h = Integer.parseInt(discon_h);
							min = Integer.parseInt(discon_min);
							s = 0;
			
							disconDate1 = new GregorianCalendar(y, m, d, h, min, s);
						}
						
						f_r_shock1  = tokens[5];
						
						//Stream interface time
						time = tokens[6];
						si_flag = true;
						if (time.equals("N/A")) {
							si_flag = false;
						} else {
							
							si_m = time.substring(0, 2);
							si_d = time.substring(3, 5);
							si_h = time.substring(6, 8);
							si_min = time.substring(8, 10);
					
							//*****
							System.out.println(st_y  + " " + si_m  + " " + si_d + " " + si_h + " " + si_min);
					
							// date
							m = Integer.parseInt(si_m) - 1;
							d = Integer.parseInt(si_d);
					
							// hms
							h = Integer.parseInt(si_h);
							min = Integer.parseInt(si_min);
							s = 0;
			
							siDate = new GregorianCalendar(y, m, d, h, min, s);
						}
						
						//pt_max = line.substring(35, 40).trim();
						pt_max = tokens[7];
						if (pt_max.equals("N/A")) pt_max = "NaN";
					
						//v_max = line.substring(43,49).trim();
						v_max = tokens[8];
						//v_min = line.substring(0,1);
						v_min = tokens[9];
						//delta_v = line.substring(1,2);
						delta_v = tokens[10];
						//b_max  = line.substring(1,2);
						b_max = tokens[11];
					
						//comment = line.substring(1,2);
						comment = tokens[12];
						
						if (extra == 1 || extra == 2) {
							
							line = in.readLine();
							tokens = line.split(delims);
							
							time = tokens[1];
							discon_m = time.substring(0, 2);
							discon_d = time.substring(3, 5);
							discon_h = time.substring(6, 8);
							discon_min = time.substring(8, 10);
					
							//*****
							System.out.println(st_y  + " " + discon_m  + " " + discon_d + " " + discon_h + " " + discon_min);
					
							// date
							m = Integer.parseInt(discon_m) - 1;
							d = Integer.parseInt(discon_d);
					
							// hms
							h = Integer.parseInt(discon_h);
							min = Integer.parseInt(discon_min);
							s = 0;
			
							disconDate2 = new GregorianCalendar(y, m, d, h, min, s);
							
							f_r_shock2 = tokens[2];

						}
						
						if (extra == 2) {
							
							line = in.readLine();
							tokens = line.split(delims);
							
							time = tokens[1];
							discon_m = time.substring(0, 2);
							discon_d = time.substring(3, 5);
							discon_h = time.substring(6, 8);
							discon_min = time.substring(8, 10);
					
							//*****
							System.out.println(st_y  + " " + discon_m  + " " + discon_d + " " + discon_h + " " + discon_min);
					
							// date
							m = Integer.parseInt(discon_m) - 1;
							d = Integer.parseInt(discon_d);
					
							// hms
							h = Integer.parseInt(discon_h);
							min = Integer.parseInt(discon_min);
							s = 0;
			
							disconDate3 = new GregorianCalendar(y, m, d, h, min, s);
							
							f_r_shock3 = tokens[2];

						}
						
						if (disc_flag && extra==2) {
							pw.println(sir + "|" + hyb_flag + "|" + cir + "|" + sdf.format( startDate.getTime() )
									+ "Z|" + sdf.format( endDate.getTime() )
									+ "Z|" + sdf.format( disconDate1.getTime() ) + "Z|" + f_r_shock1 
									+ "|" + sdf.format( disconDate2.getTime() ) + "Z|" + f_r_shock2
									+ "|" + sdf.format( disconDate3.getTime() ) + "Z|" + f_r_shock3
									+ "|" + sdf.format( siDate.getTime() ) 
									+ "Z|" + pt_max + "|" + v_max + "|" + v_min + "|" + delta_v 
									+ "|" + b_max + "|" + comment);

						} else if (disc_flag && extra==1) {
							pw.println(sir + "|" + hyb_flag + "|" + cir + "|" + sdf.format( startDate.getTime() )
									+ "Z|" + sdf.format( endDate.getTime() )
									+ "Z|" + sdf.format( disconDate1.getTime() ) + "Z|" + f_r_shock1 
									+ "|" + sdf.format( disconDate2.getTime() ) + "Z|" + f_r_shock2
									+ "|" + "NaN" + "|" + " "
									+ "|" + sdf.format( siDate.getTime() ) 
									+ "Z|" + pt_max + "|" + v_max + "|" + v_min + "|" + delta_v 
									+ "|" + b_max + "|" + comment);							
							
						} else if (disc_flag && extra==0) {
							pw.println(sir + "|" + hyb_flag + "|" + cir + "|" + sdf.format( startDate.getTime() )
									+ "Z|" + sdf.format( endDate.getTime() )
									+ "Z|" + sdf.format( disconDate1.getTime() ) + "Z|" + f_r_shock1 
									+ "|" + "NaN" + "|" + " "
									+ "|" + "NaN" + "|" + " "
									+ "|" + sdf.format( siDate.getTime() ) 
									+ "Z|" + pt_max + "|" + v_max + "|" + v_min + "|" + delta_v 
									+ "|" + b_max + "|" + comment);							

						} else {
							pw.println(sir + "|" + hyb_flag + "|" + cir + "|" + sdf.format( startDate.getTime() )
									+ "Z|" + sdf.format( endDate.getTime() ) 
									+ "Z|" + "NaN" + "|" + " " 
									+ "|" + "NaN" + "|" + " "
									+ "|" + "NaN" + "|" + " "
									+ "|" + sdf.format( siDate.getTime() ) 
									+ "Z|" + pt_max + "|" + v_max + "|" + v_min + "|" + delta_v 
									+ "|" + b_max + "|" + comment);
					
						}


					}
					
				}
			
				
				// line counter
				i++;
				
			}
			
			fw.close();
			
		} catch (Exception e) {System.out.println(e.getMessage());}
	}

	static private Calendar startDate;
	static private Calendar endDate;
	static private Calendar disconDate1;
	static private Calendar disconDate2;
	static private Calendar disconDate3;
	static private Calendar siDate;


	}

