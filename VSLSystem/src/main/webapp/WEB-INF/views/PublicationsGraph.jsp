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
		
		<link href="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css" rel="stylesheet" />
		<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/select2-bootstrap.css" />" />
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/vis/4.2.0/vis.min.js"></script>
		
		<script type="text/javascript" src="<c:url value="/resources/js/graph-functions.js" />"> </script>
		<script type="text/javascript" src="<c:url value="/resources/js/loading-modal.js" />"> </script>
		
		<script src="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>
		
		<style>
			.modal-body h4, .modal-body h5, .modal-body dt{
				font-weight: bold;
			}
			dl{
				margin-left: 10px;
				text-align: justify;
			}
		</style>
	</head>
	<body onload="draw()">
		<div class="container">	
			<div class="page-header text-center page-header-custom">
		  		<h1><a id="HomeLink" href="Home" title="Home">VSCSystem</a> <small>Author's Publications</small></h1>
		  		<h3 class="sub-page-header"><small>Selected Author: ${author.name}</small></h3>
			</div>
			<div class="col-md-3 grid-panel-filter">
				<div class="panel panel-primary">
					<div class="panel-heading panel-heading-custom">
						FILTERS
					</div>
					<div class="panel-body">
						<form action="FilterPublicationsGraph" method="post">
							<div class="form-group form-group-filter">
		  						<label for="yearFilter">Year:</label>
								<select id="yearFilter" name="yearFilter" class="form-control" multiple="multiple">
									<c:forEach items="${yearsFilter}" var="yearValue">
										<option value="${yearValue}">${yearValue}</option>
									</c:forEach>
								</select>
		  					</div>
							<div class="form-group form-group-filter">
								<label for="typeFilter">Type:</label>
								<select id="typeFilter" name="typeFilter" class="form-control">
									<option></option>
									<c:forEach items="${typesFilter}" var="typeValue">
										<option ${typeValue == typeFiltered ? 'selected' : ''} value="${typeValue}">${typeValue}</option>
									</c:forEach>
								</select>  
		  					</div>
							<div class="form-group form-group-filter">
								<label for="venueFilter">Venue:</label>
								<select id="venueFilter" name="venueFilter" class="form-control">
									<option></option>
									<option value="">Select a venue...</option>
									<c:forEach items="${venuesFilter}" var="venueValue">
										<option ${venueValue == venueFiltered ? 'selected' : ''} value="${venueValue}">${venueValue}</option>
									</c:forEach>
								</select>
		  					</div>
		  					<div class="center-block text-center" style="margin-top: 40px;" >
								<button type="submit" class="btn btn-primary btn-sm" type="button" id="submitFilter">Apply Filter</button>
								<button type="button" class="btn btn-default btn-sm" onClick="clearFilter(false)">Clear</button>
								<button type="button" class="btn btn-primary btn-sm" id="backButton"
										onClick="window.location.href='LoadGraphInformation?urlKey=${author.urlKey}&name=${author.name}'">Select another Graph</button>
		  					</div>
		  					<div class="center-block text-center">
								<span id="infoIcon" class="glyphicon glyphicon-info-sign margin-graph-type-view infoIcon"></span>
		  					</div>
						</form>
					</div>
				</div>
			</div>
			<div class="col-md-9 grid-panel-graph">
				<div class="panel panel-primary">
					<div class="panel-heading panel-heading-custom">
						GRAPH
					</div>
					<div class="panel-body panel-body-graph">
						<div id="dvGraph" class="dv-graph" ></div>
					</div>
				</div>
			</div>
			<div class="modal" role="dialog" id="publicationInformationModal" aria-labelledby="publicationInformationModalLabel" aria-hidden="true">
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
			<div class="modal" role="dialog" id="informationModal" aria-labelledby="informationModalLabel" aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header modal-header-custom">
							<button type="button" class="close close-custom" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="informationModalLabel">Graph Information</h4>
						</div>
						<div class="modal-body">
							<jsp:include page="PublicationGraphInformation.jsp" />
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	
		$(function() {	
			$("#infoIcon").click(function(e) {
				$('#informationModal').modal();
			});
			$("#submitFilter").click(function(e) {
				waitingDialog.show("Applying filters...");
			});
			$("#backButton,#HomeLink").click(function(e) {
				waitingDialog.show("Please wait...");
			});

			<c:forEach items="${yearFiltered}" var="yearValue">
				$("#yearFilter option[value='" + <c:out value="${yearValue}" /> + "']").prop("selected", true);
			</c:forEach>
			
			loadSelectFilter(false);
		});
	
		var DIR = '/VSCSystem/resources/images/';	
	    var nodes = new vis.DataSet();
		var edges = new vis.DataSet();
		var network = null;
		
		function draw() {
			waitingDialog.show("Loading graph...");
	
			// create nodes
			nodes.add([
					{
						id : 1,
						label : "${author.name}",
						image : DIR + 'Author1.png',
						shape : 'image',
						isCoAuthor: false
					}		
			]);
	
			// create connections
			var color = '#BFBFBF';
			var countIdNodes = 2;
			var countCoAuthors = 1;
			<c:forEach items="${author.publications}" var="publication">
				nodes.add([
				   		{
				   			id : countIdNodes,
							label : "${publication.urlKey}",
							image : DIR + 'Paper.png',
							shape : 'image',
							urlKey 	: "${publication.urlKey}",
							title 	: "${publication.title}",
							year 	: "${publication.year}",
							type	: "${publication.type}",
							eePath  : "<a href='${publication.eePath}' target='_blank'>${publication.eePath}</a>",
							urlPath : "<a href='${publication.urlPath}' target='_blank'>${publication.urlPath}</a>",
							venue	: "${publication.venue}",
							isCoAuthor: false
				   		}		
				]);
				
				edges.add([
				   		{
				   			from : 1,
				 			to : countIdNodes,
				 			color : color
				   		}		
		   		]);
	
				var currPublicationId = countIdNodes;
				countIdNodes++;
				<c:forEach items="${publication.coAuthors}" var="coAuthor">
					nodes.add([
						   		{
									id : countIdNodes,
									label :"${coAuthor.name}",
									image : DIR + 'CoAuthor1.png',
									shape : 'image',
									isCoAuthor: true
						   		}		
			   		]);
					edges.add([
						   		{
						   			from : currPublicationId,
						 			to : countIdNodes,
						 			color : color
						   		}		
			   		]);
	
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
		            };
			network = new vis.Network(container, data, options);
			
			network.on('doubleClick', function (properties) {
				if(properties.nodes != 1){
				    var node = nodes.get(properties.nodes)[0];
				    onSelectNode(node);
				}
			});
	
			network.once("stabilizationIterationsDone", function() {
				waitingDialog.hide();
			});
		}
	</script>
</html>