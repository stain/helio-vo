package org.egso.provider.query;

import java.util.Arrays;
import java.util.Vector;
import org.egso.common.context.EGSOContext;
import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.utils.ProviderUtils;

/**
 * The Provider Table is used to store all results from a query execution. It
 * mainly contains a table where each row is a result and each column is a
 * result.
 * 
 * @author Romain Linsolas (linsolas@gmail.com)
 * @version 1.1 - 07/04/2004 [20/11/2003]
 */
/*
 * 1.3 - 05/11/2004: Cleaning the file, removing all
 * information/warning/exception (already handled by the EGSOContext). 1.2 -
 * 17/06/2004: Incorporation of the EGSOContext. 1.1 - 07/04/2004: New XML
 * message format for results (not the only-VOTable anymore).
 */
public class ProviderTable {

    /**
     * The Vector contains all results (Vector of Vector of String).
     */
    private Vector<Vector<String>> results = null;

    /**
     * Vector containing the list of field names.
     */
    private Vector<String> fields = null;

    private EGSOContext context = null;

    private boolean[] availabilities = null;

    private int numberOfNAFields = 0;

    /**
     * Constructor.
     * 
     * @param paramNames
     *            Names of parameters of selected fields in the query.
     */
    public ProviderTable(EGSOContext cxt, Vector<String> paramNames) {
        context = cxt;
        fields = paramNames;
        availabilities = new boolean[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            availabilities[i] = true;
        }
        numberOfNAFields = 0;
        results = new Vector<Vector<String>>();
    }

    public EGSOContext getContext() {
        return (context);
    }

    public int getNumberOfNAFields() {
        return (numberOfNAFields);
    }

    public void setContext(EGSOContext cxt) {
        context = cxt;
    }

    public void notAvailableField(String name) {
        setFieldAvailability(name, false);
    }

    public void setFieldAvailability(String name, boolean available) {
        System.out.println("NOT AVAILABLE FIELD > " + name);
        if (!name.equals("IDArchive")) {
            boolean found = false;
            int i = 0;
            while (!found && (i < fields.size())) {
                found = name.equals((String) fields.get(i));
                i++;
            }
            if (found) {
                i--;
                // If it was TRUE and it is going to be FALSE, then increase
                // numberOfNAFields
                if (availabilities[i] && !available) {
                    numberOfNAFields++;
                }
                // If it was FALSE and it is going to be TRUE, then decrease
                // numberOfNAFields
                if (!availabilities[i] && available) {
                    numberOfNAFields--;
                }
                availabilities[i] = available;
            }
        }
    }

    public boolean isAvailableField(String name) {
        boolean found = false;
        int i = 0;
        while (!found && (i < fields.size())) {
            found = name.equals((String) fields.get(i));
            i++;
        }
        if (found) {
            found = availabilities[i - 1];
        }
        return (found);
    }

    /**
     * Returns all results.
     * 
     * @return All results.
     */
    public Vector<Vector<String>> getResults() {
        return (results);
    }

    /**
     * Returns all selected fields.
     * 
     * @return All selected fields.
     */
    public Vector<String> getFields() {
        return (fields);
    }

