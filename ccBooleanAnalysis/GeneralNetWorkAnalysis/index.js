var getMatrix = function(){

	var data = input_data1.nodes;
	var connection = input_data1.edges;

	var nodes = [];

	for (i in data){
		//document.write("Label: " + data[i].label + "<br>");
		nodes.push(data[i].label);
	}

	document.write("<b>" +"Nodes order: " + "</b>" + nodes.toString() + "<br>");

	var conMatrix = [];

	for (i in connection){
		//document.write(connection[i].source.label + " = " + connection[i].target.label + "<br>");

		var edge = new Object();
		edge.source = getIndex(connection[i].source.label);
		edge.target = getIndex(connection[i].target.label);

		conMatrix.push(edge);
	}

	for (i in conMatrix){

		source_index = getIndex(conMatrix[i].source);
		target_index = getIndex(conMatrix[i].target);
		document.write("(" + conMatrix[i].source + ", " + conMatrix[i].target + "), ");	
	}

	function getIndex(specie){

		var index = nodes.indexOf(specie);
		return index;
	}
};

getMatrix();
