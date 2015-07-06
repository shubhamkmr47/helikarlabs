package ccBooleanAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import javax.script.ScriptException;
import javax.swing.JFileChooser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Analysis {

	private static File INPUT_FILE;
	
	@SuppressWarnings("unused")
	private static JSONArray dataArray = new JSONArray();
	
	private static StateTransitionGraph stgObj = new StateTransitionGraph();
	private static AttractorAnalysis attObj = new AttractorAnalysis();
	private static AverageConnectivity avgConObj = new AverageConnectivity();

	public static void main(String[] args) throws FileNotFoundException, ScriptException, IOException, ParseException{
		// TODO Auto-generated method stub

		JFileChooser fileChooser = new JFileChooser();

		// This assumes user pressed Open
		@SuppressWarnings("unused")
		int result = fileChooser.showOpenDialog(null);
		
		// Get the file from the file 
		INPUT_FILE = fileChooser.getSelectedFile();
		
		PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
		System.setOut(out);

		stateTransitionAnalysis();
		//attractorStateAnalysis();
		averageConnectivity();
	}
	
	private static JSONObject getFileObj() throws FileNotFoundException, IOException, ParseException{
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(INPUT_FILE));
		JSONObject jsonObj = (JSONObject)obj;
		
		return jsonObj;
	}
	
	private static void stateTransitionAnalysis() throws FileNotFoundException, ScriptException, IOException, ParseException{
		
		JSONObject obj = getFileObj();
		dataArray = (JSONArray) obj.get("data");
		stgObj.findStateTransitionGraph(obj);		
	}
	
	private static void attractorStateAnalysis(){
		
		JSONObject obj = StateTransitionGraph.getTransitions();
		attObj.attractorAnalysis(obj);
	}
	
	private static void averageConnectivity() throws FileNotFoundException, IOException, ParseException{
		
		JSONObject obj = getFileObj();
		dataArray = (JSONArray) obj.get("data");
		JSONArray components = stgObj.getComponents();
		avgConObj.getAverageConnectivity(dataArray, components);
		
	}
}
