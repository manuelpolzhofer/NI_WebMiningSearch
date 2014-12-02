package crawler;

import java.io.Serializable;
import java.util.ArrayList;

import textminer.Document;

public class Website implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String url;
	ArrayList<String> links;
	String text;
	String title;
	private String fileName;
	
	
	private Document document;
	
	
    public double pageRankOld = 0;
    public double pageRankNew = 0;
    
    public void setFileName(String fileName)
    {
    	this.fileName = fileName;
    }
    public String getFileName()
    {
    	return fileName;
    	
    }
    
    public int numberOfOutgoingLinks() {
        return this.links.size();
        
    }
    public boolean isLinkedWithPage(Website website) {
        String vertexUrl = website.getUrl();
          for ( String weburl : this.links )
          {
              if(vertexUrl.equals(weburl))
              {
                  return true;
              }
          }
        return false;
    }
    public boolean hasNoOutgoingLinks() {
        if(this.links.size() == 0)
        {
            return true;
        }
        return false;
    }
    
    
	
	public Website(String url,String text, String title, ArrayList<String> links)
	{
		this.url = url;
		this.links = links;
		this.text = text;
		this.title = title;
		
	}
	
	public String getUrl()
	{
		return this.url;
	}
	
	
	public String getTitle()
	{
		return this.title;
	}
	
	
	public ArrayList<String> getLinks()
	{
		return this.links;
	}
	
	public String getText()
	{
		return this.text;
	}
	
	public String toString()
	{
		String string = "url: "+this.url+"\n";
		for(int i = 0;i<links.size();i++)
		{
			string = string+"Link:"+links.get(i)+"\n";
		}
		return string;
		
		
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}
