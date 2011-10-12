package ch.fhnw.i4ds.helio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class RangeTest {

	private WebDriver driver;
	private String baseUrl = "";
	private StringBuffer verificationErrors = new StringBuffer();
	String tag = "AdRangeTest: ";
	static StaticFunktions STATICFUNCTION = new StaticFunktions();

	// Ofen used Path for elements
	// private String waitForResult =
	// "//div[@id='displayableResult']/div[@id='tables']";
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
		driver = STATICFUNCTION.waitXpath("//div[@id='task_searchEvents']",
				driver);
		driver.findElement(By.xpath("//div[@id='task_searchEvents']")).click();// ("#task_searchEvents > span.ui-button-text"))

		driver = STATICFUNCTION.navigateTO("//div[@id='task_searchEvents']",
				"//h1[text()='Search Events']", driver);

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
			assertFalse(isElementPresent(By
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
		driver = STATICFUNCTION.waitXpath("//div[@id='task_searchEvents']",
				driver);
		driver.findElement(By.xpath("//div[@id='task_searchEvents']")).click();// ("#task_searchEvents > span.ui-button-text"))

		driver = STATICFUNCTION.navigateTO("//div[@id='task_searchEvents']",
				"//h1[text()='Search Events']", driver);

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
			// assertFalse(isElementPresent(By
			// .xpath("//td[@id='time_area']/table/tbody/tr[2]/td[2]")));
			assertTrue(isElementPresent(By
					.xpath("//td[@id='time_area']/table/tbody/tr[3]/td[text()='2002-11-01T17:13:21']")));
			// ssertTrue(isElementPresent(By
			// .xpath("//td[@id='time_area']/table/tbody/tr[3]/td[text()='2002-11-01T18:30:00']")));

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
		driver = STATICFUNCTION.waitXpath("//div[@id='task_searchEvents']",
				driver);
		driver.findElement(By.xpath("//div[@id='task_searchEvents']")).click();// ("#task_searchEvents > span.ui-button-text"))

		driver = STATICFUNCTION.navigateTO("//div[@id='task_searchEvents']",
				"//h1[text()='Search Events']", driver);

		// Open time selection
		driver.findElement(By.id("time_button")).click();
		driver.findElement(By.id("minDate1")).clear();
		driver.findElement(By.id("minDate1")).sendKeys("lol");
		driver.findElement(By.id("maxDate1")).clear();
		driver.findElement(By.id("maxDate1")).sendKeys("2004-11-01T18:30:00");

		driver.findElement(By.xpath(okButton)).click();
		try {
			assertFalse(isElementPresent(By
					.xpath("//td[@id='time_area']/table/tbody/tr[1]/td[2]")));

		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}

	/**
	 * Catch wrong entrees.
	 * Start date is youngerthen end date 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testReversDate() throws Exception {

		driver.get(baseUrl);
		driver = STATICFUNCTION.waitXpath("//div[@id='task_searchEvents']",
				driver);
		driver.findElement(By.xpath("//div[@id='task_searchEvents']")).click();// ("#task_searchEvents > span.ui-button-text"))

		driver = STATICFUNCTION.navigateTO("//div[@id='task_searchEvents']",
				"//h1[text()='Search Events']", driver);

		// Open time selection
		driver.findElement(By.id("time_button")).click();
		driver.findElement(By.id("minDate1")).clear();
		driver.findElement(By.id("minDate1")).sendKeys("2005-11-01T17:00:00");
		driver.findElement(By.id("maxDate1")).clear();
		driver.findElement(By.id("maxDate1")).sendKeys("2004-11-01T18:30:00");

		driver.findElement(By.xpath(okButton)).click();
		try {
			assertFalse(isElementPresent(By
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

}
