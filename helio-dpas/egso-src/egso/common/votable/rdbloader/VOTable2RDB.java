package org.egso.common.votable.rdbloader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.egso.common.votable.rdbloader.fieldhandler.FieldHandler;
import cds.savot.model.FieldSet;
import cds.savot.model.SavotField;
import cds.savot.model.SavotResource;
import cds.savot.model.SavotTable;
import cds.savot.model.SavotTableData;
import cds.savot.model.TDSet;
import cds.savot.model.TRSet;
import cds.savot.model.TableSet;
import cds.savot.pull.SavotPullEngine;
import cds.savot.pull.SavotPullParser;

/**
 * Loop over all table elements of a VOTable, dynamically create a new SQL Table
 * using the VOTable field description and fill the VOTable data in this table.
 * The loader currently supports the VOTable standard data types.
 * It currently does not support coosys, description, param, and group tags. 
 * The loader uses the Savot pull parser in sequential mode to read the data. 
 * @author Marco Soldati
 * @created 23.02.2005
 */
public class VOTable2RDB
{    
    Logger logger = Logger.getLogger(this.getClass());
    
    /**
     * hold the field handler factory
     */
    private FieldHandlerFactory fieldHandlerFactory = null;
    
    /**
     * hold the names of all imported tables
     */
    private List<String> tableNames = new ArrayList<String>();
    
    /**
     * Get the list of all imported tables as Strings. This can be used to query the table after importing it.
     * @return Returns the tableNames.
     */
    public List<String> getTableNames()
    {
        return tableNames;
    }
    
    
    /**
     * Default constructor 
     */
    public VOTable2RDB()
    {

    }
    
    /**
     * Get the current FieldHandlerFactory. If no FieldFactory was set this method
     * returns the default FieldHandlerFactory, otherwise the one than was previously set.
     * @return Returns the fieldHandlerFactory.
     */
    public FieldHandlerFactory getFieldHandlerFactory()
    {
        if (fieldHandlerFactory == null)
            fieldHandlerFactory = new FieldHandlerFactory();
        return fieldHandlerFactory;
    }
    
    /**
     * Set a custom fieldHandlerFactory. Ususally it should be enough just to add
     * custom FieldHandlers to the default FieldHandlerFactory.
     * @param fieldHandlerFactory The fieldHandlerFactory to set.
     */
    public void setFieldHandlerFactory(FieldHandlerFactory fieldHandlerFactory)
    {
        this.fieldHandlerFactory = fieldHandlerFactory;
    }
    
    /**
     * perform the import from VOTable source to RDB connection
     * @param source Source VOTable
     * @param connection Target database connection
     */
    public void doImport(InputStream source, Connection connection) throws SQLException
    {
        // begin the parsing
        SavotPullParser savotParser = new SavotPullParser(source, SavotPullEngine.SEQUENTIAL, "UTF-8"); 
        
        // get the first resource of the VOTable file
        SavotResource currentResource = savotParser.getNextResource();
        
        if (currentResource == null)
        {
            logger.warn("no resource object found int VOTable");
            return;
        }
            
        // loop over all resources
        int numOfResources = 0;
        while (currentResource != null) 
        {
            // get the name of the resource. it will be used as part of the tablename.
            String resourceName = currentResource.getName();

            // if name is not set get the id
            if (resourceName == null || resourceName.equals(""))
                resourceName = currentResource.getId();
            
            // if name is not set create an id.
            if (resourceName == null || resourceName.equals(""))
                resourceName = "resource" + numOfResources;
            
            // loop over all tables
            TableSet tableSet = currentResource.getTables();
            
            if (tableSet.getItemCount() == 0)
            {
                logger.warn("No tables found in resource '" + resourceName + "'");
            }
            else
            {
                for (int i = 0; i < tableSet.getItemCount(); i++) 
                {
                    SavotTable table = (SavotTable)tableSet.getItemAt(i);
                    
                    String tableName = table.getName();
                    if (tableName == null || tableName.equals(""))
                        tableName = table.getId();
                    if (tableName == null || tableName.equals(""))
                        tableName = "table"+i;
                    
                    tableName = toLetterAndDigit(resourceName, '_') + "_" + toLetterAndDigit(tableName, '_');
                        tableNames.add(tableName);                
                    
                    // use the shortcut to get the field set
                    FieldSet fieldSet = table.getFields();
                    
                    // and create the field handlers
                    FieldHandler[] fieldHandlers = getFieldHandlers(fieldSet);
                    
                    // now create the RDB tables
                    createRDBTable(fieldHandlers, tableName, connection);
                    
                    // get the table data
                    SavotTableData tableData = table.getData().getTableData();
                    TRSet trSet = tableData.getTRs();
                    
                    // and feed it into the RDB
                    if (trSet != null) 
                    {
                        // insert rows into the database
                        insertRows(fieldHandlers, trSet, tableName, connection);
                    }
                }
            } // end for each table
            
            numOfResources ++;
            
            // get the next resource
            currentResource = savotParser.getNextResource();
        }    
    }


