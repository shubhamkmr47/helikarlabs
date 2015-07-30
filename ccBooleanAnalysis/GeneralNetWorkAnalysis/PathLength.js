function getPath(source, target){

	source = getIndex(source);
	target = getIndex(target);
	
	var flag = false;
	
	var conMatrix = getMatrix();
	
	var length = nodes.length;
	
	var dist = new Array(length);
	var nodeSet = new Array(length);
	
	var queue = new Array();
	
	while (--length >= 0) {
		dist[length] = Number.POSITIVE_INFINITY;
		nodeSet[length] = false;
	}
	
	queue.push(source);
	
	dist[source] = 0;
	nodeSet[source] = true;
	
	while(queue.length != 0){
		
		var node = queue.shift();
		
		var distance = dist[node];
		
		for(i in conMatrix){
			
			var sourceIndex = conMatrix[i].source;
			var targetIndex = conMatrix[i].target;
			
			if(nodeSet[targetIndex] == false && sourceIndex == node){
				
				queue.push(targetIndex);
				
				nodeSet[targetIndex] = true;
				dist[targetIndex] = distance + 1;
				
				if(targetIndex == target){
					flag = true;
					break;
				}
			}
		}	
		if(flag == true)
			break;
	}
	
	if(flag == true){
		return dist[target];
	}
	else
		return Number.POSITIVE_INFINITY;
}

function getIndex(specie){
	var index = nodes.indexOf(specie);
	return index;
}
