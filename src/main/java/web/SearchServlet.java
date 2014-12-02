package web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pagerank.PageRank;
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
		
	
		
		
		out.write("<h3>Crawler</h3>"+"<div id=\"crawler-output\">");

		out.write("</div><br>");

		out.write(query);
		out.write(startpage);
		out.write(maxResults);
		out.write(searchType);
		
		out.write("<div id=\"search-for\">Search for: <b>fuu </b>");
	

		
	 
		out.write("search results");
	
	 
		out.write("NI WebCrawler");
    	NICrawler niCrawler = new NICrawler();
    	String startUrl = "http://en.wikipedia.org/wiki/Data_mining";
    	//String startUrl = "http://derstandard.at";
    	int maxNumberOfVisit = 5;
    	niCrawler.startCrawling(startUrl,maxNumberOfVisit,out);
    	
    	HashMap<String,Website> visitedWebsites = niCrawler.getVisitedWebsites();
 
    	WebsiteWriter websiteWriter = new WebsiteWriter("korpus");
    	websiteWriter.writeWebsites(visitedWebsites);
    	
    	ArrayList<Website> websites =
    		    new ArrayList<Website>(visitedWebsites.values());
    	
    	PageRank pageRanke = new PageRank(websites);
    	pageRanke.calculatePagranks();
    	
    	
		/*ArrayList<Document> docList;
		
		
		String stopwords = "../stopwords.txt";
		
		
		
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
		Document d = new Document(null,null);		
		docList = d.processAllDocuments(dir);
		
		
	 */
	 
	 
	 
	 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
