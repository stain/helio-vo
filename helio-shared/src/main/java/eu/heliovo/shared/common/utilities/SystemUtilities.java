package eu.heliovo.shared.common.utilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SystemUtilities
{
	public String getHostname()
	{
		return sysExec("/bin/hostname");
	}

	public String getLocation()
	{
		return sysExec("/bin/pwd");
	}

	public String getLocalTime()
	{
		return sysExec("/bin/date");
	}

//	public String	execTest(String cmd)
//	{
//		String	result	=	"";
//        try
//        {            
//            Runtime rt = Runtime.getRuntime();
//            Process proc = rt.exec(cmd);
//            /*
//             * Error Stream
//             */
//            InputStream 		stderr = proc.getErrorStream();
//            InputStreamReader 	isr = new InputStreamReader(stderr);
//            BufferedReader 		br = new BufferedReader(isr);
//			/*
//			 * Output Stream
//			 */
//			InputStream inputstream = proc.getInputStream();
//			InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
//			BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
//			/*
//			 * Read the command output
//			 */
//            String line = null;
//			while ((line = bufferedreader.readLine()) != null)
//			{
//				result	+=	line;
//				result  += "\n";
//			}
//            
//            System.out.println("");
//            while ( (line = br.readLine()) != null)
//            System.out.println(line);
//            System.out.println("");
//            int exitVal = proc.waitFor();
//            System.out.println("Process exitValue: " + exitVal);
//                      
//        } catch (Throwable t)
//          {
//            t.printStackTrace();
//          }
//
//		return null;
//	}
	
	
	public String sysExec(String command)
	{
//		System.out.println("Executing " + command);
		
		String result = new String();

		try
		{
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec(command);
			/*
			 * Put a BufferedReader on the command output
			 */
			InputStream inputstream = proc.getInputStream();
			InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
			BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
			/*
			 * Read the command output
			 */
			String line;
			while ((line = bufferedreader.readLine()) != null)
			{
				result	+=	line;
				result  += "\n";
			}
			/*
			 * Check for command failure
			 */
			try
			{
				if (proc.waitFor() != 0)
				{
					System.err.println("exit value = " + proc.exitValue());
				}
			}
			catch (InterruptedException e)
			{
				System.err.println(e);
				throw new SystemUtilitiesException();
			}			
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
		return result;
	}
}
