<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VSCSystem</title>

<%-- <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css" />" /> --%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

<script type="text/javascript">

	$(function(){

		$("form").submit(function(event) {
			if ($("#searchName").val() === "") {
				alert("The field Name is required!");
				event.preventDefault();
			}
		});
	});
	
</script>

</head>
<body>
	<form action="ListAuthors" method="post">
		<center>
			<h1><a href="Home" title="Home">VSCSystem</a></h1>
			<h2>Search by Author</h2>
			<br>
			<br>
			<h3>Name: <input type="text" name="searchName" id="searchName" value="" /></h3>
			<br>
			<br>
			<input type="submit" class="button formButton" title="Search" value="Search"/>
		</center>
	</form>
</body>
</html>