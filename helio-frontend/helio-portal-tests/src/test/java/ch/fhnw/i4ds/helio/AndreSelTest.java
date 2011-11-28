package ch.fhnw.i4ds.helio;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * 
 * @author Lavanchy
 * 
 *         created It my crush running the Drag and Drop.
 */
public class AndreSelTest extends SeleniumTests {

	static String TAG = "AndreSel3 inf: ";

	@Override
	public void setUp() throws Exception {
		super.setUp();
		logger.info("AndreSelTest in ff");
	}

	@Test
	public void testAndreSel3() throws Exception {
		// if
		// (!driver.toString().equals("org.openqa.selenium.firefox.FirefoxDriver@1554d32")){
		// //TODO Works only with corect firefox
		// System.out.println(TAG + "Note The optimal driver.");
		// return;
		// }
		System.out.println(TAG + "Start test with: " + driver.toString());
		logger.info(TAG + "Start test with: " + driver.toString());
		driver.get(BASE_URL);
		assertTrue(waitXpath("//div[@id='task_searchEvents']", driver));
		clickElementXpath(driver, "//div[@id='task_searchEvents']");

		driver = navigateTO("//div[@id='task_searchEvents']",
				"//h1[text()='Search Events']", driver);

		// Open time selection
		ClickElenemtId(driver, "time_button");
		driver.findElement(By.id("minDate1")).clear();
		driver.findElement(By.id("minDate1")).sendKeys("2003-10-27T00:00:00");
		driver.findElement(By.id("maxDate1")).clear();
		driver.findElement(By.id("maxDate1")).sendKeys("2003-11-03T00:00:00");
		clickElementXpath(driver, OK_BUTTON);
		// Open event selection

		ClickElenemtCss(driver, "#event_button > span.ui-button-text");
		driver.findElement(
				By.xpath("//table[@id='input_table']/tbody/tr/td[@internal='RHESSI Hard X-ray Flare List']"))
				.click();
		clickElementXpath(driver, OK_BUTTON);
		assertTrue(waitXpath(DISPLAY_BUTTON, driver));
		clickElementXpath(driver, DISPLAY_BUTTON);
		// Default wait result is displayed.
		assertTrue(waitXpath(WAIT_FOR_RESULT, driver));

		// Sort the element in the List!
		clickElementXpath(driver,
				"//div[@id='resultTable0_wrapper']/div[2]/div/div/table/thead/tr/th[11]");
		clickElementXpath(driver,
				"//div[@id='resultTable0_wrapper']/div[2]/div/div/table/thead/tr/th[11]");
		clickElementXpath(driver, "//table[@id='resultTable0']/tbody/tr/td[3]");
		ClickElenemtCss(driver,
				"#response_save_selection > span.ui-button-text");

		// Save selection to cart
		driver.findElement(By.id("minDate1")).clear();
		driver.findElement(By.id("minDate1")).sendKeys("2003-11-01T17:00:00");
		driver.findElement(By.id("maxDate1")).clear();
		driver.findElement(By.id("maxDate1")).sendKeys("2003-11-01T18:30:00");
		clickElementXpath(driver, OK_BUTTON);
		// Timselection is closed
		driver = navigateTO("//div[@id='task_searchInstCap']",
				"//h1[text()='Search Instruments by Capability']", driver);
		ClickElenemtId(driver, "task_searchInstCap");

		// Drag & Drop in Search Instruments by Capability
		WebElement from = (WebElement) driver.findElement(By
				.cssSelector("img.history_draggable.ui-draggable"));
		WebElement to = (WebElement) driver.findElement(By
				.xpath("//div/img[@id='time_drop']"));
		(new Actions(driver)).dragAndDrop(from, to).perform();
		clickElementXpath(driver, DISPLAY_BUTTON);
		assertTrue(waitXpath(WAIT_FOR_RESULT, driver));
		// HXR
		clickElementXpath(
				driver,
				"html/body/div[4]/div[3]/div[2]/div/div/div/div[3]/div/div[1]/div[2]/div/div/div[1]/table[2]/tbody/tr[8]/td/table/tbody/tr[1]/td[3]/input");

		try { // Time needed to sort the table
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(TAG + "WaitXpath error: thread.sleep");
			logger.warn(TAG + "WaitXpath error: thread.sleep");
			e.printStackTrace();
		}
		clickElementXpath(driver,
				"//table[@id='resultTable0']/tbody/tr/td[3][text()='RHESSI__HESSI_HXR']");// ict-instrument
		driver.findElement(
				By.cssSelector("#response_save_selection > span.ui-button-text"))
				.click();
		clickElementXpath(driver, OK_BUTTON);

		ClickElenemtCss(driver, "#task_searchData > span.ui-button-text");
	}

}
