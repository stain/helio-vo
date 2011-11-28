package ch.fhnw.i4ds.helio.browser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Download a Firefox and run the tests with it.
 * 
 * @author Lavanchy 25.11.2011
 */
public class DownloadManager {

	ZipHandler zipHandler = new ZipHandler();
	String os = System.getProperty("os.name").toLowerCase();
	File osTarget = new File("");
	URL url;
	File fileName = new File("Portable_Firefox_4.0/Firefox/firefox.exe");

	static List<BrowserConfig> browsers = new ArrayList<BrowserConfig>(); // todo

	// private

	public DownloadManager() {

	}

	/**
	 * Download the zip file of the Portable Browser
	 * 
	 * @param url
	 * @param target
	 */
	static void download(URL url, File target) {
		try {
			// Do not download a zip witch already exist
			if (target.exists()) {
				System.out.println("download: File exist " + target.toString());
				return;
			}
			System.out.println("download: file:  " + target.toString());
			url.openConnection();
			InputStream reader = url.openStream();
			// Thread.sleep(5500);
			System.out.println("target: " + target.toString());
			FileOutputStream writer = new FileOutputStream(target);
			byte[] buffer = new byte[153600];
			int totalBytesRead = 0;
			int bytesRead = 0;

			while ((bytesRead = reader.read(buffer)) > 0) {
				writer.write(buffer, 0, bytesRead);
				buffer = new byte[153600];
				totalBytesRead += bytesRead;
			}
			writer.close();
			reader.close();
			System.out.println("downlod sucsesful");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File IdentifyOSTarget() {

		String os = System.getProperty("os.name").toLowerCase();
		File osTarget = new File("");
		URL url;
		File fileName = new File("Portable_Firefox_4.0/Firefox/firefox.exe");
		// todo eigene funktion
		if (os.indexOf("win") >= 0) {
			osTarget = new File(System.getenv("TEMP")); // Temp of the user
			File portableDriverTarget = new File(osTarget,
					"Portable_Firefox_4.0");
			portableDriverTarget.mkdirs(); // Create a new folder for the
											// browser.
			portableDriverTarget = new File(portableDriverTarget,
					"Portable_Firefox_4.0.zip");
			try {
				url = new URL(
						"http://helio-dev.cs.technik.fhnw.ch/portable_driver/Portable_Firefox_4.0.zip");
				browsers.add(new BrowserConfig(url, "windows", 32, fileName,
						portableDriverTarget, "firefox"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			// TODO chek it the browser is alrady instaled, else ad to browsers

		} else if (os.indexOf("mac") >= 0) {
			// osTarget = new File("");

		} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
			// osTarget = new File("");

		}

		return osTarget;
	}

	/**
	 * 
	 * TODO chose a browser
	 * 
	 * @return return the location of the Portable browser. Null if failed.
	 */
	public String getPortableBrowser() {
		osTarget = IdentifyOSTarget();
		// todo
		for (BrowserConfig browserConfig : browsers) {
			download(browserConfig.getUrl(), browserConfig.getTargetName());
			// // Cheek if file exist before to unzip it
			// if (!(new File(browserConfig.targetName.toString())).exists()) {
			// System.out.println("unzipt: File exist "
			// + browserConfig.targetName.toString());

			if (zipHandler.unZip(browserConfig.getTargetName(), osTarget)) {
				System.out.println("Driver instaled "
						+ browserConfig.toString());
				// browsers.remove(browserConfig);
				// TODO erfolgreiche browser speichern

			} else {
				System.out.println("Driver instalation FAILD "
						+ browserConfig.toString());

			}
			// } else {
			// System.out.println(" alredy unzipt: File exist "
			// + browserConfig.targetName.toString());
			// // browsers.remove(browserConfig);
			// }
		}
		return osTarget.toString() + "\\" + fileName;
	}

}
