package ch.fhnw.i4ds.helio;

import static org.junit.Assert.fail;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * 
 * @author Lavanchy
 * 
 */
public class andreSel2 {

	private WebDriver driver;
	private String baseUrl = "";
	private StringBuffer verificationErrors = new StringBuffer();
	String tag = "andreSel2 inf: ";

	// Ofen used Path for elements
	private String waitForResult = "//div[@id='displayableResult']/div[@id='tables']";
	private String okButton = "//div[@class='ui-dialog-buttonset']/button/span[text()='Ok']";// For
																								// the
																								// pupops

	/**
	 * 
	 * @param baseUrl
	 *            helio or helio-dev ( whole url)
	 */
	public andreSel2(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * 
	 * * @param driver Webdriver for example FF IE
	 */
	public void runTests(WebDriver driver) throws Exception {
		this.driver = driver;
		if (driver != null) {
			testAndreSel2();
		}
		System.out.println(tag + "Test was successful");

	}

	// @Before
	// public void setUp() throws Exception {
	// driver = new FirefoxDriver();
	// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	// }
	public void testAndreSel2() throws Exception {
		System.out.println(tag + "Start test with: " + driver.toString());
		driver.get(baseUrl);
		waitXpath("//div[@id='task_searchEvents']");
		driver.findElement(By.xpath("//div[@id='task_searchEvents']")).click();// ("#task_searchEvents > span.ui-button-text"))

		// driver.findElement(By.xpath("//div[@id='task_searchEvents']")).click();////span[text()='Search
		// Events']
		// waitXpath("//h1[text()='Search Events']");
		navigateTO("//div[@id='task_searchEvents']",
				"//h1[text()='Search Events']");

		// Open time selection
		driver.findElement(By.id("time_button")).click();
		driver.findElement(By.id("minDate1")).clear();
		driver.findElement(By.id("minDate1")).sendKeys("2003-10-27T00:00:00");
		driver.findElement(By.id("maxDate1")).clear();
		driver.findElement(By.id("maxDate1")).sendKeys("2003-11-03T00:00:00");
		driver.findElement(By.xpath(okButton)).click();

		// Open event selection
		driver.findElement(
				By.cssSelector("#event_button > span.ui-button-text")).click();
		driver.findElement(By.xpath(".//*[@id='input_table']/tbody/tr[15]/td"))
				.click();
		driver.findElement(By.xpath(okButton)).click();
		waitXpath(waitForResult); // Default wait result is displayed.

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
		driver.findElement(By.xpath(okButton)).click();
		// Timselection is closed
		navigateTO("//div[@id='task_searchInstCap']",
				"//h1[text()='Search Instruments by Capability']");
		driver.findElement(By.id("task_searchInstCap")).click();

		// Drag & Drop in Search Instruments by Capability
		WebElement from = (WebElement) driver.findElement(By
				.cssSelector("img.history_draggable.ui-draggable"));
		WebElement to = (WebElement) driver.findElement(By
				.xpath("//div[@class='resultDroppable ui-droppable']/img"));
		(new Actions(driver)).dragAndDrop(from, to).perform();
		waitXpath(waitForResult);
		// // HXR		//div[@id='ics_instrument']/table/tbody/tr/td/table/tbody/tr/td/input[@name='hxr']
		//mabe select sxr to
		driver.findElement(
				By.xpath("html/body/div[4]/div[3]/div[2]/div/div/div/div[3]/div/div[1]/div[2]/div/div/div[1]/table[2]/tbody/tr[8]/td/table/tbody/tr[1]/td[3]/input"))// //table[@class='advanced_param_table']/tbody/tr/td/input[@name='hxr']
				.click();
		try {	//Time needed to sort the table 
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(tag + "WaitXpath error: thread.sleep");
			e.printStackTrace();
		}
		driver.findElement(
				By.xpath("//table[@id='resultTable0']/tbody/tr/td[3][text()='RHESSI__HESSI_HXR']")).click();	//ict-instrument 
		driver.findElement(
				By.cssSelector("#response_save_selection > span.ui-button-text"))
				.click();
		driver.findElement(By.xpath(okButton)).click();

//		driver.findElement(
//				By.xpath("//div[@class='ui-dialog-buttonset']/button")).click();

		driver.findElement(
				By.cssSelector("#task_searchData > span.ui-button-text"))
				.click();

		tearDown();

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

	public void tearDown() throws Exception {

		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

}
