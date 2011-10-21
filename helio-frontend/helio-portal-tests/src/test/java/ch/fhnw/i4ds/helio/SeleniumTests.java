package ch.fhnw.i4ds.helio;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class SeleniumTests {

	// private WebDriver driver;
	static String TAG = "Abstract class: ";
	// For the pupops
	public static final String OK_BUTTON = "//div[@class='ui-dialog-buttonset']/button/span[text()='Ok']";
	public static final String DISPLAY_BUTTON = "//div[@id='result_button']/span"; // displayButton
	public static final String WAIT_FOR_RESULT = "//div[@id='displayableResult']/div[@id='tables']";
	public static final String BASE_URL = "http://helio.i4ds.technik.fhnw.ch/Helio-dev/prototype/explorer";

	protected WebDriver driver;
	protected StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver = new FirefoxDriver();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
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
	public static WebDriver waitXpath(String path, WebDriver driver) {

		// wait 60 seconds
		for (int i = 0; i < 600; i++) {
			assert i < 600; // only in dibug
			try {
				// System.out.println(tag + "wait. element: "
				// + driver.findElement(By.xpath(path)).toString()
				// + "; xpath: " + path);

				if (driver.findElement(By.xpath(path)).isDisplayed()) {
					return driver;
				}
			} catch (Exception e) {
				// System.out.println(e.toString());
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {

				System.out.println(TAG + "WaitXpath error: thread.sleep");
				e.printStackTrace();
			}
		}

		System.out.println(TAG + "Element cant be find " + path);
		assertTrue(TAG + "Element cant be find " + path, true);
		throw new RuntimeException();
	}

	/**
	 * Try to press a Button until
	 * 
	 * @param path
	 *            Xpath of the Button to press
	 * @param untilFound
	 *            Xpath of objekt to find.
	 */
	public static WebDriver navigateTO(String path, String untilFound,
			WebDriver driver) {

		for (int second = 0;; second++) {
			if (second >= 600) {
				System.out.println(TAG + "navigatTO: Element cant be find "
						+ path);
				assert second >= 600;
				break;
			}
			try {
				driver.findElement(By.xpath(path)).click();

			} catch (Exception e) {
				// e.printStackTrace();
			}
			try {
				if (driver.findElement(By.xpath(untilFound)).isDisplayed()) {
					return driver;
					// break;
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println(TAG + "navigateTO error");
				e.printStackTrace();
			}
		}
		return driver;
	}
}
