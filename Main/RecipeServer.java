package Main;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class RecipeServer {
	private static String recipeID ;
	private static final String BASE_RECIPE_URL = 
			"https://www.allrecipes.com/recipe/";
	private static String cookingTime;
	private static String servingSize;
	private static String name; 
	private static String calories;
	private static ArrayList<String> ingredients;
	private static ArrayList<String> method;
	private static boolean success = true;

	/**
	 * Searches for a recipe given a certain id.
	 * @param args recipe id
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {		
		try {
			recipeID = args[0];
		} catch (Exception e) {
			System.out.println((new JSONObject()).toString());
			return;
		}
		findDetails();
		if (success) 
			System.out.println(getJSON());
		else
			System.out.println((new JSONObject()).toString());
	}
	
	/**
	 * Extracts recipes information from AllRecipes.
	 * @throws IOException
	 */
	private static void findDetails() {
		try {
			String buffer = BASE_RECIPE_URL + recipeID;
			final Document doc = Jsoup.connect(buffer).get();
			name = doc.select("h1.recipe-summary__h1").text();
			cookingTime = doc.select("span.ready-in-time").text();
			servingSize = doc.select("#metaRecipeServings").attr("content");
			calories = doc.select(".calorie-count span:first-child").text();
			ingredients = new ArrayList<>();
			for (Element ingredientItem : doc.select("ul#lst_ingredients_1 li")){
				buffer = ingredientItem.select("label").attr("title");
				if (!buffer.equals("")){
					ingredients.add(buffer);
				}
			}
			for (Element ingredientItem : doc.select("ul#lst_ingredients_2 li")){
				buffer = ingredientItem.select("label").attr("title");
				if (!buffer.equals("")){
					ingredients.add(buffer);
				}
			}	
	
			method = new ArrayList<>();
			for (Element step : doc.select("ol.list-numbers.recipe-directions__list"
					+ " li.step") ){			
				buffer = step.select("span.recipe-directions__list--item").text();
				if(!buffer.equals("")){
					method.add(buffer);
				}
			}	
		} catch (Exception e) {
			success = false;
		}
	}
	
	/**
	 * Generates JSON string of all the information about the recipe.
	 * @return JSON string
	 */
	@SuppressWarnings("unchecked")
	private static String getJSON() {
		JSONObject result = new JSONObject();
		result.put("cookingTime", cookingTime);
		result.put("servingSize", servingSize);
		result.put("name", name);
		result.put("calories", calories);
		result.put("ingredients", ingredients);
		result.put("method", method);
		return result.toJSONString();
	}
	
		
}