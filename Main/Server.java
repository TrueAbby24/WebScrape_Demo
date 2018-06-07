package Main;

import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Server {
	
	public static void main (String[] args) throws ParseException, IOException {
		JSONParser parser = new JSONParser();
		String toParse = "";
		for (String s : args) {
			toParse += s;
		}		
//		System.out.println(toParse);
		JSONObject obj = (JSONObject) parser.parse(toParse);
		String type = (String) obj.get("type");
//		System.out.println("type: " + type);
		if (type.equals("SearchTerms")){
			SearchServer s = new SearchServer((String) obj.get("data"));
			System.out.println(s.getResults());
		} else if (type.equals("RecipeID")) {
			RecipeServer recipe = new RecipeServer((String) obj.get("data"));
			System.out.println(recipe.getJSON());
		} else {
			System.out.println("INVALID JSON FORMAT");
		}
	}

}
