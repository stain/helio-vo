/**
 * 
 */
package ch.fhnw.i4ds.helio;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Lavanchy
 * 
 */
public class TestControler {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) {

		String urlDev = "http://helio.i4ds.technik.fhnw.ch/Helio-dev/prototype/explorer";
		String tag = "Test controller info: ";
		System.out.println(tag + "test is runing ");
		

		// Sel 1
		// AndresTest1 andre1 = new AndresTest1();
		// andre1.runFF();
		// andre1.runChrom();
		// andre1.runIE();
		try{
			System.setProperty("webdriver.firefox.bin",
			"PortableBrowser/Portable_Firefox_4.0/FirefoxLoader.exe");	
		andreSel2 andre2 = new andreSel2(urlDev);
		WebDriver driver = new FirefoxDriver();
		andre2.runTests(driver);
		}catch (Exception e) {
			System.out.println(tag + "andreSel2 couldn’t be finished. ");
		}

		System.out.println(tag + "End of all tests");
	}

}
