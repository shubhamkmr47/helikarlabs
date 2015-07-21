function getMatrix(){
	
	var conMatrix = [];

	for (i in connection){
		//document.write(connection[i].source.label + " = " + connection[i].target.label + "<br>");

		var edge = new Object();
		edge.source = getIndex(connection[i].source.label);
		edge.target = getIndex(connection[i].target.label);

		conMatrix.push(edge);
	}
	
	return conMatrix;
}

function getIndex(specie){

	var index = nodes.indexOf(specie);
	return index;
}

