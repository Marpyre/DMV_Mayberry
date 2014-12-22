<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mayberry Tactical</title>
</head>
<body>
	<h1>Mayberry Tactical Actions</h1>
	<table>
		<tr>
			<td><a href="addPOI.html"> <input type="submit"
					value="Create Person of Interest">
			</a></td>
		</tr>
		<tr>
			<td><a href="addActivity.html"> <input type="submit" value="Add Activity">
			</a></td>
		</tr>
		<tr>
			<td><a href="getPOI.html"> <input type="submit"
					value="Get Person of Interest Information">
			</a></td>
		</tr>
		<tr>
			<td><a href="CommandMayberryTactical?command=checkPOIDanger"> <input type="submit"
					value="Check if Person of Interest is Dangerous">
			</a></td>
		</tr>
	</table>

	<%
		String message = (String) request.getAttribute("msg");
	%>

	<div>
		<%=message%>
	</div>
	<br>

	<%-- Back Button --%>
	<form action="logout">
		<input type="submit" value="Mayberry Home">
	</form>
</body>
</html>