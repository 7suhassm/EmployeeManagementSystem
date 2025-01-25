import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Delete
 */
@WebServlet("/Delete")
public class Delete extends HttpServlet {
    Connection con = null;
    PreparedStatement ps = null;
    PreparedStatement ps1 = null;
    ResultSet rs = null;
    String path = "com.mysql.cj.jdbc.Driver";
    String dpath = "jdbc:mysql://localhost:3306/employeesystem?user=root&password=7suhas";
    PrintWriter out = null;

    public void init() {
        try {
            Class.forName(path);
            con = DriverManager.getConnection(dpath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void service(HttpServletRequest req, HttpServletResponse res) {
        try {
            // Set content type and get the PrintWriter
            out = res.getWriter();
            res.setContentType("text/html");

            // HTML and CSS integrated into the servlet
            out.println("<html><head>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 20px; display: flex; justify-content: center; align-items: center; height: 100vh; }");
            out.println(".container { background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); width: 100%; max-width: 500px; text-align: center; }");
            out.println("h3 { color: #333; }");
            out.println("a { text-decoration: none; color: #4CAF50; font-weight: bold; margin-top: 20px; display: inline-block; }");
            out.println("a:hover { text-decoration: underline; }");
            out.println(".btn { padding: 10px 20px; font-size: 16px; background-color: #4CAF50; color: white; border: none; border-radius: 5px; cursor: pointer; transition: background-color 0.3s ease; }");
            out.println(".btn:hover { background-color: #45a049; }");
            out.println("</style>");
            out.println("</head><body>");

            // Logic for deleting employee based on the ID passed
            String cid = req.getParameter("id");
            int id = Integer.parseInt(cid);

            // Check if the ID is valid
            if (cid == null || cid.isEmpty()) {
                out.println("<div class='container'><h3>Invalid ID</h3></div>");
                out.println("<p><a href='http://localhost:9090/EmployeeManagementSystem/Delete.html'>Enter Again</a></p>");
                return;
            }

            // Prepare SQL statement to delete the employee
            String sql1 = "DELETE FROM employee WHERE id=?";
            ps = con.prepareStatement(sql1);
            ps.setInt(1, id);
            int rowsChange = ps.executeUpdate();

            // Check if employee was deleted successfully
            if (rowsChange > 0) {
                out.println("<div class='container'><h3>Employee Deleted Successfully</h3></div>");
                out.println("<a href='http://localhost:9090/EmployeeManagementSystem/Hello.html' class='btn'>Go to Home</a>");
            } else {
                out.println("<div class='container'><h3>Employee Not Found</h3></div>");
                out.println("<p><a href='http://localhost:9090/EmployeeManagementSystem/Delete.html'>Try Again</a></p>");
            }

            out.println("</body></html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            con.close();
            ps.close();
            out.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
