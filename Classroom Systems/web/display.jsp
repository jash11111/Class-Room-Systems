<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.sql.Blob"%>
<%@page import="java.util.ArrayList"%>
<%@page import="beanData.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
			<%
					ArrayList<FileDataBean> list=(ArrayList<FileDataBean>)session.getAttribute("list");
			
					System.out.println("list size "+list.size());
			%>
			<table>
				<tr>
						<td>Name</td>
						<td>Email</td>
						<td>Password</td>
						<td>id</td>
						<td>File name</td>
				</tr>
				<%  for(FileDataBean file:list){ 
				
					/* Blob f=file.getFile();
					byte[] by=f.getBytes(1,(int)f.length()); */
					//PrintWriter pw= new PrintWriter();
				/* //	response.setContentType("image/gif");
				       OutputStream o = response.getOutputStream();
				       o.write(by);
				       o.flush(); 
				       o.close(); */
				%>
				<tr>
						<td><%= file.getName() %></td>
						<td><%= file.getEmail() %></td>
						<td><%= file.getPass() %></td>
						
						<td><%=file.getId() %></td>

						<td><%= file.getFilename() %></td>	
						<td><a href="viewfile?id=<%=file.getId() %>" target="_blank">view file</a></td>
									</tr>
				<%} %>
			</table>
</body>
</html>