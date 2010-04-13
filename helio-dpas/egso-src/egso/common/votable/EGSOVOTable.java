package org.egso.common.votable;

import cds.savot.model.SavotResource;
import cds.savot.model.SavotTable;
import cds.savot.model.SavotTR;
import cds.savot.model.SavotTD;
import cds.savot.model.SavotVOTable;
import cds.savot.model.SavotField;
import cds.savot.model.ResourceSet;
import cds.savot.model.TRSet;
import cds.savot.writer.SavotWriter;

import java.io.ByteArrayOutputStream;
import java.util.Vector;
import java.util.Iterator;
import java.util.List;


/**
 * The object containing the VOTable, as well as some methods for VOTable
 * manipulation.
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.0.1 - 04/11/2004
 */
/*
1.0.1 - 04/11/2004:
	Test of NullPointerException in getAllRows() method.
1.0 - 06/10/2004:
	First release.
*/
public class EGSOVOTable extends SavotVOTable {

	/**
	 * The VOTable element (created by SAVOT).
	 */
	private SavotVOTable votable = null;


	/**
	 * Builds a new EGSOVOTable from a SavotVOTable object.
	 *
	 * @param vot  The VOTable object.
	 */
	EGSOVOTable(SavotVOTable vot) {
		votable = vot;
	}


	/**
	 * Get all field names.
	 *
	 * @return   List (eventually empty) of field names (as String).
	 */
	@SuppressWarnings("unchecked")
  public List<String> getFieldNames() {
		ResourceSet set = votable.getResources();
		Vector<String> fields = new Vector<String>();
		if (set.getItemCount() == 0) {
			return ((List<String>) fields);
		}
		SavotResource res = (SavotResource) set.getItemAt(0);
		if (res.getTableCount() == 0) {
			return ((List<String>) fields);
		}
		SavotTable table = (SavotTable) res.getTables().getItemAt(0);
		for (Iterator<SavotField> it = table.getFields().getItems().iterator(); it.hasNext(); ) {
			fields.add(((SavotField) it.next()).getName());
		}
		return ((List<String>) fields);
	}


	/**
	 * Get all values for a given field name.
	 *
	 * @param fieldName  Name of the field.
	 * @return           A List (eventually empty) of values (String).
	 */
	@SuppressWarnings("unchecked")
  public List<String> getAllValues(String fieldName) {
		ResourceSet set = votable.getResources();
		Vector<String> values = new Vector<String>();
		if (set.getItemCount() == 0) {
			return ((List<String>) values);
		}
		SavotResource res = null;
		SavotTable table = null;
		// For each Resource...
		for (Iterator<SavotResource> it = set.getItems().iterator(); it.hasNext(); ) {
			res = (SavotResource) it.next();
			// For each Table...
			for (Iterator<SavotTable> it2 = res.getTables().getItems().iterator(); it2.hasNext(); ) {
				table = (SavotTable) it2.next();
				// Find the index of the fieldName, if it exists in the FIELDs.
				int index = -1;
				boolean found = false;
				Iterator<SavotField> it3 = table.getFields().getItems().iterator();
				while (!found && it3.hasNext()) {
					found = ((SavotField) it3.next()).getName().equals(fieldName);
					index++;
				}
				// If this field exists, find all values (contained in TR/TD[index]).
				if (found) {
					for (Iterator<SavotTR> it4 = table.getData().getTableData().getTRs().getItems().iterator(); it4.hasNext(); ) {
						values.add(((SavotTD) ((SavotTR) it4.next()).getTDs().getItemAt(index)).getContent());
					}
				}
			}
		}
		return ((List<String>) values);
	}


	/**
	 * Get all rows of the &lt;TABLEDATA&gt;, i.e. all data contained in the
	 * VOTable.
	 *
	 * @return   A List (eventually empty) of Lists of String. Each List is a row,
	 *      each String in a row is a value.
	 */
	@SuppressWarnings("unchecked")
  public List<List<String>> getAllRows() {
		Vector<List<String>> values = new Vector<List<String>>();
		ResourceSet set = votable.getResources();
		if (set == null) {
			return ((List<List<String>>) values);
		}
		Vector<String> row = new Vector<String>();
		if (set.getItemCount() == 0) {
			return ((List<List<String>>) values);
		}
		SavotResource res = null;
		SavotTable table = null;
		// For each Resource...
		try {
			for (Iterator<SavotResource> it = set.getItems().iterator(); it.hasNext(); ) {
				res = (SavotResource) it.next();
				// For each Table...
				for (Iterator<SavotTable> it2 = res.getTables().getItems().iterator(); it2.hasNext(); ) {
					table = (SavotTable) it2.next();
					// For each TR...
					for (Iterator<SavotTR> it3 = table.getData().getTableData().getTRs().getItems().iterator(); it3.hasNext(); ) {
						row = new Vector<String>();
						// For each TD in this TR...
						for (Iterator<SavotTD> it4 = ((SavotTR) it3.next()).getTDs().getItems().iterator(); it4.hasNext(); ) {
							row.add(((SavotTD) it4.next()).getContent());
						}
						values.add((List<String>) row);
					}
				}
			}
		} catch (NullPointerException npe) {
			return ((List<List<String>>) values);
		}
		return ((List<List<String>>) values);
	}


