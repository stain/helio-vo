package eu.heliovo.shared.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializationUtils 
{
    static public  boolean isSerializable(Object obj)
    {
            boolean result  =       true;
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
                    result  =       false;
            }
            return result;
    }
    
    static public byte[] serialize(Object obj) throws IOException
    {
            ByteArrayOutputStream   b_out = new ByteArrayOutputStream();
            ObjectOutputStream      o_out = new ObjectOutputStream(b_out);

            o_out.writeObject(obj);
            o_out.flush();

            return b_out.toByteArray();
    }

    public Object deserialize(byte[] input) throws IOException,
                    ClassNotFoundException
    {
            ByteArrayInputStream b_in = new ByteArrayInputStream(input);
            ObjectInputStream o_in = new ObjectInputStream(b_in);

            return o_in.readObject();
    }

    
    /** Read the object from Base64 string. */
    public static Object fromString( String s ) throws IOException ,
                                                        ClassNotFoundException {
        byte [] data = Base64Coder.decode( s );
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
    }

    /** Write the object to a Base64 string. */
    public static String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return new String( Base64Coder.encode( baos.toByteArray() ) );
    }
}
