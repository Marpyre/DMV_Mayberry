<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test Admin</title>
</head>
<body>
	<h1>Test Admin</h1>
	<ul>
		<li><a href="Admin?command=reset">Reset All Tables</a></li>
		<li><a href="Admin?command=populate">Populate Tables</a></li>
	</ul>
	
	<%
		String message = (String) request.getAttribute("msg");
	%>

	<div>
		<%=message%>
	</div>
	<br>

	<%-- Back Button --%>
	<form action="logout">
		<input type="submit" value="DMV Home">
	</form>
	
</body>
</html>