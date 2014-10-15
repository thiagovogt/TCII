<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>VSLSystem</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/js/DataTables-1.10.3/media/css/jquery.dataTables.css" />" />

<script type="text/javascript" src="<c:url value="/resources/js/jQuery-2.1.1/jquery.min.js" />"> </script>
<script type="text/javascript" src="<c:url value="/resources/js/DataTables-1.10.3/media/js/jquery.dataTables.js" />"> </script>

<script type="text/javascript">

	$(document).ready(function() {
		var table = $('#publications').DataTable();
	});
</script>
</head>
<body>
	<center>
		<h1>VSLSystem</h1>
		<h2>Search by Author</h2>
		<h2>Author: ${author.name}</h2>
		<br>
		<br>
		<span class="errorMessage">${msg}</span>
		<br>
		<br>
		<table id="publications" class="display" cellspacing="0" width="100%">
		 	<thead>
	            <tr>
	                <th>Publication Key</th>
	                <th>Publication XML</th>
	            </tr>
	        </thead>
	        <tfoot>
	            <tr>
 					<th>Publication Key</th>
	                <th>Publication XML</th>
	            </tr>
	        </tfoot>
			<tbody>
				<c:forEach items="${author.publications}" var="publication">
					<tr>
						<td>${publication.urlKey}</td>
						<td><pre><c:out value="${publication}"/></pre></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</center>
</body>
</html>