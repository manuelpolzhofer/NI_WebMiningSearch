package web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pagerank.PageRank;
import search.Query;
import search.SearchManager;
import textminer.Document;
import textminer.Preprocesser;
import crawler.NICrawler;
import crawler.Website;
import crawler.WebsiteWriter;

/**
 * Servlet implementation class Test2
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SearchServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String query = request.getParameter("query");
		String startpage = request.getParameter("startpage"); 
		String maxResults = request.getParameter("maxresults");
		String searchType = request.getParameter("ranking"); 
		
	
		out.write("<html><head><title>Search Result</title></head><body>");
		
		out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\""+request.getContextPath()+"result.css\">");
		out.write("<h3>Crawler</h3>"+"<div id=\"crawler-output\">");

	 //start crawling -----------
    	NICrawler niCrawler = new NICrawler();
    	String startUrl = startpage;	
    	int maxNumberOfVisit = 10;
    	try
    	{
    	 maxNumberOfVisit = Integer.parseInt(maxResults);
    	}
    	catch(Exception e)
    	{
    	
    		 maxNumberOfVisit = 10;
    		
    	}
    	niCrawler.startCrawling(startUrl,maxNumberOfVisit,out);
    	
    	HashMap<String,Website> visitedWebsites = niCrawler.getVisitedWebsites();
       	visitedWebsites.put("SEARCH_QUERY", new Website("SEARCH_QUERY",query,query,new ArrayList<String>()));
    	
    	
    	
    	WebsiteWriter websiteWriter = new WebsiteWriter("korpus");
    	websiteWriter.writeWebsites(visitedWebsites);
    	
 
    	ArrayList<Website> websites =
    		    new ArrayList<Website>(visitedWebsites.values());
    	
    //end crawling -----------------

    	
    	
    	
    	
    		
		ArrayList<Document> docList;
		
		String stopwords = "WEB-INF/stopwords.txt";
		
		ServletContext context = getServletConfig().getServletContext();
		String fullPath = context.getRealPath(stopwords);
		stopwords = fullPath;
		
		File folderFile = new File("korpus");
		File[] fileList = folderFile.listFiles();
		
		// Preprocessing
		File dir = null;
		try {
			dir = Preprocesser.applyStemmer((Preprocesser.removeStopWords(fileList, stopwords)).listFiles());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//String query = new String("")
		
		//Generate TF-IDF vector representation
		Document d = new Document(null,null,null);		
		docList = d.processAllDocuments(dir);
		SearchManager searchManager = new SearchManager();	
		searchManager.addDocumentsToWebsites(docList,websites);
		
    	
		out.write("<div id=\"search-for\"<br>Search for: <b>"+query+" </b>");
		
		//stemm query

		Query searchQuery  = new Query(2,query);
		Document documentQuery = null;
		try {
			documentQuery = Preprocesser.processQuery(searchQuery, stopwords);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    	if(searchType.equals("page_rank"))
    	{
    		searchManager.performPageRankSearch(query, websites, out, documentQuery);
    	}
      	if(searchType.equals("boolean"))
    	{
      		try {
      			searchQuery.setType(1);
    			documentQuery = Preprocesser.processQuery(searchQuery, stopwords);
    		} catch (Throwable e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		// documentQuery = Preprocesser.processQuery(new Query(1,query), stopwords);
    		searchManager.performBooleanSearch(query, websites, out, documentQuery);
    	}
     	if(searchType.equals("vsr"))
    	{
    		//documentQuery = Preprocesser.processQuery(new Query(2,query), stopwords);
    		searchManager.performVSRSearch(query, websites, out,documentQuery);
    	}
     	if(searchType.equals("vsr_html"))
    	{
    		// documentQuery = Preprocesser.processQuery(new Query(2,query), stopwords);
    		searchManager.performVSRHTMLSearch(query, websites, out,documentQuery);
    	}
		
		
		
	 
	 
	 
	 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
