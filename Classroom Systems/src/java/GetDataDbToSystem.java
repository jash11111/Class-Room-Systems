import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.sql.BLOB;

import beanData.FileDataBean;


public class GetDataDbToSystem extends HttpServlet{


	ArrayList<FileDataBean> list= null;
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("Blob to File System  fetch  servlet call :");	
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527", "sample", "app");
            PreparedStatement ps = con.prepareStatement("select * from student");
            ResultSet rs = ps.executeQuery();
           
          
            while ( rs.next()) {
            	File image = new File("D:/jnextjava/"+rs.getString(5));
            	FileOutputStream fos = new FileOutputStream(image);
            	byte[] buffer = new byte[1];
                InputStream is = rs.getBinaryStream(4);
                while (is.read(buffer) > 0) {
                  fos.write(buffer);
                }
                System.out.println(" file written successfully ");
                  }
            con.commit();
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
