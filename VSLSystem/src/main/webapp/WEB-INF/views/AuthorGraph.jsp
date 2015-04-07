<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="br.com.vsc.VSCSystem.model.entity.Publication"%>
<%@page import="java.util.HashMap"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>VSCSystem</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/js/jQuery-ui-1.11.2/smoothness/jquery-ui.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/js/jQuery-ui-1.11.2/smoothness/theme.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/js/DataTables-1.10.3/media/css/jquery.dataTables.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/js/vis/css/vis.css" />" />

<script type="text/javascript" src="<c:url value="/resources/js/jQuery-2.1.1/jquery.min.js" />"> </script>
<script type="text/javascript" src="<c:url value="/resources/js/jQuery-ui-1.11.2/jquery-ui.js" />"> </script>
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
		padding-top: 13px;
	}
	
	#dvYearFilter span, #dvTypeFilter span, #dvLocalFilter span{
		font-weight: bold;
		font-size: 15px;
	}
	
	#dvYearFilter, #dvTypeFilter, #dvButtonFilter, #dvLocalFilter{
		display: inline;
	}
	
	#dvYearFilter, #dvTypeFilter, #dvLocalFilter{
		text-align: left;
		margin-left: 95px;
		position: relative;
		left: -110px;
	}
	#dvButtonFilter{
		position: relative;
		right: -20px;
	}
	
</style>
<script type="text/javascript">
	
	var DIR = '/VSCSystem/resources/js/vis/images/';
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
				label : "${author.name}",
				image : DIR + 'User-Executive-Green-icon.png',
				shape : 'image'
			}				
		);
		// create connections
		var color = '#BFBFBF';
 		var edges = [];
		var countIdNodes = 2;
		var countCoAuthors = 1;
		<c:forEach items="${author.publications}" var="publication">
			publications[countIdNodes] = {
				urlKey 	: "${publication.urlKey}",
				title 	: "${publication.title}",
				year 	: "${publication.year}",
				type	: "${publication.type}",
				local	: "${publication.local}"
			}
			nodes.push(
				{
					id : countIdNodes,
					label : "Publication - " + "${publication.urlKey}",
					image : DIR + 'Document-icon48.png',
					shape : 'image'
				}	
			);
			edges.push(
				{
		 			from : 1,
		 			to : countIdNodes,
		 			value : 2,
		 			color : color
				}
			);
			
			var currPublicationId = countIdNodes;
			countIdNodes++;
			<c:forEach items="${publication.coAuthors}" var="coAuthor">
				nodes.push(
					{
						id : countIdNodes,
						label :"${coAuthor.name}",
						image : DIR + 'User-Administrator-Green-icon.png',
						shape : 'image'
					}	
				);
				edges.push(
					{
			 			from : currPublicationId,
			 			to : countIdNodes,
			 			value : 2,
			 			color : color
					}
				);
				countIdNodes++;
			</c:forEach>		
		</c:forEach>		
		// create a network
		var container = document.getElementById('dvGraph');
		var data = {
			nodes : nodes,
			edges : edges
		};
		var options = {
// 			configurePhysics:true,
			
		};
// var options = {physics: {barnesHut: {enabled: false}, repulsion: {nodeDistance:150, springConstant: 0.013, damping: 0.3}}, smoothCurves:false};
		network = new vis.Network(container, data, options);
		network.on('doubleClick', function (properties) {
			if(publications[properties.nodes] != null){
				
				$("#publicationInformation").attr("title", 'Publication - ' + publications[properties.nodes].urlKey);
				$("#publicationTitle").html(publications[properties.nodes].title);
				$("#publicationYear").html(publications[properties.nodes].year);
				$("#publicationLocal").html(publications[properties.nodes].local);
				$("#publicationType").html(publications[properties.nodes].type);
				
				$("#publicationInformation").dialog({
				      height: 250,
				      width: 800,
				      resizable: false
				});
			}
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
				<div id="dvTypeFilter">
					<span>Type:</span>
					<select id="typeFilter" name="typeFilter">
						<option value="0">Select a type...</option>
					</select>
				</div>
				<div id="dvLocalFilter">
					<span>Local:</span>
					<select id="localFilter" name="localFilter">
						<option value="0">Select a local...</option>
					</select>
				</div>
				<div id="dvButtonFilter">
					<input type="submit" class="button formButton" title="Apply Filter" value="Apply Filter"/>
				</div>
			</div>	
		</form>
		<h4>GRAPH</h4>
		<div id="dvGraph"></div>
		
		<div id="publicationInformation" style="display:none;" title="">
			<table style="border-spacing: 15px;">
				<tr>
					<td align="right"><b>Title:</b></td>
					<td id="publicationTitle"></td>
				</tr>
				<tr>
					<td align="right"><b>Year:</b></td>
					<td id="publicationYear"></td>
				</tr>
				<tr>
					<td align="right"><b>Local:</b></td>
					<td id="publicationLocal"></td>
				</tr>
				<tr>
					<td align="right"><b>Type:</b></td>
					<td id="publicationType"></td>
				</tr>
			</table>
		</div>
 
	</center>
</body>
</html>