package ch.fhnw.i4ds.helio.chrome;

//import java.io.File;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebDriverBackedSelenium;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
import ch.fhnw.i4ds.helio.EventSelectionTest;
import com.thoughtworks.selenium.Selenium;

//import org.openqa.selenium.i


/**
 * This Test Actually don’t work!
 */
public class EventSelectionChromeTest extends EventSelectionTest {
	//private WebDriver wDriver;
	//private StringBuffer verificationErrors = new StringBuffer();
	Selenium selenium;

	// @Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		tag= "Chrome";
//		 service = new ChromeDriverService.Builder()
//        .usingChromeDriverExecutable(new File("C:\\Temp\\portable\\GoogleChromePortable"))
//        .usingAnyFreePort()
//        .build();
//		service.start();
//		System.setProperty("webdriver.chrome.driver", "C:\\Temp\\portable\\GoogleChromePortable\\GoogleChromePortable.exe");

		
		// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//		wDriver = new FirefoxDriver();
//		selenium = new WebDriverBackedSelenium(wDriver, BASE_URL);
//		driver = ((WebDriverBackedSelenium) selenium).getWrappedDriver();
		
		//TODO not a coment 
		//driver = new ChromeDriver();
	logger.info("EventSelection chrome.");
	logger.error("This Test Actually don’t work!");
	}
	
	//TODO deleat
	 @Override
	public void testEventSelection() throws Exception {}
	
//	
//	@Override
//	public void tearDown() throws Exception {
//		selenium.stop();
//		// driver.quit();
//		String verificationErrorString = verificationErrors.toString();
//		if (!"".equals(verificationErrorString)) {
//			fail(verificationErrorString);
//		}
//	}

}
