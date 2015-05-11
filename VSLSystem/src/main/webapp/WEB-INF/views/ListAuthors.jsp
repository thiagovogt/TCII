<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VSCSystem</title>

<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css" />" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="https://cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script src="https://cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js"> </script>
<script src="https://cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.js"> </script>

<script type="text/javascript">

	$(function(){
	
		$("form").submit(function(event) {
			if (!$("input[type='radio'][name='urlKey']").is(':checked')) {
				alert("Select one author!");
				event.preventDefault();
			}
		});
		
		$("#authorsSearchTable tbody tr").mouseover(function() {
			$(this).css("cursor", "pointer");
		});
	});

	$(document).ready(function() {
		var table = $('#authorsSearchTable').DataTable( {
			  "columns": [
			              { className: "text-center",
			            	orderable: false 
			              },
			              null
			             ],
			  "order": [[ 1, "asc" ]]
   			} );

		$('#authorsSearchTable tbody').on('click', 'tr', function() {
			if ($(this).hasClass('active')) {
				$(this).find("input").prop('checked', false);
				$("#authorName").val("");
				$(this).removeClass('active');
			} else {
				table.$('tr.active').removeClass('active');
				$(this).addClass('active');
				table.$('tr.active').find("input").prop('checked', true);
				$("#authorName").val(table.$('tr.active').find("td").eq(1).html());
			}
		});
	});
</script>
</head>
<body>
	<form action="LoadGraphInformation" method="post">
		<center>
			<h1><a href="Home" title="Home">VSCSystem</a></h1>
			<h2><a href="SearchAuthor" title="Search new Author">Search by Author</a></h2>
			<br>
			<h2>Select one Author</h2>
			<br>
			<span class="errorMessage">${msg}</span>
			<br>
			<br>
			<div style="width: 55%">
				<table id="authorsSearchTable" class="table table-condensed table-hover" cellspacing="0" width="100%">
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
			</div>
			<input type="hidden" name="name" id="authorName" value=""/>
			<br> 
			<input type="submit" class="button formButton" title="Search" value="Search"/>
		</center>
	</form>
</body>
</html>