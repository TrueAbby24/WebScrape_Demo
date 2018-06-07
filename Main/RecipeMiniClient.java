package Main;

public class RecipeMiniClient {
	private String id;
	private String imgUrl;
	private String title;
	private String blurb;
	
	/**
	 * Creates a recipe item with minimal information for displaying search results. 
	 * @param id
	 * @param imgUrl
	 * @param title
	 * @param blurb
	 */
	public RecipeMiniClient(String id, String imgUrl, String title,
			String blurb) {
		this.id = id;
		this.imgUrl = imgUrl;
		this.title = title;
		this.blurb = blurb;
	}
	
	public String getID() {
		return id;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public String getBlurb() {
		return blurb;
	}

	public String getTitle() {
		return title;
	}
	
	public String toString() {
		return "title: " + title + "\nid: "+id+"\nimgUrl: "+imgUrl+"\nblurb: "+ blurb; 
	}
}
