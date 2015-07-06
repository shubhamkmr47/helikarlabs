package ccBooleanAnalysis;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AverageConnectivity {

	public void getAverageConnectivity(JSONArray dataArray){

		//time of computation in milliseconds
		long startTime = System.currentTimeMillis();
		
		float average = 0;
		int size;

		JSONObject speciesObj = new JSONObject();
		JSONArray inputSpeciesArray = new JSONArray();

		String species;

		for (int i = 0; i < dataArray.size(); i++) {
			speciesObj = (JSONObject) dataArray.get(i);

			species = (String) speciesObj.get("species");
			inputSpeciesArray = (JSONArray) speciesObj.get("inputSpecies");
			
			size = inputSpeciesArray.size();
			
			average = average + size;

			System.out.println("Specie: " + species + " = " + size);
		}
		
		average = average/dataArray.size();
		
		long endTime = System.currentTimeMillis();
		
		System.out.println("Average Connectivity: " + average);
		System.out.println("Time taken: " + (endTime - startTime) + " milliseconds");
	}
}
