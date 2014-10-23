<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="Author" content="Gerard Ferrandez at http://www.dhteumeuleu.com/">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="HandheldFriendly" content="true">
<meta name="description" content="I think - SVG menu tree">
<meta name="keywords" content="svg,vector,menu,tree,IK">
<link rel="canonical" href="http://www.dhteumeuleu.com/i-think">
<!--cssnav-->
<link rel="shortcut icon" href="/favicon.ico"/>

<title>VSLSystem</title>

<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css" />" />

</head>
<body>
	<center>
		<h1>VSLSystem</h1>
		<h2>Search by Author</h2>
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		<table id="" class="display" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Key</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${author.publications}" var="publication">
					<tr>
						<td>${publication.urlKey}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</center>
			<br> ${msg} ${author.name}
</body>
</html>