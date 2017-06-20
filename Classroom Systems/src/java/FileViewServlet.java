import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beanData.FileDataBean;


public class FileViewServlet extends HttpServlet {

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("view file call");
		
			String id	=req.getParameter("id");
			System.out.println("id :"+id);
			HttpSession session=req.getSession(false);
			ArrayList<FileDataBean> list=(ArrayList<FileDataBean>)session.getAttribute("list");
			System.out.println("  view controller list size"+list.size());
			
			for(FileDataBean f:list)
			{
				System.out.println(" file id "+f.getId());
				if(f.getId().equals(id)){
					
					byte []imgData = f.getFile();
					resp.setContentType("application/octet-stream");
					   OutputStream o = resp.getOutputStream();
					   o.write(imgData);
					   o.flush(); 
					   o.close();
					
				}
			}
			
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	doPost(req, resp);
	}
}
