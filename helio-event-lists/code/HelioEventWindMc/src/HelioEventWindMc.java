
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;     
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;



public class HelioEventWindMc {
	
	public static void main (String[] args)
	{
		String code;
		
		String s_y;
		String s_m;
		String s_d;
		String s_h;
		String s_doy;
		
		int y;
		int d;
		int m;
		int h;
		int min;
		int s;
		
		
		String e_m;
		String e_d;
		String e_h;
		String e_doy;
		
		String qual;
		
		// directory containing the event list
		String dir  = "C:\\Development\\HELIO\\Event_lists\\";
		
		// input fie name
		String inname = dir + "Magnetic_clouds.txt";

		// args[2] - lines to skip at start of list
		int skiplines = 1;
		
		// name of output file 
		String outname = dir + "WIND_IMF_MAG_CLOUD.txt";
		File out = new File(outname);
	
		try {
			FileWriter fw = new FileWriter( out );
			PrintWriter pw = new PrintWriter( fw );
			
			pw.println("START TIME" + " | " + "START DOY" + " | " + "END TIME" + " | " + "END DOY"
					+ " | " + "CODE NO." + " | " + "QUALITY");
			
			String delims = "[,]";
			String[] tokens;

			File file = new File(inname);
		
			FileReader fr = new FileReader ( file );
			BufferedReader in = new BufferedReader( fr);
				
			String line;
			int i = 0;
			while ((line = in.readLine()) != null) {
					
				//skip lines at start of file 
				if (i>skiplines-1) {

					tokens = line.split(delims);
					code = tokens[0];
					
					s_y = tokens[1];
					s_m = tokens[2].toUpperCase();
					s_d = tokens[3];
					s_doy = tokens[4];
					s_h = tokens[5];
				
					e_m = tokens[6].toUpperCase();
					e_d = tokens[7];
					e_doy = tokens[8];
					e_h = tokens[9];
					
					qual = tokens[10];
					
					// start date
					y = Integer.parseInt(s_y);
					d = Integer.parseInt(s_d);
					
					//years 
					if (y>20) y = 1900 + y;
					else y = 2000 + y;
					
					//months
					m = Enum.valueOf(Months.class, s_m).ordinal()+1;
					
					// hms
					double hrs = Double.parseDouble(s_h);
					h = (int) hrs;
					double fmin = (hrs - h)*60.0 + 0.0001;  // add 0.0001 to avoid rounding errors 
					min = (int) fmin;
					s = 0;
					
					System.out.println( hrs + " " + h + " " + fmin + " " + min);
					
					start_date = new Date(y, m, d, h, min, s);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        
					// end date
					// day
					d = Integer.parseInt(e_d);

					//months
					m = Enum.valueOf(Months.class, e_m).ordinal()+1;
					
					// hms
					hrs = Double.parseDouble(e_h);
					h = (int) hrs;
					fmin = (hrs - h)*60.0 + 0.0001;  // add 0.0001 to avoid rounding errors 
					min = (int) fmin;
					s = 0;
					
					end_date = new Date(y, m, d, h, min, s);

			
					//System.out.println(sdf.format( start_date.toJavaDate() ) + "Z" + " | " +
					//		s_doy + " | " + sdf.format( end_date.toJavaDate() ) + "Z" + " | " + 
					//		e_doy + " | " + code + " | " + qual);
					pw.println(sdf.format( start_date.toJavaDate() ) + "Z" + " | " +
							s_doy + " | " + sdf.format( end_date.toJavaDate() ) + "Z" + " | " + 
							e_doy + " | " + code + " | " + qual);
        
				}
				
				// line counter
				i++;
		
			}
			
			fw.close();
			
		} catch (Exception e) {System.out.println(e.getMessage());}
}
	
	static private Date start_date;
	static private Date end_date;
}
