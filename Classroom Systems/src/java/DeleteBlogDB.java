

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

// This program will delete the files from database to make it free..
public class DeleteBlogDB extends HttpServlet {


	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println(" This is a servlet call to delete files from the database :");
		
		
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527", "sample", "app");
            PreparedStatement ps = con.prepareStatement("Delete  from student");
           int a= ps.executeUpdate(); 
           con.commit();
           System.out.println("Successfully deleted.. ");
           // req.getRequestDispatcher("display.jsp").forward(req, response);
            
            
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
