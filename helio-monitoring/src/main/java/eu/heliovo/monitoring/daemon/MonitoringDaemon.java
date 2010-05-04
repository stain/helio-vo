package eu.heliovo.monitoring.daemon;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import eu.heliovo.monitoring.model.ServiceStatus;
import eu.heliovo.monitoring.model.State;

@Component
public class MonitoringDaemon implements InitializingBean, RemotingMonitoringDaemon {

	public final static String FILE_ENCODING = "UTF-8";

	protected final File nagiosExternalCommandFile;
	private final Logger logger = Logger.getLogger(getClass());
	private final boolean forceNagiosExternalCommandFileCreation;

	@Autowired
	public MonitoringDaemon(@Value("${nagiosExternalCommandFilePath}") final String nagiosExternalCommandFile,
			@Value("${forceNagiosExternalCommandFileCreation}") final boolean forceNagiosExternalCommandFileCreation) {

		this.nagiosExternalCommandFile = new File(nagiosExternalCommandFile);
		this.forceNagiosExternalCommandFileCreation = forceNagiosExternalCommandFileCreation;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		if (nagiosExternalCommandFile != null && !nagiosExternalCommandFile.exists()) {
			if (forceNagiosExternalCommandFileCreation) {
				logger.info("nagiosExternalCommandFile does not exist, but will be created");
				try {
					nagiosExternalCommandFile.createNewFile();
				} catch (final IOException e) {
					throw new IllegalStateException("nagiosExternalCommandFile could not be created!", e);
				}
			} else {
				throw new IllegalStateException(
						"nagiosExternalCommandFile does not exist and is not allowed to be created!");
			}
		}

		if (nagiosExternalCommandFile == null) {
			throw new IllegalStateException("nagiosExternalCommandFile must not be null!");
		} else if (!nagiosExternalCommandFile.canWrite()) {
			throw new IllegalStateException("nagiosExternalCommandFile cannot be written!");
		}
	}

	public void writeToNagiosExternalCommandFile(final long time, final NagiosCommand command, final String hostName,
			final String serviceName, final NagiosStatus status, final String statusMessage) {

		final String lineToWrite = buildLineToWrite(time, command, hostName, serviceName, status, statusMessage);
		try {

			final FileWriter fw = new FileWriter(nagiosExternalCommandFile, true);
			try {
				fw.write(lineToWrite);
			} finally {
				fw.close();
			}

		} catch (final IOException e) {
			throw new IllegalStateException("data could not be written!", e);
		}
	}

	public void writeToNagiosExternalCommandFile(final NagiosCommand command, final String hostName,
			final String serviceName, final NagiosStatus status, final String statusMessage) {

		this.writeToNagiosExternalCommandFile(System.currentTimeMillis() / 1000, command, hostName, serviceName,
				status, statusMessage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.heliovo.monitoring.daemon.RemotingMonitoringDaemon#writeServiceStatusToNagios(java.util.List)
	 */
	public void writeServiceStatusToNagios(final List<ServiceStatus> serviceStatus) {

		for (final ServiceStatus actualServiceStatus : serviceStatus) {

			final NagiosCommand command = NagiosCommand.PROCESS_SERVICE_CHECK_RESULT;
			final String hostName = actualServiceStatus.getUrl().getHost();
			final String serviceName = actualServiceStatus.getId();

			final NagiosStatus status;
			final State state = actualServiceStatus.getState();
			if (State.OK.equals(state)) {
				status = NagiosStatus.OK;
			} else if (State.CRITICAL.equals(state)) {
				status = NagiosStatus.CRITICAL;
			} else if (State.WARNING.equals(state)) {
				status = NagiosStatus.WARNING;
			} else if (State.UNKNOWN.equals(state)) {
				status = NagiosStatus.UNKNOWN;
			} else {
				throw new IllegalStateException("a state must be given!");
			}

			writeToNagiosExternalCommandFile(command, hostName, serviceName, status, actualServiceStatus.getMessage());
		}
	}

	private String buildLineToWrite(final long time, final NagiosCommand command, final String hostName,
			final String serviceName, final NagiosStatus status, final String statusMessage) {

		final StringBuffer buffer = new StringBuffer("[");
		buffer.append(time);
		buffer.append("]");
		buffer.append(" ");
		buffer.append(NagiosCommand.PROCESS_SERVICE_CHECK_RESULT.name());
		buffer.append(";");
		buffer.append(hostName);
		buffer.append(";");
		buffer.append(serviceName);
		buffer.append(";");
		buffer.append(status.ordinal());
		buffer.append(";");
		buffer.append(statusMessage);
		buffer.append("\n");

		return buffer.toString();
	}
}
