<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DMV Tactical</title>
</head>
<body>
	<h1>DMV Tactical Actions</h1>
	<table>
		<tr>
			<td><a href="addDriver.html"> <input
					type="submit" value="Add Driver">
			</a></td>
		</tr>
		<tr>
			<td><a href="getDriver.html"> <input
					type="submit" value="Get Driver">
			</a></td>
		</tr>
		<tr>
			<td><a href="moveResidence.html"> <input type="submit" value="Move Residence">
			</a></td>
		</tr>
		<tr>
			<td><a href="addVehicleRegistration.html"> <input type="submit"
					value="Add Vehicle Registration">
			</a></td>
		</tr>
		<tr>
			<td><a href="getVehicleRegistration.html"> <input type="submit"
					value="Get Vehicle Registration">
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
	<form action="index.html">
		<input type="submit" value="Back">
	</form>
</body>
</html>