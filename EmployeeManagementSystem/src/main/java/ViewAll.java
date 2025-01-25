import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewAll")
public class ViewAll extends HttpServlet {
    // JDBC connection parameters
    private String jdbcURL = "jdbc:mysql://localhost:3306/employeesystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "7suhas";  // Change to your DB password
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            // Set up the JDBC connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Setting the response type
        response.setContentType("text/html");

        // Get the PrintWriter object
        PrintWriter out = response.getWriter();

        // SQL Query to fetch all employee data
        String sql = "SELECT * FROM employee";

        try {
            // Execute the query with a scrollable result set
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);

            // Start the HTML output with CSS for styling
            out.println("<html><head>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px; }");
            out.println("h2 { text-align: center; color: #333; }");
            out.println("table { width: 80%; margin: 20px auto; border-collapse: collapse; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); background-color: #fff; }");
            out.println("th, td { padding: 12px; text-align: center; border: 1px solid #ddd; font-size: 16px; }");
            out.println("th { background-color: #4CAF50; color: white; font-weight: bold; }");
            out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
            out.println("tr:hover { background-color: #ddd; }");
            out.println(".no-data { text-align: center; font-size: 18px; color: #888; padding: 20px; }");
            out.println("</style>");
            out.println("</head><body>");

            out.println("<h2>Employee Data</h2>");

            // Check if any data exists
            if (!resultSet.next()) {
                out.println("<div class='no-data'>No employee data available.</div>");
            } else {
                // Reset the cursor to the beginning
                resultSet.beforeFirst();

                // Start the table and display the column headers
                out.println("<table>");
                out.println("<tr><th>ID</th><th>Name</th><th>Age</th><th>Gender</th><th>Email</th><th>Department</th></tr>");

                // Loop through the result set and display the data
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    String gender = resultSet.getString("gender");
                    String email = resultSet.getString("email");
                    String department = resultSet.getString("department");

                    // Output the row of data in the table
                    out.println("<tr>");
                    out.println("<td>" + id + "</td>");
                    out.println("<td>" + name + "</td>");
                    out.println("<td>" + age + "</td>");
                    out.println("<td>" + gender + "</td>");
                    out.println("<td>" + email + "</td>");
                    out.println("<td>" + department + "</td>");
                    out.println("</tr>");
                }

                // Close the table
                out.println("</table>");
            }

            // Close the HTML
            out.println("<a href='http://localhost:9090/EmployeeManagementSystem/Hello.html'>Back to Home</a>");

            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
