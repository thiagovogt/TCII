function onSelectNode(node) {
    doubleClickTime = new Date();
	if(node.isCoAuthor){
		window.open(encodeURI('../VSCSystem/ListAuthors?searchName='+node.label), '_blank');
	}else{
		if(node.eePath != "" && node.urlPath != ""){
			$("#publicationPath").html(node.eePath + "<br>"+ node.urlPath);
		}else if(node.eePath != ""){
			$("#publicationPath").html(node.eePath);
		}else if(node.urlPath != ""){
			$("#publicationPath").html(node.urlPath);
		}
					
		$("#publicationInformationModalLabel").html('Publication - ' + node.urlKey);
		$("#publicationTitle").html(node.title);
		$("#publicationYear").html(node.year);
		$("#publicationVenue").html(node.venue);
		$("#publicationType").html(node.type);
	
		$('#publicationInformationModal').modal();
	}
}

function onSelectEdge(nodeAuthor, nodeCoAuthor, countPublications, publications) {
	console.log(publications);
	$("#publicationListModalLabel").html('Collaborations with ' + nodeCoAuthor.label + ' (' + countPublications + ')');
	
	$("#publications").html("");
	for (var int = 1; int < publications.length; int++) {
		$('#publications').append('<div class="col-md-12" style="padding: 0 20px;">' + 
									'<p class="form-control-static">' + publications[int] + '</p><hr class="soften"></div>');	
	}
	$("#publications hr").last().remove();
	
	$('#publicationListModal').modal();
}