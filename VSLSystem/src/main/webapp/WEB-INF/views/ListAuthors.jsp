<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VSLSystem</title>
</head>
<body>
	<form action="GenerateGraph" method="post">
		<center>
			<h1>VSLSystem</h1>
			<h2>Search by Author</h2>
			<h3>Select one Author</h3>
			
			${msg}
			<table>
			 <c:forEach items="${authors}" var="author">
			    	<tr>
			    		<td>
			    			${author}
			    		</td>
			    	
			    	</tr>
			    </c:forEach>
			</table>
			<br>
			
			<input type="submit" title="Search"/>
		</center>
	</form>
</body>
</html>