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
	
</head>
	<script type="text/javascript">
		$(function(){
			$('#infoIcon').popover({ trigger: "hover" });
		});
	</script>
<body>
	<div class="container" style="max-width:600px">	
		<div class="page-header text-center page-header-custom">
	  		<h1><a href="Home" title="Home">VSCSystem</a> <small>Types of graph</small></h1>
	  		<h3 class="sub-page-header"><small>Selected Author: ${author.name}</small></h3>
		</div>
		<div class="center-block text-center types-buttons">
			<button type="button" class="btn btn-primary btn-lg btn-block margin-graph-type-view" onClick="window.location.href='GenerateAuthorGraph'">Publications</button>
			<button type="button" class="btn btn-primary btn-lg btn-block margin-graph-type-view" onClick="window.location.href='GenerateCollaborationsGraph'">Collaborations</button>
		</div>
		<div class="center-block text-center" style="max-width: 160px;" >	
			<button type="button" class="btn btn-primary btn-xs btn-block margin-graph-type-view" onClick="window.location.href='ListAuthors?searchName'">Select another Author</button>
			<span style="color: #337ab7; cursor: pointer; font-size: 20px;" id="infoIcon" class="glyphicon glyphicon-info-sign margin-graph-type-view" aria-hidden="true"
				data-content="The first graph shows the author and her publications, and the second shows the author's collaborations with her coauthors and their respective publications. 
				You can also back to the previous page and select another author" 
				rel="popover" data-placement="bottom" data-original-title="Types Information"></span>
		</div>
	</div>
</body>
</html>