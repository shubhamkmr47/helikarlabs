var data, connection;
var nodes = [];

function initData(nodesObj, connObj){

	data = nodesObj;
	connection = connObj;
	
	//load nodes in nodes[] array 
	getNodes();
}

function getNodes(){

	for (i in data){
		//document.write("Label: " + data[i].label + "<br>");
		nodes.push(data[i].label);
	}

	document.write("<b>" +"Nodes order: " + "</b>" + nodes.toString() + "<br>");
};