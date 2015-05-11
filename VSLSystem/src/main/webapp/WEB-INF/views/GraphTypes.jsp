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
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	
</head>
	<script type="text/javascript">
		$(function(){
			$('#infoIcon').popover({ trigger: "hover" });
		});
	</script>
	<style>
		button, span{
			margin-top:25px !important;
		}
		span:hover{
			color: #265a88 !important;
		}
	</style>
<body>
	<div class="container" style="max-width:600px">	
		<div class="page-header text-center">
	  		<h1><a href="Home" title="Home">VSCSystem</a> <small>Types of Graph</small></h1>
		</div>
		<div class="center-block text-center" style="max-width: 450px;" >
			<button type="button" class="btn btn-primary btn-lg btn-block" onClick="window.location.href='GenerateAuthorGraph'">Publications</button>
			<button type="button" class="btn btn-primary btn-lg btn-block" onClick="window.location.href='GenerateCollaborationsGraph'">Collaborations</button>
			<span style="color: #337ab7; cursor: pointer; font-size: 20px;" id="infoIcon" class="glyphicon glyphicon-info-sign" aria-hidden="true"
				data-content="The first graph shows the author and their publications, and the other one shows the author's collaborations with other coauthors and the resulting publications" 
				rel="popover" data-placement="bottom" data-original-title="Types Information"></span>
		</div>
	</div>
</body>
</html>