<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

		
				
					<form action="filemanipu" method="post">
					
					Select option<br>
					
						<select name="id">
						<option value="1">UploadFile</option>
						<option value="2">MakeDirectories</option>
						<option value="3">deleteFile</option>
						<option value="4">CheckIfDirectoryExists</option>
						<option value="5">downloadFile</option>
						<option value="6">ListFiles</option>
						<option value="7">removeDirectory</option>
						</select>
						
						<input type="submit" value="ok">
					
					
		     	</form>
		     	<table>
						
				</table>
		<a href="DbtoSystem">Move Files from Database to File System</a>
		<a href="deteleblob">Delete the files From Database</a>
</body>
</html>