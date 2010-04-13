package org.egso.provider.datamanagement.connector;


import java.util.Vector;

public interface ProviderResultsParser {


	public Vector<Vector<String>> getResults();

	public void setSelectedFields(Vector<String> selected);
}
