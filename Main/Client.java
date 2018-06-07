package Main;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Client {

	public static void main(String[] args) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(args[0]);
		String type = (String) obj.get("type");
		if (type.equals("SearchResults")){
			SearchResultsClient src = new SearchResultsClient(
					(String) obj.get("data"));
//			use different methods to get data
		} else if (type.equals("RecipeResult")) {
			RecipeClient rc = new RecipeClient((String) obj.get("data"));
//			use different methods to get data
		} else {
			System.out.println("problem :(");
		}
	}
	
}
