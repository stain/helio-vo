package ch.fhnw.i4ds.helio;

import static org.junit.Assert.fail;

import org.apache.xalan.xsltc.compiler.sym;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class andreSel2 {

	private WebDriver driver;
	private String baseUrl = "";
	private StringBuffer verificationErrors = new StringBuffer();
	String tag = "andreSel2 inf: ";

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
	public void runTests(WebDriver driver) {
		try {
			this.driver = driver;
			if (driver != null) {
				testAndreSel2();
			}
			System.out.println(tag + "Test was successful");
		} catch (Exception e) {
			System.out.println(tag + "Test failed");
			System.out.println(e.toString());
		}

	}

	// @Before
	// public void setUp() throws Exception {
	// driver = new FirefoxDriver();
	// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	// }

	@Test
	public void testAndreSel2() throws Exception {
		System.out.println(tag + "Start test with: " + driver.toString());
		driver.get(baseUrl);
		driver.findElement(
				By.cssSelector("#task_searchEvents > span.ui-button-text"))
				.click();
		// Open time selection
		driver.findElement(
				By.cssSelector("td > #time_button > span.ui-button-text"))
				.click();
		driver.findElement(By.id("minDate1")).clear();
		driver.findElement(By.id("minDate1")).sendKeys("2003-10-27T00:00:00");
		driver.findElement(By.id("maxDate1")).clear();
		driver.findElement(By.id("maxDate1")).sendKeys("2003-11-03T00:00:00");
		driver.findElement(
				By.xpath("//div[@class='ui-dialog-buttonset']/button")).click();
		// Standard correction for the popups to press OK

		// Open event selection
		driver.findElement(
				By.cssSelector("#event_button > span.ui-button-text")).click();
		driver.findElement(By.xpath(".//*[@id='input_table']/tbody/tr[15]/td"))
				.click();
		driver.findElement(
				By.xpath("//div[@class='ui-dialog-buttonset']/button")).click();
		waitXpath("//*[@id='voTables']/div[3]/h3", 0);

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
		driver.findElement(
				By.xpath("//div[@class='ui-dialog-buttonset']/button[1]"))
				.click();
		// Timselection is closed
		navigateTO("//div[@id='task_searchInstCap']",
				"//h1[text()='Search Instruments by Capability']");
		driver.findElement(By.id("task_searchInstCap")).click();

		// Drag & Drop
		WebElement from = (WebElement) driver.findElement(By
				.cssSelector("img.history_draggable.ui-draggable"));
		WebElement to = (WebElement) driver.findElement(By
				.xpath("//div[@class='resultDroppable ui-droppable']/img"));
		(new Actions(driver)).dragAndDrop(from, to).perform();
		waitXpath("//input[@name='HXR']",
				1);
		driver.findElement(
				By.xpath("//input[@name='HXR']"))
				.click();
		driver.findElement(
				By.xpath("//div/div/table[2]/tbody/tr[8]/td/table/tbody/tr/td[3]/input"))
				.click();
		driver.findElement(
				By.xpath("//table[@id='resultTable0']/tbody/tr/td[3]")).click();
		driver.findElement(
				By.cssSelector("#response_save_selection > span.ui-button-text"))
				.click();
		driver.findElement(By.xpath("//button[@type='button']")).click();

		driver.findElement(
				By.xpath("//div[@class='ui-dialog-buttonset']/button")).click();

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
	private void waitXpath(String path, int elementCase) {
		for (int second = 0;; second++) {
			if (second >= 20) {
				System.out.println(tag + "Element cant be find " + path);
			}
			try {
				System.out.println(tag + "wai. element: "
						+ driver.findElement(By.xpath(path)).toString()
						+"; xpath: "+ path);
				// /if (txt == null) {
				if (elementCase == 0) {
					if (driver.findElement(By.xpath(path)).isDisplayed()) {
						break;
					} else if (elementCase == 1) {
						if (driver.findElement(By.xpath(path)).isEnabled()) {
							break;
						}
					} else {
						System.out.println(tag + "case don't exist.");
					}
				}

			} catch (Exception e) {
				// System.out.println(e.toString());
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(tag + "WaitXpath error");
				e.printStackTrace();
			}
		}
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
			if (second >= 20) {
				System.out.println(tag + "Element cant be find " + path);
			}
			try {
				driver.findElement(By.xpath(path)).click();
			} catch (Exception e) {
				// System.out.println(e.toString());
			}
			try {
				if (driver.findElement(By.xpath(untilFound)).isDisplayed()) {
					break;
				}
			} catch (Exception e) {
				
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(tag + "navigateTO error");
				e.printStackTrace();
			}
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

}
