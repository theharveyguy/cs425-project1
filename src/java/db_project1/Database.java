package db_project1;
/*
 * CS 425 Project 1
 * This class creates the Database object and the db_pool for use by servlets.
 */
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

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
} // Database pool class, repurposed from Lab3B - MH
