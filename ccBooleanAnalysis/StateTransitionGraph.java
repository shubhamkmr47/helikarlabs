package stateTransitionGraph;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JFileChooser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class StateTransitionGraph {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JFileChooser fileChooser = new JFileChooser();

		// This assumes user pressed Open
		int result = fileChooser.showOpenDialog(null);

		// Get the file from the file 
		File file = fileChooser.getSelectedFile();

		//solves boolean expression
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");

		//sets value of boolean
		engine.put("1", true);
		engine.put("0", false);

		JSONParser parser = new JSONParser(); 

		//total input species in the data
		ArrayList<String> totalSpecies = new ArrayList<String>();

		// Open the file
		try {
			Object obj = parser.parse(new FileReader(file));
			JSONObject jsonObj = (JSONObject)obj;

			//time of computation in milliseconds
			long startTime = System.currentTimeMillis();

			//checks for data to be success
			Boolean success = (Boolean)jsonObj.get("success");
			System.out.println("success: " + success);

			//number of input species
			JSONArray dataArray = (JSONArray)jsonObj.get("data");
			System.out.println("number of species: " + dataArray.size());

			JSONObject dataObj = new JSONObject();
			JSONObject object = new JSONObject();
			JSONObject tempObject = new JSONObject();

			String expression;

			int quotient, j, count = 0;

			//initialize all input species
			for (int k = 0; k < dataArray.size(); k++) {

				object = (JSONObject) dataArray.get(k);
				JSONArray species = (JSONArray)object.get("inputSpecies");

				for (int l = 0; l < species.size(); l++) {
					String inputSpecies = (String)species.get(l);

					//System.out.print(inputSpecies + " "); 
					if(!tempObject.containsKey(inputSpecies)){
						tempObject.put(inputSpecies, "0");
						totalSpecies.add(inputSpecies);
					}
				}
			}

			System.out.println(tempObject.size() + "is total number of species: " + tempObject + "\n");

			Integer[] binaryNumber = new Integer[tempObject.size()];
			Arrays.fill(binaryNumber, 0);

			//computing all possible states
			for (int i = 0; i < Math.pow(2, tempObject.size()); i++) {

				count++;
				quotient = i;
				j = tempObject.size() - 1;

				while(quotient != 0){

					binaryNumber[j] = quotient%2;
					quotient = quotient/2;
					j--;
				}

				//System.out.println(Arrays.toString(binaryNumber));

				for (int k = 0; k < totalSpecies.size(); k++) {
					tempObject.put(totalSpecies.get(k), binaryNumber[k]);
				}
				
				System.out.println(tempObject);

				for (int p = 0; p < dataArray.size(); p++) {

					dataObj = (JSONObject) dataArray.get(p);

					JSONArray inputSpeciesArray = (JSONArray)dataObj.get("inputSpecies");

					expression = (String)dataObj.get("expression");
					expression = expression.replaceAll("\\s","");

					//setting states of species
					for (int q = 0; q < inputSpeciesArray.size(); q++) {

						String inputSpecies = (String)inputSpeciesArray.get(q);

						expression = expression.replace(inputSpecies.toString(),
								tempObject.get(inputSpecies).toString());
					}

					String species = (String)dataObj.get("species");
					String result1 = engine.eval(expression).toString();
					
					if(result1 == "true")
						System.out.println(species + " = " + 1);
					else if(result1 == "false")
						System.out.println(species + " = " + 1);
					else
						System.out.println(species + " = " + result1);
				}

				System.out.println("\n");
			}

			System.out.println("Total number of states: " + count);
			long endTime = System.currentTimeMillis();
			System.out.println("It took " + (endTime - startTime) + " milliseconds");

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
