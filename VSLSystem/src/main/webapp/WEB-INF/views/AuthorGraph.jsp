<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="br.com.vsl.VSLSystem.model.entity.Publication"%>
<%@page import="java.util.HashMap"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>VSLSystem</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/js/DataTables-1.10.3/media/css/jquery.dataTables.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/js/vis/css/vis.css" />" />

<script type="text/javascript" src="<c:url value="/resources/js/jQuery-2.1.1/jquery.min.js" />"> </script>
<script type="text/javascript" src="<c:url value="/resources/js/DataTables-1.10.3/media/js/jquery.dataTables.js" />"> </script>
<script type="text/javascript" src="<c:url value="/resources/js/vis/js/vis.js" />"> </script>

<style>
	
	#dvGraph, #dvFilters {
		width: 1000px;
		height: 450px;
		border: 1px solid lightgray;
		background: #F3F3F3;
	}
	
	#dvFilters{
		height: 38px !important;
		padding-top: 10px;
	}
	
	#dvYearFilter span{
		font-weight: bold;
	}
	
	#dvYearFilter, #dvButtonFilter{
		display: inline;
	}
	
	#dvYearFilter{
		text-align: left;
		margin: 	16px 0px 0px 20px;
		width: 		150px;
		position: relative;
		left: -360px;
	}
	#dvButtonFilter{
		width: 150px;
		position: relative;
		right: -340px;
	}
	
</style>
<script type="text/javascript">
	
	var DIR = '/VSLSystem/resources/js/vis/images/vis-network-icons/';
	var nodes = null;
	var edges = null;
	var publications = null;
	var network = null;
	function draw() {
		publications = [];
		// create nodes
		var nodes = [];
		nodes.push(
			{
				id : 1,
				label : '${author.name}',
				image : DIR + 'User-Executive-Green-icon.png',
				shape : 'image'
			}				
		);
		// create connections
		var color = '#BFBFBF';
 		var edges = [];
		var count = 2;
		<c:forEach items="${author.publications}" var="publication">
			publications[count] = {
				urlKey 	: "${publication.urlKey}",
				title 	: "${publication.title}",
				year 	: "${publication.year}",
				type	: "${publication.type}",
				local	: "${publication.local}"
			}
			nodes.push(
				{
					id : count,
					label :'${publication.urlKey}',
					image : DIR + 'Document-icon48.png',
					shape : 'image',
					teste : 'testeAqui'
				}	
			);
			
			edges.push(
				{
		 			from : 1,
		 			to : count,
		 			value : 2,
		 			color : color
				}
			);
			
			count++;
		</c:forEach>		
		// create a network
		var container = document.getElementById('dvGraph');
		var data = {
			nodes : nodes,
			edges : edges
		};
		var options = {};
		network = new vis.Network(container, data, options);
		network.on('doubleClick', function (properties) {
			alert("Title: " + publications[properties.nodes].title + "\nYear: " + publications[properties.nodes].year + "\nType: " + publications[properties.nodes].type + "\nLocal: " + publications[properties.nodes].local);
		});
	}
</script>
</head>
<body onload="draw()">
	<center>
		<h1><a href="Home" title="Home">VSCSystem</a></h1>
		<h2><a href="SearchAuthor" title="Search new Author">Search by Author</a></h2>
		<h2>Author: ${author.name}</h2>
		<br>
		<span class="errorMessage">${msg}</span>
		<br>
		<h4>FILTERS</h4>
		<form action="FilterByYear" method="post">
			<div id="dvFilters">
				<div id="dvYearFilter">
					<span>Year:</span>
					<select id="yearFilter" name="yearFilter">
						<option value="0">Select a year...</option>
						<c:forEach items="${yearsFilter}" var="yearValue">
							<option ${yearValue == yearFiltered ? 'selected' : ''} value="${yearValue}">${yearValue}</option>
						</c:forEach>
					</select>
				</div>
				<div id="dvButtonFilter">
					<input type="submit" class="button formButton" title="Filter" value="Filter"/>
				</div>
			</div>	
		</form>
		<h4>GRAPH</h4>
		<div id="dvGraph"></div>
	</center>
</body>
</html>