	/**
	 * Get the 'row'-th row (or results) on the VOTable.
	 *
	 * @param row                              Row number.
	 * @return                                 List (eventually empty) of values
	 *      (String) for the 'row'-th row.
	 * @throws ArrayIndexOutOfBoundsException  index is < 0 or greater than the
	 *      number of rows.
	 */
	@SuppressWarnings("unchecked")
  public List<String> getRow(int row) throws ArrayIndexOutOfBoundsException {
		if (row < 0) {
			throw (new ArrayIndexOutOfBoundsException("The index can't be negative"));
		}
		ResourceSet set = votable.getResources();
		Vector<String> values = new Vector<String>();
		if (set.getItemCount() == 0) {
			return ((List<String>) values);
		}
		SavotResource res = null;
		SavotTable table = null;
		TRSet trs = null;
		// For each Resource...
		for (Iterator<SavotResource> it = set.getItems().iterator(); it.hasNext(); ) {
			res = (SavotResource) it.next();
			// For each Table...
			for (Iterator<SavotTable> it2 = res.getTables().getItems().iterator(); it2.hasNext(); ) {
				table = (SavotTable) it2.next();
				// For each TR...
				trs = table.getData().getTableData().getTRs();
				if (row >= trs.getItemCount()) {
					throw (new ArrayIndexOutOfBoundsException("Can't reach the " + row + "-th element of the row (last index=" + (trs.getItemCount() - 1) + ")."));
				}
				for (Iterator<SavotTD> it3 = ((SavotTR) trs.getItemAt(row)).getTDs().getItems().iterator(); it3.hasNext(); ) {
					values.add(((SavotTD) it3.next()).getContent());
				}
			}
		}
		return ((List<String>) values);
	}


	/**
	 * Get all values where fieldName=fieldValue (fieldValue is case insensitive).
	 *
	 * @param fieldName   Name of the field.
	 * @param fieldValue  Value of the field.
	 * @return            List (eventually empty) of List of String. Each List is a
	 *      row (a result), each String is a value.
	 */
	public List<List<String>> getRows(String fieldName, String fieldValue) {
		return (getRows(fieldName, fieldValue, false));
	}


	/**
	 * Get all values where fieldName=fieldValue. The fieldValue value can be case
	 * sensitive or not.
	 *
	 * @param fieldName        Name of the field.
	 * @param fieldValue       Value of the field.
	 * @param isCaseSensitive  Boolean that specifies if the fieldValue is case
	 *      sensitive or not.
	 * @return                 List (eventually empty) of List of String. Each List
	 *      is a row (a result), each String is a value.
	 */
	@SuppressWarnings("unchecked")
  public List<List<String>> getRows(String fieldName, String fieldValue, boolean isCaseSensitive) {
		ResourceSet set = votable.getResources();
		Vector<List<String>> values = new Vector<List<String>>();
		Vector<String> row = null;
		if (set.getItemCount() == 0) {
			return ((List<List<String>>) values);
		}
		SavotResource res = null;
		SavotTable table = null;
		SavotTR tr = null;
		String tmp = null;
		// For each Resource...
		for (Iterator<SavotResource> it = set.getItems().iterator(); it.hasNext(); ) {
			res = (SavotResource) it.next();
			// For each Table...
			for (Iterator<SavotTable> it2 = res.getTables().getItems().iterator(); it2.hasNext(); ) {
				table = (SavotTable) it2.next();
				// Find the index of the fieldName, if it exists in the FIELDs.
				int index = -1;
				boolean found = false;
				Iterator<SavotField> it3 = table.getFields().getItems().iterator();
				while (!found && it3.hasNext()) {
					found = ((SavotField) it3.next()).getName().equals(fieldName);
					index++;
				}
				if (found) {
					for (Iterator<SavotTR> it4 = table.getData().getTableData().getTRs().getItems().iterator(); it4.hasNext(); ) {
						row = new Vector<String>();
						tr = (SavotTR) it4.next();
						tmp = ((SavotTD) tr.getTDs().getItemAt(index)).getContent();
						if ((isCaseSensitive && tmp.equals(fieldValue)) || (!isCaseSensitive && tmp.equalsIgnoreCase(fieldValue))) {
							for (Iterator<SavotTD> it5 = tr.getTDs().getItems().iterator(); it5.hasNext(); ) {
								row.add(((SavotTD) it5.next()).getContent());
							}
							values.add((List<String>) row);
						}
					}
				}
			}
		}
		return ((List<List<String>>) values);
	}


	/**
	 * Returns the number of results contained in the whole VOTable (it is possible
	 * that the VOTable contains more than one &lt;TABLEDATA&gt; node).
	 *
	 * @return   Number of rows in &lt;TABLEDATA&gt; (i.e. results).
	 */
	@SuppressWarnings("unchecked")
  public int getNumberOfRows() {
		ResourceSet set = votable.getResources();
		if (set.getItemCount() == 0) {
			return (0);
		}
		int num = 0;
		SavotResource res = null;
		SavotTable table = null;
		// For each Resource...
		for (Iterator<SavotResource> it = set.getItems().iterator(); it.hasNext(); ) {
			res = (SavotResource) it.next();
			// For each Table...
			for (Iterator<SavotTable> it2 = res.getTables().getItems().iterator(); it2.hasNext(); ) {
				table = (SavotTable) it2.next();
				// For each TR...
				num += table.getData().getTableData().getTRs().getItemCount();
			}
		}
		return (num);
	}


	/**
	 * String representation of the VOTable.
	 *
	 * @return   String representation of the VOTable.
	 */
	public String toString() {
		SavotWriter wd = new SavotWriter();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		wd.generateDocument(votable, output);
		return (output.toString().replaceAll("\n", ""));
	}

}

