package ccBooleanAnalysis;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class StateTransitionGraph {

	private static JSONObject transitionStates = new JSONObject();
	private static JSONObject graph = new JSONObject();

	private static JSONArray components = new JSONArray();
	private static JSONObject transitions = new JSONObject();

	private static ArrayList<String> expressionString = new ArrayList<String>();
	private static JSONArray dataArray = new JSONArray();
	private static int dataSize, totalSize;

	//total number of species including external species.
	@SuppressWarnings("unchecked")
	private static void initializeSpecies(){

		JSONObject object;
		JSONArray speciesArray;
		String species;

		for (int i = 0; i < dataSize; i++) {

			object = (JSONObject)dataArray.get(i);
			species = (String) object.get("species");

			if(!components.contains(species)){
				components.add(species);
			}
		}

		for (int i = 0; i < dataSize; i++) {

			object = (JSONObject) dataArray.get(i);
			speciesArray = (JSONArray) object.get("inputSpecies");

			for (int j = 0; j < speciesArray.size(); j++) {

				species = (String)speciesArray.get(j);

				if(!components.contains(species)){
					components.add(species);
				}	
			}
		}
		transitionStates.put("components", components);

		JSONObject temp = new JSONObject();
		temp.put("components", components);
		graph.put("metadata", temp);
		totalSize = components.size();
	}

	//binary value of a number
	public static String generateBinary(int state){

		String str = Integer.toBinaryString(state);

		int len = totalSize - str.length();
		String ch = "0";
		char[] chars = new char[len];

		Arrays.fill(chars, ch.charAt(0));

		String value = new String(chars);
		value = value.concat(str);

		return value;
	}

	private static int format(String result){

		if(result == "true")
			return 1;
		else if(result == "false")
			return 0;
		else
			return Character.getNumericValue(result.charAt(0));
	}

	public static JSONObject getTransitions(){
		return transitions;
	}

	@SuppressWarnings("unchecked")
	public void findStateTransitionGraph(JSONObject jsonObj) throws ScriptException, FileNotFoundException{
		// TODO Auto-generated method stub

		//time of computation in milliseconds
		long startTime = System.currentTimeMillis();

		//solves boolean expression
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");

		//sets value of boolean
		engine.put("1", true);
		engine.put("0", false);

		//checks for data to be success
		Boolean success = (Boolean)jsonObj.get("success");
		System.out.println("Data success: " + success);

		if(success.booleanValue() == true){

			//number of input species
			dataArray = (JSONArray)jsonObj.get("data");
			dataSize = dataArray.size();

			JSONObject dataObj = new JSONObject();
			JSONObject tempObj = new JSONObject();
			JSONObject tempObj1 = new JSONObject();
			JSONArray inputSpeciesArray;

			JSONArray nodes = new JSONArray();
			JSONArray edges = new JSONArray();

			String inputSpecies, expression, answer, oldAnswer = "";

			initializeSpecies();

			expressionString = new ArrayList<String>(Collections.
					nCopies(dataSize, "0"));

			for (int i = 0; i < Math.pow(2, totalSize); i++) {

				String state = generateBinary(i);

				tempObj1.put("id", state);
				nodes.add(tempObj1);


				answer = "";

				for (int j = 0; j < dataSize; j++) {

					dataObj = (JSONObject)dataArray.get(j);

					inputSpeciesArray = (JSONArray)dataObj.get("inputSpecies");

					expression = (String) dataObj.get("expression");
					expression = expression.replaceAll("\\s","");

					//evaluate boolean expression
					for (int k = 0; k < inputSpeciesArray.size(); k++) {

						inputSpecies = (String)inputSpeciesArray.get(k);

						int index = components.indexOf(inputSpecies);

						expression = expression.replace(inputSpecies,
								state.charAt(index) + "");
					}

					if(expressionString.get(j).equals(expression)){
						answer = answer.concat(oldAnswer.charAt(j) + "");						
					}
					else{
						expressionString.set(j, expression);
						String evaluate = engine.eval(expression).toString();
						answer = answer.concat(Integer.toString(format(evaluate)));
					}
				}

				answer = answer.concat(state.substring(dataSize));
				oldAnswer = answer;

				transitions.put(state, answer);

				tempObj.put("source", state);
				tempObj.put("target", answer);
				edges.add(tempObj);

			}
			graph.put("nodes", nodes);
			graph.put("edges", edges);
			transitionStates.put("transitions", transitions);

			long endTime = System.currentTimeMillis();

			/*printTime(startTime, endTime);
			printSpecies(transitionStates);
			printStates(transitionStates);*/
		}
		else{
			System.out.println("Data success: false");
		}
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
		System.out.println("Total states: " + tempObj.size());

		for (int i = 0; i < tempObj.size(); i++) {
			state = generateBinary(i);
			System.out.println(state + " " + tempObj.get(state));
		}
	}

	public void printTime(long start, long end){
		System.out.println("Time taken: " + (end - start) + " milliseconds");
	}

}