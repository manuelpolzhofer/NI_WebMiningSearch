package search;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pagerank.PageRank;
import textminer.Document;
import crawler.Website;

public class SearchManager {
	
	
	public void performPageRankSearch(String query,ArrayList<Website> websites, PrintWriter out, Document documentQuery)
	{
		out.write("<h1>PageRank Results</h1>");
	  	PageRank pageRanke = new PageRank(websites, out);
    	pageRanke.calculatePagranks();
    	
    	List<Website> results = new ArrayList<Website>();
		for(int i = 0;i<websites.size();i++)
		{
			if(websites.get(i).containsWordFromDocument(documentQuery))
			{
				websites.get(i).setSortPageRank(true);
				results.add(websites.get(i));
			}
		}
		
		//sort according page ranks
		 Collections.sort(results,Collections.reverseOrder());
	
		 this.printResults(results, out);
		
    	
   
		
	}
	public void printResults(List<Website> results, PrintWriter out)
	{
		out.write("<h2>Results</h2>");
		out.write("<table>");
		out.write("<tr><b><td>nr.</td><td>title</td><td>url</td><td style=\"white-space:nowrap;\">page rank</td><td  style=\"white-space:nowrap;\">cos simularity to query</td></b>");
		for(int i = 0;i<results.size();i++)
		{
			Website w = results.get(i);
			if(w.getUrl().equals("SEARCH_QUERY") == false)
			{
			out.write("<tr>");
			out.write("<td>"+(i+1)+"</td>");
		    out.write("<td style=\"white-space:nowrap;\">"+w.getTitle()+"</td>");
			out.write("<td><a href=\""+w.getUrl()+"\">"+w.getUrl()+"</a></td>");
			out.write("<td>"+w.pageRankNew+"</td>");
			out.write("<td>"+w.cosDisToQuery+"</td>");
			out.write("</tr>");
			}
		}
		out.write("</table>");
	}

	public void performBooleanSearch(String query, ArrayList<Website> websites,
			PrintWriter out, Document documentQuery) {

		out.write("<h1>Boolean Search Results</h1>");
    	List<Website> results = new ArrayList<Website>();
		for(int i = 0;i<websites.size();i++)
		{
			if(websites.get(i).containsWordFromDocument(documentQuery))
			{
				results.add(websites.get(i));
			}
		}
		
		this.printResults(results, out);
		
		
		
	}

	public void performVSRSearch(String query, ArrayList<Website> websites,
			PrintWriter out, Document documentQuery) {
	
		List<Website> results = new ArrayList<Website>();
		
		out.write("<h1>Vector Space Retreival</h1>");
		
		
				
				for (int i= 0; i<websites.size(); i++){
					if(websites.get(i).getUrl().equals("SEARCH_QUERY"))
					{
						documentQuery = websites.get(i).getDocument();
					}
					 
				}
		
		for (int i= 0; i<websites.size(); i++){
			
			websites.get(i).cosDisToQuery = websites.get(i).getDocument().computeCosDistance(documentQuery);
			websites.get(i).setSortVSR(true);
			results.add(websites.get(i));
		}
		
		
		//sort according to VSR
		 Collections.sort(results,Collections.reverseOrder());
		 
		 this.printResults(results, out);
				
	}
	


	public void performVSRHTMLSearch(String query, ArrayList<Website> websites,
			PrintWriter out, Document documentQuery) {
	
		this.performVSRSearch(query, websites, out, documentQuery);
		
	}
	
	public void addDocumentsToWebsites(ArrayList<Document> docs, ArrayList<Website> websites)
	{
		for(int i = 0;i< websites.size();i++)
		{
	
			String websiteFileName = websites.get(i).getFileName();
			for(int j = 0;j<docs.size();j++)
			{
			if(websiteFileName.equals(docs.get(j).getFileName()))
			{
				//now the website has the stemmed content
				websites.get(i).setDocument(docs.get(j));
			
			}
			}
		}
		
	}





}
