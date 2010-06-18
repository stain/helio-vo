package eu.heliovo.shared.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.*;

public class IOUtilTest {

    
    /**
     * Make sure that line terminators are handled correctly.
     * @throws Exception if anything goes wrong.
     *
     */
	@Test
    public void testIoCopy() throws Exception {
        // first test with an empty buffer
        ByteArrayInputStream from = new ByteArrayInputStream(new byte[0]);        
        ByteArrayOutputStream to = new ByteArrayOutputStream();        
        IOUtil.ioCopy(from, to);
        assertEquals(to.size(), 0);
        
        // now we write something
        from = new ByteArrayInputStream(new byte[512]);
        to.reset();
        IOUtil.ioCopy(from, to);
        assertEquals(to.size(), 512);
        
        // now some special characters
        byte[] testArray = new byte[] {'t', 'e', 's', 't', '\n', '\0', '\t', ' ', '\n'};
        from = new ByteArrayInputStream(testArray);
        to.reset();
        IOUtil.ioCopy(from, to);
        assertEquals(to.size(), testArray.length);
        assertTrue(Arrays.equals(testArray, to.toByteArray()));
        
        // and finally a huge record
        from = new ByteArrayInputStream(new byte[(int)Math.pow(2, 20)]);
        to.reset();
        IOUtil.ioCopy(from, to);
        assertEquals(to.size(), (int)Math.pow(2,20));
    }

}
