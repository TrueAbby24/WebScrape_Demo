package Main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RecipeClient {
	private String title;
	private String cookingTime;
	private String servingSize;
	private String calories;
	private JSONArray ingredients;
	private int ingrCount;
	private JSONArray method;
	private int methodCount;
	
	/**
	 * Initialises recipe information
	 * @param jsonString		JSONString that contains all the recipes 
	 * 							information.
	 * @throws ParseException	Exception is thrown when JSON isn't 
	 * 							formatted correctly.
	 */
	public RecipeClient (String jsonString) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(jsonString);
		title = obj.get("name").toString();
		cookingTime = obj.get("cookingTime").toString();
		servingSize = obj.get("servingSize").toString();
		calories = obj.get("calories").toString();
		ingredients = (JSONArray) obj.get("ingredients");
		ingrCount = 0;
		method = (JSONArray) obj.get("method");
		methodCount = 0;		
	}
	
	/**
	 * Gives ingredients one at a time
	 * @return	returns ingredient, null if have returned all ingredients.
	 */
	public String getNextIngredient() {
		if (ingrCount < ingredients.size()) 
			return ingredients.get(ingrCount++).toString();
		return null;
	}
	
	/**
	 * Gives method of recipe step by step.
	 * @return	returns a step in the recipe, null if all has been returned.
	 */
	public String getNextStep() {
		if (methodCount < method.size())
			return method.get(methodCount++).toString();
		return null;
	}
	
	public String[] getMethodList() {
		String[] results = new String[method.size()];
		for (int i = 0; i<method.size(); i++) {
			results[i] = method.get(i).toString();
		}
		return results;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getCookingTime(){
		return cookingTime;
	}
	
	public String getServingSize() {
		return servingSize;
	}
	
	public String getCalories() {
		return calories;
	}
}
