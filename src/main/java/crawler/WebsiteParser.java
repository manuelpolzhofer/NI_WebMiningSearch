package crawler;

/*import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;*/
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteParser {
	
	
	   Document actualWebsite;

	public WebsiteParser()
	{
		
	}
	
	private void connectToWebsite(String url)throws IOException
	{
	    actualWebsite = Jsoup.connect(url).get();
	}
	
	
	  public  ArrayList<String>extractLinks(String url){
		    
		    ArrayList<String> result = new ArrayList<String>();
		
		    
		  
		    
		    Elements links = actualWebsite.select("a[href]");
		    for (Element link : links) {
		      result.add(link.attr("abs:href"));
		    }
		    return result;
		  }
	
	
	
	public Website parseWebsite(String url)
	{
		
		try 
		{
			connectToWebsite(url);
	
		} catch (Exception e) {
			
			return null; //broken url
		}
		
		ArrayList<String> links = null;
		links = this.extractLinks(url);
		String text = actualWebsite.body().text();
	    String title = actualWebsite.title();

		Website website = new Website(url,text,title,links);
		return website;
		
	}
	
	

}
