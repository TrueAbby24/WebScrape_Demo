package Main;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SearchResultsClient {
	private ArrayList<RecipeMiniClient> results;
	private int returnCount = 0;
	/**
	 * Initialises object
	 * @throws ParseException 
	 */
	public SearchResultsClient(String jsonString) throws ParseException{
		JSONParser parser = new JSONParser();
		JSONArray jsonArr = (JSONArray) parser.parse(jsonString);
		JSONObject obj;
		results = new ArrayList<>();
		for (int i = 0; i < jsonArr.size(); i++) {
			obj = (JSONObject) jsonArr.get(i);
			results.add(new RecipeMiniClient((String) obj.get("id"), 
					obj.get("imgUrl").toString(), 
					obj.get("title").toString(), obj.get("blurb").toString()));
			
		}
	}
	
	/**
	 * Returns recipe results one at a time.
	 * @return 	Returns a recipe, if it has returned all recipes otherwise null.
	 */
	public RecipeMiniClient getRecipeMini() {
		if (returnCount < results.size()) 
				return results.get(returnCount++);
		return null;
	}
	
}
