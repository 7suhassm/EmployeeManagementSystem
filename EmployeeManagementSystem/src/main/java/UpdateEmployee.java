
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
 * Servlet implementation class UpdateEmployee
 */
@WebServlet("/UpdateEmployee")
public class UpdateEmployee extends HttpServlet {
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
	        String cid = req.getParameter("id");
	        int id = Integer.parseInt(cid);

	        String sql1 = "SELECT * FROM employee WHERE id=?";
	        ps = con.prepareStatement(sql1);
	        ps.setInt(1, id);
	        rs = ps.executeQuery();
	        
	        if (rs.next()) {

	            String cname = rs.getString("name");
	            int cage = rs.getInt("age");
	            String cgender = rs.getString("gender");
	            String cemail = rs.getString("email");
	            String cdepartment = rs.getString("department");
	            int cpass = rs.getInt("passowrd"); 

	            String name = req.getParameter("name");
	            String age1 = req.getParameter("age");
	            String gender = req.getParameter("gender");
	            String email = req.getParameter("email");
	            String department = req.getParameter("department");
	            String password = req.getParameter("password");

	            // Handle null or empty values for age and password
	            int age = (age1 != null && !age1.isEmpty()) ? Integer.parseInt(age1) : cage;
	            int pass = (password != null && !password.isEmpty()) ? Integer.parseInt(password) : cpass;

	            // Update query
	            String sql2 = "UPDATE employee SET name=?, age=?, gender=?, email=?, department=?, passowrd=? WHERE id=?";
	            ps1 = con.prepareStatement(sql2);

	            ps1.setString(1, (name != null && !name.isEmpty()) ? name : cname);
	            ps1.setInt(2, age); // Uses the default value if empty
	            ps1.setString(3, (gender != null && !gender.isEmpty()) ? gender : cgender);
	            ps1.setString(4, (email != null && !email.isEmpty()) ? email : cemail);
	            ps1.setString(5, (department != null && !department.isEmpty()) ? department : cdepartment);
	            ps1.setInt(6, pass); // Uses the default value if empty
	            ps1.setInt(7, id);

	            int rowsUpdated = ps1.executeUpdate();
	            if (rowsUpdated > 0) {
	                res.getWriter().println("<p>Employee is updated</p>");
		            res.getWriter().println("<a href='http://localhost:9090/EmployeeManagementSystem/Update.html'>Re-enter</a>");

	            } else {
	                res.getWriter().println("<p>Employee is not updated</p>");
		            res.getWriter().println("<a href='http://localhost:9090/EmployeeManagementSystem/Update.html'>Re-enter</a>");

	            }
	        } else {
	            res.getWriter().println("<p>Invalid ID, try again</p>");
	            res.getWriter().println("<a href='http://localhost:9090/EmployeeManagementSystem/Update.html'>Re-enter</a>");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	public void destroy() {
		try {
			con.close();
			ps.close();
			ps1.close();
			out.close();
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
