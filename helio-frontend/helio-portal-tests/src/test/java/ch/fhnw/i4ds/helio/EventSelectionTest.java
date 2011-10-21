package ch.fhnw.i4ds.helio;

import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.openqa.selenium.By;

public class EventSelectionTest extends SeleniumTests {

	@Test
	public void testEventSelection() throws Exception {
		driver.get(BASE_URL);
		driver = waitXpath("//div[@id='task_searchEvents']", driver);
		driver.findElement(By.xpath("//div[@id='task_searchEvents']")).click();// ("#task_searchEvents > span.ui-button-text"))
		driver.findElement(By.id("event_button")).click();
		driver.findElement(
				By.xpath("//table[@id='input_table']/tbody/tr/td[@internal='RHESSI Hard X-ray Flare List']"))
				.click();// Rhessi .//*[@id='input_table']/tbody/tr[15]/td
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
}
