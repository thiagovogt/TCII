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

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/vis/4.2.0/vis.min.css">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/vis/4.2.0/vis.min.js"></script>

<script type="text/javascript" src="<c:url value="/resources/js/functions.js" />"></script>

<script type="text/javascript">
	
	var DIR = '/VSCSystem/resources/images/';
    var nodes = new vis.DataSet();
	var edges = new vis.DataSet();
	var collaborations = [];
	var network = null;
	function draw() {
		// create nodes
		nodes.add([
					{
						id : 1,
						label : "${author.name}",
						image : DIR + 'Author1.png',
						shape : 'image',
					}		
		]);

		// create connections
		var color = '#BFBFBF';
		var countIdNodes = 2;
		
		<c:forEach items="${author.collaborations}" var="collaboration">
			nodes.add([
						{
							id : countIdNodes,
							label :"${collaboration.coAuthor.name}",
							image : DIR + 'CoAuthor1.png',
							shape : 'image'
						}		
			]);
			edges.add([
						{
							from : 1,
				 			to : countIdNodes,
				 			value : "${collaboration.numberOfCollaborations}",
				 			label : "${collaboration.numberOfCollaborations}",
				 			color : color
						}		
			]);
			
			var countAux = 1;
			var arrayAux = [];
			<c:forEach items="${collaboration.publications}" var="publication">
				var publicationLine = "<span style='color: #666666; font-weight:700;'> ${publication.title}</span>" + 
										" <span style='color:#7d848a;'>${publication.venue}</span> (${publication.year}) ";
				var hasPath = "";						
				<c:choose>
					<c:when test="${not empty publication.eePath}">
						hasPath = '${publication.eePath}';
					</c:when>
					<c:when test="${not empty publication.urlPath}">
						hasPath = '${publication.urlPath}';
					</c:when>
				</c:choose>
				if(hasPath != ""){
					publicationLine = publicationLine + "<a href='" + hasPath + "' target='_blank'><img title='Publication path' height='24' width='24' src='" +  DIR + "Paper.png'></a>";
				}
				arrayAux[countAux] = publicationLine;
				countAux++;
			</c:forEach>	
			collaborations[countIdNodes] = arrayAux; 
			countIdNodes++;
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

		network.on('selectNode', function (properties) {
			if(properties.nodes != 1){
			    var node = nodes.get(properties.nodes)[0];
			    window.open(encodeURI('../VSCSystem/ListAuthors?searchName='+node.label), '_blank');
			}
		});
		network.on('selectEdge', function (properties) {
		    var edge = edges.get(properties.edges)[0];
		    var nodeAuthor = nodes.get(edge.from);
		    var nodeCoAuthor = nodes.get(edge.to);

		    onSelectEdge(nodeAuthor, nodeCoAuthor, edge.value, collaborations[edge.to]);
		    network.unselectAll();
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
	  		<h1><a href="Home" title="Home">VSCSystem</a> <small>Author's collaborations</small></h1>
	  		<h3 class="sub-page-header"><small>Selected Author: ${author.name}</small></h3>
		</div>
		<div class="panel panel-primary">
			<div class="panel-heading panel-heading-custom">
				FILTERS
			</div>
			<div class="panel-body">
				<form class="form-inline" action="FilterCollaborations" method="post">
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
						<label for="venueFilter">Venue:</label>
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
				<div id="dvGraph" class="dv-graph"></div>
				<div id="loadingBar" style="opacity: 0; display: none;">
        <div class="outerBorder">
            <div id="text">100%</div>
            <div id="border">
                <div id="bar" style="width: 496px;"></div>
            </div>
        </div>
    </div>
			</div>
		</div>
		<div class="center-block text-center" style="max-width: 160px;" >	
			<button style="margin-top:-10px !important;" type="button" class="btn btn-primary btn-xs btn-block margin-graph-type-view" onClick="window.location.href='LoadGraphInformation?urlKey=${author.urlKey}&name=${author.name}'">Select another Graph</button>
		</div>
		<div class="modal bs-example-modal-lg" role="dialog" id="publicationListModal" aria-labelledby="publicationListModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header modal-header-custom">
						<button type="button" class="close close-custom" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="publicationListModalLabel"></h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal">
						  <div class="form-group" id="publications">
						    
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