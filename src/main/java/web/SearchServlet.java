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
import search.SearchManager;
import textminer.Document;
import textminer.Preprocesser;
import crawler.NICrawler;
import crawler.Website;
import crawler.WebsiteWriter;

/**
 * Servlet implementation class Servlet
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PAGE_RANK = "page_rank";
	private static final String BOOLEAN = "boolean";
	private static final String VSR = "vsr";
	private static final String VSR_HTML= "vsr_html";
	
	private static final String KORPUS_FOLDER_NAME = "korpus";
	private static final String SEARCH_QUERY_KEY = "SEARCH_QUERY";
	
	
	

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
       	
    	//we create out of the search query a normal document
    	visitedWebsites.put(SEARCH_QUERY_KEY, new Website(SEARCH_QUERY_KEY,query,query,new ArrayList<String>()));
    	
    	
    	
    	WebsiteWriter websiteWriter = new WebsiteWriter(KORPUS_FOLDER_NAME);
    	websiteWriter.writeWebsites(visitedWebsites);
    	
 
    	ArrayList<Website> websites =
    		    new ArrayList<Website>(visitedWebsites.values());
    	
    //end crawling -----------------	
    		
		ArrayList<Document> docList;
		
		String stopwords = "WEB-INF/stopwords.txt";
		
		ServletContext context = getServletConfig().getServletContext();
		String fullPath = context.getRealPath(stopwords);
		stopwords = fullPath;
		
		File folderFile = new File(KORPUS_FOLDER_NAME);
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
		
			
		//Generate TF-IDF vector representation
		Document d = new Document();		
		docList = d.processAllDocuments(dir);
		
	
		SearchManager searchManager = new SearchManager();	
		searchManager.addDocumentsToWebsites(docList,websites);
		
    	
		out.write("<div id=\"search-for\"<br>Search for: <b>"+query+" </b>");
		
		
		Document documentQuery = searchManager.getSearchQueryOutOfWebsites(websites);
	
    
    	if(searchType.equals(PAGE_RANK))
    	{
    		System.out.println("Page Rank");
    		searchManager.performPageRankSearch(query, websites, out, documentQuery);
    	}
      	if(searchType.equals(BOOLEAN))
    	{
 
    searchManager.performBooleanSearch(query, websites, out, documentQuery);
    	}
     	if(searchType.equals(VSR))
    	{
    searchManager.performVSRSearch(query, websites, out,documentQuery);
    	}
     	if(searchType.equals(VSR_HTML))
    	{
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
