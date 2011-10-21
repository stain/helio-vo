package ch.fhnw.i4ds.helio;

import static org.junit.Assert.fail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.Selenium;

public class EventSelectionFF4Test extends EventSelectionTest {
	private WebDriver wDriver;
	private StringBuffer verificationErrors = new StringBuffer();
	Selenium selenium;

	// @Before
	@Override
	public void setUp() throws Exception {

		// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wDriver = new FirefoxDriver();
		selenium = new WebDriverBackedSelenium(wDriver, BASE_URL);
		driver = ((WebDriverBackedSelenium) selenium).getWrappedDriver();

	}

	// @After
	@Override
	public void tearDown() throws Exception {
		selenium.stop();
		// driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

}
