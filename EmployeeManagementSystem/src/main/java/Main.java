import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Main")
public class Main extends HttpServlet {
    Connection con = null;
    PreparedStatement ps = null;
    PrintWriter out = null;

    String path = "com.mysql.cj.jdbc.Driver";
    String dpath = "jdbc:mysql://localhost:3306/employeesystem?user=root&password=7suhas";

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
            res.setContentType("text/html");
            out = res.getWriter();

            String cid = req.getParameter("id");
            int id = Integer.parseInt(cid);
            String name = req.getParameter("name");
            String age1 = req.getParameter("age");
            int age = Integer.parseInt(age1);
            String gender = req.getParameter("gender");
            String email = req.getParameter("email");
            String department = req.getParameter("department");
            String password = req.getParameter("password");
            int pass = Integer.parseInt(password);

            String sql = "insert into employee values(?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, gender);
            ps.setString(5, email);
            ps.setString(6, department);
            ps.setInt(7, pass);
            ps.executeUpdate();

            // Output success message with style
            out.println("<html><head><style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px; text-align: center; }");
            out.println("h2 { color: #4CAF50; }");
            out.println("p { font-size: 18px; color: #333; }");
            out.println("a { font-size: 18px; color: #4CAF50; text-decoration: none; padding: 10px 20px; border: 2px solid #4CAF50; border-radius: 5px; }");
            out.println("a:hover { background-color: #4CAF50; color: white; }");
            out.println("</style></head><body>");
            
            out.println("<h2>Employee Registration Complete!</h2>");
            out.println("<p>The employee has been successfully registered.</p>");
            out.println("<a href='http://localhost:9090/EmployeeManagementSystem/Hello.html'>Back to Home</a>");
            
            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
