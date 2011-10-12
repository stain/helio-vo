package ch.fhnw.i4ds.helio;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * 
 * @author Lavanchy
 * 
 */
public class AndreSelTest {

	private WebDriver driver;
	private String baseUrl = "";
	private StringBuffer verificationErrors = new StringBuffer();
	static String TAG = "AndreSel3 inf: ";

	// Ofen used Path for elements
	private String waitForResult = "//div[@id='displayableResult']/div[@id='tables']";
	// For the pupops
	private static String OK_BUTTON = "//div[@class='ui-dialog-buttonset']/button/span[text()='Ok']";
	private static String DISPLAY_BUTTON = "//div[@id='result_button']/span"; // displayButton
	static StaticFunktions STATICFUNCTION = new StaticFunktions();

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

	@Test
	public void testAndreSel3() throws Exception {
		System.out.println(TAG + "Start test with: " + driver.toString());
		driver.get(baseUrl);
		driver = STATICFUNCTION.waitXpath("//div[@id='task_searchEvents']",
				driver);
		driver.findElement(By.xpath("//div[@id='task_searchEvents']")).click();// ("#task_searchEvents > span.ui-button-text"))

		// driver.findElement(By.xpath("//div[@id='task_searchEvents']")).click();////span[text()='Search
		// Events']
		// waitXpath("//h1[text()='Search Events']");
		driver = STATICFUNCTION.navigateTO("//div[@id='task_searchEvents']",
				"//h1[text()='Search Events']", driver);

		// Open time selection
		driver.findElement(By.id("time_button")).click();
		driver.findElement(By.id("minDate1")).clear();
		driver.findElement(By.id("minDate1")).sendKeys("2003-10-27T00:00:00");
		driver.findElement(By.id("maxDate1")).clear();
		driver.findElement(By.id("maxDate1")).sendKeys("2003-11-03T00:00:00");
		driver.findElement(By.xpath(OK_BUTTON)).click();
		// Open event selection
		driver.findElement(
				By.cssSelector("#event_button > span.ui-button-text")).click();
		driver.findElement(
				By.xpath("//table[@id='input_table']/tbody/tr/td[@internal='RHESSI Hard X-ray Flare List']"))
				.click();// Rhessi .//*[@id='input_table']/tbody/tr[15]/td
		driver.findElement(By.xpath(OK_BUTTON)).click();
		driver = STATICFUNCTION.waitXpath(DISPLAY_BUTTON, driver);
		driver.findElement(By.xpath(DISPLAY_BUTTON)).click();
		driver = STATICFUNCTION.waitXpath(waitForResult, driver); // Default
																	// wait
																	// result is
																	// displayed.

		// Sort the element in the List!
		driver.findElement(
				By.xpath("//div[@id='resultTable0_wrapper']/div[2]/div/div/table/thead/tr/th[11]"))
				.click();
		driver.findElement(
				By.xpath("//div[@id='resultTable0_wrapper']/div[2]/div/div/table/thead/tr/th[11]"))
				.click();
		driver.findElement(
				By.xpath("//table[@id='resultTable0']/tbody/tr/td[3]")).click();
		driver.findElement(
				By.cssSelector("#response_save_selection > span.ui-button-text"))
				.click();

		// Save selection to cart
		driver.findElement(By.id("minDate1")).clear();
		driver.findElement(By.id("minDate1")).sendKeys("2003-11-01T17:00:00");
		driver.findElement(By.id("maxDate1")).clear();
		driver.findElement(By.id("maxDate1")).sendKeys("2003-11-01T18:30:00");
		driver.findElement(By.xpath(OK_BUTTON)).click();
		// Timselection is closed
		driver = STATICFUNCTION.navigateTO("//div[@id='task_searchInstCap']",
				"//h1[text()='Search Instruments by Capability']", driver);
		driver.findElement(By.id("task_searchInstCap")).click();

		// Drag & Drop in Search Instruments by Capability
		WebElement from = (WebElement) driver.findElement(By
				.cssSelector("img.history_draggable.ui-draggable"));
		WebElement to = (WebElement) driver.findElement(By
				.xpath("//div[@class='resultDroppable ui-droppable']/img"));
		(new Actions(driver)).dragAndDrop(from, to).perform();
		driver.findElement(By.xpath(DISPLAY_BUTTON)).click();
		driver = STATICFUNCTION.waitXpath(waitForResult, driver);
		// // HXR
		// //div[@id='ics_instrument']/table/tbody/tr/td/table/tbody/tr/td/input[@name='hxr']
		// mabe select sxr to
		driver.findElement(
				By.xpath("html/body/div[4]/div[3]/div[2]/div/div/div/div[3]/div/div[1]/div[2]/div/div/div[1]/table[2]/tbody/tr[8]/td/table/tbody/tr[1]/td[3]/input"))// //table[@class='advanced_param_table']/tbody/tr/td/input[@name='hxr']
				.click();
		try { // Time needed to sort the table
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(TAG + "WaitXpath error: thread.sleep");
			e.printStackTrace();
		}
		driver.findElement(
				By.xpath("//table[@id='resultTable0']/tbody/tr/td[3][text()='RHESSI__HESSI_HXR']"))
				.click(); // ict-instrument
		driver.findElement(
				By.cssSelector("#response_save_selection > span.ui-button-text"))
				.click();
		driver.findElement(By.xpath(OK_BUTTON)).click();

		// driver.findElement(
		// By.xpath("//div[@class='ui-dialog-buttonset']/button")).click();

		driver.findElement(
				By.cssSelector("#task_searchData > span.ui-button-text"))
				.click();

	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}

}
