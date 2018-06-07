package Main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SearchTerms {
	private JSONArray preferences;
	private JSONArray dietaryReq;
	private JSONArray allergies;
	private JSONArray keywords;
	private JSONArray ingredients;
	private enum typeArray {
		preferences, dietary_req, ingredients,allergies, keywords
	}
	/**
	 * Sets up the initial state of the object.
	 */
	public SearchTerms(){
		preferences = new JSONArray();
		dietaryReq = new JSONArray();
		allergies = new JSONArray();
		keywords = new JSONArray();
		ingredients = new JSONArray();
	}
	
	/**
	 * Finds a duplicate of string in a JSONArray and returns index of string 
	 * in array.
	 * @param jArray	JSONArray searching in
	 * @param test		String looking for in JSONArray
	 * @return 			the index of the string if found otherwise it 
	 * 					returns a value < 0;
	 */
	private int findIndex(JSONArray jArray, String test) {
		for (int i = 0; i < jArray.size(); i++) {
			if (test.equals(jArray.get(i))){
				return i;
			}
		}
		return -1;		
	}
	
	/**
	 * Returns reference to JSONArray of corresponding type of typeArray.
	 * @param type	the type of array requested.
	 * @return		reference to array that was requested.
	 */
	private JSONArray getCorrespondingArray(typeArray type) {
		switch (type) {
		case preferences:
			return preferences;
		case dietary_req:
			return dietaryReq;
		case allergies:
			return allergies;
		case keywords:
			return keywords;
		case ingredients:
			return ingredients;
		}
		return null;		
	}
	
	/**
	 * Adds a string to the array specified in type.
	 * @param type	type of the array to be added to.
	 * @param add	string to be added to array
	 */
	private void addToJArray(typeArray type, String add){
		add = add.toLowerCase();
		JSONArray array = getCorrespondingArray(type);
		if (array != null && findIndex(array, add) < 0) {
			array.add(add);
		}		
	}
	
	/**
	 * Adds preference to search terms
	 * @param preference
	 */
	public void addPrefrence(String pref) {
		addToJArray(typeArray.preferences, pref);
	}
	
	/**
	 * Adds allergy to search terms.
	 * @param allergy
	 */
	public void addAllergies(String allergy) {
		addToJArray(typeArray.allergies, allergy);
	}
	
	/**
	 * Adds dietary requirement to search terms.
	 * @param requirement
	 */
	public void addDietReq (String requirement) {
		addToJArray(typeArray.dietary_req, requirement);
	}
	
	/**
	 * Adds keyword to search terms.
	 * @param keyword
	 */
	public void addKeywords (String keyword) {
		addToJArray(typeArray.keywords, keyword);
	}
	
	/**
	 * Adds ingredient to search term
	 * @param ingr
	 */
	public void addIngredients(String ingr) {
		addToJArray(typeArray.ingredients, ingr);
	}
	
	/**
	 * Deletes a string from the array specified in type.
	 * @param type	type of the array to be added to.
	 * @param add	string to be deleted from array
	 */
	private void removeFromJArray(typeArray type, String delete){
		delete = delete.toLowerCase();
		JSONArray array = getCorrespondingArray(type);
		int i = findIndex(array, delete);
		if (i >= 0){
			array.remove(i);
		}
	}
	
	/**
	 * Removes a preference from search terms
	 * @param pref	preference
	 */
	public void removePrefrence(String pref) {
		removeFromJArray(typeArray.preferences, pref);
	}
	
	/**
	 * Removed allergy from search terms.
	 * @param allergy
	 */
	public void removeAllergies(String allergy) {
		removeFromJArray(typeArray.allergies, allergy);
	}
	
	
	/**
	 * Removes dietary requirement from search terms.
	 * @param requirement
	 */
	public void removeDietReq (String requirement) {
		removeFromJArray(typeArray.dietary_req, requirement);
	}
	
	/**
	 * Removes keyword from search terms.
	 * @param keyword
	 */
	public void removeKeywords (String keyword) {
		removeFromJArray(typeArray.keywords, keyword);
	}
	
	/**
	 * Removes ingredient from search terms.
	 * @param ingredient
	 */
	public void removeIngredients(String ingr) {
		removeFromJArray(typeArray.ingredients, ingr);
	}
	
	/**
	 * Returns a JSON string representation of the search terms.
	 */
	public String toString(){
		JSONObject buffer = new JSONObject(); 
		for (typeArray x : typeArray.values()) {
			buffer.put(x, getCorrespondingArray(x));
		}
	
		return buffer.toString().replace(" ", "%20").replace("\"", "%22");
	}
}

