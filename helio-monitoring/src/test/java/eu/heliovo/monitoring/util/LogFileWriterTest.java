package eu.heliovo.monitoring.util;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import eu.heliovo.monitoring.logging.LogFileWriter;
import eu.heliovo.monitoring.logging.LogFileWriterImpl;

public class LogFileWriterTest extends Assert {

	@Test
	public void testLogFileWriter() throws Exception {

		final LogFileWriter logFileWriter = new LogFileWriterImpl("mainlog", "HEC");
		logFileWriter.writeToLogFile("sometext");
		logFileWriter.writeToLogFile("someOtherText");
		logFileWriter.close();

		final List<String> lines = FileUtils.readLines(new File("mainlog/" + logFileWriter.getFileName()));
		assertTrue(lines.get(0).endsWith("sometext"));
		assertTrue(lines.get(1).endsWith("someOtherText"));
	}
}
