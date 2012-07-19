package eu.heliovo.shared.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SystemUtils
{
	static public String getHostname()
	{
		return sysExec("/bin/hostname");
	}

	static public String getLocation()
	{
		return sysExec("/bin/pwd");
	}

	static public String getLocalTime()
	{
		return sysExec("/bin/date");
	}
	
	static public String sysExec(String command)
	{		
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