    /**
     * get the fieldHandlers for the fieldSet from the FieldHandlerFactory.
     * @param fieldSet set of VOTable fields.
     * @return array of fieldHandlers.
     */
    private FieldHandler[] getFieldHandlers(FieldSet fieldSet)
    {
        FieldHandler[] fieldHandlers = new FieldHandler[fieldSet.getItemCount()];
        
        for (int i = 0; i < fieldSet.getItemCount(); i++)
        {
            SavotField field = (SavotField)fieldSet.getItemAt(i);
            String dataType = field.getDataType();
            String uType = field.getUtype();
            
            fieldHandlers[i] = getFieldHandlerFactory().createFieldHandler(dataType, uType, field);
        }
        return fieldHandlers;
    }

    /**
     * Creates dynamically a new table in the database.
     * @param fieldHandlers the fieldHandlers are use to define the columns.
     * @param tableName name of the new table
     * @param connection connection to the database.
     * @throws SQLException
     */
    private void createRDBTable(FieldHandler[] fieldHandlers, String tableName, Connection connection) throws SQLException
    {
        Statement stmt = null;
        
        try
        {
            StringBuffer query = new StringBuffer()
               .append("CREATE TABLE ")
               .append(tableName)
               .append(" (")
               .append(fieldHandlers[0].getSQLFieldDesc());
            
            for (int i = 1; i < fieldHandlers.length; i++)
            {
                query.append(", ")
                    .append(fieldHandlers[i].getSQLFieldDesc());
            }
            query.append(") ");
            
            // execute the query
            stmt = connection.createStatement();            
            String q = query.toString();
            
            if (logger.isDebugEnabled())
                logger.debug("Query to create database '" + tableName + "':" + q); 
            
            stmt.execute(q);
            
            logger.info("table '" + tableName + "' created.");
        }
        finally
        {
            if (stmt != null)
                stmt.close();
        }
    }
    
    /**
     * loop over all rows of a VOTable dataTable and fill them into the rdb table.
     * @param fieldHandlers fieldHandler array to convert the VOTable Strings to java Objects.
     * @param trSet set of VOTable TRs that contain the row data.
     * @param tableName name of the table to write to
     * @param connection SQLConnection
     * @throws SQLException
     */
    private void insertRows(FieldHandler[] fieldHandlers, TRSet trSet, String tableName, Connection connection) throws SQLException
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try
        {
            StringBuffer query = new StringBuffer()
                .append("INSERT INTO ")
                .append(tableName)
                .append(" (")
                .append(fieldHandlers[0].getFieldName())
                ;
            
            // fill in the field names
            for (int i = 1; i < fieldHandlers.length; i++)
            {
                query.append(", ").append(fieldHandlers[i].getFieldName());                
            }
            // and now a ? for each field
            query.append(") VALUES (?");
            
            for (int i = 1; i < fieldHandlers.length; i++)
            {
                query.append(", ?");
            }
            query.append(") ");
            
            String q = query.toString();
            if (logger.isDebugEnabled())
                logger.debug("PreparedStatement for record insertion: " + q);
                        
            // precompile the query
            pstmt = connection.prepareStatement(q);
            
            // and start to fill in the data.
            // for each row of the table
            int inserted = 0;
            
            for (int row = 0; row < trSet.getItemCount(); row++) 
            {
                // get all the data of the row (shortcut)
                TDSet tdSet = trSet.getTDSet(row);
                
                // loop over all columns
                for (int col = 0; col < tdSet.getItemCount(); col++) {
                    pstmt.setObject(col + 1, 
                            fieldHandlers[col].format(tdSet.getContent(col)), 
                            fieldHandlers[col].getJDBCType());
                }
                
                inserted += pstmt.executeUpdate();                
            }
            
            logger.info(inserted + " rows inserted.");
            
        }
        finally
        {
            if (pstmt != null)
                pstmt.close();
            
            if (rs != null)
                rs.close();
        }
    }
    
    /**
     * Converts all non alphanumeric chars to esc.
     * @param s String to parse for not letter and digits.
     * @param c character to subsitute
     * @return
     */
    private String toLetterAndDigit(String s, char esc)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c))
                sb.append(c);
            else
                sb.append(esc);
        }
        return sb.toString();
    }
    
}

