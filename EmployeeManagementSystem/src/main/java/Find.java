import java.io.*;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet implementation class Find
 */
@WebServlet("/Find")
public class Find extends HttpServlet {
    
    Connection con = null;
    PreparedStatement ps = null;
    String sql = "SELECT * FROM employee WHERE id=?";
    ResultSet rs = null;
    String path = "com.mysql.cj.jdbc.Driver";
    String dpath = "jdbc:mysql://localhost:3306/employeesystem?user=root&password=7suhas";
    
    public void init() {
        try {
            // Load database driver
            Class.forName(path);
            con = DriverManager.getConnection(dpath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Set response type to HTML
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        
        // Start HTML output with CSS
        out.println("<html>");
        out.println("<head>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px; }");
        out.println("h1 { color: #4CAF50; text-align: center; }");
        out.println(".container { max-width: 800px; margin: auto; background-color: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }");
        out.println("p { font-size: 18px; color: #333; }");
        out.println("h3 { color: #e74c3c; text-align: center; }");
        out.println(".employee-info { background-color: #f9f9f9; padding: 20px; border-radius: 8px; margin-top: 20px; border-left: 5px solid #4CAF50; }");
        out.println(".employee-info p { margin: 10px 0; font-size: 16px; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        try {
            // Get ID from request parameter
            String cid = req.getParameter("id");
            if (cid == null || cid.isEmpty()) {
                out.println("<div class='container'><h3>Invalid ID</h3></div>");
                out.println("<p><a href='http://localhost:9090/EmployeeManagementSystem/View.html'>View</a></p>");
                return;
            }
            
            // Parse the ID
            int id = Integer.parseInt(cid);
            
            // Prepare and execute SQL query
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            // Check if data is found
            if (rs.next()) {
                // If found, display the employee data
                out.println("<div class='container'>");
                out.println("<h1>Employee Details</h1>");
                out.println("<div class='employee-info'>");
                out.println("<p><strong>ID:</strong> " + rs.getInt("id") + "</p>");
                out.println("<p><strong>Name:</strong> " + rs.getString("name") + "</p>");
                out.println("<p><strong>Age:</strong> " + rs.getInt("age") + "</p>");
                out.println("<p><strong>Department:</strong> " + rs.getString("department") + "</p>");
                out.println("<p><strong>Email:</strong> " + rs.getString("email") + "</p>");
                // Add other fields as required...
                out.println("</div>");
                out.println("<a href='http://localhost:9090/EmployeeManagementSystem/Hello.html'>Home</a>");

                out.println("</div>");
            } else {
                // If no data is found, display invalid ID message
                out.println("<div class='container'><h3>Invalid ID</h3></div>");
            }
        } catch (Exception e) {
            out.println("<div class='container'><h3>Error: " + e.getMessage() + "</h3></div>");
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        // End HTML output
        out.println("</body>");
        out.println("</html>");
    }

    public void destroy() {
        try {
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
