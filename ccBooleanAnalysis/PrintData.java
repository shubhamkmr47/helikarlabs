package ccBooleanAnalysis;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PrintData {
	
	public PrintData() throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
		System.setOut(out);
	}
	
	public void printSpecies(JSONObject object){
		
		JSONArray tempArray;
		
		tempArray = (JSONArray) object.get("components");
		
		System.out.println("Total species: " + tempArray.size());
		
		System.out.println("Species: " + tempArray);
	}
	
	public void printStates(JSONObject object){
		
		JSONObject tempObj = new JSONObject();
		
		tempObj = (JSONObject) object.get("transitions");
		
		String state;
		
		StateTransitionGraph stgObj = new StateTransitionGraph();
		
		System.out.println("Total states: " + tempObj.size());
		
		for (int i = 0; i < tempObj.size(); i++) {
			
			state = stgObj.generateBinary(i);
			
			System.out.println(state + " " + tempObj.get(state));
		}
	}
	
	public void printTime(long start, long end){
		System.out.println("Time taken: " + (end - start) + " milliseconds");
	}
}
