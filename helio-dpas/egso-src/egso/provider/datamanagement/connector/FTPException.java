package org.egso.provider.datamanagement.connector;

import java.io.IOException;


/**
 * FTPException is created when an exception occurs during FTP operations.
 *
 * @author Romain Linsolas (linsolas@gmail.com)
 * @version 1.1 - 29/11/2003 [09/2001]
 **/
@SuppressWarnings("serial")
public class FTPException extends IOException {


	public FTPException() {
		super();
	}


	public FTPException(String msg) {
		super(msg);
	}


}

