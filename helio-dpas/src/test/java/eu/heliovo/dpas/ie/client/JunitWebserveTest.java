package  eu.heliovo.dpas.ie.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import eu.heliovo.dpas.ie.services.CommonDaoFactory;
import eu.heliovo.dpas.ie.services.common.utils.CommonUtils;
import eu.heliovo.dpas.ie.services.common.utils.VotableThreadAnalizer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.ws.Endpoint;

import org.apache.catalina.startup.Embedded;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import eu.heliovo.dpas.ie.services.common.dao.interfaces.ShortNameQueryDao;
import eu.heliovo.dpas.ie.services.common.transfer.CommonTO;
import eu.heliovo.dpas.ie.services.common.utils.InstanceHolders;

import eu.heliovo.dpas.ie.services.common.utils.ConnectionManager;

@SuppressWarnings("unused")
public class JunitWebserveTest {

	private final Logger logger = Logger.getLogger(this.getClass());

	// TODO: any reason why they are static?
	protected static Endpoint ep;
	protected static String address;
	protected static URL wsdlURL;
	protected static QName serviceName;
	protected static QName portName;
	protected static Embedded server;

	/**
	 * The executor is used to synchronize query analyzer threads
	 */
	private ExecutorService executor = Executors.newSingleThreadExecutor();

	/**
	 * Setup the test environment, i.e. hsqldb and log4j.
	 */
	@Ignore
	@Before
	public void setUp() throws Exception {
		File projectRoot = new File(".").getAbsoluteFile();

		// one-time initialization code
		ClassLoader loader = JunitWebserveTest.class.getClassLoader();
		String helioDbPath;
		
		helioDbPath = new File(projectRoot, "src/main/webapp/WEB-INF/").getCanonicalPath();
		//System.out.println(" : helio db file path  : "+helioDbPath);
		InstanceHolders.getInstance().setProperty("hsqldb.database.path", helioDbPath);
		
		// setup log4j
		Properties props = new Properties();
		InputStream configStream = JunitWebserveTest.class.getResourceAsStream("/log4j.properties");
		props.load(configStream);
		configStream.close();

		props.setProperty("log4j.appender.LOGFILE.File", helioDbPath + "/helio_query.log");
		LogManager.resetConfiguration();
		PropertyConfigurator.configure(props);
	}

	/**
	 * Clean up environment
	 * 
	 * @throws Exception
	 *             if anything goes wrong.
	 */
	@Ignore
	@After
	public void tearDown() throws Exception {
		InstanceHolders.getInstance().setProperty("hsqldb.database.path", null);
		Connection connection = ConnectionManager.getConnection();
	}

	/**
	 * Submit a full query to the server.
	 * 
	 * @throws Exception
	 *             if anything goes wrong.
	 */
	@Ignore
	@Test
	public void testHttpProviderDPASQuery() throws Exception {
		PipedReader pr = new PipedReader();
		PipedWriter pw = new PipedWriter(pr);
		CommonTO commonTO=new CommonTO();
		try {
			String[] arrStartTime = new String[1];
			String[] arrEndTime = new String[1];
			// Start time
			arrStartTime[0] = "2009-10-20T20:30:56";
			arrEndTime[0] = "2009-11-20T20:30:56";
			String[] arr = new String[1];
			arr[0] = "PROBA2__LYRA";
			commonTO.setPrintWriter(pw);
		    commonTO.setBufferOutput(new BufferedWriter(pw) );    
		    commonTO.setInstruments(arr);
		    commonTO.setStartTimes(arrStartTime);
		    commonTO.setStopTimes(arrEndTime);
		    runJob(commonTO,pr);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			if(pr!=null)
			{
				pr.close();
				pr=null;
			}
			//
			if(pw!=null)
			{
				pw.close();
				pw=null;
			}
			
		}
	}
	
