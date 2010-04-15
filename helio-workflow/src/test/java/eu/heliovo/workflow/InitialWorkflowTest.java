package eu.heliovo.workflow;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import eu.heliovo.workflow.workflows.InitialWorkflow;
import junit.framework.TestCase;

/**
 * Some simple unit tests for the initial workflow
 */
public class InitialWorkflowTest extends TestCase
{
    public void testForResults() throws Exception
    {
      ByteArrayOutputStream baos=new ByteArrayOutputStream();
      InitialWorkflow.runInitialWorkflow(new PrintWriter(baos),Arrays.asList(new String[]{"SMM__GRS"}),"2005-01-01 00:00:00","2005-01-11 00:00:00","","");
      assertTrue(baos.toByteArray().length>4096);
    }
    
    public void testForNoResults() throws Exception
    {
      ByteArrayOutputStream baos=new ByteArrayOutputStream();
      InitialWorkflow.runInitialWorkflow(new PrintWriter(baos),Arrays.asList(new String[]{"SMM__GRS"}),"1955-11-05 01:19:00","1955-11-12 06:00:00","","");
      assertTrue(baos.toByteArray().length<4096);
    }
}
