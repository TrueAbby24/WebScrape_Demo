package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;

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
//	private String searchUrl;
	private String searchResults;
	
	public SearchServer(String terms) throws ParseException, IOException{ 
		//take in user as param for pref and allergies 
//		construct URL
		String searchUrl = constructURL(terms);
		doSearch(searchUrl);
	}	
	
	private String arraySearchTerms(JSONArray j) {
		String result = "";
		for(int i = 0; i<j.size(); i++){
			if (i > 0)
				result += URL_ARRAY_DELIMITOR;
			result += ((String)j.get(i)).replace(" ", URL_SPACE);
			
		}
		return result;
	}
	
	private void addToJSONArray(JSONArray addTo, JSONArray contents) {
		for (int i = 0; i<contents.size(); i++) {
			addTo.add((String) contents.get(i));
		}
	}
	
	private JSONArray keywords(JSONObject jobj){
		JSONArray result = new JSONArray();
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
		return result;
	}
	private String constructURL(String terms) throws ParseException {
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
	}

	
	private void doSearch(String searchUrl) throws IOException{
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
	
	public String getResults() {
		JSONObject results = new JSONObject();
		results.put("type", "SearchResults");
		results.put("data", searchResults);
		return results.toString();
	}
}
