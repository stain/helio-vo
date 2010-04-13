package org.egso.provider.query.filehandling;


public class FileManager {


	public FileManager() {
	}


	public FileRetriever newFileRetriever() {
		FileRetriever fileRetriever = new FileRetriever();
		return(fileRetriever);
	}

}
