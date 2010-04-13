package org.egso.provider.datamanagement.connector;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.activation.DataHandler;

import org.egso.common.context.EGSOContext;
import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.datamanagement.archives.SQLArchive;
import org.egso.provider.query.ProviderTable;
import org.egso.provider.query.SQLQuery;

/**
 * TODO: Description of the Class
 * 
 * @author Romain Linsolas (linsolas@gmail.com)
 * @version 0.9 - 27/11/2003 [14/10/2003].
 */
public class SQLConnector implements Connector {

    private SQLQuery query = null;

    private SQLArchive archive = null;

    private ProviderTable results = null;

    private ResultsFormatter formatter = null;

    /**
     * TODO: Constructor for the DataPresentationManagerImpl object
     */
    public SQLConnector(SQLArchive arc, SQLQuery sql,
            ProviderTable providerTable, ResultsFormatter rf) {
        archive = arc;
        query = sql;
        results = providerTable;
        formatter = rf;
    }
    
    @Deprecated
    public SQLConnector(SQLArchive arc, SQLQuery sql,
            ProviderTable providerTable) {
        System.out
                .println("#######################################################");
        System.out
                .println("# MUST NOT BE USED ANYMORE - SQLConnector constructor #");
        System.out
                .println("#######################################################");
        archive = arc;
        query = sql;
        results = providerTable;
    }

    public void query() {
        System.out.println("  [SQL-C] Start query execution.");
        long startTime = System.currentTimeMillis();
        Connection con = createConnection();
        ResultSet set = null;
        int i = 0;
        try {
            PreparedStatement ps = con.prepareStatement(query.toString());
            set = ps.executeQuery();
            System.out.println("  [SQL-C] Query executed.");
            int nbF = formatter.getNumberOfQueriedFields();
            String[] tmp = null;
            boolean moreResults = set.next();
            while (moreResults && (i < archive.getMaximumResults())) {
                System.out.print("¤");
                tmp = new String[nbF];
                for (int col = 1; col <= nbF; col++) {

                    tmp[col - 1] = set.getString(col);

                }
                formatter.addResults(tmp);
                i++;
                moreResults = set.next();
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
        formatter.finish(results);
        float timeTaken = ((float) (System.currentTimeMillis() - startTime)) / 1000;
        System.out.println("\nFound " + i + " result" + ((i > 1) ? "s" : "")
                + ". Time taken = " + timeTaken + "s.");
        results.getContext().addParameter(
                "Query time for '" + archive.getID() + "' SQL archive",
                EGSOContext.PARAMETER_SYSTEMINFO, "" + timeTaken + " s");
    }

    /**
     * TODO: Description of the Method
     * 
     * @param query
     *            TODO: Description of the Parameter
     * @return TODO: Description of the Return Value
     */
    public void query2() {
        System.out.println("  [SQL-C] Start query execution.");
        Connection con = createConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query.toString());
            ResultSet set = ps.executeQuery();
            System.out.println("  [SQL-C] Query executed.");
            int i = 0;
            // Do not count IDArchive and link.
            int nbResults = results.getFields().size() - 1;
            int indexOfObs = results.getFields().indexOf("observingdomain");
            boolean moreResults = set.next();
            long startTime = System.currentTimeMillis();
            while (moreResults && (i < archive.getMaximumResults())) {
                System.out.print("@");
                Vector<String> res = new Vector<String>();
                res.add(archive.getID());
                res.add(archive.getObservatory());
                for (int col = 1; col < (nbResults - 1); col++) {
                    String tmp = set.getString(col);
                    if (tmp == null) {
                        res.add("N/A");
                    } else {
                        // Temporary solution before the creation of the
                        // ResultFilter class.
                        if (col == indexOfObs) {
                            res.add(getWL(Float.parseFloat(tmp)));
                        } else {
                            res.add(tmp);
                        }
                    }
                }
                //				res.add("egso://" + archive.getID() + "/file?name=" +
                // res.get(indexOfFileName) + "&amp;info=not_accessible_file");
                /*
                 * res.add("egso://" + archive.getID() + "/" +
                 * res.get(indexOfFileName) + "/not-accessible-file");
                 * System.out.print(i + "e RESULT:"); for (Iterator x =
                 * res.iterator() ; x.hasNext() ; ) { System.out.print((String)
                 * x.next() + " | "); } System.out.println();
                 */
                results.addResult(res);
                i++;
                moreResults = set.next();
            }
            System.out.println("\nFound " + i + " result"
                    + ((i > 1) ? "s" : "") + ". No more SQL results handled.");
            float timeTaken = ((float) (System.currentTimeMillis() - startTime)) / 1000;
            results.getContext().addParameter(
                    "Query time for '" + archive.getID() + "' SQL archive",
                    EGSOContext.PARAMETER_SYSTEMINFO, "" + timeTaken + " s");
        } catch (Throwable t) {
            ProviderMonitor.getInstance().reportException(t);
            System.out
                    .println("ERROR: Exception thrown during the execution of the query:");
        }
    }

    private String getWL(float x) {
        if (x < 0.25) {
            return ("Gamma rays");
        }
        if (x < 25) {
            return ("Hard X-Rays");
        }
        if (x < 100) {
            return ("Soft X-Rays");
        }
        if (x < 900) {
            return ("Extreme UltraViolet");
        }
        if (x < 3200) {
            return ("UltraViolet");
        }
        if (x < 7000) {
            return ("Visible");
        }
        if (x < 10000000) {
            return ("Infrared");
        }
        x *= 1000;
        if (x < 10000000) {
            return ("Micowaves");
        }
        if (x > 10000000) {
            return ("Radiowaves");
        }
        return ("N/A");
    }

    /**
     * TODO: Gets the files attribute of the Connector object
     * 
     * @param query
     *            TODO: Description of the Parameter
     * @return TODO: The files value
     */
    public DataHandler getFiles() {
        return (null);
    }

    public boolean testConnection() {
        return (true);
    }

    private Connection createConnection() {
        Connection con = null;
        try {
            Driver pilote = (Driver) Class.forName(archive.getDriverName())
                    .newInstance();
            //			DriverManager.registerDriver(new OracleDriver ()) ;
            DriverManager.registerDriver(pilote);
            DriverManager.setLoginTimeout(archive.getTimeout());
            System.out.println("SQL Connection on '" + archive.getCompleteURL()
                    + "'.");
            //			String tmp =
            // "jdbc:postgresql://tx-medoc3.medoc-ias.u-psud.fr:5432/test";
            con = DriverManager.getConnection(archive.getCompleteURL(), archive
                    .getUser(), archive.getPassword());
        } catch (Throwable t) {
            ProviderMonitor.getInstance().reportException(t);
        }
        return (con);
    }

}

