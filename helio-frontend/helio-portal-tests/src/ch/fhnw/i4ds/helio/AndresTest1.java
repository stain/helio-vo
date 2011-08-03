/**
 * Workflow made by Andre.
 * recorded with the IDE and exported as Junit4. 
 *
 */
package ch.fhnw.i4ds.helio;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebDriverCommandProcessor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.*;

import com.thoughtworks.selenium.Selenium;

import bsh.Capabilities;

/**
 * @author Lavanchy
 * @since 28.07.11
 * @version 0.1 Beta (not finished)
 */
public class AndresTest1 {

	
	
	Selenium selenium;
	WebDriver driver;
	String tag = "AndresTest1 inf: ";
	
	/**
	 * Run andres thest wit firefox
	 */
		public void runFF (){
			try{
			System.out.println(tag+ "SeleAndre start with Firefox");
			driver = new FirefoxDriver();
			String baseUrl = "http://helio.i4ds.technik.fhnw.ch/";
			selenium = new WebDriverBackedSelenium(driver, baseUrl);
			//selenium.start();	http://stackoverflow.com/questions/6385181/could-not-start-selenium-session-you-may-not-start-more-than-one-session-at-a-ti
			testSeleAndre();
			}catch (Exception e) {
				System.out.println(tag + "Not abel to run the test:");
				System.out.println(e.toString());
			}
		}
		
		/**
		 * Run andres thest wit InternetExplorer
		 * My make som problem with the fireWall!!!
		 */
			public void runIE (){
				try{
				System.out.println(tag+ "SeleAndre start with IE.");
				driver = new InternetExplorerDriver();
				String baseUrl = "http://helio.i4ds.technik.fhnw.ch/";
				selenium = new WebDriverBackedSelenium(driver, baseUrl);
				//selenium.start();	http://stackoverflow.com/questions/6385181/could-not-start-selenium-session-you-may-not-start-more-than-one-session-at-a-ti
				testSeleAndre();
				}catch (Exception e) {
					System.out.println(tag + "Not abel to run the test:");
					System.out.println(e.toString());
				}
			}
			

		
		/**
		 * Same test for all drivers. Andre’s workflow. 
		 * @see syntax http://seleniumhq.org/docs/02_selenium_ide.html#script-syntax
		 */
		private void testSeleAndre()  {
			try{
			selenium.open("/Helio-dev/prototype/explorer");
			selenium.click("css=#task_searchEvents > span.ui-button-text");
			selenium.click("css=td > #time_button > span.ui-button-text");
			selenium.type("id=minDate1", "2003-10-27");
			selenium.type("id=maxDate1", "2003-11-03");
			selenium.click("//button[@type='button']");
			selenium.click("css=#event_button > span.ui-button-text");
			selenium.click("xpath= .//*[@id='input_table']/tbody/tr[15]/td");
			selenium.click("//button[@type='button']");
			for (int second = 0;; second++) {
				if (second >= 60) fail("timeout");
				try { if (selenium.isVisible("//*[@id='voTables']/div[3]/h3")) break; } catch (Exception e) {}
				Thread.sleep(1000);
			}
	
			selenium.click("//div[@id='resultTable0_wrapper']/div[2]/div/div/table/thead/tr/th[11]");
			selenium.click("//div[@id='resultTable0_wrapper']/div[2]/div/div/table/thead/tr/th[11]");
			selenium.click("//table[@id='resultTable0']/tbody/tr/td[3]");
			selenium.click("css=#response_save_selection > span.ui-button-text");
			selenium.click("//button[@type='button']");
			selenium.click("css=img.history_draggable.ui-draggable");
			selenium.type("id=minDate1", "2003-11-01");
//			selenium.type("id=minDate1", "2003-11-02");
//			selenium.type("id=minTime1", "16:09");
//			selenium.type("id=maxTime1", "18:30");
//			selenium.click("//button[@type='button']");
//			selenium.click("css=#task_searchInstruments > span.ui-button-text");
//			selenium.click("//div/div/table[2]/tbody/tr[2]/td/table/tbody/tr/td/input");
//			selenium.click("//div/div/table[2]/tbody/tr[8]/td/table/tbody/tr/td[3]/input");
//			selenium.click("//table[@id='resultTable0']/tbody/tr/td[3]");
//			selenium.click("css=#response_save_selection > span.ui-button-text");
//			selenium.click("//button[@type='button']");
//			selenium.click("css=#task_searchData > span.ui-button-text");
			}catch (Exception e) {
				fail("SeleAndre Game Over!");
			}finally{
				selenium.stop();
				System.out.println(tag+ "SeleAndre over, Selenium Stop!");				
			}
		}


		/**
		 * @param string
		 */
		private void fail(String string) {
			System.out.println("AndresTest1 faild: "+ string);
			
		}
		
		
//		private void difDriver(){
//			Capabilities capabilities = new DesiredCapabilities();
//		}
}
