package eu.heliovo.shared.common.utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializationUtilities 
{
    public  boolean isSerializable(Object obj)
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
    
    public byte[] serialize(Object obj) throws IOException
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
    public Object fromString( String s ) throws IOException ,
                                                        ClassNotFoundException {
        byte [] data = Base64Coder.decode( s );
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
    }

    /** Write the object to a Base64 string. */
    public String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return new String( Base64Coder.encode( baos.toByteArray() ) );
    }

    
//    public String objectToString(Object obj) throws IOException
//    {
//            ByteArrayOutputStream   b_out = new ByteArrayOutputStream();
//            ObjectOutputStream      o_out = new ObjectOutputStream(b_out);
//
//            o_out.writeObject(obj);
//            o_out.flush();
//
//            return b_out.toString();
//    }
//
//    public Object stringToObject(String input) throws IOException,
//                    ClassNotFoundException
//    {
//    	System.out.println(input);
//    	System.out.println(input.getBytes().length);
//    	
//        ByteArrayInputStream b_in = new ByteArrayInputStream(input.getBytes());
//        ObjectInputStream o_in = new ObjectInputStream(b_in);
//
//    	System.out.println(o_in.readObject());
//    	System.out.println(o_in.readObject().getClass());
//        
//        return o_in.readObject();
//    }

}
