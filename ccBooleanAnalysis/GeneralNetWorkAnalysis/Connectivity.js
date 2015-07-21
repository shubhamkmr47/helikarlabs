var inDegree;

function getConnectivity(){
	if(conMatrix.length == 0){
		getMatrix();
	}
	
	document.write("<br><br> <b> Connectivity </b>");
	
	initInDegree(nodes.length);
	
	for (i in conMatrix){
		
		var source = conMatrix[i].source;
		var target = conMatrix[i].target;
		
		inDegree[target]++;
	}
	
	document.write(inDegree);
}

function initInDegree(length){
	
	inDegree = new Array(length);
	while (--length >= 0) {
		inDegree[length] = 0;
	}	
}
 getConnectivity();