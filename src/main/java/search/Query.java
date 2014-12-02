package search;

public class Query {

	
	private int type; //1 - Boolean, 2 - VSR,
	private String queryText;
		
	// Constructor
	public Query(int type, String queryText){
		
		this.setType(type);
		this.setQueryText(queryText);
	}

	// Getters and setters
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getQueryText() {
		return queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
}