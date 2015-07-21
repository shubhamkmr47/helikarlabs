function getConnectivity(){
	
	var inDegree;
	var conMatrix = getMatrix();
	
	if(conMatrix.length == 0){
		getMatrix();
	}
	
	var len = nodes.length;
	
	inDegree = new Array(len);
	while (--len >= 0) {
		inDegree[len] = 0;
	}
	
	
	for (i in conMatrix){
		
		var target = conMatrix[i].target;
		
		inDegree[target]++;
	}
	
	return inDegree;
}

