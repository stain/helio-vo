package eu.heliovo.shared.common.utilities;

import java.io.StringWriter;
import java.util.Vector;

import condor.classad.AttrRef;
import condor.classad.ClassAd;
import condor.classad.ClassAdParser;
import condor.classad.ClassAdWriter;
import condor.classad.Constant;
import condor.classad.Expr;
import condor.classad.RecordExpr;

public class ClassAdUtilities 
{
	/*
	 * The ClassAd parser
	 */
	transient private ClassAdParser parser = null;
	/*
	 * 
	 * Transformers - Transform ClassAd expressions to strings and vice-versa.
	 */
	/**
	 * This method transforms a ClassAd expression into a String.
	 * 
	 * @param expr
	 *            - The ClassAd expression to be returned as a String
	 * @return - The String that represents the ClassAd expression.
	 * @throws ClassAdUtilitiesException
	 */
	public String expr2String(Expr expr) throws ClassAdUtilitiesException 
	{
		String s;
		/*
		 * Converts the ClassAd expression into a String
		 */
		try {
			s = expr.toString();
		} catch (RuntimeException e) {
			e.printStackTrace();
			System.out
					.println("*******************************************************************");
			System.out
					.println("** ClassAdUtilities.expr2String() raised the following Exception **");
			System.out
					.println("** while processing :                                            **");
			System.out
					.println("*******************************************************************");
			System.out.println(expr + " of type " + expr.getClass());
			System.out
					.println("*******************************************************************");
			e.printStackTrace();
			throw new ClassAdUtilitiesException();
		}
		/*
		 * Returns the String
		 */
		return s;
	}

	/**
	 * This method transforms a ClassAd expression into a readeableString.
	 * 
	 * @param expr
	 *            - The ClassAd expression to be returned as a readable String
	 * @return - The readeable String that represents the ClassAd expression.
	 */
	public String exprToReadeableString(Expr expr) 
	{
		StringWriter ow = new StringWriter();
		ClassAdWriter caw = new ClassAdWriter(ow);
		caw.setFormatFlags(ClassAdWriter.READABLE);
		caw.println(expr);
		caw.flush();

		return ow.toString();
	}

	/**
	 * This method transforms a ClassAd expression into a readeableString.
	 * 
	 * @param String - The String representing the ClassAd expression to be returned as a readeable String
	 * @return - The readeable String that represents the ClassAd expression.
	 */
	public String exprToReadeableString(String s) 
	{
		try 
		{
			return exprToReadeableString(string2Expr(s));
		} 
		catch (ClassAdUtilitiesException e) 
		{
			e.printStackTrace();
			return	"The String did not represent a valid Expression";
		}
	}

	/**
	 * 
	 * This method transforms a String into a ClassAd expression. If the String
	 * does not represent a valid ClassAd expression a ClassAdUtilitiesException
	 * is raised.
	 * 
	 * @param string
	 *            - The String that has to be transformed into the ClassAd
	 *            expression.
	 * @return - The ClassAd expression.
	 * @throws ClassAdUtilitiesException
	 */
	public Expr string2Expr(String string) throws ClassAdUtilitiesException {
		/*
		 * If the string is null it cannot be converted into a valid ClassAd
		 * expression, an exception is raised.
		 */
		if (string == null)
			throw new ClassAdUtilitiesException();
		/*
		 * Converts the string into a ClassAd expression
		 */
		Expr expr = null;
		try {
			parser = new ClassAdParser(string);
			expr = parser.parse();
		} catch (RuntimeException e) {
			System.out
					.println("*******************************************************************");
			System.out
					.println("** ClassAdUtilities.string2Expr() raised the following Exception **");
			System.out
					.println("** while processing :                                            **");
			System.out
					.println("*******************************************************************");
			System.out.println(string);
			System.out
					.println("*******************************************************************");
			e.printStackTrace();
			throw new ClassAdUtilitiesException();
		}
		/*
		 * Returns the expression
		 */
		return expr;
	}

	/**
	 * 
	 * This method transforms a String into a Complete ClassAd Record
	 * Expression. If the String does not represent a valid ClassAd expression a
	 * ClassAdUtilitiesException is raised. If the String does not contain a
	 * Rank and Requirements field, than standard Rank and Requirements are
	 * added by the invocation to the function completeClassAd. Requirements =
	 * true; Rank = 1.0;
	 * 
	 * @param string
	 *            - The String that has to be transformed into the Complete
	 *            ClassAd Record Expression.
	 * @return - The ClassAd Record Expression.
	 * @throws ClassAdUtilitiesException
	 */
	public RecordExpr string2CompleteRecordExpr(String string)
			throws ClassAdUtilitiesException {
		/*
		 * Check that the string is not null and describes a proper
		 * RecordExpression
		 */
		if (string == null)
			throw new ClassAdUtilitiesException();
		RecordExpr re = (RecordExpr) string2Expr(string);
		if (re == null)
			throw new ClassAdUtilitiesException();

		re = completeClassAd(re);

		return re;
	}

