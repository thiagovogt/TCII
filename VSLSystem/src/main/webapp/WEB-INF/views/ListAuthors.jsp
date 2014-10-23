<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VSLSystem</title>

<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/js/DataTables-1.10.3/media/css/jquery.dataTables.css" />" />

<script type="text/javascript" src="<c:url value="/resources/js/jQuery-2.1.1/jquery.min.js" />"> </script>
<script type="text/javascript" src="<c:url value="/resources/js/DataTables-1.10.3/media/js/jquery.dataTables.js" />"> </script>

<script type="text/javascript">

	$(function(){
	
		$("form").submit(function(event) {
			if (!$("input[type='radio'][name='urlKey']").is(':checked')) {
				alert("Select one author!");
				event.preventDefault();
			}
		});
	});

	$(document).ready(function() {
		var table = $('#authorsSearchTable').DataTable();

		$('#authorsSearchTable tbody').on('click', 'tr', function() {
			if ($(this).hasClass('selected')) {
				$(this).removeClass('selected');
			} else {
				table.$('tr.selected').removeClass('selected');
				$(this).addClass('selected');
				table.$('tr.selected').find("input").attr("checked", "true");
				$("#authorName").val(table.$('tr.selected').find("td").eq(1).html());
			}
		});

		$("#authorsSearchTable tbody tr").mouseover(function() {
			$(this).css("cursor", "pointer");
		});
	});
</script>
</head>
<body>
	<form action="GenerateGraph" method="post">
		<center>
			<h1>VSLSystem</h1>
			<h2>Search by Author</h2>
			<br>
			<h2>Select one Author</h2>
			<br>
			${msg}
			<br>
			<br>
			<table id="authorsSearchTable" class="display" cellspacing="0" width="100%">
		        <thead>
		            <tr>
		                <th>&nbsp;</th>
		                <th>Name</th>
		            </tr>
		        </thead>
		        <tfoot>
		            <tr>
		                <th>&nbsp;</th>
		                <th>Name</th>
		            </tr>
		        </tfoot>
		        <tbody>
		           <c:forEach items="${authors}" var="author">
						<tr>
							<td>
								<input type="radio" name="urlKey" value="${author.urlKey}"/>
							</td>
							<td>
								${author.name}
							</td>
						</tr>
					</c:forEach>
		    	</tbody>
		   	</table>
			<input type="hidden" name="name" id="authorName" value=""/>
			<br> 
			<input type="submit" title="Search" value="Search"/>
		</center>
	</form>
</body>
</html>