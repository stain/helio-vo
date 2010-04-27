package org.egso.provider.datamanagement.connector;

import java.util.Vector;
import org.egso.provider.datamanagement.archives.Archive;
import org.egso.provider.datamanagement.archives.mapping.MappingObject;
import org.egso.provider.datamanagement.archives.mapping.MappingTable;
import org.egso.provider.query.ProviderTable;

/**
 * JAVADOC: Description of the Class
 * 
 * @author Romain Linsolas
 * @version
 */
public class ResultsFormatter {

    private Archive archive = null;

    private MappingTable mappingTable = null;

    private Vector<Vector<String>> finalResults = null;

    private Vector<Object> fields = null;

    private String[] fieldnames = null;

    private int numberOfFields = 0;

    private int indexOfInstrumentField = -1;

    private boolean ftphttp = false;

    private boolean sql = false;

    @SuppressWarnings("unused")
    private boolean webservices = false;

    /**
     * JAVADOC: Constructor for the ResultsFormatter object
     */
    public ResultsFormatter(Archive arc, Vector<String> selected) {
        archive = arc;
        ftphttp = archive.isFTP() || archive.isHTTP();
        sql = archive.isSQL();
        webservices = archive.isWebServices();
        mappingTable = archive.getMappingTable();
        finalResults = new Vector<Vector<String>>();
        fields = new Vector<Object>();
        fieldnames = new String[selected.size()];
        numberOfFields = 0;
        MappingObject map = null;
        int i = 0;
        int x = 0;
        for (String tmp:selected) {
            fieldnames[i++] = tmp;
            if (tmp.equals("IDArchive")) {
                fields.add(archive.getID());
            } else {
                if (tmp.equals("observatory")) {
                    fields.add(tmp);
                } else {
                    if (tmp.equals("instrument")) {
                        indexOfInstrumentField = x;
                    }
                    if (ftphttp
                            && (tmp.equals("filename")
                                    || tmp.equals("filesize") || tmp
                                    .equals("link"))) {
                        fields.add(tmp);
                    } else {
                        //	if (ftphttp && tmp.endsWith("date")) {
                        if (ftphttp && tmp.startsWith("time")) {
                            fields.add("DATE");
                        } else {
                            x++;
                            map = mappingTable.getFromEGSO(tmp);
                            if (map == null) {
                                fields.add("N/A");
                            } else {
                                fields.add(map);
                                numberOfFields++;
                            }
                        }
                    }
                }
            }
        }
        /*
         * System.out.print("-- <( VERIFICATION )>--\nIndex of Instrument: " +
         * indexOfInstrumentField + "\nFIELDS: "); for (int j = 0 ; j <
         * fieldnames.length ; j++) { System.out.print("'" + fieldnames[j] + "'
         * "); } System.out.println("\nMAPPING OBJECTS:"); Object obj = null;
         * for (Iterator it = fields.iterator() ; it.hasNext() ; ) { obj =
         * it.next(); if (obj instanceof String) { System.out.println("\t" +
         * obj); } else { System.out.println("\tEGSO name = " + ((MappingObject)
         * obj).getEGSOName()); } } System.out.println("\n\n");
         */
    }

    public int getNumberOfQueriedFields() {
        return (numberOfFields);
    }

    public void addResults(String[] tmp) {
        Vector<String> v = new Vector<String>();
        int i = 0;
        for (Object obj:fields)
        {
            if (obj instanceof String) {
                String s = (String) obj;
                if (s.equals("observatory")) {
                    v.add((indexOfInstrumentField == -1) ? "???" : archive
                            .getObservatory(tmp[indexOfInstrumentField]));
                } else {
                    if (ftphttp) {
                        if (s.equals("link")) {
                            v.add(tmp[0]);
                        } else {
                            if (s.equals("filename")) {
                                v.add(tmp[1]);
                            } else {
                                if (s.equals("filesize")) {
                                    v.add(tmp[2]);
                                } else {
                                    if (s.equals("DATE")) {
                                        String d = mappingTable.getFromEGSO(
                                                "DATE").archive2egso(tmp[1]);
                                        String t = mappingTable.getFromEGSO(
                                                "TIME").archive2egso(tmp[1]);
                                        if (t.length() == 5) {
                                            t += ":00";
                                        }
                                        v.add(d + " " + t);
                                    } else {
                                        v.add(s);
                                    }
                                }
                            }
                        }
                    } else {
                        v.add(s);
                    }
                }
            } else {
                // s = ((MappingObject) obj).archive2egso(tmp[1]);
                // This was Romain's code but it didnt work for SQL-BASS2000
                // It returned the instrument index not name

                String s = ((MappingObject) obj).archive2egso(tmp[i]);

                //    v.add((s != null) ? s : tmp[i]);

                if (s != null) {
                    if (s.equals("ftp")) {
                        v.add("NORH");
                    } else {
                        v.add(s);
                    }
                } else {
                    v.add(tmp[i]);
                }

                i++;
                if (ftphttp
                        && ((MappingObject) obj).getEGSOName().equals(
                                "instrument")) {
                    int n = v.indexOf("???");
                    if (n != -1) {
                        v.insertElementAt(archive.getObservatory(s), n);
                        v.remove("???");
                    }
                }
            }
        }
        if (sql && (i != tmp.length)) {
            System.out.println("ERROR !!! Only " + i
                    + " values has been handled, instead of " + tmp.length);
        }
        /*
         * System.out.print("\n[RF]> [" ); for (int j = 0 ; j < tmp.length ;
         * j++) { System.out.print("'" + tmp[j] + "', "); }
         * System.out.print("]\n>>>> ["); for (Iterator it = v.iterator() ;
         * it.hasNext() ; ) { System.out.print("'" + ((String) it.next()) + "',
         * "); } System.out.println("]\n");
         */
        finalResults.add(v);
    }

    /**
     * Gives all mapped results to the ProviderTable object.
     * 
     * @param providerTable
     *            The ProviderTable that receives the results.
     */
    public void finish(ProviderTable providerTable)
    {
        for (Vector<String> v:finalResults)
            providerTable.addResult(v);
    }

}