	/*
	 * 
	 * Manipulators - Manipulate ClassAd expressions.
	 */
	/**
	 * 
	 * This method transforms a RecordExpr in a complete ClassAd Record
	 * Expression. If the Expression does not contain a Rank and Requirements
	 * field, than standard Rank and Requirements are added. Requirements =
	 * true; Rank = 1.0;
	 * 
	 * @param RecordExpr
	 *            - The RecordExpr that has to be transformed into the Complete
	 *            ClassAd Record Expression.
	 * @return - The Complete ClassAd Record Expression.
	 */
	private RecordExpr completeClassAd(RecordExpr er) {
		/*
		 * If er does not contain the field "Requirements", add the standard one
		 * (true).
		 */
		if (er.lookup("Requirements") == null)
			er.insertAttribute("Requirements", Constant.TRUE);
		/*
		 * If er does not contain the field "Rank", add the standard one (1).
		 */
		if (er.lookup("Rank") == null)
			er.insertAttribute("Rank", Constant.getInstance(1));

		return er;
	}

	/*
	 * 
	 * Matchers - Match ClassAd expressions.
	 */
	/**
	 * 
	 * This method returns true if the two strings passed as arguments represent
	 * two ClassAd that match.
	 * 
	 * @param s1
	 *            , s2 - The strings that represent the ClassAds that are to be
	 *            matched.
	 * @return - true if s1 and s2 match, false otherwise.
	 * @throws ClassAdUtilitiesException
	 */
	public boolean doMatch(String s1, String s2)
			throws ClassAdUtilitiesException {
		RecordExpr r1 = (RecordExpr) string2Expr(s1);
		RecordExpr r2 = (RecordExpr) string2Expr(s2);

		return doMatch(r1, r2);
	}

	/**
	 * 
	 * This method returns true if the two ClassAds passed match.
	 * 
	 * @param r1
	 *            , r2 - The ClassAds that are to be matched.
	 * @return - true if r1 and r2 match, false otherwise.
	 * @throws ClassAdUtilitiesException
	 */
	public boolean doMatch(RecordExpr r1, RecordExpr r2) {
		return (ClassAd.match(r1, r2) != null);
	}

	/**
	 * 
	 * This method returns the match of the two strings passed as arguments that
	 * represent two ClassAd that match.
	 * 
	 * @param s1
	 *            , s2 - The strings that represent the ClassAds that are to be
	 *            matched.
	 * @return - the integer array that represents the match.
	 * @throws ClassAdUtilitiesException
	 */
	public int[] match(String s1, String s2) throws ClassAdUtilitiesException {
		RecordExpr r1 = (RecordExpr) string2Expr(s1);
		RecordExpr r2 = (RecordExpr) string2Expr(s2);

		return match(r1, r2);
	}

	/**
	 * 
	 * This method returns the match of the two ClassAds passed as arguments.
	 * 
	 * @param r1
	 *            , r2 - The two ClassAds that are to be matched.
	 * @return - the integer array that represents the match.
	 * @throws ClassAdUtilitiesException
	 */
	public int[] match(RecordExpr r1, RecordExpr r2) {
		return ClassAd.match(r1, r2);
	}

	/**
	 * 
	 * This method evaluates the selection in e1, where e2 represents "other".
	 * 
	 * @param selection
	 *            - The selection, e1 - the RecordExpr where the selection is
	 *            evaluated e2 - the RecordExpr that represents "other" in the
	 *            evaluation
	 * @return - boolean if the two values match.
	 * @throws ClassAdUtilitiesException
	 * @throws ClassAdUtilitiesException
	 */
	public Expr evaluate(String sel, RecordExpr r) throws ClassAdUtilitiesException {
		/*
		 * Check what is the selection
		 */
		Expr e = string2Expr(sel);
		// System.out.println(expr2String(e) + " of type " + e.getClass());
		/*
		 * If it is a selection
		 */
		if (e.getClass().equals(AttrRef.class)) 
		{
			/*
			 * If the selection is not present, return error
			 */
			if (lookup(sel, r) == null)
				return Constant.Error;
			/*
			 * Check if the selection contains a dot "."
			 */
			else if (sel.contains(".")) 
			{
				/*
				 * Transforming the string into an array
				 */
				String postfix = sel;
				String prefix = null;
				Vector<String> tmp = new Vector<String>();

				while (postfix.contains(".")) {
					prefix = postfix.substring(0, postfix.indexOf("."));
					postfix = postfix.substring(postfix.indexOf(".") + 1,
							postfix.length());
					tmp.add(prefix);
				}
				tmp.add(postfix);
				String[] newSel = new String[tmp.size()];
				tmp.copyInto(newSel);
				return ClassAd.eval(r, newSel);
			} else {
				return ClassAd.eval(r, sel);
			}
		}
		/*
		 * Else, the selection is not an attribute reference
		 */
		else
			return ClassAd.eval("GabTest", e, r);
	}

	/**
	 * 
	 * This method looks up for a selection in r
	 * 
	 * @param selection
	 *            - The selection, r - the RecordExpr where the selection is
	 *            evaluated
	 * @return - The expr, if present, null otherwise.
	 * @throws ClassAdUtilitiesException
	 */
	Expr lookup(String sel, RecordExpr r) {
		/*
		 * Check if the selection contains a dot "."
		 */
		if (sel.contains(".")) {
			String prefix = sel.substring(0, sel.indexOf("."));
			String postfix = sel.substring(sel.indexOf(".") + 1, sel.length());

			Expr e = r.lookup(prefix);
			/*
			 * Check if the Expression selected by prefix is a RecordExpression
			 */
			if (e == null)
				return null;
			if (e.getClass() == RecordExpr.class)
				return lookup(postfix, (RecordExpr) r.lookup(prefix));
			else
				return null;
		} else {
			return r.lookup(sel);
		}
	}
	
	public boolean isUndefinedValue(Expr e)
	{
		return e.toString().equals("UNDEFINED");
	}
}
