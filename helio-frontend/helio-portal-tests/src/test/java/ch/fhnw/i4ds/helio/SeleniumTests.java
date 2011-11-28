package ch.fhnw.i4ds.helio;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import java.util.Calendar;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import ch.fhnw.i4ds.helio.browser.DownloadManager;

/**
 * Abstract Class with Setup and teardown for al Tests. Other important function
 * and variable are stored hear.
 * 
 * @author Lavanchy
 * 
 *         First download and run this project, only required the first time:
 *         https://helio-vo.svn.sourceforge.net/svnroot/helio-vo/sandbox/
 *         marcosoldati/InstanzPortableDriver/
 * 
 *         created 25.11.11
 */
public abstract class SeleniumTests {

	// private WebDriver driver;
	protected String tag = "Abstract class: ";

	public String getTAG() {
		return tag;
	}

	public void setTAG(String tAG) {
		tag = tAG;
	}

	protected static Logger logger = Logger.getLogger(SeleniumTests.class);

	// For the pupops
	public static final String OK_BUTTON = "//div[@class='ui-dialog-buttonset']/button/span[text()='Ok']";
	public static final String DISPLAY_BUTTON = "//div[@id='result_button']/span"; // displayButton
	public static final String WAIT_FOR_RESULT = "//div[@id='displayableResult']/div[@id='tables']";
	public static final String BASE_URL = "http://helio.i4ds.technik.fhnw.ch/Helio-dev/prototype/explorer";
	// public static final String BASE_URL =
	// "http://helio2.cs.technik.fhnw.ch:8080/Helio/prototype/explorer";

	protected WebDriver driver;
	protected StringBuffer verificationErrors = new StringBuffer();

	// to calculate duration.
	protected long start = 0;

	@Before
	public void setUp() throws Exception {
		DownloadManager downloadManager = new DownloadManager();
		String portableBrowser = downloadManager.getPortableBrowser();
		System.out.println("Portal browser location:"
				+ portableBrowser.toString());
		// System.getenv("TEMP");
		System.setProperty("webdriver.firefox.bin", portableBrowser);
		// "C:/Temp/Portable_Firefox_4.0/Firefox/firefox.exe"); //TODO variable
		// from d manager
		//
		// portable
		// browser
		// (mabe
		// over
		// maven)
		// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver = new FirefoxDriver();

		DOMConfigurator.configure("src/test/java/log4j.xml");
		// logger.info("test is redy with FF"); //calls from instances
		Calendar calendar = Calendar.getInstance();
		start = calendar.getTimeInMillis();

	}

	@After
	public void tearDown() throws Exception {
		int min = 0;
		int s = 0;
		long duration = (Calendar.getInstance().getTimeInMillis() - start) / 1000;

		if (duration >= 60) {
			min = (int) (duration / 60);
			s = (int) (duration % 60);
		} else {
			s = (int) duration;
		}

		logger.info("duration: " + min + "min " + s + "s");
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			logger.error("verificationErrorString"
					+ verificationErrorString.toString());
			fail(verificationErrorString);
		}
	}

	protected boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * wait max 1 min until Element is present.
	 * 
	 * @param path
	 *            Path to wait
	 * @param elementCase
	 *            case 1: is Displayed. case 2: is Enabled. default 1.
	 */
	public boolean waitXpath(String path, WebDriver driver) {

		for (int i = 0; i < 600; i++) {
			assert i < 600; // only in dibug
			try {

				if (driver.findElement(By.xpath(path)).isDisplayed()) {
					return true;
				}
			} catch (Exception e) { // Don’t do anything
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.error(tag + "WaitXpath error: thread.sleep "
						+ e.toString());
			}
		}
		logger.error(tag + "Element cant be find " + path);
		assert true;
		return false;
	}

	/**
	 * Try to press a Button until
	 * 
	 * @param path
	 *            Xpath of the Button to press
	 * @param untilFound
	 *            Xpath of objekt to find.
	 */
	public WebDriver navigateTO(String path, String untilFound,
			final WebDriver driver) {

		for (int second = 0;; second++) {
			if (second >= 600) {
				logger.trace(tag + "navigatTO: Element cant be find " + path);
				break;
			}
			try {
				clickElementXpath(driver, path);

			} catch (Exception e) {
				// Don’t do anything
			}
			try {
				if (driver.findElement(By.xpath(untilFound)).isDisplayed()) {
					return driver;
				}
			} catch (Exception e) {
				// Don’t do anything
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.error(tag + "navigateTO error: " + e.toString());
			}
		}
		return driver;
	}

	/**
	 * Junit AssertNotNull. Control there is an element.
	 * 
	 * @param driver
	 * @param xpath
	 */
	public static void clickElementXpath(WebDriver driver, String xpath) {
		WebElement eventButton = driver.findElement(By.xpath(xpath));
		assertNotNull(eventButton);
		eventButton.click();
	}

	/**
	 * Junit AssertNotNull. Control there is an element.
	 * 
	 * @param driver
	 * @param id
	 */
	public static void ClickElenemtId(WebDriver driver, String id) {
		WebElement eventButton = driver.findElement(By.id(id));
		assertNotNull(eventButton);
		eventButton.click();
	}

	/**
	 * Junit AssertNotNull. Control there is an element.
	 * 
	 * @param driver
	 * @param cssSelector
	 */
	public static void ClickElenemtCss(WebDriver driver, String cssSelector) {
		WebElement eventButton = driver
				.findElement(By.cssSelector(cssSelector));
		assertNotNull(eventButton);
		eventButton.click();
	}
}
