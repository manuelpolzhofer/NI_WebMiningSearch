/*
 * Author Manuel Polzhofer
 * 
 * 
 */

package crawler;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class NICrawler {
	
	
	private HashMap<String,Website> visitedWebsites;
	private ArrayList<String> todoListForCrawler;
	
	

	
	public NICrawler(){
	
	}
	
	public HashMap<String,Website> getVisitedWebsites()
	{
		return this.visitedWebsites;
		
	}
	
	private void fillCrawlerTODOList(Website website)
	{
		String acutalLink = null;
		for(int i = 0;i<website.getLinks().size();i++)
		{
			acutalLink = website.getLinks().get(i);
			if(visitedWebsites.containsKey(acutalLink) == false)
			{
				todoListForCrawler.add(acutalLink);
			}
			
		}
		
	}
	
	
	public void startCrawling(String startUrl,int maxNumberOfWebsites, PrintWriter out){
		out.write("fuu");
		visitedWebsites = new HashMap<String,Website>();
		todoListForCrawler = new ArrayList<String>();
		Website website = null;
		WebsiteParser websiteParser = new WebsiteParser();

		todoListForCrawler.add(startUrl);
		
		int websiteCounter = 0;
		

		while(todoListForCrawler.size() > 0 && websiteCounter < maxNumberOfWebsites ){
			
			website = websiteParser.parseWebsite(this.todoListForCrawler.get(0));
			this.todoListForCrawler.remove(0);
			
			if(website != null)
			{
			out.write("visited: "+website.getUrl());
			//System.out.println(""+website.text);
		
			
			visitedWebsites.put(website.getUrl(), website);
			fillCrawlerTODOList(website); //will add all links which are not visited
			}
			websiteCounter++;
		
			
		}
		System.out.println("Crawler Stopped - Visited Websites " +websiteCounter + " found Links which are not visted " + todoListForCrawler.size());
		
		
		
		
		
	}
	
  
	
	

}
