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
	public static void main(String[] args) throws Exception {

		String urlDev = "http://helio.i4ds.technik.fhnw.ch/Helio-dev/prototype/explorer";
		String tag = "Test controller info: ";
		System.out.println(tag + "test is runing ");

		// FF drivers
		String[] ffPath = new String[4];
		ffPath[0] = "PortableBrowser/FirefoxPortableLegancy36/FirefoxPortable.exe";
		ffPath[1] = "Doc/PortableBrowser/Portable_Firefox_4.0/Firefox/firefox.exe";
		ffPath[2] = "PortableBrowser/FirefoxPortable5.0.1/FirefoxPortable.exe";	//Drag & Drop Doesn't Work in the test!
		int ffPathNr = 1;

		// Sel 1
		// AndresTest1 andre1 = new AndresTest1();
		// andre1.runFF();
		// andre1.runChrom();
		// andre1.runIE();
		try{
			System.setProperty("webdriver.firefox.bin", ffPath[ffPathNr]);
		}catch (Exception e) {
			e.setStackTrace(null);
		}
			System.setProperty("webdriver.firefox.bin", ffPath[ffPathNr]);
			AndreSel3 andreSel3 = new AndreSel3();
			andreSel2 andre2 = new andreSel2(urlDev);
			WebDriver driver = new FirefoxDriver();
			andre2.runTests(driver);
			andreSel3.runTests(driver);
		
		System.out.println(tag + "End of all tests");
	}

}
