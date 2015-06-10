<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
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
					$("#errorMsg").show().delay(2000).fadeOut("slow");
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
				  "order": [[ 1, "asc" ]],
				  "columnDefs": [
				                 { "width": "35%", "targets": 0 }
				               ]
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
			$('#dataTableForm').css("display","block");
			$('#loadingRow').css("display","none");
		});
	</script>
</head>
<body>
	<div class="container" style="width:780px !important">
		<div class="page-header text-center page-header-custom">
	  		<h1><a href="Home" title="Home">VSCSystem</a> <small>Author selection</small></h1>
		</div>
		<div class="row" id="loadingRow">
			<div class="center-block text-center loading-message">
				<img src="/VSCSystem/resources/images/Loading1.png" width="32" height="32">
				<h5 style="font-weight: bold;">Loading...</h5>
			</div>
		</div>		
		<div class="row">
			<div class="center-block validation-error-alert">
				<div class="alert alert-danger" role="alert" style="display: none;" id="errorMsg">
		      		<strong>Error!</strong> You must select an Author!
		    	</div>
	    	</div>
	    </div>
		<div class="row">
			<form id="dataTableForm" action="LoadGraphInformation" method="post" style="display:none; ">
				<div>
					<table id="authorsSearchTable" class="table table-condensed table-hover" >
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
									<td style="">
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
				<div class="center-block text-center" style="max-width: 450px;margin-top: 45px" >
					<button type="submit" class="btn btn-primary" type="button">Select</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>