var doubleClickTime = 0;
var threshold = 200;

function onClick(properties) {
    var t0 = new Date();
    if (t0 - doubleClickTime > threshold) {
        setTimeout(function () {
            if (t0 - doubleClickTime > threshold) {
                doOnClick(properties);
            }
        },threshold);
    }
}
function doOnClick(properties) {
//    console.log("execute onClick function -  properties:" + properties.nodes );
    
}

function onDoubleClick(properties, node) {
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