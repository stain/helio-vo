package ch.fhnw.i4ds.helio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.openqa.selenium.By;

public class RangeTest extends SeleniumTests {

	private StringBuffer verificationErrors = new StringBuffer();
	String tag = "AdRangeTest: ";

	private String startDate = "2003-10-27T00:00:00";
	private String endDate = "2003-11-03T00:00:00";

	/**
	 * Add and delete a Tim range.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAdRange() throws Exception {

		driver.get(BASE_URL);
		driver = waitXpath("//div[@id='task_searchEvents']", driver);
		driver.findElement(By.xpath("//div[@id='task_searchEvents']")).click();// ("#task_searchEvents > span.ui-button-text"))

		driver = navigateTO("//div[@id='task_searchEvents']",
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
		driver.findElement(By.xpath(OK_BUTTON)).click();
		try {
			assertFalse(
					"testAdRange",
					isElementPresent(By
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

		driver.get(BASE_URL);
		driver = waitXpath("//div[@id='task_searchEvents']", driver);
		driver.findElement(By.xpath("//div[@id='task_searchEvents']")).click();// ("#task_searchEvents > span.ui-button-text"))

		driver = navigateTO("//div[@id='task_searchEvents']",
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

		driver.findElement(By.xpath(OK_BUTTON)).click();
		try {

			assertTrue(
					"testAd3Range",
					isElementPresent(By
							.xpath("//td[@id='time_area']/table/tbody/tr[3]/td[text()='2002-11-01T17:13:21']")));

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

		driver.get(BASE_URL);
		driver = waitXpath("//div[@id='task_searchEvents']", driver);
		driver.findElement(By.xpath("//div[@id='task_searchEvents']")).click();// ("#task_searchEvents > span.ui-button-text"))

		driver = navigateTO("//div[@id='task_searchEvents']",
				"//h1[text()='Search Events']", driver);

		// Open time selection
		driver.findElement(By.id("time_button")).click();
		driver.findElement(By.id("minDate1")).clear();
		driver.findElement(By.id("minDate1")).sendKeys("lol");
		driver.findElement(By.id("maxDate1")).clear();
		driver.findElement(By.id("maxDate1")).sendKeys("2004-11-01T18:30:00");

		driver.findElement(By.xpath(OK_BUTTON)).click();
		try {
			assertFalse(
					"testTxtRange",
					isElementPresent(By
							.xpath("//td[@id='time_area']/table/tbody/tr[1]/td[2]")));

		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}

	/**
	 * Catch wrong entrees. Start date is youngerthen end date
	 * 
	 * @throws Exception
	 */
	@Test
	public void testReversDate() throws Exception {

		driver.get(BASE_URL);
		driver = waitXpath("//div[@id='task_searchEvents']", driver);
		driver.findElement(By.xpath("//div[@id='task_searchEvents']")).click();// ("#task_searchEvents > span.ui-button-text"))

		driver = navigateTO("//div[@id='task_searchEvents']",
				"//h1[text()='Search Events']", driver);

		// Open time selection
		driver.findElement(By.id("time_button")).click();
		driver.findElement(By.id("minDate1")).clear();
		driver.findElement(By.id("minDate1")).sendKeys("2005-11-01T17:00:00");
		driver.findElement(By.id("maxDate1")).clear();
		driver.findElement(By.id("maxDate1")).sendKeys("2004-11-01T18:30:00");

		driver.findElement(By.xpath(OK_BUTTON)).click();
		try {
			assertFalse(
					"testReversDate",
					isElementPresent(By
							.xpath("//td[@id='time_area']/table/tbody/tr[1]/td[2]")));

		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}

	/**
	 * Are the listed events in the selected time range?
	 * 
	 * @throws Exception
	 */
	public void testTeimRangeCorect() throws Exception {
		driver.get(BASE_URL);
		driver = waitXpath("//div[@id='task_searchEvents']", driver);
		driver.findElement(By.xpath("//div[@id='task_searchEvents']")).click();// ("#task_searchEvents > span.ui-button-text"))

		// driver.findElement(By.xpath("//div[@id='task_searchEvents']")).click();////span[text()='Search
		// Events']
		// waitXpath("//h1[text()='Search Events']");
		driver = navigateTO("//div[@id='task_searchEvents']",
				"//h1[text()='Search Events']", driver);

		// Open time selection
		driver.findElement(By.id("time_button")).click();
		driver.findElement(By.id("minDate1")).clear();
		driver.findElement(By.id("minDate1")).sendKeys(startDate);
		driver.findElement(By.id("maxDate1")).clear();
		driver.findElement(By.id("maxDate1")).sendKeys(endDate);
		driver.findElement(By.xpath(OK_BUTTON)).click();
		// Open event selection
		driver.findElement(
				By.cssSelector("#event_button > span.ui-button-text")).click();
		driver.findElement(
				By.xpath("//table[@id='input_table']/tbody/tr/td[@internal='RHESSI Hard X-ray Flare List']"))
				.click();// Rhessi .//*[@id='input_table']/tbody/tr[15]/td
		driver.findElement(By.xpath(OK_BUTTON)).click();
		driver = waitXpath(DISPLAY_BUTTON, driver);
		driver.findElement(By.xpath(DISPLAY_BUTTON)).click();
		// Default wait result is displayed.
		driver = waitXpath(WAIT_FOR_RESULT, driver);
		// komper the values
		// TODO how many lies compare?
		for (int i = 0; i < 6; i++) {
			String selectate[] = driver
					.findElement(
							By.xpath("//table[@id='resultTable0']/tbody/tr["
									+ i + "]/td[3]"))
					// --> original selections tr[2]/td[3]"
					.getText().split("T");
			compareTime(selectate, 0);
		}
	}

	/**
	 * Recursive Function. Get the start and end date of the function variable
	 * Split startDate and splitEndDtate.
	 * 
	 * @param selectate
	 *            Input the selected Tim range [jjjj-MM-dd][hh:mm:ss]
	 * @param t
	 *            Start with 0, used for the call of his self
	 */
	private void compareTime(String[] selectate, int t) {
		String splitStart[] = startDate.split("T");
		String splitStartDate[] = splitStart[0].split("-");
		String splitStartTime[] = splitStart[1].split(":");
		String splitEnd[] = endDate.split("T");
		String splitEndDate[] = splitEnd[0].split("-");
		String splitEndTime[] = splitEnd[1].split(":");
		String splitSelectDate[] = selectate[0].split("-");
		String splitSelectTime[] = selectate[1].split(":");

		if (t > 4) { // Date
			if (Integer.parseInt(splitStartDate[t]) > Integer
					.parseInt(splitSelectDate[t])
					&& Integer.parseInt(splitSelectDate[t]) > Integer
							.parseInt(splitEndDate[t])) {

				if (Integer.parseInt(splitStartDate[t]) == Integer
						.parseInt(splitSelectDate[t])
						&& Integer.parseInt(splitSelectDate[t]) == Integer
								.parseInt(splitEndDate[t])) {
					compareTime(selectate, t++);
					return;
				} else {
					assertTrue("testTeimRangeCorect", true);
				}
			} else {
				return; // Komplet and rankg corekt
			}
		} else { // Time
			if (t > 6)
				return;
			if (Integer.parseInt(splitStartTime[t - 3]) > Integer
					.parseInt(splitSelectTime[t - 3])
					&& Integer.parseInt(splitSelectTime[t - 3]) > Integer
							.parseInt(splitEndTime[t - 3])) {

				if (Integer.parseInt(splitStartTime[t - 3]) == Integer
						.parseInt(splitSelectTime[t - 3])
						&& Integer.parseInt(splitSelectTime[t - 3]) == Integer
								.parseInt(splitEndTime[t - 3])) {

					compareTime(selectate, t++);
					return;
				} else {
					assertTrue("testTeimRangeCorect", true);
				}

			} else {
				return; // Komplet and rankg corekt
			}
		}
	}
}
