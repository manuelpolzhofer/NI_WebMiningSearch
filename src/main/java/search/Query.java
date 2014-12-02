package search;

import stemmer.SnowballStemmer;
import textminer.Preprocesser;

public class Query {
	
	
	public String stemmQuery(String query) throws InstantiationException, IllegalAccessException
	{
	Class stemClass = null;
	try {
		stemClass = Class.forName("stemmer.ext." + Preprocesser.language + "Stemmer");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
	
	stemmer.setCurrent(query);
	return stemmer.getCurrent();
	
	}

}
