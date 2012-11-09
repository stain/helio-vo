package sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;     

public class HelioEventIcmeCycle23 {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		boolean first;
		
		String st_y = "1996";
		
		String time;
		
		String st_m;
		String st_d;
		String st_h;
		String st_min;
		
		String start_off;
		String start_off_flag;
		String end_off;
		String end_off_flag;
		
		String start_mc;
		String start_mc_flag;
		String end_mc;
		String end_mc_flag;
		
		String bidir_flow_e;
		String bidir_flow_ion;
		
		String v_max_sw;
		String v_mean_sw;
		String v_incr_sw;
		String b_intensity;
		String qual_time_est;
		String mag_cloud;
		String dst_min;
		String v_1au;
		
		String a_or_w;
		String q_weak;
		String ups_shock;
		String dst_flag;
		String mc_list;
		
		String halo;

		String comment;
		String rem = " ";
		
		int y;
		int m;
		int d;
		int h;
		int min;
		int s;
		
		int no_tokens;
		
		
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\ICME_earth_cycle23\\";
		
		// input fie name
		String inname = dir + "icme_earth_cycle23_table.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 4;
		
		// name of output file 
		String outname = dir + "icme_earth_cycle23.txt";
		File out = new File(outname);

		try {
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
			
			pw.println("time_start" + "|" + "time_start_icme" + "|" 
					+ "time_end_icme" + "|" + "flag_a_w"
					+ "|" + "time_offset_start_sig" + "|" + "flag_toff_start_sig"
					+ "|" + "time_offset_end_sig" + "|" + "flag_toff_end_sig"
					+ "|" + "time_offset_start_mc" + "|" + "flag_toff_start_mc"
					+ "|" + "time_offset_end_mc" + "|" + "flag_toff_end_mc"
					+ "|" + "bidir_flow_e" + "|" + "bidir_flow_ion"
					+ "|" + "qual_time_est" + "|" + "flag_weak_event"
					+ "|" + "v_incr_sw" + "|" + "flag_upstream_shock"
					+ "|" + "v_mean_sw"   + "|" + "v_max_sw" 
					+ "|" + "b_mean"  + "|" + "flag_mag_cloud" + "|" + "flag_mc_list"
					+ "|" + "dst_min" + "|" + "flag_dst"
					+ "|" + "v_1au" + "|" + "time_lasco" + "|" + "flag_halo");
			
			
			String delims = " ";
			String[] tokens;

			File file = new File(inname);
		
			FileReader fr = new FileReader ( file );
			BufferedReader in = new BufferedReader( fr);
				
			String line;
			int len = 0;
			int i = 0;
			while ((line = in.readLine()) != null) {
					
				//skip lines at start of file 
				if (i>skiplines-1) {

					a_or_w = " ";
					q_weak = " ";
					ups_shock = " ";
					dst_flag = " ";
					mc_list = " ";

					tokens = line.split(delims);
					
					no_tokens = tokens.length;
					System.out.println("No. of tokens =" + no_tokens);
					
					if (tokens[0].substring(0, 2).equals("**")) {
						st_y = tokens[0].substring(2, 6);
					} else if (tokens[0].equals(" ")) {
						System.out.println("skip");;
					} else {

						// start time						
						y = Integer.parseInt(st_y);
						m = Integer.parseInt(tokens[0].substring(0, 2)) - 1;
						d = Integer.parseInt(tokens[0].substring(3, 5));
						h = Integer.parseInt(tokens[1].substring(0, 2));
						min = Integer.parseInt(tokens[1].substring(2, 4));
						s = 0;
						
						//extract flag to indicate either ACE or WIND time
						len = tokens[1].length();
						if (len > 4) {
						if (tokens[1].substring(4, 5).equals("("))
							a_or_w = tokens[1].substring(5, 6);
						} else
							a_or_w = " ";
					
						//*****
						System.out.println(y  + " " + m  + " " + d  + " " + h + " " + min);
										
						startDate = new GregorianCalendar(y, m, d, h, min, s);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

						//Icme start time
						y = Integer.parseInt(st_y);
						m = Integer.parseInt(tokens[2].substring(0, 2)) - 1;
						d = Integer.parseInt(tokens[2].substring(3, 5));
						h = Integer.parseInt(tokens[3].substring(0, 2));
						min = Integer.parseInt(tokens[3].substring(2, 4));
						s = 0;
					
						//*****
						System.out.println(y  + " " + m  + " " + d  + " " + h + " " + min);
										
			
						icmeStartDate = new GregorianCalendar(y, m, d, h, min, s);

						//Icme end time
						y = Integer.parseInt(st_y);
						m = Integer.parseInt(tokens[4].substring(0, 2)) - 1;
						d = Integer.parseInt(tokens[4].substring(3, 5));
						h = Integer.parseInt(tokens[5].substring(0, 2));
						min = Integer.parseInt(tokens[5].substring(2, 4));
						s = 0;
					
						//*****
						System.out.println(y  + " " + m  + " " + d  + " " + h + " " + min);
										
			
						icmeEndDate = new GregorianCalendar(y, m, d, h, min, s);
						
						//time offset at start of signature
						start_off = tokens[6].trim();
						if (start_off.equals("...") || start_off.equals("ns") || start_off.equals("nc")) {
							if (start_off.equals("...")) start_off_flag = "nd";
							else start_off_flag = start_off;
							start_off = "NaN";
						} else {
						    start_off_flag = " ";   
						}
					
						//time offset at end of signature
						end_off = tokens[7].trim();
						if (end_off.equals("...") || end_off.equals("ns") || end_off.equals("nc")) {
							if (end_off.equals("...")) end_off_flag = "nd";
							else end_off_flag = end_off;
							end_off = "NaN";
						} else {
						    end_off_flag = " ";   
						}
					
						//time offset at start of magnetic cloud
						start_mc = tokens[8].trim();
						if (start_mc.equals("...")) {
							if (start_mc.equals("...")) start_mc_flag = "nd";
							else start_mc_flag = start_mc;
							start_mc = "NaN";
						} else {
						    start_mc_flag = " ";   
						}
					
						//time offset at end of signature
						end_mc = tokens[9].trim();
						len = end_mc.length();
						if (end_mc.equals("...")) {
							end_mc_flag = "nd";
							end_mc = "NaN";
						} else if (end_mc.substring(len-1, len).equals(")"))
						{
							end_mc_flag = end_mc.substring(len-2, len-1);
							end_mc = end_mc.substring(0, len-3);
						} else {
						    end_mc_flag = " ";   
						}
					
						bidir_flow_e = tokens[10].trim();
						if (bidir_flow_e.equals("...")) bidir_flow_e = "nd";
						
						bidir_flow_ion = tokens[11].trim();
						if (bidir_flow_ion.equals("...")) bidir_flow_ion = "nd";
						
						qual_time_est = tokens[12].substring(0, 1);
						if (tokens[12].length() > 1) q_weak = tokens[12].substring(1, 2);
						else q_weak = " ";
						
						v_incr_sw = tokens[13].trim();
						len = tokens[13].trim().length();
						if (tokens[13].substring(len-1, len).equals("S")) {
							
							ups_shock = tokens[13].substring(len-1, len);
							v_incr_sw = tokens[13].substring(0, len-1);
						} else {
							
							ups_shock = " ";
							v_incr_sw = tokens[13].trim();
						}
						
						v_mean_sw = tokens[14].trim();
						
						v_max_sw = tokens[15].trim();
						
						b_intensity = tokens[16].trim();
						
						len = tokens[17].trim().length();
						if (len==1) {
							mag_cloud = tokens[17];
							mc_list = " ";
						} else {
							mag_cloud = tokens[17].substring(0, 1);
							mc_list = tokens[17].substring(1, 2);							
						}
						
						
						dst_min = tokens[18].trim();
						len = tokens[18].trim().length();
						if (dst_min.equals("...")) dst_min = "NaN";
						else if (dst_min.substring(len-1, len).equals("P") || dst_min.substring(len-1, len).equals("Q")) {
							
							dst_min = tokens[18].substring(0, len-1);
							dst_flag = tokens[18].substring(len-1, len);
						} else {
							dst_flag = " ";
						}
						
						halo = " ";
						v_1au = tokens[19].trim();
						if (v_1au.equals("...")) v_1au = "NaN";
						else if (v_1au.equals("dg")) {
							v_1au = "NaN";
							halo = "dg";
						}
						
						if (no_tokens==20) {
					
							pw.println(sdf.format( startDate.getTime() )
									+ "Z|" + sdf.format( icmeStartDate.getTime() )
									+ "Z|" + sdf.format( icmeEndDate.getTime() ) 
									+ "Z|" + a_or_w
									+ "|" + start_off + "|" + start_off_flag + "|" + end_off + "|" + end_off_flag
									+ "|" + start_mc + "|" + start_mc_flag + "|" + end_mc + "|" + end_mc_flag
									+ "|" + bidir_flow_e + "|" + bidir_flow_ion 
									+ "|" + qual_time_est + "|" + q_weak
									+ "|" + v_incr_sw + "|" + ups_shock + "|" + v_mean_sw + "|" + v_max_sw 
									+ "|" + b_intensity + "|" + mag_cloud + "|" + mc_list
									+ "|" + dst_min + "|" + dst_flag + "|" + v_1au + "|" + "NaN" + "|" + halo);
						} else {
							
							System.out.println(tokens[20]);
							
							if (tokens[19].trim().equals("dg") && tokens[20].trim().equals("dg")) {
								
								pw.println(sdf.format( startDate.getTime() )
										+ "Z|" + sdf.format( icmeStartDate.getTime() )
										+ "Z|" + sdf.format( icmeEndDate.getTime() )
										+ "Z|" + a_or_w
										+ "|" + start_off + "|" + start_off_flag + "|" + end_off + "|" + end_off_flag
										+ "|" + start_mc + "|" + start_mc_flag + "|" + end_mc + "|" + end_mc_flag
										+ "|" + bidir_flow_e + "|" + bidir_flow_ion 
										+ "|" + qual_time_est + "|" + q_weak
										+ "|" + v_incr_sw + "|" + ups_shock + "|" + v_mean_sw + "|" + v_max_sw 
										+ "|" + b_intensity + "|" + mag_cloud + "|" + mc_list
										+ "|" + dst_min + "|" + dst_flag + "|" + v_1au + "|" + "NaN" + "|" + "dg");							
								
							} else {
								//LASCO event time
								y = Integer.parseInt(st_y);
								m = Integer.parseInt(tokens[20].substring(0, 2)) - 1;
								d = Integer.parseInt(tokens[20].substring(3, 5));
								h = Integer.parseInt(tokens[21].substring(0, 2));
								min = Integer.parseInt(tokens[21].substring(2, 4));
								s = 0;
						
								//*****
								System.out.println(y  + " " + m  + " " + d  + " " + h + " " + min);
											
								lascoDate = new GregorianCalendar(y, m, d, h, min, s);
							
								if (no_tokens==22) halo = " ";
								else halo = tokens[22].trim();
								
								pw.println(sdf.format( startDate.getTime() )
										+ "Z|" + sdf.format( icmeStartDate.getTime() )
										+ "Z|" + sdf.format( icmeEndDate.getTime() )
										+ "Z|" + a_or_w
										+ "|" + start_off + "|" + start_off_flag + "|" + end_off + "|" + end_off_flag
										+ "|" + start_mc + "|" + start_mc_flag + "|" + end_mc + "|" + end_mc_flag
										+ "|" + bidir_flow_e + "|" + bidir_flow_ion 
										+ "|" + qual_time_est + "|" + q_weak
										+ "|" + v_incr_sw + "|" + ups_shock + "|" + v_mean_sw + "|" + v_max_sw 
										+ "|" + b_intensity + "|" + mag_cloud + "|" + mc_list
										+ "|" + dst_min + "|" + dst_flag + "|" + v_1au 
										+ "|" + sdf.format( lascoDate.getTime() ) + "Z|" + halo);	
							}
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
	static private Calendar icmeStartDate;
	static private Calendar icmeEndDate;
	static private Calendar lascoDate;



}
