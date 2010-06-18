package eu.heliovo.shared.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtil {
    
    /**
     * Size for the copy buffer.
     */
    private static final int BUFF_SIZE = 8192;
    
    
    /**
     * Copy from inputStream to outputStream using a byte[]-buffer. The input and output stream
     * will be closed after copying.
     * @param from InputStream to read data from.
     * @param to OutputStream to write data to.
     * @throws IOException if anything goes wrong.
     */
    public static void ioCopy(InputStream from, OutputStream to) throws IOException {
        ioCopy(from, to, false, false);
    }

    /**
     * Copy from inputStream to outputStream using a byte[]-buffer. 
     * @param from InputStream to read data from.
     * @param to OutputStream to write data to.
     * @param keepOpenFrom Keep the from stream open after copying.
     * @param keepOpenTo Keep the to stream open after copying.
     * @throws IOException if anything goes wrong.
     */
    public static void ioCopy(InputStream from, OutputStream to, boolean keepOpenFrom, boolean keepOpenTo) throws IOException {
       final byte[] buffer = new byte[BUFF_SIZE];
       try {
          while (true) {
             synchronized (buffer) {
                int amountRead = from.read(buffer);
                if (amountRead == -1) {
                   break;
                }
                to.write(buffer, 0, amountRead); 
             }
          } 
       } finally {
          if (!keepOpenFrom && from != null) {
             from.close();
          }
          if (!keepOpenTo && to != null) {
             to.close();
          }
       }
    }

}
