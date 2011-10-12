package eu.heliovo.cis.example.server;

import java.util.Date;

public class TestResult 
{
	String	testComment		=	null;
	Date	testPerformed	=	null;

	public TestResult() 
	{
		super();
	}

	public TestResult(String testComment) 
	{
		super();
		this.testComment = testComment;
		this.testPerformed = new Date();
	}

	public String getTestComment() {
		return testComment;
	}

	public void setTestComment(String testComment) {
		this.testComment = testComment;
	}

	public Date getTestPerformed() {
		return testPerformed;
	}
}
