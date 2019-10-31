package db_project1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 3. POST requests
 *  3.1 Accept firstName, lastName, givenName, and sessionID as arguments
 *  3.2 Add new record to registrations db for the new attendee
 *  3.3 Generate registration code in this form: R0000xx
 *      'R' for registration
 *      '0...0' fill with leading 0's
 *      'xx' Primary key for new attendee
 *  3.4 Return list as JSON JSON object
 */
public class Registration extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("text/html;charset=utf-8");
        String sessionID = request.getParameter("sessionID");
        Database.getDataAsTable(sessionID);

    }// GET requests

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("application/json;charset=utf-8");
        // may need to change request to response
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String givenName = request.getParameter("givenName");
        String sessionID = request.getParameter("sessionID");
        
        try (PrintWriter out = response.getWriter()) {
            out.println(Database.postDataAsJSON(firstName, lastName, givenName, sessionID));
        }
        
    }// POST requests
    
    @Override
    public String getServletInfo() { return "Registration Application";}
}
