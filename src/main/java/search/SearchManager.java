package search;

import java.io.PrintWriter;
import java.util.ArrayList;

import pagerank.PageRank;
import textminer.Document;
import crawler.Website;

public class SearchManager {
	
	
	public void performPageRankSearch(String query,ArrayList<Website> websites, PrintWriter out)
	{
		out.write("<h1>PageRank</h1>");
	  	PageRank pageRanke = new PageRank(websites, out);
    	pageRanke.calculatePagranks();
		
	}

	public void performBooleanSearch(String query, ArrayList<Website> websites,
			PrintWriter out) {
		// TODO Auto-generated method stub
		out.write("<h1>TODO:Boolean</h1>");
		
	}

	public void performVSRSearch(String query, ArrayList<Website> websites,
			PrintWriter out) {
		// TODO Auto-generated method stub
		out.write("<h1>TODO:VSR</h1>");
	}

	public void performVSRHTMLSearch(String query, ArrayList<Website> websites,
			PrintWriter out) {
		// TODO Auto-generated method stub
		out.write("<h1>TODO:VSR HTML</h1>");
		
	}
	
	public void addDocumentsToWebsites(ArrayList<Document> docs, ArrayList<Website> websites)
	{
		for(int i = 0;i< websites.size();i++)
		{
			//if(websites.get(i).getFileName().equals(docs.get(i).g))
		}
		
	}





}
