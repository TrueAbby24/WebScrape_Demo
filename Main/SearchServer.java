package Main;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SearchServer {
//	private final static String LINE_SEPARATOR = System.getProperty(
//            "line.separator");
	private final static String BASE_URL = 
			"https://www.allrecipes.com/search/?";
	private final static char URL_ARRAY_DELIMITOR = ',';
	private final static String URL_SPACE = "%20";
	private final static char URL_SEARCH_LINK = '&';
	private final static String RECIPE_URL = 
			"https://www.allrecipes.com/recipe/";
	private static String searchResults;
	
	public static void main(String[] args) throws IOException{
		String terms = "";
		for (String s : args) {
			terms += s;
		}
		String url = constructURL(terms);
		if (url == null) {
			System.out.println((new JSONObject()).toString());
			return;
		}		
		doSearch(url);
		System.out.println(getResults());
	}


	
	private static String arraySearchTerms(JSONArray j) {
		String result = "";
		for(int i = 0; i<j.size(); i++){
			if (i > 0)
				result += URL_ARRAY_DELIMITOR;
			result += ((String)j.get(i)).replace(" ", URL_SPACE);
			
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private static JSONArray keywords(JSONObject jobj){
		JSONArray result = new JSONArray();
		try {
			JSONArray buffer = null;
			if(jobj.get("preferences") instanceof JSONArray){
				buffer = (JSONArray) jobj.get("preferences");
			}
			if (buffer.size() != 0)
				result.addAll(buffer);
			
			if(jobj.get("dietary_req") instanceof JSONArray){
				buffer = (JSONArray) jobj.get("dietary_req");
			}
			if (buffer.size() != 0){
				result.addAll(buffer);
			}
			if(jobj.get("keywords") instanceof JSONArray){
				buffer = (JSONArray) jobj.get("keywords");
			}
			if (buffer.size() != 0){
				result.addAll(buffer);
			}		
		} catch (Exception e) {
			;
		}
		return result;
	}
	private static String constructURL(String terms) {
		try{
			String result = new String(BASE_URL);
			String allergies, ingredients, keywords;
	//		convert to JSON 
			JSONParser parse = new JSONParser();
			JSONObject jobj = (JSONObject) parse.parse(terms);
	//		break-up JSON		
			JSONArray jKeywords = keywords(jobj);		
			keywords = arraySearchTerms(jKeywords);		
			allergies = arraySearchTerms((JSONArray) jobj.get("allergies"));
			ingredients = arraySearchTerms((JSONArray) jobj.get("ingredients"));
	//		construct URL
			boolean prevPart = false;
			if (keywords.length() > 0) {
				result += "wt=" + keywords;
				prevPart = true;
			}
			if (allergies.length() > 0) {
				if (prevPart == true) {
					result += URL_SEARCH_LINK;
				}
				result += "ingExcl=" + allergies;
				prevPart = true;
			}
			if (ingredients.length() > 0) {
				if (prevPart == true) {
					result += URL_SEARCH_LINK;
				}
				result += "ingIncl=" + ingredients;
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void doSearch(String searchUrl) throws IOException{
//		Web Scrape
		final Document doc = Jsoup.connect(searchUrl).get();
		JSONArray result = new JSONArray();
		JSONObject recipe;
		for (Element recipeCard : doc.select("#fixedGridSection "
				+ "article.fixed-recipe-card")){
//			break up recipe data
			String title = recipeCard.select(".fixed-recipe-card__info h3")
					.text();
			String blurb = recipeCard.select(".fixed-recipe-card__info "
					+ ".fixed-recipe-card__description").text();
			String imgUrl = recipeCard.select("img.fixed-recipe-card__img")
					.attr("data-original-src");
			String recipeUrl = recipeCard.select("h3+a").attr("href");
			String id = recipeUrl.substring(RECIPE_URL.length());
			id = id.substring(0,id.indexOf('/'));
//			save recipe data
			recipe = new JSONObject();
			recipe.put("title", title);
			recipe.put("blurb", blurb);
			recipe.put("imgUrl", imgUrl);
			recipe.put("id", id);
			result.add(recipe);
		}
		searchResults = result.toString();
	}
	
	private static String getResults() {
		return searchResults.toString();
	}
}
