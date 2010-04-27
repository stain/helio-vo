package org.egso.votable;

import org.egso.votable.element.VOTableRoot;
import java.io.File;
import java.util.List;

public class Test {
	
	
	private static final String defaultFilename = "results.xml";
	
	public Test(String filename) {
		test(filename);
	}
	
	@SuppressWarnings("unchecked")
  private void test(String filename) {
		System.out.println("Parsing VOTable file..");
		VOTableRoot votable = null;
		try {
			VOTableFactory votf = VOTableFactory.newInstance();
			File file = new File(filename);
			votable = votf.createVOTable(file);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Field names:");
		printList(votable.getFieldNames());
//		System.out.println("Get all values for duration:");
//		printList(votable.getAllValues("file_location"));
//		System.out.println("Get all rows:");
//		printListOfList(votable.getAllRows());
		System.out.println("STEP 4");
		printListOfList(votable.getRows("instrument", "KITT Peak vacuum telescope spectromagnetograph"));
		System.out.println("\nSTEP 6: " + votable.getNumberOfRows());
		List<String> l = votable.getFieldNames();

		System.out.println("\nSTEP 7: Field Type test.");
		for (String tmp:l) {
			int x = votable.getFieldType(tmp);
			System.out.println(tmp + " -> " + x + " : " + ((x == -1) ? "NULL" : VOTableConstants.DATA_TYPES[x]));
		}
	}
	
	private void printList(List<String> list) {
		System.out.println("CONTENT OF THE LIST:") ;
		if (list == null) {
			System.out.println("EMPTY LIST!");
		} else {
			for(String s:list) {
				System.out.print("'" + s + "' ");
			}
			System.out.println();
		}
	}
	
	private void printListOfList(List<List<String>> list) {
		System.out.println("CONTENT OF THE LIST OF LIST:") ;
		if (list == null) {
			System.out.println("EMPTY LIST!");
		} else {
			for (int i = 0 ; i < list.size() ; i++) {
				System.out.println("ROW NUMBER " + i + ":");
				for (String s:list.get(i)) {
					System.out.print("'" + s + "' ");
				}
				System.out.println();
			}
		}
	}
	
	public static void main(String[] args) {
		if (args.length == 1) {
			new Test(args[0]);
		} else {
			new Test(defaultFilename);
		}
	}
}

