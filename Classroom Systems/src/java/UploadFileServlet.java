import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.ObjectInputStream.GetField;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class UploadFileServlet extends HttpServlet {
	
	
//Connection connection=null;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println(" servlet call : uploadfile ");
		
        try {
           
            Part p =  request.getPart("name");
            Scanner scanner  = new Scanner( p.getInputStream());
            String name = scanner.nextLine(); 
            
            System.out.println(" name :"+name);
            
            Part p1 =  request.getPart("email");
            Scanner scanner1  = new Scanner( p1.getInputStream());
            String email = scanner1.nextLine(); 
            System.out.println(" email :"+email);
            
            Part p2 =  request.getPart("password");
            Scanner scanner2 = new Scanner( p2.getInputStream());
            String password = scanner2.nextLine(); 
            System.out.println(" password :"+password);
            Part file =  request.getPart("file");
            String fn=getFileName(file) ;
                System.out.println("file name :"+fn ); 
                
                Part p3 =  request.getPart("id");
                Scanner scanner3 = new Scanner( p3.getInputStream());
                String id = scanner3.nextLine(); 
                System.out.println(" id :"+id);
               
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527", "sample", "app");
            System.out.println(" connection "+con);
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("insert into student values(?,?,?,?,?,?)");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(6, id);
            ps.setString(5,fn);
          
            ps.setBinaryStream(4, file.getInputStream(), (int)  file.getSize());
            int a=  ps.executeUpdate();
            if(a>0){
            	ps=con.prepareStatement("select * from student");
            	ResultSet rs=ps.executeQuery();
            	if(rs.next()){
            		System.out.println(" table name :"+rs.getMetaData().getTableName(2));
            	}
            }
         
           con.commit();
           con.close();
           response.sendRedirect("sucess.jsp");
           
        } 
        catch(Exception ex) {
        	ex.printStackTrace();
        }
        
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
	}
	
	 private String getFileName(Part part) {
	        String contentDisp = part.getHeader("content-disposition");
	        System.out.println("content-disposition header= "+contentDisp);
	        String[] tokens = contentDisp.split(";");
	        for (String token : tokens) {
	            if (token.trim().startsWith("filename")) {
	                return token.substring(token.indexOf("=") + 2, token.length()-1);
	            }
	        }
	        return "";
	    }
}
