package ch.fhnw.i4ds.helio;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static org.junit.Assert.assertTrue;

public class StaticFunktions {
	
	//private WebDriver driver;
	static String TAG = "StaticFunction: ";
		
	/**
	 * wait max 1 min until Element is present.
	 * 
	 * @param path
	 *            Path to wait
	 * @param elementCase
	 *            case 1: is Displayed. case 2: is Enabled. default 1.
	 */
	public WebDriver waitXpath(String path, WebDriver driver) {
		
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
	public WebDriver navigateTO(String path, String untilFound, WebDriver driver) {

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
