package eu.heliovo.dpas.ie.classad;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationUtilities
{
	public	boolean	isSerializable(Object obj)
	{
		boolean	result	=	true;
		ByteArrayOutputStream b_out = new ByteArrayOutputStream();
		ObjectOutputStream o_out;
		try
		{
			o_out = new ObjectOutputStream(b_out);
			o_out.writeObject(obj);
			o_out.flush();	
		}
		catch (IOException e)
		{
			result	=	false;
		}
		return result;
	}
	
	public byte[] serializeObject(Object obj) throws IOException
	{
		ByteArrayOutputStream 	b_out = new ByteArrayOutputStream();
		ObjectOutputStream 		o_out = new ObjectOutputStream(b_out);

		o_out.writeObject(obj);
		o_out.flush();

		return b_out.toByteArray();
	}

	public Object deserializeObject(byte[] input) throws IOException,
			ClassNotFoundException
	{
		ByteArrayInputStream b_in = new ByteArrayInputStream(input);
		ObjectInputStream o_in = new ObjectInputStream(b_in);

		return o_in.readObject();
	}

	public	void writeTo(Object o, String file)
	{
		try
		{
			FileOutputStream fout = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(o);
			oos.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public	Object readFrom(String file)
	{
		Object	result	=	null;
		try
		{
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fin);
			result = ois.readObject();
			ois.close();	
		}		
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
}
