package db_project1;
/*
 * CS 425 Project 1
 * This class creates methods to handle and manipulate data.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import org.json.simple.JSONObject;

public class ManipulateData {
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
    public static String postDataAsJSON(){
        // used by post?
    }
} // methods to be used by servlets
