package Main;

import java.io.IOException;
import java.util.ArrayList;

import javax.print.DocFlavor.STRING;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.sun.xml.internal.bind.v2.model.util.ArrayInfoUtil;

public class WebScrape {



	private static void imdbExample() throws IOException{
		final Document doc = Jsoup.connect("https://www.imdb.com/chart/top").get();
		
		for (Element row : doc.select("table.chart.full-width tr")){
			final String title = row.select(".titleColumn a").text();
			final String rating = row.select(".imdbRating").text();
			System.out.println(title + "--> Rating: " + rating);
			
		}
	}
	
	private static void firstTry() throws Exception{
		final String recipeBaseUrl = "https://www.allrecipes.com/recipe/";
		String url = "https://www.allrecipes.com/search/?wt=chocolate&ingIncl=nut";
		final Document doc = Jsoup.connect(url).get();
//		System.out.println(doc);
		int i = 0;
		for (Element recipeCard : doc.select("#fixedGridSection article.fixed-recipe-card")){
			i++;
			String title = recipeCard.select(".fixed-recipe-card__info h3").text();
			String blurb = recipeCard.select(".fixed-recipe-card__info .fixed-recipe-card__description").text();
			String imgUrl = recipeCard.select("img.fixed-recipe-card__img").attr("data-original-src");
			String recipeUrl = recipeCard.select("h3+a").attr("href");
			String id = recipeUrl.substring(recipeBaseUrl.length());
			id = id.substring(0,id.indexOf('/'));
			
			System.out.format("%d-->%s(%s)\n",i,title,id);
			System.out.println("    "+ blurb);
			System.out.println("    img: "+ imgUrl);
			System.out.println("    recipe source: " + recipeUrl);
		}
//		System.out.println("TOTAL: " + i);
	}
	
	public static void main(String[] args) throws Exception {
		
//		SEARCH TERMS
		String buffer;
		SearchTerms terms = new SearchTerms();
		terms.addDietReq("vegan");
		terms.addAllergies("nuts");
		terms.addAllergies("nuts");
		terms.addKeywords("white chocolate");
		terms.addIngredients("flour");
		terms.addIngredients("egg");
		System.out.println("1.)  User search data:" + terms.toString());
//		SEARCH		
		buffer = HttpRequest.getSearchResults(terms.toString());
		System.out.println();
		System.out.println("2.)  Get users search results: " +buffer);
		SearchResultsClient src = new SearchResultsClient(buffer);
		RecipeMiniClient mini = src.getRecipeMini();
		while (mini != null) {
			System.out.println("-->" + mini.getTitle());
			System.out.println("Recipe ID: "+mini.getID());
			System.out.println("Img Url: "+mini.getImgUrl());
			System.out.println("Blurb: "+mini.getBlurb());			
			mini = src.getRecipeMini();
		}
		System.out.println();
		System.out.println("3.)  User chooses specific recipe (for example recipe with ID '257193')");
		buffer = HttpRequest.getRecipe("257193");
		System.out.println();
		System.out.println("4.)  Client receives all information from server on recipe 257193: \n" + buffer);
		RecipeClient rc = new RecipeClient(buffer);
		System.out.println("Title: " + rc.getTitle());
		System.out.println("Serving Size: "+rc.getServingSize());
		System.out.println("Calories: "+rc.getCalories());
		
		buffer = rc.getNextIngredient();
		System.out.println("Ingredients: ");
		while (buffer != null) {
			System.out.println("--"+buffer);
			buffer = rc.getNextIngredient();
		}
		int i = 1;
		buffer = rc.getNextStep();
		System.out.println("Steps: ");
		while (buffer != null) {
			System.out.println(i+"-"+buffer);
			buffer = rc.getNextStep();
			i++;
		}		
	}
}
