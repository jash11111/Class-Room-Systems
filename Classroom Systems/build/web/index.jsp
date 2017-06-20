<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
		<h1><center>Welcome to Submission website</center></h1>
				<a href="Admin.jsp">Admin Panel</a>
		
		<form action="uploadfile" method="post" enctype="multipart/form-data">
				
			<table>
					<tr>
							<td>Name</td>
							<td><input type="text" name="name"></td>
					</tr>
					<tr>
							<td>Email id</td>
							<td><input type="text" name="email"></td>
					</tr>
					<tr>
							<td> id</td>
							<td><input type="text" name="id"></td>
					</tr>
					<tr>
							<td>password</td>
							<td><input type="password" name="password"></td>
					</tr>
					<tr>
							<td>upload File</td>
							<td><input type="file" name="file"></td>
					</tr>
					
					<tr>
							<td></td>
							<td><input type="submit" value="save data "></td>
					</tr>
					
					
			</table>
		</form>
		
		<a href="filelist">Show all Lists</a>
</body>
</html>