package eu.heliovo.shared.common.utilities;

import java.io.*;


public class FileUtilities
{
	public String readFromFile(String fileName) throws FileUtilitiesException 
	{
		String fileContent = null;
		try
		{
			FileInputStream file = new FileInputStream(fileName);
			DataInputStream in = new DataInputStream(file);
			byte[] b = new byte[in.available()];
			in.readFully(b);
			in.close();
			fileContent = new String(b, 0, b.length, "Cp850");
		}
		catch (Exception exc)
		{
			System.out.println("*****************************************************");
			System.out.println("* !! utilities.FileUtilities.readFromFile - Error !! *");
			System.out.println("_____________________________________________________");
			exc.printStackTrace();
			System.out.println("*****************************************************");
			throw new FileUtilitiesException();
		}
		return fileContent;
	}

	public void writeToFile(String fileName, String fileContent) throws FileUtilitiesException 
	{
		try
		{
			File file = new File(fileName);
			// System.out.println("* File is " + file.toString() + " *");
			File path = new File(file.getParent());
			// System.out.println("* Path is " + path.toString() + " *");
			path.mkdirs();
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
			out.write(fileContent);
			out.close();
		}
		catch (Exception exc)
		{
			System.out.println("*****************************************************");
			System.out.println("* !! utilities.FileUtilities.writeToFile - Error !! *");
			System.out.println("_____________________________________________________");
			exc.printStackTrace();
			System.out.println("*****************************************************");
			throw new FileUtilitiesException();
		}
		return;
	}

	public void writeToFile(String fileName, char[] fileContent) throws FileUtilitiesException 
	{
		try
		{
			File file = new File(fileName);
			// System.out.println("* File is " + file.toString() + " *");
			File path = new File(file.getParent());
			// System.out.println("* Path is " + path.toString() + " *");
			path.mkdirs();
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
			out.write(fileContent);
			out.close();
		}
		catch (Exception exc)
		{
			System.out.println("*****************************************************");
			System.out.println("* !! utilities.FileUtilities.writeToFile - Error !! *");
			System.out.println("_____________________________________________________");
			exc.printStackTrace();
			System.out.println("*****************************************************");
			throw new FileUtilitiesException();
		}
		return;
	}

	public boolean isFilePresent(String filePosition)
	{
		boolean isPresent = false;

		File file = new File(filePosition);
		isPresent = file.isFile();

		return isPresent;

	}

	public String getFileName(String filePosition)
	{
		File file = new File(filePosition);
		return file.getName();
	}

	public String getFilePath(String filePosition)
	{
		File file = new File(filePosition);
		return file.getParent();
	}

	public long getFileSize(String filePosition)
	{
		File file = new File(filePosition);
		return file.length();
	}

	public void appendToFile(String fileName, String fileContent) throws FileUtilitiesException 
	{
		try
		{
		    boolean append = true;
		    FileWriter fw = new FileWriter(fileName,append);
		    fw.write(fileContent);//appends the string to the file
		    fw.close();
		}

		catch(IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
			throw new FileUtilitiesException();
		}		
		return;
	}

}