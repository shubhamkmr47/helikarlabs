package ccBooleanAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JFileChooser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class StateTransitionGraph {

	//total input species in the data
	private static ArrayList<String> totalSpecies = new ArrayList<String>();
	private static ArrayList<String> expressionString = new ArrayList<String>();
	private static JSONArray dataArray = new JSONArray();
	private static int dataSize, totalSize;
	
	

	//total number of species including external species.
	private static void initializeSpecies(){

		JSONObject object;
		JSONArray speciesArray;
		String species;

		for (int i = 0; i < dataSize; i++) {

			object = (JSONObject)dataArray.get(i);
			species = (String) object.get("species");

			if(!totalSpecies.contains(species)){
				totalSpecies.add(species);
			}
		}

		for (int i = 0; i < dataSize; i++) {

			object = (JSONObject) dataArray.get(i);
			speciesArray = (JSONArray) object.get("inputSpecies");

			for (int j = 0; j < speciesArray.size(); j++) {

				species = (String)speciesArray.get(j);

				if(!totalSpecies.contains(species)){
					totalSpecies.add(species);
				}	
			}
		}
		totalSize = totalSpecies.size();
		System.out.println(totalSize + " " + totalSpecies);
	}

	//binary value of a number
	private static String generateBinary(int state){

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


	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		JFileChooser fileChooser = new JFileChooser();

		// This assumes user pressed Open
		@SuppressWarnings("unused")
		int result = fileChooser.showOpenDialog(null);

		// Get the file from the file 
		File file = fileChooser.getSelectedFile();
		
		PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
		System.setOut(out);

		//solves boolean expression
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");

		//sets value of boolean
		engine.put("1", true);
		engine.put("0", false);

		JSONParser parser = new JSONParser(); 

		// Open the file
		try {
			Object obj = parser.parse(new FileReader(file));
			JSONObject jsonObj = (JSONObject)obj;

			//time of computation in milliseconds
			long startTime = System.currentTimeMillis();

			int count = 0;

			//checks for data to be success
			Boolean success = (Boolean)jsonObj.get("success");
			System.out.println("Data success: " + success);

			if(success.booleanValue() == true){
				//number of input species
				dataArray = (JSONArray)jsonObj.get("data");
				dataSize = dataArray.size();

				JSONObject dataObj;
				JSONArray inputSpeciesArray;

				String inputSpecies, expression, answer, oldAnswer = "";

				initializeSpecies();

				expressionString = new ArrayList<String>(Collections.
						nCopies(dataSize, "0"));


				//all possible 2^n states
				for (int i = 0; i < Math.pow(2, totalSize); i++) {

					String state = generateBinary(i);

					count++;
					answer = "";

					System.out.print(state + " => ");

					for (int j = 0; j < dataSize; j++) {

						dataObj = (JSONObject)dataArray.get(j);

						inputSpeciesArray = (JSONArray)dataObj.get("inputSpecies");

						expression = (String) dataObj.get("expression");
						expression = expression.replaceAll("\\s","");

						//evaluate boolean expression
						for (int k = 0; k < inputSpeciesArray.size(); k++) {

							inputSpecies = (String)inputSpeciesArray.get(k);

							int index = totalSpecies.indexOf(inputSpecies);

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

					System.out.println(answer);
				}
				System.out.println();

				System.out.println("Total number of states: " + count);
				long endTime = System.currentTimeMillis();
				System.out.println("It took " + (endTime - startTime) + " milliseconds");
			}
			else{
				System.out.println("Data success: false");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}