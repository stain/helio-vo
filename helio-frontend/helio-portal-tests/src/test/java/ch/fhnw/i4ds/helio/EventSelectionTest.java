package ch.fhnw.i4ds.helio;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;

public class EventSelectionTest extends SeleniumTests {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		logger.info("EventSelection in ff");
		setTAG("EventSelection_FF ");
	}

	@Test
	public void testEventSelection() throws Exception {
		logger.debug("os: " + System.getProperty("os.name"));
		driver.get(BASE_URL);

		assertTrue(waitXpath("//div[@id='task_searchEvents']", driver));

		clickElementXpath(driver, "//div[@id='task_searchEvents']");
		ClickElenemtId(driver, "event_button");

		clickElementXpath(
				driver,
				"//table[@id='input_table']/tbody/tr/td[@internal='RHESSI Hard X-ray Flare List']");
		clickElementXpath(driver, OK_BUTTON);
		try {
			logger.assertLog(isElementPresent(By
					.xpath("//td[@id='time_area']/table/tbody/tr[1]/td[2]")),
					tag + " testReversDate");

		} catch (Error e) {
			logger.error(e.toString(), e);
			verificationErrors.append(e.toString());
		}
	}

	public void setTAG(String tAG) {
		super.setTAG(tAG);

	}

}