    /**
     * 
     * 
     * @param index
     *            JAVADOC: Description of the Parameter
     * @return JAVADOC: The field value
     * @exception ArrayIndexOutOfBoundsException
     *                JAVADOC: Description of the Exception
     */
    public String getField(int index) throws ArrayIndexOutOfBoundsException {
        String tmp = null;
        try {
            tmp = (String) fields.get(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            ProviderMonitor.getInstance().reportException(e);
            throw e;
        }
        return (tmp);
    }

    /**
     * JAVADOC: Gets the result attribute of the ProviderTable object
     * 
     * @param index
     *            JAVADOC: Description of the Parameter
     * @return JAVADOC: The result value
     * @exception ArrayIndexOutOfBoundsException
     *                JAVADOC: Description of the Exception
     */
    public Vector<String> getResult(int index) throws ArrayIndexOutOfBoundsException {
        Vector<String> tmp = null;
        try {
            tmp = (Vector<String>) results.get(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            ProviderMonitor.getInstance().reportException(e);
            throw e;
        }
        return (tmp);
    }

    /**
     * JAVADOC: Gets the value attribute of the ProviderTable object
     * 
     * @param row
     *            JAVADOC: Description of the Parameter
     * @param column
     *            JAVADOC: Description of the Parameter
     * @return JAVADOC: The value value
     * @exception ArrayIndexOutOfBoundsException
     *                JAVADOC: Description of the Exception
     */
    public String getValue(int row, int column)
            throws ArrayIndexOutOfBoundsException {
        String tmp = null;
        try {
            tmp = (String) ((Vector<String>) results.get(row)).get(column);
        } catch (ArrayIndexOutOfBoundsException e) {
            ProviderMonitor.getInstance().reportException(e);
            throw e;
        }
        return (tmp);
    }

    /**
     * JAVADOC: Gets the numberOfResults attribute of the ProviderTable object
     * 
     * @return JAVADOC: The numberOfResults value
     */
    public int getNumberOfResults() {
        return (results.size());
    }

    public String createMessage(boolean withContext) {
        StringBuffer sb = new StringBuffer();
        if (withContext) {
            sb.append("<egso version=\"1.1\">");
            sb.append(context.toXML());
        }
        sb.append("<VOTABLE version=\"1.2\"><DESCRIPTION>EGSO Query Results - "
                + results.size() + " result(s) [" + ProviderUtils.getDate()
                + "].</DESCRIPTION>");
        sb.append("<RESOURCE type=\"results\"><TABLE>");
        for (String field:fields) {
            sb.append("<FIELD name=\"" + field + "\"/>");
        }
        sb.append("<DATA><TABLEDATA>");
        for (Vector<String> v:results)
        {
            sb.append("<TR>");
            for (String cell:v) {
                sb.append("<TD>" + cell + "</TD>");
            }
            sb.append("</TR>");
        }
        sb.append("</TABLEDATA></DATA></TABLE></RESOURCE></VOTABLE>");
        if (withContext) {
            sb.append("</egso>");
        }
        return (sb.toString());
    }

    /**
     * JAVADOC: Adds a feature to the Result attribute of the ProviderTable
     * object
     * 
     * @param result
     *            JAVADOC: The feature to be added to the Result attribute
     * @exception IllegalProviderTableOperationException
     *                JAVADOC: Description of the Exception
     */
    public void addResult(Vector<String> result)
            throws IllegalProviderTableOperationException {
        if (fields.size() == result.size()) {
            results.add(result);
        } else {
            if ((result.size() + numberOfNAFields) != fields.size()) {
                System.out.print("FIELDS> ");
                for (String field:fields) {
                    System.out.print("{" + field + "} ");
                }
                System.out.print("\nVALUES> ");
                for (String field:result) {
                    System.out.print("{" + field + "} ");
                }
                System.out.println();
                throw (new IllegalProviderTableOperationException(
                        "Only result with "
                                + fields.size()
                                + " fields can be added to this ProviderTable (Here there are "
                                + result.size() + " result(s) + "
                                + numberOfNAFields + " of N/A field(s))."));
            } else {
                Vector<String> tmp = new Vector<String>();
                int j = 0;
                for (int i = 0; i < fields.size(); i++) {
                    if (availabilities[i]) {

                        tmp.add(result.get(j++));

                    } else {
                        tmp.add("N/A");
                    }
                }
                results.add(tmp);
            }
        }

    }

    /**
     * JAVADOC: Description of the Method
     * 
     * @param table2
     *            JAVADOC: Description of the Parameter
     * @exception IllegalProviderTableOperationException
     *                JAVADOC: Description of the Exception
     */
    public void merge(ProviderTable table2)
            throws IllegalProviderTableOperationException {
        Vector<String> fields2 = table2.getFields();
        if (fields.size() != fields2.size()) {
            throw (new IllegalProviderTableOperationException(
                    "Two ProviderTables can be merged only if they have the same number of fields."));
        }
        int[] indexes = new int[fields.size()];
        String field = null;
        // Check the fields for both ProviderTables and create the array indexes
        // that maps indexes between each field in the two ProviderTables.
        for (int i = 0; i < fields2.size(); i++) {
            field = (String) fields2.get(i);
            int x = fields.indexOf(field);
            if (x == -1) {
                // This field is not present in table. Stop the merging
                // operation...
                throw (new IllegalProviderTableOperationException("The field '"
                        + field + "' is not present in the main ProviderTable."));
            } else {
                // The i-th element of the table2 is the x-th element of the
                // table.
                indexes[i] = x;
            }
        }
        Vector<String> result = null;
        Vector<String> tmp = null;
        // For each result in table2...
        for (int i = 0; i < table2.getNumberOfResults(); i++) {
            result = new Vector<String>();
            tmp = (Vector<String>) table2.getResult(i);
            for (int j = 0; j < tmp.size(); j++) {
                result.add((String) tmp.get(j));
            }
            results.add(result);
        }
    }

    /**
     * Allow the merging of heterogeneous ProviderTable. This means that no more
     * tests are done on the fields. If a field is present in one ProviderTable,
     * then we add this field in the other ProviderTable, and all values related
     * to this field is N/A in the second ProviderTable.
     * 
     * @param table2
     *            All results from this ProviderTable must be merged with the
     *            results from the current ProviderTable.
     */
    public void mergeForced(ProviderTable table2) {
        Vector<String> allFields = new Vector<String>();
        allFields.addAll(fields);
        Vector<String> fields2 = table2.getFields();
        String tmp = null;
        // i-th integer in indexes maps the i-th element of allFields and the
        // indexes[i]-th element in a result in table2. If it is equal to -1,
        // then the allFields.get(i) must be added to table2.
        int[] indexes = new int[allFields.size()];
        Arrays.fill(indexes, -1);
        for (int i = 0; i < fields2.size(); i++) {
            tmp = (String) fields2.get(i);
            int x = allFields.indexOf(tmp);
            if (x == -1) {
                // New field. To do: 1. Add this field in the fields list of the
                // current ProviderTable (filled with N/A values) and add
                allFields.add(tmp);
                addNewField(tmp);
                // Increase the size of indexes by one.
                int[] tmp2 = new int[indexes.length + 1];
                System.arraycopy(indexes, 0, tmp2, 0, indexes.length);
                tmp2[tmp2.length - 1] = i;
                indexes = tmp2;
            } else {
                indexes[i] = x;
            }
        }
        // Now, add all table2 values in the current ProviderTable.
        // TO BE CONTINUED...
    }

    /**
     * Add a new field and add the value "N/A" in all results.
     * 
     * @param field
     *            Name of the field.
     */
    private void addNewField(String field) {
        fields.add(field);
        for (Vector<String> v:results) {
            v.add("N/A");
        }
    }

    /**
     * JAVADOC: Description of the Method
     * 
     * @return JAVADOC: Description of the Return Value
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("FIELDS = {"
                + (String) fields.firstElement());
        for (int i = 1; i < fields.size(); i++) {
            sb.append(", " + (String) fields.get(i));
        }
        sb.append("}\n");
        for (Vector<String> v:results)
        {
            sb.append("[" + v.firstElement());
            for (int i = 1; i < v.size(); i++) {
                sb.append(", " + v.get(i));
            }
            sb.append("]\n");
        }
        return (sb.toString());
    }

}

