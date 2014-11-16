<%@page import="br.com.vsl.VSLSystem.controller.AccessReportController"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VSLSystem - Visualizer of Scientific Collaborations</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css" />" />
</head>
<body>
	<center>
		<h1>VSCSystem</h1>
		<br>
		<br>
		<br>
		<br>
		<span class="errorMessage"><%= new AccessReportController().insertAccessLog()%></span>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<a class="button menuButton" href="SearchAuthor" >Search by Author</a>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<a class="button menuButton" href="AccessReport" target="_blank">Access Report</a>
		<br>
		<br>
	</center>
</body>
</html>