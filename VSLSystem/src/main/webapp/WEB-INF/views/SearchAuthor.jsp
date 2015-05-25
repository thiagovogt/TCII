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
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	
	<script type="text/javascript">
		$(function(){
			$("form").submit(function(event) {
				if ($("#searchName").val() === "") {
					$(".input-group").addClass("has-error");
					$("#errorMsg").show().delay(2000).fadeOut("slow");
					event.preventDefault();
				}
			});
		});
	</script>
</head>
<body>
	<div class="container" style="width:600px">	
		<div class="page-header text-center">
	  		<h1><a href="Home" title="Home">VSCSystem</a> <small>Search by Author</small></h1>
		</div>
		<div class="row">
			<div class="center-block validation-error-alert">
				<div class="alert alert-danger" role="alert" style="display: none;" id="errorMsg">
		      		<strong> Error!</strong> The "Author's Name" field is required.
		    	</div>
	    	</div>
	    </div>
		<div class="row">
			<form action="ListAuthors" method="post">
				<div class="center-block" style="max-width: 500px;">
					<div class="input-group">
						<label for="searchName" class="sr-only">Author's Name:</label> 
						<input  class="form-control" type="text" name="searchName" id="searchName" value="" placeholder="Author's Name..." />
						<span class="input-group-btn">
					    	<button type="submit" class="btn btn-primary" type="button">Search</button>
					    </span>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="footer">
      <div class="container text-center">
        <p>Made by Lucas Gentile and Thiago Vogt, based on <a target="_blank" href="http://dblp.uni-trier.de/db/" title="DBLP">DBLP - Computer Science Bibliography</a> </p>
      </div>
    </div>
</body>
</html>