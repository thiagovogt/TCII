<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="br.com.vsc.VSCSystem.model.entity.Publication"%>
<%@page import="java.util.HashMap"%>
<!DOCTYPE html>
<html lang="en">
<head>

<title>VSCSystem</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css" />" />

<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/theme.css">

<link rel="stylesheet" href="<c:url value="/resources/js/vis/css/vis.css" />" />

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>

<script type="text/javascript" src="<c:url value="/resources/js/vis/js/vis.js" />"> </script>
<script type="text/javascript">
	var DIR = '/VSCSystem/resources/js/vis/images/';
	var nodes = null;
	var edges = null;
	var publications = null;
	var coauthors = null;
	var network = null;
	function draw() {
		publications = [];
		coauthors = [];
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
				eePath  : "<a href='${publication.eePath}' target='_blank'>${publication.eePath}</a>",
				urlPath : "<a href='${publication.urlPath}' target='_blank'>${publication.urlPath}</a>",
				venue	: "${publication.venue}"
			};
			nodes.push(
				{
					id : countIdNodes,
					label : "${publication.urlKey}",
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
				coauthors[countIdNodes] = {name : "${coAuthor.name}"};
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
			if(publications[properties.nodes] !== null){
				
				if(publications[properties.nodes].eePath != "" && publications[properties.nodes].urlPath != ""){
					$("#publicationPath").html(publications[properties.nodes].eePath + "<br>"+ publications[properties.nodes].urlPath);
				}else if(publications[properties.nodes].eePath != ""){
					$("#publicationPath").html(publications[properties.nodes].eePath);
				}else if(publications[properties.nodes].urlPath != ""){
					$("#publicationPath").html(publications[properties.nodes].urlPath);
				}
								
				$("#publicationInformationModalLabel").html('Publication - ' + publications[properties.nodes].urlKey);
				$("#publicationTitle").html(publications[properties.nodes].title);
				$("#publicationYear").html(publications[properties.nodes].year);
				$("#publicationVenue").html(publications[properties.nodes].venue);
				$("#publicationType").html(publications[properties.nodes].type);
				
				$('#publicationInformationModal').modal();
				
			}
			if(coauthors[properties.nodes] != null){
				window.open(encodeURI('../VSCSystem/ListAuthors?searchName='+coauthors[properties.nodes].name), '_blank');
			}
		});
		
	}
	
	function clearFilter(){
		$('#yearFilter option[value="0"]').prop('selected', true);
		$('#typeFilter option[value=""]').prop('selected', true);
		$('#venueFilter option[value=""]').prop('selected', true);
	}
	
</script>
</head>
<body onload="draw()">
	<div class="container" style="width:1007px">	
		<div class="page-header text-center page-header-custom">
	  		<h1><a href="Home" title="Home">VSCSystem</a> <small>Author's publications</small></h1>
	  		<h3 class="sub-page-header"><small>Selected Author: ${author.name}</small></h3>
		</div>
		<div class="panel panel-primary">
			<div class="panel-heading panel-heading-custom">
				FILTERS
			</div>
			<div class="panel-body">
				<form class="form-inline" action="FilterPublicationsGraph" method="post">
					<div class="form-group">
  						<label for="yearFilter">Year:</label>
						<select id="yearFilter" name="yearFilter" class="form-control select-filter">
							<option value="0">Select a year...</option>
							<c:forEach items="${yearsFilter}" var="yearValue">
								<option ${yearValue == yearFiltered ? 'selected' : ''} value="${yearValue}">${yearValue}</option>
							</c:forEach>
						</select>
  					</div>
					<div class="form-group">
						<label for="typeFilter">Type:</label>
						<select id="typeFilter" name="typeFilter" class="form-control select-filter">
							<option value="">Select a type...</option>
							<c:forEach items="${typesFilter}" var="typeValue">
								<option ${typeValue == typeFiltered ? 'selected' : ''} value="${typeValue}">${typeValue}</option>
							</c:forEach>
						</select>  
  					</div>
					<div class="form-group">
						<label for="typeFilter">Venue:</label>
						<select id="venueFilter" name="venueFilter" class="form-control select-filter">
							<option value="">Select a venue...</option>
							<c:forEach items="${venuesFilter}" var="venueValue">
								<option ${venueValue == venueFiltered ? 'selected' : ''} value="${venueValue}">${venueValue}</option>
							</c:forEach>
						</select>
  					</div>
					<button type="submit" class="btn btn-primary btn-sm" type="button">Apply Filter</button>
					<button type="button" class="btn btn-default btn-sm" onClick="clearFilter()">Clear</button>
				</form>
			</div>
		</div>
		<div class="panel panel-primary">
			<div class="panel-heading panel-heading-custom">
				GRAPH
			</div>
			<div class="panel-body panel-body-graph">
				<div id="dvGraph" class="dv-graph" ></div>
			</div>
		</div>
		<div class="center-block text-center" style="max-width: 160px;" >	
			<button style="margin-top:-10px !important;" type="button" class="btn btn-primary btn-xs btn-block margin-graph-type-view" onClick="window.location.href='LoadGraphInformation?urlKey=${author.urlKey}&name=${author.name}'">Select another Graph</button>
		</div>
		<div class="modal bs-example-modal-lg" role="dialog" id="publicationInformationModal" aria-labelledby="publicationInformationModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header modal-header-custom">
						<button type="button" class="close close-custom" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="publicationInformationModalLabel"></h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal">
						  <div class="form-group">
						    <label class="col-md-2 control-label">Title:</label>
						    <div class="col-md-10">
						      <p class="form-control-static" id="publicationTitle"></p>
						    </div>
						  </div>
						  <div class="form-group">
						    <label class="col-md-2 control-label">Year:</label>
						    <div class="col-md-10">
						      <p class="form-control-static" id="publicationYear"></p>
						    </div>
						  </div>
						  <div class="form-group">
						    <label class="col-md-2 control-label">Venue:</label>
						    <div class="col-md-10">
						      <p class="form-control-static" id="publicationVenue"></p>
						    </div>
						  </div>
						  <div class="form-group">
						    <label class="col-md-2 control-label">Type:</label>
						    <div class="col-md-10">
						      <p class="form-control-static" id="publicationType"></p>
						    </div>
						  </div>
						  <div class="form-group">
						    <label class="col-md-2 control-label">Path:</label>
						    <div class="col-md-10">
						      <p class="form-control-static" id="publicationPath"></p>
						    </div>
						  </div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>