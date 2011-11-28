package ch.fhnw.i4ds.helio.ie;

//import org.openqa.selenium.ie.InternetExplorerDriver;
import ch.fhnw.i4ds.helio.EventSelectionTest;

/**
 *  This Test Actually don’t work!
 * @author Lavanchy
 *
 */
public class EventSelectionIETest extends EventSelectionTest {

	@Override
	public void setUp() throws Exception {

		// System.setProperty("webdriver.chrome.driver",
		// "C:\\Temp\\portable\\GoogleChromePortable\\GoogleChromePortable.exe");

		// TODO not a coment on botem
		// driver = new InternetExplorerDriver();
		super.setUp();
		super.setTAG("EventSelection_IE");
		logger.info("Event Selection IE");
		logger.error(" This Test Actually don’t work!");

	}

}
