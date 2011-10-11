package ch.fhnw.i4ds.helio;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import javax.swing.text.AbstractDocument.ElementEdit;

import org.hamcrest.core.IsEqual;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AdRangeTest {

	private WebDriver driver;
	private String baseUrl = "";
	private StringBuffer verificationErrors = new StringBuffer();
	String tag = "AdRangeTest: ";

	// Ofen used Path for elements
	private String waitForResult = "//div[@id='displayableResult']/div[@id='tables']";
	private String okButton = "//div[@class='ui-dialog-buttonset']/button/span[text()='Ok']";// For
																								// the
																								// pupops

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		baseUrl = "http://helio.i4ds.technik.fhnw.ch/Helio-dev/prototype/explorer";
		driver = new FirefoxDriver();
	}

	/**
	 * Add and delete a Tim range.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAdRange() throws Exception {

		driver.get(baseUrl);
		waitXpath("//div[@id='task_searchEvents']");
		driver.findElement(By.xpath("//div[@id='task_searchEvents']")).click();// ("#task_searchEvents > span.ui-button-text"))

		navigateTO("//div[@id='task_searchEvents']",
				"//h1[text()='Search Events']");

		// Open time selection
		driver.findElement(By.id("time_button")).click();
		driver.findElement(By.id("minDate1")).clear();
		driver.findElement(By.id("minDate1")).sendKeys("2004-11-01T17:00:00");
		driver.findElement(By.id("maxDate1")).clear();
		driver.findElement(By.id("maxDate1")).sendKeys("2004-11-01T18:30:00");
		driver.findElement(
				By.xpath("//div[@id='dialog-message']/table/tbody/tr[2]/td[2]"))
				.click();
		driver.findElement(
				By.cssSelector("#input_time_range_button > span.ui-button-text"))
				.click();
		driver.findElement(By.id("minDate2")).clear();
		driver.findElement(By.id("minDate2")).sendKeys("2002-11-01T17:00:00");
		driver.findElement(By.id("maxDate2")).clear();
		driver.findElement(By.id("maxDate2")).sendKeys("2002-11-01T18:30:00");
		driver.findElement(
				By.xpath("//tr[@id='input_time_range_2']/td[5]/div/span"))
				.click();
		driver.findElement(By.xpath(okButton)).click();
		try {
			assertTrue(!isElementPresent(By
					.xpath("//td[@id='time_area']/table/tbody/tr[2]/td[2]")));

		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}

	/**
	 * Add 2 Tim ranges.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAd3Range() throws Exception {

		driver.get(baseUrl);
		waitXpath("//div[@id='task_searchEvents']");
		driver.findElement(By.xpath("//div[@id='task_searchEvents']")).click();// ("#task_searchEvents > span.ui-button-text"))

		navigateTO("//div[@id='task_searchEvents']",
				"//h1[text()='Search Events']");

		// Open time selection
		driver.findElement(By.id("time_button")).click();
		driver.findElement(By.id("minDate1")).clear();
		driver.findElement(By.id("minDate1")).sendKeys("2004-11-01T17:00:00");
		driver.findElement(By.id("maxDate1")).clear();
		driver.findElement(By.id("maxDate1")).sendKeys("2004-11-01T18:30:00");
		driver.findElement(
				By.xpath("//div[@id='dialog-message']/table/tbody/tr[2]/td[2]"))
				.click();
		driver.findElement(
				By.cssSelector("#input_time_range_button > span.ui-button-text"))
				.click();
		driver.findElement(By.id("minDate2")).clear();
		driver.findElement(By.id("minDate2")).sendKeys("2002-11-01T17:00:00");
		driver.findElement(By.id("maxDate2")).clear();
		driver.findElement(By.id("maxDate2")).sendKeys("2002-11-01T18:30:00");

		driver.findElement(
				By.cssSelector("#input_time_range_button > span.ui-button-text"))
				.click();
		driver.findElement(By.id("minDate3")).clear();
		driver.findElement(By.id("minDate3")).sendKeys("2002-11-01T17:13:21");
		driver.findElement(By.id("maxDate3")).clear();
		driver.findElement(By.id("maxDate3")).sendKeys("2002-11-01T18:30:00");

		driver.findElement(By.xpath(okButton)).click();
		try {
			// assertTrue(!isElementPresent(By
			// .xpath("//td[@id='time_area']/table/tbody/tr[2]/td[2]")));
			assertTrue(isElementPresent(By
					.xpath("//td[@id='time_area']/table/tbody/tr[3]/td[text()='2002-11-01T17:13:21']")));
//			ssertTrue(isElementPresent(By
//					.xpath("//td[@id='time_area']/table/tbody/tr[3]/td[text()='2002-11-01T18:30:00']")));

		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}
	
	
	/**
	 * Catch wrong entrees.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTxtRange() throws Exception {

		driver.get(baseUrl);
		waitXpath("//div[@id='task_searchEvents']");
		driver.findElement(By.xpath("//div[@id='task_searchEvents']")).click();// ("#task_searchEvents > span.ui-button-text"))

		navigateTO("//div[@id='task_searchEvents']",
				"//h1[text()='Search Events']");

		// Open time selection
		driver.findElement(By.id("time_button")).click();
		driver.findElement(By.id("minDate1")).clear();
		driver.findElement(By.id("minDate1")).sendKeys("lol"); 
		driver.findElement(By.id("maxDate1")).clear();
		driver.findElement(By.id("maxDate1")).sendKeys("2004-11-01T18:30:00");
		
		driver.findElement(By.xpath(okButton)).click();
		try {
			assertTrue(!isElementPresent(By
					.xpath("//td[@id='time_area']/table/tbody/tr[1]/td[2]")));

		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}

	
	/**
	 * Catch wrong entrees.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testReversDate () throws Exception {

		driver.get(baseUrl);
		waitXpath("//div[@id='task_searchEvents']");
		driver.findElement(By.xpath("//div[@id='task_searchEvents']")).click();// ("#task_searchEvents > span.ui-button-text"))

		navigateTO("//div[@id='task_searchEvents']",
				"//h1[text()='Search Events']");

		// Open time selection
		driver.findElement(By.id("time_button")).click();
		driver.findElement(By.id("minDate1")).clear();
		driver.findElement(By.id("minDate1")).sendKeys("2005-11-01T17:00:00"); 
		driver.findElement(By.id("maxDate1")).clear();
		driver.findElement(By.id("maxDate1")).sendKeys("2004-11-01T18:30:00");
		
		driver.findElement(By.xpath(okButton)).click();
		try {
			assertTrue(!isElementPresent(By
					.xpath("//td[@id='time_area']/table/tbody/tr[1]/td[2]")));

		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}
	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
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
	private void waitXpath(String path) {
		// wait 60 seconds
		for (int i = 0; i < 600; i++) {
			assert i < 600; // only in dibug
			try {
				// System.out.println(tag + "wait. element: "
				// + driver.findElement(By.xpath(path)).toString()
				// + "; xpath: " + path);

				if (driver.findElement(By.xpath(path)).isDisplayed()) {
					return;
				}

			} catch (Exception e) {
				// System.out.println(e.toString());
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {

				System.out.println(tag + "WaitXpath error: thread.sleep");
				e.printStackTrace();
			}

		}
		System.out.println(tag + "Element cant be find " + path);
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
	private void navigateTO(String path, String untilFound) {

		for (int second = 0;; second++) {
			if (second >= 600) {
				System.out.println(tag + "navigatTO: Element cant be find "
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
					return;
					// break;
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println(tag + "navigateTO error");
				e.printStackTrace();
			}
		}
	}

}
