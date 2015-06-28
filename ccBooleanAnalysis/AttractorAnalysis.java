package ccBooleanAnalysis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import org.json.simple.JSONObject;

public class AttractorAnalysis {

	private static JSONObject graph;
	private static JSONObject visitObj;

	private static Stack<String> stack= new Stack<String>();

	private static ArrayList<String> steadyStates = new ArrayList<String>();
	private static ArrayList<String> attractors = new ArrayList<String>();

	public void attractorAnalysis(JSONObject transitions) {
		// TODO Auto-generated method stub

		String source, target, visit;

		//time of computation in milliseconds
		long startTime = System.currentTimeMillis();

		graph = (JSONObject) transitions;

		visitObj = (JSONObject) graph.clone();
		System.out.println();

		for(Iterator<?> iterator = graph.keySet().iterator();
				iterator.hasNext();){

			source = (String)iterator.next();
			target = (String) graph.get(source);
			visit = (String) visitObj.get(source);

			target = pushStack(source);

			if(stack.contains(target))
				popStack(target); 
			stack.removeAllElements();
		}
		System.out.println("Steady States: " + steadyStates);

		long endTime = System.currentTimeMillis();

		System.out.println("Time taken (if state transitions are known): " + (endTime - startTime) + " milliseconds");
	}

	private void popStack(String node) {
		// TODO Auto-generated method stub

		String temp = stack.peek();

		String tempString;

		while(!node.equals(temp)){

			tempString = stack.pop();
			attractors.add(tempString);
			steadyStates.add(tempString);
			temp =stack.peek();
		}

		tempString = stack.pop();
		attractors.add(tempString);
		steadyStates.add(tempString);
		
		System.out.println("Attractors: " + attractors);
		attractors.clear();
	}

	private String pushStack(String node){

		String visit = (String) visitObj.get(node);

		while(visit.length() > 1){

			stack.push(node);
			visitObj.put(node, "1");
			node = (String) graph.get(node);
			visit = (String) visitObj.get(node);
		}

		return node;
	}
}
