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
		out.write("<h1>PageRank</h1>");
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
		 Collections.sort(results);
		 
		 this.printResults(results, out);
		
    	
   
		
	}
	public void printResults(List<Website> results, PrintWriter out)
	{
		for(int i = 0;i<results.size();i++)
		{
			out.write("<h2>"+results.get(i).getUrl()+ " "+results.get(i).pageRankNew+"<h2>");
		}
	}

	public void performBooleanSearch(String query, ArrayList<Website> websites,
			PrintWriter out, Document documentQuery) {

		out.write("<h1>TODO:Boolean</h1>");
		for(int i = 0;i<websites.size();i++)
		{
			if(websites.get(i).containsWordFromDocument(documentQuery))
			{
				out.write("<h2>"+websites.get(i).getUrl()+"</h2>");
			}
		}
		
		
		
	}

	public void performVSRSearch(String query, ArrayList<Website> websites,
			PrintWriter out, Document documentQuery) {
	
		List<Website> results = new ArrayList<Website>();
		
		out.write("<h1>VSR</h1>");
		
		for (int i= 0; i<websites.size(); i++){
			
			websites.get(i).cosDisToQuery = websites.get(i).getDocument().computeCosDistance(documentQuery);
			websites.get(i).setSortVSR(true);
			results.add(websites.get(i));
		}
		
System.out.println(results.toString());		
		//sort according to VSR
		 Collections.sort(results);
		 
		 this.printResultsVSR(results, out);
				
	}
	
	public void printResultsVSR(List<Website> results, PrintWriter out)
	{
		for(int i = 0;i<results.size();i++)
		{
			out.write("<h2>"+results.get(i).getUrl()+ " "+results.get(i).cosDisToQuery+"<h2>");
		}
	}

	public void performVSRHTMLSearch(String query, ArrayList<Website> websites,
			PrintWriter out, Document documentQuery) {
	
		out.write("<h1>TODO:VSR HTML</h1>");
		
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
