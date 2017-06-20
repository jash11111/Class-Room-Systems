import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beanData.FileDataBean;

public class FetchFileServlet extends HttpServlet {

	ArrayList<FileDataBean> list= null;
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("This is a Servlet call to fetch File :");
		
		//response.setContentType("text/html;charset=UTF-8");
     //   PrintWriter out = response.getWriter();
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527", "sample", "app");
            PreparedStatement ps = con.prepareStatement("select * from Student");
            ResultSet rs = ps.executeQuery();
           list= new ArrayList<FileDataBean>();
           /* out.print("<h1>Name </h1>");
            out.print("<h1>Email </h1>");
            out.print("<h1>Password</h1>");
            */
            while ( rs.next()) {
            	
            	FileDataBean file= new FileDataBean();
            	file.setName(rs.getString(1));
            	file.setEmail(rs.getString(2));
            	file.setPass(rs.getString(3));
            	Blob b=rs.getBlob(4);
            	file.setFile((b.getBytes(1,(int)b.length())));
            	file.setFilename(rs.getString(5));
            	file.setId(rs.getString(6));
            	list.add(file);
            	
            	System.out.println(" fetch data in result set ");
                /*  out.print("<h3>" + rs.getString("name") + "</h3>");
                  out.print("<h3>" + rs.getString("email") + "</h3>");
                  out.print("<h3>" + rs.getString("password") + "</h3>");
                //  out.print("                 <img width='300' height='300' src=displayphoto?name=" +  rs.getString("playername") + "></img> <p/>");
*/            }
         //   con.close();
            HttpSession session=req.getSession(true);
            session.setAttribute("list",list);
            req.getRequestDispatcher("display.jsp").forward(req, response);
            
            
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        finally {            
            //out.close();
        }
    
		
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