	@Ignore
	@Test
	public void testftpProviderDPASQuery() throws Exception {
		PipedReader pr = new PipedReader();
		PipedWriter pw = new PipedWriter(pr);
		CommonTO commonTO=new CommonTO();
		try {
			String[] arrStartTime = new String[1];
			String[] arrEndTime = new String[1];
			// Start time
			arrStartTime[0] = "2009-10-20T20:30:56";
			arrEndTime[0] = "2009-11-20T20:30:56";
			String[] arr = new String[1];
			arr[0] = "NANC__RHELIO";
			commonTO.setPrintWriter(pw);
		    commonTO.setBufferOutput(new BufferedWriter(pw) );    
		    commonTO.setInstruments(arr);
		    commonTO.setStartTimes(arrStartTime);
		    commonTO.setStopTimes(arrEndTime);
		    runJob(commonTO,pr);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			if(pr!=null)
			{
				pr.close();
				pr=null;
			}
			//
			if(pw!=null)
			{
				pw.close();
				pw=null;
			}
			
		}
	}
	
	@Test
	public void testVsoProviderDPASQuery() throws Exception {
		PipedReader pr = new PipedReader();
		PipedWriter pw = new PipedWriter(pr);
		CommonTO commonTO=new CommonTO();
		try {
			String[] arrStartTime = new String[1];
			String[] arrEndTime = new String[1];
			// Start time
			arrStartTime[0] = "2009-10-20T20:30:56";
			arrEndTime[0] = "2009-11-20T20:30:56";
			String[] arr = new String[1];
			arr[0] = "SOHO__LASCO";
			commonTO.setPrintWriter(pw);
		    commonTO.setBufferOutput(new BufferedWriter(pw) );    
		    commonTO.setInstruments(arr);
		    commonTO.setStartTimes(arrStartTime);
		    commonTO.setStopTimes(arrEndTime);
		    runJob(commonTO,pr);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			if(pr!=null)
			{
				pr.close();
				pr=null;
			}
			//
			if(pw!=null)
			{
				pw.close();
				pw=null;
			}
			
		}
	}
	
	@Ignore
	@Test
	public void testPatProviderDPASQuery() throws Exception {
		PipedReader pr = new PipedReader();
		PipedWriter pw = new PipedWriter(pr);
		CommonTO commonTO=new CommonTO();
		try {
			String[] arrStartTime = new String[1];
			String[] arrEndTime = new String[1];
			// Start time
			arrStartTime[0] = "2009-10-20T20:30:56";
			arrEndTime[0] = "2009-11-20T20:30:56";
			String[] arr = new String[1];
			arr[0] = "pat";
			commonTO.setPrintWriter(pw);
		    commonTO.setBufferOutput(new BufferedWriter(pw) );    
		    commonTO.setInstruments(arr);
		    commonTO.setStartTimes(arrStartTime);
		    commonTO.setStopTimes(arrEndTime);
		    runJob(commonTO,pr);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(pr!=null)
			{
				pr.close();
				pr=null;
			}
			//
			if(pw!=null)
			{
				pw.close();
				pw=null;
			}
			
		}
	}
	
	private String runJob(CommonTO comTO, PipedReader pr) throws Exception {
		VotableThreadAnalizer queryThreadAnalyzer = new VotableThreadAnalizer(comTO);
		Future<?> job = executor.submit(queryThreadAnalyzer);
		// try to get result from db for 2 seconds
		job.get(20, TimeUnit.MINUTES);
		assertTrue(job.isDone());
		// Print reader
		// System.out.println("Printing VOTable ....");

		String result = printPrintReader(pr);
		System.out.println("  :  result : "+result);
		assertNotNull(result);
		// TODO: test if result is valid
		return result;
	}
	
	/**
	 * Convert read data to string.
	 * 
	 * @param reader
	 *            the reader to read from
	 * @throws IOException
	 *             if anything goes wrong.
	 */
	private String printPrintReader(PipedReader reader) throws IOException {
		StringWriter writer = new StringWriter();
		try {
			while (reader.ready()) {
				writer.write(reader.read());
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return writer.toString();
	}

	
	
}
