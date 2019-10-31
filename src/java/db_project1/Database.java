package db_project1;
/*
 * CS 425 Project 1
 * This class creates the Database object.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

class Database{
    Context envContext = null, initContext = null;
    DataSource ds = null;
    Connection conn = null;

    public Database() throws NamingException {
        try {
            envContext = new InitialContext();
            initContext  = (Context)envContext.lookup("java:/comp/env");
            ds = (DataSource)initContext.lookup("jdbc/db_pool");
            conn = ds.getConnection();   
        }
        catch (SQLException e) {}
    } // Constructor
    
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            }
            catch (SQLException e) {}   
        }
    } // End closeConnection()
    
    public Connection getConnection() { return conn; }
    
    public static String getDataAsTable(String sessionID){
        //used by GET 
        String table = "";
        String tableRow = "";
        String tableHead = "";
        String key;
        String value;
        
        //db pool variables
        Database db = null;
        Connection connection;
        //SQL variables
        String query;
        PreparedStatement pstatement = null;
        ResultSet resultset = null;
        ResultSetMetaData metadata = null;
        boolean hasResult;

        try{
            db = new Database();
            connection = db.getConnection();
            
            query = "SELECT * FROM registrations WHERE sessionID = "+sessionID;
            pstatement = connection.prepareStatement(query);
            hasResult = pstatement.execute();
            
            if (hasResult){
                resultset = pstatement.getResultSet();
                metadata = resultset.getMetaData();
                int columnCount = metadata.getColumnCount();
                
                // begin table
                table += "<table>";
                
                // add table header
                tableHead = "<tr>";
                for (int i = 1; i <= columnCount; i++){
                    key = metadata.getColumnLabel(i);
                    tableHead += "<th>" + key + "</th>";
                }
                tableHead += "</tr>";
                table += tableHead;

                // add table data
                while (resultset.next()){
                    tableRow += "<tr>";
                    for (int i=1; i<= columnCount; i++){
                        value = resultset.getString(i);
                        if (resultset.wasNull()) {
                            tableRow += "<td></td>";
                        }
                        else {
                            tableRow += "<td>" + value + "</td>";
                        }
                    }
                    tableRow += "</tr>";
                    table += tableRow;
                    resultset.next();
                }
                table += "</table>";
                // end table
            }
            
        }
        catch (Exception e) { System.err.println( e.toString() ); }
        return (table);
    }
    public static String postDataAsJSON(String firstName, String lastName, String givenName, String sessionID){
        
        //db pool variables
        Database db = null;
        Connection connection;
        //SQL variables
        String query;
        PreparedStatement pstatement = null;
        ResultSet resultset = null;
        boolean hasResult;

        String registrationCode = "R0000"; // build registrationCode onto this
        try{
            db = new Database();
            connection = db.getConnection();
            
            query = "INSERT INTO 'registrations' (firstname, lastname, displayname, sessionid) VALUES ("+firstName+","+lastName+","+givenName+","+sessionID+");";
            pstatement = connection.prepareStatement(query);
            hasResult = pstatement.execute();
            
            query = "SELECT id FROM registrations WHERE firstname = "+firstName+" and lastname = "+lastName+";";
            pstatement = connection.prepareStatement(query);
            hasResult = pstatement.execute();
            
            if (hasResult){
                resultset = pstatement.getResultSet();
                registrationCode += resultset.toString();
            }
        }
        
        catch (Exception e) { System.err.println( e.toString() ); }
        return registrationCode;
    }
} // Database pool class, repurposed from Lab3B
