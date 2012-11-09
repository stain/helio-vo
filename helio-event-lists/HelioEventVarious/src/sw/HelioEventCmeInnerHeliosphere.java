package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;     

public class HelioEventCmeInnerHeliosphere {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int no_tokens;
		
		String cme_id;
		
		boolean no_cme1au = false;
		
		String v_cme;
		String h_p;
		String xray;
		String optical;

		String loc;
		String ns;
		String ew;
		String long_hg;
		String lat_hg;
		int o_long;
		int o_lat;
		
		String long_carr;
		
		String v_icme;
		String tt_1au;
		
		String alpha;
		String v_icme_pred;
		String tt_1au_pred;
		String cme_int;
			
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\CME_inner_heliosphere\\";
		
		// input file name
		String inname = dir + "cme_inner_heliosphere_table.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 3;
		
		// name of output file 
		String outname = dir + "cme_inner_heliosphere_reformat.txt";
		File out = new File(outname);

		try {
			
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
			
			pw.println("cme_number" + "|" + "time_start" + "|" + "v_cme" + "|" + "cme_type" 
					+ "|" + "xray_class" + "|" + "optical_class"
					+ "|" + "lat_hg" + "|" + "long_hg" + "|" + "long_carr" 
					+ "|" + "time_ips_1au" + "|" + "time_cme_1au" + "|" + "v_icme" 
					+ "|" + "time_transit_1au" + "|" + "alpha"
					+ "|" + "v_1au_predict" + "|" + "time_transit_1au_predict"
					+ "|" + "flag_cme_interaction");
			
			
			String delims = " ";
			String del = "/";
			String[] tokens;
			String[] tok;

			File file = new File(inname);
		
			FileReader fr = new FileReader ( file );
			BufferedReader in = new BufferedReader( fr);
				
			String line;
			int len = 0;
			int i = 0;
			while ((line = in.readLine()) != null) {
					
				//skip lines at start of file 
				if (i>skiplines-1) {
					
					tokens = line.split(delims);
					
					System.out.println(line);

					no_tokens = tokens.length;
					System.out.println("No. of tokens = " + no_tokens);

					cme_id = tokens[0].trim();
					
					System.out.println(tokens[2]);
					
					//Start time					
					y = Integer.parseInt(tokens[1].substring(0, 2));
					if (y > 10) y = y +1900;
					else y = y +2000;
					
					m = Integer.parseInt(tokens[1].substring(2, 4)) - 1;
					d = Integer.parseInt(tokens[1].substring(4, 6));
					h = Integer.parseInt(tokens[2].substring(0, 2));
					min = Integer.parseInt(tokens[2].substring(3, 5));
					s = 0;
					
					//*****
					System.out.println(y  + " " + m  + " " + d  + " " + h + " " + min);
									
					startDate = new GregorianCalendar(y, m, d, h, min, s);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					
					//IPS time at 1AU
					y = Integer.parseInt(tokens[7].substring(0, 2));
					if (y > 10) y = y +1900;
					else y = y +2000;
										
					m = Integer.parseInt(tokens[7].substring(2, 4)) - 1;
					d = Integer.parseInt(tokens[7].substring(4, 6));
					h = Integer.parseInt(tokens[8].substring(0, 2));
					min = Integer.parseInt(tokens[8].substring(3, 5));
					s = 0;
				
					//*****
					System.out.println(y  + " " + m  + " " + d  + " " + h + " " + min);
										
					ips1auDate = new GregorianCalendar(y, m, d, h, min, s);
					
					//CME arrival time at 1AU
					no_cme1au = false;
					if (tokens[9].equals("...")) no_cme1au = true;
					else {
						y = Integer.parseInt(tokens[9].substring(0, 2));
						if (y > 10) y = y +1900;
						else y = y +2000;
										
						m = Integer.parseInt(tokens[9].substring(2, 4)) - 1;
						d = Integer.parseInt(tokens[9].substring(4, 6));
						h = Integer.parseInt(tokens[10].substring(0, 2));
						min = Integer.parseInt(tokens[10].substring(3, 5));
						s = 0;
				
						//*****
						System.out.println(y  + " " + m  + " " + d  + " " + h + " " + min);
					
						cme1auDate = new GregorianCalendar(y, m, d, h, min, s);
					}
					
					v_cme = tokens[3].trim();
					h_p = tokens[4].trim();
					
					optical = tokens[5].trim();
					if (optical.equals("flm")) {
						xray = " ";
					} else {
						del = "[/]";
						tok = optical.split(del);
						no_tokens = tok.length;
						if (no_tokens == 1) {
							xray = tok[0];
							optical = " ";
						} else {
							xray = tok[0];
							optical = tok[1];
						}
					}
					
					loc     = tokens[6].trim();
					ns = loc.substring(0, 1);
					o_lat = Integer.parseInt(loc.substring(1, 3));
					if (ns.equals("S")) o_lat = - o_lat;
					ew = loc.substring(3, 4);
					o_long = Integer.parseInt(loc.substring(4, 6));
					if (ew.equals("E")) o_long = - o_long;
						
					lat_hg  = Integer.toString(o_lat);
					long_hg = Integer.toString(o_long);
					
					long_carr = "NaN";
					
					v_icme = tokens[11].trim();
					if (v_icme.equals("...")) v_icme = "NaN";
					
					tt_1au = tokens[12].trim();
					if (tt_1au.equals("...")) tt_1au = "NaN";
					
					alpha = tokens[13].trim();
					v_icme_pred = tokens[14].trim();
					tt_1au_pred = tokens[15].trim();
					len = tokens[15].length();
					if (tt_1au_pred.substring(len-1, len).equals("a")) {
						tt_1au_pred = tt_1au_pred.substring(0, len-1);
						cme_int     = "Y";
					} else {
						cme_int     = " ";
					}
					
					if (no_cme1au) {
						pw.println(cme_id + "|" + sdf.format( startDate.getTime() ) + "Z|" + v_cme
								+ "|" + h_p + "|" + xray + "|" + optical
								+ "|" + lat_hg + "|" + long_hg + "|" + long_carr
								+ "|" + sdf.format( ips1auDate.getTime() ) 
								+ "Z|" + "NaN" 
								+ "|" + v_icme + "|" + tt_1au + "|" + alpha 
								+ "|" + v_icme_pred + "|" + tt_1au_pred + "|" + cme_int);
					} else {
						pw.println(cme_id + "|" + sdf.format( startDate.getTime() ) + "Z|" + v_cme
								+ "|" + h_p + "|" + xray + "|" + optical
								+ "|" + lat_hg + "|" + long_hg + "|" + long_carr
								+ "|" + sdf.format( ips1auDate.getTime() ) 
								+ "Z|" + sdf.format( cme1auDate.getTime() ) 
								+ "Z|" + v_icme + "|" + tt_1au + "|" + alpha 
								+ "|" + v_icme_pred + "|" + tt_1au_pred + "|" + cme_int);
					}
	
				}
				
				// line counter
				i++;
		
			}
			
			fw.close();
			
		} catch (Exception e) {System.out.println(e.getMessage());}


	}
	
	static private int y;
	static private int m;
	static private int d;
	static private int h;
	static private int min;
	static private int s;
	
	static private Calendar startDate;
	static private Calendar ips1auDate;
	static private Calendar cme1auDate;



}
